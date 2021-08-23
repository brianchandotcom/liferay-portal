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

package com.liferay.custom.elements.model.impl;

import com.liferay.custom.elements.model.CustomElementsPortletDescriptor;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CustomElementsPortletDescriptor in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CustomElementsPortletDescriptorCacheModel
	implements CacheModel<CustomElementsPortletDescriptor>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CustomElementsPortletDescriptorCacheModel)) {
			return false;
		}

		CustomElementsPortletDescriptorCacheModel
			customElementsPortletDescriptorCacheModel =
				(CustomElementsPortletDescriptorCacheModel)object;

		if ((customElementsPortletDescriptorId ==
				customElementsPortletDescriptorCacheModel.
					customElementsPortletDescriptorId) &&
			(mvccVersion ==
				customElementsPortletDescriptorCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, customElementsPortletDescriptorId);

		return HashUtil.hash(hashCode, mvccVersion);
	}

	@Override
	public long getMvccVersion() {
		return mvccVersion;
	}

	@Override
	public void setMvccVersion(long mvccVersion) {
		this.mvccVersion = mvccVersion;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", customElementsPortletDescriptorId=");
		sb.append(customElementsPortletDescriptorId);
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
		sb.append(", cssURLs=");
		sb.append(cssURLs);
		sb.append(", htmlElementName=");
		sb.append(htmlElementName);
		sb.append(", instanceable=");
		sb.append(instanceable);
		sb.append(", name=");
		sb.append(name);
		sb.append(", properties=");
		sb.append(properties);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CustomElementsPortletDescriptor toEntityModel() {
		CustomElementsPortletDescriptorImpl
			customElementsPortletDescriptorImpl =
				new CustomElementsPortletDescriptorImpl();

		customElementsPortletDescriptorImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			customElementsPortletDescriptorImpl.setUuid("");
		}
		else {
			customElementsPortletDescriptorImpl.setUuid(uuid);
		}

		customElementsPortletDescriptorImpl.
			setCustomElementsPortletDescriptorId(
				customElementsPortletDescriptorId);
		customElementsPortletDescriptorImpl.setCompanyId(companyId);
		customElementsPortletDescriptorImpl.setUserId(userId);

		if (userName == null) {
			customElementsPortletDescriptorImpl.setUserName("");
		}
		else {
			customElementsPortletDescriptorImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			customElementsPortletDescriptorImpl.setCreateDate(null);
		}
		else {
			customElementsPortletDescriptorImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			customElementsPortletDescriptorImpl.setModifiedDate(null);
		}
		else {
			customElementsPortletDescriptorImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		if (cssURLs == null) {
			customElementsPortletDescriptorImpl.setCSSURLs("");
		}
		else {
			customElementsPortletDescriptorImpl.setCSSURLs(cssURLs);
		}

		if (htmlElementName == null) {
			customElementsPortletDescriptorImpl.setHTMLElementName("");
		}
		else {
			customElementsPortletDescriptorImpl.setHTMLElementName(
				htmlElementName);
		}

		customElementsPortletDescriptorImpl.setInstanceable(instanceable);

		if (name == null) {
			customElementsPortletDescriptorImpl.setName("");
		}
		else {
			customElementsPortletDescriptorImpl.setName(name);
		}

		if (properties == null) {
			customElementsPortletDescriptorImpl.setProperties("");
		}
		else {
			customElementsPortletDescriptorImpl.setProperties(properties);
		}

		customElementsPortletDescriptorImpl.resetOriginalValues();

		return customElementsPortletDescriptorImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		customElementsPortletDescriptorId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		cssURLs = (String)objectInput.readObject();
		htmlElementName = objectInput.readUTF();

		instanceable = objectInput.readBoolean();
		name = objectInput.readUTF();
		properties = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(customElementsPortletDescriptorId);

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

		if (cssURLs == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(cssURLs);
		}

		if (htmlElementName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(htmlElementName);
		}

		objectOutput.writeBoolean(instanceable);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (properties == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(properties);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long customElementsPortletDescriptorId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String cssURLs;
	public String htmlElementName;
	public boolean instanceable;
	public String name;
	public String properties;

}