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

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.workflow.kaleo.model.KaleoNotificationRecipient;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoNotificationRecipient in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoNotificationRecipientCacheModel
	implements CacheModel<KaleoNotificationRecipient>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoNotificationRecipientCacheModel)) {
			return false;
		}

		KaleoNotificationRecipientCacheModel
			kaleoNotificationRecipientCacheModel =
				(KaleoNotificationRecipientCacheModel)obj;

		if ((kaleoNotificationRecipientId ==
				kaleoNotificationRecipientCacheModel.
					kaleoNotificationRecipientId) &&
			(mvccVersion == kaleoNotificationRecipientCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, kaleoNotificationRecipientId);

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
		StringBundler sb = new StringBundler(37);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", kaleoNotificationRecipientId=");
		sb.append(kaleoNotificationRecipientId);
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
		sb.append(", kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);
		sb.append(", kaleoNotificationId=");
		sb.append(kaleoNotificationId);
		sb.append(", recipientClassName=");
		sb.append(recipientClassName);
		sb.append(", recipientClassPK=");
		sb.append(recipientClassPK);
		sb.append(", recipientRoleType=");
		sb.append(recipientRoleType);
		sb.append(", recipientScript=");
		sb.append(recipientScript);
		sb.append(", recipientScriptLanguage=");
		sb.append(recipientScriptLanguage);
		sb.append(", recipientScriptContexts=");
		sb.append(recipientScriptContexts);
		sb.append(", address=");
		sb.append(address);
		sb.append(", notificationReceptionType=");
		sb.append(notificationReceptionType);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoNotificationRecipient toEntityModel() {
		KaleoNotificationRecipientImpl kaleoNotificationRecipientImpl =
			new KaleoNotificationRecipientImpl();

		kaleoNotificationRecipientImpl.setMvccVersion(mvccVersion);
		kaleoNotificationRecipientImpl.setKaleoNotificationRecipientId(
			kaleoNotificationRecipientId);
		kaleoNotificationRecipientImpl.setGroupId(groupId);
		kaleoNotificationRecipientImpl.setCompanyId(companyId);
		kaleoNotificationRecipientImpl.setUserId(userId);

		if (userName == null) {
			kaleoNotificationRecipientImpl.setUserName("");
		}
		else {
			kaleoNotificationRecipientImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoNotificationRecipientImpl.setCreateDate(null);
		}
		else {
			kaleoNotificationRecipientImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoNotificationRecipientImpl.setModifiedDate(null);
		}
		else {
			kaleoNotificationRecipientImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		kaleoNotificationRecipientImpl.setKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
		kaleoNotificationRecipientImpl.setKaleoNotificationId(
			kaleoNotificationId);

		if (recipientClassName == null) {
			kaleoNotificationRecipientImpl.setRecipientClassName("");
		}
		else {
			kaleoNotificationRecipientImpl.setRecipientClassName(
				recipientClassName);
		}

		kaleoNotificationRecipientImpl.setRecipientClassPK(recipientClassPK);
		kaleoNotificationRecipientImpl.setRecipientRoleType(recipientRoleType);

		if (recipientScript == null) {
			kaleoNotificationRecipientImpl.setRecipientScript("");
		}
		else {
			kaleoNotificationRecipientImpl.setRecipientScript(recipientScript);
		}

		if (recipientScriptLanguage == null) {
			kaleoNotificationRecipientImpl.setRecipientScriptLanguage("");
		}
		else {
			kaleoNotificationRecipientImpl.setRecipientScriptLanguage(
				recipientScriptLanguage);
		}

		if (recipientScriptContexts == null) {
			kaleoNotificationRecipientImpl.setRecipientScriptContexts("");
		}
		else {
			kaleoNotificationRecipientImpl.setRecipientScriptContexts(
				recipientScriptContexts);
		}

		if (address == null) {
			kaleoNotificationRecipientImpl.setAddress("");
		}
		else {
			kaleoNotificationRecipientImpl.setAddress(address);
		}

		if (notificationReceptionType == null) {
			kaleoNotificationRecipientImpl.setNotificationReceptionType("");
		}
		else {
			kaleoNotificationRecipientImpl.setNotificationReceptionType(
				notificationReceptionType);
		}

		kaleoNotificationRecipientImpl.resetOriginalValues();

		return kaleoNotificationRecipientImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		kaleoNotificationRecipientId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		kaleoDefinitionVersionId = objectInput.readLong();

		kaleoNotificationId = objectInput.readLong();
		recipientClassName = objectInput.readUTF();

		recipientClassPK = objectInput.readLong();

		recipientRoleType = objectInput.readInt();
		recipientScript = objectInput.readUTF();
		recipientScriptLanguage = objectInput.readUTF();
		recipientScriptContexts = objectInput.readUTF();
		address = objectInput.readUTF();
		notificationReceptionType = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(kaleoNotificationRecipientId);

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

		objectOutput.writeLong(kaleoDefinitionVersionId);

		objectOutput.writeLong(kaleoNotificationId);

		if (recipientClassName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(recipientClassName);
		}

		objectOutput.writeLong(recipientClassPK);

		objectOutput.writeInt(recipientRoleType);

		if (recipientScript == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(recipientScript);
		}

		if (recipientScriptLanguage == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(recipientScriptLanguage);
		}

		if (recipientScriptContexts == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(recipientScriptContexts);
		}

		if (address == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(address);
		}

		if (notificationReceptionType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(notificationReceptionType);
		}
	}

	public long mvccVersion;
	public long kaleoNotificationRecipientId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long kaleoDefinitionVersionId;
	public long kaleoNotificationId;
	public String recipientClassName;
	public long recipientClassPK;
	public int recipientRoleType;
	public String recipientScript;
	public String recipientScriptLanguage;
	public String recipientScriptContexts;
	public String address;
	public String notificationReceptionType;

}