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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
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
			long userId, long commerceInventoryWarehouseId, String sku,
			int quantity, long bookedQuantityId, Map<String, String> context)
		throws PortalException {

		if (bookedQuantityId > 0) {
			_commerceBookedQuantityLocalService.consumeCommerceBookedQuantity(
				bookedQuantityId, quantity);
		}

		decreaseStockQuantity(
			userId, commerceInventoryWarehouseId, sku, quantity);

		CommerceInventoryAuditType commerceInventoryAuditType =
			_commerceInventoryAuditTypeRegistry.getCommerceInventoryAuditType(
				CommerceInventoryConstants.AUDIT_TYPE_CONSUME_QUANTITY);

		_commerceInventoryAuditLocalService.addCommerceInventoryAudit(
			userId, commerceInventoryAuditType.getType(),
			commerceInventoryAuditType.getLog(context),
			BigDecimal.valueOf(quantity), sku, StringPool.BLANK);

		for (CommerceInventoryEngineContributor
				commerceInventoryEngineContributor :
					_commerceInventoryEngineContributorRegistry.
						getCommerceInventoryEngineContributors()) {

			commerceInventoryEngineContributor.consumeQuantityContribute(
				userId, commerceInventoryWarehouseId, sku, quantity,
				bookedQuantityId, context);
		}
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public void decreaseStockQuantity(
			long userId, long commerceInventoryWarehouseId, String sku,
			int quantity)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemLocalService.
				fetchCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseId, sku);

		BigDecimal commerceInventoryWarehouseItemQuantity =
			commerceInventoryWarehouseItem.getQuantity();

		_commerceInventoryWarehouseItemLocalService.
			updateCommerceInventoryWarehouseItem(
				userId,
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseItemId(),
				commerceInventoryWarehouseItem.getMvccVersion(),
				commerceInventoryWarehouseItemQuantity.subtract(
					BigDecimal.valueOf(quantity)),
				commerceInventoryWarehouseItem.getUnitOfMeasureKey());

		for (CommerceInventoryEngineContributor
				commerceInventoryEngineContributor :
					_commerceInventoryEngineContributorRegistry.
						getCommerceInventoryEngineContributors()) {

			commerceInventoryEngineContributor.decreaseStockQuantityContribute(
				userId, commerceInventoryWarehouseId, sku, quantity);
		}
	}

	@Override
	public String getAvailabilityStatus(
		long companyId, long commerceChannelGroupId, int minStockQuantity,
		String sku) {

		return _getAvailabilityStatus(
			minStockQuantity,
			getStockQuantity(companyId, commerceChannelGroupId, sku));
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
	public int getStockQuantity(
		long companyId, long commerceChannelGroupId, String sku) {

		BigDecimal stockQuantity =
			_commerceInventoryWarehouseItemService.getStockQuantity(
				companyId, commerceChannelGroupId, sku);

		int commerceBookedQuantity =
			_commerceBookedQuantityLocalService.getCommerceBookedQuantity(
				companyId, commerceChannelGroupId, sku);

		return stockQuantity.intValue() - commerceBookedQuantity;
	}

	@Override
	public int getStockQuantity(long companyId, String sku) {
		BigDecimal stockQuantity =
			_commerceInventoryWarehouseItemService.getStockQuantity(
				companyId, sku);

		int commerceBookedQuantity =
			_commerceBookedQuantityLocalService.getCommerceBookedQuantity(
				companyId, sku);

		return stockQuantity.intValue() - commerceBookedQuantity;
	}

	@Override
	public boolean hasStockQuantity(long companyId, String sku, int quantity) {
		if (quantity <= getStockQuantity(companyId, sku)) {
			return true;
		}

		return false;
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, rollbackFor = Exception.class
	)
	public void increaseStockQuantity(
			long userId, long commerceInventoryWarehouseId, String sku,
			int quantity)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			_commerceInventoryWarehouseItemLocalService.
				fetchCommerceInventoryWarehouseItem(
					commerceInventoryWarehouseId, sku);

		try {
			BigDecimal commerceInventoryWarehouseItemQuantity =
				commerceInventoryWarehouseItem.getQuantity();

			_commerceInventoryWarehouseItemLocalService.
				updateCommerceInventoryWarehouseItem(
					userId,
					commerceInventoryWarehouseItem.
						getCommerceInventoryWarehouseItemId(),
					commerceInventoryWarehouseItem.getMvccVersion(),
					commerceInventoryWarehouseItemQuantity.add(
						BigDecimal.valueOf(quantity)),
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
			commerceInventoryAuditType.getLog(null),
			BigDecimal.valueOf(quantity), sku, StringPool.BLANK);

		for (CommerceInventoryEngineContributor
				commerceInventoryEngineContributor :
					_commerceInventoryEngineContributorRegistry.
						getCommerceInventoryEngineContributors()) {

			commerceInventoryEngineContributor.increaseStockQuantityContribute(
				userId, commerceInventoryWarehouseId, sku, quantity);
		}
	}

	private String _getAvailabilityStatus(
		int minStockQuantity, int stockQuantity) {

		String availabilityStatus =
			CommerceInventoryAvailabilityConstants.UNAVAILABLE;

		boolean available = false;

		if (stockQuantity > minStockQuantity) {
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
	private CommerceInventoryBookedQuantityLocalService
		_commerceBookedQuantityLocalService;

	@Reference
	private CommerceInventoryAuditLocalService
		_commerceInventoryAuditLocalService;

	@Reference
	private CommerceInventoryAuditTypeRegistry
		_commerceInventoryAuditTypeRegistry;

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