/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v4_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.CollectionPaginationUtil;
import com.liferay.layout.util.constants.LayoutDataItemTypeConstants;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.version.Version;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class LayoutPageTemplateStructureRelUpgradeProcessTest {

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
	public void testUpgrade() throws Exception {

		// Upgrade with "numberOfItemsPerPage"

		int numberOfItems = RandomTestUtil.randomInt();
		int numberOfItemsPerPage = RandomTestUtil.randomInt();

		_assertUpgradeWithItemConfig(
			HashMapBuilder.<String, Object>put(
				"displayAllItems", false
			).put(
				"numberOfItemsPerPage", numberOfItemsPerPage
			).put(
				"numberOfPages",
				(int)Math.ceil(numberOfItems / (double)numberOfItemsPerPage)
			).put(
				"paginationType",
				CollectionPaginationUtil.PAGINATION_TYPE_NUMERIC
			).build(),
			HashMapBuilder.<String, Object>put(
				"numberOfItems", numberOfItems
			).put(
				"numberOfItemsPerPage", numberOfItemsPerPage
			).put(
				"paginationType",
				CollectionPaginationUtil.PAGINATION_TYPE_NUMERIC
			).build());

		// Upgrade with "paginationType" "none"

		_assertUpgradeWithItemConfig(
			HashMapBuilder.<String, Object>put(
				"displayAllItems", false
			).put(
				"paginationType", CollectionPaginationUtil.PAGINATION_TYPE_NONE
			).build(),
			HashMapBuilder.<String, Object>put(
				"paginationType", CollectionPaginationUtil.PAGINATION_TYPE_NONE
			).build());

		// Upgrade with "paginationType" null

		_assertUpgradeWithItemConfig(
			HashMapBuilder.<String, Object>put(
				"displayAllItems", false
			).put(
				"paginationType", CollectionPaginationUtil.PAGINATION_TYPE_NONE
			).build(),
			HashMapBuilder.<String, Object>put(
				"paginationType", StringPool.BLANK
			).build());

		// Upgrade with "paginationType" numeric

		_assertUpgradeWithItemConfig(
			HashMapBuilder.<String, Object>put(
				"displayAllItems", false
			).put(
				"paginationType",
				CollectionPaginationUtil.PAGINATION_TYPE_NUMERIC
			).build(),
			HashMapBuilder.<String, Object>put(
				"paginationType",
				CollectionPaginationUtil.PAGINATION_TYPE_NUMERIC
			).build());

		// Upgrade with "paginationType" random

		_assertUpgradeWithItemConfig(
			HashMapBuilder.<String, Object>put(
				"displayAllItems", false
			).put(
				"paginationType", CollectionPaginationUtil.PAGINATION_TYPE_NONE
			).build(),
			HashMapBuilder.<String, Object>put(
				"paginationType", RandomTestUtil.randomString()
			).build());

		// Upgrade with "paginationType" regular

		_assertUpgradeWithItemConfig(
			HashMapBuilder.<String, Object>put(
				"displayAllItems", false
			).put(
				"paginationType",
				CollectionPaginationUtil.PAGINATION_TYPE_REGULAR
			).build(),
			HashMapBuilder.<String, Object>put(
				"paginationType",
				CollectionPaginationUtil.PAGINATION_TYPE_REGULAR
			).build());

		// Upgrade with "paginationType" simple

		_assertUpgradeWithItemConfig(
			HashMapBuilder.<String, Object>put(
				"displayAllItems", false
			).put(
				"paginationType",
				CollectionPaginationUtil.PAGINATION_TYPE_SIMPLE
			).build(),
			HashMapBuilder.<String, Object>put(
				"paginationType",
				CollectionPaginationUtil.PAGINATION_TYPE_SIMPLE
			).build());

		// Upgrade with "showAllItems" disabled

		_assertUpgradeWithItemConfig(
			HashMapBuilder.<String, Object>put(
				"displayAllItems", false
			).put(
				"displayAllPages", false
			).put(
				"paginationType",
				CollectionPaginationUtil.PAGINATION_TYPE_NUMERIC
			).build(),
			HashMapBuilder.<String, Object>put(
				"paginationType",
				CollectionPaginationUtil.PAGINATION_TYPE_NUMERIC
			).put(
				"showAllItems", false
			).build());

		// Upgrade with "showAllItems" enabled

		_assertUpgradeWithItemConfig(
			HashMapBuilder.<String, Object>put(
				"displayAllItems", false
			).put(
				"displayAllPages", true
			).put(
				"paginationType",
				CollectionPaginationUtil.PAGINATION_TYPE_NUMERIC
			).build(),
			HashMapBuilder.<String, Object>put(
				"paginationType",
				CollectionPaginationUtil.PAGINATION_TYPE_NUMERIC
			).put(
				"showAllItems", true
			).build());
	}

	private void _assertItemConfigJSONObject(
			String layoutPageTemplateStructure,
			String collectionStyledLayoutStructureItem, Map<String, Object> map)
		throws Exception {

		JSONObject configJSONObject = _getItemConfigJSONObject(
			layoutPageTemplateStructure, collectionStyledLayoutStructureItem);

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			if (Validator.isNull(entry.getValue())) {
				Assert.assertTrue(
					entry.getKey(),
					Validator.isNull(configJSONObject.get(entry.getKey())));
			}
			else {
				Assert.assertEquals(
					entry.getKey(), entry.getValue(),
					configJSONObject.get(entry.getKey()));
			}
		}
	}

	private void _assertUpgradeWithItemConfig(
			Map<String, Object> expectedMap, Map<String, Object> map)
		throws Exception {

		Layout layout = LayoutTestUtil.addTypeContentLayout(_group);

		long segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid());

		LayoutStructure layoutStructure =
			_layoutStructureProvider.getLayoutStructure(
				layout.getPlid(), segmentsExperienceId);

		ContentLayoutTestUtil.addItemToLayout(
			"{}", LayoutDataItemTypeConstants.TYPE_COLLECTION, layout,
			layoutStructure.getMainItemId(), 0, segmentsExperienceId);

		layoutStructure = _layoutStructureProvider.getLayoutStructure(
			layout.getPlid(), segmentsExperienceId);

		List<CollectionStyledLayoutStructureItem>
			collectionStyledLayoutStructureItems =
				layoutStructure.getCollectionStyledLayoutStructureItems();

		Assert.assertEquals(
			collectionStyledLayoutStructureItems.toString(), 1,
			collectionStyledLayoutStructureItems.size());

		CollectionStyledLayoutStructureItem
			collectionStyledLayoutStructureItem =
				collectionStyledLayoutStructureItems.get(0);

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), layout.getPlid());

		_updateItemConfig(
			collectionStyledLayoutStructureItem.getItemId(),
			layoutPageTemplateStructure, map, segmentsExperienceId);

		_runUpgrade();

		layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_group.getGroupId(), layout.getPlid());

		_assertItemConfigJSONObject(
			layoutPageTemplateStructure.getData(segmentsExperienceId),
			collectionStyledLayoutStructureItem.getItemId(), expectedMap);
	}

	private JSONObject _getItemConfigJSONObject(String data, String itemId)
		throws Exception {

		JSONObject layoutStructureJSONObject = JSONFactoryUtil.createJSONObject(
			data);

		JSONObject itemsJSONObject = layoutStructureJSONObject.getJSONObject(
			"items");

		JSONObject itemJSONObject = itemsJSONObject.getJSONObject(itemId);

		return itemJSONObject.getJSONObject("config");
	}

	private void _runUpgrade() throws Exception {
		UpgradeProcess[] upgradeProcesses = UpgradeTestUtil.getUpgradeSteps(
			_upgradeStepRegistrator, new Version(4, 0, 0));

		for (UpgradeProcess upgradeProcess : upgradeProcesses) {
			upgradeProcess.upgrade();
		}

		_multiVMPool.clear();
	}

	private void _updateItemConfig(
			String itemId,
			LayoutPageTemplateStructure layoutPageTemplateStructure,
			Map<String, Object> map, long segmentsExperienceId)
		throws Exception {

		JSONObject layoutStructureJSONObject = JSONFactoryUtil.createJSONObject(
			layoutPageTemplateStructure.getData(segmentsExperienceId));

		JSONObject itemsJSONObject = layoutStructureJSONObject.getJSONObject(
			"items");

		JSONObject itemJSONObject = itemsJSONObject.getJSONObject(itemId);

		JSONObject itemConfigJSONObject = itemJSONObject.getJSONObject(
			"config");

		for (Map.Entry<String, Object> entry : map.entrySet()) {
			itemConfigJSONObject.put(entry.getKey(), entry.getValue());
		}

		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel =
			_layoutPageTemplateStructureRelLocalService.
				fetchLayoutPageTemplateStructureRel(
					layoutPageTemplateStructure.
						getLayoutPageTemplateStructureId(),
					segmentsExperienceId);

		layoutPageTemplateStructureRel.setData(
			layoutStructureJSONObject.toString());

		layoutPageTemplateStructureRel =
			_layoutPageTemplateStructureRelLocalService.
				updateLayoutPageTemplateStructureRel(
					layoutPageTemplateStructureRel);

		_assertItemConfigJSONObject(
			layoutPageTemplateStructureRel.getData(), itemId, map);
	}

	@Inject(
		filter = "(&(component.name=com.liferay.layout.page.template.internal.upgrade.registry.LayoutPageTemplateServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private LayoutPageTemplateStructureRelLocalService
		_layoutPageTemplateStructureRelLocalService;

	@Inject
	private LayoutStructureProvider _layoutStructureProvider;

	@Inject
	private MultiVMPool _multiVMPool;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}