/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.form.field.type.constants.DDMFormFieldTypeConstants;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.HTMLInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.type.WebImage;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutTypePortlet;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.io.File;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class JournalArticleInfoItemFieldValuesProviderTest {

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
	public void testGetInfoItemFieldValues() throws Exception {
		String content = DDMStructureTestUtil.getSampleStructuredContent();

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName());

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			_portal.getClassNameId(JournalArticle.class));

		Calendar displayDateCalendar = new GregorianCalendar();

		displayDateCalendar.setTime(new Date());

		JournalArticle journalArticle = _journalArticleLocalService.addArticle(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, JournalArticleConstants.VERSION_DEFAULT,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), content,
			ddmStructure.getStructureId(), ddmTemplate.getTemplateKey(), null,
			displayDateCalendar.get(Calendar.MONTH),
			displayDateCalendar.get(Calendar.DAY_OF_MONTH),
			displayDateCalendar.get(Calendar.YEAR),
			displayDateCalendar.get(Calendar.HOUR_OF_DAY),
			displayDateCalendar.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, 0, 0,
			0, 0, 0, true, true, false, 0, 0, null, null, null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		InfoItemFieldValues infoItemFieldValues =
			_infoItemFieldValuesProvider.getInfoItemFieldValues(journalArticle);

		InfoFieldValue<Object> createDateInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("createDate");

		Assert.assertEquals(
			journalArticle.getCreateDate(),
			createDateInfoFieldValue.getValue());

		InfoFieldValue<Object> modifiedDateInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("modifiedDate");

		Assert.assertEquals(
			journalArticle.getModifiedDate(),
			modifiedDateInfoFieldValue.getValue());

		Assert.assertNull(
			infoItemFieldValues.getInfoFieldValue("previewImage"));

		InfoFieldValue<Object> publishDateInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("publishDate");

		Assert.assertEquals(
			journalArticle.getDisplayDate(),
			publishDateInfoFieldValue.getValue());

		String templateKey = ddmTemplate.getTemplateKey();

		InfoFieldValue<Object> ddmTemplateInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue(
				PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
					templateKey.replaceAll("\\W", "_"));

		Assert.assertNotNull(ddmTemplateInfoFieldValue);

		InfoField infoField = ddmTemplateInfoFieldValue.getInfoField();

		Assert.assertEquals(
			HTMLInfoFieldType.INSTANCE, infoField.getInfoFieldType());
	}

	@Test
	public void testGetInfoItemFieldValuesOfModifiedDDMStructure()
		throws Exception {

		String fieldName1 = RandomTestUtil.randomString();

		String content = DDMStructureTestUtil.getSampleStructuredContent(
			fieldName1,
			Collections.singletonList(
				HashMapBuilder.put(
					LocaleUtil.US, "example"
				).build()),
			LocaleUtil.toLanguageId(LocaleUtil.US));

		DDMForm ddmForm = DDMStructureTestUtil.getSampleDDMForm(
			fieldName1, "string", "text", true,
			DDMFormFieldTypeConstants.RICH_TEXT,
			new Locale[] {LocaleUtil.getSiteDefault()},
			LocaleUtil.getSiteDefault());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), 0, ddmForm,
			LocaleUtil.getSiteDefault(), serviceContext);

		JournalArticle journalArticle =
			_journalArticleLocalService.addArticleDefaultValues(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_classNameLocalService.getClassNameId(DDMStructure.class),
				ddmStructure.getStructureId(),
				HashMapBuilder.put(
					LocaleUtil.US, RandomTestUtil.randomString()
				).build(),
				null, content, ddmStructure.getStructureId(), null, null, 0, 0,
				0, 0, 0, 0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, true, true, false,
				0, 0, null, null, serviceContext);

		ServiceContext originalServiceContext =
			ServiceContextThreadLocal.getServiceContext();

		ServiceContextThreadLocal.pushServiceContext(
			_getServiceContext(_getThemeDisplay()));

		try {
			_assertInfoFieldValue(fieldName1, journalArticle);

			String fieldName2 = RandomTestUtil.randomString();

			ddmForm.addDDMFormField(
				_getDDMFormField(LocaleUtil.getDefault(), fieldName2, "text"));

			_ddmStructureLocalService.updateStructure(
				ddmStructure.getUserId(), ddmStructure.getStructureId(),
				ddmForm, ddmStructure.getDDMFormLayout(), serviceContext);

			_assertInfoFieldValue(fieldName2, journalArticle);
		}
		finally {
			ServiceContextThreadLocal.pushServiceContext(
				originalServiceContext);
		}
	}

	@Test
	public void testGetInfoItemFieldValuesWithDefaultValuesReturnedOnlyOneTime()
		throws Exception {

		String content = DDMStructureTestUtil.getSampleStructuredContent();

		String ddmStructureSuffix = RandomTestUtil.randomString();

		Tuple tuple = _createJournalArticleWithPredefinedValues(
			ddmStructureSuffix);

		DDMStructure ddmStructure = (DDMStructure)tuple.getObject(0);
		DDMTemplate ddmTemplate = (DDMTemplate)tuple.getObject(1);

		JournalArticle journalArticle = _journalArticleLocalService.addArticle(
			null, TestPropsValues.getUserId(), _group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, JournalArticleConstants.VERSION_DEFAULT,
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(),
			RandomTestUtil.randomLocaleStringMap(), content,
			ddmStructure.getStructureId(), ddmTemplate.getTemplateKey(), null,
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, true, 0, 0, 0, 0, 0, true, true, true,
			0, 0, null, null, null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));

		InfoItemFieldValues infoItemFieldValues =
			_infoItemFieldValuesProvider.getInfoItemFieldValues(journalArticle);

		Collection<InfoFieldValue<Object>> infoFieldValues =
			infoItemFieldValues.getInfoFieldValues(
				"DDMStructure_" + ddmStructureSuffix);

		Assert.assertEquals(
			infoFieldValues.toString(), 1, infoFieldValues.size());
	}

	@Test
	public void testGetInfoItemFieldValuesWithSmallImage() throws Exception {
		ServiceContext originalServiceContext =
			ServiceContextThreadLocal.getServiceContext();

		ThemeDisplay themeDisplay = _getThemeDisplay();

		ServiceContextThreadLocal.pushServiceContext(
			_getServiceContext(themeDisplay));

		try {
			String content = DDMStructureTestUtil.getSampleStructuredContent();

			DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
				_group.getGroupId(), JournalArticle.class.getName());

			DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
				_group.getGroupId(), ddmStructure.getStructureId(),
				_portal.getClassNameId(JournalArticle.class));

			Calendar displayDateCalendar = new GregorianCalendar();

			displayDateCalendar.setTime(new Date());

			JournalArticle journalArticle =
				_journalArticleLocalService.addArticle(
					null, TestPropsValues.getUserId(), _group.getGroupId(),
					JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
					JournalArticleConstants.CLASS_NAME_ID_DEFAULT, 0,
					StringPool.BLANK, true,
					JournalArticleConstants.VERSION_DEFAULT,
					RandomTestUtil.randomLocaleStringMap(),
					RandomTestUtil.randomLocaleStringMap(),
					RandomTestUtil.randomLocaleStringMap(), content,
					ddmStructure.getStructureId(), ddmTemplate.getTemplateKey(),
					null, displayDateCalendar.get(Calendar.MONTH),
					displayDateCalendar.get(Calendar.DAY_OF_MONTH),
					displayDateCalendar.get(Calendar.YEAR),
					displayDateCalendar.get(Calendar.HOUR_OF_DAY),
					displayDateCalendar.get(Calendar.MINUTE), 0, 0, 0, 0, 0,
					true, 0, 0, 0, 0, 0, true, true, true,
					JournalArticleConstants.SMALL_IMAGE_SOURCE_USER_COMPUTER, 0,
					null, _getFile(), null, null,
					ServiceContextTestUtil.getServiceContext(
						_group.getGroupId()));

			InfoItemFieldValues infoItemFieldValues =
				_infoItemFieldValuesProvider.getInfoItemFieldValues(
					journalArticle);

			InfoFieldValue<Object> previewImageInfoFieldValue =
				infoItemFieldValues.getInfoFieldValue("previewImage");

			WebImage webImage = (WebImage)previewImageInfoFieldValue.getValue();

			Assert.assertEquals(
				journalArticle.getArticleImageURL(themeDisplay),
				webImage.getURL());
		}
		finally {
			ServiceContextThreadLocal.pushServiceContext(
				originalServiceContext);
		}
	}

	private void _assertInfoFieldValue(
		String fieldName, JournalArticle journalArticle) {

		InfoItemFieldValues infoItemFieldValues =
			_infoItemFieldValuesProvider.getInfoItemFieldValues(journalArticle);

		Map<String, Object> infoItemFieldValuesMap = infoItemFieldValues.getMap(
			LocaleUtil.getSiteDefault());

		Assert.assertTrue(infoItemFieldValuesMap.containsKey(fieldName));
	}

	private Tuple _createJournalArticleWithPredefinedValues(String ddmName)
		throws Exception {

		Set<Locale> availableLocales = DDMFormTestUtil.createAvailableLocales(
			LocaleUtil.SPAIN, LocaleUtil.US);

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			availableLocales, LocaleUtil.US);

		DDMFormField ddmFormField =
			DDMFormTestUtil.createLocalizableTextDDMFormField(ddmName);

		LocalizedValue label = new LocalizedValue(LocaleUtil.US);

		label.addString(LocaleUtil.SPAIN, "etiqueta");
		label.addString(LocaleUtil.US, "label");

		ddmFormField.setLabel(label);

		ddmForm.addDDMFormField(ddmFormField);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), ddmForm);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			_portal.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_FTL,
			JournalTestUtil.getSampleTemplateFTL(), LocaleUtil.US);

		String content = DDMStructureTestUtil.getSampleStructuredContent(
			HashMapBuilder.put(
				LocaleUtil.SPAIN, "Valor Predefinido"
			).put(
				LocaleUtil.US, "Predefined Value"
			).build(),
			LocaleUtil.US.toString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_journalArticleLocalService.addArticleDefaultValues(
			serviceContext.getUserId(), serviceContext.getScopeGroupId(),
			_classNameLocalService.getClassNameId(DDMStructure.class),
			ddmStructure.getStructureId(),
			HashMapBuilder.put(
				LocaleUtil.US, RandomTestUtil.randomString()
			).build(),
			null, content, ddmStructure.getStructureId(),
			ddmTemplate.getTemplateKey(), null, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
			true, 0, 0, 0, 0, 0, true, true, false, 0, 0, null, null,
			serviceContext);

		return new Tuple(ddmStructure, ddmTemplate);
	}

	private DDMFormField _getDDMFormField(
		Locale locale, String name, String type) {

		DDMFormField ddmFormField = new DDMFormField(name, type);

		ddmFormField.setDataType("string");
		ddmFormField.setIndexType("text");

		LocalizedValue label = new LocalizedValue(locale);

		label.addString(locale, "Field_" + LocaleUtil.toLanguageId(locale));

		ddmFormField.setLabel(label);

		ddmFormField.setLocalizable(true);
		ddmFormField.setRepeatable(false);

		return ddmFormField;
	}

	private File _getFile() throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		File tempFile = FileUtil.createTempFile("jpg");

		FileUtil.write(
			tempFile,
			FileUtil.getBytes(
				classLoader.getResourceAsStream(
					"com/liferay/journal/info/item/provider/test/dependencies" +
						"/test.jpg")),
			false);

		return tempFile;
	}

	private ServiceContext _getServiceContext(ThemeDisplay themeDisplay)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setRequest(themeDisplay.getRequest());

		return serviceContext;
	}

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(
			_companyLocalService.getCompany(TestPropsValues.getCompanyId()));

		Layout layout = LayoutTestUtil.addTypePortletLayout(_group);

		themeDisplay.setLayout(layout);
		themeDisplay.setLayoutSet(layout.getLayoutSet());
		themeDisplay.setLayoutTypePortlet(
			(LayoutTypePortlet)layout.getLayoutType());

		themeDisplay.setRealUser(TestPropsValues.getUser());

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		themeDisplay.setRequest(mockHttpServletRequest);

		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());
		themeDisplay.setURLCurrent("http://localhost:8080/currentURL");
		themeDisplay.setUser(TestPropsValues.getUser());

		return themeDisplay;
	}

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "component.name=com.liferay.journal.web.internal.info.item.provider.JournalArticleInfoItemFieldValuesProvider"
	)
	private InfoItemFieldValuesProvider _infoItemFieldValuesProvider;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private Portal _portal;

}