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

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ERCGroupEntity}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCGroupEntity
 * @generated
 */
public class ERCGroupEntityWrapper
	extends BaseModelWrapper<ERCGroupEntity>
	implements ERCGroupEntity, ModelWrapper<ERCGroupEntity> {

	public ERCGroupEntityWrapper(ERCGroupEntity ercGroupEntity) {
		super(ercGroupEntity);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("ercGroupEntityId", getErcGroupEntityId());
		attributes.put("companyId", getCompanyId());
		attributes.put("groupId", getGroupId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long ercGroupEntityId = (Long)attributes.get("ercGroupEntityId");

		if (ercGroupEntityId != null) {
			setErcGroupEntityId(ercGroupEntityId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}
	}

	/**
	 * Returns the company ID of this erc group entity.
	 *
	 * @return the company ID of this erc group entity
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the erc group entity ID of this erc group entity.
	 *
	 * @return the erc group entity ID of this erc group entity
	 */
	@Override
	public long getErcGroupEntityId() {
		return model.getErcGroupEntityId();
	}

	/**
	 * Returns the external reference code of this erc group entity.
	 *
	 * @return the external reference code of this erc group entity
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this erc group entity.
	 *
	 * @return the group ID of this erc group entity
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the primary key of this erc group entity.
	 *
	 * @return the primary key of this erc group entity
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this erc group entity.
	 *
	 * @param companyId the company ID of this erc group entity
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the erc group entity ID of this erc group entity.
	 *
	 * @param ercGroupEntityId the erc group entity ID of this erc group entity
	 */
	@Override
	public void setErcGroupEntityId(long ercGroupEntityId) {
		model.setErcGroupEntityId(ercGroupEntityId);
	}

	/**
	 * Sets the external reference code of this erc group entity.
	 *
	 * @param externalReferenceCode the external reference code of this erc group entity
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this erc group entity.
	 *
	 * @param groupId the group ID of this erc group entity
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the primary key of this erc group entity.
	 *
	 * @param primaryKey the primary key of this erc group entity
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected ERCGroupEntityWrapper wrap(ERCGroupEntity ercGroupEntity) {
		return new ERCGroupEntityWrapper(ercGroupEntity);
	}

}