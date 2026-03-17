/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat710.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link ERCVersionedEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCVersionedEntry
 * @generated
 */
public class ERCVersionedEntryWrapper
	implements ERCVersionedEntry, ModelWrapper<ERCVersionedEntry> {

	public ERCVersionedEntryWrapper(ERCVersionedEntry ercVersionedEntry) {
		_ercVersionedEntry = ercVersionedEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return ERCVersionedEntry.class;
	}

	@Override
	public String getModelClassName() {
		return ERCVersionedEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("ercVersionedEntryId", getErcVersionedEntryId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		String externalReferenceCode = (String)attributes.get(
			"externalReferenceCode");

		if (externalReferenceCode != null) {
			setExternalReferenceCode(externalReferenceCode);
		}

		Long ercVersionedEntryId = (Long)attributes.get("ercVersionedEntryId");

		if (ercVersionedEntryId != null) {
			setErcVersionedEntryId(ercVersionedEntryId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}
	}

	@Override
	public Object clone() {
		return new ERCVersionedEntryWrapper(
			(ERCVersionedEntry)_ercVersionedEntry.clone());
	}

	@Override
	public int compareTo(ERCVersionedEntry ercVersionedEntry) {
		return _ercVersionedEntry.compareTo(ercVersionedEntry);
	}

	/**
	 * Returns the company ID of this erc versioned entry.
	 *
	 * @return the company ID of this erc versioned entry
	 */
	@Override
	public long getCompanyId() {
		return _ercVersionedEntry.getCompanyId();
	}

	/**
	 * Returns the erc versioned entry ID of this erc versioned entry.
	 *
	 * @return the erc versioned entry ID of this erc versioned entry
	 */
	@Override
	public long getErcVersionedEntryId() {
		return _ercVersionedEntry.getErcVersionedEntryId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _ercVersionedEntry.getExpandoBridge();
	}

	/**
	 * Returns the external reference code of this erc versioned entry.
	 *
	 * @return the external reference code of this erc versioned entry
	 */
	@Override
	public String getExternalReferenceCode() {
		return _ercVersionedEntry.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this erc versioned entry.
	 *
	 * @return the group ID of this erc versioned entry
	 */
	@Override
	public long getGroupId() {
		return _ercVersionedEntry.getGroupId();
	}

	/**
	 * Returns the primary key of this erc versioned entry.
	 *
	 * @return the primary key of this erc versioned entry
	 */
	@Override
	public long getPrimaryKey() {
		return _ercVersionedEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ercVersionedEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the uuid of this erc versioned entry.
	 *
	 * @return the uuid of this erc versioned entry
	 */
	@Override
	public String getUuid() {
		return _ercVersionedEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _ercVersionedEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _ercVersionedEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ercVersionedEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _ercVersionedEntry.isNew();
	}

	@Override
	public void persist() {
		_ercVersionedEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ercVersionedEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the company ID of this erc versioned entry.
	 *
	 * @param companyId the company ID of this erc versioned entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		_ercVersionedEntry.setCompanyId(companyId);
	}

	/**
	 * Sets the erc versioned entry ID of this erc versioned entry.
	 *
	 * @param ercVersionedEntryId the erc versioned entry ID of this erc versioned entry
	 */
	@Override
	public void setErcVersionedEntryId(long ercVersionedEntryId) {
		_ercVersionedEntry.setErcVersionedEntryId(ercVersionedEntryId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_ercVersionedEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_ercVersionedEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_ercVersionedEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the external reference code of this erc versioned entry.
	 *
	 * @param externalReferenceCode the external reference code of this erc versioned entry
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		_ercVersionedEntry.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this erc versioned entry.
	 *
	 * @param groupId the group ID of this erc versioned entry
	 */
	@Override
	public void setGroupId(long groupId) {
		_ercVersionedEntry.setGroupId(groupId);
	}

	@Override
	public void setNew(boolean n) {
		_ercVersionedEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this erc versioned entry.
	 *
	 * @param primaryKey the primary key of this erc versioned entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ercVersionedEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ercVersionedEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the uuid of this erc versioned entry.
	 *
	 * @param uuid the uuid of this erc versioned entry
	 */
	@Override
	public void setUuid(String uuid) {
		_ercVersionedEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ERCVersionedEntry>
		toCacheModel() {

		return _ercVersionedEntry.toCacheModel();
	}

	@Override
	public ERCVersionedEntry toEscapedModel() {
		return new ERCVersionedEntryWrapper(
			_ercVersionedEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _ercVersionedEntry.toString();
	}

	@Override
	public ERCVersionedEntry toUnescapedModel() {
		return new ERCVersionedEntryWrapper(
			_ercVersionedEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _ercVersionedEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ERCVersionedEntryWrapper)) {
			return false;
		}

		ERCVersionedEntryWrapper ercVersionedEntryWrapper =
			(ERCVersionedEntryWrapper)object;

		if (Objects.equals(
				_ercVersionedEntry,
				ercVersionedEntryWrapper._ercVersionedEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public ERCVersionedEntry getWrappedModel() {
		return _ercVersionedEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ercVersionedEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ercVersionedEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ercVersionedEntry.resetOriginalValues();
	}

	private final ERCVersionedEntry _ercVersionedEntry;

}
// SB-Hash:-1897319737