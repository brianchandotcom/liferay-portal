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

import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.saml.constants.SamlWebKeys;
import com.liferay.saml.opensaml.integration.SamlBinding;
import com.liferay.saml.opensaml.integration.internal.BaseSamlTestCase;
import com.liferay.saml.persistence.model.SamlIdpSpConnection;
import com.liferay.saml.persistence.model.SamlIdpSpSession;
import com.liferay.saml.persistence.model.SamlSpSession;
import com.liferay.saml.persistence.model.impl.SamlIdpSpConnectionImpl;
import com.liferay.saml.persistence.model.impl.SamlIdpSpSessionImpl;
import com.liferay.saml.persistence.model.impl.SamlIdpSsoSessionImpl;
import com.liferay.saml.persistence.model.impl.SamlSpSessionImpl;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalService;
import com.liferay.saml.persistence.service.SamlIdpSpConnectionLocalServiceUtil;
import com.liferay.saml.persistence.service.SamlIdpSpSessionLocalService;
import com.liferay.saml.persistence.service.SamlIdpSpSessionLocalServiceUtil;
import com.liferay.saml.persistence.service.SamlSpSessionLocalService;
import com.liferay.saml.persistence.service.SamlSpSessionLocalServiceUtil;
import com.liferay.saml.util.JspUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.opensaml.common.binding.SAMLMessageContext;
import org.opensaml.common.xml.SAMLConstants;
import org.opensaml.saml2.core.LogoutRequest;
import org.opensaml.saml2.core.NameID;

import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Matthew Tambara
 * @author William Newbury
 */
@PowerMockIgnore("javax.security.*")
@RunWith(PowerMockRunner.class)
public class SingleLogoutProfileIntegrationTest extends BaseSamlTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());

		_samlIdpSpConnectionLocalService = getMockPortletService(
			SamlIdpSpConnectionLocalServiceUtil.class,
			SamlIdpSpConnectionLocalService.class);
		_samlIdpSpSessionLocalService = getMockPortletService(
			SamlIdpSpSessionLocalServiceUtil.class,
			SamlIdpSpSessionLocalService.class);
		_samlSpSessionLocalService = getMockPortletService(
			SamlSpSessionLocalServiceUtil.class,
			SamlSpSessionLocalService.class);

		_singleLogoutProfileImpl = new SingleLogoutProfileImpl();

		_singleLogoutProfileImpl.setIdentifierGenerator(identifierGenerator);
		_singleLogoutProfileImpl.setMetadataManager(metadataManagerImpl);
		_singleLogoutProfileImpl.setPortal(portal);
		_singleLogoutProfileImpl.setSamlBindings(samlBindings);
		_singleLogoutProfileImpl.setSamlProviderConfigurationHelper(
			samlProviderConfigurationHelper);
		_singleLogoutProfileImpl.setSamlSpSessionLocalService(
			_samlSpSessionLocalService);

		prepareServiceProvider(SP_ENTITY_ID);
	}

	@Test
	public void testPerformIdpSpLogoutInvalidSloRequestInfo() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(SLO_LOGOUT_URL + "?cmd=logout");

		SamlSloContext samlSloContext = new SamlSloContext(
			null, _samlIdpSpConnectionLocalService,
			_samlIdpSpSessionLocalService, userLocalService);

		_singleLogoutProfileImpl.performIdpSpLogout(
			mockHttpServletRequest, new MockHttpServletResponse(),
			samlSloContext);

		Assert.assertEquals(
			JspUtil.PATH_PORTAL_SAML_ERROR,
			mockHttpServletRequest.getAttribute("tilesContent"));
		Assert.assertTrue(
			Boolean.valueOf(
				(String)mockHttpServletRequest.getAttribute("tilesPopUp")));
	}

	@Test
	public void testPerformIdpSpLogoutValidSloRequestInfo() throws Exception {
		SamlIdpSpConnection samlIdpSpConnection = new SamlIdpSpConnectionImpl();

		when(
			_samlIdpSpConnectionLocalService.getSamlIdpSpConnection(
				COMPANY_ID, SP_ENTITY_ID)
		).thenReturn(
			samlIdpSpConnection
		);

		List<SamlIdpSpSession> samlIdpSpSessions = new ArrayList<>();

		SamlIdpSpSessionImpl samlIdpSpSessionImpl = new SamlIdpSpSessionImpl();

		samlIdpSpSessionImpl.setCompanyId(COMPANY_ID);
		samlIdpSpSessionImpl.setSamlSpEntityId(SP_ENTITY_ID);

		samlIdpSpSessions.add(samlIdpSpSessionImpl);

		when(
			_samlIdpSpSessionLocalService.getSamlIdpSpSessions(SESSION_ID)
		).thenReturn(
			samlIdpSpSessions
		);

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(SLO_LOGOUT_URL + "?cmd=logout");

		mockHttpServletRequest.setParameter("entityId", SP_ENTITY_ID);

		SamlIdpSsoSessionImpl samlIdpSsoSessionImpl =
			new SamlIdpSsoSessionImpl();

		samlIdpSsoSessionImpl.setSamlIdpSsoSessionId(SESSION_ID);

		SamlSloContext samlSloContext = new SamlSloContext(
			samlIdpSsoSessionImpl, _samlIdpSpConnectionLocalService,
			_samlIdpSpSessionLocalService, userLocalService);

		SamlSloRequestInfo samlSloRequestInfo =
			samlSloContext.getSamlSloRequestInfo(SP_ENTITY_ID);

		samlSloRequestInfo.setStatus(SamlSloRequestInfo.REQUEST_STATUS_SUCCESS);

		_singleLogoutProfileImpl.performIdpSpLogout(
			mockHttpServletRequest, new MockHttpServletResponse(),
			samlSloContext);

		Assert.assertEquals(
			JspUtil.PATH_PORTAL_SAML_SLO_SP_STATUS,
			mockHttpServletRequest.getAttribute("tilesContent"));
		Assert.assertTrue(
			Boolean.valueOf(
				(String)mockHttpServletRequest.getAttribute("tilesPopUp")));

		JSONObject jsonObject = (JSONObject)mockHttpServletRequest.getAttribute(
			SamlWebKeys.SAML_SLO_REQUEST_INFO);

		Assert.assertNotNull(jsonObject);
		Assert.assertEquals(SP_ENTITY_ID, jsonObject.getString("entityId"));
		Assert.assertEquals(
			SamlSloRequestInfo.REQUEST_STATUS_SUCCESS,
			jsonObject.getInt("status"));
	}

	@Test
	public void testSendIdpLogoutRequestHttpRedirect() throws Exception {
		prepareIdentityProvider(IDP_ENTITY_ID);

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(SLO_LOGOUT_URL + "?cmd=logout");
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		SamlIdpSsoSessionImpl samlIdpSsoSessionImpl =
			new SamlIdpSsoSessionImpl();

		SamlSloContext samlSloContext = new SamlSloContext(
			samlIdpSsoSessionImpl, _samlIdpSpConnectionLocalService,
			_samlIdpSpSessionLocalService, userLocalService);

		SamlIdpSpSessionImpl samlIdpSpSessionImpl = new SamlIdpSpSessionImpl();

		samlIdpSpSessionImpl.setNameIdFormat(NameID.EMAIL);
		samlIdpSpSessionImpl.setNameIdValue("test@liferay.com");
		samlIdpSpSessionImpl.setSamlSpEntityId(SP_ENTITY_ID);

		SamlSloRequestInfo samlSloRequestInfo = new SamlSloRequestInfo();

		samlSloRequestInfo.setSamlIdpSpSession(samlIdpSpSessionImpl);

		_singleLogoutProfileImpl.sendIdpLogoutRequest(
			mockHttpServletRequest, mockHttpServletResponse, samlSloContext,
			samlSloRequestInfo);

		String redirect = mockHttpServletResponse.getRedirectedUrl();

		Assert.assertNotNull(redirect);

		mockHttpServletRequest = getMockHttpServletRequest(redirect);

		SamlBinding samlBinding = _singleLogoutProfileImpl.getSamlBinding(
			SAMLConstants.SAML2_REDIRECT_BINDING_URI);

		SAMLMessageContext<?, ?, ?> samlMessageContext =
			_singleLogoutProfileImpl.decodeSamlMessage(
				mockHttpServletRequest, mockHttpServletResponse, samlBinding,
				true);

		LogoutRequest logoutRequest =
			(LogoutRequest)samlMessageContext.getInboundSAMLMessage();

		NameID nameID = logoutRequest.getNameID();

		Assert.assertEquals(NameID.EMAIL, nameID.getFormat());
		Assert.assertEquals("test@liferay.com", nameID.getValue());
	}

	@Test
	public void testSendSpLogoutRequestInvalidSpSession() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(LOGOUT_URL);
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_singleLogoutProfileImpl.sendSpLogoutRequest(
			mockHttpServletRequest, mockHttpServletResponse);

		String redirect = mockHttpServletResponse.getRedirectedUrl();

		Assert.assertNotNull(redirect);
		Assert.assertEquals(LOGOUT_URL, redirect);
	}

	@Test
	public void testSendSpLogoutRequestValidSpSession() throws Exception {
		SamlSpSession samlSpSession = new SamlSpSessionImpl();

		samlSpSession.setNameIdFormat(NameID.EMAIL);
		samlSpSession.setNameIdValue("test@liferay.com");

		when(
			_samlSpSessionLocalService.fetchSamlSpSessionByJSessionId(
				Mockito.anyString())
		).thenReturn(
			samlSpSession
		);

		MockHttpServletRequest mockHttpServletRequest =
			getMockHttpServletRequest(LOGOUT_URL);
		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		_singleLogoutProfileImpl.sendSpLogoutRequest(
			mockHttpServletRequest, mockHttpServletResponse);

		String redirect = mockHttpServletResponse.getRedirectedUrl();

		Assert.assertNotNull(redirect);

		mockHttpServletRequest = getMockHttpServletRequest(redirect);

		SamlBinding samlBinding = _singleLogoutProfileImpl.getSamlBinding(
			SAMLConstants.SAML2_REDIRECT_BINDING_URI);

		SAMLMessageContext<?, ?, ?> samlMessageContext =
			_singleLogoutProfileImpl.decodeSamlMessage(
				mockHttpServletRequest, mockHttpServletResponse, samlBinding,
				true);

		LogoutRequest logoutRequest =
			(LogoutRequest)samlMessageContext.getInboundSAMLMessage();

		NameID nameID = logoutRequest.getNameID();

		Assert.assertEquals(NameID.EMAIL, nameID.getFormat());
		Assert.assertEquals("test@liferay.com", nameID.getValue());
	}

	private SamlIdpSpConnectionLocalService _samlIdpSpConnectionLocalService;
	private SamlIdpSpSessionLocalService _samlIdpSpSessionLocalService;
	private SamlSpSessionLocalService _samlSpSessionLocalService;
	private SingleLogoutProfileImpl _singleLogoutProfileImpl;

}