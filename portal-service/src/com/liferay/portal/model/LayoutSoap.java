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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.LayoutServiceSoap}.
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portal.service.http.LayoutServiceSoap
 * @generated
 */
public class LayoutSoap implements Serializable {
	public static LayoutSoap toSoapModel(Layout model) {
		LayoutSoap soapModel = new LayoutSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setPlid(model.getPlid());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setPrivateLayout(model.getPrivateLayout());
		soapModel.setLayoutId(model.getLayoutId());
		soapModel.setParentLayoutId(model.getParentLayoutId());
		soapModel.setName(model.getName());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setKeywords(model.getKeywords());
		soapModel.setRobots(model.getRobots());
		soapModel.setType(model.getType());
		soapModel.setTypeSettings(model.getTypeSettings());
		soapModel.setHidden(model.getHidden());
		soapModel.setFriendlyURL(model.getFriendlyURL());
		soapModel.setIconImage(model.getIconImage());
		soapModel.setIconImageId(model.getIconImageId());
		soapModel.setThemeId(model.getThemeId());
		soapModel.setColorSchemeId(model.getColorSchemeId());
		soapModel.setWapThemeId(model.getWapThemeId());
		soapModel.setWapColorSchemeId(model.getWapColorSchemeId());
		soapModel.setCss(model.getCss());
		soapModel.setPriority(model.getPriority());
		soapModel.setLayoutPrototypeUuid(model.getLayoutPrototypeUuid());
		soapModel.setLayoutPrototypeLinkEnabled(model.getLayoutPrototypeLinkEnabled());
		soapModel.setSourcePrototypeLayoutUuid(model.getSourcePrototypeLayoutUuid());

		return soapModel;
	}

	public static LayoutSoap[] toSoapModels(Layout[] models) {
		LayoutSoap[] soapModels = new LayoutSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LayoutSoap[][] toSoapModels(Layout[][] models) {
		LayoutSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LayoutSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LayoutSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LayoutSoap[] toSoapModels(List<Layout> models) {
		List<LayoutSoap> soapModels = new ArrayList<LayoutSoap>(models.size());

		for (Layout model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LayoutSoap[soapModels.size()]);
	}

	public LayoutSoap() {
	}

	public long getPrimaryKey() {
		return _plid;
	}

	public void setPrimaryKey(long pk) {
		setPlid(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return _userName;
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public boolean getPrivateLayout() {
		return _privateLayout;
	}

	public boolean isPrivateLayout() {
		return _privateLayout;
	}

	public void setPrivateLayout(boolean privateLayout) {
		_privateLayout = privateLayout;
	}

	public long getLayoutId() {
		return _layoutId;
	}

	public void setLayoutId(long layoutId) {
		_layoutId = layoutId;
	}

	public long getParentLayoutId() {
		return _parentLayoutId;
	}

	public void setParentLayoutId(long parentLayoutId) {
		_parentLayoutId = parentLayoutId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getTitle() {
		return _title;
	}

	public void setTitle(String title) {
		_title = title;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getKeywords() {
		return _keywords;
	}

	public void setKeywords(String keywords) {
		_keywords = keywords;
	}

	public String getRobots() {
		return _robots;
	}

	public void setRobots(String robots) {
		_robots = robots;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
	}

	public boolean getHidden() {
		return _hidden;
	}

	public boolean isHidden() {
		return _hidden;
	}

	public void setHidden(boolean hidden) {
		_hidden = hidden;
	}

	public String getFriendlyURL() {
		return _friendlyURL;
	}

	public void setFriendlyURL(String friendlyURL) {
		_friendlyURL = friendlyURL;
	}

	public boolean getIconImage() {
		return _iconImage;
	}

	public boolean isIconImage() {
		return _iconImage;
	}

	public void setIconImage(boolean iconImage) {
		_iconImage = iconImage;
	}

	public long getIconImageId() {
		return _iconImageId;
	}

	public void setIconImageId(long iconImageId) {
		_iconImageId = iconImageId;
	}

	public String getThemeId() {
		return _themeId;
	}

	public void setThemeId(String themeId) {
		_themeId = themeId;
	}

	public String getColorSchemeId() {
		return _colorSchemeId;
	}

	public void setColorSchemeId(String colorSchemeId) {
		_colorSchemeId = colorSchemeId;
	}

	public String getWapThemeId() {
		return _wapThemeId;
	}

	public void setWapThemeId(String wapThemeId) {
		_wapThemeId = wapThemeId;
	}

	public String getWapColorSchemeId() {
		return _wapColorSchemeId;
	}

	public void setWapColorSchemeId(String wapColorSchemeId) {
		_wapColorSchemeId = wapColorSchemeId;
	}

	public String getCss() {
		return _css;
	}

	public void setCss(String css) {
		_css = css;
	}

	public int getPriority() {
		return _priority;
	}

	public void setPriority(int priority) {
		_priority = priority;
	}

	public String getLayoutPrototypeUuid() {
		return _layoutPrototypeUuid;
	}

	public void setLayoutPrototypeUuid(String layoutPrototypeUuid) {
		_layoutPrototypeUuid = layoutPrototypeUuid;
	}

	public boolean getLayoutPrototypeLinkEnabled() {
		return _layoutPrototypeLinkEnabled;
	}

	public boolean isLayoutPrototypeLinkEnabled() {
		return _layoutPrototypeLinkEnabled;
	}

	public void setLayoutPrototypeLinkEnabled(
		boolean layoutPrototypeLinkEnabled) {
		_layoutPrototypeLinkEnabled = layoutPrototypeLinkEnabled;
	}

	public String getSourcePrototypeLayoutUuid() {
		return _sourcePrototypeLayoutUuid;
	}

	public void setSourcePrototypeLayoutUuid(String sourcePrototypeLayoutUuid) {
		_sourcePrototypeLayoutUuid = sourcePrototypeLayoutUuid;
	}

	private String _uuid;
	private long _plid;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _privateLayout;
	private long _layoutId;
	private long _parentLayoutId;
	private String _name;
	private String _title;
	private String _description;
	private String _keywords;
	private String _robots;
	private String _type;
	private String _typeSettings;
	private boolean _hidden;
	private String _friendlyURL;
	private boolean _iconImage;
	private long _iconImageId;
	private String _themeId;
	private String _colorSchemeId;
	private String _wapThemeId;
	private String _wapColorSchemeId;
	private String _css;
	private int _priority;
	private String _layoutPrototypeUuid;
	private boolean _layoutPrototypeLinkEnabled;
	private String _sourcePrototypeLayoutUuid;
}