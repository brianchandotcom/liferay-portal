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

import com.liferay.layout.page.template.model.LayoutPageTemplate;

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
 * The cache model class for representing LayoutPageTemplate in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutPageTemplate
 * @generated
 */
@ProviderType
public class LayoutPageTemplateCacheModel implements CacheModel<LayoutPageTemplate>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutPageTemplateCacheModel)) {
			return false;
		}

		LayoutPageTemplateCacheModel layoutPageTemplateCacheModel = (LayoutPageTemplateCacheModel)obj;

		if (layoutPageTemplateId == layoutPageTemplateCacheModel.layoutPageTemplateId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, layoutPageTemplateId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{layoutPageTemplateId=");
		sb.append(layoutPageTemplateId);
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
		sb.append(", layoutPageTemplateFolderId=");
		sb.append(layoutPageTemplateFolderId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutPageTemplate toEntityModel() {
		LayoutPageTemplateImpl layoutPageTemplateImpl = new LayoutPageTemplateImpl();

		layoutPageTemplateImpl.setLayoutPageTemplateId(layoutPageTemplateId);
		layoutPageTemplateImpl.setGroupId(groupId);
		layoutPageTemplateImpl.setCompanyId(companyId);
		layoutPageTemplateImpl.setUserId(userId);

		if (userName == null) {
			layoutPageTemplateImpl.setUserName(StringPool.BLANK);
		}
		else {
			layoutPageTemplateImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutPageTemplateImpl.setCreateDate(null);
		}
		else {
			layoutPageTemplateImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutPageTemplateImpl.setModifiedDate(null);
		}
		else {
			layoutPageTemplateImpl.setModifiedDate(new Date(modifiedDate));
		}

		layoutPageTemplateImpl.setLayoutPageTemplateFolderId(layoutPageTemplateFolderId);

		if (name == null) {
			layoutPageTemplateImpl.setName(StringPool.BLANK);
		}
		else {
			layoutPageTemplateImpl.setName(name);
		}

		layoutPageTemplateImpl.resetOriginalValues();

		return layoutPageTemplateImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		layoutPageTemplateId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		layoutPageTemplateFolderId = objectInput.readLong();
		name = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(layoutPageTemplateId);

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

		objectOutput.writeLong(layoutPageTemplateFolderId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}
	}

	public long layoutPageTemplateId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long layoutPageTemplateFolderId;
	public String name;
}