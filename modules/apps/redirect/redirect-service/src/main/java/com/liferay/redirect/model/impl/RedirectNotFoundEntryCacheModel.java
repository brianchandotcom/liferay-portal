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

package com.liferay.redirect.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.redirect.model.RedirectNotFoundEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing RedirectNotFoundEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RedirectNotFoundEntryCacheModel
	implements CacheModel<RedirectNotFoundEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof RedirectNotFoundEntryCacheModel)) {
			return false;
		}

		RedirectNotFoundEntryCacheModel redirectNotFoundEntryCacheModel =
			(RedirectNotFoundEntryCacheModel)obj;

		if ((redirectNotFoundEntryId ==
				redirectNotFoundEntryCacheModel.redirectNotFoundEntryId) &&
			(mvccVersion == redirectNotFoundEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, redirectNotFoundEntryId);

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
		StringBundler sb = new StringBundler(23);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", redirectNotFoundEntryId=");
		sb.append(redirectNotFoundEntryId);
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
		sb.append(", hits=");
		sb.append(hits);
		sb.append(", ignored=");
		sb.append(ignored);
		sb.append(", url=");
		sb.append(url);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RedirectNotFoundEntry toEntityModel() {
		RedirectNotFoundEntryImpl redirectNotFoundEntryImpl =
			new RedirectNotFoundEntryImpl();

		redirectNotFoundEntryImpl.setMvccVersion(mvccVersion);
		redirectNotFoundEntryImpl.setRedirectNotFoundEntryId(
			redirectNotFoundEntryId);
		redirectNotFoundEntryImpl.setGroupId(groupId);
		redirectNotFoundEntryImpl.setCompanyId(companyId);
		redirectNotFoundEntryImpl.setUserId(userId);

		if (userName == null) {
			redirectNotFoundEntryImpl.setUserName("");
		}
		else {
			redirectNotFoundEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			redirectNotFoundEntryImpl.setCreateDate(null);
		}
		else {
			redirectNotFoundEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			redirectNotFoundEntryImpl.setModifiedDate(null);
		}
		else {
			redirectNotFoundEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		redirectNotFoundEntryImpl.setHits(hits);
		redirectNotFoundEntryImpl.setIgnored(ignored);

		if (url == null) {
			redirectNotFoundEntryImpl.setUrl("");
		}
		else {
			redirectNotFoundEntryImpl.setUrl(url);
		}

		redirectNotFoundEntryImpl.resetOriginalValues();

		return redirectNotFoundEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		redirectNotFoundEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		hits = objectInput.readLong();

		ignored = objectInput.readBoolean();
		url = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(redirectNotFoundEntryId);

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

		objectOutput.writeLong(hits);

		objectOutput.writeBoolean(ignored);

		if (url == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(url);
		}
	}

	public long mvccVersion;
	public long redirectNotFoundEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long hits;
	public boolean ignored;
	public String url;

}