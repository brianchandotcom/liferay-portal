/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.model;

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * <p>
 * This class is a wrapper for {@link UserGroupRole}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see UserGroupRole
 * @generated
 */
public class UserGroupRoleWrapper
	extends BaseModelWrapper<UserGroupRole>
	implements ModelWrapper<UserGroupRole>, UserGroupRole {

	public UserGroupRoleWrapper(UserGroupRole userGroupRole) {
		super(userGroupRole);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("ctCollectionId", getCtCollectionId());
		attributes.put("userGroupRoleId", getUserGroupRoleId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("groupId", getGroupId());
		attributes.put("roleId", getRoleId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long ctCollectionId = (Long)attributes.get("ctCollectionId");

		if (ctCollectionId != null) {
			setCtCollectionId(ctCollectionId);
		}

		Long userGroupRoleId = (Long)attributes.get("userGroupRoleId");

		if (userGroupRoleId != null) {
			setUserGroupRoleId(userGroupRoleId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long roleId = (Long)attributes.get("roleId");

		if (roleId != null) {
			setRoleId(roleId);
		}
	}

	@Override
	public UserGroupRole cloneWithOriginalValues() {
		return wrap(model.cloneWithOriginalValues());
	}

	@Override
	public boolean equals(Object object) {
		return model.equals(object);
	}

	/**
	 * Returns the company ID of this user group role.
	 *
	 * @return the company ID of this user group role
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the ct collection ID of this user group role.
	 *
	 * @return the ct collection ID of this user group role
	 */
	@Override
	public long getCtCollectionId() {
		return model.getCtCollectionId();
	}

	@Override
	public Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getGroup();
	}

	/**
	 * Returns the group ID of this user group role.
	 *
	 * @return the group ID of this user group role
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the mvcc version of this user group role.
	 *
	 * @return the mvcc version of this user group role
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the primary key of this user group role.
	 *
	 * @return the primary key of this user group role
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	@Override
	public Role getRole()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getRole();
	}

	/**
	 * Returns the role ID of this user group role.
	 *
	 * @return the role ID of this user group role
	 */
	@Override
	public long getRoleId() {
		return model.getRoleId();
	}

	@Override
	public User getUser()
		throws com.liferay.portal.kernel.exception.PortalException {

		return model.getUser();
	}

	/**
	 * Returns the user group role ID of this user group role.
	 *
	 * @return the user group role ID of this user group role
	 */
	@Override
	public long getUserGroupRoleId() {
		return model.getUserGroupRoleId();
	}

	/**
	 * Returns the user ID of this user group role.
	 *
	 * @return the user ID of this user group role
	 */
	@Override
	public long getUserId() {
		return model.getUserId();
	}

	/**
	 * Returns the user uuid of this user group role.
	 *
	 * @return the user uuid of this user group role
	 */
	@Override
	public String getUserUuid() {
		return model.getUserUuid();
	}

	@Override
	public int hashCode() {
		return model.hashCode();
	}

	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the company ID of this user group role.
	 *
	 * @param companyId the company ID of this user group role
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the ct collection ID of this user group role.
	 *
	 * @param ctCollectionId the ct collection ID of this user group role
	 */
	@Override
	public void setCtCollectionId(long ctCollectionId) {
		model.setCtCollectionId(ctCollectionId);
	}

	/**
	 * Sets the group ID of this user group role.
	 *
	 * @param groupId the group ID of this user group role
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the mvcc version of this user group role.
	 *
	 * @param mvccVersion the mvcc version of this user group role
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the primary key of this user group role.
	 *
	 * @param primaryKey the primary key of this user group role
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the role ID of this user group role.
	 *
	 * @param roleId the role ID of this user group role
	 */
	@Override
	public void setRoleId(long roleId) {
		model.setRoleId(roleId);
	}

	/**
	 * Sets the user group role ID of this user group role.
	 *
	 * @param userGroupRoleId the user group role ID of this user group role
	 */
	@Override
	public void setUserGroupRoleId(long userGroupRoleId) {
		model.setUserGroupRoleId(userGroupRoleId);
	}

	/**
	 * Sets the user ID of this user group role.
	 *
	 * @param userId the user ID of this user group role
	 */
	@Override
	public void setUserId(long userId) {
		model.setUserId(userId);
	}

	/**
	 * Sets the user uuid of this user group role.
	 *
	 * @param userUuid the user uuid of this user group role
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
	public Map<String, Function<UserGroupRole, Object>>
		getAttributeGetterFunctions() {

		return model.getAttributeGetterFunctions();
	}

	@Override
	public Map<String, BiConsumer<UserGroupRole, Object>>
		getAttributeSetterBiConsumers() {

		return model.getAttributeSetterBiConsumers();
	}

	@Override
	protected UserGroupRoleWrapper wrap(UserGroupRole userGroupRole) {
		return new UserGroupRoleWrapper(userGroupRole);
	}

}