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
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof KaleoNotificationCacheModel)) {
			return false;
		}

		KaleoNotificationCacheModel kaleoNotificationCacheModel =
			(KaleoNotificationCacheModel)obj;

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
		StringBundler sb = new StringBundler(37);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
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
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		kaleoNotificationId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		kaleoClassName = objectInput.readUTF();

		kaleoClassPK = objectInput.readLong();

		kaleoDefinitionVersionId = objectInput.readLong();
		kaleoNodeName = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		executionType = objectInput.readUTF();
		template = objectInput.readUTF();
		templateLanguage = objectInput.readUTF();
		notificationTypes = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

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
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(template);
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
	public long kaleoNotificationId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String kaleoClassName;
	public long kaleoClassPK;
	public long kaleoDefinitionVersionId;
	public String kaleoNodeName;
	public String name;
	public String description;
	public String executionType;
	public String template;
	public String templateLanguage;
	public String notificationTypes;

}