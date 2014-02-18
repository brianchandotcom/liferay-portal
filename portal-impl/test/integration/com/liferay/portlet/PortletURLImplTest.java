/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet;

import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletApp;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.security.auth.AuthTokenUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.service.PortletPreferencesLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletCategoryKeys;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.social.util.FacebookUtil;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;

import java.lang.reflect.Field;

import java.net.URLEncoder;

import java.security.Key;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Tomas Polesovsky
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class PortletURLImplTest {

	@Before
	public void setUp() throws Exception {
		_portletUrlAppendParametersField = ReflectionUtil.getDeclaredField(
			PropsValues.class, "PORTLET_URL_APPEND_PARAMETERS");

		_portletUrlAppendParametersField.set(null, false);

		_portletUrlEscapeXMLField = ReflectionUtil.getDeclaredField(
			PropsValues.class, "PORTLET_URL_ESCAPE_XML");

		_portletUrlEscapeXMLField.set(null, false);

		_portletUrlAnchorEnableField = ReflectionUtil.getDeclaredField(
			PropsValues.class, "PORTLET_URL_ANCHOR_ENABLE");

		_portletUrlAnchorEnableField.set(null, false);

		_group = GroupTestUtil.addGroup("TestGroup");

		LayoutSetLocalServiceUtil.updateVirtualHost(
			_group.getGroupId(), false, "domain1.net");

		LayoutSetLocalServiceUtil.updateVirtualHost(
			_group.getGroupId(), true, "domain2.net");

		_sourceLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), "Source Layout", false);

		_targetLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), "Destination Layout", true);

		addPortlet(_targetLayout, PORTLET_WIKI_ID);

		_request = new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setPathMain("/c");

		themeDisplay.setScopeGroupId(_group.getGroupId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser());

		themeDisplay.setPermissionChecker(permissionChecker);

		themeDisplay.setLayout(_sourceLayout);

		themeDisplay.setSecure(false);
		themeDisplay.setServerName("liferay.com");
		themeDisplay.setServerPort(80);

		_request.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		Company company = PortalUtil.getCompany(_request);

		company.setKeyObj(null);
		company.setKey(COMPANY_KEY);
		_key = company.getKeyObj();
	}

	@After
	public void tearDown() throws Exception {
		PortletPreferencesLocalServiceUtil.deletePortletPreferencesByPlid(
			_sourceLayout.getPlid());

		PortletPreferencesLocalServiceUtil.deletePortletPreferencesByPlid(
			_targetLayout.getPlid());

		LayoutLocalServiceUtil.deleteLayout(_sourceLayout);
		LayoutLocalServiceUtil.deleteLayout(_targetLayout);

		GroupLocalServiceUtil.deleteGroup(_group);
	}

	@Test
	public void testActionURL() throws Exception {
		_portletUrlAnchorEnableField.set(null, false);
		_portletUrlAppendParametersField.set(null, false);
		testActionURL(false, false);
		testActionURL(false, true);
		testActionURL(true, false);
		testActionURL(true, true);

		_portletUrlAnchorEnableField.set(null, true);
		_portletUrlAppendParametersField.set(null, true);
		testActionURL(false, false);
		testActionURL(false, true);
		testActionURL(true, false);
		testActionURL(true, true);
	}

	@Test
	public void testCopyCurrentRenderParameters() throws Exception {
		List<String[]> expectedURLParts = new ArrayList<String[]>();

		Map<String, String[]> renderParameters =
			new HashMap<String, String[]>();

		renderParameters.put("a", new String[]{"a0"});
		renderParameters.put("b", new String[]{"b0", "b1"});
		renderParameters.put(
			namespace("c", PORTLET_DDM_ID), new String[]{"c0"});

		renderParameters.put("d", new String[]{"d0"});

		RenderParametersPool.put(
			_request, _targetLayout.getPlid(), PORTLET_DDM_ID,
			renderParameters);

		Set<String> removedParameterNames = new HashSet<String>();
		removedParameterNames.add("d");

		PortletURLImpl portletURL = new PortletURLImpl(
			_request, PORTLET_DDM_ID, _targetLayout.getPlid(),
			PortletRequest.RENDER_PHASE);

		portletURL.setCopyCurrentRenderParameters(true);
		portletURL.setParameter("a", "a1");
		portletURL.setParameter("b", "b2");

		portletURL.setRemovedParameterNames(removedParameterNames);

		addExpectedURLPart(
			"http://domain2.net/destination-layout?", expectedURLParts);

		addExpectedURLPart(
			"p_p_id", PORTLET_DDM_ID, false, false, expectedURLParts);

		addExpectedURLPart(
			"p_p_lifecycle", "0", false, false, expectedURLParts);

		addExpectedURLPart(
			"a", new String[]{"a1", "a0"}, false, false, PORTLET_DDM_ID,
			expectedURLParts);

		addExpectedURLPart(
			"b", new String[]{"b2", "b0", "b1"}, false, false, PORTLET_DDM_ID,
			expectedURLParts);

		addExpectedURLPart(
			"c", "c0", false, false, PORTLET_DDM_ID, expectedURLParts);

		expectedURLParts.remove(AMPERSAND);

		compareURLsPartiallyOrdered(
			expectedURLParts, portletURL.generateToString());

		expectedURLParts.clear();

		portletURL = new PortletURLImpl(
			_request, PORTLET_DDM_ID, _targetLayout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		portletURL.setCopyCurrentRenderParameters(true);
		portletURL.setParameter("a", "a1");
		portletURL.setParameter("b", "b2");

		portletURL.setRemovedParameterNames(removedParameterNames);

		addExpectedURLPart(
			"http://domain2.net/destination-layout?", expectedURLParts);

		addExpectedURLPart(
			"p_p_id", PORTLET_DDM_ID, false, false, expectedURLParts);

		addExpectedURLPart(
			"p_p_lifecycle", "2", false, false, expectedURLParts);

		addExpectedURLPart(
			"p_p_cacheability", "cacheLevelPage", false, false,
			expectedURLParts);

		addExpectedURLPart(
			"a", new String[]{"a1", "a0"}, false, false, PORTLET_DDM_ID,
			expectedURLParts);

		addExpectedURLPart(
			"b", new String[]{"b2", "b0", "b1"}, false, false, PORTLET_DDM_ID,
			expectedURLParts);

		addExpectedURLPart(
			"c", "c0", false, false, PORTLET_DDM_ID, expectedURLParts);

		addExpectedURLPart(
			"d", "d0", false, false, PORTLET_DDM_ID, expectedURLParts);

		expectedURLParts.remove(AMPERSAND);

		compareURLsPartiallyOrdered(
			expectedURLParts, portletURL.generateToString());

		String url1 = portletURL.generateToString();
		portletURL.clearCache();
		String url2 = portletURL.generateToString();

		Assert.assertEquals("Both URLs should be the same", url1, url2);
	}

	@Test
	public void testEscapeXMLContainerRuntimeOption() throws Exception {
		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			PORTLET_DDM_ID);

		PortletApp portletApp = portlet.getPortletApp();
		portletApp.getContainerRuntimeOptions().put(
			LiferayPortletConfig.RUNTIME_OPTION_ESCAPE_XML,
			new String[]{"false"});

		PortletURLImpl portletURL;
		List<String[]> expectedURLParts = new ArrayList<String[]>();

		portletURL = new PortletURLImpl(
			_request, PORTLET_DDM_ID, _targetLayout.getPlid(),
			PortletRequest.RENDER_PHASE);

		addExpectedURLPart(
			"http://domain2.net/destination-layout?", expectedURLParts);

		addExpectedURLPart(
			"p_p_lifecycle", "0", false, false, expectedURLParts);

		addExpectedURLPart(
			"p_p_id", PORTLET_DDM_ID, false, false, expectedURLParts);

		portletURL.setParameter("test&name", "test&value");
		addExpectedURLPart(
			"test&name", "test&value", false, false, PORTLET_DDM_ID,
			expectedURLParts);

		expectedURLParts.remove(AMPERSAND);

		compareURLsPartiallyOrdered(
			expectedURLParts, portletURL.generateToString());

		portletApp.getContainerRuntimeOptions().put(
			LiferayPortletConfig.RUNTIME_OPTION_ESCAPE_XML,
			new String[]{"true"});

		expectedURLParts.clear();

		portletURL = new PortletURLImpl(
			_request, PORTLET_DDM_ID, _targetLayout.getPlid(),
			PortletRequest.RENDER_PHASE);

		addExpectedURLPart(
			"http://domain2.net/destination-layout?", expectedURLParts);

		addExpectedURLPart("p_p_lifecycle", "0", false, true, expectedURLParts);

		addExpectedURLPart(
			"p_p_id", PORTLET_DDM_ID, false, true, expectedURLParts);

		portletURL.setParameter("test&name", "test&value");
		addExpectedURLPart(
			"test&name", "test&value", false, true, PORTLET_DDM_ID,
			expectedURLParts);

		expectedURLParts.remove(AMPERSAND_ESCAPED);

		compareURLsPartiallyOrdered(
			expectedURLParts, portletURL.generateToString());
	}

	@Test
	public void testEscapeXMLField() throws Exception {
		_portletUrlEscapeXMLField.set(null, false);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			PORTLET_DDM_ID);

		PortletApp portletApp = portlet.getPortletApp();
		portletApp.getContainerRuntimeOptions().remove(
			LiferayPortletConfig.RUNTIME_OPTION_ESCAPE_XML);

		PortletURLImpl portletURL;
		List<String[]> expectedURLParts = new ArrayList<String[]>();

		portletURL = new PortletURLImpl(
			_request, PORTLET_DDM_ID, _targetLayout.getPlid(),
			PortletRequest.RENDER_PHASE);

		addExpectedURLPart(
			"http://domain2.net/destination-layout?", expectedURLParts);

		addExpectedURLPart(
			"p_p_lifecycle", "0", false, false, expectedURLParts);

		addExpectedURLPart
			("p_p_id", PORTLET_DDM_ID, false, false, expectedURLParts);

		portletURL.setParameter("test&name", "test&value");
		addExpectedURLPart(
			"test&name", "test&value", false, false, PORTLET_DDM_ID,
			expectedURLParts);

		expectedURLParts.remove(AMPERSAND);

		compareURLsPartiallyOrdered(
			expectedURLParts, portletURL.generateToString());

		_portletUrlEscapeXMLField.set(null, true);

		expectedURLParts.clear();

		portletURL = new PortletURLImpl(
			_request, PORTLET_DDM_ID, _targetLayout.getPlid(),
			PortletRequest.RENDER_PHASE);

		addExpectedURLPart(
			"http://domain2.net/destination-layout?", expectedURLParts);

		addExpectedURLPart("p_p_lifecycle", "0", false, true, expectedURLParts);
		addExpectedURLPart(
			"p_p_id", PORTLET_DDM_ID, false, true, expectedURLParts);

		portletURL.setParameter("test&name", "test&value");
		addExpectedURLPart(
			"test&name", "test&value", false, true, PORTLET_DDM_ID,
			expectedURLParts);

		expectedURLParts.remove(AMPERSAND_ESCAPED);

		compareURLsPartiallyOrdered(
			expectedURLParts, portletURL.generateToString());
	}

	@Test
	public void testFacebookURL() throws Exception {
		List<String[]> expectedURLParts = new ArrayList<String[]>();

		ThemeDisplay themeDisplay = (ThemeDisplay) _request.getAttribute(
			WebKeys.THEME_DISPLAY);

		themeDisplay.setFacebookCanvasPageURL("canvas_page");

		try {
			PortletURLImpl portletURL = new PortletURLImpl(
				_request, PORTLET_DDM_ID, _targetLayout.getPlid(),
				PortletRequest.RENDER_PHASE);

			addExpectedURLPart(
				FacebookUtil.FACEBOOK_APPS_URL, expectedURLParts);

			addExpectedURLPart("canvas_page/?", expectedURLParts);
			addExpectedURLPart(
				"p_p_lifecycle", "0", false, false, expectedURLParts);

			addExpectedURLPart(
				"p_p_id", PORTLET_DDM_ID, false, false, expectedURLParts);

			portletURL.setParameter("test&name", "test&value");
			addExpectedURLPart(
				"test&name", "test&value", false, false, PORTLET_DDM_ID,
				expectedURLParts);

			expectedURLParts.remove(AMPERSAND);

			compareURLsPartiallyOrdered(
				expectedURLParts, portletURL.generateToString());
		}
		finally {
			themeDisplay.setFacebookCanvasPageURL(null);
		}
	}

	@Test
	public void testPortletFriendlyURL() throws Exception {
		List<String[]> expectedURLParts = new ArrayList<String[]>();

		addPortlet(_targetLayout, PORTLET_JOURNAL_ID);

		PortletURLImpl portletURL = new PortletURLImpl(
			_request, PORTLET_JOURNAL_ID, _targetLayout.getPlid(),
			PortletRequest.RESOURCE_PHASE);

		portletURL.setParameter("struts_action", "/journal/rss");
		portletURL.setParameter("feedId", "feedId");

		portletURL.setParameter("test&name", "test&value");
		addExpectedURLPart(
			"test&name", "test&value", false, false, PORTLET_JOURNAL_ID,
			expectedURLParts);

		portletURL.setWindowState(WindowState.NORMAL);

		addExpectedURLPart(
			"http://domain2.net/destination-layout", expectedURLParts);
		addExpectedURLPart("/-/journal", expectedURLParts);
		addExpectedURLPart("/rss/feedId?", expectedURLParts);

		expectedURLParts.remove(AMPERSAND);

		compareURLsPartiallyOrdered(
			expectedURLParts, portletURL.generateToString());
	}

	@Test
	public void testPPAuthToken() throws Exception {
		List<String[]> expectedURLParts = new ArrayList<String[]>();

		// DDM is whitelisted, see portlet.add.default.resource.check.whitelist

		testPortletURL(
			PORTLET_DDM_ID, PortletRequest.RENDER_PHASE, false, false,
			expectedURLParts);

		addExpectedURLPart(
			"p_p_auth", AuthTokenUtil.getToken(_request,
			_targetLayout.getPlid(), PORTLET_MY_ACCOUNT_ID), false, false, null,
			expectedURLParts);

		testPortletURL(
			PORTLET_MY_ACCOUNT_ID, PortletRequest.RENDER_PHASE, false, false,
			expectedURLParts);
	}

	@Test
	public void testRenderURL() throws Exception {
		_portletUrlAnchorEnableField.set(null, false);
		_portletUrlAppendParametersField.set(null, false);
		testRenderURL(false, false);
		testRenderURL(false, true);
		testRenderURL(true, false);
		testRenderURL(true, true);

		_portletUrlAnchorEnableField.set(null, true);
		_portletUrlAppendParametersField.set(null, true);
		testRenderURL(false, false);
		testRenderURL(false, true);
		testRenderURL(true, false);
		testRenderURL(true, true);
	}

	@Test
	public void testResourceURL() throws Exception {
		_portletUrlAnchorEnableField.set(null, false);
		_portletUrlAppendParametersField.set(null, false);
		testResourceURL(false, false);
		testResourceURL(false, true);
		testResourceURL(true, false);
		testResourceURL(true, true);

		_portletUrlAnchorEnableField.set(null, true);
		_portletUrlAppendParametersField.set(null, true);
		testResourceURL(false, false);
		testResourceURL(false, true);
		testResourceURL(true, false);
		testResourceURL(true, true);
	}

	@Test
	public void testSetPlid() throws Exception {
		List<String[]> expectedURLParts = new ArrayList<String[]>();

		PortletURLImpl portletURL = new PortletURLImpl(
			_request, PORTLET_WIKI_ID, _targetLayout.getPlid(),
			PortletRequest.RENDER_PHASE);

		addExpectedURLPart(
			"http://domain2.net/destination-layout?", expectedURLParts);

		addExpectedURLPart(
			"p_p_id", PORTLET_WIKI_ID, false, false, expectedURLParts);

		addExpectedURLPart(
			"p_p_lifecycle", "0", false, false, expectedURLParts);

		expectedURLParts.remove(AMPERSAND);

		compareURLsPartiallyOrdered(
			expectedURLParts, portletURL.generateToString());

		expectedURLParts.clear();

		portletURL.setPlid(_sourceLayout.getPlid());

		addExpectedURLPart(
			"http://domain1.net/source-layout?", expectedURLParts);

		addExpectedURLPart(
			"p_p_auth", AuthTokenUtil.getToken(_request,
			_sourceLayout.getPlid(), PORTLET_WIKI_ID), false, false, null,
			expectedURLParts);

		addExpectedURLPart(
			"p_p_id", PORTLET_WIKI_ID, false, false, expectedURLParts);

		addExpectedURLPart(
			"p_p_lifecycle", "0", false, false, expectedURLParts);

		expectedURLParts.remove(AMPERSAND);

		compareURLsPartiallyOrdered(
			expectedURLParts, portletURL.generateToString());
	}

	@Test
	public void testSetPortletIdWithAutopropagatedParameters()
		throws Exception {

		List<String[]> expectedURLParts = new ArrayList<String[]>();

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			PORTLET_DDM_ID);

		Set<String> autopropagatedParameters =
			portlet.getAutopropagatedParameters();

		Assert.assertTrue(
			"Portlet " + PORTLET_DDM_ID +
				" should have at least two autopropagated parameters!",
			autopropagatedParameters.size() > 1);

		for (String autopropagatedParameter : autopropagatedParameters) {
			_request.setParameter(autopropagatedParameter, "0");
		}

		PortletURLImpl portletURL = new PortletURLImpl(
			_request, "0", _targetLayout.getPlid(),
			PortletRequest.RENDER_PHASE);

		preparePortletURL(
			PORTLET_DDM_ID, portletURL, expectedURLParts, false, false);

		portletURL.setPortletId(PORTLET_DDM_ID);

		compareURLsPartiallyOrdered(
			expectedURLParts, portletURL.generateToString());
	}

	@Test
	public void testSetPortletIdWithPublicRenderParameters() throws Exception {
		List<String[]> expectedURLParts = new ArrayList<String[]>();

		PortletURLImpl portletURL = new PortletURLImpl(
			_request, "0", _targetLayout.getPlid(),
			PortletRequest.RENDER_PHASE);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			PORTLET_WIKI_ID);

		Set<PublicRenderParameter> publicRenderParameters =
			portlet.getPublicRenderParameters();

		Assert.assertTrue(
			"Portlet " + PORTLET_WIKI_ID +
				" should have at least one public render parameter!",
			publicRenderParameters.size() > 0);

		for (PublicRenderParameter publicRenderParameter :
				publicRenderParameters) {

			QName tagPRPQName = publicRenderParameter.getQName();

			String tagPRPName = PortletQNameUtil.getPublicRenderParameterName(
				tagPRPQName);

			portletURL.setParameter(publicRenderParameter.getIdentifier(), "0");

			addExpectedURLPart(tagPRPName, "0", false, false, expectedURLParts);

			portletURL.removePublicRenderParameter(
				publicRenderParameter.getIdentifier());

			String tagPRPNameRemove =
				PortletQNameUtil.getRemovePublicRenderParameterName(
					tagPRPQName);

			addExpectedURLPart(
				tagPRPNameRemove, "1", false, false, expectedURLParts);
		}

		portletURL.setPortletId(PORTLET_WIKI_ID);

		addExpectedURLPart(
			"http://domain2.net/destination-layout?", expectedURLParts);

		addExpectedURLPart(
			"p_p_id", PORTLET_WIKI_ID, false, false, expectedURLParts);

		addExpectedURLPart(
			"p_p_lifecycle", "0", false, false, expectedURLParts);

		expectedURLParts.remove(AMPERSAND);

		compareURLsPartiallyOrdered(
			expectedURLParts, portletURL.generateToString());
	}

	@Test
	public void testWSRPURLs() throws Exception {
		_request.setParameter(_WSRP, Boolean.TRUE.toString());
		try {
			testActionURL();
			testRenderURL();
			testResourceURL();
		}
		finally {
			_request.setParameter(_WSRP, Boolean.FALSE.toString());
		}
	}

	protected void addPortlet(Layout layout, String portletId)
		throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		if (layoutTypePortlet.hasPortletId(portletId)) {
			return;
		}

		layoutTypePortlet.addPortletId(0, portletId, "column-1", -1, false);

		LayoutLocalServiceUtil.updateLayout(
			layout.getGroupId(), layout.isPrivateLayout(), layout.getLayoutId(),
			layout.getTypeSettings());

		// add portlet preferences

		PortletPreferencesFactoryUtil.getLayoutPortletSetup(layout, portletId);
	}

	protected void compareURLsPartiallyOrdered(
		List<String[]> expectedPartiallyOrderedParts, String actualURL) {

		for (String[] expectedOrderedParts : expectedPartiallyOrderedParts) {
			String newURL = actualURL;
			int lastPos = -1;

			for (int i = 0; i < expectedOrderedParts.length; i++) {
				String expectedPart = expectedOrderedParts[i];

				int pos = actualURL.indexOf(expectedPart);

				Assert.assertTrue(
					expectedPart + " should be present in " + actualURL,
					pos > -1);

				if (i > 0) {
					Assert.assertTrue(
						expectedPart + " should be after " +
							expectedOrderedParts[i - 1] + " in " + actualURL,
						pos > lastPos);
				}

				lastPos = pos;

				pos = newURL.indexOf(expectedPart);

				String prefix = newURL.substring(0, pos);
				String postfix = newURL.substring(pos + expectedPart.length());

				newURL = prefix.concat(postfix);
			}

			actualURL = newURL;
		}

		Assert.assertTrue("Should be empty: " + actualURL, actualURL.isEmpty());
	}

	protected String encrypt(Object value) throws EncryptorException {
		return Encryptor.encrypt(_key, String.valueOf(value));
	}

	protected String namespace(String parameterName, String portletId) {
		return PortalUtil.getPortletNamespace(portletId) + parameterName;
	}

	protected void preparePortletURL(
			String portletId, PortletURLImpl portletURL,
			List<String[]> expectedURLParts, boolean encrypt, boolean escapeXML)
		throws Exception {

		addExpectedURLPart("?", expectedURLParts);

		portletURL.addProperty("key", "value0");
		portletURL.setAnchor(true);

		if (_portletUrlAnchorEnableField.getBoolean(null)) {
			addExpectedURLPart("#p_" + portletId, expectedURLParts);

			if (escapeXML) {
				expectedURLParts.add(AMPERSAND_ESCAPED);
			}
			else {
				expectedURLParts.add(AMPERSAND);
			}
		}

		if (portletURL.getLifecycle().equals(PortletRequest.RESOURCE_PHASE)) {
			portletURL.setCacheability(ResourceURL.FULL);
			addExpectedURLPart(
				"p_p_cacheability", "cacheLevelFull", encrypt, escapeXML,
				expectedURLParts);
		}

		portletURL.setControlPanelCategory(PortletCategoryKeys.SITES);
		addExpectedURLPart(
			"controlPanelCategory", "sites", encrypt, escapeXML,
			expectedURLParts);

		portletURL.setDoAsGroupId(10000);
		addExpectedURLPart(
			"doAsGroupId", "10000", encrypt, escapeXML, expectedURLParts);

		portletURL.setDoAsUserId(10000);
		addExpectedURLPart(
			"doAsUserId", "10000", true, escapeXML, expectedURLParts);

		portletURL.setDoAsUserLanguageId("en");
		addExpectedURLPart(
			"doAsUserLanguageId", "en", encrypt, escapeXML, expectedURLParts);

		portletURL.setEncrypt(encrypt);

		if (encrypt) {
			addExpectedURLPart("shuo", "1", false, escapeXML, expectedURLParts);
		}

		portletURL.setEscapeXml(escapeXML);

		if (portletURL.getLifecycle().equals(PortletRequest.ACTION_PHASE)) {
			addExpectedURLPart(
				"p_p_lifecycle", "1", encrypt, escapeXML, expectedURLParts);
		}

		if (portletURL.getLifecycle().equals(PortletRequest.RENDER_PHASE)) {
			addExpectedURLPart(
				"p_p_lifecycle", "0", encrypt, escapeXML, expectedURLParts);
		}

		if (portletURL.getLifecycle().equals(PortletRequest.RESOURCE_PHASE)) {
			addExpectedURLPart(
				"p_p_lifecycle", "2", encrypt, escapeXML, expectedURLParts);
		}

		portletURL.setParameter("a", "a");
		addExpectedURLPart(
			"a", "a", encrypt, escapeXML, portletId, expectedURLParts);

		portletURL.setParameter("b", new String[]{"b1", "b2"});
		addExpectedURLPart(
			"b", new String[]{"b1", "b2"}, encrypt, escapeXML, portletId,
			expectedURLParts);

		portletURL.setParameter("c", "c1");
		portletURL.setParameter("c", "c2", true);
		addExpectedURLPart(
			"c", new String[]{"c1", "c2"}, encrypt, escapeXML, portletId,
			expectedURLParts);

		portletURL.setParameter("d", "d1");
		portletURL.setParameter("d", "d2", false);
		addExpectedURLPart(
			"d", "d2", encrypt, escapeXML, portletId, expectedURLParts);

		portletURL.setParameter("e", "e0");
		portletURL.setParameter("e", new String[]{"e1", "e2"}, true);
		addExpectedURLPart(
			"e", new String[]{"e0", "e1", "e2"}, encrypt, escapeXML, portletId,
			expectedURLParts);

		portletURL.setParameter("f", "f0");
		portletURL.setParameter("f", new String[]{"f1", "f2"}, false);
		addExpectedURLPart(
			"f", new String[]{"f1", "f2"}, encrypt, escapeXML, portletId,
			expectedURLParts);

		portletURL.setParameter(
			"invalid & \u0000 name", " invalid \r\n\t \u0000 < > '\" value");
		addExpectedURLPart(
			"invalid & \u0000 name", " invalid \r\n\t \u0000 < > '\" value",
			encrypt, escapeXML, portletId, expectedURLParts);

		// PRP

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

		for (PublicRenderParameter publicRenderParameter :
				portlet.getPublicRenderParameters()) {

			QName tagPRPQName = publicRenderParameter.getQName();

			String tagPRPName = PortletQNameUtil.getPublicRenderParameterName(
				tagPRPQName);

			portletURL.setParameter(publicRenderParameter.getIdentifier(), "0");

			addExpectedURLPart(
				tagPRPName, "0", encrypt, escapeXML, expectedURLParts);

			portletURL.removePublicRenderParameter(
				publicRenderParameter.getIdentifier());

			String tagPRPNameRemove =
				PortletQNameUtil.getRemovePublicRenderParameterName(
					tagPRPQName);

			addExpectedURLPart(
				tagPRPNameRemove, "1", encrypt, escapeXML, expectedURLParts);
		}

		portletURL.addParameterIncludedInPath("inPath");
		portletURL.setParameter("inPath", "0");

		portletURL.setPlid(_targetLayout.getPlid());
		addExpectedURLPart("domain2.net/destination-layout", expectedURLParts);

		portletURL.setPortletId(portletId);
		addExpectedURLPart(
			"p_p_id", portletId, encrypt, escapeXML, expectedURLParts);

		portletURL.setPortletMode(PortletMode.EDIT);
		addExpectedURLPart(
			"p_p_mode", "edit", encrypt, escapeXML, expectedURLParts);

		portletURL.setProperty("key", "value");

		portletURL.setRefererGroupId(_sourceLayout.getGroupId());
		addExpectedURLPart(
			"refererGroupId", String.valueOf(_sourceLayout.getGroupId()),
			encrypt, escapeXML, expectedURLParts);

		portletURL.setRefererPlid(_sourceLayout.getPlid());
		addExpectedURLPart(
			"refererPlid", String.valueOf(_sourceLayout.getPlid()), encrypt,
			escapeXML, expectedURLParts);

		if (portletURL.getLifecycle().equals(PortletRequest.RESOURCE_PHASE)) {
			portletURL.setResourceID("resourceID");
			addExpectedURLPart(
				"p_p_resource_id", "resourceID", encrypt, escapeXML,
				expectedURLParts);
		}

		portletURL.setSecure(true);
		addExpectedURLPart("https://", expectedURLParts);

		portletURL.setWindowState(LiferayWindowState.NORMAL);
		addExpectedURLPart(
			"p_p_state", "normal", encrypt, escapeXML, expectedURLParts);

		portletURL.setWindowStateRestoreCurrentView(true);
		addExpectedURLPart(
			"p_p_state_rcv", "1", encrypt, escapeXML, expectedURLParts);

		ThemeDisplay themeDisplay = (ThemeDisplay) _request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setColumnId("column-1");
		addExpectedURLPart(
			"p_p_col_id", "column-1", encrypt, escapeXML, expectedURLParts);

		portletDisplay.setColumnPos(1);
		addExpectedURLPart(
			"p_p_col_pos", "1", encrypt, escapeXML, expectedURLParts);

		portletDisplay.setColumnCount(2);
		addExpectedURLPart(
			"p_p_col_count", "2", encrypt, escapeXML, expectedURLParts);

		boolean isPortletUrlAppendParameters =
			_portletUrlAppendParametersField.getBoolean(null);

		Set<String> autopropagatedParameters =
			portlet.getAutopropagatedParameters();

		int counter = 0;

		for (String autopropagatedParameter : autopropagatedParameters) {
			String value = _request.getParameter(autopropagatedParameter);

			if (value == null) {
				continue;
			}

			if ((counter++ % 2) == 1) {
				addExpectedURLPart(
					autopropagatedParameter, value, encrypt, escapeXML,
					portletId, expectedURLParts);
			}
			else {
				portletURL.setParameter(autopropagatedParameter, "1");

				if (isPortletUrlAppendParameters) {
					addExpectedURLPart(
						autopropagatedParameter, new String[]{value, "1"},
						encrypt, escapeXML, portletId, expectedURLParts);
				}
				else {
					addExpectedURLPart(
						autopropagatedParameter, "1", encrypt, escapeXML,
						portletId, expectedURLParts);
				}
			}
		}

		// remove last ampersand

		if (escapeXML) {
			expectedURLParts.remove(AMPERSAND_ESCAPED);
		}
		else {
			expectedURLParts.remove(AMPERSAND);
		}
	}

	protected void testActionURL(boolean encrypt, boolean escapeXML)
		throws Exception {

		List<String[]> expectedURLParts = new ArrayList<String[]>();

		boolean isWSRP = ParamUtil.getBoolean(_request, _WSRP);

		if (!isWSRP) {
			addExpectedURLPart(
				"p_auth", AuthTokenUtil.getToken(_request), encrypt, escapeXML,
				null, expectedURLParts);
		}

		testAutopropagatedPortlet(
			PortletRequest.ACTION_PHASE, encrypt, escapeXML, expectedURLParts);

		testPRPPortlet(
			PortletRequest.ACTION_PHASE, encrypt, escapeXML, expectedURLParts);
	}

	protected void testAutopropagatedPortlet(
			String lifecycle, boolean encrypt, boolean escapeXML,
			List<String[]> expectedURLParts)
		throws Exception {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			PORTLET_DDM_ID);

		Set<String> autopropagatedParameters =
			portlet.getAutopropagatedParameters();

		Assert.assertTrue(
			"Portlet " + PORTLET_DDM_ID +
				" should have at least two autopropagated parameters!",
			autopropagatedParameters.size() > 1);

		for (String autopropagatedParameter : autopropagatedParameters) {
			_request.setParameter(autopropagatedParameter, "0");
		}

		testPortletURL(
			PORTLET_DDM_ID, lifecycle, encrypt, escapeXML, expectedURLParts);
	}

	protected void testPortletURL(
			String portletId, String lifecycle, boolean encrypt,
			boolean escapeXML, List<String[]> expectedURLParts)
		throws Exception {

		List<String[]> expectedURLPartsCopy = new ArrayList<String[]>(
			expectedURLParts);

		PortletURLImpl portletURL = new PortletURLImpl(
			_request, portletId, _targetLayout.getPlid(), lifecycle);

		boolean isWSRP = ParamUtil.getBoolean(_request, _WSRP);

		if (isWSRP) {
			testWSRPURL(
				portletId, portletURL, encrypt, escapeXML,
				expectedURLPartsCopy);
		}
		else {
			preparePortletURL(
				portletId, portletURL, expectedURLPartsCopy, encrypt,
				escapeXML);

			compareURLsPartiallyOrdered(
				expectedURLPartsCopy, portletURL.generateToString());
		}
	}

	protected void testPRPPortlet(
			String lifecycle, boolean encrypt, boolean escapeXML,
			List<String[]> expectedURLParts)
		throws Exception {

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			PORTLET_WIKI_ID);

		Set<PublicRenderParameter> publicRenderParameters =
			portlet.getPublicRenderParameters();

		Assert.assertTrue(
			"Portlet " + PORTLET_WIKI_ID +
				" should have at least one public render parameter!",
			publicRenderParameters.size() > 0);

		testPortletURL(
			PORTLET_WIKI_ID, lifecycle, encrypt, escapeXML, expectedURLParts);
	}

	protected void testRenderURL(boolean encrypt, boolean escapeXML)
		throws Exception {

		List<String[]> expectedURLParts = new ArrayList<String[]>();

		testAutopropagatedPortlet(
			PortletRequest.RENDER_PHASE, encrypt, escapeXML, expectedURLParts);

		testPRPPortlet(
			PortletRequest.RENDER_PHASE, encrypt, escapeXML, expectedURLParts);
	}

	protected void testResourceURL(boolean encrypt, boolean escapeXML)
		throws Exception {

		List<String[]> expectedURLParts = new ArrayList<String[]>();

		testAutopropagatedPortlet(
			PortletRequest.RESOURCE_PHASE, encrypt, escapeXML,
			expectedURLParts);

		testPRPPortlet(
			PortletRequest.RESOURCE_PHASE, encrypt, escapeXML,
			expectedURLParts);
	}

	protected void testWSRPURL(
			String portletId, PortletURLImpl portletURL, boolean encrypt,
			boolean escapeXML, List<String[]> expectedURLParts)
		throws Exception {

		List<String[]> expectedParameters = new ArrayList<String[]>();

		addExpectedURLPart("wsrp_rewrite?", expectedURLParts);

		portletURL.addProperty("key", "value0");
		portletURL.setAnchor(true);

		if (_portletUrlAnchorEnableField.getBoolean(null)) {
			addExpectedURLPart(
				"wsrp-fragmentID=#p_" + URLEncoder.encode(
					portletId, StringPool.UTF8), expectedURLParts);

			expectedURLParts.add(AMPERSAND);
		}

		if (portletURL.getLifecycle().equals(PortletRequest.RESOURCE_PHASE)) {
			portletURL.setCacheability(ResourceURL.FULL);
			addExpectedURLPart(
				"wsrp-resourceCacheability", "cacheLevelFull", false, false,
				expectedURLParts);
		}

		portletURL.setControlPanelCategory(PortletCategoryKeys.SITES);
		portletURL.setDoAsGroupId(10000);
		portletURL.setDoAsUserId(10000);
		portletURL.setDoAsUserLanguageId("en");
		portletURL.setEncrypt(encrypt);
		portletURL.setEscapeXml(escapeXML);

		if (portletURL.getLifecycle().equals(PortletRequest.ACTION_PHASE)) {
			addExpectedURLPart(
				"wsrp-urlType", "blockingAction", false, false,
				expectedURLParts);
		}

		if (portletURL.getLifecycle().equals(PortletRequest.RENDER_PHASE)) {
			addExpectedURLPart(
				"wsrp-urlType", "render", false, false, expectedURLParts);
		}

		if (portletURL.getLifecycle().equals(PortletRequest.RESOURCE_PHASE)) {
			addExpectedURLPart(
				"wsrp-urlType", "resource", false, false, expectedURLParts);
		}

		portletURL.setParameter("a", "a");
		addExpectedURLPart(
			"a", "a", false, false, portletId, expectedParameters);

		portletURL.setParameter("b", new String[]{"b1", "b2"});
		addExpectedURLPart(
			"b", new String[]{"b1", "b2"}, false, false, portletId,
			expectedParameters);

		portletURL.setParameter("c", "c1");
		portletURL.setParameter("c", "c2", true);
		addExpectedURLPart(
			"c", new String[]{"c1", "c2"}, false, false, portletId,
			expectedParameters);

		portletURL.setParameter("d", "d1");
		portletURL.setParameter("d", "d2", false);
		addExpectedURLPart(
			"d", "d2", false, false, portletId, expectedParameters);

		portletURL.setParameter("e", "e0");
		portletURL.setParameter("e", new String[]{"e1", "e2"}, true);
		addExpectedURLPart(
			"e", new String[]{"e0", "e1", "e2"}, false, false, portletId,
			expectedParameters);

		portletURL.setParameter("f", "f0");
		portletURL.setParameter("f", new String[]{"f1", "f2"}, false);
		addExpectedURLPart(
			"f", new String[]{"f1", "f2"}, false, false, portletId,
			expectedParameters);

		portletURL.setParameter(
			"invalid & \u0000 name", " invalid \r\n\t \u0000 < > '\" value");
		addExpectedURLPart(
			"invalid & \u0000 name", " invalid \r\n\t \u0000 < > '\" value",
			false, false, portletId, expectedParameters);

		// PRP

		Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

		for (PublicRenderParameter publicRenderParameter :
				portlet.getPublicRenderParameters()) {

			QName tagPRPQName = publicRenderParameter.getQName();

			String tagPRPName = PortletQNameUtil.getPublicRenderParameterName(
				tagPRPQName);

			portletURL.setParameter(publicRenderParameter.getIdentifier(), "0");

			addExpectedURLPart(
				tagPRPName, "0", false, false, expectedParameters);
		}

		portletURL.addParameterIncludedInPath("inPath");
		portletURL.setParameter("inPath", "0");

		portletURL.setPlid(_targetLayout.getPlid());

		portletURL.setPortletId(portletId);

		portletURL.setPortletMode(PortletMode.EDIT);
		addExpectedURLPart(
			"wsrp-mode", "wsrp:edit", false, false, expectedURLParts);

		portletURL.setProperty("key", "value");

		portletURL.setRefererGroupId(_sourceLayout.getGroupId());
		portletURL.setRefererPlid(_sourceLayout.getPlid());

		if (portletURL.getLifecycle().equals(PortletRequest.RESOURCE_PHASE)) {
			portletURL.setResourceID("resourceID");
			addExpectedURLPart(
				"wsrp-resourceID", "resourceID", false, false,
				expectedURLParts);
		}

		portletURL.setSecure(true);

		portletURL.setWindowState(LiferayWindowState.NORMAL);
		addExpectedURLPart(
			"wsrp-windowState", "wsrp:normal", false, false, expectedURLParts);

		portletURL.setWindowStateRestoreCurrentView(true);

		boolean isPortletUrlAppendParameters =
			_portletUrlAppendParametersField.getBoolean(null);

		Set<String> autopropagatedParameters =
			portlet.getAutopropagatedParameters();

		int counter = 0;

		for (String autopropagatedParameter : autopropagatedParameters) {
			String value = _request.getParameter(autopropagatedParameter);

			if (value == null) {
				continue;
			}

			if ((counter++ % 2) == 1) {
				addExpectedURLPart(
					autopropagatedParameter, value, false, false, portletId,
					expectedParameters);
			}
			else {
				portletURL.setParameter(autopropagatedParameter, "1");

				if (isPortletUrlAppendParameters) {
					addExpectedURLPart(
						autopropagatedParameter, new String[]{value, "1"},
						false, false, portletId, expectedParameters);
				}
				else {
					addExpectedURLPart(
						autopropagatedParameter, "1", false, false, portletId,
						expectedParameters);
				}
			}
		}

		String wsrpURL = portletURL.generateWSRPToString();

		int startPos = wsrpURL.indexOf(_WSRP_NAVIGATIONAL_STATE);

		Assert.assertTrue(
			"WSRP URL should contain wsrp-navigationalState: " + wsrpURL,
			startPos > -1);

		int endPos = wsrpURL.indexOf(_WSRP_REWRITE, startPos);

		Assert.assertTrue(
			"WSRP wsrp-navigationalState parameter should end with " +
				"/wsrp_rewrite: " + wsrpURL, endPos > startPos);

		String wsrpState = wsrpURL.substring(
			startPos + _WSRP_NAVIGATIONAL_STATE.length(), endPos);

		wsrpURL = wsrpURL.substring(0, startPos) + wsrpURL.substring(
			endPos + _WSRP_REWRITE.length());

		compareURLsPartiallyOrdered(expectedURLParts, wsrpURL);

		String actualParams = new String(
			Base64.decode(Base64.fromURLSafe(wsrpState)), StringPool.UTF8);

		compareURLsPartiallyOrdered(expectedParameters, actualParams);
	}

	private void addExpectedURLPart(
		String part, List<String[]> expectedURLParts) {

		expectedURLParts.add(new String[]{part});
	}

	private void addExpectedURLPart(
			String name, String value, boolean encrypt, boolean escapeXML,
			List<String[]> expectedURLParts)
		throws Exception {

		addExpectedURLPart(
			name, new String[]{value}, encrypt, escapeXML, null,
			expectedURLParts);
	}

	private void addExpectedURLPart(
			String name, String value, boolean encrypt, boolean escapeXML,
			String portletId, List<String[]> expectedURLParts)
		throws Exception {

		addExpectedURLPart(
			name, new String[]{value}, encrypt, escapeXML, portletId,
			expectedURLParts);
	}

	private void addExpectedURLPart(
			String name, String[] values, boolean encrypt, boolean escapeXML,
			String portletId, List<String[]> expectedURLParts)
		throws Exception {

		String[] expectedURLPart = new String[values.length];
		expectedURLParts.add(expectedURLPart);

		for (int i = 0; i < values.length; i++) {
			String value = values[i];

			if (encrypt) {
				value = encrypt(value);
			}

			if (portletId != null) {
				expectedURLPart[i] = URLEncoder.encode(
					namespace(name, portletId), StringPool.UTF8);
			}
			else {
				expectedURLPart[i] = URLEncoder.encode(name, StringPool.UTF8);
			}

			expectedURLPart[i] += StringPool.EQUAL;
			expectedURLPart[i] += URLEncoder.encode(value, StringPool.UTF8);

			if (escapeXML) {
				expectedURLPart[i] = HtmlUtil.escape(expectedURLPart[i]);
				expectedURLParts.add(AMPERSAND_ESCAPED);
			}
			else {
				expectedURLParts.add(AMPERSAND);
			}
		}
	}

	private static final String _WSRP = "wsrp";

	private static final String _WSRP_NAVIGATIONAL_STATE =
		"wsrp-navigationalState=";

	private static final String _WSRP_REWRITE = "/wsrp_rewrite";

	private static final String[] AMPERSAND =
		new String[]{StringPool.AMPERSAND};

	private static final String[] AMPERSAND_ESCAPED =
		new String[]{StringPool.AMPERSAND + "amp;"};

	private static final String COMPANY_KEY =
		"rO0ABXNyAB9qYXZheC5jcnlwdG8uc3BlYy5TZWNyZXRLZXlTcGVjW0cLZuIwYU0CAAJM" +
			"AAlhbGdvcml0aG10ABJMamF2YS9sYW5nL1N0cmluZztbAANrZXl0AAJbQnhwdAAD" +
			"QUVTdXIAAltCrPMX+AYIVOACAAB4cAAAABDAkqmOGU4a6Kq2rZgmKMJj";

	private static final String PORTLET_DDM_ID =
		PortletKeys.DYNAMIC_DATA_MAPPING;

	private static final String PORTLET_JOURNAL_ID = PortletKeys.JOURNAL;

	private static final String PORTLET_MY_ACCOUNT_ID = PortletKeys.MY_ACCOUNT;

	private static final String PORTLET_WIKI_ID = PortletKeys.WIKI;

	private Group _group;
	private Key _key;
	private Field _portletUrlAnchorEnableField;
	private Field _portletUrlAppendParametersField;
	private Field _portletUrlEscapeXMLField;
	private MockHttpServletRequest _request;
	private Layout _sourceLayout;
	private Layout _targetLayout;

}