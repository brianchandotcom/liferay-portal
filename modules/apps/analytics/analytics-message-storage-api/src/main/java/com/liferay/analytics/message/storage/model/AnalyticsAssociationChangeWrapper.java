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

package com.liferay.analytics.message.storage.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link AnalyticsAssociationChange}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsAssociationChange
 * @generated
 */
public class AnalyticsAssociationChangeWrapper
	extends BaseModelWrapper<AnalyticsAssociationChange>
	implements AnalyticsAssociationChange,
			   ModelWrapper<AnalyticsAssociationChange> {

	public AnalyticsAssociationChangeWrapper(
		AnalyticsAssociationChange analyticsAssociationChange) {

		super(analyticsAssociationChange);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"analyticsAssociationChangeId", getAnalyticsAssociationChangeId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("userId", getUserId());
		attributes.put("associationClassName", getAssociationClassName());
		attributes.put("associationClassPK", getAssociationClassPK());
		attributes.put("className", getClassName());
		attributes.put("classPK", getClassPK());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long analyticsAssociationChangeId = (Long)attributes.get(
			"analyticsAssociationChangeId");

		if (analyticsAssociationChangeId != null) {
			setAnalyticsAssociationChangeId(analyticsAssociationChangeId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String associationClassName = (String)attributes.get(
			"associationClassName");

		if (associationClassName != null) {
			setAssociationClassName(associationClassName);
		}

		Long associationClassPK = (Long)attributes.get("associationClassPK");

		if (associationClassPK != null) {
			setAssociationClassPK(associationClassPK);
		}

		String className = (String)attributes.get("className");

		if (className != null) {
			setClassName(className);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}
	}

	@Override
	public AnalyticsAssociationChange cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the analytics association change ID of this analytics association change.
	 *
	 * @return the analytics association change ID of this analytics association change
	 */
	@Override
	public long getAnalyticsAssociationChangeId() {
		return model.getAnalyticsAssociationChangeId();
	}

	/**
	 * Returns the association class name of this analytics association change.
	 *
	 * @return the association class name of this analytics association change
	 */
	@Override
	public String getAssociationClassName() {
		return model.getAssociationClassName();
	}

	/**
	 * Returns the association class pk of this analytics association change.
	 *
	 * @return the association class pk of this analytics association change
	 */
	@Override
	public long getAssociationClassPK() {
		return model.getAssociationClassPK();
	}

	/**
	 * Returns the class name of this analytics association change.
	 *
	 * @return the class name of this analytics association change
	 */
	@Override
	public String getClassName() {
		return model.getClassName();
	}

	/**
	 * Returns the class pk of this analytics association change.
	 *
	 * @return the class pk of this analytics association change
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this analytics association change.
	 *
	 * @return the company ID of this analytics association change
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this analytics association change.
	 *
	 * @return the create date of this analytics association change
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the modified date of this analytics association change.
	 *
	 * @return the modified date of this analytics association change
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the mvcc version of this analytics association change.
	 *
	 * @return the mvcc version of this analytics association change
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this analytics association change.
	 *
	 * @return the primary key of this analytics association change
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this analytics association change.
	 *
	 * @return the user ID of this analytics association change
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this analytics association change.
	 *
	 * @return the user uuid of this analytics association change
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
	 * Sets the analytics association change ID of this analytics association change.
	 *
	 * @param analyticsAssociationChangeId the analytics association change ID of this analytics association change
	 */
	@Override
	public void setAnalyticsAssociationChangeId(
		long analyticsAssociationChangeId) {

		model.setAnalyticsAssociationChangeId(analyticsAssociationChangeId);
	}

	/**
	 * Sets the association class name of this analytics association change.
	 *
	 * @param associationClassName the association class name of this analytics association change
	 */
	@Override
	public void setAssociationClassName(String associationClassName) {
		model.setAssociationClassName(associationClassName);
	}

	/**
	 * Sets the association class pk of this analytics association change.
	 *
	 * @param associationClassPK the association class pk of this analytics association change
	 */
	@Override
	public void setAssociationClassPK(long associationClassPK) {
		model.setAssociationClassPK(associationClassPK);
	}

	/**
	 * Sets the class name of this analytics association change.
	 *
	 * @param className the class name of this analytics association change
	 */
	@Override
	public void setClassName(String className) {
		model.setClassName(className);
	}

	/**
	 * Sets the class pk of this analytics association change.
	 *
	 * @param classPK the class pk of this analytics association change
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this analytics association change.
	 *
	 * @param companyId the company ID of this analytics association change
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this analytics association change.
	 *
	 * @param createDate the create date of this analytics association change
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the modified date of this analytics association change.
	 *
	 * @param modifiedDate the modified date of this analytics association change
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the mvcc version of this analytics association change.
	 *
	 * @param mvccVersion the mvcc version of this analytics association change
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this analytics association change.
	 *
	 * @param primaryKey the primary key of this analytics association change
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this analytics association change.
	 *
	 * @param userId the user ID of this analytics association change
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this analytics association change.
	 *
	 * @param userUuid the user uuid of this analytics association change
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected AnalyticsAssociationChangeWrapper wrap(
		AnalyticsAssociationChange analyticsAssociationChange) {

		return new AnalyticsAssociationChangeWrapper(
			analyticsAssociationChange);
	}

}