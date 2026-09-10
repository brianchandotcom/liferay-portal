/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.fips.web.internal.component.enabler;

import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.security.fips.web.internal.application.list.FIPSAdminPanelApp;
import com.liferay.portal.security.fips.web.internal.portlet.FIPSAdminControlPanelEntry;
import com.liferay.portal.security.fips.web.internal.portlet.FIPSAdminPortlet;
import com.liferay.portal.security.fips.web.internal.portlet.action.EditFIPSSessionConfigurationMVCActionCommand;

import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;

/**
 * @author Lucas Miranda
 */
@Component(service = {})
public class ComponentEnabler {

	@Activate
	protected void activate(ComponentContext componentContext) {
		if (!PropsValues.FIPS_ENABLED) {
			return;
		}

		componentContext.enableComponent(
			EditFIPSSessionConfigurationMVCActionCommand.class.getName());
		componentContext.enableComponent(
			FIPSAdminControlPanelEntry.class.getName());
		componentContext.enableComponent(FIPSAdminPanelApp.class.getName());
		componentContext.enableComponent(FIPSAdminPortlet.class.getName());
	}

}