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
 * This class is a wrapper for {@link CompanyScopedEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CompanyScopedEntry
 * @generated
 */
public class CompanyScopedEntryWrapper
	extends BaseModelWrapper<CompanyScopedEntry>
	implements CompanyScopedEntry, ModelWrapper<CompanyScopedEntry> {

	public CompanyScopedEntryWrapper(CompanyScopedEntry companyScopedEntry) {
		super(companyScopedEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("CompanyScopedEntryId", getCompanyScopedEntryId());
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

		Long CompanyScopedEntryId = (Long)attributes.get(
			"CompanyScopedEntryId");

		if (CompanyScopedEntryId != null) {
			setCompanyScopedEntryId(CompanyScopedEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}
	}

	/**
	 * Returns the company ID of this company scoped entry.
	 *
	 * @return the company ID of this company scoped entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the company scoped entry ID of this company scoped entry.
	 *
	 * @return the company scoped entry ID of this company scoped entry
	 */
	@Override
	public long getCompanyScopedEntryId() {
		return model.getCompanyScopedEntryId();
	}

	/**
	 * Returns the external reference code of this company scoped entry.
	 *
	 * @return the external reference code of this company scoped entry
	 */
	@Override
	public String getExternalReferenceCode() {
		return model.getExternalReferenceCode();
	}

	/**
	 * Returns the primary key of this company scoped entry.
	 *
	 * @return the primary key of this company scoped entry
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
	 * Sets the company ID of this company scoped entry.
	 *
	 * @param companyId the company ID of this company scoped entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the company scoped entry ID of this company scoped entry.
	 *
	 * @param CompanyScopedEntryId the company scoped entry ID of this company scoped entry
	 */
	@Override
	public void setCompanyScopedEntryId(long CompanyScopedEntryId) {
		model.setCompanyScopedEntryId(CompanyScopedEntryId);
	}

	/**
	 * Sets the external reference code of this company scoped entry.
	 *
	 * @param externalReferenceCode the external reference code of this company scoped entry
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		model.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the primary key of this company scoped entry.
	 *
	 * @param primaryKey the primary key of this company scoped entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected CompanyScopedEntryWrapper wrap(
		CompanyScopedEntry companyScopedEntry) {

		return new CompanyScopedEntryWrapper(companyScopedEntry);
	}

}