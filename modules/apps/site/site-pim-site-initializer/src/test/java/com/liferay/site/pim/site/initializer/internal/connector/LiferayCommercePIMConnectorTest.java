/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.pim.site.initializer.internal.connector;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetCategoryConstants;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.model.AssetVocabularyConstants;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.list.type.model.ListTypeEntry;
import com.liferay.list.type.service.ListTypeEntryLocalServiceUtil;
import com.liferay.object.constants.ObjectFieldConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.object.service.ObjectFieldLocalServiceUtil;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelHintsUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HtmlParserUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.site.pim.site.initializer.connector.PIMConnectorChannelField;
import com.liferay.site.pim.site.initializer.constants.PIMConnectorFieldMappingTypeConstants;
import com.liferay.site.pim.site.initializer.exception.PIMConnectorException;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Andrea Sbarra
 * @author Stefano Motta
 */
public class LiferayCommercePIMConnectorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		Mockito.when(
			_friendlyURLNormalizer.normalize(Mockito.anyString())
		).thenAnswer(
			invocationOnMock -> StringUtil.toLowerCase(
				invocationOnMock.getArgument(0, String.class))
		);

		Mockito.when(
			_friendlyURLNormalizer.normalizeWithPeriodsAndSlashes(
				Mockito.anyString())
		).thenAnswer(
			invocationOnMock -> StringUtil.toLowerCase(
				StringUtil.replace(
					invocationOnMock.getArgument(0, String.class),
					new char[] {CharPool.PERIOD, CharPool.SLASH},
					new char[] {CharPool.DASH, CharPool.DASH}))
		);

		Mockito.when(
			_language.get(Mockito.eq(LocaleUtil.US), Mockito.anyString())
		).thenAnswer(
			invocationOnMock -> invocationOnMock.getArgument(1)
		);

		LanguageUtil languageUtil = new LanguageUtil();

		languageUtil.setLanguage(_language);

		ReflectionTestUtil.setFieldValue(
			_liferayCommercePIMConnector, "_assetCategoryLocalService",
			_assetCategoryLocalService);
		ReflectionTestUtil.setFieldValue(
			_liferayCommercePIMConnector, "_assetTagLocalService",
			_assetTagLocalService);
		ReflectionTestUtil.setFieldValue(
			_liferayCommercePIMConnector, "_assetVocabularyLocalService",
			_assetVocabularyLocalService);
		ReflectionTestUtil.setFieldValue(
			_liferayCommercePIMConnector, "_friendlyURLNormalizer",
			_friendlyURLNormalizer);
		ReflectionTestUtil.setFieldValue(
			_liferayCommercePIMConnector, "_groupLocalService",
			_groupLocalService);
		ReflectionTestUtil.setFieldValue(
			_liferayCommercePIMConnector, "_http", _http);
		ReflectionTestUtil.setFieldValue(
			_liferayCommercePIMConnector, "_jsonFactory",
			JSONFactoryUtil.getJSONFactory());
		ReflectionTestUtil.setFieldValue(
			_liferayCommercePIMConnector, "language", _language);
		ReflectionTestUtil.setFieldValue(
			_liferayCommercePIMConnector, "objectDefinitionLocalService",
			_objectDefinitionLocalService);
		ReflectionTestUtil.setFieldValue(
			_liferayCommercePIMConnector, "objectEntryLocalService",
			_objectEntryLocalService);

		_modelHintsUtilMockedStatic.when(
			() -> ModelHintsUtil.getMaxLength(
				AssetCategory.class.getName(), "externalReferenceCode")
		).thenReturn(
			75
		);

		Mockito.when(
			_objectDefinition.getClassName()
		).thenReturn(
			_CLASS_NAME
		);

		Mockito.when(
			_objectDefinition.getObjectDefinitionId()
		).thenReturn(
			_OBJECT_DEFINITION_ID
		);

		Mockito.when(
			_objectDefinitionLocalService.fetchObjectDefinition(
				_OBJECT_DEFINITION_ID)
		).thenReturn(
			_objectDefinition
		);

		_mockHttp(Collections.emptyMap());
	}

	@After
	public void tearDown() {
		_htmlParserUtilMockedStatic.close();
		_listTypeEntryLocalServiceUtilMockedStatic.close();
		_modelHintsUtilMockedStatic.close();
		_objectFieldLocalServiceUtilMockedStatic.close();
	}

	@Test
	public void testExecute() throws Exception {
		_testExecute();
		_testExecuteWithBooleanProductType();
		_testExecuteWithCatalogs();
		_testExecuteWithCategories();
		_testExecuteWithErrors();
		_testExecuteWithFieldMappingRules();
		_testExecuteWithFirstNonemptySourceFieldMappingRule();
		_testExecuteWithForeignStructureFixedValue();
		_testExecuteWithInvalidStructureFieldMapping();
		_testExecuteWithJoinedValues();
		_testExecuteWithPicklistProductOptions();
		_testExecuteWithPicklistProductSpecification();
		_testExecuteWithProductOptions();
		_testExecuteWithProductSpecifications();
		_testExecuteWithTags();
		_testExecuteWithTopicVocabulary();
		_testExecuteWithTrailingSlashURL();
		_testExecuteWithVariantPIMLinks();
		_testExecuteWithoutUnitOfMeasure();
	}

	@Test
	public void testGetKey() {
		Assert.assertEquals(
			"liferay-commerce", _liferayCommercePIMConnector.getKey());
	}

	@Test
	public void testGetName() {
		Assert.assertEquals(
			"liferay-commerce",
			_liferayCommercePIMConnector.getName(LocaleUtil.US));
	}

	@Test
	public void testGetPIMConnectorChannelFields() {
		List<PIMConnectorChannelField> pimConnectorChannelFields =
			_liferayCommercePIMConnector.getPIMConnectorChannelFields();

		Assert.assertEquals(
			pimConnectorChannelFields.toString(), 16,
			pimConnectorChannelFields.size());
		Assert.assertEquals(
			Arrays.asList(
				"categories=[categories, dynamicValue, fixedValue]",
				"tags=[dynamicValue, fixedValue, tags]"),
			TransformUtil.transform(
				pimConnectorChannelFields,
				pimConnectorChannelField -> {
					List<String> allowedFieldMappingTypes =
						pimConnectorChannelField.getAllowedFieldMappingTypes();

					if (allowedFieldMappingTypes.equals(
							Arrays.asList(
								PIMConnectorFieldMappingTypeConstants.
									DYNAMIC_VALUE,
								PIMConnectorFieldMappingTypeConstants.
									FIXED_VALUE))) {

						return null;
					}

					return StringBundler.concat(
						pimConnectorChannelField.getName(), StringPool.EQUAL,
						allowedFieldMappingTypes);
				}));
		Assert.assertEquals(
			Arrays.asList(
				"catalog-external-reference-code", "categories", "depth",
				"description", "height", "name", "precision", "product-options",
				"product-specifications", "product-type", "sku", "tags",
				"unit-of-measure-key", "unit-of-measure-name", "weight",
				"width"),
			TransformUtil.transform(
				pimConnectorChannelFields,
				pimConnectorChannelField -> pimConnectorChannelField.getLabel(
					LocaleUtil.US)));
		Assert.assertEquals(
			Arrays.asList(
				"catalogExternalReferenceCode", "categories", "skus[].depth",
				"description", "skus[].height", "name",
				"skus[].skuUnitOfMeasures[].precision", "productOptions",
				"productSpecifications", "productType", "skus[].sku", "tags",
				"skus[].skuUnitOfMeasures[].key",
				"skus[].skuUnitOfMeasures[].name", "skus[].weight",
				"skus[].width"),
			TransformUtil.transform(
				pimConnectorChannelFields, PIMConnectorChannelField::getName));
		Assert.assertEquals(
			Arrays.asList(
				"catalogExternalReferenceCode", "name", "productType",
				"skus[].sku"),
			TransformUtil.transform(
				pimConnectorChannelFields,
				pimConnectorChannelField -> {
					if (!pimConnectorChannelField.isRequired()) {
						return null;
					}

					return pimConnectorChannelField.getName();
				}));
		Assert.assertEquals(
			Arrays.asList(
				"Text", "Text", "Decimal", "Text", "Decimal", "Text", "Integer",
				"Text", "Text", "Text", "Text", "Text", "Text", "Text",
				"Decimal", "Decimal"),
			TransformUtil.transform(
				pimConnectorChannelFields, PIMConnectorChannelField::getType));
		Assert.assertEquals(
			Arrays.asList(
				"categories", "productOptions", "productSpecifications",
				"tags"),
			TransformUtil.transform(
				pimConnectorChannelFields,
				pimConnectorChannelField -> {
					if (!pimConnectorChannelField.isMultiple()) {
						return null;
					}

					return pimConnectorChannelField.getName();
				}));
	}

	@Test
	public void testGetPIMConnectorConfigurationObjectDefinitionExternalReferenceCode() {
		Assert.assertEquals(
			"L_PIM_CONNECTOR_CONFIGURATION_LIFERAY_COMMERCE",
			_liferayCommercePIMConnector.
				getPIMConnectorConfigurationObjectDefinitionExternalReferenceCode());
	}

	private PIMConnectorException _assertPIMConnectorException(
			String expectedMessage, UnsafeRunnable<Exception> unsafeRunnable)
		throws Exception {

		try {
			unsafeRunnable.run();
		}
		catch (PIMConnectorException pimConnectorException) {
			Assert.assertEquals(
				expectedMessage, pimConnectorException.getMessage());

			return pimConnectorException;
		}

		throw new AssertionError(expectedMessage);
	}

	private String _execute(
			Map<String, Serializable> pimConnectorConfigurationValues,
			List<ObjectEntry> pimFieldMappingObjectEntries,
			Map<String, ObjectEntry> pimFieldMappingRuleObjectEntriesMap,
			Map<String, List<ObjectEntry>> pimProductObjectEntriesMap)
		throws Exception {

		Map<String, List<ObjectEntry>> pimFieldMappingObjectEntriesMap =
			new HashMap<>();

		for (ObjectEntry objectEntry : pimFieldMappingObjectEntries) {
			List<ObjectEntry> objectEntries =
				pimFieldMappingObjectEntriesMap.computeIfAbsent(
					MapUtil.getString(
						objectEntry.getValues(), "channelFieldName"),
					key -> new ArrayList<>());

			objectEntries.add(objectEntry);
		}

		_options.clear();

		return _liferayCommercePIMConnector.doExecute(
			pimConnectorConfigurationValues, _pimConnectorObjectEntry,
			pimFieldMappingObjectEntriesMap,
			pimFieldMappingRuleObjectEntriesMap, pimProductObjectEntriesMap);
	}

	private JSONObject _getBodyContentJSONObject(String location)
		throws Exception {

		Http.Options options = _getOptions(location);

		Http.Body body = options.getBody();

		return JSONFactoryUtil.createJSONObject(body.getContent());
	}

	private Http.Options _getOptions(String location) {
		List<Http.Options> matchingOptions = ListUtil.filter(
			_options,
			options -> {
				String curLocation = options.getLocation();

				return curLocation.contains(location);
			});

		Assert.assertEquals(
			matchingOptions.toString(), 1, matchingOptions.size());

		return matchingOptions.get(0);
	}

	private JSONObject _getProductJSONObject(
			String externalReferenceCode,
			List<ObjectEntry> pimFieldMappingObjectEntries,
			Map<String, ObjectEntry> pimFieldMappingRuleObjectEntriesMap,
			ObjectEntry... pimProductObjectEntries)
		throws Exception {

		_execute(
			_pimConnectorConfigurationValues, pimFieldMappingObjectEntries,
			pimFieldMappingRuleObjectEntriesMap,
			HashMapBuilder.put(
				externalReferenceCode, Arrays.asList(pimProductObjectEntries)
			).build());

		Http.Options options = _getOptions("/import-task");

		Http.Body body = options.getBody();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			body.getContent());

		Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());

		return jsonArray.getJSONObject(0);
	}

	private List<ObjectEntry> _getRequiredObjectEntries(
		ObjectEntry... objectEntries) {

		List<ObjectEntry> requiredObjectEntries = ListUtil.fromArray(
			_mockPIMFieldMappingObjectEntry(
				"catalogExternalReferenceCode", StringPool.BLANK,
				StringPool.BLANK,
				PIMConnectorFieldMappingTypeConstants.FIXED_VALUE,
				_CATALOG_EXTERNAL_REFERENCE_CODE_1),
			_mockPIMFieldMappingObjectEntry(
				"name", StringPool.BLANK, "title",
				PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
				StringPool.BLANK),
			_mockPIMFieldMappingObjectEntry(
				"productType", StringPool.BLANK, StringPool.BLANK,
				PIMConnectorFieldMappingTypeConstants.FIXED_VALUE, "simple"),
			_mockPIMFieldMappingObjectEntry(
				"skus[].sku", StringPool.BLANK, "sku",
				PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
				StringPool.BLANK));

		Collections.addAll(requiredObjectEntries, objectEntries);

		return requiredObjectEntries;
	}

	private JSONObject _getSkuJSONObject(int index, JSONObject jsonObject) {
		JSONArray jsonArray = jsonObject.getJSONArray("skus");

		Assert.assertTrue(jsonArray.toString(), jsonArray.length() > index);

		return jsonArray.getJSONObject(index);
	}

	private List<String> _getTaxonomyPaths() {
		return TransformUtil.transform(
			_options,
			options -> {
				String location = options.getLocation();

				int index = location.indexOf("/taxonomy-");

				if (index == -1) {
					return null;
				}

				return StringBundler.concat(
					options.getMethod(), StringPool.SPACE,
					location.substring(index));
			});
	}

	private void _mockAssetCategories(
		ObjectEntry pimProductObjectEntry, AssetCategory... assetCategories) {

		long objectEntryId = RandomTestUtil.randomLong();

		Mockito.when(
			pimProductObjectEntry.getObjectEntryId()
		).thenReturn(
			objectEntryId
		);

		Mockito.when(
			_assetCategoryLocalService.getCategories(_CLASS_NAME, objectEntryId)
		).thenReturn(
			Arrays.asList(assetCategories)
		);
	}

	private AssetCategory _mockAssetCategory(
			AssetVocabulary assetVocabulary, String externalReferenceCode,
			AssetCategory... ancestorAssetCategories)
		throws Exception {

		AssetCategory assetCategory = Mockito.mock(AssetCategory.class);

		Mockito.when(
			assetCategory.getAncestors()
		).thenReturn(
			Arrays.asList(ancestorAssetCategories)
		);

		Mockito.when(
			assetCategory.getExternalReferenceCode()
		).thenReturn(
			externalReferenceCode
		);

		if (ancestorAssetCategories.length > 0) {
			Mockito.when(
				assetCategory.getParentCategory()
			).thenReturn(
				ancestorAssetCategories[0]
			);
		}

		Mockito.when(
			assetCategory.getTitle(LocaleUtil.US)
		).thenReturn(
			externalReferenceCode + " title"
		);

		long vocabularyId = assetVocabulary.getVocabularyId();

		Mockito.when(
			assetCategory.getVocabularyId()
		).thenReturn(
			vocabularyId
		);

		Mockito.when(
			assetCategory.isRootCategory()
		).thenReturn(
			ancestorAssetCategories.length == 0
		);

		return assetCategory;
	}

	private void _mockAssetTags(
		ObjectEntry pimProductObjectEntry, String... names) {

		long objectEntryId = RandomTestUtil.randomLong();

		Mockito.when(
			pimProductObjectEntry.getObjectEntryId()
		).thenReturn(
			objectEntryId
		);

		List<AssetTag> assetTags = TransformUtil.transformToList(
			names,
			name -> {
				AssetTag assetTag = Mockito.mock(AssetTag.class);

				Mockito.when(
					assetTag.getName()
				).thenReturn(
					name
				);

				return assetTag;
			});

		Mockito.when(
			_assetTagLocalService.getTags(_CLASS_NAME, objectEntryId)
		).thenReturn(
			assetTags
		);
	}

	private AssetVocabulary _mockAssetVocabulary(
			String externalReferenceCode, int visibilityType)
		throws Exception {

		AssetVocabulary assetVocabulary = Mockito.mock(AssetVocabulary.class);

		Mockito.when(
			assetVocabulary.getExternalReferenceCode()
		).thenReturn(
			externalReferenceCode
		);

		Mockito.when(
			assetVocabulary.getTitle(LocaleUtil.US)
		).thenReturn(
			externalReferenceCode + " title"
		);

		Mockito.when(
			assetVocabulary.getVisibilityType()
		).thenReturn(
			visibilityType
		);

		long vocabularyId = RandomTestUtil.randomLong();

		Mockito.when(
			assetVocabulary.getVocabularyId()
		).thenReturn(
			vocabularyId
		);

		Mockito.when(
			assetVocabulary.isMultiValued()
		).thenReturn(
			true
		);

		Mockito.when(
			_assetVocabularyLocalService.getVocabulary(vocabularyId)
		).thenReturn(
			assetVocabulary
		);

		return assetVocabulary;
	}

	private void _mockHttp(Map<String, Integer> responseCodes)
		throws Exception {

		Mockito.when(
			_http.URLtoString(Mockito.any(Http.Options.class))
		).thenAnswer(
			invocationOnMock -> {
				Http.Options options = invocationOnMock.getArgument(
					0, Http.Options.class);

				_options.add(options);

				String location = options.getLocation();
				Http.Response response = options.getResponse();

				String request = StringBundler.concat(
					options.getMethod(), StringPool.SPACE, location);

				for (Map.Entry<String, Integer> entry :
						responseCodes.entrySet()) {

					if (request.contains(entry.getKey())) {
						response.setResponseCode(entry.getValue());

						return JSONUtil.put(
							"title", RandomTestUtil.randomString()
						).toString();
					}
				}

				response.setResponseCode(200);

				if (location.endsWith("/o/oauth2/token")) {
					return JSONUtil.put(
						"access_token", "TOKEN"
					).toString();
				}

				if (location.contains("/import-task")) {
					return JSONUtil.put(
						"executeStatus", "INITIAL"
					).put(
						"externalReferenceCode", "BATCH-1"
					).toString();
				}

				return "{}";
			}
		);
	}

	private void _mockObjectField(String label, String name) {
		ObjectField objectField = Mockito.mock(ObjectField.class);

		Mockito.when(
			objectField.getLabel(LocaleUtil.US)
		).thenReturn(
			label
		);

		_objectFieldLocalServiceUtilMockedStatic.when(
			() -> ObjectFieldLocalServiceUtil.fetchObjectField(
				_OBJECT_DEFINITION_ID, name)
		).thenReturn(
			objectField
		);
	}

	private ObjectEntry _mockPIMFieldMappingObjectEntry(
		String channelFieldName, String sourceClassName, String sourceFieldName,
		String type, String value) {

		ObjectEntry objectEntry = Mockito.mock(ObjectEntry.class);

		Mockito.when(
			objectEntry.getValues()
		).thenReturn(
			HashMapBuilder.<String, Serializable>put(
				"channelFieldName", channelFieldName
			).put(
				"sourceClassName", sourceClassName
			).put(
				"sourceFieldName", sourceFieldName
			).put(
				"type", type
			).put(
				"value", value
			).build()
		);

		return objectEntry;
	}

	private ObjectEntry _mockPIMFieldMappingRuleObjectEntry(
		Map<String, Serializable> values) {

		ObjectEntry objectEntry = Mockito.mock(ObjectEntry.class);

		Mockito.when(
			objectEntry.getValues()
		).thenReturn(
			values
		);

		return objectEntry;
	}

	private ObjectEntry _mockPIMProductObjectEntry(
			String sku, Map<String, Serializable> values)
		throws Exception {

		ObjectEntry objectEntry = Mockito.mock(ObjectEntry.class);

		Mockito.when(
			objectEntry.getExternalReferenceCode()
		).thenReturn(
			sku
		);

		Mockito.when(
			objectEntry.getObjectDefinitionId()
		).thenReturn(
			_OBJECT_DEFINITION_ID
		);

		Mockito.when(
			_objectEntryLocalService.getValues(objectEntry)
		).thenReturn(
			HashMapBuilder.<String, Serializable>put(
				"sku", sku
			).put(
				"title", sku + " title"
			).put(
				"unitOfMeasureKey", "box"
			).putAll(
				values
			).build()
		);

		return objectEntry;
	}

	private void _testExecute() throws Exception {
		JSONObject jsonObject = _getProductJSONObject(
			"SKU-1", _getRequiredObjectEntries(),
			Collections.<String, ObjectEntry>emptyMap(),
			_mockPIMProductObjectEntry(
				"SKU-1", Collections.<String, Serializable>emptyMap()));

		Assert.assertTrue(jsonObject.getBoolean("active"));
		Assert.assertEquals(
			_CATALOG_EXTERNAL_REFERENCE_CODE_1,
			jsonObject.getString("catalogExternalReferenceCode"));
		Assert.assertFalse(jsonObject.has("catalogId"));
		Assert.assertFalse(jsonObject.toString(), jsonObject.has("categories"));
		Assert.assertEquals(
			"SKU-1", jsonObject.getString("externalReferenceCode"));
		Assert.assertEquals("simple", jsonObject.getString("productType"));
		Assert.assertFalse(
			jsonObject.toString(), jsonObject.has("description"));
		Assert.assertFalse(
			jsonObject.toString(), jsonObject.has("productOptions"));
		Assert.assertFalse(
			jsonObject.toString(), jsonObject.has("productSpecifications"));
		Assert.assertFalse(jsonObject.toString(), jsonObject.has("tags"));

		JSONObject nameJSONObject = jsonObject.getJSONObject("name");

		Assert.assertEquals("SKU-1 title", nameJSONObject.getString("en_US"));

		JSONObject skuJSONObject = _getSkuJSONObject(0, jsonObject);

		Assert.assertEquals(
			"SKU-1", skuJSONObject.getString("externalReferenceCode"));
		Assert.assertTrue(skuJSONObject.getBoolean("published"));
		Assert.assertTrue(skuJSONObject.getBoolean("purchasable"));
		Assert.assertEquals("SKU-1", skuJSONObject.getString("sku"));
		Assert.assertFalse(
			skuJSONObject.toString(), skuJSONObject.has("skuOptions"));
	}

	private void _testExecuteWithBooleanProductType() throws Exception {
		List<ObjectEntry> objectEntries = ListUtil.fromArray(
			_mockPIMFieldMappingObjectEntry(
				"catalogExternalReferenceCode", StringPool.BLANK,
				StringPool.BLANK,
				PIMConnectorFieldMappingTypeConstants.FIXED_VALUE,
				_CATALOG_EXTERNAL_REFERENCE_CODE_1),
			_mockPIMFieldMappingObjectEntry(
				"name", StringPool.BLANK, "title",
				PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
				StringPool.BLANK),
			_mockPIMFieldMappingObjectEntry(
				"productType", StringPool.BLANK, "virtual",
				PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
				StringPool.BLANK),
			_mockPIMFieldMappingObjectEntry(
				"skus[].sku", StringPool.BLANK, "sku",
				PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
				StringPool.BLANK));

		JSONObject jsonObject = _getProductJSONObject(
			"SKU-1", objectEntries, Collections.<String, ObjectEntry>emptyMap(),
			_mockPIMProductObjectEntry(
				"SKU-1",
				HashMapBuilder.<String, Serializable>put(
					"virtual", false
				).build()));

		Assert.assertEquals("simple", jsonObject.getString("productType"));

		jsonObject = _getProductJSONObject(
			"SKU-2", objectEntries, Collections.<String, ObjectEntry>emptyMap(),
			_mockPIMProductObjectEntry(
				"SKU-2",
				HashMapBuilder.<String, Serializable>put(
					"virtual", true
				).build()));

		Assert.assertEquals("virtual", jsonObject.getString("productType"));
	}

	private void _testExecuteWithCatalogs() throws Exception {
		_mockObjectField("Color", "color");

		List<ObjectEntry> objectEntries = ListUtil.fromArray(
			_mockPIMFieldMappingObjectEntry(
				"catalogExternalReferenceCode", StringPool.BLANK, "catalog",
				PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
				StringPool.BLANK),
			_mockPIMFieldMappingObjectEntry(
				"name", StringPool.BLANK, "title",
				PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
				StringPool.BLANK),
			_mockPIMFieldMappingObjectEntry(
				"productOptions", StringPool.BLANK, "color",
				PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
				StringPool.BLANK),
			_mockPIMFieldMappingObjectEntry(
				"productType", StringPool.BLANK, StringPool.BLANK,
				PIMConnectorFieldMappingTypeConstants.FIXED_VALUE, "simple"),
			_mockPIMFieldMappingObjectEntry(
				"skus[].sku", StringPool.BLANK, "sku",
				PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
				StringPool.BLANK));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_execute(
				_pimConnectorConfigurationValues, objectEntries,
				Collections.<String, ObjectEntry>emptyMap(),
				HashMapBuilder.put(
					"SKU-1",
					Arrays.asList(
						_mockPIMProductObjectEntry(
							"SKU-1",
							HashMapBuilder.<String, Serializable>put(
								"catalog", _CATALOG_EXTERNAL_REFERENCE_CODE_1
							).put(
								"color", RandomTestUtil.randomString()
							).build()))
				).put(
					"SKU-2",
					Arrays.asList(
						_mockPIMProductObjectEntry(
							"SKU-2",
							HashMapBuilder.<String, Serializable>put(
								"catalog", _CATALOG_EXTERNAL_REFERENCE_CODE_2
							).put(
								"color", RandomTestUtil.randomString()
							).build()))
				).build()));

		Assert.assertEquals(
			"BATCH-1", jsonObject.getString("batchExternalReferenceCode"));
		Assert.assertEquals("INITIAL", jsonObject.getString("status"));

		Http.Options options = _getOptions("/o/oauth2/token");

		Assert.assertEquals(
			"https://example.com/o/oauth2/token", options.getLocation());

		options = _getOptions("/options/by-externalReferenceCode/color");

		Assert.assertEquals(Http.Method.PUT, options.getMethod());

		Http.Body body = options.getBody();

		JSONObject optionJSONObject = JSONFactoryUtil.createJSONObject(
			body.getContent());

		Assert.assertEquals("select", optionJSONObject.getString("fieldType"));
		Assert.assertEquals("color", optionJSONObject.getString("key"));
		Assert.assertTrue(optionJSONObject.getBoolean("skuContributor"));

		JSONObject nameJSONObject = optionJSONObject.getJSONObject("name");

		Assert.assertEquals("Color", nameJSONObject.getString("en_US"));

		options = _getOptions("/import-task");

		Assert.assertEquals(Http.Method.POST, options.getMethod());
		Assert.assertEquals(
			StringBundler.concat(
				"https://example.com/o/headless-batch-engine/v1.0/import-task",
				"/com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product",
				"?createStrategy=UPSERT&importStrategy=ON_ERROR_CONTINUE"),
			options.getLocation());

		body = options.getBody();

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray(
			body.getContent());

		Assert.assertEquals(jsonArray.toString(), 2, jsonArray.length());

		Set<String> catalogExternalReferenceCodes = new HashSet<>();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject productJSONObject = jsonArray.getJSONObject(i);

			catalogExternalReferenceCodes.add(
				productJSONObject.getString("catalogExternalReferenceCode"));
		}

		Assert.assertEquals(
			SetUtil.fromArray(
				_CATALOG_EXTERNAL_REFERENCE_CODE_1,
				_CATALOG_EXTERNAL_REFERENCE_CODE_2),
			catalogExternalReferenceCodes);

		jsonArray = jsonObject.getJSONArray("log");

		Assert.assertEquals(jsonArray.toString(), 3, jsonArray.length());
	}

	private void _testExecuteWithCategories() throws Exception {
		AssetVocabulary assetVocabulary = _mockAssetVocabulary(
			RandomTestUtil.randomString(),
			AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC);

		AssetCategory assetCategory1 = _mockAssetCategory(
			assetVocabulary, RandomTestUtil.randomString());

		AssetCategory assetCategory2 = _mockAssetCategory(
			assetVocabulary, RandomTestUtil.randomString(), assetCategory1);

		AssetCategory assetCategory3 = _mockAssetCategory(
			assetVocabulary, RandomTestUtil.randomString(), assetCategory2,
			assetCategory1);

		ObjectEntry pimProductObjectEntry1 = _mockPIMProductObjectEntry(
			"SKU-1", Collections.<String, Serializable>emptyMap());

		_mockAssetCategories(
			pimProductObjectEntry1, assetCategory3,
			_mockAssetCategory(
				_mockAssetVocabulary(
					RandomTestUtil.randomString(),
					AssetVocabularyConstants.VISIBILITY_TYPE_INTERNAL),
				RandomTestUtil.randomString()));

		ObjectEntry pimProductObjectEntry2 = _mockPIMProductObjectEntry(
			"SKU-2", Collections.<String, Serializable>emptyMap());

		_mockAssetCategories(
			pimProductObjectEntry2,
			_mockAssetCategory(assetVocabulary, RandomTestUtil.randomString()));

		List<ObjectEntry> pimFieldMappingObjectEntries =
			_getRequiredObjectEntries(
				_mockPIMFieldMappingObjectEntry(
					"categories", StringPool.BLANK, StringPool.BLANK,
					PIMConnectorFieldMappingTypeConstants.CATEGORIES,
					StringPool.BLANK));

		JSONObject jsonObject1 = _getProductJSONObject(
			"SKU-1", pimFieldMappingObjectEntries,
			Collections.<String, ObjectEntry>emptyMap(), pimProductObjectEntry1,
			pimProductObjectEntry2);

		JSONArray jsonArray = jsonObject1.getJSONArray("categories");

		Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());

		jsonObject1 = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			assetCategory3.getExternalReferenceCode(),
			jsonObject1.getString("externalReferenceCode"));
		Assert.assertEquals(
			assetVocabulary.getExternalReferenceCode(),
			jsonObject1.getString("vocabularyExternalReferenceCode"));

		Assert.assertEquals(
			Arrays.asList(
				"PUT /taxonomy-vocabularies/by-external-reference-code/" +
					assetVocabulary.getExternalReferenceCode(),
				"PUT /taxonomy-categories/by-external-reference-code/" +
					assetCategory1.getExternalReferenceCode(),
				"PUT /taxonomy-categories/by-external-reference-code/" +
					assetCategory2.getExternalReferenceCode(),
				"PUT /taxonomy-categories/by-external-reference-code/" +
					assetCategory3.getExternalReferenceCode()),
			_getTaxonomyPaths());

		jsonObject1 = _getBodyContentJSONObject(
			"/taxonomy-vocabularies/by-external-reference-code/" +
				assetVocabulary.getExternalReferenceCode());

		Assert.assertTrue(jsonObject1.getBoolean("multiValued"));
		Assert.assertEquals(
			assetVocabulary.getTitle(LocaleUtil.US),
			jsonObject1.getString("name"));
		Assert.assertEquals("Anyone", jsonObject1.getString("viewableBy"));
		Assert.assertEquals("PUBLIC", jsonObject1.getString("visibilityType"));

		jsonObject1 = _getBodyContentJSONObject(
			"/taxonomy-categories/by-external-reference-code/" +
				assetCategory1.getExternalReferenceCode());

		Assert.assertEquals(
			assetCategory1.getTitle(LocaleUtil.US),
			jsonObject1.getString("name"));

		JSONObject jsonObject2 = jsonObject1.getJSONObject(
			"parentTaxonomyCategory");

		Assert.assertEquals(
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			jsonObject2.getLong("id"));

		jsonObject2 = jsonObject1.getJSONObject("parentTaxonomyVocabulary");

		Assert.assertEquals(
			assetVocabulary.getExternalReferenceCode(),
			jsonObject2.getString("externalReferenceCode"));

		Assert.assertEquals("Anyone", jsonObject1.getString("viewableBy"));

		jsonObject1 = _getBodyContentJSONObject(
			"/taxonomy-categories/by-external-reference-code/" +
				assetCategory2.getExternalReferenceCode());

		jsonObject2 = jsonObject1.getJSONObject("parentTaxonomyCategory");

		Assert.assertEquals(
			assetCategory1.getExternalReferenceCode(),
			jsonObject2.getString("externalReferenceCode"));

		jsonObject1 = _getBodyContentJSONObject(
			"/taxonomy-categories/by-external-reference-code/" +
				assetCategory3.getExternalReferenceCode());

		jsonObject2 = jsonObject1.getJSONObject("parentTaxonomyCategory");

		Assert.assertEquals(
			assetCategory2.getExternalReferenceCode(),
			jsonObject2.getString("externalReferenceCode"));

		pimProductObjectEntry1 = _mockPIMProductObjectEntry(
			"SKU-1", Collections.<String, Serializable>emptyMap());

		_mockAssetCategories(pimProductObjectEntry1);

		jsonObject1 = _getProductJSONObject(
			"SKU-1", pimFieldMappingObjectEntries,
			Collections.<String, ObjectEntry>emptyMap(),
			pimProductObjectEntry1);

		jsonArray = jsonObject1.getJSONArray("categories");

		Assert.assertEquals(jsonArray.toString(), 0, jsonArray.length());

		Assert.assertEquals(Collections.emptyList(), _getTaxonomyPaths());
	}

	private void _testExecuteWithErrors() throws Exception {
		Map<String, List<ObjectEntry>> pimProductObjectEntriesMap =
			HashMapBuilder.<String, List<ObjectEntry>>put(
				"SKU-1",
				Arrays.asList(
					_mockPIMProductObjectEntry(
						"SKU-1", Collections.<String, Serializable>emptyMap()))
			).build();
		List<ObjectEntry> requiredObjectEntries = _getRequiredObjectEntries();

		_assertPIMConnectorException(
			"enter-a-valid-url",
			() -> _execute(
				Collections.<String, Serializable>emptyMap(),
				requiredObjectEntries,
				Collections.<String, ObjectEntry>emptyMap(),
				pimProductObjectEntriesMap));
		_assertPIMConnectorException(
			"enter-a-valid-url",
			() -> _execute(
				HashMapBuilder.<String, Serializable>put(
					"url", "ftp://localhost"
				).build(),
				requiredObjectEntries,
				Collections.<String, ObjectEntry>emptyMap(),
				pimProductObjectEntriesMap));
		_assertPIMConnectorException(
			"enter-a-valid-url",
			() -> _execute(
				HashMapBuilder.<String, Serializable>put(
					"url", "http://"
				).build(),
				requiredObjectEntries,
				Collections.<String, ObjectEntry>emptyMap(),
				pimProductObjectEntriesMap));
		_assertPIMConnectorException(
			"enter-a-valid-url",
			() -> _execute(
				HashMapBuilder.<String, Serializable>put(
					"url", "localhost"
				).build(),
				requiredObjectEntries,
				Collections.<String, ObjectEntry>emptyMap(),
				pimProductObjectEntriesMap));

		Assert.assertTrue(_options.isEmpty());

		Mockito.when(
			_http.URLtoString(Mockito.any(Http.Options.class))
		).thenAnswer(
			invocationOnMock -> {
				Http.Options options = invocationOnMock.getArgument(
					0, Http.Options.class);

				_options.add(options);

				Http.Response response = options.getResponse();

				response.setResponseCode(200);

				String location = options.getLocation();

				if (location.endsWith("/o/oauth2/token")) {
					return JSONUtil.put(
						"access_token", "TOKEN"
					).toString();
				}

				if (location.contains("/import-task")) {
					return "<html>Login</html>";
				}

				return "{}";
			}
		);

		PIMConnectorException pimConnectorException =
			_assertPIMConnectorException(
				"an-unexpected-error-occurred",
				() -> _execute(
					_pimConnectorConfigurationValues,
					_getRequiredObjectEntries(),
					Collections.<String, ObjectEntry>emptyMap(),
					HashMapBuilder.put(
						"SKU-1",
						Arrays.asList(
							_mockPIMProductObjectEntry(
								"SKU-1",
								HashMapBuilder.<String, Serializable>put(
									"sku", "SKU-1"
								).put(
									"title", RandomTestUtil.randomString()
								).build()))
					).build()));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			pimConnectorException.getOutput());

		JSONArray jsonArray = jsonObject.getJSONArray("log");

		Assert.assertEquals(jsonArray.toString(), 2, jsonArray.length());

		JSONObject logJSONObject = jsonArray.getJSONObject(1);

		JSONObject responseJSONObject = logJSONObject.getJSONObject("response");

		Assert.assertEquals(
			"<html>Login</html>", responseJSONObject.getString("body"));

		_mockHttp(Collections.emptyMap());

		_mockHttp(
			HashMapBuilder.put(
				"/import-task", 500
			).build());

		pimConnectorException = _assertPIMConnectorException(
			"the-remote-instance-returned-an-error",
			() -> _getProductJSONObject(
				"SKU-1", _getRequiredObjectEntries(),
				Collections.<String, ObjectEntry>emptyMap(),
				_mockPIMProductObjectEntry(
					"SKU-1", Collections.<String, Serializable>emptyMap())));

		jsonObject = JSONFactoryUtil.createJSONObject(
			pimConnectorException.getOutput());

		jsonArray = jsonObject.getJSONArray("log");

		Assert.assertEquals(jsonArray.toString(), 2, jsonArray.length());

		_mockHttp(
			HashMapBuilder.put(
				"/taxonomy-categories", 500
			).build());

		ObjectEntry pimProductObjectEntry = _mockPIMProductObjectEntry(
			"SKU-1", Collections.<String, Serializable>emptyMap());

		_mockAssetCategories(
			pimProductObjectEntry,
			_mockAssetCategory(
				_mockAssetVocabulary(
					RandomTestUtil.randomString(),
					AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC),
				RandomTestUtil.randomString()));

		_assertPIMConnectorException(
			"the-remote-instance-returned-an-error",
			() -> _execute(
				_pimConnectorConfigurationValues,
				_getRequiredObjectEntries(
					_mockPIMFieldMappingObjectEntry(
						"categories", StringPool.BLANK, StringPool.BLANK,
						PIMConnectorFieldMappingTypeConstants.CATEGORIES,
						StringPool.BLANK)),
				Collections.<String, ObjectEntry>emptyMap(),
				HashMapBuilder.put(
					"SKU-1", Arrays.asList(pimProductObjectEntry)
				).build()));

		Assert.assertFalse(
			ListUtil.exists(
				_options,
				options -> {
					String location = options.getLocation();

					return location.contains("/import-task");
				}));

		_mockHttp(Collections.emptyMap());
	}

	private void _testExecuteWithFieldMappingRules() throws Exception {
		_htmlParserUtilMockedStatic.when(
			() -> HtmlParserUtil.extractText("<br>")
		).thenReturn(
			StringPool.BLANK
		);

		JSONObject jsonObject1 = _getProductJSONObject(
			"SKU-1",
			_getRequiredObjectEntries(
				_mockPIMFieldMappingObjectEntry(
					"description", StringPool.BLANK, "description",
					PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
					StringPool.BLANK),
				_mockPIMFieldMappingObjectEntry(
					"name", StringPool.BLANK, "sku",
					PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
					StringPool.BLANK),
				_mockPIMFieldMappingObjectEntry(
					"productOptions", StringPool.BLANK, "color",
					PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
					StringPool.BLANK),
				_mockPIMFieldMappingObjectEntry(
					"productSpecifications", StringPool.BLANK, "material",
					PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
					StringPool.BLANK),
				_mockPIMFieldMappingObjectEntry(
					"tags", StringPool.BLANK, StringPool.BLANK,
					PIMConnectorFieldMappingTypeConstants.FIXED_VALUE,
					"Home Office"),
				_mockPIMFieldMappingObjectEntry(
					"tags", StringPool.BLANK, StringPool.BLANK,
					PIMConnectorFieldMappingTypeConstants.FIXED_VALUE,
					"Summer Sale")),
			HashMapBuilder.put(
				"description",
				_mockPIMFieldMappingRuleObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"stripHTMLTags", true
					).build())
			).put(
				"name",
				_mockPIMFieldMappingRuleObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"textCase", "upper"
					).build())
			).put(
				"productOptions",
				_mockPIMFieldMappingRuleObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"trimWhitespace", true
					).build())
			).put(
				"productSpecifications",
				_mockPIMFieldMappingRuleObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"textCase", "upper"
					).build())
			).put(
				"tags",
				_mockPIMFieldMappingRuleObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"textCase", "lower"
					).put(
						"urlEncode", true
					).build())
			).build(),
			_mockPIMProductObjectEntry(
				"SKU-1",
				HashMapBuilder.<String, Serializable>put(
					"color", " Red "
				).put(
					"description", "<br>"
				).put(
					"material", "Cotton"
				).build()));

		Assert.assertFalse(
			jsonObject1.toString(), jsonObject1.has("description"));

		JSONObject jsonObject2 = jsonObject1.getJSONObject("name");

		Assert.assertEquals(
			"SKU-1 TITLE SKU-1", jsonObject2.getString("en_US"));

		JSONArray jsonArray = jsonObject1.getJSONArray("productOptions");

		jsonObject2 = jsonArray.getJSONObject(0);

		jsonArray = jsonObject2.getJSONArray("productOptionValues");

		jsonObject2 = jsonArray.getJSONObject(0);

		Assert.assertEquals("red", jsonObject2.getString("key"));

		jsonObject2 = jsonObject2.getJSONObject("name");

		Assert.assertEquals("Red", jsonObject2.getString("en_US"));

		jsonArray = jsonObject1.getJSONArray("productSpecifications");

		jsonObject2 = jsonArray.getJSONObject(0);

		jsonObject2 = jsonObject2.getJSONObject("value");

		Assert.assertEquals("COTTON", jsonObject2.getString("en_US"));

		jsonObject2 = _getSkuJSONObject(0, jsonObject1);

		jsonArray = jsonObject2.getJSONArray("skuOptions");

		jsonObject2 = jsonArray.getJSONObject(0);

		Assert.assertEquals("red", jsonObject2.getString("value"));

		Assert.assertEquals(
			Arrays.asList("home%20office", "summer%20sale"),
			JSONUtil.toStringList(jsonObject1.getJSONArray("tags")));
	}

	private void _testExecuteWithFirstNonemptySourceFieldMappingRule()
		throws Exception {

		JSONObject jsonObject = _getProductJSONObject(
			"SKU-1",
			_getRequiredObjectEntries(
				_mockPIMFieldMappingObjectEntry(
					"name", StringPool.BLANK, "sku",
					PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
					StringPool.BLANK)),
			HashMapBuilder.put(
				"name",
				_mockPIMFieldMappingRuleObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"useFirstNonemptySource", true
					).build())
			).build(),
			_mockPIMProductObjectEntry(
				"SKU-1", Collections.<String, Serializable>emptyMap()));

		jsonObject = jsonObject.getJSONObject("name");

		Assert.assertEquals("SKU-1 title", jsonObject.getString("en_US"));
	}

	private void _testExecuteWithForeignStructureFixedValue() throws Exception {
		JSONObject jsonObject = _getProductJSONObject(
			"SKU-1",
			_getRequiredObjectEntries(
				_mockPIMFieldMappingObjectEntry(
					"description", RandomTestUtil.randomString(),
					StringPool.BLANK,
					PIMConnectorFieldMappingTypeConstants.FIXED_VALUE,
					"A shirt")),
			Collections.<String, ObjectEntry>emptyMap(),
			_mockPIMProductObjectEntry(
				"SKU-1", Collections.<String, Serializable>emptyMap()));

		Assert.assertFalse(
			jsonObject.toString(), jsonObject.has("description"));
	}

	private void _testExecuteWithInvalidStructureFieldMapping()
		throws Exception {

		JSONObject jsonObject = _getProductJSONObject(
			"SKU-1",
			_getRequiredObjectEntries(
				_mockPIMFieldMappingObjectEntry(
					"description", RandomTestUtil.randomString(), "description",
					PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
					StringPool.BLANK)),
			Collections.<String, ObjectEntry>emptyMap(),
			_mockPIMProductObjectEntry(
				"SKU-1",
				HashMapBuilder.<String, Serializable>put(
					"description", "A red shirt"
				).build()));

		Assert.assertFalse(
			jsonObject.toString(), jsonObject.has("description"));
	}

	private void _testExecuteWithJoinedValues() throws Exception {
		JSONObject jsonObject = _getProductJSONObject(
			"SKU-1",
			_getRequiredObjectEntries(
				_mockPIMFieldMappingObjectEntry(
					"name", _CLASS_NAME, "sku",
					PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
					StringPool.BLANK)),
			Collections.<String, ObjectEntry>emptyMap(),
			_mockPIMProductObjectEntry(
				"SKU-1", Collections.<String, Serializable>emptyMap()));

		JSONObject nameJSONObject = jsonObject.getJSONObject("name");

		Assert.assertEquals("SKU-1", nameJSONObject.getString("en_US"));
	}

	private void _testExecuteWithPicklistProductOptions() throws Exception {
		ObjectField objectField = Mockito.mock(ObjectField.class);

		Mockito.when(
			objectField.compareBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)
		).thenReturn(
			true
		);

		Mockito.when(
			objectField.getListTypeDefinitionId()
		).thenReturn(
			_LIST_TYPE_DEFINITION_ID
		);

		_objectFieldLocalServiceUtilMockedStatic.when(
			() -> ObjectFieldLocalServiceUtil.fetchObjectField(
				_OBJECT_DEFINITION_ID, "size")
		).thenReturn(
			objectField
		);

		ListTypeEntry listTypeEntry = Mockito.mock(ListTypeEntry.class);

		Mockito.when(
			listTypeEntry.getKey()
		).thenReturn(
			"extraLarge"
		);

		Mockito.when(
			listTypeEntry.getName(LocaleUtil.US)
		).thenReturn(
			"Extra Large"
		);

		_listTypeEntryLocalServiceUtilMockedStatic.when(
			() -> ListTypeEntryLocalServiceUtil.fetchListTypeEntry(
				_LIST_TYPE_DEFINITION_ID, "extraLarge")
		).thenReturn(
			listTypeEntry
		);

		JSONObject jsonObject = _getProductJSONObject(
			"SKU-1",
			_getRequiredObjectEntries(
				_mockPIMFieldMappingObjectEntry(
					"productOptions", StringPool.BLANK, "size",
					PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
					StringPool.BLANK)),
			Collections.<String, ObjectEntry>emptyMap(),
			_mockPIMProductObjectEntry(
				"SKU-1",
				HashMapBuilder.<String, Serializable>put(
					"size", "extraLarge"
				).build()));

		JSONArray jsonArray = jsonObject.getJSONArray("productOptions");

		Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());

		JSONObject productOptionJSONObject = jsonArray.getJSONObject(0);

		jsonArray = productOptionJSONObject.getJSONArray("productOptionValues");

		Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());

		JSONObject productOptionValueJSONObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			"extraLarge", productOptionValueJSONObject.getString("key"));

		JSONObject nameJSONObject = productOptionValueJSONObject.getJSONObject(
			"name");

		Assert.assertEquals("Extra Large", nameJSONObject.getString("en_US"));

		JSONObject skuJSONObject = _getSkuJSONObject(0, jsonObject);

		jsonArray = skuJSONObject.getJSONArray("skuOptions");

		Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());

		JSONObject skuOptionJSONObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			"extraLarge", skuOptionJSONObject.getString("value"));
	}

	private void _testExecuteWithPicklistProductSpecification()
		throws Exception {

		ObjectField objectField = Mockito.mock(ObjectField.class);

		Mockito.when(
			objectField.compareBusinessType(
				ObjectFieldConstants.BUSINESS_TYPE_PICKLIST)
		).thenReturn(
			true
		);

		Mockito.when(
			objectField.getLabel(LocaleUtil.US)
		).thenReturn(
			"Size"
		);

		Mockito.when(
			objectField.getListTypeDefinitionId()
		).thenReturn(
			_LIST_TYPE_DEFINITION_ID
		);

		_objectFieldLocalServiceUtilMockedStatic.when(
			() -> ObjectFieldLocalServiceUtil.fetchObjectField(
				_OBJECT_DEFINITION_ID, "size")
		).thenReturn(
			objectField
		);

		ListTypeEntry listTypeEntry = Mockito.mock(ListTypeEntry.class);

		Mockito.when(
			listTypeEntry.getName(LocaleUtil.US)
		).thenReturn(
			"Small"
		);

		_listTypeEntryLocalServiceUtilMockedStatic.when(
			() -> ListTypeEntryLocalServiceUtil.fetchListTypeEntry(
				_LIST_TYPE_DEFINITION_ID, "small")
		).thenReturn(
			listTypeEntry
		);

		JSONObject jsonObject = _getProductJSONObject(
			"SKU-1",
			_getRequiredObjectEntries(
				_mockPIMFieldMappingObjectEntry(
					"productSpecifications", StringPool.BLANK, "size",
					PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
					StringPool.BLANK)),
			Collections.<String, ObjectEntry>emptyMap(),
			_mockPIMProductObjectEntry(
				"SKU-1",
				HashMapBuilder.<String, Serializable>put(
					"size", "small"
				).build()));

		JSONArray jsonArray = jsonObject.getJSONArray("productSpecifications");

		Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());

		jsonObject = jsonArray.getJSONObject(0);

		JSONObject valueJSONObject = jsonObject.getJSONObject("value");

		Assert.assertEquals("Small", valueJSONObject.getString("en_US"));
	}

	private void _testExecuteWithProductOptions() throws Exception {
		_mockObjectField("Color", "color");

		JSONObject jsonObject = _getProductJSONObject(
			"SKU-1",
			_getRequiredObjectEntries(
				_mockPIMFieldMappingObjectEntry(
					"productOptions", StringPool.BLANK, "color",
					PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
					StringPool.BLANK)),
			Collections.<String, ObjectEntry>emptyMap(),
			_mockPIMProductObjectEntry(
				"SKU-1",
				HashMapBuilder.<String, Serializable>put(
					"color", "Red"
				).build()),
			_mockPIMProductObjectEntry(
				"SKU-2",
				HashMapBuilder.<String, Serializable>put(
					"color", "Blue"
				).build()));

		JSONArray jsonArray = jsonObject.getJSONArray("productOptions");

		Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());

		JSONObject productOptionJSONObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			"select", productOptionJSONObject.getString("fieldType"));
		Assert.assertEquals("color", productOptionJSONObject.getString("key"));
		Assert.assertEquals(
			"color",
			productOptionJSONObject.getString("optionExternalReferenceCode"));
		Assert.assertEquals(0, productOptionJSONObject.getLong("optionId"));
		Assert.assertTrue(productOptionJSONObject.getBoolean("skuContributor"));

		JSONObject nameJSONObject = productOptionJSONObject.getJSONObject(
			"name");

		Assert.assertEquals("Color", nameJSONObject.getString("en_US"));

		jsonArray = productOptionJSONObject.getJSONArray("productOptionValues");

		Assert.assertEquals(jsonArray.toString(), 2, jsonArray.length());

		JSONObject productOptionValueJSONObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			"red", productOptionValueJSONObject.getString("key"));
		Assert.assertEquals(0, productOptionValueJSONObject.getInt("quantity"));

		productOptionValueJSONObject = jsonArray.getJSONObject(1);

		Assert.assertEquals(
			"blue", productOptionValueJSONObject.getString("key"));

		jsonArray = _getSkuJSONObject(
			0, jsonObject
		).getJSONArray(
			"skuOptions"
		);

		Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());

		JSONObject skuOptionJSONObject = jsonArray.getJSONObject(0);

		Assert.assertEquals("color", skuOptionJSONObject.getString("key"));
		Assert.assertEquals("red", skuOptionJSONObject.getString("value"));
	}

	private void _testExecuteWithProductSpecifications() throws Exception {
		_mockObjectField("Color", "color");

		JSONObject jsonObject = _getProductJSONObject(
			"SKU-1",
			_getRequiredObjectEntries(
				_mockPIMFieldMappingObjectEntry(
					"productSpecifications", StringPool.BLANK, "color",
					PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
					StringPool.BLANK),
				_mockPIMFieldMappingObjectEntry(
					"productSpecifications", StringPool.BLANK, "color",
					PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
					StringPool.BLANK),
				_mockPIMFieldMappingObjectEntry(
					"productSpecifications", StringPool.BLANK, StringPool.BLANK,
					PIMConnectorFieldMappingTypeConstants.FIXED_VALUE, "Red")),
			Collections.<String, ObjectEntry>emptyMap(),
			_mockPIMProductObjectEntry(
				"SKU-1",
				HashMapBuilder.<String, Serializable>put(
					"color", "Red"
				).build()));

		JSONArray jsonArray = jsonObject.getJSONArray("productSpecifications");

		Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());

		JSONObject productSpecificationJSONObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(
			0, productSpecificationJSONObject.getInt("priority"));
		Assert.assertEquals(
			"color",
			productSpecificationJSONObject.getString("specificationKey"));

		JSONObject labelJSONObject =
			productSpecificationJSONObject.getJSONObject("label");

		Assert.assertEquals("Color", labelJSONObject.getString("en_US"));
	}

	private void _testExecuteWithTags() throws Exception {
		String sourceFieldName = RandomTestUtil.randomString();
		String tag1 = RandomTestUtil.randomString();

		ObjectEntry pimProductObjectEntry1 = _mockPIMProductObjectEntry(
			"SKU-1",
			HashMapBuilder.<String, Serializable>put(
				sourceFieldName, tag1
			).build());

		String tag2 = RandomTestUtil.randomString();

		_mockAssetTags(pimProductObjectEntry1, tag1, tag2);

		ObjectEntry pimProductObjectEntry2 = _mockPIMProductObjectEntry(
			"SKU-2", Collections.<String, Serializable>emptyMap());

		_mockAssetTags(pimProductObjectEntry2, RandomTestUtil.randomString());

		List<ObjectEntry> pimFieldMappingObjectEntries =
			_getRequiredObjectEntries(
				_mockPIMFieldMappingObjectEntry(
					"tags", StringPool.BLANK, sourceFieldName,
					PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
					StringPool.BLANK),
				_mockPIMFieldMappingObjectEntry(
					"tags", StringPool.BLANK, StringPool.BLANK,
					PIMConnectorFieldMappingTypeConstants.TAGS,
					StringPool.BLANK));

		JSONObject jsonObject = _getProductJSONObject(
			"SKU-1", pimFieldMappingObjectEntries,
			Collections.<String, ObjectEntry>emptyMap(), pimProductObjectEntry1,
			pimProductObjectEntry2);

		Assert.assertEquals(
			Arrays.asList(tag1, tag2),
			JSONUtil.toStringList(jsonObject.getJSONArray("tags")));

		jsonObject = _getProductJSONObject(
			"SKU-1", pimFieldMappingObjectEntries,
			HashMapBuilder.put(
				"tags",
				_mockPIMFieldMappingRuleObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"useFirstNonemptySource", true
					).build())
			).build(),
			pimProductObjectEntry1);

		Assert.assertEquals(
			Arrays.asList(tag1),
			JSONUtil.toStringList(jsonObject.getJSONArray("tags")));

		pimProductObjectEntry1 = _mockPIMProductObjectEntry(
			"SKU-1", Collections.<String, Serializable>emptyMap());

		_mockAssetTags(pimProductObjectEntry1);

		jsonObject = _getProductJSONObject(
			"SKU-1",
			_getRequiredObjectEntries(
				_mockPIMFieldMappingObjectEntry(
					"tags", StringPool.BLANK, StringPool.BLANK,
					PIMConnectorFieldMappingTypeConstants.TAGS,
					StringPool.BLANK)),
			Collections.<String, ObjectEntry>emptyMap(),
			pimProductObjectEntry1);

		JSONArray jsonArray = jsonObject.getJSONArray("tags");

		Assert.assertEquals(jsonArray.toString(), 0, jsonArray.length());
	}

	private void _testExecuteWithTopicVocabulary() throws Exception {
		String sourceFieldName = RandomTestUtil.randomString();
		String value1 = StringBundler.concat(
			RandomTestUtil.randomString(), StringPool.SLASH,
			RandomTestUtil.randomString());

		ObjectEntry pimProductObjectEntry = _mockPIMProductObjectEntry(
			"SKU-1",
			HashMapBuilder.<String, Serializable>put(
				sourceFieldName, value1
			).build());

		AssetVocabulary assetVocabulary = _mockAssetVocabulary(
			AssetVocabularyConstants.EXTERNAL_REFERENCE_CODE_TOPIC,
			AssetVocabularyConstants.VISIBILITY_TYPE_PUBLIC);

		AssetCategory assetCategory = _mockAssetCategory(
			assetVocabulary, RandomTestUtil.randomString());

		_mockAssetCategories(pimProductObjectEntry, assetCategory);

		Group group = Mockito.mock(Group.class);
		long groupId = RandomTestUtil.randomLong();

		Mockito.when(
			group.getGroupId()
		).thenReturn(
			groupId
		);

		Mockito.when(
			_groupLocalService.getCompanyGroup(Mockito.anyLong())
		).thenReturn(
			group
		);

		Mockito.when(
			_assetVocabularyLocalService.
				getAssetVocabularyByExternalReferenceCode(
					AssetVocabularyConstants.EXTERNAL_REFERENCE_CODE_TOPIC,
					groupId)
		).thenReturn(
			assetVocabulary
		);

		String value2 = StringUtil.toLowerCase(RandomTestUtil.randomString(76));

		List<ObjectEntry> pimFieldMappingObjectEntries =
			_getRequiredObjectEntries(
				_mockPIMFieldMappingObjectEntry(
					"categories", StringPool.BLANK, sourceFieldName,
					PIMConnectorFieldMappingTypeConstants.DYNAMIC_VALUE,
					StringPool.BLANK),
				_mockPIMFieldMappingObjectEntry(
					"categories", StringPool.BLANK, StringPool.BLANK,
					PIMConnectorFieldMappingTypeConstants.FIXED_VALUE, value2),
				_mockPIMFieldMappingObjectEntry(
					"categories", StringPool.BLANK, StringPool.BLANK,
					PIMConnectorFieldMappingTypeConstants.CATEGORIES,
					StringPool.BLANK));

		JSONObject jsonObject1 = _getProductJSONObject(
			"SKU-1", pimFieldMappingObjectEntries,
			HashMapBuilder.put(
				"categories",
				_mockPIMFieldMappingRuleObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"textCase", "upper"
					).build())
			).build(),
			pimProductObjectEntry);

		String externalReferenceCode1 = StringUtil.toLowerCase(
			StringUtil.replace(value1, CharPool.SLASH, CharPool.DASH));
		String externalReferenceCode2 = PortalUUIDUtil.generate(
			value2.getBytes());

		Assert.assertEquals(
			Arrays.asList(
				externalReferenceCode1, externalReferenceCode2,
				assetCategory.getExternalReferenceCode()),
			JSONUtil.toStringList(
				jsonObject1.getJSONArray("categories"),
				"externalReferenceCode"));

		Assert.assertEquals(
			SetUtil.fromArray(
				"GET /taxonomy-vocabularies/by-external-reference-code/L_TOPIC",
				"PUT /taxonomy-categories/by-external-reference-code/" +
					assetCategory.getExternalReferenceCode(),
				"PUT /taxonomy-categories/by-external-reference-code/" +
					externalReferenceCode1,
				"PUT /taxonomy-categories/by-external-reference-code/" +
					externalReferenceCode2),
			SetUtil.fromList(_getTaxonomyPaths()));

		jsonObject1 = _getBodyContentJSONObject(
			"/taxonomy-categories/by-external-reference-code/" +
				assetCategory.getExternalReferenceCode());

		Assert.assertEquals(
			assetCategory.getTitle(LocaleUtil.US),
			jsonObject1.getString("name"));

		jsonObject1 = _getBodyContentJSONObject(
			"/taxonomy-categories/by-external-reference-code/" +
				externalReferenceCode1);

		Assert.assertEquals(
			StringUtil.toUpperCase(value1), jsonObject1.getString("name"));

		JSONObject jsonObject2 = jsonObject1.getJSONObject(
			"parentTaxonomyCategory");

		Assert.assertEquals(
			AssetCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			jsonObject2.getLong("id"));

		jsonObject2 = jsonObject1.getJSONObject("parentTaxonomyVocabulary");

		Assert.assertEquals(
			AssetVocabularyConstants.EXTERNAL_REFERENCE_CODE_TOPIC,
			jsonObject2.getString("externalReferenceCode"));

		Assert.assertEquals("Anyone", jsonObject1.getString("viewableBy"));

		_mockHttp(
			HashMapBuilder.put(
				"GET https://example.com/o/headless-admin-taxonomy", 404
			).build());

		jsonObject1 = _getProductJSONObject(
			"SKU-1", pimFieldMappingObjectEntries,
			HashMapBuilder.put(
				"categories",
				_mockPIMFieldMappingRuleObjectEntry(
					HashMapBuilder.<String, Serializable>put(
						"useFirstNonemptySource", true
					).build())
			).build(),
			_mockPIMProductObjectEntry(
				"SKU-1", Collections.<String, Serializable>emptyMap()));

		Assert.assertEquals(
			Arrays.asList(externalReferenceCode2),
			JSONUtil.toStringList(
				jsonObject1.getJSONArray("categories"),
				"externalReferenceCode"));

		Assert.assertEquals(
			Arrays.asList(
				"GET /taxonomy-vocabularies/by-external-reference-code/L_TOPIC",
				"PUT /taxonomy-vocabularies/by-external-reference-code/L_TOPIC",
				"PUT /taxonomy-categories/by-external-reference-code/" +
					externalReferenceCode2),
			_getTaxonomyPaths());

		_mockHttp(Collections.emptyMap());
	}

	private void _testExecuteWithTrailingSlashURL() throws Exception {
		_execute(
			HashMapBuilder.<String, Serializable>put(
				"clientId", RandomTestUtil.randomString()
			).put(
				"clientSecret", RandomTestUtil.randomString()
			).put(
				"url", "https://example.com//"
			).build(),
			_getRequiredObjectEntries(),
			Collections.<String, ObjectEntry>emptyMap(),
			HashMapBuilder.put(
				"SKU-1",
				Arrays.asList(
					_mockPIMProductObjectEntry(
						"SKU-1",
						HashMapBuilder.<String, Serializable>put(
							"sku", "SKU-1"
						).put(
							"title", RandomTestUtil.randomString()
						).build()))
			).build());

		Http.Options options = _getOptions("/o/oauth2/token");

		Assert.assertEquals(
			"https://example.com/o/oauth2/token", options.getLocation());
	}

	private void _testExecuteWithVariantPIMLinks() throws Exception {
		JSONObject jsonObject = _getProductJSONObject(
			"CLUSTER-1", _getRequiredObjectEntries(),
			Collections.<String, ObjectEntry>emptyMap(),
			_mockPIMProductObjectEntry(
				"SKU-1", Collections.<String, Serializable>emptyMap()),
			_mockPIMProductObjectEntry(
				"SKU-2", Collections.<String, Serializable>emptyMap()));

		Assert.assertEquals(
			"CLUSTER-1", jsonObject.getString("externalReferenceCode"));

		JSONObject skuJSONObject = _getSkuJSONObject(0, jsonObject);

		Assert.assertEquals(
			"SKU-1", skuJSONObject.getString("externalReferenceCode"));
		Assert.assertEquals("SKU-1", skuJSONObject.getString("sku"));

		skuJSONObject = _getSkuJSONObject(1, jsonObject);

		Assert.assertEquals(
			"SKU-2", skuJSONObject.getString("externalReferenceCode"));
		Assert.assertEquals("SKU-2", skuJSONObject.getString("sku"));
	}

	private void _testExecuteWithoutUnitOfMeasure() throws Exception {
		JSONObject jsonObject = _getProductJSONObject(
			"SKU-1", _getRequiredObjectEntries(),
			Collections.<String, ObjectEntry>emptyMap(),
			_mockPIMProductObjectEntry(
				"SKU-1", Collections.<String, Serializable>emptyMap()));

		JSONObject skuJSONObject = _getSkuJSONObject(0, jsonObject);

		Assert.assertFalse(
			skuJSONObject.toString(), skuJSONObject.has("skuUnitOfMeasures"));
	}

	private static final String _CATALOG_EXTERNAL_REFERENCE_CODE_1 =
		RandomTestUtil.randomString();

	private static final String _CATALOG_EXTERNAL_REFERENCE_CODE_2 =
		RandomTestUtil.randomString();

	private static final String _CLASS_NAME =
		"com.liferay.object.model.ObjectDefinition#PIMBasicProduct";

	private static final long _LIST_TYPE_DEFINITION_ID =
		RandomTestUtil.randomLong();

	private static final long _OBJECT_DEFINITION_ID =
		RandomTestUtil.randomLong();

	private static final Map<String, Serializable>
		_pimConnectorConfigurationValues =
			HashMapBuilder.<String, Serializable>put(
				"clientId", RandomTestUtil.randomString()
			).put(
				"clientSecret", RandomTestUtil.randomString()
			).put(
				"url", "https://example.com"
			).build();

	private final AssetCategoryLocalService _assetCategoryLocalService =
		Mockito.mock(AssetCategoryLocalService.class);
	private final AssetTagLocalService _assetTagLocalService = Mockito.mock(
		AssetTagLocalService.class);
	private final AssetVocabularyLocalService _assetVocabularyLocalService =
		Mockito.mock(AssetVocabularyLocalService.class);
	private final FriendlyURLNormalizer _friendlyURLNormalizer = Mockito.mock(
		FriendlyURLNormalizer.class);
	private final GroupLocalService _groupLocalService = Mockito.mock(
		GroupLocalService.class);
	private final MockedStatic<HtmlParserUtil> _htmlParserUtilMockedStatic =
		Mockito.mockStatic(HtmlParserUtil.class);
	private final Http _http = Mockito.mock(Http.class);
	private final Language _language = Mockito.mock(Language.class);
	private final LiferayCommercePIMConnector _liferayCommercePIMConnector =
		Mockito.spy(new LiferayCommercePIMConnector());
	private final MockedStatic<ListTypeEntryLocalServiceUtil>
		_listTypeEntryLocalServiceUtilMockedStatic = Mockito.mockStatic(
			ListTypeEntryLocalServiceUtil.class);
	private final MockedStatic<ModelHintsUtil> _modelHintsUtilMockedStatic =
		Mockito.mockStatic(ModelHintsUtil.class);
	private final ObjectDefinition _objectDefinition = Mockito.mock(
		ObjectDefinition.class);
	private final ObjectDefinitionLocalService _objectDefinitionLocalService =
		Mockito.mock(ObjectDefinitionLocalService.class);
	private final ObjectEntryLocalService _objectEntryLocalService =
		Mockito.mock(ObjectEntryLocalService.class);
	private final MockedStatic<ObjectFieldLocalServiceUtil>
		_objectFieldLocalServiceUtilMockedStatic = Mockito.mockStatic(
			ObjectFieldLocalServiceUtil.class);
	private final List<Http.Options> _options = new ArrayList<>();
	private final ObjectEntry _pimConnectorObjectEntry = Mockito.mock(
		ObjectEntry.class);

}