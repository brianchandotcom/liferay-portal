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

package com.liferay.layout.page.template.admin.web.internal.display.context;

import com.liferay.asset.kernel.model.ClassType;
import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class SelectDisplayPageMasterLayoutDisplayContext {

	public SelectDisplayPageMasterLayoutDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;

		_infoDisplayContributorTracker =
			(InfoDisplayContributorTracker)httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR_TRACKER);
		_themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	public JSONArray getMappingTypesJSONArray() {
		JSONArray mappingTypesJSONArray = JSONFactoryUtil.createJSONArray();

		for (InfoDisplayContributor<?> infoDisplayContributor :
				_infoDisplayContributorTracker.getInfoDisplayContributors()) {

			JSONObject jsonObject = JSONUtil.put(
				"id",
				String.valueOf(
					PortalUtil.getClassNameId(
						infoDisplayContributor.getClassName()))
			).put(
				"label",
				infoDisplayContributor.getLabel(_themeDisplay.getLocale())
			).put(
				"subtypes", _getMappingSubtypesJSONArray(infoDisplayContributor)
			);

			mappingTypesJSONArray.put(jsonObject);
		}

		return mappingTypesJSONArray;
	}

	public List<LayoutPageTemplateEntry> getMasterLayoutPageTemplateEntries() {
		List<LayoutPageTemplateEntry> masterLayoutPageTemplateEntries =
			new ArrayList<>();

		LayoutPageTemplateEntry layoutPageTemplateEntry =
			LayoutPageTemplateEntryLocalServiceUtil.
				createLayoutPageTemplateEntry(0);

		layoutPageTemplateEntry.setName(
			LanguageUtil.get(_httpServletRequest, "blank"));
		layoutPageTemplateEntry.setStatus(WorkflowConstants.STATUS_APPROVED);

		masterLayoutPageTemplateEntries.add(layoutPageTemplateEntry);

		masterLayoutPageTemplateEntries.addAll(
			LayoutPageTemplateEntryServiceUtil.getLayoutPageTemplateEntries(
				_themeDisplay.getScopeGroupId(),
				LayoutPageTemplateEntryTypeConstants.TYPE_MASTER_LAYOUT,
				WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null));

		return masterLayoutPageTemplateEntries;
	}

	private JSONArray _getMappingSubtypesJSONArray(
		InfoDisplayContributor<?> infoDisplayContributor) {

		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		try {
			List<ClassType> classTypes = infoDisplayContributor.getClassTypes(
				_themeDisplay.getScopeGroupId(), _themeDisplay.getLocale());

			for (ClassType classType : classTypes) {
				JSONObject jsonObject = JSONUtil.put(
					"id", String.valueOf(classType.getClassTypeId())
				).put(
					"label", classType.getName()
				);

				jsonArray.put(jsonObject);
			}
		}
		catch (PortalException portalException) {
			_log.error("Unable to get mapping subtypes", portalException);
		}

		return jsonArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SelectDisplayPageMasterLayoutDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private final InfoDisplayContributorTracker _infoDisplayContributorTracker;
	private final ThemeDisplay _themeDisplay;

}