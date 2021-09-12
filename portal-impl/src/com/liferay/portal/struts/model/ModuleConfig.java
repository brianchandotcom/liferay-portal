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

package com.liferay.portal.struts.model;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.portal.struts.ActionAdapter;

import java.util.HashMap;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Shuyang Zhou
 */
public class ModuleConfig {

	public void addActionForward(ActionForward actionForward) {
		_actionForwards.put(actionForward.getName(), actionForward);
	}

	public void addActionMapping(ActionMapping actionMapping) {
		_actionMappings.put(actionMapping.getPath(), actionMapping);
	}

	public ActionForward getActionForward(String name) {
		return _actionForwards.get(name);
	}

	public ActionMapping getActionMapping(String path) {
		ActionAdapter actionAdapter = _getActionAdaptor(path);

		if (actionAdapter != null) {
			return new ActionMapping(this, null, path, actionAdapter);
		}

		return _actionMappings.get(path);
	}

	private ActionAdapter _getActionAdaptor(String path) {
		ActionAdapter actionAdapter = _actionAdaptors.getService(path);

		if (actionAdapter != null) {
			return actionAdapter;
		}

		for (String key : _actionAdaptors.keySet()) {
			if (path.startsWith(key)) {
				return _actionAdaptors.getService(key);
			}
		}

		return null;
	}

	private static final ServiceTrackerMap<String, ActionAdapter>
		_actionAdaptors;

	static {
		BundleContext bundleContext = SystemBundleUtil.getBundleContext();

		_actionAdaptors = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, StrutsAction.class, "path",
			new ServiceTrackerCustomizer<StrutsAction, ActionAdapter>() {

				@Override
				public ActionAdapter addingService(
					ServiceReference<StrutsAction> serviceReference) {

					return new ActionAdapter(
						bundleContext.getService(serviceReference));
				}

				@Override
				public void modifiedService(
					ServiceReference<StrutsAction> serviceReference,
					ActionAdapter actionAdapter) {
				}

				@Override
				public void removedService(
					ServiceReference<StrutsAction> serviceReference,
					ActionAdapter actionAdapter) {

					bundleContext.ungetService(serviceReference);
				}

			});
	}

	private final Map<String, ActionForward> _actionForwards = new HashMap<>();
	private final Map<String, ActionMapping> _actionMappings = new HashMap<>();

}