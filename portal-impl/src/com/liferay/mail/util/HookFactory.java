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
import com.liferay.portal.util.ClassLoaderUtil;
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
			_hook = _originalHook;
		}
		else {
			_hook = hook;
		}
	}

	public void afterPropertiesSet() throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Instantiate " + PropsValues.MAIL_HOOK_IMPL);
		}

		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		_originalHook = (Hook)InstanceFactory.newInstance(
			classLoader, PropsValues.MAIL_HOOK_IMPL);

		_hook = _originalHook;
	}

	private static Log _log = LogFactoryUtil.getLog(HookFactory.class);

	private static volatile Hook _hook;
	private static Hook _originalHook;

}