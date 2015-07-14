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

package com.liferay.portal.search;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.comments.Comment;
import com.liferay.portal.kernel.comments.CommentManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.BaseSearchResultManager;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchResult;
import com.liferay.portal.kernel.search.SearchResultManager;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.service.ClassNameLocalService;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalService;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.collections.ServiceReferenceMapper;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerMap;

import java.util.Locale;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Adolfo Pérez
 */
public class SearchResultManagerImpl implements SearchResultManager {

	public SearchResultManagerImpl() {
	}

	public SearchResultManagerImpl(
		ClassNameLocalService classNameLocalService,
		DLAppLocalService dlAppLocalService,
		MBMessageLocalService mbMessageLocalService) {

		this.classNameLocalService = classNameLocalService;
		this.dlAppLocalService = dlAppLocalService;
		this.mbMessageLocalService = mbMessageLocalService;
	}

	@Override
	public SearchResult createSearchResult(Document document)
		throws PortalException {

		SearchResultManager searchResultManager = _getSearchResultManager(
			document);

		return searchResultManager.createSearchResult(document);
	}

	@Override
	public void updateSearchResult(
			SearchResult searchResult, Document document, Locale locale,
			PortletRequest portletRequest, PortletResponse portletResponse)
		throws PortalException {

		SearchResultManager searchResultManager = _getSearchResultManager(
			document);

		searchResultManager.updateSearchResult(
			searchResult, document, locale, portletRequest, portletResponse);
	}

	@BeanReference(type = ClassNameLocalService.class)
	protected ClassNameLocalService classNameLocalService;

	@BeanReference(type = DLAppLocalService.class)
	protected DLAppLocalService dlAppLocalService;

	@BeanReference(type = MBMessageLocalService.class)
	protected MBMessageLocalService mbMessageLocalService;

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
		extends BaseSearchResultManager {

		@Override
		public SearchResult createSearchResult(Document document) {
			String entryClassName = GetterUtil.getString(
				document.get(Field.ENTRY_CLASS_NAME));
			long entryClassPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			return new SearchResult(entryClassName, entryClassPK);
		}

		@Override
		public void updateSearchResult(
				SearchResult searchResult, Document document, Locale locale,
				PortletRequest portletRequest, PortletResponse portletResponse)
			throws PortalException {

			String entryClassName = GetterUtil.getString(
				document.get(Field.ENTRY_CLASS_NAME));
			long entryClassPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			searchResult.setSummary(
				getSummary(
					document, entryClassName, entryClassPK, locale,
					portletRequest, portletResponse));
		}

	}

	private class DLFileEntrySearchResultManager
		extends BaseSearchResultManager {

		@Override
		protected void addRelatedModel(
				SearchResult searchResult, Document document, Locale locale,
				PortletRequest portletRequest, PortletResponse portletResponse)
			throws PortalException {

			long entryClassPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			FileEntry fileEntry = dlAppLocalService.getFileEntry(entryClassPK);

			if (fileEntry != null) {
				Summary summary = getSummary(
					document, DLFileEntry.class.getName(),
					fileEntry.getFileEntryId(), locale, portletRequest,
					portletResponse);

				if (Validator.isNull(summary.getContent())) {
					summary.setContent(fileEntry.getTitle());
				}

				searchResult.addFileEntry(fileEntry, summary);
			}
			else {
				long classNameId = GetterUtil.getLong(
					document.get(Field.CLASS_NAME_ID));
				long classPK = GetterUtil.getLong(document.get(Field.CLASS_PK));

				ClassName className = classNameLocalService.getClassName(
					classNameId);

				Summary summary = getSummary(
					document, className.getClassName(), classPK, locale,
					portletRequest, portletResponse);

				searchResult.setSummary(summary);
			}
		}

	}

	private class MBMessageSearchResultManager extends BaseSearchResultManager {

		@Override
		protected void addRelatedModel(
				SearchResult searchResult, Document document, Locale locale,
				PortletRequest portletRequest, PortletResponse portletResponse)
			throws PortalException {

			long entryClassPK = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			MBMessage mbMessage = mbMessageLocalService.getMessage(
				entryClassPK);

			Comment comment = CommentManagerUtil.fetchComment(
				mbMessage.getMessageId());

			Summary summary = new Summary(null, mbMessage.getBody());

			summary.setEscape(false);

			searchResult.addComment(comment, summary);
		}

	}

}