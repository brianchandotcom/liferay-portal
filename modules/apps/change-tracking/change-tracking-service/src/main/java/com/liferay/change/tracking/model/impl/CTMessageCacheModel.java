/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.model.impl;

import com.liferay.change.tracking.model.CTMessage;
import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CTMessage in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class CTMessageCacheModel
	implements CacheModel<CTMessage>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CTMessageCacheModel)) {
			return false;
		}

		CTMessageCacheModel ctMessageCacheModel = (CTMessageCacheModel)object;

		if ((ctMessageId == ctMessageCacheModel.ctMessageId) &&
			(mvccVersion == ctMessageCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, ctMessageId);

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
		StringBundler sb = new StringBundler(11);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctMessageId=");
		sb.append(ctMessageId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", messageContent=");
		sb.append(messageContent);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CTMessage toEntityModel() {
		CTMessageImpl ctMessageImpl = new CTMessageImpl();

		ctMessageImpl.setMvccVersion(mvccVersion);
		ctMessageImpl.setCtMessageId(ctMessageId);
		ctMessageImpl.setCompanyId(companyId);
		ctMessageImpl.setCtCollectionId(ctCollectionId);

		if (messageContent == null) {
			ctMessageImpl.setMessageContent("");
		}
		else {
			ctMessageImpl.setMessageContent(messageContent);
		}

		ctMessageImpl.resetOriginalValues();

		return ctMessageImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput)
		throws ClassNotFoundException, IOException {

		mvccVersion = objectInput.readLong();

		ctMessageId = objectInput.readLong();

		companyId = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		messageContent = (String)objectInput.readObject();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctMessageId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(ctCollectionId);

		if (messageContent == null) {
			objectOutput.writeObject("");
		}
		else {
			objectOutput.writeObject(messageContent);
		}
	}

	public long mvccVersion;
	public long ctMessageId;
	public long companyId;
	public long ctCollectionId;
	public String messageContent;

}