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

package com.liferay.portal.sso.openid.internal;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.openid.OpenId;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.sso.openid.configuration.OpenIdConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Michael C. Han
 */
@Component(
	configurationPid = "com.liferay.portal.sso.openid.configuration.OpenIdConfiguration",
	immediate = true, service = OpenId.class
)
public class OpenIdImpl implements OpenId {

	@Override
	public boolean isEnabled(long companyId) {
		return PrefsPropsUtil.getBoolean(
			companyId, PropsKeys.OPEN_ID_AUTH_ENABLED,
			_openIdConfiguration.enabled());
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_openIdConfiguration = Configurable.createConfigurable(
			OpenIdConfiguration.class, properties);
	}

	private volatile OpenIdConfiguration _openIdConfiguration;

}