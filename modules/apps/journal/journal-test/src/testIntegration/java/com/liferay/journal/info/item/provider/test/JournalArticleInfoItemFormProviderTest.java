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

package com.liferay.journal.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.BooleanInfoFieldType;
import com.liferay.info.field.type.CategoriesInfoFieldType;
import com.liferay.info.field.type.DateInfoFieldType;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.NumberInfoFieldType;
import com.liferay.info.field.type.TagsInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.field.type.URLInfoFieldType;
import com.liferay.info.form.InfoForm;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.item.provider.InfoItemFormProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.type.WebImage;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TempFileEntryUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.InputStream;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alejandro Tardín
 */
@RunWith(Arquillian.class)
public class JournalArticleInfoItemFormProviderTest {

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
	public void testGetInfoForm() throws Exception {
		InfoItemFormProvider<JournalArticle> infoItemFormProvider =
			(InfoItemFormProvider<JournalArticle>)
				_infoItemServiceTracker.getFirstInfoItemService(
					InfoItemFormProvider.class, JournalArticle.class.getName());

		JournalArticle journalArticle = _getJournalArticle();

		InfoForm infoForm = infoItemFormProvider.getInfoForm(journalArticle);

		List<InfoField> infoFields = infoForm.getAllInfoFields();

		infoFields.sort(
			Comparator.comparing(
				InfoField::getName, String::compareToIgnoreCase));

		Assert.assertEquals(infoFields.toString(), 20, infoFields.size());

		Iterator<InfoField> iterator = infoFields.iterator();

		InfoField infoField = iterator.next();

		Assert.assertEquals("authorName", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());
		Assert.assertEquals(
			TextInfoFieldType.INSTANCE, infoField.getInfoFieldType());

		infoField = iterator.next();

		Assert.assertEquals("authorProfileImage", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());
		Assert.assertEquals(
			ImageInfoFieldType.INSTANCE, infoField.getInfoFieldType());

		infoField = iterator.next();

		Assert.assertEquals("boolean", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());
		Assert.assertEquals(
			BooleanInfoFieldType.INSTANCE, infoField.getInfoFieldType());

		infoField = iterator.next();

		Assert.assertEquals("categories", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());
		Assert.assertEquals(
			CategoriesInfoFieldType.INSTANCE, infoField.getInfoFieldType());

		infoField = iterator.next();

		Assert.assertEquals("DDM_Text", infoField.getName());
		Assert.assertTrue(infoField.isLocalizable());
		Assert.assertEquals(
			TextInfoFieldType.INSTANCE, infoField.getInfoFieldType());

		infoField = iterator.next();

		Assert.assertEquals("description", infoField.getName());
		Assert.assertTrue(infoField.isLocalizable());
		Assert.assertEquals(
			TextInfoFieldType.INSTANCE, infoField.getInfoFieldType());

		Optional<Boolean> htmlAttributeOptional =
			infoField.getAttributeOptional(TextInfoFieldType.HTML);

		Assert.assertTrue(htmlAttributeOptional.get());

		infoField = iterator.next();

		Assert.assertEquals("displayPageURL", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());
		Assert.assertEquals(
			URLInfoFieldType.INSTANCE, infoField.getInfoFieldType());

		infoField = iterator.next();

		Assert.assertEquals("HTML", infoField.getName());
		Assert.assertTrue(infoField.isLocalizable());
		Assert.assertEquals(
			TextInfoFieldType.INSTANCE, infoField.getInfoFieldType());

		htmlAttributeOptional = infoField.getAttributeOptional(
			TextInfoFieldType.HTML);

		Assert.assertTrue(htmlAttributeOptional.get());

		Optional<Boolean> multilineAttributeOptional =
			infoField.getAttributeOptional(TextInfoFieldType.MULTILINE);

		Assert.assertTrue(multilineAttributeOptional.get());

		infoField = iterator.next();

		Assert.assertEquals("image", infoField.getName());
		Assert.assertTrue(infoField.isLocalizable());
		Assert.assertEquals(
			ImageInfoFieldType.INSTANCE, infoField.getInfoFieldType());

		infoField = iterator.next();

		Assert.assertEquals("integer", infoField.getName());
		Assert.assertTrue(infoField.isLocalizable());
		Assert.assertEquals(
			NumberInfoFieldType.INSTANCE, infoField.getInfoFieldType());

		infoField = iterator.next();

		Assert.assertEquals("lastEditorName", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());
		Assert.assertEquals(
			TextInfoFieldType.INSTANCE, infoField.getInfoFieldType());

		infoField = iterator.next();

		Assert.assertEquals("lastEditorProfileImage", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());
		Assert.assertEquals(
			ImageInfoFieldType.INSTANCE, infoField.getInfoFieldType());

		infoField = iterator.next();

		Assert.assertEquals("publishDate", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());
		Assert.assertEquals(
			DateInfoFieldType.INSTANCE, infoField.getInfoFieldType());

		infoField = iterator.next();

		Assert.assertEquals("smallImage", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());
		Assert.assertEquals(
			ImageInfoFieldType.INSTANCE, infoField.getInfoFieldType());

		infoField = iterator.next();

		Assert.assertEquals("tagNames", infoField.getName());
		Assert.assertFalse(infoField.isLocalizable());
		Assert.assertEquals(
			TagsInfoFieldType.INSTANCE, infoField.getInfoFieldType());

		infoField = iterator.next();

		Assert.assertEquals("TextBox", infoField.getName());
		Assert.assertTrue(infoField.isLocalizable());
		Assert.assertEquals(
			TextInfoFieldType.INSTANCE, infoField.getInfoFieldType());

		htmlAttributeOptional = infoField.getAttributeOptional(
			TextInfoFieldType.HTML);

		Assert.assertFalse(htmlAttributeOptional.isPresent());

		multilineAttributeOptional = infoField.getAttributeOptional(
			TextInfoFieldType.MULTILINE);

		Assert.assertTrue(multilineAttributeOptional.get());

		infoField = iterator.next();

		Assert.assertEquals("title", infoField.getName());
		Assert.assertTrue(infoField.isLocalizable());
		Assert.assertEquals(
			TextInfoFieldType.INSTANCE, infoField.getInfoFieldType());

		infoField = iterator.next();

		Assert.assertEquals(
			"topic", StringUtil.toLowerCase(infoField.getName()));
		Assert.assertEquals(
			CategoriesInfoFieldType.INSTANCE, infoField.getInfoFieldType());

		Assert.assertFalse(iterator.hasNext());
	}

	@Test
	public void testGetInfoItemFieldValues() throws Exception {
		InfoItemFieldValuesProvider<JournalArticle>
			infoItemFieldValuesProvider =
				(InfoItemFieldValuesProvider<JournalArticle>)
					_infoItemServiceTracker.getFirstInfoItemService(
						InfoItemFieldValuesProvider.class,
						JournalArticle.class.getName());

		JournalArticle journalArticle = _getJournalArticle();

		InfoItemFieldValues infoItemFieldValues =
			infoItemFieldValuesProvider.getInfoItemFieldValues(journalArticle);

		InfoItemReference infoItemReference =
			infoItemFieldValues.getInfoItemReference();

		Assert.assertEquals(
			journalArticle.getResourcePrimKey(),
			infoItemReference.getClassPK());
		Assert.assertEquals(
			JournalArticle.class.getName(), infoItemReference.getClassName());

		Collection<InfoFieldValue<Object>> infoFieldValues =
			infoItemFieldValues.getInfoFieldValues();

		Assert.assertEquals(
			infoFieldValues.toString(), 17, infoFieldValues.size());

		InfoFieldValue<Object> descriptionInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("description");

		Assert.assertEquals(
			"Description",
			descriptionInfoFieldValue.getValue(LocaleUtil.getDefault()));
		Assert.assertEquals(
			"Descripción",
			descriptionInfoFieldValue.getValue(LocaleUtil.SPAIN));

		InfoFieldValue<Object> titleInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("title");

		Assert.assertEquals(
			"Test Article",
			titleInfoFieldValue.getValue(LocaleUtil.getDefault()));
		Assert.assertEquals(
			"Artículo de prueba",
			titleInfoFieldValue.getValue(LocaleUtil.SPAIN));

		InfoFieldValue<Object> ddmTextInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("DDM_Text");

		Assert.assertEquals(
			"Some text",
			ddmTextInfoFieldValue.getValue(LocaleUtil.getDefault()));
		Assert.assertEquals(
			"Un poco de texto",
			ddmTextInfoFieldValue.getValue(LocaleUtil.SPAIN));

		Collection<InfoFieldValue<Object>> ddmTextInfoFieldValues =
			infoItemFieldValues.getInfoFieldValues("DDM_Text");

		Iterator<InfoFieldValue<Object>> ddmTextInfoFieldValuesIterator =
			ddmTextInfoFieldValues.iterator();

		InfoFieldValue<Object> firstDDMTextInfoFieldValue =
			ddmTextInfoFieldValuesIterator.next();

		Assert.assertEquals(
			"Some text",
			firstDDMTextInfoFieldValue.getValue(LocaleUtil.getDefault()));
		Assert.assertEquals(
			"Un poco de texto",
			firstDDMTextInfoFieldValue.getValue(LocaleUtil.SPAIN));

		InfoFieldValue<Object> secondDDMTextInfoFieldValue =
			ddmTextInfoFieldValuesIterator.next();

		Assert.assertEquals(
			"Some more text",
			secondDDMTextInfoFieldValue.getValue(LocaleUtil.getDefault()));
		Assert.assertEquals(
			"Un poco más de texto",
			secondDDMTextInfoFieldValue.getValue(LocaleUtil.SPAIN));

		Assert.assertNotNull(infoItemFieldValues.getInfoFieldValue("boolean"));

		InfoFieldValue<Object> imageInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("image");

		WebImage webImage = (WebImage)imageInfoFieldValue.getValue(
			LocaleUtil.getDefault());

		Optional<InfoLocalizedValue<String>> altInfoLocalizedValueOptional =
			webImage.getAltInfoLocalizedValueOptional();

		InfoLocalizedValue<String> altInfoLocalizedValue =
			altInfoLocalizedValueOptional.get();

		Assert.assertEquals(
			"alt text",
			altInfoLocalizedValue.getValue(LocaleUtil.getDefault()));

		Assert.assertNotNull(webImage.getUrl());

		Assert.assertNotNull(infoItemFieldValues.getInfoFieldValue("integer"));

		InfoFieldValue<Object> textBoxInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("TextBox");

		Assert.assertEquals(
			"A lot of text",
			textBoxInfoFieldValue.getValue(LocaleUtil.getDefault()));

		InfoFieldValue<Object> htmlInfoFieldValue =
			infoItemFieldValues.getInfoFieldValue("HTML");

		Assert.assertEquals(
			"<p><strong>Bold text</strong></p>",
			htmlInfoFieldValue.getValue(LocaleUtil.getDefault()));
	}

	private JournalArticle _getJournalArticle() throws Exception {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(
				_readFileToString("dependencies/test-ddm-form.json"));

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_ddmFormDeserializer.deserialize(builder.build());

		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			_group.getGroupId(), JournalArticle.class.getName(),
			ddmFormDeserializerDeserializeResponse.getDDMForm());

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"/com/liferay/journal/dependencies/liferay.png");

		FileEntry tempFileEntry = TempFileEntryUtil.addTempFileEntry(
			_group.getGroupId(), TestPropsValues.getUserId(),
			JournalArticle.class.getName(), "image.png", inputStream,
			ContentTypes.IMAGE_PNG);

		JournalArticle journalArticle =
			JournalTestUtil.addArticleWithXMLContent(
				_group.getGroupId(),
				StringUtil.replace(
					StringUtil.replace(
						_readFileToString(
							"dependencies/test-journal-content.xml"),
						"$UUID", String.valueOf(tempFileEntry.getUuid())),
					"$GROUP_ID", String.valueOf(_group.getGroupId())),
				ddmStructure.getStructureKey(), null);

		journalArticle.setDescriptionMap(
			HashMapBuilder.put(
				LocaleUtil.getDefault(), "Description"
			).put(
				LocaleUtil.SPAIN, "Descripción"
			).build());

		journalArticle = _journalArticleLocalService.updateArticleTranslation(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			journalArticle.getVersion(), LocaleUtil.getDefault(),
			journalArticle.getTitle(), "Description",
			journalArticle.getContent(), null,
			ServiceContextTestUtil.getServiceContext(
				journalArticle.getGroupId()));

		return _journalArticleLocalService.updateArticleTranslation(
			journalArticle.getGroupId(), journalArticle.getArticleId(),
			journalArticle.getVersion(), LocaleUtil.SPAIN, "Artículo de prueba",
			"Descripción", journalArticle.getContent(), null,
			ServiceContextTestUtil.getServiceContext(
				journalArticle.getGroupId()));
	}

	private String _readFileToString(String s) throws Exception {
		return new String(FileUtil.getBytes(getClass(), s));
	}

	@Inject(filter = "ddm.form.deserializer.type=json")
	private DDMFormDeserializer _ddmFormDeserializer;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoItemServiceTracker _infoItemServiceTracker;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

}