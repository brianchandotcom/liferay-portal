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