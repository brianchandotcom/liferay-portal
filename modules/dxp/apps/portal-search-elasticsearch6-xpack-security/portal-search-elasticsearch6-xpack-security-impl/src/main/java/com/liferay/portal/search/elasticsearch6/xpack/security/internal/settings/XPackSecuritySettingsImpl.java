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

package com.liferay.portal.search.elasticsearch6.xpack.security.internal.settings;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.search.elasticsearch6.settings.XPackSecuritySettings;
import com.liferay.portal.search.elasticsearch6.xpack.security.internal.configuration.XPackSecurityConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Bryan Engler
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch6.xpack.security.internal.configuration.XPackSecurityConfiguration",
	immediate = true, property = "operation.mode=REMOTE",
	service = XPackSecuritySettings.class
)
public class XPackSecuritySettingsImpl implements XPackSecuritySettings {

	@Override
	public boolean requiresXPackSecurity() {
		return xPackSecurityConfiguration.requiresAuthentication();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		xPackSecurityConfiguration = ConfigurableUtil.createConfigurable(
			XPackSecurityConfiguration.class, properties);
	}

	protected volatile XPackSecurityConfiguration xPackSecurityConfiguration;

}