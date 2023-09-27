/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.auto.tagger.internal.configuration.admin.display;

import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfiguration;
import com.liferay.asset.auto.tagger.configuration.AssetAutoTaggerConfigurationFactory;
import com.liferay.configuration.admin.display.ConfigurationVisibilityController;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"configuration.pid=com.liferay.asset.auto.tagger.google.cloud.natural.language.internal.configuration.GCloudNaturalLanguageAssetAutoTaggerCompanyConfiguration",
		"configuration.pid=com.liferay.asset.auto.tagger.internal.configuration.AssetAutoTaggerCompanyConfiguration",
		"configuration.pid=com.liferay.asset.auto.tagger.opennlp.internal.configuration.OpenNLPDocumentAssetAutoTaggerCompanyConfiguration",
		"configuration.pid=com.liferay.document.library.asset.auto.tagger.google.cloud.vision.internal.configuration.GCloudVisionAssetAutoTagProviderCompanyConfiguration",
		"configuration.pid=com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.configuration.MSCognitiveServicesAssetAutoTagProviderCompanyConfiguration",
		"configuration.pid=com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderCompanyConfiguration",
		"configuration.pid=com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderProcessConfiguration"
	},
	service = ConfigurationVisibilityController.class
)
public class AssetAutoTagProviderConfigurationVisibilityController
	implements ConfigurationVisibilityController {

	@Override
	public boolean isVisible(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		try {
			AssetAutoTaggerConfiguration assetAutoTaggerConfiguration =
				_getAssetAutoTaggerConfiguration(scope, scopePK);

			if (ExtendedObjectClassDefinition.Scope.SYSTEM.equals(scope)) {
				return assetAutoTaggerConfiguration.isEnabled();
			}

			return assetAutoTaggerConfiguration.isAvailable();
		}
		catch (PortalException portalException) {
			_log.error(portalException);

			return false;
		}
	}

	private AssetAutoTaggerConfiguration _getAssetAutoTaggerConfiguration(
			ExtendedObjectClassDefinition.Scope scope, Serializable scopePK)
		throws PortalException {

		if (ExtendedObjectClassDefinition.Scope.SYSTEM.equals(scope)) {
			return _assetAutoTaggerConfigurationFactory.
				getSystemAssetAutoTaggerConfiguration();
		}

		if (ExtendedObjectClassDefinition.Scope.COMPANY.equals(scope)) {
			return _assetAutoTaggerConfigurationFactory.
				getCompanyAssetAutoTaggerConfiguration(
					_companyLocalService.getCompany((long)scopePK));
		}

		if (ExtendedObjectClassDefinition.Scope.GROUP.equals(scope)) {
			return _assetAutoTaggerConfigurationFactory.
				getGroupAssetAutoTaggerConfiguration(
					_groupLocalService.getGroup((long)scopePK));
		}

		throw new IllegalArgumentException("Unrecognized scope: " + scope);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetAutoTagProviderConfigurationVisibilityController.class);

	@Reference
	private AssetAutoTaggerConfigurationFactory
		_assetAutoTaggerConfigurationFactory;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

}