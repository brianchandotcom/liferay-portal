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

package com.liferay.commerce.product.content.search.web.internal.display.context;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.display.context.util.CPRequestHelper;
import com.liferay.commerce.product.search.CPDefinitionIndexer;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.portlet.shared.search.PortletSharedSearchResponse;

import java.util.Locale;

import javax.portlet.PortletURL;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 */
public class CPSearchResultsDisplayContext {

	public CPSearchResultsDisplayContext(
		DLAppService dlAppService, HttpServletRequest httpServletRequest,
		PortletSharedSearchResponse portletSharedSearchResponse) {

		_dlAppService = dlAppService;
		_httpServletRequest = httpServletRequest;
		_portletSharedSearchResponse = portletSharedSearchResponse;

		CPRequestHelper cpRequestHelper = new CPRequestHelper(
			httpServletRequest);

		_liferayPortletRequest = cpRequestHelper.getLiferayPortletRequest();
		_liferayPortletResponse = cpRequestHelper.getLiferayPortletResponse();

		_locale = httpServletRequest.getLocale();
	}

	public String getProductDefaultImage(
			Document document, ThemeDisplay themeDisplay)
		throws Exception {

		long fileEntryId = GetterUtil.getLong(
			document.get(
				CPDefinitionIndexer.FIELD_DEFAULT_IMAGE_FILE_ENTRY_ID));

		FileEntry fileEntry = null;

		if (fileEntryId > 0) {
			try {
				fileEntry = _dlAppService.getFileEntry(fileEntryId);

			}catch(NoSuchFileEntryException nsfee) {
			}
		}

		if (fileEntry != null) {
			return DLUtil.getThumbnailSrc(fileEntry, themeDisplay);
		}

		return null;
	}

	public String getProductFriendlyURL(String baseUrl, Document document) {
		String url = document.get(_locale, Field.URL);

		return baseUrl + CPConstants.PRODUCT_URL_SEPARATOR + url;
	}

	public SearchContainer<Document> getSearchContainer()
		throws PortalException {

		if (_searchContainer != null) {
			return _searchContainer;
		}

		PortletURL portletURL = _liferayPortletResponse.createRenderURL();

		_searchContainer = new SearchContainer<>(
			_liferayPortletRequest, portletURL, null, null);

		_searchContainer.setEmptyResultsMessage("no-products-were-found");

		_searchContainer.setTotal(_portletSharedSearchResponse.getTotalHits());
		_searchContainer.setResults(
			_portletSharedSearchResponse.getDocuments());

		return _searchContainer;
	}

	public String getTitle(Document document) {
		String title = document.get(_locale, Field.TITLE);

		if (Validator.isNull(title)) {
			title = document.get(Field.TITLE);
		}

		return title;
	}

	private final DLAppService _dlAppService;
	private final HttpServletRequest _httpServletRequest;
	private final LiferayPortletRequest _liferayPortletRequest;
	private final LiferayPortletResponse _liferayPortletResponse;
	private final Locale _locale;
	private final PortletSharedSearchResponse _portletSharedSearchResponse;
	private SearchContainer<Document> _searchContainer;

}