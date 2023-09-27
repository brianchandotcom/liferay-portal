/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.changeset.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link ChangesetCollection}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see ChangesetCollection
 * @generated
 */
public class ChangesetCollectionWrapper
	extends BaseModelWrapper<ChangesetCollection>
	implements ChangesetCollection, ModelWrapper<ChangesetCollection> {

	public ChangesetCollectionWrapper(ChangesetCollection changesetCollection) {
		super(changesetCollection);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("changesetCollectionId", getChangesetCollectionId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("name", getName());
		attributes.put("description", getDescription());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long changesetCollectionId = (Long)attributes.get(
			"changesetCollectionId");

		if (changesetCollectionId != null) {
			setChangesetCollectionId(changesetCollectionId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String name = (String)attributes.get("name");

		if (name != null) {
			setName(name);
		}

		String description = (String)attributes.get("description");

		if (description != null) {
			setDescription(description);
		}
	}

	@Override
	public ChangesetCollection cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	/**
	 * Returns the changeset collection ID of this changeset collection.
	 *
	 * @return the changeset collection ID of this changeset collection
	 */
	@Override
	public long getChangesetCollectionId() {
		return model.getChangesetCollectionId();
	}

	/**
	 * Returns the company ID of this changeset collection.
	 *
	 * @return the company ID of this changeset collection
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the create date of this changeset collection.
	 *
	 * @return the create date of this changeset collection
	 */
	@Override
	public Date getCreateDate() {
		return model.getCreateDate();
	}

	/**
	 * Returns the description of this changeset collection.
	 *
	 * @return the description of this changeset collection
	 */
	@Override
	public String getDescription() {
		return model.getDescription();
	}

	/**
	 * Returns the group ID of this changeset collection.
	 *
	 * @return the group ID of this changeset collection
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the modified date of this changeset collection.
	 *
	 * @return the modified date of this changeset collection
	 */
	@Override
	public Date getModifiedDate() {
		return model.getModifiedDate();
	}

	/**
	 * Returns the name of this changeset collection.
	 *
	 * @return the name of this changeset collection
	 */
	@Override
	public String getName() {
		return model.getName();
	}

	/**
	 * Returns the primary key of this changeset collection.
	 *
	 * @return the primary key of this changeset collection
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the user ID of this changeset collection.
	 *
	 * @return the user ID of this changeset collection
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user name of this changeset collection.
	 *
	 * @return the user name of this changeset collection
	 */
	@Override
	public String getUserName() {
		return model.getUserName();
	}

	/**
	 * Returns the user uuid of this changeset collection.
	 *
	 * @return the user uuid of this changeset collection
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the changeset collection ID of this changeset collection.
	 *
	 * @param changesetCollectionId the changeset collection ID of this changeset collection
	 */
	@Override
	public void setChangesetCollectionId(long changesetCollectionId) {
		model.setChangesetCollectionId(changesetCollectionId);
	}

	/**
	 * Sets the company ID of this changeset collection.
	 *
	 * @param companyId the company ID of this changeset collection
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the create date of this changeset collection.
	 *
	 * @param createDate the create date of this changeset collection
	 */
	@Override
	public void setCreateDate(Date createDate) {
		model.setCreateDate(createDate);
	}

	/**
	 * Sets the description of this changeset collection.
	 *
	 * @param description the description of this changeset collection
	 */
	@Override
	public void setDescription(String description) {
		model.setDescription(description);
	}

	/**
	 * Sets the group ID of this changeset collection.
	 *
	 * @param groupId the group ID of this changeset collection
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the modified date of this changeset collection.
	 *
	 * @param modifiedDate the modified date of this changeset collection
	 */
	@Override
	public void setModifiedDate(Date modifiedDate) {
		model.setModifiedDate(modifiedDate);
	}

	/**
	 * Sets the name of this changeset collection.
	 *
	 * @param name the name of this changeset collection
	 */
	@Override
	public void setName(String name) {
		model.setName(name);
	}

	/**
	 * Sets the primary key of this changeset collection.
	 *
	 * @param primaryKey the primary key of this changeset collection
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the user ID of this changeset collection.
	 *
	 * @param userId the user ID of this changeset collection
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user name of this changeset collection.
	 *
	 * @param userName the user name of this changeset collection
	 */
	@Override
	public void setUserName(String userName) {
		model.setUserName(userName);
	}

	/**
	 * Sets the user uuid of this changeset collection.
	 *
	 * @param userUuid the user uuid of this changeset collection
	 */
	@Override
	public void setUserUuid(String userUuid) {
		model.setUserUuid(userUuid);
	}

	@Override
	public String toXmlString() {
		return model.toXmlString();
	}

	@Override
	protected ChangesetCollectionWrapper wrap(
		ChangesetCollection changesetCollection) {

		return new ChangesetCollectionWrapper(changesetCollection);
	}

}