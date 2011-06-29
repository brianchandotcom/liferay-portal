/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class TemplateDisplayTerms extends DisplayTerms {

	public static final String DESCRIPTION = "description";

	public static final String GROUP_IDS = "groupIds";

	public static final String NAME = "name";

	public static final String STRUCTURE_ID = "structureId";

	public static final String TEMPLATE_ID = "searchTemplateId";

	public TemplateDisplayTerms(PortletRequest portletRequest) {
		super(portletRequest);

		description = ParamUtil.getString(portletRequest, DESCRIPTION);
		groupIds = setGroupIds(portletRequest);
		name = ParamUtil.getString(portletRequest, NAME);
		structureId = ParamUtil.getString(portletRequest, STRUCTURE_ID);
		templateId = ParamUtil.getString(portletRequest, TEMPLATE_ID);
	}

	public String getDescription() {
		return description;
	}

	public long getGroupId() {
		if (groupIds.length == 1) {
			return groupIds[0];
		}
		else {
			return 0;
		}
	}

	public long[] getGroupIds() {
		return groupIds;
	}

	public String getGroupIds(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String strutsAction = ParamUtil.getString(
			portletRequest, "struts_action");

		long groupId = ParamUtil.getLong(portletRequest, "groupId");

		StringBundler sb = new StringBundler();

		if (groupId > 0) {
			sb.append(groupId);
		}
		else {
			sb.append(themeDisplay.getScopeGroupId());
		}

		if (strutsAction.equalsIgnoreCase("/journal/select_template")) {
			sb.append(StringPool.COMMA);
			sb.append(themeDisplay.getCompanyGroupId());
		}

		return sb.toString();
	}

	public String getName() {
		return name;
	}

	public String getStructureId() {
		return structureId;
	}

	public String getTemplateId() {
		return templateId;
	}

	protected long[] setGroupIds(PortletRequest portletRequest) {
		String groupIdsString = ParamUtil.getString(
			portletRequest, GROUP_IDS, getGroupIds(portletRequest));

		return StringUtil.split(groupIdsString, 0L);
	}

	protected String description;
	protected long[] groupIds;
	protected String name;
	protected String structureId;
	protected String templateId;

}