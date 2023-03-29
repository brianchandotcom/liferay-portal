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
public class ContactsCardTemplateSoap implements Serializable {

	public static ContactsCardTemplateSoap toSoapModel(
		ContactsCardTemplate model) {

		ContactsCardTemplateSoap soapModel = new ContactsCardTemplateSoap();

		soapModel.setContactsCardTemplateId(model.getContactsCardTemplateId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateTime(model.getCreateTime());
		soapModel.setModifiedTime(model.getModifiedTime());
		soapModel.setName(model.getName());
		soapModel.setSettings(model.getSettings());
		soapModel.setType(model.getType());

		return soapModel;
	}

	public static ContactsCardTemplateSoap[] toSoapModels(
		ContactsCardTemplate[] models) {

		ContactsCardTemplateSoap[] soapModels =
			new ContactsCardTemplateSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static ContactsCardTemplateSoap[][] toSoapModels(
		ContactsCardTemplate[][] models) {

		ContactsCardTemplateSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new ContactsCardTemplateSoap[models.length][models[0].length];
		}
		else {
			soapModels = new ContactsCardTemplateSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static ContactsCardTemplateSoap[] toSoapModels(
		List<ContactsCardTemplate> models) {

		List<ContactsCardTemplateSoap> soapModels =
			new ArrayList<ContactsCardTemplateSoap>(models.size());

		for (ContactsCardTemplate model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(
			new ContactsCardTemplateSoap[soapModels.size()]);
	}

	public ContactsCardTemplateSoap() {
	}

	public long getPrimaryKey() {
		return _contactsCardTemplateId;
	}

	public void setPrimaryKey(long pk) {
		setContactsCardTemplateId(pk);
	}

	public long getContactsCardTemplateId() {
		return _contactsCardTemplateId;
	}

	public void setContactsCardTemplateId(long contactsCardTemplateId) {
		_contactsCardTemplateId = contactsCardTemplateId;
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

	private long _contactsCardTemplateId;
	private long _groupId;
	private long _userId;
	private String _userName;
	private long _createTime;
	private long _modifiedTime;
	private String _name;
	private String _settings;
	private int _type;

}