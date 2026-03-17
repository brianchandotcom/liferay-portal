/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat700.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link LikeFinderEntry}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LikeFinderEntry
 * @generated
 */
public class LikeFinderEntryWrapper
	implements LikeFinderEntry, ModelWrapper<LikeFinderEntry> {

	public LikeFinderEntryWrapper(LikeFinderEntry likeFinderEntry) {
		_likeFinderEntry = likeFinderEntry;
	}

	@Override
	public Class<?> getModelClass() {
		return LikeFinderEntry.class;
	}

	@Override
	public String getModelClassName() {
		return LikeFinderEntry.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("likeFinderEntryId", getLikeFinderEntryId());
		attributes.put("companyId", getCompanyId());
		attributes.put("ownerId", getOwnerId());
		attributes.put("ownerType", getOwnerType());
		attributes.put("portletId", getPortletId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long likeFinderEntryId = (Long)attributes.get("likeFinderEntryId");

		if (likeFinderEntryId != null) {
			setLikeFinderEntryId(likeFinderEntryId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long ownerId = (Long)attributes.get("ownerId");

		if (ownerId != null) {
			setOwnerId(ownerId);
		}

		Integer ownerType = (Integer)attributes.get("ownerType");

		if (ownerType != null) {
			setOwnerType(ownerType);
		}

		String portletId = (String)attributes.get("portletId");

		if (portletId != null) {
			setPortletId(portletId);
		}
	}

	@Override
	public Object clone() {
		return new LikeFinderEntryWrapper(
			(LikeFinderEntry)_likeFinderEntry.clone());
	}

	@Override
	public int compareTo(LikeFinderEntry likeFinderEntry) {
		return _likeFinderEntry.compareTo(likeFinderEntry);
	}

	/**
	 * Returns the company ID of this like finder entry.
	 *
	 * @return the company ID of this like finder entry
	 */
	@Override
	public long getCompanyId() {
		return _likeFinderEntry.getCompanyId();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _likeFinderEntry.getExpandoBridge();
	}

	/**
	 * Returns the like finder entry ID of this like finder entry.
	 *
	 * @return the like finder entry ID of this like finder entry
	 */
	@Override
	public long getLikeFinderEntryId() {
		return _likeFinderEntry.getLikeFinderEntryId();
	}

	/**
	 * Returns the owner ID of this like finder entry.
	 *
	 * @return the owner ID of this like finder entry
	 */
	@Override
	public long getOwnerId() {
		return _likeFinderEntry.getOwnerId();
	}

	/**
	 * Returns the owner type of this like finder entry.
	 *
	 * @return the owner type of this like finder entry
	 */
	@Override
	public int getOwnerType() {
		return _likeFinderEntry.getOwnerType();
	}

	/**
	 * Returns the portlet ID of this like finder entry.
	 *
	 * @return the portlet ID of this like finder entry
	 */
	@Override
	public String getPortletId() {
		return _likeFinderEntry.getPortletId();
	}

	/**
	 * Returns the primary key of this like finder entry.
	 *
	 * @return the primary key of this like finder entry
	 */
	@Override
	public long getPrimaryKey() {
		return _likeFinderEntry.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _likeFinderEntry.getPrimaryKeyObj();
	}

	@Override
	public int hashCode() {
		return _likeFinderEntry.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _likeFinderEntry.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _likeFinderEntry.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _likeFinderEntry.isNew();
	}

	@Override
	public void persist() {
		_likeFinderEntry.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_likeFinderEntry.setCachedModel(cachedModel);
	}

	/**
	 * Sets the company ID of this like finder entry.
	 *
	 * @param companyId the company ID of this like finder entry
	 */
	@Override
	public void setCompanyId(long companyId) {
		_likeFinderEntry.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_likeFinderEntry.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_likeFinderEntry.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_likeFinderEntry.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the like finder entry ID of this like finder entry.
	 *
	 * @param likeFinderEntryId the like finder entry ID of this like finder entry
	 */
	@Override
	public void setLikeFinderEntryId(long likeFinderEntryId) {
		_likeFinderEntry.setLikeFinderEntryId(likeFinderEntryId);
	}

	@Override
	public void setNew(boolean n) {
		_likeFinderEntry.setNew(n);
	}

	/**
	 * Sets the owner ID of this like finder entry.
	 *
	 * @param ownerId the owner ID of this like finder entry
	 */
	@Override
	public void setOwnerId(long ownerId) {
		_likeFinderEntry.setOwnerId(ownerId);
	}

	/**
	 * Sets the owner type of this like finder entry.
	 *
	 * @param ownerType the owner type of this like finder entry
	 */
	@Override
	public void setOwnerType(int ownerType) {
		_likeFinderEntry.setOwnerType(ownerType);
	}

	/**
	 * Sets the portlet ID of this like finder entry.
	 *
	 * @param portletId the portlet ID of this like finder entry
	 */
	@Override
	public void setPortletId(String portletId) {
		_likeFinderEntry.setPortletId(portletId);
	}

	/**
	 * Sets the primary key of this like finder entry.
	 *
	 * @param primaryKey the primary key of this like finder entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_likeFinderEntry.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_likeFinderEntry.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<LikeFinderEntry>
		toCacheModel() {

		return _likeFinderEntry.toCacheModel();
	}

	@Override
	public LikeFinderEntry toEscapedModel() {
		return new LikeFinderEntryWrapper(_likeFinderEntry.toEscapedModel());
	}

	@Override
	public String toString() {
		return _likeFinderEntry.toString();
	}

	@Override
	public LikeFinderEntry toUnescapedModel() {
		return new LikeFinderEntryWrapper(_likeFinderEntry.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _likeFinderEntry.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LikeFinderEntryWrapper)) {
			return false;
		}

		LikeFinderEntryWrapper likeFinderEntryWrapper =
			(LikeFinderEntryWrapper)object;

		if (Objects.equals(
				_likeFinderEntry, likeFinderEntryWrapper._likeFinderEntry)) {

			return true;
		}

		return false;
	}

	@Override
	public LikeFinderEntry getWrappedModel() {
		return _likeFinderEntry;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _likeFinderEntry.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _likeFinderEntry.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_likeFinderEntry.resetOriginalValues();
	}

	private final LikeFinderEntry _likeFinderEntry;

}
// SB-Hash:764653888