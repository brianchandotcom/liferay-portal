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

package com.liferay.message.boards.internal.search;

import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBDiscussion;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.message.boards.service.MBDiscussionLocalService;
import com.liferay.message.boards.service.MBThreadLocalService;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelperUtil;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;
import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eudaldo Alonso
 */
@Component(immediate = true, service = Indexer.class)
public class MBThreadIndexer extends BaseIndexer<MBThread> {

	public static final String CLASS_NAME = MBThread.class.getName();

	public MBThreadIndexer() {
		setDefaultSelectedFieldNames(
			Field.CLASS_NAME_ID, Field.CLASS_PK, Field.COMPANY_ID,
			Field.ENTRY_CLASS_NAME, Field.ENTRY_CLASS_PK, Field.UID);
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		return true;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		addStatus(contextBooleanFilter, searchContext);

		boolean discussion = GetterUtil.getBoolean(
			searchContext.getAttribute("discussion"));

		contextBooleanFilter.addRequiredTerm("discussion", discussion);

		long endDate = GetterUtil.getLong(
			searchContext.getAttribute("endDate"));
		long startDate = GetterUtil.getLong(
			searchContext.getAttribute("startDate"));

		if ((endDate > 0) && (startDate > 0)) {
			contextBooleanFilter.addRangeTerm(
				"lastPostDate", startDate, endDate);
		}

		long participantUserId = GetterUtil.getLong(
			searchContext.getAttribute("participantUserId"));

		if (participantUserId > 0) {
			contextBooleanFilter.addRequiredTerm(
				"participantUserIds", participantUserId);
		}
	}

	@Override
	protected void doDelete(MBThread mbThread) throws Exception {
		SearchContext searchContext = new SearchContext();

		searchContext.setSearchEngineId(getSearchEngineId());

		deleteDocument(mbThread.getCompanyId(), mbThread.getThreadId());
	}

	@Override
	protected Document doGetDocument(MBThread mbThread) throws Exception {
		Document document = getBaseModelDocument(CLASS_NAME, mbThread);

		MBDiscussion discussion =
			mbDiscussionLocalService.fetchThreadDiscussion(
				mbThread.getThreadId());

		if (discussion == null) {
			document.addKeyword("discussion", false);
		}
		else {
			document.addKeyword("discussion", true);
		}

		Date lastPostDate = mbThread.getLastPostDate();

		document.addKeyword("lastPostDate", lastPostDate.getTime());

		document.addKeyword(
			"participantUserIds", mbThread.getParticipantUserIds());

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return null;
	}

	@Override
	protected void doReindex(MBThread mbThread) throws Exception {
		Document document = getDocument(mbThread);

		IndexWriterHelperUtil.updateDocument(
			getSearchEngineId(), mbThread.getCompanyId(), document,
			isCommitImmediately());
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		MBThread thread = mbThreadLocalService.getThread(classPK);

		doReindex(thread);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCategories(companyId);
		reindexDiscussions(companyId);
		reindexRoot(companyId);
	}

	protected void reindexCategories(final long companyId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			mbCategoryLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<MBCategory>() {

				@Override
				public void performAction(MBCategory category)
					throws PortalException {

					reindexThreads(
						companyId, category.getGroupId(),
						category.getCategoryId());
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void reindexDiscussions(final long companyId)
		throws PortalException {

		ActionableDynamicQuery actionableDynamicQuery =
			groupLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Group>() {

				@Override
				public void performAction(Group group) throws PortalException {
					reindexThreads(
						companyId, group.getGroupId(),
						MBCategoryConstants.DISCUSSION_CATEGORY_ID);
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void reindexRoot(final long companyId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			groupLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<Group>() {

				@Override
				public void performAction(Group group) throws PortalException {
					reindexThreads(
						companyId, group.getGroupId(),
						MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
				}

			});

		actionableDynamicQuery.performActions();
	}

	protected void reindexThreads(
			long companyId, long groupId, final long categoryId)
		throws PortalException {

		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			mbThreadLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					Property categoryIdProperty = PropertyFactoryUtil.forName(
						"categoryId");

					dynamicQuery.add(categoryIdProperty.eq(categoryId));

					Property statusProperty = PropertyFactoryUtil.forName(
						"status");

					dynamicQuery.add(
						statusProperty.eq(WorkflowConstants.STATUS_APPROVED));
				}

			});
		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setGroupId(groupId);
		indexableActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<MBThread>() {

				@Override
				public void performAction(MBThread thread) {
					try {
						Document document = getDocument(thread);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index message boards thread " +
									thread.getThreadId(),
								pe);
						}
					}
				}

			});
		indexableActionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		indexableActionableDynamicQuery.performActions();
	}

	@Reference
	protected GroupLocalService groupLocalService;

	@Reference
	protected MBCategoryLocalService mbCategoryLocalService;

	@Reference
	protected MBDiscussionLocalService mbDiscussionLocalService;

	@Reference
	protected MBThreadLocalService mbThreadLocalService;

	private static final Log _log = LogFactoryUtil.getLog(
		MBThreadIndexer.class);

}