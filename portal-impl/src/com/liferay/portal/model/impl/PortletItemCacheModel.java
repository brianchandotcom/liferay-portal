/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.PortletItem;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing PortletItem in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see PortletItem
 * @generated
 */
public class PortletItemCacheModel implements CacheModel<PortletItem>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(23);

		sb.append("{portletItemId=");
		sb.append(portletItemId);
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
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public PortletItem toEntityModel() {
		PortletItemImpl portletItemImpl = new PortletItemImpl();

		portletItemImpl.setPortletItemId(portletItemId);
		portletItemImpl.setGroupId(groupId);
		portletItemImpl.setCompanyId(companyId);
		portletItemImpl.setUserId(userId);

		if (userName == null) {
			portletItemImpl.setUserName(StringPool.BLANK);
		}
		else {
			portletItemImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			portletItemImpl.setCreateDate(null);
		}
		else {
			portletItemImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			portletItemImpl.setModifiedDate(null);
		}
		else {
			portletItemImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (name == null) {
			portletItemImpl.setName(StringPool.BLANK);
		}
		else {
			portletItemImpl.setName(name);
		}

		if (portletId == null) {
			portletItemImpl.setPortletId(StringPool.BLANK);
		}
		else {
			portletItemImpl.setPortletId(portletId);
		}

		portletItemImpl.setClassNameId(classNameId);
		portletItemImpl.setMvccVersion(mvccVersion);

		portletItemImpl.resetOriginalValues();

		return portletItemImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		portletItemId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		name = objectInput.readUTF();
		portletId = objectInput.readUTF();
		classNameId = objectInput.readLong();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(portletItemId);
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

		if (portletId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(portletId);
		}

		objectOutput.writeLong(classNameId);
		objectOutput.writeLong(mvccVersion);
	}

	public long portletItemId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String name;
	public String portletId;
	public long classNameId;
	public long mvccVersion;
}