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

package com.liferay.segments.asah.connector.internal.client.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class Individual {

	public Individual() {
	}

	public List<DataSourceIndividualPK> getDataSourceIndividualPKs() {
		return _dataSourceIndividualPKs;
	}

	public Date getDateCreated() {
		return _dateCreated;
	}

	public Date getDateModified() {
		return _dateModified;
	}

	public Map<String, List<Field>> getDemographics() {
		return _demographics;
	}

	public String getId() {
		return _id;
	}

	public List<String> getIndividualSegmentIds() {
		return _individualSegmentIds;
	}

	public void setDataSourceIndividualPKs(
		List<DataSourceIndividualPK> dataSourceIndividualPKs) {

		_dataSourceIndividualPKs = dataSourceIndividualPKs;
	}

	public void setDateCreated(Date dateCreated) {
		_dateCreated = dateCreated;
	}

	public void setDateModified(Date dateModified) {
		_dateModified = dateModified;
	}

	public void setDemographics(Map<String, List<Field>> demographics) {
		_demographics = demographics;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setIndividualSegmentIds(List<String> individualSegmentIds) {
		_individualSegmentIds = individualSegmentIds;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(11);

		sb.append("{dataSourceIndividualPKs=");
		sb.append(_dataSourceIndividualPKs);
		sb.append(", dateCreated=");
		sb.append(_dateCreated);
		sb.append(", dateModified=");
		sb.append(_dateModified);
		sb.append(", demographics=");
		sb.append(_demographics);
		sb.append(", id=");
		sb.append(_id);
		sb.append(", individualSegmentIds=");
		sb.append(_individualSegmentIds);
		sb.append("}");

		return sb.toString();
	}

	public static class DataSourceIndividualPK {

		public DataSourceIndividualPK() {
		}

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

	private List<DataSourceIndividualPK> _dataSourceIndividualPKs =
		new ArrayList<>();
	private Date _dateCreated;
	private Date _dateModified;
	private Map<String, List<Field>> _demographics = new HashMap<>();
	private String _id;
	private List<String> _individualSegmentIds = new ArrayList<>();

}