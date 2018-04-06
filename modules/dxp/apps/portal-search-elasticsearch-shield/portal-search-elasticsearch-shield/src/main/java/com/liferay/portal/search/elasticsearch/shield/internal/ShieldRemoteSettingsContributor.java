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

package com.liferay.portal.search.elasticsearch.shield.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.search.elasticsearch.settings.BaseSettingsContributor;
import com.liferay.portal.search.elasticsearch.settings.ClientSettingsHelper;
import com.liferay.portal.search.elasticsearch.settings.SettingsContributor;
import com.liferay.portal.search.elasticsearch.shield.configuration.ShieldConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author André de Oliveira
 */
@Component(
	configurationPid = "com.liferay.portal.search.elasticsearch.shield.configuration.ShieldConfiguration",
	immediate = true, property = "operation.mode=REMOTE",
	service = SettingsContributor.class
)
public class ShieldRemoteSettingsContributor extends BaseSettingsContributor {

	public ShieldRemoteSettingsContributor() {
		super(1);
	}

	@Override
	public void populate(ClientSettingsHelper clientSettingsHelper) {
		if (!shieldConfiguration.requiresAuthentication()) {
			return;
		}

		clientSettingsHelper.addPlugin("org.elasticsearch.shield.ShieldPlugin");

		configureAuthentication(clientSettingsHelper);
		configureSSL(clientSettingsHelper);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		shieldConfiguration = ConfigurableUtil.createConfigurable(
			ShieldConfiguration.class, properties);
	}

	protected void configureAuthentication(
		ClientSettingsHelper clientSettingsHelper) {

		String user =
			shieldConfiguration.username() + ":" +
				shieldConfiguration.password();

		clientSettingsHelper.put("shield.user", user);
	}

	protected void configureSSL(ClientSettingsHelper clientSettingsHelper) {
		if (!shieldConfiguration.requiresSSL()) {
			return;
		}

		clientSettingsHelper.put("shield.http.ssl", "true");
		clientSettingsHelper.put(
			"shield.ssl.keystore.path", shieldConfiguration.sslKeystorePath());
		clientSettingsHelper.put(
			"shield.ssl.keystore.password",
			shieldConfiguration.sslKeystorePassword());
		clientSettingsHelper.put("shield.transport.ssl", "true");

		String sslKeystoreKeyPassword =
			shieldConfiguration.sslKeystoreKeyPassword();

		if (sslKeystoreKeyPassword != null) {
			clientSettingsHelper.put(
				"shield.ssl.keystore.key_password", sslKeystoreKeyPassword);
		}
	}

	protected volatile ShieldConfiguration shieldConfiguration;

}