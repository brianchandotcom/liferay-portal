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
 * This class is a wrapper for {@link FriendlyURLLocalization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLLocalization
 * @generated
 */
@ProviderType
public class FriendlyURLLocalizationWrapper implements FriendlyURLLocalization,
	ModelWrapper<FriendlyURLLocalization> {
	public FriendlyURLLocalizationWrapper(
		FriendlyURLLocalization friendlyURLLocalization) {
		_friendlyURLLocalization = friendlyURLLocalization;
	}

	@Override
	public Class<?> getModelClass() {
		return FriendlyURLLocalization.class;
	}

	@Override
	public String getModelClassName() {
		return FriendlyURLLocalization.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("friendlyURLLocalizationId",
			getFriendlyURLLocalizationId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("friendlyURLId", getFriendlyURLId());
		attributes.put("urlTitle", getUrlTitle());
		attributes.put("languageId", getLanguageId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long friendlyURLLocalizationId = (Long)attributes.get(
				"friendlyURLLocalizationId");

		if (friendlyURLLocalizationId != null) {
			setFriendlyURLLocalizationId(friendlyURLLocalizationId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long friendlyURLId = (Long)attributes.get("friendlyURLId");

		if (friendlyURLId != null) {
			setFriendlyURLId(friendlyURLId);
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
	public FriendlyURLLocalization toEscapedModel() {
		return new FriendlyURLLocalizationWrapper(_friendlyURLLocalization.toEscapedModel());
	}

	@Override
	public FriendlyURLLocalization toUnescapedModel() {
		return new FriendlyURLLocalizationWrapper(_friendlyURLLocalization.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _friendlyURLLocalization.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _friendlyURLLocalization.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _friendlyURLLocalization.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _friendlyURLLocalization.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<FriendlyURLLocalization> toCacheModel() {
		return _friendlyURLLocalization.toCacheModel();
	}

	@Override
	public int compareTo(FriendlyURLLocalization friendlyURLLocalization) {
		return _friendlyURLLocalization.compareTo(friendlyURLLocalization);
	}

	@Override
	public int hashCode() {
		return _friendlyURLLocalization.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _friendlyURLLocalization.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new FriendlyURLLocalizationWrapper((FriendlyURLLocalization)_friendlyURLLocalization.clone());
	}

	/**
	* Returns the language ID of this friendly url localization.
	*
	* @return the language ID of this friendly url localization
	*/
	@Override
	public java.lang.String getLanguageId() {
		return _friendlyURLLocalization.getLanguageId();
	}

	/**
	* Returns the url title of this friendly url localization.
	*
	* @return the url title of this friendly url localization
	*/
	@Override
	public java.lang.String getUrlTitle() {
		return _friendlyURLLocalization.getUrlTitle();
	}

	@Override
	public java.lang.String toString() {
		return _friendlyURLLocalization.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _friendlyURLLocalization.toXmlString();
	}

	/**
	* Returns the company ID of this friendly url localization.
	*
	* @return the company ID of this friendly url localization
	*/
	@Override
	public long getCompanyId() {
		return _friendlyURLLocalization.getCompanyId();
	}

	/**
	* Returns the friendly url ID of this friendly url localization.
	*
	* @return the friendly url ID of this friendly url localization
	*/
	@Override
	public long getFriendlyURLId() {
		return _friendlyURLLocalization.getFriendlyURLId();
	}

	/**
	* Returns the friendly url localization ID of this friendly url localization.
	*
	* @return the friendly url localization ID of this friendly url localization
	*/
	@Override
	public long getFriendlyURLLocalizationId() {
		return _friendlyURLLocalization.getFriendlyURLLocalizationId();
	}

	/**
	* Returns the group ID of this friendly url localization.
	*
	* @return the group ID of this friendly url localization
	*/
	@Override
	public long getGroupId() {
		return _friendlyURLLocalization.getGroupId();
	}

	/**
	* Returns the primary key of this friendly url localization.
	*
	* @return the primary key of this friendly url localization
	*/
	@Override
	public long getPrimaryKey() {
		return _friendlyURLLocalization.getPrimaryKey();
	}

	@Override
	public void persist() {
		_friendlyURLLocalization.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_friendlyURLLocalization.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this friendly url localization.
	*
	* @param companyId the company ID of this friendly url localization
	*/
	@Override
	public void setCompanyId(long companyId) {
		_friendlyURLLocalization.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_friendlyURLLocalization.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_friendlyURLLocalization.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_friendlyURLLocalization.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the friendly url ID of this friendly url localization.
	*
	* @param friendlyURLId the friendly url ID of this friendly url localization
	*/
	@Override
	public void setFriendlyURLId(long friendlyURLId) {
		_friendlyURLLocalization.setFriendlyURLId(friendlyURLId);
	}

	/**
	* Sets the friendly url localization ID of this friendly url localization.
	*
	* @param friendlyURLLocalizationId the friendly url localization ID of this friendly url localization
	*/
	@Override
	public void setFriendlyURLLocalizationId(long friendlyURLLocalizationId) {
		_friendlyURLLocalization.setFriendlyURLLocalizationId(friendlyURLLocalizationId);
	}

	/**
	* Sets the group ID of this friendly url localization.
	*
	* @param groupId the group ID of this friendly url localization
	*/
	@Override
	public void setGroupId(long groupId) {
		_friendlyURLLocalization.setGroupId(groupId);
	}

	/**
	* Sets the language ID of this friendly url localization.
	*
	* @param languageId the language ID of this friendly url localization
	*/
	@Override
	public void setLanguageId(java.lang.String languageId) {
		_friendlyURLLocalization.setLanguageId(languageId);
	}

	@Override
	public void setNew(boolean n) {
		_friendlyURLLocalization.setNew(n);
	}

	/**
	* Sets the primary key of this friendly url localization.
	*
	* @param primaryKey the primary key of this friendly url localization
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_friendlyURLLocalization.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_friendlyURLLocalization.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the url title of this friendly url localization.
	*
	* @param urlTitle the url title of this friendly url localization
	*/
	@Override
	public void setUrlTitle(java.lang.String urlTitle) {
		_friendlyURLLocalization.setUrlTitle(urlTitle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FriendlyURLLocalizationWrapper)) {
			return false;
		}

		FriendlyURLLocalizationWrapper friendlyURLLocalizationWrapper = (FriendlyURLLocalizationWrapper)obj;

		if (Objects.equals(_friendlyURLLocalization,
					friendlyURLLocalizationWrapper._friendlyURLLocalization)) {
			return true;
		}

		return false;
	}

	@Override
	public FriendlyURLLocalization getWrappedModel() {
		return _friendlyURLLocalization;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _friendlyURLLocalization.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _friendlyURLLocalization.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_friendlyURLLocalization.resetOriginalValues();
	}

	private final FriendlyURLLocalization _friendlyURLLocalization;
}