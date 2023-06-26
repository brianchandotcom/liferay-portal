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

package com.liferay.analytics.audit.header.internal.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationVisibilityController;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alvaro Saugar
 */
@Component(
	property = "configuration.pid=com.liferay.analytics.audit.header.internal.configuration.AnalyticsAuditHeaderConfiguration",
	service = ConfigurationVisibilityController.class
)
public class AuditHeaderConfigurationVisibilityController
	implements ConfigurationVisibilityController {

	@Override
	public boolean isVisible(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		return FeatureFlagManagerUtil.isEnabled("LPS-177196");
	}

}