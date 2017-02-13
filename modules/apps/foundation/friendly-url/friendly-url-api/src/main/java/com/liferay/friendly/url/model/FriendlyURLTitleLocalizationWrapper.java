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
 * This class is a wrapper for {@link FriendlyURLTitleLocalization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FriendlyURLTitleLocalization
 * @generated
 */
@ProviderType
public class FriendlyURLTitleLocalizationWrapper
	implements FriendlyURLTitleLocalization,
		ModelWrapper<FriendlyURLTitleLocalization> {
	public FriendlyURLTitleLocalizationWrapper(
		FriendlyURLTitleLocalization friendlyURLTitleLocalization) {
		_friendlyURLTitleLocalization = friendlyURLTitleLocalization;
	}

	@Override
	public Class<?> getModelClass() {
		return FriendlyURLTitleLocalization.class;
	}

	@Override
	public String getModelClassName() {
		return FriendlyURLTitleLocalization.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("friendlyURLTitleLocalizationId",
			getFriendlyURLTitleLocalizationId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("friendlyURLId", getFriendlyURLId());
		attributes.put("urlTitle", getUrlTitle());
		attributes.put("languageId", getLanguageId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long friendlyURLTitleLocalizationId = (Long)attributes.get(
				"friendlyURLTitleLocalizationId");

		if (friendlyURLTitleLocalizationId != null) {
			setFriendlyURLTitleLocalizationId(friendlyURLTitleLocalizationId);
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
	public FriendlyURLTitleLocalization toEscapedModel() {
		return new FriendlyURLTitleLocalizationWrapper(_friendlyURLTitleLocalization.toEscapedModel());
	}

	@Override
	public FriendlyURLTitleLocalization toUnescapedModel() {
		return new FriendlyURLTitleLocalizationWrapper(_friendlyURLTitleLocalization.toUnescapedModel());
	}

	@Override
	public boolean isCachedModel() {
		return _friendlyURLTitleLocalization.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _friendlyURLTitleLocalization.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _friendlyURLTitleLocalization.isNew();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _friendlyURLTitleLocalization.getExpandoBridge();
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<FriendlyURLTitleLocalization> toCacheModel() {
		return _friendlyURLTitleLocalization.toCacheModel();
	}

	@Override
	public int compareTo(
		FriendlyURLTitleLocalization friendlyURLTitleLocalization) {
		return _friendlyURLTitleLocalization.compareTo(friendlyURLTitleLocalization);
	}

	@Override
	public int hashCode() {
		return _friendlyURLTitleLocalization.hashCode();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _friendlyURLTitleLocalization.getPrimaryKeyObj();
	}

	@Override
	public java.lang.Object clone() {
		return new FriendlyURLTitleLocalizationWrapper((FriendlyURLTitleLocalization)_friendlyURLTitleLocalization.clone());
	}

	/**
	* Returns the language ID of this friendly url title localization.
	*
	* @return the language ID of this friendly url title localization
	*/
	@Override
	public java.lang.String getLanguageId() {
		return _friendlyURLTitleLocalization.getLanguageId();
	}

	/**
	* Returns the url title of this friendly url title localization.
	*
	* @return the url title of this friendly url title localization
	*/
	@Override
	public java.lang.String getUrlTitle() {
		return _friendlyURLTitleLocalization.getUrlTitle();
	}

	@Override
	public java.lang.String toString() {
		return _friendlyURLTitleLocalization.toString();
	}

	@Override
	public java.lang.String toXmlString() {
		return _friendlyURLTitleLocalization.toXmlString();
	}

	/**
	* Returns the company ID of this friendly url title localization.
	*
	* @return the company ID of this friendly url title localization
	*/
	@Override
	public long getCompanyId() {
		return _friendlyURLTitleLocalization.getCompanyId();
	}

	/**
	* Returns the friendly url ID of this friendly url title localization.
	*
	* @return the friendly url ID of this friendly url title localization
	*/
	@Override
	public long getFriendlyURLId() {
		return _friendlyURLTitleLocalization.getFriendlyURLId();
	}

	/**
	* Returns the friendly url title localization ID of this friendly url title localization.
	*
	* @return the friendly url title localization ID of this friendly url title localization
	*/
	@Override
	public long getFriendlyURLTitleLocalizationId() {
		return _friendlyURLTitleLocalization.getFriendlyURLTitleLocalizationId();
	}

	/**
	* Returns the group ID of this friendly url title localization.
	*
	* @return the group ID of this friendly url title localization
	*/
	@Override
	public long getGroupId() {
		return _friendlyURLTitleLocalization.getGroupId();
	}

	/**
	* Returns the primary key of this friendly url title localization.
	*
	* @return the primary key of this friendly url title localization
	*/
	@Override
	public long getPrimaryKey() {
		return _friendlyURLTitleLocalization.getPrimaryKey();
	}

	@Override
	public void persist() {
		_friendlyURLTitleLocalization.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_friendlyURLTitleLocalization.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this friendly url title localization.
	*
	* @param companyId the company ID of this friendly url title localization
	*/
	@Override
	public void setCompanyId(long companyId) {
		_friendlyURLTitleLocalization.setCompanyId(companyId);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_friendlyURLTitleLocalization.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_friendlyURLTitleLocalization.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_friendlyURLTitleLocalization.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the friendly url ID of this friendly url title localization.
	*
	* @param friendlyURLId the friendly url ID of this friendly url title localization
	*/
	@Override
	public void setFriendlyURLId(long friendlyURLId) {
		_friendlyURLTitleLocalization.setFriendlyURLId(friendlyURLId);
	}

	/**
	* Sets the friendly url title localization ID of this friendly url title localization.
	*
	* @param friendlyURLTitleLocalizationId the friendly url title localization ID of this friendly url title localization
	*/
	@Override
	public void setFriendlyURLTitleLocalizationId(
		long friendlyURLTitleLocalizationId) {
		_friendlyURLTitleLocalization.setFriendlyURLTitleLocalizationId(friendlyURLTitleLocalizationId);
	}

	/**
	* Sets the group ID of this friendly url title localization.
	*
	* @param groupId the group ID of this friendly url title localization
	*/
	@Override
	public void setGroupId(long groupId) {
		_friendlyURLTitleLocalization.setGroupId(groupId);
	}

	/**
	* Sets the language ID of this friendly url title localization.
	*
	* @param languageId the language ID of this friendly url title localization
	*/
	@Override
	public void setLanguageId(java.lang.String languageId) {
		_friendlyURLTitleLocalization.setLanguageId(languageId);
	}

	@Override
	public void setNew(boolean n) {
		_friendlyURLTitleLocalization.setNew(n);
	}

	/**
	* Sets the primary key of this friendly url title localization.
	*
	* @param primaryKey the primary key of this friendly url title localization
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_friendlyURLTitleLocalization.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_friendlyURLTitleLocalization.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the url title of this friendly url title localization.
	*
	* @param urlTitle the url title of this friendly url title localization
	*/
	@Override
	public void setUrlTitle(java.lang.String urlTitle) {
		_friendlyURLTitleLocalization.setUrlTitle(urlTitle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FriendlyURLTitleLocalizationWrapper)) {
			return false;
		}

		FriendlyURLTitleLocalizationWrapper friendlyURLTitleLocalizationWrapper = (FriendlyURLTitleLocalizationWrapper)obj;

		if (Objects.equals(_friendlyURLTitleLocalization,
					friendlyURLTitleLocalizationWrapper._friendlyURLTitleLocalization)) {
			return true;
		}

		return false;
	}

	@Override
	public FriendlyURLTitleLocalization getWrappedModel() {
		return _friendlyURLTitleLocalization;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _friendlyURLTitleLocalization.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _friendlyURLTitleLocalization.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_friendlyURLTitleLocalization.resetOriginalValues();
	}

	private final FriendlyURLTitleLocalization _friendlyURLTitleLocalization;
}