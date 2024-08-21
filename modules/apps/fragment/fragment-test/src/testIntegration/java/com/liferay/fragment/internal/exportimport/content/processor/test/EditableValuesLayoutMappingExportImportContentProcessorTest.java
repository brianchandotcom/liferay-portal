/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.exportimport.content.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.service.SegmentsExperienceLocalService;

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
public class EditableValuesLayoutMappingExportImportContentProcessorTest {

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
	@TestInfo("LPD-34189")
	public void testLinkedLayoutMapping() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_stagingGroup);

		FragmentEntryLink stagingFragmentEntryLink =
			_setUpFragmentEntryLinkWithLinkMappedToLayout(layout);

		_assertFragmentEntryLink(stagingFragmentEntryLink, layout);

		ContentLayoutTestUtil.publishLayout(_draftLayout, _layout);

		_publishLayouts();

		FragmentEntryLink liveFragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLinkByUuidAndGroupId(
				stagingFragmentEntryLink.getUuid(), _liveGroup.getGroupId());

		Layout liveLayout = _layoutLocalService.getLayoutByUuidAndGroupId(
			layout.getUuid(), _liveGroup.getGroupId(),
			layout.isPrivateLayout());

		_assertFragmentEntryLink(liveFragmentEntryLink, liveLayout);
	}

	@Test
	@TestInfo("LPD-34189")
	public void testLinkedLayoutMappingWithDeletedLayout() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_stagingGroup);

		FragmentEntryLink stagingFragmentEntryLink =
			_setUpFragmentEntryLinkWithLinkMappedToLayout(layout);

		_assertFragmentEntryLink(stagingFragmentEntryLink, layout);

		_layoutLocalService.deleteLayout(layout.getPlid());

		ContentLayoutTestUtil.publishLayout(_draftLayout, _layout);

		_publishLayouts();

		FragmentEntryLink liveFragmentEntryLink =
			_fragmentEntryLinkLocalService.getFragmentEntryLinkByUuidAndGroupId(
				stagingFragmentEntryLink.getUuid(), _liveGroup.getGroupId());

		JSONObject layoutJSONObject = _getLayoutJSONObject(
			liveFragmentEntryLink);

		Assert.assertNotEquals(
			layout.getGroupId(), layoutJSONObject.getLong("groupId"));
		Assert.assertNotEquals(
			layout.getLayoutId(), layoutJSONObject.getLong("layoutId"));
	}

	private void _assertFragmentEntryLink(
			FragmentEntryLink fragmentEntryLink, Layout layout)
		throws Exception {

		JSONObject layoutJSONObject = _getLayoutJSONObject(fragmentEntryLink);

		Assert.assertEquals(
			layout.getGroupId(), layoutJSONObject.getLong("groupId"));
		Assert.assertEquals(
			layout.getLayoutId(), layoutJSONObject.getLong("layoutId"));
	}

	private JSONObject _getLayoutJSONObject(FragmentEntryLink fragmentEntryLink)
		throws Exception {

		JSONObject configurationValuesJSONObject =
			_jsonFactory.createJSONObject(
				fragmentEntryLink.getEditableValues());

		JSONObject editableValuesJSONObject =
			configurationValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR);

		Assert.assertNotNull(editableValuesJSONObject);

		JSONObject textJSONObject = editableValuesJSONObject.getJSONObject(
			"element-text");

		Assert.assertNotNull(textJSONObject);

		JSONObject configJSONObject = textJSONObject.getJSONObject("config");

		Assert.assertNotNull(configJSONObject);

		return configJSONObject.getJSONObject("layout");
	}

	private void _publishLayouts() throws Exception {
		Map<String, String[]> parameterMap =
			ExportImportConfigurationParameterMapFactoryUtil.
				buildParameterMap();

		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA,
			new String[] {Boolean.TRUE.toString()});
		parameterMap.put(
			PortletDataHandlerKeys.PORTLET_DATA_ALL,
			new String[] {Boolean.TRUE.toString()});

		StagingUtil.publishLayouts(
			TestPropsValues.getUserId(), _stagingGroup.getGroupId(),
			_liveGroup.getGroupId(), false, parameterMap);
	}

	private FragmentEntryLink _setUpFragmentEntryLinkWithLinkMappedToLayout(
			Layout layout)
		throws Exception {

		long segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_draftLayout.getPlid());

		FragmentEntry fragmentEntry =
			_fragmentCollectionContributorRegistry.getFragmentEntry(
				"BASIC_COMPONENT-heading");

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				null, TestPropsValues.getUserId(), _draftLayout.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), segmentsExperienceId,
				_draftLayout.getPlid(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(),
				fragmentEntry.getConfiguration(),
				JSONUtil.put(
					FragmentEntryProcessorConstants.
						KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
					JSONUtil.put(
						"element-text",
						JSONUtil.put(
							"config",
							JSONUtil.put(
								"layout",
								JSONUtil.put(
									"groupId", layout.getGroupId()
								).put(
									"layoutId", layout.getLayoutId()
								).put(
									"layoutUuid", layout.getUuid()
								).put(
									"privateLayout", layout.isPrivateLayout()
								).put(
									"title", layout.getTitle()
								)
							).put(
								"mapperType", "link"
							)
						).put(
							"defaultValue", "Heading Example"
						))
				).toString(),
				StringPool.BLANK, 0, fragmentEntry.getFragmentEntryKey(),
				fragmentEntry.getType(),
				ServiceContextTestUtil.getServiceContext(
					_liveGroup.getGroupId(), TestPropsValues.getUserId()));

		ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			fragmentEntryLink, _draftLayout, null, 0, segmentsExperienceId);

		return fragmentEntryLink;
	}

	private Layout _draftLayout;

	@Inject
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private JSONFactory _jsonFactory;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

	@DeleteAfterTestRun
	private Group _liveGroup;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private Group _stagingGroup;

}