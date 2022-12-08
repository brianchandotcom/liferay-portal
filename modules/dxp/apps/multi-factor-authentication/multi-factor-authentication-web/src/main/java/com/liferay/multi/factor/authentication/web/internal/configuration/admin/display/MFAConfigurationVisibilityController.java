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