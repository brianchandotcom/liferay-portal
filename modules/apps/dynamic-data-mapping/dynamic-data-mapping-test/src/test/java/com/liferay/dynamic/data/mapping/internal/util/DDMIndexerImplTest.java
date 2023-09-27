/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.util;

import com.liferay.dynamic.data.mapping.configuration.DDMIndexerConfiguration;
import com.liferay.dynamic.data.mapping.form.field.type.DDMFormFieldTypeServicesRegistry;
import com.liferay.dynamic.data.mapping.internal.io.DDMFormJSONDeserializer;
import com.liferay.dynamic.data.mapping.internal.io.DDMFormJSONSerializer;
import com.liferay.dynamic.data.mapping.internal.test.util.DDMFixture;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormSerializerSerializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.impl.DDMStructureImpl;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMIndexer;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.PropsTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.engine.ConnectionInformation;
import com.liferay.portal.search.engine.SearchEngineInformation;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.indexing.DocumentFixture;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Lino Alves
 * @author André de Oliveira
 */
public class DDMIndexerImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() throws Exception {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		Mockito.when(
			FrameworkUtil.getBundle(Mockito.any())
		).thenReturn(
			bundleContext.getBundle()
		);

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put("ddm.form.deserializer.type", "json");

		_ddmFormDeserializerServiceRegistration = bundleContext.registerService(
			DDMFormDeserializer.class, _ddmFormDeserializer, properties);
	}

	@AfterClass
	public static void tearDownClass() {
		_ddmFormDeserializerServiceRegistration.unregister();

		_frameworkUtilMockedStatic.close();
	}

	@Before
	public void setUp() throws Exception {
		_ddmFixture.setUp();
		_documentFixture.setUp();

		_setUpPortalUtil();
		_setUpPropsUtil();

		_ddmIndexer = _createDDMIndexer();
	}

	@After
	public void tearDown() {
		_ddmFixture.tearDown();
		_documentFixture.tearDown();
	}

	@Test
	public void testExtractIndexableAttributes() {
		_testExtractIndexableAttributes(
			_createDDMFormField(), StringPool.BLANK);
		_testExtractIndexableAttributes(_createDDMFormField(), "Create New");
		_testExtractIndexableAttributes(_createDDMFormField(), "null");

		DDMFormField ddmFormField = _createDDMFormField();

		ddmFormField.setRepeatable(true);

		_testExtractIndexableAttributes(ddmFormField, StringPool.BLANK);
	}

	@Test
	public void testFormWithOneAvailableLocaleSameAsDefaultLocale() {
		Document document = _createDocument();

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			SetUtil.fromArray(LocaleUtil.JAPAN), LocaleUtil.JAPAN);

		ddmForm.addDDMFormField(_createDDMFormField());

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.JAPAN);

		localizedValue.addString(LocaleUtil.JAPAN, "新規作成");

		_ddmIndexer.addAttributes(
			document, _createDDMStructure(ddmForm),
			_createDDMFormValues(
				ddmForm,
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					_FIELD_NAME, localizedValue)));

		FieldValuesAssert.assertFieldValues(
			_getSortableValues(
				Collections.singletonMap(
					"ddmFieldArray.ddmFieldValueText_ja_JP", "新規作成")),
			"ddmFieldArray.ddmFieldValueText", document, "新規作成");
	}

	@Test
	public void testFormWithTwoAvailableLocalesAndFieldWithNondefaultLocale() {
		Document document = _createDocument();

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			SetUtil.fromArray(LocaleUtil.US, LocaleUtil.JAPAN), LocaleUtil.US);

		ddmForm.addDDMFormField(_createDDMFormField());

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(LocaleUtil.JAPAN, "新規作成");

		_ddmIndexer.addAttributes(
			document, _createDDMStructure(ddmForm),
			_createDDMFormValues(
				ddmForm,
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					_FIELD_NAME, localizedValue)));

		FieldValuesAssert.assertFieldValues(
			_getSortableValues(
				Collections.singletonMap(
					"ddmFieldArray.ddmFieldValueText_ja_JP", "新規作成")),
			"ddmFieldArray.ddmFieldValueText", document, "新規作成");
	}

	@Test
	public void testFormWithTwoAvailableLocalesAndFieldWithTwoLocales() {
		Document document = _createDocument();

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			SetUtil.fromArray(LocaleUtil.JAPAN, LocaleUtil.US),
			LocaleUtil.JAPAN);

		ddmForm.addDDMFormField(_createDDMFormField());

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.JAPAN);

		localizedValue.addString(LocaleUtil.JAPAN, "新規作成");
		localizedValue.addString(LocaleUtil.US, "Create New");

		_ddmIndexer.addAttributes(
			document, _createDDMStructure(ddmForm),
			_createDDMFormValues(
				ddmForm,
				DDMFormValuesTestUtil.createDDMFormFieldValue(
					_FIELD_NAME, localizedValue)));

		FieldValuesAssert.assertFieldValues(
			_getSortableValues(
				HashMapBuilder.put(
					"ddmFieldArray.ddmFieldValueText_en_US", "Create New"
				).put(
					"ddmFieldArray.ddmFieldValueText_ja_JP", "新規作成"
				).build()),
			"ddmFieldArray.ddmFieldValueText", document, "新規作成");
	}

	private DDMFormField _createDDMFormField() {
		DDMFormField ddmFormField = DDMFormTestUtil.createTextDDMFormField(
			_FIELD_NAME, true, false, true);

		ddmFormField.setIndexType("text");

		return ddmFormField;
	}

	private DDMFormJSONSerializer _createDDMFormJSONSerializer() {
		return new DDMFormJSONSerializer() {
			{
				ReflectionTestUtil.setFieldValue(
					this, "_ddmFormFieldTypeServicesRegistry",
					Mockito.mock(DDMFormFieldTypeServicesRegistry.class));
				ReflectionTestUtil.setFieldValue(
					this, "_jsonFactory", new JSONFactoryImpl());
			}
		};
	}

	private DDMFormValues _createDDMFormValues(
		DDMForm ddmForm, DDMFormFieldValue ddmFormFieldValue) {

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		return ddmFormValues;
	}

	private DDMIndexer _createDDMIndexer() {
		return new DDMIndexerImpl() {
			{
				DDMIndexerConfiguration ddmIndexerConfiguration = () -> false;

				ReflectionTestUtil.setFieldValue(
					this, "_ddmFormValuesToFieldsConverter",
					new DDMFormValuesToFieldsConverterImpl());
				ReflectionTestUtil.setFieldValue(
					this, "_ddmIndexerConfiguration", ddmIndexerConfiguration);

				searchEngineInformation = new SearchEngineInformation() {

					public String getClientVersionString() {
						return null;
					}

					public List<ConnectionInformation>
						getConnectionInformationList() {

						return null;
					}

					public String getNodesString() {
						return null;
					}

					public String getVendorString() {
						return null;
					}

				};
			}
		};
	}

	private DDMStructure _createDDMStructure(DDMForm ddmForm) {
		DDMStructure ddmStructure = new DDMStructureImpl();

		DDMFormSerializerSerializeRequest.Builder builder =
			DDMFormSerializerSerializeRequest.Builder.newBuilder(ddmForm);

		DDMFormSerializerSerializeResponse ddmFormSerializerSerializeResponse =
			_ddmFormJSONSerializer.serialize(builder.build());

		ddmStructure.setDefinition(
			ddmFormSerializerSerializeResponse.getContent());

		ddmStructure.setDDMForm(ddmForm);
		ddmStructure.setStructureId(RandomTestUtil.randomLong());
		ddmStructure.setName(RandomTestUtil.randomString());

		_ddmFixture.whenDDMStructureLocalServiceFetchStructure(ddmStructure);

		return ddmStructure;
	}

	private Document _createDocument() {
		return DocumentFixture.newDocument(
			RandomTestUtil.randomLong(), RandomTestUtil.randomLong(),
			DDMForm.class.getName());
	}

	private Map<String, String> _getSortableValues(Map<String, String> map) {
		Map<String, String> sortableValues = new HashMap<>();

		for (Map.Entry<String, String> entry : map.entrySet()) {
			sortableValues.put(
				entry.getKey() + "_String_sortable",
				StringUtil.toLowerCase(entry.getValue()));
		}

		sortableValues.putAll(map);

		return sortableValues;
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = Mockito.mock(Portal.class);

		ResourceBundle resourceBundle = Mockito.mock(ResourceBundle.class);

		Mockito.when(
			portal.getResourceBundle(Mockito.any(Locale.class))
		).thenReturn(
			resourceBundle
		);

		portalUtil.setPortal(portal);
	}

	private void _setUpPropsUtil() {
		PropsTestUtil.setProps(
			PropsKeys.INDEX_SORTABLE_TEXT_FIELDS_TRUNCATED_LENGTH, "255");
	}

	private void _testExtractIndexableAttributes(
		DDMFormField ddmFormField, String fieldValue) {

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			SetUtil.fromArray(LocaleUtil.US), LocaleUtil.US);

		ddmForm.addDDMFormField(ddmFormField);

		Assert.assertEquals(
			fieldValue,
			_ddmIndexer.extractIndexableAttributes(
				_createDDMStructure(ddmForm),
				_createDDMFormValues(
					ddmForm,
					DDMFormValuesTestUtil.createDDMFormFieldValue(
						_FIELD_NAME,
						DDMFormValuesTestUtil.createLocalizedValue(
							fieldValue, LocaleUtil.US))),
				LocaleUtil.US));
	}

	private static final String _FIELD_NAME = RandomTestUtil.randomString();

	private static final DDMFormDeserializer _ddmFormDeserializer =
		new DDMFormJSONDeserializer();
	private static ServiceRegistration<DDMFormDeserializer>
		_ddmFormDeserializerServiceRegistration;
	private static final MockedStatic<FrameworkUtil>
		_frameworkUtilMockedStatic = Mockito.mockStatic(FrameworkUtil.class);

	private final DDMFixture _ddmFixture = new DDMFixture();
	private final DDMFormJSONSerializer _ddmFormJSONSerializer =
		_createDDMFormJSONSerializer();
	private DDMIndexer _ddmIndexer;
	private final DocumentFixture _documentFixture = new DocumentFixture();

}