/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.notification.model.impl;

import com.liferay.commerce.notification.model.CommerceNotificationTemplate;
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
 * The cache model class for representing CommerceNotificationTemplate in entity cache.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceNotificationTemplateCacheModel
	implements CacheModel<CommerceNotificationTemplate>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CommerceNotificationTemplateCacheModel)) {
			return false;
		}

		CommerceNotificationTemplateCacheModel
			commerceNotificationTemplateCacheModel =
				(CommerceNotificationTemplateCacheModel)object;

		if ((commerceNotificationTemplateId ==
				commerceNotificationTemplateCacheModel.
					commerceNotificationTemplateId) &&
			(mvccVersion ==
				commerceNotificationTemplateCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, commerceNotificationTemplateId);

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
		StringBundler sb = new StringBundler(41);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
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
		sb.append(", from=");
		sb.append(from);
		sb.append(", fromName=");
		sb.append(fromName);
		sb.append(", to=");
		sb.append(to);
		sb.append(", cc=");
		sb.append(cc);
		sb.append(", bcc=");
		sb.append(bcc);
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
		CommerceNotificationTemplateImpl commerceNotificationTemplateImpl =
			new CommerceNotificationTemplateImpl();

		commerceNotificationTemplateImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			commerceNotificationTemplateImpl.setUuid("");
		}
		else {
			commerceNotificationTemplateImpl.setUuid(uuid);
		}

		commerceNotificationTemplateImpl.setCommerceNotificationTemplateId(
			commerceNotificationTemplateId);
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
			commerceNotificationTemplateImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			commerceNotificationTemplateImpl.setModifiedDate(null);
		}
		else {
			commerceNotificationTemplateImpl.setModifiedDate(
				new Date(modifiedDate));
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

		if (from == null) {
			commerceNotificationTemplateImpl.setFrom("");
		}
		else {
			commerceNotificationTemplateImpl.setFrom(from);
		}

		if (fromName == null) {
			commerceNotificationTemplateImpl.setFromName("");
		}
		else {
			commerceNotificationTemplateImpl.setFromName(fromName);
		}

		if (to == null) {
			commerceNotificationTemplateImpl.setTo("");
		}
		else {
			commerceNotificationTemplateImpl.setTo(to);
		}

		if (cc == null) {
			commerceNotificationTemplateImpl.setCc("");
		}
		else {
			commerceNotificationTemplateImpl.setCc(cc);
		}

		if (bcc == null) {
			commerceNotificationTemplateImpl.setBcc("");
		}
		else {
			commerceNotificationTemplateImpl.setBcc(bcc);
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
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
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
		from = (String)objectInput.readObject();
		fromName = objectInput.readUTF();
		to = (String)objectInput.readObject();
		cc = (String)objectInput.readObject();
		bcc = (String)objectInput.readObject();
		type = objectInput.readUTF();

		enabled = objectInput.readBoolean();
		subject = objectInput.readUTF();
		body = (String)objectInput.readObject();
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

		if (from == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(from);
		}

		if (fromName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(fromName);
		}

		if (to == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(to);
		}

		if (cc == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(cc);
		}

		if (bcc == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(bcc);
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
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(body);
		}
	}

	public long mvccVersion;
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
	public String from;
	public String fromName;
	public String to;
	public String cc;
	public String bcc;
	public String type;
	public boolean enabled;
	public String subject;
	public String body;

}