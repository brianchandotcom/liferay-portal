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

package com.liferay.social.activity.test.util;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portlet.social.model.SocialActivityInterpreter;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.Collection;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

/**
 * @author Adolfo Pérez
 */
public class SocialActivityInterpreterHelper {

	public SocialActivityInterpreterHelper(String className, String portletId) {
		_className = className;
		_portletId = portletId;
	}

	public SocialActivityInterpreter getActivityInterpreter() {
		try {
			Registry registry = RegistryUtil.getRegistry();

			Collection<SocialActivityInterpreter> socialActivityInterpreters =
				registry.getServices(
					SocialActivityInterpreter.class,
					"(javax.portlet.name=" + _portletId + ")");

			for (SocialActivityInterpreter socialActivityInterpreter :
					socialActivityInterpreters) {

				if (ArrayUtil.contains(
						socialActivityInterpreter.getClassNames(),
						_className)) {

					return socialActivityInterpreter;
				}
			}

			throw new IllegalStateException(
				"No activity interpreter found for class " + _className);
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public SocialActivityInterpreter getActivityInterpreter(
		BundleContext bundleContext) {

		try {
			Collection<ServiceReference<SocialActivityInterpreter>>
				serviceReferences = bundleContext.getServiceReferences(
					SocialActivityInterpreter.class,
					"(javax.portlet.name=" + _portletId + ")");

			for (ServiceReference<SocialActivityInterpreter> serviceReference :
					serviceReferences) {

				SocialActivityInterpreter socialActivityInterpreter =
					bundleContext.getService(serviceReference);

				if (ArrayUtil.contains(
						socialActivityInterpreter.getClassNames(),
						_className)) {

					return socialActivityInterpreter;
				}
			}

			throw new IllegalStateException(
				"No activity interpreter found for class " + _className);
		}
		catch (InvalidSyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private final String _className;
	private final String _portletId;

}