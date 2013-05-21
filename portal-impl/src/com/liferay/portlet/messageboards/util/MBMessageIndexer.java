/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.util;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.persistence.GroupActionableDynamicQuery;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.asset.MBMessageAssetRendererFactory;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryServiceUtil;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryActionableDynamicQuery;
import com.liferay.portlet.messageboards.service.persistence.MBMessageActionableDynamicQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Raymond Augé
 */
public class MBMessageIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {MBMessage.class.getName()};

	public static final String PORTLET_ID = PortletKeys.MESSAGE_BOARDS;

	public MBMessageIndexer() {
		setFilterSearch(true);
		setPermissionAware(true);
	}

	@Override
	public void addRelatedEntryFields(Document document, Object obj)
		throws Exception {

		DLFileEntry dlFileEntry = (DLFileEntry)obj;

		MBMessage message = MBMessageAttachmentsUtil.getMessage(
			dlFileEntry.getFileEntryId());

		document.addKeyword(Field.CATEGORY_ID, message.getCategoryId());
		document.addKeyword(
			Field.CLASS_NAME_ID,
			PortalUtil.getClassNameId(MBMessage.class.getName()));
		document.addKeyword(Field.CLASS_PK, message.getMessageId());
		document.addKeyword(Field.RELATED_ENTRY, true);

		document.addKeyword("discussion", false);
		document.addKeyword("threadId", message.getThreadId());
	}

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		return MBMessagePermission.contains(
			permissionChecker, entryClassPK, ActionKeys.VIEW);
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_APPROVED);

		if (status != WorkflowConstants.STATUS_ANY) {
			contextQuery.addRequiredTerm(Field.STATUS, status);
		}

		boolean discussion = GetterUtil.getBoolean(
			searchContext.getAttribute("discussion"), false);

		contextQuery.addRequiredTerm("discussion", discussion);

		if (searchContext.isIncludeDiscussions()) {
			addRelatedClassNames(contextQuery, searchContext);
		}

		long threadId = GetterUtil.getLong(
			(String)searchContext.getAttribute("threadId"));

		if (threadId > 0) {
			contextQuery.addRequiredTerm("threadId", threadId);
		}

		long[] categoryIds = searchContext.getCategoryIds();

		if ((categoryIds == null) || (categoryIds.length == 0)) {
			return;
		}

		if (categoryIds[0] == MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {
			return;
		}

		BooleanQuery categoriesQuery = BooleanQueryFactoryUtil.create(
			searchContext);

		for (long categoryId : categoryIds) {
			try {
				MBCategoryServiceUtil.getCategory(categoryId);
			}
			catch (Exception e) {
				continue;
			}

			categoriesQuery.addTerm(Field.CATEGORY_ID, categoryId);
		}

		contextQuery.add(categoriesQuery, BooleanClauseOccur.MUST);
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		SearchContext searchContext = new SearchContext();

		searchContext.setSearchEngineId(getSearchEngineId());

		if (obj instanceof MBCategory) {
			MBCategory category = (MBCategory)obj;

			BooleanQuery booleanQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			booleanQuery.addRequiredTerm(Field.PORTLET_ID, PORTLET_ID);

			booleanQuery.addRequiredTerm(
				"categoryId", category.getCategoryId());

			Hits hits = SearchEngineUtil.search(
				getSearchEngineId(), category.getCompanyId(), booleanQuery,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (int i = 0; i < hits.getLength(); i++) {
				Document document = hits.doc(i);

				SearchEngineUtil.deleteDocument(
					getSearchEngineId(), category.getCompanyId(),
					document.get(Field.UID));
			}
		}
		else if (obj instanceof MBMessage) {
			MBMessage message = (MBMessage)obj;

			Document document = new DocumentImpl();

			document.addUID(PORTLET_ID, message.getMessageId());

			SearchEngineUtil.deleteDocument(
				getSearchEngineId(), message.getCompanyId(),
				document.get(Field.UID));
		}
		else if (obj instanceof MBThread) {
			MBThread thread = (MBThread)obj;

			MBMessage message = MBMessageLocalServiceUtil.getMessage(
				thread.getRootMessageId());

			BooleanQuery booleanQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			booleanQuery.addRequiredTerm(Field.PORTLET_ID, PORTLET_ID);

			booleanQuery.addRequiredTerm("threadId", thread.getThreadId());

			Hits hits = SearchEngineUtil.search(
				getSearchEngineId(), message.getCompanyId(), booleanQuery,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			for (int i = 0; i < hits.getLength(); i++) {
				Document document = hits.doc(i);

				SearchEngineUtil.deleteDocument(
					getSearchEngineId(), message.getCompanyId(),
					document.get(Field.UID));
			}
		}
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		MBMessage message = (MBMessage)obj;

		Document document = getBaseModelDocument(PORTLET_ID, message);

		document.addKeyword(Field.CATEGORY_ID, message.getCategoryId());
		document.addText(Field.CONTENT, processContent(message));
		document.addKeyword(
			Field.ROOT_ENTRY_CLASS_PK, message.getRootMessageId());
		document.addText(Field.TITLE, message.getSubject());

		if (message.isAnonymous()) {
			document.remove(Field.USER_NAME);
		}

		try {
			MBDiscussionLocalServiceUtil.getThreadDiscussion(
				message.getThreadId());

			document.addKeyword("discussion", true);
		}
		catch (NoSuchDiscussionException nsde) {
			document.addKeyword("discussion", false);
		}

		document.addKeyword("threadId", message.getThreadId());

		if (message.isDiscussion()) {
			Indexer indexer = IndexerRegistryUtil.getIndexer(
				message.getClassName());

			if (indexer != null) {
				indexer.addRelatedEntryFields(document, obj);
			}
		}

		if (!message.isInTrash() && message.isInTrashThread()) {
			addTrashFields(
				document, MBThread.class.getName(), message.getThreadId(), null,
				null, MBMessageAssetRendererFactory.TYPE);

			String className = MBThread.class.getName();
			long classPK = message.getThreadId();

			MBThread thread = message.getThread();

			if (thread.isInTrashContainer()) {
				MBCategory category = thread.getTrashContainer();

				className = MBCategory.class.getName();
				classPK = category.getCategoryId();
			}

			document.addKeyword(Field.ROOT_ENTRY_CLASS_NAME, className);
			document.addKeyword(Field.ROOT_ENTRY_CLASS_PK, classPK);

			document.addKeyword(
				Field.STATUS, WorkflowConstants.STATUS_IN_TRASH);
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		String messageId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter(
			"struts_action", "/message_boards/view_message");
		portletURL.setParameter("messageId", messageId);

		Summary summary = createSummary(document, Field.TITLE, Field.CONTENT);

		summary.setMaxContentLength(200);
		summary.setPortletURL(portletURL);

		return summary;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		MBMessage message = (MBMessage)obj;

		if (message.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			return;
		}

		if (message.isDiscussion() && message.isRoot()) {
			return;
		}

		Document document = getDocument(message);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), message.getCompanyId(), document);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		MBMessage message = MBMessageLocalServiceUtil.getMessage(classPK);

		doReindex(message);

		if (message.isRoot()) {
			List<MBMessage> messages =
				MBMessageLocalServiceUtil.getThreadMessages(
					message.getThreadId(), WorkflowConstants.STATUS_APPROVED);

			for (MBMessage curMessage : messages) {
				reindex(curMessage);
			}
		}
		else {
			reindex(message);
		}
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexCategories(companyId);
		reindexDiscussions(companyId);
		reindexRoot(companyId);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected String processContent(MBMessage message) {
		String content = message.getBody();

		try {
			if (message.isFormatBBCode()) {
				content = BBCodeTranslatorUtil.getHTML(content);
			}
		}
		catch (Exception e) {
			_log.error(
				"Could not parse message " + message.getMessageId() + ": " +
					e.getMessage());
		}

		content = HtmlUtil.extractText(content);

		return content;
	}

	protected void reindexCategories(final long companyId)
		throws PortalException, SystemException {

		ActionableDynamicQuery actionableDynamicQuery =
			new MBCategoryActionableDynamicQuery() {

			@Override
			protected void performAction(Object object)
				throws PortalException, SystemException {

				MBCategory category = (MBCategory)object;

				reindexMessages(
					companyId, category.getGroupId(), category.getCategoryId());
			}

		};

		actionableDynamicQuery.setCompanyId(companyId);

		actionableDynamicQuery.performActions();
	}

	protected void reindexDiscussions(final long companyId)
		throws PortalException, SystemException {

		ActionableDynamicQuery actionableDynamicQuery =
			new GroupActionableDynamicQuery() {

			@Override
			protected void performAction(Object object)
				throws PortalException, SystemException {

				Group group = (Group)object;

				reindexMessages(
					companyId, group.getGroupId(),
					MBCategoryConstants.DISCUSSION_CATEGORY_ID);
			}

		};

		actionableDynamicQuery.setCompanyId(companyId);

		actionableDynamicQuery.performActions();
	}

	protected void reindexMessages(
			long companyId, long groupId, final long categoryId)
		throws PortalException, SystemException {

		final Collection<Document> documents = new ArrayList<Document>();

		ActionableDynamicQuery actionableDynamicQuery =
			new MBMessageActionableDynamicQuery() {

			@Override
			protected void addCriteria(DynamicQuery dynamicQuery) {
				Property categoryIdProperty = PropertyFactoryUtil.forName(
					"categoryId");

				dynamicQuery.add(categoryIdProperty.eq(categoryId));

				Property statusProperty = PropertyFactoryUtil.forName("status");

				dynamicQuery.add(
					statusProperty.eq(WorkflowConstants.STATUS_APPROVED));
			}

			@Override
			protected void performAction(Object object) throws PortalException {
				MBMessage message = (MBMessage)object;

				Document document = getDocument(message);

				documents.add(document);
			}

		};

		actionableDynamicQuery.setGroupId(groupId);

		actionableDynamicQuery.performActions();

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

	protected void reindexRoot(final long companyId)
		throws PortalException, SystemException {

		ActionableDynamicQuery actionableDynamicQuery =
			new GroupActionableDynamicQuery() {

			@Override
			protected void performAction(Object object)
				throws PortalException, SystemException {

				Group group = (Group)object;

				reindexMessages(
					companyId, group.getGroupId(),
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID);
			}

		};

		actionableDynamicQuery.setCompanyId(companyId);

		actionableDynamicQuery.performActions();
	}

	private static Log _log = LogFactoryUtil.getLog(MBMessageIndexer.class);

}