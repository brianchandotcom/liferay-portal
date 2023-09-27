/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.saml.internal.upgrade;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.PropsImpl;
import com.liferay.saml.internal.constants.LegacySamlPropsKeys;
import com.liferay.saml.internal.upgrade.v1_0_0.SamlIdpSsoSessionMaxAgePropertyUpgradeProcess;
import com.liferay.saml.runtime.configuration.SamlProviderConfiguration;

import java.util.Dictionary;
import java.util.Hashtable;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Tomas Polesovsky
 */
public class SamlIdpSsoSessionMaxAgePropertyUpgradeProcessTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		PropsUtil.setProps(new PropsImpl());
	}

	@Test
	public void testSamlProviderPropertyMapping() throws Exception {
		long samlIdpSsoSessionMaxAge = RandomTestUtil.randomLong();

		SamlProviderConfiguration samlProviderConfiguration =
			ConfigurableUtil.createConfigurable(
				SamlProviderConfiguration.class,
				HashMapBuilder.<String, Object>put(
					LegacySamlPropsKeys.SAML_IDP_SESSION_MAXIMUM_AGE,
					samlIdpSsoSessionMaxAge
				).build());

		Assert.assertEquals(
			samlIdpSsoSessionMaxAge,
			samlProviderConfiguration.sessionMaximumAge());
	}

	@Test
	public void testUpgradeWithDefaultProperty() throws Exception {
		ConfigurationAdmin configurationAdmin = Mockito.mock(
			ConfigurationAdmin.class);

		Configuration configuration = Mockito.mock(Configuration.class);

		Mockito.when(
			configurationAdmin.listConfigurations(Mockito.anyString())
		).thenReturn(
			new Configuration[] {configuration}
		);

		Props props = Mockito.mock(Props.class);

		Mockito.when(
			props.get(LegacySamlPropsKeys.SAML_IDP_SSO_SESSION_MAX_AGE)
		).thenReturn(
			null
		);

		SamlIdpSsoSessionMaxAgePropertyUpgradeProcess
			samlIdpSsoSessionMaxAgePropertyUpgradeProcess =
				new SamlIdpSsoSessionMaxAgePropertyUpgradeProcess(
					configurationAdmin, props);

		samlIdpSsoSessionMaxAgePropertyUpgradeProcess.doUpgrade();

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			LegacySamlPropsKeys.SAML_IDP_SESSION_MAXIMUM_AGE, "86400000");

		Configuration verifyConfiguration = Mockito.verify(
			configuration, Mockito.times(1));

		verifyConfiguration.update(Mockito.eq(properties));
	}

	@Test
	public void testUpgradeWithEmptyProperty() throws Exception {
		ConfigurationAdmin configurationAdmin = Mockito.mock(
			ConfigurationAdmin.class);

		Configuration configuration = Mockito.mock(Configuration.class);

		Mockito.when(
			configurationAdmin.listConfigurations(Mockito.anyString())
		).thenReturn(
			new Configuration[] {configuration}
		);

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(LegacySamlPropsKeys.SAML_IDP_SESSION_MAXIMUM_AGE, "0");

		Mockito.when(
			configuration.getProperties()
		).thenReturn(
			properties
		);

		Props props = Mockito.mock(Props.class);

		Mockito.when(
			props.get(LegacySamlPropsKeys.SAML_IDP_SSO_SESSION_MAX_AGE)
		).thenReturn(
			""
		);

		SamlIdpSsoSessionMaxAgePropertyUpgradeProcess
			samlIdpSsoSessionMaxAgePropertyUpgradeProcess =
				new SamlIdpSsoSessionMaxAgePropertyUpgradeProcess(
					configurationAdmin, props);

		samlIdpSsoSessionMaxAgePropertyUpgradeProcess.doUpgrade();

		properties = new Hashtable<>();

		properties.put(
			LegacySamlPropsKeys.SAML_IDP_SESSION_MAXIMUM_AGE, "86400000");

		Configuration verifyConfiguration = Mockito.verify(
			configuration, Mockito.times(1));

		verifyConfiguration.update(Mockito.eq(properties));
	}

	@Test
	public void testUpgradeWithProperty() throws Exception {
		ConfigurationAdmin configurationAdmin = Mockito.mock(
			ConfigurationAdmin.class);

		Configuration configuration = Mockito.mock(Configuration.class);

		Mockito.when(
			configurationAdmin.listConfigurations(Mockito.anyString())
		).thenReturn(
			new Configuration[] {configuration}
		);

		Props props = Mockito.mock(Props.class);

		String samlIdpSsoSessionMaxAge = String.valueOf(
			RandomTestUtil.randomInt());

		Mockito.when(
			props.get(LegacySamlPropsKeys.SAML_IDP_SSO_SESSION_MAX_AGE)
		).thenReturn(
			samlIdpSsoSessionMaxAge
		);

		SamlIdpSsoSessionMaxAgePropertyUpgradeProcess
			samlIdpSsoSessionMaxAgePropertyUpgradeProcess =
				new SamlIdpSsoSessionMaxAgePropertyUpgradeProcess(
					configurationAdmin, props);

		samlIdpSsoSessionMaxAgePropertyUpgradeProcess.doUpgrade();

		Dictionary<String, Object> properties = new Hashtable<>();

		properties.put(
			LegacySamlPropsKeys.SAML_IDP_SESSION_MAXIMUM_AGE,
			samlIdpSsoSessionMaxAge);

		Configuration verifyConfiguration = Mockito.verify(
			configuration, Mockito.times(1));

		verifyConfiguration.update(Mockito.eq(properties));
	}

	@Test
	public void testUpgradeWithPropertyAlreadyOverwritten() throws Exception {
		ConfigurationAdmin configurationAdmin = Mockito.mock(
			ConfigurationAdmin.class);

		Configuration configuration = Mockito.mock(Configuration.class);

		Mockito.when(
			configurationAdmin.listConfigurations(Mockito.anyString())
		).thenReturn(
			new Configuration[] {configuration}
		);

		Dictionary<String, Object> properties = new Hashtable<>();

		String samlIdpSsoSessionMaxAge = String.valueOf(
			RandomTestUtil.randomInt());

		properties.put(
			LegacySamlPropsKeys.SAML_IDP_SESSION_MAXIMUM_AGE,
			samlIdpSsoSessionMaxAge);

		Mockito.when(
			configuration.getProperties()
		).thenReturn(
			properties
		);

		Props props = Mockito.mock(Props.class);

		Mockito.when(
			props.get(LegacySamlPropsKeys.SAML_IDP_SSO_SESSION_MAX_AGE)
		).thenReturn(
			String.valueOf(RandomTestUtil.randomInt())
		);

		SamlIdpSsoSessionMaxAgePropertyUpgradeProcess
			samlIdpSsoSessionMaxAgePropertyUpgradeProcess =
				new SamlIdpSsoSessionMaxAgePropertyUpgradeProcess(
					configurationAdmin, props);

		samlIdpSsoSessionMaxAgePropertyUpgradeProcess.doUpgrade();

		Configuration verifyConfiguration = Mockito.verify(
			configuration, Mockito.never());

		verifyConfiguration.update(Mockito.any(Dictionary.class));
	}

}