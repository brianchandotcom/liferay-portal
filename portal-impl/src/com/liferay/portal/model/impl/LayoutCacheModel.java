/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.Layout;

import java.io.Serializable;

import java.util.Date;

/**
 * The cache model class for representing Layout in entity cache.
 *
 * @author Brian Wing Shun Chan
 * @see Layout
 * @generated
 */
public class LayoutCacheModel implements CacheModel<Layout>, Serializable {
	@Override
	public String toString() {
		StringBundler sb = new StringBundler(59);

		sb.append("{uuid=");
		sb.append(uuid);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", groupId=");
		sb.append(groupId);
		sb.append(", companyId=");
		sb.append(companyId);
		sb.append(", createDate=");
		sb.append(createDate);
		sb.append(", modifiedDate=");
		sb.append(modifiedDate);
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
		sb.append(", friendlyURL=");
		sb.append(friendlyURL);
		sb.append(", iconImage=");
		sb.append(iconImage);
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
		sb.append(", templateLayoutUuid=");
		sb.append(templateLayoutUuid);
		sb.append("}");

		return sb.toString();
	}

	public Layout toEntityModel() {
		LayoutImpl layoutImpl = new LayoutImpl();

		if (uuid == null) {
			layoutImpl.setUuid(StringPool.BLANK);
		}
		else {
			layoutImpl.setUuid(uuid);
		}

		layoutImpl.setPlid(plid);
		layoutImpl.setGroupId(groupId);
		layoutImpl.setCompanyId(companyId);

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

		layoutImpl.setPrivateLayout(privateLayout);
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

		layoutImpl.setIconImage(iconImage);
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

		if (templateLayoutUuid == null) {
			layoutImpl.setTemplateLayoutUuid(StringPool.BLANK);
		}
		else {
			layoutImpl.setTemplateLayoutUuid(templateLayoutUuid);
		}

		layoutImpl.resetOriginalValues();

		return layoutImpl;
	}

	public String uuid;
	public long plid;
	public long groupId;
	public long companyId;
	public long createDate;
	public long modifiedDate;
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
	public String friendlyURL;
	public boolean iconImage;
	public long iconImageId;
	public String themeId;
	public String colorSchemeId;
	public String wapThemeId;
	public String wapColorSchemeId;
	public String css;
	public int priority;
	public String layoutPrototypeUuid;
	public boolean layoutPrototypeLinkEnabled;
	public String templateLayoutUuid;
}