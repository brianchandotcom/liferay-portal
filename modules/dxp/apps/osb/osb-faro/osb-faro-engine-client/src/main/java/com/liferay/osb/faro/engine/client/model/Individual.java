/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class Individual {

	public long getActivitiesCount() {
		return _activitiesCount;
	}

	public Map<String, List<Field>> getCustom() {
		return _custom;
	}

	public List<DataSourceIndividualPK> getDataSourceIndividualPKs() {
		return _dataSourceIndividualPKs;
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

	public Map<String, List<Field>> getDemographics() {
		return _demographics;
	}

	@JsonProperty("_embedded")
	public Map<String, Object> getEmbeddedResources() {
		return _embeddedResources;
	}

	public String getId() {
		return _id;
	}

	public Date getLastActivityDate() {
		if (_lastActivityDate == null) {
			return null;
		}

		return new Date(_lastActivityDate.getTime());
	}

	public void setActivitiesCount(long activitiesCount) {
		_activitiesCount = activitiesCount;
	}

	public void setCustom(Map<String, List<Field>> custom) {
		_custom = custom;
	}

	public void setDataSourceIndividualPKs(
		List<DataSourceIndividualPK> dataSourceIndividualPKs) {

		_dataSourceIndividualPKs = dataSourceIndividualPKs;
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

	public void setDemographics(Map<String, List<Field>> demographics) {
		_demographics = demographics;
	}

	public void setEmbeddedResources(Map<String, Object> embeddedResources) {
		_embeddedResources = embeddedResources;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setLastActivityDate(Date lastActivityDate) {
		if (lastActivityDate != null) {
			_lastActivityDate = new Date(lastActivityDate.getTime());
		}
	}

	public static class DataSourceIndividualPK {

		public String getDataSourceId() {
			return _dataSourceId;
		}

		public String getDataSourceType() {
			return _dataSourceType;
		}

		public List<String> getIndividualPKs() {
			return _individualPKs;
		}

		public void setDataSourceId(String dataSourceId) {
			_dataSourceId = dataSourceId;
		}

		public void setDataSourceType(String dataSourceType) {
			_dataSourceType = dataSourceType;
		}

		public void setIndividualPKs(List<String> individualPKs) {
			_individualPKs = individualPKs;
		}

		private String _dataSourceId;
		private String _dataSourceType;
		private List<String> _individualPKs;

	}

	private long _activitiesCount;
	private Map<String, List<Field>> _custom = new HashMap<>();
	private List<DataSourceIndividualPK> _dataSourceIndividualPKs =
		new ArrayList<>();
	private Date _dateCreated;
	private Date _dateModified;
	private Map<String, List<Field>> _demographics = new HashMap<>();
	private Map<String, Object> _embeddedResources = new HashMap<>();
	private String _id;
	private Date _lastActivityDate;

}