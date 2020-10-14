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

package com.liferay.portlet.documentlibrary.antivirus;

import com.liferay.document.library.kernel.antivirus.AntivirusScanner;
import com.liferay.document.library.kernel.antivirus.AntivirusScannerException;
import com.liferay.document.library.kernel.antivirus.AntivirusScannerWrapper;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.ServiceProxyFactory;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import java.io.File;

/**
 * @author Brian Wing Shun Chan
 */
public class AntivirusScannerImpl extends AntivirusScannerWrapper {

	public AntivirusScannerImpl() {
		super(null);
	}

	public void afterPropertiesSet() {
		if (Validator.isNull(PropsValues.DL_STORE_ANTIVIRUS_IMPL)) {
			return;
		}

		Registry registry = RegistryUtil.getRegistry();

		_serviceRegistration = registry.registerService(
			AntivirusScanner.class,
			(AntivirusScanner)InstancePool.get(
				PropsValues.DL_STORE_ANTIVIRUS_IMPL));
	}

	public void destroy() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	@Override
	public boolean isActive() {
		return _antivirusScanner.isActive();
	}

	@Override
	public void scan(byte[] bytes) throws AntivirusScannerException {
		_antivirusScanner.scan(bytes);
	}

	@Override
	public void scan(File file) throws AntivirusScannerException {
		_antivirusScanner.scan(file);
	}

	private static volatile AntivirusScanner _antivirusScanner =
		ServiceProxyFactory.newServiceTrackedInstance(
			AntivirusScanner.class, AntivirusScannerImpl.class,
			"_antivirusScanner", false);

	private ServiceRegistration<AntivirusScanner> _serviceRegistration;

}