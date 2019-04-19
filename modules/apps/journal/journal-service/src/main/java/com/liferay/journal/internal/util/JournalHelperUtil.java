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

package com.liferay.journal.internal.util;

import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.util.JournalHelper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.search.Hits;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Tom Wang
 */
@Component(immediate = true, service = {})
public class JournalHelperUtil {

	public static String getAbsolutePath(
			PortletRequest portletRequest, long folderId)
		throws PortalException {

		return _journalHelper.getAbsolutePath(portletRequest, folderId);
	}

	public static Layout getArticleLayout(String layoutUuid, long groupId) {
		return _journalHelper.getArticleLayout(layoutUuid, groupId);
	}

	public static List<JournalArticle> getArticles(Hits hits)
		throws PortalException {

		return _journalHelper.getArticles(hits);
	}

	public static int getRestrictionType(long folderId) {
		return _journalHelper.getRestrictionType(folderId);
	}

	public static String getTemplateScript(
			long groupId, String ddmTemplateKey, Map<String, String> tokens,
			String languageId)
		throws PortalException {

		return _journalHelper.getTemplateScript(
			groupId, ddmTemplateKey, tokens, languageId);
	}

	@Reference(unbind = "-")
	public void setJournalHelper(JournalHelper journalHelper) {
		_journalHelper = journalHelper;
	}

	private static JournalHelper _journalHelper;

}