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
 * This class is a wrapper for {@link ERCCompanyEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ERCCompanyEntry
 * @generated
 */
public class ERCCompanyEntryWrapper
	implements ERCCompanyEntry, ModelWrapper<ERCCompanyEntry> {

	public ERCCompanyEntryWrapper(ERCCompanyEntry ercCompanyEntry) {
		_ercCompanyEntry = ercCompanyEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return ERCCompanyEntry.class;
	}

	@Override
	public String getModelClassName() {
		return ERCCompanyEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("externalReferenceCode", getExternalReferenceCode());
		attributes.put("ercCompanyEntryId", getErcCompanyEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("column1", getColumn1());

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

		Long ercCompanyEntryId = (Long)attributes.get("ercCompanyEntryId");

		if (ercCompanyEntryId != null) {
			setErcCompanyEntryId(ercCompanyEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Integer column1 = (Integer)attributes.get("column1");

		if (column1 != null) {
			setColumn1(column1);
		}
	}

	@Override
	public Object clone() {
		return new ERCCompanyEntryWrapper(
			(ERCCompanyEntry)_ercCompanyEntry.clone());
	}

	@Override
	public int compareTo(ERCCompanyEntry ercCompanyEntry) {
		return _ercCompanyEntry.compareTo(ercCompanyEntry);
	}

	/**
	 * Returns the column1 of this erc company entry.
	 *
	 * @return the column1 of this erc company entry
	 */
	@Override
	public int getColumn1() {
		return _ercCompanyEntry.getColumn1();
	}

	/**
	 * Returns the company ID of this erc company entry.
	 *
	 * @return the company ID of this erc company entry
	 */
	@Override
	public long getCompanyId() {
		return _ercCompanyEntry.getCompanyId();
	}

	/**
	 * Returns the erc company entry ID of this erc company entry.
	 *
	 * @return the erc company entry ID of this erc company entry
	 */
	@Override
	public long getErcCompanyEntryId() {
		return _ercCompanyEntry.getErcCompanyEntryId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _ercCompanyEntry.getExpandoBridge();
	}

	/**
	 * Returns the external reference code of this erc company entry.
	 *
	 * @return the external reference code of this erc company entry
	 */
	@Override
	public String getExternalReferenceCode() {
		return _ercCompanyEntry.getExternalReferenceCode();
	}

	/**
	 * Returns the primary key of this erc company entry.
	 *
	 * @return the primary key of this erc company entry
	 */
	@Override
	public long getPrimaryKey() {
		return _ercCompanyEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _ercCompanyEntry.getPrimaryKeyObj();
	}

	/**
	 * Returns the user ID of this erc company entry.
	 *
	 * @return the user ID of this erc company entry
	 */
	@Override
	public long getUserId() {
		return _ercCompanyEntry.getUserId();
	}

	/**
	 * Returns the user name of this erc company entry.
	 *
	 * @return the user name of this erc company entry
	 */
	@Override
	public String getUserName() {
		return _ercCompanyEntry.getUserName();
	}

	/**
	 * Returns the user uuid of this erc company entry.
	 *
	 * @return the user uuid of this erc company entry
	 */
	@Override
	public String getUserUuid() {
		return _ercCompanyEntry.getUserUuid();
	}

	/**
	 * Returns the uuid of this erc company entry.
	 *
	 * @return the uuid of this erc company entry
	 */
	@Override
	public String getUuid() {
		return _ercCompanyEntry.getUuid();
	}

	@Override
	public int hashCode() {
		return _ercCompanyEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _ercCompanyEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _ercCompanyEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _ercCompanyEntry.isNew();
	}

	@Override
	public void persist() {
		_ercCompanyEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_ercCompanyEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the column1 of this erc company entry.
	 *
	 * @param column1 the column1 of this erc company entry
	 */
	@Override
	public void setColumn1(int column1) {
		_ercCompanyEntry.setColumn1(column1);
	}

	/**
	 * Sets the company ID of this erc company entry.
	 *
	 * @param companyId the company ID of this erc company entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		_ercCompanyEntry.setCompanyId(companyId);
	}

	/**
	 * Sets the erc company entry ID of this erc company entry.
	 *
	 * @param ercCompanyEntryId the erc company entry ID of this erc company entry
	 */
	@Override
	public void setErcCompanyEntryId(long ercCompanyEntryId) {
		_ercCompanyEntry.setErcCompanyEntryId(ercCompanyEntryId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_ercCompanyEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_ercCompanyEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_ercCompanyEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the external reference code of this erc company entry.
	 *
	 * @param externalReferenceCode the external reference code of this erc company entry
	 */
	@Override
	public void setExternalReferenceCode(String externalReferenceCode) {
		_ercCompanyEntry.setExternalReferenceCode(externalReferenceCode);
	}

	@Override
	public void setNew(boolean n) {
		_ercCompanyEntry.setNew(n);
	}

	/**
	 * Sets the primary key of this erc company entry.
	 *
	 * @param primaryKey the primary key of this erc company entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_ercCompanyEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_ercCompanyEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the user ID of this erc company entry.
	 *
	 * @param userId the user ID of this erc company entry
	 */
	@Override
	public void setUserId(long userId) {
		_ercCompanyEntry.setUserId(userId);
	}

	/**
	 * Sets the user name of this erc company entry.
	 *
	 * @param userName the user name of this erc company entry
	 */
	@Override
	public void setUserName(String userName) {
		_ercCompanyEntry.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this erc company entry.
	 *
	 * @param userUuid the user uuid of this erc company entry
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_ercCompanyEntry.setUserUuid(userUuid);
	}

	/**
	 * Sets the uuid of this erc company entry.
	 *
	 * @param uuid the uuid of this erc company entry
	 */
	@Override
	public void setUuid(String uuid) {
		_ercCompanyEntry.setUuid(uuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ERCCompanyEntry>
		toCacheModel() {

		return _ercCompanyEntry.toCacheModel();
	}

	@Override
	public ERCCompanyEntry toEscapedModel() {
		return new ERCCompanyEntryWrapper(_ercCompanyEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _ercCompanyEntry.toString();
	}

	@Override
	public ERCCompanyEntry toUnescapedModel() {
		return new ERCCompanyEntryWrapper(_ercCompanyEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _ercCompanyEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ERCCompanyEntryWrapper)) {
			return false;
		}

		ERCCompanyEntryWrapper ercCompanyEntryWrapper =
			(ERCCompanyEntryWrapper)object;

		if (Objects.equals(
				_ercCompanyEntry, ercCompanyEntryWrapper._ercCompanyEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public ERCCompanyEntry getWrappedModel() {
		return _ercCompanyEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _ercCompanyEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _ercCompanyEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_ercCompanyEntry.resetOriginalValues();
	}

	private final ERCCompanyEntry _ercCompanyEntry;

}
// SB-Hash:19257312