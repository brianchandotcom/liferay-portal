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

package com.liferay.blogs.web.internal.servlet.taglib.clay;

import com.liferay.blogs.web.internal.servlet.taglib.util.BlogsEntryImageActionDropdownItemsProvider;
import com.liferay.document.library.util.DLURLHelperUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.LabelItemList;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.capabilities.WorkflowCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Adolfo Pérez
 */
public class BlogsEntryImageVerticalCard implements VerticalCard {

	public BlogsEntryImageVerticalCard(
		FileEntry fileEntry, RenderRequest renderRequest,
		RenderResponse renderResponse, RowChecker rowChecker) {

		_fileEntry = fileEntry;
		_renderRequest = renderRequest;
		_renderResponse = renderResponse;
		_rowChecker = rowChecker;

		_httpServletRequest = PortalUtil.getHttpServletRequest(_renderRequest);

		_themeDisplay = (ThemeDisplay)_httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	@Override
	public List<DropdownItem> getActionDropdownItems() {
		BlogsEntryImageActionDropdownItemsProvider
			blogsEntryImageActionDropdownItemsProvider =
				new BlogsEntryImageActionDropdownItemsProvider(
					_fileEntry, _renderRequest, _renderResponse);

		return blogsEntryImageActionDropdownItemsProvider.
			getActionDropdownItems();
	}

	@Override
	public String getIcon() {
		return "documents-and-media";
	}

	@Override
	public String getImageSrc() {
		try {
			return DLURLHelperUtil.getThumbnailSrc(_fileEntry, _themeDisplay);
		}
		catch (Exception e) {
			return ReflectionUtil.throwException(e);
		}
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
	public List<LabelItem> getLabels() {
		if (!_fileEntry.isRepositoryCapabilityProvided(
				WorkflowCapability.class)) {

			return Collections.emptyList();
		}

		WorkflowCapability workflowCapability =
			_fileEntry.getRepositoryCapability(WorkflowCapability.class);

		return new LabelItemList() {
			{
				add(
					labelItem -> labelItem.setStatus(
						workflowCapability.getStatus(_fileEntry)));
			}
		};
	}

	@Override
	public String getStickerImageSrc() {
		try {
			User user = UserLocalServiceUtil.fetchUser(_fileEntry.getUserId());

			if (user == null) {
				return StringPool.BLANK;
			}

			return user.getPortraitURL(_themeDisplay);
		}
		catch (PortalException pe) {
			return ReflectionUtil.throwException(pe);
		}
	}

	@Override
	public String getStickerLabel() {
		User user = UserLocalServiceUtil.fetchUser(_fileEntry.getUserId());

		if (user == null) {
			return StringPool.BLANK;
		}

		return user.getInitials();
	}

	@Override
	public String getStickerShape() {
		return "circle";
	}

	@Override
	public String getSubtitle() {
		Date modifiedDate = _fileEntry.getModifiedDate();

		String modifiedDateDescription = LanguageUtil.getTimeDescription(
			PortalUtil.getHttpServletRequest(_renderRequest),
			System.currentTimeMillis() - modifiedDate.getTime(), true);

		return LanguageUtil.format(
			_httpServletRequest, "x-ago-by-x",
			new Object[] {
				modifiedDateDescription,
				HtmlUtil.escape(_fileEntry.getUserName())
			});
	}

	@Override
	public String getTitle() {
		return _fileEntry.getTitle();
	}

	@Override
	public boolean isDisabled() {
		return _rowChecker.isDisabled(_fileEntry);
	}

	@Override
	public boolean isSelected() {
		return _rowChecker.isChecked(_fileEntry);
	}

	private final FileEntry _fileEntry;
	private final HttpServletRequest _httpServletRequest;
	private final RenderRequest _renderRequest;
	private final RenderResponse _renderResponse;
	private final RowChecker _rowChecker;
	private final ThemeDisplay _themeDisplay;

}