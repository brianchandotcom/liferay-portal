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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CommerceOrganizationSearchDisplayContext
	extends BaseCommerceOrganizationDisplayContext {

	public CommerceOrganizationSearchDisplayContext(
		CommerceOrganizationHelper commerceOrganizationHelper,
		CommerceOrganizationService commerceOrganizationService,
		HttpServletRequest httpServletRequest, Portal portal) {

		super(
			commerceOrganizationHelper, commerceOrganizationService,
			httpServletRequest, portal);

		setDefaultOrderByCol("name");
	}

	public String getKeywords() {
		if (Validator.isNotNull(_keywords)) {
			return _keywords;
		}

		_keywords = ParamUtil.getString(
			commerceOrganizationRequestHelper.getRequest(), "keywords", null);

		if (_keywords == null) {
			return StringPool.BLANK;
		}

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
			getPortletURL(), null, "no-results");

		Sort sort = CommerceOrganizationPortletUtil.getOrganizationSort(
			getOrderByCol(), getOrderByType());

		BaseModelSearchResult<Organization> baseModelSearchResult =
			commerceOrganizationService.searchOrganizationsByGroup(
				commerceOrganizationRequestHelper.getScopeGroupId(), userId,
				CommerceOrganizationConstants.TYPE_ACCOUNT, getKeywords(),
				_searchContainer.getStart(), _searchContainer.getEnd(),
				new Sort[] {sort});

		_searchContainer.setTotal(baseModelSearchResult.getLength());
		_searchContainer.setResults(baseModelSearchResult.getBaseModels());

		return _searchContainer;
	}

	private String _keywords;
	private SearchContainer<Organization> _searchContainer;

}