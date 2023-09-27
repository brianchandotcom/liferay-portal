/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.expando.kernel.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

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

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("rowId", getRowId());
		attributes.put("companyId", getCompanyId());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("tableId", getTableId());
		attributes.put("classPK", getClassPK());

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

	@Override
	public ExpandoRow cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
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
	 * Returns the ct collection ID of this expando row.
	 *
	 * @return the ct collection ID of this expando row
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
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
	 * Returns the mvcc version of this expando row.
	 *
	 * @return the mvcc version of this expando row
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
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
	 * Sets the ct collection ID of this expando row.
	 *
	 * @param ctCollectionId the ct collection ID of this expando row
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
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
	 * Sets the mvcc version of this expando row.
	 *
	 * @param mvccVersion the mvcc version of this expando row
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
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
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<ExpandoRow, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<ExpandoRow, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected ExpandoRowWrapper wrap(ExpandoRow expandoRow) {
		return new ExpandoRowWrapper(expandoRow);
	}

}