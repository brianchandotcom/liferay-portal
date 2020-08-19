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

package com.liferay.commerce.catalog.web.internal.frontend;

import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.commerce.account.service.CommerceAccountGroupRelService;
import com.liferay.commerce.account.service.CommerceAccountGroupService;
import com.liferay.commerce.catalog.web.internal.constants.CommerceCatalogDataSetConstants;
import com.liferay.commerce.catalog.web.internal.model.AccountGroup;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.frontend.taglib.clay.data.Filter;
import com.liferay.frontend.taglib.clay.data.Pagination;
import com.liferay.frontend.taglib.clay.data.set.provider.ClayDataSetDataProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gianmarco Brunialti Masera
 */
@Component(
	immediate = true,
	property = "clay.data.provider.key=" + CommerceCatalogDataSetConstants.COMMERCE_DATA_SET_KEY_CATALOG_ACCOUNT_GROUPS,
	service = ClayDataSetDataProvider.class
)
public class CommerceCatalogAccountGroupsDataSetDataProvider
	implements ClayDataSetDataProvider<AccountGroup> {

	@Override
	public List<AccountGroup> getItems(
			HttpServletRequest httpServletRequest, Filter filter,
			Pagination pagination, Sort sort)
		throws PortalException {

		List<AccountGroup> accountGroups = new ArrayList<>();

		long commerceCatalogId = ParamUtil.getLong(
			httpServletRequest, "commerceCatalogId");

		List<CommerceAccountGroupRel> commerceAccountGroups =
			_commerceAccountGroupRelService.getCommerceAccountGroupRels(
				CommerceCatalog.class.getName(), commerceCatalogId,
				pagination.getStartPosition(), pagination.getEndPosition(),
				null);

		for (CommerceAccountGroupRel commerceAccountGroupRel :
				commerceAccountGroups) {

			CommerceAccountGroup commerceAccountGroup =
				_commerceAccountGroupService.getCommerceAccountGroup(
					commerceAccountGroupRel.getCommerceAccountGroupId());

			accountGroups.add(new AccountGroup(commerceAccountGroup.getName()));
		}

		return accountGroups;
	}

	@Override
	public int getItemsCount(
			HttpServletRequest httpServletRequest, Filter filter)
		throws PortalException {

		long commerceCatalogId = ParamUtil.getLong(
			httpServletRequest, "commerceCatalogId");

		return _commerceAccountGroupRelService.getCommerceAccountGroupRelsCount(
			CommerceCatalog.class.getName(), commerceCatalogId);
	}

	@Reference
	private CommerceAccountGroupRelService _commerceAccountGroupRelService;

	@Reference
	private CommerceAccountGroupService _commerceAccountGroupService;

}