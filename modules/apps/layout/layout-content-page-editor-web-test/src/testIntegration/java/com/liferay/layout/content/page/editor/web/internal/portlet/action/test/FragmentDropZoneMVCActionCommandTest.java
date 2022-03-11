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

package com.liferay.layout.content.page.editor.web.internal.portlet.action.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.ContainerStyledLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentDropZoneLayoutStructureItem;
import com.liferay.layout.util.structure.FragmentStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.layout.util.structure.LayoutStructureItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
@Sync
public class FragmentDropZoneMVCActionCommandTest {

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

		_fragmentEntry = _addFragmentEntry();

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			LayoutPageTemplateStructureLocalServiceUtil.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), _layout.getPlid());

		String data = layoutPageTemplateStructure.getData(
			SegmentsExperienceConstants.ID_DEFAULT);

		_layoutStructure = LayoutStructure.of(data);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setRequest(_getMockHttpServletRequest());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);
	}

	@Test
	public void testAddFragmentEntryLinkWithDropZone() throws Exception {
		MockLiferayPortletActionRequest actionRequest =
			_getMockLiferayPortletActionRequest(_group.getGroupId());

		actionRequest.addParameter(
			"fragmentEntryKey", _fragmentEntry.getFragmentEntryKey());
		actionRequest.addParameter(
			"itemType", LayoutDataItemTypeConstants.TYPE_FRAGMENT);
		actionRequest.addParameter(
			"parentItemId", _layoutStructure.getMainItemId());
		actionRequest.addParameter("position", "0");

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_addFragmentEntryLinkMVCActionCommand,
			"_processAddFragmentEntryLink",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			actionRequest, new MockLiferayPortletActionResponse());

		JSONObject layoutDataJSONObject = jsonObject.getJSONObject(
			"layoutData");

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutDataJSONObject.toString());

		JSONObject fragmentEntryLinkJSONObject = jsonObject.getJSONObject(
			"fragmentEntryLink");

		long fragmentEntryLinkId = fragmentEntryLinkJSONObject.getLong(
			"fragmentEntryLinkId");

		LayoutStructureItem fragmentFayoutStructureItem =
			layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
				fragmentEntryLinkId);

		List<String> childrenItemIds =
			fragmentFayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(
			childrenItemIds.toString(), 1, childrenItemIds.size());

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(childrenItemIds.get(0));

		Assert.assertTrue(
			layoutStructureItem instanceof FragmentDropZoneLayoutStructureItem);
	}

	@Test
	public void testDuplicateFragmentEntryLinkWithDropZone() throws Exception {
		MockLiferayPortletActionRequest actionRequest =
			_getMockLiferayPortletActionRequest(_group.getGroupId());

		actionRequest.addParameter(
			"fragmentEntryKey", _fragmentEntry.getFragmentEntryKey());
		actionRequest.addParameter(
			"itemType", LayoutDataItemTypeConstants.TYPE_FRAGMENT);
		actionRequest.addParameter(
			"parentItemId", _layoutStructure.getMainItemId());
		actionRequest.addParameter("position", "0");

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_addFragmentEntryLinkMVCActionCommand,
			"_processAddFragmentEntryLink",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			actionRequest, new MockLiferayPortletActionResponse());

		JSONObject layoutDataJSONObject = jsonObject.getJSONObject(
			"layoutData");

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutDataJSONObject.toString());

		JSONObject fragmentEntryLinkJSONObject = jsonObject.getJSONObject(
			"fragmentEntryLink");

		long fragmentEntryLinkId = fragmentEntryLinkJSONObject.getLong(
			"fragmentEntryLinkId");

		LayoutStructureItem fragmentLayoutStructureItem =
			layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
				fragmentEntryLinkId);

		List<String> childrenItemIds =
			fragmentLayoutStructureItem.getChildrenItemIds();

		actionRequest = _getMockLiferayPortletActionRequest(
			_group.getGroupId());

		actionRequest.addParameter(
			"itemType", LayoutDataItemTypeConstants.TYPE_CONTAINER);
		actionRequest.addParameter("parentItemId", childrenItemIds.get(0));
		actionRequest.addParameter("position", "0");

		ReflectionTestUtil.invoke(
			_addItemMVCActionCommand, "_addItemToLayoutData",
			new Class<?>[] {ActionRequest.class}, actionRequest);

		actionRequest = _getMockLiferayPortletActionRequest(
			_group.getGroupId());

		actionRequest.addParameter(
			"itemId", fragmentLayoutStructureItem.getItemId());

		jsonObject = ReflectionTestUtil.invoke(
			_duplicateItemMVCActionCommand,
			"_addDuplicateFragmentEntryLinkToLayoutDataJSONObject",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			actionRequest, new MockLiferayPortletActionResponse());

		layoutDataJSONObject = jsonObject.getJSONObject("layoutData");

		layoutStructure = LayoutStructure.of(layoutDataJSONObject.toString());

		List<LayoutStructureItem> layoutStructureItems =
			layoutStructure.getLayoutStructureItems();

		Assert.assertEquals(
			layoutStructureItems.toString(), 7, layoutStructureItems.size());

		LayoutStructureItem rootLayoutStructureItem =
			layoutStructure.getMainLayoutStructureItem();

		childrenItemIds = rootLayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(
			childrenItemIds.toString(), 2, childrenItemIds.size());

		for (String childrenItemId : childrenItemIds) {
			fragmentLayoutStructureItem =
				layoutStructure.getLayoutStructureItem(childrenItemId);

			Assert.assertTrue(
				fragmentLayoutStructureItem instanceof
					FragmentStyledLayoutStructureItem);

			List<String> fragmentChildrenItemIds =
				fragmentLayoutStructureItem.getChildrenItemIds();

			LayoutStructureItem fragmentDropZoneLayoutStructureItem =
				layoutStructure.getLayoutStructureItem(
					fragmentChildrenItemIds.get(0));

			Assert.assertTrue(
				fragmentDropZoneLayoutStructureItem instanceof
					FragmentDropZoneLayoutStructureItem);

			List<String> fragmentDropZoneChildrenItemIds =
				fragmentDropZoneLayoutStructureItem.getChildrenItemIds();

			LayoutStructureItem containerLayoutStructureItem =
				layoutStructure.getLayoutStructureItem(
					fragmentDropZoneChildrenItemIds.get(0));

			Assert.assertTrue(
				containerLayoutStructureItem instanceof
					ContainerStyledLayoutStructureItem);
		}
	}

	@Test
	public void testUpdateConfigurationValuesFragmentEntryLinkWithDropZone()
		throws Exception {

		MockLiferayPortletActionRequest actionRequest =
			_getMockLiferayPortletActionRequest(_group.getGroupId());

		actionRequest.addParameter(
			"fragmentEntryKey", _fragmentEntry.getFragmentEntryKey());
		actionRequest.addParameter(
			"itemType", LayoutDataItemTypeConstants.TYPE_FRAGMENT);
		actionRequest.addParameter(
			"parentItemId", _layoutStructure.getMainItemId());
		actionRequest.addParameter("position", "0");

		JSONObject jsonObject = ReflectionTestUtil.invoke(
			_addFragmentEntryLinkMVCActionCommand,
			"_processAddFragmentEntryLink",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			actionRequest, new MockLiferayPortletActionResponse());

		JSONObject layoutDataJSONObject = jsonObject.getJSONObject(
			"layoutData");

		LayoutStructure layoutStructure = LayoutStructure.of(
			layoutDataJSONObject.toString());

		JSONObject fragmentEntryLinkJSONObject = jsonObject.getJSONObject(
			"fragmentEntryLink");

		long fragmentEntryLinkId = fragmentEntryLinkJSONObject.getLong(
			"fragmentEntryLinkId");

		LayoutStructureItem fragmentFayoutStructureItem =
			layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
				fragmentEntryLinkId);

		List<String> childrenItemIds =
			fragmentFayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(
			childrenItemIds.toString(), 1, childrenItemIds.size());

		LayoutStructureItem layoutStructureItem =
			layoutStructure.getLayoutStructureItem(childrenItemIds.get(0));

		Assert.assertTrue(
			layoutStructureItem instanceof FragmentDropZoneLayoutStructureItem);

		actionRequest = _getMockLiferayPortletActionRequest(
			_group.getGroupId());

		actionRequest.addParameter(
			"fragmentEntryLinkId", String.valueOf(fragmentEntryLinkId));
		actionRequest.addParameter(
			"editableValues",
			_readFileToString("drop_zone_fragment_entry_editable_values.json"));

		jsonObject = ReflectionTestUtil.invoke(
			_updateConfigurationValuesMVCActionCommand,
			"_processUpdateConfigurationValues",
			new Class<?>[] {ActionRequest.class, ActionResponse.class},
			actionRequest, new MockLiferayPortletActionResponse());

		layoutDataJSONObject = jsonObject.getJSONObject("layoutData");

		layoutStructure = LayoutStructure.of(layoutDataJSONObject.toString());

		fragmentFayoutStructureItem =
			layoutStructure.getLayoutStructureItemByFragmentEntryLinkId(
				fragmentEntryLinkId);

		childrenItemIds = fragmentFayoutStructureItem.getChildrenItemIds();

		Assert.assertEquals(
			childrenItemIds.toString(), 2, childrenItemIds.size());

		layoutStructureItem = layoutStructure.getLayoutStructureItem(
			childrenItemIds.get(0));

		Assert.assertTrue(
			layoutStructureItem instanceof FragmentDropZoneLayoutStructureItem);

		layoutStructureItem = layoutStructure.getLayoutStructureItem(
			childrenItemIds.get(1));

		Assert.assertTrue(
			layoutStructureItem instanceof FragmentDropZoneLayoutStructureItem);
	}

	private FragmentEntry _addFragmentEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				TestPropsValues.getUserId(), _group.getGroupId(),
				StringUtil.randomString(), StringPool.BLANK, serviceContext);

		return _fragmentEntryLocalService.addFragmentEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			fragmentCollection.getFragmentCollectionId(),
			StringUtil.randomString(), StringUtil.randomString(),
			RandomTestUtil.randomString(),
			_readFileToString("drop_zone_fragment_entry.html"),
			RandomTestUtil.randomString(), false,
			_readFileToString("drop_zone_fragment_entry_configuration.json"),
			null, 0, FragmentConstants.TYPE_COMPONENT,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	private MockHttpServletRequest _getMockHttpServletRequest()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			JavaConstants.JAVAX_PORTLET_RESPONSE,
			new MockLiferayPortletActionResponse());
		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		return mockHttpServletRequest;
	}

	private MockLiferayPortletActionRequest _getMockLiferayPortletActionRequest(
			long groupId)
		throws Exception {

		MockLiferayPortletActionRequest mockLiferayPortletActionRequest =
			new MockLiferayPortletActionRequest();

		mockLiferayPortletActionRequest.setAttribute(
			WebKeys.THEME_DISPLAY, _getThemeDisplay());

		mockLiferayPortletActionRequest.addParameter(
			"groupId", String.valueOf(groupId));

		return mockLiferayPortletActionRequest;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);
		themeDisplay.setLayoutSet(_layout.getLayoutSet());
		themeDisplay.setLocale(LocaleUtil.US);

		LayoutSet layoutSet = _layout.getLayoutSet();

		Theme theme = _themeLocalService.getTheme(
			_company.getCompanyId(), layoutSet.getThemeId());

		themeDisplay.setLookAndFeel(theme, null);

		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setPlid(_layout.getPlid());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	private String _readFileToString(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(),
			"com/liferay/layout/content/page/editor/web/internal/portlet" +
				"/action/test/dependencies/" + fileName);
	}

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/add_fragment_entry_link"
	)
	private MVCActionCommand _addFragmentEntryLinkMVCActionCommand;

	@Inject(filter = "mvc.command.name=/layout_content_page_editor/add_item")
	private MVCActionCommand _addItemMVCActionCommand;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/duplicate_item"
	)
	private MVCActionCommand _duplicateItemMVCActionCommand;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	private FragmentEntry _fragmentEntry;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	private LayoutStructure _layoutStructure;

	@Inject
	private ThemeLocalService _themeLocalService;

	@Inject(
		filter = "mvc.command.name=/layout_content_page_editor/update_configuration_values"
	)
	private MVCActionCommand _updateConfigurationValuesMVCActionCommand;

}