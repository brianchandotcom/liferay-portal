/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.virtual.instance.migration.common;

import java.util.Date;
import java.util.List;

/**
 * @author Luis Ortiz
 */
public class InstanceData {

	public InstanceData() {
		_date = new Date(System.currentTimeMillis());
	}

	public List<Company> getCompanies() {
		return _companies;
	}

	public Long getCompanyId() {
		return _companyId;
	}

	public Date getDate() {
		return _date;
	}

	public List<Release> getReleases() {
		return _releases;
	}

	public List<String> getTableNames() {
		return _tableNames;
	}

	public boolean isDefaultPartition() {
		return _defaultPartition;
	}

	public void setCompanies(List<Company> companies) {
		_companies = companies;
	}

	public void setCompanyId(Long companyId) {
		_companyId = companyId;
	}

	public void setDate(Date date) {
		_date = date;
	}

	public void setDefaultPartition(boolean defaultPartition) {
		_defaultPartition = defaultPartition;
	}

	public void setReleases(List<Release> releases) {
		_releases = releases;
	}

	public void setTableNames(List<String> tableNames) {
		_tableNames = tableNames;
	}

	private List<Company> _companies;
	private Long _companyId;
	private Date _date;
	private boolean _defaultPartition;
	private List<Release> _releases;
	private List<String> _tableNames;

}