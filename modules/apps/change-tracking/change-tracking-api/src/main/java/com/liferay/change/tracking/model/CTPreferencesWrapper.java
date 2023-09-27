/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CTPreferences}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CTPreferences
 * @generated
 */
public class CTPreferencesWrapper
	extends BaseModelWrapper<CTPreferences>
	implements CTPreferences, ModelWrapper<CTPreferences> {

	public CTPreferencesWrapper(CTPreferences ctPreferences) {
		super(ctPreferences);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctPreferencesId", getCtPreferencesId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("previousCtCollectionId", getPreviousCtCollectionId());
		attributes.put("confirmationEnabled", isConfirmationEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctPreferencesId = (Long)attributes.get("ctPreferencesId");

		if (ctPreferencesId != null) {
			setCtPreferencesId(ctPreferencesId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		Long previousCtCollectionId = (Long)attributes.get(
			"previousCtCollectionId");

		if (previousCtCollectionId != null) {
			setPreviousCtCollectionId(previousCtCollectionId);
		}

		Boolean confirmationEnabled = (Boolean)attributes.get(
			"confirmationEnabled");

		if (confirmationEnabled != null) {
			setConfirmationEnabled(confirmationEnabled);
		}
	}

	@Override
	public CTPreferences cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this ct preferences.
	 *
	 * @return the company ID of this ct preferences
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the confirmation enabled of this ct preferences.
	 *
	 * @return the confirmation enabled of this ct preferences
	 */
	@Override
	public boolean getConfirmationEnabled() {
		return model.getConfirmationEnabled();
	}

	/**
	 * Returns the ct collection ID of this ct preferences.
	 *
	 * @return the ct collection ID of this ct preferences
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the ct preferences ID of this ct preferences.
	 *
	 * @return the ct preferences ID of this ct preferences
	 */
	@Override
	public long getCtPreferencesId() {
		return model.getCtPreferencesId();
	}

	/**
	 * Returns the mvcc version of this ct preferences.
	 *
	 * @return the mvcc version of this ct preferences
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the previous ct collection ID of this ct preferences.
	 *
	 * @return the previous ct collection ID of this ct preferences
	 */
	@Override
	public long getPreviousCtCollectionId() {
		return model.getPreviousCtCollectionId();
	}

	/**
	 * Returns the primary key of this ct preferences.
	 *
	 * @return the primary key of this ct preferences
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this ct preferences.
	 *
	 * @return the user ID of this ct preferences
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this ct preferences.
	 *
	 * @return the user uuid of this ct preferences
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	/**
	 * Returns <code>true</code> if this ct preferences is confirmation enabled.
	 *
	 * @return <code>true</code> if this ct preferences is confirmation enabled; <code>false</code> otherwise
	 */
	@Override
	public boolean isConfirmationEnabled() {
		return model.isConfirmationEnabled();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this ct preferences.
	 *
	 * @param companyId the company ID of this ct preferences
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets whether this ct preferences is confirmation enabled.
	 *
	 * @param confirmationEnabled the confirmation enabled of this ct preferences
	 */
	@Override
	public void setConfirmationEnabled(boolean confirmationEnabled) {
		model.setConfirmationEnabled(confirmationEnabled);
	}

	/**
	 * Sets the ct collection ID of this ct preferences.
	 *
	 * @param ctCollectionId the ct collection ID of this ct preferences
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the ct preferences ID of this ct preferences.
	 *
	 * @param ctPreferencesId the ct preferences ID of this ct preferences
	 */
	@Override
	public void setCtPreferencesId(long ctPreferencesId) {
		model.setCtPreferencesId(ctPreferencesId);
	}

	/**
	 * Sets the mvcc version of this ct preferences.
	 *
	 * @param mvccVersion the mvcc version of this ct preferences
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the previous ct collection ID of this ct preferences.
	 *
	 * @param previousCtCollectionId the previous ct collection ID of this ct preferences
	 */
	@Override
	public void setPreviousCtCollectionId(long previousCtCollectionId) {
		model.setPreviousCtCollectionId(previousCtCollectionId);
	}

	/**
	 * Sets the primary key of this ct preferences.
	 *
	 * @param primaryKey the primary key of this ct preferences
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this ct preferences.
	 *
	 * @param userId the user ID of this ct preferences
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this ct preferences.
	 *
	 * @param userUuid the user uuid of this ct preferences
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected CTPreferencesWrapper wrap(CTPreferences ctPreferences) {
		return new CTPreferencesWrapper(ctPreferences);
	}

}