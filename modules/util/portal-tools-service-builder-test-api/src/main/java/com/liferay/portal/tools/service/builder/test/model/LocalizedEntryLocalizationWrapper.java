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

package com.liferay.portal.tools.service.builder.test.model;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link LocalizedEntryLocalization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LocalizedEntryLocalization
 * @generated
 */
public class LocalizedEntryLocalizationWrapper
	implements LocalizedEntryLocalization,
			   ModelWrapper<LocalizedEntryLocalization> {

	public LocalizedEntryLocalizationWrapper(
		LocalizedEntryLocalization localizedEntryLocalization) {

		_localizedEntryLocalization = localizedEntryLocalization;
	}

	@Override
	public Class<?> getModelClass() {
		return LocalizedEntryLocalization.class;
	}

	@Override
	public String getModelClassName() {
		return LocalizedEntryLocalization.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"localizedEntryLocalizationId", getLocalizedEntryLocalizationId());
		attributes.put("localizedEntryId", getLocalizedEntryId());
		attributes.put("languageId", getLanguageId());
		attributes.put("title", getTitle());
		attributes.put("content", getContent());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long localizedEntryLocalizationId = (Long)attributes.get(
			"localizedEntryLocalizationId");

		if (localizedEntryLocalizationId != null) {
			setLocalizedEntryLocalizationId(localizedEntryLocalizationId);
		}

		Long localizedEntryId = (Long)attributes.get("localizedEntryId");

		if (localizedEntryId != null) {
			setLocalizedEntryId(localizedEntryId);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		String title = (String)attributes.get("title");

		if (title != null) {
			setTitle(title);
		}

		String content = (String)attributes.get("content");

		if (content != null) {
			setContent(content);
		}
	}

	@Override
	public Object clone() {
		return new LocalizedEntryLocalizationWrapper(
			(LocalizedEntryLocalization)_localizedEntryLocalization.clone());
	}

	@Override
	public int compareTo(
		LocalizedEntryLocalization localizedEntryLocalization) {

		return _localizedEntryLocalization.compareTo(
			localizedEntryLocalization);
	}

	/**
	 * Returns the content of this localized entry localization.
	 *
	 * @return the content of this localized entry localization
	 */
	@Override
	public String getContent() {
		return _localizedEntryLocalization.getContent();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _localizedEntryLocalization.getExpandoBridge();
	}

	/**
	 * Returns the language ID of this localized entry localization.
	 *
	 * @return the language ID of this localized entry localization
	 */
	@Override
	public String getLanguageId() {
		return _localizedEntryLocalization.getLanguageId();
	}

	/**
	 * Returns the localized entry ID of this localized entry localization.
	 *
	 * @return the localized entry ID of this localized entry localization
	 */
	@Override
	public long getLocalizedEntryId() {
		return _localizedEntryLocalization.getLocalizedEntryId();
	}

	/**
	 * Returns the localized entry localization ID of this localized entry localization.
	 *
	 * @return the localized entry localization ID of this localized entry localization
	 */
	@Override
	public long getLocalizedEntryLocalizationId() {
		return _localizedEntryLocalization.getLocalizedEntryLocalizationId();
	}

	/**
	 * Returns the mvcc version of this localized entry localization.
	 *
	 * @return the mvcc version of this localized entry localization
	 */
	@Override
	public long getMvccVersion() {
		return _localizedEntryLocalization.getMvccVersion();
	}

	/**
	 * Returns the primary key of this localized entry localization.
	 *
	 * @return the primary key of this localized entry localization
	 */
	@Override
	public long getPrimaryKey() {
		return _localizedEntryLocalization.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _localizedEntryLocalization.getPrimaryKeyObj();
	}

	/**
	 * Returns the title of this localized entry localization.
	 *
	 * @return the title of this localized entry localization
	 */
	@Override
	public String getTitle() {
		return _localizedEntryLocalization.getTitle();
	}

	@Override
	public int hashCode() {
		return _localizedEntryLocalization.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _localizedEntryLocalization.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _localizedEntryLocalization.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _localizedEntryLocalization.isNew();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_localizedEntryLocalization.setCachedModel(cachedModel);
	}

	/**
	 * Sets the content of this localized entry localization.
	 *
	 * @param content the content of this localized entry localization
	 */
	@Override
	public void setContent(String content) {
		_localizedEntryLocalization.setContent(content);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_localizedEntryLocalization.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_localizedEntryLocalization.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_localizedEntryLocalization.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the language ID of this localized entry localization.
	 *
	 * @param languageId the language ID of this localized entry localization
	 */
	@Override
	public void setLanguageId(String languageId) {
		_localizedEntryLocalization.setLanguageId(languageId);
	}

	/**
	 * Sets the localized entry ID of this localized entry localization.
	 *
	 * @param localizedEntryId the localized entry ID of this localized entry localization
	 */
	@Override
	public void setLocalizedEntryId(long localizedEntryId) {
		_localizedEntryLocalization.setLocalizedEntryId(localizedEntryId);
	}

	/**
	 * Sets the localized entry localization ID of this localized entry localization.
	 *
	 * @param localizedEntryLocalizationId the localized entry localization ID of this localized entry localization
	 */
	@Override
	public void setLocalizedEntryLocalizationId(
		long localizedEntryLocalizationId) {

		_localizedEntryLocalization.setLocalizedEntryLocalizationId(
			localizedEntryLocalizationId);
	}

	/**
	 * Sets the mvcc version of this localized entry localization.
	 *
	 * @param mvccVersion the mvcc version of this localized entry localization
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		_localizedEntryLocalization.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_localizedEntryLocalization.setNew(n);
	}

	/**
	 * Sets the primary key of this localized entry localization.
	 *
	 * @param primaryKey the primary key of this localized entry localization
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_localizedEntryLocalization.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_localizedEntryLocalization.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the title of this localized entry localization.
	 *
	 * @param title the title of this localized entry localization
	 */
	@Override
	public void setTitle(String title) {
		_localizedEntryLocalization.setTitle(title);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel
		<LocalizedEntryLocalization> toCacheModel() {

		return _localizedEntryLocalization.toCacheModel();
	}

	@Override
	public LocalizedEntryLocalization toEscapedModel() {
		return new LocalizedEntryLocalizationWrapper(
			_localizedEntryLocalization.toEscapedModel());
	}

	@Override
	public String toString() {
		return _localizedEntryLocalization.toString();
	}

	@Override
	public LocalizedEntryLocalization toUnescapedModel() {
		return new LocalizedEntryLocalizationWrapper(
			_localizedEntryLocalization.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _localizedEntryLocalization.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LocalizedEntryLocalizationWrapper)) {
			return false;
		}

		LocalizedEntryLocalizationWrapper localizedEntryLocalizationWrapper =
			(LocalizedEntryLocalizationWrapper)obj;

		if (Objects.equals(
				_localizedEntryLocalization,
				localizedEntryLocalizationWrapper.
					_localizedEntryLocalization)) {

			return true;
		}

		return false;
	}

	@Override
	public LocalizedEntryLocalization getWrappedModel() {
		return _localizedEntryLocalization;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _localizedEntryLocalization.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _localizedEntryLocalization.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_localizedEntryLocalization.resetOriginalValues();
	}

	private final LocalizedEntryLocalization _localizedEntryLocalization;

}