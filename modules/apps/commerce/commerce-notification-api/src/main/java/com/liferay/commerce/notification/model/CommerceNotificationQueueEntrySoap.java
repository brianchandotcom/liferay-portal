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

package com.liferay.commerce.notification.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.liferay.commerce.notification.service.http.CommerceNotificationQueueEntryServiceSoap}.
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceNotificationQueueEntrySoap implements Serializable {

	public static CommerceNotificationQueueEntrySoap toSoapModel(
		CommerceNotificationQueueEntry model) {

		CommerceNotificationQueueEntrySoap soapModel =
			new CommerceNotificationQueueEntrySoap();

		soapModel.setCommerceNotificationQueueEntryId(
			model.getCommerceNotificationQueueEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setCommerceNotificationTemplateId(
			model.getCommerceNotificationTemplateId());
		soapModel.setFrom(model.getFrom());
		soapModel.setFromName(model.getFromName());
		soapModel.setTo(model.getTo());
		soapModel.setToName(model.getToName());
		soapModel.setCc(model.getCc());
		soapModel.setBcc(model.getBcc());
		soapModel.setSubject(model.getSubject());
		soapModel.setBody(model.getBody());
		soapModel.setPriority(model.getPriority());
		soapModel.setSent(model.isSent());
		soapModel.setSentDate(model.getSentDate());

		return soapModel;
	}

	public static CommerceNotificationQueueEntrySoap[] toSoapModels(
		CommerceNotificationQueueEntry[] models) {

		CommerceNotificationQueueEntrySoap[] soapModels =
			new CommerceNotificationQueueEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CommerceNotificationQueueEntrySoap[][] toSoapModels(
		CommerceNotificationQueueEntry[][] models) {

		CommerceNotificationQueueEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new CommerceNotificationQueueEntrySoap
				[models.length][models[0].length];
		}
		else {
			soapModels = new CommerceNotificationQueueEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CommerceNotificationQueueEntrySoap[] toSoapModels(
		List<CommerceNotificationQueueEntry> models) {

		List<CommerceNotificationQueueEntrySoap> soapModels =
			new ArrayList<CommerceNotificationQueueEntrySoap>(models.size());

		for (CommerceNotificationQueueEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new CommerceNotificationQueueEntrySoap[soapModels.size()]);
	}

	public CommerceNotificationQueueEntrySoap() {
	}

	public long getPrimaryKey() {
		return _commerceNotificationQueueEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCommerceNotificationQueueEntryId(pk);
	}

	public long getCommerceNotificationQueueEntryId() {
		return _commerceNotificationQueueEntryId;
	}

	public void setCommerceNotificationQueueEntryId(
		long commerceNotificationQueueEntryId) {

		_commerceNotificationQueueEntryId = commerceNotificationQueueEntryId;
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

	public long getCommerceNotificationTemplateId() {
		return _commerceNotificationTemplateId;
	}

	public void setCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {

		_commerceNotificationTemplateId = commerceNotificationTemplateId;
	}

	public String getFrom() {
		return _from;
	}

	public void setFrom(String from) {
		_from = from;
	}

	public String getFromName() {
		return _fromName;
	}

	public void setFromName(String fromName) {
		_fromName = fromName;
	}

	public String getTo() {
		return _to;
	}

	public void setTo(String to) {
		_to = to;
	}

	public String getToName() {
		return _toName;
	}

	public void setToName(String toName) {
		_toName = toName;
	}

	public String getCc() {
		return _cc;
	}

	public void setCc(String cc) {
		_cc = cc;
	}

	public String getBcc() {
		return _bcc;
	}

	public void setBcc(String bcc) {
		_bcc = bcc;
	}

	public String getSubject() {
		return _subject;
	}

	public void setSubject(String subject) {
		_subject = subject;
	}

	public String getBody() {
		return _body;
	}

	public void setBody(String body) {
		_body = body;
	}

	public double getPriority() {
		return _priority;
	}

	public void setPriority(double priority) {
		_priority = priority;
	}

	public boolean getSent() {
		return _sent;
	}

	public boolean isSent() {
		return _sent;
	}

	public void setSent(boolean sent) {
		_sent = sent;
	}

	public Date getSentDate() {
		return _sentDate;
	}

	public void setSentDate(Date sentDate) {
		_sentDate = sentDate;
	}

	private long _commerceNotificationQueueEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _classPK;
	private long _commerceNotificationTemplateId;
	private String _from;
	private String _fromName;
	private String _to;
	private String _toName;
	private String _cc;
	private String _bcc;
	private String _subject;
	private String _body;
	private double _priority;
	private boolean _sent;
	private Date _sentDate;

}