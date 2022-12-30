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

package com.liferay.journal.web.internal.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.type.WebImage;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.portlet.display.template.PortletDisplayTemplate;

import java.io.File;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
			PortalUtil.getClassNameId(JournalArticle.class));

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
			ddmStructure.getStructureKey(), ddmTemplate.getTemplateKey(), null,
			displayDateCalendar.get(Calendar.MONTH),
			displayDateCalendar.get(Calendar.DAY_OF_MONTH),
			displayDateCalendar.get(Calendar.YEAR),
			displayDateCalendar.get(Calendar.HOUR_OF_DAY),
			displayDateCalendar.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, 0, 0,
			0, 0, 0, true, true, false, null, null, null, null,
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

		Assert.assertTrue(
			GetterUtil.getBoolean(
				infoField.getAttribute(TextInfoFieldType.HTML)));
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
				PortalUtil.getClassNameId(JournalArticle.class));

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
					ddmStructure.getStructureKey(),
					ddmTemplate.getTemplateKey(), null,
					displayDateCalendar.get(Calendar.MONTH),
					displayDateCalendar.get(Calendar.DAY_OF_MONTH),
					displayDateCalendar.get(Calendar.YEAR),
					displayDateCalendar.get(Calendar.HOUR_OF_DAY),
					displayDateCalendar.get(Calendar.MINUTE), 0, 0, 0, 0, 0,
					true, 0, 0, 0, 0, 0, true, true, true, null, _getFile(),
					null, null,
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
				webImage.getUrl());
		}
		finally {
			ServiceContextThreadLocal.pushServiceContext(
				originalServiceContext);
		}
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

	private ThemeDisplay _getThemeDisplay() {
		ThemeDisplay themeDisplay = new ThemeDisplay();

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setAttribute(
			WebKeys.THEME_DISPLAY, themeDisplay);

		themeDisplay.setRequest(mockHttpServletRequest);

		themeDisplay.setURLCurrent("http://localhost:8080/currentURL");

		return themeDisplay;
	}

	@DeleteAfterTestRun
	private Group _group;

	@Inject(
		filter = "component.name=*.JournalArticleInfoItemFieldValuesProvider"
	)
	private InfoItemFieldValuesProvider _infoItemFieldValuesProvider;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

}