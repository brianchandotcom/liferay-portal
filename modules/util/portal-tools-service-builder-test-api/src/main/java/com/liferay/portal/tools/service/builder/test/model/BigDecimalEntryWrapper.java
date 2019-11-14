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

import java.math.BigDecimal;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link BigDecimalEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see BigDecimalEntry
 * @generated
 */
public class BigDecimalEntryWrapper
	extends BaseModelWrapper<BigDecimalEntry>
	implements BigDecimalEntry, ModelWrapper<BigDecimalEntry> {

	public BigDecimalEntryWrapper(BigDecimalEntry bigDecimalEntry) {
		super(bigDecimalEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("bigDecimalEntryId", getBigDecimalEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("bigDecimalValue", getBigDecimalValue());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long bigDecimalEntryId = (Long)attributes.get("bigDecimalEntryId");

		if (bigDecimalEntryId != null) {
			setBigDecimalEntryId(bigDecimalEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		BigDecimal bigDecimalValue = (BigDecimal)attributes.get(
			"bigDecimalValue");

		if (bigDecimalValue != null) {
			setBigDecimalValue(bigDecimalValue);
		}
	}

	/**
	 * Returns the big decimal entry ID of this big decimal entry.
	 *
	 * @return the big decimal entry ID of this big decimal entry
	 */
	@Override
	public long getBigDecimalEntryId() {
		return model.getBigDecimalEntryId();
	}

	/**
	 * Returns the big decimal value of this big decimal entry.
	 *
	 * @return the big decimal value of this big decimal entry
	 */
	@Override
	public BigDecimal getBigDecimalValue() {
		return model.getBigDecimalValue();
	}

	/**
	 * Returns the company ID of this big decimal entry.
	 *
	 * @return the company ID of this big decimal entry
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the primary key of this big decimal entry.
	 *
	 * @return the primary key of this big decimal entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Sets the big decimal entry ID of this big decimal entry.
	 *
	 * @param bigDecimalEntryId the big decimal entry ID of this big decimal entry
	 */
	@Override
	public void setBigDecimalEntryId(long bigDecimalEntryId) {
		model.setBigDecimalEntryId(bigDecimalEntryId);
	}

	/**
	 * Sets the big decimal value of this big decimal entry.
	 *
	 * @param bigDecimalValue the big decimal value of this big decimal entry
	 */
	@Override
	public void setBigDecimalValue(BigDecimal bigDecimalValue) {
		model.setBigDecimalValue(bigDecimalValue);
	}

	/**
	 * Sets the company ID of this big decimal entry.
	 *
	 * @param companyId the company ID of this big decimal entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the primary key of this big decimal entry.
	 *
	 * @param primaryKey the primary key of this big decimal entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	protected BigDecimalEntryWrapper wrap(BigDecimalEntry bigDecimalEntry) {
		return new BigDecimalEntryWrapper(bigDecimalEntry);
	}

}