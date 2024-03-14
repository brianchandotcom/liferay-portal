/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.script.management.groovy.script.use;

/**
 * @author Feliphe Marinho
 */
public class GroovyScriptUse {

	public GroovyScriptUse(
		String companyId, String sourceName, String sourceURL) {

		_companyWebId = companyId;

		_sourceName = sourceName;
		_sourceURL = sourceURL;
	}

	public String getCompanyWebId() {
		return _companyWebId;
	}

	public String getSourceName() {
		return _sourceName;
	}

	public String getSourceURL() {
		return _sourceURL;
	}

	public void setCompanyWebId(String companyWebId) {
		_companyWebId = companyWebId;
	}

	public void setSourceName(String sourceName) {
		_sourceName = sourceName;
	}

	public void setSourceURL(String sourceURL) {
		_sourceURL = sourceURL;
	}

	private String _companyWebId;
	private String _sourceName;
	private String _sourceURL;

}