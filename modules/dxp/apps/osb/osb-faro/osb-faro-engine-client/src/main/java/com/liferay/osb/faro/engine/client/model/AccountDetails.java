/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.osb.faro.engine.client.model;

import java.util.Date;
import java.util.List;

/**
 * @author Caio Pinheiro
 */
public class AccountDetails {

	public List<Field> getFields() {
		return _fields;
	}

	public void setFields(List<Field> fields) {
		_fields = fields;
	}

	public static class Field {

		public Long getDataSourceId() {
			return _dataSourceId;
		}

		public Date getDataSourceModifiedDate() {
			return _dataSourceModifiedDate;
		}

		public String getDataSourceName() {
			return _dataSourceName;
		}

		public String getName() {
			return _name;
		}

		public String getValue() {
			return _value;
		}

		public void setDataSourceId(Long dataSourceId) {
			_dataSourceId = dataSourceId;
		}

		public void setDataSourceModifiedDate(Date dataSourceModifiedDate) {
			_dataSourceModifiedDate = dataSourceModifiedDate;
		}

		public void setDataSourceName(String dataSourceName) {
			_dataSourceName = dataSourceName;
		}

		public void setName(String name) {
			_name = name;
		}

		public void setValue(String value) {
			_value = value;
		}

		private Long _dataSourceId;
		private Date _dataSourceModifiedDate;
		private String _dataSourceName;
		private String _name;
		private String _value;

	}

	private List<Field> _fields;

}