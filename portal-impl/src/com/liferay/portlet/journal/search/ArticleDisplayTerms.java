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

package com.liferay.portlet.journal.search;

import com.liferay.portal.kernel.dao.search.DisplayTerms;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;
import com.liferay.portlet.journal.model.JournalArticle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ArticleDisplayTerms extends DisplayTerms {

	public static final String ARTICLE_ID = "searchArticleId";

	public static final String CONTENT = "content";

	public static final String DDM_STRUCTURE_KEY = "ddmStructureKey";

	public static final String DDM_TEMPLATE_KEY = "ddmTemplateKey";

	public static final String DESCRIPTION = "description";

	public static final String DISPLAY_DATE_GT = "displayDateGT";

	public static final String DISPLAY_DATE_LT = "displayDateLT";

	public static final String FOLDER_ID = "folderId";

	public static final String GROUP_ID = "groupId";

	public static final String NAVIGATION = "navigation";

	public static final String STATUS = "status";

	public static final String TITLE = "title";

	public static final String VERSION = "version";

	public ArticleDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		articleId = ParamUtil.getString(portletRequest, ARTICLE_ID);
		content = ParamUtil.getString(portletRequest, CONTENT);
		ddmStructureKey = ParamUtil.getString(
			portletRequest, DDM_STRUCTURE_KEY);
		ddmTemplateKey = ParamUtil.getString(portletRequest, DDM_TEMPLATE_KEY);
		description = ParamUtil.getString(portletRequest, DESCRIPTION);
		folderId = ParamUtil.getLong(portletRequest, FOLDER_ID);
		navigation = ParamUtil.getString(portletRequest, NAVIGATION);
		status = ParamUtil.getInteger(portletRequest, STATUS);
		title = ParamUtil.getString(portletRequest, TITLE);
		version = ParamUtil.getDouble(portletRequest, VERSION);

		groupId = setGroupId(portletRequest);
	}

	public String getArticleId() {
		return articleId;
	}

	public String getContent() {
		return content;
	}

	public String getDDMStructureKey() {
		return ddmStructureKey;
	}

	public String getDDMTemplateKey() {
		return ddmTemplateKey;
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

	public List<Long> getFolderIds() {
		if (folderIds != null) {
			return folderIds;
		}

		List<Long> folderIds = new ArrayList<Long>();

		folderIds.add(folderId);

		return folderIds;
	}

	public long getGroupId() {
		return groupId;
	}

	public String getNavigation() {
		return navigation;
	}

	public int getStatus() {
		return status;
	}

	public String getTitle() {
		return title;
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

	public boolean isNavigationRecent() {
		if (navigation.equals("recent")) {
			return true;
		}

		return false;
	}

	public void setDisplayDateGT(Date displayDateGT) {
		this.displayDateGT = displayDateGT;
	}

	public void setDisplayDateLT(Date displayDateLT) {
		this.displayDateLT = displayDateLT;
	}

	public void setFolderIds(List<Long> folderIds) {
		this.folderIds = folderIds;
	}

	public long setGroupId(PortletRequest portletRequest) {
		groupId = ParamUtil.getLong(portletRequest, GROUP_ID);

		if (groupId != 0) {
			return groupId;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (Validator.isNotNull(ddmStructureKey) &&
			!ddmStructureKey.equals("0")) {

			DDMStructure ddmStructure = null;

			try {
				ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
					themeDisplay.getSiteGroupId(),
					PortalUtil.getClassNameId(JournalArticle.class),
					ddmStructureKey);
			}
			catch (SystemException se) {
			}

			if (ddmStructure != null) {
				return 0;
			}
		}

		if (Validator.isNotNull(ddmTemplateKey) &&
			!ddmTemplateKey.equals("0")) {

			DDMTemplate ddmTemplate = null;

			try {
				ddmTemplate = DDMTemplateLocalServiceUtil.fetchTemplate(
					themeDisplay.getSiteGroupId(),
					PortalUtil.getClassNameId(JournalArticle.class),
					ddmTemplateKey);
			}
			catch (SystemException se) {
			}

			if (ddmTemplate != null) {
				return 0;
			}
		}

		return themeDisplay.getScopeGroupId();
	}

	public void setStatus(int status) {
		this.status = status;
	}

	protected String articleId;
	protected String content;
	protected String ddmStructureKey;
	protected String ddmTemplateKey;
	protected String description;
	protected Date displayDateGT;
	protected Date displayDateLT;
	protected long folderId;
	protected List<Long> folderIds;
	protected long groupId;
	protected String navigation;
	protected int status;
	protected String title;
	protected double version;

}