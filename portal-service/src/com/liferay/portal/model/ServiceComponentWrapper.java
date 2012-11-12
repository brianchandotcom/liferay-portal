/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ServiceComponent}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ServiceComponent
 * @generated
 */
public class ServiceComponentWrapper implements ServiceComponent,
	ModelWrapper<ServiceComponent> {
	public ServiceComponentWrapper(ServiceComponent serviceComponent) {
		_serviceComponent = serviceComponent;
	}

	public Class<?> getModelClass() {
		return ServiceComponent.class;
	}

	public String getModelClassName() {
		return ServiceComponent.class.getName();
	}

	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("serviceComponentId", getServiceComponentId());
		attributes.put("buildNamespace", getBuildNamespace());
		attributes.put("buildNumber", getBuildNumber());
		attributes.put("buildDate", getBuildDate());
		attributes.put("data", getData());

		return attributes;
	}

	public void setModelAttributes(Map<String, Object> attributes) {
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
	* Returns the primary key of this service component.
	*
	* @return the primary key of this service component
	*/
	public long getPrimaryKey() {
		return _serviceComponent.getPrimaryKey();
	}

	/**
	* Sets the primary key of this service component.
	*
	* @param primaryKey the primary key of this service component
	*/
	public void setPrimaryKey(long primaryKey) {
		_serviceComponent.setPrimaryKey(primaryKey);
	}

	/**
	* Returns the service component ID of this service component.
	*
	* @return the service component ID of this service component
	*/
	public long getServiceComponentId() {
		return _serviceComponent.getServiceComponentId();
	}

	/**
	* Sets the service component ID of this service component.
	*
	* @param serviceComponentId the service component ID of this service component
	*/
	public void setServiceComponentId(long serviceComponentId) {
		_serviceComponent.setServiceComponentId(serviceComponentId);
	}

	/**
	* Returns the build namespace of this service component.
	*
	* @return the build namespace of this service component
	*/
	public java.lang.String getBuildNamespace() {
		return _serviceComponent.getBuildNamespace();
	}

	/**
	* Sets the build namespace of this service component.
	*
	* @param buildNamespace the build namespace of this service component
	*/
	public void setBuildNamespace(java.lang.String buildNamespace) {
		_serviceComponent.setBuildNamespace(buildNamespace);
	}

	/**
	* Returns the build number of this service component.
	*
	* @return the build number of this service component
	*/
	public long getBuildNumber() {
		return _serviceComponent.getBuildNumber();
	}

	/**
	* Sets the build number of this service component.
	*
	* @param buildNumber the build number of this service component
	*/
	public void setBuildNumber(long buildNumber) {
		_serviceComponent.setBuildNumber(buildNumber);
	}

	/**
	* Returns the build date of this service component.
	*
	* @return the build date of this service component
	*/
	public long getBuildDate() {
		return _serviceComponent.getBuildDate();
	}

	/**
	* Sets the build date of this service component.
	*
	* @param buildDate the build date of this service component
	*/
	public void setBuildDate(long buildDate) {
		_serviceComponent.setBuildDate(buildDate);
	}

	/**
	* Returns the data of this service component.
	*
	* @return the data of this service component
	*/
	public java.lang.String getData() {
		return _serviceComponent.getData();
	}

	/**
	* Sets the data of this service component.
	*
	* @param data the data of this service component
	*/
	public void setData(java.lang.String data) {
		_serviceComponent.setData(data);
	}

	public boolean isNew() {
		return _serviceComponent.isNew();
	}

	public void setNew(boolean n) {
		_serviceComponent.setNew(n);
	}

	public boolean isCachedModel() {
		return _serviceComponent.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_serviceComponent.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _serviceComponent.isEscapedModel();
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _serviceComponent.getPrimaryKeyObj();
	}

	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_serviceComponent.setPrimaryKeyObj(primaryKeyObj);
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _serviceComponent.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_serviceComponent.setExpandoBridgeAttributes(serviceContext);
	}

	@Override
	public java.lang.Object clone() {
		return new ServiceComponentWrapper((ServiceComponent)_serviceComponent.clone());
	}

	public int compareTo(
		com.liferay.portal.model.ServiceComponent serviceComponent) {
		return _serviceComponent.compareTo(serviceComponent);
	}

	@Override
	public int hashCode() {
		return _serviceComponent.hashCode();
	}

	public com.liferay.portal.model.CacheModel<com.liferay.portal.model.ServiceComponent> toCacheModel() {
		return _serviceComponent.toCacheModel();
	}

	public com.liferay.portal.model.ServiceComponent toEscapedModel() {
		return new ServiceComponentWrapper(_serviceComponent.toEscapedModel());
	}

	public com.liferay.portal.model.ServiceComponent toUnescapedModel() {
		return new ServiceComponentWrapper(_serviceComponent.toUnescapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _serviceComponent.toString();
	}

	public java.lang.String toXmlString() {
		return _serviceComponent.toXmlString();
	}

	public void persist()
		throws com.liferay.portal.kernel.exception.SystemException {
		_serviceComponent.persist();
	}

	public java.lang.String getIndexesSQL() {
		return _serviceComponent.getIndexesSQL();
	}

	public java.lang.String getSequencesSQL() {
		return _serviceComponent.getSequencesSQL();
	}

	public java.lang.String getTablesSQL() {
		return _serviceComponent.getTablesSQL();
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedModel}
	 */
	public ServiceComponent getWrappedServiceComponent() {
		return _serviceComponent;
	}

	public ServiceComponent getWrappedModel() {
		return _serviceComponent;
	}

	public void resetOriginalValues() {
		_serviceComponent.resetOriginalValues();
	}

	private ServiceComponent _serviceComponent;
}