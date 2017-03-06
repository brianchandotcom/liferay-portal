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

package com.liferay.friendly.url.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link FriendlyURLEntryLocalization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLEntryLocalization
 * @generated
 */
@ProviderType
public class FriendlyURLEntryLocalizationWrapper
	implements FriendlyURLEntryLocalization,
		ModelWrapper<FriendlyURLEntryLocalization> {
	public FriendlyURLEntryLocalizationWrapper(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization) {
		_friendlyURLEntryLocalization = friendlyURLEntryLocalization;
	}

	@Override
	public Class<?> getModelClass() {
		return FriendlyURLEntryLocalization.class;
	}

	@Override
	public String getModelClassName() {
		return FriendlyURLEntryLocalization.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("friendlyURLEntryLocalizationId",
			getFriendlyURLEntryLocalizationId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("friendlyURLEntryId", getFriendlyURLEntryId());
		attributes.put("urlTitle", getUrlTitle());
		attributes.put("languageId", getLanguageId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long friendlyURLEntryLocalizationId = (Long)attributes.get(
				"friendlyURLEntryLocalizationId");

		if (friendlyURLEntryLocalizationId != null) {
			setFriendlyURLEntryLocalizationId(friendlyURLEntryLocalizationId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long friendlyURLEntryId = (Long)attributes.get("friendlyURLEntryId");

		if (friendlyURLEntryId != null) {
			setFriendlyURLEntryId(friendlyURLEntryId);
		}

		String urlTitle = (String)attributes.get("urlTitle");

		if (urlTitle != null) {
			setUrlTitle(urlTitle);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}
	}

	@Override
	public FriendlyURLEntryLocalization toEscapedModel() {
		return new FriendlyURLEntryLocalizationWrapper(_friendlyURLEntryLocalization.toEscapedModel());
	}

	@Override
	public FriendlyURLEntryLocalization toUnescapedModel() {
		return new FriendlyURLEntryLocalizationWrapper(_friendlyURLEntryLocalization.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _friendlyURLEntryLocalization.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _friendlyURLEntryLocalization.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _friendlyURLEntryLocalization.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _friendlyURLEntryLocalization.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<FriendlyURLEntryLocalization> toCacheModel() {
		return _friendlyURLEntryLocalization.toCacheModel();
	}

	@Override
	public int compareTo(
		FriendlyURLEntryLocalization friendlyURLEntryLocalization) {
		return _friendlyURLEntryLocalization.compareTo(friendlyURLEntryLocalization);
	}

	@Override
	public int hashCode() {
		return _friendlyURLEntryLocalization.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _friendlyURLEntryLocalization.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new FriendlyURLEntryLocalizationWrapper((FriendlyURLEntryLocalization)_friendlyURLEntryLocalization.clone());
	}

	/**
	* Returns the language ID of this friendly url entry localization.
	*
	* @return the language ID of this friendly url entry localization
	*/
	@Override
	public java.lang.String getLanguageId() {
		return _friendlyURLEntryLocalization.getLanguageId();
	}

	/**
	* Returns the url title of this friendly url entry localization.
	*
	* @return the url title of this friendly url entry localization
	*/
	@Override
	public java.lang.String getUrlTitle() {
		return _friendlyURLEntryLocalization.getUrlTitle();
	}

	@Override
	public java.lang.String toString() {
		return _friendlyURLEntryLocalization.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _friendlyURLEntryLocalization.toXmlString();
	}

	/**
	* Returns the company ID of this friendly url entry localization.
	*
	* @return the company ID of this friendly url entry localization
	*/
	@Override
	public long getCompanyId() {
		return _friendlyURLEntryLocalization.getCompanyId();
	}

	/**
	* Returns the friendly url entry ID of this friendly url entry localization.
	*
	* @return the friendly url entry ID of this friendly url entry localization
	*/
	@Override
	public long getFriendlyURLEntryId() {
		return _friendlyURLEntryLocalization.getFriendlyURLEntryId();
	}

	/**
	* Returns the friendly url entry localization ID of this friendly url entry localization.
	*
	* @return the friendly url entry localization ID of this friendly url entry localization
	*/
	@Override
	public long getFriendlyURLEntryLocalizationId() {
		return _friendlyURLEntryLocalization.getFriendlyURLEntryLocalizationId();
	}

	/**
	* Returns the group ID of this friendly url entry localization.
	*
	* @return the group ID of this friendly url entry localization
	*/
	@Override
	public long getGroupId() {
		return _friendlyURLEntryLocalization.getGroupId();
	}

	/**
	* Returns the primary key of this friendly url entry localization.
	*
	* @return the primary key of this friendly url entry localization
	*/
	@Override
	public long getPrimaryKey() {
		return _friendlyURLEntryLocalization.getPrimaryKey();
	}

	@Override
	public void persist() {
		_friendlyURLEntryLocalization.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_friendlyURLEntryLocalization.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this friendly url entry localization.
	*
	* @param companyId the company ID of this friendly url entry localization
	*/
	@Override
	public void setCompanyId(long companyId) {
		_friendlyURLEntryLocalization.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_friendlyURLEntryLocalization.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_friendlyURLEntryLocalization.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_friendlyURLEntryLocalization.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the friendly url entry ID of this friendly url entry localization.
	*
	* @param friendlyURLEntryId the friendly url entry ID of this friendly url entry localization
	*/
	@Override
	public void setFriendlyURLEntryId(long friendlyURLEntryId) {
		_friendlyURLEntryLocalization.setFriendlyURLEntryId(friendlyURLEntryId);
	}

	/**
	* Sets the friendly url entry localization ID of this friendly url entry localization.
	*
	* @param friendlyURLEntryLocalizationId the friendly url entry localization ID of this friendly url entry localization
	*/
	@Override
	public void setFriendlyURLEntryLocalizationId(
		long friendlyURLEntryLocalizationId) {
		_friendlyURLEntryLocalization.setFriendlyURLEntryLocalizationId(friendlyURLEntryLocalizationId);
	}

	/**
	* Sets the group ID of this friendly url entry localization.
	*
	* @param groupId the group ID of this friendly url entry localization
	*/
	@Override
	public void setGroupId(long groupId) {
		_friendlyURLEntryLocalization.setGroupId(groupId);
	}

	/**
	* Sets the language ID of this friendly url entry localization.
	*
	* @param languageId the language ID of this friendly url entry localization
	*/
	@Override
	public void setLanguageId(java.lang.String languageId) {
		_friendlyURLEntryLocalization.setLanguageId(languageId);
	}

	@Override
	public void setNew(boolean n) {
		_friendlyURLEntryLocalization.setNew(n);
	}

	/**
	* Sets the primary key of this friendly url entry localization.
	*
	* @param primaryKey the primary key of this friendly url entry localization
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_friendlyURLEntryLocalization.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_friendlyURLEntryLocalization.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the url title of this friendly url entry localization.
	*
	* @param urlTitle the url title of this friendly url entry localization
	*/
	@Override
	public void setUrlTitle(java.lang.String urlTitle) {
		_friendlyURLEntryLocalization.setUrlTitle(urlTitle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FriendlyURLEntryLocalizationWrapper)) {
			return false;
		}

		FriendlyURLEntryLocalizationWrapper friendlyURLEntryLocalizationWrapper = (FriendlyURLEntryLocalizationWrapper)obj;

		if (Objects.equals(_friendlyURLEntryLocalization,
					friendlyURLEntryLocalizationWrapper._friendlyURLEntryLocalization)) {
			return true;
		}

		return false;
	}

	@Override
	public FriendlyURLEntryLocalization getWrappedModel() {
		return _friendlyURLEntryLocalization;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _friendlyURLEntryLocalization.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _friendlyURLEntryLocalization.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_friendlyURLEntryLocalization.resetOriginalValues();
	}

	private final FriendlyURLEntryLocalization _friendlyURLEntryLocalization;
}