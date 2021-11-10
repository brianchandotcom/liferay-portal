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

package com.liferay.remote.app.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link RemoteAppEntryLocalization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RemoteAppEntryLocalization
 * @generated
 */
public class RemoteAppEntryLocalizationWrapper
	extends BaseModelWrapper<RemoteAppEntryLocalization>
	implements ModelWrapper<RemoteAppEntryLocalization>,
			   RemoteAppEntryLocalization {

	public RemoteAppEntryLocalizationWrapper(
		RemoteAppEntryLocalization remoteAppEntryLocalization) {

		super(remoteAppEntryLocalization);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put(
			"remoteAppEntryLocalizationId", getRemoteAppEntryLocalizationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("remoteAppEntryId", getRemoteAppEntryId());
		attributes.put("languageId", getLanguageId());
		attributes.put("description", getDescription());
		attributes.put("name", getName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long remoteAppEntryLocalizationId = (Long)attributes.get(
			"remoteAppEntryLocalizationId");

		if (remoteAppEntryLocalizationId != null) {
			setRemoteAppEntryLocalizationId(remoteAppEntryLocalizationId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long remoteAppEntryId = (Long)attributes.get("remoteAppEntryId");

		if (remoteAppEntryId != null) {
			setRemoteAppEntryId(remoteAppEntryId);
		}

		String languageId = (String)attributes.get("languageId");

		if (languageId != null) {
			setLanguageId(languageId);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}
	}

	@Override
	public RemoteAppEntryLocalization cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this remote app entry localization.
	 *
	 * @return the company ID of this remote app entry localization
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the description of this remote app entry localization.
	 *
	 * @return the description of this remote app entry localization
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the language ID of this remote app entry localization.
	 *
	 * @return the language ID of this remote app entry localization
	 */
	@Override
	public String getLanguageId() {
		return model.getLanguageId();
	}

	/**
	 * Returns the mvcc version of this remote app entry localization.
	 *
	 * @return the mvcc version of this remote app entry localization
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the name of this remote app entry localization.
	 *
	 * @return the name of this remote app entry localization
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this remote app entry localization.
	 *
	 * @return the primary key of this remote app entry localization
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the remote app entry ID of this remote app entry localization.
	 *
	 * @return the remote app entry ID of this remote app entry localization
	 */
	@Override
	public long getRemoteAppEntryId() {
		return model.getRemoteAppEntryId();
	}

	/**
	 * Returns the remote app entry localization ID of this remote app entry localization.
	 *
	 * @return the remote app entry localization ID of this remote app entry localization
	 */
	@Override
	public long getRemoteAppEntryLocalizationId() {
		return model.getRemoteAppEntryLocalizationId();
	}

	/**
	 * Sets the company ID of this remote app entry localization.
	 *
	 * @param companyId the company ID of this remote app entry localization
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the description of this remote app entry localization.
	 *
	 * @param description the description of this remote app entry localization
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the language ID of this remote app entry localization.
	 *
	 * @param languageId the language ID of this remote app entry localization
	 */
	@Override
	public void setLanguageId(String languageId) {
		model.setLanguageId(languageId);
	}

	/**
	 * Sets the mvcc version of this remote app entry localization.
	 *
	 * @param mvccVersion the mvcc version of this remote app entry localization
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the name of this remote app entry localization.
	 *
	 * @param name the name of this remote app entry localization
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this remote app entry localization.
	 *
	 * @param primaryKey the primary key of this remote app entry localization
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the remote app entry ID of this remote app entry localization.
	 *
	 * @param remoteAppEntryId the remote app entry ID of this remote app entry localization
	 */
	@Override
	public void setRemoteAppEntryId(long remoteAppEntryId) {
		model.setRemoteAppEntryId(remoteAppEntryId);
	}

	/**
	 * Sets the remote app entry localization ID of this remote app entry localization.
	 *
	 * @param remoteAppEntryLocalizationId the remote app entry localization ID of this remote app entry localization
	 */
	@Override
	public void setRemoteAppEntryLocalizationId(
		long remoteAppEntryLocalizationId) {

		model.setRemoteAppEntryLocalizationId(remoteAppEntryLocalizationId);
	}

	@Override
	protected RemoteAppEntryLocalizationWrapper wrap(
		RemoteAppEntryLocalization remoteAppEntryLocalization) {

		return new RemoteAppEntryLocalizationWrapper(
			remoteAppEntryLocalization);
	}

}