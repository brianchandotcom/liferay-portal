/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.page.template.internal.upgrade.v6_0_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.info.list.provider.item.selector.criterion.InfoListProviderItemSelectorReturnType;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.provider.LayoutStructureProvider;
import com.liferay.layout.test.util.ContentLayoutTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.layout.util.structure.CollectionStyledLayoutStructureItem;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.object.constants.ObjectDefinitionSettingConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectDefinitionSetting;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectDefinitionSettingLocalService;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portal.upgrade.registry.UpgradeStepRegistrator;
import com.liferay.portal.upgrade.test.util.UpgradeTestUtil;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jhosseph Gonzalez
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

		_layout = LayoutTestUtil.addTypeContentLayout(_group);

		_draftLayout = _layout.fetchDraftLayout();

		_segmentsExperienceId =
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				_draftLayout.getPlid());

		_objectDefinition =
			ObjectDefinitionTestUtil.addCustomObjectDefinition();

		_objectDefinitionSetting =
			_objectDefinitionSettingLocalService.addObjectDefinitionSetting(
				TestPropsValues.getUserId(),
				_objectDefinition.getObjectDefinitionId(),
				ObjectDefinitionSettingConstants.NAME_OLD_CLASS_NAME,
				_OLD_CLASS_NAME);

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	@After
	public void tearDown() throws Exception {
		_objectDefinitionLocalService.deleteObjectDefinition(_objectDefinition);
		_objectDefinitionSettingLocalService.deleteObjectDefinitionSetting(
			_objectDefinitionSetting);
		ServiceContextThreadLocal.popServiceContext();
	}

	@Test
	public void testUpgradeManyToManyObjectRelationshipRelatedInfoCollectionProvider()
		throws Exception {

		_testUpgrade("ManyToManyObjectRelationship");
	}

	@Test
	public void testUpgradeOneToManyObjectRelationshipRelatedInfoCollectionProvider()
		throws Exception {

		_testUpgrade("OneToManyObjectRelationship");
	}

	private void _assertCollectionKey(
		String expectedKey1, String expectedKey2) {

		Assert.assertEquals(
			expectedKey1,
			_getCollectionKey(
				_KEY_SUFFIX_1, _draftLayout, _segmentsExperienceId));
		Assert.assertEquals(
			expectedKey1,
			_getCollectionKey(
				_KEY_SUFFIX_1, _layout,
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(_layout.getPlid())));
		Assert.assertEquals(
			expectedKey2,
			_getCollectionKey(
				_KEY_SUFFIX_2, _draftLayout, _segmentsExperienceId));
		Assert.assertEquals(
			expectedKey2,
			_getCollectionKey(
				_KEY_SUFFIX_2, _layout,
				_segmentsExperienceLocalService.
					fetchDefaultSegmentsExperienceId(_layout.getPlid())));
	}

	private String _getCollectionKey(
		String keySuffix, Layout layout, long segmentsExperienceId) {

		LayoutStructure layoutStructure =
			_layoutStructureProvider.getLayoutStructure(
				layout.getPlid(), segmentsExperienceId);

		for (CollectionStyledLayoutStructureItem
				collectionStyledLayoutStructureItem :
					layoutStructure.getCollectionStyledLayoutStructureItems()) {

			JSONObject collectionJSONObject =
				collectionStyledLayoutStructureItem.getCollectionJSONObject();

			if (collectionJSONObject == null) {
				continue;
			}

			String key = collectionJSONObject.getString("key");

			if (StringUtil.startsWith(
					key, _INFO_COLLECTION_PROVIDER_CLASS_NAME_PREFIX) &&
				StringUtil.endsWith(key, "_" + keySuffix)) {

				return key;
			}
		}

		return null;
	}

	private void _testUpgrade(String relationshipType) throws Exception {
		String key1 = StringBundler.concat(
			_INFO_COLLECTION_PROVIDER_CLASS_NAME_PREFIX, relationshipType,
			"RelatedInfoCollectionProvider_", _group.getCompanyId(), "_C_",
			RandomTestUtil.randomString(), "_", _KEY_SUFFIX_1);

		ContentLayoutTestUtil.addCollectionDisplayToLayout(
			JSONUtil.put(
				"itemType", _OLD_CLASS_NAME
			).put(
				"key", key1
			).put(
				"type", InfoListProviderItemSelectorReturnType.class.getName()
			),
			_draftLayout, _layoutStructureProvider, null, null, 0,
			_segmentsExperienceId);

		String key2 = StringBundler.concat(
			_INFO_COLLECTION_PROVIDER_CLASS_NAME_PREFIX, relationshipType,
			"RelatedInfoCollectionProvider_", _group.getCompanyId(), "_C_",
			RandomTestUtil.randomString(), "_", _KEY_SUFFIX_2);

		ContentLayoutTestUtil.addCollectionDisplayToLayout(
			JSONUtil.put(
				"key", key2
			).put(
				"sourceItemType", _OLD_CLASS_NAME
			).put(
				"type", InfoListProviderItemSelectorReturnType.class.getName()
			),
			_draftLayout, _layoutStructureProvider, null, null, 1,
			_segmentsExperienceId);

		LayoutStructure layoutStructure =
			_layoutStructureProvider.getLayoutStructure(
				_draftLayout.getPlid(), _segmentsExperienceId);

		_layoutPageTemplateStructureLocalService.
			updateLayoutPageTemplateStructureData(
				_draftLayout.getUserId(), _draftLayout.getGroupId(),
				_draftLayout.getPlid(), _segmentsExperienceId,
				layoutStructure.toString());

		ContentLayoutTestUtil.publishLayout(_draftLayout, _layout);

		_assertCollectionKey(key1, key2);

		UpgradeProcess upgradeProcess = UpgradeTestUtil.getUpgradeStep(
			_upgradeStepRegistrator,
			"com.liferay.layout.page.template.internal.upgrade.v6_0_0." +
				"LayoutPageTemplateStructureRelUpgradeProcess");

		upgradeProcess.upgrade();

		_entityCache.clearCache();
		_multiVMPool.clear();

		_assertCollectionKey(
			StringBundler.concat(
				_INFO_COLLECTION_PROVIDER_CLASS_NAME_PREFIX, relationshipType,
				"RelatedInfoCollectionProvider_",
				_objectDefinition.getClassName(), "_", _KEY_SUFFIX_1),
			StringBundler.concat(
				_INFO_COLLECTION_PROVIDER_CLASS_NAME_PREFIX, relationshipType,
				"RelatedInfoCollectionProvider_",
				_objectDefinition.getClassName(), "_", _KEY_SUFFIX_2));
	}

	private static final String _INFO_COLLECTION_PROVIDER_CLASS_NAME_PREFIX =
		"com.liferay.object.internal.info.collection.provider.";

	private static final String _KEY_SUFFIX_1 = RandomTestUtil.randomString();

	private static final String _KEY_SUFFIX_2 = RandomTestUtil.randomString();

	private static final String _OLD_CLASS_NAME =
		"com.liferay.object.model.ObjectDefinition#A1B2";

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
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Inject
	private LayoutStructureProvider _layoutStructureProvider;

	@Inject
	private MultiVMPool _multiVMPool;

	private ObjectDefinition _objectDefinition;

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	private ObjectDefinitionSetting _objectDefinitionSetting;

	@Inject
	private ObjectDefinitionSettingLocalService
		_objectDefinitionSettingLocalService;

	private long _segmentsExperienceId;

	@Inject
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}