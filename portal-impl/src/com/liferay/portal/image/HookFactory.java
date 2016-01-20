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

package com.liferay.portal.image;

import com.liferay.portal.kernel.image.Hook;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Jorge Ferrer
 */
public class HookFactory {

	public static Hook getInstance() {
		if (_hook == null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Instantiate " + PropsValues.IMAGE_HOOK_IMPL);
			}

			_hook = new DLHook();
		}

		if (_log.isDebugEnabled()) {
			Class<?> clazz = _hook.getClass();

			_log.debug("Return " + clazz.getName());
		}

		return _hook;
	}

	public static void setInstance(Hook hook) {
		if (_log.isDebugEnabled()) {
			Class<?> clazz = hook.getClass();

			_log.debug("Set " + clazz.getName());
		}

		_hook = hook;
	}

	private static final Log _log = LogFactoryUtil.getLog(HookFactory.class);

	private static Hook _hook;

}