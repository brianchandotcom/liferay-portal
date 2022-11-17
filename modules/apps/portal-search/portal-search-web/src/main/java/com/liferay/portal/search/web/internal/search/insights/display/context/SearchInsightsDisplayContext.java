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