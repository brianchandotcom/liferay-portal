/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.db.partition.migration.validator;

import java.util.Objects;

/**
 * @author Luis Ortiz
 */
public class Company {

	public Company() {
	}

	public Company(
		long companyId, String companyName, String virtualHostname,
		String webId) {

		_companyId = companyId;
		_companyName = companyName;
		_virtualHostname = virtualHostname;
		_webId = webId;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof Company)) {
			return false;
		}

		Company company = (Company)object;

		if ((_companyId == company._companyId) &&
			_companyName.equals(company._companyName) &&
			_virtualHostname.equals(company._virtualHostname) &&
			_webId.equals(company._webId)) {

			return true;
		}

		return false;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public String getCompanyName() {
		return _companyName;
	}

	public String getVirtualHostname() {
		return _virtualHostname;
	}

	public String getWebId() {
		return _webId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_companyId, _companyName, _virtualHostname, _webId);
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public void setCompanyName(String companyName) {
		_companyName = companyName;
	}

	public void setVirtualHostname(String virtualHostname) {
		_virtualHostname = virtualHostname;
	}

	public void setWebId(String webId) {
		_webId = webId;
	}

	private long _companyId;
	private String _companyName;
	private String _virtualHostname;
	private String _webId;

}