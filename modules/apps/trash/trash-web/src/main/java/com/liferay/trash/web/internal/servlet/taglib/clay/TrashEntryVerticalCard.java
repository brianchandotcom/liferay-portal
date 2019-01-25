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

package com.liferay.trash.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.soy.BaseVerticalCard;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.trash.TrashRenderer;
import com.liferay.trash.model.TrashEntry;

import javax.portlet.RenderRequest;

/**
 * @author Pavel Savinov
 */
public class TrashEntryVerticalCard extends BaseVerticalCard {

	public TrashEntryVerticalCard(
		TrashEntry trashEntry, TrashRenderer trashRenderer,
		RenderRequest renderRequest, RowChecker rowChecker,
		String viewContentURL) {

		super(trashEntry, renderRequest, rowChecker);

		_trashEntry = trashEntry;
		_trashRenderer = trashRenderer;
		_viewContentURL = viewContentURL;
	}

	@Override
	public String getHref() {
		return _viewContentURL;
	}

	@Override
	public String getIcon() {
		try {
			return _trashRenderer.getIconCssClass();
		}
		catch (PortalException pe) {
			_log.error("Unable to get trash renderer icon CSS class", pe);
		}

		return "magic";
	}

	@Override
	public String getSubtitle() {
		return ResourceActionsUtil.getModelResource(
			themeDisplay.getLocale(), _trashEntry.getClassName());
	}

	@Override
	public String getTitle() {
		return _trashRenderer.getTitle(themeDisplay.getLocale());
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TrashEntryVerticalCard.class);

	private final TrashEntry _trashEntry;
	private final TrashRenderer _trashRenderer;
	private final String _viewContentURL;

}