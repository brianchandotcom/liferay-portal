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

package com.liferay.marketplace.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link Module}.
 * </p>
 *
 * @author Ryan Park
 * @see Module
 * @generated
 */
public class ModuleWrapper
	extends BaseModelWrapper<Module> implements ModelWrapper<Module>, Module {

	public ModuleWrapper(Module module) {
		super(module);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("uuid", getUuid());
		attributes.put("moduleId", getModuleId());
		attributes.put("companyId", getCompanyId());
		attributes.put("appId", getAppId());
		attributes.put("bundleSymbolicName", getBundleSymbolicName());
		attributes.put("bundleVersion", getBundleVersion());
		attributes.put("contextName", getContextName());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		String uuid = (String)attributes.get("uuid");

		if (uuid != null) {
			setUuid(uuid);
		}

		Long moduleId = (Long)attributes.get("moduleId");

		if (moduleId != null) {
			setModuleId(moduleId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long appId = (Long)attributes.get("appId");

		if (appId != null) {
			setAppId(appId);
		}

		String bundleSymbolicName = (String)attributes.get(
			"bundleSymbolicName");

		if (bundleSymbolicName != null) {
			setBundleSymbolicName(bundleSymbolicName);
		}

		String bundleVersion = (String)attributes.get("bundleVersion");

		if (bundleVersion != null) {
			setBundleVersion(bundleVersion);
		}

		String contextName = (String)attributes.get("contextName");

		if (contextName != null) {
			setContextName(contextName);
		}
	}

	/**
	 * Returns the app ID of this module.
	 *
	 * @return the app ID of this module
	 */
	@Override
	public long getAppId() {
		return model.getAppId();
	}

	/**
	 * Returns the bundle symbolic name of this module.
	 *
	 * @return the bundle symbolic name of this module
	 */
	@Override
	public String getBundleSymbolicName() {
		return model.getBundleSymbolicName();
	}

	/**
	 * Returns the bundle version of this module.
	 *
	 * @return the bundle version of this module
	 */
	@Override
	public String getBundleVersion() {
		return model.getBundleVersion();
	}

	/**
	 * Returns the company ID of this module.
	 *
	 * @return the company ID of this module
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the context name of this module.
	 *
	 * @return the context name of this module
	 */
	@Override
	public String getContextName() {
		return model.getContextName();
	}

	/**
	 * Returns the module ID of this module.
	 *
	 * @return the module ID of this module
	 */
	@Override
	public long getModuleId() {
		return model.getModuleId();
	}

	/**
	 * Returns the primary key of this module.
	 *
	 * @return the primary key of this module
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the uuid of this module.
	 *
	 * @return the uuid of this module
	 */
	@Override
	public String getUuid() {
		return model.getUuid();
	}

	@Override
	public boolean isBundle() {
		return model.isBundle();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a module model instance should use the <code>Module</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the app ID of this module.
	 *
	 * @param appId the app ID of this module
	 */
	@Override
	public void setAppId(long appId) {
		model.setAppId(appId);
	}

	/**
	 * Sets the bundle symbolic name of this module.
	 *
	 * @param bundleSymbolicName the bundle symbolic name of this module
	 */
	@Override
	public void setBundleSymbolicName(String bundleSymbolicName) {
		model.setBundleSymbolicName(bundleSymbolicName);
	}

	/**
	 * Sets the bundle version of this module.
	 *
	 * @param bundleVersion the bundle version of this module
	 */
	@Override
	public void setBundleVersion(String bundleVersion) {
		model.setBundleVersion(bundleVersion);
	}

	/**
	 * Sets the company ID of this module.
	 *
	 * @param companyId the company ID of this module
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the context name of this module.
	 *
	 * @param contextName the context name of this module
	 */
	@Override
	public void setContextName(String contextName) {
		model.setContextName(contextName);
	}

	/**
	 * Sets the module ID of this module.
	 *
	 * @param moduleId the module ID of this module
	 */
	@Override
	public void setModuleId(long moduleId) {
		model.setModuleId(moduleId);
	}

	/**
	 * Sets the primary key of this module.
	 *
	 * @param primaryKey the primary key of this module
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the uuid of this module.
	 *
	 * @param uuid the uuid of this module
	 */
	@Override
	public void setUuid(String uuid) {
		model.setUuid(uuid);
	}

	@Override
	protected ModuleWrapper wrap(Module module) {
		return new ModuleWrapper(module);
	}

}