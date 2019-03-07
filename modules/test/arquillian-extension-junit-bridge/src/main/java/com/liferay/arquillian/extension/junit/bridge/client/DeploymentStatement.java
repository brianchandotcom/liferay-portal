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

package com.liferay.arquillian.extension.junit.bridge.client;

import java.net.URI;
import java.net.URL;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.runners.model.Statement;

import org.osgi.jmx.framework.FrameworkMBean;

/**
 * @author Shuyang Zhou
 */
public class DeploymentStatement extends Statement {

	public DeploymentStatement(Statement statement) {
		_statement = statement;
	}

	@Override
	public void evaluate() throws Throwable {
		FrameworkMBean frameworkMBean = MBeans.getFrameworkMBean();

		long bundleId = _installBundle(frameworkMBean);

		frameworkMBean.startBundle(bundleId);

		try {
			_statement.evaluate();
		}
		finally {
			frameworkMBean.uninstallBundle(bundleId);
		}
	}

	private long _installBundle(FrameworkMBean frameworkMBean)
		throws Exception {

		Path path = BndBundleUtil.createBundle();

		URI uri = path.toUri();

		URL url = uri.toURL();

		try {
			return frameworkMBean.installBundleFromURL(
				url.getPath(), url.toExternalForm());
		}
		finally {
			Files.delete(path);
		}
	}

	private final Statement _statement;

}