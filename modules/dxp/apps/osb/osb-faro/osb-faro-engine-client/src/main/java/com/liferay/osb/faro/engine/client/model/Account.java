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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class Account {

	public long getActivitiesCount() {
		return _activitiesCount;
	}

	public Map<String, String> getDataSourceAccountPKs() {
		return _dataSourceAccountPKs;
	}

	public Date getDateCreated() {
		if (_dateCreated == null) {
			return null;
		}

		return new Date(_dateCreated.getTime());
	}

	public Date getDateModified() {
		if (_dateModified == null) {
			return null;
		}

		return new Date(_dateModified.getTime());
	}

	public String getId() {
		return _id;
	}

	public long getIndividualCount() {
		return _individualCount;
	}

	public Map<String, List<Field>> getOrganization() {
		return _organization;
	}

	public void setActivitiesCount(long activitiesCount) {
		_activitiesCount = activitiesCount;
	}

	public void setDataSourceAccountPKs(
		Map<String, String> dataSourceAccountPKs) {

		_dataSourceAccountPKs = dataSourceAccountPKs;
	}

	public void setDateCreated(Date dateCreated) {
		if (dateCreated != null) {
			_dateCreated = new Date(dateCreated.getTime());
		}
	}

	public void setDateModified(Date dateModified) {
		if (dateModified != null) {
			_dateModified = new Date(dateModified.getTime());
		}
	}

	public void setId(String id) {
		_id = id;
	}

	public void setIndividualCount(long individualCount) {
		_individualCount = individualCount;
	}

	public void setOrganization(Map<String, List<Field>> organization) {
		_organization = organization;
	}

	private long _activitiesCount;
	private Map<String, String> _dataSourceAccountPKs = new HashMap<>();
	private Date _dateCreated;
	private Date _dateModified;
	private String _id;
	private long _individualCount;
	private Map<String, List<Field>> _organization = new HashMap<>();

}