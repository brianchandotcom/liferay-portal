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

package com.liferay.users.admin.internal.search.spi.model.result.contributor;

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import java.util.LinkedHashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Luan Maoski
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.kernel.model.User",
	service = ModelPreFilterContributor.class
)
public class UserModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	@SuppressWarnings("unchecked")
	public void contribute(
		BooleanFilter contextBooleanFilter,
		ModelSearchSettings modelSearchSettings, SearchContext searchContext) {

		contextBooleanFilter.addTerm(
			"defaultUser", Boolean.TRUE.toString(),
			BooleanClauseOccur.MUST_NOT);

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

	protected void addContextQueryParams(
		BooleanFilter contextFilter, SearchContext searchContext, String key,
		Object value) {

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

}