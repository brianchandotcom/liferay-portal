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
 * This class is a wrapper for {@link ERCCompanyEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCCompanyEntry
 * @generated
 */
public class ERCCompanyEntryWrapper
	extends BaseModelWrapper<ERCCompanyEntry>
	implements ERCCompanyEntry, ModelWrapper<ERCCompanyEntry> {

	public ERCCompanyEntryWrapper(ERCCompanyEntry ercCompanyEntry) {
		super(ercCompanyEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("ercCompanyEntryId", getErcCompanyEntryId());
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

		Long ercCompanyEntryId = (Long)attributes.get("ercCompanyEntryId");

		if (ercCompanyEntryId != null) {
			setErcCompanyEntryId(ercCompanyEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}
	}

	/**
	 * Returns the company ID of this erc company entry.
	 *
	 * @return the company ID of this erc company entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the erc company entry ID of this erc company entry.
	 *
	 * @return the erc company entry ID of this erc company entry
	 */
	@Override
	public long getErcCompanyEntryId() {
		return model.getErcCompanyEntryId();
	}

	/**
	 * Returns the external reference code of this erc company entry.
	 *
	 * @return the external reference code of this erc company entry
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the primary key of this erc company entry.
	 *
	 * @return the primary key of this erc company entry
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
	 * Sets the company ID of this erc company entry.
	 *
	 * @param companyId the company ID of this erc company entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the erc company entry ID of this erc company entry.
	 *
	 * @param ercCompanyEntryId the erc company entry ID of this erc company entry
	 */
	@Override
	public void setErcCompanyEntryId(long ercCompanyEntryId) {
		model.setErcCompanyEntryId(ercCompanyEntryId);
	}

	/**
	 * Sets the external reference code of this erc company entry.
	 *
	 * @param externalReferenceCode the external reference code of this erc company entry
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the primary key of this erc company entry.
	 *
	 * @param primaryKey the primary key of this erc company entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected ERCCompanyEntryWrapper wrap(ERCCompanyEntry ercCompanyEntry) {
		return new ERCCompanyEntryWrapper(ercCompanyEntry);
	}

}