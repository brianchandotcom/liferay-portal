/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.persistence.listener;

import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderCompanyConfiguration;
import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.constants.TensorFlowDestinationNames;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.configuration.persistence.listener.ConfigurationModelListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;

import java.util.Dictionary;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = "model.class.name=com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderCompanyConfiguration",
	service = ConfigurationModelListener.class
)
public class
	TensorFlowImageAssetAutoTagProviderCompanyConfigurationModelListener
		implements ConfigurationModelListener {

	@Override
	public void onAfterSave(String pid, Dictionary<String, Object> properties) {
		TensorFlowImageAssetAutoTagProviderCompanyConfiguration
			tensorFlowImageAssetAutoTagProviderCompanyConfiguration =
				ConfigurableUtil.createConfigurable(
					TensorFlowImageAssetAutoTagProviderCompanyConfiguration.
						class,
					properties);

		if (tensorFlowImageAssetAutoTagProviderCompanyConfiguration.enabled()) {
			_messageBus.sendMessage(
				TensorFlowDestinationNames.TENSORFLOW_MODEL_DOWNLOAD,
				new Message());
		}
	}

	@Reference
	private MessageBus _messageBus;

}