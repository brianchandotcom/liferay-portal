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

package com.liferay.saml.opensaml.integration.internal.profile;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.servlet.HttpHeaders;
import com.liferay.portal.kernel.servlet.HttpMethods;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.opensaml.integration.SamlBinding;
import com.liferay.saml.opensaml.integration.internal.transport.HttpClientInTransport;
import com.liferay.saml.opensaml.integration.internal.transport.HttpClientOutTransport;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.opensaml.integration.internal.util.SamlUtil;
import com.liferay.saml.opensaml.integration.metadata.MetadataManager;
import com.liferay.saml.persistence.model.SamlIdpSpSession;
import com.liferay.saml.persistence.model.SamlIdpSsoSession;
import com.liferay.saml.persistence.model.SamlSpSession;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalService;
import com.liferay.saml.persistence.service.SamlIdpSpSessionLocalService;
import com.liferay.saml.persistence.service.SamlIdpSsoSessionLocalService;
import com.liferay.saml.persistence.service.SamlSpSessionLocalService;
import com.liferay.saml.runtime.SamlException;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;
import com.liferay.saml.runtime.exception.UnsolicitedLogoutResponseException;
import com.liferay.saml.runtime.exception.UnsupportedBindingException;
import com.liferay.saml.runtime.profile.SingleLogoutProfile;
import com.liferay.saml.util.JspUtil;
import com.liferay.saml.util.SamlHttpRequestUtil;

import java.io.Writer;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.client.methods.HttpPost;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import org.opensaml.common.IdentifierGenerator;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.SAMLVersion;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.LogoutRequest;
import org.opensaml.saml2.core.LogoutResponse;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.SessionIndex;
import org.opensaml.saml2.core.Status;
import org.opensaml.saml2.core.StatusCode;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml2.metadata.SSODescriptor;
import org.opensaml.saml2.metadata.SingleLogoutService;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.ws.message.decoder.MessageDecoder;
import org.opensaml.ws.message.encoder.MessageEncoder;
import org.opensaml.ws.security.SecurityPolicyResolver;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.opensaml.ws.transport.http.HttpServletResponseAdapter;
import org.opensaml.xml.security.credential.Credential;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Mika Koivisto
 */
@Component(immediate = true, service = SingleLogoutProfile.class)
public class SingleLogoutProfileImpl
	extends BaseProfile implements SingleLogoutProfile {

	@Override
	public boolean isSingleLogoutSupported(HttpServletRequest request) {
		try {
			MetadataProvider metadataProvider =
				metadataManager.getMetadataProvider();

			String entityId = metadataManager.getDefaultIdpEntityId();

			EntityDescriptor entityDescriptor =
				metadataProvider.getEntityDescriptor(entityId);

			IDPSSODescriptor idpSSODescriptor =
				entityDescriptor.getIDPSSODescriptor(SAMLConstants.SAML20P_NS);

			SingleLogoutService singleLogoutService =
				SamlUtil.resolveSingleLogoutService(
					idpSSODescriptor, SAMLConstants.SAML2_REDIRECT_BINDING_URI);

			if (singleLogoutService != null) {
				String binding = singleLogoutService.getBinding();

				if (!binding.equals(SAMLConstants.SAML2_SOAP11_BINDING_URI)) {
					return true;
				}
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to verify single logout support", e);
			}
		}

		return false;
	}

	@Override
	public void processIdpLogout(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException {

		String requestPath = _samlHttpRequestUtil.getRequestPath(request);

		try {
			response.addHeader(
				HttpHeaders.CACHE_CONTROL,
				HttpHeaders.CACHE_CONTROL_NO_CACHE_VALUE);
			response.addHeader(
				HttpHeaders.PRAGMA, HttpHeaders.PRAGMA_NO_CACHE_VALUE);

			if (requestPath.equals("/c/portal/logout")) {
				initiateIdpSingleLogout(request, response);
			}
			else if (requestPath.equals("/c/portal/saml/slo_logout")) {
				SamlSloContext samlSloContext = getSamlSloContext(
					request, null);

				if (samlSloContext == null) {
					redirectToLogout(request, response);

					return;
				}

				String cmd = ParamUtil.getString(request, Constants.CMD);

				if (Validator.isNull(cmd)) {
					request.setAttribute(
						SamlWebKeys.SAML_SLO_CONTEXT,
						samlSloContext.toJSONObject());

					JspUtil.dispatch(
						request, response, JspUtil.PATH_PORTAL_SAML_SLO,
						"single-sign-out");
				}
				else if (cmd.equals("logout")) {
					performIdpSpLogout(request, response, samlSloContext);
				}
				else if (cmd.equals("finish")) {
					performIdpFinishLogout(request, response, samlSloContext);
				}
				else if (cmd.equals("status")) {
					performIdpStatus(request, response, samlSloContext);
				}
			}
		}
		catch (Exception e) {
			ExceptionHandlerUtil.handleException(e);
		}
	}

	@Override
	public void processSingleLogout(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException {

		SamlBinding samlBinding = null;

		String method = request.getMethod();
		String requestPath = _samlHttpRequestUtil.getRequestPath(request);

		if (requestPath.endsWith("/slo") &&
			StringUtil.equalsIgnoreCase(method, HttpMethods.GET)) {

			samlBinding = getSamlBinding(
				SAMLConstants.SAML2_REDIRECT_BINDING_URI);
		}
		else if (requestPath.endsWith("/slo") &&
				 StringUtil.equalsIgnoreCase(method, HttpMethods.POST)) {

			samlBinding = getSamlBinding(SAMLConstants.SAML2_POST_BINDING_URI);
		}
		else if (requestPath.endsWith("/slo_soap") &&
				 StringUtil.equalsIgnoreCase(method, HttpMethods.POST)) {

			samlBinding = getSamlBinding(
				SAMLConstants.SAML2_SOAP11_BINDING_URI);
		}
		else {
			throw new UnsupportedBindingException();
		}

		try {
			SAMLMessageContext<?, ?, ?> samlMessageContext = decodeSamlMessage(
				request, response, samlBinding, true);

			Object inboundSamlMessage =
				samlMessageContext.getInboundSAMLMessage();

			if (inboundSamlMessage instanceof LogoutRequest) {
				processSingleLogoutRequest(
					request, response,
					(SAMLMessageContext<LogoutRequest, LogoutResponse, NameID>)
						samlMessageContext);
			}
			else if (inboundSamlMessage instanceof LogoutResponse) {
				processSingleLogoutResponse(
					request, response,
					(SAMLMessageContext<LogoutResponse, ?, ?>)
						samlMessageContext);
			}
			else {
				throw new SamlException(
					"Unrecognized inbound SAML message " +
						inboundSamlMessage.getClass());
			}
		}
		catch (Exception e) {
			ExceptionHandlerUtil.handleException(e);
		}
	}

	@Override
	public void processSpLogout(
			HttpServletRequest request, HttpServletResponse response)
		throws PortalException {

		try {
			sendSpLogoutRequest(request, response);
		}
		catch (Exception e) {
			ExceptionHandlerUtil.handleException(e);
		}
	}

	@Override
	@Reference(unbind = "-")
	public void setIdentifierGenerator(
		IdentifierGenerator identifierGenerator) {

		super.setIdentifierGenerator(identifierGenerator);
	}

	@Override
	@Reference(unbind = "-")
	public void setMetadataManager(MetadataManager metadataManager) {
		super.setMetadataManager(metadataManager);
	}

	@Reference(
		cardinality = ReferenceCardinality.AT_LEAST_ONE,
		policyOption = ReferencePolicyOption.GREEDY, unbind = "unsetSamlBinding"
	)
	public void setSamlBinding(SamlBinding samlBinding) {
		addSamlBinding(samlBinding);
	}

	@Override
	@Reference(unbind = "-")
	public void setSamlProviderConfigurationHelper(
		SamlProviderConfigurationHelper samlProviderConfigurationHelper) {

		super.setSamlProviderConfigurationHelper(
			samlProviderConfigurationHelper);
	}

	@Override
	public void terminateSpSession(
		HttpServletRequest request, HttpServletResponse response) {

		try {
			SamlSpSession samlSpSession = getSamlSpSession(request);

			if (samlSpSession == null) {
				return;
			}

			samlSpSessionLocalService.deleteSamlSpSession(samlSpSession);

			addCookie(
				request, response, SamlWebKeys.SAML_SP_SESSION_KEY,
				StringPool.BLANK, 0);
		}
		catch (SystemException se) {
			_log.error(se, se);
		}
	}

	@Override
	public void terminateSsoSession(
		HttpServletRequest request, HttpServletResponse response) {

		String samlSsoSessionId = getSamlSsoSessionId(request);

		if (Validator.isNotNull(samlSsoSessionId)) {
			try {
				SamlIdpSsoSession samlIdpSsoSession =
					_samlIdpSsoSessionLocalService.fetchSamlIdpSso(
						samlSsoSessionId);

				if (samlIdpSsoSession != null) {
					_samlIdpSsoSessionLocalService.deleteSamlIdpSsoSession(
						samlIdpSsoSession);

					List<SamlIdpSpSession> samlIdpSpSessions =
						_samlIdpSpSessionLocalService.getSamlIdpSpSessions(
							samlIdpSsoSession.getSamlIdpSsoSessionId());

					for (SamlIdpSpSession samlIdpSpSession :
							samlIdpSpSessions) {

						_samlIdpSpSessionLocalService.deleteSamlIdpSpSession(
							samlIdpSpSession);
					}
				}
			}
			catch (SystemException se) {
				_log.error(se, se);
			}
		}

		addCookie(
			request, response, SamlWebKeys.SAML_SSO_SESSION_ID,
			StringPool.BLANK, 0);
	}

	protected void addSessionIndex(
		LogoutRequest logoutRequest, String sessionIndexString) {

		if (Validator.isNull(sessionIndexString)) {
			return;
		}

		List<SessionIndex> sessionIndexes = logoutRequest.getSessionIndexes();

		SessionIndex sessionIndex = OpenSamlUtil.buildSessionIndex(
			sessionIndexString);

		sessionIndexes.add(sessionIndex);
	}

	protected SamlSloContext getSamlSloContext(
			HttpServletRequest request,
			SAMLMessageContext<LogoutRequest, LogoutResponse, NameID>
				samlMessageContext)
		throws Exception {

		HttpSession session = request.getSession();

		SamlSloContext samlSloContext = (SamlSloContext)session.getAttribute(
			SamlWebKeys.SAML_SLO_CONTEXT);

		String samlSsoSessionId = getSamlSsoSessionId(request);

		if (samlMessageContext != null) {
			LogoutRequest logoutRequest =
				samlMessageContext.getInboundSAMLMessage();

			List<SessionIndex> sessionIndexes =
				logoutRequest.getSessionIndexes();

			if (!sessionIndexes.isEmpty()) {
				SessionIndex sessionIndex = sessionIndexes.get(0);

				samlSsoSessionId = sessionIndex.getSessionIndex();
			}
		}

		if ((samlSloContext == null) && Validator.isNotNull(samlSsoSessionId)) {
			SamlIdpSsoSession samlIdpSsoSession =
				_samlIdpSsoSessionLocalService.fetchSamlIdpSso(
					samlSsoSessionId);

			if (samlIdpSsoSession != null) {
				samlSloContext = new SamlSloContext(
					samlIdpSsoSession, samlMessageContext,
					_samlIdpSpConnectionLocalService,
					_samlIdpSpSessionLocalService, _userLocalService);

				samlSloContext.setSamlSsoSessionId(samlSsoSessionId);
				samlSloContext.setUserId(portal.getUserId(request));

				session.setAttribute(
					SamlWebKeys.SAML_SLO_CONTEXT, samlSloContext);
			}
		}

		return samlSloContext;
	}

	protected void initiateIdpSingleLogout(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		SamlSloContext samlSloContext = getSamlSloContext(request, null);

		if (samlSloContext != null) {
			String portalURL = portal.getPortalURL(request);

			String pathMain = portal.getPathMain();

			String redirect = portalURL.concat(pathMain).concat(
				"/portal/saml/slo_logout");

			response.sendRedirect(redirect);
		}
		else {
			redirectToLogout(request, response);
		}
	}

	protected void performIdpFinishLogout(
			HttpServletRequest request, HttpServletResponse response,
			SamlSloContext samlSloContext)
		throws Exception {

		if (samlSloContext.getSamlMessageContext() != null) {
			String statusCode = StatusCode.SUCCESS_URI;

			for (SamlSloRequestInfo samlRequestInfo :
					samlSloContext.getSamlSloRequestInfos()) {

				String samlRequestInfoStatusCode =
					samlRequestInfo.getStatusCode();

				if (!samlRequestInfoStatusCode.equals(StatusCode.SUCCESS_URI)) {
					statusCode = StatusCode.PARTIAL_LOGOUT_URI;

					break;
				}
			}

			sendIdpLogoutResponse(
				request, response, statusCode, samlSloContext);
		}
		else {
			redirectToLogout(request, response);
		}
	}

	protected void performIdpSpLogout(
			HttpServletRequest request, HttpServletResponse response,
			SamlSloContext samlSloContext)
		throws Exception {

		String entityId = ParamUtil.getString(request, "entityId");

		SamlSloRequestInfo samlSloRequestInfo =
			samlSloContext.getSamlSloRequestInfo(entityId);

		if (samlSloRequestInfo == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Received logout request for service provider " + entityId +
						" that the user is not logged into");
			}

			JspUtil.dispatch(
				request, response, JspUtil.PATH_PORTAL_SAML_ERROR,
				"single-sign-out", true);

			return;
		}

		if (samlSloRequestInfo.getStatus() ==
				SamlSloRequestInfo.REQUEST_STATUS_SUCCESS) {

			request.setAttribute(
				SamlWebKeys.SAML_SLO_REQUEST_INFO,
				samlSloRequestInfo.toJSONObject());

			JspUtil.dispatch(
				request, response, JspUtil.PATH_PORTAL_SAML_SLO_SP_STATUS,
				"single-sign-out", true);

			return;
		}

		SAMLMessageContext<LogoutResponse, LogoutRequest, NameID>
			samlMessageContext =
				(SAMLMessageContext<LogoutResponse, LogoutRequest, NameID>)
					getSamlMessageContext(request, response, entityId);

		SPSSODescriptor spSSODescriptor =
			(SPSSODescriptor)samlMessageContext.getPeerEntityRoleMetadata();

		SingleLogoutService singleLogoutService =
			SamlUtil.resolveSingleLogoutService(
				spSSODescriptor, SAMLConstants.SAML2_SOAP11_BINDING_URI);

		if (singleLogoutService == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Single logout not supported by " + entityId);
			}

			samlSloRequestInfo.setStatus(
				SamlSloRequestInfo.REQUEST_STATUS_UNSUPPORTED);
			samlSloRequestInfo.setStatusCode(
				StatusCode.UNSUPPORTED_BINDING_URI);

			request.setAttribute(
				SamlWebKeys.SAML_SLO_REQUEST_INFO,
				samlSloRequestInfo.toJSONObject());

			JspUtil.dispatch(
				request, response, JspUtil.PATH_PORTAL_SAML_SLO_SP_STATUS,
				"single-sign-out", true);
		}
		else {
			try {
				sendIdpLogoutRequest(
					request, response, samlSloContext, samlSloRequestInfo);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					StringBundler sb = new StringBundler(7);

					sb.append("Unable to perform a single logout for service ");
					sb.append("provider ");
					sb.append(entityId);
					sb.append(" with binding ");
					sb.append(singleLogoutService.getBinding());
					sb.append(" to ");
					sb.append(singleLogoutService.getLocation());

					_log.debug(sb.toString(), e);
				}

				samlSloRequestInfo.setStatus(
					SamlSloRequestInfo.REQUEST_STATUS_FAILED);
				samlSloRequestInfo.setStatusCode(StatusCode.PARTIAL_LOGOUT_URI);

				request.setAttribute(
					SamlWebKeys.SAML_SLO_REQUEST_INFO,
					samlSloRequestInfo.toJSONObject());

				JspUtil.dispatch(
					request, response, JspUtil.PATH_PORTAL_SAML_SLO_SP_STATUS,
					"single-sign-out", true);
			}
		}
	}

	protected void performIdpStatus(
			HttpServletRequest request, HttpServletResponse response,
			SamlSloContext samlSloContext)
		throws Exception {

		for (SamlSloRequestInfo samlRequestInfo :
				samlSloContext.getSamlSloRequestInfos()) {

			int status = samlRequestInfo.getStatus();

			if (status == SamlSloRequestInfo.REQUEST_STATUS_INITIATED) {
				DateTime initiateDateTime = samlRequestInfo.getInitiateTime();

				DateTime expireDateTime = initiateDateTime.plusSeconds(10);

				if (expireDateTime.isBeforeNow()) {
					samlRequestInfo.setStatus(
						SamlSloRequestInfo.REQUEST_STATUS_TIMED_OUT);
					samlRequestInfo.setStatusCode(
						StatusCode.PARTIAL_LOGOUT_URI);
				}
			}
		}

		response.setContentType(ContentTypes.TEXT_JAVASCRIPT);

		Writer writer = response.getWriter();

		JSONObject jsonObject = samlSloContext.toJSONObject();

		writer.write(jsonObject.toString());
	}

	protected void processIdpLogoutRequest(
			HttpServletRequest request, HttpServletResponse response,
			SAMLMessageContext<LogoutRequest, LogoutResponse, NameID>
				samlMessageContext)
		throws Exception {

		SamlSloContext samlSloContext = getSamlSloContext(
			request, samlMessageContext);

		Set<String> samlSpEntityIds = samlSloContext.getSamlSpEntityIds();

		String binding = samlMessageContext.getCommunicationProfileId();

		if (binding.equals(SAMLConstants.SAML2_SOAP11_BINDING_URI)) {
			sendIdpLogoutResponse(
				request, response, StatusCode.UNSUPPORTED_BINDING_URI,
				samlSloContext);
		}
		else if (samlSloContext == null) {
			sendIdpLogoutResponse(
				request, response, StatusCode.UNKNOWN_PRINCIPAL_URI,
				new SamlSloContext(
					null, samlMessageContext, _samlIdpSpConnectionLocalService,
					_samlIdpSpSessionLocalService, _userLocalService));
		}
		else if (!samlSpEntityIds.isEmpty()) {
			initiateIdpSingleLogout(request, response);
		}
		else {
			sendIdpLogoutResponse(
				request, response, StatusCode.SUCCESS_URI, samlSloContext);
		}
	}

	protected void processIdpLogoutResponse(
			HttpServletRequest request, HttpServletResponse response,
			SAMLMessageContext<LogoutResponse, ?, ?> samlMessageContext)
		throws Exception {

		SamlSloContext samlSloContext = getSamlSloContext(request, null);

		if (samlSloContext == null) {
			throw new UnsolicitedLogoutResponseException(
				"Received logout response from " +
					samlMessageContext.getPeerEntityId() +
						" without an active SSO session");
		}

		String entityId = samlMessageContext.getInboundMessageIssuer();

		SamlSloRequestInfo samlSloRequestInfo =
			samlSloContext.getSamlSloRequestInfo(entityId);

		if (samlSloRequestInfo == null) {
			throw new UnsolicitedLogoutResponseException(
				"Received unsolicited logout response from " +
					samlMessageContext.getPeerEntityId());
		}

		LogoutResponse logoutResponse =
			samlMessageContext.getInboundSAMLMessage();

		Status status = logoutResponse.getStatus();

		StatusCode statusCode = status.getStatusCode();

		samlSloRequestInfo.setStatusCode(statusCode.getValue());

		request.setAttribute(
			SamlWebKeys.SAML_SLO_REQUEST_INFO,
			samlSloRequestInfo.toJSONObject());

		JspUtil.dispatch(
			request, response, JspUtil.PATH_PORTAL_SAML_SLO_SP_STATUS,
			"single-sign-out", true);
	}

	protected void processSingleLogoutRequest(
			HttpServletRequest request, HttpServletResponse response,
			SAMLMessageContext<LogoutRequest, LogoutResponse, NameID>
				samlMessageContext)
		throws Exception {

		if (samlProviderConfigurationHelper.isRoleIdp()) {
			processIdpLogoutRequest(request, response, samlMessageContext);
		}
		else if (samlProviderConfigurationHelper.isRoleSp()) {
			processSpLogoutRequest(request, response, samlMessageContext);
		}
	}

	protected void processSingleLogoutResponse(
			HttpServletRequest request, HttpServletResponse response,
			SAMLMessageContext<LogoutResponse, ?, ?> samlMessageContext)
		throws Exception {

		if (samlProviderConfigurationHelper.isRoleIdp()) {
			processIdpLogoutResponse(request, response, samlMessageContext);
		}
		else if (samlProviderConfigurationHelper.isRoleSp()) {
			processSpLogoutResponse(request, response, samlMessageContext);
		}
	}

	protected void processSpLogoutRequest(
			HttpServletRequest request, HttpServletResponse response,
			SAMLMessageContext<LogoutRequest, LogoutResponse, NameID>
				samlMessageContext)
		throws Exception {

		LogoutRequest logoutRequest =
			samlMessageContext.getInboundSAMLMessage();

		NameID nameID = logoutRequest.getNameID();

		List<SessionIndex> sessionIndexes = logoutRequest.getSessionIndexes();

		String statusCodeURI = StatusCode.SUCCESS_URI;

		if (sessionIndexes.isEmpty()) {
			List<SamlSpSession> samlSpSessions =
				samlSpSessionLocalService.getSamlSpSessions(nameID.getValue());

			if (samlSpSessions.isEmpty()) {
				statusCodeURI = StatusCode.UNKNOWN_PRINCIPAL_URI;
			}

			for (SamlSpSession samlSpSession : samlSpSessions) {
				samlSpSession.setTerminated(true);

				samlSpSessionLocalService.updateSamlSpSession(samlSpSession);
			}
		}

		for (SessionIndex sessionIndex : sessionIndexes) {
			SamlSpSession samlSpSession =
				samlSpSessionLocalService.fetchSamlSpSessionBySessionIndex(
					sessionIndex.getSessionIndex());

			if (samlSpSession == null) {
				statusCodeURI = StatusCode.UNKNOWN_PRINCIPAL_URI;

				continue;
			}

			if (Objects.equals(
					samlSpSession.getNameIdValue(), nameID.getValue()) &&
				Objects.equals(
					samlSpSession.getNameIdFormat(), nameID.getFormat())) {

				samlSpSession.setTerminated(true);

				samlSpSessionLocalService.updateSamlSpSession(samlSpSession);
			}
			else if (!statusCodeURI.equals(StatusCode.PARTIAL_LOGOUT_URI)) {
				statusCodeURI = StatusCode.UNKNOWN_PRINCIPAL_URI;

				continue;
			}

			if (statusCodeURI.equals(StatusCode.UNKNOWN_PRINCIPAL_URI)) {
				statusCodeURI = StatusCode.PARTIAL_LOGOUT_URI;
			}
		}

		samlMessageContext.setOutboundSAMLMessageSigningCredential(
			metadataManager.getSigningCredential());

		LogoutResponse logoutResponse = OpenSamlUtil.buildLogoutResponse();

		samlMessageContext.setOutboundSAMLMessage(logoutResponse);

		logoutResponse.setID(generateIdentifier(20));
		logoutResponse.setInResponseTo(logoutRequest.getID());
		logoutResponse.setIssueInstant(new DateTime(DateTimeZone.UTC));

		Issuer issuer = OpenSamlUtil.buildIssuer(
			samlMessageContext.getLocalEntityId());

		logoutResponse.setIssuer(issuer);

		StatusCode statusCode = OpenSamlUtil.buildStatusCode(statusCodeURI);

		Status status = OpenSamlUtil.buildStatus(statusCode);

		logoutResponse.setStatus(status);

		logoutResponse.setVersion(SAMLVersion.VERSION_20);

		SSODescriptor ssoDescriptor =
			(SSODescriptor)samlMessageContext.getPeerEntityRoleMetadata();

		SingleLogoutService singleLogoutService =
			SamlUtil.resolveSingleLogoutService(
				ssoDescriptor, samlMessageContext.getCommunicationProfileId());

		samlMessageContext.setPeerEntityEndpoint(singleLogoutService);

		sendSamlMessage(samlMessageContext);
	}

	protected void processSpLogoutResponse(
			HttpServletRequest request, HttpServletResponse response,
			SAMLMessageContext<LogoutResponse, ?, ?> samlMessageContext)
		throws Exception {

		redirectToLogout(request, response);
	}

	protected void redirectToLogout(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		if (samlProviderConfigurationHelper.isRoleIdp()) {
			terminateSsoSession(request, response);
		}
		else if (samlProviderConfigurationHelper.isRoleSp()) {
			terminateSpSession(request, response);
		}

		String portalURL = portal.getPortalURL(request);

		String pathMain = portal.getPathMain();

		String redirect = portalURL.concat(pathMain).concat("/portal/logout");

		response.sendRedirect(redirect);
	}

	protected void sendAsyncLogoutRequest(
			SAMLMessageContext<LogoutResponse, LogoutRequest, NameID>
				samlMessageContext,
			SamlSloContext samlSloContext)
		throws Exception {

		SingleLogoutService singleLogoutService =
			(SingleLogoutService)samlMessageContext.getPeerEntityEndpoint();

		LogoutRequest logoutRequest = OpenSamlUtil.buildLogoutRequest();

		logoutRequest.setDestination(singleLogoutService.getLocation());
		logoutRequest.setID(generateIdentifier(20));
		logoutRequest.setIssueInstant(new DateTime(DateTimeZone.UTC));

		SSODescriptor ssoDescriptor =
			(SSODescriptor)samlMessageContext.getLocalEntityRoleMetadata();

		Issuer issuer = OpenSamlUtil.buildIssuer(ssoDescriptor.getID());

		logoutRequest.setIssuer(issuer);

		logoutRequest.setNameID(samlMessageContext.getSubjectNameIdentifier());

		logoutRequest.setVersion(SAMLVersion.VERSION_20);

		addSessionIndex(logoutRequest, samlSloContext.getSamlSsoSessionId());

		samlMessageContext.setOutboundSAMLMessage(logoutRequest);

		Credential credential = metadataManager.getSigningCredential();

		samlMessageContext.setOutboundSAMLMessageSigningCredential(credential);

		samlMessageContext.setOutboundSAMLProtocol(SAMLConstants.SAML20P_NS);

		OpenSamlUtil.signObject(logoutRequest, credential);

		SamlBinding samlBinding = getSamlBinding(
			singleLogoutService.getBinding());

		MessageEncoder messageEncoder = samlBinding.getMessageEncoder();

		messageEncoder.encode(samlMessageContext);
	}

	protected void sendIdpLogoutRequest(
			HttpServletRequest request, HttpServletResponse response,
			SamlSloContext samlSloContext,
			SamlSloRequestInfo samlSloRequestInfo)
		throws Exception {

		SAMLMessageContext<LogoutResponse, LogoutRequest, NameID>
			samlMessageContext =
				(SAMLMessageContext<LogoutResponse, LogoutRequest, NameID>)
					getSamlMessageContext(
						request, response, samlSloRequestInfo.getEntityId());

		SPSSODescriptor spSSODescriptor =
			(SPSSODescriptor)samlMessageContext.getPeerEntityRoleMetadata();

		SingleLogoutService singleLogoutService =
			SamlUtil.resolveSingleLogoutService(
				spSSODescriptor, SAMLConstants.SAML2_REDIRECT_BINDING_URI);

		samlMessageContext.setPeerEntityEndpoint(singleLogoutService);

		SamlIdpSpSession samlIdpSpSession =
			samlSloRequestInfo.getSamlIdpSpSession();

		NameID nameID = OpenSamlUtil.buildNameId(
			samlIdpSpSession.getNameIdFormat(),
			samlIdpSpSession.getNameIdValue());

		samlMessageContext.setSubjectNameIdentifier(nameID);

		samlSloRequestInfo.setInitiateTime(new DateTime(DateTimeZone.UTC));
		samlSloRequestInfo.setStatus(
			SamlSloRequestInfo.REQUEST_STATUS_INITIATED);

		String binding = singleLogoutService.getBinding();

		if (binding.equals(SAMLConstants.SAML2_SOAP11_BINDING_URI)) {
			String statusCode = sendSyncLogoutRequest(
				samlMessageContext, samlSloContext);

			samlSloRequestInfo.setStatusCode(statusCode);

			request.setAttribute(
				SamlWebKeys.SAML_SLO_REQUEST_INFO,
				samlSloRequestInfo.toJSONObject());

			JspUtil.dispatch(
				request, response, JspUtil.PATH_PORTAL_SAML_SLO_SP_STATUS,
				"single-sign-out", true);
		}
		else {
			sendAsyncLogoutRequest(samlMessageContext, samlSloContext);
		}
	}

	protected void sendIdpLogoutResponse(
			HttpServletRequest request, HttpServletResponse response,
			String statusCodeURI, SamlSloContext samlSloContext)
		throws Exception {

		SAMLMessageContext<LogoutRequest, LogoutResponse, NameID>
			samlMessageContext = samlSloContext.getSamlMessageContext();

		LogoutResponse logoutResponse = OpenSamlUtil.buildLogoutResponse();

		SSODescriptor ssoDescriptor =
			(SSODescriptor)samlMessageContext.getPeerEntityRoleMetadata();

		SingleLogoutService singleLogoutService =
			SamlUtil.resolveSingleLogoutService(
				ssoDescriptor, samlMessageContext.getCommunicationProfileId());

		logoutResponse.setDestination(singleLogoutService.getLocation());

		logoutResponse.setID(generateIdentifier(20));

		LogoutRequest logoutRequest =
			samlMessageContext.getInboundSAMLMessage();

		logoutResponse.setInResponseTo(logoutRequest.getID());

		logoutResponse.setIssueInstant(new DateTime(DateTimeZone.UTC));

		Issuer issuer = OpenSamlUtil.buildIssuer(
			samlMessageContext.getLocalEntityId());

		logoutResponse.setIssuer(issuer);

		StatusCode statusCode = OpenSamlUtil.buildStatusCode(statusCodeURI);

		Status status = OpenSamlUtil.buildStatus(statusCode);

		logoutResponse.setStatus(status);

		logoutResponse.setVersion(SAMLVersion.VERSION_20);

		HttpServletRequestAdapter httpServletRequestAdapter =
			new HttpServletRequestAdapter(request);

		samlMessageContext.setInboundMessageTransport(
			httpServletRequestAdapter);

		HttpServletResponseAdapter httpServletResponseAdapter =
			new HttpServletResponseAdapter(response, request.isSecure());

		samlMessageContext.setOutboundMessageTransport(
			httpServletResponseAdapter);

		samlMessageContext.setOutboundSAMLMessage(logoutResponse);

		samlMessageContext.setOutboundSAMLMessageSigningCredential(
			metadataManager.getSigningCredential());
		samlMessageContext.setOutboundSAMLProtocol(SAMLConstants.SAML20P_NS);
		samlMessageContext.setPeerEntityEndpoint(singleLogoutService);

		if (!statusCodeURI.equals(StatusCode.UNSUPPORTED_BINDING_URI)) {
			terminateSsoSession(request, response);

			logout(request, response);
		}

		sendSamlMessage(samlMessageContext);
	}

	protected void sendSpLogoutRequest(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		SamlSpSession samlSpSession = getSamlSpSession(request);

		if ((samlSpSession == null) || samlSpSession.isTerminated()) {
			redirectToLogout(request, response);

			return;
		}

		LogoutRequest logoutRequest = OpenSamlUtil.buildLogoutRequest();

		String entityId = metadataManager.getDefaultIdpEntityId();

		SAMLMessageContext<SAMLObject, LogoutRequest, SAMLObject>
			samlMessageContext =
				(SAMLMessageContext<SAMLObject, LogoutRequest, SAMLObject>)
					getSamlMessageContext(request, response, entityId);

		IDPSSODescriptor idpSSODescriptor =
			(IDPSSODescriptor)samlMessageContext.getPeerEntityRoleMetadata();

		SingleLogoutService singleLogoutService =
			SamlUtil.resolveSingleLogoutService(
				idpSSODescriptor, SAMLConstants.SAML2_POST_BINDING_URI);

		logoutRequest.setDestination(singleLogoutService.getLocation());

		logoutRequest.setID(generateIdentifier(20));

		DateTime issueInstantDateTime = new DateTime(DateTimeZone.UTC);

		logoutRequest.setIssueInstant(issueInstantDateTime);

		Issuer issuer = OpenSamlUtil.buildIssuer(
			samlMessageContext.getLocalEntityId());

		logoutRequest.setIssuer(issuer);

		String nameIdFormat = samlSpSession.getNameIdFormat();
		String nameIdNameQualifier = samlSpSession.getNameIdNameQualifier();
		String nameIdSPNameQualifier = samlSpSession.getNameIdSPNameQualifier();
		String nameIdValue = samlSpSession.getNameIdValue();

		NameID nameID = OpenSamlUtil.buildNameId(
			nameIdFormat, nameIdNameQualifier, nameIdSPNameQualifier,
			nameIdValue);

		logoutRequest.setNameID(nameID);

		logoutRequest.setVersion(SAMLVersion.VERSION_20);

		addSessionIndex(logoutRequest, samlSpSession.getSessionIndex());

		samlMessageContext.setOutboundSAMLMessage(logoutRequest);

		samlMessageContext.setOutboundSAMLMessageSigningCredential(
			metadataManager.getSigningCredential());
		samlMessageContext.setPeerEntityEndpoint(singleLogoutService);

		sendSamlMessage(samlMessageContext);
	}

	protected String sendSyncLogoutRequest(
			SAMLMessageContext<LogoutResponse, LogoutRequest, NameID>
				samlMessageContext,
			SamlSloContext samlSloContext)
		throws Exception {

		SingleLogoutService singleLogoutService =
			(SingleLogoutService)samlMessageContext.getPeerEntityEndpoint();

		HttpPost httpPost = new HttpPost(singleLogoutService.getLocation());

		HttpClientInTransport httpClientInTransport = new HttpClientInTransport(
			httpPost, singleLogoutService.getLocation());

		samlMessageContext.setInboundMessageTransport(httpClientInTransport);

		HttpClientOutTransport httpClientOutTransport =
			new HttpClientOutTransport(httpPost);

		samlMessageContext.setOutboundMessageTransport(httpClientOutTransport);

		LogoutRequest logoutRequest = OpenSamlUtil.buildLogoutRequest();

		logoutRequest.setDestination(singleLogoutService.getLocation());
		logoutRequest.setID(generateIdentifier(20));
		logoutRequest.setIssueInstant(new DateTime(DateTimeZone.UTC));

		SSODescriptor ssoDescriptor =
			(SSODescriptor)samlMessageContext.getLocalEntityRoleMetadata();

		Issuer issuer = OpenSamlUtil.buildIssuer(ssoDescriptor.getID());

		logoutRequest.setIssuer(issuer);

		logoutRequest.setNameID(samlMessageContext.getSubjectNameIdentifier());

		logoutRequest.setVersion(SAMLVersion.VERSION_20);

		addSessionIndex(logoutRequest, samlSloContext.getSamlSsoSessionId());

		samlMessageContext.setOutboundSAMLMessage(logoutRequest);

		Credential credential = metadataManager.getSigningCredential();

		samlMessageContext.setOutboundSAMLMessageSigningCredential(credential);

		samlMessageContext.setOutboundSAMLProtocol(SAMLConstants.SAML20P_NS);

		OpenSamlUtil.signObject(logoutRequest, credential);

		SamlBinding samlBinding = getSamlBinding(
			SAMLConstants.SAML2_SOAP11_BINDING_URI);

		MessageEncoder messageEncoder = samlBinding.getMessageEncoder();

		messageEncoder.encode(samlMessageContext);

		SecurityPolicyResolver securityPolicyResolver =
			metadataManager.getSecurityPolicyResolver(
				samlBinding.getCommunicationProfileId(), true);

		samlMessageContext.setSecurityPolicyResolver(securityPolicyResolver);

		MessageDecoder messageDecoder = samlBinding.getMessageDecoder();

		messageDecoder.decode(samlMessageContext);

		LogoutResponse logoutResponse =
			samlMessageContext.getInboundSAMLMessage();

		Status status = logoutResponse.getStatus();

		StatusCode statusCode = status.getStatusCode();

		return statusCode.getValue();
	}

	@Reference(unbind = "-")
	protected void setPortal(Portal portal) {
		super.portal = portal;
	}

	@Reference(unbind = "-")
	protected void setSamlHttpRequestUtil(
		SamlHttpRequestUtil samlHttpRequestUtil) {

		_samlHttpRequestUtil = samlHttpRequestUtil;
	}

	@Reference(unbind = "-")
	protected void setSamlIdpSpConnectionLocalService(
		SamlIdpSpConnectionLocalService samlIdpSpConnectionLocalService) {

		_samlIdpSpConnectionLocalService = samlIdpSpConnectionLocalService;
	}

	@Reference(unbind = "-")
	protected void setSamlIdpSpSessionLocalService(
		SamlIdpSpSessionLocalService samlIdpSpSessionLocalService) {

		_samlIdpSpSessionLocalService = samlIdpSpSessionLocalService;
	}

	@Reference(unbind = "-")
	protected void setSamlIdpSsoSessionLocalService(
		SamlIdpSsoSessionLocalService samlIdpSsoSessionLocalService) {

		_samlIdpSsoSessionLocalService = samlIdpSsoSessionLocalService;
	}

	@Reference(unbind = "-")
	protected void setSamlSpSessionLocalService(
		SamlSpSessionLocalService samlSpSessionLocalService) {

		super.samlSpSessionLocalService = samlSpSessionLocalService;
	}

	@Reference(unbind = "-")
	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SingleLogoutProfileImpl.class);

	private SamlHttpRequestUtil _samlHttpRequestUtil;
	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;
	private SamlIdpSpSessionLocalService _samlIdpSpSessionLocalService;
	private SamlIdpSsoSessionLocalService _samlIdpSsoSessionLocalService;
	private UserLocalService _userLocalService;

}