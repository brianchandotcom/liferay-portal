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

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
public class SPIDefinitionWrapper extends BaseModelWrapper<SPIDefinition>
	implements SPIDefinition, ModelWrapper<SPIDefinition> {
	public SPIDefinitionWrapper(SPIDefinition spiDefinition) {
		super(spiDefinition);
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
	public void deleteBaseDir() {
		model.deleteBaseDir();
	}

	@Override
	public String getAgentClassName() {
		return model.getAgentClassName();
	}

	@Override
	public String getBaseDir() {
		return model.getBaseDir();
	}

	/**
	* Returns the company ID of this spi definition.
	*
	* @return the company ID of this spi definition
	*/
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	* Returns the connector address of this spi definition.
	*
	* @return the connector address of this spi definition
	*/
	@Override
	public String getConnectorAddress() {
		return model.getConnectorAddress();
	}

	/**
	* Returns the connector port of this spi definition.
	*
	* @return the connector port of this spi definition
	*/
	@Override
	public int getConnectorPort() {
		return model.getConnectorPort();
	}

	/**
	* Returns the create date of this spi definition.
	*
	* @return the create date of this spi definition
	*/
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	* Returns the description of this spi definition.
	*
	* @return the description of this spi definition
	*/
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	@Override
	public String getJavaExecutable() {
		return model.getJavaExecutable();
	}

	/**
	* Returns the jvm arguments of this spi definition.
	*
	* @return the jvm arguments of this spi definition
	*/
	@Override
	public String getJvmArguments() {
		return model.getJvmArguments();
	}

	@Override
	public int getMaxRestartAttempts() {
		return model.getMaxRestartAttempts();
	}

	@Override
	public int getMaxThreads() {
		return model.getMaxThreads();
	}

	@Override
	public int getMinThreads() {
		return model.getMinThreads();
	}

	/**
	* Returns the modified date of this spi definition.
	*
	* @return the modified date of this spi definition
	*/
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	* Returns the name of this spi definition.
	*
	* @return the name of this spi definition
	*/
	@Override
	public String getName() {
		return model.getName();
	}

	@Override
	public String getNotificationRecipients() {
		return model.getNotificationRecipients();
	}

	@Override
	public long getPingInterval() {
		return model.getPingInterval();
	}

	@Override
	public String getPortalProperties() {
		return model.getPortalProperties();
	}

	/**
	* Returns the portlet IDs of this spi definition.
	*
	* @return the portlet IDs of this spi definition
	*/
	@Override
	public String getPortletIds() {
		return model.getPortletIds();
	}

	/**
	* Returns the primary key of this spi definition.
	*
	* @return the primary key of this spi definition
	*/
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public long getRegisterTimeout() {
		return model.getRegisterTimeout();
	}

	@Override
	public int getRestartAttempts() {
		return model.getRestartAttempts();
	}

	/**
	* Returns the servlet context names of this spi definition.
	*
	* @return the servlet context names of this spi definition
	*/
	@Override
	public String getServletContextNames() {
		return model.getServletContextNames();
	}

	@Override
	public long getShutdownTimeout() {
		return model.getShutdownTimeout();
	}

	@Override
	public com.liferay.portal.kernel.resiliency.spi.SPI getSPI() {
		return model.getSPI();
	}

	/**
	* Returns the spi definition ID of this spi definition.
	*
	* @return the spi definition ID of this spi definition
	*/
	@Override
	public long getSpiDefinitionId() {
		return model.getSpiDefinitionId();
	}

	/**
	* Returns the status of this spi definition.
	*
	* @return the status of this spi definition
	*/
	@Override
	public int getStatus() {
		return model.getStatus();
	}

	@Override
	public String getStatusLabel() {
		return model.getStatusLabel();
	}

	/**
	* Returns the status message of this spi definition.
	*
	* @return the status message of this spi definition
	*/
	@Override
	public String getStatusMessage() {
		return model.getStatusMessage();
	}

	/**
	* Returns the type settings of this spi definition.
	*
	* @return the type settings of this spi definition
	*/
	@Override
	public String getTypeSettings() {
		return model.getTypeSettings();
	}

	@Override
	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties() {
		return model.getTypeSettingsProperties();
	}

	@Override
	public String getTypeSettingsProperty(String key) {
		return model.getTypeSettingsProperty(key);
	}

	@Override
	public String getTypeSettingsProperty(String key, String defaultValue) {
		return model.getTypeSettingsProperty(key, defaultValue);
	}

	/**
	* Returns the user ID of this spi definition.
	*
	* @return the user ID of this spi definition
	*/
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	* Returns the user name of this spi definition.
	*
	* @return the user name of this spi definition
	*/
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	* Returns the user uuid of this spi definition.
	*
	* @return the user uuid of this spi definition
	*/
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public boolean isAlive() {
		return model.isAlive();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	* Sets the company ID of this spi definition.
	*
	* @param companyId the company ID of this spi definition
	*/
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	* Sets the connector address of this spi definition.
	*
	* @param connectorAddress the connector address of this spi definition
	*/
	@Override
	public void setConnectorAddress(String connectorAddress) {
		model.setConnectorAddress(connectorAddress);
	}

	/**
	* Sets the connector port of this spi definition.
	*
	* @param connectorPort the connector port of this spi definition
	*/
	@Override
	public void setConnectorPort(int connectorPort) {
		model.setConnectorPort(connectorPort);
	}

	/**
	* Sets the create date of this spi definition.
	*
	* @param createDate the create date of this spi definition
	*/
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	* Sets the description of this spi definition.
	*
	* @param description the description of this spi definition
	*/
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	* Sets the jvm arguments of this spi definition.
	*
	* @param jvmArguments the jvm arguments of this spi definition
	*/
	@Override
	public void setJvmArguments(String jvmArguments) {
		model.setJvmArguments(jvmArguments);
	}

	@Override
	public void setMaxRestartAttempts(int maxRestartAttempts) {
		model.setMaxRestartAttempts(maxRestartAttempts);
	}

	/**
	* Sets the modified date of this spi definition.
	*
	* @param modifiedDate the modified date of this spi definition
	*/
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	* Sets the name of this spi definition.
	*
	* @param name the name of this spi definition
	*/
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	@Override
	public void setNotificationRecipients(String notificationRecipients) {
		model.setNotificationRecipients(notificationRecipients);
	}

	@Override
	public void setPortalProperties(String portalProperties) {
		model.setPortalProperties(portalProperties);
	}

	/**
	* Sets the portlet IDs of this spi definition.
	*
	* @param portletIds the portlet IDs of this spi definition
	*/
	@Override
	public void setPortletIds(String portletIds) {
		model.setPortletIds(portletIds);
	}

	/**
	* Sets the primary key of this spi definition.
	*
	* @param primaryKey the primary key of this spi definition
	*/
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	@Override
	public void setRestartAttempts(int restartAttempts) {
		model.setRestartAttempts(restartAttempts);
	}

	/**
	* Sets the servlet context names of this spi definition.
	*
	* @param servletContextNames the servlet context names of this spi definition
	*/
	@Override
	public void setServletContextNames(String servletContextNames) {
		model.setServletContextNames(servletContextNames);
	}

	/**
	* Sets the spi definition ID of this spi definition.
	*
	* @param spiDefinitionId the spi definition ID of this spi definition
	*/
	@Override
	public void setSpiDefinitionId(long spiDefinitionId) {
		model.setSpiDefinitionId(spiDefinitionId);
	}

	/**
	* Sets the status of this spi definition.
	*
	* @param status the status of this spi definition
	*/
	@Override
	public void setStatus(int status) {
		model.setStatus(status);
	}

	/**
	* Sets the status message of this spi definition.
	*
	* @param statusMessage the status message of this spi definition
	*/
	@Override
	public void setStatusMessage(String statusMessage) {
		model.setStatusMessage(statusMessage);
	}

	/**
	* Sets the type settings of this spi definition.
	*
	* @param typeSettings the type settings of this spi definition
	*/
	@Override
	public void setTypeSettings(String typeSettings) {
		model.setTypeSettings(typeSettings);
	}

	@Override
	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties) {
		model.setTypeSettingsProperties(typeSettingsProperties);
	}

	/**
	* Sets the user ID of this spi definition.
	*
	* @param userId the user ID of this spi definition
	*/
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	* Sets the user name of this spi definition.
	*
	* @param userName the user name of this spi definition
	*/
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	* Sets the user uuid of this spi definition.
	*
	* @param userUuid the user uuid of this spi definition
	*/
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	protected SPIDefinitionWrapper wrap(SPIDefinition spiDefinition) {
		return new SPIDefinitionWrapper(spiDefinition);
	}
}