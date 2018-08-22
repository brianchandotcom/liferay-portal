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

package com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.configuration.declaration;

import com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.configuration.MicrosoftCognitiveServicesAssetAutoTagProviderCompanyConfiguration;
import com.liferay.document.library.asset.auto.tagger.microsoft.cognitive.services.internal.constants.MicrosoftCognitiveServicesAssetAutoTagProviderConstants;
import com.liferay.portal.kernel.settings.definition.ConfigurationPidMapping;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alejandro Tardín
 */
@Component
public class MicrosoftCognitiveServicesAssetAutoTagProviderCompanyConfigurationPidMapping
	implements ConfigurationPidMapping {

	@Override
	public Class<?> getConfigurationBeanClass() {
		return
			MicrosoftCognitiveServicesAssetAutoTagProviderCompanyConfiguration.
				class;
	}

	@Override
	public String getConfigurationPid() {
		return MicrosoftCognitiveServicesAssetAutoTagProviderConstants.
			SERVICE_NAME;
	}

}