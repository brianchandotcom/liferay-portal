/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.notification.model.impl;

import com.liferay.notification.model.NotificationTemplate;
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
 * The cache model class for representing NotificationTemplate in entity cache.
 *
 * @author Gabriel Albuquerque
 * @generated
 */
public class NotificationTemplateCacheModel
	implements CacheModel<NotificationTemplate>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof NotificationTemplateCacheModel)) {
			return false;
		}

		NotificationTemplateCacheModel notificationTemplateCacheModel =
			(NotificationTemplateCacheModel)object;

		if ((notificationTemplateId ==
				notificationTemplateCacheModel.notificationTemplateId) &&
			(mvccVersion == notificationTemplateCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, notificationTemplateId);

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
		StringBundler sb = new StringBundler(35);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", notificationTemplateId=");
		sb.append(notificationTemplateId);
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
		sb.append(", objectDefinitionId=");
		sb.append(objectDefinitionId);
		sb.append(", body=");
		sb.append(body);
		sb.append(", description=");
		sb.append(description);
		sb.append(", editorType=");
		sb.append(editorType);
		sb.append(", name=");
		sb.append(name);
		sb.append(", recipientType=");
		sb.append(recipientType);
		sb.append(", subject=");
		sb.append(subject);
		sb.append(", type=");
		sb.append(type);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public NotificationTemplate toEntityModel() {
		NotificationTemplateImpl notificationTemplateImpl =
			new NotificationTemplateImpl();

		notificationTemplateImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			notificationTemplateImpl.setUuid("");
		}
		else {
			notificationTemplateImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			notificationTemplateImpl.setExternalReferenceCode("");
		}
		else {
			notificationTemplateImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		notificationTemplateImpl.setNotificationTemplateId(
			notificationTemplateId);
		notificationTemplateImpl.setCompanyId(companyId);
		notificationTemplateImpl.setUserId(userId);

		if (userName == null) {
			notificationTemplateImpl.setUserName("");
		}
		else {
			notificationTemplateImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			notificationTemplateImpl.setCreateDate(null);
		}
		else {
			notificationTemplateImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			notificationTemplateImpl.setModifiedDate(null);
		}
		else {
			notificationTemplateImpl.setModifiedDate(new Date(modifiedDate));
		}

		notificationTemplateImpl.setObjectDefinitionId(objectDefinitionId);

		if (body == null) {
			notificationTemplateImpl.setBody("");
		}
		else {
			notificationTemplateImpl.setBody(body);
		}

		if (description == null) {
			notificationTemplateImpl.setDescription("");
		}
		else {
			notificationTemplateImpl.setDescription(description);
		}

		if (editorType == null) {
			notificationTemplateImpl.setEditorType("");
		}
		else {
			notificationTemplateImpl.setEditorType(editorType);
		}

		if (name == null) {
			notificationTemplateImpl.setName("");
		}
		else {
			notificationTemplateImpl.setName(name);
		}

		if (recipientType == null) {
			notificationTemplateImpl.setRecipientType("");
		}
		else {
			notificationTemplateImpl.setRecipientType(recipientType);
		}

		if (subject == null) {
			notificationTemplateImpl.setSubject("");
		}
		else {
			notificationTemplateImpl.setSubject(subject);
		}

		if (type == null) {
			notificationTemplateImpl.setType("");
		}
		else {
			notificationTemplateImpl.setType(type);
		}

		notificationTemplateImpl.resetOriginalValues();

		return notificationTemplateImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		notificationTemplateId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		objectDefinitionId = objectInput.readLong();
		body = (String)objectInput.readObject();
		description = objectInput.readUTF();
		editorType = objectInput.readUTF();
		name = objectInput.readUTF();
		recipientType = objectInput.readUTF();
		subject = objectInput.readUTF();
		type = objectInput.readUTF();
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

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(notificationTemplateId);

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

		objectOutput.writeLong(objectDefinitionId);

		if (body == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(body);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (editorType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(editorType);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (recipientType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(recipientType);
		}

		if (subject == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(subject);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}
	}

	public long mvccVersion;
	public String uuid;
	public String externalReferenceCode;
	public long notificationTemplateId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long objectDefinitionId;
	public String body;
	public String description;
	public String editorType;
	public String name;
	public String recipientType;
	public String subject;
	public String type;

}