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

package com.liferay.portal.security.pacl;

/**
 * @author Raymond Augé
 */
public class PACLPolicyThreadLocal {

	public static PACLPolicy get() {
		return _paclPolicy.get();
	}

	public static void set(PACLPolicy paclPolicy) {
		_paclPolicy.set(paclPolicy);
	}

	private static final ThreadLocal<PACLPolicy> _paclPolicy =
		new ThreadLocal<PACLPolicy>();

}