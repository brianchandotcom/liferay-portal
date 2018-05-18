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

package com.liferay.commerce.user.segment.internal.criterion;

import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionType;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterionConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.filter.TermsSetFilterBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"commerce.user.segment.criterion.type.key=" + CommerceUserSegmentCriterionConstants.TYPE_ROLE,
		"commerce.user.segment.criterion.type.order:Integer=40"
	},
	service = CommerceUserSegmentCriterionType.class
)
public class RoleCommerceUserSegmentCriterionTypeImpl
	extends BaseCommerceUserSegmentCriterionType {

	@Override
	public String getKey() {
		return CommerceUserSegmentCriterionConstants.TYPE_ROLE;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, "roles");
	}

	@Override
	public String getPreview(
		CommerceUserSegmentCriterion commerceUserSegmentCriterion, int length) {

		if (length <= 0) {
			return StringPool.BLANK;
		}

		List<String> roleNames = new ArrayList<>();

		String[] roleIds = StringUtil.split(
			commerceUserSegmentCriterion.getTypeSettings());

		for (String roleId : roleIds) {
			Role role = _roleLocalService.fetchRole(GetterUtil.getLong(roleId));

			if (role != null) {
				roleNames.add(role.getName());
			}
		}

		String preview = StringUtil.merge(
			roleNames, StringPool.COMMA_AND_SPACE);

		return StringUtil.shorten(preview, length, StringPool.TRIPLE_PERIOD);
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws PortalException {

		User user = userLocalService.getUser(searchContext.getUserId());

		long organizationId = GetterUtil.getLong(
			searchContext.getAttribute("organizationId"));

		long[] classPKs = getUserClassPKs(
			user, organizationId, searchContext.getGroupIds());

		TermsSetFilterBuilder termsSetFilterBuilder =
			filterBuilders.termsSetFilterBuilder();

		termsSetFilterBuilder.setFieldName(getIndexerFieldName());
		termsSetFilterBuilder.setMinimumShouldMatchField(
			getIndexerFieldName() + "_required_matches");

		List<String> values = new ArrayList<>(classPKs.length);

		for (long classPK : classPKs) {
			values.add(String.valueOf(classPK));
		}

		termsSetFilterBuilder.setValues(values);

		Filter existFilter = new TermFilter(
			getIndexerFieldName() + "_required_matches", "0");

		BooleanFilter fieldBooleanFilter = new BooleanFilter();

		fieldBooleanFilter.add(existFilter, BooleanClauseOccur.SHOULD);
		fieldBooleanFilter.add(
			termsSetFilterBuilder.build(), BooleanClauseOccur.SHOULD);

		contextBooleanFilter.add(fieldBooleanFilter, BooleanClauseOccur.MUST);
	}

	@Override
	protected long[] getUserClassPKs(User user) throws PortalException {
		return null;
	}

	protected long[] getUserClassPKs(
			User user, long organizationId, long... groupIds)
		throws PortalException {

		if (user.isDefaultUser()) {
			Role role = _roleLocalService.getRole(
				user.getCompanyId(), RoleConstants.GUEST);

			return new long[] {role.getRoleId()};
		}

		List<UserGroupRole> userGroupRoles = new ArrayList<>();

		Organization organization = _organizationLocalService.fetchOrganization(
			organizationId);

		if (organization != null) {
			userGroupRoles.addAll(
				_userGroupRoleLocalService.getUserGroupRoles(
					user.getUserId(), organization.getGroupId()));
		}

		for (long groupId : groupIds) {
			userGroupRoles.addAll(
				_userGroupRoleLocalService.getUserGroupRoles(
					user.getUserId(), groupId));
		}

		Stream<UserGroupRole> stream = userGroupRoles.stream();

		long[] roleIds = stream.mapToLong(UserGroupRole::getRoleId).toArray();

		return ArrayUtil.append(roleIds, user.getRoleIds());
	}

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private RoleLocalService _roleLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}