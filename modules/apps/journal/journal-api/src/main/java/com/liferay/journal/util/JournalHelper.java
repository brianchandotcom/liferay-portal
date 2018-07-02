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

package com.liferay.journal.util;

import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.portlet.PortletRequestModel;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.theme.ThemeDisplay;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletRequest;

/**
 * @author Tom Wang
 */
public interface JournalHelper {

	public String diffHtml(
			long groupId, String articleId, double sourceVersion,
			double targetVersion, String languageId,
			PortletRequestModel portletRequestModel, ThemeDisplay themeDisplay)
		throws Exception;

	public String getAbsolutePath(PortletRequest portletRequest, long folderId)
		throws PortalException;

	public Layout getArticleLayout(String layoutUuid, long groupId);

	/**
	 * @deprecated As of Judson (7.1.x), with no direct replacement
	 */
	@Deprecated
	public List<JournalArticle> getArticles(Hits hits) throws PortalException;

	public int getRestrictionType(long folderId);

	public String getTemplateScript(
			long groupId, String ddmTemplateKey, Map<String, String> tokens,
			String languageId)
		throws PortalException;

}