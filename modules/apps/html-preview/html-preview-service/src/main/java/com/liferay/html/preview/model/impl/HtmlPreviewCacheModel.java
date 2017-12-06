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

package com.liferay.html.preview.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.html.preview.model.HtmlPreview;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing HtmlPreview in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see HtmlPreview
 * @generated
 */
@ProviderType
public class HtmlPreviewCacheModel implements CacheModel<HtmlPreview>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof HtmlPreviewCacheModel)) {
			return false;
		}

		HtmlPreviewCacheModel htmlPreviewCacheModel = (HtmlPreviewCacheModel)obj;

		if (htmlPreviewId == htmlPreviewCacheModel.htmlPreviewId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, htmlPreviewId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{htmlPreviewId=");
		sb.append(htmlPreviewId);
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
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", fileEntryId=");
		sb.append(fileEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public HtmlPreview toEntityModel() {
		HtmlPreviewImpl htmlPreviewImpl = new HtmlPreviewImpl();

		htmlPreviewImpl.setHtmlPreviewId(htmlPreviewId);
		htmlPreviewImpl.setGroupId(groupId);
		htmlPreviewImpl.setCompanyId(companyId);
		htmlPreviewImpl.setUserId(userId);

		if (userName == null) {
			htmlPreviewImpl.setUserName("");
		}
		else {
			htmlPreviewImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			htmlPreviewImpl.setCreateDate(null);
		}
		else {
			htmlPreviewImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			htmlPreviewImpl.setModifiedDate(null);
		}
		else {
			htmlPreviewImpl.setModifiedDate(new Date(modifiedDate));
		}

		htmlPreviewImpl.setClassNameId(classNameId);
		htmlPreviewImpl.setClassPK(classPK);
		htmlPreviewImpl.setFileEntryId(fileEntryId);

		htmlPreviewImpl.resetOriginalValues();

		return htmlPreviewImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		htmlPreviewId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();

		fileEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(htmlPreviewId);

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

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

		objectOutput.writeLong(fileEntryId);
	}

	public long htmlPreviewId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long classNameId;
	public long classPK;
	public long fileEntryId;
}