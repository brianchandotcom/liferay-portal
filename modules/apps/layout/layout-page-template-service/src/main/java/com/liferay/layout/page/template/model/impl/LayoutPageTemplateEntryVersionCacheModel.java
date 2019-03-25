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

import com.liferay.layout.page.template.model.LayoutPageTemplateEntryVersion;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing LayoutPageTemplateEntryVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class LayoutPageTemplateEntryVersionCacheModel
	implements CacheModel<LayoutPageTemplateEntryVersion>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutPageTemplateEntryVersionCacheModel)) {
			return false;
		}

		LayoutPageTemplateEntryVersionCacheModel
			layoutPageTemplateEntryVersionCacheModel =
				(LayoutPageTemplateEntryVersionCacheModel)obj;

		if (layoutPageTemplateEntryVersionId ==
				layoutPageTemplateEntryVersionCacheModel.
					layoutPageTemplateEntryVersionId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, layoutPageTemplateEntryVersionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{layoutPageTemplateEntryVersionId=");
		sb.append(layoutPageTemplateEntryVersionId);
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
		sb.append(", layoutPageTemplateEntryId=");
		sb.append(layoutPageTemplateEntryId);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", version=");
		sb.append(version);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutPageTemplateEntryVersion toEntityModel() {
		LayoutPageTemplateEntryVersionImpl layoutPageTemplateEntryVersionImpl =
			new LayoutPageTemplateEntryVersionImpl();

		layoutPageTemplateEntryVersionImpl.setLayoutPageTemplateEntryVersionId(
			layoutPageTemplateEntryVersionId);
		layoutPageTemplateEntryVersionImpl.setGroupId(groupId);
		layoutPageTemplateEntryVersionImpl.setCompanyId(companyId);
		layoutPageTemplateEntryVersionImpl.setUserId(userId);

		if (userName == null) {
			layoutPageTemplateEntryVersionImpl.setUserName("");
		}
		else {
			layoutPageTemplateEntryVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutPageTemplateEntryVersionImpl.setCreateDate(null);
		}
		else {
			layoutPageTemplateEntryVersionImpl.setCreateDate(
				new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutPageTemplateEntryVersionImpl.setModifiedDate(null);
		}
		else {
			layoutPageTemplateEntryVersionImpl.setModifiedDate(
				new Date(modifiedDate));
		}

		layoutPageTemplateEntryVersionImpl.setLayoutPageTemplateEntryId(
			layoutPageTemplateEntryId);
		layoutPageTemplateEntryVersionImpl.setPlid(plid);
		layoutPageTemplateEntryVersionImpl.setVersion(version);

		layoutPageTemplateEntryVersionImpl.resetOriginalValues();

		return layoutPageTemplateEntryVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		layoutPageTemplateEntryVersionId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		layoutPageTemplateEntryId = objectInput.readLong();

		plid = objectInput.readLong();

		version = objectInput.readDouble();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(layoutPageTemplateEntryVersionId);

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

		objectOutput.writeLong(layoutPageTemplateEntryId);

		objectOutput.writeLong(plid);

		objectOutput.writeDouble(version);
	}

	public long layoutPageTemplateEntryVersionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long layoutPageTemplateEntryId;
	public long plid;
	public double version;

}