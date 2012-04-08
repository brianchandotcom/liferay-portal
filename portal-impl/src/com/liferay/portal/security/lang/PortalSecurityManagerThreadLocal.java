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

package com.liferay.portal.security.lang;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.security.pacl.PACLPolicy;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalSecurityManagerThreadLocal {

	public static PACLPolicy getPACLPolicy() {
		return _paclPolicy.get();
	}

	public static boolean isEnabled() {
		return _enabled.get();
	}

	public static void setEnabled(boolean enabled) {
		_enabled.set(enabled);
	}

	public static void setPACLPolicy(PACLPolicy paclPolicy) {
		_paclPolicy.set(paclPolicy);
	}

	private static ThreadLocal<Boolean> _enabled =
		new AutoResetThreadLocal<Boolean>(
			PortalSecurityManagerThreadLocal.class + "._enabled", true);
	private static ThreadLocal<PACLPolicy> _paclPolicy =
		new AutoResetThreadLocal<PACLPolicy>(
			PortalSecurityManagerThreadLocal.class + "._paclPolicy");

}