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

package com.liferay.portal.workflow.kaleo.designer.web.internal.action.executor;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory.ServiceWrapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.workflow.kaleo.runtime.action.executor.ActionExecutor;

import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Rafael Praxedes
 */
@Component(service = FunctionActionExecutorServiceWrapperTracker.class)
public class FunctionActionExecutorServiceWrapperTracker {

	public static final String KEY =
		"com.liferay.portal.workflow.kaleo.runtime.action.executor.language";

	public Set<String> getFunctionActionExecutorKeys() {
		return _serviceTrackerMap.keySet();
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, ActionExecutor.class, "(" + KEY + "=function*)",
			(serviceReference, emitter) -> emitter.emit(
				(String)serviceReference.getProperty(KEY)),
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