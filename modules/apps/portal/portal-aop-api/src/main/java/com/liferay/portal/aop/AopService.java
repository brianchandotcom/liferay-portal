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

package com.liferay.portal.aop;

/**
 * Interface used to declare a service should be intercepted and proxied by an
 * AOP and re-registered.
 *
 * <p>
 * It is important that services required to be intercepted are only registered
 * as a <code>AopService</code> so service listeners do not see the service
 * before it is proxied.
 * </p>
 *
 * @author Preston Crary
 */
public interface AopService {

	/**
	 * The interfaces under which to register this AopService.
	 *
	 * If null or empty, the service types for this AopService are all the
	 * <i>directly</i> implemented interfaces of the AopService.
	 */
	public default Class<?>[] getAopInterfaces() {
		return null;
	}

	/**
	 * @param aopProxy the enclosing AOP proxy for this service
	 */
	public default void setAopProxy(Object aopProxy) {
	}

}