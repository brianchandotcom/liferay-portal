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

package com.liferay.fragment.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.fragment.service.http.FragmentEntryLinkServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @deprecated As of Athanasius (7.3.x), with no direct replacement
 * @generated
 */
@Deprecated
public class FragmentEntryLinkSoap implements Serializable {

	public static FragmentEntryLinkSoap toSoapModel(FragmentEntryLink model) {
		FragmentEntryLinkSoap soapModel = new FragmentEntryLinkSoap();

		soapModel.setMvccVersion(model.getMvccVersion());
		soapModel.setUuid(model.getUuid());
		soapModel.setFragmentEntryLinkId(model.getFragmentEntryLinkId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setOriginalFragmentEntryLinkId(
			model.getOriginalFragmentEntryLinkId());
		soapModel.setFragmentEntryId(model.getFragmentEntryId());
		soapModel.setSegmentsExperienceId(model.getSegmentsExperienceId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setPlid(model.getPlid());
		soapModel.setCss(model.getCss());
		soapModel.setHtml(model.getHtml());
		soapModel.setJs(model.getJs());
		soapModel.setConfiguration(model.getConfiguration());
		soapModel.setEditableValues(model.getEditableValues());
		soapModel.setNamespace(model.getNamespace());
		soapModel.setPosition(model.getPosition());
		soapModel.setRendererKey(model.getRendererKey());
		soapModel.setLastPropagationDate(model.getLastPropagationDate());
		soapModel.setLastPublishDate(model.getLastPublishDate());

		return soapModel;
	}

	public static FragmentEntryLinkSoap[] toSoapModels(
		FragmentEntryLink[] models) {

		FragmentEntryLinkSoap[] soapModels =
			new FragmentEntryLinkSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FragmentEntryLinkSoap[][] toSoapModels(
		FragmentEntryLink[][] models) {

		FragmentEntryLinkSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new FragmentEntryLinkSoap[models.length][models[0].length];
		}
		else {
			soapModels = new FragmentEntryLinkSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FragmentEntryLinkSoap[] toSoapModels(
		List<FragmentEntryLink> models) {

		List<FragmentEntryLinkSoap> soapModels =
			new ArrayList<FragmentEntryLinkSoap>(models.size());

		for (FragmentEntryLink model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FragmentEntryLinkSoap[soapModels.size()]);
	}

	public FragmentEntryLinkSoap() {
	}

	public long getPrimaryKey() {
		return _fragmentEntryLinkId;
	}

	public void setPrimaryKey(long pk) {
		setFragmentEntryLinkId(pk);
	}

	public long getMvccVersion() {
		return _mvccVersion;
	}

	public void setMvccVersion(long mvccVersion) {
		_mvccVersion = mvccVersion;
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getFragmentEntryLinkId() {
		return _fragmentEntryLinkId;
	}

	public void setFragmentEntryLinkId(long fragmentEntryLinkId) {
		_fragmentEntryLinkId = fragmentEntryLinkId;
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

	public long getOriginalFragmentEntryLinkId() {
		return _originalFragmentEntryLinkId;
	}

	public void setOriginalFragmentEntryLinkId(
		long originalFragmentEntryLinkId) {

		_originalFragmentEntryLinkId = originalFragmentEntryLinkId;
	}

	public long getFragmentEntryId() {
		return _fragmentEntryId;
	}

	public void setFragmentEntryId(long fragmentEntryId) {
		_fragmentEntryId = fragmentEntryId;
	}

	public long getSegmentsExperienceId() {
		return _segmentsExperienceId;
	}

	public void setSegmentsExperienceId(long segmentsExperienceId) {
		_segmentsExperienceId = segmentsExperienceId;
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

	public long getPlid() {
		return _plid;
	}

	public void setPlid(long plid) {
		_plid = plid;
	}

	public String getCss() {
		return _css;
	}

	public void setCss(String css) {
		_css = css;
	}

	public String getHtml() {
		return _html;
	}

	public void setHtml(String html) {
		_html = html;
	}

	public String getJs() {
		return _js;
	}

	public void setJs(String js) {
		_js = js;
	}

	public String getConfiguration() {
		return _configuration;
	}

	public void setConfiguration(String configuration) {
		_configuration = configuration;
	}

	public String getEditableValues() {
		return _editableValues;
	}

	public void setEditableValues(String editableValues) {
		_editableValues = editableValues;
	}

	public String getNamespace() {
		return _namespace;
	}

	public void setNamespace(String namespace) {
		_namespace = namespace;
	}

	public int getPosition() {
		return _position;
	}

	public void setPosition(int position) {
		_position = position;
	}

	public String getRendererKey() {
		return _rendererKey;
	}

	public void setRendererKey(String rendererKey) {
		_rendererKey = rendererKey;
	}

	public Date getLastPropagationDate() {
		return _lastPropagationDate;
	}

	public void setLastPropagationDate(Date lastPropagationDate) {
		_lastPropagationDate = lastPropagationDate;
	}

	public Date getLastPublishDate() {
		return _lastPublishDate;
	}

	public void setLastPublishDate(Date lastPublishDate) {
		_lastPublishDate = lastPublishDate;
	}

	private long _mvccVersion;
	private String _uuid;
	private long _fragmentEntryLinkId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _originalFragmentEntryLinkId;
	private long _fragmentEntryId;
	private long _segmentsExperienceId;
	private long _classNameId;
	private long _classPK;
	private long _plid;
	private String _css;
	private String _html;
	private String _js;
	private String _configuration;
	private String _editableValues;
	private String _namespace;
	private int _position;
	private String _rendererKey;
	private Date _lastPropagationDate;
	private Date _lastPublishDate;

}