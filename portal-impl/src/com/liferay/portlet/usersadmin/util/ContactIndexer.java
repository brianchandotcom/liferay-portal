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

package com.liferay.portlet.usersadmin.util;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionList;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ContactLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortletKeys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Raymond Augé
 * @author Zsigmond Rab
 * @author Hugo Huijser
 */
public class ContactIndexer extends BaseIndexer {

	public static final String[] CLASS_NAMES = {Contact.class.getName()};

	public static final String PORTLET_ID = PortletKeys.USERS_ADMIN;

	public ContactIndexer() {
		setStagingAware(false);
	}

	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	public String getPortletId() {
		return PORTLET_ID;
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, "city", false);
		addSearchTerm(searchQuery, searchContext, "country", false);
		addSearchTerm(searchQuery, searchContext, "emailAddress", false);
		addSearchTerm(searchQuery, searchContext, "firstName", false);
		addSearchTerm(searchQuery, searchContext, "fullName", false);
		addSearchTerm(searchQuery, searchContext, "lastName", false);
		addSearchTerm(searchQuery, searchContext, "middleName", false);
		addSearchTerm(searchQuery, searchContext, "region", false);
		addSearchTerm(searchQuery, searchContext, "screenName", false);
		addSearchTerm(searchQuery, searchContext, "street", false);
		addSearchTerm(searchQuery, searchContext, "zip", false);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params != null) {
			String expandoAttributes = (String)params.get("expandoAttributes");

			if (Validator.isNotNull(expandoAttributes)) {
				addSearchExpando(searchQuery, searchContext, expandoAttributes);
			}
		}
	}

	protected void addReindexCriteria(
		DynamicQuery dynamicQuery, long companyId) {

		Property property = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(property.eq(companyId));
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		Contact contact = (Contact)obj;

		deleteDocument(contact.getCompanyId(), contact.getContactId());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		Contact contact = (Contact)obj;

		if (contact.isUser()) {
			User user = UserLocalServiceUtil.getUserByContactId(
				contact.getContactId());

			if (user.isDefaultUser() ||
				(user.getStatus() != WorkflowConstants.STATUS_APPROVED)) {

				return null;
			}
		}

		Document document = getBaseModelDocument(PORTLET_ID, contact);

		document.addKeyword(Field.COMPANY_ID, contact.getCompanyId());
		document.addDate(Field.MODIFIED_DATE, contact.getModifiedDate());
		document.addKeyword(Field.USER_ID, contact.getUserId());
		document.addKeyword(Field.USER_NAME, contact.getFullName());

		document.addText("emailAddress", contact.getEmailAddress());
		document.addText("firstName", contact.getFirstName());
		document.addText("fullName", contact.getFullName());
		document.addText("jobTitle", contact.getJobTitle());
		document.addText("lastName", contact.getLastName());
		document.addText("middleName", contact.getMiddleName());

		return document;
	}

	@Override
	protected String doGetSortField(String orderByCol) {
		if (orderByCol.equals("email-address")) {
			return "emailAddress";
		}
		else if (orderByCol.equals("first-name")) {
			return "firstName";
		}
		else if (orderByCol.equals("job-title")) {
			return "jobTitle";
		}
		else if (orderByCol.equals("last-name")) {
			return "lastName";
		}
		else {
			return orderByCol;
		}
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		return null;
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		Contact contact = (Contact)obj;

		Document document = getDocument(contact);

		if (document != null) {
			SearchEngineUtil.updateDocument(
				getSearchEngineId(), contact.getCompanyId(), document);
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		Contact contact = ContactLocalServiceUtil.getContact(classPK);

		doReindex(contact);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexContacts(companyId);
	}

	@Override
	protected String getPortletId(SearchContext searchContext) {
		return PORTLET_ID;
	}

	protected void reindexContacts(long companyId) throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Contact.class, PortalClassLoaderUtil.getClassLoader());

		Projection minContactIdProjection = ProjectionFactoryUtil.min(
			"contactId");
		Projection maxContactIdProjection = ProjectionFactoryUtil.max(
			"contactId");

		ProjectionList projectionList = ProjectionFactoryUtil.projectionList();

		projectionList.add(minContactIdProjection);
		projectionList.add(maxContactIdProjection);

		dynamicQuery.setProjection(projectionList);

		addReindexCriteria(dynamicQuery, companyId);

		List<Object[]> results = ContactLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		Object[] minAndMaxContactIds = results.get(0);

		if ((minAndMaxContactIds[0] == null) ||
			(minAndMaxContactIds[1] == null)) {

			return;
		}

		long minContactId = (Long)minAndMaxContactIds[0];
		long maxContactId = (Long)minAndMaxContactIds[1];

		long startContactId = minContactId;
		long endContactId = startContactId + DEFAULT_INTERVAL;

		while (startContactId <= maxContactId) {
			reindexContacts(companyId, startContactId, endContactId);

			startContactId = endContactId;
			endContactId += DEFAULT_INTERVAL;
		}
	}

	protected void reindexContacts(
			long companyId, long startContactId, long endContactId)
		throws Exception {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			Contact.class, PortalClassLoaderUtil.getClassLoader());

		Property property = PropertyFactoryUtil.forName("contactId");

		dynamicQuery.add(property.ge(startContactId));
		dynamicQuery.add(property.lt(endContactId));

		addReindexCriteria(dynamicQuery, companyId);

		List<Contact> contacts = ContactLocalServiceUtil.dynamicQuery(
			dynamicQuery);

		if (contacts.isEmpty()) {
			return;
		}

		Collection<Document> documents = new ArrayList<Document>(
			contacts.size());

		for (Contact contact : contacts) {
			Document document = getDocument(contact);

			if (document == null) {
				continue;
			}

			documents.add(document);
		}

		SearchEngineUtil.updateDocuments(
			getSearchEngineId(), companyId, documents);
	}

}