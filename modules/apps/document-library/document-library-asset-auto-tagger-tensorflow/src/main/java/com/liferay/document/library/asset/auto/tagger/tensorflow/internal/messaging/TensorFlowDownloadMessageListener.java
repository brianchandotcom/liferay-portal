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

package com.liferay.document.library.asset.auto.tagger.tensorflow.internal.messaging;

import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderDownloadConfiguration;
import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.constants.TensorFlowDestinationNames;
import com.liferay.document.library.asset.auto.tagger.tensorflow.internal.util.TensorFlowDownloadHelper;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	configurationPid = "com.liferay.document.library.asset.auto.tagger.tensorflow.internal.configuration.TensorFlowImageAssetAutoTagProviderDownloadConfiguration",
	property = "destination.name=" + TensorFlowDestinationNames.TENSORFLOW_MODEL_DOWNLOAD,
	service = MessageListener.class
)
public class TensorFlowDownloadMessageListener extends BaseMessageListener {

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		modified(properties);

		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_SERIAL,
				TensorFlowDestinationNames.TENSORFLOW_MODEL_DOWNLOAD);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		_destinationServiceRegistration = bundleContext.registerService(
			Destination.class, destination,
			MapUtil.singletonDictionary(
				"destination.name", destination.getName()));
	}

	@Deactivate
	protected void deactivate() {
		_destinationServiceRegistration.unregister();
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		_tensorFlowDownloadHelper.download(
			_tensorFlowImageAssetAutoTagProviderDownloadConfiguration);
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		_tensorFlowImageAssetAutoTagProviderDownloadConfiguration =
			ConfigurableUtil.createConfigurable(
				TensorFlowImageAssetAutoTagProviderDownloadConfiguration.class,
				properties);
	}

	@Reference
	private DestinationFactory _destinationFactory;

	private ServiceRegistration<Destination> _destinationServiceRegistration;

	@Reference
	private TensorFlowDownloadHelper _tensorFlowDownloadHelper;

	private volatile TensorFlowImageAssetAutoTagProviderDownloadConfiguration
		_tensorFlowImageAssetAutoTagProviderDownloadConfiguration;

}