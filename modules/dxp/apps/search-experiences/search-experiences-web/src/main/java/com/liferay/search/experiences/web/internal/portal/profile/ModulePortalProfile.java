/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.web.internal.portal.profile;

import com.liferay.portal.profile.BaseDSModulePortalProfile;
import com.liferay.portal.profile.PortalProfile;
import com.liferay.search.experiences.web.internal.power.tools.portlet.SXPPowerToolsPortlet;
import com.liferay.search.experiences.web.internal.power.tools.portlet.action.ImportGooglePlacesMVCActionCommand;
import com.liferay.search.experiences.web.internal.power.tools.portlet.action.ImportWikipediaMVCActionCommand;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(enabled = false, immediate = true, service = PortalProfile.class)
public class ModulePortalProfile extends BaseDSModulePortalProfile {

	@Activate
	protected void activate(ComponentContext componentContext) {
		List<String> supportedPortalProfileNames = Collections.emptyList();

		// TODO PropsValues.SEARCH_EXPERIENCES_POWER_TOOLS_ENABLED

		if (false) {
			supportedPortalProfileNames = Arrays.asList(
				PortalProfile.PORTAL_PROFILE_NAME_CE,
				PortalProfile.PORTAL_PROFILE_NAME_DXP);
		}

		init(
			componentContext, supportedPortalProfileNames,
			ImportGooglePlacesMVCActionCommand.class.getName(),
			ImportWikipediaMVCActionCommand.class.getName(),
			SXPPowerToolsPortlet.class.getName());
	}

}