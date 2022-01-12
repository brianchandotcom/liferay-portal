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

package com.liferay.commerce.pricing.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CommercePricingClassCPDefinitionRel}.
 * </p>
 *
 * @author Riccardo Alberti
 * @see CommercePricingClassCPDefinitionRel
 * @generated
 */
public class CommercePricingClassCPDefinitionRelWrapper
	extends BaseModelWrapper<CommercePricingClassCPDefinitionRel>
	implements CommercePricingClassCPDefinitionRel,
			   ModelWrapper<CommercePricingClassCPDefinitionRel> {

	public CommercePricingClassCPDefinitionRelWrapper(
		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel) {

		super(commercePricingClassCPDefinitionRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put(
			"CommercePricingClassCPDefinitionRelId",
			getCommercePricingClassCPDefinitionRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commercePricingClassId", getCommercePricingClassId());
		attributes.put("CPDefinitionId", getCPDefinitionId());

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

		Long CommercePricingClassCPDefinitionRelId = (Long)attributes.get(
			"CommercePricingClassCPDefinitionRelId");

		if (CommercePricingClassCPDefinitionRelId != null) {
			setCommercePricingClassCPDefinitionRelId(
				CommercePricingClassCPDefinitionRelId);
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

		Long commercePricingClassId = (Long)attributes.get(
			"commercePricingClassId");

		if (commercePricingClassId != null) {
			setCommercePricingClassId(commercePricingClassId);
		}

		Long CPDefinitionId = (Long)attributes.get("CPDefinitionId");

		if (CPDefinitionId != null) {
			setCPDefinitionId(CPDefinitionId);
		}
	}

	@Override
	public CommercePricingClassCPDefinitionRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public CommercePricingClass getCommercePricingClass()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommercePricingClass();
	}

	/**
	 * Returns the commerce pricing class cp definition rel ID of this commerce pricing class cp definition rel.
	 *
	 * @return the commerce pricing class cp definition rel ID of this commerce pricing class cp definition rel
	 */
	@Override
	public long getCommercePricingClassCPDefinitionRelId() {
		return model.getCommercePricingClassCPDefinitionRelId();
	}

	/**
	 * Returns the commerce pricing class ID of this commerce pricing class cp definition rel.
	 *
	 * @return the commerce pricing class ID of this commerce pricing class cp definition rel
	 */
	@Override
	public long getCommercePricingClassId() {
		return model.getCommercePricingClassId();
	}

	/**
	 * Returns the company ID of this commerce pricing class cp definition rel.
	 *
	 * @return the company ID of this commerce pricing class cp definition rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the cp definition ID of this commerce pricing class cp definition rel.
	 *
	 * @return the cp definition ID of this commerce pricing class cp definition rel
	 */
	@Override
	public long getCPDefinitionId() {
		return model.getCPDefinitionId();
	}

	/**
	 * Returns the create date of this commerce pricing class cp definition rel.
	 *
	 * @return the create date of this commerce pricing class cp definition rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this commerce pricing class cp definition rel.
	 *
	 * @return the ct collection ID of this commerce pricing class cp definition rel
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the modified date of this commerce pricing class cp definition rel.
	 *
	 * @return the modified date of this commerce pricing class cp definition rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce pricing class cp definition rel.
	 *
	 * @return the mvcc version of this commerce pricing class cp definition rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce pricing class cp definition rel.
	 *
	 * @return the primary key of this commerce pricing class cp definition rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce pricing class cp definition rel.
	 *
	 * @return the user ID of this commerce pricing class cp definition rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce pricing class cp definition rel.
	 *
	 * @return the user name of this commerce pricing class cp definition rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce pricing class cp definition rel.
	 *
	 * @return the user uuid of this commerce pricing class cp definition rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the commerce pricing class cp definition rel ID of this commerce pricing class cp definition rel.
	 *
	 * @param CommercePricingClassCPDefinitionRelId the commerce pricing class cp definition rel ID of this commerce pricing class cp definition rel
	 */
	@Override
	public void setCommercePricingClassCPDefinitionRelId(
		long CommercePricingClassCPDefinitionRelId) {

		model.setCommercePricingClassCPDefinitionRelId(
			CommercePricingClassCPDefinitionRelId);
	}

	/**
	 * Sets the commerce pricing class ID of this commerce pricing class cp definition rel.
	 *
	 * @param commercePricingClassId the commerce pricing class ID of this commerce pricing class cp definition rel
	 */
	@Override
	public void setCommercePricingClassId(long commercePricingClassId) {
		model.setCommercePricingClassId(commercePricingClassId);
	}

	/**
	 * Sets the company ID of this commerce pricing class cp definition rel.
	 *
	 * @param companyId the company ID of this commerce pricing class cp definition rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cp definition ID of this commerce pricing class cp definition rel.
	 *
	 * @param CPDefinitionId the cp definition ID of this commerce pricing class cp definition rel
	 */
	@Override
	public void setCPDefinitionId(long CPDefinitionId) {
		model.setCPDefinitionId(CPDefinitionId);
	}

	/**
	 * Sets the create date of this commerce pricing class cp definition rel.
	 *
	 * @param createDate the create date of this commerce pricing class cp definition rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this commerce pricing class cp definition rel.
	 *
	 * @param ctCollectionId the ct collection ID of this commerce pricing class cp definition rel
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the modified date of this commerce pricing class cp definition rel.
	 *
	 * @param modifiedDate the modified date of this commerce pricing class cp definition rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce pricing class cp definition rel.
	 *
	 * @param mvccVersion the mvcc version of this commerce pricing class cp definition rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce pricing class cp definition rel.
	 *
	 * @param primaryKey the primary key of this commerce pricing class cp definition rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce pricing class cp definition rel.
	 *
	 * @param userId the user ID of this commerce pricing class cp definition rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce pricing class cp definition rel.
	 *
	 * @param userName the user name of this commerce pricing class cp definition rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce pricing class cp definition rel.
	 *
	 * @param userUuid the user uuid of this commerce pricing class cp definition rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public Map<String, Function<CommercePricingClassCPDefinitionRel, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CommercePricingClassCPDefinitionRel, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected CommercePricingClassCPDefinitionRelWrapper wrap(
		CommercePricingClassCPDefinitionRel
			commercePricingClassCPDefinitionRel) {

		return new CommercePricingClassCPDefinitionRelWrapper(
			commercePricingClassCPDefinitionRel);
	}

}