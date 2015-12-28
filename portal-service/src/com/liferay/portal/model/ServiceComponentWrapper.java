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

package com.liferay.portal.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.Validator;

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
@ProviderType
public class ServiceComponentWrapper implements ServiceComponent,
	ModelWrapper<ServiceComponent> {
	public ServiceComponentWrapper(ServiceComponent serviceComponent) {
		_serviceComponent = serviceComponent;
	}

	@Override
	public Class<?> getModelClass() {
		return ServiceComponent.class;
	}

	@Override
	public String getModelClassName() {
		return ServiceComponent.class.getName();
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

	@Override
	public java.lang.Object clone() {
		return new ServiceComponentWrapper((ServiceComponent)_serviceComponent.clone());
	}

	@Override
	public int compareTo(
		com.liferay.portal.model.ServiceComponent serviceComponent) {
		return _serviceComponent.compareTo(serviceComponent);
	}

	/**
	* Returns the build date of this service component.
	*
	* @return the build date of this service component
	*/
	@Override
	public long getBuildDate() {
		return _serviceComponent.getBuildDate();
	}

	/**
	* Returns the build namespace of this service component.
	*
	* @return the build namespace of this service component
	*/
	@Override
	public java.lang.String getBuildNamespace() {
		return _serviceComponent.getBuildNamespace();
	}

	/**
	* Returns the build number of this service component.
	*
	* @return the build number of this service component
	*/
	@Override
	public long getBuildNumber() {
		return _serviceComponent.getBuildNumber();
	}

	/**
	* Returns the data of this service component.
	*
	* @return the data of this service component
	*/
	@Override
	public java.lang.String getData() {
		return _serviceComponent.getData();
	}

	@Override
	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _serviceComponent.getExpandoBridge();
	}

	@Override
	public java.lang.String getIndexesSQL() {
		return _serviceComponent.getIndexesSQL();
	}

	/**
	* Returns the mvcc version of this service component.
	*
	* @return the mvcc version of this service component
	*/
	@Override
	public long getMvccVersion() {
		return _serviceComponent.getMvccVersion();
	}

	/**
	* Returns the primary key of this service component.
	*
	* @return the primary key of this service component
	*/
	@Override
	public long getPrimaryKey() {
		return _serviceComponent.getPrimaryKey();
	}

	@Override
	public java.io.Serializable getPrimaryKeyObj() {
		return _serviceComponent.getPrimaryKeyObj();
	}

	@Override
	public java.lang.String getSequencesSQL() {
		return _serviceComponent.getSequencesSQL();
	}

	/**
	* Returns the service component ID of this service component.
	*
	* @return the service component ID of this service component
	*/
	@Override
	public long getServiceComponentId() {
		return _serviceComponent.getServiceComponentId();
	}

	@Override
	public java.lang.String getTablesSQL() {
		return _serviceComponent.getTablesSQL();
	}

	@Override
	public int hashCode() {
		return _serviceComponent.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _serviceComponent.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _serviceComponent.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _serviceComponent.isNew();
	}

	@Override
	public void persist() {
		_serviceComponent.persist();
	}

	/**
	* Sets the build date of this service component.
	*
	* @param buildDate the build date of this service component
	*/
	@Override
	public void setBuildDate(long buildDate) {
		_serviceComponent.setBuildDate(buildDate);
	}

	/**
	* Sets the build namespace of this service component.
	*
	* @param buildNamespace the build namespace of this service component
	*/
	@Override
	public void setBuildNamespace(java.lang.String buildNamespace) {
		_serviceComponent.setBuildNamespace(buildNamespace);
	}

	/**
	* Sets the build number of this service component.
	*
	* @param buildNumber the build number of this service component
	*/
	@Override
	public void setBuildNumber(long buildNumber) {
		_serviceComponent.setBuildNumber(buildNumber);
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_serviceComponent.setCachedModel(cachedModel);
	}

	/**
	* Sets the data of this service component.
	*
	* @param data the data of this service component
	*/
	@Override
	public void setData(java.lang.String data) {
		_serviceComponent.setData(data);
	}

	@Override
	public void setExpandoBridgeAttributes(BaseModel<?> baseModel) {
		_serviceComponent.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portlet.expando.model.ExpandoBridge expandoBridge) {
		_serviceComponent.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_serviceComponent.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the mvcc version of this service component.
	*
	* @param mvccVersion the mvcc version of this service component
	*/
	@Override
	public void setMvccVersion(long mvccVersion) {
		_serviceComponent.setMvccVersion(mvccVersion);
	}

	@Override
	public void setNew(boolean n) {
		_serviceComponent.setNew(n);
	}

	/**
	* Sets the primary key of this service component.
	*
	* @param primaryKey the primary key of this service component
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_serviceComponent.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(java.io.Serializable primaryKeyObj) {
		_serviceComponent.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	* Sets the service component ID of this service component.
	*
	* @param serviceComponentId the service component ID of this service component
	*/
	@Override
	public void setServiceComponentId(long serviceComponentId) {
		_serviceComponent.setServiceComponentId(serviceComponentId);
	}

	@Override
	public CacheModel<com.liferay.portal.model.ServiceComponent> toCacheModel() {
		return _serviceComponent.toCacheModel();
	}

	@Override
	public com.liferay.portal.model.ServiceComponent toEscapedModel() {
		return new ServiceComponentWrapper(_serviceComponent.toEscapedModel());
	}

	@Override
	public java.lang.String toString() {
		return _serviceComponent.toString();
	}

	@Override
	public com.liferay.portal.model.ServiceComponent toUnescapedModel() {
		return new ServiceComponentWrapper(_serviceComponent.toUnescapedModel());
	}

	@Override
	public java.lang.String toXmlString() {
		return _serviceComponent.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof ServiceComponentWrapper)) {
			return false;
		}

		ServiceComponentWrapper serviceComponentWrapper = (ServiceComponentWrapper)obj;

		if (Validator.equals(_serviceComponent,
					serviceComponentWrapper._serviceComponent)) {
			return true;
		}

		return false;
	}

	@Override
	public ServiceComponent getWrappedModel() {
		return _serviceComponent;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _serviceComponent.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _serviceComponent.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_serviceComponent.resetOriginalValues();
	}

	private final ServiceComponent _serviceComponent;
}