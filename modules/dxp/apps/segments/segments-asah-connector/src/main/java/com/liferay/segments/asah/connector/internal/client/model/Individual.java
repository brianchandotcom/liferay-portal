/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.model;

import com.liferay.petra.string.StringBundler;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Matthew Kong
 */
public class Individual {

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
		return StringBundler.concat(
			"{dataSourceIndividualPKs=", _dataSourceIndividualPKs,
			", dateCreated=", _dateCreated, ", dateModified=", _dateModified,
			", demographics=", _demographics, ", id=", _id,
			", individualSegmentIds=", _individualSegmentIds, "}");
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

	private List<DataSourceIndividualPK> _dataSourceIndividualPKs =
		new ArrayList<>();
	private Date _dateCreated;
	private Date _dateModified;
	private Map<String, List<Field>> _demographics = new HashMap<>();
	private String _id;
	private List<String> _individualSegmentIds = new ArrayList<>();

}