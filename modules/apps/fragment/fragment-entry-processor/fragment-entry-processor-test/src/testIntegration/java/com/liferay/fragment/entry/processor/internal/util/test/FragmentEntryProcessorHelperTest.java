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

package com.liferay.fragment.entry.processor.internal.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.fragment.entry.processor.helper.FragmentEntryProcessorHelper;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.type.CategoriesInfoFieldType;
import com.liferay.info.field.type.ImageInfoFieldType;
import com.liferay.info.field.type.TextInfoFieldType;
import com.liferay.info.formatter.InfoTextFormatter;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemFieldValuesProvider;
import com.liferay.info.localized.InfoLocalizedValue;
import com.liferay.info.type.KeyLocalizedLabelPair;
import com.liferay.info.type.WebImage;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.petra.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.InputStream;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Rubén Pulido
 */
@RunWith(Arquillian.class)
public class FragmentEntryProcessorHelperTest {

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
	public void testGetFileEntryIdClassNameClassPKDLImage() throws Exception {
		FileEntry fileEntry = _addImageFileEntry();

		Assert.assertEquals(
			fileEntry.getFileEntryId(),
			_fragmentEntryProcessorHelper.getFileEntryId(
				FileEntry.class.getName(), fileEntry.getFileEntryId()));
	}

	@Test
	public void testGetFileEntryIdClassNameClassPKJournalArticle()
		throws Exception {

		JournalArticle journalArticle = _addJournalArticle(
			_addImageFileEntry(), "ImageFieldName");

		Assert.assertEquals(
			0L,
			_fragmentEntryProcessorHelper.getFileEntryId(
				JournalArticle.class.getName(),
				journalArticle.getResourcePrimKey()));
	}

	@Test
	public void testGetFileEntryIdClassPKDLImage() throws Exception {
		FileEntry fileEntry = _addImageFileEntry();

		Assert.assertEquals(
			fileEntry.getFileEntryId(),
			_fragmentEntryProcessorHelper.getFileEntryId(
				_portal.getClassNameId(FileEntry.class.getName()),
				fileEntry.getFileEntryId(), "fileURL",
				LocaleUtil.getSiteDefault()));
	}

	@Test
	public void testGetFileEntryIdClassPKJournalArticle() throws Exception {
		FileEntry fileEntry = _addImageFileEntry();

		String fieldId = "ImageFieldName";

		JournalArticle journalArticle = _addJournalArticle(fileEntry, fieldId);

		Assert.assertEquals(
			fileEntry.getFileEntryId(),
			_fragmentEntryProcessorHelper.getFileEntryId(
				_portal.getClassNameId(JournalArticle.class.getName()),
				journalArticle.getResourcePrimKey(), fieldId,
				LocaleUtil.getSiteDefault()));
	}

	@Test
	public void testGetFileEntryIdDisplayObjectJournalArticle()
		throws Exception {

		FileEntry fileEntry = _addImageFileEntry();

		String fieldId = "ImageFieldName";

		JournalArticle journalArticle = _addJournalArticle(fileEntry, fieldId);

		Assert.assertEquals(
			fileEntry.getFileEntryId(),
			_fragmentEntryProcessorHelper.getFileEntryId(
				new InfoItemReference(
					JournalArticle.class.getName(),
					new ClassPKInfoItemIdentifier(
						journalArticle.getResourcePrimKey())),
				fieldId, LocaleUtil.getSiteDefault()));
	}

	@Test
	public void testGetMappedInfoItemFieldValueFromCollectionValue() {
		String localizedCategoryName1 = RandomTestUtil.randomString();
		String localizedCategoryName2 = RandomTestUtil.randomString();

		_assertGetMappedInfoItemFieldValue(
			localizedCategoryName1 + StringPool.COMMA_AND_SPACE +
				localizedCategoryName2,
			InfoField.builder(
			).infoFieldType(
				CategoriesInfoFieldType.INSTANCE
			).namespace(
				AssetCategory.class.getSimpleName()
			).name(
				RandomTestUtil.randomString()
			).labelInfoLocalizedValue(
				null
			).build(),
			LocaleUtil.SPAIN,
			Arrays.asList(
				new KeyLocalizedLabelPair(
					RandomTestUtil.randomString(),
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.US
					).values(
						HashMapBuilder.put(
							LocaleUtil.GERMANY, RandomTestUtil.randomString()
						).put(
							LocaleUtil.SPAIN, localizedCategoryName1
						).put(
							LocaleUtil.US, RandomTestUtil.randomString()
						).build()
					).build()),
				new KeyLocalizedLabelPair(
					RandomTestUtil.randomString(),
					InfoLocalizedValue.<String>builder(
					).defaultLocale(
						LocaleUtil.US
					).values(
						HashMapBuilder.put(
							LocaleUtil.GERMANY, RandomTestUtil.randomString()
						).put(
							LocaleUtil.SPAIN, localizedCategoryName2
						).put(
							LocaleUtil.US, RandomTestUtil.randomString()
						).build()
					).build())));
	}

	@Test
	public void testGetMappedInfoItemFieldValueFromEmptyCollectionValue() {
		_assertGetMappedInfoItemFieldValue(
			StringPool.BLANK,
			InfoField.builder(
			).infoFieldType(
				CategoriesInfoFieldType.INSTANCE
			).namespace(
				AssetCategory.class.getSimpleName()
			).name(
				RandomTestUtil.randomString()
			).labelInfoLocalizedValue(
				null
			).build(),
			LocaleUtil.SPAIN, Collections.emptyList());
	}

	@Test
	public void testGetMappedInfoItemFieldValueFromLabeledValue() {
		String expected = RandomTestUtil.randomString();

		_assertGetMappedInfoItemFieldValue(
			expected,
			InfoField.builder(
			).infoFieldType(
				CategoriesInfoFieldType.INSTANCE
			).namespace(
				AssetCategory.class.getSimpleName()
			).name(
				RandomTestUtil.randomString()
			).labelInfoLocalizedValue(
				null
			).build(),
			LocaleUtil.SPAIN,
			new KeyLocalizedLabelPair(
				RandomTestUtil.randomString(),
				InfoLocalizedValue.<String>builder(
				).defaultLocale(
					LocaleUtil.US
				).values(
					HashMapBuilder.put(
						LocaleUtil.GERMANY, RandomTestUtil.randomString()
					).put(
						LocaleUtil.SPAIN, expected
					).put(
						LocaleUtil.US, RandomTestUtil.randomString()
					).build()
				).build()));
	}

	@Test
	public void testGetMappedInfoItemFieldValueFromNullValue() {
		_assertGetMappedInfoItemFieldValue(
			null,
			InfoField.builder(
			).infoFieldType(
				CategoriesInfoFieldType.INSTANCE
			).namespace(
				AssetCategory.class.getSimpleName()
			).name(
				RandomTestUtil.randomString()
			).labelInfoLocalizedValue(
				null
			).build(),
			LocaleUtil.SPAIN, null);
	}

	@Test
	public void testGetMappedInfoItemFieldValueFromOtherClassValue() {
		DummyClass testDummyClass = new DummyClass();

		InfoField<TextInfoFieldType> infoField = InfoField.builder(
		).infoFieldType(
			TextInfoFieldType.INSTANCE
		).namespace(
			AssetCategory.class.getSimpleName()
		).name(
			RandomTestUtil.randomString()
		).labelInfoLocalizedValue(
			null
		).build();

		_assertGetMappedInfoItemFieldValue(
			testDummyClass.toString(), infoField, LocaleUtil.SPAIN,
			testDummyClass);

		Bundle bundle = FrameworkUtil.getBundle(
			FragmentEntryProcessorHelperTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		InfoTextFormatter<DummyClass> infoTextFormatter =
			(dummyClass, locale) -> dummyClass.toString() + locale.toString();

		ServiceRegistration<InfoTextFormatter> serviceRegistration =
			bundleContext.registerService(
				InfoTextFormatter.class, infoTextFormatter,
				HashMapDictionaryBuilder.<String, Object>put(
					"item.class.name", DummyClass.class.getName()
				).build());

		try {
			_assertGetMappedInfoItemFieldValue(
				infoTextFormatter.format(testDummyClass, LocaleUtil.SPAIN),
				infoField, LocaleUtil.SPAIN, testDummyClass);
		}
		finally {
			serviceRegistration.unregister();
		}
	}

	@Test
	public void testGetMappedInfoItemFieldValueFromWebImage() {
		WebImage webImage = new WebImage(RandomTestUtil.randomString());

		Object actual = _getMappedInfoItemFieldValue(
			InfoField.builder(
			).infoFieldType(
				ImageInfoFieldType.INSTANCE
			).namespace(
				StringPool.BLANK
			).name(
				RandomTestUtil.randomString()
			).build(),
			LocaleUtil.SPAIN, webImage);

		Assert.assertTrue(actual instanceof JSONObject);

		Assert.assertTrue(
			JSONUtil.equals(webImage.toJSONObject(), (JSONObject)actual));
	}

	private DDMStructure _addDDMStructure(Group group, String content)
		throws Exception {

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				_portal.getClassNameId(JournalArticle.class), group);

		return ddmStructureTestHelper.addStructure(
			_portal.getClassNameId(JournalArticle.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_deserialize(content), StorageType.DEFAULT.getValue(),
			DDMStructureConstants.TYPE_DEFAULT);
	}

	private FileEntry _addImageFileEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		byte[] bytes = FileUtil.getBytes(
			FragmentEntryProcessorHelperTest.class,
			"/com/liferay/fragment/entry/processor/internal/util/test" +
				"/dependencies/image.jpg");

		InputStream inputStream = new UnsyncByteArrayInputStream(bytes);

		LocalRepository localRepository =
			RepositoryProviderUtil.getLocalRepository(_group.getGroupId());

		return localRepository.addFileEntry(
			null, TestPropsValues.getUserId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.IMAGE_JPEG,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			StringPool.BLANK, StringPool.BLANK, inputStream, bytes.length, null,
			null, serviceContext);
	}

	private JournalArticle _addJournalArticle(
			DDMStructure ddmStructure, String fieldId, FileEntry fileEntry)
		throws Exception {

		User user = TestPropsValues.getUser();

		Locale defaultLocale = LocaleUtil.getSiteDefault();

		String dynamicContent = _readJSONFileToString("dynamic_content.json");

		dynamicContent = StringUtil.replace(
			dynamicContent,
			new String[] {
				"FILE_ENTRY_ID", "GROUP_ID", "RESOURCE_PRIM_KEY", "UUID"
			},
			new String[] {
				String.valueOf(fileEntry.getFileEntryId()),
				String.valueOf(fileEntry.getGroupId()),
				String.valueOf(fileEntry.getPrimaryKey()),
				String.valueOf(fileEntry.getUuid())
			});

		DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), ddmStructure.getStructureId(),
			_portal.getClassNameId(JournalArticle.class));

		Calendar displayCalendar = CalendarFactoryUtil.getCalendar(
			user.getTimeZone());

		return _journalArticleLocalService.addArticle(
			null, user.getUserId(), _group.getGroupId(), 0,
			JournalArticleConstants.CLASS_NAME_ID_DEFAULT, 0, StringPool.BLANK,
			true, JournalArticleConstants.VERSION_DEFAULT,
			HashMapBuilder.put(
				defaultLocale, RandomTestUtil.randomString()
			).build(),
			HashMapBuilder.put(
				defaultLocale, defaultLocale.toString()
			).build(),
			HashMapBuilder.put(
				defaultLocale, RandomTestUtil.randomString()
			).build(),
			_getJournalArticleStructuredContent(
				fieldId,
				Collections.singletonList(
					HashMapBuilder.put(
						defaultLocale, dynamicContent
					).build()),
				LocaleUtil.toLanguageId(defaultLocale)),
			ddmStructure.getStructureKey(), ddmTemplate.getTemplateKey(), null,
			displayCalendar.get(Calendar.MONTH),
			displayCalendar.get(Calendar.DATE),
			displayCalendar.get(Calendar.YEAR),
			displayCalendar.get(Calendar.HOUR_OF_DAY),
			displayCalendar.get(Calendar.MINUTE), 0, 0, 0, 0, 0, true, 0, 0, 0,
			0, 0, true, true, false, null, null, null, null,
			ServiceContextTestUtil.getServiceContext(_group.getGroupId()));
	}

	private JournalArticle _addJournalArticle(
			FileEntry fileEntry, String fieldId)
		throws Exception {

		String ddmStructureContent = _readJSONFileToString(
			"ddm_structure.json");

		ddmStructureContent = StringUtil.replace(
			ddmStructureContent, "FIELD_NAME", fieldId);

		DDMStructure ddmStructure = _addDDMStructure(
			_group, ddmStructureContent);

		return _addJournalArticle(ddmStructure, fieldId, fileEntry);
	}

	private <T> void _assertGetMappedInfoItemFieldValue(
		Object expected, InfoField infoField, Locale locale, T value) {

		Assert.assertEquals(
			expected, _getMappedInfoItemFieldValue(infoField, locale, value));
	}

	private Document _createDocument(
		String availableLocales, String defaultLocale) {

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("root");

		rootElement.addAttribute("available-locales", availableLocales);
		rootElement.addAttribute("default-locale", defaultLocale);
		rootElement.addElement("request");

		return document;
	}

	private DDMForm _deserialize(String content) {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_jsonDDMFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private String _getJournalArticleStructuredContent(
		String name, List<Map<Locale, String>> contents, String defaultLocale) {

		StringBundler sb = new StringBundler();

		for (Map<Locale, String> map : contents) {
			for (Locale locale : map.keySet()) {
				sb.append(LocaleUtil.toLanguageId(locale));
				sb.append(StringPool.COMMA);
			}

			sb.setIndex(sb.index() - 1);
		}

		Document document = _createDocument(sb.toString(), defaultLocale);

		Element rootElement = document.getRootElement();

		for (Map<Locale, String> map : contents) {
			Element dynamicElementElement = rootElement.addElement(
				"dynamic-element");

			dynamicElementElement.addAttribute("index-type", "keyword");
			dynamicElementElement.addAttribute("name", name);
			dynamicElementElement.addAttribute("type", "image");

			for (Map.Entry<Locale, String> entry : map.entrySet()) {
				Element element = dynamicElementElement.addElement(
					"dynamic-content");

				element.addAttribute(
					"language-id", LocaleUtil.toLanguageId(entry.getKey()));
				element.addCDATA(entry.getValue());
			}
		}

		return document.asXML();
	}

	private <T> Object _getMappedInfoItemFieldValue(
		InfoField infoField, Locale locale, T value) {

		InfoItemFieldValuesProvider<Object> infoItemFieldValuesProvider =
			object -> InfoItemFieldValues.builder(
			).infoFieldValue(
				new InfoFieldValue<>(infoField, value)
			).build();

		return _fragmentEntryProcessorHelper.getMappedInfoItemFieldValue(
			infoField.getName(), infoItemFieldValuesProvider, locale,
			new Object());
	}

	private String _readFileToString(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(),
			"com/liferay/fragment/entry/processor/internal/util/test" +
				"/dependencies/" + fileName);
	}

	private String _readJSONFileToString(String jsonFileName) throws Exception {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			_readFileToString(jsonFileName));

		return jsonObject.toString();
	}

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	@Inject(filter = "ddm.form.deserializer.type=json")
	private static DDMFormDeserializer _jsonDDMFormDeserializer;

	@Inject
	private FragmentEntryProcessorHelper _fragmentEntryProcessorHelper;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Portal _portal;

	private static class DummyClass {
	}

}