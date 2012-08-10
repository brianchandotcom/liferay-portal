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

package com.liferay.portlet.portletdisplaytemplates.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.staging.StagingConstants;
import com.liferay.portal.kernel.templateparser.Transformer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.dynamicdatalists.util.DDLTransformer;
import com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Juan Fernández
 * @author Brian Wing Shun Chan
 */
public class PortletDisplayTemplatesUtil {

	public static DDMTemplate fetchDDMTemplate(
		long groupId, String displayStyle) {

		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);

			Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
				group.getCompanyId());

			if (!displayStyle.startsWith("ddmTemplate_")) {
				return null;
			}

			String uuid = displayStyle.substring(12);

			if (Validator.isNull(uuid)) {
				return null;
			}

			try {
				return
					DDMTemplateLocalServiceUtil.getDDMTemplateByUuidAndGroupId(
						uuid, groupId);
			}
			catch (NoSuchTemplateException nste) {
			}

			try {
				return
					DDMTemplateLocalServiceUtil.getDDMTemplateByUuidAndGroupId(
						uuid, companyGroup.getGroupId());
			}
			catch (NoSuchTemplateException nste) {
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return null;
	}

	public static long getDDMTemplateGroupId(ThemeDisplay themeDisplay) {
		try {
			Group scopeGroup = themeDisplay.getScopeGroup();

			if (scopeGroup.hasStagingGroup()) {
				Group stagingGroup = GroupLocalServiceUtil.getStagingGroup(
					scopeGroup.getGroupId());

				if (GetterUtil.getBoolean(
						scopeGroup.getTypeSettingsProperty(
							StagingConstants.STAGED_PORTLET +
								PortletKeys.PORTLET_DISPLAY_TEMPLATES))) {

					return stagingGroup.getGroupId();
				}
			}
			else if (scopeGroup.getLiveGroupId() > 0) {
				Group liveGroup = scopeGroup.getLiveGroup();

				if (!GetterUtil.getBoolean(
						liveGroup.getTypeSettingsProperty(
							StagingConstants.STAGED_PORTLET +
								PortletKeys.PORTLET_DISPLAY_TEMPLATES))) {

					return liveGroup.getGroupId();
				}
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}
		}

		return themeDisplay.getScopeGroupId();
	}

	public static long getPortletDisplayTemplateDDMTemplateId(
		ThemeDisplay themeDisplay, String displayStyle) {

		long portletDisplayDDMTemplateId = 0;

		long portletDisplayDDMTemplateGroupId = getDDMTemplateGroupId(
			themeDisplay);

		if (displayStyle.startsWith("ddmTemplate_")) {
			DDMTemplate portletDisplayDDMTemplate =
				PortletDisplayTemplatesUtil.fetchDDMTemplate(
					portletDisplayDDMTemplateGroupId, displayStyle);

			if (portletDisplayDDMTemplate != null) {
				portletDisplayDDMTemplateId =
					portletDisplayDDMTemplate.getTemplateId();
			}
		}

		return portletDisplayDDMTemplateId;
	}

	public static String renderDDMTemplate(
			RenderRequest renderRequest, RenderResponse renderResponse,
			long ddmTemplateId, List<?> entries)
		throws Exception {

		Map<String, Object> contextObjects = new HashMap<String, Object>();

		return renderDDMTemplate(
			renderRequest, renderResponse, ddmTemplateId, entries,
			contextObjects);
	}

	public static String renderDDMTemplate(
			RenderRequest renderRequest, RenderResponse renderResponse,
			long ddmTemplateId, List<?> entries,
			Map<String, Object> contextObjects)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
			ddmTemplateId);

		contextObjects.put(
			PortletDisplayTemplatesConstants.DDM_TEMPLATE_ID, ddmTemplateId);
		contextObjects.put(PortletDisplayTemplatesConstants.ENTRIES, entries);

		if (entries.size() == 1) {
			contextObjects.put(
				PortletDisplayTemplatesConstants.ENTRY, entries.get(0));
		}

		contextObjects.put(
			PortletDisplayTemplatesConstants.LOCALE, renderRequest.getLocale());
		contextObjects.put(
			PortletDisplayTemplatesConstants.RENDER_REQUEST, renderRequest);
		contextObjects.put(
			PortletDisplayTemplatesConstants.RENDER_RESPONSE, renderResponse);
		contextObjects.put(
			PortletDisplayTemplatesConstants.THEME_DISPLAY, themeDisplay);

		contextObjects.putAll(_getPortletPreferences(renderRequest));

		return _transformer.transform(
			themeDisplay, contextObjects, ddmTemplate.getScript(),
			ddmTemplate.getLanguage());
	}

	private static Map<String, Object> _getPortletPreferences(
		RenderRequest renderRequest) {

		Map<String, Object> contextObjects = new HashMap<String, Object>();

		PortletPreferences portletPreferences = renderRequest.getPreferences();

		Map<String, String[]> map = portletPreferences.getMap();

		for (Map.Entry<String, String[]> entry : map.entrySet()) {
			String[] values = entry.getValue();

			if ((values == null) || (values.length == 0)) {
				continue;
			}

			String value = values[0];

			if (value == null) {
				continue;
			}

			contextObjects.put(entry.getKey(), value);
		}

		return contextObjects;
	}

	private static Log _log = LogFactoryUtil.getLog(
		PortletDisplayTemplatesUtil.class);

	private static Transformer _transformer = new DDLTransformer();

}