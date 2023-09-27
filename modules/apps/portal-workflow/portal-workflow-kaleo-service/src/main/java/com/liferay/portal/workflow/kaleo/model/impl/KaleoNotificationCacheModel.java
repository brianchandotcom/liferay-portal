/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.kaleo.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.workflow.kaleo.model.KaleoNotification;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing KaleoNotification in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class KaleoNotificationCacheModel
	implements CacheModel<KaleoNotification>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof KaleoNotificationCacheModel)) {
			return false;
		}

		KaleoNotificationCacheModel kaleoNotificationCacheModel =
			(KaleoNotificationCacheModel)object;

		if ((kaleoNotificationId ==
				kaleoNotificationCacheModel.kaleoNotificationId) &&
			(mvccVersion == kaleoNotificationCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, kaleoNotificationId);

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
		sb.append(", kaleoNotificationId=");
		sb.append(kaleoNotificationId);
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
		sb.append(", kaleoClassName=");
		sb.append(kaleoClassName);
		sb.append(", kaleoClassPK=");
		sb.append(kaleoClassPK);
		sb.append(", kaleoDefinitionId=");
		sb.append(kaleoDefinitionId);
		sb.append(", kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);
		sb.append(", kaleoNodeName=");
		sb.append(kaleoNodeName);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", executionType=");
		sb.append(executionType);
		sb.append(", template=");
		sb.append(template);
		sb.append(", templateLanguage=");
		sb.append(templateLanguage);
		sb.append(", notificationTypes=");
		sb.append(notificationTypes);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public KaleoNotification toEntityModel() {
		KaleoNotificationImpl kaleoNotificationImpl =
			new KaleoNotificationImpl();

		kaleoNotificationImpl.setMvccVersion(mvccVersion);
		kaleoNotificationImpl.setCtCollectionId(ctCollectionId);
		kaleoNotificationImpl.setKaleoNotificationId(kaleoNotificationId);
		kaleoNotificationImpl.setGroupId(groupId);
		kaleoNotificationImpl.setCompanyId(companyId);
		kaleoNotificationImpl.setUserId(userId);

		if (userName == null) {
			kaleoNotificationImpl.setUserName("");
		}
		else {
			kaleoNotificationImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			kaleoNotificationImpl.setCreateDate(null);
		}
		else {
			kaleoNotificationImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			kaleoNotificationImpl.setModifiedDate(null);
		}
		else {
			kaleoNotificationImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (kaleoClassName == null) {
			kaleoNotificationImpl.setKaleoClassName("");
		}
		else {
			kaleoNotificationImpl.setKaleoClassName(kaleoClassName);
		}

		kaleoNotificationImpl.setKaleoClassPK(kaleoClassPK);
		kaleoNotificationImpl.setKaleoDefinitionId(kaleoDefinitionId);
		kaleoNotificationImpl.setKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);

		if (kaleoNodeName == null) {
			kaleoNotificationImpl.setKaleoNodeName("");
		}
		else {
			kaleoNotificationImpl.setKaleoNodeName(kaleoNodeName);
		}

		if (name == null) {
			kaleoNotificationImpl.setName("");
		}
		else {
			kaleoNotificationImpl.setName(name);
		}

		if (description == null) {
			kaleoNotificationImpl.setDescription("");
		}
		else {
			kaleoNotificationImpl.setDescription(description);
		}

		if (executionType == null) {
			kaleoNotificationImpl.setExecutionType("");
		}
		else {
			kaleoNotificationImpl.setExecutionType(executionType);
		}

		if (template == null) {
			kaleoNotificationImpl.setTemplate("");
		}
		else {
			kaleoNotificationImpl.setTemplate(template);
		}

		if (templateLanguage == null) {
			kaleoNotificationImpl.setTemplateLanguage("");
		}
		else {
			kaleoNotificationImpl.setTemplateLanguage(templateLanguage);
		}

		if (notificationTypes == null) {
			kaleoNotificationImpl.setNotificationTypes("");
		}
		else {
			kaleoNotificationImpl.setNotificationTypes(notificationTypes);
		}

		kaleoNotificationImpl.resetOriginalValues();

		return kaleoNotificationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		kaleoNotificationId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		kaleoClassName = objectInput.readUTF();

		kaleoClassPK = objectInput.readLong();

		kaleoDefinitionId = objectInput.readLong();

		kaleoDefinitionVersionId = objectInput.readLong();
		kaleoNodeName = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		executionType = objectInput.readUTF();
		template = (String)objectInput.readObject();
		templateLanguage = objectInput.readUTF();
		notificationTypes = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(kaleoNotificationId);

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

		if (kaleoClassName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(kaleoClassName);
		}

		objectOutput.writeLong(kaleoClassPK);

		objectOutput.writeLong(kaleoDefinitionId);

		objectOutput.writeLong(kaleoDefinitionVersionId);

		if (kaleoNodeName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(kaleoNodeName);
		}

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

		if (executionType == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(executionType);
		}

		if (template == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(template);
		}

		if (templateLanguage == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(templateLanguage);
		}

		if (notificationTypes == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(notificationTypes);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long kaleoNotificationId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String kaleoClassName;
	public long kaleoClassPK;
	public long kaleoDefinitionId;
	public long kaleoDefinitionVersionId;
	public String kaleoNodeName;
	public String name;
	public String description;
	public String executionType;
	public String template;
	public String templateLanguage;
	public String notificationTypes;

}