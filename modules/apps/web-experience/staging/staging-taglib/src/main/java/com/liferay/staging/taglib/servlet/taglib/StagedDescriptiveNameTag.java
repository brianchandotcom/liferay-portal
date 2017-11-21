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

package com.liferay.staging.taglib.servlet.taglib;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.jsp.JspWriter;

/**
 * @author Balázs Sáfrány-Kovalik
 */
public class StagedDescriptiveNameTag extends IncludeTag {

	@Override
	public void cleanUp() {
		_group = null;
	}

	public void setGroup(Group group) {
		_group = group;
	}

	@Override
	protected int processEndTag() throws Exception {
		JspWriter jspWriter = pageContext.getOut();

		jspWriter.write(_getStagedDescriptiveName());

		return EVAL_PAGE;
	}

	private String _getStagedDescriptiveName() {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		String stagedDescriptiveName = "";

		try {
			stagedDescriptiveName = HtmlUtil.escape(
				_group.getDescriptiveName(themeDisplay.getLocale()));

			if (_group.isStaged() && !_group.isStagedRemotely() &&
				_group.isStagingGroup()) {

				stagedDescriptiveName +=
					" (" + LanguageUtil.get(request, "staging") + ")";
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe);
			}
		}

		return stagedDescriptiveName;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagedDescriptiveNameTag.class);

	private Group _group;

}