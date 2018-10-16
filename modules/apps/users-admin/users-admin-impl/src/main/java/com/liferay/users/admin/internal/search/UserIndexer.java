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

package com.liferay.users.admin.internal.search;

import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.exception.NoSuchContactException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Contact;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseIndexer;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.IndexWriterHelper;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.impl.ContactImpl;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 * @author Zsigmond Rab
 * @author Hugo Huijser
 */
@Component(immediate = true, service = Indexer.class)
public class UserIndexer extends BaseIndexer<User> {

	public static final String CLASS_NAME = User.class.getName();

	public static long getUserId(Document document) {
		return GetterUtil.getLong(document.get(Field.USER_ID));
	}

	public UserIndexer() {
		setDefaultSelectedFieldNames(
			Field.ASSET_TAG_NAMES, Field.COMPANY_ID, Field.ENTRY_CLASS_NAME,
			Field.ENTRY_CLASS_PK, Field.GROUP_ID, Field.MODIFIED_DATE,
			Field.SCOPE_GROUP_ID, Field.UID, Field.USER_ID);
		setPermissionAware(true);
		setStagingAware(false);
	}

	@Override
	public String getClassName() {
		return CLASS_NAME;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		int status = GetterUtil.getInteger(
			searchContext.getAttribute(Field.STATUS),
			WorkflowConstants.STATUS_APPROVED);

		if (status != WorkflowConstants.STATUS_ANY) {
			contextBooleanFilter.addRequiredTerm(Field.STATUS, status);
		}

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params == null) {
			return;
		}

		for (Map.Entry<String, Object> entry : params.entrySet()) {
			Object value = entry.getValue();

			if (value == null) {
				continue;
			}

			Class<?> clazz = value.getClass();

			if (clazz.isArray()) {
				Object[] values = (Object[])value;

				if (values.length == 0) {
					continue;
				}
			}

			addContextQueryParams(
				contextBooleanFilter, searchContext, entry.getKey(), value);
		}
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		addHighlightFieldNames(searchContext);

		addSearchTerm(searchQuery, searchContext, "city", false);
		addSearchTerm(searchQuery, searchContext, "country", false);
		addSearchTerm(searchQuery, searchContext, "emailAddress", false);
		addSearchTerm(searchQuery, searchContext, "firstName", false);
		addSearchTerm(searchQuery, searchContext, "fullName", false);
		addSearchTerm(searchQuery, searchContext, "jobTitle", false);
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

	protected void addContextQueryParams(
			BooleanFilter contextFilter, SearchContext searchContext,
			String key, Object value)
		throws Exception {

		if (key.equals("usersGroups")) {
			if (value instanceof Long[]) {
				Long[] values = (Long[])value;

				if (ArrayUtil.isEmpty(values)) {
					return;
				}

				TermsFilter userGroupsTermsFilter = new TermsFilter("groupIds");

				userGroupsTermsFilter.addValues(
					ArrayUtil.toStringArray(values));

				contextFilter.add(
					userGroupsTermsFilter, BooleanClauseOccur.MUST);
			}
			else {
				contextFilter.addRequiredTerm(
					"groupIds", String.valueOf(value));
			}
		}
		else if (key.equals("usersOrgs")) {
			if (value instanceof Long[]) {
				Long[] values = (Long[])value;

				if (ArrayUtil.isEmpty(values)) {
					return;
				}

				TermsFilter organizationsTermsFilter = new TermsFilter(
					"organizationIds");
				TermsFilter ancestorOrgsTermsFilter = new TermsFilter(
					"ancestorOrganizationIds");

				String[] organizationIdsStrings = ArrayUtil.toStringArray(
					values);

				ancestorOrgsTermsFilter.addValues(organizationIdsStrings);

				organizationsTermsFilter.addValues(organizationIdsStrings);

				BooleanFilter userOrgsBooleanFilter = new BooleanFilter();

				userOrgsBooleanFilter.add(ancestorOrgsTermsFilter);
				userOrgsBooleanFilter.add(organizationsTermsFilter);

				contextFilter.add(
					userOrgsBooleanFilter, BooleanClauseOccur.MUST);
			}
			else {
				contextFilter.addRequiredTerm(
					"organizationIds", String.valueOf(value));
			}
		}
		else if (key.equals("usersOrgsCount")) {
			contextFilter.addRequiredTerm(
				"organizationCount", String.valueOf(value));
		}
		else if (key.equals("usersRoles")) {
			contextFilter.addRequiredTerm("roleIds", String.valueOf(value));
		}
		else if (key.equals("usersTeams")) {
			contextFilter.addRequiredTerm("teamIds", String.valueOf(value));
		}
		else if (key.equals("usersUserGroups")) {
			contextFilter.addRequiredTerm(
				"userGroupIds", String.valueOf(value));
		}
	}

	protected void addHighlightFieldNames(SearchContext searchContext) {
		QueryConfig queryConfig = searchContext.getQueryConfig();

		if (!queryConfig.isHighlightEnabled()) {
			return;
		}

		queryConfig.addHighlightFieldNames("fullName");
	}

	@Override
	protected void doDelete(User user) throws Exception {
		deleteDocument(user.getCompanyId(), user.getUserId());

		Indexer<Contact> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			Contact.class);

		Contact contact = new ContactImpl();

		contact.setContactId(user.getContactId());
		contact.setCompanyId(user.getCompanyId());

		indexer.delete(contact);
	}

	@Override
	protected Document doGetDocument(User user) throws Exception {
		Document document = getBaseModelDocument(CLASS_NAME, user);

		long[] organizationIds = user.getOrganizationIds();

		document.addKeyword(Field.COMPANY_ID, user.getCompanyId());
		document.addKeyword(
			Field.GROUP_ID, getActiveTransitiveGroupIds(user.getUserId()));
		document.addDate(Field.MODIFIED_DATE, user.getModifiedDate());
		document.addKeyword(
			Field.SCOPE_GROUP_ID,
			getActiveTransitiveGroupIds(user.getUserId()));
		document.addKeyword(Field.STATUS, user.getStatus());
		document.addKeyword(Field.USER_ID, user.getUserId());
		document.addKeyword(Field.USER_NAME, user.getFullName(), true);
		document.addKeyword(
			"ancestorOrganizationIds",
			getAncestorOrganizationIds(user.getOrganizationIds()));
		document.addText("emailAddress", user.getEmailAddress());
		document.addText("firstName", user.getFirstName());
		document.addText("fullName", user.getFullName());
		document.addKeyword("groupIds", user.getGroupIds());
		document.addText("jobTitle", user.getJobTitle());
		document.addText("lastName", user.getLastName());
		document.addText("middleName", user.getMiddleName());
		document.addKeyword("organizationIds", organizationIds);
		document.addKeyword(
			"organizationCount", String.valueOf(organizationIds.length));
		document.addKeyword("roleIds", user.getRoleIds());
		document.addText("screenName", user.getScreenName());
		document.addKeyword("teamIds", user.getTeamIds());
		document.addKeyword("userGroupIds", user.getUserGroupIds());

		populateAddresses(document, user.getAddresses(), 0, 0);

		return document;
	}

	@Override
	protected Summary doGetSummary(
		Document document, Locale locale, String snippet,
		PortletRequest portletRequest, PortletResponse portletResponse) {

		return createSummary(document, "fullName", null);
	}

	@Override
	protected void doReindex(String className, long classPK) throws Exception {
		User user = userLocalService.getUserById(classPK);

		doReindex(user);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		reindexUsers(companyId);
	}

	@Override
	protected void doReindex(User user) throws Exception {
		if (user.isDefaultUser()) {
			return;
		}

		Document document = getDocument(user);

		indexWriterHelper.updateDocument(
			getSearchEngineId(), user.getCompanyId(), document,
			isCommitImmediately());

		Indexer<Contact> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			Contact.class);

		try {
			indexer.reindex(user.getContact());
		}
		catch (NoSuchContactException nsce) {

			// This is a temporary workaround for LPS-46825

			if (_log.isDebugEnabled()) {
				_log.debug(nsce, nsce);
			}
		}
	}

	protected long[] getActiveGroupIds(long userId) {
		List<Long> groupIds = groupLocalService.getActiveGroupIds(userId);

		return ArrayUtil.toArray(groupIds.toArray(new Long[groupIds.size()]));
	}

	protected long[] getActiveTransitiveGroupIds(long userId)
		throws PortalException {

		List<Group> groups = groupLocalService.getUserGroups(userId, true);

		Stream<Group> stream = groups.stream();

		return stream.filter(
			Group::isSite
		).filter(
			Group::isActive
		).mapToLong(
			Group::getGroupId
		).toArray();
	}

	protected long[] getAncestorOrganizationIds(long[] organizationIds)
		throws Exception {

		Set<Long> ancestorOrganizationIds = new HashSet<>();

		for (long organizationId : organizationIds) {
			Organization organization =
				organizationLocalService.getOrganization(organizationId);

			for (long ancestorOrganizationId :
					organization.getAncestorOrganizationIds()) {

				ancestorOrganizationIds.add(ancestorOrganizationId);
			}
		}

		return ArrayUtil.toLongArray(ancestorOrganizationIds);
	}

	protected void reindexUsers(long companyId) throws PortalException {
		final IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			userLocalService.getIndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setCompanyId(companyId);
		indexableActionableDynamicQuery.setIndexWriterHelper(indexWriterHelper);
		indexableActionableDynamicQuery.setPerformActionMethod(
			(User user) -> {
				if (!user.isDefaultUser()) {
					try {
						Document document = getDocument(user);

						indexableActionableDynamicQuery.addDocuments(document);
					}
					catch (PortalException pe) {
						if (_log.isWarnEnabled()) {
							_log.warn(
								"Unable to index user " + user.getUserId(), pe);
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
	protected IndexWriterHelper indexWriterHelper;

	@Reference
	protected OrganizationLocalService organizationLocalService;

	@Reference
	protected UserLocalService userLocalService;

	private static final Log _log = LogFactoryUtil.getLog(UserIndexer.class);

}