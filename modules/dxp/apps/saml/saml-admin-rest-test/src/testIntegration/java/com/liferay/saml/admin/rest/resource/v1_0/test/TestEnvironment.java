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

package com.liferay.saml.admin.rest.resource.v1_0.test;

import com.liferay.portal.configuration.test.util.ConfigurationTemporarySwapper;
import com.liferay.portal.configuration.test.util.ConfigurationTestUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.saml.util.PortletPropsKeys;

import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.ListIterator;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Stian Sigvartsen
 */
public class TestEnvironment implements AutoCloseable {

	public TestEnvironment(
		CompanyLocalService companyLocalService,
		ConfigurationAdmin configurationAdmin) {

		_companyLocalService = companyLocalService;
		_configurationAdmin = configurationAdmin;
	}

	@Override
	public void close() throws IOException {
		ListIterator<AutoCloseable> listIterator = _autoCloseables.listIterator(
			_autoCloseables.size());

		while (listIterator.hasPrevious()) {
			AutoCloseable previousAutoCloseable = listIterator.previous();

			try {
				previousAutoCloseable.close();
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	public void mountKeyStore() throws Exception {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		File file = FileUtil.createTempFile(
			classLoader.getResourceAsStream("testSAMLEntity.jks"));

		_autoCloseables.add(file::delete);
		_autoCloseables.add(
			new ConfigurationTemporarySwapper(
				"com.liferay.saml.runtime.configuration.SamlConfiguration",
				HashMapDictionaryBuilder.put(
					PortletPropsKeys.SAML_KEYSTORE_PATH,
					(Object)file.getAbsolutePath()
				).build()));
	}

	public void temporarilyClearSamlProviderConfiguration() throws Exception {
		_temporarilyClearSamlProviderConfiguration(CompanyConstants.SYSTEM);

		_companyLocalService.forEachCompanyId(
			this::_temporarilyClearSamlProviderConfiguration);
	}

	private void _deleteSamlProviderConfiguration(long companyId)
		throws Exception {

		Configuration configuration = _getSamlProviderConfiguration(companyId);

		if (configuration != null) {
			ConfigurationTestUtil.deleteConfiguration(configuration);
		}
	}

	private Configuration _getSamlProviderConfiguration(long companyId)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			"(&(service.factoryPid=com.liferay.saml.runtime.configuration." +
				"SamlProviderConfiguration)(companyId=" + companyId + "))");

		if ((configurations != null) && (configurations.length > 0)) {
			return configurations[0];
		}

		return null;
	}

	private void _temporarilyClearSamlProviderConfiguration(long companyId)
		throws Exception {

		Configuration configuration = _getSamlProviderConfiguration(companyId);

		if (configuration != null) {
			Dictionary<String, Object> dictionary =
				configuration.getProperties();

			dictionary.put("companyId", -companyId - 1);

			ConfigurationTestUtil.saveConfiguration(configuration, dictionary);

			_autoCloseables.add(
				() -> {
					_deleteSamlProviderConfiguration(companyId);
					dictionary.put("companyId", companyId);

					ConfigurationTestUtil.saveConfiguration(
						configuration, dictionary);
				});
		}
		else {
			_autoCloseables.add(
				() -> _deleteSamlProviderConfiguration(companyId));
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TestEnvironment.class);

	private final ArrayList<AutoCloseable> _autoCloseables = new ArrayList<>();
	private final CompanyLocalService _companyLocalService;
	private final ConfigurationAdmin _configurationAdmin;

}