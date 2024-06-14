/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.web.internal.info.item.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.constants.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.info.item.provider.DDMStructureRepeatableFieldsInfoItemFieldSetProvider;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.info.field.InfoField;
import com.liferay.info.field.InfoFieldSet;
import com.liferay.info.field.InfoFieldSetEntry;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class DDMStructureRepeatableFieldsInfoItemFieldSetProviderImplTest {

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
	public void testGetRepeatableField() throws Exception {
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				_portal.getClassNameId(JournalArticle.class), _group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			_portal.getClassNameId(JournalArticle.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_deserialize(
				_readFileToString("structure_with_repeatable_field.json")),
			StorageType.DEFAULT.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		List<InfoFieldSetEntry> infoItemFieldSet =
			_ddmStructureRepeatableFieldsInfoItemFieldSetProvider.
				getInfoItemFieldSet(ddmStructure.getStructureId());

		Assert.assertEquals(
			infoItemFieldSet.toString(), 2, infoItemFieldSet.size());

		InfoFieldSetEntry infoFieldSetEntry1 = infoItemFieldSet.get(0);

		Assert.assertTrue(infoFieldSetEntry1 instanceof InfoField<?>);

		InfoField<?> infoField1 = (InfoField<?>)infoFieldSetEntry1;

		Assert.assertEquals("Text1", infoField1.getName());

		InfoFieldSetEntry infoFieldSetEntry2 = infoItemFieldSet.get(1);

		Assert.assertTrue(infoFieldSetEntry2 instanceof InfoField<?>);

		InfoField<?> infoField2 = (InfoField<?>)infoFieldSetEntry2;

		Assert.assertEquals("Text2", infoField2.getName());
	}

	@Test
	public void testGetRepeatableFieldset() throws Exception {
		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				_portal.getClassNameId(JournalArticle.class), _group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			_portal.getClassNameId(JournalArticle.class),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_deserialize(
				_readFileToString("structure_with_repeatable_fieldset.json")),
			StorageType.DEFAULT.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		List<InfoFieldSetEntry> infoItemFieldSet =
			_ddmStructureRepeatableFieldsInfoItemFieldSetProvider.
				getInfoItemFieldSet(ddmStructure.getStructureId());

		Assert.assertEquals(
			infoItemFieldSet.toString(), 1, infoItemFieldSet.size());

		InfoFieldSetEntry infoFieldSetEntry = infoItemFieldSet.get(0);

		Assert.assertTrue(infoFieldSetEntry instanceof InfoFieldSet);

		InfoFieldSet infoFieldSet = (InfoFieldSet)infoFieldSetEntry;

		Assert.assertEquals("Fieldset", infoFieldSet.getName());

		List<InfoField<?>> infoFields = infoFieldSet.getAllInfoFields();

		Assert.assertEquals(infoFields.toString(), 2, infoFields.size());

		InfoField<?> infoField1 = infoFields.get(0);

		Assert.assertEquals("Text", infoField1.getName());

		InfoField<?> infoField2 = infoFields.get(1);

		Assert.assertEquals("Image", infoField2.getName());
	}

	private DDMForm _deserialize(String content) {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_jsonDDMFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private String _readFileToString(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(),
			"com/liferay/dynamic/data/mapping/web/internal/info/item/provider" +
				"/test/dependencies/" + fileName);
	}

	@Inject(filter = "ddm.form.deserializer.type=json")
	private static DDMFormDeserializer _jsonDDMFormDeserializer;

	@Inject
	private DDMStructureRepeatableFieldsInfoItemFieldSetProvider
		_ddmStructureRepeatableFieldsInfoItemFieldSetProvider;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private Portal _portal;

}