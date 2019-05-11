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
import com.liferay.portal.kernel.model.LayoutVersion;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import java.util.Date;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The cache model class for representing LayoutVersion in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class LayoutVersionCacheModel
	implements CacheModel<LayoutVersion>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof LayoutVersionCacheModel)) {
			return false;
		}

		LayoutVersionCacheModel layoutVersionCacheModel =
			(LayoutVersionCacheModel)obj;

		if (layoutVersionId == layoutVersionCacheModel.layoutVersionId) {
			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, layoutVersionId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(73);

		sb.append("{layoutVersionId=");
		sb.append(layoutVersionId);
		sb.append(", version=");
		sb.append(version);
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
		sb.append(", privateLayout=");
		sb.append(privateLayout);
		sb.append(", layoutId=");
		sb.append(layoutId);
		sb.append(", parentLayoutId=");
		sb.append(parentLayoutId);
		sb.append(", classNameId=");
		sb.append(classNameId);
		sb.append(", classPK=");
		sb.append(classPK);
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
		sb.append(", publishDate=");
		sb.append(publishDate);
		sb.append(", lastPublishDate=");
		sb.append(lastPublishDate);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public LayoutVersion toEntityModel() {
		LayoutVersionImpl layoutVersionImpl = new LayoutVersionImpl();

		layoutVersionImpl.setLayoutVersionId(layoutVersionId);
		layoutVersionImpl.setVersion(version);

		if (uuid == null) {
			layoutVersionImpl.setUuid("");
		}
		else {
			layoutVersionImpl.setUuid(uuid);
		}

		layoutVersionImpl.setPlid(plid);
		layoutVersionImpl.setGroupId(groupId);
		layoutVersionImpl.setCompanyId(companyId);
		layoutVersionImpl.setUserId(userId);

		if (userName == null) {
			layoutVersionImpl.setUserName("");
		}
		else {
			layoutVersionImpl.setUserName(userName);
		}

		if (createDate == Long.MIN_VALUE) {
			layoutVersionImpl.setCreateDate(null);
		}
		else {
			layoutVersionImpl.setCreateDate(new Date(createDate));
		}

		if (modifiedDate == Long.MIN_VALUE) {
			layoutVersionImpl.setModifiedDate(null);
		}
		else {
			layoutVersionImpl.setModifiedDate(new Date(modifiedDate));
		}

		layoutVersionImpl.setParentPlid(parentPlid);
		layoutVersionImpl.setPrivateLayout(privateLayout);
		layoutVersionImpl.setLayoutId(layoutId);
		layoutVersionImpl.setParentLayoutId(parentLayoutId);
		layoutVersionImpl.setClassNameId(classNameId);
		layoutVersionImpl.setClassPK(classPK);

		if (name == null) {
			layoutVersionImpl.setName("");
		}
		else {
			layoutVersionImpl.setName(name);
		}

		if (title == null) {
			layoutVersionImpl.setTitle("");
		}
		else {
			layoutVersionImpl.setTitle(title);
		}

		if (description == null) {
			layoutVersionImpl.setDescription("");
		}
		else {
			layoutVersionImpl.setDescription(description);
		}

		if (keywords == null) {
			layoutVersionImpl.setKeywords("");
		}
		else {
			layoutVersionImpl.setKeywords(keywords);
		}

		if (robots == null) {
			layoutVersionImpl.setRobots("");
		}
		else {
			layoutVersionImpl.setRobots(robots);
		}

		if (type == null) {
			layoutVersionImpl.setType("");
		}
		else {
			layoutVersionImpl.setType(type);
		}

		if (typeSettings == null) {
			layoutVersionImpl.setTypeSettings("");
		}
		else {
			layoutVersionImpl.setTypeSettings(typeSettings);
		}

		layoutVersionImpl.setHidden(hidden);
		layoutVersionImpl.setSystem(system);

		if (friendlyURL == null) {
			layoutVersionImpl.setFriendlyURL("");
		}
		else {
			layoutVersionImpl.setFriendlyURL(friendlyURL);
		}

		layoutVersionImpl.setIconImageId(iconImageId);

		if (themeId == null) {
			layoutVersionImpl.setThemeId("");
		}
		else {
			layoutVersionImpl.setThemeId(themeId);
		}

		if (colorSchemeId == null) {
			layoutVersionImpl.setColorSchemeId("");
		}
		else {
			layoutVersionImpl.setColorSchemeId(colorSchemeId);
		}

		if (css == null) {
			layoutVersionImpl.setCss("");
		}
		else {
			layoutVersionImpl.setCss(css);
		}

		layoutVersionImpl.setPriority(priority);

		if (layoutPrototypeUuid == null) {
			layoutVersionImpl.setLayoutPrototypeUuid("");
		}
		else {
			layoutVersionImpl.setLayoutPrototypeUuid(layoutPrototypeUuid);
		}

		layoutVersionImpl.setLayoutPrototypeLinkEnabled(
			layoutPrototypeLinkEnabled);

		if (sourcePrototypeLayoutUuid == null) {
			layoutVersionImpl.setSourcePrototypeLayoutUuid("");
		}
		else {
			layoutVersionImpl.setSourcePrototypeLayoutUuid(
				sourcePrototypeLayoutUuid);
		}

		if (publishDate == Long.MIN_VALUE) {
			layoutVersionImpl.setPublishDate(null);
		}
		else {
			layoutVersionImpl.setPublishDate(new Date(publishDate));
		}

		if (lastPublishDate == Long.MIN_VALUE) {
			layoutVersionImpl.setLastPublishDate(null);
		}
		else {
			layoutVersionImpl.setLastPublishDate(new Date(lastPublishDate));
		}

		layoutVersionImpl.resetOriginalValues();

		return layoutVersionImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		layoutVersionId = objectInput.readLong();

		version = objectInput.readInt();
		uuid = objectInput.readUTF();

		plid = objectInput.readLong();

		groupId = objectInput.readLong();

		companyId = objectInput.readLong();

		userId = objectInput.readLong();
		userName = objectInput.readUTF();
		createDate = objectInput.readLong();
		modifiedDate = objectInput.readLong();

		parentPlid = objectInput.readLong();

		privateLayout = objectInput.readBoolean();

		layoutId = objectInput.readLong();

		parentLayoutId = objectInput.readLong();

		classNameId = objectInput.readLong();

		classPK = objectInput.readLong();
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
		publishDate = objectInput.readLong();
		lastPublishDate = objectInput.readLong();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(layoutVersionId);

		objectOutput.writeInt(version);

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

		objectOutput.writeBoolean(privateLayout);

		objectOutput.writeLong(layoutId);

		objectOutput.writeLong(parentLayoutId);

		objectOutput.writeLong(classNameId);

		objectOutput.writeLong(classPK);

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

		objectOutput.writeLong(publishDate);
		objectOutput.writeLong(lastPublishDate);
	}

	public long layoutVersionId;
	public int version;
	public String uuid;
	public long plid;
	public long groupId;
	public long companyId;
	public long userId;
	public String userName;
	public long createDate;
	public long modifiedDate;
	public long parentPlid;
	public boolean privateLayout;
	public long layoutId;
	public long parentLayoutId;
	public long classNameId;
	public long classPK;
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
	public long publishDate;
	public long lastPublishDate;

}