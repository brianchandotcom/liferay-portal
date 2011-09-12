/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.BooleanQueryFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBCategoryServiceUtil;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;

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
public class MBIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {MBMessage.class.getName()};

	public static final String PORTLET_ID = PortletKeys.MESSAGE_BOARDS;

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, long entryClassPK,
			String actionId)
		throws Exception {

		return MBMessagePermission.contains(
			permissionChecker, entryClassPK, ActionKeys.VIEW);
	}

	@Override
	public boolean isFilterSearch() {
		return _FILTER_SEARCH;
	}

	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_ANY);

		if (status != WorkflowConstants.STATUS_ANY) {
			contextQuery.addRequiredTerm(Field.STATUS, status);
		}

		boolean discussion = GetterUtil.getBoolean(
			searchContext.getAttribute("discussion"), false);

		contextQuery.addRequiredTerm("discussion", discussion);

		long threadId = GetterUtil.getLong(
			(String)searchContext.getAttribute("threadId"));

		if (threadId > 0) {
			contextQuery.addRequiredTerm("threadId", threadId);
		}

		long[] categoryIds = searchContext.getCategoryIds();

		if ((categoryIds != null) && (categoryIds.length > 0)) {
			if (categoryIds[0] ==
					MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID) {

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
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		SearchContext searchContext = new SearchContext();

		searchContext.setSearchEngineId(SearchEngineUtil.SYSTEM_ENGINE_ID);

		if (obj instanceof MBCategory) {
			MBCategory category = (MBCategory)obj;

			BooleanQuery booleanQuery = BooleanQueryFactoryUtil.create(
				searchContext);

			booleanQuery.addRequiredTerm(Field.PORTLET_ID, PORTLET_ID);

			booleanQuery.addRequiredTerm(
				"categoryId", category.getCategoryId());

			Hits hits = SearchEngineUtil.search(
				category.getCompanyId(), booleanQuery, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

			for (int i = 0; i < hits.getLength(); i++) {
				Document document = hits.doc(i);

				SearchEngineUtil.deleteDocument(
					category.getCompanyId(), document.get(Field.UID));
			}
		}
		else if (obj instanceof MBMessage) {
			MBMessage message = (MBMessage)obj;

			Document document = new DocumentImpl();

			document.addUID(PORTLET_ID, message.getMessageId());

			SearchEngineUtil.deleteDocument(
				message.getCompanyId(), document.get(Field.UID));
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
				message.getCompanyId(), booleanQuery, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

			for (int i = 0; i < hits.getLength(); i++) {
				Document document = hits.doc(i);

				SearchEngineUtil.deleteDocument(
					message.getCompanyId(), document.get(Field.UID));
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

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		String title = document.get(Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.CONTENT), 200);
		}

		String messageId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter(
			"struts_action", "/message_boards/view_message");
		portletURL.setParameter("messageId", messageId);

		return new Summary(title, content, portletURL);
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		MBMessage message = (MBMessage)obj;

		if (message.isDiscussion() ||
			(message.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

			return;
		}

		Document document = getDocument(message);

		SearchEngineUtil.updateDocument(message.getCompanyId(), document);
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
		reindexRoot(companyId);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected String processContent(MBMessage message) {
		String content = message.getBody();

		try {
			content = BBCodeUtil.getHTML(content);
		}
		catch (Exception e) {
			_log.error(
				"Could not parse message " + message.getMessageId() + ": " +
					e.getMessage());
		}

		content = HtmlUtil.extractText(content);

		return content;
	}

	protected void reindexCategories(long companyId) throws Exception {
		int categoryCount =
			MBCategoryLocalServiceUtil.getCompanyCategoriesCount(companyId);

		int categoryPages = categoryCount / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= categoryPages; i++) {
			int categoryStart = (i * Indexer.DEFAULT_INTERVAL);
			int categoryEnd = categoryStart + Indexer.DEFAULT_INTERVAL;

			reindexCategories(companyId, categoryStart, categoryEnd);
		}
	}

	protected void reindexCategories(
			long companyId, int categoryStart, int categoryEnd)
		throws Exception {

		List<MBCategory> categories =
			MBCategoryLocalServiceUtil.getCompanyCategories(
				companyId, categoryStart, categoryEnd);

		for (MBCategory category : categories) {
			long groupId = category.getGroupId();
			long categoryId = category.getCategoryId();

			int messageCount =
				MBMessageLocalServiceUtil.getCategoryMessagesCount(
					groupId, categoryId, WorkflowConstants.STATUS_APPROVED);

			int messagePages = messageCount / Indexer.DEFAULT_INTERVAL;

			for (int i = 0; i <= messagePages; i++) {
				int messageStart = (i * Indexer.DEFAULT_INTERVAL);
				int messageEnd = messageStart + Indexer.DEFAULT_INTERVAL;

				reindexMessages(
					companyId, groupId, categoryId, messageStart, messageEnd);
			}
		}
	}

	protected void reindexMessages(
			long companyId, long groupId, long categoryId, int messageStart,
			int messageEnd)
		throws Exception {

		List<MBMessage> messages =
			MBMessageLocalServiceUtil.getCategoryMessages(
				groupId, categoryId, WorkflowConstants.STATUS_APPROVED,
				messageStart, messageEnd);

		if (messages.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>();

		for (MBMessage message : messages) {
			Document document = getDocument(message);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

	protected void reindexRoot(long companyId) throws Exception {
		int groupCount = GroupLocalServiceUtil.getCompanyGroupsCount(companyId);

		int groupPages = groupCount / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= groupPages; i++) {
			int groupStart = (i * Indexer.DEFAULT_INTERVAL);
			int groupEnd = groupStart + Indexer.DEFAULT_INTERVAL;

			reindexRoot(companyId, groupStart, groupEnd);
		}
	}

	protected void reindexRoot(long companyId, int groupStart, int groupEnd)
		throws Exception {

		List<Group> groups = GroupLocalServiceUtil.getCompanyGroups(
			companyId, groupStart, groupEnd);

		for (Group group : groups) {
			long groupId = group.getGroupId();
			long categoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;

			int entryCount = MBMessageLocalServiceUtil.getCategoryMessagesCount(
				groupId, categoryId, WorkflowConstants.STATUS_APPROVED);

			int entryPages = entryCount / Indexer.DEFAULT_INTERVAL;

			for (int i = 0; i <= entryPages; i++) {
				int entryStart = (i * Indexer.DEFAULT_INTERVAL);
				int entryEnd = entryStart + Indexer.DEFAULT_INTERVAL;

				reindexMessages(
					companyId, groupId, categoryId, entryStart, entryEnd);
			}
		}
	}

	private static final boolean _FILTER_SEARCH = true;

	private static Log _log = LogFactoryUtil.getLog(MBIndexer.class);

}