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