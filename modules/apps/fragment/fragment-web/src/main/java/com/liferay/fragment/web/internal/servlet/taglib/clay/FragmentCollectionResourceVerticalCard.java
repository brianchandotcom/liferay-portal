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

package com.liferay.fragment.web.internal.servlet.taglib.clay;

import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.fragment.web.internal.constants.FragmentWebKeys;
import com.liferay.fragment.web.internal.servlet.taglib.util.FragmentCollectionResourceActionDropdownItemsProvider;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.PortalUtil;

import java.util.Date;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Jürgen Kappler
 */
public class FragmentCollectionResourceVerticalCard implements VerticalCard {

	public FragmentCollectionResourceVerticalCard(
		FileEntry fileEntry, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker) {

		_fileEntry = fileEntry;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_rowChecker = rowChecker;
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		FragmentCollectionResourceActionDropdownItemsProvider
			fragmentCollectionResourceActionDropdownItemsProvider =
				new FragmentCollectionResourceActionDropdownItemsProvider(
					_fileEntry, _renderRequest, _renderResponse);

		try {
			return fragmentCollectionResourceActionDropdownItemsProvider.
				getActionDropdownItems();
		}
		catch (Exception e) {
		}

		return null;
	}

	@Override
	public String getDefaultEventHandler() {
		return FragmentWebKeys.
			FRAGMENT_COLLECTION_RESOURCE_DROPDOWN_DEFAULT_EVENT_HANDLER;
	}

	@Override
	public String getIcon() {
		return "document-image";
	}

	@Override
	public String getImageSrc() {
		String imageSrc = StringPool.BLANK;

		try {
			imageSrc = DLUtil.getPreviewURL(
				_fileEntry, _fileEntry.getFileVersion(), null, StringPool.BLANK,
				false, false);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return imageSrc;
	}

	@Override
	public String getInputName() {
		return _rowChecker.getRowIds();
	}

	@Override
	public String getInputValue() {
		return String.valueOf(_fileEntry.getPrimaryKeyObj());
	}

	@Override
	public String getSubtitle() {
		HttpServletRequest httpServletRequest =
			PortalUtil.getHttpServletRequest(_renderRequest);

		Date modifiedDate = _fileEntry.getModifiedDate();

		String modifiedDateDescription = LanguageUtil.getTimeDescription(
			httpServletRequest,
			System.currentTimeMillis() - modifiedDate.getTime(), true);

		return LanguageUtil.format(
			httpServletRequest, "x-ago", modifiedDateDescription);
	}

	@Override
	public String getTitle() {
		return _fileEntry.getTitle();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentCollectionResourceVerticalCard.class);

	private final FileEntry _fileEntry;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final RowChecker _rowChecker;

}