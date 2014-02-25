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

import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.QName;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.security.auth.AuthTokenUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletCategoryKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.util.Encryptor;
import com.liferay.util.EncryptorException;

import java.security.Key;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceURL;

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
		_group = GroupTestUtil.addGroup("TestGroup");

		LayoutSetLocalServiceUtil.updateVirtualHost(
			_group.getGroupId(), false, "domain1.net");

		LayoutSetLocalServiceUtil.updateVirtualHost(
			_group.getGroupId(), true, "domain2.net");

		_sourceLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), "Source Layout", false);

		_targetLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), "Destination Layout", true);

		addPortlet(_targetLayout, PORTLET_MY_ACCOUNT_ID);
		addPortlet(_targetLayout, PORTLET_MESSAGE_BOARDS_ID);
		addPortlet(_sourceLayout, PORTLET_MESSAGE_BOARDS_ID);

		_request = new MockHttpServletRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

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
	}

	@After
	public void tearDown() throws Exception {
		LayoutLocalServiceUtil.deleteLayout(_sourceLayout);
		LayoutLocalServiceUtil.deleteLayout(_targetLayout);

		GroupLocalServiceUtil.deleteGroup(_group);
	}

	@Test
	public void testGenerateToString() throws Exception {
		PortletURLImpl portletURL =
			new PortletURLImpl(
				_request, PORTLET_MY_ACCOUNT_ID, _targetLayout.getPlid(),
				PortletRequest.RENDER_PHASE);

		String[] expectedParts = {
			"http://domain2.net/destination-layout", "p_p_id=2",
			"p_p_lifecycle=0", "?&"
		};

		compareURLs(expectedParts, portletURL.generateToString());
	}

	@Test
	public void testGenerateToStringActionComplex() throws Exception {
		PortletURLImpl portletURL =
			new PortletURLImpl(
				_request, PORTLET_MESSAGE_BOARDS_ID, _targetLayout.getPlid(),
				PortletRequest.ACTION_PHASE);

		portletURL.setWindowState(LiferayWindowState.NORMAL);
		portletURL.setWindowStateRestoreCurrentView(true);
		portletURL.setPortletMode(PortletMode.EDIT);
		portletURL.setResourceID("resourceId");

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setColumnId("column-1");
		portletDisplay.setColumnPos(1);
		portletDisplay.setColumnCount(2);

		portletURL.setDoAsGroupId(10000);
		portletURL.setDoAsUserId(10000);
		portletURL.setDoAsUserLanguageId("en");
		portletURL.setRefererGroupId(10000);
		portletURL.setRefererPlid(10000);
		portletURL.setControlPanelCategory("MY");

		// PRP

		portletURL.setParameter("tag", "0");
		portletURL.removePublicRenderParameter("tag");
		portletURL.addParameterIncludedInPath("inPath");
		portletURL.setParameter("inPath", "0");
		portletURL.setParameter("a", "0");
		portletURL.setParameter("b", new String[]{"0", "1"});
		portletURL.setParameter("&c&", "&c&");

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			PORTLET_MESSAGE_BOARDS_ID);

		PublicRenderParameter publicRenderParameter =
			portlet.getPublicRenderParameter("tag");

		QName tagPRPQName = publicRenderParameter.getQName();

		String tagPRPName = PortletQNameUtil.getPublicRenderParameterName(
			tagPRPQName);

		String tagPRPNameRemove =
			PortletQNameUtil.getRemovePublicRenderParameterName(tagPRPQName);

		String[] expectedParts = new String[] {
			"http://domain2.net/destination-layout",
			"p_auth=" + AuthTokenUtil.getToken(_request), "p_p_id=19",
			"p_p_lifecycle=1", "p_p_state=normal", "p_p_state_rcv=1",
			"p_p_mode=edit", "p_p_resource_id=resourceId",
			"p_p_col_id=column-1", "p_p_col_pos=1", "p_p_col_count=2",
			"doAsUserId=y4iMKl9Wv7skJFYEehXTKQ%3D%3D", "doAsUserLanguageId=en",
			"doAsGroupId=10000", "refererGroupId=10000", "refererPlid=10000",
			"controlPanelCategory=MY", tagPRPNameRemove + "=1",
			tagPRPName + "=0", "_19_a=0", "_19_b=0", "_19_b=1",
			"_19_%26c%26=%26c%26", "?&&&&&&&&&&&&&&&&&&&&&"
		};

		compareURLs(expectedParts, portletURL.generateToString());

		portletURL.setLifecycle(PortletRequest.RESOURCE_PHASE);
		portletURL.setEncrypt(true);

		expectedParts = new String[] {
			"http://domain2.net/destination-layout",
			"p_p_id=%2FUyxNdhH5RmqYqg7E%2BGgcw%3D%3D",
			"p_p_lifecycle=wDuoiLepIRWTCLSAoqQsgg%3D%3D",
			"p_p_state=cvvSzHIk8MphBugJuqp5wA%3D%3D",
			"p_p_state_rcv=rBN6S65rh0ezykVrbtJWFA%3D%3D",
			"p_p_mode=mQM8DVnZ1CzPzt7ayhjHNA%3D%3D",
			"p_p_resource_id=0rLz3TdFGtWIh9EHrM3AsQ%3D%3D",
			"p_p_cacheability=MR0nTlUuB6C0QYXfCbWLxA%3D%3D",
			"p_p_col_id=%2BrXc0Q4nbvrvkzEnQH%2FOSg%3D%3D",
			"p_p_col_pos=rBN6S65rh0ezykVrbtJWFA%3D%3D",
			"p_p_col_count=wDuoiLepIRWTCLSAoqQsgg%3D%3D",
			"doAsUserId=y4iMKl9Wv7skJFYEehXTKQ%3D%3D",
			"doAsUserLanguageId=T1bJvFsD1nyZTaFS3FZhfQ%3D%3D",
			"doAsGroupId=y4iMKl9Wv7skJFYEehXTKQ%3D%3D",
			"refererGroupId=y4iMKl9Wv7skJFYEehXTKQ%3D%3D",
			"refererPlid=y4iMKl9Wv7skJFYEehXTKQ%3D%3D",
			"controlPanelCategory=vVIAB6cxbmOxViGETzrwUg%3D%3D",
			tagPRPNameRemove + "=rBN6S65rh0ezykVrbtJWFA%3D%3D",
			tagPRPName + "=bXgYTje3zSVA%2FD%2BEg%2F%2FwEA%3D%3D",
			"_19_a=bXgYTje3zSVA%2FD%2BEg%2F%2FwEA%3D%3D",
			"_19_b=bXgYTje3zSVA%2FD%2BEg%2F%2FwEA%3D%3D",
			"_19_b=rBN6S65rh0ezykVrbtJWFA%3D%3D",
			"_19_%26c%26=42iZV7yhtYJCHCubzHoa1Q%3D%3D", "shuo=1",
			"?&&&&&&&&&&&&&&&&&&&&&&"
		};

		compareURLs(expectedParts, portletURL.generateToString());

		portletURL.setEscapeXml(true);

		expectedParts[expectedParts.length - 1] =
			"?&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;" +
				"&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;";

		compareURLs(expectedParts, portletURL.generateToString());
	}

	@Test
	public void testGenerateToStringResourceComplete() throws Exception {
		PortletURLImpl portletURL = new PortletURLImpl(
			_request, "0", 0, PortletRequest.RENDER_PHASE);

		portletURL.addProperty("key", "value0");
		portletURL.setAnchor(true);
		portletURL.setCacheability(ResourceURL.FULL);
		portletURL.setControlPanelCategory(PortletCategoryKeys.SITES);
		portletURL.setCopyCurrentRenderParameters(true);
		portletURL.setDoAsGroupId(10000);
		portletURL.setDoAsUserId(10000);
		portletURL.setDoAsUserLanguageId("en");
		portletURL.setLifecycle(PortletRequest.RESOURCE_PHASE);
		portletURL.setParameter("a", "a");
		portletURL.setParameter("b", new String[]{"b1", "b2"});
		portletURL.setParameter("c", "c1");
		portletURL.setParameter("c", "c2", true);
		portletURL.setParameter("d", "d1");
		portletURL.setParameter("d", "d2", false);
		portletURL.setParameter("e", "e0");
		portletURL.setParameter("e", new String[]{"e1", "e2"}, true);
		portletURL.setParameter("f", "f0");
		portletURL.setParameter("f", new String[]{"f1", "f2"}, false);

		// PRP

		portletURL.setParameter("tag", "0");
		portletURL.removePublicRenderParameter("tag");
		portletURL.addParameterIncludedInPath("inPath");
		portletURL.setParameter("inPath", "0");
		portletURL.setPlid(_targetLayout.getPlid());
		portletURL.setPortletId(PORTLET_MESSAGE_BOARDS_ID);
		portletURL.setPortletMode(PortletMode.EDIT);
		portletURL.setProperty("key", "value");
		portletURL.setRefererGroupId(_sourceLayout.getGroupId());
		portletURL.setRefererPlid(_sourceLayout.getPlid());
		portletURL.setResourceID("resourceID");
		portletURL.setSecure(true);
		portletURL.setWindowState(LiferayWindowState.EXCLUSIVE);
		portletURL.setWindowStateRestoreCurrentView(true);

		ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		portletDisplay.setColumnId("column-1");
		portletDisplay.setColumnPos(1);
		portletDisplay.setColumnCount(2);

		Portlet portlet = PortletLocalServiceUtil.getPortletById(
			PORTLET_MESSAGE_BOARDS_ID);

		PublicRenderParameter publicRenderParameter =
			portlet.getPublicRenderParameter("tag");

		QName tagPRPQName = publicRenderParameter.getQName();

		String tagPRPName = PortletQNameUtil.getPublicRenderParameterName(
			tagPRPQName);

		String tagPRPNameRemove =
			PortletQNameUtil.getRemovePublicRenderParameterName(tagPRPQName);

		String[] expectedParts = new String[] {
			"https://domain2.net/destination-layout",
			"p_p_id=" + PORTLET_MESSAGE_BOARDS_ID, "p_p_lifecycle=2",
			"p_p_state=exclusive", "p_p_state_rcv=1", "p_p_mode=edit",
			"p_p_resource_id=resourceID", "p_p_cacheability=cacheLevelFull",
			"p_p_col_id=column-1", "p_p_col_pos=1", "p_p_col_count=2",
			"doAsUserId=y4iMKl9Wv7skJFYEehXTKQ%3D%3D", "doAsUserLanguageId=en",
			"doAsGroupId=10000", "refererGroupId=" + _sourceLayout.getGroupId(),
			"refererPlid=" + _sourceLayout.getPlid(),
			"controlPanelCategory=sites", "_19_a=a", "_19_b=b1", "_19_b=b2",
			"_19_c=c1", "_19_c=c2", "_19_d=d2", "_19_e=e0", "_19_e=e1",
			"_19_e=e2", "_19_f=f1", "_19_f=f2", tagPRPNameRemove + "=1",
			tagPRPName + "=0", "?&&&&&&&&&&&&&&&&&&&&&&&&&&&&"
		};

		compareURLs(expectedParts, portletURL.generateToString());

		portletURL.setEncrypt(true);
		portletURL.setPlid(_sourceLayout.getPlid());

		Company company = PortalUtil.getCompany(_request);

		Key key = company.getKeyObj();

		expectedParts = new String[] {
			"https://domain1.net/source-layout",
			"p_p_id=%2FUyxNdhH5RmqYqg7E%2BGgcw%3D%3D",
			"p_p_lifecycle=wDuoiLepIRWTCLSAoqQsgg%3D%3D",
			"p_p_state=cIDxD03RNPUe5Aty0Fii%2BA%3D%3D",
			"p_p_state_rcv=rBN6S65rh0ezykVrbtJWFA%3D%3D",
			"p_p_mode=mQM8DVnZ1CzPzt7ayhjHNA%3D%3D",
			"p_p_resource_id=3A5KU4CgUgrPok9EpK9ZAQ%3D%3D",
			"p_p_cacheability=nIW%2BktGqPaW80hP71YVVRA%3D%3D",
			"p_p_col_id=%2BrXc0Q4nbvrvkzEnQH%2FOSg%3D%3D",
			"p_p_col_pos=rBN6S65rh0ezykVrbtJWFA%3D%3D",
			"p_p_col_count=wDuoiLepIRWTCLSAoqQsgg%3D%3D",
			"doAsUserId=y4iMKl9Wv7skJFYEehXTKQ%3D%3D",
			"doAsUserLanguageId=T1bJvFsD1nyZTaFS3FZhfQ%3D%3D",
			"doAsGroupId=y4iMKl9Wv7skJFYEehXTKQ%3D%3D",
			"refererGroupId=" + encrypt(key, _sourceLayout.getGroupId()),
			"refererPlid=" + encrypt(key, _sourceLayout.getPlid()),
			"controlPanelCategory=R50hL5BiH0AEiVzHCjzOXw%3D%3D",
			"_19_a=YjGjF2JxcNdBjkxIOcmQuA%3D%3D",
			"_19_b=pQ8oY8tR1xC7h7D7e8%2BFAQ%3D%3D",
			"_19_b=xwm%2BJos95yE9yI7hQuRfHg%3D%3D",
			"_19_c=SLbxUyovs1PW38A%2BHkAj4w%3D%3D",
			"_19_c=5GC37AtHRNc8NRAnq%2FEnFA%3D%3D",
			"_19_d=%2FntW9rF2S42IKkUPe1Ncew%3D%3D",
			"_19_e=Dg4wyBhlvEcwztuCCjmznA%3D%3D",
			"_19_e=JHm0703i5i%2FgsUHAWVhF3g%3D%3D",
			"_19_e=a6UwzDM6vGaV3kbtwID%2FoA%3D%3D",
			"_19_f=ycLfXbSojNIraaiXrRu0KQ%3D%3D",
			"_19_f=G6mmZgz%2B2Emi5dye%2FYDaNg%3D%3D", "shuo=1",
			tagPRPNameRemove + "=rBN6S65rh0ezykVrbtJWFA%3D%3D",
			tagPRPName + "=bXgYTje3zSVA%2FD%2BEg%2F%2FwEA%3D%3D",
			"?&&&&&&&&&&&&&&&&&&&&&&&&&&&&&"
		};

		compareURLs(expectedParts, portletURL.generateToString());

		portletURL.setEscapeXml(true);

		expectedParts[expectedParts.length - 1] =
			"?&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;" +
				"&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;" +
				"&amp;&amp;&amp;&amp;&amp;";

		compareURLs(expectedParts, portletURL.generateToString());
	}

	@Test
	public void testGenerateToStringWithAutopropagating() throws Exception {
		List<String> expectedURLParts = new ArrayList<String>();

		Portlet ddmPortlet = PortletLocalServiceUtil.getPortletById(
			PORTLET_DDM_ID);

		Set<String> autopropagatedParameters =
			ddmPortlet.getAutopropagatedParameters();

		StringBundler sb = new StringBundler(autopropagatedParameters.size());

		for (String autopropagatedParameter : autopropagatedParameters) {
			String namespacedAutoprogatedParameter =
				StringPool.UNDERLINE + PORTLET_DDM_ID + StringPool.UNDERLINE +
					autopropagatedParameter;

			_request.setParameter(autopropagatedParameter, "0");

			expectedURLParts.add(namespacedAutoprogatedParameter + "=0");

			sb.append(StringPool.AMPERSAND);
		}

		addPortlet(_targetLayout, PORTLET_DDM_ID);

		PortletURLImpl portletURL =
			new PortletURLImpl(
				_request, "0", _targetLayout.getPlid(),
				PortletRequest.RENDER_PHASE);

		portletURL.setPortletId(PORTLET_DDM_ID);

		expectedURLParts.add("http://domain2.net/destination-layout");
		expectedURLParts.add("p_p_id=" + PORTLET_DDM_ID);
		expectedURLParts.add("p_p_lifecycle=0");
		expectedURLParts.add("?&" + sb.toString());

		compareURLs(
			expectedURLParts.toArray(new String[expectedURLParts.size()]),
			portletURL.generateToString());
	}

	@Test
	public void testGenerateToStringWithPPAuthToken() throws Exception {
		String[] expectedParts = {
			"http://localhost/portal/layout", "p_l_id=1", "p_p_auth=" +
				AuthTokenUtil.getToken(_request, 1, PORTLET_MY_ACCOUNT_ID),
			"p_p_id=2", "p_p_lifecycle=0", "?&&&"
		};

		PortletURLImpl portletURL =
			new PortletURLImpl(
				_request, PORTLET_MY_ACCOUNT_ID, 1,
				PortletRequest.RENDER_PHASE);

		compareURLs(expectedParts, portletURL.generateToString());
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

	protected void compareURLs(String expectedParts[], String actualURL) {
		for (String expectedPart : expectedParts) {
			int pos = actualURL.indexOf(expectedPart);

			Assert.assertTrue(
				expectedPart + " is not present in " + actualURL, pos > -1);

			String prefix = actualURL.substring(0, pos);
			String postfix = actualURL.substring(pos + expectedPart.length());

			actualURL = prefix.concat(postfix);
		}

		Assert.assertTrue("Should be empty", actualURL.isEmpty());
	}

	protected String encrypt(Key key, long value) throws EncryptorException {
		return HttpUtil.encodeURL(
			Encryptor.encrypt(key, String.valueOf(value)));
	}

	private static final String COMPANY_KEY =
		"rO0ABXNyAB9qYXZheC5jcnlwdG8uc3BlYy5TZWNyZXRLZXlTcGVjW0cLZuIwYU0CAAJM" +
			"AAlhbGdvcml0aG10ABJMamF2YS9sYW5nL1N0cmluZztbAANrZXl0AAJbQnhwdAAD" +
			"QUVTdXIAAltCrPMX+AYIVOACAAB4cAAAABDAkqmOGU4a6Kq2rZgmKMJj";

	private static final String PORTLET_DDM_ID = "166";

	private static final String PORTLET_MESSAGE_BOARDS_ID = "19";

	private static final String PORTLET_MY_ACCOUNT_ID = "2";

	private Group _group;
	private MockHttpServletRequest _request;
	private Layout _sourceLayout;
	private Layout _targetLayout;

}