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

package com.liferay.fragment.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.fragment.model.FragmentEntry;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing FragmentEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntry
 * @generated
 */
@ProviderType
public class FragmentEntryCacheModel implements CacheModel<FragmentEntry>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FragmentEntryCacheModel)) {
			return false;
		}

		FragmentEntryCacheModel fragmentEntryCacheModel = (FragmentEntryCacheModel)obj;

		if (fragmentEntryId == fragmentEntryCacheModel.fragmentEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, fragmentEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(27);

		sb.append("{fragmentEntryId=");
		sb.append(fragmentEntryId);
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
		sb.append(", fragmentCollectionId=");
		sb.append(fragmentCollectionId);
		sb.append(", name=");
		sb.append(name);
		sb.append(", css=");
		sb.append(css);
		sb.append(", html=");
		sb.append(html);
		sb.append(", js=");
		sb.append(js);
		sb.append(", htmlPreviewEntryId=");
		sb.append(htmlPreviewEntryId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FragmentEntry toEntityModel() {
		FragmentEntryImpl fragmentEntryImpl = new FragmentEntryImpl();

		fragmentEntryImpl.setFragmentEntryId(fragmentEntryId);
		fragmentEntryImpl.setGroupId(groupId);
		fragmentEntryImpl.setCompanyId(companyId);
		fragmentEntryImpl.setUserId(userId);

		if (userName == null) {
			fragmentEntryImpl.setUserName("");
		}
		else {
			fragmentEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			fragmentEntryImpl.setCreateDate(null);
		}
		else {
			fragmentEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			fragmentEntryImpl.setModifiedDate(null);
		}
		else {
			fragmentEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		fragmentEntryImpl.setFragmentCollectionId(fragmentCollectionId);

		if (name == null) {
			fragmentEntryImpl.setName("");
		}
		else {
			fragmentEntryImpl.setName(name);
		}

		if (css == null) {
			fragmentEntryImpl.setCss("");
		}
		else {
			fragmentEntryImpl.setCss(css);
		}

		if (html == null) {
			fragmentEntryImpl.setHtml("");
		}
		else {
			fragmentEntryImpl.setHtml(html);
		}

		if (js == null) {
			fragmentEntryImpl.setJs("");
		}
		else {
			fragmentEntryImpl.setJs(js);
		}

		fragmentEntryImpl.setHtmlPreviewEntryId(htmlPreviewEntryId);

		fragmentEntryImpl.resetOriginalValues();

		return fragmentEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		fragmentEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		fragmentCollectionId = objectInput.readLong();
		name = objectInput.readUTF();
		css = objectInput.readUTF();
		html = objectInput.readUTF();
		js = objectInput.readUTF();

		htmlPreviewEntryId = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(fragmentEntryId);

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

		objectOutput.writeLong(fragmentCollectionId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (css == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(css);
		}

		if (html == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(html);
		}

		if (js == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(js);
		}

		objectOutput.writeLong(htmlPreviewEntryId);
	}

	public long fragmentEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long fragmentCollectionId;
	public String name;
	public String css;
	public String html;
	public String js;
	public long htmlPreviewEntryId;
}