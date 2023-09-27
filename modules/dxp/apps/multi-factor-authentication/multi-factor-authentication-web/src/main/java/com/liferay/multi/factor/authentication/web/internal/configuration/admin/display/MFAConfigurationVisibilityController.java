/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.web.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationVisibilityController;
import com.liferay.multi.factor.authentication.web.internal.policy.MFAPolicy;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marta Medio
 */
@Component(service = ConfigurationVisibilityController.class)
public class MFAConfigurationVisibilityController
	implements ConfigurationVisibilityController {

	@Override
	public String getKey() {
		return "multi-factor-authentication";
	}

	@Override
	public boolean isVisible(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		if (_mfaPolicy.isMFAEnabled(CompanyThreadLocal.getCompanyId()) &&
			(ExtendedObjectClassDefinition.Scope.COMPANY == scope)) {

			return true;
		}

		return false;
	}

	@Reference
	private MFAPolicy _mfaPolicy;

}