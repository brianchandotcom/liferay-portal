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

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.service.ServiceContext;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * This class is a wrapper for {@link ContactsCardTemplate}.
 * </p>
 *
 * @author Shinn Lok
 * @see ContactsCardTemplate
 * @generated
 */
public class ContactsCardTemplateWrapper
	implements ContactsCardTemplate, ModelWrapper<ContactsCardTemplate> {

	public ContactsCardTemplateWrapper(
		ContactsCardTemplate contactsCardTemplate) {

		_contactsCardTemplate = contactsCardTemplate;
	}

	@Override
	public Class<?> getModelClass() {
		return ContactsCardTemplate.class;
	}

	@Override
	public String getModelClassName() {
		return ContactsCardTemplate.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("contactsCardTemplateId", getContactsCardTemplateId());
		attributes.put("groupId", getGroupId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createTime", getCreateTime());
		attributes.put("modifiedTime", getModifiedTime());
		attributes.put("name", getName());
		attributes.put("settings", getSettings());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long contactsCardTemplateId = (Long)attributes.get(
			"contactsCardTemplateId");

		if (contactsCardTemplateId != null) {
			setContactsCardTemplateId(contactsCardTemplateId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Long createTime = (Long)attributes.get("createTime");

		if (createTime != null) {
			setCreateTime(createTime);
		}

		Long modifiedTime = (Long)attributes.get("modifiedTime");

		if (modifiedTime != null) {
			setModifiedTime(modifiedTime);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String settings = (String)attributes.get("settings");

		if (settings != null) {
			setSettings(settings);
		}

		Integer type = (Integer)attributes.get("type");

		if (type != null) {
			setType(type);
		}
	}

	@Override
	public Object clone() {
		return new ContactsCardTemplateWrapper(
			(ContactsCardTemplate)_contactsCardTemplate.clone());
	}

	@Override
	public int compareTo(ContactsCardTemplate contactsCardTemplate) {
		return _contactsCardTemplate.compareTo(contactsCardTemplate);
	}

	/**
	 * Returns the contacts card template ID of this contacts card template.
	 *
	 * @return the contacts card template ID of this contacts card template
	 */
	@Override
	public long getContactsCardTemplateId() {
		return _contactsCardTemplate.getContactsCardTemplateId();
	}

	/**
	 * Returns the create time of this contacts card template.
	 *
	 * @return the create time of this contacts card template
	 */
	@Override
	public long getCreateTime() {
		return _contactsCardTemplate.getCreateTime();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _contactsCardTemplate.getExpandoBridge();
	}

	/**
	 * Returns the group ID of this contacts card template.
	 *
	 * @return the group ID of this contacts card template
	 */
	@Override
	public long getGroupId() {
		return _contactsCardTemplate.getGroupId();
	}

	/**
	 * Returns the modified time of this contacts card template.
	 *
	 * @return the modified time of this contacts card template
	 */
	@Override
	public long getModifiedTime() {
		return _contactsCardTemplate.getModifiedTime();
	}

	/**
	 * Returns the name of this contacts card template.
	 *
	 * @return the name of this contacts card template
	 */
	@Override
	public String getName() {
		return _contactsCardTemplate.getName();
	}

	/**
	 * Returns the primary key of this contacts card template.
	 *
	 * @return the primary key of this contacts card template
	 */
	@Override
	public long getPrimaryKey() {
		return _contactsCardTemplate.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _contactsCardTemplate.getPrimaryKeyObj();
	}

	/**
	 * Returns the settings of this contacts card template.
	 *
	 * @return the settings of this contacts card template
	 */
	@Override
	public String getSettings() {
		return _contactsCardTemplate.getSettings();
	}

	/**
	 * Returns the type of this contacts card template.
	 *
	 * @return the type of this contacts card template
	 */
	@Override
	public int getType() {
		return _contactsCardTemplate.getType();
	}

	/**
	 * Returns the user ID of this contacts card template.
	 *
	 * @return the user ID of this contacts card template
	 */
	@Override
	public long getUserId() {
		return _contactsCardTemplate.getUserId();
	}

	/**
	 * Returns the user name of this contacts card template.
	 *
	 * @return the user name of this contacts card template
	 */
	@Override
	public String getUserName() {
		return _contactsCardTemplate.getUserName();
	}

	/**
	 * Returns the user uuid of this contacts card template.
	 *
	 * @return the user uuid of this contacts card template
	 */
	@Override
	public String getUserUuid() {
		return _contactsCardTemplate.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _contactsCardTemplate.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _contactsCardTemplate.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _contactsCardTemplate.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _contactsCardTemplate.isNew();
	}

	@Override
	public void persist() {
		_contactsCardTemplate.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_contactsCardTemplate.setCachedModel(cachedModel);
	}

	/**
	 * Sets the contacts card template ID of this contacts card template.
	 *
	 * @param contactsCardTemplateId the contacts card template ID of this contacts card template
	 */
	@Override
	public void setContactsCardTemplateId(long contactsCardTemplateId) {
		_contactsCardTemplate.setContactsCardTemplateId(contactsCardTemplateId);
	}

	/**
	 * Sets the create time of this contacts card template.
	 *
	 * @param createTime the create time of this contacts card template
	 */
	@Override
	public void setCreateTime(long createTime) {
		_contactsCardTemplate.setCreateTime(createTime);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_contactsCardTemplate.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_contactsCardTemplate.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_contactsCardTemplate.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the group ID of this contacts card template.
	 *
	 * @param groupId the group ID of this contacts card template
	 */
	@Override
	public void setGroupId(long groupId) {
		_contactsCardTemplate.setGroupId(groupId);
	}

	/**
	 * Sets the modified time of this contacts card template.
	 *
	 * @param modifiedTime the modified time of this contacts card template
	 */
	@Override
	public void setModifiedTime(long modifiedTime) {
		_contactsCardTemplate.setModifiedTime(modifiedTime);
	}

	/**
	 * Sets the name of this contacts card template.
	 *
	 * @param name the name of this contacts card template
	 */
	@Override
	public void setName(String name) {
		_contactsCardTemplate.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_contactsCardTemplate.setNew(n);
	}

	/**
	 * Sets the primary key of this contacts card template.
	 *
	 * @param primaryKey the primary key of this contacts card template
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_contactsCardTemplate.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_contactsCardTemplate.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the settings of this contacts card template.
	 *
	 * @param settings the settings of this contacts card template
	 */
	@Override
	public void setSettings(String settings) {
		_contactsCardTemplate.setSettings(settings);
	}

	/**
	 * Sets the type of this contacts card template.
	 *
	 * @param type the type of this contacts card template
	 */
	@Override
	public void setType(int type) {
		_contactsCardTemplate.setType(type);
	}

	/**
	 * Sets the user ID of this contacts card template.
	 *
	 * @param userId the user ID of this contacts card template
	 */
	@Override
	public void setUserId(long userId) {
		_contactsCardTemplate.setUserId(userId);
	}

	/**
	 * Sets the user name of this contacts card template.
	 *
	 * @param userName the user name of this contacts card template
	 */
	@Override
	public void setUserName(String userName) {
		_contactsCardTemplate.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this contacts card template.
	 *
	 * @param userUuid the user uuid of this contacts card template
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_contactsCardTemplate.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ContactsCardTemplate>
		toCacheModel() {

		return _contactsCardTemplate.toCacheModel();
	}

	@Override
	public ContactsCardTemplate toEscapedModel() {
		return new ContactsCardTemplateWrapper(
			_contactsCardTemplate.toEscapedModel());
	}

	@Override
	public String toString() {
		return _contactsCardTemplate.toString();
	}

	@Override
	public ContactsCardTemplate toUnescapedModel() {
		return new ContactsCardTemplateWrapper(
			_contactsCardTemplate.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _contactsCardTemplate.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ContactsCardTemplateWrapper)) {
			return false;
		}

		ContactsCardTemplateWrapper contactsCardTemplateWrapper =
			(ContactsCardTemplateWrapper)object;

		if (Objects.equals(
				_contactsCardTemplate,
				contactsCardTemplateWrapper._contactsCardTemplate)) {

			return true;
		}

		return false;
	}

	@Override
	public ContactsCardTemplate getWrappedModel() {
		return _contactsCardTemplate;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _contactsCardTemplate.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _contactsCardTemplate.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_contactsCardTemplate.resetOriginalValues();
	}

	private final ContactsCardTemplate _contactsCardTemplate;

}