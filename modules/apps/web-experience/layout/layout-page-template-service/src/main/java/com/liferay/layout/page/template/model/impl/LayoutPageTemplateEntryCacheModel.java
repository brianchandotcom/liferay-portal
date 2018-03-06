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

import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing LayoutPageTemplateEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplateEntry
 * @generated
 */
@ProviderType
public class LayoutPageTemplateEntryCacheModel implements CacheModel<LayoutPageTemplateEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutPageTemplateEntryCacheModel)) {
			return false;
		}

		LayoutPageTemplateEntryCacheModel layoutPageTemplateEntryCacheModel = (LayoutPageTemplateEntryCacheModel)obj;

		if (layoutPageTemplateEntryId == layoutPageTemplateEntryCacheModel.layoutPageTemplateEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, layoutPageTemplateEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{layoutPageTemplateEntryId=");
		sb.append(layoutPageTemplateEntryId);
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
		sb.append(", layoutPageTemplateCollectionId=");
		sb.append(layoutPageTemplateCollectionId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", htmlPreviewEntryId=");
		sb.append(htmlPreviewEntryId);
		sb.append(", defaultTemplate=");
		sb.append(defaultTemplate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutPageTemplateEntry toEntityModel() {
		LayoutPageTemplateEntryImpl layoutPageTemplateEntryImpl = new LayoutPageTemplateEntryImpl();

		layoutPageTemplateEntryImpl.setLayoutPageTemplateEntryId(layoutPageTemplateEntryId);
		layoutPageTemplateEntryImpl.setGroupId(groupId);
		layoutPageTemplateEntryImpl.setCompanyId(companyId);
		layoutPageTemplateEntryImpl.setUserId(userId);

		if (userName == null) {
			layoutPageTemplateEntryImpl.setUserName("");
		}
		else {
			layoutPageTemplateEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutPageTemplateEntryImpl.setCreateDate(null);
		}
		else {
			layoutPageTemplateEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutPageTemplateEntryImpl.setModifiedDate(null);
		}
		else {
			layoutPageTemplateEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		layoutPageTemplateEntryImpl.setLayoutPageTemplateCollectionId(layoutPageTemplateCollectionId);
		layoutPageTemplateEntryImpl.setClassNameId(classNameId);

		if (name == null) {
			layoutPageTemplateEntryImpl.setName("");
		}
		else {
			layoutPageTemplateEntryImpl.setName(name);
		}

		layoutPageTemplateEntryImpl.setHtmlPreviewEntryId(htmlPreviewEntryId);
		layoutPageTemplateEntryImpl.setDefaultTemplate(defaultTemplate);

		layoutPageTemplateEntryImpl.resetOriginalValues();

		return layoutPageTemplateEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		layoutPageTemplateEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		layoutPageTemplateCollectionId = objectInput.readLong();

		classNameId = objectInput.readLong();
		name = objectInput.readUTF();

		htmlPreviewEntryId = objectInput.readLong();

		defaultTemplate = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(layoutPageTemplateEntryId);

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

		objectOutput.writeLong(layoutPageTemplateCollectionId);

		objectOutput.writeLong(classNameId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		objectOutput.writeLong(htmlPreviewEntryId);

		objectOutput.writeBoolean(defaultTemplate);
	}

	public long layoutPageTemplateEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long layoutPageTemplateCollectionId;
	public long classNameId;
	public String name;
	public long htmlPreviewEntryId;
	public boolean defaultTemplate;
}