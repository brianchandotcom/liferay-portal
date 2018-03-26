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

import com.liferay.fragment.model.FragmentEntryLink;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing FragmentEntryLink in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see FragmentEntryLink
 * @generated
 */
@ProviderType
public class FragmentEntryLinkCacheModel implements CacheModel<FragmentEntryLink>,
	Externalizable {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof FragmentEntryLinkCacheModel)) {
			return false;
		}

		FragmentEntryLinkCacheModel fragmentEntryLinkCacheModel = (FragmentEntryLinkCacheModel)obj;

		if (fragmentEntryLinkId == fragmentEntryLinkCacheModel.fragmentEntryLinkId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, fragmentEntryLinkId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(35);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", fragmentEntryLinkId=");
		sb.append(fragmentEntryLinkId);
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
		sb.append(", originalFragmentEntryLinkId=");
		sb.append(originalFragmentEntryLinkId);
		sb.append(", fragmentEntryId=");
		sb.append(fragmentEntryId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
		sb.append(", css=");
		sb.append(css);
		sb.append(", html=");
		sb.append(html);
		sb.append(", js=");
		sb.append(js);
		sb.append(", editableValues=");
		sb.append(editableValues);
		sb.append(", position=");
		sb.append(position);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public FragmentEntryLink toEntityModel() {
		FragmentEntryLinkImpl fragmentEntryLinkImpl = new FragmentEntryLinkImpl();

		if (uuid == null) {
			fragmentEntryLinkImpl.setUuid("");
		}
		else {
			fragmentEntryLinkImpl.setUuid(uuid);
		}

		fragmentEntryLinkImpl.setFragmentEntryLinkId(fragmentEntryLinkId);
		fragmentEntryLinkImpl.setGroupId(groupId);
		fragmentEntryLinkImpl.setCompanyId(companyId);
		fragmentEntryLinkImpl.setUserId(userId);

		if (userName == null) {
			fragmentEntryLinkImpl.setUserName("");
		}
		else {
			fragmentEntryLinkImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			fragmentEntryLinkImpl.setCreateDate(null);
		}
		else {
			fragmentEntryLinkImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			fragmentEntryLinkImpl.setModifiedDate(null);
		}
		else {
			fragmentEntryLinkImpl.setModifiedDate(new Date(modifiedDate));
		}

		fragmentEntryLinkImpl.setOriginalFragmentEntryLinkId(originalFragmentEntryLinkId);
		fragmentEntryLinkImpl.setFragmentEntryId(fragmentEntryId);
		fragmentEntryLinkImpl.setClassNameId(classNameId);
		fragmentEntryLinkImpl.setClassPK(classPK);

		if (css == null) {
			fragmentEntryLinkImpl.setCss("");
		}
		else {
			fragmentEntryLinkImpl.setCss(css);
		}

		if (html == null) {
			fragmentEntryLinkImpl.setHtml("");
		}
		else {
			fragmentEntryLinkImpl.setHtml(html);
		}

		if (js == null) {
			fragmentEntryLinkImpl.setJs("");
		}
		else {
			fragmentEntryLinkImpl.setJs(js);
		}

		if (editableValues == null) {
			fragmentEntryLinkImpl.setEditableValues("");
		}
		else {
			fragmentEntryLinkImpl.setEditableValues(editableValues);
		}

		fragmentEntryLinkImpl.setPosition(position);

		fragmentEntryLinkImpl.resetOriginalValues();

		return fragmentEntryLinkImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		uuid = objectInput.readUTF();

		fragmentEntryLinkId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		originalFragmentEntryLinkId = objectInput.readLong();

		fragmentEntryId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
		css = objectInput.readUTF();
		html = objectInput.readUTF();
		js = objectInput.readUTF();
		editableValues = objectInput.readUTF();

		position = objectInput.readInt();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(fragmentEntryLinkId);

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

		objectOutput.writeLong(originalFragmentEntryLinkId);

		objectOutput.writeLong(fragmentEntryId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

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

		if (editableValues == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(editableValues);
		}

		objectOutput.writeInt(position);
	}

	public String uuid;
	public long fragmentEntryLinkId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long originalFragmentEntryLinkId;
	public long fragmentEntryId;
	public long classNameId;
	public long classPK;
	public String css;
	public String html;
	public String js;
	public String editableValues;
	public int position;
}