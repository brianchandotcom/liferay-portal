/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.search.spi.model.index.contributor;

import com.liferay.commerce.model.CommerceOrderItem;
import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.expando.ExpandoBridgeIndexer;
import com.liferay.portal.search.spi.model.index.contributor.ModelDocumentContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "indexer.class.name=com.liferay.commerce.model.CommerceOrderItem",
	service = ModelDocumentContributor.class
)
public class CommerceOrderItemModelDocumentContributor
	implements ModelDocumentContributor<CommerceOrderItem> {

	@Override
	public void contribute(
		Document document, CommerceOrderItem commerceOrderItem) {

		try {
			document.addKeyword(CPField.SKU, commerceOrderItem.getSku(), true);
			document.addNumber(
				Field.ENTRY_CLASS_PK,
				commerceOrderItem.getCommerceOrderItemId());
			document.addLocalizedKeyword(
				Field.NAME, commerceOrderItem.getNameMap(), false, true);
			document.addNumber(
				"commerceOrderId", commerceOrderItem.getCommerceOrderId());
			document.addKeyword(
				"CPDefinitionId", commerceOrderItem.getCPDefinitionId());
			document.addNumber("finalPrice", commerceOrderItem.getFinalPrice());
			document.addNumber(
				"parentCommerceOrderItemId",
				commerceOrderItem.getParentCommerceOrderItemId());
			document.addNumber("quantity", commerceOrderItem.getQuantity());
			document.addNumber("unitPrice", commerceOrderItem.getUnitPrice());

			String unitOfMeasureKey = commerceOrderItem.getUnitOfMeasureKey();

			if (Validator.isNotNull(unitOfMeasureKey)) {
				CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
					_cpInstanceUnitOfMeasureLocalService.
						fetchCPInstanceUnitOfMeasure(
							commerceOrderItem.getCPInstanceId(),
							unitOfMeasureKey);

				if (cpInstanceUnitOfMeasure != null) {
					document.addLocalizedText(
						"cpInstanceUnitOfMeasure",
						cpInstanceUnitOfMeasure.getNameMap(), true);
				}
			}

			_expandoBridgeIndexer.addAttributes(document, commerceOrderItem);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to index commerce order item " +
						commerceOrderItem.getCommerceOrderItemId(),
					exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderItemModelDocumentContributor.class);

	@Reference
	private CPInstanceUnitOfMeasureLocalService
		_cpInstanceUnitOfMeasureLocalService;

	@Reference
	private ExpandoBridgeIndexer _expandoBridgeIndexer;

}