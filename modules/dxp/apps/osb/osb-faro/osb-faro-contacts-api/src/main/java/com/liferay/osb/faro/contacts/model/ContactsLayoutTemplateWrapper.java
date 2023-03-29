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
 * This class is a wrapper for {@link ContactsLayoutTemplate}.
 * </p>
 *
 * @author Shinn Lok
 * @see ContactsLayoutTemplate
 * @generated
 */
public class ContactsLayoutTemplateWrapper
	implements ContactsLayoutTemplate, ModelWrapper<ContactsLayoutTemplate> {

	public ContactsLayoutTemplateWrapper(
		ContactsLayoutTemplate contactsLayoutTemplate) {

		_contactsLayoutTemplate = contactsLayoutTemplate;
	}

	@Override
	public Class<?> getModelClass() {
		return ContactsLayoutTemplate.class;
	}

	@Override
	public String getModelClassName() {
		return ContactsLayoutTemplate.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put(
			"contactsLayoutTemplateId", getContactsLayoutTemplateId());
		attributes.put("groupId", getGroupId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createTime", getCreateTime());
		attributes.put("modifiedTime", getModifiedTime());
		attributes.put(
			"headerContactsCardTemplateIds",
			getHeaderContactsCardTemplateIds());
		attributes.put("name", getName());
		attributes.put("settings", getSettings());
		attributes.put("type", getType());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long contactsLayoutTemplateId = (Long)attributes.get(
			"contactsLayoutTemplateId");

		if (contactsLayoutTemplateId != null) {
			setContactsLayoutTemplateId(contactsLayoutTemplateId);
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

		String headerContactsCardTemplateIds = (String)attributes.get(
			"headerContactsCardTemplateIds");

		if (headerContactsCardTemplateIds != null) {
			setHeaderContactsCardTemplateIds(headerContactsCardTemplateIds);
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
		return new ContactsLayoutTemplateWrapper(
			(ContactsLayoutTemplate)_contactsLayoutTemplate.clone());
	}

	@Override
	public int compareTo(ContactsLayoutTemplate contactsLayoutTemplate) {
		return _contactsLayoutTemplate.compareTo(contactsLayoutTemplate);
	}

	/**
	 * Returns the contacts layout template ID of this contacts layout template.
	 *
	 * @return the contacts layout template ID of this contacts layout template
	 */
	@Override
	public long getContactsLayoutTemplateId() {
		return _contactsLayoutTemplate.getContactsLayoutTemplateId();
	}

	/**
	 * Returns the create time of this contacts layout template.
	 *
	 * @return the create time of this contacts layout template
	 */
	@Override
	public long getCreateTime() {
		return _contactsLayoutTemplate.getCreateTime();
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return _contactsLayoutTemplate.getExpandoBridge();
	}

	/**
	 * Returns the group ID of this contacts layout template.
	 *
	 * @return the group ID of this contacts layout template
	 */
	@Override
	public long getGroupId() {
		return _contactsLayoutTemplate.getGroupId();
	}

	/**
	 * Returns the header contacts card template IDs of this contacts layout template.
	 *
	 * @return the header contacts card template IDs of this contacts layout template
	 */
	@Override
	public String getHeaderContactsCardTemplateIds() {
		return _contactsLayoutTemplate.getHeaderContactsCardTemplateIds();
	}

	/**
	 * Returns the modified time of this contacts layout template.
	 *
	 * @return the modified time of this contacts layout template
	 */
	@Override
	public long getModifiedTime() {
		return _contactsLayoutTemplate.getModifiedTime();
	}

	/**
	 * Returns the name of this contacts layout template.
	 *
	 * @return the name of this contacts layout template
	 */
	@Override
	public String getName() {
		return _contactsLayoutTemplate.getName();
	}

	/**
	 * Returns the primary key of this contacts layout template.
	 *
	 * @return the primary key of this contacts layout template
	 */
	@Override
	public long getPrimaryKey() {
		return _contactsLayoutTemplate.getPrimaryKey();
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _contactsLayoutTemplate.getPrimaryKeyObj();
	}

	/**
	 * Returns the settings of this contacts layout template.
	 *
	 * @return the settings of this contacts layout template
	 */
	@Override
	public String getSettings() {
		return _contactsLayoutTemplate.getSettings();
	}

	/**
	 * Returns the type of this contacts layout template.
	 *
	 * @return the type of this contacts layout template
	 */
	@Override
	public int getType() {
		return _contactsLayoutTemplate.getType();
	}

	/**
	 * Returns the user ID of this contacts layout template.
	 *
	 * @return the user ID of this contacts layout template
	 */
	@Override
	public long getUserId() {
		return _contactsLayoutTemplate.getUserId();
	}

	/**
	 * Returns the user name of this contacts layout template.
	 *
	 * @return the user name of this contacts layout template
	 */
	@Override
	public String getUserName() {
		return _contactsLayoutTemplate.getUserName();
	}

	/**
	 * Returns the user uuid of this contacts layout template.
	 *
	 * @return the user uuid of this contacts layout template
	 */
	@Override
	public String getUserUuid() {
		return _contactsLayoutTemplate.getUserUuid();
	}

	@Override
	public int hashCode() {
		return _contactsLayoutTemplate.hashCode();
	}

	@Override
	public boolean isCachedModel() {
		return _contactsLayoutTemplate.isCachedModel();
	}

	@Override
	public boolean isEscapedModel() {
		return _contactsLayoutTemplate.isEscapedModel();
	}

	@Override
	public boolean isNew() {
		return _contactsLayoutTemplate.isNew();
	}

	@Override
	public void persist() {
		_contactsLayoutTemplate.persist();
	}

	@Override
	public void setCachedModel(boolean cachedModel) {
		_contactsLayoutTemplate.setCachedModel(cachedModel);
	}

	/**
	 * Sets the contacts layout template ID of this contacts layout template.
	 *
	 * @param contactsLayoutTemplateId the contacts layout template ID of this contacts layout template
	 */
	@Override
	public void setContactsLayoutTemplateId(long contactsLayoutTemplateId) {
		_contactsLayoutTemplate.setContactsLayoutTemplateId(
			contactsLayoutTemplateId);
	}

	/**
	 * Sets the create time of this contacts layout template.
	 *
	 * @param createTime the create time of this contacts layout template
	 */
	@Override
	public void setCreateTime(long createTime) {
		_contactsLayoutTemplate.setCreateTime(createTime);
	}

	@Override
	public void setExpandoBridgeAttributes(
		com.liferay.portal.kernel.model.BaseModel<?> baseModel) {

		_contactsLayoutTemplate.setExpandoBridgeAttributes(baseModel);
	}

	@Override
	public void setExpandoBridgeAttributes(ExpandoBridge expandoBridge) {
		_contactsLayoutTemplate.setExpandoBridgeAttributes(expandoBridge);
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		_contactsLayoutTemplate.setExpandoBridgeAttributes(serviceContext);
	}

	/**
	 * Sets the group ID of this contacts layout template.
	 *
	 * @param groupId the group ID of this contacts layout template
	 */
	@Override
	public void setGroupId(long groupId) {
		_contactsLayoutTemplate.setGroupId(groupId);
	}

	/**
	 * Sets the header contacts card template IDs of this contacts layout template.
	 *
	 * @param headerContactsCardTemplateIds the header contacts card template IDs of this contacts layout template
	 */
	@Override
	public void setHeaderContactsCardTemplateIds(
		String headerContactsCardTemplateIds) {

		_contactsLayoutTemplate.setHeaderContactsCardTemplateIds(
			headerContactsCardTemplateIds);
	}

	/**
	 * Sets the modified time of this contacts layout template.
	 *
	 * @param modifiedTime the modified time of this contacts layout template
	 */
	@Override
	public void setModifiedTime(long modifiedTime) {
		_contactsLayoutTemplate.setModifiedTime(modifiedTime);
	}

	/**
	 * Sets the name of this contacts layout template.
	 *
	 * @param name the name of this contacts layout template
	 */
	@Override
	public void setName(String name) {
		_contactsLayoutTemplate.setName(name);
	}

	@Override
	public void setNew(boolean n) {
		_contactsLayoutTemplate.setNew(n);
	}

	/**
	 * Sets the primary key of this contacts layout template.
	 *
	 * @param primaryKey the primary key of this contacts layout template
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		_contactsLayoutTemplate.setPrimaryKey(primaryKey);
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		_contactsLayoutTemplate.setPrimaryKeyObj(primaryKeyObj);
	}

	/**
	 * Sets the settings of this contacts layout template.
	 *
	 * @param settings the settings of this contacts layout template
	 */
	@Override
	public void setSettings(String settings) {
		_contactsLayoutTemplate.setSettings(settings);
	}

	/**
	 * Sets the type of this contacts layout template.
	 *
	 * @param type the type of this contacts layout template
	 */
	@Override
	public void setType(int type) {
		_contactsLayoutTemplate.setType(type);
	}

	/**
	 * Sets the user ID of this contacts layout template.
	 *
	 * @param userId the user ID of this contacts layout template
	 */
	@Override
	public void setUserId(long userId) {
		_contactsLayoutTemplate.setUserId(userId);
	}

	/**
	 * Sets the user name of this contacts layout template.
	 *
	 * @param userName the user name of this contacts layout template
	 */
	@Override
	public void setUserName(String userName) {
		_contactsLayoutTemplate.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this contacts layout template.
	 *
	 * @param userUuid the user uuid of this contacts layout template
	 */
	@Override
	public void setUserUuid(String userUuid) {
		_contactsLayoutTemplate.setUserUuid(userUuid);
	}

	@Override
	public com.liferay.portal.kernel.model.CacheModel<ContactsLayoutTemplate>
		toCacheModel() {

		return _contactsLayoutTemplate.toCacheModel();
	}

	@Override
	public ContactsLayoutTemplate toEscapedModel() {
		return new ContactsLayoutTemplateWrapper(
			_contactsLayoutTemplate.toEscapedModel());
	}

	@Override
	public String toString() {
		return _contactsLayoutTemplate.toString();
	}

	@Override
	public ContactsLayoutTemplate toUnescapedModel() {
		return new ContactsLayoutTemplateWrapper(
			_contactsLayoutTemplate.toUnescapedModel());
	}

	@Override
	public String toXmlString() {
		return _contactsLayoutTemplate.toXmlString();
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof ContactsLayoutTemplateWrapper)) {
			return false;
		}

		ContactsLayoutTemplateWrapper contactsLayoutTemplateWrapper =
			(ContactsLayoutTemplateWrapper)object;

		if (Objects.equals(
				_contactsLayoutTemplate,
				contactsLayoutTemplateWrapper._contactsLayoutTemplate)) {

			return true;
		}

		return false;
	}

	@Override
	public ContactsLayoutTemplate getWrappedModel() {
		return _contactsLayoutTemplate;
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return _contactsLayoutTemplate.isEntityCacheEnabled();
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return _contactsLayoutTemplate.isFinderCacheEnabled();
	}

	@Override
	public void resetOriginalValues() {
		_contactsLayoutTemplate.resetOriginalValues();
	}

	private final ContactsLayoutTemplate _contactsLayoutTemplate;

}