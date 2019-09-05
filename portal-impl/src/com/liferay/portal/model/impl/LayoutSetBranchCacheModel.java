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
import com.liferay.portal.kernel.model.LayoutSetBranch;
import com.liferay.portal.kernel.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing LayoutSetBranch in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutSetBranchCacheModel
	implements CacheModel<LayoutSetBranch>, Externalizable, MVCCModel {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutSetBranchCacheModel)) {
			return false;
		}

		LayoutSetBranchCacheModel layoutSetBranchCacheModel =
			(LayoutSetBranchCacheModel)obj;

		if ((layoutSetBranchId ==
				layoutSetBranchCacheModel.layoutSetBranchId) &&
			(mvccVersion == layoutSetBranchCacheModel.mvccVersion)) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, layoutSetBranchId);

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
		StringBundler sb = new StringBundler(39);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", layoutSetBranchId=");
		sb.append(layoutSetBranchId);
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
		sb.append(", privateLayout=");
		sb.append(privateLayout);
		sb.append(", name=");
		sb.append(name);
		sb.append(", description=");
		sb.append(description);
		sb.append(", master=");
		sb.append(master);
		sb.append(", logoId=");
		sb.append(logoId);
		sb.append(", themeId=");
		sb.append(themeId);
		sb.append(", colorSchemeId=");
		sb.append(colorSchemeId);
		sb.append(", css=");
		sb.append(css);
		sb.append(", settings=");
		sb.append(settings);
		sb.append(", layoutSetPrototypeUuid=");
		sb.append(layoutSetPrototypeUuid);
		sb.append(", layoutSetPrototypeLinkEnabled=");
		sb.append(layoutSetPrototypeLinkEnabled);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutSetBranch toEntityModel() {
		LayoutSetBranchImpl layoutSetBranchImpl = new LayoutSetBranchImpl();

		layoutSetBranchImpl.setMvccVersion(mvccVersion);
		layoutSetBranchImpl.setLayoutSetBranchId(layoutSetBranchId);
		layoutSetBranchImpl.setGroupId(groupId);
		layoutSetBranchImpl.setCompanyId(companyId);
		layoutSetBranchImpl.setUserId(userId);

		if (userName == null) {
			layoutSetBranchImpl.setUserName("");
		}
		else {
			layoutSetBranchImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutSetBranchImpl.setCreateDate(null);
		}
		else {
			layoutSetBranchImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutSetBranchImpl.setModifiedDate(null);
		}
		else {
			layoutSetBranchImpl.setModifiedDate(new Date(modifiedDate));
		}

		layoutSetBranchImpl.setPrivateLayout(privateLayout);

		if (name == null) {
			layoutSetBranchImpl.setName("");
		}
		else {
			layoutSetBranchImpl.setName(name);
		}

		if (description == null) {
			layoutSetBranchImpl.setDescription("");
		}
		else {
			layoutSetBranchImpl.setDescription(description);
		}

		layoutSetBranchImpl.setMaster(master);
		layoutSetBranchImpl.setLogoId(logoId);

		if (themeId == null) {
			layoutSetBranchImpl.setThemeId("");
		}
		else {
			layoutSetBranchImpl.setThemeId(themeId);
		}

		if (colorSchemeId == null) {
			layoutSetBranchImpl.setColorSchemeId("");
		}
		else {
			layoutSetBranchImpl.setColorSchemeId(colorSchemeId);
		}

		if (css == null) {
			layoutSetBranchImpl.setCss("");
		}
		else {
			layoutSetBranchImpl.setCss(css);
		}

		if (settings == null) {
			layoutSetBranchImpl.setSettings("");
		}
		else {
			layoutSetBranchImpl.setSettings(settings);
		}

		if (layoutSetPrototypeUuid == null) {
			layoutSetBranchImpl.setLayoutSetPrototypeUuid("");
		}
		else {
			layoutSetBranchImpl.setLayoutSetPrototypeUuid(
				layoutSetPrototypeUuid);
		}

		layoutSetBranchImpl.setLayoutSetPrototypeLinkEnabled(
			layoutSetPrototypeLinkEnabled);

		layoutSetBranchImpl.resetOriginalValues();

		return layoutSetBranchImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();

		layoutSetBranchId = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		privateLayout = objectInput.readBoolean();
		name = objectInput.readUTF();
		description = objectInput.readUTF();

		master = objectInput.readBoolean();

		logoId = objectInput.readLong();
		themeId = objectInput.readUTF();
		colorSchemeId = objectInput.readUTF();
		css = objectInput.readUTF();
		settings = objectInput.readUTF();
		layoutSetPrototypeUuid = objectInput.readUTF();

		layoutSetPrototypeLinkEnabled = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(mvccVersion);

		objectOutput.writeLong(layoutSetBranchId);

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

		objectOutput.writeBoolean(privateLayout);

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

		objectOutput.writeBoolean(master);

		objectOutput.writeLong(logoId);

		if (themeId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(themeId);
		}

		if (colorSchemeId == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(colorSchemeId);
		}

		if (css == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(css);
		}

		if (settings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(settings);
		}

		if (layoutSetPrototypeUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(layoutSetPrototypeUuid);
		}

		objectOutput.writeBoolean(layoutSetPrototypeLinkEnabled);
	}

	public long mvccVersion;
	public long layoutSetBranchId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public boolean privateLayout;
	public String name;
	public String description;
	public boolean master;
	public long logoId;
	public String themeId;
	public String colorSchemeId;
	public String css;
	public String settings;
	public String layoutSetPrototypeUuid;
	public boolean layoutSetPrototypeLinkEnabled;

}