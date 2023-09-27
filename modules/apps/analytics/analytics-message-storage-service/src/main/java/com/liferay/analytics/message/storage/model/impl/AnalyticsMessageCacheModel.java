/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.message.storage.model.impl;

import com.liferay.analytics.message.storage.model.AnalyticsMessage;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing AnalyticsMessage in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AnalyticsMessageCacheModel
	implements CacheModel<AnalyticsMessage>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof AnalyticsMessageCacheModel)) {
			return false;
		}

		AnalyticsMessageCacheModel analyticsMessageCacheModel =
			(AnalyticsMessageCacheModel)object;

		if ((analyticsMessageId ==
				analyticsMessageCacheModel.analyticsMessageId) &&
			(mvccVersion == analyticsMessageCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, analyticsMessageId);

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
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", analyticsMessageId=");
		sb.append(analyticsMessageId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", userId=");
		sb.append(userId);
		sb.append(", userName=");
		sb.append(userName);
		sb.append(", createDate=");
		sb.append(createDate);

		return sb.toString();
	}

	@Override
	public AnalyticsMessage toEntityModel() {
		AnalyticsMessageImpl analyticsMessageImpl = new AnalyticsMessageImpl();

		analyticsMessageImpl.setMvccVersion(mvccVersion);
		analyticsMessageImpl.setCtCollectionId(ctCollectionId);
		analyticsMessageImpl.setAnalyticsMessageId(analyticsMessageId);
		analyticsMessageImpl.setCompanyId(companyId);
		analyticsMessageImpl.setUserId(userId);

		if (userName == null) {
			analyticsMessageImpl.setUserName("");
		}
		else {
			analyticsMessageImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			analyticsMessageImpl.setCreateDate(null);
		}
		else {
			analyticsMessageImpl.setCreateDate(new Date(createDate));
		}

		analyticsMessageImpl.resetOriginalValues();

		return analyticsMessageImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();

		analyticsMessageId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		objectOutput.writeLong(analyticsMessageId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
	}

	public long mvccVersion;
	public long ctCollectionId;
	public long analyticsMessageId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;

}