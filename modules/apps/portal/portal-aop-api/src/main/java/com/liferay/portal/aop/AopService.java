/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.aop;

/**
 * Declares implementations to be proxied and intercepted by advices. The
 * service is re-registered as interfaces it implements.
 *
 * <p>
 * <strong>Important:</strong> A service implementation should only register as
 * an <code>AopService</code> so that service listeners only see the service
 * proxy.
 * </p>
 *
 * @author Preston Crary
 */
public interface AopService {

	/**
	 * Returns the services that the service proxy registers into OSGi for this
	 * <code>AopService</code>.
	 *
	 * <p>
	 * If the method is not overridden, all the services this
	 * <code>AopService</code> directly implements, except
	 * <code>AopService</code>, are returned and registered into OSGi by the
	 * service proxy.
	 * </p>
	 *
	 * <p>
	 * Here are the restraints:
	 * </p>
	 *
	 * <ul>
	 * <li>
	 * The services cannot include <code>AopService</code>
	 * </li>
	 * <li>
	 * The service list must not change
	 * </li>
	 * <li>
	 * The service list can only contain interfaces
	 * </li>
	 * <li>
	 * This <code>AopService</code> implementation must implement each service
	 * interface
	 * </li>
	 * <li>
	 * This <code>AopService</code> implementation must implement something in
	 * addition to <code>AopService</code>
	 * </li>
	 * </ul>
	 */
	public default Class<?>[] getAopInterfaces() {
		return null;
	}

	/**
	 * Sets this service's enclosing AOP proxy.
	 *
	 * @param aopProxy this service's enclosing AOP proxy
	 */
	public default void setAopProxy(Object aopProxy) {
	}

}