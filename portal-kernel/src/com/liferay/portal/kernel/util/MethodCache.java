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

package com.liferay.portal.kernel.util;

import java.lang.reflect.Method;

/**
 * @author     Michael C. Han
 * @author     Shuyang Zhou
 * @deprecated As of Judson (7.1.x), replaced by {@link MethodKey}
 */
@Deprecated
public class MethodCache {

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             MethodKey#resetCache()}
	 */
	@Deprecated
	public static void reset() {
		MethodKey.resetCache();
	}

	/**
	 * @see MethodKey
	 */
	protected static Method get(MethodKey methodKey)
		throws NoSuchMethodException {

		return methodKey.getMethod();
	}

}