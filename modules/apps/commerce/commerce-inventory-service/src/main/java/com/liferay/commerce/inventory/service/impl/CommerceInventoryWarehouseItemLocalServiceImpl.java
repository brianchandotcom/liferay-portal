/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.inventory.service.impl;

import com.liferay.commerce.inventory.constants.CommerceInventoryConstants;
import com.liferay.commerce.inventory.exception.CommerceInventoryWarehouseItemSkuException;
import com.liferay.commerce.inventory.exception.DuplicateCommerceInventoryWarehouseItemException;
import com.liferay.commerce.inventory.exception.MVCCException;
import com.liferay.commerce.inventory.model.CIWarehouseItem;
import com.liferay.commerce.inventory.model.CommerceInventoryBookedQuantityTable;
import com.liferay.commerce.inventory.model.CommerceInventoryReplenishmentItemTable;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouse;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItem;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseItemTable;
import com.liferay.commerce.inventory.model.CommerceInventoryWarehouseTable;
import com.liferay.commerce.inventory.service.CommerceInventoryAuditLocalService;
import com.liferay.commerce.inventory.service.base.CommerceInventoryWarehouseItemLocalServiceBaseImpl;
import com.liferay.commerce.inventory.type.CommerceInventoryAuditType;
import com.liferay.commerce.inventory.type.CommerceInventoryAuditTypeRegistry;
import com.liferay.commerce.inventory.type.constants.CommerceInventoryAuditTypeConstants;
import com.liferay.commerce.product.exception.CPInstanceUnitOfMeasureKeyException;
import com.liferay.commerce.product.exception.NoSuchCPInstanceUnitOfMeasureException;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceUnitOfMeasure;
import com.liferay.commerce.product.model.CommerceChannel;
import com.liferay.commerce.product.model.CommerceChannelRelTable;
import com.liferay.commerce.product.service.CPInstanceLocalService;
import com.liferay.commerce.product.service.CPInstanceUnitOfMeasureLocalService;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.base.BaseTable;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.petra.sql.dsl.spi.expression.Scalar;
import com.liferay.petra.sql.dsl.spi.query.QueryTable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.GroupTable;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
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
			String externalReferenceCode, long userId,
			long commerceInventoryWarehouseId, BigDecimal quantity, String sku,
			String unitOfMeasureKey)
		throws PortalException {

		User user = _userLocalService.getUser(userId);

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		_validateSku(commerceInventoryWarehouseId, sku);

		_validateUnitOfMeasureKey(user.getCompanyId(), sku, unitOfMeasureKey);

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
		commerceInventoryWarehouseItem.setQuantity(quantity);
		commerceInventoryWarehouseItem.setReservedQuantity(BigDecimal.ZERO);
		commerceInventoryWarehouseItem.setSku(sku);
		commerceInventoryWarehouseItem.setUnitOfMeasureKey(
			_normalizeUnitOfMeasureKey(
				user.getCompanyId(), sku, unitOfMeasureKey));

		return commerceInventoryWarehouseItemPersistence.update(
			commerceInventoryWarehouseItem);
	}

	@Override
	public CommerceInventoryWarehouseItem
			addOrUpdateCommerceInventoryWarehouseItem(
				String externalReferenceCode, long companyId, long userId,
				long commerceInventoryWarehouseId, BigDecimal quantity,
				String sku, String unitOfMeasureKey)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem = null;

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
			commerceInventoryWarehouseItem =
				commerceInventoryWarehouseItemPersistence.fetchByC_S(
					commerceInventoryWarehouseId, sku);
		}
		else {
			commerceInventoryWarehouseItem =
				commerceInventoryWarehouseItemPersistence.fetchByERC_C(
					externalReferenceCode, companyId);
		}

		if (commerceInventoryWarehouseItem != null) {
			return commerceInventoryWarehouseItemLocalService.
				updateCommerceInventoryWarehouseItem(
					userId,
					commerceInventoryWarehouseItem.
						getCommerceInventoryWarehouseItemId(),
					commerceInventoryWarehouseItem.getMvccVersion(), quantity,
					unitOfMeasureKey);
		}

		return commerceInventoryWarehouseItemLocalService.
			addCommerceInventoryWarehouseItem(
				externalReferenceCode, userId, commerceInventoryWarehouseId,
				quantity, sku, unitOfMeasureKey);
	}

	@Override
	public int countItemsByCompanyId(long companyId, String sku) {
		return dslQueryCount(
			DSLQueryFactoryUtil.countDistinct(
				CommerceInventoryWarehouseItemTable.INSTANCE.sku
			).from(
				CommerceInventoryWarehouseItemTable.INSTANCE
			).where(
				CommerceInventoryWarehouseItemTable.INSTANCE.companyId.eq(
					companyId
				).and(
					() -> {
						if (Validator.isNull(sku)) {
							return null;
						}

						return DSLFunctionFactoryUtil.lower(
							CommerceInventoryWarehouseItemTable.INSTANCE.sku
						).like(
							StringPool.PERCENT + StringUtil.toLowerCase(sku) +
								StringPool.PERCENT
						);
					}
				)
			));
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

		return dslQuery(
			DSLQueryFactoryUtil.select(
				CommerceInventoryWarehouseItemTable.INSTANCE
			).from(
				CommerceInventoryWarehouseItemTable.INSTANCE
			).where(
				CommerceInventoryWarehouseItemTable.INSTANCE.companyId.eq(
					companyId
				).and(
					CommerceInventoryWarehouseItemTable.INSTANCE.modifiedDate.
						gte(startDate)
				).and(
					CommerceInventoryWarehouseItemTable.INSTANCE.modifiedDate.
						lt(endDate)
				)
			).orderBy(
				CommerceInventoryWarehouseItemTable.INSTANCE.sku.ascending(),
				CommerceInventoryWarehouseItemTable.INSTANCE.unitOfMeasureKey.
					ascending()
			));
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCount(
		long commerceInventoryWarehouseId) {

		return commerceInventoryWarehouseItemPersistence.
			countByCommerceInventoryWarehouseId(commerceInventoryWarehouseId);
	}

	@Override
	public int getCommerceInventoryWarehouseItemsCount(
		long companyId, long groupId, String sku) {

		return dslQueryCount(
			DSLQueryFactoryUtil.countDistinct(
				CommerceInventoryWarehouseItemTable.INSTANCE.
					commerceInventoryWarehouseItemId
			).from(
				CommerceInventoryWarehouseItemTable.INSTANCE
			).innerJoinON(
				CommerceChannelRelTable.INSTANCE,
				CommerceChannelRelTable.INSTANCE.classNameId.eq(
					_portal.getClassNameId(
						CommerceInventoryWarehouse.class.getName())
				).and(
					CommerceChannelRelTable.INSTANCE.classPK.eq(
						CommerceInventoryWarehouseItemTable.INSTANCE.
							commerceInventoryWarehouseId)
				)
			).innerJoinON(
				GroupTable.INSTANCE,
				GroupTable.INSTANCE.classNameId.eq(
					_portal.getClassNameId(CommerceChannel.class.getName())
				).and(
					GroupTable.INSTANCE.classPK.eq(
						CommerceChannelRelTable.INSTANCE.commerceChannelId)
				)
			).innerJoinON(
				CommerceInventoryWarehouseTable.INSTANCE,
				CommerceInventoryWarehouseTable.INSTANCE.
					commerceInventoryWarehouseId.eq(
						CommerceInventoryWarehouseItemTable.INSTANCE.
							commerceInventoryWarehouseId)
			).where(
				CommerceInventoryWarehouseItemTable.INSTANCE.companyId.eq(
					companyId
				).and(
					CommerceInventoryWarehouseItemTable.INSTANCE.sku.eq(sku)
				).and(
					CommerceInventoryWarehouseTable.INSTANCE.active.eq(true)
				).and(
					GroupTable.INSTANCE.groupId.eq(groupId)
				)
			));
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

		return dslQueryCount(
			DSLQueryFactoryUtil.countDistinct(
				CommerceInventoryWarehouseItemTable.INSTANCE.
					commerceInventoryWarehouseItemId
			).from(
				CommerceInventoryWarehouseItemTable.INSTANCE
			).where(
				CommerceInventoryWarehouseItemTable.INSTANCE.companyId.eq(
					companyId
				).and(
					CommerceInventoryWarehouseItemTable.INSTANCE.modifiedDate.
						gte(
							startDate
						).and(
							CommerceInventoryWarehouseItemTable.INSTANCE.
								modifiedDate.lt(endDate)
						)
				)
			));
	}

	@Override
	public List<CIWarehouseItem> getItemsByCompanyId(
		long companyId, String sku, int start, int end) {

		List<Object[]> sumStocks = dslQuery(
			DSLQueryFactoryUtil.select(
				CommerceInventoryWarehouseItemTable.INSTANCE.sku,
				DSLFunctionFactoryUtil.sum(
					CommerceInventoryWarehouseItemTable.INSTANCE.quantity
				).as(
					"SUM_STOCK"
				),
				DSLFunctionFactoryUtil.min(
					BookedQuantityTable.INSTANCE.sumBookedColumn
				).as(
					BookedQuantityTable.INSTANCE.sumBookedColumn.getName()
				),
				DSLFunctionFactoryUtil.min(
					ReplenishmentQuantityTable.INSTANCE.sumAwaitingColumn
				).as(
					ReplenishmentQuantityTable.INSTANCE.sumAwaitingColumn.
						getName()
				)
			).from(
				CommerceInventoryWarehouseItemTable.INSTANCE
			).leftJoinOn(
				BookedQuantityTable.INSTANCE.getQueryTable(companyId),
				CommerceInventoryWarehouseItemTable.INSTANCE.sku.eq(
					BookedQuantityTable.INSTANCE.skuColumn)
			).leftJoinOn(
				ReplenishmentQuantityTable.INSTANCE.getQueryTable(companyId),
				CommerceInventoryWarehouseItemTable.INSTANCE.sku.eq(
					ReplenishmentQuantityTable.INSTANCE.skuColumn)
			).where(
				CommerceInventoryWarehouseItemTable.INSTANCE.companyId.eq(
					companyId
				).and(
					() -> {
						if (Validator.isNull(sku)) {
							return null;
						}

						return DSLFunctionFactoryUtil.lower(
							CommerceInventoryWarehouseItemTable.INSTANCE.sku
						).like(
							StringPool.PERCENT + StringUtil.toLowerCase(sku) +
								StringPool.PERCENT
						);
					}
				)
			).groupBy(
				CommerceInventoryWarehouseItemTable.INSTANCE.sku,
				CommerceInventoryWarehouseItemTable.INSTANCE.unitOfMeasureKey
			).orderBy(
				CommerceInventoryWarehouseItemTable.INSTANCE.sku.ascending(),
				CommerceInventoryWarehouseItemTable.INSTANCE.unitOfMeasureKey.
					ascending()
			).limit(
				start, end
			));

		List<CIWarehouseItem> ciWarehouseItems = new ArrayList<>();

		for (Object[] stock : sumStocks) {
			if (stock != null) {
				String skuCode = StringPool.BLANK;

				if ((stock.length > 0) && (stock[0] != null)) {
					skuCode = (String)stock[0];
				}

				BigDecimal stockQuantity = BigDecimal.ZERO;

				if ((stock.length > 1) && (stock[1] != null)) {
					stockQuantity = (BigDecimal)stock[1];
				}

				Integer bookedQuantity = 0;

				if ((stock.length > 2) && (stock[2] != null)) {
					bookedQuantity = (Integer)stock[2];
				}

				Integer replenishmentQuantity = 0;

				if ((stock.length > 3) && (stock[3] != null)) {
					replenishmentQuantity = (Integer)stock[3];
				}

				ciWarehouseItems.add(
					new CIWarehouseItem(
						skuCode, stockQuantity.intValue(), bookedQuantity,
						replenishmentQuantity));
			}
		}

		return ciWarehouseItems;
	}

	@Override
	public BigDecimal getStockQuantity(
		long companyId, long groupId, String sku) {

		Iterable<BigDecimal> iterable = dslQuery(
			DSLQueryFactoryUtil.select(
				DSLFunctionFactoryUtil.sum(
					DSLFunctionFactoryUtil.subtract(
						(Expression<Number>)_getExpression(
							CommerceInventoryWarehouseItemTable.INSTANCE.
								quantity),
						(Expression<Number>)_getExpression(
							CommerceInventoryWarehouseItemTable.INSTANCE.
								reservedQuantity))
				).as(
					"SUM_VALUE"
				)
			).from(
				CommerceInventoryWarehouseItemTable.INSTANCE
			).innerJoinON(
				CommerceChannelRelTable.INSTANCE,
				CommerceChannelRelTable.INSTANCE.classNameId.eq(
					_portal.getClassNameId(
						CommerceInventoryWarehouse.class.getName())
				).and(
					CommerceChannelRelTable.INSTANCE.classPK.eq(
						CommerceInventoryWarehouseItemTable.INSTANCE.
							commerceInventoryWarehouseId)
				)
			).innerJoinON(
				GroupTable.INSTANCE,
				GroupTable.INSTANCE.classNameId.eq(
					_portal.getClassNameId(CommerceChannel.class.getName())
				).and(
					GroupTable.INSTANCE.classPK.eq(
						CommerceChannelRelTable.INSTANCE.commerceChannelId)
				)
			).innerJoinON(
				CommerceInventoryWarehouseTable.INSTANCE,
				CommerceInventoryWarehouseTable.INSTANCE.
					commerceInventoryWarehouseId.eq(
						CommerceInventoryWarehouseItemTable.INSTANCE.
							commerceInventoryWarehouseId)
			).where(
				CommerceInventoryWarehouseItemTable.INSTANCE.companyId.eq(
					companyId
				).and(
					CommerceInventoryWarehouseItemTable.INSTANCE.sku.eq(sku)
				).and(
					CommerceInventoryWarehouseTable.INSTANCE.active.eq(true)
				).and(
					GroupTable.INSTANCE.groupId.eq(groupId)
				)
			));

		Iterator<BigDecimal> iterator = iterable.iterator();

		BigDecimal stockQuantity = iterator.next();

		if (stockQuantity == null) {
			return BigDecimal.ZERO;
		}

		return stockQuantity;
	}

	@Override
	public BigDecimal getStockQuantity(long companyId, String sku) {
		Iterable<BigDecimal> iterable = dslQuery(
			DSLQueryFactoryUtil.select(
				DSLFunctionFactoryUtil.sum(
					DSLFunctionFactoryUtil.subtract(
						(Expression<Number>)_getExpression(
							CommerceInventoryWarehouseItemTable.INSTANCE.
								quantity),
						(Expression<Number>)_getExpression(
							CommerceInventoryWarehouseItemTable.INSTANCE.
								reservedQuantity))
				).as(
					"SUM_VALUE"
				)
			).from(
				CommerceInventoryWarehouseItemTable.INSTANCE
			).innerJoinON(
				CommerceInventoryWarehouseTable.INSTANCE,
				CommerceInventoryWarehouseTable.INSTANCE.
					commerceInventoryWarehouseId.eq(
						CommerceInventoryWarehouseItemTable.INSTANCE.
							commerceInventoryWarehouseId)
			).where(
				CommerceInventoryWarehouseItemTable.INSTANCE.companyId.eq(
					companyId
				).and(
					CommerceInventoryWarehouseItemTable.INSTANCE.sku.eq(sku)
				).and(
					CommerceInventoryWarehouseTable.INSTANCE.active.eq(true)
				)
			));

		Iterator<BigDecimal> iterator = iterable.iterator();

		BigDecimal stockQuantity = iterator.next();

		if (stockQuantity == null) {
			return BigDecimal.ZERO;
		}

		return stockQuantity;
	}

	@Override
	public CommerceInventoryWarehouseItem
			increaseCommerceInventoryWarehouseItemQuantity(
				long userId, long commerceInventoryWarehouseItemId,
				BigDecimal quantity)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemPersistence.findByPrimaryKey(
				commerceInventoryWarehouseItemId);

		quantity = quantity.add(commerceInventoryWarehouseItem.getQuantity());

		commerceInventoryWarehouseItem.setQuantity(quantity);

		commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemPersistence.update(
				commerceInventoryWarehouseItem);

		CommerceInventoryAuditType commerceInventoryAuditType =
			_commerceInventoryAuditTypeRegistry.getCommerceInventoryAuditType(
				CommerceInventoryConstants.AUDIT_TYPE_INCREASE_QUANTITY);

		_commerceInventoryAuditLocalService.addCommerceInventoryAudit(
			userId, commerceInventoryAuditType.getType(),
			commerceInventoryAuditType.getLog(null), quantity,
			commerceInventoryWarehouseItem.getSku(),
			commerceInventoryWarehouseItem.getUnitOfMeasureKey());

		return commerceInventoryWarehouseItem;
	}

	@Override
	@Transactional(
		propagation = Propagation.REQUIRED, readOnly = false,
		rollbackFor = Exception.class
	)
	public void moveQuantitiesBetweenWarehouses(
			long userId, long fromCommerceInventoryWarehouseId,
			long toCommerceInventoryWarehouseId, BigDecimal quantity,
			String sku)
		throws PortalException {

		CommerceInventoryWarehouseItem fromWarehouseItem =
			commerceInventoryWarehouseItemPersistence.findByC_S(
				fromCommerceInventoryWarehouseId, sku);

		BigDecimal fromWarehouseItemQuantity = fromWarehouseItem.getQuantity();

		if (quantity.compareTo(fromWarehouseItemQuantity) == 1) {
			throw new PortalException("Quantity to transfer unavailable");
		}

		commerceInventoryWarehouseItemLocalService.
			updateCommerceInventoryWarehouseItem(
				userId, fromWarehouseItem.getCommerceInventoryWarehouseItemId(),
				fromWarehouseItem.getMvccVersion(),
				fromWarehouseItemQuantity.subtract(quantity),
				fromWarehouseItem.getUnitOfMeasureKey());

		CommerceInventoryWarehouseItem toWarehouseItem =
			commerceInventoryWarehouseItemPersistence.findByC_S(
				toCommerceInventoryWarehouseId, sku);

		BigDecimal toWarehouseItemQuantity = toWarehouseItem.getQuantity();

		commerceInventoryWarehouseItemLocalService.
			updateCommerceInventoryWarehouseItem(
				userId, toWarehouseItem.getCommerceInventoryWarehouseItemId(),
				toWarehouseItem.getMvccVersion(),
				toWarehouseItemQuantity.add(quantity),
				toWarehouseItem.getUnitOfMeasureKey());

		CommerceInventoryAuditType commerceInventoryAuditType =
			_commerceInventoryAuditTypeRegistry.getCommerceInventoryAuditType(
				CommerceInventoryConstants.AUDIT_TYPE_MOVE_QUANTITY);

		_commerceInventoryAuditLocalService.addCommerceInventoryAudit(
			userId, commerceInventoryAuditType.getType(),
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
			quantity, sku, StringPool.BLANK);
	}

	@Override
	public CommerceInventoryWarehouseItem updateCommerceInventoryWarehouseItem(
			long userId, long commerceInventoryWarehouseItemId,
			BigDecimal quantity, BigDecimal reservedQuantity, long mvccVersion)
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
			userId, commerceInventoryAuditType.getType(),
			commerceInventoryAuditType.getLog(
				HashMapBuilder.put(
					CommerceInventoryAuditTypeConstants.RESERVED,
					String.valueOf(reservedQuantity)
				).put(
					CommerceInventoryAuditTypeConstants.WAREHOUSE,
					String.valueOf(commerceInventoryWarehouse.getName())
				).build()),
			quantity, commerceInventoryWarehouseItem.getSku(),
			commerceInventoryWarehouseItem.getUnitOfMeasureKey());

		return commerceInventoryWarehouseItem;
	}

	@Override
	public CommerceInventoryWarehouseItem updateCommerceInventoryWarehouseItem(
			long userId, long commerceInventoryWarehouseItemId,
			long mvccVersion, BigDecimal quantity, String unitOfMeasureKey)
		throws PortalException {

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemPersistence.findByPrimaryKey(
				commerceInventoryWarehouseItemId);

		if (commerceInventoryWarehouseItem.getMvccVersion() != mvccVersion) {
			throw new MVCCException();
		}

		_validateUnitOfMeasureKey(
			commerceInventoryWarehouseItem.getCompanyId(),
			commerceInventoryWarehouseItem.getSku(), unitOfMeasureKey);

		commerceInventoryWarehouseItem.setQuantity(quantity);
		commerceInventoryWarehouseItem.setUnitOfMeasureKey(
			_normalizeUnitOfMeasureKey(
				commerceInventoryWarehouseItem.getCompanyId(),
				commerceInventoryWarehouseItem.getSku(), unitOfMeasureKey));

		commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemPersistence.update(
				commerceInventoryWarehouseItem);

		CommerceInventoryAuditType commerceInventoryAuditType =
			_commerceInventoryAuditTypeRegistry.getCommerceInventoryAuditType(
				CommerceInventoryConstants.AUDIT_TYPE_UPDATE_WAREHOUSE_ITEM);

		CommerceInventoryWarehouse commerceInventoryWarehouse =
			commerceInventoryWarehouseItem.getCommerceInventoryWarehouse();

		_commerceInventoryAuditLocalService.addCommerceInventoryAudit(
			userId, commerceInventoryAuditType.getType(),
			commerceInventoryAuditType.getLog(
				HashMapBuilder.put(
					CommerceInventoryAuditTypeConstants.WAREHOUSE,
					String.valueOf(commerceInventoryWarehouse.getName())
				).build()),
			quantity, commerceInventoryWarehouseItem.getSku(),
			commerceInventoryWarehouseItem.getUnitOfMeasureKey());

		return commerceInventoryWarehouseItem;
	}

	private BigDecimal _getBigDecimal(Comparable<?> comparable) {
		if (comparable == null) {
			return BigDecimal.ZERO;
		}

		if (comparable instanceof BigDecimal) {
			return (BigDecimal)comparable;
		}

		String value = comparable.toString();

		if (Validator.isNull(value)) {
			return BigDecimal.ZERO;
		}

		return new BigDecimal(value);
	}

	private Expression<?> _getExpression(Object object) {
		if (object instanceof BigDecimal) {
			object = _getBigDecimal((Comparable<?>)object);
		}
		else if (object instanceof Expression) {
			return (Expression<?>)object;
		}

		return new Scalar<>(object);
	}

	private String _normalizeUnitOfMeasureKey(
			long companyId, String sku, String unitOfMeasureKey)
		throws PortalException {

		List<CPInstanceUnitOfMeasure> cpInstanceUnitOfMeasures =
			_cpInstanceUnitOfMeasureLocalService.fetchCPInstanceUnitOfMeasures(
				companyId, sku);

		if (Validator.isNull(unitOfMeasureKey) &&
			(cpInstanceUnitOfMeasures.size() == 1)) {

			CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure =
				cpInstanceUnitOfMeasures.get(0);

			return cpInstanceUnitOfMeasure.getKey();
		}

		return unitOfMeasureKey;
	}

	private void _validateSku(long commerceInventoryWarehouseId, String sku)
		throws PortalException {

		if (Validator.isNull(sku)) {
			throw new CommerceInventoryWarehouseItemSkuException();
		}

		CommerceInventoryWarehouseItem commerceInventoryWarehouseItem =
			commerceInventoryWarehouseItemPersistence.fetchByC_S(
				commerceInventoryWarehouseId, sku);

		if (commerceInventoryWarehouseItem != null) {
			throw new DuplicateCommerceInventoryWarehouseItemException();
		}
	}

	private void _validateUnitOfMeasureKey(
			long companyId, String sku, String unitOfMeasureKey)
		throws PortalException {

		int cpInstanceUnitOfMeasuresCount =
			_cpInstanceUnitOfMeasureLocalService.
				getCPInstanceUnitOfMeasuresCount(companyId, sku);

		if (Validator.isNull(unitOfMeasureKey)) {
			if (cpInstanceUnitOfMeasuresCount == 1) {
				return;
			}

			if (cpInstanceUnitOfMeasuresCount > 0) {
				throw new CPInstanceUnitOfMeasureKeyException(
					"You must specify the Unit of Measure in order to set " +
						"the inventory for this SKU");
			}
		}
		else {
			if (cpInstanceUnitOfMeasuresCount == 0) {
				List<CPInstance> cpInstances =
					_cpInstanceLocalService.getCPInstances(companyId, sku);

				if (cpInstances.isEmpty()) {
					return;
				}

				throw new NoSuchCPInstanceUnitOfMeasureException(
					"No commerce product instance unit of measure exists " +
						"with the primary key " + unitOfMeasureKey);
			}

			List<CPInstanceUnitOfMeasure> cpInstanceUnitOfMeasures =
				_cpInstanceUnitOfMeasureLocalService.
					fetchCPInstanceUnitOfMeasures(companyId, sku);

			for (CPInstanceUnitOfMeasure cpInstanceUnitOfMeasure :
					cpInstanceUnitOfMeasures) {

				if (unitOfMeasureKey.equals(cpInstanceUnitOfMeasure.getKey())) {
					return;
				}
			}

			throw new NoSuchCPInstanceUnitOfMeasureException(
				"No commerce product instance unit of measure exists with " +
					"the primary key " + unitOfMeasureKey);
		}
	}

	@Reference
	private CommerceInventoryAuditLocalService
		_commerceInventoryAuditLocalService;

	@Reference
	private CommerceInventoryAuditTypeRegistry
		_commerceInventoryAuditTypeRegistry;

	@Reference
	private CPInstanceLocalService _cpInstanceLocalService;

	@Reference
	private CPInstanceUnitOfMeasureLocalService
		_cpInstanceUnitOfMeasureLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private UserLocalService _userLocalService;

	private static class BookedQuantityTable
		extends BaseTable<BookedQuantityTable> {

		public static final BookedQuantityTable INSTANCE =
			new BookedQuantityTable();

		public QueryTable getQueryTable(long companyId) {
			return new QueryTable(
				BookedQuantityTable.INSTANCE.getTableName(),
				DSLQueryFactoryUtil.select(
					CommerceInventoryBookedQuantityTable.INSTANCE.sku.as(
						skuColumn.getName()),
					DSLFunctionFactoryUtil.sum(
						CommerceInventoryBookedQuantityTable.INSTANCE.quantity
					).as(
						sumBookedColumn.getName()
					)
				).from(
					CommerceInventoryBookedQuantityTable.INSTANCE
				).where(
					CommerceInventoryBookedQuantityTable.INSTANCE.companyId.eq(
						companyId)
				).groupBy(
					CommerceInventoryBookedQuantityTable.INSTANCE.sku,
					CommerceInventoryBookedQuantityTable.INSTANCE.
						unitOfMeasureKey
				),
				Arrays.asList(skuColumn, sumBookedColumn));
		}

		public final Column<BookedQuantityTable, String> skuColumn =
			createColumn(
				"SKU", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
		public final Column<BookedQuantityTable, Integer> sumBookedColumn =
			createColumn(
				"SUM_BOOKED", Integer.class, Types.INTEGER,
				Column.FLAG_DEFAULT);

		private BookedQuantityTable() {
			super("BookedQuantityTable", BookedQuantityTable::new);
		}

	}

	private static class ReplenishmentQuantityTable
		extends BaseTable<ReplenishmentQuantityTable> {

		public static final ReplenishmentQuantityTable INSTANCE =
			new ReplenishmentQuantityTable();

		public QueryTable getQueryTable(long companyId) {
			return new QueryTable(
				ReplenishmentQuantityTable.INSTANCE.getTableName(),
				DSLQueryFactoryUtil.select(
					CommerceInventoryReplenishmentItemTable.INSTANCE.sku.as(
						skuColumn.getName()),
					DSLFunctionFactoryUtil.sum(
						CommerceInventoryReplenishmentItemTable.INSTANCE.
							quantity
					).as(
						sumAwaitingColumn.getName()
					)
				).from(
					CommerceInventoryReplenishmentItemTable.INSTANCE
				).where(
					CommerceInventoryReplenishmentItemTable.INSTANCE.companyId.
						eq(companyId)
				).groupBy(
					CommerceInventoryReplenishmentItemTable.INSTANCE.sku,
					CommerceInventoryReplenishmentItemTable.INSTANCE.
						unitOfMeasureKey
				),
				Arrays.asList(skuColumn, sumAwaitingColumn));
		}

		public final Column<ReplenishmentQuantityTable, String> skuColumn =
			createColumn(
				"SKU", String.class, Types.VARCHAR, Column.FLAG_DEFAULT);
		public final Column<ReplenishmentQuantityTable, Integer>
			sumAwaitingColumn = createColumn(
				"SUM_AWAITING", Integer.class, Types.INTEGER,
				Column.FLAG_DEFAULT);

		private ReplenishmentQuantityTable() {
			super(
				"ReplenishmentQuantityTable", ReplenishmentQuantityTable::new);
		}

	}

}