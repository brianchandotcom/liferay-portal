/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.LayoutSetBranch;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing LayoutSetBranch in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetBranch
 * @generated
 */
public class LayoutSetBranchCacheModel implements CacheModel<LayoutSetBranch>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(43);

		sb.append("{layoutSetBranchId=");
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
		sb.append(", wapThemeId=");
		sb.append(wapThemeId);
		sb.append(", wapColorSchemeId=");
		sb.append(wapColorSchemeId);
		sb.append(", css=");
		sb.append(css);
		sb.append(", settings=");
		sb.append(settings);
		sb.append(", layoutSetPrototypeUuid=");
		sb.append(layoutSetPrototypeUuid);
		sb.append(", layoutSetPrototypeLinkEnabled=");
		sb.append(layoutSetPrototypeLinkEnabled);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutSetBranch toEntityModel() {
		LayoutSetBranchImpl layoutSetBranchImpl = new LayoutSetBranchImpl();

		layoutSetBranchImpl.setLayoutSetBranchId(layoutSetBranchId);
		layoutSetBranchImpl.setGroupId(groupId);
		layoutSetBranchImpl.setCompanyId(companyId);
		layoutSetBranchImpl.setUserId(userId);

		if (userName == null) {
			layoutSetBranchImpl.setUserName(StringPool.BLANK);
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
			layoutSetBranchImpl.setName(StringPool.BLANK);
		}
		else {
			layoutSetBranchImpl.setName(name);
		}

		if (description == null) {
			layoutSetBranchImpl.setDescription(StringPool.BLANK);
		}
		else {
			layoutSetBranchImpl.setDescription(description);
		}

		layoutSetBranchImpl.setMaster(master);
		layoutSetBranchImpl.setLogoId(logoId);

		if (themeId == null) {
			layoutSetBranchImpl.setThemeId(StringPool.BLANK);
		}
		else {
			layoutSetBranchImpl.setThemeId(themeId);
		}

		if (colorSchemeId == null) {
			layoutSetBranchImpl.setColorSchemeId(StringPool.BLANK);
		}
		else {
			layoutSetBranchImpl.setColorSchemeId(colorSchemeId);
		}

		if (wapThemeId == null) {
			layoutSetBranchImpl.setWapThemeId(StringPool.BLANK);
		}
		else {
			layoutSetBranchImpl.setWapThemeId(wapThemeId);
		}

		if (wapColorSchemeId == null) {
			layoutSetBranchImpl.setWapColorSchemeId(StringPool.BLANK);
		}
		else {
			layoutSetBranchImpl.setWapColorSchemeId(wapColorSchemeId);
		}

		if (css == null) {
			layoutSetBranchImpl.setCss(StringPool.BLANK);
		}
		else {
			layoutSetBranchImpl.setCss(css);
		}

		if (settings == null) {
			layoutSetBranchImpl.setSettings(StringPool.BLANK);
		}
		else {
			layoutSetBranchImpl.setSettings(settings);
		}

		if (layoutSetPrototypeUuid == null) {
			layoutSetBranchImpl.setLayoutSetPrototypeUuid(StringPool.BLANK);
		}
		else {
			layoutSetBranchImpl.setLayoutSetPrototypeUuid(layoutSetPrototypeUuid);
		}

		layoutSetBranchImpl.setLayoutSetPrototypeLinkEnabled(layoutSetPrototypeLinkEnabled);
		layoutSetBranchImpl.setMvccVersion(mvccVersion);

		layoutSetBranchImpl.resetOriginalValues();

		return layoutSetBranchImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
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
		wapThemeId = objectInput.readUTF();
		wapColorSchemeId = objectInput.readUTF();
		css = objectInput.readUTF();
		settings = objectInput.readUTF();
		layoutSetPrototypeUuid = objectInput.readUTF();
		layoutSetPrototypeLinkEnabled = objectInput.readBoolean();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(layoutSetBranchId);
		objectOutput.writeLong(groupId);
		objectOutput.writeLong(companyId);
		objectOutput.writeLong(userId);

		if (userName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(userName);
		}

		objectOutput.writeLong(createDate);
		objectOutput.writeLong(modifiedDate);
		objectOutput.writeBoolean(privateLayout);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		objectOutput.writeBoolean(master);
		objectOutput.writeLong(logoId);

		if (themeId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(themeId);
		}

		if (colorSchemeId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(colorSchemeId);
		}

		if (wapThemeId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(wapThemeId);
		}

		if (wapColorSchemeId == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(wapColorSchemeId);
		}

		if (css == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(css);
		}

		if (settings == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(settings);
		}

		if (layoutSetPrototypeUuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(layoutSetPrototypeUuid);
		}

		objectOutput.writeBoolean(layoutSetPrototypeLinkEnabled);
		objectOutput.writeLong(mvccVersion);
	}

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
	public String wapThemeId;
	public String wapColorSchemeId;
	public String css;
	public String settings;
	public String layoutSetPrototypeUuid;
	public boolean layoutSetPrototypeLinkEnabled;
	public long mvccVersion;
}