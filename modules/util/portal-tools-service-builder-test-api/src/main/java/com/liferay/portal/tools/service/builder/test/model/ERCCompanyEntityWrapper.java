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
 * This class is a wrapper for {@link ERCCompanyEntity}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCCompanyEntity
 * @generated
 */
public class ERCCompanyEntityWrapper
	extends BaseModelWrapper<ERCCompanyEntity>
	implements ERCCompanyEntity, ModelWrapper<ERCCompanyEntity> {

	public ERCCompanyEntityWrapper(ERCCompanyEntity ercCompanyEntity) {
		super(ercCompanyEntity);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("ercCompanyEntityId", getErcCompanyEntityId());
		attributes.put("companyId", getCompanyId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long ercCompanyEntityId = (Long)attributes.get("ercCompanyEntityId");

		if (ercCompanyEntityId != null) {
			setErcCompanyEntityId(ercCompanyEntityId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}
	}

	/**
	 * Returns the company ID of this erc company entity.
	 *
	 * @return the company ID of this erc company entity
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the erc company entity ID of this erc company entity.
	 *
	 * @return the erc company entity ID of this erc company entity
	 */
	@Override
	public long getErcCompanyEntityId() {
		return model.getErcCompanyEntityId();
	}

	/**
	 * Returns the external reference code of this erc company entity.
	 *
	 * @return the external reference code of this erc company entity
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the primary key of this erc company entity.
	 *
	 * @return the primary key of this erc company entity
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
	 * Sets the company ID of this erc company entity.
	 *
	 * @param companyId the company ID of this erc company entity
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the erc company entity ID of this erc company entity.
	 *
	 * @param ercCompanyEntityId the erc company entity ID of this erc company entity
	 */
	@Override
	public void setErcCompanyEntityId(long ercCompanyEntityId) {
		model.setErcCompanyEntityId(ercCompanyEntityId);
	}

	/**
	 * Sets the external reference code of this erc company entity.
	 *
	 * @param externalReferenceCode the external reference code of this erc company entity
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the primary key of this erc company entity.
	 *
	 * @param primaryKey the primary key of this erc company entity
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected ERCCompanyEntityWrapper wrap(ERCCompanyEntity ercCompanyEntity) {
		return new ERCCompanyEntityWrapper(ercCompanyEntity);
	}

}