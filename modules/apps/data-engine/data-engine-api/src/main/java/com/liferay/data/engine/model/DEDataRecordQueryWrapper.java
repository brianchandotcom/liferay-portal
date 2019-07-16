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

package com.liferay.data.engine.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * <p>
 * This class is a wrapper for {@link DEDataRecordQuery}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DEDataRecordQuery
 * @generated
 */
@ProviderType
public class DEDataRecordQueryWrapper
	extends BaseModelWrapper<DEDataRecordQuery>
	implements DEDataRecordQuery, ModelWrapper<DEDataRecordQuery> {

	public DEDataRecordQueryWrapper(DEDataRecordQuery deDataRecordQuery) {
		super(deDataRecordQuery);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("deDataRecordQueryId", getDeDataRecordQueryId());
		attributes.put("appliedFilters", getAppliedFilters());
		attributes.put("fieldNames", getFieldNames());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long deDataRecordQueryId = (Long)attributes.get("deDataRecordQueryId");

		if (deDataRecordQueryId != null) {
			setDeDataRecordQueryId(deDataRecordQueryId);
		}

		String appliedFilters = (String)attributes.get("appliedFilters");

		if (appliedFilters != null) {
			setAppliedFilters(appliedFilters);
		}

		String fieldNames = (String)attributes.get("fieldNames");

		if (fieldNames != null) {
			setFieldNames(fieldNames);
		}
	}

	/**
	 * Returns the applied filters of this de data record query.
	 *
	 * @return the applied filters of this de data record query
	 */
	@Override
	public String getAppliedFilters() {
		return model.getAppliedFilters();
	}

	/**
	 * Returns the de data record query ID of this de data record query.
	 *
	 * @return the de data record query ID of this de data record query
	 */
	@Override
	public long getDeDataRecordQueryId() {
		return model.getDeDataRecordQueryId();
	}

	/**
	 * Returns the field names of this de data record query.
	 *
	 * @return the field names of this de data record query
	 */
	@Override
	public String getFieldNames() {
		return model.getFieldNames();
	}

	/**
	 * Returns the primary key of this de data record query.
	 *
	 * @return the primary key of this de data record query
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the uuid of this de data record query.
	 *
	 * @return the uuid of this de data record query
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the applied filters of this de data record query.
	 *
	 * @param appliedFilters the applied filters of this de data record query
	 */
	@Override
	public void setAppliedFilters(String appliedFilters) {
		model.setAppliedFilters(appliedFilters);
	}

	/**
	 * Sets the de data record query ID of this de data record query.
	 *
	 * @param deDataRecordQueryId the de data record query ID of this de data record query
	 */
	@Override
	public void setDeDataRecordQueryId(long deDataRecordQueryId) {
		model.setDeDataRecordQueryId(deDataRecordQueryId);
	}

	/**
	 * Sets the field names of this de data record query.
	 *
	 * @param fieldNames the field names of this de data record query
	 */
	@Override
	public void setFieldNames(String fieldNames) {
		model.setFieldNames(fieldNames);
	}

	/**
	 * Sets the primary key of this de data record query.
	 *
	 * @param primaryKey the primary key of this de data record query
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the uuid of this de data record query.
	 *
	 * @param uuid the uuid of this de data record query
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	protected DEDataRecordQueryWrapper wrap(
		DEDataRecordQuery deDataRecordQuery) {

		return new DEDataRecordQueryWrapper(deDataRecordQuery);
	}

}