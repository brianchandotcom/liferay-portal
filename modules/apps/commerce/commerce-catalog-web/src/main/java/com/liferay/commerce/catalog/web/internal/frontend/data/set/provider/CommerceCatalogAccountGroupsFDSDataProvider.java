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

package com.liferay.commerce.catalog.web.internal.frontend.data.set.provider;

import com.liferay.commerce.account.model.CommerceAccountGroup;
import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.commerce.account.service.CommerceAccountGroupRelService;
import com.liferay.commerce.account.service.CommerceAccountGroupService;
import com.liferay.commerce.catalog.web.internal.constants.CommerceCatalogFDSNames;
import com.liferay.commerce.catalog.web.internal.model.AccountGroup;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.frontend.data.set.provider.FDSDataProvider;
import com.liferay.frontend.data.set.provider.search.FDSKeywords;
import com.liferay.frontend.data.set.provider.search.FDSPagination;
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
	enabled = false, immediate = true,
	property = "fds.data.provider.key=" + CommerceCatalogFDSNames.CATALOG_ACCOUNT_GROUPS,
	service = FDSDataProvider.class
)
public class CommerceCatalogAccountGroupsFDSDataProvider
	implements FDSDataProvider<AccountGroup> {

	@Override
	public List<AccountGroup> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		List<AccountGroup> accountGroups = new ArrayList<>();

		long commerceCatalogId = ParamUtil.getLong(
			httpServletRequest, "commerceCatalogId");

		List<CommerceAccountGroupRel> commerceAccountGroups =
			_commerceAccountGroupRelService.getCommerceAccountGroupRels(
				CommerceCatalog.class.getName(), commerceCatalogId,
				fdsPagination.getStartPosition(),
				fdsPagination.getEndPosition(), null);

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
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
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