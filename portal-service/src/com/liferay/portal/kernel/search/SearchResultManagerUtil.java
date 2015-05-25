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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

/**
 * @author Adolfo Pérez
 */
public class SearchResultManagerUtil {

	public static SearchResult createSearchResult(Document document)
		throws PortalException {

		return _instance._createSearchResult(document);
	}

	private SearchResultManagerUtil() {
		_serviceTrackerMap.open();
	}

	private SearchResult _createSearchResult(Document document)
		throws PortalException {

		SearchResultManager searchResultManager = _getSearchResultManager(
			document);

		return searchResultManager.createSearchResult(document);
	}

	private SearchResultManager _getSearchResultManager(Document document) {
		String entryClassName = GetterUtil.getString(
			document.get(Field.ENTRY_CLASS_NAME));

		SearchResultManager searchResultManager = _serviceTrackerMap.getService(
			entryClassName);

		if (searchResultManager == null) {
			if (entryClassName.equals(DLFileEntryConstants.getClassName())) {
				return new DLFileEntrySearchResultManager();
			}
			else if (entryClassName.equals(MBMessage.class.getName())) {
				return new MBMessageSearchResultManager();
			}
			else {
				return new DefaultSearchResultManagerImpl();
			}
		}

		return searchResultManager;
	}

	private static final SearchResultManagerUtil _instance =
		new SearchResultManagerUtil();

	private final ServiceTrackerMap<String, SearchResultManager>
		_serviceTrackerMap = ServiceTrackerCollections.singleValueMap(
			SearchResultManager.class, "(model.className=*)",
			new ServiceReferenceMapper<String, SearchResultManager>() {

				@Override
				public void map(
					ServiceReference<SearchResultManager> serviceReference,
					Emitter<String> emitter) {

					Object modelClassName = serviceReference.getProperty(
						"model.className");

					emitter.emit((String)modelClassName);
				}

			});

	private static class DefaultSearchResultManagerImpl
		implements SearchResultManager {

		@Override
		public SearchResult createSearchResult(Document document) {
			String entryClassName = GetterUtil.getString(
				document.get(Field.ENTRY_CLASS_NAME));
			long entryClassPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			return new SearchResult(entryClassName, entryClassPK);
		}

	}

	private static class DLFileEntrySearchResultManager
		extends BaseSearchResultManager {
	}

	private static class MBMessageSearchResultManager
		extends BaseSearchResultManager {
	}

}