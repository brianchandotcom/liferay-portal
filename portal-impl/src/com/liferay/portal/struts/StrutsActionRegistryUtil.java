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

package com.liferay.portal.struts;

import com.liferay.portal.kernel.struts.StrutsAction;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTrackerCustomizer;
import com.liferay.registry.collections.ServiceTrackerMap;
import com.liferay.registry.collections.ServiceTrackerMapFactory;
import com.liferay.registry.collections.ServiceTrackerMapFactoryUtil;

/**
 * @author Mika Koivisto
 * @author Raymond Augé
 */
public class StrutsActionRegistryUtil {

	public static Action getAction(String path) {
		Action action = _actions.getService(path);

		if (action != null) {
			return action;
		}

		for (String key : _actions.keySet()) {
			if (path.startsWith(key)) {
				return _actions.getService(key);
			}
		}

		return null;
	}

	private static final ServiceTrackerMap<String, Action> _actions;

	static {
		ServiceTrackerMapFactory serviceTrackerMapFactory =
			ServiceTrackerMapFactoryUtil.getServiceTrackerMapFactory();

		_actions = serviceTrackerMapFactory.openSingleValueMap(
			StrutsAction.class, "path",
			new ServiceTrackerCustomizer<StrutsAction, Action>() {

				@Override
				public Action addingService(
					ServiceReference<StrutsAction> serviceReference) {

					Registry registry = RegistryUtil.getRegistry();

					return new ActionAdapter(
						registry.getService(serviceReference));
				}

				@Override
				public void modifiedService(
					ServiceReference<StrutsAction> serviceReference,
					Action service) {
				}

				@Override
				public void removedService(
					ServiceReference<StrutsAction> serviceReference,
					Action service) {

					Registry registry = RegistryUtil.getRegistry();

					registry.ungetService(serviceReference);
				}

			});
	}

}