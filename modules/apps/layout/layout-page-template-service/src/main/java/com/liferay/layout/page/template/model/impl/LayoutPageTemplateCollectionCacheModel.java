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

package com.liferay.layout.page.template.model.impl;

import com.liferay.layout.page.template.model.LayoutPageTemplateCollection;
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
 * The cache model class for representing LayoutPageTemplateCollection in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutPageTemplateCollectionCacheModel
	implements CacheModel<LayoutPageTemplateCollection>, Externalizable,
			   MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutPageTemplateCollectionCacheModel)) {
			return false;
		}

		LayoutPageTemplateCollectionCacheModel
			layoutPageTemplateCollectionCacheModel =
				(LayoutPageTemplateCollectionCacheModel)obj;

		if ((layoutPageTemplateCollectionId ==
				layoutPageTemplateCollectionCacheModel.
					layoutPageTemplateCollectionId) &&
			(mvccVersion ==
				layoutPageTemplateCollectionCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, layoutPageTemplateCollectionId);

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
		StringBundler sb = new StringBundler(27);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", layoutPageTemplateCollectionId=");
		sb.append(layoutPageTemplateCollectionId);
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
		sb.append(", layoutPageTemplateCollectionKey=");
		sb.append(layoutPageTemplateCollectionKey);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutPageTemplateCollection toEntityModel() {
		LayoutPageTemplateCollectionImpl layoutPageTemplateCollectionImpl =
			new LayoutPageTemplateCollectionImpl();

		layoutPageTemplateCollectionImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			layoutPageTemplateCollectionImpl.setUuid("");
		}
		else {
			layoutPageTemplateCollectionImpl.setUuid(uuid);
		}

		layoutPageTemplateCollectionImpl.setLayoutPageTemplateCollectionId(
			layoutPageTemplateCollectionId);
		layoutPageTemplateCollectionImpl.setGroupId(groupId);
		layoutPageTemplateCollectionImpl.setCompanyId(companyId);
		layoutPageTemplateCollectionImpl.setUserId(userId);

		if (userName == null) {
			layoutPageTemplateCollectionImpl.setUserName("");
		}
		else {
			layoutPageTemplateCollectionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutPageTemplateCollectionImpl.setCreateDate(null);
		}
		else {
			layoutPageTemplateCollectionImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutPageTemplateCollectionImpl.setModifiedDate(null);
		}
		else {
			layoutPageTemplateCollectionImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		if (layoutPageTemplateCollectionKey == null) {
			layoutPageTemplateCollectionImpl.setLayoutPageTemplateCollectionKey(
				"");
		}
		else {
			layoutPageTemplateCollectionImpl.setLayoutPageTemplateCollectionKey(
				layoutPageTemplateCollectionKey);
		}

		if (name == null) {
			layoutPageTemplateCollectionImpl.setName("");
		}
		else {
			layoutPageTemplateCollectionImpl.setName(name);
		}

		if (description == null) {
			layoutPageTemplateCollectionImpl.setDescription("");
		}
		else {
			layoutPageTemplateCollectionImpl.setDescription(description);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			layoutPageTemplateCollectionImpl.setLastPublishDate(null);
		}
		else {
			layoutPageTemplateCollectionImpl.setLastPublishDate(
				new Date(lastPublishDate));
		}

		layoutPageTemplateCollectionImpl.resetOriginalValues();

		return layoutPageTemplateCollectionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		layoutPageTemplateCollectionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		layoutPageTemplateCollectionKey = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
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

		objectOutput.writeLong(layoutPageTemplateCollectionId);

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

		if (layoutPageTemplateCollectionKey == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(layoutPageTemplateCollectionKey);
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

		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long layoutPageTemplateCollectionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String layoutPageTemplateCollectionKey;
	public String name;
	public String description;
	public long lastPublishDate;

}