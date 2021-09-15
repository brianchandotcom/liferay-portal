/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.model.impl;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.MVCCModel;
import com.liferay.search.experiences.model.SXPBlueprint;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing SXPBlueprint in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class SXPBlueprintCacheModel
	implements CacheModel<SXPBlueprint>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof SXPBlueprintCacheModel)) {
			return false;
		}

		SXPBlueprintCacheModel sxpBlueprintCacheModel =
			(SXPBlueprintCacheModel)object;

		if ((sxpBlueprintId == sxpBlueprintCacheModel.sxpBlueprintId) &&
			(mvccVersion == sxpBlueprintCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, sxpBlueprintId);

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
		StringBundler sb = new StringBundler(35);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", sxpBlueprintId=");
		sb.append(sxpBlueprintId);
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
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", configurationJSON=");
		sb.append(configurationJSON);
		sb.append(", selectedElementsJSON=");
		sb.append(selectedElementsJSON);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public SXPBlueprint toEntityModel() {
		SXPBlueprintImpl sxpBlueprintImpl = new SXPBlueprintImpl();

		sxpBlueprintImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			sxpBlueprintImpl.setUuid("");
		}
		else {
			sxpBlueprintImpl.setUuid(uuid);
		}

		sxpBlueprintImpl.setSXPBlueprintId(sxpBlueprintId);
		sxpBlueprintImpl.setGroupId(groupId);
		sxpBlueprintImpl.setCompanyId(companyId);
		sxpBlueprintImpl.setUserId(userId);

		if (userName == null) {
			sxpBlueprintImpl.setUserName("");
		}
		else {
			sxpBlueprintImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			sxpBlueprintImpl.setCreateDate(null);
		}
		else {
			sxpBlueprintImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			sxpBlueprintImpl.setModifiedDate(null);
		}
		else {
			sxpBlueprintImpl.setModifiedDate(new Date(modifiedDate));
		}

		sxpBlueprintImpl.setStatus(status);
		sxpBlueprintImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			sxpBlueprintImpl.setStatusByUserName("");
		}
		else {
			sxpBlueprintImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			sxpBlueprintImpl.setStatusDate(null);
		}
		else {
			sxpBlueprintImpl.setStatusDate(new Date(statusDate));
		}

		if (title == null) {
			sxpBlueprintImpl.setTitle("");
		}
		else {
			sxpBlueprintImpl.setTitle(title);
		}

		if (description == null) {
			sxpBlueprintImpl.setDescription("");
		}
		else {
			sxpBlueprintImpl.setDescription(description);
		}

		if (configurationJSON == null) {
			sxpBlueprintImpl.setConfigurationJSON("");
		}
		else {
			sxpBlueprintImpl.setConfigurationJSON(configurationJSON);
		}

		if (selectedElementsJSON == null) {
			sxpBlueprintImpl.setSelectedElementsJSON("");
		}
		else {
			sxpBlueprintImpl.setSelectedElementsJSON(selectedElementsJSON);
		}

		sxpBlueprintImpl.resetOriginalValues();

		return sxpBlueprintImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();

		sxpBlueprintId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		status = objectInput.readInt();

		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
		title = objectInput.readUTF();
		description = objectInput.readUTF();
		configurationJSON = objectInput.readUTF();
		selectedElementsJSON = objectInput.readUTF();
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

		objectOutput.writeLong(sxpBlueprintId);

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

		objectOutput.writeInt(status);

		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);

		if (title == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (description == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (configurationJSON == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(configurationJSON);
		}

		if (selectedElementsJSON == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(selectedElementsJSON);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long sxpBlueprintId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
	public String title;
	public String description;
	public String configurationJSON;
	public String selectedElementsJSON;

}