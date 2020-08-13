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

package com.liferay.portal.kernel.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.LayoutServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class LayoutSoap implements Serializable {

	public static LayoutSoap toSoapModel(Layout model) {
		LayoutSoap soapModel = new LayoutSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setCtCollectionId(model.getCtCollectionId());
		soapModel.setUuid(model.getUuid());
		soapModel.setPlid(model.getPlid());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setParentPlid(model.getParentPlid());
		soapModel.setPrivateLayout(model.isPrivateLayout());
		soapModel.setLayoutId(model.getLayoutId());
		soapModel.setParentLayoutId(model.getParentLayoutId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setName(model.getName());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setKeywords(model.getKeywords());
		soapModel.setRobots(model.getRobots());
		soapModel.setType(model.getType());
		soapModel.setTypeSettings(model.getTypeSettings());
		soapModel.setHidden(model.isHidden());
		soapModel.setSystem(model.isSystem());
		soapModel.setFriendlyURL(model.getFriendlyURL());
		soapModel.setIconImageId(model.getIconImageId());
		soapModel.setThemeId(model.getThemeId());
		soapModel.setColorSchemeId(model.getColorSchemeId());
		soapModel.setStyleBookEntryId(model.getStyleBookEntryId());
		soapModel.setCss(model.getCss());
		soapModel.setPriority(model.getPriority());
		soapModel.setMasterLayoutPlid(model.getMasterLayoutPlid());
		soapModel.setLayoutPrototypeUuid(model.getLayoutPrototypeUuid());
		soapModel.setLayoutPrototypeLinkEnabled(
			model.isLayoutPrototypeLinkEnabled());
		soapModel.setSourcePrototypeLayoutUuid(
			model.getSourcePrototypeLayoutUuid());
		soapModel.setPublishDate(model.getPublishDate());
		soapModel.setLastPublishDate(model.getLastPublishDate());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());

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

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getCtCollectionId() {
		return _ctCollectionId;
	}

	public void setCtCollectionId(long ctCollectionId) {
		_ctCollectionId = ctCollectionId;
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

	public long getParentPlid() {
		return _parentPlid;
	}

	public void setParentPlid(long parentPlid) {
		_parentPlid = parentPlid;
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

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
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

	public boolean getSystem() {
		return _system;
	}

	public boolean isSystem() {
		return _system;
	}

	public void setSystem(boolean system) {
		_system = system;
	}

	public String getFriendlyURL() {
		return _friendlyURL;
	}

	public void setFriendlyURL(String friendlyURL) {
		_friendlyURL = friendlyURL;
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

	public long getStyleBookEntryId() {
		return _styleBookEntryId;
	}

	public void setStyleBookEntryId(long styleBookEntryId) {
		_styleBookEntryId = styleBookEntryId;
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

	public long getMasterLayoutPlid() {
		return _masterLayoutPlid;
	}

	public void setMasterLayoutPlid(long masterLayoutPlid) {
		_masterLayoutPlid = masterLayoutPlid;
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

	public Date getPublishDate() {
		return _publishDate;
	}

	public void setPublishDate(Date publishDate) {
		_publishDate = publishDate;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	public int getStatus() {
		return _status;
	}

	public void setStatus(int status) {
		_status = status;
	}

	public long getStatusByUserId() {
		return _statusByUserId;
	}

	public void setStatusByUserId(long statusByUserId) {
		_statusByUserId = statusByUserId;
	}

	public String getStatusByUserName() {
		return _statusByUserName;
	}

	public void setStatusByUserName(String statusByUserName) {
		_statusByUserName = statusByUserName;
	}

	public Date getStatusDate() {
		return _statusDate;
	}

	public void setStatusDate(Date statusDate) {
		_statusDate = statusDate;
	}

	private long _mvccVersion;
	private long _ctCollectionId;
	private String _uuid;
	private long _plid;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _parentPlid;
	private boolean _privateLayout;
	private long _layoutId;
	private long _parentLayoutId;
	private long _classNameId;
	private long _classPK;
	private String _name;
	private String _title;
	private String _description;
	private String _keywords;
	private String _robots;
	private String _type;
	private String _typeSettings;
	private boolean _hidden;
	private boolean _system;
	private String _friendlyURL;
	private long _iconImageId;
	private String _themeId;
	private String _colorSchemeId;
	private long _styleBookEntryId;
	private String _css;
	private int _priority;
	private long _masterLayoutPlid;
	private String _layoutPrototypeUuid;
	private boolean _layoutPrototypeLinkEnabled;
	private String _sourcePrototypeLayoutUuid;
	private Date _publishDate;
	private Date _lastPublishDate;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;

}