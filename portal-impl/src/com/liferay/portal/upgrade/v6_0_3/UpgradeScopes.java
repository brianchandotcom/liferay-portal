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

import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.verify.model.VerifiableUUIDModel;
import com.liferay.portal.verify.VerifyUUID;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.portlet.PortletPreferences;

/**
 * @author Julio Camarero
 */
public class UpgradeScopes extends BaseUpgradePortletPreferences {

	@Override
	protected void doUpgrade() throws Exception {
		super.doUpgrade();

		VerifyUUID.verify(
			new VerifiableUUIDModel() {

				@Override
				public String getPrimaryKeyColumnName() {
					return "plid";
				}

				@Override
				public String getTableName() {
					return "Layout";
				}

			});
	}

	@Override
	protected String getUpdatePortletPreferencesWhereClause() {
		return "preferences like '%lfr-scope-layout-id%'";
	}

	@Override
	protected String upgradePreferences(
			long companyId, long ownerId, int ownerType, long plid,
			String portletId, String xml)
		throws Exception {

		PortletPreferences portletPreferences =
			PortletPreferencesFactoryUtil.fromXML(
				companyId, ownerId, ownerType, plid, portletId, xml);

		long linkToLayoutId = GetterUtil.getLong(
			portletPreferences.getValue("lfr-scope-layout-id", null));

		if (linkToLayoutId > 0) {
			String uuid = getLayoutUuid(plid, linkToLayoutId);

			if (uuid != null) {
				portletPreferences.setValue("lfr-scope-layout-uuid", uuid);
			}

			portletPreferences.reset("lfr-scope-layout-id");
		}

		return PortletPreferencesFactoryUtil.toXML(portletPreferences);
	}

}