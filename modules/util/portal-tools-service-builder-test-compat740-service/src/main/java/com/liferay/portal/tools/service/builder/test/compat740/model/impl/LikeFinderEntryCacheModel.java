/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.compat740.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.tools.service.builder.test.compat740.model.LikeFinderEntry;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing LikeFinderEntry in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LikeFinderEntryCacheModel
	implements CacheModel<LikeFinderEntry>, Externalizable {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof LikeFinderEntryCacheModel)) {
			return false;
		}

		LikeFinderEntryCacheModel likeFinderEntryCacheModel =
			(LikeFinderEntryCacheModel)object;

		if (likeFinderEntryId == likeFinderEntryCacheModel.likeFinderEntryId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, likeFinderEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(11);

		sb.append("{likeFinderEntryId=");
		sb.append(likeFinderEntryId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", ownerId=");
		sb.append(ownerId);
		sb.append(", ownerType=");
		sb.append(ownerType);
		sb.append(", portletId=");
		sb.append(portletId);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LikeFinderEntry toEntityModel() {
		LikeFinderEntryImpl likeFinderEntryImpl = new LikeFinderEntryImpl();

		likeFinderEntryImpl.setLikeFinderEntryId(likeFinderEntryId);
		likeFinderEntryImpl.setCompanyId(companyId);
		likeFinderEntryImpl.setOwnerId(ownerId);
		likeFinderEntryImpl.setOwnerType(ownerType);

		if (portletId == null) {
			likeFinderEntryImpl.setPortletId("");
		}
		else {
			likeFinderEntryImpl.setPortletId(portletId);
		}

		likeFinderEntryImpl.resetOriginalValues();

		return likeFinderEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		likeFinderEntryId = objectInput.readLong();

		companyId = objectInput.readLong();

		ownerId = objectInput.readLong();

		ownerType = objectInput.readInt();
		portletId = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(likeFinderEntryId);

		objectOutput.writeLong(companyId);

		objectOutput.writeLong(ownerId);

		objectOutput.writeInt(ownerType);

		if (portletId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(portletId);
		}
	}

	public long likeFinderEntryId;
	public long companyId;
	public long ownerId;
	public int ownerType;
	public String portletId;

}
// SB-Hash:-662560061