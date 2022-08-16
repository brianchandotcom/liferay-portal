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

package com.liferay.roles.admin.web.internal.servlet.taglib.clay;

import com.liferay.frontend.taglib.clay.servlet.taglib.BaseVerticalCard;
import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.HtmlUtil;

import javax.portlet.RenderRequest;

/**
 * @author Eudaldo Alonso
 */
public class GroupVerticalCard extends BaseVerticalCard {

	public GroupVerticalCard(
		Group group, RenderRequest renderRequest, RowChecker rowChecker) {

		super(group, renderRequest, rowChecker);

		_group = group;
	}

	@Override
	public String getIcon() {
		return "sites";
	}

	@Override
	public String getSubtitle() {
		return LanguageUtil.get(
			themeDisplay.getLocale(), _group.getTypeLabel());
	}

	@Override
	public String getTitle() {
		try {
			return HtmlUtil.escape(
				_group.getDescriptiveName(themeDisplay.getLocale()));
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return HtmlUtil.escape(_group.getName(themeDisplay.getLocale()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		GroupVerticalCard.class);

	private final Group _group;

}