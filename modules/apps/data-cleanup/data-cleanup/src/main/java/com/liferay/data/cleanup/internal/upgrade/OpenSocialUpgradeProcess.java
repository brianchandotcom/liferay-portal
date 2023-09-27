/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.cleanup.internal.upgrade;

import com.liferay.expando.kernel.service.ExpandoTableLocalService;

/**
 * @author Kevin Lee
 */
public class OpenSocialUpgradeProcess extends BaseUpgradeProcess {

	public OpenSocialUpgradeProcess(
		ExpandoTableLocalService expandoTableLocalService) {

		_expandoTableLocalService = expandoTableLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		removeExpandoData(_expandoTableLocalService, "OPEN_SOCIAL_DATA_");

		removePortletData(
			new String[] {"opensocial-portlet"}, null,
			new String[] {
				"1_WAR_opensocialportlet", "2_WAR_opensocialportlet",
				"3_WAR_opensocialportlet", "4_WAR_opensocialportlet"
			});

		removeServiceData(
			"OpenSocial", new String[] {"opensocial-portlet"},
			new String[] {
				"com.liferay.opensocial", "com.liferay.opensocial.model.Gadget",
				"com.liferay.opensocial.model.OAuthConsumer",
				"com.liferay.opensocial.model.OAuthToken"
			},
			new String[] {
				"OpenSocial_Gadget", "OpenSocial_OAuthConsumer",
				"OpenSocial_OAuthToken"
			});
	}

	private final ExpandoTableLocalService _expandoTableLocalService;

}