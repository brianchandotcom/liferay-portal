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

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.MVCCModel;

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
		StringBundler sb = new StringBundler(69);

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
		sb.append(", parentPlid=");
		sb.append(parentPlid);
		sb.append(", leftPlid=");
		sb.append(leftPlid);
		sb.append(", rightPlid=");
		sb.append(rightPlid);
		sb.append(", privateLayout=");
		sb.append(privateLayout);
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
		sb.append(", system=");
		sb.append(system);
		sb.append(", friendlyURL=");
		sb.append(friendlyURL);
		sb.append(", iconImageId=");
		sb.append(iconImageId);
		sb.append(", themeId=");
		sb.append(themeId);
		sb.append(", colorSchemeId=");
		sb.append(colorSchemeId);
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
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public Layout toEntityModel() {
		LayoutImpl layoutImpl = new LayoutImpl();

		layoutImpl.setMvccVersion(mvccVersion);

		if (uuid == null) {
			layoutImpl.setUuid("");
		}
		else {
			layoutImpl.setUuid(uuid);
		}

		layoutImpl.setPlid(plid);
		layoutImpl.setGroupId(groupId);
		layoutImpl.setCompanyId(companyId);
		layoutImpl.setUserId(userId);

		if (userName == null) {
			layoutImpl.setUserName("");
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

		layoutImpl.setParentPlid(parentPlid);
		layoutImpl.setLeftPlid(leftPlid);
		layoutImpl.setRightPlid(rightPlid);
		layoutImpl.setPrivateLayout(privateLayout);
		layoutImpl.setLayoutId(layoutId);
		layoutImpl.setParentLayoutId(parentLayoutId);

		if (name == null) {
			layoutImpl.setName("");
		}
		else {
			layoutImpl.setName(name);
		}

		if (title == null) {
			layoutImpl.setTitle("");
		}
		else {
			layoutImpl.setTitle(title);
		}

		if (description == null) {
			layoutImpl.setDescription("");
		}
		else {
			layoutImpl.setDescription(description);
		}

		if (keywords == null) {
			layoutImpl.setKeywords("");
		}
		else {
			layoutImpl.setKeywords(keywords);
		}

		if (robots == null) {
			layoutImpl.setRobots("");
		}
		else {
			layoutImpl.setRobots(robots);
		}

		if (type == null) {
			layoutImpl.setType("");
		}
		else {
			layoutImpl.setType(type);
		}

		if (typeSettings == null) {
			layoutImpl.setTypeSettings("");
		}
		else {
			layoutImpl.setTypeSettings(typeSettings);
		}

		layoutImpl.setHidden(hidden);
		layoutImpl.setSystem(system);

		if (friendlyURL == null) {
			layoutImpl.setFriendlyURL("");
		}
		else {
			layoutImpl.setFriendlyURL(friendlyURL);
		}

		layoutImpl.setIconImageId(iconImageId);

		if (themeId == null) {
			layoutImpl.setThemeId("");
		}
		else {
			layoutImpl.setThemeId(themeId);
		}

		if (colorSchemeId == null) {
			layoutImpl.setColorSchemeId("");
		}
		else {
			layoutImpl.setColorSchemeId(colorSchemeId);
		}

		if (css == null) {
			layoutImpl.setCss("");
		}
		else {
			layoutImpl.setCss(css);
		}

		layoutImpl.setPriority(priority);

		if (layoutPrototypeUuid == null) {
			layoutImpl.setLayoutPrototypeUuid("");
		}
		else {
			layoutImpl.setLayoutPrototypeUuid(layoutPrototypeUuid);
		}

		layoutImpl.setLayoutPrototypeLinkEnabled(layoutPrototypeLinkEnabled);

		if (sourcePrototypeLayoutUuid == null) {
			layoutImpl.setSourcePrototypeLayoutUuid("");
		}
		else {
			layoutImpl.setSourcePrototypeLayoutUuid(sourcePrototypeLayoutUuid);
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			layoutImpl.setLastPublishDate(null);
		}
		else {
			layoutImpl.setLastPublishDate(new Date(lastPublishDate));
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

		parentPlid = objectInput.readLong();

		leftPlid = objectInput.readLong();

		rightPlid = objectInput.readLong();

		privateLayout = objectInput.readBoolean();

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

		system = objectInput.readBoolean();
		friendlyURL = objectInput.readUTF();

		iconImageId = objectInput.readLong();
		themeId = objectInput.readUTF();
		colorSchemeId = objectInput.readUTF();
		css = objectInput.readUTF();

		priority = objectInput.readInt();
		layoutPrototypeUuid = objectInput.readUTF();

		layoutPrototypeLinkEnabled = objectInput.readBoolean();
		sourcePrototypeLayoutUuid = objectInput.readUTF();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput)
		throws IOException {
		objectOutput.writeLong(mvccVersion);

		if (uuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(uuid);
		}

		objectOutput.writeLong(plid);

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

		objectOutput.writeLong(parentPlid);

		objectOutput.writeLong(leftPlid);

		objectOutput.writeLong(rightPlid);

		objectOutput.writeBoolean(privateLayout);

		objectOutput.writeLong(layoutId);

		objectOutput.writeLong(parentLayoutId);

		if (name == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(name);
		}

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

		if (keywords == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(keywords);
		}

		if (robots == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(robots);
		}

		if (type == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(type);
		}

		if (typeSettings == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(typeSettings);
		}

		objectOutput.writeBoolean(hidden);

		objectOutput.writeBoolean(system);

		if (friendlyURL == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(friendlyURL);
		}

		objectOutput.writeLong(iconImageId);

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

		objectOutput.writeInt(priority);

		if (layoutPrototypeUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(layoutPrototypeUuid);
		}

		objectOutput.writeBoolean(layoutPrototypeLinkEnabled);

		if (sourcePrototypeLayoutUuid == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(sourcePrototypeLayoutUuid);
		}

		objectOutput.writeLong(lastPublishDate);
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
	public long parentPlid;
	public long leftPlid;
	public long rightPlid;
	public boolean privateLayout;
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
	public boolean system;
	public String friendlyURL;
	public long iconImageId;
	public String themeId;
	public String colorSchemeId;
	public String css;
	public int priority;
	public String layoutPrototypeUuid;
	public boolean layoutPrototypeLinkEnabled;
	public String sourcePrototypeLayoutUuid;
	public long lastPublishDate;
}