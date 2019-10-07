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

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.web.internal.util.BlogsEntryUtil;
import com.liferay.frontend.taglib.clay.servlet.taglib.soy.VerticalCard;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Date;
import java.util.ResourceBundle;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class BlogsEntryItemSelectorVerticalCard implements VerticalCard {

	public BlogsEntryItemSelectorVerticalCard(
		BlogsEntry blogsEntry, RenderRequest renderRequest,
		ResourceBundle resourceBundle) {

		_blogsEntry = blogsEntry;
		_renderRequest = renderRequest;
		_resourceBundle = resourceBundle;
	}

	@Override
	public String getAspectRatioCssClasses() {
		return "aspect-ratio-item-center-middle " +
			"aspect-ratio-item-vertical-fluid";
	}

	@Override
	public String getElementClasses() {
		return "card-interactive card-interactive-secondary";
	}

	@Override
	public String getIcon() {
		return "blogs";
	}

	@Override
	public String getImageSrc() {
		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_renderRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			String coverImageURL = _blogsEntry.getCoverImageURL(themeDisplay);

			if (Validator.isNull(coverImageURL)) {
				return _blogsEntry.getSmallImageURL(themeDisplay);
			}

			return coverImageURL;
		}
		catch (PortalException pe) {
			return ReflectionUtil.throwException(pe);
		}
	}

	@Override
	public String getSubtitle() {
		Date modifiedDate = _blogsEntry.getModifiedDate();

		String modifiedDateDescription = LanguageUtil.getTimeDescription(
			PortalUtil.getHttpServletRequest(_renderRequest),
			System.currentTimeMillis() - modifiedDate.getTime(), true);

		return LanguageUtil.format(
			_resourceBundle, "x-ago-by-x",
			new Object[] {
				modifiedDateDescription,
				HtmlUtil.escape(_blogsEntry.getUserName())
			});
	}

	@Override
	public String getTitle() {
		return BlogsEntryUtil.getDisplayTitle(_resourceBundle, _blogsEntry);
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

	private final BlogsEntry _blogsEntry;
	private final RenderRequest _renderRequest;
	private final ResourceBundle _resourceBundle;

}