/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v5_7_1.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.change.tracking.test.util.BaseCTUpgradeProcessTestCase;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructureRel;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureRelLocalService;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.change.tracking.CTModel;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.change.tracking.CTService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Costa
 */
@RunWith(Arquillian.class)
public class LayoutPageTemplateStructureRelUpgradeProcessTest
	extends BaseCTUpgradeProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_draftLayout = _layout.fetchDraftLayout();

		_segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_draftLayout.getPlid());

		_objectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	@After
	public void tearDown() throws Exception {
		_objectDefinitionLocalService.deleteObjectDefinition(_objectDefinition);

		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testUpgrade() throws Exception {
		String key = StringBundler.concat(
			"com.liferay.object.web.internal.info.collection.provider.",
			"ObjectEntrySingleFormVariationInfoCollectionProvider_",
			TestPropsValues.getCompanyId(), "_", _objectDefinition.getName());

		ContentLayoutTestUtil.addCollectionDisplayToLayout(
			JSONUtil.put(
				"itemSubtype",
				String.valueOf(_objectDefinition.getObjectDefinitionId())
			).put(
				"itemType", _objectDefinition.getClassName()
			).put(
				"key", key
			).put(
				"type", InfoListProviderItemSelectorReturnType.class.getName()
			),
			_draftLayout, _layoutStructureProvider, null, null, 0,
			_segmentsExperienceId);

		ContentLayoutTestUtil.publishLayout(_draftLayout, _layout);

		Assert.assertEquals(
			key,
			_getLayoutPageTemplateStructureDataKey(
				_layout,
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(_layout.getPlid())));
		Assert.assertEquals(
			key,
			_getLayoutPageTemplateStructureDataKey(
				_draftLayout, _segmentsExperienceId));

		runUpgrade();

		Assert.assertEquals(
			_objectDefinition.getClassName(),
			_getLayoutPageTemplateStructureDataKey(
				_layout,
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(_layout.getPlid())));
		Assert.assertEquals(
			_objectDefinition.getClassName(),
			_getLayoutPageTemplateStructureDataKey(
				_draftLayout, _segmentsExperienceId));
	}

	@Override
	protected CTModel<?> addCTModel() throws Exception {
		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				fetchLayoutPageTemplateStructure(
					_draftLayout.getGroupId(), _draftLayout.getPlid());

		return _layoutPageTemplateStructureRelLocalService.
			fetchLayoutPageTemplateStructureRel(
				layoutPageTemplateStructure.getLayoutPageTemplateStructureId(),
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(_draftLayout.getPlid()));
	}

	@Override
	protected CTService<?> getCTService() {
		return _layoutPageTemplateStructureRelLocalService;
	}

	@Override
	protected void runUpgrade() throws Exception {
		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator, _CLASS_NAME);

		upgradeProcess.upgrade();

		_entityCache.clearCache();
		_multiVMPool.clear();
	}

	@Override
	protected CTModel<?> updateCTModel(CTModel<?> ctModel) throws Exception {
		LayoutPageTemplateStructureRel layoutPageTemplateStructureRel =
			(LayoutPageTemplateStructureRel)ctModel;

		LayoutPageTemplateStructure layoutPageTemplateStructure =
			_layoutPageTemplateStructureLocalService.
				getLayoutPageTemplateStructure(
					layoutPageTemplateStructureRel.
						getLayoutPageTemplateStructureId());

		Layout layout = _layoutLocalService.getLayout(
			layoutPageTemplateStructure.getPlid());

		ContentLayoutTestUtil.addCollectionDisplayToLayout(
			JSONUtil.put(
				"itemType", AssetEntry.class.getName()
			).put(
				"key",
				"com.liferay.asset.internal.info.collection.provider." +
					"RecentContentInfoCollectionProvider"
			).put(
				"type", InfoListProviderItemSelectorReturnType.class.getName()
			),
			layout, _layoutStructureProvider, null, null, 0,
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				layout.getPlid()));

		return _layoutPageTemplateStructureRelLocalService.
			getLayoutPageTemplateStructureRel(
				layoutPageTemplateStructureRel.
					getLayoutPageTemplateStructureRelId());
	}

	private String _getLayoutPageTemplateStructureDataKey(
		Layout layout, long segmentsExperienceId) {

		LayoutStructure layoutStructure =
			_layoutStructureProvider.getLayoutStructure(
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

		JSONObject collectionJSONObject =
			collectionStyledLayoutStructureItem.getCollectionJSONObject();

		return collectionJSONObject.getString("key");
	}

	private static final String _CLASS_NAME =
		"com.liferay.layout.page.template.internal.upgrade.v5_7_1." +
			"LayoutPageTemplateStructureRelUpgradeProcess";

	@Inject(
		filter = "(&(component.name=com.liferay.layout.page.template.internal.upgrade.registry.LayoutPageTemplateServiceUpgradeStepRegistrator))"
	)
	private static UpgradeStepRegistrator _upgradeStepRegistrator;

	private Layout _draftLayout;

	@Inject
	private EntityCache _entityCache;

	@DeleteAfterTestRun
	private Group _group;

	private Layout _layout;

	@Inject
	private LayoutLocalService _layoutLocalService;

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

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private long _segmentsExperienceId;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}