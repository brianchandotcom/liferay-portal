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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryTypeConstants;

import edu.emory.mathcs.backport.java.util.Arrays;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.LinkedList;
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
				PropsUtil.get("dl.file.entry.type.igimage"))) {

			return xml;
		}

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		String classNameIds[] = portletPreferences.getValues(
			"classNameIds", null);

		if (Validator.isNotNull(classNameIds)) {
			long igClassNameId = PortalUtil.getClassNameId(
				"com.liferay.portlet.imagegallery.model.IGImage");

			long dlClassNameId = PortalUtil.getClassNameId(
				DLFileEntry.class.getName());

			List<String> list = new LinkedList<String>(
				Arrays.asList(classNameIds));

			int index = list.indexOf(String.valueOf(igClassNameId));

			if (index >= 0) {
				list.remove(index);

				if (!list.contains(String.valueOf(dlClassNameId))) {
					list.add(index, String.valueOf(dlClassNameId));
				}
			}

			portletPreferences.setValues(
				"classNameIds", list.toArray(new String[list.size()]));

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