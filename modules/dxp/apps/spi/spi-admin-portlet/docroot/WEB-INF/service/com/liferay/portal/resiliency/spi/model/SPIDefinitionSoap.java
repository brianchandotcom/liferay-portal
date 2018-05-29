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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.resiliency.spi.service.http.SPIDefinitionServiceSoap}.
 *
 * @author Michael C. Han
 * @see com.liferay.portal.resiliency.spi.service.http.SPIDefinitionServiceSoap
 * @generated
 */
@ProviderType
public class SPIDefinitionSoap implements Serializable {
	public static SPIDefinitionSoap toSoapModel(SPIDefinition model) {
		SPIDefinitionSoap soapModel = new SPIDefinitionSoap();

		soapModel.setSpiDefinitionId(model.getSpiDefinitionId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setName(model.getName());
		soapModel.setConnectorAddress(model.getConnectorAddress());
		soapModel.setConnectorPort(model.getConnectorPort());
		soapModel.setDescription(model.getDescription());
		soapModel.setJvmArguments(model.getJvmArguments());
		soapModel.setPortletIds(model.getPortletIds());
		soapModel.setServletContextNames(model.getServletContextNames());
		soapModel.setTypeSettings(model.getTypeSettings());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusMessage(model.getStatusMessage());

		return soapModel;
	}

	public static SPIDefinitionSoap[] toSoapModels(SPIDefinition[] models) {
		SPIDefinitionSoap[] soapModels = new SPIDefinitionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SPIDefinitionSoap[][] toSoapModels(SPIDefinition[][] models) {
		SPIDefinitionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SPIDefinitionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SPIDefinitionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SPIDefinitionSoap[] toSoapModels(List<SPIDefinition> models) {
		List<SPIDefinitionSoap> soapModels = new ArrayList<SPIDefinitionSoap>(models.size());

		for (SPIDefinition model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SPIDefinitionSoap[soapModels.size()]);
	}

	public SPIDefinitionSoap() {
	}

	public long getPrimaryKey() {
		return _spiDefinitionId;
	}

	public void setPrimaryKey(long pk) {
		setSpiDefinitionId(pk);
	}

	public long getSpiDefinitionId() {
		return _spiDefinitionId;
	}

	public void setSpiDefinitionId(long spiDefinitionId) {
		_spiDefinitionId = spiDefinitionId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getConnectorAddress() {
		return _connectorAddress;
	}

	public void setConnectorAddress(String connectorAddress) {
		_connectorAddress = connectorAddress;
	}

	public int getConnectorPort() {
		return _connectorPort;
	}

	public void setConnectorPort(int connectorPort) {
		_connectorPort = connectorPort;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getJvmArguments() {
		return _jvmArguments;
	}

	public void setJvmArguments(String jvmArguments) {
		_jvmArguments = jvmArguments;
	}

	public String getPortletIds() {
		return _portletIds;
	}

	public void setPortletIds(String portletIds) {
		_portletIds = portletIds;
	}

	public String getServletContextNames() {
		return _servletContextNames;
	}

	public void setServletContextNames(String servletContextNames) {
		_servletContextNames = servletContextNames;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public String getStatusMessage() {
		return _statusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		_statusMessage = statusMessage;
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
}