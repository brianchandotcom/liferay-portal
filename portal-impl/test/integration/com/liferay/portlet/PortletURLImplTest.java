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
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.AuthTokenUtil;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.LayoutSetLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.test.TransactionalCallbackAwareExecutionTestListener;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletCategoryKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.util.Encryptor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.ResourceURL;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

import org.testng.Assert;

/**
 * @author Tomas Polesovsky
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		TransactionalCallbackAwareExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class PortletURLImplTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup("TestGroup");

		LayoutSetLocalServiceUtil.updateVirtualHost(
			_group.getGroupId(), false, "domain1.net");
		LayoutSetLocalServiceUtil.updateVirtualHost(
			_group.getGroupId(), true, "domain2.net");

		_srcLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), "Source Layout", false);
		_dstLayout = LayoutTestUtil.addLayout(
			_group.getGroupId(), "Destination Layout", true);

		addPortlet(_dstLayout, PORTLET_ID);
		addPortlet(_dstLayout, PORTLET_MESSAGE_BOARDS);
		addPortlet(_srcLayout, PORTLET_MESSAGE_BOARDS);
	}

	@After
	public void tearDown() throws Exception {
		GroupLocalServiceUtil.deleteGroup(_group);
	}

	@Test
	public void testGenerateToString() throws Exception {
		HttpServletRequest request = getHttpServletRequest();

		PortletURLImpl portletURL =
			new PortletURLImpl(
				request, PORTLET_ID, _dstLayout.getPlid(),
				PortletRequest.RENDER_PHASE);

		compareURLs(new String[] {
			"http://domain2.net/destination-layout", "p_p_id=2",
			"p_p_lifecycle=0", "?&"}, portletURL.generateToString());
	}

	@Test
	public void testGenerateToStringActionComplex() throws Exception {
		HttpServletRequest request = getHttpServletRequest();
		PortletURLImpl portletURL =
			new PortletURLImpl(
				request, PORTLET_MESSAGE_BOARDS, _dstLayout.getPlid(),
				PortletRequest.ACTION_PHASE);

		portletURL.setWindowState(LiferayWindowState.NORMAL);
		portletURL.setWindowStateRestoreCurrentView(true);
		portletURL.setPortletMode(PortletMode.EDIT);
		portletURL.setResourceID("resourceId");

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			com.liferay.portal.util.WebKeys.THEME_DISPLAY);

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

		String token = AuthTokenUtil.getToken(request);
		QName tagPRPQName =
			PortletLocalServiceUtil.getPortletById(PORTLET_MESSAGE_BOARDS).
				getPublicRenderParameter("tag").getQName();

		String tagPRPName = PortletQNameUtil.getPublicRenderParameterName(
			tagPRPQName);

		String tagPRPNameRemove =
			PortletQNameUtil.getRemovePublicRenderParameterName(tagPRPQName);

		String[] expected = new String[] {
			"http://domain2.net/destination-layout", "p_auth=" + token,
			"p_p_id=19", "p_p_lifecycle=1", "p_p_state=normal",
			"p_p_state_rcv=1", "p_p_mode=edit", "p_p_resource_id=resourceId",
			"p_p_col_id=column-1", "p_p_col_pos=1", "p_p_col_count=2",
			"doAsUserId=y4iMKl9Wv7skJFYEehXTKQ%3D%3D", "doAsUserLanguageId=en",
			"doAsGroupId=10000", "refererGroupId=10000", "refererPlid=10000",
			"controlPanelCategory=MY", tagPRPNameRemove + "=1",
			tagPRPName + "=0", "_19_a=0", "_19_b=0", "_19_b=1",
			"_19_%26c%26=%26c%26", "?&&&&&&&&&&&&&&&&&&&&&"
		};

		compareURLs(expected, portletURL.generateToString());

		portletURL.setLifecycle(PortletRequest.RESOURCE_PHASE);
		portletURL.setEncrypt(true);

		expected = new String[] {
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
		compareURLs(expected, portletURL.generateToString());

		portletURL.setEscapeXml(true);
		expected[expected.length - 1] =
			"?&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;" +
				"&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;";
		compareURLs(expected, portletURL.generateToString());
	}

	@Test
	public void testGenerateToStringResourceComplete() throws Exception {
		HttpServletRequest request = getHttpServletRequest();
		PortletURLImpl portletURL = new PortletURLImpl(
			request, "0", 0, PortletRequest.RENDER_PHASE);

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

		portletURL.setPlid(_dstLayout.getPlid());
		portletURL.setPortletId(PORTLET_MESSAGE_BOARDS);
		portletURL.setPortletMode(PortletMode.EDIT);
		portletURL.setProperty("key", "value");
		portletURL.setRefererGroupId(_srcLayout.getGroupId());
		portletURL.setRefererPlid(_srcLayout.getPlid());
		portletURL.setResourceID("resourceID");
		portletURL.setSecure(true);
		portletURL.setWindowState(LiferayWindowState.EXCLUSIVE);
		portletURL.setWindowStateRestoreCurrentView(true);

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			com.liferay.portal.util.WebKeys.THEME_DISPLAY);
		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();
		portletDisplay.setColumnId("column-1");
		portletDisplay.setColumnPos(1);
		portletDisplay.setColumnCount(2);

		QName tagPRPQName =
			PortletLocalServiceUtil.getPortletById(PORTLET_MESSAGE_BOARDS).
				getPublicRenderParameter("tag").getQName();

		String tagPRPName = PortletQNameUtil.getPublicRenderParameterName(
			tagPRPQName);

		String tagPRPNameRemove =
			PortletQNameUtil.getRemovePublicRenderParameterName(tagPRPQName);

		String[] expected = new String[] {
			"https://domain2.net/destination-layout",
			"p_p_id=" + PORTLET_MESSAGE_BOARDS, "p_p_lifecycle=2",
			"p_p_state=exclusive", "p_p_state_rcv=1", "p_p_mode=edit",
			"p_p_resource_id=resourceID", "p_p_cacheability=cacheLevelFull",
			"p_p_col_id=column-1", "p_p_col_pos=1", "p_p_col_count=2",
			"doAsUserId=y4iMKl9Wv7skJFYEehXTKQ%3D%3D", "doAsUserLanguageId=en",
			"doAsGroupId=10000", "refererGroupId=" + _srcLayout.getGroupId(),
			"refererPlid=" + _srcLayout.getPlid(), "controlPanelCategory=sites",
			"_19_a=a", "_19_b=b1", "_19_b=b2", "_19_c=c1", "_19_c=c2",
			"_19_d=d2", "_19_e=e0", "_19_e=e1", "_19_e=e2", "_19_f=f1",
			"_19_f=f2", tagPRPNameRemove + "=1", tagPRPName + "=0",
			"?&&&&&&&&&&&&&&&&&&&&&&&&&&&&"
		};

		compareURLs(expected, portletURL.generateToString());

		portletURL.setEncrypt(true);
		portletURL.setPlid(_srcLayout.getPlid());

		expected = new String[] {
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
			"refererGroupId=" + encrypt(request, _srcLayout.getGroupId()),
			"refererPlid=" + encrypt(request, _srcLayout.getPlid()),
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

		compareURLs(expected, portletURL.generateToString());

		portletURL.setEscapeXml(true);
		expected[expected.length - 1] =
			"?&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;" +
				"&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;&amp;" +
				"&amp;&amp;&amp;&amp;&amp;";

		compareURLs(expected, portletURL.generateToString());
	}

	@Test
	public void testGenerateToStringWithAutopropagating() throws Exception {
		MockHttpServletRequest request = getHttpServletRequest();

		List<String> expectedURLParts = new ArrayList<String>();

		Portlet ddmPortlet = PortletLocalServiceUtil.getPortletById(
			PORTLET_DDM);

		Set<String> autopropagatedParameters =
			ddmPortlet.getAutopropagatedParameters();

		StringBundler ampersands = new StringBundler(
			autopropagatedParameters.size());

		for (String autoParam : autopropagatedParameters) {
			String namespacedAutoParam =
				StringPool.UNDERLINE + PORTLET_DDM + StringPool.UNDERLINE +
					autoParam;

			request.setParameter(autoParam, "0");
			expectedURLParts.add(namespacedAutoParam + "=0");
			ampersands.append(StringPool.AMPERSAND);
		}

		addPortlet(_dstLayout, PORTLET_DDM);

		PortletURLImpl portletURL =
			new PortletURLImpl(
				request, "0", _dstLayout.getPlid(),
				PortletRequest.RENDER_PHASE);

		portletURL.setPortletId(PORTLET_DDM);

		expectedURLParts.add("http://domain2.net/destination-layout");
		expectedURLParts.add("p_p_id="+PORTLET_DDM);
		expectedURLParts.add("p_p_lifecycle=0");
		expectedURLParts.add("?&" + ampersands.toString());

		compareURLs(
			expectedURLParts.toArray(new String[0]),
			portletURL.generateToString());
	}

	@Test
	public void testGenerateToStringWithPPAuthToken() throws Exception {
		HttpServletRequest request = getHttpServletRequest();

		int plid = 1;
		String token = AuthTokenUtil.getToken(request, plid, PORTLET_ID);

		PortletURLImpl portletURL =
			new PortletURLImpl(
				request, PORTLET_ID, 1, PortletRequest.RENDER_PHASE);

		compareURLs(new String[] {
			"http://localhost/portal/layout", "p_l_id=1", "p_p_auth=" +
			token, "p_p_id=2", "p_p_lifecycle=0", "?&&&"},
			portletURL.generateToString());
	}

	protected void addPortlet(Layout layout, String portletId)
		throws Exception {

		LayoutTypePortlet layoutTypePortlet =
			(LayoutTypePortlet)layout.getLayoutType();

		if (!layoutTypePortlet.hasPortletId(portletId)) {
			layoutTypePortlet.addPortletId(0, portletId, "column-1", -1, false);

			LayoutLocalServiceUtil.updateLayout(
				layout.getGroupId(), layout.isPrivateLayout(),
				layout.getLayoutId(), layout.getTypeSettings());

			// add portlet preferences

			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, portletId);
		}
	}

	protected void compareURLs(String expectedParts[], String actualURL) {
		for (String expectedPart : expectedParts) {
			int pos = actualURL.indexOf(expectedPart);

			Assert.assertTrue(
				pos > -1, expectedPart + " is not present in " + actualURL);

			actualURL = actualURL.substring(0, pos) + actualURL.substring(
				pos + expectedPart.length());
		}

		Assert.assertEquals(actualURL, "", "Should be empty");
	}

	protected String encrypt(HttpServletRequest request, long value)
		throws Exception {

		return encrypt(request, String.valueOf(value));
	}

	protected String encrypt(HttpServletRequest request, String value)
		throws Exception {

		return HttpUtil.encodeURL(
			Encryptor.encrypt(
				PortalUtil.getCompany(request).getKeyObj(), value));
	}

	protected MockHttpServletRequest getHttpServletRequest() throws Exception {
		MockHttpServletRequest request = new MockHttpServletRequest();

		ThemeDisplay themeDisplay = getThemeDisplay();

		request.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		PortalUtil.getCompany(request).setKeyObj(null);
		PortalUtil.getCompany(request).setKey(COMPANY_KEY);

		return request;
	}

	protected ThemeDisplay getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setScopeGroupId(_group.getGroupId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(TestPropsValues.getUser());

		themeDisplay.setPermissionChecker(permissionChecker);

		themeDisplay.setLayout(_srcLayout);

		themeDisplay.setSecure(false);
		themeDisplay.setServerName("liferay.com");
		themeDisplay.setServerPort(80);

		return themeDisplay;
	}

	private static String COMPANY_KEY =
		"rO0ABXNyAB9qYXZheC5jcnlwdG8uc3BlYy5TZWNyZXRLZXlTcGVjW0cLZuIwYU0CAAJM" +
			"AAlhbGdvcml0aG10ABJMamF2YS9sYW5nL1N0cmluZztbAANrZXl0AAJbQnhwdAAD" +
			"QUVTdXIAAltCrPMX+AYIVOACAAB4cAAAABDAkqmOGU4a6Kq2rZgmKMJj";

	private static String PORTLET_DDM = "166";

	private static String PORTLET_ID = "2";

	private static String PORTLET_MESSAGE_BOARDS = "19";

	private Group _group;
	private Layout _dstLayout; private Layout _srcLayout;

}