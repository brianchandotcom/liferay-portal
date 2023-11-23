/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.preview.video.internal.messaging;

import com.liferay.document.library.kernel.processor.VideoProcessorUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portlet.documentlibrary.messaging.BaseProcessorMessageListener;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Juan González
 * @author Sergio González
 */
@Component(
	property = "destination.name=" + DestinationNames.DOCUMENT_LIBRARY_VIDEO_PROCESSOR,
	service = MessageListener.class
)
public class VideoProcessorMessageListener
	extends BaseProcessorMessageListener {

	@Activate
	protected void activate(BundleContext bundleContext) {
		Destination destination = _destinationFactory.createDestination(
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_SERIAL,
				DestinationNames.DOCUMENT_LIBRARY_VIDEO_PROCESSOR));

		_serviceRegistration = bundleContext.registerService(
			Destination.class, destination,
			MapUtil.singletonDictionary(
				"destination.name", destination.getName()));
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Override
	protected void generate(
			FileVersion sourceFileVersion, FileVersion destinationFileVersion)
		throws Exception {

		VideoProcessorUtil.generateVideo(
			sourceFileVersion, destinationFileVersion);
	}

	@Reference
	private DestinationFactory _destinationFactory;

	private ServiceRegistration<Destination> _serviceRegistration;

}