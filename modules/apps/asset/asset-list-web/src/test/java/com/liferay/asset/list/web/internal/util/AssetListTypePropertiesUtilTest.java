/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.web.internal.util;

import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalServiceUtil;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalServiceUtil;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Joshua Cords
 */
public class AssetListTypePropertiesUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@AfterClass
	public static void tearDownClass() {
		_featureFlagManagerUtilMockedStatic.close();
		_listTypeEntryLocalServiceUtilMockedStatic.close();
		_objectDefinitionLocalServiceUtilMockedStatic.close();
		_objectFieldLocalServiceUtilMockedStatic.close();
		_portalUtilMockedStatic.close();
	}

	@Before
	public void setUp() {
		_featureFlagManagerUtilMockedStatic.reset();
		_listTypeEntryLocalServiceUtilMockedStatic.reset();
		_objectDefinitionLocalServiceUtilMockedStatic.reset();
		_objectFieldLocalServiceUtilMockedStatic.reset();
		_portalUtilMockedStatic.reset();

		_featureFlagManagerUtilMockedStatic.when(
			() -> FeatureFlagManagerUtil.isEnabled(
				Mockito.anyLong(), Mockito.eq("LPD-74731"))
		).thenReturn(
			true
		);
	}

	@Test
	public void testExcludesMetadataAndUnfilterableFields() {
		_stubObjectDefinition(
			_CLASS_NAME_ID_1, _CLASS_TYPE_ID_1,
			Arrays.asList(
				_mockObjectField(
					"creator", ObjectFieldConstants.BUSINESS_TYPE_TEXT, true),
				_mockObjectField(
					"attachment", ObjectFieldConstants.BUSINESS_TYPE_ATTACHMENT,
					false),
				_mockObjectField(
					"title", ObjectFieldConstants.BUSINESS_TYPE_TEXT, false)));

		JSONArray jsonArray =
			AssetListTypePropertiesUtil.getTypePropertiesJSONArray(
				new long[] {_CLASS_NAME_ID_1}, new long[] {_CLASS_TYPE_ID_1},
				_COMPANY_ID, LocaleUtil.US);

		Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());
		Assert.assertEquals(
			"title",
			jsonArray.getJSONObject(
				0
			).getString(
				"name"
			));
	}

	@Test
	public void testFeatureFlagDisabledReturnsEmptyArray() {
		_featureFlagManagerUtilMockedStatic.when(
			() -> FeatureFlagManagerUtil.isEnabled(
				Mockito.anyLong(), Mockito.eq("LPD-74731"))
		).thenReturn(
			false
		);

		_stubObjectDefinition(
			_CLASS_NAME_ID_1, _CLASS_TYPE_ID_1,
			Collections.singletonList(
				_mockObjectField(
					"title", ObjectFieldConstants.BUSINESS_TYPE_TEXT, false)));

		JSONArray jsonArray =
			AssetListTypePropertiesUtil.getTypePropertiesJSONArray(
				new long[] {_CLASS_NAME_ID_1}, new long[] {_CLASS_TYPE_ID_1},
				_COMPANY_ID, LocaleUtil.US);

		Assert.assertEquals(jsonArray.toString(), 0, jsonArray.length());
	}

	@Test
	public void testIncludesClassNameIdAndClassTypeIdInEachEntry() {
		_stubObjectDefinition(
			_CLASS_NAME_ID_1, _CLASS_TYPE_ID_1,
			Collections.singletonList(
				_mockObjectField(
					"title", ObjectFieldConstants.BUSINESS_TYPE_TEXT, false)));

		JSONArray jsonArray =
			AssetListTypePropertiesUtil.getTypePropertiesJSONArray(
				new long[] {_CLASS_NAME_ID_1}, new long[] {_CLASS_TYPE_ID_1},
				_COMPANY_ID, LocaleUtil.US);

		Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());

		JSONObject jsonObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			_CLASS_NAME_ID_1, jsonObject.getLong("classNameId"));
		Assert.assertEquals(
			_CLASS_TYPE_ID_1, jsonObject.getLong("classTypeId"));
		Assert.assertEquals("title", jsonObject.getString("name"));
		Assert.assertEquals("string", jsonObject.getString("type"));
	}

	@Test
	public void testIncludesPicklistOptions() {
		long listTypeDefinitionId = RandomTestUtil.randomLong();

		ObjectField objectField = _mockObjectField(
			"status", ObjectFieldConstants.BUSINESS_TYPE_PICKLIST, false);

		Mockito.when(
			objectField.getListTypeDefinitionId()
		).thenReturn(
			listTypeDefinitionId
		);

		_stubObjectDefinition(
			_CLASS_NAME_ID_1, _CLASS_TYPE_ID_1,
			Collections.singletonList(objectField));

		ListTypeEntry approvedListTypeEntry = Mockito.mock(ListTypeEntry.class);

		Mockito.when(
			approvedListTypeEntry.getKey()
		).thenReturn(
			"approved"
		);

		Mockito.when(
			approvedListTypeEntry.getName(LocaleUtil.US, true)
		).thenReturn(
			"Approved"
		);

		ListTypeEntry draftListTypeEntry = Mockito.mock(ListTypeEntry.class);

		Mockito.when(
			draftListTypeEntry.getKey()
		).thenReturn(
			"draft"
		);

		Mockito.when(
			draftListTypeEntry.getName(LocaleUtil.US, true)
		).thenReturn(
			"Draft"
		);

		_listTypeEntryLocalServiceUtilMockedStatic.when(
			() -> ListTypeEntryLocalServiceUtil.getListTypeEntries(
				listTypeDefinitionId)
		).thenReturn(
			Arrays.asList(approvedListTypeEntry, draftListTypeEntry)
		);

		JSONArray jsonArray =
			AssetListTypePropertiesUtil.getTypePropertiesJSONArray(
				new long[] {_CLASS_NAME_ID_1}, new long[] {_CLASS_TYPE_ID_1},
				_COMPANY_ID, LocaleUtil.US);

		Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());

		JSONObject jsonObject = jsonArray.getJSONObject(0);

		Assert.assertEquals("picklist", jsonObject.getString("type"));

		JSONArray optionsJSONArray = jsonObject.getJSONArray("options");

		Assert.assertEquals(
			optionsJSONArray.toString(), 2, optionsJSONArray.length());
		Assert.assertEquals(
			"Approved",
			optionsJSONArray.getJSONObject(
				0
			).getString(
				"label"
			));
		Assert.assertEquals(
			"approved",
			optionsJSONArray.getJSONObject(
				0
			).getString(
				"value"
			));
	}

	@Test
	public void testProducesEntryPerPairWhenSameFieldNameIsShared() {
		_stubObjectDefinition(
			_CLASS_NAME_ID_1, _CLASS_TYPE_ID_1,
			Collections.singletonList(
				_mockObjectField(
					"title", ObjectFieldConstants.BUSINESS_TYPE_TEXT, false)));
		_stubObjectDefinition(
			_CLASS_NAME_ID_2, _CLASS_TYPE_ID_2,
			Arrays.asList(
				_mockObjectField(
					"title", ObjectFieldConstants.BUSINESS_TYPE_TEXT, false),
				_mockObjectField(
					"priority", ObjectFieldConstants.BUSINESS_TYPE_INTEGER,
					false)));

		JSONArray jsonArray =
			AssetListTypePropertiesUtil.getTypePropertiesJSONArray(
				new long[] {_CLASS_NAME_ID_1, _CLASS_NAME_ID_2},
				new long[] {_CLASS_TYPE_ID_1, _CLASS_TYPE_ID_2}, _COMPANY_ID,
				LocaleUtil.US);

		Assert.assertEquals(jsonArray.toString(), 3, jsonArray.length());

		JSONObject jsonObject1 = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			_CLASS_NAME_ID_1, jsonObject1.getLong("classNameId"));
		Assert.assertEquals(
			_CLASS_TYPE_ID_1, jsonObject1.getLong("classTypeId"));
		Assert.assertEquals("title", jsonObject1.getString("name"));

		JSONObject jsonObject2 = jsonArray.getJSONObject(1);

		Assert.assertEquals(
			_CLASS_NAME_ID_2, jsonObject2.getLong("classNameId"));
		Assert.assertEquals(
			_CLASS_TYPE_ID_2, jsonObject2.getLong("classTypeId"));
		Assert.assertEquals("title", jsonObject2.getString("name"));

		JSONObject jsonObject3 = jsonArray.getJSONObject(2);

		Assert.assertEquals(
			_CLASS_NAME_ID_2, jsonObject3.getLong("classNameId"));
		Assert.assertEquals(
			_CLASS_TYPE_ID_2, jsonObject3.getLong("classTypeId"));
		Assert.assertEquals("priority", jsonObject3.getString("name"));
		Assert.assertEquals("integer", jsonObject3.getString("type"));
	}

	private ObjectField _mockObjectField(
		String name, String businessType, boolean metadata) {

		ObjectField objectField = Mockito.mock(ObjectField.class);

		Mockito.when(
			objectField.getBusinessType()
		).thenReturn(
			businessType
		);

		Mockito.when(
			objectField.getName()
		).thenReturn(
			name
		);

		Mockito.when(
			objectField.getLabel(LocaleUtil.US, true)
		).thenReturn(
			name
		);

		Mockito.when(
			objectField.isMetadata()
		).thenReturn(
			metadata
		);

		return objectField;
	}

	private void _stubObjectDefinition(
		long classNameId, long objectDefinitionId,
		List<ObjectField> objectFields) {

		ObjectDefinition objectDefinition = Mockito.mock(
			ObjectDefinition.class);

		Mockito.when(
			objectDefinition.getObjectDefinitionId()
		).thenReturn(
			objectDefinitionId
		);

		_objectDefinitionLocalServiceUtilMockedStatic.when(
			() -> ObjectDefinitionLocalServiceUtil.fetchObjectDefinition(
				objectDefinitionId)
		).thenReturn(
			objectDefinition
		);

		_objectFieldLocalServiceUtilMockedStatic.when(
			() -> ObjectFieldLocalServiceUtil.getObjectFields(
				objectDefinitionId)
		).thenReturn(
			objectFields
		);

		_portalUtilMockedStatic.when(
			() -> PortalUtil.getClassName(classNameId)
		).thenReturn(
			"com.liferay.test.Class" + classNameId
		);
	}

	private static final long _CLASS_NAME_ID_1 = 30601L;

	private static final long _CLASS_NAME_ID_2 = 30602L;

	private static final long _CLASS_TYPE_ID_1 = 42L;

	private static final long _CLASS_TYPE_ID_2 = 99L;

	private static final long _COMPANY_ID = 12345L;

	private static final MockedStatic<FeatureFlagManagerUtil>
		_featureFlagManagerUtilMockedStatic = Mockito.mockStatic(
			FeatureFlagManagerUtil.class);
	private static final MockedStatic<ListTypeEntryLocalServiceUtil>
		_listTypeEntryLocalServiceUtilMockedStatic = Mockito.mockStatic(
			ListTypeEntryLocalServiceUtil.class);
	private static final MockedStatic<ObjectDefinitionLocalServiceUtil>
		_objectDefinitionLocalServiceUtilMockedStatic = Mockito.mockStatic(
			ObjectDefinitionLocalServiceUtil.class);
	private static final MockedStatic<ObjectFieldLocalServiceUtil>
		_objectFieldLocalServiceUtilMockedStatic = Mockito.mockStatic(
			ObjectFieldLocalServiceUtil.class);
	private static final MockedStatic<PortalUtil> _portalUtilMockedStatic =
		Mockito.mockStatic(PortalUtil.class);

}