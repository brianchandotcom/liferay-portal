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

package com.liferay.commerce.price.list.internal.verify;

import com.liferay.commerce.price.list.internal.helper.CommerceBasePriceListHelper;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.verify.VerifyProcess;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(
	enabled = false, immediate = true,
	property = "verify.process.name=com.liferay.commerce.price.list.service",
	service = {CommercePriceListServiceVerifyProcess.class, VerifyProcess.class}
)
public class CommercePriceListServiceVerifyProcess extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		verifyBasePriceLists();
	}

	protected void verifyBasePriceLists() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			List<Company> companies = _companyLocalService.getCompanies();

			for (Company company : companies) {
				List<CommerceCatalog> commerceCatalogs =
					_commerceCatalogLocalService.getCommerceCatalogs(
						company.getCompanyId(), true);

				for (CommerceCatalog commerceCatalog : commerceCatalogs) {
					_commerceBasePriceListHelper.
						addCatalogBaseCommercePriceList(commerceCatalog);
				}
			}
		}
	}

	@Reference
	private CommerceBasePriceListHelper _commerceBasePriceListHelper;

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

}