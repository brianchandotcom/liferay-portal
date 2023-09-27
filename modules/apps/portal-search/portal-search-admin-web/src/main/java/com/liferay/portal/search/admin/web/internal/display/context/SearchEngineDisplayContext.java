/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.display.context;

import com.liferay.portal.search.engine.ConnectionInformation;

import java.util.List;

/**
 * @author Adam Brandizzi
 */
public class SearchEngineDisplayContext {

	public String getClientVersionString() {
		return _clientVersionString;
	}

	public List<ConnectionInformation> getConnectionInformationList() {
		return _connectionInformationList;
	}

	public String getNodesString() {
		return _nodesString;
	}

	public String getVendorString() {
		return _vendorString;
	}

	public boolean isMissingSearchEngine() {
		return _missingSearchEngine;
	}

	public void setClientVersionString(String clientVersionString) {
		_clientVersionString = clientVersionString;
	}

	public void setConnectionInformationList(
		List<ConnectionInformation> connectionInformationList) {

		_connectionInformationList = connectionInformationList;
	}

	public void setMissingSearchEngine(boolean missingSearchEngine) {
		_missingSearchEngine = missingSearchEngine;
	}

	public void setNodesString(String nodesString) {
		_nodesString = nodesString;
	}

	public void setVendorString(String vendorString) {
		_vendorString = vendorString;
	}

	private String _clientVersionString;
	private List<ConnectionInformation> _connectionInformationList;
	private boolean _missingSearchEngine;
	private String _nodesString;
	private String _vendorString;

}