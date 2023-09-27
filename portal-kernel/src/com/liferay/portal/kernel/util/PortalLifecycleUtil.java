/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.util;

import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.servlet.ServletContextClassLoaderPool;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class PortalLifecycleUtil {

	public static void flushDestroys() {
		_inFlushDestroys = true;

		for (int i = _portalLifecyclesDestroy.size() - 1; i >= 0; i--) {
			PortalLifecycle portalLifecycle = _portalLifecyclesDestroy.get(i);

			portalLifecycle.portalDestroy();
		}

		_portalLifecyclesDestroy.clear();

		_inFlushDestroys = false;
	}

	@SuppressWarnings("deprecation")
	public static void flushInits() {
		if (_portalLifecyclesInit != null) {
			List<PortalLifecycle> portalLifecyclesInit = _portalLifecyclesInit;

			_portalLifecyclesInit = null;

			for (PortalLifecycle portalLifecycle : portalLifecyclesInit) {
				portalLifecycle.portalInit();
			}
		}
	}

	public static void register(PortalLifecycle portalLifecycle) {
		register(portalLifecycle, PortalLifecycle.METHOD_ALL);
	}

	public static void register(PortalLifecycle portalLifecycle, int method) {
		if ((method == PortalLifecycle.METHOD_ALL) ||
			(method == PortalLifecycle.METHOD_INIT)) {

			if (_portalLifecyclesInit == null) {
				Thread currentThread = Thread.currentThread();

				ClassLoader classLoader = currentThread.getContextClassLoader();

				if (PortalClassLoaderUtil.isPortalClassLoader(classLoader) ||
					!HotDeployUtil.registerDependentPortalLifecycle(
						ServletContextClassLoaderPool.getServletContextName(
							classLoader),
						portalLifecycle)) {

					portalLifecycle.portalInit();
				}
			}
			else {
				_portalLifecyclesInit.add(portalLifecycle);
			}
		}

		if ((method == PortalLifecycle.METHOD_ALL) ||
			(method == PortalLifecycle.METHOD_DESTROY)) {

			_portalLifecyclesDestroy.add(portalLifecycle);
		}
	}

	public static void removeDestroy(PortalLifecycle portalLifecycle) {
		if (!_inFlushDestroys) {
			_portalLifecyclesDestroy.remove(portalLifecycle);
		}
	}

	public static void reset() {
		_inFlushDestroys = false;

		if (_portalLifecyclesInit == null) {
			_portalLifecyclesInit = Collections.synchronizedList(
				new ArrayList<PortalLifecycle>());
		}
		else {
			_portalLifecyclesInit.clear();
		}

		_portalLifecyclesDestroy.clear();
	}

	private static boolean _inFlushDestroys;
	private static final List<PortalLifecycle> _portalLifecyclesDestroy =
		Collections.synchronizedList(new ArrayList<PortalLifecycle>());
	private static List<PortalLifecycle> _portalLifecyclesInit =
		Collections.synchronizedList(new ArrayList<PortalLifecycle>());

}