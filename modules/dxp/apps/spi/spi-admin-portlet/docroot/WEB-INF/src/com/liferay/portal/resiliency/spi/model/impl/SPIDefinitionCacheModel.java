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

package com.liferay.portal.resiliency.spi.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.resiliency.spi.model.SPIDefinition;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SPIDefinition in entity cache.
 *
 * @author Michael C. Han
 * @see SPIDefinition
 * @generated
 */
@ProviderType
public class SPIDefinitionCacheModel implements CacheModel<SPIDefinition>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof SPIDefinitionCacheModel)) {
			return false;
		}

		SPIDefinitionCacheModel spiDefinitionCacheModel = (SPIDefinitionCacheModel)obj;

		if (spiDefinitionId == spiDefinitionCacheModel.spiDefinitionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, spiDefinitionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{spiDefinitionId=");
		sb.append(spiDefinitionId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", name=");
		sb.append(name);
		sb.append(", connectorAddress=");
		sb.append(connectorAddress);
		sb.append(", connectorPort=");
		sb.append(connectorPort);
		sb.append(", description=");
		sb.append(description);
		sb.append(", jvmArguments=");
		sb.append(jvmArguments);
		sb.append(", portletIds=");
		sb.append(portletIds);
		sb.append(", servletContextNames=");
		sb.append(servletContextNames);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusMessage=");
		sb.append(statusMessage);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SPIDefinition toEntityModel() {
		SPIDefinitionImpl spiDefinitionImpl = new SPIDefinitionImpl();

		spiDefinitionImpl.setSpiDefinitionId(spiDefinitionId);
		spiDefinitionImpl.setCompanyId(companyId);
		spiDefinitionImpl.setUserId(userId);

		if (userName == null) {
			spiDefinitionImpl.setUserName("");
		}
		else {
			spiDefinitionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			spiDefinitionImpl.setCreateDate(null);
		}
		else {
			spiDefinitionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			spiDefinitionImpl.setModifiedDate(null);
		}
		else {
			spiDefinitionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			spiDefinitionImpl.setName("");
		}
		else {
			spiDefinitionImpl.setName(name);
		}

		if (connectorAddress == null) {
			spiDefinitionImpl.setConnectorAddress("");
		}
		else {
			spiDefinitionImpl.setConnectorAddress(connectorAddress);
		}

		spiDefinitionImpl.setConnectorPort(connectorPort);

		if (description == null) {
			spiDefinitionImpl.setDescription("");
		}
		else {
			spiDefinitionImpl.setDescription(description);
		}

		if (jvmArguments == null) {
			spiDefinitionImpl.setJvmArguments("");
		}
		else {
			spiDefinitionImpl.setJvmArguments(jvmArguments);
		}

		if (portletIds == null) {
			spiDefinitionImpl.setPortletIds("");
		}
		else {
			spiDefinitionImpl.setPortletIds(portletIds);
		}

		if (servletContextNames == null) {
			spiDefinitionImpl.setServletContextNames("");
		}
		else {
			spiDefinitionImpl.setServletContextNames(servletContextNames);
		}

		if (typeSettings == null) {
			spiDefinitionImpl.setTypeSettings("");
		}
		else {
			spiDefinitionImpl.setTypeSettings(typeSettings);
		}

		spiDefinitionImpl.setStatus(status);

		if (statusMessage == null) {
			spiDefinitionImpl.setStatusMessage("");
		}
		else {
			spiDefinitionImpl.setStatusMessage(statusMessage);
		}

		spiDefinitionImpl.resetOriginalValues();

		return spiDefinitionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		spiDefinitionId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		connectorAddress = objectInput.readUTF();

		connectorPort = objectInput.readInt();
		description = objectInput.readUTF();
		jvmArguments = objectInput.readUTF();
		portletIds = objectInput.readUTF();
		servletContextNames = objectInput.readUTF();
		typeSettings = objectInput.readUTF();

		status = objectInput.readInt();
		statusMessage = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(spiDefinitionId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (connectorAddress == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(connectorAddress);
		}

		objectOutput.writeInt(connectorPort);

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (jvmArguments == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(jvmArguments);
		}

		if (portletIds == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(portletIds);
		}

		if (servletContextNames == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(servletContextNames);
		}

		if (typeSettings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}

		objectOutput.writeInt(status);

		if (statusMessage == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusMessage);
		}
	}

	public long spiDefinitionId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String connectorAddress;
	public int connectorPort;
	public String description;
	public String jvmArguments;
	public String portletIds;
	public String servletContextNames;
	public String typeSettings;
	public int status;
	public String statusMessage;
}