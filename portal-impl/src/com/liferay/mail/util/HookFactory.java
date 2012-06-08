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

package com.liferay.mail.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class HookFactory {

	public static Hook getInstance() {
		if (_hook == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Instantiate " + PropsValues.MAIL_HOOK_IMPL);
			}

			ClassLoader classLoader =
				PACLClassLoaderUtil.getPortalClassLoader();

			try {
				_hook = (Hook)classLoader.loadClass(
					PropsValues.MAIL_HOOK_IMPL).newInstance();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Return " + _hook.getClass().getName());
		}

		return _hook;
	}

	public static void setInstance(Hook hook) {
		if (_log.isDebugEnabled()) {
			_log.debug("Set " + hook.getClass().getName());
		}

		_hook = hook;
	}

	private static Log _log = LogFactoryUtil.getLog(HookFactory.class);

	private static Hook _hook;

}