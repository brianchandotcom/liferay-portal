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

package com.liferay.asset.publisher.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.list.constants.AssetListEntryTypeConstants;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.publisher.util.AssetPublisherHelper;
import com.liferay.asset.publisher.util.AssetQueryRule;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.portlet.MockLiferayPortletRenderRequest;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portletmvc4spring.test.mock.web.portlet.MockPortletPreferences;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.criteria.Criteria;
import com.liferay.segments.criteria.CriteriaSerializer;
import com.liferay.segments.criteria.contributor.SegmentsCriteriaContributor;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.test.util.SegmentsTestUtil;

import java.util.Arrays;
import java.util.List;

import javax.portlet.PortletPreferences;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Eudaldo Alonso
 */
@RunWith(Arquillian.class)
public class AssetPublisherHelperTest {

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
	public void testGetAssetCategoryIdsContainsAllCategories()
		throws Exception {

		long assetCategoryId1 = RandomTestUtil.nextLong();
		long assetCategoryId2 = RandomTestUtil.nextLong();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			true, true, "assetCategories",
			new String[] {
				String.valueOf(assetCategoryId1),
				String.valueOf(assetCategoryId2)
			});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		long[] assetCategoryIds = _assetPublisherHelper.getAssetCategoryIds(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetCategoryIds), 2, assetCategoryIds.length);
		Assert.assertEquals(assetCategoryId1, assetCategoryIds[0]);
		Assert.assertEquals(assetCategoryId2, assetCategoryIds[1]);
	}

	@Test
	public void testGetAssetCategoryIdsContainsAllCategory() throws Exception {
		long assetCategoryId = RandomTestUtil.nextLong();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			true, true, "assetCategories",
			new String[] {String.valueOf(assetCategoryId)});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		long[] assetCategoryIds = _assetPublisherHelper.getAssetCategoryIds(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetCategoryIds), 1, assetCategoryIds.length);
		Assert.assertEquals(assetCategoryId, assetCategoryIds[0]);
	}

	@Test
	public void testGetAssetCategoryIdsContainsAnyCategories()
		throws Exception {

		long assetCategoryId1 = RandomTestUtil.nextLong();
		long assetCategoryId2 = RandomTestUtil.nextLong();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			true, false, "assetCategories",
			new String[] {
				String.valueOf(assetCategoryId1),
				String.valueOf(assetCategoryId2)
			});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		long[] assetCategoryIds = _assetPublisherHelper.getAssetCategoryIds(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetCategoryIds), 0, assetCategoryIds.length);
	}

	@Test
	public void testGetAssetCategoryIdsContainsAnyCategory() throws Exception {
		long assetCategoryId = RandomTestUtil.nextLong();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			true, false, "assetCategories",
			new String[] {String.valueOf(assetCategoryId)});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		long[] assetCategoryIds = _assetPublisherHelper.getAssetCategoryIds(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetCategoryIds), 1, assetCategoryIds.length);
		Assert.assertEquals(assetCategoryId, assetCategoryIds[0]);
	}

	@Test
	public void testGetAssetCategoryIdsNotContainsAllCategories()
		throws Exception {

		long assetCategoryId1 = RandomTestUtil.nextLong();
		long assetCategoryId2 = RandomTestUtil.nextLong();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			false, true, "assetCategories",
			new String[] {
				String.valueOf(assetCategoryId1),
				String.valueOf(assetCategoryId2)
			});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		long[] assetCategoryIds = _assetPublisherHelper.getAssetCategoryIds(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetCategoryIds), 0, assetCategoryIds.length);
	}

	@Test
	public void testGetAssetCategoryIdsNotContainsAllCategory()
		throws Exception {

		long assetCategoryId = RandomTestUtil.nextLong();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			false, true, "assetCategories",
			new String[] {String.valueOf(assetCategoryId)});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		long[] assetCategoryIds = _assetPublisherHelper.getAssetCategoryIds(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetCategoryIds), 0, assetCategoryIds.length);
	}

	@Test
	public void testGetAssetCategoryIdsNotContainsAnyCategories()
		throws Exception {

		long assetCategoryId1 = RandomTestUtil.nextLong();
		long assetCategoryId2 = RandomTestUtil.nextLong();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			false, false, "assetCategories",
			new String[] {
				String.valueOf(assetCategoryId1),
				String.valueOf(assetCategoryId2)
			});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		long[] assetCategoryIds = _assetPublisherHelper.getAssetCategoryIds(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetCategoryIds), 0, assetCategoryIds.length);
	}

	@Test
	public void testGetAssetCategoryIdsNotContainsAnyCategory()
		throws Exception {

		long assetCategoryId = RandomTestUtil.nextLong();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			false, false, "assetCategories",
			new String[] {String.valueOf(assetCategoryId)});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		long[] assetCategoryIds = _assetPublisherHelper.getAssetCategoryIds(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetCategoryIds), 0, assetCategoryIds.length);
	}

	@Test
	public void testGetAssetEntries() throws Exception {
		AssetListEntry assetListEntry = _addAssetListEntry(_group.getGroupId());

		SegmentsEntry segmentsEntry = _addSegmentsEntry(
			_group.getGroupId(), TestPropsValues.getUser());

		MockLiferayPortletRenderRequest mockLiferayPortletRenderRequest =
			new MockLiferayPortletRenderRequest();

		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setLayout(LayoutTestUtil.addLayout(_group));
		themeDisplay.setUser(TestPropsValues.getUser());

		mockLiferayPortletRenderRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, TestPropsValues.getUserId());

		serviceContext.setRequest(
			mockLiferayPortletRenderRequest.getHttpServletRequest());

		ServiceContextThreadLocal.pushServiceContext(serviceContext);

		try {
			PortletPreferences portletPreferences =
				mockLiferayPortletRenderRequest.getPreferences();

			portletPreferences.setValues(
				"assetListEntryId",
				String.valueOf(assetListEntry.getAssetListEntryId()));
			portletPreferences.setValue("selectionStyle", "asset-list");

			_assetPublisherHelper.getAssetEntries(
				mockLiferayPortletRenderRequest, portletPreferences,
				PermissionCheckerFactoryUtil.create(TestPropsValues.getUser()),
				new long[0], new long[0], new String[0], false, true);

			long[] segmentsEntryIds =
				(long[])mockLiferayPortletRenderRequest.getAttribute(
					"SEGMENTS_ENTRY_IDS");

			Assert.assertEquals(
				Arrays.toString(segmentsEntryIds), 2, segmentsEntryIds.length);
			Assert.assertEquals(
				segmentsEntry.getSegmentsEntryId(), segmentsEntryIds[0]);
			Assert.assertEquals(
				SegmentsEntryConstants.ID_DEFAULT, segmentsEntryIds[1]);
		}
		finally {
			ServiceContextThreadLocal.popServiceContext();
		}
	}

	@Test
	public void testGetAssetTagNamesContainsAllTagName() throws Exception {
		String assetTagName = RandomTestUtil.randomString();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			true, true, "assetTags", new String[] {assetTagName});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		String[] assetTagNames = _assetPublisherHelper.getAssetTagNames(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetTagNames), 1, assetTagNames.length);
		Assert.assertEquals(
			StringUtil.toLowerCase(assetTagName), assetTagNames[0]);
	}

	@Test
	public void testGetAssetTagNamesContainsAllTagNames() throws Exception {
		String assetTagName1 = RandomTestUtil.randomString();
		String assetTagName2 = RandomTestUtil.randomString();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			true, true, "assetTags",
			new String[] {assetTagName1, assetTagName2});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		String[] assetTagNames = _assetPublisherHelper.getAssetTagNames(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetTagNames), 2, assetTagNames.length);
		Assert.assertEquals(
			StringUtil.toLowerCase(assetTagName1), assetTagNames[0]);
		Assert.assertEquals(
			StringUtil.toLowerCase(assetTagName2), assetTagNames[1]);
	}

	@Test
	public void testGetAssetTagNamesContainsAnyTagName() throws Exception {
		String assetTagName = RandomTestUtil.randomString();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			true, false, "assetTags", new String[] {assetTagName});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		String[] assetTagNames = _assetPublisherHelper.getAssetTagNames(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetTagNames), 1, assetTagNames.length);
		Assert.assertEquals(
			StringUtil.toLowerCase(assetTagName), assetTagNames[0]);
	}

	@Test
	public void testGetAssetTagNamesContainsAnyTagNames() throws Exception {
		String assetTagName1 = RandomTestUtil.randomString();
		String assetTagName2 = RandomTestUtil.randomString();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			true, false, "assetTags",
			new String[] {assetTagName1, assetTagName2});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		String[] assetTagNames = _assetPublisherHelper.getAssetTagNames(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetTagNames), 0, assetTagNames.length);
	}

	@Test
	public void testGetAssetTagNamesNotContainsAllTagName() throws Exception {
		String assetTagName = RandomTestUtil.randomString();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			false, true, "assetTags", new String[] {assetTagName});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		String[] assetTagNames = _assetPublisherHelper.getAssetTagNames(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetTagNames), 0, assetTagNames.length);
	}

	@Test
	public void testGetAssetTagNamesNotContainsAllTagNames() throws Exception {
		String assetTagName1 = RandomTestUtil.randomString();
		String assetTagName2 = RandomTestUtil.randomString();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			false, true, "assetTags",
			new String[] {assetTagName1, assetTagName2});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		String[] assetTagNames = _assetPublisherHelper.getAssetTagNames(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetTagNames), 0, assetTagNames.length);
	}

	@Test
	public void testGetAssetTagNamesNotContainsAnyTagName() throws Exception {
		String assetTagName = RandomTestUtil.randomString();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			false, false, "assetTags", new String[] {assetTagName});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		String[] assetTagNames = _assetPublisherHelper.getAssetTagNames(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetTagNames), 0, assetTagNames.length);
	}

	@Test
	public void testGetAssetTagNamesNotContainsAnyTagNames() throws Exception {
		String assetTagName1 = RandomTestUtil.randomString();
		String assetTagName2 = RandomTestUtil.randomString();

		AssetQueryRule assetQueryRule = new AssetQueryRule(
			false, false, "assetTags",
			new String[] {assetTagName1, assetTagName2});

		PortletPreferences portletPreferences =
			getAssetPublisherPortletPreferences(
				ListUtil.fromArray(assetQueryRule));

		String[] assetTagNames = _assetPublisherHelper.getAssetTagNames(
			portletPreferences);

		Assert.assertEquals(
			Arrays.toString(assetTagNames), 0, assetTagNames.length);
	}

	protected PortletPreferences getAssetPublisherPortletPreferences(
			List<AssetQueryRule> assetQueryRules)
		throws Exception {

		PortletPreferences portletPreferences = new MockPortletPreferences();

		for (int i = 0; i < assetQueryRules.size(); i++) {
			AssetQueryRule assetQueryRule = assetQueryRules.get(i);

			portletPreferences.setValue(
				"queryAndOperator" + i,
				String.valueOf(assetQueryRule.isAndOperator()));
			portletPreferences.setValue(
				"queryContains" + i,
				String.valueOf(assetQueryRule.isContains()));
			portletPreferences.setValue(
				"queryName" + i, assetQueryRule.getName());
			portletPreferences.setValues(
				"queryValues" + i, assetQueryRule.getValues());
		}

		return portletPreferences;
	}

	private AssetListEntry _addAssetListEntry(long groupId) throws Exception {
		return _assetListEntryLocalService.addAssetListEntry(
			TestPropsValues.getUserId(), groupId, RandomTestUtil.randomString(),
			AssetListEntryTypeConstants.TYPE_DYNAMIC,
			ServiceContextTestUtil.getServiceContext(
				groupId, TestPropsValues.getUserId()));
	}

	private SegmentsEntry _addSegmentsEntry(long groupId, User user)
		throws Exception {

		Criteria criteria = new Criteria();

		_segmentsCriteriaContributor.contribute(
			criteria, String.format("(firstName eq '%s')", user.getFirstName()),
			Criteria.Conjunction.AND);

		return SegmentsTestUtil.addSegmentsEntry(
			groupId, CriteriaSerializer.serialize(criteria),
			User.class.getName());
	}

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Inject
	private AssetPublisherHelper _assetPublisherHelper;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "segments.criteria.contributor.key=user",
		type = SegmentsCriteriaContributor.class
	)
	private SegmentsCriteriaContributor _segmentsCriteriaContributor;

}