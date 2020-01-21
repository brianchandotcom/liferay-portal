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

package com.liferay.change.tracking.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CTAutoResolutionInfo}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTAutoResolutionInfo
 * @generated
 */
public class CTAutoResolutionInfoWrapper
	extends BaseModelWrapper<CTAutoResolutionInfo>
	implements CTAutoResolutionInfo, ModelWrapper<CTAutoResolutionInfo> {

	public CTAutoResolutionInfoWrapper(
		CTAutoResolutionInfo ctAutoResolutionInfo) {

		super(ctAutoResolutionInfo);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctAutoResolutionInfoId", getCtAutoResolutionInfoId());
		attributes.put("companyId", getCompanyId());
		attributes.put("createDate", getCreateDate());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("modelClassNameId", getModelClassNameId());
		attributes.put("sourceModelClassPK", getSourceModelClassPK());
		attributes.put("targetModelClassPK", getTargetModelClassPK());
		attributes.put("conflictIdentifier", getConflictIdentifier());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctAutoResolutionInfoId = (Long)attributes.get(
			"ctAutoResolutionInfoId");

		if (ctAutoResolutionInfoId != null) {
			setCtAutoResolutionInfoId(ctAutoResolutionInfoId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		Long modelClassNameId = (Long)attributes.get("modelClassNameId");

		if (modelClassNameId != null) {
			setModelClassNameId(modelClassNameId);
		}

		Long sourceModelClassPK = (Long)attributes.get("sourceModelClassPK");

		if (sourceModelClassPK != null) {
			setSourceModelClassPK(sourceModelClassPK);
		}

		Long targetModelClassPK = (Long)attributes.get("targetModelClassPK");

		if (targetModelClassPK != null) {
			setTargetModelClassPK(targetModelClassPK);
		}

		String conflictIdentifier = (String)attributes.get(
			"conflictIdentifier");

		if (conflictIdentifier != null) {
			setConflictIdentifier(conflictIdentifier);
		}
	}

	/**
	 * Returns the company ID of this ct auto resolution info.
	 *
	 * @return the company ID of this ct auto resolution info
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the conflict identifier of this ct auto resolution info.
	 *
	 * @return the conflict identifier of this ct auto resolution info
	 */
	@Override
	public String getConflictIdentifier() {
		return model.getConflictIdentifier();
	}

	/**
	 * Returns the create date of this ct auto resolution info.
	 *
	 * @return the create date of this ct auto resolution info
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the ct auto resolution info ID of this ct auto resolution info.
	 *
	 * @return the ct auto resolution info ID of this ct auto resolution info
	 */
	@Override
	public long getCtAutoResolutionInfoId() {
		return model.getCtAutoResolutionInfoId();
	}

	/**
	 * Returns the ct collection ID of this ct auto resolution info.
	 *
	 * @return the ct collection ID of this ct auto resolution info
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the model class name ID of this ct auto resolution info.
	 *
	 * @return the model class name ID of this ct auto resolution info
	 */
	@Override
	public long getModelClassNameId() {
		return model.getModelClassNameId();
	}

	/**
	 * Returns the mvcc version of this ct auto resolution info.
	 *
	 * @return the mvcc version of this ct auto resolution info
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this ct auto resolution info.
	 *
	 * @return the primary key of this ct auto resolution info
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the source model class pk of this ct auto resolution info.
	 *
	 * @return the source model class pk of this ct auto resolution info
	 */
	@Override
	public long getSourceModelClassPK() {
		return model.getSourceModelClassPK();
	}

	/**
	 * Returns the target model class pk of this ct auto resolution info.
	 *
	 * @return the target model class pk of this ct auto resolution info
	 */
	@Override
	public long getTargetModelClassPK() {
		return model.getTargetModelClassPK();
	}

	/**
	 * Sets the company ID of this ct auto resolution info.
	 *
	 * @param companyId the company ID of this ct auto resolution info
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the conflict identifier of this ct auto resolution info.
	 *
	 * @param conflictIdentifier the conflict identifier of this ct auto resolution info
	 */
	@Override
	public void setConflictIdentifier(String conflictIdentifier) {
		model.setConflictIdentifier(conflictIdentifier);
	}

	/**
	 * Sets the create date of this ct auto resolution info.
	 *
	 * @param createDate the create date of this ct auto resolution info
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the ct auto resolution info ID of this ct auto resolution info.
	 *
	 * @param ctAutoResolutionInfoId the ct auto resolution info ID of this ct auto resolution info
	 */
	@Override
	public void setCtAutoResolutionInfoId(long ctAutoResolutionInfoId) {
		model.setCtAutoResolutionInfoId(ctAutoResolutionInfoId);
	}

	/**
	 * Sets the ct collection ID of this ct auto resolution info.
	 *
	 * @param ctCollectionId the ct collection ID of this ct auto resolution info
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the model class name ID of this ct auto resolution info.
	 *
	 * @param modelClassNameId the model class name ID of this ct auto resolution info
	 */
	@Override
	public void setModelClassNameId(long modelClassNameId) {
		model.setModelClassNameId(modelClassNameId);
	}

	/**
	 * Sets the mvcc version of this ct auto resolution info.
	 *
	 * @param mvccVersion the mvcc version of this ct auto resolution info
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this ct auto resolution info.
	 *
	 * @param primaryKey the primary key of this ct auto resolution info
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the source model class pk of this ct auto resolution info.
	 *
	 * @param sourceModelClassPK the source model class pk of this ct auto resolution info
	 */
	@Override
	public void setSourceModelClassPK(long sourceModelClassPK) {
		model.setSourceModelClassPK(sourceModelClassPK);
	}

	/**
	 * Sets the target model class pk of this ct auto resolution info.
	 *
	 * @param targetModelClassPK the target model class pk of this ct auto resolution info
	 */
	@Override
	public void setTargetModelClassPK(long targetModelClassPK) {
		model.setTargetModelClassPK(targetModelClassPK);
	}

	@Override
	protected CTAutoResolutionInfoWrapper wrap(
		CTAutoResolutionInfo ctAutoResolutionInfo) {

		return new CTAutoResolutionInfoWrapper(ctAutoResolutionInfo);
	}

}