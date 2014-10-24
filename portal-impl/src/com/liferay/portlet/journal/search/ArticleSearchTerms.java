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

import com.liferay.portal.kernel.dao.search.DAOParamUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Date;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class ArticleSearchTerms extends ArticleDisplayTerms {

	public ArticleSearchTerms(PortletRequest portletRequest) {
		super(portletRequest);

		articleId = DAOParamUtil.getString(portletRequest, ARTICLE_ID);
		content = DAOParamUtil.getString(portletRequest, CONTENT);
		ddmStructureKey = DAOParamUtil.getString(
			portletRequest, DDM_STRUCTURE_KEY);
		ddmTemplateKey = DAOParamUtil.getString(
			portletRequest, DDM_TEMPLATE_KEY);
		description = DAOParamUtil.getString(portletRequest, DESCRIPTION);
		status = ParamUtil.getInteger(portletRequest, STATUS);
		title = DAOParamUtil.getString(portletRequest, TITLE);
		version = ParamUtil.getDouble(portletRequest, VERSION);

		groupId = setGroupId(portletRequest);
	}

	public Date getReviewDate() {
		if (status == WorkflowConstants.STATUS_PENDING) {
			return new Date();
		}
		else {
			return null;
		}
	}

	public Double getVersionObj() {
		if (version == 0) {
			return null;
		}
		else {
			return new Double(version);
		}
	}

	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}

	public void setDDMStructureKey(String ddmStructureKey) {
		this.ddmStructureKey = ddmStructureKey;
	}

	public void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	@Override
	public void setStatus(int status) {
		this.status = status;
	}

	public void setVersion(double version) {
		this.version = version;
	}

}