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

import com.liferay.portal.server.DeepNamedValueScanner;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Igor Spasic
 */
public class JettyServerCapabilities implements ServerCapabilities {

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
			"_scanInterval");

		deepNamedValueScanner.setExcludedClassNames("WebAppProvider");
		deepNamedValueScanner.setIncludedClassNames("org.eclipse.jetty");
		deepNamedValueScanner.setVisitLists(true);

		deepNamedValueScanner.scan(servletContext);

		Integer scanInterval = (Integer)deepNamedValueScanner.getMatchedValue();

		if ((scanInterval != null) && (scanInterval.intValue() > 0)) {
			_supportsHotDeploy = true;
		}
		else {
			_supportsHotDeploy = false;
		}
	}

	private boolean _supportsHotDeploy;

}