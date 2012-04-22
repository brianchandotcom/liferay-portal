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

package com.liferay.portal.jndi.pacl;

import com.liferay.portal.security.pacl.PACLPolicy;

import java.lang.Object;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.naming.Context;
import javax.naming.Name;

/**
 * @author Brian Wing Shun Chan
 */
public class PACLContextHandler implements InvocationHandler {

	public PACLContextHandler(Context context, PACLPolicy paclPolicy) {
		_context = context;
		_paclPolicy = paclPolicy;
	}

	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		try {
			String methodName = method.getName();

			if (methodName.equals("equals")) {
				if (proxy == arguments[0]) {
					return true;
				}
				else {
					return false;
				}
			}
			else if (methodName.equals("hashCode")) {
				return System.identityHashCode(proxy);
			}
			else if (methodName.equals("bind") ||
					 methodName.equals("composeName") ||
					 methodName.equals("createSubcontext") ||
					 methodName.equals("destroySubcontext") ||
					 methodName.equals("getNameParser") ||
					 methodName.equals("list") ||
					 methodName.equals("listBindings") ||
					 methodName.equals("lookup") ||
					 methodName.equals("lookupLink") ||
					 methodName.equals("rebind") ||
					 methodName.equals("rename") ||
					 methodName.equals("unbind")) {

				String name = null;

				Object argument = arguments[0];

				if (argument instanceof Name) {
					Name nameObject = (Name)argument;

					name = nameObject.toString();
				}
				else {
					name = (String)argument;
				}

				if (!_paclPolicy.hasJNDI(name)) {
					throw new SecurityException(
						"Attempted to use unapproved JNDI " + name);
				}
			}

			Object returnValue = method.invoke(_context, arguments);

			return returnValue;
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	private Context _context;
	private PACLPolicy _paclPolicy;

}