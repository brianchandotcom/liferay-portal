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

package com.liferay.dynamic.data.lists.display.web.upgrade;

import com.liferay.dynamic.data.lists.display.web.constants.DDLDisplayPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.upgrade.BaseUpgradePortletPreferences;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ReleaseLocalService;
import com.liferay.portal.upgrade.util.UpgradePortletId;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletPreferences;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDLDisplayWebUpgrade.class)
public class DDLDisplayWebUpgrade {

	@Reference(unbind = "-")
	protected void setReleaseLocalService(
		ReleaseLocalService releaseLocalService) {

		_releaseLocalService = releaseLocalService;
	}

	@Reference(target = "(original.bean=*)", unbind = "-")
	protected void setServletContext(ServletContext servletContext) {
	}

	@Activate
	protected void upgrade() throws PortalException {
		List<UpgradeProcess> upgradeProcesses = new ArrayList<>();

		upgradeProcesses.add(new DDLDisplayWebUpgradePorletId());
		upgradeProcesses.add(new DDLDisplayWebUpgradePorletPreferences());

		_releaseLocalService.updateRelease(
			"com.liferay.dynamic.data.lists.display.web", upgradeProcesses, 1,
			0, false);
	}

	private ReleaseLocalService _releaseLocalService;

	private static class DDLDisplayWebUpgradePorletId extends UpgradePortletId {

		@Override
		protected String[][] getRenamePortletIdsArray() {
			return new String[][] {
				new String[] {
					"169", DDLDisplayPortletKeys.DDL_DISPLAY
				}
			};
		}

	}

	private static class DDLDisplayWebUpgradePorletPreferences
		extends BaseUpgradePortletPreferences {

		@Override
		protected String[] getPortletIds() {
			return new String[] {
				DDLDisplayPortletKeys.DDL_DISPLAY + StringPool.PERCENT
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

			portletPreferences.setValue(
				"displayStyle", _DDL_DISPLAY_DEFAULT_TEMPLATE);
			portletPreferences.setValue("displayStyleGroupId", "0");

			return PortletPreferencesFactoryUtil.toXML(portletPreferences);
		}

		private static final String _DDL_DISPLAY_DEFAULT_TEMPLATE =
			"ddmTemplate_DYNAMIC-DATA-LISTS-DISPLAY-DEFAULT-FTL";

	}

}