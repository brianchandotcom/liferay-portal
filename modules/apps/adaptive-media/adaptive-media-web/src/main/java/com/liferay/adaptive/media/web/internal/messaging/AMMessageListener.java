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

package com.liferay.adaptive.media.web.internal.messaging;

import com.liferay.adaptive.media.processor.AMProcessor;
import com.liferay.adaptive.media.web.internal.constants.AMDestinationNames;
import com.liferay.adaptive.media.web.internal.processor.AMAsyncProcessorImpl;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Adolfo Pérez
 */
@Component(
	immediate = true,
	property = "destination.name=" + AMDestinationNames.ADAPTIVE_MEDIA_PROCESSOR,
	service = MessageListener.class
)
public class AMMessageListener extends BaseMessageListener {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, AMProcessor.class, "(model.class.name=*)",
			(serviceReference, emitter) -> emitter.emit(
				(String)serviceReference.getProperty("model.class.name")));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		String className = message.getString("className");

		List<AMProcessor> amProcessors = _serviceTrackerMap.getService(
			className);

		if (amProcessors == null) {
			return;
		}

		AMProcessorCommand amProcessorCommand = (AMProcessorCommand)message.get(
			"command");

		Object model = message.get("model");
		String modelId = (String)message.get("modelId");

		for (AMProcessor amProcessor : amProcessors) {
			try {
				amProcessorCommand.execute(amProcessor, model, modelId);
			}
			catch (NoSuchFileEntryException nsfee) {
				if (_log.isInfoEnabled()) {
					_log.info(nsfee, nsfee);
				}
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}
			}
		}

		AMAsyncProcessorImpl.cleanQueue(amProcessorCommand, modelId);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AMMessageListener.class);

	private ServiceTrackerMap<String, List<AMProcessor>> _serviceTrackerMap;

}