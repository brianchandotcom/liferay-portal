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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.staging.StagingConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Andrew Betts
 */
public class VerifyGroupPostStartup extends VerifyProcess {

	protected void doVerify() throws Exception {
		verifyStagedGroup();
	}

	protected void verifyStagedGroup() throws Exception {
		List<Group> groups = GroupLocalServiceUtil.getLiveGroups();

		for (Group group : groups) {
			if (!group.hasStagingGroup()) {
				continue;
			}

			UnicodeProperties typeSettingsProperties =
				group.getTypeSettingsProperties();

			verifyStagingTypeSettingsProperties(typeSettingsProperties);

			GroupLocalServiceUtil.updateGroup(
				group.getGroupId(), typeSettingsProperties.toString());
		}
	}

	protected void verifyStagingTypeSettingsProperties(
		UnicodeProperties typeSettingsProperties) {

		Set<String> keys = typeSettingsProperties.keySet();

		Iterator<String> iterator = keys.iterator();

		while (iterator.hasNext()) {
			String key = iterator.next();

			if (!key.contains(StagingConstants.STAGED_PORTLET)) {
				continue;
			}

			String portletId = StringUtil.replace(
				key, StagingConstants.STAGED_PORTLET, StringPool.BLANK);

			Portlet portlet = PortletLocalServiceUtil.getPortletById(portletId);

			if (portlet == null) {
				if (_log.isInfoEnabled()) {
					_log.info("Removing type settings property " + key);
				}

				iterator.remove();

				continue;
			}

			PortletDataHandler portletDataHandler =
				portlet.getPortletDataHandlerInstance();

			if ((portletDataHandler == null) ||
				!portletDataHandler.isDataSiteLevel()) {

				if (_log.isInfoEnabled()) {
					_log.info("Removing type settings property " + key);
				}

				iterator.remove();
			}
		}
	}

	Log _log = LogFactoryUtil.getLog(VerifyGroupPostStartup.class);

}