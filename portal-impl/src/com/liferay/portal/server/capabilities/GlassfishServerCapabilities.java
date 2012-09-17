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

package com.liferay.portal.server.capabilities;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.server.DeepNamedValueScanner;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Igor Spasic
 */
public class GlassfishServerCapabilities implements ServerCapabilities {

	public void determine(ServletContext servletContext) throws Exception {
		determineSupportsHotDeploy(servletContext);
	}

	public boolean isSupportsHotDeploy() {
		return _supportsHotDeploy;
	}

	protected void determineSupportsHotDeploy(ServletContext servletContext)
		throws Exception {

		DeepNamedValueScanner deepNamedValueScanner =
					new DeepNamedValueScanner("masterView");

		deepNamedValueScanner.setExcludedClassNames("org.apache.felix.");
		deepNamedValueScanner.setSkipFirstCount(4);

		deepNamedValueScanner.scan(servletContext);

		if (!deepNamedValueScanner.isFounded()) {
			_supportsHotDeploy = false;

			return;
		}

		Object masterViewObject = deepNamedValueScanner.getMatchedValue();

		deepNamedValueScanner = new DeepNamedValueScanner("autodeploy-enabled");

		deepNamedValueScanner.setVisitMaps(true);
		deepNamedValueScanner.setExcludedClassNames(
			"org.apache.felix.", "CountStatisticImpl");
		deepNamedValueScanner.setSkipFirstCount(2);

		deepNamedValueScanner.scan(masterViewObject);

		if (_log.isDebugEnabled()) {
			_log.debug("HotDeploy founded: " +
				deepNamedValueScanner.isFounded() + " in " +
				deepNamedValueScanner.getElapsed() + "ms by " +
				deepNamedValueScanner.getMatchingCount() + " matches");
		}

		boolean autoDeployEnabled = true;

		if (deepNamedValueScanner.isFounded()) {
			autoDeployEnabled = Boolean.parseBoolean(
				deepNamedValueScanner.getMatchedValue().toString());
		}

		_supportsHotDeploy = autoDeployEnabled;
	}

	private static Log _log = LogFactoryUtil.getLog(
		GlassfishServerCapabilities.class);

	private boolean _supportsHotDeploy;

}