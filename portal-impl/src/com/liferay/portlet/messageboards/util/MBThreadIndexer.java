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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.parsers.bbcode.BBCodeTranslatorUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Zsolt Berentey
 */
public class MBThreadIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {MBThread.class.getName()};

	public static final String PORTLET_ID = PortletKeys.MESSAGE_BOARDS;

	public MBThreadIndexer() {
		setFilterSearch(false);
		setPermissionAware(false);
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

		MBThread thread = MBThreadLocalServiceUtil.getThread(entryClassPK);

		return MBMessagePermission.contains(
			permissionChecker, thread.getRootMessageId(), ActionKeys.VIEW);
	}

	protected void addReindexCriteria(
		DynamicQuery dynamicQuery, long companyId) {

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		Property statusProperty = PropertyFactoryUtil.forName("status");

		dynamicQuery.add(statusProperty.eq(WorkflowConstants.STATUS_IN_TRASH));
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		MBThread message = (MBThread)obj;

		Document document = new DocumentImpl();

		document.addUID(
			PORTLET_ID, CLASS_NAMES[0], String.valueOf(message.getThreadId()));

		SearchEngineUtil.deleteDocument(
			getSearchEngineId(), message.getCompanyId(),
			document.get(Field.UID));
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {

		MBThread thread = (MBThread)obj;

		MBMessage message = MBMessageLocalServiceUtil.getMBMessage(
			thread.getRootMessageId());

		Document document = getBaseModelDocument(PORTLET_ID, thread);

		document.addUID(
			PORTLET_ID, CLASS_NAMES[0], String.valueOf(thread.getThreadId()));
		document.addKeyword(Field.CATEGORY_ID, message.getCategoryId());
		document.addKeyword(Field.COMPANY_ID, thread.getCompanyId());
		document.addText(Field.CONTENT, processContent(message));
		document.addKeyword(
			Field.GROUP_ID, getParentGroupId(thread.getGroupId()));
		document.addKeyword(
			Field.ROOT_ENTRY_CLASS_PK, message.getRootMessageId());
		document.addKeyword(Field.SCOPE_GROUP_ID, thread.getGroupId());
		document.addKeyword(Field.STATUS, thread.getStatus());
		document.addText(Field.TITLE, message.getSubject());

		if (message.isAnonymous()) {
			document.remove(Field.USER_NAME);
		}

		return document;
	}

	@Override
	protected Summary doGetSummary(
			Document document, Locale locale, String snippet,
			PortletURL portletURL)
		throws Exception {

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
		MBThread thread = (MBThread)obj;

		Document document = getDocument(obj);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), thread.getCompanyId(), document);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		MBThread thread = MBThreadLocalServiceUtil.getThread(classPK);

		doReindex(thread);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexEntries(companyId);
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

	protected void reindexEntries(long companyId) throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MBThread.class, PACLClassLoaderUtil.getPortalClassLoader());

		Projection minEntryIdProjection = ProjectionFactoryUtil.min("threadId");
		Projection maxEntryIdProjection = ProjectionFactoryUtil.max("threadId");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minEntryIdProjection);
		projectionList.add(maxEntryIdProjection);

		dynamicQuery.setProjection(projectionList);

		addReindexCriteria(dynamicQuery, companyId);

		List<Object[]> results = MBThreadLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Object[] minAndMaxThreadIds = results.get(0);

		if ((minAndMaxThreadIds[0] == null) ||
			(minAndMaxThreadIds[1] == null)) {

			return;
		}

		long minThreadId = (Long)minAndMaxThreadIds[0];
		long maxThreadId = (Long)minAndMaxThreadIds[1];

		long startThreadId = minThreadId;
		long endThreadId = startThreadId + DEFAULT_INTERVAL;

		while (startThreadId <= maxThreadId) {
			reindexEntries(companyId, startThreadId, endThreadId);

			startThreadId = endThreadId;
			endThreadId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexEntries(
			long companyId, long startThreadId, long endThreadId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			MBThread.class, PACLClassLoaderUtil.getPortalClassLoader());

		Property property = PropertyFactoryUtil.forName("threadId");

		dynamicQuery.add(property.ge(startThreadId));
		dynamicQuery.add(property.lt(endThreadId));

		addReindexCriteria(dynamicQuery, companyId);

		List<MBThread> threads = MBThreadLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		if (threads.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>(
			threads.size());

		for (MBThread thread : threads) {
			Document document = getDocument(thread);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

	private static Log _log = LogFactoryUtil.getLog(MBThreadIndexer.class);

}