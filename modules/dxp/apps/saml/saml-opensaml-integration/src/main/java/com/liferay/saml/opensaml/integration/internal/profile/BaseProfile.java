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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.CookieKeys;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.opensaml.integration.SamlBinding;
import com.liferay.saml.opensaml.integration.internal.transport.ProxyPathRequestWrapper;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.opensaml.integration.metadata.MetadataManager;
import com.liferay.saml.persistence.model.SamlSpSession;
import com.liferay.saml.persistence.service.SamlSpSessionLocalService;
import com.liferay.saml.runtime.SamlException;
import com.liferay.saml.runtime.configuration.SamlProviderConfigurationHelper;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.opensaml.common.IdentifierGenerator;
import org.opensaml.common.SAMLObject;
import org.opensaml.common.binding.BasicSAMLMessageContext;
import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.metadata.Endpoint;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml2.metadata.provider.MetadataProvider;
import org.opensaml.ws.message.decoder.MessageDecoder;
import org.opensaml.ws.message.encoder.MessageEncoder;
import org.opensaml.ws.message.encoder.MessageEncodingException;
import org.opensaml.ws.security.SecurityPolicyResolver;
import org.opensaml.ws.transport.http.HttpServletRequestAdapter;
import org.opensaml.ws.transport.http.HttpServletResponseAdapter;
import org.opensaml.xml.XMLObject;
import org.opensaml.xml.io.MarshallingException;

/**
 * @author Mika Koivisto
 */
public abstract class BaseProfile {

	public SAMLMessageContext<?, ?, ?> decodeSamlMessage(
			HttpServletRequest request, HttpServletResponse response,
			SamlBinding samlBinding, boolean requireSignature)
		throws Exception {

		SAMLMessageContext<SAMLObject, SAMLObject, NameID> samlMessageContext =
			(SAMLMessageContext<SAMLObject, SAMLObject, NameID>)
				getSamlMessageContext(request, response);

		samlMessageContext.setCommunicationProfileId(
			samlBinding.getCommunicationProfileId());

		SecurityPolicyResolver securityPolicyResolver =
			metadataManager.getSecurityPolicyResolver(
				samlMessageContext.getCommunicationProfileId(),
				requireSignature);

		samlMessageContext.setSecurityPolicyResolver(securityPolicyResolver);

		MessageDecoder messageDecoder = samlBinding.getMessageDecoder();

		messageDecoder.decode(samlMessageContext);

		if (_log.isDebugEnabled()) {
			SAMLObject samlObject = samlMessageContext.getInboundSAMLMessage();

			_log.debug(
				"Received message using binding " +
					samlMessageContext.getCommunicationProfileId() + " " +
						OpenSamlUtil.marshall(samlObject));
		}

		EntityDescriptor entityDescriptor =
			samlMessageContext.getPeerEntityMetadata();

		if (entityDescriptor == null) {
			throw new SamlException(
				"Unable to resolve metadata for issuer " +
					samlMessageContext.getInboundMessageIssuer());
		}

		samlMessageContext.setPeerEntityId(entityDescriptor.getEntityID());

		RoleDescriptor roleDescriptor = null;

		if (samlProviderConfigurationHelper.isRoleIdp()) {
			roleDescriptor = entityDescriptor.getSPSSODescriptor(
				SAMLConstants.SAML20P_NS);
		}
		else if (samlProviderConfigurationHelper.isRoleSp()) {
			roleDescriptor = entityDescriptor.getIDPSSODescriptor(
				SAMLConstants.SAML20P_NS);
		}

		samlMessageContext.setPeerEntityRoleMetadata(roleDescriptor);

		return samlMessageContext;
	}

	public String generateIdentifier(int length) {
		return _identifierGenerator.generateIdentifier(length);
	}

	public IdentifierGenerator getIdentifierGenerator() {
		return _identifierGenerator;
	}

	public SamlBinding getSamlBinding(String communicationProfileId)
		throws PortalException {

		for (SamlBinding samlBinding : _samlBindings) {
			if (communicationProfileId.equals(
					samlBinding.getCommunicationProfileId())) {

				return samlBinding;
			}
		}

		throw new SamlException(
			"Unsupported binding " + communicationProfileId);
	}

	public SAMLMessageContext<?, ?, ?> getSamlMessageContext(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		SAMLMessageContext<SAMLObject, SAMLObject, NameID> samlMessageContext =
			new BasicSAMLMessageContext<>();

		HttpServletRequestAdapter httpServletRequestAdapter =
			new HttpServletRequestAdapter(new ProxyPathRequestWrapper(request));

		samlMessageContext.setInboundMessageTransport(
			httpServletRequestAdapter);

		samlMessageContext.setInboundSAMLProtocol(SAMLConstants.SAML20P_NS);

		RoleDescriptor roleDescriptor = null;

		EntityDescriptor entityDescriptor = metadataManager.getEntityDescriptor(
			request);

		samlMessageContext.setLocalEntityMetadata(entityDescriptor);

		if (samlProviderConfigurationHelper.isRoleIdp()) {
			roleDescriptor = entityDescriptor.getIDPSSODescriptor(
				SAMLConstants.SAML20P_NS);
		}
		else if (samlProviderConfigurationHelper.isRoleSp()) {
			roleDescriptor = entityDescriptor.getSPSSODescriptor(
				SAMLConstants.SAML20P_NS);
		}

		samlMessageContext.setLocalEntityId(roleDescriptor.getID());

		if (samlProviderConfigurationHelper.isRoleIdp()) {
			samlMessageContext.setLocalEntityRole(
				IDPSSODescriptor.DEFAULT_ELEMENT_NAME);
		}
		else if (samlProviderConfigurationHelper.isRoleSp()) {
			samlMessageContext.setLocalEntityRole(
				SPSSODescriptor.DEFAULT_ELEMENT_NAME);
		}

		samlMessageContext.setLocalEntityRoleMetadata(roleDescriptor);

		MetadataProvider metadataProvider =
			metadataManager.getMetadataProvider();

		samlMessageContext.setMetadataProvider(metadataProvider);

		HttpServletResponseAdapter httpServletResponseAdapter =
			new HttpServletResponseAdapter(response, request.isSecure());

		samlMessageContext.setOutboundMessageTransport(
			httpServletResponseAdapter);

		if (samlProviderConfigurationHelper.isRoleIdp()) {
			samlMessageContext.setPeerEntityRole(
				SPSSODescriptor.DEFAULT_ELEMENT_NAME);
		}
		else if (samlProviderConfigurationHelper.isRoleSp()) {
			samlMessageContext.setPeerEntityRole(
				IDPSSODescriptor.DEFAULT_ELEMENT_NAME);
		}

		return samlMessageContext;
	}

	public SAMLMessageContext<?, ?, ?> getSamlMessageContext(
			HttpServletRequest request, HttpServletResponse response,
			String peerEntityId)
		throws Exception {

		SAMLMessageContext<?, ?, ?> samlMessageContext = getSamlMessageContext(
			request, response);

		samlMessageContext.setPeerEntityId(peerEntityId);

		MetadataProvider metadataProvider =
			samlMessageContext.getMetadataProvider();

		EntityDescriptor entityDescriptor =
			metadataProvider.getEntityDescriptor(peerEntityId);

		if (entityDescriptor == null) {
			throw new SamlException("Unknown peer entity ID " + peerEntityId);
		}

		samlMessageContext.setPeerEntityMetadata(entityDescriptor);

		RoleDescriptor roleDescriptor = null;

		if (samlProviderConfigurationHelper.isRoleIdp()) {
			roleDescriptor = entityDescriptor.getSPSSODescriptor(
				SAMLConstants.SAML20P_NS);
		}
		else if (samlProviderConfigurationHelper.isRoleSp()) {
			roleDescriptor = entityDescriptor.getIDPSSODescriptor(
				SAMLConstants.SAML20P_NS);
		}

		samlMessageContext.setPeerEntityRoleMetadata(roleDescriptor);

		return samlMessageContext;
	}

	public SamlSpSession getSamlSpSession(HttpServletRequest request) {
		String samlSpSessionKey = getSamlSpSessionKey(request);

		if (Validator.isNotNull(samlSpSessionKey)) {
			SamlSpSession samlSpSession =
				samlSpSessionLocalService.fetchSamlSpSessionBySamlSpSessionKey(
					samlSpSessionKey);

			if (samlSpSession != null) {
				return samlSpSession;
			}
		}

		HttpSession session = request.getSession();

		SamlSpSession samlSpSession =
			samlSpSessionLocalService.fetchSamlSpSessionByJSessionId(
				session.getId());

		return samlSpSession;
	}

	public String getSamlSpSessionKey(HttpServletRequest request) {
		HttpSession session = request.getSession();

		String samlSpSessionKey = (String)session.getAttribute(
			SamlWebKeys.SAML_SP_SESSION_KEY);

		if (Validator.isNull(samlSpSessionKey)) {
			samlSpSessionKey = CookieKeys.getCookie(
				request, SamlWebKeys.SAML_SP_SESSION_KEY);
		}

		return samlSpSessionKey;
	}

	public String getSamlSsoSessionId(HttpServletRequest request) {
		return CookieKeys.getCookie(request, SamlWebKeys.SAML_SSO_SESSION_ID);
	}

	public void logout(
		HttpServletRequest request, HttpServletResponse response) {

		String domain = CookieKeys.getDomain(request);

		Cookie companyIdCookie = new Cookie(
			CookieKeys.COMPANY_ID, StringPool.BLANK);

		if (Validator.isNotNull(domain)) {
			companyIdCookie.setDomain(domain);
		}

		companyIdCookie.setMaxAge(0);
		companyIdCookie.setPath(StringPool.SLASH);

		Cookie idCookie = new Cookie(CookieKeys.ID, StringPool.BLANK);

		if (Validator.isNotNull(domain)) {
			idCookie.setDomain(domain);
		}

		idCookie.setMaxAge(0);
		idCookie.setPath(StringPool.SLASH);

		Cookie passwordCookie = new Cookie(
			CookieKeys.PASSWORD, StringPool.BLANK);

		if (Validator.isNotNull(domain)) {
			passwordCookie.setDomain(domain);
		}

		passwordCookie.setMaxAge(0);
		passwordCookie.setPath(StringPool.SLASH);

		boolean rememberMe = GetterUtil.getBoolean(
			CookieKeys.getCookie(request, CookieKeys.REMEMBER_ME));

		if (!rememberMe) {
			Cookie loginCookie = new Cookie(CookieKeys.LOGIN, StringPool.BLANK);

			if (Validator.isNotNull(domain)) {
				loginCookie.setDomain(domain);
			}

			loginCookie.setMaxAge(0);
			loginCookie.setPath(StringPool.SLASH);

			CookieKeys.addCookie(request, response, loginCookie);
		}

		Cookie rememberMeCookie = new Cookie(
			CookieKeys.REMEMBER_ME, StringPool.BLANK);

		if (Validator.isNotNull(domain)) {
			rememberMeCookie.setDomain(domain);
		}

		rememberMeCookie.setMaxAge(0);
		rememberMeCookie.setPath(StringPool.SLASH);

		CookieKeys.addCookie(request, response, companyIdCookie);
		CookieKeys.addCookie(request, response, idCookie);
		CookieKeys.addCookie(request, response, passwordCookie);
		CookieKeys.addCookie(request, response, rememberMeCookie);

		HttpSession session = request.getSession();

		try {
			session.invalidate();
		}
		catch (Exception e) {
		}
	}

	public void sendSamlMessage(SAMLMessageContext<?, ?, ?> samlMessageContext)
		throws PortalException {

		Endpoint endpoint = samlMessageContext.getPeerEntityEndpoint();

		SamlBinding samlBinding = getSamlBinding(endpoint.getBinding());

		if (_log.isDebugEnabled()) {
			try {
				XMLObject xmlObject =
					samlMessageContext.getOutboundSAMLMessage();

				String samlMessage = OpenSamlUtil.marshall(xmlObject);

				_log.debug(
					"Sending SAML message " + samlMessage + " to " +
						endpoint.getLocation() + " with binding " +
							endpoint.getBinding());
			}
			catch (MarshallingException me) {
			}
		}

		MessageEncoder messageEncoder = samlBinding.getMessageEncoder();

		try {
			messageEncoder.encode(samlMessageContext);
		}
		catch (MessageEncodingException mee) {
			throw new SamlException(
				"Unable to send SAML message to " + endpoint.getLocation() +
					" with binding " + endpoint.getBinding(),
				mee);
		}
	}

	protected void addCookie(
		HttpServletRequest request, HttpServletResponse response,
		String cookieName, String cookieValue, int maxAge) {

		Cookie cookie = new Cookie(cookieName, cookieValue);

		cookie.setMaxAge(maxAge);

		if (Validator.isNull(portal.getPathContext())) {
			cookie.setPath(StringPool.SLASH);
		}
		else {
			cookie.setPath(portal.getPathContext());
		}

		cookie.setSecure(request.isSecure());

		response.addCookie(cookie);
	}

	protected void addSamlBinding(SamlBinding samlBinding) {
		_samlBindings.add(samlBinding);
	}

	protected void removeSamlBinding(SamlBinding samlBinding) {
		_samlBindings.remove(samlBinding);
	}

	protected void setIdentifierGenerator(
		IdentifierGenerator identifierGenerator) {

		_identifierGenerator = identifierGenerator;
	}

	protected void setMetadataManager(MetadataManager metadataManager) {
		this.metadataManager = metadataManager;
	}

	protected void setSamlBindings(List<SamlBinding> samlBindings) {
		_samlBindings = samlBindings;
	}

	protected void setSamlProviderConfigurationHelper(
		SamlProviderConfigurationHelper samlProviderConfigurationHelper) {

		this.samlProviderConfigurationHelper = samlProviderConfigurationHelper;
	}

	protected void unsetSamlBinding(SamlBinding samlBinding) {
		removeSamlBinding(samlBinding);
	}

	protected MetadataManager metadataManager;
	protected Portal portal;
	protected SamlProviderConfigurationHelper samlProviderConfigurationHelper;
	protected SamlSpSessionLocalService samlSpSessionLocalService;

	private static final Log _log = LogFactoryUtil.getLog(BaseProfile.class);

	private IdentifierGenerator _identifierGenerator;
	private List<SamlBinding> _samlBindings = new ArrayList<>();

}