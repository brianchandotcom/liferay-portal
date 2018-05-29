/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.resiliency.spi.model;

import aQute.bnd.annotation.ProviderType;

import com.liferay.expando.kernel.model.ExpandoBridge;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link SPIDefinition}.
 * </p>
 *
 * @author Michael C. Han
 * @see SPIDefinition
 * @generated
 */
@ProviderType
public class SPIDefinitionWrapper implements SPIDefinition,
	ModelWrapper<SPIDefinition> {
	public SPIDefinitionWrapper(SPIDefinition spiDefinition) {
		_spiDefinition = spiDefinition;
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
	}

	@Override
	public Object clone() {
		return new SPIDefinitionWrapper((SPIDefinition)_spiDefinition.clone());
	}

	@Override
	public int compareTo(SPIDefinition spiDefinition) {
		return _spiDefinition.compareTo(spiDefinition);
	}

	@Override
	public void deleteBaseDir() {
		_spiDefinition.deleteBaseDir();
	}

	@Override
	public String getAgentClassName() {
		return _spiDefinition.getAgentClassName();
	}

	@Override
	public String getBaseDir() {
		return _spiDefinition.getBaseDir();
	}

	/**
	* Returns the company ID of this spi definition.
	*
	* @return the company ID of this spi definition
	*/
	@Override
	public long getCompanyId() {
		return _spiDefinition.getCompanyId();
	}

	/**
	* Returns the connector address of this spi definition.
	*
	* @return the connector address of this spi definition
	*/
	@Override
	public String getConnectorAddress() {
		return _spiDefinition.getConnectorAddress();
	}

	/**
	* Returns the connector port of this spi definition.
	*
	* @return the connector port of this spi definition
	*/
	@Override
	public int getConnectorPort() {
		return _spiDefinition.getConnectorPort();
	}

	/**
	* Returns the create date of this spi definition.
	*
	* @return the create date of this spi definition
	*/
	@Override
	public Date getCreateDate() {
		return _spiDefinition.getCreateDate();
	}

	/**
	* Returns the description of this spi definition.
	*
	* @return the description of this spi definition
	*/
	@Override
	public String getDescription() {
		return _spiDefinition.getDescription();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _spiDefinition.getExpandoBridge();
	}

	@Override
	public String getJavaExecutable() {
		return _spiDefinition.getJavaExecutable();
	}

	/**
	* Returns the jvm arguments of this spi definition.
	*
	* @return the jvm arguments of this spi definition
	*/
	@Override
	public String getJvmArguments() {
		return _spiDefinition.getJvmArguments();
	}

	@Override
	public int getMaxRestartAttempts() {
		return _spiDefinition.getMaxRestartAttempts();
	}

	@Override
	public int getMaxThreads() {
		return _spiDefinition.getMaxThreads();
	}

	@Override
	public int getMinThreads() {
		return _spiDefinition.getMinThreads();
	}

	/**
	* Returns the modified date of this spi definition.
	*
	* @return the modified date of this spi definition
	*/
	@Override
	public Date getModifiedDate() {
		return _spiDefinition.getModifiedDate();
	}

	/**
	* Returns the name of this spi definition.
	*
	* @return the name of this spi definition
	*/
	@Override
	public String getName() {
		return _spiDefinition.getName();
	}

	@Override
	public String getNotificationRecipients() {
		return _spiDefinition.getNotificationRecipients();
	}

	@Override
	public long getPingInterval() {
		return _spiDefinition.getPingInterval();
	}

	@Override
	public String getPortalProperties() {
		return _spiDefinition.getPortalProperties();
	}

	/**
	* Returns the portlet IDs of this spi definition.
	*
	* @return the portlet IDs of this spi definition
	*/
	@Override
	public String getPortletIds() {
		return _spiDefinition.getPortletIds();
	}

	/**
	* Returns the primary key of this spi definition.
	*
	* @return the primary key of this spi definition
	*/
	@Override
	public long getPrimaryKey() {
		return _spiDefinition.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _spiDefinition.getPrimaryKeyObj();
	}

	@Override
	public long getRegisterTimeout() {
		return _spiDefinition.getRegisterTimeout();
	}

	@Override
	public int getRestartAttempts() {
		return _spiDefinition.getRestartAttempts();
	}

	/**
	* Returns the servlet context names of this spi definition.
	*
	* @return the servlet context names of this spi definition
	*/
	@Override
	public String getServletContextNames() {
		return _spiDefinition.getServletContextNames();
	}

	@Override
	public long getShutdownTimeout() {
		return _spiDefinition.getShutdownTimeout();
	}

	@Override
	public com.liferay.portal.kernel.resiliency.spi.SPI getSPI() {
		return _spiDefinition.getSPI();
	}

	/**
	* Returns the spi definition ID of this spi definition.
	*
	* @return the spi definition ID of this spi definition
	*/
	@Override
	public long getSpiDefinitionId() {
		return _spiDefinition.getSpiDefinitionId();
	}

	/**
	* Returns the status of this spi definition.
	*
	* @return the status of this spi definition
	*/
	@Override
	public int getStatus() {
		return _spiDefinition.getStatus();
	}

	@Override
	public String getStatusLabel() {
		return _spiDefinition.getStatusLabel();
	}

	/**
	* Returns the status message of this spi definition.
	*
	* @return the status message of this spi definition
	*/
	@Override
	public String getStatusMessage() {
		return _spiDefinition.getStatusMessage();
	}

	/**
	* Returns the type settings of this spi definition.
	*
	* @return the type settings of this spi definition
	*/
	@Override
	public String getTypeSettings() {
		return _spiDefinition.getTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return _spiDefinition.getTypeSettingsProperties();
	}

	@Override
	public String getTypeSettingsProperty(String key) {
		return _spiDefinition.getTypeSettingsProperty(key);
	}

	@Override
	public String getTypeSettingsProperty(String key, String defaultValue) {
		return _spiDefinition.getTypeSettingsProperty(key, defaultValue);
	}

	/**
	* Returns the user ID of this spi definition.
	*
	* @return the user ID of this spi definition
	*/
	@Override
	public long getUserId() {
		return _spiDefinition.getUserId();
	}

	/**
	* Returns the user name of this spi definition.
	*
	* @return the user name of this spi definition
	*/
	@Override
	public String getUserName() {
		return _spiDefinition.getUserName();
	}

	/**
	* Returns the user uuid of this spi definition.
	*
	* @return the user uuid of this spi definition
	*/
	@Override
	public String getUserUuid() {
		return _spiDefinition.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _spiDefinition.hashCode();
	}

	@Override
	public boolean isAlive() {
		return _spiDefinition.isAlive();
	}

	@Override
	public boolean isCachedModel() {
		return _spiDefinition.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _spiDefinition.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _spiDefinition.isNew();
	}

	@Override
	public void persist() {
		_spiDefinition.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_spiDefinition.setCachedModel(cachedModel);
	}

	/**
	* Sets the company ID of this spi definition.
	*
	* @param companyId the company ID of this spi definition
	*/
	@Override
	public void setCompanyId(long companyId) {
		_spiDefinition.setCompanyId(companyId);
	}

	/**
	* Sets the connector address of this spi definition.
	*
	* @param connectorAddress the connector address of this spi definition
	*/
	@Override
	public void setConnectorAddress(String connectorAddress) {
		_spiDefinition.setConnectorAddress(connectorAddress);
	}

	/**
	* Sets the connector port of this spi definition.
	*
	* @param connectorPort the connector port of this spi definition
	*/
	@Override
	public void setConnectorPort(int connectorPort) {
		_spiDefinition.setConnectorPort(connectorPort);
	}

	/**
	* Sets the create date of this spi definition.
	*
	* @param createDate the create date of this spi definition
	*/
	@Override
	public void setCreateDate(Date createDate) {
		_spiDefinition.setCreateDate(createDate);
	}

	/**
	* Sets the description of this spi definition.
	*
	* @param description the description of this spi definition
	*/
	@Override
	public void setDescription(String description) {
		_spiDefinition.setDescription(description);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {
		_spiDefinition.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_spiDefinition.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_spiDefinition.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	* Sets the jvm arguments of this spi definition.
	*
	* @param jvmArguments the jvm arguments of this spi definition
	*/
	@Override
	public void setJvmArguments(String jvmArguments) {
		_spiDefinition.setJvmArguments(jvmArguments);
	}

	@Override
	public void setMaxRestartAttempts(int maxRestartAttempts) {
		_spiDefinition.setMaxRestartAttempts(maxRestartAttempts);
	}

	/**
	* Sets the modified date of this spi definition.
	*
	* @param modifiedDate the modified date of this spi definition
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		_spiDefinition.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this spi definition.
	*
	* @param name the name of this spi definition
	*/
	@Override
	public void setName(String name) {
		_spiDefinition.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_spiDefinition.setNew(n);
	}

	@Override
	public void setNotificationRecipients(String notificationRecipients) {
		_spiDefinition.setNotificationRecipients(notificationRecipients);
	}

	@Override
	public void setPortalProperties(String portalProperties) {
		_spiDefinition.setPortalProperties(portalProperties);
	}

	/**
	* Sets the portlet IDs of this spi definition.
	*
	* @param portletIds the portlet IDs of this spi definition
	*/
	@Override
	public void setPortletIds(String portletIds) {
		_spiDefinition.setPortletIds(portletIds);
	}

	/**
	* Sets the primary key of this spi definition.
	*
	* @param primaryKey the primary key of this spi definition
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		_spiDefinition.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_spiDefinition.setPrimaryKeyObj(primaryKeyObj);
	}

	@Override
	public void setRestartAttempts(int restartAttempts) {
		_spiDefinition.setRestartAttempts(restartAttempts);
	}

	/**
	* Sets the servlet context names of this spi definition.
	*
	* @param servletContextNames the servlet context names of this spi definition
	*/
	@Override
	public void setServletContextNames(String servletContextNames) {
		_spiDefinition.setServletContextNames(servletContextNames);
	}

	/**
	* Sets the spi definition ID of this spi definition.
	*
	* @param spiDefinitionId the spi definition ID of this spi definition
	*/
	@Override
	public void setSpiDefinitionId(long spiDefinitionId) {
		_spiDefinition.setSpiDefinitionId(spiDefinitionId);
	}

	/**
	* Sets the status of this spi definition.
	*
	* @param status the status of this spi definition
	*/
	@Override
	public void setStatus(int status) {
		_spiDefinition.setStatus(status);
	}

	/**
	* Sets the status message of this spi definition.
	*
	* @param statusMessage the status message of this spi definition
	*/
	@Override
	public void setStatusMessage(String statusMessage) {
		_spiDefinition.setStatusMessage(statusMessage);
	}

	/**
	* Sets the type settings of this spi definition.
	*
	* @param typeSettings the type settings of this spi definition
	*/
	@Override
	public void setTypeSettings(String typeSettings) {
		_spiDefinition.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		_spiDefinition.setTypeSettingsProperties(typeSettingsProperties);
	}

	/**
	* Sets the user ID of this spi definition.
	*
	* @param userId the user ID of this spi definition
	*/
	@Override
	public void setUserId(long userId) {
		_spiDefinition.setUserId(userId);
	}

	/**
	* Sets the user name of this spi definition.
	*
	* @param userName the user name of this spi definition
	*/
	@Override
	public void setUserName(String userName) {
		_spiDefinition.setUserName(userName);
	}

	/**
	* Sets the user uuid of this spi definition.
	*
	* @param userUuid the user uuid of this spi definition
	*/
	@Override
	public void setUserUuid(String userUuid) {
		_spiDefinition.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<SPIDefinition> toCacheModel() {
		return _spiDefinition.toCacheModel();
	}

	@Override
	public SPIDefinition toEscapedModel() {
		return new SPIDefinitionWrapper(_spiDefinition.toEscapedModel());
	}

	@Override
	public String toString() {
		return _spiDefinition.toString();
	}

	@Override
	public SPIDefinition toUnescapedModel() {
		return new SPIDefinitionWrapper(_spiDefinition.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _spiDefinition.toXmlString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SPIDefinitionWrapper)) {
			return false;
		}

		SPIDefinitionWrapper spiDefinitionWrapper = (SPIDefinitionWrapper)obj;

		if (Objects.equals(_spiDefinition, spiDefinitionWrapper._spiDefinition)) {
			return true;
		}

		return false;
	}

	@Override
	public SPIDefinition getWrappedModel() {
		return _spiDefinition;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _spiDefinition.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _spiDefinition.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_spiDefinition.resetOriginalValues();
	}

	private final SPIDefinition _spiDefinition;
}