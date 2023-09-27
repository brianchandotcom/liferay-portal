/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.organizations.object.system.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.field.builder.TextObjectFieldBuilder;
import com.liferay.object.model.ObjectField;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.system.SystemObjectDefinitionManager;
import com.liferay.object.system.SystemObjectDefinitionManagerRegistry;
import com.liferay.portal.kernel.exception.NoSuchOrganizationException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;

import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Pedro Leite
 */
@RunWith(Arquillian.class)
public class OrganizationSystemObjectDefinitionManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_organizationSystemObjectDefinitionManager =
			_systemObjectDefinitionManagerRegistry.
				getSystemObjectDefinitionManager("Organization");
	}

	@Test
	public void testAddBaseModel() throws Exception {
		_assertCount(0);

		String comments1 = RandomTestUtil.randomString();
		String name1 = RandomTestUtil.randomString();

		long organizationId1 = _addBaseModel(
			HashMapBuilder.<String, Object>put(
				"comment", comments1
			).put(
				"name", name1
			).build());

		_assertCount(1);

		String comments2 = RandomTestUtil.randomString();
		String name2 = RandomTestUtil.randomString();

		long organizationId2 = _addBaseModel(
			HashMapBuilder.<String, Object>put(
				"comment", comments2
			).put(
				"name", name2
			).build());

		_assertCount(2);

		Organization organization1 = _organizationLocalService.getOrganization(
			organizationId1);

		Assert.assertEquals(comments1, organization1.getComments());
		Assert.assertEquals(name1, organization1.getName());

		Organization organization2 = _organizationLocalService.getOrganization(
			organizationId2);

		Assert.assertEquals(comments2, organization2.getComments());
		Assert.assertEquals(name2, organization2.getName());

		_organizationLocalService.deleteOrganization(organizationId1);
		_organizationLocalService.deleteOrganization(organizationId2);
	}

	@Test
	public void testDeleteBaseModel() throws Exception {
		long organizationId = _addBaseModel(
			HashMapBuilder.<String, Object>put(
				"comment", RandomTestUtil.randomString()
			).put(
				"name", RandomTestUtil.randomString()
			).build());

		_assertCount(1);

		_organizationSystemObjectDefinitionManager.deleteBaseModel(
			_organizationLocalService.getOrganization(organizationId));

		AssertUtils.assertFailure(
			NoSuchOrganizationException.class,
			"No Organization exists with the primary key " + organizationId,
			() -> _organizationLocalService.getOrganization(organizationId));

		_assertCount(0);
	}

	@Test
	public void testGetObjectFields() throws Exception {
		List<ObjectField> objectFields =
			_organizationSystemObjectDefinitionManager.getObjectFields();

		Assert.assertNotNull(objectFields);
		Assert.assertEquals(objectFields.toString(), 2, objectFields.size());

		ListIterator<ObjectField> iterator = objectFields.listIterator();

		Assert.assertTrue(iterator.hasNext());

		_assertEquals(
			new TextObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(
					LanguageUtil.get(LocaleUtil.getDefault(), "comments"))
			).name(
				"comment"
			).system(
				true
			).build(),
			iterator.next());

		Assert.assertTrue(iterator.hasNext());

		_assertEquals(
			new TextObjectFieldBuilder(
			).labelMap(
				LocalizedMapUtil.getLocalizedMap(
					LanguageUtil.get(LocaleUtil.getDefault(), "name"))
			).name(
				"name"
			).required(
				true
			).system(
				true
			).build(),
			iterator.next());
	}

	@Test
	public void testGetters() throws Exception {
		Assert.assertEquals(
			"L_ORGANIZATION",
			_organizationSystemObjectDefinitionManager.
				getExternalReferenceCode());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap(
				LanguageUtil.get(LocaleUtil.getDefault(), "organization")),
			_organizationSystemObjectDefinitionManager.getLabelMap());
		Assert.assertEquals(
			LocalizedMapUtil.getLocalizedMap(
				LanguageUtil.get(LocaleUtil.getDefault(), "organizations")),
			_organizationSystemObjectDefinitionManager.getPluralLabelMap());
		Assert.assertEquals(
			ObjectDefinitionConstants.SCOPE_COMPANY,
			_organizationSystemObjectDefinitionManager.getScope());
		Assert.assertEquals(
			"name",
			_organizationSystemObjectDefinitionManager.
				getTitleObjectFieldName());
		Assert.assertEquals(
			1, _organizationSystemObjectDefinitionManager.getVersion());
	}

	private long _addBaseModel(Map<String, Object> values) throws Exception {
		return _organizationSystemObjectDefinitionManager.addBaseModel(
			TestPropsValues.getUser(), values);
	}

	private void _assertCount(int count) throws Exception {
		Assert.assertEquals(
			count, _organizationLocalService.getOrganizationsCount());
	}

	private void _assertEquals(
		ObjectField expectedObjectField, ObjectField actualObjectField) {

		Assert.assertEquals(
			expectedObjectField.getDBColumnName(),
			actualObjectField.getDBColumnName());
		Assert.assertEquals(
			expectedObjectField.getDBTableName(),
			actualObjectField.getDBTableName());
		Assert.assertEquals(
			expectedObjectField.getDBType(), actualObjectField.getDBType());
		Assert.assertEquals(
			expectedObjectField.isIndexed(), actualObjectField.isIndexed());
		Assert.assertEquals(
			expectedObjectField.isIndexedAsKeyword(),
			actualObjectField.isIndexedAsKeyword());
		Assert.assertEquals(
			expectedObjectField.getIndexedLanguageId(),
			actualObjectField.getIndexedLanguageId());
		Assert.assertEquals(
			expectedObjectField.getLabelMap(), actualObjectField.getLabelMap());
		Assert.assertEquals(
			expectedObjectField.getName(), actualObjectField.getName());
		Assert.assertEquals(
			expectedObjectField.isRequired(), actualObjectField.isRequired());
		Assert.assertEquals(
			expectedObjectField.isState(), actualObjectField.isState());
	}

	@Inject
	private ObjectDefinitionLocalService _objectDefinitionLocalService;

	@Inject
	private OrganizationLocalService _organizationLocalService;

	private SystemObjectDefinitionManager
		_organizationSystemObjectDefinitionManager;

	@Inject
	private SystemObjectDefinitionManagerRegistry
		_systemObjectDefinitionManagerRegistry;

}