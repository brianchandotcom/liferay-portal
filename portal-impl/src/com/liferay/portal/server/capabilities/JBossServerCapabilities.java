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

import com.liferay.portal.kernel.util.ServerDetector;
import com.liferay.portal.server.DeepNamedValueScanner;

import javax.servlet.ServletContext;

/**
 * @author Igor Spasic
 */
public class JBossServerCapabilities implements ServerCapabilities {

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

		if (ServerDetector.isJBoss5()) {
			_supportsHotDeploy = true;
		}
		else {
			DeepNamedValueScanner deepNamedValueScanner =
				new DeepNamedValueScanner("scanEnabled", true);

			deepNamedValueScanner.setExcludedClassNames(
				"ChainedInterceptorFactory", "TagAttributeInfo", ".jandex.",
				".vfs.");
			deepNamedValueScanner.setExcludedNames("serialversion");
			deepNamedValueScanner.setIncludedClassNames(
				"org.apache.", "org.jboss.");
			deepNamedValueScanner.setVisitArrays(true);
			deepNamedValueScanner.setVisitMaps(true);

			deepNamedValueScanner.scan(servletContext);

			Boolean scanEnabledValue =
				(Boolean)deepNamedValueScanner.getMatchedValue();

			if (scanEnabledValue == null) {
				_supportsHotDeploy = false;
			}
			else {
				_supportsHotDeploy = scanEnabledValue.booleanValue();
			}
		}
	}

	private boolean _supportsHotDeploy;

}