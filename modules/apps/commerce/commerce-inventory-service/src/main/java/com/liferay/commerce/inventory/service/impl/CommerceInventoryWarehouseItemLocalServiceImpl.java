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

package com.liferay.commerce.inventory.service.impl;

import com.liferay.commerce.inventory.constants.CommerceInventoryConstants;
import com.liferay.commerce.inventory.exception.DuplicateCommerceInventoryWarehouseItemException;
import com.liferay.commerce.inventory.exception.MVCCException;
import com.liferay.commerce.inventory.exception.NoSuchInventoryWarehouseItemException;
import com.liferay.commerce.inventory.model.CIWarehouseItem;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.service.CommerceInventoryAuditLocalService;
import com.liferay.commerce.inventory.service.base.CommerceInventoryWarehouseItemLocalServiceBaseImpl;
import com.liferay.commerce.inventory.type.CommerceInventoryAuditType;
import com.liferay.commerce.inventory.type.CommerceInventoryAuditTypeRegistry;
import com.liferay.commerce.inventory.type.constants.CommerceInventoryAuditTypeConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luca Pellizzon
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "model.class.name=com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem",
	service = AopService.class
)
public class CommerceInventoryWarehouseItemLocalServiceImpl
	extends CommerceInventoryWarehouseItemLocalServiceBaseImpl {

	@Override
	public CommerceInventoryWarehouseItem addCommerceInventoryWarehouseItem(
			long userId, long commerceInventoryWarehouseId, String sku,
			int quantity)
		throws PortalException {

		return commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				StringPool.BLANK, userId, commerceInventoryWarehouseId, sku,
				quantity);
	}

	@Override
	public CommerceInventoryWarehouseItem addCommerceInventoryWarehouseItem(
			String externalReferenceCode, long userId,
			long commerceInventoryWarehouseId, String sku, int quantity)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		if (Validator.isNotNull(sku)) {
			_validate(commerceInventoryWarehouseId, sku);
		}

		long commerceInventoryWarehouseItemId = counterLocalService.increment();

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemPersistence.create(
				commerceInventoryWarehouseItemId);

		commerceInventoryWarehouseItem.setExternalReferenceCode(
			externalReferenceCode);
		commerceInventoryWarehouseItem.setCompanyId(user.getCompanyId());
		commerceInventoryWarehouseItem.setUserId(user.getUserId());
		commerceInventoryWarehouseItem.setUserName(user.getFullName());
		commerceInventoryWarehouseItem.setCommerceInventoryWarehouseId(
			commerceInventoryWarehouseId);
		commerceInventoryWarehouseItem.setSku(sku);
		commerceInventoryWarehouseItem.setQuantity(quantity);

		return commerceInventoryWarehouseItemPersistence.update(
			commerceInventoryWarehouseItem);
	}

	@Override
	public CommerceInventoryWarehouseItem
			addOrUpdateCommerceInventoryWarehouseItem(
				long userId, long commerceInventoryWarehouseId, String sku,
				int quantity)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemPersistence.fetchByC_S(
				commerceInventoryWarehouseId, sku);

		if (commerceInventoryWarehouseItem == null) {
			return commerceInventoryWarehouseItemLocalService.
				addCommerceInventoryWarehouseItem(
					userId, commerceInventoryWarehouseId, sku, quantity);
		}

		return commerceInventoryWarehouseItemLocalService.
			updateCommerceInventoryWarehouseItem(
				userId,
				commerceInventoryWarehouseItem.
					getCommerceInventoryWarehouseItemId(),
				quantity, commerceInventoryWarehouseItem.getMvccVersion());
	}

	@Override
	public CommerceInventoryWarehouseItem
			addOrUpdateCommerceInventoryWarehouseItem(
				String externalReferenceCode, long companyId, long userId,
				long commerceInventoryWarehouseId, String sku, int quantity)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}
		else {
			CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
				commerceInventoryWarehouseItemPersistence.fetchByERC_C(
					externalReferenceCode, companyId);

			if (commerceInventoryWarehouseItem != null) {
				return commerceInventoryWarehouseItemLocalService.
					updateCommerceInventoryWarehouseItem(
						userId,
						commerceInventoryWarehouseItem.
							getCommerceInventoryWarehouseItemId(),
						quantity,
						commerceInventoryWarehouseItem.getMvccVersion());
			}
		}

		return commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				externalReferenceCode, userId, commerceInventoryWarehouseId,
				sku, quantity);
	}

	@Override
	public int countItemsByCompanyId(long companyId, String sku) {
		return commerceInventoryWarehouseItemFinder.countItemsByCompanyId(
			companyId, sku);
	}

	@Override
	public void deleteCommerceInventoryWarehouseItems(
		long commerceInventoryWarehouseId) {

		commerceInventoryWarehouseItemPersistence.
			removeByCommerceInventoryWarehouseId(commerceInventoryWarehouseId);
	}

	@Override
	public void deleteCommerceInventoryWarehouseItems(
		long companyId, String sku) {

		commerceInventoryWarehouseItemPersistence.removeByCompanyId_Sku(
			companyId, sku);
	}

	@Override
	public void deleteCommerceInventoryWarehouseItemsByCompanyId(
		long companyId) {

		commerceInventoryWarehouseItemPersistence.removeByCompanyId(companyId);
	}

	@Override
	public CommerceInventoryWarehouseItem fetchCommerceInventoryWarehouseItem(
		long commerceInventoryWarehouseId, String sku) {

		return commerceInventoryWarehouseItemPersistence.fetchByC_S(
			commerceInventoryWarehouseId, sku);
	}

	@Override
	public CommerceInventoryWarehouseItem getCommerceInventoryWarehouseItem(
			long commerceInventoryWarehouseId, String sku)
		throws PortalException {

		return commerceInventoryWarehouseItemPersistence.findByC_S(
			commerceInventoryWarehouseId, sku);
	}

	@Override
	public CommerceInventoryWarehouseItem
			getCommerceInventoryWarehouseItemByReferenceCode(
				String externalReferenceCode, long companyId)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			throw new NoSuchInventoryWarehouseItemException();
		}

		return commerceInventoryWarehouseItemPersistence.findByERC_C(
			externalReferenceCode, companyId);
	}

	@Override
	public List<CommerceInventoryWarehouseItem>
		getCommerceInventoryWarehouseItems(
			long commerceInventoryWarehouseId, int start, int end) {

		return commerceInventoryWarehouseItemPersistence.
			findByCommerceInventoryWarehouseId(
				commerceInventoryWarehouseId, start, end);
	}

	@Override
	public List<CommerceInventoryWarehouseItem>
		getCommerceInventoryWarehouseItemsByCompanyId(
			long companyId, int start, int end) {

		return commerceInventoryWarehouseItemPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public List<CommerceInventoryWarehouseItem>
		getCommerceInventoryWarehouseItemsByCompanyIdAndSku(
			long companyId, String sku, int start, int end) {

		return commerceInventoryWarehouseItemPersistence.findByCompanyId_Sku(
			companyId, sku, start, end);
	}

	@Override
	public List<CommerceInventoryWarehouseItem>
		getCommerceInventoryWarehouseItemsByModifiedDate(
			long companyId, Date startDate, Date endDate, int start, int end) {

		return commerceInventoryWarehouseItemFinder.findUpdatedItemsByC_M(
			companyId, startDate, endDate, start, end);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCount(
		long commerceInventoryWarehouseId) {

		return commerceInventoryWarehouseItemPersistence.
			countByCommerceInventoryWarehouseId(commerceInventoryWarehouseId);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCount(
		long companyId, String sku) {

		return commerceInventoryWarehouseItemPersistence.countByCompanyId_Sku(
			companyId, sku);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCountByCompanyId(
		long companyId) {

		return commerceInventoryWarehouseItemPersistence.countByCompanyId(
			companyId);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCountByModifiedDate(
		long companyId, Date startDate, Date endDate) {

		return commerceInventoryWarehouseItemFinder.countUpdatedItemsByC_M(
			companyId, startDate, endDate);
	}

	@Override
	public List<CIWarehouseItem> getItemsByCompanyId(
		long companyId, String sku, int start, int end) {

		List<Object[]> objects =
			commerceInventoryWarehouseItemFinder.findItemsByCompanyId(
				companyId, sku, start, end);

		List<CIWarehouseItem> ciWarehouseItems = new ArrayList<>();

		for (Object[] object : objects) {
			if (object != null) {
				String skuCode = "";

				if ((object.length > 0) && (object[0] != null)) {
					skuCode = (String)object[0];
				}

				Integer stock = 0;

				if ((object.length > 1) && (object[1] != null)) {
					stock = (Integer)object[1];
				}

				Integer booked = 0;

				if ((object.length > 2) && (object[2] != null)) {
					booked = (Integer)object[2];
				}

				Integer replenishment = 0;

				if ((object.length > 3) && (object[3] != null)) {
					replenishment = (Integer)object[3];
				}

				ciWarehouseItems.add(
					new CIWarehouseItem(skuCode, stock, booked, replenishment));
			}
		}

		return ciWarehouseItems;
	}

	@Override
	public int getStockQuantity(long companyId, long groupId, String sku) {
		return commerceInventoryWarehouseItemFinder.countStockQuantityByC_G_S(
			companyId, groupId, sku);
	}

	@Override
	public int getStockQuantity(long companyId, String sku) {
		return commerceInventoryWarehouseItemFinder.countStockQuantityByC_S(
			companyId, sku);
	}

	@Override
	public CommerceInventoryWarehouseItem
			increaseCommerceInventoryWarehouseItemQuantity(
				long userId, long commerceInventoryWarehouseItemId,
				int quantity)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemPersistence.findByPrimaryKey(
				commerceInventoryWarehouseItemId);

		quantity = quantity + commerceInventoryWarehouseItem.getQuantity();

		commerceInventoryWarehouseItem.setQuantity(quantity);

		commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemPersistence.update(
				commerceInventoryWarehouseItem);

		CommerceInventoryAuditType commerceInventoryAuditType =
			_commerceInventoryAuditTypeRegistry.getCommerceInventoryAuditType(
				CommerceInventoryConstants.AUDIT_TYPE_INCREASE_QUANTITY);

		_commerceInventoryAuditLocalService.addCommerceInventoryAudit(
			userId, commerceInventoryWarehouseItem.getSku(),
			commerceInventoryAuditType.getType(),
			commerceInventoryAuditType.getLog(null), quantity);

		return commerceInventoryWarehouseItem;
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, readOnly = false,
		rollbackFor = Exception.class
	)
	public void moveQuantitiesBetweenWarehouses(
			long userId, long fromCommerceInventoryWarehouseId,
			long toCommerceInventoryWarehouseId, String sku, int quantity)
		throws PortalException {

		CommerceInventoryWarehouseItem fromWarehouseItem =
			commerceInventoryWarehouseItemPersistence.findByC_S(
				fromCommerceInventoryWarehouseId, sku);

		if (quantity > fromWarehouseItem.getQuantity()) {
			throw new PortalException("Quantity to transfer unavailable");
		}

		commerceInventoryWarehouseItemLocalService.
			updateCommerceInventoryWarehouseItem(
				userId, fromWarehouseItem.getCommerceInventoryWarehouseItemId(),
				fromWarehouseItem.getQuantity() - quantity,
				fromWarehouseItem.getMvccVersion());

		CommerceInventoryWarehouseItem toWarehouseItem =
			commerceInventoryWarehouseItemPersistence.findByC_S(
				toCommerceInventoryWarehouseId, sku);

		commerceInventoryWarehouseItemLocalService.
			updateCommerceInventoryWarehouseItem(
				userId, toWarehouseItem.getCommerceInventoryWarehouseItemId(),
				toWarehouseItem.getQuantity() + quantity,
				toWarehouseItem.getMvccVersion());

		CommerceInventoryAuditType commerceInventoryAuditType =
			_commerceInventoryAuditTypeRegistry.getCommerceInventoryAuditType(
				CommerceInventoryConstants.AUDIT_TYPE_MOVE_QUANTITY);

		_commerceInventoryAuditLocalService.addCommerceInventoryAudit(
			userId, sku, commerceInventoryAuditType.getType(),
			commerceInventoryAuditType.getLog(
				HashMapBuilder.put(
					CommerceInventoryAuditTypeConstants.FROM,
					() -> {
						CommerceInventoryWarehouse
							fromCommerceInventoryWarehouse =
								fromWarehouseItem.
									getCommerceInventoryWarehouse();

						return String.valueOf(
							fromCommerceInventoryWarehouse.getName());
					}
				).put(
					CommerceInventoryAuditTypeConstants.TO,
					() -> {
						CommerceInventoryWarehouse
							toCommerceInventoryWarehouse =
								toWarehouseItem.getCommerceInventoryWarehouse();

						return String.valueOf(
							toCommerceInventoryWarehouse.getName());
					}
				).build()),
			quantity);
	}

	@Override
	public CommerceInventoryWarehouseItem updateCommerceInventoryWarehouseItem(
			long userId, long commerceInventoryWarehouseItemId, int quantity,
			int reservedQuantity, long mvccVersion)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemPersistence.findByPrimaryKey(
				commerceInventoryWarehouseItemId);

		if (commerceInventoryWarehouseItem.getMvccVersion() != mvccVersion) {
			throw new MVCCException();
		}

		commerceInventoryWarehouseItem.setQuantity(quantity);
		commerceInventoryWarehouseItem.setReservedQuantity(reservedQuantity);

		commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemPersistence.update(
				commerceInventoryWarehouseItem);

		CommerceInventoryAuditType commerceInventoryAuditType =
			_commerceInventoryAuditTypeRegistry.getCommerceInventoryAuditType(
				CommerceInventoryConstants.AUDIT_TYPE_UPDATE_WAREHOUSE_ITEM);

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			commerceInventoryWarehouseItem.getCommerceInventoryWarehouse();

		_commerceInventoryAuditLocalService.addCommerceInventoryAudit(
			userId, commerceInventoryWarehouseItem.getSku(),
			commerceInventoryAuditType.getType(),
			commerceInventoryAuditType.getLog(
				HashMapBuilder.put(
					CommerceInventoryAuditTypeConstants.RESERVED,
					String.valueOf(reservedQuantity)
				).put(
					CommerceInventoryAuditTypeConstants.WAREHOUSE,
					String.valueOf(commerceInventoryWarehouse.getName())
				).build()),
			quantity);

		return commerceInventoryWarehouseItem;
	}

	@Override
	public CommerceInventoryWarehouseItem updateCommerceInventoryWarehouseItem(
			long userId, long commerceInventoryWarehouseItemId, int quantity,
			long mvccVersion)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemPersistence.findByPrimaryKey(
				commerceInventoryWarehouseItemId);

		if (commerceInventoryWarehouseItem.getMvccVersion() != mvccVersion) {
			throw new MVCCException();
		}

		commerceInventoryWarehouseItem.setQuantity(quantity);

		commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemPersistence.update(
				commerceInventoryWarehouseItem);

		CommerceInventoryAuditType commerceInventoryAuditType =
			_commerceInventoryAuditTypeRegistry.getCommerceInventoryAuditType(
				CommerceInventoryConstants.AUDIT_TYPE_UPDATE_WAREHOUSE_ITEM);

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			commerceInventoryWarehouseItem.getCommerceInventoryWarehouse();

		_commerceInventoryAuditLocalService.addCommerceInventoryAudit(
			userId, commerceInventoryWarehouseItem.getSku(),
			commerceInventoryAuditType.getType(),
			commerceInventoryAuditType.getLog(
				HashMapBuilder.put(
					CommerceInventoryAuditTypeConstants.WAREHOUSE,
					String.valueOf(commerceInventoryWarehouse.getName())
				).build()),
			quantity);

		return commerceInventoryWarehouseItem;
	}

	private void _validate(long commerceInventoryWarehouseId, String sku)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemPersistence.fetchByC_S(
				commerceInventoryWarehouseId, sku);

		if (commerceInventoryWarehouseItem != null) {
			throw new DuplicateCommerceInventoryWarehouseItemException();
		}
	}

	@Reference
	private CommerceInventoryAuditLocalService
		_commerceInventoryAuditLocalService;

	@Reference
	private CommerceInventoryAuditTypeRegistry
		_commerceInventoryAuditTypeRegistry;

	@Reference
	private UserLocalService _userLocalService;

}