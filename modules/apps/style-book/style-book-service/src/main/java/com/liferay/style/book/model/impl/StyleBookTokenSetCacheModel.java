/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.style.book.model.StyleBookTokenSet;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing StyleBookTokenSet in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class StyleBookTokenSetCacheModel
	implements CacheModel<StyleBookTokenSet>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof StyleBookTokenSetCacheModel)) {
			return false;
		}

		StyleBookTokenSetCacheModel styleBookTokenSetCacheModel =
			(StyleBookTokenSetCacheModel)object;

		if ((styleBookTokenSetId ==
				styleBookTokenSetCacheModel.styleBookTokenSetId) &&
			(mvccVersion == styleBookTokenSetCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, styleBookTokenSetId);

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
		StringBundler sb = new StringBundler(33);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", ctCollectionId=");
		sb.append(ctCollectionId);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", externalReferenceCode=");
		sb.append(externalReferenceCode);
		sb.append(", styleBookTokenSetId=");
		sb.append(styleBookTokenSetId);
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
		sb.append(", styleBookEntryId=");
		sb.append(styleBookEntryId);
		sb.append(", frontendTokenDefinitionId=");
		sb.append(frontendTokenDefinitionId);
		sb.append(", frontendTokenCategoryName=");
		sb.append(frontendTokenCategoryName);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public StyleBookTokenSet toEntityModel() {
		StyleBookTokenSetImpl styleBookTokenSetImpl =
			new StyleBookTokenSetImpl();

		styleBookTokenSetImpl.setMvccVersion(mvccVersion);
		styleBookTokenSetImpl.setCtCollectionId(ctCollectionId);

		if (uuid == null) {
			styleBookTokenSetImpl.setUuid("");
		}
		else {
			styleBookTokenSetImpl.setUuid(uuid);
		}

		if (externalReferenceCode == null) {
			styleBookTokenSetImpl.setExternalReferenceCode("");
		}
		else {
			styleBookTokenSetImpl.setExternalReferenceCode(
				externalReferenceCode);
		}

		styleBookTokenSetImpl.setStyleBookTokenSetId(styleBookTokenSetId);
		styleBookTokenSetImpl.setGroupId(groupId);
		styleBookTokenSetImpl.setCompanyId(companyId);
		styleBookTokenSetImpl.setUserId(userId);

		if (userName == null) {
			styleBookTokenSetImpl.setUserName("");
		}
		else {
			styleBookTokenSetImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			styleBookTokenSetImpl.setCreateDate(null);
		}
		else {
			styleBookTokenSetImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			styleBookTokenSetImpl.setModifiedDate(null);
		}
		else {
			styleBookTokenSetImpl.setModifiedDate(new Date(modifiedDate));
		}

		styleBookTokenSetImpl.setStyleBookEntryId(styleBookEntryId);

		if (frontendTokenDefinitionId == null) {
			styleBookTokenSetImpl.setFrontendTokenDefinitionId("");
		}
		else {
			styleBookTokenSetImpl.setFrontendTokenDefinitionId(
				frontendTokenDefinitionId);
		}

		if (frontendTokenCategoryName == null) {
			styleBookTokenSetImpl.setFrontendTokenCategoryName("");
		}
		else {
			styleBookTokenSetImpl.setFrontendTokenCategoryName(
				frontendTokenCategoryName);
		}

		if (name == null) {
			styleBookTokenSetImpl.setName("");
		}
		else {
			styleBookTokenSetImpl.setName(name);
		}

		if (description == null) {
			styleBookTokenSetImpl.setDescription("");
		}
		else {
			styleBookTokenSetImpl.setDescription(description);
		}

		styleBookTokenSetImpl.resetOriginalValues();

		return styleBookTokenSetImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		ctCollectionId = objectInput.readLong();
		uuid = objectInput.readUTF();
		externalReferenceCode = objectInput.readUTF();

		styleBookTokenSetId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		styleBookEntryId = objectInput.readLong();
		frontendTokenDefinitionId = objectInput.readUTF();
		frontendTokenCategoryName = objectInput.readUTF();
		name = objectInput.readUTF();
		description = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(ctCollectionId);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		if (externalReferenceCode == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(externalReferenceCode);
		}

		objectOutput.writeLong(styleBookTokenSetId);

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

		objectOutput.writeLong(styleBookEntryId);

		if (frontendTokenDefinitionId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(frontendTokenDefinitionId);
		}

		if (frontendTokenCategoryName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(frontendTokenCategoryName);
		}

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}
	}

	public long mvccVersion;
	public long ctCollectionId;
	public String uuid;
	public String externalReferenceCode;
	public long styleBookTokenSetId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long styleBookEntryId;
	public String frontendTokenDefinitionId;
	public String frontendTokenCategoryName;
	public String name;
	public String description;

}
// LIFERAY-SERVICE-BUILDER-HASH:-2052094932