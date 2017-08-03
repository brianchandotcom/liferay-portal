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

package com.liferay.portal.resiliency.spi.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.resiliency.spi.service.ClpSerializer;
import com.liferay.portal.resiliency.spi.service.SPIDefinitionLocalServiceUtil;

import java.io.Serializable;

import java.lang.reflect.Method;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @generated
 */
@ProviderType
public class SPIDefinitionClp extends BaseModelImpl<SPIDefinition>
	implements SPIDefinition {
	public SPIDefinitionClp() {
	}

	@Override
	public Class<?> getModelClass() {
		return SPIDefinition.class;
	}

	@Override
	public String getModelClassName() {
		return SPIDefinition.class.getName();
	}

	@Override
	public long getPrimaryKey() {
		return _spiDefinitionId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setSpiDefinitionId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _spiDefinitionId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("spiDefinitionId", getSpiDefinitionId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("connectorAddress", getConnectorAddress());
		attributes.put("connectorPort", getConnectorPort());
		attributes.put("description", getDescription());
		attributes.put("jvmArguments", getJvmArguments());
		attributes.put("portletIds", getPortletIds());
		attributes.put("servletContextNames", getServletContextNames());
		attributes.put("typeSettings", getTypeSettings());
		attributes.put("status", getStatus());
		attributes.put("statusMessage", getStatusMessage());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long spiDefinitionId = (Long)attributes.get("spiDefinitionId");

		if (spiDefinitionId != null) {
			setSpiDefinitionId(spiDefinitionId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String connectorAddress = (String)attributes.get("connectorAddress");

		if (connectorAddress != null) {
			setConnectorAddress(connectorAddress);
		}

		Integer connectorPort = (Integer)attributes.get("connectorPort");

		if (connectorPort != null) {
			setConnectorPort(connectorPort);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}

		String jvmArguments = (String)attributes.get("jvmArguments");

		if (jvmArguments != null) {
			setJvmArguments(jvmArguments);
		}

		String portletIds = (String)attributes.get("portletIds");

		if (portletIds != null) {
			setPortletIds(portletIds);
		}

		String servletContextNames = (String)attributes.get(
				"servletContextNames");

		if (servletContextNames != null) {
			setServletContextNames(servletContextNames);
		}

		String typeSettings = (String)attributes.get("typeSettings");

		if (typeSettings != null) {
			setTypeSettings(typeSettings);
		}

		Integer status = (Integer)attributes.get("status");

		if (status != null) {
			setStatus(status);
		}

		String statusMessage = (String)attributes.get("statusMessage");

		if (statusMessage != null) {
			setStatusMessage(statusMessage);
		}

		_entityCacheEnabled = GetterUtil.getBoolean("entityCacheEnabled");
		_finderCacheEnabled = GetterUtil.getBoolean("finderCacheEnabled");
	}

	@Override
	public long getSpiDefinitionId() {
		return _spiDefinitionId;
	}

	@Override
	public void setSpiDefinitionId(long spiDefinitionId) {
		_spiDefinitionId = spiDefinitionId;

		if (_spiDefinitionRemoteModel != null) {
			try {
				Class<?> clazz = _spiDefinitionRemoteModel.getClass();

				Method method = clazz.getMethod("setSpiDefinitionId", long.class);

				method.invoke(_spiDefinitionRemoteModel, spiDefinitionId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;

		if (_spiDefinitionRemoteModel != null) {
			try {
				Class<?> clazz = _spiDefinitionRemoteModel.getClass();

				Method method = clazz.getMethod("setCompanyId", long.class);

				method.invoke(_spiDefinitionRemoteModel, companyId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;

		if (_spiDefinitionRemoteModel != null) {
			try {
				Class<?> clazz = _spiDefinitionRemoteModel.getClass();

				Method method = clazz.getMethod("setUserId", long.class);

				method.invoke(_spiDefinitionRemoteModel, userId);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return StringPool.BLANK;
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@Override
	public String getUserName() {
		return _userName;
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;

		if (_spiDefinitionRemoteModel != null) {
			try {
				Class<?> clazz = _spiDefinitionRemoteModel.getClass();

				Method method = clazz.getMethod("setUserName", String.class);

				method.invoke(_spiDefinitionRemoteModel, userName);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;

		if (_spiDefinitionRemoteModel != null) {
			try {
				Class<?> clazz = _spiDefinitionRemoteModel.getClass();

				Method method = clazz.getMethod("setCreateDate", Date.class);

				method.invoke(_spiDefinitionRemoteModel, createDate);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;

		if (_spiDefinitionRemoteModel != null) {
			try {
				Class<?> clazz = _spiDefinitionRemoteModel.getClass();

				Method method = clazz.getMethod("setModifiedDate", Date.class);

				method.invoke(_spiDefinitionRemoteModel, modifiedDate);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public void setName(String name) {
		_name = name;

		if (_spiDefinitionRemoteModel != null) {
			try {
				Class<?> clazz = _spiDefinitionRemoteModel.getClass();

				Method method = clazz.getMethod("setName", String.class);

				method.invoke(_spiDefinitionRemoteModel, name);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getConnectorAddress() {
		return _connectorAddress;
	}

	@Override
	public void setConnectorAddress(String connectorAddress) {
		_connectorAddress = connectorAddress;

		if (_spiDefinitionRemoteModel != null) {
			try {
				Class<?> clazz = _spiDefinitionRemoteModel.getClass();

				Method method = clazz.getMethod("setConnectorAddress",
						String.class);

				method.invoke(_spiDefinitionRemoteModel, connectorAddress);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public int getConnectorPort() {
		return _connectorPort;
	}

	@Override
	public void setConnectorPort(int connectorPort) {
		_connectorPort = connectorPort;

		if (_spiDefinitionRemoteModel != null) {
			try {
				Class<?> clazz = _spiDefinitionRemoteModel.getClass();

				Method method = clazz.getMethod("setConnectorPort", int.class);

				method.invoke(_spiDefinitionRemoteModel, connectorPort);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getDescription() {
		return _description;
	}

	@Override
	public void setDescription(String description) {
		_description = description;

		if (_spiDefinitionRemoteModel != null) {
			try {
				Class<?> clazz = _spiDefinitionRemoteModel.getClass();

				Method method = clazz.getMethod("setDescription", String.class);

				method.invoke(_spiDefinitionRemoteModel, description);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getJvmArguments() {
		return _jvmArguments;
	}

	@Override
	public void setJvmArguments(String jvmArguments) {
		_jvmArguments = jvmArguments;

		if (_spiDefinitionRemoteModel != null) {
			try {
				Class<?> clazz = _spiDefinitionRemoteModel.getClass();

				Method method = clazz.getMethod("setJvmArguments", String.class);

				method.invoke(_spiDefinitionRemoteModel, jvmArguments);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getPortletIds() {
		return _portletIds;
	}

	@Override
	public void setPortletIds(String portletIds) {
		_portletIds = portletIds;

		if (_spiDefinitionRemoteModel != null) {
			try {
				Class<?> clazz = _spiDefinitionRemoteModel.getClass();

				Method method = clazz.getMethod("setPortletIds", String.class);

				method.invoke(_spiDefinitionRemoteModel, portletIds);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getServletContextNames() {
		return _servletContextNames;
	}

	@Override
	public void setServletContextNames(String servletContextNames) {
		_servletContextNames = servletContextNames;

		if (_spiDefinitionRemoteModel != null) {
			try {
				Class<?> clazz = _spiDefinitionRemoteModel.getClass();

				Method method = clazz.getMethod("setServletContextNames",
						String.class);

				method.invoke(_spiDefinitionRemoteModel, servletContextNames);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getTypeSettings() {
		return _typeSettings;
	}

	@Override
	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;

		if (_spiDefinitionRemoteModel != null) {
			try {
				Class<?> clazz = _spiDefinitionRemoteModel.getClass();

				Method method = clazz.getMethod("setTypeSettings", String.class);

				method.invoke(_spiDefinitionRemoteModel, typeSettings);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public int getStatus() {
		return _status;
	}

	@Override
	public void setStatus(int status) {
		_status = status;

		if (_spiDefinitionRemoteModel != null) {
			try {
				Class<?> clazz = _spiDefinitionRemoteModel.getClass();

				Method method = clazz.getMethod("setStatus", int.class);

				method.invoke(_spiDefinitionRemoteModel, status);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public String getStatusMessage() {
		return _statusMessage;
	}

	@Override
	public void setStatusMessage(String statusMessage) {
		_statusMessage = statusMessage;

		if (_spiDefinitionRemoteModel != null) {
			try {
				Class<?> clazz = _spiDefinitionRemoteModel.getClass();

				Method method = clazz.getMethod("setStatusMessage", String.class);

				method.invoke(_spiDefinitionRemoteModel, statusMessage);
			}
			catch (Exception e) {
				throw new UnsupportedOperationException(e);
			}
		}
	}

	@Override
	public void deleteBaseDir() {
		try {
			String methodName = "deleteBaseDir";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			invokeOnRemoteModel(methodName, parameterTypes, parameterValues);
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public java.lang.String getAgentClassName() {
		try {
			String methodName = "getAgentClassName";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			java.lang.String returnObj = (java.lang.String)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public java.lang.String getBaseDir() {
		try {
			String methodName = "getBaseDir";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			java.lang.String returnObj = (java.lang.String)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public java.lang.String getJavaExecutable() {
		try {
			String methodName = "getJavaExecutable";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			java.lang.String returnObj = (java.lang.String)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public int getMaxRestartAttempts() {
		try {
			String methodName = "getMaxRestartAttempts";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			Integer returnObj = (Integer)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public int getMaxThreads() {
		try {
			String methodName = "getMaxThreads";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			Integer returnObj = (Integer)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public int getMinThreads() {
		try {
			String methodName = "getMinThreads";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			Integer returnObj = (Integer)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public java.lang.String getNotificationRecipients() {
		try {
			String methodName = "getNotificationRecipients";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			java.lang.String returnObj = (java.lang.String)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public long getPingInterval() {
		try {
			String methodName = "getPingInterval";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			Long returnObj = (Long)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public java.lang.String getPortalProperties() {
		try {
			String methodName = "getPortalProperties";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			java.lang.String returnObj = (java.lang.String)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public long getRegisterTimeout() {
		try {
			String methodName = "getRegisterTimeout";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			Long returnObj = (Long)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public int getRestartAttempts() {
		try {
			String methodName = "getRestartAttempts";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			Integer returnObj = (Integer)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public long getShutdownTimeout() {
		try {
			String methodName = "getShutdownTimeout";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			Long returnObj = (Long)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public com.liferay.portal.kernel.resiliency.spi.SPI getSPI() {
		try {
			String methodName = "getSPI";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			com.liferay.portal.kernel.resiliency.spi.SPI returnObj = (com.liferay.portal.kernel.resiliency.spi.SPI)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public java.lang.String getStatusLabel() {
		try {
			String methodName = "getStatusLabel";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			java.lang.String returnObj = (java.lang.String)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		try {
			String methodName = "getTypeSettingsProperties";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			com.liferay.portal.kernel.util.UnicodeProperties returnObj = (com.liferay.portal.kernel.util.UnicodeProperties)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public java.lang.String getTypeSettingsProperty(java.lang.String key) {
		try {
			String methodName = "getTypeSettingsProperty";

			Class<?>[] parameterTypes = new Class<?>[] { java.lang.String.class };

			Object[] parameterValues = new Object[] { key };

			java.lang.String returnObj = (java.lang.String)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public java.lang.String getTypeSettingsProperty(java.lang.String key,
		java.lang.String defaultValue) {
		try {
			String methodName = "getTypeSettingsProperty";

			Class<?>[] parameterTypes = new Class<?>[] {
					java.lang.String.class, java.lang.String.class
				};

			Object[] parameterValues = new Object[] { key, defaultValue };

			java.lang.String returnObj = (java.lang.String)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public boolean isAlive() {
		try {
			String methodName = "isAlive";

			Class<?>[] parameterTypes = new Class<?>[] {  };

			Object[] parameterValues = new Object[] {  };

			Boolean returnObj = (Boolean)invokeOnRemoteModel(methodName,
					parameterTypes, parameterValues);

			return returnObj;
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public void setMaxRestartAttempts(int maxRestartAttempts) {
		try {
			String methodName = "setMaxRestartAttempts";

			Class<?>[] parameterTypes = new Class<?>[] { int.class };

			Object[] parameterValues = new Object[] { maxRestartAttempts };

			invokeOnRemoteModel(methodName, parameterTypes, parameterValues);
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public void setNotificationRecipients(
		java.lang.String notificationRecipients) {
		try {
			String methodName = "setNotificationRecipients";

			Class<?>[] parameterTypes = new Class<?>[] { java.lang.String.class };

			Object[] parameterValues = new Object[] { notificationRecipients };

			invokeOnRemoteModel(methodName, parameterTypes, parameterValues);
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public void setPortalProperties(java.lang.String portalProperties) {
		try {
			String methodName = "setPortalProperties";

			Class<?>[] parameterTypes = new Class<?>[] { java.lang.String.class };

			Object[] parameterValues = new Object[] { portalProperties };

			invokeOnRemoteModel(methodName, parameterTypes, parameterValues);
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public void setRestartAttempts(int restartAttempts) {
		try {
			String methodName = "setRestartAttempts";

			Class<?>[] parameterTypes = new Class<?>[] { int.class };

			Object[] parameterValues = new Object[] { restartAttempts };

			invokeOnRemoteModel(methodName, parameterTypes, parameterValues);
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@Override
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		try {
			String methodName = "setTypeSettingsProperties";

			Class<?>[] parameterTypes = new Class<?>[] {
					com.liferay.portal.kernel.util.UnicodeProperties.class
				};

			Object[] parameterValues = new Object[] { typeSettingsProperties };

			invokeOnRemoteModel(methodName, parameterTypes, parameterValues);
		}
		catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	public BaseModel<?> getSPIDefinitionRemoteModel() {
		return _spiDefinitionRemoteModel;
	}

	public void setSPIDefinitionRemoteModel(
		BaseModel<?> spiDefinitionRemoteModel) {
		_spiDefinitionRemoteModel = spiDefinitionRemoteModel;
	}

	public Object invokeOnRemoteModel(String methodName,
		Class<?>[] parameterTypes, Object[] parameterValues)
		throws Exception {
		Object[] remoteParameterValues = new Object[parameterValues.length];

		for (int i = 0; i < parameterValues.length; i++) {
			if (parameterValues[i] != null) {
				remoteParameterValues[i] = ClpSerializer.translateInput(parameterValues[i]);
			}
		}

		Class<?> remoteModelClass = _spiDefinitionRemoteModel.getClass();

		ClassLoader remoteModelClassLoader = remoteModelClass.getClassLoader();

		Class<?>[] remoteParameterTypes = new Class[parameterTypes.length];

		for (int i = 0; i < parameterTypes.length; i++) {
			if (parameterTypes[i].isPrimitive()) {
				remoteParameterTypes[i] = parameterTypes[i];
			}
			else {
				String parameterTypeName = parameterTypes[i].getName();

				remoteParameterTypes[i] = remoteModelClassLoader.loadClass(parameterTypeName);
			}
		}

		Method method = remoteModelClass.getMethod(methodName,
				remoteParameterTypes);

		Object returnValue = method.invoke(_spiDefinitionRemoteModel,
				remoteParameterValues);

		if (returnValue != null) {
			returnValue = ClpSerializer.translateOutput(returnValue);
		}

		return returnValue;
	}

	@Override
	public void persist() {
		if (this.isNew()) {
			SPIDefinitionLocalServiceUtil.addSPIDefinition(this);
		}
		else {
			SPIDefinitionLocalServiceUtil.updateSPIDefinition(this);
		}
	}

	@Override
	public SPIDefinition toEscapedModel() {
		return (SPIDefinition)ProxyUtil.newProxyInstance(SPIDefinition.class.getClassLoader(),
			new Class[] { SPIDefinition.class }, new AutoEscapeBeanHandler(this));
	}

	@Override
	public Object clone() {
		SPIDefinitionClp clone = new SPIDefinitionClp();

		clone.setSpiDefinitionId(getSpiDefinitionId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setName(getName());
		clone.setConnectorAddress(getConnectorAddress());
		clone.setConnectorPort(getConnectorPort());
		clone.setDescription(getDescription());
		clone.setJvmArguments(getJvmArguments());
		clone.setPortletIds(getPortletIds());
		clone.setServletContextNames(getServletContextNames());
		clone.setTypeSettings(getTypeSettings());
		clone.setStatus(getStatus());
		clone.setStatusMessage(getStatusMessage());

		return clone;
	}

	@Override
	public int compareTo(SPIDefinition spiDefinition) {
		int value = 0;

		if (getSpiDefinitionId() < spiDefinition.getSpiDefinitionId()) {
			value = -1;
		}
		else if (getSpiDefinitionId() > spiDefinition.getSpiDefinitionId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		return 0;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SPIDefinitionClp)) {
			return false;
		}

		SPIDefinitionClp spiDefinition = (SPIDefinitionClp)obj;

		long primaryKey = spiDefinition.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	public Class<?> getClpSerializerClass() {
		return _clpSerializerClass;
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _entityCacheEnabled;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _finderCacheEnabled;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{spiDefinitionId=");
		sb.append(getSpiDefinitionId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", name=");
		sb.append(getName());
		sb.append(", connectorAddress=");
		sb.append(getConnectorAddress());
		sb.append(", connectorPort=");
		sb.append(getConnectorPort());
		sb.append(", description=");
		sb.append(getDescription());
		sb.append(", jvmArguments=");
		sb.append(getJvmArguments());
		sb.append(", portletIds=");
		sb.append(getPortletIds());
		sb.append(", servletContextNames=");
		sb.append(getServletContextNames());
		sb.append(", typeSettings=");
		sb.append(getTypeSettings());
		sb.append(", status=");
		sb.append(getStatus());
		sb.append(", statusMessage=");
		sb.append(getStatusMessage());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(52);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.resiliency.spi.model.SPIDefinition");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>spiDefinitionId</column-name><column-value><![CDATA[");
		sb.append(getSpiDefinitionId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>name</column-name><column-value><![CDATA[");
		sb.append(getName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>connectorAddress</column-name><column-value><![CDATA[");
		sb.append(getConnectorAddress());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>connectorPort</column-name><column-value><![CDATA[");
		sb.append(getConnectorPort());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>description</column-name><column-value><![CDATA[");
		sb.append(getDescription());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>jvmArguments</column-name><column-value><![CDATA[");
		sb.append(getJvmArguments());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>portletIds</column-name><column-value><![CDATA[");
		sb.append(getPortletIds());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>servletContextNames</column-name><column-value><![CDATA[");
		sb.append(getServletContextNames());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>typeSettings</column-name><column-value><![CDATA[");
		sb.append(getTypeSettings());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>status</column-name><column-value><![CDATA[");
		sb.append(getStatus());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>statusMessage</column-name><column-value><![CDATA[");
		sb.append(getStatusMessage());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _spiDefinitionId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _name;
	private String _connectorAddress;
	private int _connectorPort;
	private String _description;
	private String _jvmArguments;
	private String _portletIds;
	private String _servletContextNames;
	private String _typeSettings;
	private int _status;
	private String _statusMessage;
	private BaseModel<?> _spiDefinitionRemoteModel;
	private Class<?> _clpSerializerClass = ClpSerializer.class;
	private boolean _entityCacheEnabled;
	private boolean _finderCacheEnabled;
}