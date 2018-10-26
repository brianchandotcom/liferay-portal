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

package com.liferay.portal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutCloneFactory {

	public static LayoutClone getInstance() {
		if (_layoutClone == null) {
			if (Validator.isNotNull(PropsValues.LAYOUT_CLONE_IMPL)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Instantiate " + PropsValues.LAYOUT_CLONE_IMPL);
				}

				ClassLoader classLoader =
					PortalClassLoaderUtil.getClassLoader();

				try {
					Class<?> clazz = classLoader.loadClass(
						PropsValues.LAYOUT_CLONE_IMPL);

					_layoutClone = (LayoutClone)clazz.newInstance();
				}
				catch (Exception e) {
					_log.error(e, e);
				}
			}
		}
		else {
			if (_log.isDebugEnabled()) {
				Class<?> clazz = _layoutClone.getClass();

				_log.debug("Return " + clazz.getName());
			}
		}

		return _layoutClone;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutCloneFactory.class);

	private static LayoutClone _layoutClone;

}