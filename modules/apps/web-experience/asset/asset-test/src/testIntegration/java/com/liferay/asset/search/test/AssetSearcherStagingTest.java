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

package com.liferay.asset.search.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.asset.kernel.service.persistence.AssetEntryQuery;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.persistence.UserGroupRolePK;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.RoleTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.test.util.DocumentsAssert;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.asset.util.AssetSearcher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

/**
 * @author Bryan Engler
 * @author André de Oliveira
 */
@RunWith(Arquillian.class)
@Sync
public class AssetSearcherStagingTest {

	@ClassRule
	@Rule
	public static final TestRule testRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		SynchronousDestinationTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testSiteRolePermissions() throws Exception {
		Role role = addRole(RoleConstants.TYPE_SITE);

		String className = "com.liferay.journal.model.JournalArticle";

		RoleTestUtil.addResourcePermission(
			role, className, ResourceConstants.SCOPE_GROUP_TEMPLATE, "0",
			ActionKeys.VIEW);

		User user = addUser();

		ServiceTestUtil.setUser(user);

		addUserGroupRole(user, role);

		addJournalArticle();

		GroupTestUtil.enableLocalStaging(_group);

		SearchContext searchContext = getSearchContext();

		Group stagingGroup = _group.getStagingGroup();

		searchContext.setGroupIds(new long[] {stagingGroup.getGroupId()});

		searchContext.setUserId(user.getUserId());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.addSelectedFieldNames(Field.GROUP_ID, Field.STAGING_GROUP);

		AssetEntryQuery assetEntryQuery = getAssetEntryQuery(className);

		Hits hits = search(assetEntryQuery, searchContext);

		Document[] documents = hits.getDocs();

		DocumentsAssert.assertCount(
			hits.toString(), documents, Field.COMPANY_ID, 1);

		Document document = documents[0];

		assertField(
			document, Field.GROUP_ID,
			String.valueOf(stagingGroup.getGroupId()));
		assertField(document, Field.STAGING_GROUP, StringPool.TRUE);
	}

	protected JournalArticle addJournalArticle() throws Exception {
		Map<Locale, String> titleMap = new HashMap<>();

		titleMap.put(Locale.US, RandomTestUtil.randomString());

		Map<Locale, String> descriptionMap = new HashMap<>();

		descriptionMap.put(Locale.US, RandomTestUtil.randomString());

		String ddmStructureKey = "BASIC-WEB-CONTENT";
		String ddmTemplateKey = "BASIC-WEB-CONTENT";

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		JournalArticle journalArticle = _journalArticleLocalService.addArticle(
			TestPropsValues.getUserId(), _group.getGroupId(), 0, titleMap,
			descriptionMap, DDMStructureTestUtil.getSampleStructuredContent(),
			ddmStructureKey, ddmTemplateKey, serviceContext);

		_journalArticles.add(journalArticle);

		return journalArticle;
	}

	protected Role addRole(int roleType) throws Exception {
		Role role = RoleTestUtil.addRole(roleType);

		_roles.add(role);

		return role;
	}

	protected User addUser() throws Exception {
		User user = UserTestUtil.addUser(_group.getGroupId());

		_users.add(user);

		return user;
	}

	protected UserGroupRole addUserGroupRole(User user, Role role) {
		UserGroupRole userGroupRole =
			_userGroupRoleLocalService.addUserGroupRole(
				_userGroupRoleLocalService.createUserGroupRole(
					new UserGroupRolePK(
						user.getUserId(), _group.getGroupId(),
						role.getRoleId())));

		_userGroupRoles.add(userGroupRole);

		return userGroupRole;
	}

	protected void assertField(Document document, String field, String value) {
		Assert.assertEquals(document.toString(), document.get(field), value);
	}

	protected AssetEntryQuery getAssetEntryQuery(String... classNames) {
		AssetEntryQuery assetEntryQuery = new AssetEntryQuery();

		assetEntryQuery.setClassNameIds(getClassNameIds(classNames));
		assetEntryQuery.setGroupIds(new long[] {_group.getGroupId()});

		return assetEntryQuery;
	}

	protected long[] getClassNameIds(String... classNames) {
		Stream<String> stream = Stream.of(classNames);

		return stream.mapToLong(
			PortalUtil::getClassNameId
		).toArray();
	}

	protected SearchContext getSearchContext() {
		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(_group.getCompanyId());

		return searchContext;
	}

	protected Hits search(
			AssetEntryQuery assetEntryQuery, SearchContext searchContext)
		throws Exception {

		AssetSearcher assetSearcher = new AssetSearcher();

		assetSearcher.setAssetEntryQuery(assetEntryQuery);

		return assetSearcher.search(searchContext);
	}

	@Inject
	private static JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private static UserGroupRoleLocalService _userGroupRoleLocalService;

	@Inject
	private static UserLocalService _userLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private final List<JournalArticle> _journalArticles = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<Role> _roles = new ArrayList<>();

	@DeleteAfterTestRun
	private final List<UserGroupRole> _userGroupRoles = new ArrayList<>();

	private final List<User> _users = new ArrayList<>();

}