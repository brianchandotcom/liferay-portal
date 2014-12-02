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

package com.liferay.portal.wab.extender.internal;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.wab.extender.internal.event.EventUtil;

import java.util.Dictionary;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.xml.parsers.SAXParserFactory;

import org.apache.felix.utils.extender.AbstractExtender;
import org.apache.felix.utils.extender.Extension;
import org.apache.felix.utils.log.Logger;

import org.eclipse.equinox.http.servlet.ExtendedHttpService;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Miguel Pastor
 * @author Raymond Augé
 */
@Component(
	immediate = true, configurationPid = "com.liferay.portal.wab.extender",
	configurationPolicy = ConfigurationPolicy.OPTIONAL,
	property = {
		"com.liferay.portal.wab.extender.stop.timeout=60000"
	}
)
public class WabFactory extends AbstractExtender {

	@Activate
	public void activate(ComponentContext componentContext) {
		_bundleContext = componentContext.getBundleContext();

		Dictionary<String, Object> properties =
			componentContext.getProperties();

		_stopTimeout = GetterUtil.getLong(
			properties.get("com.liferay.portal.wab.extender.stop.timeout"),
			60000);

		_logger = new Logger(_bundleContext);
		_eventUtil = new EventUtil(_bundleContext);

		try {
			_webBundleDeployer = new WebBundleDeployer(
				_bundleContext, _extendedHttpService, _saxParserFactory,
				_eventUtil, _logger);

			super.start(_bundleContext);
		}
		catch (Exception e) {
			_logger.log(Logger.LOG_ERROR, e.getMessage(), e);
		}
	}

	@Deactivate
	public void deactivate() throws Exception {
		super.stop(_bundleContext);

		_webBundleDeployer.close();
		_eventUtil.close();

		_eventUtil = null;
		_logger = null;
		_bundleContext = null;
		_webBundleDeployer = null;
	}

	@Override
	protected void debug(Bundle bundle, String message) {
		_logger.log(Logger.LOG_DEBUG, "[" + bundle + "] " + message);
	}

	@Override
	protected Extension doCreateExtension(Bundle bundle) throws Exception {
		return new WABExtension(bundle);
	}

	@Override
	protected void error(String message, Throwable t) {
		_logger.log(Logger.LOG_ERROR, message, t);
	}

	@Reference
	protected void setExtendedHttpService(
		ExtendedHttpService extendedHttpService) {

		_extendedHttpService = extendedHttpService;
	}

	@Reference(unbind = "-")
	protected void setSAXParserFactory(SAXParserFactory saxParserFactory) {
		_saxParserFactory = saxParserFactory;

		_saxParserFactory.setValidating(false);
	}

	@Override
	protected void warn(Bundle bundle, String message, Throwable t) {
		_logger.log(Logger.LOG_WARNING, "[" + bundle + "] " + message, t);
	}

	private BundleContext _bundleContext;
	private EventUtil _eventUtil;
	private ExtendedHttpService _extendedHttpService;
	private Logger _logger;
	private SAXParserFactory _saxParserFactory;
	private long _stopTimeout;
	private WebBundleDeployer _webBundleDeployer;

	private class WABExtension implements Extension {

		public WABExtension(Bundle bundle) {
			_bundle = bundle;
			_started = new CountDownLatch(1);
		}

		@Override
		public void destroy() throws Exception {
			try {
				_started.await(_stopTimeout, TimeUnit.MILLISECONDS);
			}
			catch (InterruptedException ie) {
				_logger.log(
					Logger.LOG_ERROR,
					String.format(
						"The wait for bundle {0}/{1} being started " +
							"before destruction has been interrupted.",
						_bundle.getSymbolicName(), _bundle.getBundleId()),
					ie);
			}

			_webBundleDeployer.doStop(_bundle);
		}

		@Override
		public void start() throws Exception {
			try {
				_webBundleDeployer.doStart(_bundle);
			}
			finally {
				_started.countDown();
			}
		}

		private final Bundle _bundle;
		private final CountDownLatch _started;

	}

}