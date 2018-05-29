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

package com.liferay.commerce.user.segment.internal.search;

import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionType;
import com.liferay.commerce.user.segment.criterion.CommerceUserSegmentCriterionTypeRegistry;
import com.liferay.commerce.user.segment.model.CommerceUserSegmentCriterion;
import com.liferay.commerce.user.segment.service.CommerceUserSegmentCriterionLocalService;
import com.liferay.commerce.user.segment.util.comparator.CommerceUserSegmentCriterionPriorityComparator;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserGroupRole;
import com.liferay.portal.kernel.search.BaseIndexerPostProcessor;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.kernel.model.User",
	service = IndexerPostProcessor.class
)
public class UserIndexerPostProcessor extends BaseIndexerPostProcessor {

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter booleanFilter, SearchContext searchContext)
		throws Exception {

		long[] commerceUserSegmentEntryIds = GetterUtil.getLongValues(
			searchContext.getAttribute("commerceUserSegmentEntryIds"), null);

		if (commerceUserSegmentEntryIds == null) {
			return;
		}

		for (long commerceUserSegmentEntryId : commerceUserSegmentEntryIds) {
			List<CommerceUserSegmentCriterion> commerceUserSegmentCriteria =
				_commerceUserSegmentCriterionLocalService.
					getCommerceUserSegmentCriteria(
						commerceUserSegmentEntryId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS,
						new CommerceUserSegmentCriterionPriorityComparator(
							true));

			for (CommerceUserSegmentCriterion commerceUserSegmentCriterion :
					commerceUserSegmentCriteria) {

				CommerceUserSegmentCriterionType
					commerceUserSegmentCriterionType =
						_commerceUserSegmentCriterionTypeRegistry.
							getCommerceUserSegmentCriterionType(
								commerceUserSegmentCriterion.getType());

				commerceUserSegmentCriterionType.
					userPostProcessContextBooleanFilter(
						commerceUserSegmentCriterion, booleanFilter,
						searchContext);
			}
		}
	}

	@Override
	public void postProcessDocument(Document document, Object obj)
		throws Exception {

		User user = (User)obj;

		List<Organization> organizations =
			_organizationLocalService.getUserOrganizations(user.getUserId());

		for (Organization organization : organizations) {
			List<UserGroupRole> userGroupRoles =
				_userGroupRoleLocalService.getUserGroupRoles(
					user.getUserId(), organization.getGroupId());

			Stream<UserGroupRole> stream = userGroupRoles.stream();

			long[] roleIds = stream.mapToLong(
				UserGroupRole::getRoleId).toArray();

			document.addNumber(
				"organization_" + organization.getOrganizationId() + "_roleIds",
				roleIds);
		}
	}

	@Reference
	private CommerceUserSegmentCriterionLocalService
		_commerceUserSegmentCriterionLocalService;

	@Reference
	private CommerceUserSegmentCriterionTypeRegistry
		_commerceUserSegmentCriterionTypeRegistry;

	@Reference
	private OrganizationLocalService _organizationLocalService;

	@Reference
	private UserGroupRoleLocalService _userGroupRoleLocalService;

}