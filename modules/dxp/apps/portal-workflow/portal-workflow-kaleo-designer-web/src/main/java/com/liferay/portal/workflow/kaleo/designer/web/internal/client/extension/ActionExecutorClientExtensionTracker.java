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

package com.liferay.portal.workflow.kaleo.designer.web.internal.client.extension;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.runtime.action.executor.ActionExecutor;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Rafael Praxedes
 */
@Component(service = ActionExecutorClientExtensionTracker.class)
public class ActionExecutorClientExtensionTracker {

	public static final String ACTION_EXECUTOR_LANGUAGE_KEY =
		"com.liferay.portal.workflow.kaleo.runtime.action.executor.language";

	public JSONArray getClientExtensionsJSONArray() throws Exception {
		return JSONUtil.toJSONArray(
			_serviceTrackerMap.keySet(),
			clientExtensionFunctionKey -> {
				ServiceWrapper<ActionExecutor> actionExecutorServiceWrapper =
					_serviceTrackerMap.getService(clientExtensionFunctionKey);

				String description = MapUtil.getString(
					actionExecutorServiceWrapper.getProperties(),
					"client.extension.description");

				if (Validator.isBlank(description)) {
					description = clientExtensionFunctionKey;
				}

				return JSONUtil.put(
					"description", description
				).put(
					"key", clientExtensionFunctionKey
				);
			});
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ActionExecutor.class,
			"(" + ACTION_EXECUTOR_LANGUAGE_KEY + "=function*)",
			(serviceReference, emitter) -> emitter.emit(
				(String)serviceReference.getProperty(
					ACTION_EXECUTOR_LANGUAGE_KEY)),
			ServiceTrackerCustomizerFactory.<ActionExecutor>serviceWrapper(
				bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, ServiceWrapper<ActionExecutor>>
		_serviceTrackerMap;

}