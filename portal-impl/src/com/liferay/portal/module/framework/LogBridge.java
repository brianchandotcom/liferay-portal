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

package com.liferay.portal.module.framework;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogReaderService;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * <a href="LogBridge.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class LogBridge
	implements BundleActivator, LogListener,
		ServiceTrackerCustomizer<LogReaderService, LogReaderService> {

	public LogReaderService addingService(
		ServiceReference<LogReaderService> reference) {

		LogReaderService logReaderService = _bundleContext.getService(
			reference);

		logReaderService.addLogListener(this);

		return logReaderService;
	}

	public void modifiedService(
		ServiceReference<LogReaderService> reference,
		LogReaderService service) {
	}

	public void logged(LogEntry entry) {
		Bundle bundle = entry.getBundle();
		int level = entry.getLevel();
		ServiceReference<?> serviceReference = entry.getServiceReference();

		String symbolicName = StringUtil.replace(
			bundle.getSymbolicName(), StringPool.PERIOD, StringPool.UNDERLINE);

		Log log = LogFactoryUtil.getLog("osgi.logging." + symbolicName);

		StringBuilder sb = new StringBuilder(3);

		sb.append(entry.getMessage());

		if (serviceReference != null) {
			sb.append(" ");
			sb.append(serviceReference.toString());
		}

		if ((level == LogService.LOG_DEBUG) && log.isDebugEnabled()) {
			log.debug(sb.toString(), entry.getException());
		}
		else if ((level == LogService.LOG_ERROR) && log.isErrorEnabled()) {
			log.error(sb.toString(), entry.getException());
		}
		else if ((level == LogService.LOG_INFO) && log.isInfoEnabled()) {
			log.info(sb.toString(), entry.getException());
		}
		else if ((level == LogService.LOG_WARNING) && log.isWarnEnabled()) {
			log.warn(sb.toString(), entry.getException());
		}
	}

	public void removedService(
		ServiceReference<LogReaderService> reference,
		LogReaderService service) {

		service.removeLogListener(this);
	}

	public void start(BundleContext bundleContext) throws Exception {
		_bundleContext = bundleContext;

		_logReaderServiceTracker =
			new ServiceTracker<LogReaderService, LogReaderService>(
				bundleContext, LogReaderService.class, this);

		_logReaderServiceTracker.open();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		_logReaderServiceTracker.close();

		_logReaderServiceTracker = null;
		_bundleContext = null;
	}

	private BundleContext _bundleContext;
	private ServiceTracker<LogReaderService, LogReaderService>
		_logReaderServiceTracker;

}