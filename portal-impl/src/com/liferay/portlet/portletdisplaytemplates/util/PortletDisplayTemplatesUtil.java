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

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Juan Fernández
 */
public class PortletDisplayTemplatesUtil {

	public static DDMTemplate getPortletDisplayDDMTemplate(
		long groupId, String displayStyle) {

		DDMTemplate portletDisplayDDMTemplate = null;

		try {
			Group group = GroupLocalServiceUtil.getGroup(groupId);
			Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
				group.getCompanyId());

			if (displayStyle.startsWith("ddmTemplate_")) {
				String portletDisplayDDMTemplateUuid = displayStyle.substring(
					"ddmTemplate_".length());

				if (Validator.isNotNull(portletDisplayDDMTemplateUuid)) {
					try {
						portletDisplayDDMTemplate =
							DDMTemplateLocalServiceUtil.
								getDDMTemplateByUuidAndGroupId(
									portletDisplayDDMTemplateUuid, groupId);
					}
					catch (NoSuchTemplateException nste1) {
						try {
							portletDisplayDDMTemplate =
								DDMTemplateLocalServiceUtil.
									getDDMTemplateByUuidAndGroupId(
										portletDisplayDDMTemplateUuid,
										companyGroup.getGroupId());
						}
						catch (NoSuchTemplateException nste2) {}
					}
				}
			}
		}
		catch (Exception e) {}

		return portletDisplayDDMTemplate;
	}

	public static long getPortletDisplayDDMTemplateGroupId(
		ThemeDisplay themeDisplay) {

		long portletDisplayDDMTemplateGroupId = themeDisplay.getScopeGroupId();

		try {
			Group scopeGroup = themeDisplay.getScopeGroup();

			if (scopeGroup.hasStagingGroup()) {
				Group stagingGroup = GroupLocalServiceUtil.getStagingGroup(
					scopeGroup.getGroupId());

				boolean staged = GetterUtil.getBoolean(
					scopeGroup.getTypeSettingsProperty(
						StagingConstants.STAGED_PORTLET +
							PortletKeys.PORTLET_DISPLAY_TEMPLATES));

				if (staged) {
					portletDisplayDDMTemplateGroupId =
						stagingGroup.getGroupId();
				}
			}
			else if (scopeGroup.getLiveGroupId() > 0) {
				Group liveGroup = scopeGroup.getLiveGroup();

				boolean staged = GetterUtil.getBoolean(
					liveGroup.getTypeSettingsProperty(
						StagingConstants.STAGED_PORTLET +
							PortletKeys.PORTLET_DISPLAY_TEMPLATES));

				if (!staged) {
					portletDisplayDDMTemplateGroupId = liveGroup.getGroupId();
				}
			}
		}
		catch (Exception e) {
		}

		return portletDisplayDDMTemplateGroupId;
	}

	public static String renderDDMTemplate(
			RenderRequest renderRequest, RenderResponse renderResponse,
			long ddmTemplateId, List entries)
		throws Exception {

		Map<String, Object> contextObjects = new HashMap<String, Object>();

		return renderDDMTemplate(
			renderRequest, renderResponse, ddmTemplateId, entries,
			contextObjects);
	}

	public static String renderDDMTemplate(
			RenderRequest renderRequest, RenderResponse renderResponse,
			long ddmTemplateId, List entries,
			Map<String, Object> contextObjects)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		DDMTemplate ddmTemplate = DDMTemplateLocalServiceUtil.getTemplate(
			ddmTemplateId);

		contextObjects.put(PortletDisplayTemplatesConstants.ENTRIES, entries);

		if (entries.size() == 1) {
			contextObjects.put(
				PortletDisplayTemplatesConstants.ENTRY, entries.get(0));
		}

		contextObjects.put(
			PortletDisplayTemplatesConstants.DDM_TEMPLATE_ID, ddmTemplateId);

		contextObjects.put(
			PortletDisplayTemplatesConstants.LOCALE, renderRequest.getLocale());

		contextObjects.put(
			PortletDisplayTemplatesConstants.RENDER_REQUEST, renderRequest);

		contextObjects.put(
			PortletDisplayTemplatesConstants.RENDER_RESPONSE, renderResponse);

		contextObjects.put(
			PortletDisplayTemplatesConstants.THEME_DISPLAY, themeDisplay);

		_transformer = new DDLTransformer();

		return _transformer.transform(
			themeDisplay, contextObjects, ddmTemplate.getScript(),
			ddmTemplate.getLanguage());
	}

	private static Transformer _transformer = new DDLTransformer();

}