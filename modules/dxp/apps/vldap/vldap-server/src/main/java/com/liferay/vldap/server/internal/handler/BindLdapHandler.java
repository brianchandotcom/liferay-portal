/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.vldap.server.internal.handler;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.NoSuchCompanyException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.Authenticator;
import com.liferay.portal.kernel.service.CompanyLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.vldap.server.internal.handler.util.LdapHandlerContext;
import com.liferay.vldap.server.internal.handler.util.LdapHandlerThreadLocal;
import com.liferay.vldap.server.internal.handler.util.SaslCallbackHandler;
import com.liferay.vldap.server.internal.util.PortletPropsValues;

import java.net.InetSocketAddress;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.sasl.Sasl;
import javax.security.sasl.SaslException;
import javax.security.sasl.SaslServer;

import org.apache.directory.api.ldap.model.message.BindRequest;
import org.apache.directory.api.ldap.model.message.BindResponse;
import org.apache.directory.api.ldap.model.message.Request;
import org.apache.directory.api.ldap.model.message.Response;
import org.apache.directory.api.ldap.model.message.ResultCodeEnum;
import org.apache.directory.api.ldap.model.name.Dn;
import org.apache.directory.api.ldap.model.name.Rdn;
import org.apache.directory.api.util.StringConstants;
import org.apache.mina.core.session.IoSession;

/**
 * @author Jonathan Potter
 * @author Brian Wing Shun Chan
 */
public class BindLdapHandler extends BaseLdapHandler {

	public static final String DIGEST_MD5 = "DIGEST-MD5";

	@Override
	public List<Response> messageReceived(
			Request request, IoSession ioSession,
			LdapHandlerContext ldapHandlerContext)
		throws PortalException {

		BindRequest bindRequest = (BindRequest)request;

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Bind request from ", ioSession.getRemoteAddress(), " for ",
					bindRequest.getName()));
		}

		String saslMechanism = GetterUtil.getString(
			bindRequest.getSaslMechanism());

		Response response = null;

		if (saslMechanism.equals(DIGEST_MD5)) {
			response = getSaslResponse(
				bindRequest, ioSession, ldapHandlerContext);
		}
		else if (bindRequest.isSimple()) {
			response = getSimpleResponse(bindRequest, ldapHandlerContext);
		}
		else {
			response = getUnsupportedResponse(bindRequest);
		}

		return toList(response);
	}

	protected String getSaslHostName(IoSession ioSession) {
		String saslHostName = PortletPropsValues.BIND_SASL_HOSTNAME;

		if (Validator.isNull(saslHostName)) {
			InetSocketAddress inetSocketAddress =
				(InetSocketAddress)ioSession.getLocalAddress();

			saslHostName = inetSocketAddress.getHostName();
		}

		if (_log.isDebugEnabled()) {
			_log.debug("SASL host name " + saslHostName);
		}

		return saslHostName;
	}

	protected Response getSaslResponse(
			BindRequest bindRequest, IoSession ioSession,
			LdapHandlerContext ldapHandlerContext)
		throws PortalException {

		if (bindRequest.getCredentials() == null) {
			bindRequest.setCredentials(StringConstants.EMPTY);
		}

		SaslServer saslServer = ldapHandlerContext.getSaslServer();

		try {
			if (saslServer == null) {
				synchronized (ldapHandlerContext) {
					saslServer = ldapHandlerContext.getSaslServer();

					if (saslServer == null) {
						SaslCallbackHandler saslCallbackHandler =
							new SaslCallbackHandler();

						ldapHandlerContext.setSaslCallbackHandler(
							saslCallbackHandler);

						saslServer = Sasl.createSaslServer(
							DIGEST_MD5, _LDAP, getSaslHostName(ioSession), null,
							saslCallbackHandler);

						ldapHandlerContext.setSaslServer(saslServer);
					}
				}
			}

			BindResponse bindResponse =
				(BindResponse)bindRequest.getResultResponse();

			byte[] challenge = saslServer.evaluateResponse(
				bindRequest.getCredentials());

			bindResponse.setServerSaslCreds(challenge);
		}
		catch (SaslException se) {
			_log.error(se, se);

			ldapHandlerContext.setSaslCallbackHandler(null);
			ldapHandlerContext.setSaslServer(null);

			return getResultResponse(
				bindRequest, ResultCodeEnum.INVALID_CREDENTIALS);
		}

		if (saslServer.isComplete()) {
			SaslCallbackHandler saslCallbackHandler =
				ldapHandlerContext.getSaslCallbackHandler();

			ldapHandlerContext.setSaslCallbackHandler(null);
			ldapHandlerContext.setSaslServer(null);

			Dn name = saslCallbackHandler.getName();

			setCompany(ldapHandlerContext, name);
			setUser(ldapHandlerContext, name);

			return getResultResponse(bindRequest, ResultCodeEnum.SUCCESS);
		}

		return getResultResponse(
			bindRequest, ResultCodeEnum.SASL_BIND_IN_PROGRESS);
	}

	protected Response getSimpleResponse(
			BindRequest bindRequest, LdapHandlerContext ldapHandlerContext)
		throws PortalException {

		Dn name = bindRequest.getDn();

		if (Validator.isNull(name.getName())) {
			return getResultResponse(bindRequest, ResultCodeEnum.SUCCESS);
		}

		Company company = setCompany(ldapHandlerContext, name);

		String screenName = getValue(name, "cn");

		if (Validator.isNull(screenName)) {
			screenName = getValue(name, "uid");
		}

		String emailAddress = getValue(name, "mail");
		String password = new String(bindRequest.getCredentials());
		Map<String, String[]> headerMap = new HashMap<>();
		Map<String, String[]> parameterMap = new HashMap<>();
		Map<String, Object> resultsMap = new HashMap<>();

		int authResult = Authenticator.FAILURE;

		if (Validator.isNotNull(screenName)) {
			authResult = UserLocalServiceUtil.authenticateByScreenName(
				company.getCompanyId(), screenName, password, headerMap,
				parameterMap, resultsMap);
		}
		else if (Validator.isNotNull(emailAddress)) {
			if (isEmailAddressWhitelisted(emailAddress)) {
				authResult = Authenticator.SUCCESS;
			}
			else {
				authResult = UserLocalServiceUtil.authenticateByEmailAddress(
					company.getCompanyId(), emailAddress, password, headerMap,
					parameterMap, resultsMap);
			}
		}

		if (authResult != Authenticator.SUCCESS) {
			return getResultResponse(
				bindRequest, ResultCodeEnum.INVALID_CREDENTIALS);
		}

		setUser(ldapHandlerContext, name);

		return getResultResponse(bindRequest, ResultCodeEnum.SUCCESS);
	}

	protected Response getUnsupportedResponse(BindRequest bindRequest) {
		return getResultResponse(
			bindRequest, ResultCodeEnum.AUTH_METHOD_NOT_SUPPORTED);
	}

	protected String getValue(Dn dn, String normType) {
		for (Rdn rdn : dn) {
			if (StringUtil.equalsIgnoreCase(rdn.getNormType(), normType)) {
				return GetterUtil.getString(rdn.getValue());
			}
		}

		return StringPool.BLANK;
	}

	protected boolean isEmailAddressWhitelisted(String emailAddress) {
		for (String allowedEmailAddress :
				PortletPropsValues.EMAIL_ADDRESSES_WHITELIST) {

			String[] parts = StringUtil.split(
				allowedEmailAddress, StringPool.COLON);

			if (!emailAddress.equals(parts[0])) {
				continue;
			}

			if (parts.length == 1) {
				return true;
			}

			String[] hostsAllowed = StringUtil.split(
				parts[1], StringPool.SEMICOLON);

			if (LdapHandlerThreadLocal.isHostAllowed(hostsAllowed)) {
				return true;
			}
		}

		return false;
	}

	protected Company setCompany(LdapHandlerContext ldapHandlerContext, Dn name)
		throws PortalException {

		String webId = getValue(name, "webId");

		Company company = null;

		try {
			company = CompanyLocalServiceUtil.getCompanyByWebId(webId);
		}
		catch (NoSuchCompanyException nsce) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to get company with web ID " + webId, nsce);
			}

			long companyId = PortalUtil.getDefaultCompanyId();

			company = CompanyLocalServiceUtil.getCompany(companyId);
		}

		ldapHandlerContext.setCompany(company);

		return company;
	}

	protected void setUser(LdapHandlerContext ldapHandlerContext, Dn name)
		throws PortalException {

		User user = null;

		boolean allowDefaultUser = false;

		String screenName = getValue(name, "cn");

		if (Validator.isNull(screenName)) {
			screenName = getValue(name, "uid");
		}

		String emailAddress = getValue(name, "mail");

		if (Validator.isNotNull(screenName)) {
			user = UserLocalServiceUtil.fetchUserByScreenName(
				ldapHandlerContext.getCompanyId(), screenName);
		}
		else if (Validator.isNotNull(emailAddress)) {
			if (isEmailAddressWhitelisted(emailAddress)) {
				user = UserLocalServiceUtil.getDefaultUser(
					ldapHandlerContext.getCompanyId());

				allowDefaultUser = true;
			}
			else {
				user = UserLocalServiceUtil.fetchUserByEmailAddress(
					ldapHandlerContext.getCompanyId(), emailAddress);
			}
		}

		if ((user != null) && (!user.isDefaultUser() || allowDefaultUser)) {
			ldapHandlerContext.setUser(user);
		}
	}

	private static final String _LDAP = "ldap";

	private static final Log _log = LogFactoryUtil.getLog(
		BindLdapHandler.class);

}