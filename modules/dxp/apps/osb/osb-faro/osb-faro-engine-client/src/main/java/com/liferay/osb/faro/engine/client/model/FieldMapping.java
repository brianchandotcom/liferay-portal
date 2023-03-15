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
public class FieldMapping {

	public Author getAuthor() {
		return _author;
	}

	public String getContext() {
		return _context;
	}

	public String getDataSourceFieldName(String dataSourceUUID) {
		return _dataSourceFieldNames.get(dataSourceUUID);
	}

	public Map<String, String> getDataSourceFieldNames() {
		return _dataSourceFieldNames;
	}

	public List<DataSourceFieldName> getDataSources() {
		return _dataSources;
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

	public String getDisplayName() {
		return _displayName;
	}

	public String getDisplayType() {
		return _displayType;
	}

	public String getFieldName() {
		return _fieldName;
	}

	public String getFieldType() {
		return _fieldType;
	}

	public String getId() {
		return _id;
	}

	public String getOwnerType() {
		return _ownerType;
	}

	public Strategy getStrategy() {
		return _strategy;
	}

	public void setAuthor(Author author) {
		_author = author;
	}

	public void setContext(String context) {
		_context = context;
	}

	public void setDataSourceFieldNames(
		Map<String, String> dataSourceFieldNames) {

		_dataSourceFieldNames = dataSourceFieldNames;
	}

	public void setDataSources(List<DataSourceFieldName> dataSources) {
		_dataSources = dataSources;
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

	public void setDisplayName(String displayName) {
		_displayName = displayName;
	}

	public void setDisplayType(String displayType) {
		_displayType = displayType;
	}

	public void setFieldName(String fieldName) {
		_fieldName = fieldName;
	}

	public void setFieldType(String fieldType) {
		_fieldType = fieldType;
	}

	public void setId(String id) {
		_id = id;
	}

	public void setOwnerType(String ownerType) {
		_ownerType = ownerType;
	}

	public void setStrategy(Strategy strategy) {
		_strategy = strategy;
	}

	public static class DataSourceFieldName {

		public String getDataSourceFieldName() {
			return _dataSourceFieldName;
		}

		public String getDataSourceName() {
			return _dataSourceName;
		}

		public void setDataSourceFieldName(String dataSourceFieldName) {
			_dataSourceFieldName = dataSourceFieldName;
		}

		public void setDataSourceName(String dataSourceName) {
			_dataSourceName = dataSourceName;
		}

		private String _dataSourceFieldName;
		private String _dataSourceName;

	}

	public static class Strategy {

		public static final Strategy DEFAULT = new Strategy(
			Key.MOST_RECENT.toString(), new HashMap<>());

		public Strategy() {
		}

		public Strategy(String key, Map<String, String> configuration) {
			_key = key;
			_configuration = configuration;
		}

		public Map<String, String> getConfiguration() {
			return _configuration;
		}

		public String getKey() {
			return _key;
		}

		public void setConfiguration(Map<String, String> configuration) {
			_configuration = configuration;
		}

		public void setKey(String key) {
			_key = key;
		}

		public enum Key {

			MOST_RECENT, PRIORITY_DATASOURCE

		}

		private Map<String, String> _configuration;
		private String _key;

	}

	private Author _author;
	private String _context;
	private Map<String, String> _dataSourceFieldNames = new HashMap<>();
	private List<DataSourceFieldName> _dataSources;
	private Date _dateCreated;
	private Date _dateModified;
	private String _displayName;
	private String _displayType;
	private String _fieldName;
	private String _fieldType;
	private String _id;
	private String _ownerType;
	private Strategy _strategy;

}