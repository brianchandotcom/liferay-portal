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

package com.liferay.commerce.tax.engine.fixed.web.internal.display.context;

import com.liferay.commerce.currency.service.CommerceCurrencyService;
import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.model.CommerceRegion;
import com.liferay.commerce.product.model.CPTaxCategory;
import com.liferay.commerce.product.service.CPTaxCategoryService;
import com.liferay.commerce.product.util.comparator.CPTaxCategoryCreateDateComparator;
import com.liferay.commerce.service.CommerceCountryService;
import com.liferay.commerce.service.CommerceRegionService;
import com.liferay.commerce.service.CommerceTaxMethodService;
import com.liferay.commerce.tax.engine.fixed.model.CommerceTaxFixedRateAddressRel;
import com.liferay.commerce.tax.engine.fixed.service.CommerceTaxFixedRateAddressRelService;
import com.liferay.commerce.tax.engine.fixed.util.CommerceTaxEngineFixedUtil;
import com.liferay.commerce.tax.engine.fixed.web.internal.servlet.taglib.ui.CommerceTaxMethodAddressRateRelsScreenNavigationEntry;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CommerceTaxFixedRateAddressRelsDisplayContext
	extends BaseCommerceTaxFixedRateDisplayContext
		<CommerceTaxFixedRateAddressRel> {

	public CommerceTaxFixedRateAddressRelsDisplayContext(
		CommerceCountryService commerceCountryService,
		CommerceCurrencyService commerceCurrencyService,
		CommerceRegionService commerceRegionService,
		CommerceTaxMethodService commerceTaxMethodService,
		CommerceTaxFixedRateAddressRelService
			commerceTaxFixedRateAddressRelService,
		CPTaxCategoryService cpTaxCategoryService, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		super(
			commerceCurrencyService, commerceTaxMethodService, renderRequest,
			renderResponse);

		_commerceCountryService = commerceCountryService;
		_commerceRegionService = commerceRegionService;
		_commerceTaxFixedRateAddressRelService =
			commerceTaxFixedRateAddressRelService;
		_cpTaxCategoryService = cpTaxCategoryService;
	}

	public List<CPTaxCategory> getAvailableCPTaxCategories()
		throws PortalException {

		return _cpTaxCategoryService.getCPTaxCategories(
			commerceTaxFixedRateRequestHelper.getScopeGroupId(),
			QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			new CPTaxCategoryCreateDateComparator());
	}

	public List<CommerceCountry> getCommerceCountries() {
		return _commerceCountryService.getCommerceCountries(
			commerceTaxFixedRateRequestHelper.getScopeGroupId(), true);
	}

	public long getCommerceCountryId() throws PortalException {
		long commerceCountryId = 0;

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			getCommerceTaxFixedRateAddressRel();

		if (commerceTaxFixedRateAddressRel != null) {
			commerceCountryId =
				commerceTaxFixedRateAddressRel.getCommerceCountryId();
		}

		return commerceCountryId;
	}

	public long getCommerceRegionId() throws PortalException {
		long commerceRegionId = 0;

		CommerceTaxFixedRateAddressRel commerceTaxFixedRateAddressRel =
			getCommerceTaxFixedRateAddressRel();

		if (commerceTaxFixedRateAddressRel != null) {
			commerceRegionId =
				commerceTaxFixedRateAddressRel.getCommerceRegionId();
		}

		return commerceRegionId;
	}

	public List<CommerceRegion> getCommerceRegions() throws PortalException {
		return _commerceRegionService.getCommerceRegions(
			getCommerceCountryId(), true);
	}

	public CommerceTaxFixedRateAddressRel getCommerceTaxFixedRateAddressRel()
		throws PortalException {

		long commerceTaxFixedRateAddressRelId = ParamUtil.getLong(
			commerceTaxFixedRateRequestHelper.getRequest(),
			"commerceTaxFixedRateAddressRelId");

		return _commerceTaxFixedRateAddressRelService.
			fetchCommerceTaxFixedRateAddressRel(
				commerceTaxFixedRateAddressRelId);
	}

	@Override
	public String getScreenNavigationEntryKey() {
		return CommerceTaxMethodAddressRateRelsScreenNavigationEntry.ENTRY_KEY;
	}

	@Override
	public SearchContainer<CommerceTaxFixedRateAddressRel> getSearchContainer()
		throws PortalException {

		if (searchContainer != null) {
			return searchContainer;
		}

		searchContainer = new SearchContainer<>(
			commerceTaxFixedRateRequestHelper.getLiferayPortletRequest(),
			getPortletURL(), null, null);

		searchContainer.setEmptyResultsMessage(
			"there-are-no-tax-rate-settings");

		OrderByComparator<CommerceTaxFixedRateAddressRel> orderByComparator =
			CommerceTaxEngineFixedUtil.
				getCommerceTaxFixedRateAddressRelOrderByComparator(
					getOrderByCol(), getOrderByType());

		searchContainer.setOrderByCol(getOrderByCol());
		searchContainer.setOrderByComparator(orderByComparator);
		searchContainer.setOrderByType(getOrderByType());
		searchContainer.setRowChecker(getRowChecker());

		int total =
			_commerceTaxFixedRateAddressRelService.
				getCommerceTaxMethodFixedRateAddressRelsCount(
					commerceTaxFixedRateRequestHelper.getScopeGroupId(),
					getCommerceTaxMethodId());

		searchContainer.setTotal(total);

		List<CommerceTaxFixedRateAddressRel> results =
			_commerceTaxFixedRateAddressRelService.
				getCommerceTaxMethodFixedRateAddressRels(
					commerceTaxFixedRateRequestHelper.getScopeGroupId(),
					getCommerceTaxMethodId(), searchContainer.getStart(),
					searchContainer.getEnd(), orderByComparator);

		searchContainer.setResults(results);

		return searchContainer;
	}

	private final CommerceCountryService _commerceCountryService;
	private final CommerceRegionService _commerceRegionService;
	private final CommerceTaxFixedRateAddressRelService
		_commerceTaxFixedRateAddressRelService;
	private final CPTaxCategoryService _cpTaxCategoryService;

}