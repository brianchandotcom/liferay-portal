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

package com.liferay.portal.kernel.aop;

import java.lang.reflect.Method;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides immutable AOP method invocation to by used by {@link
 * ChainableMethodAdvice}.
 *
 * @author Preston Crary
 */
@ProviderType
public interface AopMethodInvocation {

	/**
	 * @return The cached context object defined in {@link
	 *         ChainableMethodAdvice#createMethodContext(Class, Method,
	 *         java.util.Map)}
	 */
	public <T> T getAdviceMethodContext();

	/**
	 * @return the <code>Method</code> being advised for this invocation
	 */
	public Method getMethod();

	/**
	 * @return the <code>Object</code> being advised for this invocation
	 */
	public Object getThis();

	/**
	 * @param  arguments the arguments to use when invoking the method
	 * @return the result of the underlying invocation chain
	 */
	public Object proceed(Object[] arguments) throws Throwable;

}