/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.server.capabilities;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.server.DeepNamedValueScanner;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Igor Spasic
 */
public class GlassfishServerCapabilities implements ServerCapabilities {

	@Override
	public void determine(ServletContext servletContext) throws Exception {
		determineSupportsHotDeploy(servletContext);
	}

	@Override
	public boolean isSupportsHotDeploy() {
		return _supportsHotDeploy;
	}

	protected void determineSupportsHotDeploy(ServletContext servletContext)
		throws Exception {

		DeepNamedValueScanner deepNamedValueScanner = new DeepNamedValueScanner(
			"masterView");

		deepNamedValueScanner.setExcludedClassNames("org.apache.felix.");
		deepNamedValueScanner.setSkipFirstCount(4);

		deepNamedValueScanner.scan(servletContext);

		if (deepNamedValueScanner.isScanning()) {
			_supportsHotDeploy = false;

			return;
		}

		Object masterViewObject = deepNamedValueScanner.getMatchedValue();

		deepNamedValueScanner = new DeepNamedValueScanner("autodeploy-enabled");

		deepNamedValueScanner.setExcludedClassNames(
			"org.apache.felix.", "CountStatisticImpl");
		deepNamedValueScanner.setSkipFirstCount(2);
		deepNamedValueScanner.setVisitMaps(true);

		deepNamedValueScanner.scan(masterViewObject);

		boolean autoDeployEnabled = true;

		if (!deepNamedValueScanner.isScanning()) {
			autoDeployEnabled = GetterUtil.getBoolean(
				deepNamedValueScanner.getMatchedValue());
		}

		_supportsHotDeploy = autoDeployEnabled;
	}

	private boolean _supportsHotDeploy;

}