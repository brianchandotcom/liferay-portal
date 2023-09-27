/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.my.subscriptions.web.internal.portal.profile;

import com.liferay.my.subscriptions.web.internal.application.list.MySubscriptionPanelApp;
import com.liferay.my.subscriptions.web.internal.portlet.MySubscriptionsPortlet;
import com.liferay.my.subscriptions.web.internal.upgrade.registry.MySubscriptionsWebUpgradeStepRegistrator;
import com.liferay.portal.profile.BaseDSModulePortalProfile;
import com.liferay.portal.profile.PortalProfile;

import java.util.Arrays;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Shuyang Zhou
 */
@Component(service = PortalProfile.class)
public class ModulePortalProfile extends BaseDSModulePortalProfile {

	@Activate
	protected void activate(ComponentContext componentContext) {
		init(
			componentContext,
			Arrays.asList(
				PortalProfile.PORTAL_PROFILE_NAME_CE,
				PortalProfile.PORTAL_PROFILE_NAME_DXP),
			MySubscriptionPanelApp.class.getName(),
			MySubscriptionsPortlet.class.getName(),
			MySubscriptionsWebUpgradeStepRegistrator.class.getName());
	}

}