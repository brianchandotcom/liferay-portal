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

package com.liferay.layout.admin.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.item.selector.criteria.InfoListItemSelectorReturnType;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.layout.util.structure.RootLayoutStructureItem;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutSetLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.ThemeLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionRequest;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletActionResponse;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.util.List;

import javax.portlet.ActionRequest;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
@Sync
public class AddCollectionLayoutMVCActionCommandTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());

		_themeDisplay = _getThemeDisplay();

		_serviceContext = _getServiceContext(_group, _themeDisplay);

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		_assetListEntry = _addAssetListEntry(_serviceContext);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testAddChildCollectionLayout() throws Exception {
		Layout parentLayout = LayoutTestUtil.addTypePortletLayout(_group);

		Layout layout = ReflectionTestUtil.invoke(
			_mvcActionCommand, "_addCollectionLayout",
			new Class<?>[] {ActionRequest.class},
			_getMockLiferayPortletActionRequest(
				_assetListEntry, parentLayout.getParentLayoutId(),
				_themeDisplay));

		_validateCollectionLayout(
			layout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
	}

	@Test
	public void testAddCollectionLayout() {
		Layout layout = ReflectionTestUtil.invoke(
			_mvcActionCommand, "_addCollectionLayout",
			new Class<?>[] {ActionRequest.class},
			_getMockLiferayPortletActionRequest(
				_assetListEntry, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID,
				_themeDisplay));

		_validateCollectionLayout(
			layout, LayoutConstants.DEFAULT_PARENT_LAYOUT_ID);
	}

	private AssetListEntry _addAssetListEntry(ServiceContext serviceContext)
		throws Exception {

		return _assetListEntryLocalService.addManualAssetListEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			"Collection Title", new long[0], serviceContext);
	}

	private MockHttpServletRequest _getMockHttpServletRequest(
		ThemeDisplay themeDisplay) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
		AssetListEntry assetListEntry, long parentPlid,
		ThemeDisplay themeDisplay) {

		MockHttpServletRequest mockHttpServletRequest =
			_getMockHttpServletRequest(themeDisplay);

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest(mockHttpServletRequest);

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		mockLiferayPortletActionRequest.addParameter(
			"collectionPK",
			String.valueOf(assetListEntry.getAssetListEntryId()));
		mockLiferayPortletActionRequest.addParameter(
			"collectionType", InfoListItemSelectorReturnType.class.getName());
		mockLiferayPortletActionRequest.addParameter(
			"groupId", String.valueOf(_group.getGroupId()));
		mockLiferayPortletActionRequest.addParameter(
			"liveGroupId", String.valueOf(_group.getLiveGroupId()));
		mockLiferayPortletActionRequest.addParameter(
			"name", RandomTestUtil.randomString());
		mockLiferayPortletActionRequest.addParameter(
			"parentLayoutId", String.valueOf(parentPlid));
		mockLiferayPortletActionRequest.addParameter(
			"privateLayout", String.valueOf(Boolean.FALSE));
		mockLiferayPortletActionRequest.addParameter(
			"stagingGroupId", String.valueOf(_group.getGroupId()));

		return mockLiferayPortletActionRequest;
	}

	private ServiceContext _getServiceContext(
			Group group, ThemeDisplay themeDisplay)
		throws Exception {

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayPortletActionResponse());
		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, themeDisplay);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		serviceContext.setRequest(httpServletRequest);

		return serviceContext;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(LayoutTestUtil.addTypePortletLayout(_group));

		LayoutSet layoutSet = _layoutSetLocalService.getLayoutSet(
			_group.getGroupId(), false);

		themeDisplay.setLayoutSet(layoutSet);
		themeDisplay.setLookAndFeel(
			_themeLocalService.getTheme(
				_company.getCompanyId(), layoutSet.getThemeId()),
			null);

		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private void _validateCollectionLayout(Layout layout, long parentLayoutId) {
		_validateLayoutProperties(layout, parentLayoutId);
		_validateLayoutStructure(layout);
	}

	private void _validateLayoutProperties(Layout layout, long parentLayoutId) {
		Assert.assertEquals(parentLayoutId, layout.getParentLayoutId());
		Assert.assertEquals(LayoutConstants.TYPE_COLLECTION, layout.getType());
		Assert.assertEquals(
			String.valueOf(_assetListEntry.getAssetListEntryId()),
			layout.getTypeSettingsProperty("collectionPK"));
		Assert.assertEquals(
			InfoListItemSelectorReturnType.class.getName(),
			layout.getTypeSettingsProperty("collectionType"));
	}

	private void _validateLayoutStructure(Layout layout) {
		Layout draftLayout = layout.fetchDraftLayout();

		Assert.assertNotNull(draftLayout);

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					draftLayout.getGroupId(), draftLayout.getPlid());

		Assert.assertNotNull(layoutPageTemplateStructure);

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutPageTemplateStructure.getData(
				SegmentsExperienceConstants.ID_DEFAULT));

		Assert.assertNotNull(layoutStructure.getMainItemId());

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.getMainLayoutStructureItem();

		Assert.assertNotNull(rootLayoutStructureItem);
		Assert.assertTrue(
			rootLayoutStructureItem instanceof RootLayoutStructureItem);
		Assert.assertEquals(
			rootLayoutStructureItem.getItemType(),
			LayoutDataItemTypeConstants.TYPE_ROOT);

		List<String> rootItemIds = rootLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(rootItemIds.toString(), 1, rootItemIds.size());

		LayoutStructureItem containerLayoutStructureItem =
			layoutStructure.getLayoutStructureItem(rootItemIds.get(0));

		Assert.assertNotNull(containerLayoutStructureItem);
		Assert.assertTrue(
			containerLayoutStructureItem instanceof
				ContainerStyledLayoutStructureItem);
		Assert.assertEquals(
			containerLayoutStructureItem.getItemType(),
			LayoutDataItemTypeConstants.TYPE_CONTAINER);

		List<String> containerItemIds =
			containerLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(
			containerItemIds.toString(), 2, containerItemIds.size());

		FragmentStyledLayoutStructureItem fragmentStyledLayoutStructureItem =
			(FragmentStyledLayoutStructureItem)
				layoutStructure.getLayoutStructureItem(containerItemIds.get(0));

		Assert.assertNotNull(fragmentStyledLayoutStructureItem);

		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem =
				(CollectionStyledLayoutStructureItem)
					layoutStructure.getLayoutStructureItem(
						containerItemIds.get(1));

		Assert.assertNotNull(collectionStyledLayoutStructureItem);

		JSONObject collectionJSONObject =
			collectionStyledLayoutStructureItem.getCollectionJSONObject();

		Assert.assertNotNull(collectionJSONObject);

		Assert.assertEquals(
			collectionJSONObject.getInt("classNameId"),
			_portal.getClassNameId(AssetListEntry.class.getName()));
		Assert.assertEquals(
			collectionJSONObject.getLong("classPK"),
			_assetListEntry.getAssetListEntryId());
		Assert.assertEquals(
			collectionJSONObject.get("title"), _assetListEntry.getTitle());
		Assert.assertEquals(
			collectionJSONObject.get("type"),
			InfoListItemSelectorReturnType.class.getName());
	}

	private AssetListEntry _assetListEntry;

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private LayoutSetLocalService _layoutSetLocalService;

	@Inject(filter = "mvc.command.name=/layout_admin/add_collection_layout")
	private MVCActionCommand _mvcActionCommand;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;
	private ThemeDisplay _themeDisplay;

	@Inject
	private ThemeLocalService _themeLocalService;

}