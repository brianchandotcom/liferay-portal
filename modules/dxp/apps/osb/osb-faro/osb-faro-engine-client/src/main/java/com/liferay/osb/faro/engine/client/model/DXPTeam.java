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

/**
 * @author Matthew Kong
 */
public class DXPTeam {

	public long getGroupId() {
		return _groupId;
	}

	public String getId() {
		return _id;
	}

	public String getName() {
		return _name;
	}

	public String getOsbAsahDataSourceId() {
		return _osbAsahDataSourceId;
	}

	public long getTeamId() {
		return _teamId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setOsbAsahDataSourceId(String osbAsahDataSourceId) {
		_osbAsahDataSourceId = osbAsahDataSourceId;
	}

	public void setTeamId(long teamId) {
		_teamId = teamId;
	}

	private long _groupId;
	private String _id;
	private String _name;
	private String _osbAsahDataSourceId;
	private long _teamId;

}