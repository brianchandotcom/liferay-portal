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

package com.liferay.portal.verifier.tracker;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.xml.DocumentException;
import com.liferay.portal.verify.VerifyException;
import com.liferay.portal.verify.VerifyProcess;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Miguel Pastor
 */
public class VerifyProcessTracker
	extends ServiceTracker<VerifyProcess, VerifyProcess> {

	public VerifyProcessTracker(BundleContext bundleContext)
		throws DocumentException {

		super(bundleContext, VerifyProcess.class, null);

		_bundleContext = bundleContext;
	}

	@Override
	public VerifyProcess addingService(
		ServiceReference<VerifyProcess> serviceReference) {

		VerifyProcess verifyProcess = _addVerifyProcess(serviceReference);

		_execute(verifyProcess);

		return verifyProcess;
	}

	@Override
	public void modifiedService(
		ServiceReference<VerifyProcess> serviceReference,
		VerifyProcess verifyProcess) {

		addingService(serviceReference);
	}

	@Override
	public void removedService(
		ServiceReference<VerifyProcess> serviceReference,
		VerifyProcess verifyProcess) {

		_removeVerifyProcess(serviceReference);
	}

	private VerifyProcess _addVerifyProcess(
		ServiceReference<VerifyProcess> serviceReference) {

		VerifyProcess verifyProcess = _bundleContext.getService(
			serviceReference);

		String verifyProcessName = _getVerifyProcessName(serviceReference);

		_verifyProcesses.put(verifyProcessName, verifyProcess);

		return verifyProcess;
	}

	private void _execute(VerifyProcess verifyProcess) {
		if (_log.isDebugEnabled()) {
			Bundle bundle = _bundleContext.getBundle();

			_log.debug(
				"Executing verify process " + verifyProcess.getClass() +
					" defined by " + bundle.getSymbolicName());
		}

		try {
			verifyProcess.verify();
		}
		catch (VerifyException ve) {
			if (_log.isErrorEnabled()) {
				_log.error(
					"An error has occurred while executing the verifier " +
						verifyProcess.getClass(), ve);
			}
		}
	}

	private String _getVerifyProcessName(
		ServiceReference<VerifyProcess> serviceReference) {

		String verifyProcessName = (String)serviceReference.getProperty(
			"verify.process.name");

		if (verifyProcessName == null || verifyProcessName.equals("")) {
			throw new IllegalArgumentException(
				"Verify processes must define the property " +
					"\"verify.process.name\"");
		}

		return verifyProcessName;
	}

	private void _removeVerifyProcess(
		ServiceReference<VerifyProcess> serviceReference) {

		String verifyProcessName = _getVerifyProcessName(serviceReference);

		_verifyProcesses.remove(verifyProcessName);
	}

	private static Log _log = LogFactoryUtil.getLog(VerifyProcessTracker.class);

	private BundleContext _bundleContext;
	private Map<String, VerifyProcess> _verifyProcesses =
		new ConcurrentHashMap<String, VerifyProcess>();

}