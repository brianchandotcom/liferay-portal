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

import com.liferay.account.model.AccountGroup;
import com.liferay.account.model.AccountGroupRel;
import com.liferay.account.service.AccountGroupRelLocalService;
import com.liferay.account.service.AccountGroupService;
import com.liferay.commerce.catalog.web.internal.constants.CommerceCatalogFDSNames;
import com.liferay.commerce.catalog.web.internal.model.CatalogAccountGroup;
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
	property = "fds.data.provider.key=" + CommerceCatalogFDSNames.CATALOG_ACCOUNT_GROUPS,
	service = FDSDataProvider.class
)
public class CommerceCatalogAccountGroupsFDSDataProvider
	implements FDSDataProvider<CatalogAccountGroup> {

	@Override
	public List<CatalogAccountGroup> getItems(
			FDSKeywords fdsKeywords, FDSPagination fdsPagination,
			HttpServletRequest httpServletRequest, Sort sort)
		throws PortalException {

		List<CatalogAccountGroup> catalogAccountGroups = new ArrayList<>();

		long commerceCatalogId = ParamUtil.getLong(
			httpServletRequest, "commerceCatalogId");

		List<AccountGroupRel> accountGroupRels =
			_accountGroupRelLocalService.getAccountGroupRels(
				CommerceCatalog.class.getName(), commerceCatalogId,
				fdsPagination.getStartPosition(),
				fdsPagination.getEndPosition(), null);

		for (AccountGroupRel accountGroupRel : accountGroupRels) {
			AccountGroup accountGroup = _accountGroupService.getAccountGroup(
				accountGroupRel.getAccountGroupId());

			catalogAccountGroups.add(
				new CatalogAccountGroup(accountGroup.getName()));
		}

		return catalogAccountGroups;
	}

	@Override
	public int getItemsCount(
			FDSKeywords fdsKeywords, HttpServletRequest httpServletRequest)
		throws PortalException {

		long commerceCatalogId = ParamUtil.getLong(
			httpServletRequest, "commerceCatalogId");

		return _accountGroupRelLocalService.getAccountGroupRelsCount(
			CommerceCatalog.class.getName(), commerceCatalogId);
	}

	@Reference
	private AccountGroupRelLocalService _accountGroupRelLocalService;

	@Reference
	private AccountGroupService _accountGroupService;

}