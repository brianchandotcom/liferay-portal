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
 * This class is a wrapper for {@link CommercePriceModifierRel}.
 * </p>
 *
 * @author Riccardo Alberti
 * @see CommercePriceModifierRel
 * @generated
 */
public class CommercePriceModifierRelWrapper
	extends BaseModelWrapper<CommercePriceModifierRel>
	implements CommercePriceModifierRel,
			   ModelWrapper<CommercePriceModifierRel> {

	public CommercePriceModifierRelWrapper(
		CommercePriceModifierRel commercePriceModifierRel) {

		super(commercePriceModifierRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put(
			"commercePriceModifierRelId", getCommercePriceModifierRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("commercePriceModifierId", getCommercePriceModifierId());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());

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

		Long commercePriceModifierRelId = (Long)attributes.get(
			"commercePriceModifierRelId");

		if (commercePriceModifierRelId != null) {
			setCommercePriceModifierRelId(commercePriceModifierRelId);
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

		Long commercePriceModifierId = (Long)attributes.get(
			"commercePriceModifierId");

		if (commercePriceModifierId != null) {
			setCommercePriceModifierId(commercePriceModifierId);
		}

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}
	}

	@Override
	public CommercePriceModifierRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this commerce price modifier rel.
	 *
	 * @return the fully qualified class name of this commerce price modifier rel
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this commerce price modifier rel.
	 *
	 * @return the class name ID of this commerce price modifier rel
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this commerce price modifier rel.
	 *
	 * @return the class pk of this commerce price modifier rel
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	@Override
	public CommercePriceModifier getCommercePriceModifier()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommercePriceModifier();
	}

	/**
	 * Returns the commerce price modifier ID of this commerce price modifier rel.
	 *
	 * @return the commerce price modifier ID of this commerce price modifier rel
	 */
	@Override
	public long getCommercePriceModifierId() {
		return model.getCommercePriceModifierId();
	}

	/**
	 * Returns the commerce price modifier rel ID of this commerce price modifier rel.
	 *
	 * @return the commerce price modifier rel ID of this commerce price modifier rel
	 */
	@Override
	public long getCommercePriceModifierRelId() {
		return model.getCommercePriceModifierRelId();
	}

	/**
	 * Returns the company ID of this commerce price modifier rel.
	 *
	 * @return the company ID of this commerce price modifier rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce price modifier rel.
	 *
	 * @return the create date of this commerce price modifier rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this commerce price modifier rel.
	 *
	 * @return the ct collection ID of this commerce price modifier rel
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the modified date of this commerce price modifier rel.
	 *
	 * @return the modified date of this commerce price modifier rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce price modifier rel.
	 *
	 * @return the mvcc version of this commerce price modifier rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce price modifier rel.
	 *
	 * @return the primary key of this commerce price modifier rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce price modifier rel.
	 *
	 * @return the user ID of this commerce price modifier rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce price modifier rel.
	 *
	 * @return the user name of this commerce price modifier rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce price modifier rel.
	 *
	 * @return the user uuid of this commerce price modifier rel
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class name ID of this commerce price modifier rel.
	 *
	 * @param classNameId the class name ID of this commerce price modifier rel
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this commerce price modifier rel.
	 *
	 * @param classPK the class pk of this commerce price modifier rel
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the commerce price modifier ID of this commerce price modifier rel.
	 *
	 * @param commercePriceModifierId the commerce price modifier ID of this commerce price modifier rel
	 */
	@Override
	public void setCommercePriceModifierId(long commercePriceModifierId) {
		model.setCommercePriceModifierId(commercePriceModifierId);
	}

	/**
	 * Sets the commerce price modifier rel ID of this commerce price modifier rel.
	 *
	 * @param commercePriceModifierRelId the commerce price modifier rel ID of this commerce price modifier rel
	 */
	@Override
	public void setCommercePriceModifierRelId(long commercePriceModifierRelId) {
		model.setCommercePriceModifierRelId(commercePriceModifierRelId);
	}

	/**
	 * Sets the company ID of this commerce price modifier rel.
	 *
	 * @param companyId the company ID of this commerce price modifier rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce price modifier rel.
	 *
	 * @param createDate the create date of this commerce price modifier rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this commerce price modifier rel.
	 *
	 * @param ctCollectionId the ct collection ID of this commerce price modifier rel
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the modified date of this commerce price modifier rel.
	 *
	 * @param modifiedDate the modified date of this commerce price modifier rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce price modifier rel.
	 *
	 * @param mvccVersion the mvcc version of this commerce price modifier rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce price modifier rel.
	 *
	 * @param primaryKey the primary key of this commerce price modifier rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce price modifier rel.
	 *
	 * @param userId the user ID of this commerce price modifier rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce price modifier rel.
	 *
	 * @param userName the user name of this commerce price modifier rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce price modifier rel.
	 *
	 * @param userUuid the user uuid of this commerce price modifier rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public Map<String, Function<CommercePriceModifierRel, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CommercePriceModifierRel, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected CommercePriceModifierRelWrapper wrap(
		CommercePriceModifierRel commercePriceModifierRel) {

		return new CommercePriceModifierRelWrapper(commercePriceModifierRel);
	}

}