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

package com.liferay.commerce.user.web.internal.util;

import com.liferay.commerce.user.util.CommerceRole;
import com.liferay.commerce.user.web.internal.configuration.CommerceRoleGroupServiceConfiguration;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	configurationPid = "com.liferay.commerce.user.web.internal.configuration.CommerceRoleGroupServiceConfiguration",
	immediate = true
)
public class CommerceRoleImpl implements CommerceRole {

	@Override
	public String getRoleName() {
		return _commerceRoleGroupServiceConfiguration.roleName();
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_commerceRoleGroupServiceConfiguration =
			ConfigurableUtil.createConfigurable(
				CommerceRoleGroupServiceConfiguration.class, properties);
	}

	private volatile CommerceRoleGroupServiceConfiguration
		_commerceRoleGroupServiceConfiguration;

}