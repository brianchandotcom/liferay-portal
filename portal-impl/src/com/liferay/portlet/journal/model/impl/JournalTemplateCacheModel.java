/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;

import com.liferay.portlet.journal.model.JournalTemplate;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing JournalTemplate in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see JournalTemplate
 * @generated
 */
public class JournalTemplateCacheModel implements CacheModel<JournalTemplate>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(37);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", id=");
		sb.append(id);
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
		sb.append(", templateId=");
		sb.append(templateId);
		sb.append(", structureId=");
		sb.append(structureId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", xsl=");
		sb.append(xsl);
		sb.append(", langType=");
		sb.append(langType);
		sb.append(", cacheable=");
		sb.append(cacheable);
		sb.append(", smallImage=");
		sb.append(smallImage);
		sb.append(", smallImageId=");
		sb.append(smallImageId);
		sb.append(", smallImageURL=");
		sb.append(smallImageURL);
		sb.append("}");

		return sb.toString();
	}

	public JournalTemplate toEntityModel() {
		JournalTemplateImpl journalTemplateImpl = new JournalTemplateImpl();

		if (uuid == null) {
			journalTemplateImpl.setUuid(StringPool.BLANK);
		}
		else {
			journalTemplateImpl.setUuid(uuid);
		}

		journalTemplateImpl.setId(id);
		journalTemplateImpl.setGroupId(groupId);
		journalTemplateImpl.setCompanyId(companyId);
		journalTemplateImpl.setUserId(userId);

		if (userName == null) {
			journalTemplateImpl.setUserName(StringPool.BLANK);
		}
		else {
			journalTemplateImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			journalTemplateImpl.setCreateDate(null);
		}
		else {
			journalTemplateImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			journalTemplateImpl.setModifiedDate(null);
		}
		else {
			journalTemplateImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (templateId == null) {
			journalTemplateImpl.setTemplateId(StringPool.BLANK);
		}
		else {
			journalTemplateImpl.setTemplateId(templateId);
		}

		if (structureId == null) {
			journalTemplateImpl.setStructureId(StringPool.BLANK);
		}
		else {
			journalTemplateImpl.setStructureId(structureId);
		}

		if (name == null) {
			journalTemplateImpl.setName(StringPool.BLANK);
		}
		else {
			journalTemplateImpl.setName(name);
		}

		if (description == null) {
			journalTemplateImpl.setDescription(StringPool.BLANK);
		}
		else {
			journalTemplateImpl.setDescription(description);
		}

		if (xsl == null) {
			journalTemplateImpl.setXsl(StringPool.BLANK);
		}
		else {
			journalTemplateImpl.setXsl(xsl);
		}

		if (langType == null) {
			journalTemplateImpl.setLangType(StringPool.BLANK);
		}
		else {
			journalTemplateImpl.setLangType(langType);
		}

		journalTemplateImpl.setCacheable(cacheable);
		journalTemplateImpl.setSmallImage(smallImage);
		journalTemplateImpl.setSmallImageId(smallImageId);

		if (smallImageURL == null) {
			journalTemplateImpl.setSmallImageURL(StringPool.BLANK);
		}
		else {
			journalTemplateImpl.setSmallImageURL(smallImageURL);
		}

		journalTemplateImpl.resetOriginalValues();

		return journalTemplateImpl;
	}

	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {
		uuid = objectInput.readUTF();

		id = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		templateId = objectInput.readUTF();
		structureId = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		xsl = objectInput.readUTF();
		langType = objectInput.readUTF();

		cacheable = objectInput.readBoolean();

		smallImage = objectInput.readBoolean();

		smallImageId = objectInput.readLong();
		smallImageURL = objectInput.readUTF();
	}

	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(id);

		objectOutput.writeLong(groupId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);

		if (templateId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(templateId);
		}

		if (structureId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(structureId);
		}

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (xsl == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(xsl);
		}

		if (langType == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(langType);
		}

		objectOutput.writeBoolean(cacheable);

		objectOutput.writeBoolean(smallImage);

		objectOutput.writeLong(smallImageId);

		if (smallImageURL == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(smallImageURL);
		}
	}

	public String uuid;
	public long id;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String templateId;
	public String structureId;
	public String name;
	public String description;
	public String xsl;
	public String langType;
	public boolean cacheable;
	public boolean smallImage;
	public long smallImageId;
	public String smallImageURL;
}