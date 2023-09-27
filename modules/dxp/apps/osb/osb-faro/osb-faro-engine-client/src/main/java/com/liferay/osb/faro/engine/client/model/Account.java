/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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