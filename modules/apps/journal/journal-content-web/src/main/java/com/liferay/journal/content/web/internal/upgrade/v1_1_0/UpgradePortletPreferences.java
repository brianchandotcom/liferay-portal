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

package com.liferay.journal.content.web.internal.upgrade.v1_1_0;

import com.liferay.journal.constants.JournalContentPortletKeys;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.service.GroupLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.BasePortletPreferencesUpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Objects;

import javax.portlet.PortletPreferences;

/**
 * @author Lourdes Fernández Besada
 */
public class UpgradePortletPreferences
	extends BasePortletPreferencesUpgradeProcess {

	@Override
	protected String[] getPortletIds() {
		return new String[] {
			StringPool.PERCENT + JournalContentPortletKeys.JOURNAL_CONTENT +
				StringPool.PERCENT
		};
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"UpdateProcess ", portletId, " from portletPreferences ",
					MapUtil.toString(portletPreferences.getMap())));
		}

		try {
			long groupId = GetterUtil.getLong(
				portletPreferences.getValue("groupId", "0"));

			if (groupId == 0) {
				return PortletPreferencesFactoryUtil.toXML(portletPreferences);
			}

			Group group = GroupLocalServiceUtil.fetchGroup(groupId);

			if (group == null) {
				return PortletPreferencesFactoryUtil.toXML(portletPreferences);
			}

			String lfrScopeType = portletPreferences.getValue(
				"lfrScopeType", StringPool.BLANK);

			if (group.isCompany() && Objects.equals("company", lfrScopeType)) {
				return PortletPreferencesFactoryUtil.toXML(portletPreferences);
			}

			if (group.isLayout() && Objects.equals("layout", lfrScopeType)) {
				return PortletPreferencesFactoryUtil.toXML(portletPreferences);
			}

			if (!group.isCompany() && !group.isLayout() &&
				Validator.isNull(lfrScopeType)) {

				return PortletPreferencesFactoryUtil.toXML(portletPreferences);
			}

			portletPreferences.reset("assetEntryId");
			portletPreferences.reset("articleId");
			portletPreferences.reset("ddmTemplateKey");
			portletPreferences.reset("groupId");

			portletPreferences.store();
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}

			throw exception;
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		UpgradePortletPreferences.class);

}