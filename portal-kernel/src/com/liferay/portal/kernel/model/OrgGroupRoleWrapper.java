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

import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link OrgGroupRole}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see OrgGroupRole
 * @generated
 */
public class OrgGroupRoleWrapper
	extends BaseModelWrapper<OrgGroupRole>
	implements ModelWrapper<OrgGroupRole>, OrgGroupRole {

	public OrgGroupRoleWrapper(OrgGroupRole orgGroupRole) {
		super(orgGroupRole);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("mvccVersion", getMvccVersion());
		attributes.put("organizationId", getOrganizationId());
		attributes.put("groupId", getGroupId());
		attributes.put("roleId", getRoleId());
		attributes.put("companyId", getCompanyId());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long mvccVersion = (Long)attributes.get("mvccVersion");

		if (mvccVersion != null) {
			setMvccVersion(mvccVersion);
		}

		Long organizationId = (Long)attributes.get("organizationId");

		if (organizationId != null) {
			setOrganizationId(organizationId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long roleId = (Long)attributes.get("roleId");

		if (roleId != null) {
			setRoleId(roleId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}
	}

	@Override
	public boolean containsGroup(java.util.List<Group> groups) {
		return model.containsGroup(groups);
	}

	@Override
	public boolean containsOrganization(
		java.util.List<Organization> organizations) {

		return model.containsOrganization(organizations);
	}

	/**
	 * Returns the company ID of this org group role.
	 *
	 * @return the company ID of this org group role
	 */
	@Override
	public long getCompanyId() {
		return model.getCompanyId();
	}

	/**
	 * Returns the group ID of this org group role.
	 *
	 * @return the group ID of this org group role
	 */
	@Override
	public long getGroupId() {
		return model.getGroupId();
	}

	/**
	 * Returns the mvcc version of this org group role.
	 *
	 * @return the mvcc version of this org group role
	 */
	@Override
	public long getMvccVersion() {
		return model.getMvccVersion();
	}

	/**
	 * Returns the organization ID of this org group role.
	 *
	 * @return the organization ID of this org group role
	 */
	@Override
	public long getOrganizationId() {
		return model.getOrganizationId();
	}

	/**
	 * Returns the primary key of this org group role.
	 *
	 * @return the primary key of this org group role
	 */
	@Override
	public com.liferay.portal.kernel.service.persistence.OrgGroupRolePK
		getPrimaryKey() {

		return model.getPrimaryKey();
	}

	/**
	 * Returns the role ID of this org group role.
	 *
	 * @return the role ID of this org group role
	 */
	@Override
	public long getRoleId() {
		return model.getRoleId();
	}

	/**
	 * Sets the company ID of this org group role.
	 *
	 * @param companyId the company ID of this org group role
	 */
	@Override
	public void setCompanyId(long companyId) {
		model.setCompanyId(companyId);
	}

	/**
	 * Sets the group ID of this org group role.
	 *
	 * @param groupId the group ID of this org group role
	 */
	@Override
	public void setGroupId(long groupId) {
		model.setGroupId(groupId);
	}

	/**
	 * Sets the mvcc version of this org group role.
	 *
	 * @param mvccVersion the mvcc version of this org group role
	 */
	@Override
	public void setMvccVersion(long mvccVersion) {
		model.setMvccVersion(mvccVersion);
	}

	/**
	 * Sets the organization ID of this org group role.
	 *
	 * @param organizationId the organization ID of this org group role
	 */
	@Override
	public void setOrganizationId(long organizationId) {
		model.setOrganizationId(organizationId);
	}

	/**
	 * Sets the primary key of this org group role.
	 *
	 * @param primaryKey the primary key of this org group role
	 */
	@Override
	public void setPrimaryKey(
		com.liferay.portal.kernel.service.persistence.OrgGroupRolePK
			primaryKey) {

		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the role ID of this org group role.
	 *
	 * @param roleId the role ID of this org group role
	 */
	@Override
	public void setRoleId(long roleId) {
		model.setRoleId(roleId);
	}

	@Override
	protected OrgGroupRoleWrapper wrap(OrgGroupRole orgGroupRole) {
		return new OrgGroupRoleWrapper(orgGroupRole);
	}

}