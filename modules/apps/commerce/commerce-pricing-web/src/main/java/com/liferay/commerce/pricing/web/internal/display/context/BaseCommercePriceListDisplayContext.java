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

package com.liferay.commerce.pricing.web.internal.display.context;

import com.liferay.commerce.price.list.constants.CommercePriceListConstants;
import com.liferay.commerce.price.list.model.CommercePriceList;
import com.liferay.commerce.price.list.service.CommercePriceListService;
import com.liferay.commerce.pricing.constants.CommercePricingPortletKeys;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogService;
import com.liferay.frontend.taglib.clay.data.set.servlet.taglib.util.ClayDataSetActionDropdownItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Alessio Antonio Rendina
 */
public abstract class BaseCommercePriceListDisplayContext
	extends BasePricingDisplayContext {

	public BaseCommercePriceListDisplayContext(
		CommerceCatalogService commerceCatalogService,
		ModelResourcePermission<CommercePriceList>
			commercePriceListModelResourcePermission,
		CommercePriceListService commercePriceListService,
		HttpServletRequest httpServletRequest) {

		super(httpServletRequest);

		this.commerceCatalogService = commerceCatalogService;
		this.commercePriceListModelResourcePermission =
			commercePriceListModelResourcePermission;
		this.commercePriceListService = commercePriceListService;
	}

	public long getCommerceCatalogId() throws PortalException {
		CommercePriceList commercePriceList = getCommercePriceList();

		CommerceCatalog commerceCatalog =
			commerceCatalogService.fetchCommerceCatalogByGroupId(
				commercePriceList.getGroupId());

		if (commerceCatalog == null) {
			return 0;
		}

		return commerceCatalog.getCommerceCatalogId();
	}

	public CommercePriceList getCommercePriceList() throws PortalException {
		if (commercePriceList != null) {
			return commercePriceList;
		}

		long commercePriceListId = ParamUtil.getLong(
			commercePricingRequestHelper.getRequest(), "commercePriceListId");

		if (commercePriceListId > 0) {
			commercePriceList = commercePriceListService.getCommercePriceList(
				commercePriceListId);
		}

		return commercePriceList;
	}

	public long getCommercePriceListId() throws PortalException {
		CommercePriceList commercePriceList = getCommercePriceList();

		if (commercePriceList == null) {
			return 0;
		}

		return commercePriceList.getCommercePriceListId();
	}

	public String getCommercePriceListType(String portletName) {
		if (Validator.isNull(portletName)) {
			return StringPool.BLANK;
		}

		if (portletName.equals(
				CommercePricingPortletKeys.COMMERCE_PRICE_LIST)) {

			return CommercePriceListConstants.TYPE_PRICE_LIST;
		}
		else if (portletName.equals(
					CommercePricingPortletKeys.COMMERCE_PROMOTION)) {

			return CommercePriceListConstants.TYPE_PROMOTION;
		}

		return StringPool.BLANK;
	}

	public boolean hasPermission(long commercePriceListId, String actionId)
		throws PortalException {

		return commercePriceListModelResourcePermission.contains(
			commercePricingRequestHelper.getPermissionChecker(),
			commercePriceListId, actionId);
	}

	public boolean hasPermission(String actionId) throws PortalException {
		PortletResourcePermission portletResourcePermission =
			commercePriceListModelResourcePermission.
				getPortletResourcePermission();

		return portletResourcePermission.contains(
			commercePricingRequestHelper.getPermissionChecker(), null,
			actionId);
	}

	protected List<ClayDataSetActionDropdownItem>
		getClayDataSetActionDropdownItems(
			String portletURL, boolean sidePanel) {

		List<ClayDataSetActionDropdownItem> clayDataSetActionDropdownItems =
			new ArrayList<>();

		ClayDataSetActionDropdownItem clayDataSetActionDropdownItem =
			new ClayDataSetActionDropdownItem(
				portletURL, "pencil", "edit",
				LanguageUtil.get(httpServletRequest, "edit"), "get", null,
				null);

		if (sidePanel) {
			clayDataSetActionDropdownItem.setTarget("sidePanel");
		}

		clayDataSetActionDropdownItems.add(clayDataSetActionDropdownItem);

		clayDataSetActionDropdownItems.add(
			new ClayDataSetActionDropdownItem(
				null, "trash", "delete",
				LanguageUtil.get(httpServletRequest, "delete"), "delete",
				"delete", "headless"));

		return clayDataSetActionDropdownItems;
	}

	protected CommerceCatalogService commerceCatalogService;
	protected CommercePriceList commercePriceList;
	protected final ModelResourcePermission<CommercePriceList>
		commercePriceListModelResourcePermission;
	protected CommercePriceListService commercePriceListService;

}