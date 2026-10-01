/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.commerce.admin.catalog.internal.util.v1_0;

import com.liferay.commerce.product.exception.NoSuchCProductException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryService;
import com.liferay.headless.commerce.admin.catalog.dto.v1_0.GroupedProduct;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lazy.referencing.LazyReferencingThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Alessio Antonio Rendina
 */
public class GroupedProductUtil {

	public static CPDefinitionGroupedEntry addCPDefinitionGroupedEntry(
			CPDefinition cpDefinition,
			CPDefinitionGroupedEntryService cpDefinitionGroupedEntryService,
			CPDefinitionService cpDefinitionService,
			GroupedProduct groupedProduct, ServiceContext serviceContext)
		throws PortalException {

		CPDefinition entryCPDefinition = _getEntryCPDefinition(
			cpDefinition, cpDefinitionService, groupedProduct);

		return cpDefinitionGroupedEntryService.addCPDefinitionGroupedEntry(
			cpDefinition.getCPDefinitionId(), entryCPDefinition.getCProductId(),
			GetterUtil.getDouble(groupedProduct.getPriority()),
			GetterUtil.getInteger(groupedProduct.getQuantity()),
			serviceContext);
	}

	public static CPDefinitionGroupedEntry addOrUpdateCPDefinitionGroupedEntry(
			CPDefinition cpDefinition,
			CPDefinitionGroupedEntryService cpDefinitionGroupedEntryService,
			CPDefinitionService cpDefinitionService,
			GroupedProduct groupedProduct, ServiceContext serviceContext)
		throws PortalException {

		CPDefinition entryCPDefinition = _getEntryCPDefinition(
			cpDefinition, cpDefinitionService, groupedProduct);

		for (CPDefinitionGroupedEntry cpDefinitionGroupedEntry :
				cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntries(
					cpDefinition.getCPDefinitionId(), QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			if (cpDefinitionGroupedEntry.getEntryCProductId() ==
					entryCPDefinition.getCProductId()) {

				return cpDefinitionGroupedEntryService.
					updateCPDefinitionGroupedEntry(
						cpDefinitionGroupedEntry.
							getCPDefinitionGroupedEntryId(),
						GetterUtil.getDouble(
							groupedProduct.getPriority(),
							cpDefinitionGroupedEntry.getPriority()),
						GetterUtil.getInteger(
							groupedProduct.getQuantity(),
							cpDefinitionGroupedEntry.getQuantity()));
			}
		}

		return cpDefinitionGroupedEntryService.addCPDefinitionGroupedEntry(
			cpDefinition.getCPDefinitionId(), entryCPDefinition.getCProductId(),
			GetterUtil.getDouble(groupedProduct.getPriority()),
			GetterUtil.getInteger(groupedProduct.getQuantity()),
			serviceContext);
	}

	private static CPDefinition _getEntryCPDefinition(
			CPDefinition cpDefinition, CPDefinitionService cpDefinitionService,
			GroupedProduct groupedProduct)
		throws PortalException {

		CPDefinition entryCPDefinition = null;

		String entryProductExternalReferenceCode =
			groupedProduct.getEntryProductExternalReferenceCode();

		if (Validator.isNotNull(entryProductExternalReferenceCode)) {
			entryCPDefinition =
				cpDefinitionService.
					fetchCPDefinitionByCProductExternalReferenceCode(
						entryProductExternalReferenceCode,
						cpDefinition.getCompanyId(), false);
		}

		if ((entryCPDefinition == null) &&
			!LazyReferencingThreadLocal.isEnabled()) {

			entryCPDefinition =
				cpDefinitionService.fetchCPDefinitionByCProductId(
					groupedProduct.getEntryProductId(), false);
		}

		if (entryCPDefinition != null) {
			return entryCPDefinition;
		}

		if (Validator.isNull(entryProductExternalReferenceCode)) {
			throw new NoSuchCProductException(
				"Unable to find entry product with external reference code " +
					entryProductExternalReferenceCode);
		}

		return ProductUtil.getCPDefinitionByCProductExternalReferenceCode(
			cpDefinition.getCompanyId(), cpDefinitionService,
			entryProductExternalReferenceCode, cpDefinition.getGroupId(),
			groupedProduct.getEntryProductType());
	}

}