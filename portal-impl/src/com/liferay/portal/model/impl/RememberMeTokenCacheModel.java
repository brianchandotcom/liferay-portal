/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.portal.kernel.model.RememberMeToken;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing RememberMeToken in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class RememberMeTokenCacheModel
	implements CacheModel<RememberMeToken>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof RememberMeTokenCacheModel)) {
			return false;
		}

		RememberMeTokenCacheModel rememberMeTokenCacheModel =
			(RememberMeTokenCacheModel)object;

		if ((rememberMeTokenId ==
				rememberMeTokenCacheModel.rememberMeTokenId) &&
			(mvccVersion == rememberMeTokenCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, rememberMeTokenId);

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
		StringBundler sb = new StringBundler(15);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", rememberMeTokenId=");
		sb.append(rememberMeTokenId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", expirationDate=");
		sb.append(expirationDate);
		sb.append(", value=");
		sb.append(value);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public RememberMeToken toEntityModel() {
		RememberMeTokenImpl rememberMeTokenImpl = new RememberMeTokenImpl();

		rememberMeTokenImpl.setMvccVersion(mvccVersion);
		rememberMeTokenImpl.setRememberMeTokenId(rememberMeTokenId);
		rememberMeTokenImpl.setCompanyId(companyId);
		rememberMeTokenImpl.setUserId(userId);

		if (createDate == Long.MIN_VALUE) {
			rememberMeTokenImpl.setCreateDate(null);
		}
		else {
			rememberMeTokenImpl.setCreateDate(new Date(createDate));
		}

		if (expirationDate == Long.MIN_VALUE) {
			rememberMeTokenImpl.setExpirationDate(null);
		}
		else {
			rememberMeTokenImpl.setExpirationDate(new Date(expirationDate));
		}

		if (value == null) {
			rememberMeTokenImpl.setValue("");
		}
		else {
			rememberMeTokenImpl.setValue(value);
		}

		rememberMeTokenImpl.resetOriginalValues();

		return rememberMeTokenImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		rememberMeTokenId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		createDate = objectInput.readLong();
		expirationDate = objectInput.readLong();
		value = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(rememberMeTokenId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);
		objectOutput.writeLong(createDate);
		objectOutput.writeLong(expirationDate);

		if (value == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(value);
		}
	}

	public long mvccVersion;
	public long rememberMeTokenId;
	public long companyId;
	public long userId;
	public long createDate;
	public long expirationDate;
	public String value;

}