/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.users.admin.internal.search.spi.model.query.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.UserConstants;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
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
			Field.TYPE, String.valueOf(UserConstants.TYPE_GUEST),
			BooleanClauseOccur.MUST_NOT);

		_filterByEmailAddress(contextBooleanFilter, searchContext);
		_filterByType(contextBooleanFilter, searchContext);

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

			if (clazz.isArray() && (value instanceof Object[])) {
				Object[] values = (Object[])value;

				if (values.length == 0) {
					continue;
				}
			}

			_addContextQueryParams(contextBooleanFilter, entry.getKey(), value);
		}
	}

	private void _addContextQueryParams(
		BooleanFilter contextFilter, String key, Object value) {

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

				String[] organizationIdsStrings = ArrayUtil.toStringArray(
					values);

				BooleanFilter userOrgsBooleanFilter = new BooleanFilter();

				userOrgsBooleanFilter.add(
					_createTermsFilter(
						"ancestorOrganizationIds", organizationIdsStrings));
				userOrgsBooleanFilter.add(
					_createTermsFilter(
						"organizationIds", organizationIdsStrings));

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
			if (value instanceof Long[]) {
				Long[] values = (Long[])value;

				if (ArrayUtil.isEmpty(values)) {
					return;
				}

				contextFilter.add(
					_createTermsFilter(
						"roleIds", ArrayUtil.toStringArray(values)),
					BooleanClauseOccur.MUST);
			}
			else {
				contextFilter.addRequiredTerm("roleIds", String.valueOf(value));
			}
		}
		else if (key.equals("usersTeams")) {
			contextFilter.addRequiredTerm("teamIds", String.valueOf(value));
		}
		else if (key.equals("usersUserGroups")) {
			contextFilter.addRequiredTerm(
				"userGroupIds", String.valueOf(value));
		}
	}

	private TermsFilter _createTermsFilter(String filterName, String[] values) {
		TermsFilter termsFilter = new TermsFilter(filterName);

		termsFilter.addValues(values);

		return termsFilter;
	}

	private void _filterByEmailAddress(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		String emailAddress = (String)searchContext.getAttribute(
			"emailAddress");

		if (Validator.isNull(emailAddress)) {
			return;
		}

		BooleanFilter emailAddressBooleanFilter = new BooleanFilter();

		emailAddressBooleanFilter.add(
			new QueryFilter(
				new WildcardQueryImpl(
					"emailAddress", emailAddress + StringPool.STAR)));
		emailAddressBooleanFilter.add(
			new QueryFilter(
				new WildcardQueryImpl(
					"emailAddressDomain", emailAddress + StringPool.STAR)));

		booleanFilter.add(emailAddressBooleanFilter, BooleanClauseOccur.MUST);
	}

	private void _filterByType(
		BooleanFilter contextBooleanFilter, SearchContext searchContext) {

		long[] types = GetterUtil.getLongValues(
			searchContext.getAttribute("types"),
			new long[] {UserConstants.TYPE_REGULAR});

		if (ArrayUtil.isNotEmpty(types)) {
			TermsFilter termsFilter = new TermsFilter(Field.TYPE);

			termsFilter.addValues(ArrayUtil.toStringArray(types));

			contextBooleanFilter.add(termsFilter, BooleanClauseOccur.MUST);
		}
	}

}