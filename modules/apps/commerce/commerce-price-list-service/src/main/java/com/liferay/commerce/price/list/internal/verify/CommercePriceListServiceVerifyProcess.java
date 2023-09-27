/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.price.list.internal.verify;

import com.liferay.commerce.price.list.internal.helper.CommerceBasePriceListHelper;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.service.CommerceCatalogLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.verify.VerifyProcess;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Alberti
 */
@Component(property = "initial.deployment=true", service = VerifyProcess.class)
public class CommercePriceListServiceVerifyProcess extends VerifyProcess {

	public void verifyBasePriceLists() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			_companyLocalService.forEachCompanyId(
				companyId -> {
					List<CommerceCatalog> commerceCatalogs =
						_commerceCatalogLocalService.getCommerceCatalogs(
							companyId, true);

					for (CommerceCatalog commerceCatalog : commerceCatalogs) {
						_commerceBasePriceListHelper.
							addCatalogBaseCommercePriceList(commerceCatalog);
					}
				});
		}
	}

	@Override
	protected void doVerify() throws Exception {
		verifyBasePriceLists();
	}

	@Reference
	private CommerceBasePriceListHelper _commerceBasePriceListHelper;

	@Reference
	private CommerceCatalogLocalService _commerceCatalogLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

}