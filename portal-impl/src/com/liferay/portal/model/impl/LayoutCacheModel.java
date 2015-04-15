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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.util.HashUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.MVCCModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

/**
 * The cache model class for representing Layout in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Layout
 * @generated
 */
@ProviderType
public class LayoutCacheModel implements CacheModel<Layout>, Externalizable,
	MVCCModel {
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutCacheModel)) {
			return false;
		}

		LayoutCacheModel layoutCacheModel = (LayoutCacheModel)obj;

		if ((plid == layoutCacheModel.plid) &&
				(mvccVersion == layoutCacheModel.mvccVersion)) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		int hashCode = HashUtil.hash(0, plid);

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
		StringBundler sb = new StringBundler(61);

		sb.append("{mvccVersion=");
		sb.append(mvccVersion);
		sb.append(", uuid=");
		sb.append(uuid);
		sb.append(", plid=");
		sb.append(plid);
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
		sb.append(", layoutId=");
		sb.append(layoutId);
		sb.append(", parentLayoutId=");
		sb.append(parentLayoutId);
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
		sb.append(", type=");
		sb.append(type);
		sb.append(", typeSettings=");
		sb.append(typeSettings);
		sb.append(", hidden=");
		sb.append(hidden);
		sb.append(", friendlyURL=");
		sb.append(friendlyURL);
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
		sb.append(", priority=");
		sb.append(priority);
		sb.append(", layoutPrototypeUuid=");
		sb.append(layoutPrototypeUuid);
		sb.append(", layoutPrototypeLinkEnabled=");
		sb.append(layoutPrototypeLinkEnabled);
		sb.append(", sourcePrototypeLayoutUuid=");
		sb.append(sourcePrototypeLayoutUuid);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Layout toEntityModel() {
		LayoutImpl layoutImpl = new LayoutImpl();

		layoutImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			layoutImpl.setUuid(StringPool.BLANK);
		}
		else {
			layoutImpl.setUuid(uuid);
		}

		layoutImpl.setPlid(plid);
		layoutImpl.setGroupId(groupId);
		layoutImpl.setCompanyId(companyId);
		layoutImpl.setUserId(userId);

		if (userName == null) {
			layoutImpl.setUserName(StringPool.BLANK);
		}
		else {
			layoutImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutImpl.setCreateDate(null);
		}
		else {
			layoutImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutImpl.setModifiedDate(null);
		}
		else {
			layoutImpl.setModifiedDate(new Date(modifiedDate));
		}

		layoutImpl.setLayoutId(layoutId);
		layoutImpl.setParentLayoutId(parentLayoutId);

		if (name == null) {
			layoutImpl.setName(StringPool.BLANK);
		}
		else {
			layoutImpl.setName(name);
		}

		if (title == null) {
			layoutImpl.setTitle(StringPool.BLANK);
		}
		else {
			layoutImpl.setTitle(title);
		}

		if (description == null) {
			layoutImpl.setDescription(StringPool.BLANK);
		}
		else {
			layoutImpl.setDescription(description);
		}

		if (keywords == null) {
			layoutImpl.setKeywords(StringPool.BLANK);
		}
		else {
			layoutImpl.setKeywords(keywords);
		}

		if (robots == null) {
			layoutImpl.setRobots(StringPool.BLANK);
		}
		else {
			layoutImpl.setRobots(robots);
		}

		if (type == null) {
			layoutImpl.setType(StringPool.BLANK);
		}
		else {
			layoutImpl.setType(type);
		}

		if (typeSettings == null) {
			layoutImpl.setTypeSettings(StringPool.BLANK);
		}
		else {
			layoutImpl.setTypeSettings(typeSettings);
		}

		layoutImpl.setHidden(hidden);

		if (friendlyURL == null) {
			layoutImpl.setFriendlyURL(StringPool.BLANK);
		}
		else {
			layoutImpl.setFriendlyURL(friendlyURL);
		}

		layoutImpl.setIconImageId(iconImageId);

		if (themeId == null) {
			layoutImpl.setThemeId(StringPool.BLANK);
		}
		else {
			layoutImpl.setThemeId(themeId);
		}

		if (colorSchemeId == null) {
			layoutImpl.setColorSchemeId(StringPool.BLANK);
		}
		else {
			layoutImpl.setColorSchemeId(colorSchemeId);
		}

		if (wapThemeId == null) {
			layoutImpl.setWapThemeId(StringPool.BLANK);
		}
		else {
			layoutImpl.setWapThemeId(wapThemeId);
		}

		if (wapColorSchemeId == null) {
			layoutImpl.setWapColorSchemeId(StringPool.BLANK);
		}
		else {
			layoutImpl.setWapColorSchemeId(wapColorSchemeId);
		}

		if (css == null) {
			layoutImpl.setCss(StringPool.BLANK);
		}
		else {
			layoutImpl.setCss(css);
		}

		layoutImpl.setPriority(priority);

		if (layoutPrototypeUuid == null) {
			layoutImpl.setLayoutPrototypeUuid(StringPool.BLANK);
		}
		else {
			layoutImpl.setLayoutPrototypeUuid(layoutPrototypeUuid);
		}

		layoutImpl.setLayoutPrototypeLinkEnabled(layoutPrototypeLinkEnabled);

		if (sourcePrototypeLayoutUuid == null) {
			layoutImpl.setSourcePrototypeLayoutUuid(StringPool.BLANK);
		}
		else {
			layoutImpl.setSourcePrototypeLayoutUuid(sourcePrototypeLayoutUuid);
		}

		layoutImpl.resetOriginalValues();

		return layoutImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		mvccVersion = objectInput.readLong();
		uuid = objectInput.readUTF();
		plid = objectInput.readLong();
		groupId = objectInput.readLong();
		companyId = objectInput.readLong();
		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();
		layoutId = objectInput.readLong();
		parentLayoutId = objectInput.readLong();
		name = objectInput.readUTF();
		title = objectInput.readUTF();
		description = objectInput.readUTF();
		keywords = objectInput.readUTF();
		robots = objectInput.readUTF();
		type = objectInput.readUTF();
		typeSettings = objectInput.readUTF();
		hidden = objectInput.readBoolean();
		friendlyURL = objectInput.readUTF();
		iconImageId = objectInput.readLong();
		themeId = objectInput.readUTF();
		colorSchemeId = objectInput.readUTF();
		wapThemeId = objectInput.readUTF();
		wapColorSchemeId = objectInput.readUTF();
		css = objectInput.readUTF();
		priority = objectInput.readInt();
		layoutPrototypeUuid = objectInput.readUTF();
		layoutPrototypeLinkEnabled = objectInput.readBoolean();
		sourcePrototypeLayoutUuid = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(plid);
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
		objectOutput.writeLong(layoutId);
		objectOutput.writeLong(parentLayoutId);

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

		if (type == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (typeSettings == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}

		objectOutput.writeBoolean(hidden);

		if (friendlyURL == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(friendlyURL);
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

		objectOutput.writeInt(priority);

		if (layoutPrototypeUuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(layoutPrototypeUuid);
		}

		objectOutput.writeBoolean(layoutPrototypeLinkEnabled);

		if (sourcePrototypeLayoutUuid == null) {
			objectOutput.writeUTF(StringPool.BLANK);
		}
		else {
			objectOutput.writeUTF(sourcePrototypeLayoutUuid);
		}
	}

	public long mvccVersion;
	public String uuid;
	public long plid;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long layoutId;
	public long parentLayoutId;
	public String name;
	public String title;
	public String description;
	public String keywords;
	public String robots;
	public String type;
	public String typeSettings;
	public boolean hidden;
	public String friendlyURL;
	public long iconImageId;
	public String themeId;
	public String colorSchemeId;
	public String wapThemeId;
	public String wapColorSchemeId;
	public String css;
	public int priority;
	public String layoutPrototypeUuid;
	public boolean layoutPrototypeLinkEnabled;
	public String sourcePrototypeLayoutUuid;
}