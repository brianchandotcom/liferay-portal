/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.internal.method;

import com.liferay.commerce.inventory.constants.CommerceInventoryAvailabilityConstants;
import com.liferay.commerce.inventory.constants.CommerceInventoryConstants;
import com.liferay.commerce.inventory.engine.contributor.CommerceInventoryEngineContributor;
import com.liferay.commerce.inventory.engine.contributor.CommerceInventoryEngineContributorRegistry;
import com.liferay.commerce.inventory.exception.MVCCException;
import com.liferay.commerce.inventory.method.CommerceInventoryMethod;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryAuditLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryBookedQuantityLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemLocalService;
import com.liferay.commerce.inventory.service.CommerceInventoryWarehouseItemService;
import com.liferay.commerce.inventory.type.CommerceInventoryAuditType;
import com.liferay.commerce.inventory.type.CommerceInventoryAuditTypeRegistry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.BigDecimalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.math.BigDecimal;

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = {
		"commerce.inventory.method.key=" + CommerceInventoryConstants.DEFAULT_METHOD_KEY,
		"commerce.inventory.method.order:Integer=100"
	},
	service = CommerceInventoryMethod.class
)
public class DefaultCommerceInventoryMethodImpl
	implements CommerceInventoryMethod {

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public void consumeQuantity(
			long userId, long commerceInventoryBookedQuantityId,
			long commerceInventoryWarehouseId, BigDecimal quantity, String sku,
			String unitOfMeasureKey, Map<String, String> context)
		throws PortalException {

		if (commerceInventoryBookedQuantityId > 0) {
			_commerceInventoryBookedQuantityLocalService.
				consumeCommerceInventoryBookedQuantity(
					commerceInventoryBookedQuantityId, quantity);
		}

		decreaseStockQuantity(
			userId, commerceInventoryWarehouseId, quantity, sku,
			unitOfMeasureKey);

		CommerceInventoryAuditType commerceInventoryAuditType =
			_commerceInventoryAuditTypeRegistry.getCommerceInventoryAuditType(
				CommerceInventoryConstants.AUDIT_TYPE_CONSUME_QUANTITY);

		_commerceInventoryAuditLocalService.addCommerceInventoryAudit(
			userId, commerceInventoryAuditType.getType(),
			commerceInventoryAuditType.getLog(context), quantity, sku,
			unitOfMeasureKey);

		for (CommerceInventoryEngineContributor
				commerceInventoryEngineContributor :
					_commerceInventoryEngineContributorRegistry.
						getCommerceInventoryEngineContributors()) {

			commerceInventoryEngineContributor.consumeQuantityContribute(
				userId, commerceInventoryBookedQuantityId,
				commerceInventoryWarehouseId, quantity, sku, unitOfMeasureKey,
				context);
		}
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public void decreaseStockQuantity(
			long userId, long commerceInventoryWarehouseId, BigDecimal quantity,
			String sku, String unitOfMeasureKey)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemLocalService.
				fetchCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseId, sku, unitOfMeasureKey);

		BigDecimal commerceInventoryWarehouseItemQuantity =
			commerceInventoryWarehouseItem.getQuantity();

		_commerceInventoryWarehouseItemLocalService.
			updateCommerceInventoryWarehouseItem(
				userId,
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseItemId(),
				commerceInventoryWarehouseItem.getMvccVersion(),
				commerceInventoryWarehouseItemQuantity.subtract(quantity),
				commerceInventoryWarehouseItem.getUnitOfMeasureKey());

		for (CommerceInventoryEngineContributor
				commerceInventoryEngineContributor :
					_commerceInventoryEngineContributorRegistry.
						getCommerceInventoryEngineContributors()) {

			commerceInventoryEngineContributor.decreaseStockQuantityContribute(
				userId, commerceInventoryWarehouseId, quantity, sku,
				unitOfMeasureKey);
		}
	}

	@Override
	public String getAvailabilityStatus(
		long companyId, long commerceChannelGroupId,
		BigDecimal minStockQuantity, String sku, String unitOfMeasureKey) {

		return _getAvailabilityStatus(
			minStockQuantity,
			getStockQuantity(
				companyId, commerceChannelGroupId, sku, unitOfMeasureKey));
	}

	@Override
	public String getKey() {
		return CommerceInventoryConstants.DEFAULT_METHOD_KEY;
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return _language.get(resourceBundle, getKey());
	}

	@Override
	public BigDecimal getStockQuantity(
		long companyId, long commerceChannelGroupId, String sku,
		String unitOfMeasureKey) {

		BigDecimal stockQuantity =
			_commerceInventoryWarehouseItemService.getStockQuantity(
				companyId, commerceChannelGroupId, sku, unitOfMeasureKey);

		return stockQuantity.subtract(
			_commerceInventoryBookedQuantityLocalService.
				getCommerceInventoryBookedQuantity(
					companyId, commerceChannelGroupId, sku, unitOfMeasureKey));
	}

	@Override
	public BigDecimal getStockQuantity(
		long companyId, String sku, String unitOfMeasureKey) {

		BigDecimal stockQuantity =
			_commerceInventoryWarehouseItemService.getStockQuantity(
				companyId, sku, unitOfMeasureKey);

		return stockQuantity.subtract(
			_commerceInventoryBookedQuantityLocalService.
				getCommerceInventoryBookedQuantity(
					companyId, sku, unitOfMeasureKey));
	}

	@Override
	public boolean hasStockQuantity(
		long companyId, BigDecimal quantity, String sku,
		String unitOfMeasureKey) {

		if (BigDecimalUtil.lte(
				quantity, getStockQuantity(companyId, sku, unitOfMeasureKey))) {

			return true;
		}

		return false;
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public void increaseStockQuantity(
			long userId, long commerceInventoryWarehouseId, BigDecimal quantity,
			String sku, String unitOfMeasureKey)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemLocalService.
				fetchCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseId, sku, unitOfMeasureKey);

		try {
			BigDecimal commerceInventoryWarehouseItemQuantity =
				commerceInventoryWarehouseItem.getQuantity();

			_commerceInventoryWarehouseItemLocalService.
				updateCommerceInventoryWarehouseItem(
					userId,
					commerceInventoryWarehouseItem.
						getCommerceInventoryWarehouseItemId(),
					commerceInventoryWarehouseItem.getMvccVersion(),
					commerceInventoryWarehouseItemQuantity.add(quantity),
					commerceInventoryWarehouseItem.getUnitOfMeasureKey());
		}
		catch (MVCCException mvccException) {
			_log.error(mvccException);

			throw mvccException;
		}

		CommerceInventoryAuditType commerceInventoryAuditType =
			_commerceInventoryAuditTypeRegistry.getCommerceInventoryAuditType(
				CommerceInventoryConstants.AUDIT_TYPE_INCREASE_QUANTITY);

		_commerceInventoryAuditLocalService.addCommerceInventoryAudit(
			userId, commerceInventoryAuditType.getType(),
			commerceInventoryAuditType.getLog(null), quantity, sku,
			unitOfMeasureKey);

		for (CommerceInventoryEngineContributor
				commerceInventoryEngineContributor :
					_commerceInventoryEngineContributorRegistry.
						getCommerceInventoryEngineContributors()) {

			commerceInventoryEngineContributor.increaseStockQuantityContribute(
				userId, commerceInventoryWarehouseId, quantity, sku,
				unitOfMeasureKey);
		}
	}

	private String _getAvailabilityStatus(
		BigDecimal minStockQuantity, BigDecimal stockQuantity) {

		String availabilityStatus =
			CommerceInventoryAvailabilityConstants.UNAVAILABLE;

		boolean available = false;

		if (BigDecimalUtil.gt(stockQuantity, minStockQuantity)) {
			available = true;
		}

		if (available) {
			availabilityStatus =
				CommerceInventoryAvailabilityConstants.AVAILABLE;
		}

		return availabilityStatus;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultCommerceInventoryMethodImpl.class);

	@Reference
	private CommerceInventoryAuditLocalService
		_commerceInventoryAuditLocalService;

	@Reference
	private CommerceInventoryAuditTypeRegistry
		_commerceInventoryAuditTypeRegistry;

	@Reference
	private CommerceInventoryBookedQuantityLocalService
		_commerceInventoryBookedQuantityLocalService;

	@Reference
	private CommerceInventoryEngineContributorRegistry
		_commerceInventoryEngineContributorRegistry;

	@Reference
	private CommerceInventoryWarehouseItemLocalService
		_commerceInventoryWarehouseItemLocalService;

	@Reference
	private CommerceInventoryWarehouseItemService
		_commerceInventoryWarehouseItemService;

	@Reference
	private Language _language;

}