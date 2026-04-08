/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.admin.web.internal.model;

import com.liferay.frontend.data.set.SystemFDSEntry;

/**
 * @author Miguel Arroyo
 */
public class SystemFDSEntryInfo implements SystemFDSEntry {

	public SystemFDSEntryInfo(
		String additionalAPIURLParameters, int defaultItemsPerPage,
		String description, boolean imported, String name,
		String restApplication, String restEndpoint, String restSchema,
		String symbol, String title) {

		_additionalAPIURLParameters = additionalAPIURLParameters;
		_defaultItemsPerPage = defaultItemsPerPage;
		_description = description;
		_imported = imported;
		_name = name;
		_restApplication = restApplication;
		_restEndpoint = restEndpoint;
		_restSchema = restSchema;
		_symbol = symbol;
		_title = title;
	}

	public String getAdditionalAPIURLParameters() {
		return _additionalAPIURLParameters;
	}

	public int getDefaultItemsPerPage() {
		return _defaultItemsPerPage;
	}

	public String getDescription() {
		return _description;
	}

	public String getName() {
		return _name;
	}

	public String getRESTApplication() {
		return _restApplication;
	}

	public String getRESTEndpoint() {
		return _restEndpoint;
	}

	public String getRESTSchema() {
		return _restSchema;
	}

	public String getSymbol() {
		return _symbol;
	}

	public String getTitle() {
		return _title;
	}

	public boolean isImported() {
		return _imported;
	}

	private final String _additionalAPIURLParameters;
	private final int _defaultItemsPerPage;
	private final String _description;
	private final boolean _imported;
	private final String _name;
	private final String _restApplication;
	private final String _restEndpoint;
	private final String _restSchema;
	private final String _symbol;
	private final String _title;

}