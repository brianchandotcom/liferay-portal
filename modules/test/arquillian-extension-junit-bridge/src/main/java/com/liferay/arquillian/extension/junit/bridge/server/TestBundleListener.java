/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.arquillian.extension.junit.bridge.server;

import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;

/**
 * @author Shuyang Zhou
 */
public class TestBundleListener implements BundleListener {

	public TestBundleListener(
		BundleContext systemBundleContext, Bundle testBundle,
		Map<String, List<String>> filteredMethodNamesMap,
		String reportServerHostName, int reportServerPort, long passCode) {

		_systemBundleContext = systemBundleContext;
		_testBundle = testBundle;
		_filteredMethodNamesMap = filteredMethodNamesMap;
		_reportServerHostName = reportServerHostName;
		_reportServerPort = reportServerPort;
		_passCode = passCode;
	}

	@Override
	public void bundleChanged(BundleEvent bundleEvent) {
		Bundle bundle = bundleEvent.getBundle();

		if (!_testBundle.equals(bundle)) {
			return;
		}

		_bundleChanged(bundle);
	}

	private synchronized void _bundleChanged(Bundle bundle) {
		if (bundle.getState() == Bundle.ACTIVE) {
			_testExecutorThread = new Thread(
				new TestExecutorRunnable(
					_testBundle, _filteredMethodNamesMap, _reportServerHostName,
					_reportServerPort, _passCode),
				_testBundle.getSymbolicName() + "-executor-thread");

			_testExecutorThread.setDaemon(true);

			_testExecutorThread.start();

			return;
		}

		if (bundle.getState() <= Bundle.RESOLVED) {
			_systemBundleContext.removeBundleListener(this);

			if (_testExecutorThread != null) {
				_testExecutorThread.interrupt();
			}
		}
	}

	private final Map<String, List<String>> _filteredMethodNamesMap;
	private final long _passCode;
	private final String _reportServerHostName;
	private final int _reportServerPort;
	private final BundleContext _systemBundleContext;
	private final Bundle _testBundle;
	private Thread _testExecutorThread;

}