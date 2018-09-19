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

package com.liferay.journal.web.internal.util;

import com.liferay.journal.util.JournalHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tom Wang
 */
@Component(immediate = true, service = {})
public class JournalHelperUtil {

	public static String diffHtml(
			long groupId, String articleId, double sourceVersion,
			double targetVersion, String languageId,
			PortletRequestModel portletRequestModel, ThemeDisplay themeDisplay)
		throws Exception {

		return _journalHelper.diffHtml(
			groupId, articleId, sourceVersion, targetVersion, languageId,
			portletRequestModel, themeDisplay);
	}

	public static String getAbsolutePath(
			PortletRequest portletRequest, long folderId)
		throws PortalException {

		return _journalHelper.getAbsolutePath(portletRequest, folderId);
	}

	public static int getRestrictionType(long folderId) {
		return _journalHelper.getRestrictionType(folderId);
	}

	@Reference(unbind = "-")
	protected void setJournalHelper(JournalHelper journalHelper) {
		_journalHelper = journalHelper;
	}

	private static JournalHelper _journalHelper;

}