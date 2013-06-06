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

package com.liferay.portlet.journal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.portlet.journal.service.http.JournalFeedServiceSoap}.
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portlet.journal.service.http.JournalFeedServiceSoap
 * @generated
 */
public class JournalFeedSoap implements Serializable {
	public static JournalFeedSoap toSoapModel(JournalFeed model) {
		JournalFeedSoap soapModel = new JournalFeedSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setId(model.getId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setFeedId(model.getFeedId());
		soapModel.setName(model.getName());
		soapModel.setDescription(model.getDescription());
		soapModel.setType(model.getType());
		soapModel.setStructureId(model.getStructureId());
		soapModel.setTemplateId(model.getTemplateId());
		soapModel.setRendererTemplateId(model.getRendererTemplateId());
		soapModel.setDelta(model.getDelta());
		soapModel.setOrderByCol(model.getOrderByCol());
		soapModel.setOrderByType(model.getOrderByType());
		soapModel.setTargetLayoutFriendlyUrl(model.getTargetLayoutFriendlyUrl());
		soapModel.setTargetPortletId(model.getTargetPortletId());
		soapModel.setContentField(model.getContentField());
		soapModel.setFeedFormat(model.getFeedFormat());
		soapModel.setFeedVersion(model.getFeedVersion());

		return soapModel;
	}

	public static JournalFeedSoap[] toSoapModels(JournalFeed[] models) {
		JournalFeedSoap[] soapModels = new JournalFeedSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static JournalFeedSoap[][] toSoapModels(JournalFeed[][] models) {
		JournalFeedSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new JournalFeedSoap[models.length][models[0].length];
		}
		else {
			soapModels = new JournalFeedSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static JournalFeedSoap[] toSoapModels(List<JournalFeed> models) {
		List<JournalFeedSoap> soapModels = new ArrayList<JournalFeedSoap>(models.size());

		for (JournalFeed model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new JournalFeedSoap[soapModels.size()]);
	}

	public JournalFeedSoap() {
	}

	public long getPrimaryKey() {
		return _id;
	}

	public void setPrimaryKey(long pk) {
		setId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getId() {
		return _id;
	}

	public void setId(long id) {
		_id = id;
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

	public String getFeedId() {
		return _feedId;
	}

	public void setFeedId(String feedId) {
		_feedId = feedId;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getDescription() {
		return _description;
	}

	public void setDescription(String description) {
		_description = description;
	}

	public String getType() {
		return _type;
	}

	public void setType(String type) {
		_type = type;
	}

	public String getStructureId() {
		return _structureId;
	}

	public void setStructureId(String structureId) {
		_structureId = structureId;
	}

	public String getTemplateId() {
		return _templateId;
	}

	public void setTemplateId(String templateId) {
		_templateId = templateId;
	}

	public String getRendererTemplateId() {
		return _rendererTemplateId;
	}

	public void setRendererTemplateId(String rendererTemplateId) {
		_rendererTemplateId = rendererTemplateId;
	}

	public int getDelta() {
		return _delta;
	}

	public void setDelta(int delta) {
		_delta = delta;
	}

	public String getOrderByCol() {
		return _orderByCol;
	}

	public void setOrderByCol(String orderByCol) {
		_orderByCol = orderByCol;
	}

	public String getOrderByType() {
		return _orderByType;
	}

	public void setOrderByType(String orderByType) {
		_orderByType = orderByType;
	}

	public String getTargetLayoutFriendlyUrl() {
		return _targetLayoutFriendlyUrl;
	}

	public void setTargetLayoutFriendlyUrl(String targetLayoutFriendlyUrl) {
		_targetLayoutFriendlyUrl = targetLayoutFriendlyUrl;
	}

	public String getTargetPortletId() {
		return _targetPortletId;
	}

	public void setTargetPortletId(String targetPortletId) {
		_targetPortletId = targetPortletId;
	}

	public String getContentField() {
		return _contentField;
	}

	public void setContentField(String contentField) {
		_contentField = contentField;
	}

	public String getFeedFormat() {
		return _feedFormat;
	}

	public void setFeedFormat(String feedFormat) {
		_feedFormat = feedFormat;
	}

	public double getFeedVersion() {
		return _feedVersion;
	}

	public void setFeedVersion(double feedVersion) {
		_feedVersion = feedVersion;
	}

	private String _uuid;
	private long _id;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _feedId;
	private String _name;
	private String _description;
	private String _type;
	private String _structureId;
	private String _templateId;
	private String _rendererTemplateId;
	private int _delta;
	private String _orderByCol;
	private String _orderByType;
	private String _targetLayoutFriendlyUrl;
	private String _targetPortletId;
	private String _contentField;
	private String _feedFormat;
	private double _feedVersion;
}