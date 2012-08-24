/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
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
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
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

	public MBIndexer() {
		setFilterSearch(true);
		setPermissionAware(true);
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

	protected void addReindexCriteria(
		DynamicQuery dynamicQuery, long companyId) {

		Property property = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(property.eq(companyId));
	}

	protected void addReindexCriteria(
		DynamicQuery dynamicQuery, long groupId, long categoryId) {

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(groupIdProperty.eq(groupId));

		Property categoryIdProperty = PropertyFactoryUtil.forName("categoryId");

		dynamicQuery.add(categoryIdProperty.eq(categoryId));

		Property statusProperty = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(statusProperty.eq(WorkflowConstants.STATUS_APPROVED));
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

		if (message.isDiscussion() ||
			(message.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

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
		reindexRoot(companyId);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected String processContent(MBMessage message) {
		String content = message.getBody();

		try {
			content = BBCodeTranslatorUtil.getHTML(content);
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
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MBCategory.class, PACLClassLoaderUtil.getPortalClassLoader());

		Projection minCategoryIdProjection = ProjectionFactoryUtil.min(
			"categoryId");
		Projection maxCategoryIdProjection = ProjectionFactoryUtil.max(
			"categoryId");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minCategoryIdProjection);
		projectionList.add(maxCategoryIdProjection);

		dynamicQuery.setProjection(projectionList);

		addReindexCriteria(dynamicQuery, companyId);

		List<Object[]> results = MBCategoryLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Object[] minAndMaxCategoryIds = results.get(0);

		if ((minAndMaxCategoryIds[0] == null) ||
			(minAndMaxCategoryIds[1] == null)) {

			return;
		}

		long minCategoryId = (Long)minAndMaxCategoryIds[0];
		long maxCategoryId = (Long)minAndMaxCategoryIds[1];

		long startCategoryId = minCategoryId;
		long endCategoryId = startCategoryId + DEFAULT_INTERVAL;

		while (startCategoryId <= maxCategoryId) {
			reindexCategories(companyId, startCategoryId, endCategoryId);

			startCategoryId = endCategoryId;
			endCategoryId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexCategories(
			long companyId, long startCategoryId, long endCategoryId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MBCategory.class, PACLClassLoaderUtil.getPortalClassLoader());

		Property property = PropertyFactoryUtil.forName("categoryId");

		dynamicQuery.add(property.ge(startCategoryId));
		dynamicQuery.add(property.lt(endCategoryId));

		addReindexCriteria(dynamicQuery, companyId);

		List<MBCategory> categories = MBCategoryLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		for (MBCategory category : categories) {
			long groupId = category.getGroupId();
			long categoryId = category.getCategoryId();

			reindexMessages(companyId, groupId, categoryId);
		}
	}

	protected void reindexMessages(
			long companyId, long groupId, long categoryId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MBMessage.class, PACLClassLoaderUtil.getPortalClassLoader());

		Projection minMessageIdProjection = ProjectionFactoryUtil.min(
			"messageId");
		Projection maxMessageIdProjection = ProjectionFactoryUtil.max(
			"messageId");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minMessageIdProjection);
		projectionList.add(maxMessageIdProjection);

		dynamicQuery.setProjection(projectionList);

		addReindexCriteria(dynamicQuery, groupId, categoryId);

		List<Object[]> results = MBMessageLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Object[] minAndMaxMessageIds = results.get(0);

		if ((minAndMaxMessageIds[0] == null) ||
			(minAndMaxMessageIds[1] == null)) {

			return;
		}

		long minMessageId = (Long)minAndMaxMessageIds[0];
		long maxMessageId = (Long)minAndMaxMessageIds[1];

		long startMessageId = minMessageId;
		long endMessageId = startMessageId + DEFAULT_INTERVAL;

		while (startMessageId <= maxMessageId) {
			reindexMessages(
				companyId, groupId, categoryId, startMessageId, endMessageId);

			startMessageId = endMessageId;
			endMessageId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexMessages(
			long companyId, long groupId, long categoryId, long startMessageId,
			long endMessageId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MBMessage.class, PACLClassLoaderUtil.getPortalClassLoader());

		Property property = PropertyFactoryUtil.forName("messageId");

		dynamicQuery.add(property.ge(startMessageId));
		dynamicQuery.add(property.lt(endMessageId));

		addReindexCriteria(dynamicQuery, groupId, categoryId);

		List<MBMessage> messages = MBMessageLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		if (messages.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>(
			messages.size());

		for (MBMessage message : messages) {
			Document document = getDocument(message);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

	protected void reindexRoot(long companyId) throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Group.class, PACLClassLoaderUtil.getPortalClassLoader());

		Projection minGroupIdProjection = ProjectionFactoryUtil.min("groupId");
		Projection maxGroupIdProjection = ProjectionFactoryUtil.max("groupId");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minGroupIdProjection);
		projectionList.add(maxGroupIdProjection);

		dynamicQuery.setProjection(projectionList);

		addReindexCriteria(dynamicQuery, companyId);

		List<Object[]> results = GroupLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Object[] minAndMaxGroupIds = results.get(0);

		if ((minAndMaxGroupIds[0] == null) || (minAndMaxGroupIds[1] == null)) {
			return;
		}

		long minGroupId = (Long)minAndMaxGroupIds[0];
		long maxGroupId = (Long)minAndMaxGroupIds[1];

		long startGroupId = minGroupId;
		long endGroupId = startGroupId + DEFAULT_INTERVAL;

		while (startGroupId <= maxGroupId) {
			reindexRoot(companyId, startGroupId, endGroupId);

			startGroupId = endGroupId;
			endGroupId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexRoot(
			long companyId, long startGroupId, long endGroupId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Group.class, PACLClassLoaderUtil.getPortalClassLoader());

		Property property = PropertyFactoryUtil.forName("groupId");

		dynamicQuery.add(property.ge(startGroupId));
		dynamicQuery.add(property.lt(endGroupId));

		addReindexCriteria(dynamicQuery, companyId);

		List<Group> groups = GroupLocalServiceUtil.dynamicQuery(dynamicQuery);

		for (Group group : groups) {
			long groupId = group.getGroupId();
			long categoryId = MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID;

			reindexMessages(companyId, groupId, categoryId);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MBIndexer.class);

}