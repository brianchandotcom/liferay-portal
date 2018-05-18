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

package com.liferay.commerce.product.definitions.web.internal.display.context;

import com.liferay.commerce.product.definitions.web.display.context.BaseCPDefinitionsSearchContainerDisplayContext;
import com.liferay.commerce.product.definitions.web.internal.util.CPDefinitionsPortletUtil;
import com.liferay.commerce.product.definitions.web.portlet.action.ActionHelper;
import com.liferay.commerce.product.definitions.web.servlet.taglib.ui.CPDefinitionScreenNavigationConstants;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelService;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.CustomAttributesUtil;

import java.util.List;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CPDefinitionOptionValueRelDisplayContext extends
	BaseCPDefinitionsSearchContainerDisplayContext<CPDefinitionOptionValueRel> {

	public CPDefinitionOptionValueRelDisplayContext(
			ActionHelper actionHelper, HttpServletRequest httpServletRequest,
			CPDefinitionOptionValueRelService cpDefinitionOptionValueRelService)
		throws PortalException {

		super(
			actionHelper, httpServletRequest,
			CPDefinitionOptionValueRel.class.getSimpleName());

		setDefaultOrderByCol("priority");

		_cpDefinitionOptionValueRelService = cpDefinitionOptionValueRelService;
	}

	public CPDefinitionOptionRel getCPDefinitionOptionRel()
		throws PortalException {

		if (_cpDefinitionOptionRel != null) {
			return _cpDefinitionOptionRel;
		}

		_cpDefinitionOptionRel = actionHelper.getCPDefinitionOptionRel(
			cpRequestHelper.getRenderRequest());

		return _cpDefinitionOptionRel;
	}

	public long getCPDefinitionOptionRelId() throws PortalException {
		CPDefinitionOptionRel cpDefinitionOptionRel =
			getCPDefinitionOptionRel();

		if (cpDefinitionOptionRel == null) {
			return 0;
		}

		return cpDefinitionOptionRel.getCPDefinitionOptionRelId();
	}

	public CPDefinitionOptionValueRel getCPDefinitionOptionValueRel()
		throws PortalException {

		if (_cpDefinitionOptionValueRel != null) {
			return _cpDefinitionOptionValueRel;
		}

		_cpDefinitionOptionValueRel =
			actionHelper.getCPDefinitionOptionValueRel(
				cpRequestHelper.getRenderRequest());

		return _cpDefinitionOptionValueRel;
	}

	public long getCPDefinitionOptionValueRelId() throws PortalException {
		CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
			getCPDefinitionOptionValueRel();

		if (cpDefinitionOptionValueRel == null) {
			return 0;
		}

		return cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId();
	}

	@Override
	public PortletURL getPortletURL() throws PortalException {
		PortletURL portletURL = super.getPortletURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "viewProductDefinitionOptionValueRels");
		portletURL.setParameter(
			"cpDefinitionOptionRelId",
			String.valueOf(getCPDefinitionOptionRelId()));

		return portletURL;
	}

	@Override
	public String getScreenNavigationCategoryKey() {
		return CPDefinitionScreenNavigationConstants.CATEGORY_KEY_OPTIONS;
	}

	@Override
	public SearchContainer<CPDefinitionOptionValueRel> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		searchContainer = new SearchContainer<>(
			liferayPortletRequest, getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage("no-option-values-were-found");

		OrderByComparator<CPDefinitionOptionValueRel> orderByComparator =
			CPDefinitionsPortletUtil.
				getCPDefinitionOptionValueRelOrderByComparator(
					getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		if (isSearch()) {
			Sort sort =
				CPDefinitionsPortletUtil.getCPDefinitionOptionValueRelSort(
					getOrderByCol(), getOrderByType());

			BaseModelSearchResult<CPDefinitionOptionValueRel>
				cpDefinitionOptionValueRelBaseModelSearchResult =
					_cpDefinitionOptionValueRelService.
						searchCPDefinitionOptionValueRels(
							themeDisplay.getCompanyId(),
							themeDisplay.getScopeGroupId(),
							getCPDefinitionOptionRelId(), getKeywords(),
							searchContainer.getStart(),
							searchContainer.getEnd(), sort);

			searchContainer.setTotal(
				cpDefinitionOptionValueRelBaseModelSearchResult.getLength());
			searchContainer.setResults(
				cpDefinitionOptionValueRelBaseModelSearchResult.
					getBaseModels());
		}
		else {
			int total =
				_cpDefinitionOptionValueRelService.
					getCPDefinitionOptionValueRelsCount(
						getCPDefinitionOptionRelId());

			searchContainer.setTotal(total);

			List<CPDefinitionOptionValueRel> results =
				_cpDefinitionOptionValueRelService.
					getCPDefinitionOptionValueRels(
						getCPDefinitionOptionRelId(),
						searchContainer.getStart(), searchContainer.getEnd(),
						orderByComparator);

			searchContainer.setResults(results);
		}

		return searchContainer;
	}

	public boolean hasCustomAttributesAvailable() throws Exception {
		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return CustomAttributesUtil.hasCustomAttributes(
			themeDisplay.getCompanyId(),
			CPDefinitionOptionValueRel.class.getName(),
			getCPDefinitionOptionValueRelId(), null);
	}

	private CPDefinitionOptionRel _cpDefinitionOptionRel;
	private CPDefinitionOptionValueRel _cpDefinitionOptionValueRel;
	private final CPDefinitionOptionValueRelService
		_cpDefinitionOptionValueRelService;

}