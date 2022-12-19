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

package com.liferay.dispatch.internal.metadata;

import com.liferay.dispatch.metadata.DispatchTriggerMetadata;
import com.liferay.dispatch.metadata.DispatchTriggerMetadataFactory;
import com.liferay.dispatch.metadata.DispatchTriggerMetadataProvider;
import com.liferay.dispatch.model.DispatchTrigger;
import com.liferay.dispatch.service.DispatchTriggerLocalService;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapListener;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collections;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Igor Beslic
 */
@Component(service = DispatchTriggerMetadataProvider.class)
public class DispatchTriggerMetadataProviderImpl
	implements DispatchTriggerMetadataProvider {

	@Override
	public DispatchTriggerMetadata getDispatchTriggerMetadata(
		long dispatchTriggerId) {

		DispatchTrigger dispatchTrigger =
			_dispatchTriggerLocalService.fetchDispatchTrigger(
				dispatchTriggerId);

		if ((dispatchTrigger == null) ||
			!_serviceTrackerMap.containsKey(
				dispatchTrigger.getDispatchTaskExecutorType())) {

			return _defaultDispatchTriggerMetadata;
		}

		DispatchTriggerMetadataFactory dispatchTriggerMetadataFactory =
			_serviceTrackerMap.getService(
				dispatchTrigger.getDispatchTaskExecutorType());

		return dispatchTriggerMetadataFactory.instance(dispatchTrigger);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DispatchTriggerMetadataFactory.class,
			_KEY_DISPATCH_TASK_EXECUTOR_TYPE,
			new ServiceTrackerMapListener
				<String, DispatchTriggerMetadataFactory,
				 DispatchTriggerMetadataFactory>() {

				@Override
				public void keyEmitted(
					ServiceTrackerMap<String, DispatchTriggerMetadataFactory>
						serviceTrackerMap,
					String key,
					DispatchTriggerMetadataFactory
						serviceDispatchTriggerMetadataFactory,
					DispatchTriggerMetadataFactory
						contentDispatchTriggerMetadataFactory) {

					if (serviceTrackerMap.containsKey(key)) {
						DispatchTriggerMetadataFactory
							curDispatchTriggerMetadataFactory =
								serviceTrackerMap.getService(key);

						Class<?> clazz1 =
							curDispatchTriggerMetadataFactory.getClass();

						Class<?> clazz2 =
							serviceDispatchTriggerMetadataFactory.getClass();

						_log.error(
							StringBundler.concat(
								_KEY_DISPATCH_TASK_EXECUTOR_TYPE,
								" property must have unique value. The same ",
								"value is found in ", clazz1.getName(), " and ",
								clazz2.getName(), StringPool.PERIOD));
					}
				}

				@Override
				public void keyRemoved(
					ServiceTrackerMap<String, DispatchTriggerMetadataFactory>
						serviceTrackerMap,
					String key,
					DispatchTriggerMetadataFactory
						serviceDispatchTriggerMetadataFactory,
					DispatchTriggerMetadataFactory
						contentDispatchTriggerMetadataFactory) {
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final String _KEY_DISPATCH_TASK_EXECUTOR_TYPE =
		"dispatch.task.executor.type";

	private static final Log _log = LogFactoryUtil.getLog(
		DispatchTriggerMetadataProviderImpl.class);

	private static final DispatchTriggerMetadata
		_defaultDispatchTriggerMetadata = new DispatchTriggerMetadata() {

			@Override
			public Map<String, String> getAttributes() {
				return Collections.emptyMap();
			}

			@Override
			public Map<String, String> getErrors() {
				return Collections.emptyMap();
			}

			@Override
			public boolean isDispatchTaskExecutorReady() {
				return true;
			}

		};

	@Reference
	private DispatchTriggerLocalService _dispatchTriggerLocalService;

	private ServiceTrackerMap<String, DispatchTriggerMetadataFactory>
		_serviceTrackerMap;

}