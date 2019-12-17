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