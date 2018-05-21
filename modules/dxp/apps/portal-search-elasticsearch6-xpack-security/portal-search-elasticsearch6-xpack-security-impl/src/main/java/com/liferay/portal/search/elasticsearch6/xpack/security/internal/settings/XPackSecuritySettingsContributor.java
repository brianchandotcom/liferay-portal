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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.elasticsearch6.settings.ClientSettingsHelper;
import com.liferay.portal.search.elasticsearch6.settings.SettingsContributor;
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
	service = SettingsContributor.class
)
public class XPackSecuritySettingsContributor implements SettingsContributor {

	public XPackSecuritySettingsContributor() {
		priority = 1;
	}

	@Override
	public int compareTo(SettingsContributor settingsContributor) {
		if (priority > settingsContributor.getPriority()) {
			return 1;
		}
		else if (priority == settingsContributor.getPriority()) {
			return 0;
		}

		return -1;
	}

	@Override
	public int getPriority() {
		return priority;
	}

	@Override
	public void populate(ClientSettingsHelper clientSettingsHelper) {
		if (!xPackSecurityConfiguration.requiresAuthentication()) {
			return;
		}

		configureAuthentication(clientSettingsHelper);
		configureSSL(clientSettingsHelper);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		xPackSecurityConfiguration = ConfigurableUtil.createConfigurable(
			XPackSecurityConfiguration.class, properties);
	}

	protected void configureAuthentication(
		ClientSettingsHelper clientSettingsHelper) {

		String user =
			xPackSecurityConfiguration.username() + ":" +
				xPackSecurityConfiguration.password();

		clientSettingsHelper.put("xpack.security.user", user);
	}

	protected void configurePEMPaths(
		ClientSettingsHelper clientSettingsHelper) {

		clientSettingsHelper.put(
			"xpack.ssl.certificate",
			xPackSecurityConfiguration.sslCertificatePath());
		clientSettingsHelper.putArray(
			"xpack.ssl.certificate_authorities",
			xPackSecurityConfiguration.sslCertificateAuthoritiesPaths());
		clientSettingsHelper.put(
			"xpack.ssl.key", xPackSecurityConfiguration.sslKeyPath());
	}

	protected void configurePKCSPaths(
		ClientSettingsHelper clientSettingsHelper) {

		clientSettingsHelper.put(
			"xpack.ssl.keystore.password",
			xPackSecurityConfiguration.sslKeystorePassword());
		clientSettingsHelper.put(
			"xpack.ssl.keystore.path",
			xPackSecurityConfiguration.sslKeystorePath());
		clientSettingsHelper.put(
			"xpack.ssl.truststore.password",
			xPackSecurityConfiguration.sslTruststorePassword());
		clientSettingsHelper.put(
			"xpack.ssl.truststore.path",
			xPackSecurityConfiguration.sslTruststorePath());
	}

	protected void configureSSL(ClientSettingsHelper clientSettingsHelper) {
		if (!xPackSecurityConfiguration.transportSSLEnabled()) {
			return;
		}

		clientSettingsHelper.put(
			"xpack.security.transport.ssl.enabled", "true");
		clientSettingsHelper.put(
			"xpack.security.transport.ssl.verification_mode",
			StringUtil.toLowerCase(
				xPackSecurityConfiguration.transportSSLVerificationMode()));

		String certificateFormat =
			xPackSecurityConfiguration.certificateFormat();

		if (certificateFormat.equals("PKCS#12")) {
			configurePKCSPaths(clientSettingsHelper);
		}
		else {
			configurePEMPaths(clientSettingsHelper);
		}
	}

	protected int priority;
	protected volatile XPackSecurityConfiguration xPackSecurityConfiguration;

}