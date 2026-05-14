/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.asset.entry.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.asset.list.asset.entry.provider.AssetListAssetEntryProvider;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.asset.list.test.util.AssetListTestUtil;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.field.util.ObjectFieldUtil;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.test.util.ObjectDefinitionTestUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.FeatureFlag;
import com.liferay.portal.test.rule.FeatureFlags;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.segments.constants.SegmentsEntryConstants;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Joshua Cords
 */
@RunWith(Arquillian.class)
public class AssetListAssetEntryProviderFiltersTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_objectDefinition = ObjectDefinitionTestUtil.publishObjectDefinition(
			Arrays.asList(
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_TEXT,
					ObjectFieldConstants.DB_TYPE_STRING, "Title", "title"),
				ObjectFieldUtil.createObjectField(
					ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
					ObjectFieldConstants.DB_TYPE_INTEGER, "Priority",
					"priority")));
	}

	@FeatureFlag(enable = false, value = "LPD-74731")
	@Test
	public void testFiltersAreIgnoredWhenFeatureFlagDisabled()
		throws Exception {

		JSONArray filtersJSONArray = JSONUtil.putAll(
			JSONUtil.put(
				"classNameId",
				_portal.getClassNameId(_objectDefinition.getClassName())
			).put(
				"classTypeId", _objectDefinition.getObjectDefinitionId()
			).put(
				"propertyName", "title"
			).put(
				"value", "keyword"
			));

		AssetListEntry assetListEntry = _addDynamicAssetListEntryWithFilters(
			filtersJSONArray.toString());

		AssetEntryQuery assetEntryQuery =
			_assetListAssetEntryProvider.getAssetEntryQuery(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				null);

		Assert.assertNull(assetEntryQuery.getAttribute("filters"));
	}

	@FeatureFlags(featureFlags = @FeatureFlag(value = "LPD-74731"))
	@Test
	public void testFiltersArePropagatedAsAttributeWhenFeatureFlagEnabled()
		throws Exception {

		JSONArray filtersJSONArray = JSONUtil.putAll(
			JSONUtil.put(
				"classNameId",
				_portal.getClassNameId(_objectDefinition.getClassName())
			).put(
				"classTypeId", _objectDefinition.getObjectDefinitionId()
			).put(
				"operatorName", "contains"
			).put(
				"propertyName", "title"
			).put(
				"value", "keyword"
			),
			JSONUtil.put(
				"classNameId",
				_portal.getClassNameId(_objectDefinition.getClassName())
			).put(
				"classTypeId", _objectDefinition.getObjectDefinitionId()
			).put(
				"operatorName", "eq"
			).put(
				"propertyName", "priority"
			).put(
				"value", "1"
			));

		AssetListEntry assetListEntry = _addDynamicAssetListEntryWithFilters(
			filtersJSONArray.toString());

		AssetEntryQuery assetEntryQuery =
			_assetListAssetEntryProvider.getAssetEntryQuery(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				null);

		JSONArray actualJSONArray = (JSONArray)assetEntryQuery.getAttribute(
			"filters");

		Assert.assertNotNull(actualJSONArray);
		Assert.assertEquals(
			actualJSONArray.toString(), 2, actualJSONArray.length());

		JSONObject jsonObject = actualJSONArray.getJSONObject(0);

		Assert.assertEquals("title", jsonObject.getString("propertyName"));
		Assert.assertEquals("keyword", jsonObject.getString("value"));
		Assert.assertEquals(
			_portal.getClassNameId(_objectDefinition.getClassName()),
			jsonObject.getLong("classNameId"));
	}

	@FeatureFlags(featureFlags = @FeatureFlag(value = "LPD-74731"))
	@Test
	public void testInvalidFiltersJSONIsTolerated() throws Exception {
		AssetListEntry assetListEntry = _addDynamicAssetListEntryWithFilters(
			"not-a-json-array");

		AssetEntryQuery assetEntryQuery =
			_assetListAssetEntryProvider.getAssetEntryQuery(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				null);

		Assert.assertNull(assetEntryQuery.getAttribute("filters"));
	}

	@FeatureFlags(featureFlags = @FeatureFlag(value = "LPD-74731"))
	@Test
	public void testNoFiltersAttributeWhenTypeSettingsHasNoFilters()
		throws Exception {

		AssetListEntry assetListEntry = _addDynamicAssetListEntryWithFilters(
			null);

		AssetEntryQuery assetEntryQuery =
			_assetListAssetEntryProvider.getAssetEntryQuery(
				assetListEntry, new long[] {SegmentsEntryConstants.ID_DEFAULT},
				null);

		Assert.assertNull(assetEntryQuery.getAttribute("filters"));
	}

	private AssetListEntry _addDynamicAssetListEntryWithFilters(String filters)
		throws Exception {

		AssetListEntry assetListEntry = AssetListTestUtil.addAssetListEntry(
			_group.getGroupId(), 0);

		UnicodePropertiesBuilder.UnicodePropertiesWrapper
			unicodePropertiesWrapper = UnicodePropertiesBuilder.create(
				true
			).put(
				"anyAssetType",
				String.valueOf(
					_portal.getClassNameId(_objectDefinition.getClassName()))
			);

		if (filters != null) {
			unicodePropertiesWrapper = unicodePropertiesWrapper.put(
				"filters", filters);
		}

		UnicodeProperties typeSettingsUnicodeProperties =
			unicodePropertiesWrapper.build();

		_assetListEntryLocalService.updateAssetListEntryTypeSettings(
			assetListEntry.getAssetListEntryId(),
			SegmentsEntryConstants.ID_DEFAULT,
			typeSettingsUnicodeProperties.toString());

		return _assetListEntryLocalService.getAssetListEntry(
			assetListEntry.getAssetListEntryId());
	}

	@Inject
	private AssetListAssetEntryProvider _assetListAssetEntryProvider;

	@Inject
	private AssetListEntryLocalService _assetListEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private ObjectDefinition _objectDefinition;

	@Inject
	private Portal _portal;

}