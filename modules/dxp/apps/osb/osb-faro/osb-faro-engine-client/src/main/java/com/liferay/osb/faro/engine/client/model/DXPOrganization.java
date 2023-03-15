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

package com.liferay.osb.faro.engine.client.model;

import java.util.Date;

/**
 * @author Matthew Kong
 */
public class DXPOrganization {

	public String getId() {
		return _id;
	}

	public Date getModifiedDate() {
		if (_modifiedDate == null) {
			return null;
		}

		return new Date(_modifiedDate.getTime());
	}

	public String getName() {
		return _name;
	}

	public String getNameTreePath() {
		return _nameTreePath;
	}

	public long getOrganizationId() {
		return _organizationId;
	}

	public String getOsbAsahDataSourceId() {
		return _osbAsahDataSourceId;
	}

	public String getParentName() {
		return _parentName;
	}

	public long getParentOrganizationId() {
		return _parentOrganizationId;
	}

	public String getTreePath() {
		return _treePath;
	}

	public String getType() {
		return _type;
	}

	public long getUsersCount() {
		return _usersCount;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setModifiedDate(Date modifiedDate) {
		if (modifiedDate != null) {
			_modifiedDate = new Date(modifiedDate.getTime());
		}
	}

	public void setName(String name) {
		_name = name;
	}

	public void setNameTreePath(String nameTreePath) {
		_nameTreePath = nameTreePath;
	}

	public void setOrganizationId(long organizationId) {
		_organizationId = organizationId;
	}

	public void setOsbAsahDataSourceId(String osbAsahDataSourceId) {
		_osbAsahDataSourceId = osbAsahDataSourceId;
	}

	public void setParentName(String parentName) {
		_parentName = parentName;
	}

	public void setParentOrganizationId(long parentOrganizationId) {
		_parentOrganizationId = parentOrganizationId;
	}

	public void setTreePath(String treePath) {
		_treePath = treePath;
	}

	public void setType(String type) {
		_type = type;
	}

	public void setUsersCount(long usersCount) {
		_usersCount = usersCount;
	}

	private String _id;
	private Date _modifiedDate;
	private String _name;
	private String _nameTreePath;
	private long _organizationId;
	private String _osbAsahDataSourceId;
	private String _parentName;
	private long _parentOrganizationId;
	private String _treePath;
	private String _type;
	private long _usersCount;

}