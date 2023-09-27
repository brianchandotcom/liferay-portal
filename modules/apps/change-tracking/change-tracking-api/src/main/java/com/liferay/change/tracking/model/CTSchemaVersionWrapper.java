/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CTSchemaVersion}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTSchemaVersion
 * @generated
 */
public class CTSchemaVersionWrapper
	extends BaseModelWrapper<CTSchemaVersion>
	implements CTSchemaVersion, ModelWrapper<CTSchemaVersion> {

	public CTSchemaVersionWrapper(CTSchemaVersion ctSchemaVersion) {
		super(ctSchemaVersion);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("schemaVersionId", getSchemaVersionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("schemaContext", getSchemaContext());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long schemaVersionId = (Long)attributes.get("schemaVersionId");

		if (schemaVersionId != null) {
			setSchemaVersionId(schemaVersionId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Map<String, Serializable> schemaContext =
			(Map<String, Serializable>)attributes.get("schemaContext");

		if (schemaContext != null) {
			setSchemaContext(schemaContext);
		}
	}

	@Override
	public CTSchemaVersion cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this ct schema version.
	 *
	 * @return the company ID of this ct schema version
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the mvcc version of this ct schema version.
	 *
	 * @return the mvcc version of this ct schema version
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this ct schema version.
	 *
	 * @return the primary key of this ct schema version
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the schema context of this ct schema version.
	 *
	 * @return the schema context of this ct schema version
	 */
	@Override
	public Map<String, Serializable> getSchemaContext() {
		return model.getSchemaContext();
	}

	/**
	 * Returns the schema version ID of this ct schema version.
	 *
	 * @return the schema version ID of this ct schema version
	 */
	@Override
	public long getSchemaVersionId() {
		return model.getSchemaVersionId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this ct schema version.
	 *
	 * @param companyId the company ID of this ct schema version
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the mvcc version of this ct schema version.
	 *
	 * @param mvccVersion the mvcc version of this ct schema version
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this ct schema version.
	 *
	 * @param primaryKey the primary key of this ct schema version
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the schema context of this ct schema version.
	 *
	 * @param schemaContext the schema context of this ct schema version
	 */
	@Override
	public void setSchemaContext(Map<String, Serializable> schemaContext) {
		model.setSchemaContext(schemaContext);
	}

	/**
	 * Sets the schema version ID of this ct schema version.
	 *
	 * @param schemaVersionId the schema version ID of this ct schema version
	 */
	@Override
	public void setSchemaVersionId(long schemaVersionId) {
		model.setSchemaVersionId(schemaVersionId);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected CTSchemaVersionWrapper wrap(CTSchemaVersion ctSchemaVersion) {
		return new CTSchemaVersionWrapper(ctSchemaVersion);
	}

}