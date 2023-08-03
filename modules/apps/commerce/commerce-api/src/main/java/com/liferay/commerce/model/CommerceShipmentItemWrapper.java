/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.math.BigDecimal;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CommerceShipmentItem}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShipmentItem
 * @generated
 */
public class CommerceShipmentItemWrapper
	extends BaseModelWrapper<CommerceShipmentItem>
	implements CommerceShipmentItem, ModelWrapper<CommerceShipmentItem> {

	public CommerceShipmentItemWrapper(
		CommerceShipmentItem commerceShipmentItem) {

		super(commerceShipmentItem);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("commerceShipmentItemId", getCommerceShipmentItemId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commerceShipmentId", getCommerceShipmentId());
		attributes.put("commerceOrderItemId", getCommerceOrderItemId());
		attributes.put(
			"commerceInventoryWarehouseId", getCommerceInventoryWarehouseId());
		attributes.put("quantity", getQuantity());
		attributes.put("unitOfMeasureKey", getUnitOfMeasureKey());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long commerceShipmentItemId = (Long)attributes.get(
			"commerceShipmentItemId");

		if (commerceShipmentItemId != null) {
			setCommerceShipmentItemId(commerceShipmentItemId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long commerceShipmentId = (Long)attributes.get("commerceShipmentId");

		if (commerceShipmentId != null) {
			setCommerceShipmentId(commerceShipmentId);
		}

		Long commerceOrderItemId = (Long)attributes.get("commerceOrderItemId");

		if (commerceOrderItemId != null) {
			setCommerceOrderItemId(commerceOrderItemId);
		}

		Long commerceInventoryWarehouseId = (Long)attributes.get(
			"commerceInventoryWarehouseId");

		if (commerceInventoryWarehouseId != null) {
			setCommerceInventoryWarehouseId(commerceInventoryWarehouseId);
		}

		BigDecimal quantity = (BigDecimal)attributes.get("quantity");

		if (quantity != null) {
			setQuantity(quantity);
		}

		String unitOfMeasureKey = (String)attributes.get("unitOfMeasureKey");

		if (unitOfMeasureKey != null) {
			setUnitOfMeasureKey(unitOfMeasureKey);
		}
	}

	@Override
	public CommerceShipmentItem cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public CommerceOrderItem fetchCommerceOrderItem() {
		return model.fetchCommerceOrderItem();
	}

	/**
	 * Returns the commerce inventory warehouse ID of this commerce shipment item.
	 *
	 * @return the commerce inventory warehouse ID of this commerce shipment item
	 */
	@Override
	public long getCommerceInventoryWarehouseId() {
		return model.getCommerceInventoryWarehouseId();
	}

	/**
	 * Returns the commerce order item ID of this commerce shipment item.
	 *
	 * @return the commerce order item ID of this commerce shipment item
	 */
	@Override
	public long getCommerceOrderItemId() {
		return model.getCommerceOrderItemId();
	}

	@Override
	public CommerceShipment getCommerceShipment()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceShipment();
	}

	/**
	 * Returns the commerce shipment ID of this commerce shipment item.
	 *
	 * @return the commerce shipment ID of this commerce shipment item
	 */
	@Override
	public long getCommerceShipmentId() {
		return model.getCommerceShipmentId();
	}

	/**
	 * Returns the commerce shipment item ID of this commerce shipment item.
	 *
	 * @return the commerce shipment item ID of this commerce shipment item
	 */
	@Override
	public long getCommerceShipmentItemId() {
		return model.getCommerceShipmentItemId();
	}

	/**
	 * Returns the company ID of this commerce shipment item.
	 *
	 * @return the company ID of this commerce shipment item
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce shipment item.
	 *
	 * @return the create date of this commerce shipment item
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the external reference code of this commerce shipment item.
	 *
	 * @return the external reference code of this commerce shipment item
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this commerce shipment item.
	 *
	 * @return the group ID of this commerce shipment item
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this commerce shipment item.
	 *
	 * @return the modified date of this commerce shipment item
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce shipment item.
	 *
	 * @return the mvcc version of this commerce shipment item
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce shipment item.
	 *
	 * @return the primary key of this commerce shipment item
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the quantity of this commerce shipment item.
	 *
	 * @return the quantity of this commerce shipment item
	 */
	@Override
	public BigDecimal getQuantity() {
		return model.getQuantity();
	}

	/**
	 * Returns the unit of measure key of this commerce shipment item.
	 *
	 * @return the unit of measure key of this commerce shipment item
	 */
	@Override
	public String getUnitOfMeasureKey() {
		return model.getUnitOfMeasureKey();
	}

	/**
	 * Returns the user ID of this commerce shipment item.
	 *
	 * @return the user ID of this commerce shipment item
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce shipment item.
	 *
	 * @return the user name of this commerce shipment item
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce shipment item.
	 *
	 * @return the user uuid of this commerce shipment item
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce shipment item.
	 *
	 * @return the uuid of this commerce shipment item
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the commerce inventory warehouse ID of this commerce shipment item.
	 *
	 * @param commerceInventoryWarehouseId the commerce inventory warehouse ID of this commerce shipment item
	 */
	@Override
	public void setCommerceInventoryWarehouseId(
		long commerceInventoryWarehouseId) {

		model.setCommerceInventoryWarehouseId(commerceInventoryWarehouseId);
	}

	/**
	 * Sets the commerce order item ID of this commerce shipment item.
	 *
	 * @param commerceOrderItemId the commerce order item ID of this commerce shipment item
	 */
	@Override
	public void setCommerceOrderItemId(long commerceOrderItemId) {
		model.setCommerceOrderItemId(commerceOrderItemId);
	}

	/**
	 * Sets the commerce shipment ID of this commerce shipment item.
	 *
	 * @param commerceShipmentId the commerce shipment ID of this commerce shipment item
	 */
	@Override
	public void setCommerceShipmentId(long commerceShipmentId) {
		model.setCommerceShipmentId(commerceShipmentId);
	}

	/**
	 * Sets the commerce shipment item ID of this commerce shipment item.
	 *
	 * @param commerceShipmentItemId the commerce shipment item ID of this commerce shipment item
	 */
	@Override
	public void setCommerceShipmentItemId(long commerceShipmentItemId) {
		model.setCommerceShipmentItemId(commerceShipmentItemId);
	}

	/**
	 * Sets the company ID of this commerce shipment item.
	 *
	 * @param companyId the company ID of this commerce shipment item
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce shipment item.
	 *
	 * @param createDate the create date of this commerce shipment item
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the external reference code of this commerce shipment item.
	 *
	 * @param externalReferenceCode the external reference code of this commerce shipment item
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this commerce shipment item.
	 *
	 * @param groupId the group ID of this commerce shipment item
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this commerce shipment item.
	 *
	 * @param modifiedDate the modified date of this commerce shipment item
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce shipment item.
	 *
	 * @param mvccVersion the mvcc version of this commerce shipment item
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce shipment item.
	 *
	 * @param primaryKey the primary key of this commerce shipment item
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the quantity of this commerce shipment item.
	 *
	 * @param quantity the quantity of this commerce shipment item
	 */
	@Override
	public void setQuantity(BigDecimal quantity) {
		model.setQuantity(quantity);
	}

	/**
	 * Sets the unit of measure key of this commerce shipment item.
	 *
	 * @param unitOfMeasureKey the unit of measure key of this commerce shipment item
	 */
	@Override
	public void setUnitOfMeasureKey(String unitOfMeasureKey) {
		model.setUnitOfMeasureKey(unitOfMeasureKey);
	}

	/**
	 * Sets the user ID of this commerce shipment item.
	 *
	 * @param userId the user ID of this commerce shipment item
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce shipment item.
	 *
	 * @param userName the user name of this commerce shipment item
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce shipment item.
	 *
	 * @param userUuid the user uuid of this commerce shipment item
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce shipment item.
	 *
	 * @param uuid the uuid of this commerce shipment item
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CommerceShipmentItemWrapper wrap(
		CommerceShipmentItem commerceShipmentItem) {

		return new CommerceShipmentItemWrapper(commerceShipmentItem);
	}

}