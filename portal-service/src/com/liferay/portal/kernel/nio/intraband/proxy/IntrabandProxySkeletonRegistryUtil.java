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

package com.liferay.portal.kernel.nio.intraband.proxy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Shuyang Zhou
 */
public class IntrabandProxySkeletonRegistryUtil {

	public static IntrabandProxySkeleton get(String skeletonId) {
		return _intrabandProxySkeletons.get(skeletonId);
	}

	public static IntrabandProxySkeleton register(
		String skeletonId, IntrabandProxySkeleton intrabandProxySkeleton) {

		intrabandProxySkeleton =
			AsyncIntrabandProxySkeleton.createAsyncIntrabandProxySkeleton(
				skeletonId, intrabandProxySkeleton);

		return _intrabandProxySkeletons.put(skeletonId, intrabandProxySkeleton);
	}

	public static IntrabandProxySkeleton unregister(String skeletonId) {
		return _intrabandProxySkeletons.remove(skeletonId);
	}

	private static final Map<String, IntrabandProxySkeleton>
		_intrabandProxySkeletons =
			new ConcurrentHashMap<String, IntrabandProxySkeleton>();

}