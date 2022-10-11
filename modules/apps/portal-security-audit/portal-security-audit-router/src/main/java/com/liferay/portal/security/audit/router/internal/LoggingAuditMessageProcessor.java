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

package com.liferay.portal.security.audit.router.internal;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.audit.AuditMessageProcessor;
import com.liferay.portal.security.audit.formatter.LogMessageFormatter;
import com.liferay.portal.security.audit.router.configuration.LoggingAuditMessageProcessorConfiguration;

import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 * @author Brian Greenwald
 * @author Prathima Shreenath
 */
@Component(
	configurationPid = "com.liferay.portal.security.audit.router.configuration.LoggingAuditMessageProcessorConfiguration",
	immediate = true, property = "eventTypes=*",
	service = AuditMessageProcessor.class
)
public class LoggingAuditMessageProcessor implements AuditMessageProcessor {

	@Override
	public void process(AuditMessage auditMessage) {
		try {
			doProcess(auditMessage);
		}
		catch (Exception exception) {
			_log.fatal(
				"Unable to process audit message " + auditMessage, exception);
		}
	}

	@Activate
	@Modified
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		_loggingAuditMessageProcessorConfiguration =
			ConfigurableUtil.createConfigurable(
				LoggingAuditMessageProcessorConfiguration.class, properties);

		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, LogMessageFormatter.class, null,
			(serviceReference, emitter) -> {
				String format = (String)serviceReference.getProperty("format");

				if (Validator.isNull(format)) {
					throw new IllegalArgumentException(
						"The property \"format\" is null");
				}

				emitter.emit(format);
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	protected void doProcess(AuditMessage auditMessage) throws Exception {
		if (_loggingAuditMessageProcessorConfiguration.enabled() &&
			(_log.isInfoEnabled() ||
			 _loggingAuditMessageProcessorConfiguration.outputToConsole())) {

			LogMessageFormatter logMessageFormatter =
				_serviceTrackerMap.getService(
					_loggingAuditMessageProcessorConfiguration.
						logMessageFormat());

			if (logMessageFormatter == null) {
				if (_log.isWarnEnabled()) {
					String logMessageFormat =
						_loggingAuditMessageProcessorConfiguration.
							logMessageFormat();

					_log.warn(
						"No log message formatter found for log message " +
							"format " + logMessageFormat);
				}

				return;
			}

			String logMessage = logMessageFormatter.format(auditMessage);

			if (_log.isInfoEnabled()) {
				_log.info(logMessage);
			}

			if (_loggingAuditMessageProcessorConfiguration.outputToConsole()) {
				System.out.println(
					"LoggingAuditMessageProcessor: " + logMessage);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LoggingAuditMessageProcessor.class);

	private volatile LoggingAuditMessageProcessorConfiguration
		_loggingAuditMessageProcessorConfiguration;
	private volatile ServiceTrackerMap<String, LogMessageFormatter>
		_serviceTrackerMap;

}