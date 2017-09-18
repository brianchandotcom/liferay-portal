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

package com.liferay.modern.site.building.fragment.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.modern.site.building.fragment.model.MSBFragmentEntry;

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
 * The cache model class for representing MSBFragmentEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see MSBFragmentEntry
 * @generated
 */
@ProviderType
public class MSBFragmentEntryCacheModel implements CacheModel<MSBFragmentEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof MSBFragmentEntryCacheModel)) {
			return false;
		}

		MSBFragmentEntryCacheModel msbFragmentEntryCacheModel = (MSBFragmentEntryCacheModel)obj;

		if (msbFragmentEntryId == msbFragmentEntryCacheModel.msbFragmentEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, msbFragmentEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(25);

		sb.append("{msbFragmentEntryId=");
		sb.append(msbFragmentEntryId);
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
		sb.append(", msbFragmentCollectionId=");
		sb.append(msbFragmentCollectionId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", css=");
		sb.append(css);
		sb.append(", html=");
		sb.append(html);
		sb.append(", js=");
		sb.append(js);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public MSBFragmentEntry toEntityModel() {
		MSBFragmentEntryImpl msbFragmentEntryImpl = new MSBFragmentEntryImpl();

		msbFragmentEntryImpl.setMsbFragmentEntryId(msbFragmentEntryId);
		msbFragmentEntryImpl.setGroupId(groupId);
		msbFragmentEntryImpl.setCompanyId(companyId);
		msbFragmentEntryImpl.setUserId(userId);

		if (userName == null) {
			msbFragmentEntryImpl.setUserName(StringPool.BLANK);
		}
		else {
			msbFragmentEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			msbFragmentEntryImpl.setCreateDate(null);
		}
		else {
			msbFragmentEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			msbFragmentEntryImpl.setModifiedDate(null);
		}
		else {
			msbFragmentEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		msbFragmentEntryImpl.setMsbFragmentCollectionId(msbFragmentCollectionId);

		if (name == null) {
			msbFragmentEntryImpl.setName(StringPool.BLANK);
		}
		else {
			msbFragmentEntryImpl.setName(name);
		}

		if (css == null) {
			msbFragmentEntryImpl.setCss(StringPool.BLANK);
		}
		else {
			msbFragmentEntryImpl.setCss(css);
		}

		if (html == null) {
			msbFragmentEntryImpl.setHtml(StringPool.BLANK);
		}
		else {
			msbFragmentEntryImpl.setHtml(html);
		}

		if (js == null) {
			msbFragmentEntryImpl.setJs(StringPool.BLANK);
		}
		else {
			msbFragmentEntryImpl.setJs(js);
		}

		msbFragmentEntryImpl.resetOriginalValues();

		return msbFragmentEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		msbFragmentEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		msbFragmentCollectionId = objectInput.readLong();
		name = objectInput.readUTF();
		css = objectInput.readUTF();
		html = objectInput.readUTF();
		js = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(msbFragmentEntryId);

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

		objectOutput.writeLong(msbFragmentCollectionId);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (css == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(css);
		}

		if (html == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(html);
		}

		if (js == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(js);
		}
	}

	public long msbFragmentEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long msbFragmentCollectionId;
	public String name;
	public String css;
	public String html;
	public String js;
}