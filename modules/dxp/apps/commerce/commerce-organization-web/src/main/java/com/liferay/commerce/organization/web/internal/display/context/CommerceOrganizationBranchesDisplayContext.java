/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.organization.web.internal.display.context;

import com.liferay.commerce.organization.constants.CommerceOrganizationConstants;
import com.liferay.commerce.organization.service.CommerceOrganizationService;
import com.liferay.commerce.organization.util.CommerceOrganizationHelper;
import com.liferay.commerce.organization.web.internal.util.CommerceOrganizationPortletUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CommerceOrganizationBranchesDisplayContext
	extends BaseCommerceOrganizationDisplayContext {

	public CommerceOrganizationBranchesDisplayContext(
		CommerceOrganizationHelper commerceOrganizationHelper,
		CommerceOrganizationService commerceOrganizationService,
		HttpServletRequest httpServletRequest, Portal portal) {

		super(
			commerceOrganizationHelper, commerceOrganizationService,
			httpServletRequest, portal);

		setDefaultOrderByCol("nameTreePath");
	}

	public String getKeywords() {
		if (_keywords != null) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(
			commerceOrganizationRequestHelper.getRequest(), "keywords");

		return _keywords;
	}

	public SearchContainer<Organization> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		long userId = commerceOrganizationRequestHelper.getUserId();

		_searchContainer = new SearchContainer<>(
			commerceOrganizationRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, "no-organizations-were-found");

		Organization organization = getCurrentOrganization();

		Sort sort = CommerceOrganizationPortletUtil.getOrganizationSort(
			getOrderByCol(), getOrderByType());

		BaseModelSearchResult<Organization> organizationBaseModelSearchResult =
			commerceOrganizationService.searchOrganizations(
				userId, organization.getOrganizationId(),
				CommerceOrganizationConstants.TYPE_BRANCH, getKeywords(),
				_searchContainer.getStart(), _searchContainer.getEnd(),
				new Sort[] {sort});

		_searchContainer.setTotal(
			organizationBaseModelSearchResult.getLength());

		_searchContainer.setResults(
			organizationBaseModelSearchResult.getBaseModels());

		return _searchContainer;
	}

	public boolean hasManageBranchesPermission() {
		return true;
	}

	private String _keywords;
	private SearchContainer<Organization> _searchContainer;

}