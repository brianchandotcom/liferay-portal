/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.reports.engine.console.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
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
 * @see Definition
 * @generated
 */
@ProviderType
public class DefinitionCacheModel implements CacheModel<Definition>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DefinitionCacheModel)) {
			return false;
		}

		DefinitionCacheModel definitionCacheModel = (DefinitionCacheModel)obj;

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
	public void readExternal(ObjectInput objectInput) throws IOException {
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
		reportParameters = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
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
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(reportParameters);
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