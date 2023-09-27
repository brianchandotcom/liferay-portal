/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link PortletPreferences}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPreferences
 * @generated
 */
public class PortletPreferencesWrapper
	extends BaseModelWrapper<PortletPreferences>
	implements ModelWrapper<PortletPreferences>, PortletPreferences {

	public PortletPreferencesWrapper(PortletPreferences portletPreferences) {
		super(portletPreferences);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("portletPreferencesId", getPortletPreferencesId());
		attributes.put("companyId", getCompanyId());
		attributes.put("ownerId", getOwnerId());
		attributes.put("ownerType", getOwnerType());
		attributes.put("plid", getPlid());
		attributes.put("portletId", getPortletId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		Long portletPreferencesId = (Long)attributes.get(
			"portletPreferencesId");

		if (portletPreferencesId != null) {
			setPortletPreferencesId(portletPreferencesId);
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

		Long plid = (Long)attributes.get("plid");

		if (plid != null) {
			setPlid(plid);
		}

		String portletId = (String)attributes.get("portletId");

		if (portletId != null) {
			setPortletId(portletId);
		}
	}

	@Override
	public PortletPreferences cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this portlet preferences.
	 *
	 * @return the company ID of this portlet preferences
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this portlet preferences.
	 *
	 * @return the ct collection ID of this portlet preferences
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	/**
	 * Returns the mvcc version of this portlet preferences.
	 *
	 * @return the mvcc version of this portlet preferences
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the owner ID of this portlet preferences.
	 *
	 * @return the owner ID of this portlet preferences
	 */
	@Override
	public long getOwnerId() {
		return model.getOwnerId();
	}

	/**
	 * Returns the owner type of this portlet preferences.
	 *
	 * @return the owner type of this portlet preferences
	 */
	@Override
	public int getOwnerType() {
		return model.getOwnerType();
	}

	/**
	 * Returns the plid of this portlet preferences.
	 *
	 * @return the plid of this portlet preferences
	 */
	@Override
	public long getPlid() {
		return model.getPlid();
	}

	/**
	 * Returns the portlet ID of this portlet preferences.
	 *
	 * @return the portlet ID of this portlet preferences
	 */
	@Override
	public String getPortletId() {
		return model.getPortletId();
	}

	/**
	 * Returns the portlet preferences ID of this portlet preferences.
	 *
	 * @return the portlet preferences ID of this portlet preferences
	 */
	@Override
	public long getPortletPreferencesId() {
		return model.getPortletPreferencesId();
	}

	/**
	 * Returns the primary key of this portlet preferences.
	 *
	 * @return the primary key of this portlet preferences
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this portlet preferences.
	 *
	 * @param companyId the company ID of this portlet preferences
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this portlet preferences.
	 *
	 * @param ctCollectionId the ct collection ID of this portlet preferences
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the mvcc version of this portlet preferences.
	 *
	 * @param mvccVersion the mvcc version of this portlet preferences
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the owner ID of this portlet preferences.
	 *
	 * @param ownerId the owner ID of this portlet preferences
	 */
	@Override
	public void setOwnerId(long ownerId) {
		model.setOwnerId(ownerId);
	}

	/**
	 * Sets the owner type of this portlet preferences.
	 *
	 * @param ownerType the owner type of this portlet preferences
	 */
	@Override
	public void setOwnerType(int ownerType) {
		model.setOwnerType(ownerType);
	}

	/**
	 * Sets the plid of this portlet preferences.
	 *
	 * @param plid the plid of this portlet preferences
	 */
	@Override
	public void setPlid(long plid) {
		model.setPlid(plid);
	}

	/**
	 * Sets the portlet ID of this portlet preferences.
	 *
	 * @param portletId the portlet ID of this portlet preferences
	 */
	@Override
	public void setPortletId(String portletId) {
		model.setPortletId(portletId);
	}

	/**
	 * Sets the portlet preferences ID of this portlet preferences.
	 *
	 * @param portletPreferencesId the portlet preferences ID of this portlet preferences
	 */
	@Override
	public void setPortletPreferencesId(long portletPreferencesId) {
		model.setPortletPreferencesId(portletPreferencesId);
	}

	/**
	 * Sets the primary key of this portlet preferences.
	 *
	 * @param primaryKey the primary key of this portlet preferences
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	public Map<String, Function<PortletPreferences, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<PortletPreferences, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected PortletPreferencesWrapper wrap(
		PortletPreferences portletPreferences) {

		return new PortletPreferencesWrapper(portletPreferences);
	}

}