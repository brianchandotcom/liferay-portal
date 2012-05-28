/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;

import java.util.Stack;

/**
 * @author Michael C. Han
 */
public class ServiceContextThreadLocal {

	public static ServiceContext getServiceContext() {
		Stack<ServiceContext> serviceContextStack =
			_serviceContextThreadLocal.get();

		if (serviceContextStack.isEmpty()) {
			return null;
		}

		return serviceContextStack.peek();
	}

	public static ServiceContext popServiceContext() {
		Stack<ServiceContext> serviceContextStack =
			_serviceContextThreadLocal.get();

		if (serviceContextStack.isEmpty()) {
			return null;
		}

		return serviceContextStack.pop();
	}

	public static void pushServiceContext(ServiceContext serviceContext) {
		Stack<ServiceContext> serviceContextStack =
			_serviceContextThreadLocal.get();

		serviceContextStack.push(serviceContext);
	}

	private static ThreadLocal<Stack<ServiceContext>>
		_serviceContextThreadLocal =
			new AutoResetThreadLocal<Stack<ServiceContext>>(
				ServiceContextThreadLocal.class + "._serviceContextThreadLocal",
				new Stack<ServiceContext>()) {

					@Override
					protected Stack<ServiceContext> copy(
						Stack<ServiceContext> serviceContexts) {

						Stack<ServiceContext> cloneServiceContexts =
							new Stack<ServiceContext>();

						for (ServiceContext serviceContext : serviceContexts) {
							ServiceContext cloneServiceContext =
								(ServiceContext)serviceContext.clone();

							cloneServiceContexts.add(cloneServiceContext);
						}

						return cloneServiceContexts;
					}

				};

}