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

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.LayoutFriendlyURL;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing LayoutFriendlyURL in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutFriendlyURLCacheModel
	implements CacheModel<LayoutFriendlyURL>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutFriendlyURLCacheModel)) {
			return false;
		}

		LayoutFriendlyURLCacheModel layoutFriendlyURLCacheModel =
			(LayoutFriendlyURLCacheModel)obj;

		if ((layoutFriendlyURLId ==
				layoutFriendlyURLCacheModel.layoutFriendlyURLId) &&
			(mvccVersion == layoutFriendlyURLCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, layoutFriendlyURLId);

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
		StringBundler sb = new StringBundler(29);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", layoutFriendlyURLId=");
		sb.append(layoutFriendlyURLId);
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
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", privateLayout=");
		sb.append(privateLayout);
		sb.append(", friendlyURL=");
		sb.append(friendlyURL);
		sb.append(", languageId=");
		sb.append(languageId);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutFriendlyURL toEntityModel() {
		LayoutFriendlyURLImpl layoutFriendlyURLImpl =
			new LayoutFriendlyURLImpl();

		layoutFriendlyURLImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			layoutFriendlyURLImpl.setUuid("");
		}
		else {
			layoutFriendlyURLImpl.setUuid(uuid);
		}

		layoutFriendlyURLImpl.setLayoutFriendlyURLId(layoutFriendlyURLId);
		layoutFriendlyURLImpl.setGroupId(groupId);
		layoutFriendlyURLImpl.setCompanyId(companyId);
		layoutFriendlyURLImpl.setUserId(userId);

		if (userName == null) {
			layoutFriendlyURLImpl.setUserName("");
		}
		else {
			layoutFriendlyURLImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutFriendlyURLImpl.setCreateDate(null);
		}
		else {
			layoutFriendlyURLImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutFriendlyURLImpl.setModifiedDate(null);
		}
		else {
			layoutFriendlyURLImpl.setModifiedDate(new Date(modifiedDate));
		}

		layoutFriendlyURLImpl.setPlid(plid);
		layoutFriendlyURLImpl.setPrivateLayout(privateLayout);

		if (friendlyURL == null) {
			layoutFriendlyURLImpl.setFriendlyURL("");
		}
		else {
			layoutFriendlyURLImpl.setFriendlyURL(friendlyURL);
		}

		if (languageId == null) {
			layoutFriendlyURLImpl.setLanguageId("");
		}
		else {
			layoutFriendlyURLImpl.setLanguageId(languageId);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			layoutFriendlyURLImpl.setLastPublishDate(null);
		}
		else {
			layoutFriendlyURLImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		layoutFriendlyURLImpl.resetOriginalValues();

		return layoutFriendlyURLImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		layoutFriendlyURLId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		plid = objectInput.readLong();

		privateLayout = objectInput.readBoolean();
		friendlyURL = objectInput.readUTF();
		languageId = objectInput.readUTF();
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

		objectOutput.writeLong(layoutFriendlyURLId);

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

		objectOutput.writeLong(plid);

		objectOutput.writeBoolean(privateLayout);

		if (friendlyURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(friendlyURL);
		}

		if (languageId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(languageId);
		}

		objectOutput.writeLong(lastPublishDate);
	}

	public long mvccVersion;
	public String uuid;
	public long layoutFriendlyURLId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long plid;
	public boolean privateLayout;
	public String friendlyURL;
	public String languageId;
	public long lastPublishDate;

}