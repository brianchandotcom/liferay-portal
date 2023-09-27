/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.admin.web.internal.display.context.builder;

import com.liferay.portal.search.admin.web.internal.display.context.SearchEngineDisplayContext;
import com.liferay.portal.search.engine.SearchEngineInformation;

/**
 * @author Adam Brandizzi
 */
public class SearchEngineDisplayContextBuilder {

	public SearchEngineDisplayContext build() {
		SearchEngineDisplayContext searchEngineDisplayContext =
			new SearchEngineDisplayContext();

		if (_searchEngineInformation != null) {
			searchEngineDisplayContext.setClientVersionString(
				_searchEngineInformation.getClientVersionString());
			searchEngineDisplayContext.setConnectionInformationList(
				_searchEngineInformation.getConnectionInformationList());
			searchEngineDisplayContext.setNodesString(
				_searchEngineInformation.getNodesString());
			searchEngineDisplayContext.setVendorString(
				_searchEngineInformation.getVendorString());
		}
		else {
			searchEngineDisplayContext.setMissingSearchEngine(true);
		}

		return searchEngineDisplayContext;
	}

	public void setSearchEngineInformation(
		SearchEngineInformation searchEngineInformation) {

		_searchEngineInformation = searchEngineInformation;
	}

	private SearchEngineInformation _searchEngineInformation;

}