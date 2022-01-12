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

package com.liferay.commerce.price.list.model;

import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CommercePriceListOrderTypeRel}.
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommercePriceListOrderTypeRel
 * @generated
 */
public class CommercePriceListOrderTypeRelWrapper
	extends BaseModelWrapper<CommercePriceListOrderTypeRel>
	implements CommercePriceListOrderTypeRel,
			   ModelWrapper<CommercePriceListOrderTypeRel> {

	public CommercePriceListOrderTypeRelWrapper(
		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel) {

		super(commercePriceListOrderTypeRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("uuid", getUuid());
		attributes.put(
			"commercePriceListOrderTypeRelId",
			getCommercePriceListOrderTypeRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commercePriceListId", getCommercePriceListId());
		attributes.put("commerceOrderTypeId", getCommerceOrderTypeId());
		attributes.put("priority", getPriority());
		attributes.put("lastPublishDate", getLastPublishDate());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long commercePriceListOrderTypeRelId = (Long)attributes.get(
			"commercePriceListOrderTypeRelId");

		if (commercePriceListOrderTypeRelId != null) {
			setCommercePriceListOrderTypeRelId(commercePriceListOrderTypeRelId);
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

		Long commercePriceListId = (Long)attributes.get("commercePriceListId");

		if (commercePriceListId != null) {
			setCommercePriceListId(commercePriceListId);
		}

		Long commerceOrderTypeId = (Long)attributes.get("commerceOrderTypeId");

		if (commerceOrderTypeId != null) {
			setCommerceOrderTypeId(commerceOrderTypeId);
		}

		Integer priority = (Integer)attributes.get("priority");

		if (priority != null) {
			setPriority(priority);
		}

		Date lastPublishDate = (Date)attributes.get("lastPublishDate");

		if (lastPublishDate != null) {
			setLastPublishDate(lastPublishDate);
		}
	}

	@Override
	public CommercePriceListOrderTypeRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the commerce order type ID of this commerce price list order type rel.
	 *
	 * @return the commerce order type ID of this commerce price list order type rel
	 */
	@Override
	public long getCommerceOrderTypeId() {
		return model.getCommerceOrderTypeId();
	}

	@Override
	public CommercePriceList getCommercePriceList()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommercePriceList();
	}

	/**
	 * Returns the commerce price list ID of this commerce price list order type rel.
	 *
	 * @return the commerce price list ID of this commerce price list order type rel
	 */
	@Override
	public long getCommercePriceListId() {
		return model.getCommercePriceListId();
	}

	/**
	 * Returns the commerce price list order type rel ID of this commerce price list order type rel.
	 *
	 * @return the commerce price list order type rel ID of this commerce price list order type rel
	 */
	@Override
	public long getCommercePriceListOrderTypeRelId() {
		return model.getCommercePriceListOrderTypeRelId();
	}

	/**
	 * Returns the company ID of this commerce price list order type rel.
	 *
	 * @return the company ID of this commerce price list order type rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce price list order type rel.
	 *
	 * @return the create date of this commerce price list order type rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this commerce price list order type rel.
	 *
	 * @return the ct collection ID of this commerce price list order type rel
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the last publish date of this commerce price list order type rel.
	 *
	 * @return the last publish date of this commerce price list order type rel
	 */
	@Override
	public Date getLastPublishDate() {
		return model.getLastPublishDate();
	}

	/**
	 * Returns the modified date of this commerce price list order type rel.
	 *
	 * @return the modified date of this commerce price list order type rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce price list order type rel.
	 *
	 * @return the mvcc version of this commerce price list order type rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce price list order type rel.
	 *
	 * @return the primary key of this commerce price list order type rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the priority of this commerce price list order type rel.
	 *
	 * @return the priority of this commerce price list order type rel
	 */
	@Override
	public int getPriority() {
		return model.getPriority();
	}

	/**
	 * Returns the user ID of this commerce price list order type rel.
	 *
	 * @return the user ID of this commerce price list order type rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce price list order type rel.
	 *
	 * @return the user name of this commerce price list order type rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce price list order type rel.
	 *
	 * @return the user uuid of this commerce price list order type rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns the uuid of this commerce price list order type rel.
	 *
	 * @return the uuid of this commerce price list order type rel
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
	 * Sets the commerce order type ID of this commerce price list order type rel.
	 *
	 * @param commerceOrderTypeId the commerce order type ID of this commerce price list order type rel
	 */
	@Override
	public void setCommerceOrderTypeId(long commerceOrderTypeId) {
		model.setCommerceOrderTypeId(commerceOrderTypeId);
	}

	/**
	 * Sets the commerce price list ID of this commerce price list order type rel.
	 *
	 * @param commercePriceListId the commerce price list ID of this commerce price list order type rel
	 */
	@Override
	public void setCommercePriceListId(long commercePriceListId) {
		model.setCommercePriceListId(commercePriceListId);
	}

	/**
	 * Sets the commerce price list order type rel ID of this commerce price list order type rel.
	 *
	 * @param commercePriceListOrderTypeRelId the commerce price list order type rel ID of this commerce price list order type rel
	 */
	@Override
	public void setCommercePriceListOrderTypeRelId(
		long commercePriceListOrderTypeRelId) {

		model.setCommercePriceListOrderTypeRelId(
			commercePriceListOrderTypeRelId);
	}

	/**
	 * Sets the company ID of this commerce price list order type rel.
	 *
	 * @param companyId the company ID of this commerce price list order type rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce price list order type rel.
	 *
	 * @param createDate the create date of this commerce price list order type rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this commerce price list order type rel.
	 *
	 * @param ctCollectionId the ct collection ID of this commerce price list order type rel
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the last publish date of this commerce price list order type rel.
	 *
	 * @param lastPublishDate the last publish date of this commerce price list order type rel
	 */
	@Override
	public void setLastPublishDate(Date lastPublishDate) {
		model.setLastPublishDate(lastPublishDate);
	}

	/**
	 * Sets the modified date of this commerce price list order type rel.
	 *
	 * @param modifiedDate the modified date of this commerce price list order type rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce price list order type rel.
	 *
	 * @param mvccVersion the mvcc version of this commerce price list order type rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce price list order type rel.
	 *
	 * @param primaryKey the primary key of this commerce price list order type rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the priority of this commerce price list order type rel.
	 *
	 * @param priority the priority of this commerce price list order type rel
	 */
	@Override
	public void setPriority(int priority) {
		model.setPriority(priority);
	}

	/**
	 * Sets the user ID of this commerce price list order type rel.
	 *
	 * @param userId the user ID of this commerce price list order type rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce price list order type rel.
	 *
	 * @param userName the user name of this commerce price list order type rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce price list order type rel.
	 *
	 * @param userUuid the user uuid of this commerce price list order type rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this commerce price list order type rel.
	 *
	 * @param uuid the uuid of this commerce price list order type rel
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	public Map<String, Function<CommercePriceListOrderTypeRel, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CommercePriceListOrderTypeRel, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	public StagedModelType getStagedModelType() {
		return model.getStagedModelType();
	}

	@Override
	protected CommercePriceListOrderTypeRelWrapper wrap(
		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel) {

		return new CommercePriceListOrderTypeRelWrapper(
			commercePriceListOrderTypeRel);
	}

}