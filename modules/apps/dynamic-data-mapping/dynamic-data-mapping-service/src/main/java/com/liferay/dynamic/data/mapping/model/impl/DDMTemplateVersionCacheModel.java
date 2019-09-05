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

package com.liferay.dynamic.data.mapping.model.impl;

import com.liferay.dynamic.data.mapping.model.DDMTemplateVersion;
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
 * The cache model class for representing DDMTemplateVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMTemplateVersionCacheModel
	implements CacheModel<DDMTemplateVersion>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMTemplateVersionCacheModel)) {
			return false;
		}

		DDMTemplateVersionCacheModel ddmTemplateVersionCacheModel =
			(DDMTemplateVersionCacheModel)obj;

		if ((templateVersionId ==
				ddmTemplateVersionCacheModel.templateVersionId) &&
			(mvccVersion == ddmTemplateVersionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, templateVersionId);

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
		StringBundler sb = new StringBundler(39);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", templateVersionId=");
		sb.append(templateVersionId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", templateId=");
		sb.append(templateId);
		sb.append(", version=");
		sb.append(version);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", language=");
		sb.append(language);
		sb.append(", script=");
		sb.append(script);
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMTemplateVersion toEntityModel() {
		DDMTemplateVersionImpl ddmTemplateVersionImpl =
			new DDMTemplateVersionImpl();

		ddmTemplateVersionImpl.setMvccVersion(mvccVersion);
		ddmTemplateVersionImpl.setTemplateVersionId(templateVersionId);
		ddmTemplateVersionImpl.setGroupId(groupId);
		ddmTemplateVersionImpl.setCompanyId(companyId);
		ddmTemplateVersionImpl.setUserId(userId);

		if (userName == null) {
			ddmTemplateVersionImpl.setUserName("");
		}
		else {
			ddmTemplateVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			ddmTemplateVersionImpl.setCreateDate(null);
		}
		else {
			ddmTemplateVersionImpl.setCreateDate(new Date(createDate));
		}

		ddmTemplateVersionImpl.setClassNameId(classNameId);
		ddmTemplateVersionImpl.setClassPK(classPK);
		ddmTemplateVersionImpl.setTemplateId(templateId);

		if (version == null) {
			ddmTemplateVersionImpl.setVersion("");
		}
		else {
			ddmTemplateVersionImpl.setVersion(version);
		}

		if (name == null) {
			ddmTemplateVersionImpl.setName("");
		}
		else {
			ddmTemplateVersionImpl.setName(name);
		}

		if (description == null) {
			ddmTemplateVersionImpl.setDescription("");
		}
		else {
			ddmTemplateVersionImpl.setDescription(description);
		}

		if (language == null) {
			ddmTemplateVersionImpl.setLanguage("");
		}
		else {
			ddmTemplateVersionImpl.setLanguage(language);
		}

		if (script == null) {
			ddmTemplateVersionImpl.setScript("");
		}
		else {
			ddmTemplateVersionImpl.setScript(script);
		}

		ddmTemplateVersionImpl.setStatus(status);
		ddmTemplateVersionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			ddmTemplateVersionImpl.setStatusByUserName("");
		}
		else {
			ddmTemplateVersionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			ddmTemplateVersionImpl.setStatusDate(null);
		}
		else {
			ddmTemplateVersionImpl.setStatusDate(new Date(statusDate));
		}

		ddmTemplateVersionImpl.resetOriginalValues();

		return ddmTemplateVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		templateVersionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		templateId = objectInput.readLong();
		version = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		language = objectInput.readUTF();
		script = objectInput.readUTF();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(templateVersionId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(templateId);

		if (version == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(version);
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

		if (language == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(language);
		}

		if (script == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(script);
		}

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
	}

	public long mvccVersion;
	public long templateVersionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long classNameId;
	public long classPK;
	public long templateId;
	public String version;
	public String name;
	public String description;
	public String language;
	public String script;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;

}