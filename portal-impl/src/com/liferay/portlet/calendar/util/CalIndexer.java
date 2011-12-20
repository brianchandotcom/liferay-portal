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

package com.liferay.portlet.calendar.util;

import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.calendar.service.CalEventLocalServiceUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Brett Swaim
 */
public class CalIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {CalEvent.class.getName()};

	public static final String PORTLET_ID = PortletKeys.CALENDAR;

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public boolean isPermissionAware() {
		return _PERMISSION_AWARE;
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

		String title = document.get(Field.TITLE);

		String content = snippet;

		if (Validator.isNull(snippet)) {
			content = StringUtil.shorten(document.get(Field.DESCRIPTION), 200);
		}

		String eventId = document.get(Field.ENTRY_CLASS_PK);

		portletURL.setParameter("struts_action", "/calendar/view_event");
		portletURL.setParameter("eventId", eventId);

		return new Summary(title, content, portletURL);
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		CalEvent event = (CalEvent)obj;

		Document document = getDocument(event);

		SearchEngineUtil.updateDocument(event.getCompanyId(), document);
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

	protected void reindexEvents(long companyId) throws Exception {
		int count = CalEventLocalServiceUtil.getCompanyEventsCount(companyId);

		int pages = count / Indexer.DEFAULT_INTERVAL;

		for (int i = 0; i <= pages; i++) {
			int start = (i * Indexer.DEFAULT_INTERVAL);
			int end = start + Indexer.DEFAULT_INTERVAL;

			reindexEvents(companyId, start, end);
		}
	}

	protected void reindexEvents(long companyId, int start, int end)
		throws Exception {

		List<CalEvent> events = CalEventLocalServiceUtil.getCompanyEvents(
			companyId, start, end);

		if (events.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>();

		for (CalEvent event : events) {
			Document document = getDocument(event);

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(companyId, documents);
	}

	private static final boolean _PERMISSION_AWARE = true;

}