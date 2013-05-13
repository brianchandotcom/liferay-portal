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

package com.liferay.portlet.calendar.util;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;
import com.liferay.portlet.calendar.service.persistence.CalEventActionableDynamicQuery;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Brett Swaim
 */
public class CalIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {CalEvent.class.getName()};

	public static final String PORTLET_ID = PortletKeys.CALENDAR;

	public CalIndexer() {
		setPermissionAware(true);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		CalEvent event = (CalEvent)obj;

		deleteDocument(event.getCompanyId(), event.getEventId());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		CalEvent event = (CalEvent)obj;

		Document document = getBaseModelDocument(PORTLET_ID, event);

		document.addText(
			Field.DESCRIPTION, HtmlUtil.extractText(event.getDescription()));
		document.addText(Field.TITLE, event.getTitle());
		document.addKeyword(Field.TYPE, event.getType());

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		String eventId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/calendar/view_event");
		portletURL.setParameter("eventId", eventId);

		Summary summary = createSummary(
			document, Field.TITLE, Field.DESCRIPTION);

		summary.setMaxContentLength(200);
		summary.setPortletURL(portletURL);

		return summary;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		CalEvent event = (CalEvent)obj;

		Document document = getDocument(event);

		SearchEngineUtil.updateDocument(
			getSearchEngineId(), event.getCompanyId(), document);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		CalEvent event = CalEventLocalServiceUtil.getEvent(classPK);

		doReindex(event);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexEvents(companyId);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexEvents(long companyId)
		throws PortalException, SystemException {

		final Collection<Document> documents = new ArrayList<Document>();

		ActionableDynamicQuery actionableDynamicQuery =
			new CalEventActionableDynamicQuery() {

			@Override
			protected void performAction(Object object) throws PortalException {
				CalEvent event = (CalEvent)object;

				Document document = getDocument(event);

				documents.add(document);
			}

		};

		actionableDynamicQuery.setCompanyId(companyId);

		actionableDynamicQuery.performActions();

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

}