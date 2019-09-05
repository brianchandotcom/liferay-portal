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

package com.liferay.dynamic.data.mapping.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DDMDataProviderInstanceLink}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMDataProviderInstanceLink
 * @generated
 */
public class DDMDataProviderInstanceLinkWrapper
	extends BaseModelWrapper<DDMDataProviderInstanceLink>
	implements DDMDataProviderInstanceLink,
			   ModelWrapper<DDMDataProviderInstanceLink> {

	public DDMDataProviderInstanceLinkWrapper(
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {

		super(ddmDataProviderInstanceLink);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"dataProviderInstanceLinkId", getDataProviderInstanceLinkId());
		attributes.put("companyId", getCompanyId());
		attributes.put("dataProviderInstanceId", getDataProviderInstanceId());
		attributes.put("structureId", getStructureId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long dataProviderInstanceLinkId = (Long)attributes.get(
			"dataProviderInstanceLinkId");

		if (dataProviderInstanceLinkId != null) {
			setDataProviderInstanceLinkId(dataProviderInstanceLinkId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long dataProviderInstanceId = (Long)attributes.get(
			"dataProviderInstanceId");

		if (dataProviderInstanceId != null) {
			setDataProviderInstanceId(dataProviderInstanceId);
		}

		Long structureId = (Long)attributes.get("structureId");

		if (structureId != null) {
			setStructureId(structureId);
		}
	}

	/**
	 * Returns the company ID of this ddm data provider instance link.
	 *
	 * @return the company ID of this ddm data provider instance link
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the data provider instance ID of this ddm data provider instance link.
	 *
	 * @return the data provider instance ID of this ddm data provider instance link
	 */
	@Override
	public long getDataProviderInstanceId() {
		return model.getDataProviderInstanceId();
	}

	/**
	 * Returns the data provider instance link ID of this ddm data provider instance link.
	 *
	 * @return the data provider instance link ID of this ddm data provider instance link
	 */
	@Override
	public long getDataProviderInstanceLinkId() {
		return model.getDataProviderInstanceLinkId();
	}

	/**
	 * Returns the mvcc version of this ddm data provider instance link.
	 *
	 * @return the mvcc version of this ddm data provider instance link
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this ddm data provider instance link.
	 *
	 * @return the primary key of this ddm data provider instance link
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the structure ID of this ddm data provider instance link.
	 *
	 * @return the structure ID of this ddm data provider instance link
	 */
	@Override
	public long getStructureId() {
		return model.getStructureId();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this ddm data provider instance link.
	 *
	 * @param companyId the company ID of this ddm data provider instance link
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the data provider instance ID of this ddm data provider instance link.
	 *
	 * @param dataProviderInstanceId the data provider instance ID of this ddm data provider instance link
	 */
	@Override
	public void setDataProviderInstanceId(long dataProviderInstanceId) {
		model.setDataProviderInstanceId(dataProviderInstanceId);
	}

	/**
	 * Sets the data provider instance link ID of this ddm data provider instance link.
	 *
	 * @param dataProviderInstanceLinkId the data provider instance link ID of this ddm data provider instance link
	 */
	@Override
	public void setDataProviderInstanceLinkId(long dataProviderInstanceLinkId) {
		model.setDataProviderInstanceLinkId(dataProviderInstanceLinkId);
	}

	/**
	 * Sets the mvcc version of this ddm data provider instance link.
	 *
	 * @param mvccVersion the mvcc version of this ddm data provider instance link
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this ddm data provider instance link.
	 *
	 * @param primaryKey the primary key of this ddm data provider instance link
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the structure ID of this ddm data provider instance link.
	 *
	 * @param structureId the structure ID of this ddm data provider instance link
	 */
	@Override
	public void setStructureId(long structureId) {
		model.setStructureId(structureId);
	}

	@Override
	protected DDMDataProviderInstanceLinkWrapper wrap(
		DDMDataProviderInstanceLink ddmDataProviderInstanceLink) {

		return new DDMDataProviderInstanceLinkWrapper(
			ddmDataProviderInstanceLink);
	}

}