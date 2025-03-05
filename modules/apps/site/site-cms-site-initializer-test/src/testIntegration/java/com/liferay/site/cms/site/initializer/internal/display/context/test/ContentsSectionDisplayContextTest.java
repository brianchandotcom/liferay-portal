/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.renderer.FragmentRenderer;
import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectFolderLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Mikel Lorza
 */
@RunWith(Arquillian.class)
@Sync
public class ContentsSectionDisplayContextTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetAPIURL() throws Exception {
		String apiURL = _getAPIURL();

		Assert.assertTrue(apiURL.contains("emptySearch=true"));

		StringBundler sb = new StringBundler(3);

		sb.append("filter=objectDefinitionFolder in ('");
		sb.append(
			StringUtil.merge(
				_getObjectDefinitionFolderExternalReferenceCodes(), "','"));
		sb.append("')");

		Assert.assertTrue(apiURL.contains(sb.toString()));
	}

	@Test
	public void testGetFDSActionDropdownItems() throws Exception {
		List<FDSActionDropdownItem> fdsActionDropdownItems =
			_getFDSActionDropdownItems();

		Assert.assertEquals(
			fdsActionDropdownItems.toString(), 1,
			fdsActionDropdownItems.size());

		FDSActionDropdownItem fdsActionDropdownItem =
			fdsActionDropdownItems.get(0);

		Assert.assertNotNull(fdsActionDropdownItem);

		Map<String, String> data =
			(Map<String, String>)fdsActionDropdownItem.get("data");

		Assert.assertNotNull("permissions", data.get("id"));
		Assert.assertNotNull("get", data.get("method"));

		Assert.assertNotNull(fdsActionDropdownItem.get("href"));
		Assert.assertEquals(
			"password-policies", fdsActionDropdownItem.get("icon"));
		Assert.assertEquals("permissions", fdsActionDropdownItem.get("label"));
		Assert.assertEquals(
			"modal-permissions", fdsActionDropdownItem.get("target"));
		Assert.assertEquals("item", fdsActionDropdownItem.get("type"));
	}

	private String _getAPIURL() throws Exception {
		return ReflectionTestUtil.invoke(
			_getContentsSectionDisplayContext(_getMockHttpServletRequest()),
			"getAPIURL", new Class<?>[0]);
	}

	private Object _getContentsSectionDisplayContext(
			HttpServletRequest httpServletRequest)
		throws Exception {

		_fragmentRenderer.render(
			null, httpServletRequest, new MockHttpServletResponse());

		Object contentsSectionDisplayContext = httpServletRequest.getAttribute(
			"com.liferay.site.cms.site.initializer.internal.display.context." +
				"ContentsSectionDisplayContext");

		Assert.assertNotNull(contentsSectionDisplayContext);

		return contentsSectionDisplayContext;
	}

	private List<FDSActionDropdownItem> _getFDSActionDropdownItems()
		throws Exception {

		return ReflectionTestUtil.invoke(
			_getContentsSectionDisplayContext(_getMockHttpServletRequest()),
			"getFDSActionDropdownItems", new Class<?>[0]);
	}

	private HttpServletRequest _getMockHttpServletRequest() throws Exception {
		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay(httpServletRequest));

		return httpServletRequest;
	}

	private String[] _getObjectDefinitionFolderExternalReferenceCodes()
		throws Exception {

		return ReflectionTestUtil.invoke(
			_getContentsSectionDisplayContext(_getMockHttpServletRequest()),
			"getObjectDefinitionFolderExternalReferenceCodes", new Class<?>[0]);
	}

	private ThemeDisplay _getThemeDisplay(HttpServletRequest httpServletRequest)
		throws Exception {

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));
		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setRequest(httpServletRequest);
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setURLCurrent("http://localhost:8080/currentURL");
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "component.name=com.liferay.site.cms.site.initializer.internal.fragment.renderer.ContentsSectionFragmentRenderer"
	)
	private FragmentRenderer _fragmentRenderer;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private ObjectFolderLocalService _objectFolderLocalService;

}