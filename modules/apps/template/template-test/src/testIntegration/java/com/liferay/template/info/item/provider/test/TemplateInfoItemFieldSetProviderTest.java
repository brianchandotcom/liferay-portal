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

package com.liferay.template.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.asset.kernel.model.AssetVocabulary;
import com.liferay.asset.test.util.AssetTestUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.service.DDMFieldLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
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
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.template.info.item.provider.TemplateInfoItemFieldSetProvider;
import com.liferay.template.model.TemplateEntry;
import com.liferay.template.test.util.TemplateTestUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

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

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_company = _companyLocalService.getCompany(_group.getCompanyId());

		_layout = LayoutTestUtil.addLayout(_group);

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		_serviceContext.setCompanyId(TestPropsValues.getCompanyId());

		_serviceContext.setRequest(
			_getMockHttpServletRequest(_getThemeDisplay()));

		ServiceContextThreadLocal.pushServiceContext(_serviceContext);

		_journalArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		_assetVocabulary = AssetTestUtil.addVocabulary(_group.getGroupId());

		_assetCategory = AssetTestUtil.addCategory(
			_group.getGroupId(), _assetVocabulary.getVocabularyId());
	}

	@Test
	public void testGetInfoFieldSetByClassNameAndVariationKeyWhenNoTemplateEntryExists() {
		InfoFieldSet infoFieldSet =
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				JournalArticle.class.getName(),
				_journalArticle.getDDMStructureKey());

		List<InfoField> infoFields = infoFieldSet.getAllInfoFields();

		Assert.assertTrue(infoFields.isEmpty());
	}

	@Test
	public void testGetInfoFieldSetByClassNameAndVariationKeyWhenTemplateEntryExists()
		throws PortalException {

		TemplateEntry articleTemplateEntry = TemplateTestUtil.addTemplateEntry(
			JournalArticle.class.getName(),
			_journalArticle.getDDMStructureKey(), _serviceContext);

		TemplateTestUtil.addTemplateEntry(
			AssetCategory.class.getName(), StringPool.BLANK, _serviceContext);

		InfoFieldSet infoFieldSet =
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				JournalArticle.class.getName(),
				_journalArticle.getDDMStructureKey());

		List<InfoField> infoFields = infoFieldSet.getAllInfoFields();

		Assert.assertEquals(infoFields.toString(), 1, infoFields.size());

		InfoField infoField = infoFields.get(0);

		Assert.assertEquals(
			infoFields.toString(),
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				articleTemplateEntry.getTemplateEntryId(),
			infoField.getName());
	}

	@Test
	public void testGetInfoFieldSetByClassNameWhenNoTemplateEntryExists() {
		InfoFieldSet infoFieldSet =
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				AssetCategory.class.getName());

		List<InfoField> infoFields = infoFieldSet.getAllInfoFields();

		Assert.assertTrue(infoFields.isEmpty());
	}

	@Test
	public void testGetInfoFieldSetByClassNameWhenTemplateEntryExists()
		throws PortalException {

		TemplateTestUtil.addTemplateEntry(
			JournalArticle.class.getName(),
			_journalArticle.getDDMStructureKey(), _serviceContext);

		TemplateEntry categoryTemplateEntry = TemplateTestUtil.addTemplateEntry(
			AssetCategory.class.getName(), StringPool.BLANK, _serviceContext);

		InfoFieldSet infoFieldSet =
			_templateInfoItemFieldSetProvider.getInfoFieldSet(
				AssetCategory.class.getName());

		List<InfoField> infoFields = infoFieldSet.getAllInfoFields();

		Assert.assertEquals(infoFields.toString(), 1, infoFields.size());

		InfoField infoField = infoFields.get(0);

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
				_journalArticle.getDDMStructureKey(), _journalArticle);

		Assert.assertTrue(infoFieldValues.isEmpty());
	}

	@Test
	public void testGetInfoFieldValuesByClassNameAndVariationKeyWhenTemplateEntryExists()
		throws PortalException {

		DDMStructure ddmStructure = _journalArticle.getDDMStructure();

		DDMFormValues ddmFormValues = DDMFieldLocalServiceUtil.getDDMFormValues(
			ddmStructure.getDDMForm(), _journalArticle.getId());

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap(false);

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			"name");

		DDMFormFieldValue nameDDMFormFieldValue = ddmFormFieldValues.get(0);

		Value nameValue = nameDDMFormFieldValue.getValue();

		TemplateEntry articleTemplateEntry = TemplateTestUtil.addTemplateEntry(
			JournalArticle.class.getName(),
			_journalArticle.getDDMStructureKey(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(),
			JournalTestUtil.getSampleTemplateFTL(), _serviceContext);

		TemplateTestUtil.addTemplateEntry(
			AssetCategory.class.getName(), StringPool.BLANK, _serviceContext);

		List<InfoFieldValue<Object>> infoFieldValues =
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				JournalArticle.class.getName(),
				_journalArticle.getDDMStructureKey(), _journalArticle);

		Assert.assertEquals(
			infoFieldValues.toString(), 1, infoFieldValues.size());

		InfoFieldValue<Object> infoFieldValue = infoFieldValues.get(0);

		InfoField infoField = infoFieldValue.getInfoField();

		Assert.assertEquals(
			infoField.toString(),
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				articleTemplateEntry.getTemplateEntryId(),
			infoField.getName());

		Locale defaultLocale = _portal.getSiteDefaultLocale(
			_group.getGroupId());

		Assert.assertEquals(
			infoFieldValue.toString(), nameValue.getString(defaultLocale),
			infoFieldValue.getValue());
	}

	@Test
	public void testGetInfoFieldValuesByClassNameWhenNoTemplateEntryExists() {
		List<InfoFieldValue<Object>> infoFieldValues =
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				AssetCategory.class.getName(), _assetCategory);

		Assert.assertTrue(infoFieldValues.isEmpty());
	}

	@Test
	public void testGetInfoFieldValuesByClassNameWhenTemplateEntryExists()
		throws PortalException {

		TemplateTestUtil.addTemplateEntry(
			JournalArticle.class.getName(),
			_journalArticle.getDDMStructureKey(), _serviceContext);

		TemplateEntry categoryTemplateEntry = TemplateTestUtil.addTemplateEntry(
			AssetCategory.class.getName(), StringPool.BLANK,
			_assetCategory.getName(), RandomTestUtil.randomString(),
			JournalTestUtil.getSampleTemplateFTL(), _serviceContext);

		List<InfoFieldValue<Object>> infoFieldValues =
			_templateInfoItemFieldSetProvider.getInfoFieldValues(
				AssetCategory.class.getName(), _assetCategory);

		Assert.assertEquals(
			infoFieldValues.toString(), 1, infoFieldValues.size());

		InfoFieldValue<Object> infoFieldValue = infoFieldValues.get(0);

		InfoField infoField = infoFieldValue.getInfoField();

		Assert.assertEquals(
			infoField.toString(),
			PortletDisplayTemplate.DISPLAY_STYLE_PREFIX +
				categoryTemplateEntry.getTemplateEntryId(),
			infoField.getName());

		Assert.assertEquals(
			infoFieldValue.toString(), _assetCategory.getName(),
			infoFieldValue.getValue(
				_portal.getSiteDefaultLocale(_group.getGroupId())));
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

	private ThemeDisplay _getThemeDisplay() throws Exception {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		themeDisplay.setCompany(_company);
		themeDisplay.setLayout(_layout);

		LayoutSet layoutSet = _group.getPublicLayoutSet();

		themeDisplay.setLayoutSet(layoutSet);
		themeDisplay.setLookAndFeel(
			layoutSet.getTheme(), layoutSet.getColorScheme());

		themeDisplay.setPermissionChecker(
			PermissionThreadLocal.getPermissionChecker());
		themeDisplay.setRealUser(TestPropsValues.getUser());
		themeDisplay.setRequest(_getMockHttpServletRequest(themeDisplay));
		themeDisplay.setResponse(new MockHttpServletResponse());
		themeDisplay.setUser(TestPropsValues.getUser());
		themeDisplay.setScopeGroupId(_group.getGroupId());
		themeDisplay.setSiteGroupId(_group.getGroupId());

		return themeDisplay;
	}

	private AssetCategory _assetCategory;
	private AssetVocabulary _assetVocabulary;
	private Company _company;

	@Inject
	private CompanyLocalService _companyLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceTracker _infoItemServiceTracker;

	private JournalArticle _journalArticle;
	private Layout _layout;

	@Inject
	private Portal _portal;

	private ServiceContext _serviceContext;

	@Inject
	private TemplateInfoItemFieldSetProvider _templateInfoItemFieldSetProvider;

}