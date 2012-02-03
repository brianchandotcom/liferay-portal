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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

/**
 * @author Brian Wing Shun Chan
 */
public class StoreFactory {

	public static void checkProperties() {
		if (_warned) {
			return;
		}

		String dlHookImpl = PropsUtil.get("dl.hook.impl");

		if (Validator.isNotNull(dlHookImpl)) {
			boolean found = false;

			for (String[] dlHookStoreParts : _DL_HOOK_STORES) {
				if (dlHookImpl.equals(dlHookStoreParts[0])) {
					PropsValues.DL_STORE_IMPL = dlHookStoreParts[1];

					found = true;

					break;
				}
			}

			if (!found) {
				PropsValues.DL_STORE_IMPL = dlHookImpl;
			}

			if (_log.isWarnEnabled()) {
				StringBundler sb = new StringBundler(8);

				sb.append("Liferay is configured with the legacy ");
				sb.append("property \"dl.hook.impl=" + dlHookImpl + "\" ");
				sb.append("in portal-ext.properties. Please reconfigure ");
				sb.append("to use the new property \"");
				sb.append(PropsKeys.DL_STORE_IMPL + "\". Liferay will ");
				sb.append("attempt to temporarily set \"");
				sb.append(PropsKeys.DL_STORE_IMPL + "=");
				sb.append(PropsValues.DL_STORE_IMPL + "\".");

				_log.warn(sb.toString());
			}
		}

		_warned = true;
	}

	public static Store getInstance() {
		if (_store == null) {
			checkProperties();

			if (_log.isDebugEnabled()) {
				_log.debug("Instantiate " + PropsValues.DL_STORE_IMPL);
			}

			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			try {
				_store = (Store)classLoader.loadClass(
					PropsValues.DL_STORE_IMPL).newInstance();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Return " + _store.getClass().getName());
		}

		return _store;
	}

	public static void setInstance(Store store) {
		if (_log.isDebugEnabled()) {
			_log.debug("Set " + store.getClass().getName());
		}

		_store = store;
	}

	private static final String[][] _DL_HOOK_STORES = new String[][] {
		new String[] {
			"com.liferay.documentlibrary.util.AdvancedFileSystemHook",
			AdvancedFileSystemStore.class.getName()
		},
		new String[] {
			"com.liferay.documentlibrary.util.CMISHook",
			CMISStore.class.getName()
		},
		new String[] {
			"com.liferay.documentlibrary.util.FileSystemHook",
			FileSystemStore.class.getName()
		},
		new String[] {
			"com.liferay.documentlibrary.util.JCRHook", JCRStore.class.getName()
		},
		new String[] {
			"com.liferay.documentlibrary.util.S3Hook", S3Store.class.getName()
		}
	};

	private static Log _log = LogFactoryUtil.getLog(StoreFactory.class);

	private static Store _store;
	private static boolean _warned;

}