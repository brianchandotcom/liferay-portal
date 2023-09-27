/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.reports.engine.console.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.reports.engine.console.model.Definition;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Definition in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DefinitionCacheModel
	implements CacheModel<Definition>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof DefinitionCacheModel)) {
			return false;
		}

		DefinitionCacheModel definitionCacheModel =
			(DefinitionCacheModel)object;

		if (definitionId == definitionCacheModel.definitionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, definitionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(29);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", definitionId=");
		sb.append(definitionId);
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
		sb.append(", sourceId=");
		sb.append(sourceId);
		sb.append(", reportName=");
		sb.append(reportName);
		sb.append(", reportParameters=");
		sb.append(reportParameters);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Definition toEntityModel() {
		DefinitionImpl definitionImpl = new DefinitionImpl();

		if (uuid == null) {
			definitionImpl.setUuid("");
		}
		else {
			definitionImpl.setUuid(uuid);
		}

		definitionImpl.setDefinitionId(definitionId);
		definitionImpl.setGroupId(groupId);
		definitionImpl.setCompanyId(companyId);
		definitionImpl.setUserId(userId);

		if (userName == null) {
			definitionImpl.setUserName("");
		}
		else {
			definitionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			definitionImpl.setCreateDate(null);
		}
		else {
			definitionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			definitionImpl.setModifiedDate(null);
		}
		else {
			definitionImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			definitionImpl.setName("");
		}
		else {
			definitionImpl.setName(name);
		}

		if (description == null) {
			definitionImpl.setDescription("");
		}
		else {
			definitionImpl.setDescription(description);
		}

		definitionImpl.setSourceId(sourceId);

		if (reportName == null) {
			definitionImpl.setReportName("");
		}
		else {
			definitionImpl.setReportName(reportName);
		}

		if (reportParameters == null) {
			definitionImpl.setReportParameters("");
		}
		else {
			definitionImpl.setReportParameters(reportParameters);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			definitionImpl.setLastPublishDate(null);
		}
		else {
			definitionImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		definitionImpl.resetOriginalValues();

		return definitionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		uuid = objectInput.readUTF();

		definitionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		sourceId = objectInput.readLong();
		reportName = objectInput.readUTF();
		reportParameters = (String)objectInput.readObject();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(definitionId);

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

		objectOutput.writeLong(sourceId);

		if (reportName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(reportName);
		}

		if (reportParameters == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(reportParameters);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public String uuid;
	public long definitionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
	public long sourceId;
	public String reportName;
	public String reportParameters;
	public long lastPublishDate;

}