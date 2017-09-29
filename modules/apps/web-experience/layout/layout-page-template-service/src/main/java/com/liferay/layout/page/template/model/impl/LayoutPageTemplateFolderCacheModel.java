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

import aQute.bnd.annotation.ProviderType;

import com.liferay.layout.page.template.model.LayoutPageTemplateFolder;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing LayoutPageTemplateFolder in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateFolder
 * @generated
 */
@ProviderType
public class LayoutPageTemplateFolderCacheModel implements CacheModel<LayoutPageTemplateFolder>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutPageTemplateFolderCacheModel)) {
			return false;
		}

		LayoutPageTemplateFolderCacheModel layoutPageTemplateFolderCacheModel = (LayoutPageTemplateFolderCacheModel)obj;

		if (layoutPageTemplateFolderId == layoutPageTemplateFolderCacheModel.layoutPageTemplateFolderId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, layoutPageTemplateFolderId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{layoutPageTemplateFolderId=");
		sb.append(layoutPageTemplateFolderId);
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
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutPageTemplateFolder toEntityModel() {
		LayoutPageTemplateFolderImpl layoutPageTemplateFolderImpl = new LayoutPageTemplateFolderImpl();

		layoutPageTemplateFolderImpl.setLayoutPageTemplateFolderId(layoutPageTemplateFolderId);
		layoutPageTemplateFolderImpl.setGroupId(groupId);
		layoutPageTemplateFolderImpl.setCompanyId(companyId);
		layoutPageTemplateFolderImpl.setUserId(userId);

		if (userName == null) {
			layoutPageTemplateFolderImpl.setUserName(StringPool.BLANK);
		}
		else {
			layoutPageTemplateFolderImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutPageTemplateFolderImpl.setCreateDate(null);
		}
		else {
			layoutPageTemplateFolderImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutPageTemplateFolderImpl.setModifiedDate(null);
		}
		else {
			layoutPageTemplateFolderImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			layoutPageTemplateFolderImpl.setName(StringPool.BLANK);
		}
		else {
			layoutPageTemplateFolderImpl.setName(name);
		}

		if (description == null) {
			layoutPageTemplateFolderImpl.setDescription(StringPool.BLANK);
		}
		else {
			layoutPageTemplateFolderImpl.setDescription(description);
		}

		layoutPageTemplateFolderImpl.resetOriginalValues();

		return layoutPageTemplateFolderImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		layoutPageTemplateFolderId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(layoutPageTemplateFolderId);

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
	}

	public long layoutPageTemplateFolderId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String description;
}