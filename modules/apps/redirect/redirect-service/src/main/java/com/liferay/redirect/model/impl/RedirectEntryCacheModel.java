/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.redirect.model.RedirectEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing RedirectEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RedirectEntryCacheModel
	implements CacheModel<RedirectEntry>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RedirectEntryCacheModel)) {
			return false;
		}

		RedirectEntryCacheModel redirectEntryCacheModel =
			(RedirectEntryCacheModel)object;

		if ((redirectEntryId == redirectEntryCacheModel.redirectEntryId) &&
			(mvccVersion == redirectEntryCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, redirectEntryId);

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
		sb.append(", redirectEntryId=");
		sb.append(redirectEntryId);
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
		sb.append(", destinationURL=");
		sb.append(destinationURL);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", lastOccurrenceDate=");
		sb.append(lastOccurrenceDate);
		sb.append(", permanent=");
		sb.append(permanent);
		sb.append(", sourceURL=");
		sb.append(sourceURL);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RedirectEntry toEntityModel() {
		RedirectEntryImpl redirectEntryImpl = new RedirectEntryImpl();

		redirectEntryImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			redirectEntryImpl.setUuid("");
		}
		else {
			redirectEntryImpl.setUuid(uuid);
		}

		redirectEntryImpl.setRedirectEntryId(redirectEntryId);
		redirectEntryImpl.setGroupId(groupId);
		redirectEntryImpl.setCompanyId(companyId);
		redirectEntryImpl.setUserId(userId);

		if (userName == null) {
			redirectEntryImpl.setUserName("");
		}
		else {
			redirectEntryImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			redirectEntryImpl.setCreateDate(null);
		}
		else {
			redirectEntryImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			redirectEntryImpl.setModifiedDate(null);
		}
		else {
			redirectEntryImpl.setModifiedDate(new Date(modifiedDate));
		}

		if (destinationURL == null) {
			redirectEntryImpl.setDestinationURL("");
		}
		else {
			redirectEntryImpl.setDestinationURL(destinationURL);
		}

		if (expirationDate == Long.MIN_VALUE) {
			redirectEntryImpl.setExpirationDate(null);
		}
		else {
			redirectEntryImpl.setExpirationDate(new Date(expirationDate));
		}

		if (lastOccurrenceDate == Long.MIN_VALUE) {
			redirectEntryImpl.setLastOccurrenceDate(null);
		}
		else {
			redirectEntryImpl.setLastOccurrenceDate(
				new Date(lastOccurrenceDate));
		}

		redirectEntryImpl.setPermanent(permanent);

		if (sourceURL == null) {
			redirectEntryImpl.setSourceURL("");
		}
		else {
			redirectEntryImpl.setSourceURL(sourceURL);
		}

		redirectEntryImpl.resetOriginalValues();

		return redirectEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		redirectEntryId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		destinationURL = objectInput.readUTF();
		expirationDate = objectInput.readLong();
		lastOccurrenceDate = objectInput.readLong();

		permanent = objectInput.readBoolean();
		sourceURL = objectInput.readUTF();
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

		objectOutput.writeLong(redirectEntryId);

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

		if (destinationURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(destinationURL);
		}

		objectOutput.writeLong(expirationDate);
		objectOutput.writeLong(lastOccurrenceDate);

		objectOutput.writeBoolean(permanent);

		if (sourceURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sourceURL);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long redirectEntryId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public String destinationURL;
	public long expirationDate;
	public long lastOccurrenceDate;
	public boolean permanent;
	public String sourceURL;

}