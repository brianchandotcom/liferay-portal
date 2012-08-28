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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

import javax.portlet.PortletPreferences;
public class UpgradeAssetPublisher extends BaseUpgradePortletPreferences {

	protected long getIGImageFileEntryType(long companyId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		long fileEntryTypeId = 0;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select fileEntryTypeId from DLFileEntryType " +
					"where name = ? AND companyId = ?");

			ps.setString(1, DLFileEntryTypeConstants.NAME_IG_IMAGE);
			ps.setLong(2, companyId);

			rs = ps.executeQuery();

			if (rs.next()) {
				fileEntryTypeId = rs.getLong("fileEntryTypeId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return fileEntryTypeId;
	}

	@Override
	protected String[] getPortletIds() {
		return new String[] {"101_INSTANCE_%"};
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		if (!GetterUtil.getBoolean(
				PropsUtil.get(PropsKeys.DL_FILE_ENTRY_TYPE_IGIMAGE))) {

			return xml;
		}

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		String classNameIdsPreference[] = portletPreferences.getValues(
			"classNameIds", null);

		if (Validator.isNotNull(classNameIdsPreference)) {
			long igClassNameId = PortalUtil.getClassNameId(
				"com.liferay.portlet.imagegallery.model.IGImage");

			long dlClassNameId = PortalUtil.getClassNameId(
				DLFileEntry.class.getName());

			List<String> classNameIds = ListUtil.fromArray(
				classNameIdsPreference);

			int index = classNameIds.indexOf(String.valueOf(igClassNameId));

			if (index >= 0) {
				classNameIds.remove(index);

				if (!classNameIds.contains(String.valueOf(dlClassNameId))) {
					classNameIds.add(index, String.valueOf(dlClassNameId));
				}
			}

			portletPreferences.setValues(
				"classNameIds",
				classNameIds.toArray(new String[classNameIds.size()]));

			long fileEntryTypeId = getIGImageFileEntryType(companyId);

			portletPreferences.setValue(
				"anyClassTypeDLFileEntryAssetRendererFactory",
				String.valueOf(fileEntryTypeId));
			portletPreferences.setValue("classTypeIds",
				String.valueOf(fileEntryTypeId));

		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

}