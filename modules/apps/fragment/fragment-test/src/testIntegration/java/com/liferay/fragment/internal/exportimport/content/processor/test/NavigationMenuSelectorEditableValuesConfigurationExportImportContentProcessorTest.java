/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.exportimport.content.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.renderer.FragmentRendererRegistry;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.site.navigation.constants.SiteNavigationConstants;
import com.liferay.site.navigation.model.SiteNavigationMenu;
import com.liferay.site.navigation.service.SiteNavigationMenuLocalService;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Javier Moral
 */
@RunWith(Arquillian.class)
public class
	NavigationMenuSelectorEditableValuesConfigurationExportImportContentProcessorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_liveGroup = GroupTestUtil.addGroup();

		GroupTestUtil.enableLocalStaging(
			_liveGroup, TestPropsValues.getUserId());

		_stagingGroup = _liveGroup.getStagingGroup();

		_layout = LayoutTestUtil.addTypeContentLayout(_stagingGroup);

		_draftLayout = _layout.fetchDraftLayout();
	}

	@Test
	@TestInfo("LPD-67100")
	public void testNavigationMenuSelectorEditableValues() throws Exception {
		SiteNavigationMenu siteNavigationMenu = _addSiteNavigationMenu(
			_stagingGroup.getGroupId());

		Layout layout = LayoutTestUtil.addTypeContentLayout(_stagingGroup);

		Layout draftLayout = layout.fetchDraftLayout();

		FragmentEntryLink draftFragmentEntryLink =
			ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
				JSONUtil.put(
					FragmentEntryProcessorConstants.
						KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
					JSONUtil.put(
						"displayStyle", "horizontal"
					).put(
						"hoveredItemColor", StringPool.BLANK
					).put(
						"selectedItemColor", StringPool.BLANK
					).put(
						"source",
						JSONUtil.put(
							"parentSiteNavigationMenuItemExternalReferenceCode",
							StringPool.BLANK
						).put(
							"privateLayout", Boolean.FALSE.toString()
						).put(
							"siteNavigationMenuExternalReferenceCode",
							siteNavigationMenu.getExternalReferenceCode()
						).put(
							"siteNavigationMenuId", RandomTestUtil.randomLong()
						).put(
							"siteNavigationMenuScopeExternalReferenceCode",
							StringPool.BLANK
						).put(
							"title", RandomTestUtil.randomString()
						).put(
							"type",
							"class com.liferay.site.navigation.item.selector." +
								"SiteNavigationMenuItemSelectorReturnType"
						)
					).put(
						"sublevels", "-1"
					)
				).toString(),
				_fragmentRendererRegistry.getFragmentRenderer(
					"com.liferay.fragment.renderer.menu.display.internal." +
						"MenuDisplayFragmentRenderer"),
				draftLayout, null, 0,
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(draftLayout.getPlid()));

		ContentLayoutTestUtil.publishLayout(draftLayout, layout);

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLink(
				layout.getGroupId(),
				draftFragmentEntryLink.getFragmentEntryLinkId(),
				layout.getPlid());

		_publishLayouts();

		FragmentEntryLink importedFragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLinkByUuidAndGroupId(
				fragmentEntryLink.getUuid(), _liveGroup.getGroupId());

		JSONObject editableValuesJSONObject =
			importedFragmentEntryLink.getEditableValuesJSONObject();

		JSONObject freeMarkerFragmentEntryProcessorJSONObject =
			editableValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

		JSONObject sourceJSONObject =
			freeMarkerFragmentEntryProcessorJSONObject.getJSONObject("source");

		Assert.assertEquals(
			siteNavigationMenu.getExternalReferenceCode(),
			sourceJSONObject.get("siteNavigationMenuExternalReferenceCode"));
		Assert.assertTrue(
			Validator.isNull(
				sourceJSONObject.get(
					"siteNavigationMenuScopeExternalReferenceCode")));

		_siteNavigationMenuLocalService.getSiteNavigationMenuByUuidAndGroupId(
			siteNavigationMenu.getUuid(), _liveGroup.getGroupId());
	}

	private SiteNavigationMenu _addSiteNavigationMenu(long groupId)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return _siteNavigationMenuLocalService.addSiteNavigationMenu(
			null, TestPropsValues.getUserId(), groupId,
			RandomTestUtil.randomString(), SiteNavigationConstants.TYPE_DEFAULT,
			true, serviceContext);
	}

	private void _publishLayouts() throws Exception {
		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap();

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.FALSE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.FALSE.toString()});

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), _stagingGroup.getGroupId(),
			_liveGroup.getGroupId(), false, parameterMap);
	}

	private Layout _draftLayout;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentRendererRegistry _fragmentRendererRegistry;

	@Inject
	private GroupLocalService _groupLocalService;

	private Layout _layout;

	@DeleteAfterTestRun
	private Group _liveGroup;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Inject
	private SiteNavigationMenuLocalService _siteNavigationMenuLocalService;

	private Group _stagingGroup;

}