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

package com.liferay.layout.type.controller.portlet.internal.display.context;

import com.liferay.info.constants.InfoDisplayWebKeys;
import com.liferay.info.display.contributor.InfoDisplayContributor;
import com.liferay.info.display.contributor.InfoDisplayContributorTracker;
import com.liferay.info.display.contributor.InfoDisplayObjectProvider;
import com.liferay.layout.page.template.model.LayoutPageTemplateEntry;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateEntryLocalServiceUtil;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalServiceUtil;
import com.liferay.layout.page.template.util.LayoutDataConverter;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.service.LayoutLocalServiceUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class PortletLayoutDisplayContext {

	public PortletLayoutDisplayContext(HttpServletRequest httpServletRequest) {
		_httpServletRequest = httpServletRequest;

		_infoDisplayContributorTracker =
			(InfoDisplayContributorTracker)httpServletRequest.getAttribute(
				InfoDisplayWebKeys.INFO_DISPLAY_CONTRIBUTOR_TRACKER);
	}

	public String getBackgroundImage(JSONObject rowConfigJSONObject)
		throws PortalException {

		if (rowConfigJSONObject == null) {
			return StringPool.BLANK;
		}

		JSONObject backgroundImageJSONObject =
			rowConfigJSONObject.getJSONObject("backgroundImage");

		if (backgroundImageJSONObject == null) {
			return rowConfigJSONObject.getString(
				"backgroundImage", StringPool.BLANK);
		}

		String fieldId = backgroundImageJSONObject.getString("fieldId");

		if (Validator.isNotNull(fieldId)) {
			long classNameId = backgroundImageJSONObject.getLong("classNameId");
			long classPK = backgroundImageJSONObject.getLong("classPK");

			if ((classNameId != 0L) && (classPK != 0L)) {
				InfoDisplayContributor infoDisplayContributor =
					_infoDisplayContributorTracker.getInfoDisplayContributor(
						PortalUtil.getClassName(classNameId));

				if (infoDisplayContributor != null) {
					InfoDisplayObjectProvider infoDisplayObjectProvider =
						infoDisplayContributor.getInfoDisplayObjectProvider(
							classPK);

					if (infoDisplayObjectProvider != null) {
						Object object =
							infoDisplayContributor.getInfoDisplayFieldValue(
								infoDisplayObjectProvider.getDisplayObject(),
								fieldId, LocaleUtil.getDefault());

						if (object instanceof JSONObject) {
							JSONObject fieldValueJSONObject =
								(JSONObject)object;

							return fieldValueJSONObject.getString(
								"url", StringPool.BLANK);
						}
					}
				}
			}
		}

		String backgroundImageURL = backgroundImageJSONObject.getString("url");

		if (Validator.isNotNull(backgroundImageURL)) {
			return backgroundImageURL;
		}

		return StringPool.BLANK;
	}

	public JSONObject getDataJSONObject() {
		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)_httpServletRequest.getAttribute(
					WebKeys.THEME_DISPLAY);

			Layout layout = LayoutLocalServiceUtil.fetchLayout(
				themeDisplay.getPlid());

			LayoutPageTemplateEntry masterLayoutPageTemplateEntry =
				LayoutPageTemplateEntryLocalServiceUtil.
					fetchLayoutPageTemplateEntryByPlid(
						layout.getMasterLayoutPlid());

			if (masterLayoutPageTemplateEntry == null) {
				JSONObject defaultStructureJSONObject =
					_getDefaultStructureJSONObject();

				String data = LayoutDataConverter.convert(
					defaultStructureJSONObject.toString());

				return JSONFactoryUtil.createJSONObject(data);
			}

			LayoutPageTemplateStructure masterLayoutPageTemplateStructure =
				LayoutPageTemplateStructureLocalServiceUtil.
					fetchLayoutPageTemplateStructure(
						masterLayoutPageTemplateEntry.getGroupId(),
						PortalUtil.getClassNameId(Layout.class),
						masterLayoutPageTemplateEntry.getPlid());

			String data = masterLayoutPageTemplateStructure.getData(
				SegmentsExperienceConstants.ID_DEFAULT);

			if (Validator.isNull(data)) {
				return _getDefaultStructureJSONObject();
			}

			return JSONFactoryUtil.createJSONObject(data);
		}
		catch (Exception exception) {
			_log.error("Unable to get JSON object", exception);

			return null;
		}
	}

	private JSONObject _getDefaultStructureJSONObject() {
		return JSONUtil.put(
			"structure",
			JSONUtil.putAll(
				JSONUtil.put(
					"columns",
					JSONUtil.putAll(
						JSONUtil.put(
							"fragmentEntryLinkIds", JSONUtil.put("drop-zone")
						).put(
							"size", 12
						)))));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortletLayoutDisplayContext.class);

	private final HttpServletRequest _httpServletRequest;
	private final InfoDisplayContributorTracker _infoDisplayContributorTracker;

}