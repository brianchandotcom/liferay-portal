/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class SearchResultUtil {

	public static List<SearchResult> getSearchResults(Hits hits) {
		List<SearchResult> searchResults = new ArrayList<SearchResult>();

		for (Document document : hits.getDocs()) {
			String entryClassName = GetterUtil.getString(
				document.get(Field.ENTRY_CLASS_NAME));
			long entryClassPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			try {
				String className = entryClassName;
				long classPK = entryClassPK;

				MBMessage mbMessage = null;

				if (entryClassName.equals(MBMessage.class.getName())) {
					classPK = GetterUtil.getLong(document.get(Field.CLASS_PK));
					long classNameId = GetterUtil.getLong(
						document.get(Field.CLASS_NAME_ID));

					className = PortalUtil.getClassName(classNameId);

					mbMessage = MBMessageLocalServiceUtil.getMessage(
						entryClassPK);
				}

				SearchResult searchResult = new SearchResult(
					className, classPK);

				int index = searchResults.indexOf(searchResult);

				if (index < 0) {
					searchResults.add(searchResult);
				}
				else {
					searchResult = searchResults.get(index);
				}

				if (mbMessage != null) {
					searchResult.addMBMessage(mbMessage);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Search index is stale and contains entry {" +
							entryClassPK + "}");
				}
			}
		}

		return searchResults;
	}

	private static Log _log = LogFactoryUtil.getLog(SearchResultUtil.class);

}