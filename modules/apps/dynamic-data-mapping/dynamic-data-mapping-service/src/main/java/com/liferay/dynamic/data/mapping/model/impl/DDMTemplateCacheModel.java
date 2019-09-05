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

import com.liferay.dynamic.data.mapping.model.DDMTemplate;
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
 * The cache model class for representing DDMTemplate in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class DDMTemplateCacheModel
	implements CacheModel<DDMTemplate>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DDMTemplateCacheModel)) {
			return false;
		}

		DDMTemplateCacheModel ddmTemplateCacheModel =
			(DDMTemplateCacheModel)obj;

		if ((templateId == ddmTemplateCacheModel.templateId) &&
			(mvccVersion == ddmTemplateCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, templateId);

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
		StringBundler sb = new StringBundler(55);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", templateId=");
		sb.append(templateId);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", versionUserId=");
		sb.append(versionUserId);
		sb.append(", versionUserName=");
		sb.append(versionUserName);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", resourceClassNameId=");
		sb.append(resourceClassNameId);
		sb.append(", templateKey=");
		sb.append(templateKey);
		sb.append(", version=");
		sb.append(version);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", type=");
		sb.append(type);
		sb.append(", mode=");
		sb.append(mode);
		sb.append(", language=");
		sb.append(language);
		sb.append(", script=");
		sb.append(script);
		sb.append(", cacheable=");
		sb.append(cacheable);
		sb.append(", smallImage=");
		sb.append(smallImage);
		sb.append(", smallImageId=");
		sb.append(smallImageId);
		sb.append(", smallImageURL=");
		sb.append(smallImageURL);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public DDMTemplate toEntityModel() {
		DDMTemplateImpl ddmTemplateImpl = new DDMTemplateImpl();

		ddmTemplateImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			ddmTemplateImpl.setUuid("");
		}
		else {
			ddmTemplateImpl.setUuid(uuid);
		}

		ddmTemplateImpl.setTemplateId(templateId);
		ddmTemplateImpl.setGroupId(groupId);
		ddmTemplateImpl.setCompanyId(companyId);
		ddmTemplateImpl.setUserId(userId);

		if (userName == null) {
			ddmTemplateImpl.setUserName("");
		}
		else {
			ddmTemplateImpl.setUserName(userName);
		}

		ddmTemplateImpl.setVersionUserId(versionUserId);

		if (versionUserName == null) {
			ddmTemplateImpl.setVersionUserName("");
		}
		else {
			ddmTemplateImpl.setVersionUserName(versionUserName);
		}

		if (createDate == Long.MIN_VALUE) {
			ddmTemplateImpl.setCreateDate(null);
		}
		else {
			ddmTemplateImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			ddmTemplateImpl.setModifiedDate(null);
		}
		else {
			ddmTemplateImpl.setModifiedDate(new Date(modifiedDate));
		}

		ddmTemplateImpl.setClassNameId(classNameId);
		ddmTemplateImpl.setClassPK(classPK);
		ddmTemplateImpl.setResourceClassNameId(resourceClassNameId);

		if (templateKey == null) {
			ddmTemplateImpl.setTemplateKey("");
		}
		else {
			ddmTemplateImpl.setTemplateKey(templateKey);
		}

		if (version == null) {
			ddmTemplateImpl.setVersion("");
		}
		else {
			ddmTemplateImpl.setVersion(version);
		}

		if (name == null) {
			ddmTemplateImpl.setName("");
		}
		else {
			ddmTemplateImpl.setName(name);
		}

		if (description == null) {
			ddmTemplateImpl.setDescription("");
		}
		else {
			ddmTemplateImpl.setDescription(description);
		}

		if (type == null) {
			ddmTemplateImpl.setType("");
		}
		else {
			ddmTemplateImpl.setType(type);
		}

		if (mode == null) {
			ddmTemplateImpl.setMode("");
		}
		else {
			ddmTemplateImpl.setMode(mode);
		}

		if (language == null) {
			ddmTemplateImpl.setLanguage("");
		}
		else {
			ddmTemplateImpl.setLanguage(language);
		}

		if (script == null) {
			ddmTemplateImpl.setScript("");
		}
		else {
			ddmTemplateImpl.setScript(script);
		}

		ddmTemplateImpl.setCacheable(cacheable);
		ddmTemplateImpl.setSmallImage(smallImage);
		ddmTemplateImpl.setSmallImageId(smallImageId);

		if (smallImageURL == null) {
			ddmTemplateImpl.setSmallImageURL("");
		}
		else {
			ddmTemplateImpl.setSmallImageURL(smallImageURL);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			ddmTemplateImpl.setLastPublishDate(null);
		}
		else {
			ddmTemplateImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		ddmTemplateImpl.resetOriginalValues();

		ddmTemplateImpl.setResourceClassName(_resourceClassName);

		return ddmTemplateImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		templateId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();

		versionUserId = objectInput.readLong();
		versionUserName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		resourceClassNameId = objectInput.readLong();
		templateKey = objectInput.readUTF();
		version = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		type = objectInput.readUTF();
		mode = objectInput.readUTF();
		language = objectInput.readUTF();
		script = objectInput.readUTF();

		cacheable = objectInput.readBoolean();

		smallImage = objectInput.readBoolean();

		smallImageId = objectInput.readLong();
		smallImageURL = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();

		_resourceClassName = (String)objectInput.readObject();
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

		objectOutput.writeLong(templateId);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(versionUserId);

		if (versionUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(versionUserName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(resourceClassNameId);

		if (templateKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(templateKey);
		}

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

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (mode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(mode);
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

		objectOutput.writeBoolean(cacheable);

		objectOutput.writeBoolean(smallImage);

		objectOutput.writeLong(smallImageId);

		if (smallImageURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(smallImageURL);
		}

		objectOutput.writeLong(lastPublishDate);

		objectOutput.writeObject(_resourceClassName);
	}

	public long mvccVersion;
	public String uuid;
	public long templateId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long versionUserId;
	public String versionUserName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long resourceClassNameId;
	public String templateKey;
	public String version;
	public String name;
	public String description;
	public String type;
	public String mode;
	public String language;
	public String script;
	public boolean cacheable;
	public boolean smallImage;
	public long smallImageId;
	public String smallImageURL;
	public long lastPublishDate;
	public String _resourceClassName;

}