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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.dynamicdatamapping.NoSuchTemplateException;
import com.liferay.portlet.dynamicdatamapping.model.DDMTemplate;
import com.liferay.portlet.dynamicdatamapping.service.DDMTemplateLocalServiceUtil;

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

}