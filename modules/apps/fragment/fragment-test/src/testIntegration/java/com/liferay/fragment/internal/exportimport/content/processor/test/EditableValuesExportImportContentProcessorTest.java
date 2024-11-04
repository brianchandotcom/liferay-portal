/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.internal.exportimport.content.processor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.configuration.ExportImportConfigurationParameterMapFactoryUtil;
import com.liferay.exportimport.kernel.lar.PortletDataHandlerKeys;
import com.liferay.exportimport.kernel.staging.StagingUtil;
import com.liferay.fragment.constants.FragmentConstants;
import com.liferay.fragment.contributor.FragmentCollectionContributorRegistry;
import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentCollection;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.service.FragmentCollectionLocalService;
import com.liferay.fragment.service.FragmentEntryLinkLocalService;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.TestInfo;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
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
public class EditableValuesExportImportContentProcessorTest {

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
	@TestInfo({"LPD-34189", "LPS-120198"})
	public void testLinkedLayoutMapping() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_stagingGroup);

		FragmentEntryLink fragmentEntryLink =
			_addLinkMappedToLayoutFragmentEntryLink(layout);

		_assertLayoutJSONObject(
			_getEditableFragmentEntryProcessorLayoutJSONObject(
				fragmentEntryLink),
			layout);

		_publishLayouts();

		_assertLayoutJSONObject(
			_getEditableFragmentEntryProcessorLayoutJSONObject(
				_fragmentEntryLinkLocalService.
					getFragmentEntryLinkByUuidAndGroupId(
						fragmentEntryLink.getUuid(), _liveGroup.getGroupId())),
			_layoutLocalService.getLayoutByUuidAndGroupId(
				layout.getUuid(), _liveGroup.getGroupId(),
				layout.isPrivateLayout()));
	}

	@Test
	@TestInfo("LPD-34189")
	public void testLinkedLayoutMappingWithDeletedLayout() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_stagingGroup);

		FragmentEntryLink fragmentEntryLink =
			_addLinkMappedToLayoutFragmentEntryLink(layout);

		_assertLayoutJSONObject(
			_getEditableFragmentEntryProcessorLayoutJSONObject(
				fragmentEntryLink),
			layout);

		_layoutLocalService.deleteLayout(layout.getPlid());

		_publishLayouts();

		_assertDeletedLayoutJSONObject(
			_getEditableFragmentEntryProcessorLayoutJSONObject(
				_fragmentEntryLinkLocalService.
					getFragmentEntryLinkByUuidAndGroupId(
						fragmentEntryLink.getUuid(), _liveGroup.getGroupId())));
	}

	@Test
	@TestInfo("LPD-34189")
	public void testURLEditableValues() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_stagingGroup);

		FragmentEntryLink fragmentEntryLink =
			_addUrlMappedToLayoutFragmentEntryLink(layout);

		_assertLayoutJSONObject(
			_getFreeMarkerFragmentEntryProcessorLayoutJSONObject(
				fragmentEntryLink),
			layout);

		_publishLayouts();

		_assertLayoutJSONObject(
			_getFreeMarkerFragmentEntryProcessorLayoutJSONObject(
				_fragmentEntryLinkLocalService.
					getFragmentEntryLinkByUuidAndGroupId(
						fragmentEntryLink.getUuid(), _liveGroup.getGroupId())),
			_layoutLocalService.getLayoutByUuidAndGroupId(
				layout.getUuid(), _liveGroup.getGroupId(),
				layout.isPrivateLayout()));
	}

	@Test
	@TestInfo("LPD-34189")
	public void testURLEditableValuesWithDeletedLayout() throws Exception {
		Layout layout = LayoutTestUtil.addTypeContentLayout(_stagingGroup);

		FragmentEntryLink fragmentEntryLink =
			_addUrlMappedToLayoutFragmentEntryLink(layout);

		_assertLayoutJSONObject(
			_getFreeMarkerFragmentEntryProcessorLayoutJSONObject(
				fragmentEntryLink),
			layout);

		_layoutLocalService.deleteLayout(layout.getPlid());

		_publishLayouts();

		_assertDeletedLayoutJSONObject(
			_getFreeMarkerFragmentEntryProcessorLayoutJSONObject(
				_fragmentEntryLinkLocalService.
					getFragmentEntryLinkByUuidAndGroupId(
						fragmentEntryLink.getUuid(), _liveGroup.getGroupId())));
	}

	private FragmentEntry _addFragmentEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_stagingGroup, TestPropsValues.getUserId());

		FragmentCollection fragmentCollection =
			_fragmentCollectionLocalService.addFragmentCollection(
				null, TestPropsValues.getUserId(),
				serviceContext.getScopeGroupId(), RandomTestUtil.randomString(),
				StringPool.BLANK, serviceContext);

		return _fragmentEntryLocalService.addFragmentEntry(
			null, TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			fragmentCollection.getFragmentCollectionId(), null,
			RandomTestUtil.randomString(), StringPool.BLANK,
			"<div class=\"fragment_1\"><a href=${configuration.myURL}>" +
				RandomTestUtil.randomString() + "</a></div>",
			StringPool.BLANK, false,
			JSONUtil.put(
				"fieldSets",
				JSONUtil.put(
					JSONUtil.put(
						"fields",
						JSONUtil.put(
							JSONUtil.put(
								"label", "My URL"
							).put(
								"name", "myURL"
							).put(
								"type", "url"
							))
					).put(
						"label", "Configuration"
					))
			).toString(),
			null, 0, false, FragmentConstants.TYPE_COMPONENT, null,
			WorkflowConstants.STATUS_APPROVED, serviceContext);
	}

	private FragmentEntryLink _addLinkMappedToLayoutFragmentEntryLink(
			Layout layout)
		throws Exception {

		FragmentEntry fragmentEntry =
			_fragmentCollectionContributorRegistry.getFragmentEntry(
				"BASIC_COMPONENT-heading");

		long segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_draftLayout.getPlid());

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
					_stagingGroup.getGroupId(), TestPropsValues.getUserId()));

		ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			fragmentEntryLink, _draftLayout, null, 0, segmentsExperienceId);

		ContentLayoutTestUtil.publishLayout(_draftLayout, _layout);

		return _fragmentEntryLinkLocalService.getFragmentEntryLink(
			_stagingGroup.getGroupId(),
			fragmentEntryLink.getFragmentEntryLinkId(), _layout.getPlid());
	}

	private FragmentEntryLink _addUrlMappedToLayoutFragmentEntryLink(
			Layout layout)
		throws Exception {

		FragmentEntry fragmentEntry = _addFragmentEntry();

		long segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_draftLayout.getPlid());

		FragmentEntryLink fragmentEntryLink =
			_fragmentEntryLinkLocalService.addFragmentEntryLink(
				null, TestPropsValues.getUserId(), _draftLayout.getGroupId(), 0,
				fragmentEntry.getFragmentEntryId(), segmentsExperienceId,
				_draftLayout.getPlid(), fragmentEntry.getCss(),
				fragmentEntry.getHtml(), fragmentEntry.getJs(),
				fragmentEntry.getConfiguration(),
				JSONUtil.put(
					FragmentEntryProcessorConstants.
						KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR,
					JSONUtil.put(
						"myURL",
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
							)))
				).toString(),
				StringPool.BLANK, 0, fragmentEntry.getFragmentEntryKey(),
				fragmentEntry.getType(),
				ServiceContextTestUtil.getServiceContext(
					_stagingGroup.getGroupId(), TestPropsValues.getUserId()));

		ContentLayoutTestUtil.addFragmentEntryLinkToLayout(
			fragmentEntryLink, _draftLayout, null, 0, segmentsExperienceId);

		ContentLayoutTestUtil.publishLayout(_draftLayout, _layout);

		return _fragmentEntryLinkLocalService.getFragmentEntryLink(
			_stagingGroup.getGroupId(),
			fragmentEntryLink.getFragmentEntryLinkId(), _layout.getPlid());
	}

	private void _assertDeletedLayoutJSONObject(JSONObject layoutJSONObject) {
		Assert.assertFalse(layoutJSONObject.has("groupId"));
		Assert.assertFalse(layoutJSONObject.has("layoutId"));
	}

	private void _assertLayoutJSONObject(JSONObject jsonObject, Layout layout) {
		Assert.assertEquals(layout.getGroupId(), jsonObject.getLong("groupId"));
		Assert.assertEquals(
			layout.getLayoutId(), jsonObject.getLong("layoutId"));
	}

	private JSONObject _getEditableFragmentEntryProcessorLayoutJSONObject(
			FragmentEntryLink fragmentEntryLink)
		throws Exception {

		JSONObject configurationValuesJSONObject =
			_jsonFactory.createJSONObject(
				fragmentEntryLink.getEditableValues());

		JSONObject editableValuesJSONObject =
			configurationValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR);

		JSONObject textJSONObject = editableValuesJSONObject.getJSONObject(
			"element-text");

		JSONObject configJSONObject = textJSONObject.getJSONObject("config");

		return configJSONObject.getJSONObject("layout");
	}

	private JSONObject _getFreeMarkerFragmentEntryProcessorLayoutJSONObject(
			FragmentEntryLink fragmentEntryLink)
		throws Exception {

		JSONObject configurationValuesJSONObject =
			_jsonFactory.createJSONObject(
				fragmentEntryLink.getEditableValues());

		JSONObject freeMarkerJSONObject =
			configurationValuesJSONObject.getJSONObject(
				FragmentEntryProcessorConstants.
					KEY_FREEMARKER_FRAGMENT_ENTRY_PROCESSOR);

		JSONObject myURLJSONObject = freeMarkerJSONObject.getJSONObject(
			"myURL");

		return myURLJSONObject.getJSONObject("layout");
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

	private Layout _draftLayout;

	@Inject
	private FragmentCollectionContributorRegistry
		_fragmentCollectionContributorRegistry;

	@Inject
	private FragmentCollectionLocalService _fragmentCollectionLocalService;

	@Inject
	private FragmentEntryLinkLocalService _fragmentEntryLinkLocalService;

	@Inject
	private FragmentEntryLocalService _fragmentEntryLocalService;

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