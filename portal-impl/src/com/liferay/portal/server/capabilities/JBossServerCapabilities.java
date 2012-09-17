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
 * @author Igor Spasic
 */
public class JBossServerCapabilities implements ServerCapabilities  {

	public void determine(ServletContext servletContext) throws Exception {
		determineSupportsHotDeploy(servletContext);
	}

	public boolean isSupportsHotDeploy() {
		return _supportsHotDeploy;
	}

	protected void determineSupportsHotDeploy(ServletContext servletContext)
		throws Exception {

		DeepNamedValueScanner deepNamedValueScanner =
			new DeepNamedValueScanner("scanEnabled", true);

		deepNamedValueScanner.setVisitArrays(true);

		deepNamedValueScanner.setVisitMaps(true);

		deepNamedValueScanner.setIncludedClassNames(
			"org.apache.", "org.jboss.");

		deepNamedValueScanner.setExcludedClassNames(
			"ChainedInterceptorFactory", "TagAttributeInfo", ".jandex.",
			".vfs.");

		deepNamedValueScanner.setExcludedNames("serialversion");

		deepNamedValueScanner.scan(servletContext);

		if (_log.isDebugEnabled()) {
			_log.debug("HotDeploy founded: " +
				deepNamedValueScanner.isFounded() + " in " +
				deepNamedValueScanner.getElapsed() + "ms by " +
				deepNamedValueScanner.getMatchingCount() + " matches");
		}

		Boolean scanEnabledValue =
			(Boolean)deepNamedValueScanner.getMatchedValue();

		if (scanEnabledValue == null) {
			_supportsHotDeploy = false;
		}
		else {
			_supportsHotDeploy = scanEnabledValue.booleanValue();
		}
	}


	private static Log _log = LogFactoryUtil.getLog(
		JBossServerCapabilities.class);

	private boolean _supportsHotDeploy;

}