/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.web.internal.info.collection.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.info.collection.provider.CollectionQuery;
import com.liferay.info.collection.provider.RepeatableFieldInfoItemCollectionProvider;
import com.liferay.info.field.InfoFieldValue;
import com.liferay.info.field.RepeatableInfoFieldValue;
import com.liferay.info.item.InfoItemFieldValues;
import com.liferay.info.pagination.InfoPage;
import com.liferay.info.pagination.Pagination;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.io.InputStream;

import java.util.List;
import java.util.Locale;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Marco Galluzzi
 */
@RunWith(Arquillian.class)
public class JournalRepeatableFieldsInfoCollectionProviderTest {

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
	public void testRepeatableFieldInfoItemCollectionProviderWithCustomCollectionQuery()
		throws Exception {

		JournalArticle journalArticle = _addJournalArticle(
			_readFileToString("structure_with_repeatable_field.json"),
			_readFileToString("repeatable_field_content.xml"));

		InfoPage<RepeatableInfoFieldValue> infoPage =
			_repeatableFieldInfoItemCollectionProvider.getCollectionInfoPage(
				_getCollectionQuery(
					"Text1", journalArticle, Pagination.of(2, 0)));

		Assert.assertEquals(infoPage.toString(), 4, infoPage.getTotalCount());

		List<? extends RepeatableInfoFieldValue> pageItems =
			infoPage.getPageItems();

		Assert.assertEquals(pageItems.toString(), 2, pageItems.size());

		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(0), "one");
		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(1), "two");

		infoPage =
			_repeatableFieldInfoItemCollectionProvider.getCollectionInfoPage(
				_getCollectionQuery(
					"Text1", journalArticle, Pagination.of(4, 2)));

		Assert.assertEquals(infoPage.toString(), 4, infoPage.getTotalCount());

		pageItems = infoPage.getPageItems();

		Assert.assertEquals(pageItems.toString(), 2, pageItems.size());

		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(0), "three");
		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(1), "four");
	}

	@Test
	public void testRepeatableFieldInfoItemCollectionProviderWithRepeatableField()
		throws Exception {

		JournalArticle journalArticle = _addJournalArticle(
			_readFileToString("structure_with_repeatable_field.json"),
			_readFileToString("repeatable_field_content.xml"));

		InfoPage<RepeatableInfoFieldValue> infoPage =
			_repeatableFieldInfoItemCollectionProvider.getCollectionInfoPage(
				_getCollectionQuery(
					"Text1", journalArticle,
					Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS)));

		Assert.assertEquals(infoPage.toString(), 4, infoPage.getTotalCount());

		List<? extends RepeatableInfoFieldValue> pageItems =
			infoPage.getPageItems();

		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(0), "one");
		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(1), "two");
		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(2), "three");
		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(3), "four");

		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.SPAIN, pageItems.get(0), "uno");
		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.SPAIN, pageItems.get(1), "dos");
		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.SPAIN, pageItems.get(2), "tres");
		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.SPAIN, pageItems.get(3), "cuatro");
	}

	@Test
	public void testRepeatableFieldInfoItemCollectionProviderWithRepeatableFieldset()
		throws Exception {

		JournalArticle journalArticle = _addJournalArticle(
			_readFileToString("structure_with_repeatable_fieldset.json"),
			_readFileToString("repeatable_fieldset_content.xml"));

		InfoPage<RepeatableInfoFieldValue> infoPage =
			_repeatableFieldInfoItemCollectionProvider.getCollectionInfoPage(
				_getCollectionQuery(
					"Fieldset", journalArticle,
					Pagination.of(QueryUtil.ALL_POS, QueryUtil.ALL_POS)));

		Assert.assertEquals(infoPage.toString(), 3, infoPage.getTotalCount());

		List<? extends RepeatableInfoFieldValue> pageItems =
			infoPage.getPageItems();

		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(0), "text1one");
		_assertInfoItemFieldValue(
			"Text2", LocaleUtil.US, pageItems.get(0), "text2one");
		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(1), "text1two");
		_assertInfoItemFieldValue(
			"Text2", LocaleUtil.US, pageItems.get(1), "text2two");
		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.US, pageItems.get(2), "text1three");
		_assertInfoItemFieldValue(
			"Text2", LocaleUtil.US, pageItems.get(2), "text2three");

		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.SPAIN, pageItems.get(0), "text1uno");
		_assertInfoItemFieldValue(
			"Text2", LocaleUtil.SPAIN, pageItems.get(0), "text2uno");
		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.SPAIN, pageItems.get(1), "text1dos");
		_assertInfoItemFieldValue(
			"Text2", LocaleUtil.SPAIN, pageItems.get(1), "text2dos");
		_assertInfoItemFieldValue(
			"Text1", LocaleUtil.SPAIN, pageItems.get(2), "text1tres");
		_assertInfoItemFieldValue(
			"Text2", LocaleUtil.SPAIN, pageItems.get(2), "text2tres");
	}

	private JournalArticle _addJournalArticle(
			String ddmStructureDefinition, String content)
		throws Exception {

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				_portal.getClassNameId(JournalArticle.class), _group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			_portal.getClassNameId(JournalArticle.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_deserialize(ddmStructureDefinition),
			StorageType.DEFAULT.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		return JournalTestUtil.addArticleWithXMLContent(
			_group.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			PortalUtil.getClassNameId(DDMStructure.class),
			ddmStructure.getStructureId(), content,
			ddmStructure.getStructureKey(), null, LocaleUtil.getSiteDefault());
	}

	private void _assertInfoItemFieldValue(
		String fieldName, Locale locale,
		RepeatableInfoFieldValue repeatableInfoFieldValue, String value) {

		InfoItemFieldValues infoItemFieldValues =
			repeatableInfoFieldValue.getInfoItemFieldValues();

		InfoFieldValue<Object> infoFieldValue =
			infoItemFieldValues.getInfoFieldValue(fieldName);

		Assert.assertEquals(value, infoFieldValue.getValue(locale));
	}

	private DDMForm _deserialize(String content) {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_jsonDDMFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private CollectionQuery _getCollectionQuery(
		String fieldName, JournalArticle journalArticle,
		Pagination pagination) {

		CollectionQuery collectionQuery = new CollectionQuery();

		collectionQuery.setConfiguration(
			HashMapBuilder.put(
				"fieldNames", new String[] {fieldName}
			).build());

		collectionQuery.setPagination(pagination);
		collectionQuery.setRelatedItemObject(journalArticle);

		return collectionQuery;
	}

	private String _readFileToString(String fileName) throws Exception {
		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(
			"dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	@Inject(filter = "ddm.form.deserializer.type=json")
	private static DDMFormDeserializer _jsonDDMFormDeserializer;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Portal _portal;

	@Inject(
		filter = "component.name=com.liferay.journal.web.internal.info.collection.provider.JournalRepeatableFieldsInfoCollectionProvider"
	)
	private RepeatableFieldInfoItemCollectionProvider
		_repeatableFieldInfoItemCollectionProvider;

}