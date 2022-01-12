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

package com.liferay.commerce.product.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link CommerceChannelRel}.
 * </p>
 *
 * @author Marco Leo
 * @see CommerceChannelRel
 * @generated
 */
public class CommerceChannelRelWrapper
	extends BaseModelWrapper<CommerceChannelRel>
	implements CommerceChannelRel, ModelWrapper<CommerceChannelRel> {

	public CommerceChannelRelWrapper(CommerceChannelRel commerceChannelRel) {
		super(commerceChannelRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("commerceChannelRelId", getCommerceChannelRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("commerceChannelId", getCommerceChannelId());

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

		Long commerceChannelRelId = (Long)attributes.get(
			"commerceChannelRelId");

		if (commerceChannelRelId != null) {
			setCommerceChannelRelId(commerceChannelRelId);
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

		Long classNameId = (Long)attributes.get("classNameId");

		if (classNameId != null) {
			setClassNameId(classNameId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}

		Long commerceChannelId = (Long)attributes.get("commerceChannelId");

		if (commerceChannelId != null) {
			setCommerceChannelId(commerceChannelId);
		}
	}

	@Override
	public CommerceChannelRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this commerce channel rel.
	 *
	 * @return the fully qualified class name of this commerce channel rel
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this commerce channel rel.
	 *
	 * @return the class name ID of this commerce channel rel
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this commerce channel rel.
	 *
	 * @return the class pk of this commerce channel rel
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	@Override
	public CommerceChannel getCommerceChannel()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCommerceChannel();
	}

	/**
	 * Returns the commerce channel ID of this commerce channel rel.
	 *
	 * @return the commerce channel ID of this commerce channel rel
	 */
	@Override
	public long getCommerceChannelId() {
		return model.getCommerceChannelId();
	}

	/**
	 * Returns the commerce channel rel ID of this commerce channel rel.
	 *
	 * @return the commerce channel rel ID of this commerce channel rel
	 */
	@Override
	public long getCommerceChannelRelId() {
		return model.getCommerceChannelRelId();
	}

	/**
	 * Returns the company ID of this commerce channel rel.
	 *
	 * @return the company ID of this commerce channel rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this commerce channel rel.
	 *
	 * @return the create date of this commerce channel rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct collection ID of this commerce channel rel.
	 *
	 * @return the ct collection ID of this commerce channel rel
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the modified date of this commerce channel rel.
	 *
	 * @return the modified date of this commerce channel rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this commerce channel rel.
	 *
	 * @return the mvcc version of this commerce channel rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this commerce channel rel.
	 *
	 * @return the primary key of this commerce channel rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this commerce channel rel.
	 *
	 * @return the user ID of this commerce channel rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this commerce channel rel.
	 *
	 * @return the user name of this commerce channel rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this commerce channel rel.
	 *
	 * @return the user uuid of this commerce channel rel
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
	 * Sets the class name ID of this commerce channel rel.
	 *
	 * @param classNameId the class name ID of this commerce channel rel
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this commerce channel rel.
	 *
	 * @param classPK the class pk of this commerce channel rel
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the commerce channel ID of this commerce channel rel.
	 *
	 * @param commerceChannelId the commerce channel ID of this commerce channel rel
	 */
	@Override
	public void setCommerceChannelId(long commerceChannelId) {
		model.setCommerceChannelId(commerceChannelId);
	}

	/**
	 * Sets the commerce channel rel ID of this commerce channel rel.
	 *
	 * @param commerceChannelRelId the commerce channel rel ID of this commerce channel rel
	 */
	@Override
	public void setCommerceChannelRelId(long commerceChannelRelId) {
		model.setCommerceChannelRelId(commerceChannelRelId);
	}

	/**
	 * Sets the company ID of this commerce channel rel.
	 *
	 * @param companyId the company ID of this commerce channel rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this commerce channel rel.
	 *
	 * @param createDate the create date of this commerce channel rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct collection ID of this commerce channel rel.
	 *
	 * @param ctCollectionId the ct collection ID of this commerce channel rel
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the modified date of this commerce channel rel.
	 *
	 * @param modifiedDate the modified date of this commerce channel rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this commerce channel rel.
	 *
	 * @param mvccVersion the mvcc version of this commerce channel rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this commerce channel rel.
	 *
	 * @param primaryKey the primary key of this commerce channel rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this commerce channel rel.
	 *
	 * @param userId the user ID of this commerce channel rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this commerce channel rel.
	 *
	 * @param userName the user name of this commerce channel rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this commerce channel rel.
	 *
	 * @param userUuid the user uuid of this commerce channel rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public Map<String, Function<CommerceChannelRel, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<CommerceChannelRel, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected CommerceChannelRelWrapper wrap(
		CommerceChannelRel commerceChannelRel) {

		return new CommerceChannelRelWrapper(commerceChannelRel);
	}

}