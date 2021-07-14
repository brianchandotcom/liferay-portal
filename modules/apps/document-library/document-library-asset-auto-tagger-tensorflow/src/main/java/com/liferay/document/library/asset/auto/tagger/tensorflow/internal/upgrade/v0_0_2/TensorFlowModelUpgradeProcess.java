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

package com.liferay.document.library.asset.auto.tagger.tensorflow.internal.upgrade.v0_0_2;

import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderCompanyConfiguration;
import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderDownloadConfiguration;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.util.JarUtil;

import java.io.File;
import java.io.FileInputStream;

import java.net.URL;

import java.nio.file.Paths;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Alejandro Tardín
 */
public class TensorFlowModelUpgradeProcess extends UpgradeProcess {

	public TensorFlowModelUpgradeProcess(
		CompanyLocalService companyLocalService,
		ConfigurationAdmin configurationAdmin,
		ConfigurationProvider configurationProvider, Store store) {

		_companyLocalService = companyLocalService;
		_configurationAdmin = configurationAdmin;
		_configurationProvider = configurationProvider;
		_store = store;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> {
				TensorFlowImageAssetAutoTagProviderCompanyConfiguration
					tensorFlowImageAssetAutoTagProviderCompanyConfiguration =
						_configurationProvider.getCompanyConfiguration(
							TensorFlowImageAssetAutoTagProviderCompanyConfiguration.class,
							companyId);

				if (tensorFlowImageAssetAutoTagProviderCompanyConfiguration.
						enabled()) {

					Configuration configuration =
						_configurationAdmin.getConfiguration(
							TensorFlowImageAssetAutoTagProviderDownloadConfiguration.class.
								getName(),
							StringPool.QUESTION);

					Dictionary<String, Object> dictionary =
						configuration.getProperties();

					if (dictionary == null) {
						dictionary = new HashMapDictionary<>();
					}

					TensorFlowImageAssetAutoTagProviderDownloadConfiguration
						tensorFlowImageAssetAutoTagProviderDownloadConfiguration =
							ConfigurableUtil.createConfigurable(
								TensorFlowImageAssetAutoTagProviderDownloadConfiguration.class,
								dictionary);

					_downloadFile(
						"org.tensorflow.models.inception-5h.jar",
						tensorFlowImageAssetAutoTagProviderDownloadConfiguration.
							modelDownloadURL());
					_downloadFile(
						"libtensorflow_jni-1.15.0.jar",
						tensorFlowImageAssetAutoTagProviderDownloadConfiguration.
							nativeLibraryDownloadURL());

					return;
				}
			});
	}

	private void _downloadFile(String fileName, String url) throws Exception {
		File tempFile = FileUtil.createTempFile();

		JarUtil.downloadAndInstallJar(new URL(url), tempFile.toPath());

		_store.addFile(
			0, CompanyConstants.SYSTEM,
			String.valueOf(
				Paths.get(
					"com.liferay.document.library.asset.auto.tagger.tensorflow",
					fileName)),
			Store.VERSION_DEFAULT, new FileInputStream(tempFile));
	}

	private final CompanyLocalService _companyLocalService;
	private final ConfigurationAdmin _configurationAdmin;
	private final ConfigurationProvider _configurationProvider;
	private final Store _store;

}