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

package com.liferay.organizations.internal.search.spi.model.query.contributor;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.WildcardQuery;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.TermQueryImpl;
import com.liferay.portal.kernel.search.generic.WildcardQueryImpl;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.permission.OrganizationPermission;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import java.util.LinkedHashMap;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Fabiano Nazar
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.portal.kernel.model.Organization",
	service = ModelPreFilterContributor.class
)
public class OrganizationModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	@SuppressWarnings("unchecked")
	public void contribute(
		BooleanFilter contextBooleanFilter,
		ModelSearchSettings modelSearchSettings, SearchContext searchContext) {

		LinkedHashMap<String, Object> params =
			(LinkedHashMap<String, Object>)searchContext.getAttribute("params");

		if (params == null) {
			return;
		}

		List<Long> excludedOrganizationIds = (List<Long>)params.get(
			"excludedOrganizationIds");

		if (ListUtil.isNotEmpty(excludedOrganizationIds)) {
			TermsFilter termsFilter = new TermsFilter("organizationId");

			termsFilter.addValues(
				ArrayUtil.toStringArray(
					excludedOrganizationIds.toArray(new Long[0])));

			contextBooleanFilter.add(termsFilter, BooleanClauseOccur.MUST_NOT);
		}

		List<Organization> organizationsTree = (List<Organization>)params.get(
			"organizationsTree");

		if (organizationsTree != null) {
			BooleanFilter booleanFilter = new BooleanFilter();

			if (organizationsTree.isEmpty()) {
				TermQuery termQuery = new TermQueryImpl(
					Field.TREE_PATH, StringPool.BLANK);

				booleanFilter.add(new QueryFilter(termQuery));
			}

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			for (Organization organization : organizationsTree) {
				String treePath;

				try {
					treePath = organization.buildTreePath();

					if ((permissionChecker != null) &&
						(permissionChecker.isOrganizationAdmin(
							organization.getOrganizationId()) ||
						 permissionChecker.isOrganizationOwner(
							 organization.getOrganizationId()) ||
						 _organizationPermission.contains(
							 permissionChecker, organization,
							 ActionKeys.MANAGE_SUBORGANIZATIONS))) {

						treePath = treePath + "*";
					}
				}
				catch (PortalException portalException) {
					throw new RuntimeException(portalException);
				}

				WildcardQuery wildcardQuery = new WildcardQueryImpl(
					Field.TREE_PATH, treePath);

				booleanFilter.add(new QueryFilter(wildcardQuery));
			}

			contextBooleanFilter.add(booleanFilter, BooleanClauseOccur.MUST);
		}
		else {
			long parentOrganizationId = GetterUtil.getLong(
				searchContext.getAttribute("parentOrganizationId"));

			if (parentOrganizationId !=
					OrganizationConstants.ANY_PARENT_ORGANIZATION_ID) {

				contextBooleanFilter.addRequiredTerm(
					"parentOrganizationId", parentOrganizationId);
			}
		}
	}

	@Reference
	private OrganizationPermission _organizationPermission;

}