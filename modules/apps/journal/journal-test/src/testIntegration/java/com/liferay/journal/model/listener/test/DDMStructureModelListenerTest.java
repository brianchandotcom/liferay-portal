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

package com.liferay.journal.model.listener.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.data.engine.rest.test.util.DataDefinitionTestUtil;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.Value;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.Arrays;
import java.util.List;
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
 * @author Lourdes Fernández Besada
 */
@RunWith(Arquillian.class)
public class DDMStructureModelListenerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		Class<?> clazz = getClass();

		String json = StringUtil.read(
			clazz.getResourceAsStream("dependencies/data_definition.json"));

		_dataDefinition = DataDefinitionTestUtil.addDataDefinition(
			"journal", _dataDefinitionResourceFactory, _group.getGroupId(),
			json, TestPropsValues.getUser());
	}

	@Test
	public void testUpdateDataDefinition() throws Exception {
		JournalArticle journalArticle = _addJournalArticle();

		_updateDataDefinition("dependencies/updated_data_definition.json");

		JournalArticle updatedJournalArticle =
			_journalArticleLocalService.getJournalArticle(
				journalArticle.getId());

		_assertDDMFormFieldValuesMap(
			_expectedUpdatedFieldValuesMap,
			updatedJournalArticle.getDDMFormValues());
	}

	@Test
	public void testUpdateDataDefinitionStructureKeyChanged() throws Exception {
		JournalArticle journalArticle = _addJournalArticle();

		_updateDataDefinition("dependencies/data_definition.json");

		journalArticle = _journalArticleLocalService.getJournalArticle(
			journalArticle.getId());

		_assertDDMFormFieldValuesMap(
			_expectedFieldValuesMap, journalArticle.getDDMFormValues());
	}

	@Test
	public void testUpdateDataDefinitionThrowsRuntimeException()
		throws Exception {

		Bundle bundle = FrameworkUtil.getBundle(
			DDMStructureModelListenerTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		JournalArticle journalArticle = _addJournalArticle();

		String expectedContent = journalArticle.getContent();

		RuntimeException runtimeException = new RuntimeException(
			RandomTestUtil.randomString());

		ServiceRegistration<?> serviceRegistration = null;

		try {
			serviceRegistration = bundleContext.registerService(
				ModelListener.class,
				new TestDDMStructureModelListener(runtimeException),
				new HashMapDictionary<>());

			Exception exception1 = null;

			try {
				_updateDataDefinition(
					"dependencies/updated_data_definition.json");
			}
			catch (Exception exception2) {
				exception1 = exception2;
			}

			Assert.assertNotNull(exception1);

			Assert.assertEquals(
				runtimeException.getMessage(), exception1.getMessage());

			journalArticle = _journalArticleLocalService.getJournalArticle(
				journalArticle.getId());

			_assertDDMFormFieldValuesMap(
				_expectedFieldValuesMap, journalArticle.getDDMFormValues());

			Assert.assertEquals(expectedContent, journalArticle.getContent());
		}
		finally {
			if (serviceRegistration != null) {
				serviceRegistration.unregister();
			}
		}
	}

	private JournalArticle _addJournalArticle() throws Exception {
		Class<?> clazz = getClass();

		String content = StringUtil.read(
			clazz.getResourceAsStream(
				"dependencies/journal_article_content.xml"));

		JournalArticle journalArticle =
			JournalTestUtil.addArticleWithXMLContent(
				_group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				JournalArticleConstants.CLASS_NAME_ID_DEFAULT, content,
				_dataDefinition.getDataDefinitionKey(), null, LocaleUtil.US);

		_assertDDMFormFieldValuesMap(
			_expectedFieldValuesMap, journalArticle.getDDMFormValues());

		return journalArticle;
	}

	private void _assertDDMFormFieldValuesMap(
		Map<String, List<String>> expectedFieldValuesMap,
		DDMFormValues ddmFormValues) {

		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			ddmFormValues.getDDMFormFieldValuesMap(true);

		Assert.assertEquals(
			ddmFormFieldValuesMap.toString(), expectedFieldValuesMap.size(),
			ddmFormFieldValuesMap.size());

		for (Map.Entry<String, List<String>> entry :
				expectedFieldValuesMap.entrySet()) {

			List<DDMFormFieldValue> ddmFormFieldValues =
				ddmFormFieldValuesMap.get(entry.getKey());

			Assert.assertNotNull(ddmFormFieldValues);

			List<String> expectedFieldValues = entry.getValue();

			Assert.assertEquals(
				ddmFormFieldValues.toString(), expectedFieldValues.size(),
				ddmFormFieldValues.size());

			for (int i = 0; i < expectedFieldValues.size(); i++) {
				String stringValue = StringPool.BLANK;

				DDMFormFieldValue ddmFormFieldValue = ddmFormFieldValues.get(i);

				Value value = ddmFormFieldValue.getValue();

				if (value != null) {
					stringValue = value.getString(LocaleUtil.US);
				}

				Assert.assertEquals(expectedFieldValues.get(i), stringValue);
			}
		}
	}

	private void _updateDataDefinition(String resourceName) throws Exception {
		DataDefinitionResource.Builder dataDefinitionResourcedBuilder =
			_dataDefinitionResourceFactory.create();

		DataDefinitionResource dataDefinitionResource =
			dataDefinitionResourcedBuilder.user(
				TestPropsValues.getUser()
			).build();

		Class<?> clazz = getClass();

		DataDefinition updatedDataDefinition = DataDefinition.toDTO(
			StringUtil.read(clazz.getResourceAsStream(resourceName)));

		updatedDataDefinition.setDataDefinitionKey(
			_dataDefinition.getDataDefinitionKey());

		dataDefinitionResource.putDataDefinition(
			_dataDefinition.getId(), updatedDataDefinition);
	}

	private static final Map<String, List<String>> _expectedFieldValuesMap =
		HashMapBuilder.<String, List<String>>put(
			"Field32391309",
			Arrays.asList(
				"Parent1Child1", "Parent1Child2", "Parent2Child1",
				"Parent3Child1", "Parent3Child2")
		).put(
			"Field68979894",
			Arrays.asList(
				"Parent1GrandChild1", "Parent1GrandChild2",
				"Parent2GrandChild1", "Parent2GrandChild2",
				"Parent3GrandChild1")
		).put(
			"ParentFieldSet",
			Arrays.asList(StringPool.BLANK, StringPool.BLANK, StringPool.BLANK)
		).put(
			"Text80567124", Arrays.asList("Parent1", "Parent2", "Parent3")
		).build();
	private static final Map<String, List<String>>
		_expectedUpdatedFieldValuesMap =
			HashMapBuilder.<String, List<String>>put(
				"Field32391309",
				Arrays.asList(
					"Parent1Child1", "Parent1Child2", "Parent2Child1",
					"Parent3Child1", "Parent3Child2")
			).put(
				"Field68979894",
				Arrays.asList(
					"Parent1GrandChild1", "Parent1GrandChild2",
					"Parent2GrandChild1", "Parent2GrandChild2",
					"Parent3GrandChild1")
			).put(
				"Fieldset78954432",
				Arrays.asList(
					StringPool.BLANK, StringPool.BLANK, StringPool.BLANK)
			).put(
				"ParentFieldSet",
				Arrays.asList(
					StringPool.BLANK, StringPool.BLANK, StringPool.BLANK)
			).put(
				"Text80567124", Arrays.asList("Parent1", "Parent2", "Parent3")
			).build();

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	private DataDefinition _dataDefinition;

	@Inject
	private DataDefinitionResource.Factory _dataDefinitionResourceFactory;

	@DeleteAfterTestRun
	private Group _group;

	private class TestDDMStructureModelListener
		extends BaseModelListener<DDMStructure> {

		@Override
		public void onAfterUpdate(
				DDMStructure originalModel, DDMStructure model)
			throws ModelListenerException {

			throw _runtimeException;
		}

		private TestDDMStructureModelListener(
			RuntimeException runtimeException) {

			_runtimeException = runtimeException;
		}

		private final RuntimeException _runtimeException;

	}

}