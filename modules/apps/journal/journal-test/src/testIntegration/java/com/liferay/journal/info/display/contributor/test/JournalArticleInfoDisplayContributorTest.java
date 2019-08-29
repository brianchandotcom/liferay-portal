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

package com.liferay.journal.info.display.contributor.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializer;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeRequest;
import com.liferay.dynamic.data.mapping.io.DDMFormDeserializerDeserializeResponse;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMStructureConstants;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class JournalArticleInfoDisplayContributorTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		long classNameId = PortalUtil.getClassNameId(JournalArticle.class);

		_ddmStructureTestHelper = new DDMStructureTestHelper(
			classNameId, _group);

		String definition = _read("test-ddm-structure-all-fields.xml");

		DDMForm ddmForm = _deserialize(definition);

		_ddmStructure = _ddmStructureTestHelper.addStructure(
			classNameId, null, "Test Structure", ddmForm,
			StorageType.JSON.getValue(), DDMStructureConstants.TYPE_DEFAULT);

		DDMTemplateTestUtil.addTemplate(
			_group.getGroupId(), PortalUtil.getClassNameId(DDMStructure.class),
			_ddmStructure.getStructureId(),
			PortalUtil.getClassNameId(JournalArticle.class),
			"TEST-TEMPLATE-KEY", TemplateConstants.LANG_TYPE_VM,
			"$name.getData()", PortalUtil.getSiteDefaultLocale(_group));
	}

	@Test
	public void testDDMTemplateAssetDisplayContributorField()
		throws PortalException {

		InfoDisplayContributor<JournalArticle> infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(
				JournalArticle.class.getName());

		Set<InfoDisplayField> infoDisplayFields =
			infoDisplayContributor.getInfoDisplayFields(
				_ddmStructure.getStructureId(), LocaleUtil.ENGLISH);

		Stream<InfoDisplayField> stream = infoDisplayFields.stream();

		List<String> infoDisplayFieldKeys = stream.map(
			infoDisplayField -> infoDisplayField.getKey()
		).collect(
			Collectors.toList()
		);

		Assert.assertTrue(
			infoDisplayFieldKeys.contains("ddmTemplate_TEST_TEMPLATE_KEY"));
	}

	@Test
	public void testNestedDDMAssetDisplayContributorFields()
		throws PortalException {

		InfoDisplayContributor<JournalArticle> infoDisplayContributor =
			_infoDisplayContributorTracker.getInfoDisplayContributor(
				JournalArticle.class.getName());

		Set<InfoDisplayField> infoDisplayFields =
			infoDisplayContributor.getInfoDisplayFields(
				_ddmStructure.getStructureId(), LocaleUtil.ENGLISH);

		Stream<InfoDisplayField> stream = infoDisplayFields.stream();

		List<String> infoDisplayFieldKeys = stream.map(
			infoDisplayField -> infoDisplayField.getKey()
		).collect(
			Collectors.toList()
		);

		Assert.assertTrue(infoDisplayFieldKeys.contains("ext"));
		Assert.assertTrue(infoDisplayFieldKeys.contains("phone"));
	}

	private DDMForm _deserialize(String content) {
		DDMFormDeserializerDeserializeRequest.Builder builder =
			DDMFormDeserializerDeserializeRequest.Builder.newBuilder(content);

		DDMFormDeserializerDeserializeResponse
			ddmFormDeserializerDeserializeResponse =
				_xsdDDMFormDeserializer.deserialize(builder.build());

		return ddmFormDeserializerDeserializeResponse.getDDMForm();
	}

	private String _read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		InputStream inputStream = classLoader.getResourceAsStream(
			"com/liferay/journal/dependencies/" + fileName);

		return StringUtil.read(inputStream);
	}

	@Inject(filter = "ddm.form.deserializer.type=xsd")
	private static DDMFormDeserializer _xsdDDMFormDeserializer;

	private DDMStructure _ddmStructure;
	private DDMStructureTestHelper _ddmStructureTestHelper;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private InfoDisplayContributorTracker _infoDisplayContributorTracker;

}