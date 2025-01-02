/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.security.auth.CompanyCentralizedThreadLocal;

import java.util.LinkedList;

/**
 * @author Michael C. Han
 */
public class ServiceContextThreadLocal {

	public static ServiceContext getServiceContext() {
		LinkedList<ServiceContext> serviceContexts = _serviceContexts.get();

		return serviceContexts.peek();
	}

	public static ServiceContext popServiceContext() {
		LinkedList<ServiceContext> serviceContexts = _serviceContexts.get();

		if (serviceContexts.isEmpty()) {
			return null;
		}

		return serviceContexts.pop();
	}

	public static void pushServiceContext(ServiceContext serviceContext) {
		LinkedList<ServiceContext> serviceContexts = _serviceContexts.get();

		serviceContexts.push(serviceContext);
	}

	public static void remove() {
		LinkedList<ServiceContext> serviceContexts = _serviceContexts.get();

		if (serviceContexts != null) {
			serviceContexts.clear();
		}
	}

	private static final ThreadLocal<LinkedList<ServiceContext>>
		_serviceContexts = new CompanyCentralizedThreadLocal<>(
			ServiceContextThreadLocal.class + "._serviceContexts",
			LinkedList::new,
			serviceContexts -> {
				LinkedList<ServiceContext> cloneServiceContexts =
					new LinkedList<>();

				for (ServiceContext serviceContext : serviceContexts) {
					ServiceContext cloneServiceContext =
						(ServiceContext)serviceContext.clone();

					cloneServiceContexts.add(cloneServiceContext);
				}

				return cloneServiceContexts;
			});

}