/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.journal.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import java.util.Date;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ArticleDisplayTerms extends DisplayTerms {

	public static final String ARTICLE_ID = "searchArticleId";

	public static final String CONTENT = "content";

	public static final String DESCRIPTION = "description";

	public static final String DISPLAY_DATE_GT = "displayDateGT";

	public static final String DISPLAY_DATE_LT = "displayDateLT";

	public static final String FOLDER_ID = "folderId";

	public static final String GROUP_ID = "groupId";

	public static final String STATUS = "status";

	public static final String STRUCTURE_ID = "structureId";

	public static final String TEMPLATE_ID = "templateId";

	public static final String TITLE = "title";

	public static final String TYPE = "type";

	public static final String VERSION = "version";

	public ArticleDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		articleId = ParamUtil.getString(portletRequest, ARTICLE_ID);
		content = ParamUtil.getString(portletRequest, CONTENT);
		description = ParamUtil.getString(portletRequest, DESCRIPTION);
		folderId = ParamUtil.getLong(portletRequest, FOLDER_ID);
		status = ParamUtil.getString(portletRequest, STATUS);
		structureId = ParamUtil.getString(portletRequest, STRUCTURE_ID);
		templateId = ParamUtil.getString(portletRequest, TEMPLATE_ID);
		title = ParamUtil.getString(portletRequest, TITLE);
		type = ParamUtil.getString(portletRequest, TYPE);
		version = ParamUtil.getDouble(portletRequest, VERSION);

		groupId = setGroupId(portletRequest);
	}

	public String getArticleId() {
		return articleId;
	}

	public String getContent() {
		return content;
	}

	public String getDescription() {
		return description;
	}

	public Date getDisplayDateGT() {
		return displayDateGT;
	}

	public Date getDisplayDateLT() {
		return displayDateLT;
	}

	public long getFolderId() {
		return folderId;
	}

	public long getGroupId() {
		return groupId;
	}

	public String getStatus() {
		return status;
	}

	public String getStructureId() {
		return structureId;
	}

	public String getTemplateId() {
		return templateId;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return type;
	}

	public double getVersion() {
		return version;
	}

	public String getVersionString() {
		if (version != 0) {
			return String.valueOf(version);
		}
		else {
			return StringPool.BLANK;
		}
	}

	public void setDisplayDateGT(Date displayDateGT) {
		this.displayDateGT = displayDateGT;
	}

	public void setDisplayDateLT(Date displayDateLT) {
		this.displayDateLT = displayDateLT;
	}

	public long setGroupId(PortletRequest portletRequest) {
		groupId = ParamUtil.getLong(portletRequest, GROUP_ID);

		if ((groupId == 0) && Validator.isNull(structureId) &&
			Validator.isNull(templateId)) {

			ThemeDisplay themeDisplay =
				(ThemeDisplay)portletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			groupId = themeDisplay.getScopeGroupId();
		}

		return groupId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	protected String articleId;
	protected String content;
	protected String description;
	protected Date displayDateGT;
	protected Date displayDateLT;
	protected long folderId;
	protected long groupId;
	protected String status;
	protected String structureId;
	protected String templateId;
	protected String title;
	protected String type;
	protected double version;

}