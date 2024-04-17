/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.migration.validator;

import java.util.Date;
import java.util.List;

/**
 * @author Luis Ortiz
 */
public class LiferayInstance {

	public LiferayInstance() {
		_date = new Date(System.currentTimeMillis());
	}

	public List<Company> getCompanies() {
		return _companies;
	}

	public Date getDate() {
		return _date;
	}

	public long getExportedCompanyId() {
		return _exportedCompanyId;
	}

	public List<Release> getReleases() {
		return _releases;
	}

	public List<String> getTableNames() {
		return _tableNames;
	}

	public boolean isExportedCompanyDefault() {
		return _exportedCompanyDefault;
	}

	public void setCompanies(List<Company> companies) {
		_companies = companies;
	}

	public void setDate(Date date) {
		_date = date;
	}

	public void setExportedCompanyDefault(boolean exportedCompanyDefault) {
		_exportedCompanyDefault = exportedCompanyDefault;
	}

	public void setExportedCompanyId(long companyId) {
		_exportedCompanyId = companyId;
	}

	public void setReleases(List<Release> releases) {
		_releases = releases;
	}

	public void setTableNames(List<String> tableNames) {
		_tableNames = tableNames;
	}

	private List<Company> _companies;
	private Date _date;
	private boolean _exportedCompanyDefault;
	private long _exportedCompanyId;
	private List<Release> _releases;
	private List<String> _tableNames;

}