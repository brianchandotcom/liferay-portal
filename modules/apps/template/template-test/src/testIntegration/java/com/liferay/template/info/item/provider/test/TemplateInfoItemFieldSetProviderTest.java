/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.template.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.kernel.service.AssetCategoryLocalService;
import com.liferay.asset.kernel.service.AssetVocabularyLocalService;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMFormFieldOptions;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.util.DDMFormValuesToFieldsConverter;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.HTMLInfoFieldType;
import com.liferay.info.localized.bundle.FunctionInfoLocalizedValue;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.journal.util.JournalConverter;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutSet;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.test.util.TemplateTestUtil;

import java.text.DateFormat;

import java.time.chrono.IsoChronology;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class TemplateInfoItemFieldSetProviderTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	public boolean isHTMLInfoFieldType(InfoField infoField) {
		if (infoField.getInfoFieldType() instanceof HTMLInfoFieldType) {
			return true;
		}

		return false;
	}

	@Before
	public void setUp() throws Exception {
		_originalServiceContext = ServiceContextThreadLocal.getServiceContext();
		_originalSiteDefaultLocale = LocaleThreadLocal.getSiteDefaultLocale();
		_originalThemeDisplayLocale = LocaleThreadLocal.getThemeDisplayLocale();

		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());
		_layout = LayoutTestUtil.addTypePortletLayout(_group);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		_serviceContext.setCompanyId(TestPropsValues.getCompanyId());

		_serviceContext.setRequest(
			_getMockHttpServletRequest(_getThemeDisplay()));

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		_journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	@After
	public void tearDown() {
		ServiceContextThreadLocal.pushServiceContext(_originalServiceContext);
		LocaleThreadLocal.setSiteDefaultLocale(_originalSiteDefaultLocale);
		LocaleThreadLocal.setThemeDisplayLocale(_originalThemeDisplayLocale);
	}

	@Test
	public void testGetInfoFieldSetByClassNameAndVariationKeyWhenNoTemplateEntryExists() {
		InfoFieldSet infoFieldSet =
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				JournalArticle.class.getName(),
				String.valueOf(_journalArticle.getDDMStructureId()));

		List<InfoField<?>> infoFields = infoFieldSet.getAllInfoFields();

		Assert.assertTrue(infoFields.isEmpty());
	}

	@Test
	public void testGetInfoFieldSetByClassNameAndVariationKeyWhenTemplateEntryExists()
		throws PortalException {

		TemplateEntry journalArticleTemplateEntry =
			TemplateTestUtil.addTemplateEntry(
				JournalArticle.class.getName(),
				String.valueOf(_journalArticle.getDDMStructureId()),
				_serviceContext);

		TemplateTestUtil.addTemplateEntry(
			AssetCategory.class.getName(), StringPool.BLANK, _serviceContext);

		InfoFieldSet infoFieldSet =
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				JournalArticle.class.getName(),
				String.valueOf(_journalArticle.getDDMStructureId()));

		List<InfoField<?>> infoFields = infoFieldSet.getAllInfoFields();

		Assert.assertEquals(infoFields.toString(), 1, infoFields.size());

		InfoField infoField = infoFields.get(0);

		Assert.assertTrue(
			infoField.getInfoFieldType() instanceof HTMLInfoFieldType);
		Assert.assertEquals(
			infoFields.toString(),
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				journalArticleTemplateEntry.getTemplateEntryId(),
			infoField.getName());
	}

	@Test
	public void testGetInfoFieldSetByClassNameWhenNoTemplateEntryExists() {
		InfoFieldSet infoFieldSet =
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				AssetCategory.class.getName());

		List<InfoField<?>> infoFields = infoFieldSet.getAllInfoFields();

		Assert.assertTrue(infoFields.isEmpty());
	}

	@Test
	public void testGetInfoFieldSetByClassNameWhenTemplateEntryExists()
		throws PortalException {

		TemplateTestUtil.addTemplateEntry(
			JournalArticle.class.getName(),
			String.valueOf(_journalArticle.getDDMStructureId()),
			_serviceContext);

		TemplateEntry categoryTemplateEntry = TemplateTestUtil.addTemplateEntry(
			AssetCategory.class.getName(), StringPool.BLANK, _serviceContext);

		InfoFieldSet infoFieldSet =
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				AssetCategory.class.getName());

		List<InfoField<?>> infoFields = infoFieldSet.getAllInfoFields();

		Assert.assertEquals(infoFields.toString(), 1, infoFields.size());

		InfoField infoField = infoFields.get(0);

		Assert.assertTrue(
			infoField.getInfoFieldType() instanceof HTMLInfoFieldType);
		Assert.assertEquals(
			infoFields.toString(),
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				categoryTemplateEntry.getTemplateEntryId(),
			infoField.getName());
	}

	@Test
	public void testGetInfoFieldValuesByClassNameAndVariationKeyWhenNoTemplateEntryExists() {
		List<InfoFieldValue<Object>> infoFieldValues =
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				JournalArticle.class.getName(),
				String.valueOf(_journalArticle.getDDMStructureId()),
				_journalArticle);

		Assert.assertTrue(infoFieldValues.isEmpty());
	}

	@Test
	public void testGetInfoFieldValuesByClassNameAndVariationKeyWhenTemplateEntryExists()
		throws PortalException {

		DDMFormValues ddmFormValues = _journalArticle.getDDMFormValues();

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap(false);

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			"name");

		DDMFormFieldValue nameDDMFormFieldValue = ddmFormFieldValues.get(0);

		Value nameValue = nameDDMFormFieldValue.getValue();

		TemplateEntry journalArticleTemplateEntry =
			TemplateTestUtil.addTemplateEntry(
				JournalArticle.class.getName(),
				String.valueOf(_journalArticle.getDDMStructureId()),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				JournalTestUtil.getSampleTemplateFTL(), _serviceContext);

		TemplateTestUtil.addTemplateEntry(
			AssetCategory.class.getName(), StringPool.BLANK, _serviceContext);

		List<InfoFieldValue<Object>> infoFieldValues =
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				JournalArticle.class.getName(),
				String.valueOf(_journalArticle.getDDMStructureId()),
				_journalArticle);

		Assert.assertEquals(
			infoFieldValues.toString(), 1, infoFieldValues.size());

		InfoFieldValue<Object> infoFieldValue = infoFieldValues.get(0);

		InfoField infoField = infoFieldValue.getInfoField();

		Assert.assertTrue(
			infoField.getInfoFieldType() instanceof HTMLInfoFieldType);
		Assert.assertEquals(
			infoField.toString(),
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				journalArticleTemplateEntry.getTemplateEntryId(),
			infoField.getName());

		Object value = infoFieldValue.getValue();

		Assert.assertNotNull(value);
		Assert.assertTrue(
			value.toString(), value instanceof FunctionInfoLocalizedValue<?>);

		FunctionInfoLocalizedValue functionInfoLocalizedValue =
			(FunctionInfoLocalizedValue)value;

		Assert.assertEquals(
			infoFieldValue.toString(),
			nameValue.getString(
				_portal.getSiteDefaultLocale(_group.getGroupId())),
			functionInfoLocalizedValue.getValue());
	}

	@Test
	public void testGetInfoFieldValuesByClassNameWhenNoTemplateEntryExists()
		throws Exception {

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		List<InfoFieldValue<Object>> infoFieldValues =
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				AssetCategory.class.getName(), assetCategory);

		Assert.assertTrue(infoFieldValues.isEmpty());
	}

	@Test
	public void testGetInfoFieldValuesByClassNameWhenTemplateEntryExists()
		throws Exception {

		TemplateTestUtil.addTemplateEntry(
			JournalArticle.class.getName(),
			String.valueOf(_journalArticle.getDDMStructureId()),
			_serviceContext);

		AssetVocabulary assetVocabulary = AssetTestUtil.addVocabulary(
			_group.getGroupId());

		AssetCategory assetCategory = AssetTestUtil.addCategory(
			_group.getGroupId(), assetVocabulary.getVocabularyId());

		TemplateEntry categoryTemplateEntry = TemplateTestUtil.addTemplateEntry(
			AssetCategory.class.getName(), StringPool.BLANK,
			assetCategory.getName(), RandomTestUtil.randomString(),
			JournalTestUtil.getSampleTemplateFTL(), _serviceContext);

		List<InfoFieldValue<Object>> infoFieldValues =
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				AssetCategory.class.getName(), assetCategory);

		Assert.assertEquals(
			infoFieldValues.toString(), 1, infoFieldValues.size());

		InfoFieldValue<Object> infoFieldValue = infoFieldValues.get(0);

		InfoField<?> infoField = infoFieldValue.getInfoField();

		Assert.assertEquals(
			infoField.toString(),
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				categoryTemplateEntry.getTemplateEntryId(),
			infoField.getName());

		Assert.assertEquals(
			infoFieldValue.toString(), assetCategory.getName(),
			infoFieldValue.getValue(
				_portal.getSiteDefaultLocale(_group.getGroupId())));
	}

	@Test
	public void testGetInfoFieldValuesLocalizedContent() throws Exception {
		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			ListUtil.fromArray(
				LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US),
			LocaleUtil.SPAIN);

		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.SPAIN);

		Map<Locale, String> contentMap = _getRandomLocalizedMap();

		_journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
			_getRandomLocalizedMap(), _getRandomLocalizedMap(), contentMap,
			LocaleUtil.SPAIN, false, false, _serviceContext);

		_assertLocalizedValues(
			HashMapBuilder.put(
				LocaleUtil.GERMANY, contentMap.get(LocaleUtil.SPAIN)
			).put(
				LocaleUtil.SPAIN, contentMap.get(LocaleUtil.SPAIN)
			).put(
				LocaleUtil.US, contentMap.get(LocaleUtil.US)
			).build(),
			"name");
	}

	@Test
	public void testGetInfoFieldValuesLocalizedContentDifferentDefaultLocale()
		throws Exception {

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			ListUtil.fromArray(
				LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US),
			LocaleUtil.SPAIN);

		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.SPAIN);

		Map<Locale, String> contentMap = _getRandomLocalizedMap();

		_journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(), 0,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT,
			_getRandomLocalizedMap(), _getRandomLocalizedMap(), contentMap,
			LocaleUtil.US, false, false, _serviceContext);

		_assertLocalizedValues(
			HashMapBuilder.put(
				LocaleUtil.GERMANY, contentMap.get(LocaleUtil.US)
			).put(
				LocaleUtil.SPAIN, contentMap.get(LocaleUtil.SPAIN)
			).put(
				LocaleUtil.US, contentMap.get(LocaleUtil.US)
			).build(),
			"name");
	}

	@Test
	public void testGetInfoFieldValuesRenderingCategoriesInfoFieldType()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		AssetVocabulary assetVocabulary =
			_assetVocabularyLocalService.addVocabulary(
				TestPropsValues.getUserId(), _group.getGroupId(),
				RandomTestUtil.randomString(), serviceContext);

		AssetCategory assetCategory1 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), assetVocabulary.getVocabularyId(),
			serviceContext);
		AssetCategory assetCategory2 = _assetCategoryLocalService.addCategory(
			TestPropsValues.getUserId(), _group.getGroupId(),
			RandomTestUtil.randomString(), assetVocabulary.getVocabularyId(),
			serviceContext);

		serviceContext.setAssetCategoryIds(
			new long[] {
				assetCategory1.getCategoryId(), assetCategory2.getCategoryId()
			});

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);

		TemplateEntry journalArticleTemplateEntry =
			TemplateTestUtil.addTemplateEntry(
				JournalArticle.class.getName(),
				String.valueOf(journalArticle.getDDMStructureId()),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				TemplateTestUtil.getRepeatableFieldSampleScriptFTL(
					"categories"),
				_serviceContext);

		List<InfoFieldValue<Object>> infoFieldValues =
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				JournalArticle.class.getName(),
				String.valueOf(journalArticle.getDDMStructureId()),
				journalArticle);

		Assert.assertEquals(
			infoFieldValues.toString(), 1, infoFieldValues.size());

		InfoFieldValue<Object> infoFieldValue = infoFieldValues.get(0);

		InfoField<?> infoField = infoFieldValue.getInfoField();

		Assert.assertEquals(
			infoField.toString(),
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				journalArticleTemplateEntry.getTemplateEntryId(),
			infoField.getName());

		Locale locale = _portal.getSiteDefaultLocale(_group.getGroupId());

		_assertExpectedNames(
			(String)infoFieldValue.getValue(locale),
			assetCategory1.getTitle(locale), assetCategory2.getTitle(locale));
	}

	@Test
	public void testGetInfoFieldValuesRenderingDateInfoFieldType()
		throws Exception {

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			ListUtil.fromArray(LocaleUtil.US, LocaleUtil.SPAIN), LocaleUtil.US);

		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.US);

		TemplateEntry journalArticleTemplateEntry =
			TemplateTestUtil.addTemplateEntry(
				JournalArticle.class.getName(),
				String.valueOf(_journalArticle.getDDMStructureId()),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				TemplateTestUtil.getSampleScriptFTL("createDate"),
				_serviceContext);

		List<InfoFieldValue<Object>> infoFieldValues =
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				JournalArticle.class.getName(),
				String.valueOf(_journalArticle.getDDMStructureId()),
				_journalArticle);

		Assert.assertEquals(
			infoFieldValues.toString(), 1, infoFieldValues.size());

		InfoFieldValue<Object> infoFieldValue = infoFieldValues.get(0);

		InfoField<?> infoField = infoFieldValue.getInfoField();

		Assert.assertEquals(
			infoField.toString(),
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				journalArticleTemplateEntry.getTemplateEntryId(),
			infoField.getName());

		String value = (String)infoFieldValue.getValue(LocaleUtil.US);

		Assert.assertNotNull(value);
		Assert.assertEquals(
			DateUtil.getDate(
				_journalArticle.getCreateDate(),
				DateTimeFormatterBuilder.getLocalizedDateTimePattern(
					FormatStyle.SHORT, FormatStyle.SHORT,
					IsoChronology.INSTANCE, LocaleUtil.US),
				LocaleUtil.US),
			value);
	}

	@Test
	public void testGetInfoFieldValuesRenderingDateInfoFieldTypeLocalizedDateFormat()
		throws Exception {

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			ListUtil.fromArray(
				LocaleUtil.GERMANY, LocaleUtil.SPAIN, LocaleUtil.US),
			LocaleUtil.SPAIN);

		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.SPAIN);

		DDMFormField ddmFormField = _createDDMFormField(
			false, Collections.emptyMap(), DDMFormFieldTypeConstants.DATE);

		Date date = new Date();

		_journalArticle = JournalTestUtil.addJournalArticle(
			_dataDefinitionResourceFactory, ddmFormField,
			_ddmFormValuesToFieldsConverter,
			DateUtil.getDate(date, "yyyy-MM-dd", LocaleUtil.SPAIN),
			_group.getGroupId(), _journalConverter);

		_assertLocalizedValues(
			HashMapBuilder.put(
				LocaleUtil.GERMANY, _formatDate(date, LocaleUtil.GERMANY)
			).put(
				LocaleUtil.SPAIN, _formatDate(date, LocaleUtil.SPAIN)
			).put(
				LocaleUtil.US, _formatDate(date, LocaleUtil.US)
			).build(),
			ddmFormField.getName());
	}

	@Test
	public void testGetInfoFieldValuesRenderingDateInfoFieldTypeSpainLocale()
		throws Exception {

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			ListUtil.fromArray(LocaleUtil.US, LocaleUtil.SPAIN), LocaleUtil.US);

		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.US);

		TemplateEntry journalArticleTemplateEntry =
			TemplateTestUtil.addTemplateEntry(
				JournalArticle.class.getName(),
				String.valueOf(_journalArticle.getDDMStructureId()),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				TemplateTestUtil.getSampleScriptFTL("createDate"),
				_serviceContext);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = serviceContext.getThemeDisplay();

		themeDisplay.setLocale(LocaleUtil.SPAIN);

		List<InfoFieldValue<Object>> infoFieldValues =
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				JournalArticle.class.getName(),
				String.valueOf(_journalArticle.getDDMStructureId()),
				_journalArticle);

		Assert.assertEquals(
			infoFieldValues.toString(), 1, infoFieldValues.size());

		InfoFieldValue<Object> infoFieldValue = infoFieldValues.get(0);

		InfoField<?> infoField = infoFieldValue.getInfoField();

		Assert.assertEquals(
			infoField.toString(),
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				journalArticleTemplateEntry.getTemplateEntryId(),
			infoField.getName());

		String value = (String)infoFieldValue.getValue(LocaleUtil.SPAIN);

		Assert.assertNotNull(value);
		Assert.assertEquals(
			DateUtil.getDate(
				_journalArticle.getCreateDate(),
				DateTimeFormatterBuilder.getLocalizedDateTimePattern(
					FormatStyle.SHORT, FormatStyle.SHORT,
					IsoChronology.INSTANCE, LocaleUtil.SPAIN),
				LocaleUtil.SPAIN),
			value);
	}

	@Test
	public void testGetInfoFieldValuesRenderingOtherListInfoFieldType()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		String tagName1 = RandomTestUtil.randomString();
		String tagName2 = RandomTestUtil.randomString();

		serviceContext.setAssetTagNames(new String[] {tagName1, tagName2});

		JournalArticle journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);

		TemplateEntry journalArticleTemplateEntry =
			TemplateTestUtil.addTemplateEntry(
				JournalArticle.class.getName(),
				String.valueOf(journalArticle.getDDMStructureId()),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				TemplateTestUtil.getRepeatableFieldSampleScriptFTL("tagNames"),
				_serviceContext);

		List<InfoFieldValue<Object>> infoFieldValues =
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				JournalArticle.class.getName(),
				String.valueOf(journalArticle.getDDMStructureId()),
				journalArticle);

		Assert.assertEquals(
			infoFieldValues.toString(), 1, infoFieldValues.size());

		InfoFieldValue<Object> infoFieldValue = infoFieldValues.get(0);

		InfoField<?> infoField = infoFieldValue.getInfoField();

		Assert.assertEquals(
			infoField.toString(),
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				journalArticleTemplateEntry.getTemplateEntryId(),
			infoField.getName());

		_assertExpectedNames(
			(String)infoFieldValue.getValue(LocaleUtil.US),
			StringUtil.toLowerCase(tagName1), StringUtil.toLowerCase(tagName2));
	}

	@Test
	public void testGetInfoFieldValuesRenderingSelectInfoFieldTypeMultipleSelection()
		throws Exception {

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			ListUtil.fromArray(LocaleUtil.US, LocaleUtil.SPAIN), LocaleUtil.US);

		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.US);

		String expectedKey1 = RandomTestUtil.randomString(10);
		String expectedKey2 = RandomTestUtil.randomString(10);

		DDMFormField ddmFormField = _createDDMFormField(
			true,
			HashMapBuilder.put(
				expectedKey1, RandomTestUtil.randomString()
			).put(
				expectedKey2, RandomTestUtil.randomString()
			).build(),
			DDMFormFieldTypeConstants.CHECKBOX_MULTIPLE);

		JournalArticle journalArticle = JournalTestUtil.addJournalArticle(
			_dataDefinitionResourceFactory, ddmFormField,
			_ddmFormValuesToFieldsConverter,
			JSONUtil.putAll(
				expectedKey1, expectedKey2
			).toString(),
			_group.getGroupId(), _journalConverter);

		TemplateEntry journalArticleTemplateEntry =
			TemplateTestUtil.addTemplateEntry(
				JournalArticle.class.getName(),
				String.valueOf(journalArticle.getDDMStructureId()),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				TemplateTestUtil.getSampleScriptFTL(
					DDMStructure.class.getSimpleName() + StringPool.UNDERLINE +
						ddmFormField.getName()),
				_serviceContext);

		List<InfoFieldValue<Object>> infoFieldValues =
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				JournalArticle.class.getName(),
				String.valueOf(journalArticle.getDDMStructureId()),
				journalArticle);

		Assert.assertEquals(
			infoFieldValues.toString(), 1, infoFieldValues.size());

		InfoFieldValue<Object> infoFieldValue = infoFieldValues.get(0);

		InfoField<?> infoField = infoFieldValue.getInfoField();

		Assert.assertEquals(
			infoField.toString(),
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				journalArticleTemplateEntry.getTemplateEntryId(),
			infoField.getName());

		String value = (String)infoFieldValue.getValue(LocaleUtil.US);

		Assert.assertNotNull(value);

		JSONArray jsonArray = _jsonFactory.createJSONArray(value);

		Assert.assertEquals(jsonArray.toString(), 2, jsonArray.length());

		String[] expectedKeys = {expectedKey1, expectedKey2};

		for (int i = 0; i < jsonArray.length(); i++) {
			Assert.assertTrue(
				ArrayUtil.contains(expectedKeys, jsonArray.getString(i)));
		}
	}

	@Test
	public void testGetInfoFieldValuesRenderingSelectInfoFieldTypeNoSelection()
		throws Exception {

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			ListUtil.fromArray(LocaleUtil.US, LocaleUtil.SPAIN), LocaleUtil.US);

		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.US);

		DDMFormField ddmFormField = _createDDMFormField(
			false,
			HashMapBuilder.put(
				RandomTestUtil.randomString(10), RandomTestUtil.randomString()
			).put(
				RandomTestUtil.randomString(10), RandomTestUtil.randomString()
			).build(),
			DDMFormFieldTypeConstants.SELECT);

		JournalArticle journalArticle = JournalTestUtil.addJournalArticle(
			_dataDefinitionResourceFactory, ddmFormField,
			_ddmFormValuesToFieldsConverter, StringPool.BLANK,
			_group.getGroupId(), _journalConverter);

		TemplateEntry journalArticleTemplateEntry =
			TemplateTestUtil.addTemplateEntry(
				JournalArticle.class.getName(),
				String.valueOf(journalArticle.getDDMStructureId()),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				TemplateTestUtil.getSampleScriptFTL(
					DDMStructure.class.getSimpleName() + StringPool.UNDERLINE +
						ddmFormField.getName()),
				_serviceContext);

		List<InfoFieldValue<Object>> infoFieldValues =
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				JournalArticle.class.getName(),
				String.valueOf(journalArticle.getDDMStructureId()),
				journalArticle);

		Assert.assertEquals(
			infoFieldValues.toString(), 1, infoFieldValues.size());

		InfoFieldValue<Object> infoFieldValue = infoFieldValues.get(0);

		InfoField<?> infoField = infoFieldValue.getInfoField();

		Assert.assertEquals(
			infoField.toString(),
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				journalArticleTemplateEntry.getTemplateEntryId(),
			infoField.getName());

		Assert.assertEquals(
			StringPool.BLANK, infoFieldValue.getValue(LocaleUtil.US));
	}

	@Test
	public void testGetInfoFieldValuesRenderingSelectInfoFieldTypeSingleSelection()
		throws Exception {

		_group = GroupTestUtil.updateDisplaySettings(
			_group.getGroupId(),
			ListUtil.fromArray(LocaleUtil.US, LocaleUtil.SPAIN), LocaleUtil.US);

		LocaleThreadLocal.setSiteDefaultLocale(LocaleUtil.US);

		String expectedKey = RandomTestUtil.randomString(10);

		DDMFormField ddmFormField = _createDDMFormField(
			false,
			HashMapBuilder.put(
				RandomTestUtil.randomString(10), RandomTestUtil.randomString()
			).put(
				expectedKey, RandomTestUtil.randomString()
			).build(),
			DDMFormFieldTypeConstants.SELECT);

		JournalArticle journalArticle = JournalTestUtil.addJournalArticle(
			_dataDefinitionResourceFactory, ddmFormField,
			_ddmFormValuesToFieldsConverter,
			JSONUtil.put(
				expectedKey
			).toString(),
			_group.getGroupId(), _journalConverter);

		TemplateEntry journalArticleTemplateEntry =
			TemplateTestUtil.addTemplateEntry(
				JournalArticle.class.getName(),
				String.valueOf(journalArticle.getDDMStructureId()),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				TemplateTestUtil.getSampleScriptFTL(
					DDMStructure.class.getSimpleName() + StringPool.UNDERLINE +
						ddmFormField.getName()),
				_serviceContext);

		List<InfoFieldValue<Object>> infoFieldValues =
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				JournalArticle.class.getName(),
				String.valueOf(journalArticle.getDDMStructureId()),
				journalArticle);

		Assert.assertEquals(
			infoFieldValues.toString(), 1, infoFieldValues.size());

		InfoFieldValue<Object> infoFieldValue = infoFieldValues.get(0);

		InfoField<?> infoField = infoFieldValue.getInfoField();

		Assert.assertEquals(
			infoField.toString(),
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				journalArticleTemplateEntry.getTemplateEntryId(),
			infoField.getName());

		Assert.assertEquals(
			expectedKey, infoFieldValue.getValue(LocaleUtil.US));
	}

	private void _assertExpectedNames(
		String currentNamesString, String... expectedNames) {

		Assert.assertNotNull(currentNamesString);

		String[] currentNames = currentNamesString.split(StringPool.COMMA);

		Assert.assertEquals(
			currentNames.toString(), expectedNames.length, currentNames.length);

		for (String expectedName : expectedNames) {
			Assert.assertTrue(ArrayUtil.contains(currentNames, expectedName));
		}
	}

	private void _assertLocalizedValues(
			Map<Locale, String> expectedValues, String fieldName)
		throws Exception {

		TemplateEntry templateEntry = TemplateTestUtil.addTemplateEntry(
			JournalArticle.class.getName(),
			String.valueOf(_journalArticle.getDDMStructureId()),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			TemplateTestUtil.getSampleScriptFTL(fieldName), _serviceContext);

		Locale currentThemeDisplayLocale =
			LocaleThreadLocal.getThemeDisplayLocale();

		List<InfoFieldValue<Object>> infoFieldValues =
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				JournalArticle.class.getName(),
				String.valueOf(_journalArticle.getDDMStructureId()),
				_journalArticle);

		Assert.assertEquals(
			currentThemeDisplayLocale,
			LocaleThreadLocal.getThemeDisplayLocale());

		Assert.assertEquals(
			infoFieldValues.toString(), 1, infoFieldValues.size());

		InfoFieldValue<Object> infoFieldValue = infoFieldValues.get(0);

		InfoField<?> infoField = infoFieldValue.getInfoField();

		Assert.assertEquals(
			infoField.toString(),
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				templateEntry.getTemplateEntryId(),
			infoField.getName());

		Object value = infoFieldValue.getValue();

		Assert.assertNotNull(value);
		Assert.assertTrue(
			value.toString(), value instanceof FunctionInfoLocalizedValue<?>);

		Locale siteDefaultLocale = LocaleUtil.fromLanguageId(
			_group.getDefaultLanguageId());

		FunctionInfoLocalizedValue functionInfoLocalizedValue =
			(FunctionInfoLocalizedValue)value;

		Assert.assertEquals(
			expectedValues.get(siteDefaultLocale),
			functionInfoLocalizedValue.getValue());

		for (Map.Entry<Locale, String> entry : expectedValues.entrySet()) {
			Assert.assertEquals(
				entry.getValue(),
				functionInfoLocalizedValue.getValue(entry.getKey()));
		}
	}

	private DDMFormField _createDDMFormField(
		boolean multiple, Map<String, String> optionsMap, String type) {

		DDMFormField ddmFormField = new DDMFormField("name", type);

		ddmFormField.setDataType("text");
		ddmFormField.setIndexType("text");
		ddmFormField.setLocalizable(true);
		ddmFormField.setMultiple(multiple);

		LocalizedValue localizedValue = new LocalizedValue(LocaleUtil.US);

		localizedValue.addString(
			LocaleUtil.US, RandomTestUtil.randomString(10));

		ddmFormField.setLabel(localizedValue);

		DDMFormFieldOptions ddmFormFieldOptions =
			ddmFormField.getDDMFormFieldOptions();

		for (Map.Entry<String, String> entry : optionsMap.entrySet()) {
			String optionKey = entry.getKey();
			String optionLabel = entry.getValue();

			ddmFormFieldOptions.addOptionLabel(
				optionKey, LocaleUtil.US, optionLabel);
		}

		return ddmFormField;
	}

	private String _formatDate(Date date, Locale locale) {
		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			DateTimeFormatterBuilder.getLocalizedDateTimePattern(
				FormatStyle.SHORT, null, IsoChronology.INSTANCE, locale),
			locale);

		return dateFormat.format(date);
	}

	private MockHttpServletRequest _getMockHttpServletRequest(
			ThemeDisplay themeDisplay)
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		return mockHttpServletRequest;
	}

	private Map<Locale, String> _getRandomLocalizedMap() {
		return HashMapBuilder.put(
			LocaleUtil.SPAIN, RandomTestUtil.randomString()
		).put(
			LocaleUtil.US, RandomTestUtil.randomString()
		).build();
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		themeDisplay.setLayoutSet(layoutSet);
		themeDisplay.setLookAndFeel(
			layoutSet.getTheme(), layoutSet.getColorScheme());

		themeDisplay.setLocale(_portal.getSiteDefaultLocale(_group));

		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setRequest(_getMockHttpServletRequest(themeDisplay));
		themeDisplay.setResponse(new MockHttpServletResponse());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	@Inject
	private AssetCategoryLocalService _assetCategoryLocalService;

	@Inject
	private AssetVocabularyLocalService _assetVocabularyLocalService;

	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	@Inject
	private DDMFormValuesToFieldsConverter _ddmFormValuesToFieldsConverter;

	@DeleteAfterTestRun
	private Group _group;

	private JournalArticle _journalArticle;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private JournalConverter _journalConverter;

	@Inject
	private JSONFactory _jsonFactory;

	private Layout _layout;
	private ServiceContext _originalServiceContext;
	private Locale _originalSiteDefaultLocale;
	private Locale _originalThemeDisplayLocale;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

	@Inject
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

}