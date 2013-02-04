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
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class HookFactory {

	public static Hook getInstance() {
		return _hook;
	}

	public static void setInstance(Hook hook) {
		if (_log.isDebugEnabled()) {
			_log.debug("Set " + ClassUtil.getClassName(hook));
		}

		if (hook == null) {
			hook = _createHook();
		}

		_hook = hook;
	}

	public void afterPropertiesSet() {
		_hook = _createHook();
	}

	private static Hook _createHook() {
		if (_log.isDebugEnabled()) {
			_log.debug("Instantiate " + PropsValues.MAIL_HOOK_IMPL);
		}

		Hook hook = null;

		ClassLoader classLoader = PACLClassLoaderUtil.getPortalClassLoader();

		try {
			hook = (Hook)InstanceFactory.newInstance(
				classLoader, PropsValues.MAIL_HOOK_IMPL);
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return hook;
	}

	private static Log _log = LogFactoryUtil.getLog(HookFactory.class);

	private static volatile Hook _hook;

}