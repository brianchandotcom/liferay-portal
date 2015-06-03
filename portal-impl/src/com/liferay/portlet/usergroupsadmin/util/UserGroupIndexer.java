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

package com.liferay.portlet.usergroupsadmin.util;

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.service.UserGroupLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Hugo Huijser
 */
@OSGiBeanProperties
public class UserGroupIndexer extends BaseIndexer {

	public static final String CLASS_NAME = UserGroup.class.getName();

	public UserGroupIndexer() {
		setCommitImmediately(true);
		setDefaultSelectedFieldNames(
			Field.COMPANY_ID, Field.UID, Field.USER_GROUP_ID);
		setIndexerEnabled(PropsValues.USER_GROUPS_INDEXER_ENABLED);
		setPermissionAware(true);
		setStagingAware(false);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addSearchTerm(searchQuery, searchContext, "description", false);
		addSearchTerm(searchQuery, searchContext, "name", false);

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params != null) {
			String expandoAttributes = (String)params.get("expandoAttributes");

			if (Validator.isNotNull(expandoAttributes)) {
				addSearchExpando(searchQuery, searchContext, expandoAttributes);
			}
		}
	}

	@Override
	protected void doDelete(Object obj) throws Exception {
		UserGroup userGroup = (UserGroup)obj;

		deleteDocument(userGroup.getCompanyId(), userGroup.getUserGroupId());
	}

	@Override
	protected Document doGetDocument(Object obj) throws Exception {
		UserGroup userGroup = (UserGroup)obj;

		Document document = getBaseModelDocument(CLASS_NAME, userGroup);

		document.addKeyword(Field.COMPANY_ID, userGroup.getCompanyId());
		document.addText(Field.DESCRIPTION, userGroup.getDescription());
		document.addText(Field.NAME, userGroup.getName());
		document.addKeyword(Field.USER_GROUP_ID, userGroup.getUserGroupId());

		return document;
	}

	@Override
	protected String doGetSortField(String orderByCol) {
		if (orderByCol.equals("description")) {
			return "description";
		}
		else if (orderByCol.equals("name")) {
			return "name";
		}
		else {
			return orderByCol;
		}
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		String title = document.get("name");

		String content = null;

		return new Summary(title, content);
	}

	@Override
	protected void doReindex(Object obj) throws Exception {
		if (obj instanceof Long) {
			long userGroupId = (Long)obj;

			UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(
				userGroupId);

			doReindex(userGroup);
		}
		else if (obj instanceof long[]) {
			long[] userGroupIds = (long[])obj;

			Map<Long, Collection<Document>> documentsMap = new HashMap<>();

			for (long userGroupId : userGroupIds) {
				UserGroup userGroup = UserGroupLocalServiceUtil.fetchUserGroup(
					userGroupId);

				if (userGroup == null) {
					continue;
				}

				Document document = getDocument(userGroup);

				long companyId = userGroup.getCompanyId();

				Collection<Document> documents = documentsMap.get(companyId);

				if (documents == null) {
					documents = new ArrayList<>();

					documentsMap.put(companyId, documents);
				}

				documents.add(document);
			}

			for (Map.Entry<Long, Collection<Document>> entry :
					documentsMap.entrySet()) {

				long companyId = entry.getKey();
				Collection<Document> documents = entry.getValue();

				SearchEngineUtil.updateDocuments(
					getSearchEngineId(), companyId, documents,
					isCommitImmediately());
			}
		}
		else if (obj instanceof UserGroup) {
			UserGroup userGroup = (UserGroup)obj;

			Document document = getDocument(userGroup);

			SearchEngineUtil.updateDocument(
				getSearchEngineId(), userGroup.getCompanyId(), document,
				isCommitImmediately());
		}
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		UserGroup userGroup = UserGroupLocalServiceUtil.getUserGroup(classPK);

		doReindex(userGroup);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexUserGroups(companyId);
	}

	protected void reindexUserGroups(long companyId) throws PortalException {
		final ActionableDynamicQuery actionableDynamicQuery =
			UserGroupLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setCompanyId(companyId);
		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod() {

				@Override
				public void performAction(Object object)
					throws PortalException {

					UserGroup userGroup = (UserGroup)object;

					Document document = getDocument(userGroup);

					actionableDynamicQuery.addDocument(document);
				}

			});
		actionableDynamicQuery.setSearchEngineId(getSearchEngineId());

		actionableDynamicQuery.performActions();
	}

}