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

package com.liferay.portal.upgrade.v6_0_3;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.portlet.PortletPreferences;

/**
 * @author Julio Camarero
 */
public class UpgradeAssetPublisher extends BaseUpgradePortletPreferences {

	protected String[] getAssetEntryXmls(String[] assetEntryXmls)
		throws Exception {

		String[] newAssetEntryXmls = new String[assetEntryXmls.length];

		for (int i = 0; i < assetEntryXmls.length; i++) {
			String assetEntryXml = assetEntryXmls[i];

			Document document = SAXReaderUtil.read(assetEntryXml);

			Element rootElement = document.getRootElement();

			upgradeToAssetEntryUuidElement(rootElement);

			newAssetEntryXmls[i] = document.formattedString(StringPool.BLANK);
		}

		return newAssetEntryXmls;
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

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		String selectionStyle = portletPreferences.getValue(
			"selection-style", null);

		if (Validator.isNotNull(selectionStyle) &&
			!selectionStyle.equals("dynamic")) {

			String[] assetEntryXmls = portletPreferences.getValues(
				"asset-entry-xml", new String[0]);

			String[] newAssetEntryXmls = getAssetEntryXmls(assetEntryXmls);

			portletPreferences.setValues("asset-entry-xml", newAssetEntryXmls);
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

	protected void upgradeToAssetEntryUuidElement(Element rootElement)
		throws Exception {

		Element assetEntryIdElement = rootElement.element("assetEntryId");

		long assetEntryId = GetterUtil.getLong(assetEntryIdElement.getText());

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select classUuid from AssetEntry where entryId = ?");

			ps.setLong(1, assetEntryId);

			rs = ps.executeQuery();

			if (rs.next()) {
				String classUuid = rs.getString("classUuid");

				Element assetEntryUuidElement = rootElement.addElement(
					"assetEntryUuid");

				assetEntryUuidElement.addText(classUuid);

				rootElement.remove(assetEntryIdElement);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}