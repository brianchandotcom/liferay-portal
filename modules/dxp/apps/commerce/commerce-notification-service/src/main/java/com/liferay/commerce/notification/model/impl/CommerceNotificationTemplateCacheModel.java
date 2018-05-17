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

package com.liferay.commerce.notification.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.notification.model.CommerceNotificationTemplate;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing CommerceNotificationTemplate in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplate
 * @generated
 */
@ProviderType
public class CommerceNotificationTemplateCacheModel implements CacheModel<CommerceNotificationTemplate>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CommerceNotificationTemplateCacheModel)) {
			return false;
		}

		CommerceNotificationTemplateCacheModel commerceNotificationTemplateCacheModel =
			(CommerceNotificationTemplateCacheModel)obj;

		if (commerceNotificationTemplateId == commerceNotificationTemplateCacheModel.commerceNotificationTemplateId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, commerceNotificationTemplateId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(33);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", commerceNotificationTemplateId=");
		sb.append(commerceNotificationTemplateId);
		sb.append(", groupId=");
		sb.append(groupId);
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
		sb.append(", description=");
		sb.append(description);
		sb.append(", cc=");
		sb.append(cc);
		sb.append(", ccn=");
		sb.append(ccn);
		sb.append(", type=");
		sb.append(type);
		sb.append(", enabled=");
		sb.append(enabled);
		sb.append(", subject=");
		sb.append(subject);
		sb.append(", body=");
		sb.append(body);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CommerceNotificationTemplate toEntityModel() {
		CommerceNotificationTemplateImpl commerceNotificationTemplateImpl = new CommerceNotificationTemplateImpl();

		if (uuid == null) {
			commerceNotificationTemplateImpl.setUuid("");
		}
		else {
			commerceNotificationTemplateImpl.setUuid(uuid);
		}

		commerceNotificationTemplateImpl.setCommerceNotificationTemplateId(commerceNotificationTemplateId);
		commerceNotificationTemplateImpl.setGroupId(groupId);
		commerceNotificationTemplateImpl.setCompanyId(companyId);
		commerceNotificationTemplateImpl.setUserId(userId);

		if (userName == null) {
			commerceNotificationTemplateImpl.setUserName("");
		}
		else {
			commerceNotificationTemplateImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			commerceNotificationTemplateImpl.setCreateDate(null);
		}
		else {
			commerceNotificationTemplateImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceNotificationTemplateImpl.setModifiedDate(null);
		}
		else {
			commerceNotificationTemplateImpl.setModifiedDate(new Date(
					modifiedDate));
		}

		if (name == null) {
			commerceNotificationTemplateImpl.setName("");
		}
		else {
			commerceNotificationTemplateImpl.setName(name);
		}

		if (description == null) {
			commerceNotificationTemplateImpl.setDescription("");
		}
		else {
			commerceNotificationTemplateImpl.setDescription(description);
		}

		if (cc == null) {
			commerceNotificationTemplateImpl.setCc("");
		}
		else {
			commerceNotificationTemplateImpl.setCc(cc);
		}

		if (ccn == null) {
			commerceNotificationTemplateImpl.setCcn("");
		}
		else {
			commerceNotificationTemplateImpl.setCcn(ccn);
		}

		if (type == null) {
			commerceNotificationTemplateImpl.setType("");
		}
		else {
			commerceNotificationTemplateImpl.setType(type);
		}

		commerceNotificationTemplateImpl.setEnabled(enabled);

		if (subject == null) {
			commerceNotificationTemplateImpl.setSubject("");
		}
		else {
			commerceNotificationTemplateImpl.setSubject(subject);
		}

		if (body == null) {
			commerceNotificationTemplateImpl.setBody("");
		}
		else {
			commerceNotificationTemplateImpl.setBody(body);
		}

		commerceNotificationTemplateImpl.resetOriginalValues();

		return commerceNotificationTemplateImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		commerceNotificationTemplateId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		cc = objectInput.readUTF();
		ccn = objectInput.readUTF();
		type = objectInput.readUTF();

		enabled = objectInput.readBoolean();
		subject = objectInput.readUTF();
		body = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(commerceNotificationTemplateId);

		objectOutput.writeLong(groupId);

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

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (cc == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(cc);
		}

		if (ccn == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(ccn);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		objectOutput.writeBoolean(enabled);

		if (subject == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(subject);
		}

		if (body == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(body);
		}
	}

	public String uuid;
	public long commerceNotificationTemplateId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public String cc;
	public String ccn;
	public String type;
	public boolean enabled;
	public String subject;
	public String body;
}