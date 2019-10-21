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

package com.liferay.depot.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DepotEntryGroupRel}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DepotEntryGroupRel
 * @generated
 */
public class DepotEntryGroupRelWrapper
	extends BaseModelWrapper<DepotEntryGroupRel>
	implements DepotEntryGroupRel, ModelWrapper<DepotEntryGroupRel> {

	public DepotEntryGroupRelWrapper(DepotEntryGroupRel depotEntryGroupRel) {
		super(depotEntryGroupRel);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("depotEntryGroupRelId", getDepotEntryGroupRelId());
		attributes.put("companyId", getCompanyId());
		attributes.put("depotEntryId", getDepotEntryId());
		attributes.put("toGroupId", getToGroupId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long depotEntryGroupRelId = (Long)attributes.get(
			"depotEntryGroupRelId");

		if (depotEntryGroupRelId != null) {
			setDepotEntryGroupRelId(depotEntryGroupRelId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long depotEntryId = (Long)attributes.get("depotEntryId");

		if (depotEntryId != null) {
			setDepotEntryId(depotEntryId);
		}

		Long toGroupId = (Long)attributes.get("toGroupId");

		if (toGroupId != null) {
			setToGroupId(toGroupId);
		}
	}

	/**
	 * Returns the company ID of this depot entry group rel.
	 *
	 * @return the company ID of this depot entry group rel
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the depot entry group rel ID of this depot entry group rel.
	 *
	 * @return the depot entry group rel ID of this depot entry group rel
	 */
	@Override
	public long getDepotEntryGroupRelId() {
		return model.getDepotEntryGroupRelId();
	}

	/**
	 * Returns the depot entry ID of this depot entry group rel.
	 *
	 * @return the depot entry ID of this depot entry group rel
	 */
	@Override
	public long getDepotEntryId() {
		return model.getDepotEntryId();
	}

	/**
	 * Returns the mvcc version of this depot entry group rel.
	 *
	 * @return the mvcc version of this depot entry group rel
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this depot entry group rel.
	 *
	 * @return the primary key of this depot entry group rel
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the to group ID of this depot entry group rel.
	 *
	 * @return the to group ID of this depot entry group rel
	 */
	@Override
	public long getToGroupId() {
		return model.getToGroupId();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a depot entry group rel model instance should use the <code>DepotEntryGroupRel</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this depot entry group rel.
	 *
	 * @param companyId the company ID of this depot entry group rel
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the depot entry group rel ID of this depot entry group rel.
	 *
	 * @param depotEntryGroupRelId the depot entry group rel ID of this depot entry group rel
	 */
	@Override
	public void setDepotEntryGroupRelId(long depotEntryGroupRelId) {
		model.setDepotEntryGroupRelId(depotEntryGroupRelId);
	}

	/**
	 * Sets the depot entry ID of this depot entry group rel.
	 *
	 * @param depotEntryId the depot entry ID of this depot entry group rel
	 */
	@Override
	public void setDepotEntryId(long depotEntryId) {
		model.setDepotEntryId(depotEntryId);
	}

	/**
	 * Sets the mvcc version of this depot entry group rel.
	 *
	 * @param mvccVersion the mvcc version of this depot entry group rel
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this depot entry group rel.
	 *
	 * @param primaryKey the primary key of this depot entry group rel
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the to group ID of this depot entry group rel.
	 *
	 * @param toGroupId the to group ID of this depot entry group rel
	 */
	@Override
	public void setToGroupId(long toGroupId) {
		model.setToGroupId(toGroupId);
	}

	@Override
	protected DepotEntryGroupRelWrapper wrap(
		DepotEntryGroupRel depotEntryGroupRel) {

		return new DepotEntryGroupRelWrapper(depotEntryGroupRel);
	}

}