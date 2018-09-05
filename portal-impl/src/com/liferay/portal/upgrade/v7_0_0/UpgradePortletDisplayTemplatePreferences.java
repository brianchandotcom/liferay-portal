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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.portletdisplaytemplate.PortletDisplayTemplateManager;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.Validator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

import javax.portlet.PortletPreferences;

/**
 * @author Eduardo Garcia
 */
public class UpgradePortletDisplayTemplatePreferences
	extends BaseUpgradePortletPreferences {

	protected long getCompanyGroupId(long companyId) throws Exception {
		Long companyGroupId = _companyGroupIds.get(companyId);

		if (companyGroupId != null) {
			return companyGroupId;
		}

		try (PreparedStatement ps = connection.prepareStatement(
				"select groupId from Group_ where classNameId = ? and " +
					"classPK = ?")) {

			ps.setLong(1, _COMPANY_CLASS_NAME_ID);
			ps.setLong(2, companyId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					companyGroupId = rs.getLong("groupId");
				}
				else {
					companyGroupId = 0L;
				}

				_companyGroupIds.put(companyId, companyGroupId);

				return companyGroupId;
			}
		}
	}

	protected ObjectValuePair<Long, String> getTemplateGroupAndKey(
			long displayStyleGroupId, String displayStyle)
		throws Exception {

		String uuid = displayStyle.substring(DISPLAY_STYLE_PREFIX_6_2.length());

		try (PreparedStatement ps = connection.prepareStatement(
				"select groupId, templateKey from DDMTemplate where (groupId " +
					"= ? or groupId = ?) and uuid_ = ?")) {

			ps.setLong(1, displayStyleGroupId);
			ps.setLong(2, _companyGroupId);
			ps.setString(3, uuid);

			ObjectValuePair<Long, String> objectValuePair = null;

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long groupId = rs.getLong("groupId");

					objectValuePair = new ObjectValuePair<>(
						groupId, rs.getString("templateKey"));

					if (groupId == displayStyleGroupId) {
						return objectValuePair;
					}
				}
			}

			return objectValuePair;
		}
	}

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             #getTemplateGroupAndKey(long, String)}
	 */
	@Deprecated
	protected String getTemplateKey(
			long displayStyleGroupId, String displayStyle)
		throws Exception {

		ObjectValuePair<Long, String> objectValuePair = getTemplateGroupAndKey(
			displayStyleGroupId, displayStyle);

		if (objectValuePair == null) {
			return null;
		}

		return objectValuePair.getValue();
	}

	@Override
	protected String getUpdatePortletPreferencesWhereClause() {
		return UPDATE_PORTLET_PREFERENCES_WHERE_CLAUSE;
	}

	protected void upgradeDisplayStyle(PortletPreferences portletPreferences)
		throws Exception {

		String displayStyle = GetterUtil.getString(
			portletPreferences.getValue("displayStyle", null));

		if (Validator.isNull(displayStyle) ||
			!displayStyle.startsWith(DISPLAY_STYLE_PREFIX_6_2)) {

			return;
		}

		long displayStyleGroupId = GetterUtil.getLong(
			portletPreferences.getValue("displayStyleGroupId", null));

		ObjectValuePair<Long, String> objectValuePair = getTemplateGroupAndKey(
			displayStyleGroupId, displayStyle);

		if (objectValuePair != null) {
			Long key = objectValuePair.getKey();

			portletPreferences.setValue("displayStyleGroupId", key.toString());

			portletPreferences.setValue(
				"displayStyle",
				PortletDisplayTemplateManager.DISPLAY_STYLE_PREFIX +
					objectValuePair.getValue());
		}
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		_companyGroupId = getCompanyGroupId(companyId);

		upgradeDisplayStyle(portletPreferences);

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected static final String DISPLAY_STYLE_PREFIX_6_2 = "ddmTemplate_";

	protected static final String UPDATE_PORTLET_PREFERENCES_WHERE_CLAUSE =
		"(preferences like '%" + DISPLAY_STYLE_PREFIX_6_2 + "%')";

	private static final Long _COMPANY_CLASS_NAME_ID =
		PortalUtil.getClassNameId("com.liferay.portal.kernel.model.Company");

	private long _companyGroupId = 0L;
	private final Map<Long, Long> _companyGroupIds = new HashMap<>();

}