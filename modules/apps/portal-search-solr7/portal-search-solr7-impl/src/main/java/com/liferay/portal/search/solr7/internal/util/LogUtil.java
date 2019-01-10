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

package com.liferay.portal.search.solr7.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;

import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.common.SolrException;

/**
 * @author Michael C. Han
 */
public class LogUtil {

	public static void logSolrException(Log log, SolrException solrException) {
		if (log.isWarnEnabled()) {
			StringBundler sb = new StringBundler(5);

			sb.append("{class=");
			sb.append(solrException.getClass());
			sb.append(", message=");
			sb.append(solrException.getMessage());
			sb.append("}");

			log.warn(sb);
		}
	}

	public static void logSolrResponse(Log log, SolrResponse solrResponse) {
		if (log.isInfoEnabled()) {
			StringBundler sb = new StringBundler(5);

			sb.append("{elapsedTime=");
			sb.append(solrResponse.getElapsedTime());
			sb.append(", response=");
			sb.append(solrResponse);
			sb.append("}");

			log.info(sb);
		}
	}

}