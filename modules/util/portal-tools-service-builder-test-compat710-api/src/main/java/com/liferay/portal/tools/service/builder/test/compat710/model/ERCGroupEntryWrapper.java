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
 * This class is a wrapper for {@link ERCGroupEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCGroupEntry
 * @generated
 */
public class ERCGroupEntryWrapper
	implements ERCGroupEntry, ModelWrapper<ERCGroupEntry> {

	public ERCGroupEntryWrapper(ERCGroupEntry ercGroupEntry) {
		_ercGroupEntry = ercGroupEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return ERCGroupEntry.class;
	}

	@Override
	public String getModelClassName() {
		return ERCGroupEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("ercGroupEntryId", getErcGroupEntryId());
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

		Long ercGroupEntryId = (Long)attributes.get("ercGroupEntryId");

		if (ercGroupEntryId != null) {
			setErcGroupEntryId(ercGroupEntryId);
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
		return new ERCGroupEntryWrapper((ERCGroupEntry)_ercGroupEntry.clone());
	}

	@Override
	public int compareTo(ERCGroupEntry ercGroupEntry) {
		return _ercGroupEntry.compareTo(ercGroupEntry);
	}

	/**
	 * Returns the company ID of this erc group entry.
	 *
	 * @return the company ID of this erc group entry
	 */
	@Override
	public long getCompanyId() {
		return _ercGroupEntry.getCompanyId();
	}

	/**
	 * Returns the erc group entry ID of this erc group entry.
	 *
	 * @return the erc group entry ID of this erc group entry
	 */
	@Override
	public long getErcGroupEntryId() {
		return _ercGroupEntry.getErcGroupEntryId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _ercGroupEntry.getExpandoBridge();
	}

	/**
	 * Returns the external reference code of this erc group entry.
	 *
	 * @return the external reference code of this erc group entry
	 */
	@Override
	public String getExternalReferenceCode() {
		return _ercGroupEntry.getExternalReferenceCode();
	}

	/**
	 * Returns the group ID of this erc group entry.
	 *
	 * @return the group ID of this erc group entry
	 */
	@Override
	public long getGroupId() {
		return _ercGroupEntry.getGroupId();
	}

	/**
	 * Returns the primary key of this erc group entry.
	 *
	 * @return the primary key of this erc group entry
	 */
	@Override
	public long getPrimaryKey() {
		return _ercGroupEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ercGroupEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the uuid of this erc group entry.
	 *
	 * @return the uuid of this erc group entry
	 */
	@Override
	public String getUuid() {
		return _ercGroupEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _ercGroupEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _ercGroupEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ercGroupEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _ercGroupEntry.isNew();
	}

	@Override
	public void persist() {
		_ercGroupEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ercGroupEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the company ID of this erc group entry.
	 *
	 * @param companyId the company ID of this erc group entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		_ercGroupEntry.setCompanyId(companyId);
	}

	/**
	 * Sets the erc group entry ID of this erc group entry.
	 *
	 * @param ercGroupEntryId the erc group entry ID of this erc group entry
	 */
	@Override
	public void setErcGroupEntryId(long ercGroupEntryId) {
		_ercGroupEntry.setErcGroupEntryId(ercGroupEntryId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_ercGroupEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_ercGroupEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_ercGroupEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the external reference code of this erc group entry.
	 *
	 * @param externalReferenceCode the external reference code of this erc group entry
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		_ercGroupEntry.setExternalReferenceCode(externalReferenceCode);
	}

	/**
	 * Sets the group ID of this erc group entry.
	 *
	 * @param groupId the group ID of this erc group entry
	 */
	@Override
	public void setGroupId(long groupId) {
		_ercGroupEntry.setGroupId(groupId);
	}

	@Override
	public void setNew(boolean n) {
		_ercGroupEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this erc group entry.
	 *
	 * @param primaryKey the primary key of this erc group entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ercGroupEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ercGroupEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the uuid of this erc group entry.
	 *
	 * @param uuid the uuid of this erc group entry
	 */
	@Override
	public void setUuid(String uuid) {
		_ercGroupEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ERCGroupEntry>
		toCacheModel() {

		return _ercGroupEntry.toCacheModel();
	}

	@Override
	public ERCGroupEntry toEscapedModel() {
		return new ERCGroupEntryWrapper(_ercGroupEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _ercGroupEntry.toString();
	}

	@Override
	public ERCGroupEntry toUnescapedModel() {
		return new ERCGroupEntryWrapper(_ercGroupEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _ercGroupEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ERCGroupEntryWrapper)) {
			return false;
		}

		ERCGroupEntryWrapper ercGroupEntryWrapper =
			(ERCGroupEntryWrapper)object;

		if (Objects.equals(
				_ercGroupEntry, ercGroupEntryWrapper._ercGroupEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public ERCGroupEntry getWrappedModel() {
		return _ercGroupEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ercGroupEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ercGroupEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ercGroupEntry.resetOriginalValues();
	}

	private final ERCGroupEntry _ercGroupEntry;

}
// SB-Hash:-75659769