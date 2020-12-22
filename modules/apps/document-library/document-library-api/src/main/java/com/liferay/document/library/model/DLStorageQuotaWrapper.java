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

package com.liferay.document.library.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DLStorageQuota}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLStorageQuota
 * @generated
 */
public class DLStorageQuotaWrapper
	extends BaseModelWrapper<DLStorageQuota>
	implements DLStorageQuota, ModelWrapper<DLStorageQuota> {

	public DLStorageQuotaWrapper(DLStorageQuota dlStorageQuota) {
		super(dlStorageQuota);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("dlStorageQuotaId", getDlStorageQuotaId());
		attributes.put("companyId", getCompanyId());
		attributes.put("storageSize", getStorageSize());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long dlStorageQuotaId = (Long)attributes.get("dlStorageQuotaId");

		if (dlStorageQuotaId != null) {
			setDlStorageQuotaId(dlStorageQuotaId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long storageSize = (Long)attributes.get("storageSize");

		if (storageSize != null) {
			setStorageSize(storageSize);
		}
	}

	/**
	 * Returns the company ID of this dl storage quota.
	 *
	 * @return the company ID of this dl storage quota
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the dl storage quota ID of this dl storage quota.
	 *
	 * @return the dl storage quota ID of this dl storage quota
	 */
	@Override
	public long getDlStorageQuotaId() {
		return model.getDlStorageQuotaId();
	}

	/**
	 * Returns the mvcc version of this dl storage quota.
	 *
	 * @return the mvcc version of this dl storage quota
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this dl storage quota.
	 *
	 * @return the primary key of this dl storage quota
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the storage size of this dl storage quota.
	 *
	 * @return the storage size of this dl storage quota
	 */
	@Override
	public long getStorageSize() {
		return model.getStorageSize();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this dl storage quota.
	 *
	 * @param companyId the company ID of this dl storage quota
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the dl storage quota ID of this dl storage quota.
	 *
	 * @param dlStorageQuotaId the dl storage quota ID of this dl storage quota
	 */
	@Override
	public void setDlStorageQuotaId(long dlStorageQuotaId) {
		model.setDlStorageQuotaId(dlStorageQuotaId);
	}

	/**
	 * Sets the mvcc version of this dl storage quota.
	 *
	 * @param mvccVersion the mvcc version of this dl storage quota
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this dl storage quota.
	 *
	 * @param primaryKey the primary key of this dl storage quota
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the storage size of this dl storage quota.
	 *
	 * @param storageSize the storage size of this dl storage quota
	 */
	@Override
	public void setStorageSize(long storageSize) {
		model.setStorageSize(storageSize);
	}

	@Override
	protected DLStorageQuotaWrapper wrap(DLStorageQuota dlStorageQuota) {
		return new DLStorageQuotaWrapper(dlStorageQuota);
	}

}