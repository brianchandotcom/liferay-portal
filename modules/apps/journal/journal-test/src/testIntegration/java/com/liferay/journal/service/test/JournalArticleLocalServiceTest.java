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

package com.liferay.journal.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.display.page.constants.AssetDisplayPageConstants;
import com.liferay.asset.display.page.service.AssetDisplayPageEntryLocalService;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMFormField;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.model.LocalizedValue;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.service.DDMTemplateLinkLocalService;
import com.liferay.dynamic.data.mapping.storage.Field;
import com.liferay.dynamic.data.mapping.storage.Fields;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.exception.DuplicateArticleExternalReferenceCodeException;
import com.liferay.journal.exception.DuplicateArticleIdException;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalArticleDisplay;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.journal.util.JournalConverter;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalService;
import com.liferay.layout.test.util.LayoutTestUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Michael C. Han
 */
@RunWith(Arquillian.class)
public class JournalArticleLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		Layout layout = LayoutTestUtil.addLayout(_group);

		_themeDisplay = new ThemeDisplay();

		_themeDisplay.setCompany(
			_companyLocalService.getCompany(_group.getCompanyId()));
		_themeDisplay.setLayout(layout);
		_themeDisplay.setLookAndFeel(
			layout.getTheme(), layout.getColorScheme());

		User user = _userLocalService.getUser(_group.getCreatorUserId());

		_themeDisplay.setRealUser(user);

		HttpServletRequest httpServletRequest = new MockHttpServletRequest();

		httpServletRequest.setAttribute(WebKeys.THEME_DISPLAY, _themeDisplay);

		_themeDisplay.setRequest(httpServletRequest);

		_themeDisplay.setResponse(new MockHttpServletResponse());
		_themeDisplay.setScopeGroupId(_group.getGroupId());
		_themeDisplay.setSiteGroupId(_group.getGroupId());
		_themeDisplay.setUser(user);
	}

	@Test
	public void testCopyArticle() throws Exception {
		JournalArticle oldArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test-1",
			RandomTestUtil.randomString());

		JournalArticle thirdArticle = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test-2",
			oldArticle.getContent());

		JournalArticle newArticle = _journalArticleLocalService.copyArticle(
			oldArticle.getUserId(), oldArticle.getGroupId(),
			oldArticle.getArticleId(), null, true, oldArticle.getVersion());

		Assert.assertNotEquals(oldArticle, newArticle);
		Assert.assertNotEquals(
			thirdArticle.getUrlTitle(), newArticle.getUrlTitle());

		List<ResourcePermission> oldResourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				oldArticle.getCompanyId(), JournalArticle.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(oldArticle.getResourcePrimKey()));

		List<ResourcePermission> newResourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				newArticle.getCompanyId(), JournalArticle.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(newArticle.getResourcePrimKey()));

		Assert.assertEquals(
			StringBundler.concat(
				"Old resource permissions: ", oldResourcePermissions,
				", new resource permissions: ", newResourcePermissions),
			oldResourcePermissions.size(), newResourcePermissions.size());

		for (int i = 0; i < oldResourcePermissions.size(); i++) {
			ResourcePermission oldResourcePermission =
				oldResourcePermissions.get(i);
			ResourcePermission newResourcePermission =
				newResourcePermissions.get(i);

			Assert.assertNotEquals(
				oldResourcePermission, newResourcePermission);

			Assert.assertEquals(
				oldResourcePermission.getRoleId(),
				newResourcePermission.getRoleId());
			Assert.assertEquals(
				oldResourcePermission.getOwnerId(),
				newResourcePermission.getOwnerId());
			Assert.assertEquals(
				oldResourcePermission.getActionIds(),
				newResourcePermission.getActionIds());
			Assert.assertEquals(
				oldResourcePermission.isViewActionId(),
				newResourcePermission.isViewActionId());
		}
	}

	@Test
	public void testDeleteDDMStructurePredefinedValues() throws Exception {
		Tuple tuple = _createJournalArticleWithPredefinedValues("Test Article");

		JournalArticle journalArticle = (JournalArticle)tuple.getObject(0);
		DDMStructure ddmStructure = (DDMStructure)tuple.getObject(1);

		DDMStructure actualDDMStructure = journalArticle.getDDMStructure();

		List<DDMFormField> ddmFormFields = actualDDMStructure.getDDMFormFields(
			false);

		for (DDMFormField ddmFormField : ddmFormFields) {
			LocalizedValue localizedValue = ddmFormField.getPredefinedValue();

			Map<Locale, String> values = localizedValue.getValues();

			for (Map.Entry<Locale, String> entry : values.entrySet()) {
				Assert.assertNotEquals(StringPool.BLANK, entry.getValue());
			}
		}

		Assert.assertEquals(
			actualDDMStructure.getStructureId(), ddmStructure.getStructureId());

		JournalArticle defaultJournalArticle =
			_journalArticleLocalService.getArticle(
				ddmStructure.getGroupId(), DDMStructure.class.getName(),
				ddmStructure.getStructureId());

		Assert.assertEquals(
			defaultJournalArticle.getTitle(), journalArticle.getTitle());

		ServiceContextThreadLocal.pushServiceContext(
			ServiceContextTestUtil.getServiceContext());

		_journalArticleLocalService.deleteArticleDefaultValues(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			journalArticle.getDDMStructureKey());

		Assert.assertNull(
			_journalArticleLocalService.fetchLatestArticle(
				defaultJournalArticle.getResourcePrimKey()));

		actualDDMStructure = _ddmStructureLocalService.getDDMStructure(
			actualDDMStructure.getStructureId());

		ddmFormFields = actualDDMStructure.getDDMFormFields(false);

		for (DDMFormField ddmFormField : ddmFormFields) {
			LocalizedValue localizedValue = ddmFormField.getPredefinedValue();

			Map<Locale, String> values = localizedValue.getValues();

			for (Map.Entry<Locale, String> entry : values.entrySet()) {
				Assert.assertEquals(StringPool.BLANK, entry.getValue());
			}
		}
	}

	@Test(expected = DuplicateArticleIdException.class)
	public void testDuplicatedArticleId() throws Exception {
		JournalArticle article = JournalTestUtil.addArticle(
			RandomTestUtil.randomString(), _group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, null, true);

		JournalTestUtil.addArticle(
			RandomTestUtil.randomString(), _group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			article.getArticleId(), false);
	}

	@Test
	public void testDuplicatedAutoGeneratedArticleId() throws Exception {
		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			article.getArticleId(), true);
	}

	@Test(expected = DuplicateArticleExternalReferenceCodeException.class)
	public void testDuplicatedExternalReferenceCode() throws Exception {
		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		JournalTestUtil.addArticle(
			article.getExternalReferenceCode(), _group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			article.getArticleId(), true);
	}

	@Test
	public void testGetArticleDisplayFriendlyURLDisplayPageExists()
		throws Exception {

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		String script = StringBundler.concat(
			"${friendlyURLs[\"", defaultLanguageId, "\"]}");

		DDMStructure ddmStructure = article.getDDMStructure();

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			_portal.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_FTL, script,
			LocaleUtil.getSiteDefault());

		article.setDDMTemplateKey(ddmTemplate.getTemplateKey());

		_journalArticleLocalService.updateJournalArticle(article);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			_layoutPageTemplateEntryLocalService.addLayoutPageTemplateEntry(
				_group.getCreatorUserId(), _group.getGroupId(), 0,
				_portal.getClassNameId(JournalArticle.class.getName()),
				ddmStructure.getStructureId(), RandomTestUtil.randomString(),
				LayoutPageTemplateEntryTypeConstants.TYPE_DISPLAY_PAGE, 0, true,
				0, 0, 0, 0, serviceContext);

		_assetDisplayPageEntryLocalService.addAssetDisplayPageEntry(
			article.getUserId(), _group.getGroupId(),
			_portal.getClassNameId(JournalArticle.class.getName()),
			article.getResourcePrimKey(),
			layoutPageTemplateEntry.getLayoutPageTemplateEntryId(),
			AssetDisplayPageConstants.TYPE_DEFAULT, serviceContext);

		JournalArticleDisplay articleDisplay =
			_journalArticleLocalService.getArticleDisplay(
				_group.getGroupId(), article.getArticleId(), Constants.VIEW,
				defaultLanguageId, _themeDisplay);

		String content = articleDisplay.getContent();

		Assert.assertTrue(content.contains(_group.getFriendlyURL()));
		Assert.assertTrue(
			content.contains(article.getUrlTitle(LocaleUtil.getSiteDefault())));
	}

	@Test
	public void testGetArticleDisplayFriendlyURLWithoutDisplayPage()
		throws Exception {

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		String script = StringBundler.concat(
			"${friendlyURLs[\"", defaultLanguageId, "\"]!\"no-friendly-url\"}");

		DDMStructure ddmStructure = article.getDDMStructure();

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			_portal.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_FTL, script,
			LocaleUtil.getSiteDefault());

		article.setDDMTemplateKey(ddmTemplate.getTemplateKey());

		_journalArticleLocalService.updateJournalArticle(article);

		JournalArticleDisplay articleDisplay =
			_journalArticleLocalService.getArticleDisplay(
				_group.getGroupId(), article.getArticleId(), Constants.VIEW,
				defaultLanguageId, _themeDisplay);

		Assert.assertEquals("no-friendly-url", articleDisplay.getContent());
	}

	@Test
	public void testGetNoAssetArticles() throws Exception {
		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		AssetEntry assetEntry = _assetEntryLocalService.fetchEntry(
			JournalArticle.class.getName(), article.getResourcePrimKey());

		Assert.assertNotNull(assetEntry);

		_assetEntryLocalService.deleteAssetEntry(assetEntry);

		List<JournalArticle> articles =
			_journalArticleLocalService.getNoAssetArticles();

		for (JournalArticle curArticle : articles) {
			assetEntry = _assetEntryLocalService.fetchEntry(
				JournalArticle.class.getName(),
				curArticle.getResourcePrimKey());

			Assert.assertNull(assetEntry);

			_ddmTemplateLinkLocalService.deleteTemplateLink(
				PortalUtil.getClassNameId(JournalArticle.class),
				curArticle.getPrimaryKey());

			_journalArticleLocalService.deleteJournalArticle(
				curArticle.getPrimaryKey());
		}
	}

	@Test
	public void testGetNoPermissionArticles() throws Exception {
		JournalArticle article = JournalTestUtil.addArticle(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID);

		List<ResourcePermission> resourcePermissions =
			_resourcePermissionLocalService.getResourcePermissions(
				article.getCompanyId(), JournalArticle.class.getName(),
				ResourceConstants.SCOPE_INDIVIDUAL,
				String.valueOf(article.getResourcePrimKey()));

		for (ResourcePermission resourcePermission : resourcePermissions) {
			_resourcePermissionLocalService.deleteResourcePermission(
				resourcePermission.getResourcePermissionId());
		}

		List<JournalArticle> articles =
			_journalArticleLocalService.getNoPermissionArticles();

		Assert.assertEquals(articles.toString(), 1, articles.size());
		Assert.assertEquals(article, articles.get(0));
	}

	@Test
	public void testUpdateDDMStructurePredefinedValues() throws Exception {
		Tuple tuple = _createJournalArticleWithPredefinedValues("Test Article");

		JournalArticle journalArticle = (JournalArticle)tuple.getObject(0);
		DDMStructure ddmStructure = (DDMStructure)tuple.getObject(1);

		DDMStructure actualDDMStructure = journalArticle.getDDMStructure();

		Assert.assertEquals(
			actualDDMStructure.getStructureId(), ddmStructure.getStructureId());

		Fields fields = _journalConverter.getDDMFields(
			ddmStructure, journalArticle.getContent());

		Field field = fields.get("name");

		Assert.assertNotNull(field);

		Assert.assertEquals(
			"Valor Predefinido", field.getValue(LocaleUtil.BRAZIL));
		Assert.assertEquals(
			"Valeur Prédéfinie", field.getValue(LocaleUtil.FRENCH));
		Assert.assertEquals(
			"Valore Predefinito", field.getValue(LocaleUtil.ITALY));
		Assert.assertEquals("Predefined Value", field.getValue(LocaleUtil.US));
	}

	private Tuple _createJournalArticleWithPredefinedValues(String title)
		throws Exception {

		Set<Locale> availableLocales = DDMFormTestUtil.createAvailableLocales(
			LocaleUtil.BRAZIL, LocaleUtil.FRENCH, LocaleUtil.ITALY,
			LocaleUtil.US);

		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			availableLocales, LocaleUtil.US);

		DDMFormField ddmFormField =
			DDMFormTestUtil.createLocalizableTextDDMFormField("name");

		LocalizedValue label = new LocalizedValue(LocaleUtil.US);

		label.addString(LocaleUtil.BRAZIL, "rótulo");
		label.addString(LocaleUtil.FRENCH, "étiquette");
		label.addString(LocaleUtil.ITALY, "etichetta");
		label.addString(LocaleUtil.US, "label");

		ddmFormField.setLabel(label);

		ddmForm.addDDMFormField(ddmFormField);

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(), ddmForm);

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			TemplateConstants.LANG_TYPE_FTL,
			JournalTestUtil.getSampleTemplateXSL(), LocaleUtil.US);

		String content = DDMStructureTestUtil.getSampleStructuredContent(
			HashMapBuilder.put(
				LocaleUtil.BRAZIL, "Valor Predefinido"
			).put(
				LocaleUtil.FRENCH, "Valeur Prédéfinie"
			).put(
				LocaleUtil.ITALY, "Valore Predefinito"
			).put(
				LocaleUtil.US, "Predefined Value"
			).build(),
			LocaleUtil.US.toString());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		JournalArticle article =
			_journalArticleLocalService.addArticleDefaultValues(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				_classNameLocalService.getClassNameId(DDMStructure.class),
				ddmStructure.getStructureId(),
				HashMapBuilder.put(
					LocaleUtil.US, title
				).build(),
				null, content, ddmStructure.getStructureKey(),
				ddmTemplate.getTemplateKey(), null, true, false, null, null,
				serviceContext);

		return new Tuple(article, ddmStructure);
	}

	@Inject
	private AssetDisplayPageEntryLocalService
		_assetDisplayPageEntryLocalService;

	@Inject
	private AssetEntryLocalService _assetEntryLocalService;

	@Inject
	private ClassNameLocalService _classNameLocalService;

	@Inject
	private CompanyLocalService _companyLocalService;

	@Inject
	private DDMStructureLocalService _ddmStructureLocalService;

	@Inject
	private DDMTemplateLinkLocalService _ddmTemplateLinkLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private JournalConverter _journalConverter;

	@Inject
	private LayoutPageTemplateEntryLocalService
		_layoutPageTemplateEntryLocalService;

	@Inject
	private Portal _portal;

	@Inject
	private ResourcePermissionLocalService _resourcePermissionLocalService;

	private ThemeDisplay _themeDisplay;

	@Inject
	private UserLocalService _userLocalService;

}