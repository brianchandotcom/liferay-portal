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

package com.liferay.expando.kernel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ExpandoRow}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ExpandoRow
 * @generated
 */
public class ExpandoRowWrapper
	extends BaseModelWrapper<ExpandoRow>
	implements ExpandoRow, ModelWrapper<ExpandoRow> {

	public ExpandoRowWrapper(ExpandoRow expandoRow) {
		super(expandoRow);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("rowId", getRowId());
		attributes.put("companyId", getCompanyId());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("tableId", getTableId());
		attributes.put("classPK", getClassPK());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long rowId = (Long)attributes.get("rowId");

		if (rowId != null) {
			setRowId(rowId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		Long tableId = (Long)attributes.get("tableId");

		if (tableId != null) {
			setTableId(tableId);
		}

		Long classPK = (Long)attributes.get("classPK");

		if (classPK != null) {
			setClassPK(classPK);
		}
	}

	/**
	 * Returns the class pk of this expando row.
	 *
	 * @return the class pk of this expando row
	 */
	@Override
	public long getClassPK() {
		return model.getClassPK();
	}

	/**
	 * Returns the company ID of this expando row.
	 *
	 * @return the company ID of this expando row
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the modified date of this expando row.
	 *
	 * @return the modified date of this expando row
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the primary key of this expando row.
	 *
	 * @return the primary key of this expando row
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the row ID of this expando row.
	 *
	 * @return the row ID of this expando row
	 */
	@Override
	public long getRowId() {
		return model.getRowId();
	}

	/**
	 * Returns the table ID of this expando row.
	 *
	 * @return the table ID of this expando row
	 */
	@Override
	public long getTableId() {
		return model.getTableId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the class pk of this expando row.
	 *
	 * @param classPK the class pk of this expando row
	 */
	@Override
	public void setClassPK(long classPK) {
		model.setClassPK(classPK);
	}

	/**
	 * Sets the company ID of this expando row.
	 *
	 * @param companyId the company ID of this expando row
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the modified date of this expando row.
	 *
	 * @param modifiedDate the modified date of this expando row
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the primary key of this expando row.
	 *
	 * @param primaryKey the primary key of this expando row
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the row ID of this expando row.
	 *
	 * @param rowId the row ID of this expando row
	 */
	@Override
	public void setRowId(long rowId) {
		model.setRowId(rowId);
	}

	/**
	 * Sets the table ID of this expando row.
	 *
	 * @param tableId the table ID of this expando row
	 */
	@Override
	public void setTableId(long tableId) {
		model.setTableId(tableId);
	}

	@Override
	protected ExpandoRowWrapper wrap(ExpandoRow expandoRow) {
		return new ExpandoRowWrapper(expandoRow);
	}

}