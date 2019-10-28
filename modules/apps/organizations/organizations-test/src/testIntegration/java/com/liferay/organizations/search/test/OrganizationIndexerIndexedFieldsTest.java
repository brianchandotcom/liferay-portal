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

package com.liferay.organizations.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.expando.kernel.model.ExpandoColumn;
import com.liferay.expando.kernel.model.ExpandoColumnConstants;
import com.liferay.expando.kernel.model.ExpandoTable;
import com.liferay.expando.kernel.service.ExpandoColumnLocalService;
import com.liferay.expando.kernel.service.ExpandoTableLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineHelper;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.CountryService;
import com.liferay.portal.kernel.service.OrganizationService;
import com.liferay.portal.kernel.service.RegionService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.test.util.ExpandoTableSearchFixture;
import com.liferay.portal.search.test.util.FieldValuesAssert;
import com.liferay.portal.search.test.util.IndexedFieldsFixture;
import com.liferay.portal.search.test.util.IndexerFixture;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;
import com.liferay.users.admin.test.util.search.UserSearchFixture;

import java.io.Serializable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Igor Fabiano Nazar
 * @author Luan Maoski
 */
@RunWith(Arquillian.class)
public class OrganizationIndexerIndexedFieldsTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE,
			SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		setUpExpandoTableSearchFixture();
		setUpIndexedFieldsFixture();
		setUpOrganizationIndexerFixture();

		setUpUserSearchFixture();

		setUpOrganizationFixture();
	}

	@Test
	public void testIndexedFields() throws Exception {
		Organization organization = organizationFixture.createOrganization(
			"abcd efgh");

		String searchTerm = "abcd";

		Document document = organizationIndexerFixture.searchOnlyOne(
			searchTerm);

		indexedFieldsFixture.postProcessDocument(document);

		FieldValuesAssert.assertFieldValues(
			_expectedFieldValues(organization), document, searchTerm);
	}

	@Test
	public void testIndexedFieldsWithExpando() throws Exception {
		String expandoColumnName = "expandoColumnName";
		String expandoColumnObs = "expandoColumnObs";

		expandoTableSearchFixture.addExpandoColumn(
			Organization.class, ExpandoColumnConstants.INDEX_TYPE_KEYWORD,
			expandoColumnObs, expandoColumnName);

		Map<String, Serializable> expandoValues =
			HashMapBuilder.<String, Serializable>put(
				expandoColumnName, "Software Developer"
			).put(
				expandoColumnObs, "Software Engineer"
			).build();

		Organization organization = organizationFixture.createOrganization(
			"My Organization", expandoValues);

		String searchTerm = "Developer";

		Document document = organizationIndexerFixture.searchOnlyOne(
			searchTerm);

		indexedFieldsFixture.postProcessDocument(document);

		FieldValuesAssert.assertFieldValues(
			_expectedFieldValuesWithExpando(organization), document,
			searchTerm);
	}

	protected void setUpExpandoTableSearchFixture() {
		expandoTableSearchFixture = new ExpandoTableSearchFixture(
			classNameLocalService, expandoColumnLocalService,
			expandoTableLocalService);

		_expandoColumns = expandoTableSearchFixture.getExpandoColumns();
		_expandoTables = expandoTableSearchFixture.getExpandoTables();
	}

	protected void setUpIndexedFieldsFixture() {
		indexedFieldsFixture = new IndexedFieldsFixture(
			resourcePermissionLocalService, searchEngineHelper);
	}

	protected void setUpOrganizationFixture() throws Exception {
		organizationFixture = new OrganizationFixture(
			organizationService, countryService, regionService);

		organizationFixture.setUp();

		organizationFixture.setGroup(group);

		_organizations = organizationFixture.getOrganizations();
	}

	protected void setUpOrganizationIndexerFixture() {
		organizationIndexerFixture = new IndexerFixture<>(Organization.class);
	}

	protected void setUpUserSearchFixture() throws Exception {
		userSearchFixture = new UserSearchFixture();

		userSearchFixture.setUp();

		_groups = userSearchFixture.getGroups();
		_users = userSearchFixture.getUsers();

		group = userSearchFixture.addGroup();
	}

	@Inject
	protected ClassNameLocalService classNameLocalService;

	@Inject
	protected CountryService countryService;

	@Inject
	protected ExpandoColumnLocalService expandoColumnLocalService;

	@Inject
	protected ExpandoTableLocalService expandoTableLocalService;

	protected ExpandoTableSearchFixture expandoTableSearchFixture;
	protected Group group;
	protected IndexedFieldsFixture indexedFieldsFixture;
	protected OrganizationFixture organizationFixture;
	protected IndexerFixture<Organization> organizationIndexerFixture;

	@Inject
	protected OrganizationService organizationService;

	@Inject
	protected RegionService regionService;

	@Inject
	protected ResourcePermissionLocalService resourcePermissionLocalService;

	@Inject
	protected SearchEngineHelper searchEngineHelper;

	protected UserSearchFixture userSearchFixture;

	private Map<String, String> _expectedFieldValues(Organization organization)
		throws Exception {

		String countryName = organizationFixture.getCountryNames(organization);

		Region region = regionService.getRegion(organization.getRegionId());

		String regionName = StringUtil.toLowerCase(region.getName());

		Map<String, String> map = new HashMap<>();

		map.put(Field.COMPANY_ID, String.valueOf(organization.getCompanyId()));
		map.put(Field.ENTRY_CLASS_NAME, Organization.class.getName());
		map.put(
			Field.ENTRY_CLASS_PK,
			String.valueOf(organization.getOrganizationId()));
		map.put(Field.NAME, organization.getName());
		map.put(
			Field.NAME + "_sortable",
			StringUtil.toLowerCase(organization.getName()));
		map.put(
			Field.ORGANIZATION_ID,
			String.valueOf(organization.getOrganizationId()));
		map.put(Field.TREE_PATH, organization.getTreePath());
		map.put(Field.USER_ID, String.valueOf(organization.getUserId()));
		map.put(
			Field.USER_NAME,
			StringUtil.toLowerCase(organization.getUserName()));
		map.put("country", countryName);
		map.put("nameTreePath", organization.getName());
		map.put(
			"nameTreePath_String_sortable",
			StringUtil.toLowerCase(organization.getName()));
		map.put(
			"parentOrganizationId",
			String.valueOf(organization.getParentOrganizationId()));
		map.put("region", regionName);

		indexedFieldsFixture.populateUID(
			Organization.class.getName(), organization.getOrganizationId(),
			map);

		map.put(Field.TYPE, organization.getType());

		_populateDates(organization, map);
		_populateRoles(organization, map);

		return map;
	}

	private Map<String, String> _expectedFieldValuesWithExpando(
			Organization organization)
		throws Exception {

		Map<String, String> expectedFieldValues = _expectedFieldValues(
			organization);

		expectedFieldValues.put(
			"expando__keyword__custom_fields__expandoColumnName",
			"Software Developer");
		expectedFieldValues.put(
			"expando__keyword__custom_fields__expandoColumnObs",
			"Software Engineer");

		return expectedFieldValues;
	}

	private void _populateDates(
		Organization organization, Map<String, String> map) {

		indexedFieldsFixture.populateDate(
			Field.CREATE_DATE, organization.getCreateDate(), map);
		indexedFieldsFixture.populateDate(
			Field.MODIFIED_DATE, organization.getModifiedDate(), map);
	}

	private void _populateRoles(
			Organization organization, Map<String, String> map)
		throws Exception {

		indexedFieldsFixture.populateRoleIdFields(
			organization.getCompanyId(), Organization.class.getName(),
			organization.getOrganizationId(), organization.getGroupId(), null,
			map);
	}

	@DeleteAfterTestRun
	private List<ExpandoColumn> _expandoColumns;

	@DeleteAfterTestRun
	private List<ExpandoTable> _expandoTables;

	@DeleteAfterTestRun
	private List<Group> _groups;

	@DeleteAfterTestRun
	private List<Organization> _organizations;

	@DeleteAfterTestRun
	private List<User> _users;

}