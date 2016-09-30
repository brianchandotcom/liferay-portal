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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.document.library.kernel.model.DLFileEntryTypeConstants;
import com.liferay.portal.kernel.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.util.PropsValues;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * @author Juan Fernández
 * @author Sergio Sanchez
 * @author Brian Wing Shun Chan
 */
public class UpgradeAssetPublisher extends BaseUpgradePortletPreferences {

	protected long getIGImageFileEntryType(long companyId) throws Exception {
		try (PreparedStatement ps = connection.prepareStatement(
				"select fileEntryTypeId from DLFileEntryType where name = ? " +
					"AND companyId = ?")) {

			ps.setString(1, DLFileEntryTypeConstants.NAME_IG_IMAGE);
			ps.setLong(2, companyId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getLong("fileEntryTypeId");
				}

				return 0;
			}
		}
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

		if (!PropsValues.DL_FILE_ENTRY_TYPE_IG_IMAGE_AUTO_CREATE_ON_UPGRADE) {
			return xml;
		}

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		String[] classNameIds = portletPreferences.getValues(
			"classNameIds", null);

		if (ArrayUtil.isEmpty(classNameIds)) {
			return PortletPreferencesFactoryUtil.toXML(portletPreferences);
		}

		long dlFileEntryClassNameId = PortalUtil.getClassNameId(
			"com.liferay.portlet.documentlibrary.model.DLFileEntry");
		long igImageClassNameId = PortalUtil.getClassNameId(
			"com.liferay.portlet.imagegallery.model.IGImage");

		List<String> classNameIdsList = ListUtil.fromArray(classNameIds);

		int index = classNameIdsList.indexOf(
			String.valueOf(igImageClassNameId));

		if (index >= 0) {
			classNameIdsList.remove(index);

			if (!classNameIdsList.contains(
					String.valueOf(dlFileEntryClassNameId))) {

				classNameIdsList.add(
					index, String.valueOf(dlFileEntryClassNameId));
			}

			portletPreferences.setValues(
				"classNameIds",
				classNameIdsList.toArray(new String[classNameIdsList.size()]));

			if (classNameIdsList.size() == 1) {
				long fileEntryTypeId = getIGImageFileEntryType(companyId);

				portletPreferences.setValue(
					"anyClassTypeDLFileEntryAssetRendererFactory",
					String.valueOf(fileEntryTypeId));
				portletPreferences.setValue(
					"classTypeIds", String.valueOf(fileEntryTypeId));
			}
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

}