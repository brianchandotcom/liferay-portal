/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KaleoNotificationRecipientCacheModel)) {
			return false;
		}

		KaleoNotificationRecipientCacheModel
			kaleoNotificationRecipientCacheModel =
				(KaleoNotificationRecipientCacheModel)object;

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
		StringBundler sb = new StringBundler(41);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
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
		sb.append(", kaleoDefinitionId=");
		sb.append(kaleoDefinitionId);
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
		kaleoNotificationRecipientImpl.setCtCollectionId(ctCollectionId);
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

		kaleoNotificationRecipientImpl.setKaleoDefinitionId(kaleoDefinitionId);
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
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		kaleoNotificationRecipientId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		kaleoDefinitionId = objectInput.readLong();

		kaleoDefinitionVersionId = objectInput.readLong();

		kaleoNotificationId = objectInput.readLong();
		recipientClassName = objectInput.readUTF();

		recipientClassPK = objectInput.readLong();

		recipientRoleType = objectInput.readInt();
		recipientScript = (String)objectInput.readObject();
		recipientScriptLanguage = objectInput.readUTF();
		recipientScriptContexts = objectInput.readUTF();
		address = objectInput.readUTF();
		notificationReceptionType = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

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

		objectOutput.writeLong(kaleoDefinitionId);

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
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(recipientScript);
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
	public long ctCollectionId;
	public long kaleoNotificationRecipientId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long kaleoDefinitionId;
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