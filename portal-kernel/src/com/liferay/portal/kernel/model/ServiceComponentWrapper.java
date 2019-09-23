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

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ServiceComponent}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ServiceComponent
 * @generated
 */
public class ServiceComponentWrapper
	extends BaseModelWrapper<ServiceComponent>
	implements ModelWrapper<ServiceComponent>, ServiceComponent {

	public ServiceComponentWrapper(ServiceComponent serviceComponent) {
		super(serviceComponent);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("serviceComponentId", getServiceComponentId());
		attributes.put("buildNamespace", getBuildNamespace());
		attributes.put("buildNumber", getBuildNumber());
		attributes.put("buildDate", getBuildDate());
		attributes.put("data", getData());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long serviceComponentId = (Long)attributes.get("serviceComponentId");

		if (serviceComponentId != null) {
			setServiceComponentId(serviceComponentId);
		}

		String buildNamespace = (String)attributes.get("buildNamespace");

		if (buildNamespace != null) {
			setBuildNamespace(buildNamespace);
		}

		Long buildNumber = (Long)attributes.get("buildNumber");

		if (buildNumber != null) {
			setBuildNumber(buildNumber);
		}

		Long buildDate = (Long)attributes.get("buildDate");

		if (buildDate != null) {
			setBuildDate(buildDate);
		}

		String data = (String)attributes.get("data");

		if (data != null) {
			setData(data);
		}
	}

	/**
	 * Returns the build date of this service component.
	 *
	 * @return the build date of this service component
	 */
	@Override
	public long getBuildDate() {
		return model.getBuildDate();
	}

	/**
	 * Returns the build namespace of this service component.
	 *
	 * @return the build namespace of this service component
	 */
	@Override
	public String getBuildNamespace() {
		return model.getBuildNamespace();
	}

	/**
	 * Returns the build number of this service component.
	 *
	 * @return the build number of this service component
	 */
	@Override
	public long getBuildNumber() {
		return model.getBuildNumber();
	}

	/**
	 * Returns the data of this service component.
	 *
	 * @return the data of this service component
	 */
	@Override
	public String getData() {
		return model.getData();
	}

	@Override
	public String getIndexesSQL() {
		return model.getIndexesSQL();
	}

	/**
	 * Returns the mvcc version of this service component.
	 *
	 * @return the mvcc version of this service component
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this service component.
	 *
	 * @return the primary key of this service component
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public String getSequencesSQL() {
		return model.getSequencesSQL();
	}

	/**
	 * Returns the service component ID of this service component.
	 *
	 * @return the service component ID of this service component
	 */
	@Override
	public long getServiceComponentId() {
		return model.getServiceComponentId();
	}

	@Override
	public String getTablesSQL() {
		return model.getTablesSQL();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a service component model instance should use the <code>ServiceComponent</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the build date of this service component.
	 *
	 * @param buildDate the build date of this service component
	 */
	@Override
	public void setBuildDate(long buildDate) {
		model.setBuildDate(buildDate);
	}

	/**
	 * Sets the build namespace of this service component.
	 *
	 * @param buildNamespace the build namespace of this service component
	 */
	@Override
	public void setBuildNamespace(String buildNamespace) {
		model.setBuildNamespace(buildNamespace);
	}

	/**
	 * Sets the build number of this service component.
	 *
	 * @param buildNumber the build number of this service component
	 */
	@Override
	public void setBuildNumber(long buildNumber) {
		model.setBuildNumber(buildNumber);
	}

	/**
	 * Sets the data of this service component.
	 *
	 * @param data the data of this service component
	 */
	@Override
	public void setData(String data) {
		model.setData(data);
	}

	/**
	 * Sets the mvcc version of this service component.
	 *
	 * @param mvccVersion the mvcc version of this service component
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this service component.
	 *
	 * @param primaryKey the primary key of this service component
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the service component ID of this service component.
	 *
	 * @param serviceComponentId the service component ID of this service component
	 */
	@Override
	public void setServiceComponentId(long serviceComponentId) {
		model.setServiceComponentId(serviceComponentId);
	}

	@Override
	protected ServiceComponentWrapper wrap(ServiceComponent serviceComponent) {
		return new ServiceComponentWrapper(serviceComponent);
	}

}