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

package com.liferay.data.cleanup.internal.upgrade;

/**
 * @author Kevin Lee
 */
public class OpenSocialUpgradeProcess extends BaseUpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
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

}