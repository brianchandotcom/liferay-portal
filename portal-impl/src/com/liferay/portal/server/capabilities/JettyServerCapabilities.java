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
public class JettyServerCapabilities implements ServerCapabilities {

	public void determine(ServletContext servletContext) throws Exception {
		determineSupportsHotDeploy(servletContext);
	}

	public boolean isSupportsHotDeploy() {
		return _supportsHotDeploy;
	}

	protected void determineSupportsHotDeploy(ServletContext servletContext)
		throws Exception {

		DeepNamedValueScanner deepNamedValueScanner =
			new DeepNamedValueScanner("_scanInterval");

		deepNamedValueScanner.setVisitLists(true);

		deepNamedValueScanner.setExcludedClassNames("WebAppProvider");

		deepNamedValueScanner.setIncludedClassNames("org.eclipse.jetty");

		deepNamedValueScanner.scan(servletContext);

		if (_log.isDebugEnabled()) {
			_log.debug("HotDeploy founded: " +
				deepNamedValueScanner.isFounded() + " in " +
				deepNamedValueScanner.getElapsed() + "ms by " +
				deepNamedValueScanner.getMatchingCount() + " matches");
		}

		Integer scanInterval = (Integer)deepNamedValueScanner.getMatchedValue();

		System.out.println("---------> " + scanInterval);


		if ((scanInterval != null) && (scanInterval.intValue() > 0)) {
			_supportsHotDeploy = true;
		}
		else {
			_supportsHotDeploy = false;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		JettyServerCapabilities.class);

	private boolean _supportsHotDeploy;

}