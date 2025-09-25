/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.helper;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.search.elasticsearch7.configuration.RESTClientLoggerLevel;

import org.elasticsearch.action.ActionResponse;
import org.elasticsearch.action.bulk.BulkResponse;

/**
 * @author Michael C. Han
 */
public class SearchLogHelperUtil {

	public static void logActionResponse(
		Log log, ActionResponse actionResponse) {

		_searchLogHelper.logActionResponse(log, actionResponse);
	}

	public static void logActionResponse(Log log, BulkResponse bulkResponse) {
		_searchLogHelper.logActionResponse(log, bulkResponse);
	}

	public static void setRESTClientLoggerLevel(
		RESTClientLoggerLevel restClientLoggerLevel) {

		_searchLogHelper.setRESTClientLoggerLevel(restClientLoggerLevel);
	}

	public static void setSearchLogHelper(SearchLogHelper searchLogHelper) {
		_searchLogHelper = searchLogHelper;
	}

	private static SearchLogHelper _searchLogHelper = new SearchLogHelperImpl();

}