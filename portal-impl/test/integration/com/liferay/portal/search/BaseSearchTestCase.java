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

package com.liferay.portal.search;

import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.trash.TrashHandler;
import com.liferay.portal.kernel.trash.TrashHandlerRegistryUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowThreadLocal;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.model.ClassedModel;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.SearchContextTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Eudaldo Alonso
 * @author Tibor Lipusz
 */
public abstract class BaseSearchTestCase {

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();
	}

	@Test
	public void testBaseModelUserPermissions() throws Exception {
		testUserPermissions(false, true);
	}

	@Test
	public void testLocalizedSearch() throws Exception {
		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		Map<Locale, String> keywordsMap = new HashMap<Locale, String>();

		keywordsMap.put(LocaleUtil.getDefault(), "entity title");
		keywordsMap.put(LocaleUtil.HUNGARY, "entitas neve");

		baseModel = addBaseModelWithWorkflow(
			parentBaseModel, true, keywordsMap, serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		searchContext.setAttribute(Field.TITLE, "nev");
		searchContext.setKeywords("nev");
		searchContext.setLocale(LocaleUtil.HUNGARY);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	@Test
	public void testParentBaseModelUserPermissions() throws Exception {
		testUserPermissions(true, false);
	}

	@Test
	public void testSearchAttachments() throws Exception {
		searchAttachments();
	}

	@Test
	public void testSearchBaseModel() throws Exception {
		searchBaseModel();
	}

	@Test
	public void testSearchBaseModelWithDelete() throws Exception {
		searchBaseModelWithDelete();
	}

	@Test
	public void testSearchBaseModelWithTrash() throws Exception {
		searchBaseModelWithTrash();
	}

	@Test
	public void testSearchBaseModelWithUpdate() throws Exception {
		searchBaseModelWithUpdate();
	}

	@Test
	public void testSearchByDDMStructureField() throws Exception {
		searchByDDMStructureField();
	}

	@Test
	public void testSearchByKeywords() throws Exception {
		searchByKeywords();
	}

	@Test
	public void testSearchByKeywordsInsideParentBaseModel() throws Exception {
		searchByKeywordsInsideParentBaseModel();
	}

	@Test
	public void testSearchComments() throws Exception {
		searchComments();
	}

	@Test
	public void testSearchExpireAllVersions() throws Exception {
		searchExpireVersions(false);
	}

	@Test
	public void testSearchExpireLatestVersion() throws Exception {
		searchExpireVersions(true);
	}

	@Test
	public void testSearchMyEntries() throws Exception {
		searchMyEntries();
	}

	@Test
	public void testSearchRecentEntries() throws Exception {
		searchRecentEntries();
	}

	@Test
	public void testSearchStatus() throws Exception {
		searchStatus();
	}

	@Test
	public void testSearchVersions() throws Exception {
		searchVersions();
	}

	@Test
	public void testSearchWithinDDMStructure() throws Exception {
		searchWithinDDMStructure();
	}

	protected void addAttachment(ClassedModel classedModel) throws Exception {
	}

	protected BaseModel<?> addBaseModel(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		boolean workflowEnabled = WorkflowThreadLocal.isEnabled();

		try {
			WorkflowThreadLocal.setEnabled(true);

			BaseModel<?> baseModel = addBaseModelWithWorkflow(
				parentBaseModel, approved, keywords, serviceContext);

			return baseModel;
		}
		finally {
			WorkflowThreadLocal.setEnabled(workflowEnabled);
		}
	}

	protected BaseModel<?> addBaseModelWithDDMStructure(
			BaseModel<?> parentBaseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		return addBaseModel(parentBaseModel, true, keywords, serviceContext);
	}

	protected BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved,
			Map<Locale, String> keywordsMap, ServiceContext serviceContext)
		throws Exception {

		return addBaseModelWithWorkflow(
			parentBaseModel, approved, keywordsMap.get(LocaleUtil.getDefault()),
			serviceContext);
	}

	protected abstract BaseModel<?> addBaseModelWithWorkflow(
			BaseModel<?> parentBaseModel, boolean approved, String keywords,
			ServiceContext serviceContext)
		throws Exception;

	protected void addComment(
			ClassedModel classedModel, String body,
			ServiceContext serviceContext)
		throws Exception {

		User user = TestPropsValues.getUser();

		List<MBMessage> messages = MBMessageLocalServiceUtil.getMessages(
			getBaseModelClassName(), getBaseModelClassPK(classedModel),
			WorkflowConstants.STATUS_ANY);

		MBMessage message = messages.get(0);

		MBMessageLocalServiceUtil.addDiscussionMessage(
			user.getUserId(), user.getFullName(),
			serviceContext.getScopeGroupId(), getBaseModelClassName(),
			getBaseModelClassPK(classedModel), message.getThreadId(),
			message.getMessageId(), message.getSubject(), body, serviceContext);
	}

	protected void deleteBaseModel(BaseModel<?> baseModel) throws Exception {
		deleteBaseModel((Long)baseModel.getPrimaryKeyObj());
	}

	protected void deleteBaseModel(long primaryKey) throws Exception {
	}

	protected void expireBaseModelVersions(
			BaseModel<?> baseModel, boolean expireAllVersions,
			ServiceContext serviceContext)
		throws Exception {
	}

	protected abstract Class<?> getBaseModelClass();

	protected String getBaseModelClassName() {
		Class<?> clazz = getBaseModelClass();

		return clazz.getName();
	}

	protected Long getBaseModelClassPK(ClassedModel classedModel) {
		return (Long)classedModel.getPrimaryKeyObj();
	}

	protected String getDDMStructureFieldName() {
		return StringPool.BLANK;
	}

	protected BaseModel<?> getParentBaseModel(
			BaseModel<?> parentBaseModel, ServiceContext serviceContext)
		throws Exception {

		return parentBaseModel;
	}

	protected BaseModel<?> getParentBaseModel(
			Group group, ServiceContext serviceContext)
		throws Exception {

		return group;
	}

	protected String getParentBaseModelClassName() {
		return StringPool.BLANK;
	}

	protected abstract String getSearchKeywords();

	protected boolean isCheckBaseModelPermission() {
		return CHECK_BASE_MODEL_PERMISSION;
	}

	protected boolean isExpirableAllVersions() {
		return false;
	}

	protected void moveBaseModelToTrash(long primaryKey) throws Exception {
	}

	protected void moveParentBaseModelToTrash(long primaryKey)
		throws Exception {
	}

	protected void searchAttachments() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setIncludeAttachments(true);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		baseModel = addBaseModel(
			parentBaseModel, true, RandomTestUtil.randomString(),
			serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		addAttachment(baseModel);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 2,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	protected void searchBaseModel() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		baseModel = addBaseModel(
			parentBaseModel, true, RandomTestUtil.randomString(),
			serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	protected int searchBaseModelsCount(
			Class<?> clazz, long groupId, SearchContext searchContext)
		throws Exception {

		Indexer indexer = IndexerRegistryUtil.getIndexer(clazz);

		searchContext.setGroupIds(new long[] {groupId});

		Hits results = indexer.search(searchContext);

		return results.getLength();
	}

	protected int searchBaseModelsCount(
			Class<?> clazz, long groupId, String keywords,
			SearchContext searchContext)
		throws Exception {

		searchContext.setKeywords(keywords);

		return searchBaseModelsCount(clazz, groupId, searchContext);
	}

	protected void searchBaseModelWithDelete() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		baseModel = addBaseModel(
			parentBaseModel, true, getSearchKeywords(), serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		deleteBaseModel(baseModel);

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	protected void searchBaseModelWithTrash() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		baseModel = addBaseModel(
			parentBaseModel, true, getSearchKeywords(), serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		searchContext.setKeywords(getSearchKeywords());

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	protected void searchBaseModelWithUpdate() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		searchContext.setKeywords(getSearchKeywords());

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		baseModel = addBaseModel(
			parentBaseModel, true, getSearchKeywords(), serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		String updatedKeywords = RandomTestUtil.randomString();

		baseModel = updateBaseModel(baseModel, updatedKeywords, serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		searchContext.setKeywords(updatedKeywords);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	protected void searchByDDMStructureField() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModelWithDDMStructure(
			parentBaseModel, getSearchKeywords(), serviceContext);

		searchContext.setAttribute(
			"ddmStructureFieldName", getDDMStructureFieldName());
		searchContext.setAttribute(
			"ddmStructureFieldValue", getSearchKeywords());

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		updateDDMStructure(serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	protected void searchByKeywords() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setKeywords(getSearchKeywords());

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(
			parentBaseModel, true, getSearchKeywords(), serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	protected void searchByKeywordsInsideParentBaseModel() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		baseModel = addBaseModel(
			null, true, getSearchKeywords(), serviceContext);

		BaseModel<?> parentBaseModel1 = getParentBaseModel(
			group, serviceContext);

		searchContext.setFolderIds(
			new long[] {(Long)parentBaseModel1.getPrimaryKeyObj()});
		searchContext.setKeywords(getSearchKeywords());

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		baseModel = addBaseModel(
			parentBaseModel1, true, getSearchKeywords(), serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		BaseModel<?> parentBaseModel2 = getParentBaseModel(
			parentBaseModel1, serviceContext);

		baseModel = addBaseModel(
			parentBaseModel2, true, getSearchKeywords(), serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 2,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	protected void searchComments() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setIncludeDiscussions(true);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		baseModel = addBaseModel(
			parentBaseModel, true, RandomTestUtil.randomString(),
			serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		addComment(baseModel, getSearchKeywords(), serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 2,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		moveBaseModelToTrash((Long)baseModel.getPrimaryKeyObj());

		Assert.assertEquals(
			initialBaseModelsSearchCount,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	protected void searchExpireVersions(boolean expireAllVersions)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setKeywords(getSearchKeywords());

		int initialBaseModelsCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(
			parentBaseModel, true, getSearchKeywords(), serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		baseModel = updateBaseModel(baseModel, "liferay", serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		expireBaseModelVersions(baseModel, expireAllVersions, serviceContext);

		if (expireAllVersions && isExpirableAllVersions()) {
			Assert.assertEquals(
				initialBaseModelsCount,
				searchBaseModelsCount(
					getBaseModelClass(), group.getGroupId(), searchContext));
		}
		else {
			Assert.assertEquals(
				initialBaseModelsCount + 1,
				searchBaseModelsCount(
					getBaseModelClass(), group.getGroupId(), searchContext));
		}
	}

	protected long searchGroupEntriesCount(long groupId, long userId)
		throws Exception {

		return -1;
	}

	protected void searchMyEntries() throws Exception {
		User user1 = UserTestUtil.addUser(null, 0);

		long initialUser1SearchGroupEntriesCount = searchGroupEntriesCount(
			group.getGroupId(), user1.getUserId());

		User user2 = UserTestUtil.addUser(null, 0);

		long initialUser2SearchGroupEntriesCount = searchGroupEntriesCount(
			group.getGroupId(), user2.getUserId());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel1 = getParentBaseModel(
			group, serviceContext);
		BaseModel<?> parentBaseModel2 = getParentBaseModel(
			group, serviceContext);

		String name = PrincipalThreadLocal.getName();

		long userId = serviceContext.getUserId();

		try {
			PrincipalThreadLocal.setName(user1.getUserId());

			serviceContext.setUserId(user1.getUserId());

			baseModel = addBaseModel(
				parentBaseModel1, true, RandomTestUtil.randomString(),
				serviceContext);
			baseModel = addBaseModel(
				parentBaseModel1, true, RandomTestUtil.randomString(),
				serviceContext);
			baseModel = addBaseModel(
				parentBaseModel2, true, RandomTestUtil.randomString(),
				serviceContext);

			PrincipalThreadLocal.setName(user2.getUserId());

			serviceContext.setUserId(user2.getUserId());

			baseModel = addBaseModel(
				parentBaseModel1, true, RandomTestUtil.randomString(),
				serviceContext);
			baseModel = addBaseModel(
				parentBaseModel2, true, RandomTestUtil.randomString(),
				serviceContext);
		}
		finally {
			PrincipalThreadLocal.setName(name);

			serviceContext.setUserId(userId);
		}

		Assert.assertEquals(
			initialUser1SearchGroupEntriesCount + 3,
			searchGroupEntriesCount(group.getGroupId(), user1.getUserId()));
		Assert.assertEquals(
			initialUser2SearchGroupEntriesCount + 2,
			searchGroupEntriesCount(group.getGroupId(), user2.getUserId()));

		moveParentBaseModelToTrash((Long)parentBaseModel2.getPrimaryKeyObj());

		Assert.assertEquals(
			initialUser1SearchGroupEntriesCount + 2,
			searchGroupEntriesCount(group.getGroupId(), user1.getUserId()));
		Assert.assertEquals(
			initialUser2SearchGroupEntriesCount + 1,
			searchGroupEntriesCount(group.getGroupId(), user2.getUserId()));

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				getParentBaseModelClassName());

		parentTrashHandler.restoreTrashEntry(
			user1.getUserId(), (Long)parentBaseModel2.getPrimaryKeyObj());

		Assert.assertEquals(
			initialUser1SearchGroupEntriesCount + 3,
			searchGroupEntriesCount(group.getGroupId(), user1.getUserId()));
		Assert.assertEquals(
			initialUser2SearchGroupEntriesCount + 2,
			searchGroupEntriesCount(group.getGroupId(), user2.getUserId()));
	}

	protected void searchRecentEntries() throws Exception {
		long initialSearchGroupEntriesCount = searchGroupEntriesCount(
			group.getGroupId(), 0);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		BaseModel<?> parentBaseModel1 = getParentBaseModel(
			group, serviceContext);
		BaseModel<?> parentBaseModel2 = getParentBaseModel(
			group, serviceContext);

		String name = PrincipalThreadLocal.getName();

		try {
			User user1 = UserTestUtil.addUser(null, 0);

			PrincipalThreadLocal.setName(user1.getUserId());

			baseModel = addBaseModel(
				parentBaseModel1, true, RandomTestUtil.randomString(),
				serviceContext);
			baseModel = addBaseModel(
				parentBaseModel1, true, RandomTestUtil.randomString(),
				serviceContext);
			baseModel = addBaseModel(
				parentBaseModel2, true, RandomTestUtil.randomString(),
				serviceContext);

			User user2 = UserTestUtil.addUser(null, 0);

			PrincipalThreadLocal.setName(user2.getUserId());

			baseModel = addBaseModel(
				parentBaseModel1, true, RandomTestUtil.randomString(),
				serviceContext);
			baseModel = addBaseModel(
				parentBaseModel2, true, RandomTestUtil.randomString(),
				serviceContext);
		}
		finally {
			PrincipalThreadLocal.setName(name);
		}

		Assert.assertEquals(
			initialSearchGroupEntriesCount + 5,
			searchGroupEntriesCount(group.getGroupId(), 0));

		moveParentBaseModelToTrash((Long)parentBaseModel2.getPrimaryKeyObj());

		Assert.assertEquals(
			initialSearchGroupEntriesCount + 3,
			searchGroupEntriesCount(group.getGroupId(), 0));

		TrashHandler parentTrashHandler =
			TrashHandlerRegistryUtil.getTrashHandler(
				getParentBaseModelClassName());

		parentTrashHandler.restoreTrashEntry(
			TestPropsValues.getUserId(),
			(Long)parentBaseModel2.getPrimaryKeyObj());

		Assert.assertEquals(
			initialSearchGroupEntriesCount + 5,
			searchGroupEntriesCount(group.getGroupId(), 0));
	}

	protected void searchStatus() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		int initialBaseModelsCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), "1.0", searchContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(
			parentBaseModel, false, "Version 1.0", serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		baseModel = updateBaseModel(baseModel, "Version 1.1", serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), "1.0", searchContext));
		Assert.assertEquals(
			initialBaseModelsCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), "1.1", searchContext));

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_SAVE_DRAFT);

		baseModel = updateBaseModel(baseModel, "Version 1.2", serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), "1.1", searchContext));
		Assert.assertEquals(
			initialBaseModelsCount,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), "1.2", searchContext));

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		baseModel = updateBaseModel(baseModel, "Version 1.3", serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), "1.2", searchContext));
		Assert.assertEquals(
			initialBaseModelsCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), "1.3", searchContext));
	}

	protected void searchVersions() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setKeywords(getSearchKeywords());

		int initialBaseModelsCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModel(
			parentBaseModel, true, getSearchKeywords(), serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		baseModel = updateBaseModel(baseModel, "liferay", serviceContext);

		Assert.assertEquals(
			initialBaseModelsCount,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));

		baseModel = updateBaseModel(baseModel, "portal", serviceContext);

		searchContext.setKeywords("portal");

		Assert.assertEquals(
			initialBaseModelsCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	protected void searchWithinDDMStructure() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setKeywords(getSearchKeywords());

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		baseModel = addBaseModelWithDDMStructure(
			parentBaseModel,  getSearchKeywords(), serviceContext);

		Assert.assertEquals(
			initialBaseModelsSearchCount + 1,
			searchBaseModelsCount(
				getBaseModelClass(), group.getGroupId(), searchContext));
	}

	protected void testUserPermissions(
			boolean addBaseModelPermission,
			boolean addParentBaseModelPermission)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(group.getGroupId());

		SearchContext searchContext = SearchContextTestUtil.getSearchContext(
			group.getGroupId());

		searchContext.setKeywords(getSearchKeywords());

		int initialBaseModelsSearchCount = searchBaseModelsCount(
			getBaseModelClass(), group.getGroupId(), searchContext);

		serviceContext.setAddGroupPermissions(addParentBaseModelPermission);
		serviceContext.setAddGuestPermissions(addParentBaseModelPermission);

		BaseModel<?> parentBaseModel = getParentBaseModel(
			group, serviceContext);

		serviceContext.setAddGroupPermissions(addBaseModelPermission);
		serviceContext.setAddGuestPermissions(addBaseModelPermission);

		baseModel = addBaseModel(
			parentBaseModel, true, getSearchKeywords(), serviceContext);

		User user = UserTestUtil.addUser(null, 0);

		PermissionChecker originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		try {
			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			searchContext.setUserId(user.getUserId());

			int baseModelsCount =  initialBaseModelsSearchCount;

			if (addBaseModelPermission && !isCheckBaseModelPermission()) {
				baseModelsCount++;
			}

			Assert.assertEquals(
				baseModelsCount,
				searchBaseModelsCount(
					getBaseModelClass(), group.getGroupId(), searchContext));
		}
		finally {
			PermissionThreadLocal.setPermissionChecker(
				originalPermissionChecker);
		}
	}

	protected BaseModel<?> updateBaseModel(
			BaseModel<?> baseModel, String keywords,
			ServiceContext serviceContext)
		throws Exception {

		return baseModel;
	}

	protected void updateDDMStructure(ServiceContext serviceContext)
		throws Exception {
	}

	protected final boolean CHECK_BASE_MODEL_PERMISSION = true;

	protected BaseModel<?> baseModel;

	@DeleteAfterTestRun
	protected Group group;

}