/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.faro.contacts.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Shinn Lok
 * @generated
 */
public class ContactsLayoutTemplateSoap implements Serializable {

	public static ContactsLayoutTemplateSoap toSoapModel(
		ContactsLayoutTemplate model) {

		ContactsLayoutTemplateSoap soapModel = new ContactsLayoutTemplateSoap();

		soapModel.setContactsLayoutTemplateId(
			model.getContactsLayoutTemplateId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateTime(model.getCreateTime());
		soapModel.setModifiedTime(model.getModifiedTime());
		soapModel.setHeaderContactsCardTemplateIds(
			model.getHeaderContactsCardTemplateIds());
		soapModel.setName(model.getName());
		soapModel.setSettings(model.getSettings());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static ContactsLayoutTemplateSoap[] toSoapModels(
		ContactsLayoutTemplate[] models) {

		ContactsLayoutTemplateSoap[] soapModels =
			new ContactsLayoutTemplateSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ContactsLayoutTemplateSoap[][] toSoapModels(
		ContactsLayoutTemplate[][] models) {

		ContactsLayoutTemplateSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ContactsLayoutTemplateSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ContactsLayoutTemplateSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ContactsLayoutTemplateSoap[] toSoapModels(
		List<ContactsLayoutTemplate> models) {

		List<ContactsLayoutTemplateSoap> soapModels =
			new ArrayList<ContactsLayoutTemplateSoap>(models.size());

		for (ContactsLayoutTemplate model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new ContactsLayoutTemplateSoap[soapModels.size()]);
	}

	public ContactsLayoutTemplateSoap() {
	}

	public long getPrimaryKey() {
		return _contactsLayoutTemplateId;
	}

	public void setPrimaryKey(long pk) {
		setContactsLayoutTemplateId(pk);
	}

	public long getContactsLayoutTemplateId() {
		return _contactsLayoutTemplateId;
	}

	public void setContactsLayoutTemplateId(long contactsLayoutTemplateId) {
		_contactsLayoutTemplateId = contactsLayoutTemplateId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
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

	public long getCreateTime() {
		return _createTime;
	}

	public void setCreateTime(long createTime) {
		_createTime = createTime;
	}

	public long getModifiedTime() {
		return _modifiedTime;
	}

	public void setModifiedTime(long modifiedTime) {
		_modifiedTime = modifiedTime;
	}

	public String getHeaderContactsCardTemplateIds() {
		return _headerContactsCardTemplateIds;
	}

	public void setHeaderContactsCardTemplateIds(
		String headerContactsCardTemplateIds) {

		_headerContactsCardTemplateIds = headerContactsCardTemplateIds;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getSettings() {
		return _settings;
	}

	public void setSettings(String settings) {
		_settings = settings;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	private long _contactsLayoutTemplateId;
	private long _groupId;
	private long _userId;
	private String _userName;
	private long _createTime;
	private long _modifiedTime;
	private String _headerContactsCardTemplateIds;
	private String _name;
	private String _settings;
	private int _type;

}