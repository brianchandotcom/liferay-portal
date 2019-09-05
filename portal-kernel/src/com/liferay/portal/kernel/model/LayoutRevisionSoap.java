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
 * This class is used by SOAP remote services, specifically {@link com.liferay.portal.service.http.LayoutRevisionServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutRevisionSoap implements Serializable {

	public static LayoutRevisionSoap toSoapModel(LayoutRevision model) {
		LayoutRevisionSoap soapModel = new LayoutRevisionSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setLayoutRevisionId(model.getLayoutRevisionId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setLayoutSetBranchId(model.getLayoutSetBranchId());
		soapModel.setLayoutBranchId(model.getLayoutBranchId());
		soapModel.setParentLayoutRevisionId(model.getParentLayoutRevisionId());
		soapModel.setHead(model.isHead());
		soapModel.setMajor(model.isMajor());
		soapModel.setPlid(model.getPlid());
		soapModel.setPrivateLayout(model.isPrivateLayout());
		soapModel.setName(model.getName());
		soapModel.setTitle(model.getTitle());
		soapModel.setDescription(model.getDescription());
		soapModel.setKeywords(model.getKeywords());
		soapModel.setRobots(model.getRobots());
		soapModel.setTypeSettings(model.getTypeSettings());
		soapModel.setIconImageId(model.getIconImageId());
		soapModel.setThemeId(model.getThemeId());
		soapModel.setColorSchemeId(model.getColorSchemeId());
		soapModel.setCss(model.getCss());
		soapModel.setStatus(model.getStatus());
		soapModel.setStatusByUserId(model.getStatusByUserId());
		soapModel.setStatusByUserName(model.getStatusByUserName());
		soapModel.setStatusDate(model.getStatusDate());

		return soapModel;
	}

	public static LayoutRevisionSoap[] toSoapModels(LayoutRevision[] models) {
		LayoutRevisionSoap[] soapModels = new LayoutRevisionSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LayoutRevisionSoap[][] toSoapModels(
		LayoutRevision[][] models) {

		LayoutRevisionSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new LayoutRevisionSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LayoutRevisionSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LayoutRevisionSoap[] toSoapModels(
		List<LayoutRevision> models) {

		List<LayoutRevisionSoap> soapModels = new ArrayList<LayoutRevisionSoap>(
			models.size());

		for (LayoutRevision model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LayoutRevisionSoap[soapModels.size()]);
	}

	public LayoutRevisionSoap() {
	}

	public long getPrimaryKey() {
		return _layoutRevisionId;
	}

	public void setPrimaryKey(long pk) {
		setLayoutRevisionId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public long getLayoutRevisionId() {
		return _layoutRevisionId;
	}

	public void setLayoutRevisionId(long layoutRevisionId) {
		_layoutRevisionId = layoutRevisionId;
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

	public long getLayoutSetBranchId() {
		return _layoutSetBranchId;
	}

	public void setLayoutSetBranchId(long layoutSetBranchId) {
		_layoutSetBranchId = layoutSetBranchId;
	}

	public long getLayoutBranchId() {
		return _layoutBranchId;
	}

	public void setLayoutBranchId(long layoutBranchId) {
		_layoutBranchId = layoutBranchId;
	}

	public long getParentLayoutRevisionId() {
		return _parentLayoutRevisionId;
	}

	public void setParentLayoutRevisionId(long parentLayoutRevisionId) {
		_parentLayoutRevisionId = parentLayoutRevisionId;
	}

	public boolean getHead() {
		return _head;
	}

	public boolean isHead() {
		return _head;
	}

	public void setHead(boolean head) {
		_head = head;
	}

	public boolean getMajor() {
		return _major;
	}

	public boolean isMajor() {
		return _major;
	}

	public void setMajor(boolean major) {
		_major = major;
	}

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		_plid = plid;
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

	public String getTypeSettings() {
		return _typeSettings;
	}

	public void setTypeSettings(String typeSettings) {
		_typeSettings = typeSettings;
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

	public String getCss() {
		return _css;
	}

	public void setCss(String css) {
		_css = css;
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
	private long _layoutRevisionId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _layoutSetBranchId;
	private long _layoutBranchId;
	private long _parentLayoutRevisionId;
	private boolean _head;
	private boolean _major;
	private long _plid;
	private boolean _privateLayout;
	private String _name;
	private String _title;
	private String _description;
	private String _keywords;
	private String _robots;
	private String _typeSettings;
	private long _iconImageId;
	private String _themeId;
	private String _colorSchemeId;
	private String _css;
	private int _status;
	private long _statusByUserId;
	private String _statusByUserName;
	private Date _statusDate;

}