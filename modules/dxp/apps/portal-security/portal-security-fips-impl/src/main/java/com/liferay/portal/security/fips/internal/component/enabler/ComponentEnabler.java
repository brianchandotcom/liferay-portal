/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.fips.internal.component.enabler;

import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.security.fips.internal.configuration.persistence.listener.FIPSSessionConfigurationModelListener;
import com.liferay.portal.security.fips.internal.crypto.officer.model.listener.PasswordPolicyModelListener;
import com.liferay.portal.security.fips.internal.crypto.officer.model.listener.RoleModelListener;
import com.liferay.portal.security.fips.internal.events.LoginPostAction;
import com.liferay.portal.security.fips.internal.instance.lifecycle.FIPSPortalInstanceLifecycleListener;
import com.liferay.portal.security.fips.internal.security.auth.CryptoOfficerAuthFailure;
import com.liferay.portal.security.fips.internal.servlet.filter.FIPSSessionFilter;

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
			CryptoOfficerAuthFailure.class.getName());
		componentContext.enableComponent(
			FIPSPortalInstanceLifecycleListener.class.getName());
		componentContext.enableComponent(
			FIPSSessionConfigurationModelListener.class.getName());
		componentContext.enableComponent(FIPSSessionFilter.class.getName());
		componentContext.enableComponent(LoginPostAction.class.getName());
		componentContext.enableComponent(
			PasswordPolicyModelListener.class.getName());
		componentContext.enableComponent(RoleModelListener.class.getName());
	}

}