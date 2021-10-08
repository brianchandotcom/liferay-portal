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

package com.liferay.commerce.order.rule.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link COREntryRel}.
 * </p>
 *
 * @author Luca Pellizzon
 * @see COREntryRel
 * @generated
 */
public class COREntryRelWrapper
	extends BaseModelWrapper<COREntryRel>
	implements COREntryRel, ModelWrapper<COREntryRel> {

	public COREntryRelWrapper(COREntryRel corEntryRel) {
		super(corEntryRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("COREntryRelId", getCOREntryRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("classNameId", getClassNameId());
		attributes.put("classPK", getClassPK());
		attributes.put("COREntryId", getCOREntryId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long COREntryRelId = (Long)attributes.get("COREntryRelId");

		if (COREntryRelId != null) {
			setCOREntryRelId(COREntryRelId);
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

		Long COREntryId = (Long)attributes.get("COREntryId");

		if (COREntryId != null) {
			setCOREntryId(COREntryId);
		}
	}

	@Override
	public COREntryRel cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the fully qualified class name of this cor entry rel.
	 *
	 * @return the fully qualified class name of this cor entry rel
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class name ID of this cor entry rel.
	 *
	 * @return the class name ID of this cor entry rel
	 */
	@Override
	public long getClassNameId() {
		return model.getClassNameId();
	}

	/**
	 * Returns the class pk of this cor entry rel.
	 *
	 * @return the class pk of this cor entry rel
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this cor entry rel.
	 *
	 * @return the company ID of this cor entry rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	@Override
	public COREntry getCOREntry()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getCOREntry();
	}

	/**
	 * Returns the cor entry ID of this cor entry rel.
	 *
	 * @return the cor entry ID of this cor entry rel
	 */
	@Override
	public long getCOREntryId() {
		return model.getCOREntryId();
	}

	/**
	 * Returns the cor entry rel ID of this cor entry rel.
	 *
	 * @return the cor entry rel ID of this cor entry rel
	 */
	@Override
	public long getCOREntryRelId() {
		return model.getCOREntryRelId();
	}

	/**
	 * Returns the create date of this cor entry rel.
	 *
	 * @return the create date of this cor entry rel
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this cor entry rel.
	 *
	 * @return the modified date of this cor entry rel
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this cor entry rel.
	 *
	 * @return the primary key of this cor entry rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this cor entry rel.
	 *
	 * @return the user ID of this cor entry rel
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this cor entry rel.
	 *
	 * @return the user name of this cor entry rel
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this cor entry rel.
	 *
	 * @return the user uuid of this cor entry rel
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
	 * Sets the class name ID of this cor entry rel.
	 *
	 * @param classNameId the class name ID of this cor entry rel
	 */
	@Override
	public void setClassNameId(long classNameId) {
		model.setClassNameId(classNameId);
	}

	/**
	 * Sets the class pk of this cor entry rel.
	 *
	 * @param classPK the class pk of this cor entry rel
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this cor entry rel.
	 *
	 * @param companyId the company ID of this cor entry rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the cor entry ID of this cor entry rel.
	 *
	 * @param COREntryId the cor entry ID of this cor entry rel
	 */
	@Override
	public void setCOREntryId(long COREntryId) {
		model.setCOREntryId(COREntryId);
	}

	/**
	 * Sets the cor entry rel ID of this cor entry rel.
	 *
	 * @param COREntryRelId the cor entry rel ID of this cor entry rel
	 */
	@Override
	public void setCOREntryRelId(long COREntryRelId) {
		model.setCOREntryRelId(COREntryRelId);
	}

	/**
	 * Sets the create date of this cor entry rel.
	 *
	 * @param createDate the create date of this cor entry rel
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this cor entry rel.
	 *
	 * @param modifiedDate the modified date of this cor entry rel
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this cor entry rel.
	 *
	 * @param primaryKey the primary key of this cor entry rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this cor entry rel.
	 *
	 * @param userId the user ID of this cor entry rel
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this cor entry rel.
	 *
	 * @param userName the user name of this cor entry rel
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this cor entry rel.
	 *
	 * @param userUuid the user uuid of this cor entry rel
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected COREntryRelWrapper wrap(COREntryRel corEntryRel) {
		return new COREntryRelWrapper(corEntryRel);
	}

}