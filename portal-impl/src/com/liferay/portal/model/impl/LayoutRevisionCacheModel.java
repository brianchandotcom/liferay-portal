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
import com.liferay.portal.model.LayoutRevision;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing LayoutRevision in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutRevision
 * @generated
 */
public class LayoutRevisionCacheModel implements CacheModel<LayoutRevision>,
	Externalizable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(63);

		sb.append("{layoutRevisionId=");
		sb.append(layoutRevisionId);
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
		sb.append(", layoutSetBranchId=");
		sb.append(layoutSetBranchId);
		sb.append(", layoutBranchId=");
		sb.append(layoutBranchId);
		sb.append(", parentLayoutRevisionId=");
		sb.append(parentLayoutRevisionId);
		sb.append(", head=");
		sb.append(head);
		sb.append(", major=");
		sb.append(major);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", privateLayout=");
		sb.append(privateLayout);
		sb.append(", name=");
		sb.append(name);
		sb.append(", title=");
		sb.append(title);
		sb.append(", description=");
		sb.append(description);
		sb.append(", keywords=");
		sb.append(keywords);
		sb.append(", robots=");
		sb.append(robots);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append(", iconImageId=");
		sb.append(iconImageId);
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
		sb.append(", status=");
		sb.append(status);
		sb.append(", statusByUserId=");
		sb.append(statusByUserId);
		sb.append(", statusByUserName=");
		sb.append(statusByUserName);
		sb.append(", statusDate=");
		sb.append(statusDate);
		sb.append(", mvccVersion=");
		sb.append(mvccVersion);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutRevision toEntityModel() {
		LayoutRevisionImpl layoutRevisionImpl = new LayoutRevisionImpl();

		layoutRevisionImpl.setLayoutRevisionId(layoutRevisionId);
		layoutRevisionImpl.setGroupId(groupId);
		layoutRevisionImpl.setCompanyId(companyId);
		layoutRevisionImpl.setUserId(userId);

		if (userName == null) {
			layoutRevisionImpl.setUserName(StringPool.BLANK);
		}
		else {
			layoutRevisionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutRevisionImpl.setCreateDate(null);
		}
		else {
			layoutRevisionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutRevisionImpl.setModifiedDate(null);
		}
		else {
			layoutRevisionImpl.setModifiedDate(new Date(modifiedDate));
		}

		layoutRevisionImpl.setLayoutSetBranchId(layoutSetBranchId);
		layoutRevisionImpl.setLayoutBranchId(layoutBranchId);
		layoutRevisionImpl.setParentLayoutRevisionId(parentLayoutRevisionId);
		layoutRevisionImpl.setHead(head);
		layoutRevisionImpl.setMajor(major);
		layoutRevisionImpl.setPlid(plid);
		layoutRevisionImpl.setPrivateLayout(privateLayout);

		if (name == null) {
			layoutRevisionImpl.setName(StringPool.BLANK);
		}
		else {
			layoutRevisionImpl.setName(name);
		}

		if (title == null) {
			layoutRevisionImpl.setTitle(StringPool.BLANK);
		}
		else {
			layoutRevisionImpl.setTitle(title);
		}

		if (description == null) {
			layoutRevisionImpl.setDescription(StringPool.BLANK);
		}
		else {
			layoutRevisionImpl.setDescription(description);
		}

		if (keywords == null) {
			layoutRevisionImpl.setKeywords(StringPool.BLANK);
		}
		else {
			layoutRevisionImpl.setKeywords(keywords);
		}

		if (robots == null) {
			layoutRevisionImpl.setRobots(StringPool.BLANK);
		}
		else {
			layoutRevisionImpl.setRobots(robots);
		}

		if (typeSettings == null) {
			layoutRevisionImpl.setTypeSettings(StringPool.BLANK);
		}
		else {
			layoutRevisionImpl.setTypeSettings(typeSettings);
		}

		layoutRevisionImpl.setIconImageId(iconImageId);

		if (themeId == null) {
			layoutRevisionImpl.setThemeId(StringPool.BLANK);
		}
		else {
			layoutRevisionImpl.setThemeId(themeId);
		}

		if (colorSchemeId == null) {
			layoutRevisionImpl.setColorSchemeId(StringPool.BLANK);
		}
		else {
			layoutRevisionImpl.setColorSchemeId(colorSchemeId);
		}

		if (wapThemeId == null) {
			layoutRevisionImpl.setWapThemeId(StringPool.BLANK);
		}
		else {
			layoutRevisionImpl.setWapThemeId(wapThemeId);
		}

		if (wapColorSchemeId == null) {
			layoutRevisionImpl.setWapColorSchemeId(StringPool.BLANK);
		}
		else {
			layoutRevisionImpl.setWapColorSchemeId(wapColorSchemeId);
		}

		if (css == null) {
			layoutRevisionImpl.setCss(StringPool.BLANK);
		}
		else {
			layoutRevisionImpl.setCss(css);
		}

		layoutRevisionImpl.setStatus(status);
		layoutRevisionImpl.setStatusByUserId(statusByUserId);

		if (statusByUserName == null) {
			layoutRevisionImpl.setStatusByUserName(StringPool.BLANK);
		}
		else {
			layoutRevisionImpl.setStatusByUserName(statusByUserName);
		}

		if (statusDate == Long.MIN_VALUE) {
			layoutRevisionImpl.setStatusDate(null);
		}
		else {
			layoutRevisionImpl.setStatusDate(new Date(statusDate));
		}

		layoutRevisionImpl.setMvccVersion(mvccVersion);

		layoutRevisionImpl.resetOriginalValues();

		return layoutRevisionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		layoutRevisionId = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		layoutSetBranchId = objectInput.readLong();
		layoutBranchId = objectInput.readLong();
		parentLayoutRevisionId = objectInput.readLong();
		head = objectInput.readBoolean();
		major = objectInput.readBoolean();
		plid = objectInput.readLong();
		privateLayout = objectInput.readBoolean();
		name = objectInput.readUTF();
		title = objectInput.readUTF();
		description = objectInput.readUTF();
		keywords = objectInput.readUTF();
		robots = objectInput.readUTF();
		typeSettings = objectInput.readUTF();
		iconImageId = objectInput.readLong();
		themeId = objectInput.readUTF();
		colorSchemeId = objectInput.readUTF();
		wapThemeId = objectInput.readUTF();
		wapColorSchemeId = objectInput.readUTF();
		css = objectInput.readUTF();
		status = objectInput.readInt();
		statusByUserId = objectInput.readLong();
		statusByUserName = objectInput.readUTF();
		statusDate = objectInput.readLong();
		mvccVersion = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(layoutRevisionId);
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
		objectOutput.writeLong(layoutSetBranchId);
		objectOutput.writeLong(layoutBranchId);
		objectOutput.writeLong(parentLayoutRevisionId);
		objectOutput.writeBoolean(head);
		objectOutput.writeBoolean(major);
		objectOutput.writeLong(plid);
		objectOutput.writeBoolean(privateLayout);

		if (name == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(name);
		}

		if (title == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(title);
		}

		if (description == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(description);
		}

		if (keywords == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(keywords);
		}

		if (robots == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(robots);
		}

		if (typeSettings == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}

		objectOutput.writeLong(iconImageId);

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

		objectOutput.writeInt(status);
		objectOutput.writeLong(statusByUserId);

		if (statusByUserName == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(statusByUserName);
		}

		objectOutput.writeLong(statusDate);
		objectOutput.writeLong(mvccVersion);
	}

	public long layoutRevisionId;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long layoutSetBranchId;
	public long layoutBranchId;
	public long parentLayoutRevisionId;
	public boolean head;
	public boolean major;
	public long plid;
	public boolean privateLayout;
	public String name;
	public String title;
	public String description;
	public String keywords;
	public String robots;
	public String typeSettings;
	public long iconImageId;
	public String themeId;
	public String colorSchemeId;
	public String wapThemeId;
	public String wapColorSchemeId;
	public String css;
	public int status;
	public long statusByUserId;
	public String statusByUserName;
	public long statusDate;
	public long mvccVersion;
}