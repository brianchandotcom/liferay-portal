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

package com.liferay.portal.remote.jaxrs.whiteboard.internal.util;

import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.AopInvocationHandler;

import java.lang.reflect.InvocationHandler;

import org.apache.cxf.common.util.ClassHelper;

/**
 * @author Shuyang Zhou
 */
public class LiferayClassHelper extends ClassHelper {

	protected Object getRealObjectInternal(Object o) {
		if (ProxyUtil.isProxyClass(o.getClass())) {
			InvocationHandler invocationHandler =
				ProxyUtil.getInvocationHandler(o);

			if (!(invocationHandler instanceof AopInvocationHandler)) {
				return invocationHandler;
			}
		}

		return o;
	}

}