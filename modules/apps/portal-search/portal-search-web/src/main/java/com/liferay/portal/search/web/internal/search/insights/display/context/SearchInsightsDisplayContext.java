/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.search.insights.display.context;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.Serializable;

/**
 * @author Bryan Engler
 */
public class SearchInsightsDisplayContext implements Serializable {

	public String getHelpMessage() {
		return _helpMessage;
	}

	public String getRequestString() {
		if (_searchEngineVendor.equals("Solr")) {
			return _requestString;
		}

		return _getPrettyPrintedJSON(_requestString);
	}

	public String getResponseString() {
		if (_searchEngineVendor.equals("Solr")) {
			return _responseString;
		}

		return _getPrettyPrintedJSON(_responseString);
	}

	public void setHelpMessage(String helpMessage) {
		_helpMessage = helpMessage;
	}

	public void setRequestString(String queryString) {
		_requestString = queryString;
	}

	public void setResponseString(String responseString) {
		_responseString = responseString;
	}

	public void setSearchEngineVendor(String searchEngineVendor) {
		_searchEngineVendor = searchEngineVendor;
	}

	private String _getPrettyPrintedJSON(String json) {
		try {
			JSONObject jsonObject = JSONFactoryUtil.createJSONObject(json);

			return jsonObject.toString(4);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return json;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchInsightsDisplayContext.class);

	private String _helpMessage;
	private String _requestString;
	private String _responseString;
	private String _searchEngineVendor;

}