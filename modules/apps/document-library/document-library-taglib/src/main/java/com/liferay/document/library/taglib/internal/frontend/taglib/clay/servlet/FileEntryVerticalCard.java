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

package com.liferay.document.library.taglib.internal.frontend.taglib.clay.servlet;

import com.liferay.document.library.taglib.internal.display.context.RepositoryBrowserTagDisplayContext;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.dao.search.SearchContainer;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class FileEntryVerticalCard implements VerticalCard {

	public FileEntryVerticalCard(
			FileEntry fileEntry, HttpServletRequest httpServletRequest,
			RepositoryBrowserTagDisplayContext
				repositoryBrowserTagDisplayContext)
		throws PortalException {

		_fileEntry = fileEntry;
		_httpServletRequest = httpServletRequest;
		_repositoryBrowserTagDisplayContext =
			repositoryBrowserTagDisplayContext;

		_fileVersion = fileEntry.getFileVersion();
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		return _repositoryBrowserTagDisplayContext.getActionDropdownItems(
			_fileEntry);
	}

	@Override
	public String getDefaultEventHandler() {
		return "repositoryBrowserEventHandler";
	}

	@Override
	public String getImageSrc() {
		try {
			return DLURLHelperUtil.getThumbnailSrc(
				_fileEntry, _fileVersion,
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY));
		}
		catch (Exception exception) {
			return ReflectionUtil.throwException(exception);
		}
	}

	@Override
	public String getInputName() {
		try {
			SearchContainer<Object> searchContainer =
				_repositoryBrowserTagDisplayContext.getSearchContainer();

			RowChecker rowChecker = searchContainer.getRowChecker();

			if (rowChecker == null) {
				return null;
			}

			return rowChecker.getRowIds();
		}
		catch (PortalException portalException) {
			return ReflectionUtil.throwException(portalException);
		}
	}

	@Override
	public String getInputValue() {
		return String.valueOf(_fileEntry.getFileEntryId());
	}

	@Override
	public String getTitle() {
		return _fileEntry.getTitle();
	}

	private final FileEntry _fileEntry;
	private final FileVersion _fileVersion;
	private final HttpServletRequest _httpServletRequest;
	private final RepositoryBrowserTagDisplayContext
		_repositoryBrowserTagDisplayContext;

}