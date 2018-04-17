/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.saml.opensaml.integration.internal.profile;

import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.opensaml.integration.internal.BaseSamlTestCase;
import com.liferay.saml.opensaml.integration.internal.util.OpenSamlUtil;
import com.liferay.saml.opensaml.integration.internal.util.SamlUtil;
import com.liferay.saml.persistence.model.SamlSpAuthRequest;
import com.liferay.saml.persistence.model.SamlSpIdpConnection;
import com.liferay.saml.persistence.model.impl.SamlSpAuthRequestImpl;
import com.liferay.saml.persistence.model.impl.SamlSpIdpConnectionImpl;
import com.liferay.saml.persistence.service.SamlSpAuthRequestLocalService;
import com.liferay.saml.persistence.service.SamlSpAuthRequestLocalServiceUtil;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalService;
import com.liferay.saml.persistence.service.SamlSpIdpConnectionLocalServiceUtil;
import com.liferay.saml.persistence.service.SamlSpMessageLocalService;
import com.liferay.saml.persistence.service.SamlSpMessageLocalServiceUtil;
import com.liferay.saml.persistence.service.SamlSpSessionLocalService;
import com.liferay.saml.persistence.service.SamlSpSessionLocalServiceUtil;
import com.liferay.saml.runtime.exception.AssertionException;
import com.liferay.saml.runtime.exception.AudienceException;
import com.liferay.saml.runtime.exception.DestinationException;
import com.liferay.saml.runtime.exception.ExpiredException;
import com.liferay.saml.runtime.exception.InResponseToException;
import com.liferay.saml.runtime.exception.IssuerException;
import com.liferay.saml.runtime.exception.SignatureException;
import com.liferay.saml.runtime.exception.SubjectException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.Assertion;
import org.opensaml.saml2.core.AudienceRestriction;
import org.opensaml.saml2.core.AuthnRequest;
import org.opensaml.saml2.core.Conditions;
import org.opensaml.saml2.core.Issuer;
import org.opensaml.saml2.core.NameID;
import org.opensaml.saml2.core.NameIDType;
import org.opensaml.saml2.core.Response;
import org.opensaml.saml2.core.Subject;
import org.opensaml.saml2.core.SubjectConfirmation;
import org.opensaml.saml2.core.SubjectConfirmationData;
import org.opensaml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml2.metadata.SPSSODescriptor;
import org.opensaml.xml.security.credential.Credential;

import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Mika Koivisto
 * @author Matthew Tambara
 * @author William Newbury
 */
@PowerMockIgnore("javax.security.*")
@RunWith(PowerMockRunner.class)
public class WebSsoProfileIntegrationTest extends BaseSamlTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_samlSpAuthRequestLocalService = getMockPortletService(
			SamlSpAuthRequestLocalServiceUtil.class,
			SamlSpAuthRequestLocalService.class);
		_samlSpSessionLocalService = getMockPortletService(
			SamlSpSessionLocalServiceUtil.class,
			SamlSpSessionLocalService.class);

		_webSsoProfileImpl.setIdentifierGenerator(identifierGenerator);
		_webSsoProfileImpl.setMetadataManager(metadataManagerImpl);
		_webSsoProfileImpl.setPortal(portal);
		_webSsoProfileImpl.setSamlBindings(samlBindings);

		_webSsoProfileImpl.setSamlProviderConfigurationHelper(
			samlProviderConfigurationHelper);
		_webSsoProfileImpl.setSamlSpAuthRequestLocalService(
			_samlSpAuthRequestLocalService);
		_webSsoProfileImpl.setSamlSpSessionLocalService(
			_samlSpSessionLocalService);

		SamlSpMessageLocalService samlSpMessageLocalService =
			getMockPortletService(
				SamlSpMessageLocalServiceUtil.class,
				SamlSpMessageLocalService.class);

		_webSsoProfileImpl.setSamlSpMessageLocalService(
			samlSpMessageLocalService);

		SamlSpIdpConnectionLocalService samlSpIdpConnectionLocalService =
			getMockPortletService(
				SamlSpIdpConnectionLocalServiceUtil.class,
				SamlSpIdpConnectionLocalService.class);

		_webSsoProfileImpl.setSamlSpIdpConnectionLocalService(
			samlSpIdpConnectionLocalService);

		_webSsoProfileImpl.activate(new HashMap<String, Object>());

		prepareServiceProvider(SP_ENTITY_ID);
	}

	@Test
	public void testDecodeAuthnRequestIdpInitiatedSso() throws Exception {
		prepareIdentityProvider(IDP_ENTITY_ID);

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(SSO_URL);

		mockHttpServletRequest.setParameter("entityId", SP_ENTITY_ID);
		mockHttpServletRequest.setParameter("RelayState", RELAY_STATE);

		SamlSsoRequestContext samlSsoRequestContext =
			_webSsoProfileImpl.decodeAuthnRequest(
				mockHttpServletRequest, new MockHttpServletResponse());

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			samlSsoRequestContext.getSAMLMessageContext();

		Assert.assertEquals(
			IDP_ENTITY_ID, samlMessageContext.getLocalEntityId());
		Assert.assertNotNull(samlMessageContext.getLocalEntityMetadata());
		Assert.assertNotNull(samlMessageContext.getLocalEntityRoleMetadata());
		Assert.assertTrue(
			samlMessageContext.getLocalEntityRoleMetadata() instanceof
				IDPSSODescriptor);
		Assert.assertEquals(SP_ENTITY_ID, samlMessageContext.getPeerEntityId());
		Assert.assertNotNull(samlMessageContext.getPeerEntityMetadata());
		Assert.assertNotNull(samlMessageContext.getPeerEntityRoleMetadata());
		Assert.assertTrue(
			samlMessageContext.getPeerEntityRoleMetadata() instanceof
				SPSSODescriptor);
		Assert.assertEquals(RELAY_STATE, samlMessageContext.getRelayState());

		Assert.assertTrue(samlSsoRequestContext.isNewSession());
	}

	@Test
	public void testDecodeAuthnRequestIdpInitiatedSsoAfterAuthentication()
		throws Exception {

		prepareIdentityProvider(IDP_ENTITY_ID);

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(SSO_URL + "?entityId=" + SP_ENTITY_ID);

		HttpSession mockSession = mockHttpServletRequest.getSession();

		SamlSsoRequestContext samlSsoRequestContext = new SamlSsoRequestContext(
			SP_ENTITY_ID, RELAY_STATE, null, userLocalService);

		mockSession.setAttribute(
			SamlWebKeys.SAML_SSO_REQUEST_CONTEXT, samlSsoRequestContext);

		samlSsoRequestContext = _webSsoProfileImpl.decodeAuthnRequest(
			mockHttpServletRequest, new MockHttpServletResponse());

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			samlSsoRequestContext.getSAMLMessageContext();

		Assert.assertEquals(
			IDP_ENTITY_ID, samlMessageContext.getLocalEntityId());
		Assert.assertNotNull(samlMessageContext.getLocalEntityMetadata());
		Assert.assertNotNull(samlMessageContext.getLocalEntityRoleMetadata());
		Assert.assertTrue(
			samlMessageContext.getLocalEntityRoleMetadata() instanceof
				IDPSSODescriptor);
		Assert.assertEquals(SP_ENTITY_ID, samlMessageContext.getPeerEntityId());
		Assert.assertNotNull(samlMessageContext.getPeerEntityMetadata());
		Assert.assertNotNull(samlMessageContext.getPeerEntityRoleMetadata());
		Assert.assertTrue(
			samlMessageContext.getPeerEntityRoleMetadata() instanceof
				SPSSODescriptor);
		Assert.assertEquals(RELAY_STATE, samlMessageContext.getRelayState());

		Assert.assertTrue(samlSsoRequestContext.isNewSession());
	}

	@Test
	public void testDecodeAuthnRequestStageAuthenticated() throws Exception {
		SamlSpIdpConnectionLocalService samlSpIdpConnectionLocalService =
			getMockPortletService(
				SamlSpIdpConnectionLocalServiceUtil.class,
				SamlSpIdpConnectionLocalService.class);

		SamlSpIdpConnection samlSpIdpConnection = new SamlSpIdpConnectionImpl();

		when(
			samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
				Mockito.eq(COMPANY_ID), Mockito.eq(IDP_ENTITY_ID))
		).thenReturn(
			samlSpIdpConnection
		);

		_webSsoProfileImpl.setSamlSpIdpConnectionLocalService(
			samlSpIdpConnectionLocalService);

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(LOGIN_URL);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_webSsoProfileImpl.doSendAuthnRequest(
			mockHttpServletRequest, mockHttpServletResponse, RELAY_STATE);

		String redirect = mockHttpServletResponse.getRedirectedUrl();

		prepareIdentityProvider(IDP_ENTITY_ID);

		mockHttpServletRequest = getMockHttpServletRequest(redirect);

		mockHttpServletResponse = new MockHttpServletResponse();

		SamlSsoRequestContext samlSsoRequestContext =
			_webSsoProfileImpl.decodeAuthnRequest(
				mockHttpServletRequest, mockHttpServletResponse);

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			samlSsoRequestContext.getSAMLMessageContext();

		Assert.assertNotNull(samlMessageContext.getInboundSAMLMessageId());

		String inboundSamlMessageId =
			samlMessageContext.getInboundSAMLMessageId();

		mockHttpServletRequest = getMockHttpServletRequest(
			SSO_URL + "?saml_message_id=" + inboundSamlMessageId);

		HttpSession mockSession = mockHttpServletRequest.getSession();

		samlSsoRequestContext.setSAMLMessageContext(null);

		mockSession.setAttribute(
			SamlWebKeys.SAML_SSO_REQUEST_CONTEXT, samlSsoRequestContext);

		when(
			portal.getUserId(Mockito.any(MockHttpServletRequest.class))
		).thenReturn(
			1000L
		);

		samlSsoRequestContext = _webSsoProfileImpl.decodeAuthnRequest(
			mockHttpServletRequest, mockHttpServletResponse);

		samlMessageContext = samlSsoRequestContext.getSAMLMessageContext();

		Assert.assertNotNull(samlMessageContext.getInboundSAMLMessage());
		Assert.assertEquals(
			inboundSamlMessageId, samlMessageContext.getInboundSAMLMessageId());
		Assert.assertEquals(RELAY_STATE, samlMessageContext.getRelayState());

		Assert.assertNull(
			mockSession.getAttribute(SamlWebKeys.SAML_SSO_REQUEST_CONTEXT));
		Assert.assertEquals(
			SamlSsoRequestContext.STAGE_AUTHENTICATED,
			samlSsoRequestContext.getStage());
		Assert.assertEquals(1000, samlSsoRequestContext.getUserId());
	}

	@Test
	public void testDecodeAuthnRequestStageInitial() throws Exception {
		SamlSpIdpConnectionLocalService samlSpIdpConnectionLocalService =
			getMockPortletService(
				SamlSpIdpConnectionLocalServiceUtil.class,
				SamlSpIdpConnectionLocalService.class);

		SamlSpIdpConnection samlSpIdpConnection = new SamlSpIdpConnectionImpl();

		when(
			samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
				Mockito.eq(COMPANY_ID), Mockito.eq(IDP_ENTITY_ID))
		).thenReturn(
			samlSpIdpConnection
		);

		_webSsoProfileImpl.setSamlSpIdpConnectionLocalService(
			samlSpIdpConnectionLocalService);

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(LOGIN_URL);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_webSsoProfileImpl.doSendAuthnRequest(
			mockHttpServletRequest, mockHttpServletResponse, RELAY_STATE);

		String redirect = mockHttpServletResponse.getRedirectedUrl();

		Assert.assertNotNull(redirect);

		prepareIdentityProvider(IDP_ENTITY_ID);

		mockHttpServletRequest = getMockHttpServletRequest(redirect);

		mockHttpServletResponse = new MockHttpServletResponse();

		SamlSsoRequestContext samlSsoRequestContext =
			_webSsoProfileImpl.decodeAuthnRequest(
				mockHttpServletRequest, mockHttpServletResponse);

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			samlSsoRequestContext.getSAMLMessageContext();

		Assert.assertEquals(
			IDP_ENTITY_ID, samlMessageContext.getLocalEntityId());
		Assert.assertNotNull(samlMessageContext.getLocalEntityMetadata());
		Assert.assertNotNull(samlMessageContext.getLocalEntityRoleMetadata());
		Assert.assertTrue(
			samlMessageContext.getLocalEntityRoleMetadata() instanceof
				IDPSSODescriptor);
		Assert.assertEquals(SP_ENTITY_ID, samlMessageContext.getPeerEntityId());
		Assert.assertNotNull(samlMessageContext.getPeerEntityMetadata());
		Assert.assertNotNull(samlMessageContext.getPeerEntityRoleMetadata());
		Assert.assertTrue(
			samlMessageContext.getPeerEntityRoleMetadata() instanceof
				SPSSODescriptor);
		Assert.assertEquals(RELAY_STATE, samlMessageContext.getRelayState());

		AuthnRequest authnRequest = samlMessageContext.getInboundSAMLMessage();

		Assert.assertEquals(identifiers.get(0), authnRequest.getID());

		Assert.assertEquals(2, identifiers.size());
		Assert.assertFalse(authnRequest.isForceAuthn());
		Assert.assertTrue(samlSsoRequestContext.isNewSession());
	}

	@Test
	public void testForceAuthn() throws Exception {
		SamlSpIdpConnectionLocalService samlSpIdpConnectionLocalService =
			getMockPortletService(
				SamlSpIdpConnectionLocalServiceUtil.class,
				SamlSpIdpConnectionLocalService.class);

		SamlSpIdpConnection samlSpIdpConnection = new SamlSpIdpConnectionImpl();

		samlSpIdpConnection.setForceAuthn(true);

		when(
			samlSpIdpConnectionLocalService.getSamlSpIdpConnection(
				Mockito.eq(COMPANY_ID), Mockito.eq(IDP_ENTITY_ID))
		).thenReturn(
			samlSpIdpConnection
		);

		_webSsoProfileImpl.setSamlSpIdpConnectionLocalService(
			samlSpIdpConnectionLocalService);

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(LOGIN_URL);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_webSsoProfileImpl.doSendAuthnRequest(
			mockHttpServletRequest, mockHttpServletResponse, RELAY_STATE);

		String redirect = mockHttpServletResponse.getRedirectedUrl();

		Assert.assertNotNull(redirect);

		prepareIdentityProvider(IDP_ENTITY_ID);

		mockHttpServletRequest = getMockHttpServletRequest(redirect);

		mockHttpServletResponse = new MockHttpServletResponse();

		SamlSsoRequestContext samlSsoRequestContext =
			_webSsoProfileImpl.decodeAuthnRequest(
				mockHttpServletRequest, mockHttpServletResponse);

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			samlSsoRequestContext.getSAMLMessageContext();

		AuthnRequest authnRequest = samlMessageContext.getInboundSAMLMessage();

		Assert.assertTrue(authnRequest.isForceAuthn());
	}

	@Test(expected = SignatureException.class)
	public void testVerifyAssertionSignatureInvalidSignature()
		throws Exception {

		testVerifyAssertionSignature(UNKNOWN_ENTITY_ID);
	}

	@Test
	public void testVerifyAssertionSignatureNoSignatureNotRequired()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(ACS_URL);

		SAMLMessageContext<?, ?, ?> samlMessageContext =
			_webSsoProfileImpl.getSamlMessageContext(
				mockHttpServletRequest, new MockHttpServletResponse());

		EntityDescriptor entityDescriptor =
			samlMessageContext.getLocalEntityMetadata();

		SPSSODescriptor spSSODescriptor = entityDescriptor.getSPSSODescriptor(
			SAMLConstants.SAML20P_NS);

		spSSODescriptor.setWantAssertionsSigned(false);

		samlMessageContext.setLocalEntityRoleMetadata(spSSODescriptor);

		samlMessageContext.setPeerEntityId(IDP_ENTITY_ID);

		_webSsoProfileImpl.verifyAssertionSignature(
			null, samlMessageContext,
			metadataManagerImpl.getSignatureTrustEngine());
	}

	@Test(expected = SignatureException.class)
	public void testVerifyAssertionSignatureNoSignatureRequired()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(ACS_URL);

		SAMLMessageContext<?, ?, ?> samlMessageContext =
			_webSsoProfileImpl.getSamlMessageContext(
				mockHttpServletRequest, new MockHttpServletResponse());

		EntityDescriptor entityDescriptor =
			samlMessageContext.getLocalEntityMetadata();

		SPSSODescriptor spSSODescriptor = entityDescriptor.getSPSSODescriptor(
			SAMLConstants.SAML20P_NS);

		spSSODescriptor.setWantAssertionsSigned(true);

		samlMessageContext.setLocalEntityRoleMetadata(spSSODescriptor);

		samlMessageContext.setPeerEntityId(IDP_ENTITY_ID);

		_webSsoProfileImpl.verifyAssertionSignature(
			null, samlMessageContext,
			metadataManagerImpl.getSignatureTrustEngine());
	}

	@Test
	public void testVerifyAssertionSignatureValidSignature() throws Exception {
		testVerifyAssertionSignature(IDP_ENTITY_ID);
	}

	@Test
	public void testVerifyAudienceRestrictionsAllow() throws Exception {
		List<AudienceRestriction> audienceRestrictions = new ArrayList<>();

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(ACS_URL);

		SAMLMessageContext<?, ?, ?> samlMessageContext =
			_webSsoProfileImpl.getSamlMessageContext(
				mockHttpServletRequest, new MockHttpServletResponse());

		AudienceRestriction audienceRestriction =
			_webSsoProfileImpl.getSuccessAudienceRestriction(
				samlMessageContext.getLocalEntityId());

		audienceRestrictions.add(audienceRestriction);

		_webSsoProfileImpl.verifyAudienceRestrictions(
			audienceRestrictions, samlMessageContext);
	}

	@Test(expected = AudienceException.class)
	public void testVerifyAudienceRestrictionsDeny() throws Exception {
		List<AudienceRestriction> audienceRestrictions = new ArrayList<>();

		AudienceRestriction audienceRestriction =
			_webSsoProfileImpl.getSuccessAudienceRestriction(UNKNOWN_ENTITY_ID);

		audienceRestrictions.add(audienceRestriction);

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(ACS_URL);

		SAMLMessageContext<?, ?, ?> samlMessageContext =
			_webSsoProfileImpl.getSamlMessageContext(
				mockHttpServletRequest, new MockHttpServletResponse());

		_webSsoProfileImpl.verifyAudienceRestrictions(
			audienceRestrictions, samlMessageContext);
	}

	@Test(expected = AssertionException.class)
	public void testVerifyConditionNotOnBefore() throws Exception {
		prepareIdentityProvider(IDP_ENTITY_ID);

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(SSO_URL);

		SAMLMessageContext<AuthnRequest, Response, NameID>
			idpSamlMessageContext =
				(SAMLMessageContext<AuthnRequest, Response, NameID>)
					_webSsoProfileImpl.getSamlMessageContext(
						mockHttpServletRequest, new MockHttpServletResponse());

		idpSamlMessageContext.setPeerEntityId(SP_ENTITY_ID);

		SamlSsoRequestContext samlSsoRequestContext = new SamlSsoRequestContext(
			SP_ENTITY_ID, null, idpSamlMessageContext, userLocalService);

		Conditions conditions = _webSsoProfileImpl.getSuccessConditions(
			samlSsoRequestContext, new DateTime(DateTimeZone.UTC).plusDays(1),
			null);

		prepareServiceProvider(SP_ENTITY_ID);

		mockHttpServletRequest = getMockHttpServletRequest(ACS_URL);

		SAMLMessageContext<?, ?, ?> spSamlMessageContext =
			_webSsoProfileImpl.getSamlMessageContext(
				mockHttpServletRequest, new MockHttpServletResponse());

		_webSsoProfileImpl.verifyConditions(spSamlMessageContext, conditions);
	}

	@Test(expected = ExpiredException.class)
	public void testVerifyConditionNotOnOrAfter() throws Exception {
		prepareIdentityProvider(IDP_ENTITY_ID);

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(SSO_URL);

		SAMLMessageContext<AuthnRequest, Response, NameID>
			idpSamlMessageContext =
				(SAMLMessageContext<AuthnRequest, Response, NameID>)
					_webSsoProfileImpl.getSamlMessageContext(
						mockHttpServletRequest, new MockHttpServletResponse());

		idpSamlMessageContext.setPeerEntityId(SP_ENTITY_ID);

		SamlSsoRequestContext samlSsoRequestContext = new SamlSsoRequestContext(
			SP_ENTITY_ID, null, idpSamlMessageContext, userLocalService);

		Conditions conditions = _webSsoProfileImpl.getSuccessConditions(
			samlSsoRequestContext, null,
			new DateTime(DateTimeZone.UTC).minusYears(1));

		prepareServiceProvider(SP_ENTITY_ID);

		mockHttpServletRequest = getMockHttpServletRequest(ACS_URL);

		SAMLMessageContext<?, ?, ?> spSamlMessageContext =
			_webSsoProfileImpl.getSamlMessageContext(
				mockHttpServletRequest, new MockHttpServletResponse());

		_webSsoProfileImpl.verifyConditions(spSamlMessageContext, conditions);
	}

	@Test
	public void testVerifyConditionsNoDates() throws Exception {
		prepareIdentityProvider(IDP_ENTITY_ID);

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(SSO_URL);

		SAMLMessageContext<AuthnRequest, Response, NameID>
			idpSamlMessageContext =
				(SAMLMessageContext<AuthnRequest, Response, NameID>)
					_webSsoProfileImpl.getSamlMessageContext(
						mockHttpServletRequest, new MockHttpServletResponse());

		idpSamlMessageContext.setPeerEntityId(SP_ENTITY_ID);

		SamlSsoRequestContext samlSsoRequestContext = new SamlSsoRequestContext(
			SP_ENTITY_ID, null, idpSamlMessageContext, userLocalService);

		Conditions conditions = _webSsoProfileImpl.getSuccessConditions(
			samlSsoRequestContext, null, null);

		prepareServiceProvider(SP_ENTITY_ID);

		mockHttpServletRequest = getMockHttpServletRequest(ACS_URL);

		SAMLMessageContext<?, ?, ?> spSamlMessageContext =
			_webSsoProfileImpl.getSamlMessageContext(
				mockHttpServletRequest, new MockHttpServletResponse());

		_webSsoProfileImpl.verifyConditions(spSamlMessageContext, conditions);
	}

	@Test
	public void testVerifyDestinationAllow() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(ACS_URL);

		SAMLMessageContext<?, ?, ?> samlMessageContext =
			_webSsoProfileImpl.getSamlMessageContext(
				mockHttpServletRequest, new MockHttpServletResponse());

		samlMessageContext.setCommunicationProfileId(
			SAMLConstants.SAML2_POST_BINDING_URI);

		_webSsoProfileImpl.verifyDestination(samlMessageContext, ACS_URL);
	}

	@Test(expected = DestinationException.class)
	public void testVerifyDestinationDeny() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(ACS_URL);

		SAMLMessageContext<?, ?, ?> samlMessageContext =
			_webSsoProfileImpl.getSamlMessageContext(
				mockHttpServletRequest, new MockHttpServletResponse());

		samlMessageContext.setCommunicationProfileId(
			SAMLConstants.SAML2_POST_BINDING_URI);

		_webSsoProfileImpl.verifyDestination(
			samlMessageContext, "http://www.fail.com/c/portal/saml/acs");
	}

	@Test
	public void testVerifyInResponseToInvalidResponse() throws Exception {
		Response response = OpenSamlUtil.buildResponse();

		response.setIssuer(OpenSamlUtil.buildIssuer(IDP_ENTITY_ID));

		_webSsoProfileImpl.verifyInResponseTo(response);
	}

	@Test(expected = InResponseToException.class)
	public void testVerifyInResponseToNoAuthnRequest() throws Exception {
		Response response = OpenSamlUtil.buildResponse();

		response.setInResponseTo("responseto");
		response.setIssuer(OpenSamlUtil.buildIssuer(IDP_ENTITY_ID));

		_webSsoProfileImpl.verifyInResponseTo(response);
	}

	@Test
	public void testVerifyInResponseToValidResponse() throws Exception {
		Response response = OpenSamlUtil.buildResponse();

		SamlSpAuthRequest samlSpAuthRequest = new SamlSpAuthRequestImpl();

		samlSpAuthRequest.setSamlIdpEntityId(IDP_ENTITY_ID);

		String samlSpAuthRequestKey = identifierGenerator.generateIdentifier(
			30);

		samlSpAuthRequest.setSamlSpAuthRequestKey(samlSpAuthRequestKey);

		when(
			_samlSpAuthRequestLocalService.fetchSamlSpAuthRequest(
				Mockito.any(String.class), Mockito.any(String.class))
		).thenReturn(
			samlSpAuthRequest
		);

		response.setInResponseTo(samlSpAuthRequestKey);
		response.setIssuer(OpenSamlUtil.buildIssuer(IDP_ENTITY_ID));

		_webSsoProfileImpl.verifyInResponseTo(response);
	}

	@Test(expected = IssuerException.class)
	public void testVerifyIssuerInvalidFormat() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(ACS_URL);

		SAMLMessageContext<?, ?, ?> samlMessageContext =
			_webSsoProfileImpl.getSamlMessageContext(
				mockHttpServletRequest, new MockHttpServletResponse());

		samlMessageContext.setPeerEntityId(IDP_ENTITY_ID);

		Issuer issuer = OpenSamlUtil.buildIssuer(IDP_ENTITY_ID);

		issuer.setFormat(NameIDType.UNSPECIFIED);

		_webSsoProfileImpl.verifyIssuer(samlMessageContext, issuer);
	}

	@Test(expected = IssuerException.class)
	public void testVerifyIssuerInvalidIssuer() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(ACS_URL);

		SAMLMessageContext<?, ?, ?> samlMessageContext =
			_webSsoProfileImpl.getSamlMessageContext(
				mockHttpServletRequest, new MockHttpServletResponse());

		samlMessageContext.setPeerEntityId(IDP_ENTITY_ID);

		_webSsoProfileImpl.verifyIssuer(
			samlMessageContext, OpenSamlUtil.buildIssuer(UNKNOWN_ENTITY_ID));
	}

	@Test
	public void testVerifyIssuerValidIssuer() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(ACS_URL);

		SAMLMessageContext<?, ?, ?> samlMessageContext =
			_webSsoProfileImpl.getSamlMessageContext(
				mockHttpServletRequest, new MockHttpServletResponse());

		samlMessageContext.setPeerEntityId(IDP_ENTITY_ID);

		_webSsoProfileImpl.verifyIssuer(
			samlMessageContext, OpenSamlUtil.buildIssuer(IDP_ENTITY_ID));
	}

	@Test
	public void testVerifyReplayNoConditionsDate() throws Exception {
		prepareIdentityProvider(IDP_ENTITY_ID);

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(SSO_URL);

		SAMLMessageContext<AuthnRequest, Response, NameID>
			idpSamlMessageContext =
				(SAMLMessageContext<AuthnRequest, Response, NameID>)
					_webSsoProfileImpl.getSamlMessageContext(
						mockHttpServletRequest, new MockHttpServletResponse());

		idpSamlMessageContext.setPeerEntityId(SP_ENTITY_ID);

		SamlSsoRequestContext samlSsoRequestContext = new SamlSsoRequestContext(
			SP_ENTITY_ID, null, idpSamlMessageContext, userLocalService);

		Conditions conditions = _webSsoProfileImpl.getSuccessConditions(
			samlSsoRequestContext, null, null);

		prepareServiceProvider(SP_ENTITY_ID);

		mockHttpServletRequest = getMockHttpServletRequest(ACS_URL);

		SAMLMessageContext<?, ?, ?> spSamlMessageContext =
			_webSsoProfileImpl.getSamlMessageContext(
				mockHttpServletRequest, new MockHttpServletResponse());

		Assertion assertion = OpenSamlUtil.buildAssertion();

		assertion.setConditions(conditions);

		Issuer issuer = OpenSamlUtil.buildIssuer(IDP_ENTITY_ID);

		assertion.setIssuer(issuer);

		String messageId = identifierGenerator.generateIdentifier();

		assertion.setID(messageId);

		SamlSpMessageLocalService samlSpMessageLocalService =
			getMockPortletService(
				SamlSpMessageLocalServiceUtil.class,
				SamlSpMessageLocalService.class);

		when(
			samlSpMessageLocalService.fetchSamlSpMessage(
				Mockito.eq(IDP_ENTITY_ID), Mockito.eq(messageId))
		).thenReturn(
			null
		);

		_webSsoProfileImpl.verifyReplay(spSamlMessageContext, assertion);
	}

	@Test(expected = ExpiredException.class)
	public void testVerifySubjectExpired() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(ACS_URL);

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			(SAMLMessageContext<AuthnRequest, Response, NameID>)
				_webSsoProfileImpl.getSamlMessageContext(
					mockHttpServletRequest, new MockHttpServletResponse());

		samlMessageContext.setCommunicationProfileId(
			SAMLConstants.SAML2_POST_BINDING_URI);
		samlMessageContext.setPeerEntityId(IDP_ENTITY_ID);

		NameID nameID = OpenSamlUtil.buildNameId(
			NameIDType.UNSPECIFIED, "test");

		DateTime issueDate = new DateTime(DateTimeZone.UTC);

		issueDate = issueDate.minusYears(1);

		Subject subject = getSubject(samlMessageContext, nameID, issueDate);

		_webSsoProfileImpl.verifySubject(samlMessageContext, subject);
	}

	@Test(expected = SubjectException.class)
	public void testVerifySubjectNoBearerSubjectConfirmation()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(ACS_URL);

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			(SAMLMessageContext<AuthnRequest, Response, NameID>)
				_webSsoProfileImpl.getSamlMessageContext(
					mockHttpServletRequest, new MockHttpServletResponse());

		samlMessageContext.setCommunicationProfileId(
			SAMLConstants.SAML2_POST_BINDING_URI);
		samlMessageContext.setPeerEntityId(IDP_ENTITY_ID);

		NameID nameID = OpenSamlUtil.buildNameId(
			NameIDType.UNSPECIFIED, "test");

		Subject subject = getSubject(
			samlMessageContext, nameID, new DateTime(DateTimeZone.UTC));

		List<SubjectConfirmation> subjectConfirmations =
			subject.getSubjectConfirmations();

		SubjectConfirmation subjectConfirmation = subjectConfirmations.get(0);

		subjectConfirmation.setMethod(
			SubjectConfirmation.METHOD_SENDER_VOUCHES);

		_webSsoProfileImpl.verifySubject(samlMessageContext, subject);
	}

	@Test
	public void testVerifySubjectValidSubject() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(ACS_URL);

		SAMLMessageContext<AuthnRequest, Response, NameID> samlMessageContext =
			(SAMLMessageContext<AuthnRequest, Response, NameID>)
				_webSsoProfileImpl.getSamlMessageContext(
					mockHttpServletRequest, new MockHttpServletResponse());

		samlMessageContext.setCommunicationProfileId(
			SAMLConstants.SAML2_POST_BINDING_URI);
		samlMessageContext.setPeerEntityId(IDP_ENTITY_ID);

		NameID nameID = OpenSamlUtil.buildNameId(
			NameIDType.UNSPECIFIED, "test");

		Subject subject = getSubject(
			samlMessageContext, nameID, new DateTime(DateTimeZone.UTC));

		_webSsoProfileImpl.verifySubject(samlMessageContext, subject);

		NameID resolvedNameId = samlMessageContext.getSubjectNameIdentifier();

		Assert.assertNotNull(resolvedNameId);
		Assert.assertEquals(nameID.getFormat(), resolvedNameId.getFormat());
		Assert.assertEquals(nameID.getValue(), resolvedNameId.getValue());
	}

	protected Subject getSubject(
			SAMLMessageContext<AuthnRequest, Response, NameID>
				samlMessageContext,
			NameID nameID, DateTime issueDate)
		throws Exception {

		SamlSsoRequestContext samlSsoRequestContext = new SamlSsoRequestContext(
			samlMessageContext.getPeerEntityId(),
			samlMessageContext.getRelayState(), samlMessageContext,
			userLocalService);

		SPSSODescriptor spSSODescriptor =
			(SPSSODescriptor)samlMessageContext.getLocalEntityRoleMetadata();

		AssertionConsumerService assertionConsumerService =
			SamlUtil.getAssertionConsumerServiceForBinding(
				spSSODescriptor, SAMLConstants.SAML2_POST_BINDING_URI);

		SubjectConfirmationData subjectConfirmationData =
			_webSsoProfileImpl.getSuccessSubjectConfirmationData(
				samlSsoRequestContext, assertionConsumerService, issueDate);

		return _webSsoProfileImpl.getSuccessSubject(
			samlSsoRequestContext, assertionConsumerService, nameID,
			subjectConfirmationData);
	}

	protected void testVerifyAssertionSignature(String entityId)
		throws Exception {

		Assertion assertion = OpenSamlUtil.buildAssertion();

		Credential credential = getCredential(entityId);

		OpenSamlUtil.signObject(assertion, credential);

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(ACS_URL);

		SAMLMessageContext<?, ?, ?> samlMessageContext =
			_webSsoProfileImpl.getSamlMessageContext(
				mockHttpServletRequest, new MockHttpServletResponse());

		samlMessageContext.setPeerEntityId(IDP_ENTITY_ID);

		_webSsoProfileImpl.verifyAssertionSignature(
			assertion.getSignature(), samlMessageContext,
			metadataManagerImpl.getSignatureTrustEngine());
	}

	private SamlSpAuthRequestLocalService _samlSpAuthRequestLocalService;
	private SamlSpSessionLocalService _samlSpSessionLocalService;
	private final WebSsoProfileImpl _webSsoProfileImpl =
		new WebSsoProfileImpl();

}