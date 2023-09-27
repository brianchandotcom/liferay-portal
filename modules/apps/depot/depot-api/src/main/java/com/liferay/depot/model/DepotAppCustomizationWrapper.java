/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link DepotAppCustomization}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DepotAppCustomization
 * @generated
 */
public class DepotAppCustomizationWrapper
	extends BaseModelWrapper<DepotAppCustomization>
	implements DepotAppCustomization, ModelWrapper<DepotAppCustomization> {

	public DepotAppCustomizationWrapper(
		DepotAppCustomization depotAppCustomization) {

		super(depotAppCustomization);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("depotAppCustomizationId", getDepotAppCustomizationId());
		attributes.put("companyId", getCompanyId());
		attributes.put("depotEntryId", getDepotEntryId());
		attributes.put("enabled", isEnabled());
		attributes.put("portletId", getPortletId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long depotAppCustomizationId = (Long)attributes.get(
			"depotAppCustomizationId");

		if (depotAppCustomizationId != null) {
			setDepotAppCustomizationId(depotAppCustomizationId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long depotEntryId = (Long)attributes.get("depotEntryId");

		if (depotEntryId != null) {
			setDepotEntryId(depotEntryId);
		}

		Boolean enabled = (Boolean)attributes.get("enabled");

		if (enabled != null) {
			setEnabled(enabled);
		}

		String portletId = (String)attributes.get("portletId");

		if (portletId != null) {
			setPortletId(portletId);
		}
	}

	@Override
	public DepotAppCustomization cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the company ID of this depot app customization.
	 *
	 * @return the company ID of this depot app customization
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the depot app customization ID of this depot app customization.
	 *
	 * @return the depot app customization ID of this depot app customization
	 */
	@Override
	public long getDepotAppCustomizationId() {
		return model.getDepotAppCustomizationId();
	}

	/**
	 * Returns the depot entry ID of this depot app customization.
	 *
	 * @return the depot entry ID of this depot app customization
	 */
	@Override
	public long getDepotEntryId() {
		return model.getDepotEntryId();
	}

	/**
	 * Returns the enabled of this depot app customization.
	 *
	 * @return the enabled of this depot app customization
	 */
	@Override
	public boolean getEnabled() {
		return model.getEnabled();
	}

	/**
	 * Returns the mvcc version of this depot app customization.
	 *
	 * @return the mvcc version of this depot app customization
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the portlet ID of this depot app customization.
	 *
	 * @return the portlet ID of this depot app customization
	 */
	@Override
	public String getPortletId() {
		return model.getPortletId();
	}

	/**
	 * Returns the primary key of this depot app customization.
	 *
	 * @return the primary key of this depot app customization
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns <code>true</code> if this depot app customization is enabled.
	 *
	 * @return <code>true</code> if this depot app customization is enabled; <code>false</code> otherwise
	 */
	@Override
	public boolean isEnabled() {
		return model.isEnabled();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this depot app customization.
	 *
	 * @param companyId the company ID of this depot app customization
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the depot app customization ID of this depot app customization.
	 *
	 * @param depotAppCustomizationId the depot app customization ID of this depot app customization
	 */
	@Override
	public void setDepotAppCustomizationId(long depotAppCustomizationId) {
		model.setDepotAppCustomizationId(depotAppCustomizationId);
	}

	/**
	 * Sets the depot entry ID of this depot app customization.
	 *
	 * @param depotEntryId the depot entry ID of this depot app customization
	 */
	@Override
	public void setDepotEntryId(long depotEntryId) {
		model.setDepotEntryId(depotEntryId);
	}

	/**
	 * Sets whether this depot app customization is enabled.
	 *
	 * @param enabled the enabled of this depot app customization
	 */
	@Override
	public void setEnabled(boolean enabled) {
		model.setEnabled(enabled);
	}

	/**
	 * Sets the mvcc version of this depot app customization.
	 *
	 * @param mvccVersion the mvcc version of this depot app customization
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the portlet ID of this depot app customization.
	 *
	 * @param portletId the portlet ID of this depot app customization
	 */
	@Override
	public void setPortletId(String portletId) {
		model.setPortletId(portletId);
	}

	/**
	 * Sets the primary key of this depot app customization.
	 *
	 * @param primaryKey the primary key of this depot app customization
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
	protected DepotAppCustomizationWrapper wrap(
		DepotAppCustomization depotAppCustomization) {

		return new DepotAppCustomizationWrapper(depotAppCustomization);
	}

}