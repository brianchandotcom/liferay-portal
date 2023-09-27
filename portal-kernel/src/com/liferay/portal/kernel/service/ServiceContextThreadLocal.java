/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.petra.lang.CentralizedThreadLocal;

import java.util.LinkedList;

/**
 * @author Michael C. Han
 */
public class ServiceContextThreadLocal {

	public static ServiceContext getServiceContext() {
		LinkedList<ServiceContext> serviceContextStack =
			_serviceContextThreadLocal.get();

		return serviceContextStack.peek();
	}

	public static ServiceContext popServiceContext() {
		LinkedList<ServiceContext> serviceContextStack =
			_serviceContextThreadLocal.get();

		if (serviceContextStack.isEmpty()) {
			return null;
		}

		return serviceContextStack.pop();
	}

	public static void pushServiceContext(ServiceContext serviceContext) {
		LinkedList<ServiceContext> serviceContextStack =
			_serviceContextThreadLocal.get();

		serviceContextStack.push(serviceContext);
	}

	private static final ThreadLocal<LinkedList<ServiceContext>>
		_serviceContextThreadLocal = new CentralizedThreadLocal<>(
			ServiceContextThreadLocal.class + "._serviceContextThreadLocal",
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
			},
			true);

}