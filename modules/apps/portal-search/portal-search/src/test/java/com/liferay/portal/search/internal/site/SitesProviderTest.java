/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.site;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.service.GroupService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.site.SitesProvider;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.portlet.ResourceRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Gustavo Lima
 */
public class SitesProviderTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_sitesProvider = new SitesProviderImpl();

		ReflectionTestUtil.setFieldValue(
			_sitesProvider, "_groupService", _groupService);

		_paramUtilMockedStatic = Mockito.mockStatic(ParamUtil.class);
	}

	@After
	public void tearDown() {
		_paramUtilMockedStatic.close();
	}

	@Test
	public void testGetSiteByExternalReferenceCodeJSONObject()
		throws Exception {

		_setUpThemeDisplay(_resourceRequest);

		Group childrenGroup = _createGroup(
			RandomTestUtil.randomString(), true, new ArrayList<>());

		_setUpParamUtil("externalReferenceCode", RandomTestUtil.randomString());

		Mockito.doReturn(
			childrenGroup
		).when(
			_groupService
		).fetchGroupByExternalReferenceCode(
			Mockito.anyString(), Mockito.anyLong()
		);

		_setUpGroups(
			true, 3,
			new ArrayList<Group>() {
				{
					add(childrenGroup);
				}
			});

		JSONObject siteJSONObject = _sitesProvider.getSiteJSONObject(
			_resourceRequest);

		Assert.assertEquals(
			siteJSONObject.toString(),
			childrenGroup.getDescriptiveName(_themeDisplay.getLocale()),
			siteJSONObject.get("descriptiveName"));
		Assert.assertEquals(
			siteJSONObject.toString(), childrenGroup.getExternalReferenceCode(),
			siteJSONObject.get("externalReferenceCode"));
		Assert.assertEquals(
			siteJSONObject.toString(),
			String.valueOf(childrenGroup.getGroupId()),
			siteJSONObject.get("groupId"));
		Assert.assertEquals(
			siteJSONObject.toString(),
			childrenGroup.getName(_themeDisplay.getLocale()),
			siteJSONObject.get("name"));
	}

	@Test
	public void testGetSitesJSONObjectFromNotActiveGroups() throws Exception {
		_setUpThemeDisplay(_resourceRequest);

		_setUpGroups(false, 3, new ArrayList<>());

		JSONObject sitesJSONObject = _sitesProvider.getSitesJSONObject(
			_resourceRequest);

		JSONArray sitesJSONArray = sitesJSONObject.getJSONArray("items");

		Assert.assertEquals(
			sitesJSONArray.toString(), 0, sitesJSONArray.length());
	}

	@Test
	public void testGetSitesJSONObjectWithChildrenGroups() throws Exception {
		_setUpThemeDisplay(_resourceRequest);
		_setUpPages(_resourceRequest, 10, 1);

		Group childrenGroup = _createGroup(
			RandomTestUtil.randomString(), true, new ArrayList<>());

		_setUpGroups(
			true, 1,
			new ArrayList<Group>() {
				{
					add(childrenGroup);
				}
			});

		JSONObject sitesJSONObject = _sitesProvider.getSitesJSONObject(
			_resourceRequest);

		JSONArray sitesJSONArray = sitesJSONObject.getJSONArray("items");

		Assert.assertEquals(
			sitesJSONArray.toString(), 2, sitesJSONArray.length());
	}

	@Test
	public void testGetSitesJSONObjectWithSameCompanyGroupId()
		throws Exception {

		_setUpThemeDisplay(_resourceRequest);
		_setUpPages(_resourceRequest, 10, 1);

		_setUpGroups(true, 3, new ArrayList<>());

		JSONObject sitesJSONObject = _sitesProvider.getSitesJSONObject(
			_resourceRequest);

		JSONArray sitesJSONArray = sitesJSONObject.getJSONArray("items");

		Assert.assertEquals(
			sitesJSONArray.toString(), 3, sitesJSONArray.length());
	}

	@Test
	public void testSitesJSONObjectPaginationConditions() throws Exception {
		_setUpThemeDisplay(_resourceRequest);
		_setUpPages(_resourceRequest, 5, 3);
		_setUpGroups(true, 8, new ArrayList<>());

		JSONObject sitesJSONObject = _sitesProvider.getSitesJSONObject(
			_resourceRequest);

		Assert.assertEquals(2, sitesJSONObject.getInt("lastPage"));

		JSONArray sitesJSONArray = sitesJSONObject.getJSONArray("items");

		Assert.assertEquals(
			sitesJSONArray.toString(), 3, sitesJSONArray.length());
	}

	@Test
	public void testSitesJSONObjectPaginationOnePage() throws Exception {
		_setUpThemeDisplay(_resourceRequest);
		_setUpPages(_resourceRequest, 10, 1);
		_setUpGroups(true, 8, new ArrayList<>());

		JSONObject sitesJSONObject = _sitesProvider.getSitesJSONObject(
			_resourceRequest);

		Assert.assertEquals(1, sitesJSONObject.getInt("lastPage"));

		JSONArray sitesJSONArray = sitesJSONObject.getJSONArray("items");

		Assert.assertEquals(
			sitesJSONArray.toString(), 8, sitesJSONArray.length());
	}

	@Test
	public void testSitesJSONObjectPaginationTwoPages() throws Exception {
		_setUpThemeDisplay(_resourceRequest);
		_setUpPages(_resourceRequest, 5, 2);
		_setUpGroups(true, 10, new ArrayList<>());

		JSONObject sitesJSONObject = _sitesProvider.getSitesJSONObject(
			_resourceRequest);

		Assert.assertEquals(2, sitesJSONObject.getInt("lastPage"));

		JSONArray sitesJSONArray = sitesJSONObject.getJSONArray("items");

		Assert.assertEquals(
			sitesJSONArray.toString(), 5, sitesJSONArray.length());
	}

	private Group _createGroup(
			String descriptiveName, boolean active, List<Group> childrenGroups)
		throws Exception {

		Group group = Mockito.mock(Group.class);

		Mockito.when(
			group.isActive()
		).thenReturn(
			active
		);

		Mockito.when(
			group.getChildren(true)
		).thenReturn(
			childrenGroups
		);

		Mockito.when(
			group.getGroupId()
		).thenReturn(
			_COMPANY_GROUP_ID
		);

		Mockito.when(
			group.getDescriptiveName(Mockito.any())
		).thenReturn(
			descriptiveName
		);

		Mockito.when(
			group.getExternalReferenceCode()
		).thenReturn(
			RandomTestUtil.randomString()
		);

		Mockito.when(
			group.getGroupId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			group.getName(_themeDisplay.getLocale())
		).thenReturn(
			descriptiveName
		);

		return group;
	}

	private void _setUpGroups(
			boolean fromCompanyGroupId, int numberOfGroups,
			List<Group> childrenGroups)
		throws Exception {

		List<Group> groups = new ArrayList<>();

		for (int i = 0; i < numberOfGroups; i++) {
			groups.add(
				_createGroup("group:" + i, fromCompanyGroupId, childrenGroups));
		}

		Mockito.doReturn(
			groups
		).when(
			_groupService
		).getGroups(
			_COMPANY_ID, GroupConstants.DEFAULT_PARENT_GROUP_ID, true
		);
	}

	private void _setUpPages(
		ResourceRequest resourceRequest, int pageSize, int page) {

		_paramUtilMockedStatic.when(
			() -> ParamUtil.getInteger(resourceRequest, "pageSize")
		).thenReturn(
			pageSize
		);

		_paramUtilMockedStatic.when(
			() -> ParamUtil.getInteger(resourceRequest, "page")
		).thenReturn(
			page
		);
	}

	private void _setUpParamUtil(String key, String value) {
		_paramUtilMockedStatic.when(
			() -> ParamUtil.getString(_resourceRequest, key)
		).thenReturn(
			value
		);
	}

	private void _setUpThemeDisplay(ResourceRequest resourceRequest) {
		Mockito.when(
			resourceRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).thenReturn(
			_themeDisplay
		);

		Mockito.when(
			_themeDisplay.getCompanyId()
		).thenReturn(
			_COMPANY_ID
		);

		Mockito.when(
			_themeDisplay.getCompanyGroupId()
		).thenReturn(
			_COMPANY_GROUP_ID
		);

		Mockito.when(
			_themeDisplay.getLocale()
		).thenReturn(
			Mockito.mock(Locale.class)
		);
	}

	private static final long _COMPANY_GROUP_ID = 1234L;

	private static final long _COMPANY_ID = 12345L;

	private final GroupService _groupService = Mockito.mock(GroupService.class);
	private MockedStatic<ParamUtil> _paramUtilMockedStatic;
	private final ResourceRequest _resourceRequest = Mockito.mock(
		ResourceRequest.class);
	private SitesProvider _sitesProvider;
	private final ThemeDisplay _themeDisplay = Mockito.mock(ThemeDisplay.class);

}