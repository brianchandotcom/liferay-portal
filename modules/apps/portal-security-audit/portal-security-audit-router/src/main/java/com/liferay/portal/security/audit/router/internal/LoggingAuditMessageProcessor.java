/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.security.audit.router.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.audit.AuditMessageProcessor;
import com.liferay.portal.security.audit.router.LogMessageFormatter;
import com.liferay.portal.security.audit.router.configuration.LoggingAuditMessageProcessorConfiguration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

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
		catch (Exception e) {
			_log.fatal("Unable to process audit message " + auditMessage, e);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_loggingAuditMessageProcessorConfiguration =
			ConfigurableUtil.createConfigurable(
				LoggingAuditMessageProcessorConfiguration.class, properties);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeLogMessageFormatter"
	)
	protected void addLogMessageFormatter(
		LogMessageFormatter logMessageFormatter,
		Map<String, Object> properties) {

		String format = (String)properties.get("format");

		if (Validator.isNull(format)) {
			throw new IllegalArgumentException(
				"The property \"format\" is null");
		}

		_logMessageFormatters.put(format, logMessageFormatter);
	}

	protected void doProcess(AuditMessage auditMessage) throws Exception {
		if (_loggingAuditMessageProcessorConfiguration.enabled() &&
			(_log.isInfoEnabled() ||
			 _loggingAuditMessageProcessorConfiguration.outputToConsole())) {

			LogMessageFormatter logMessageFormatter = _logMessageFormatters.get(
				_loggingAuditMessageProcessorConfiguration.logMessageFormat());

			if (logMessageFormatter == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"No log message formatter found for log message " +
							"format " +
								_loggingAuditMessageProcessorConfiguration.
									logMessageFormat());
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

	protected void removeLogMessageFormatter(
		LogMessageFormatter logMessageFormatter,
		Map<String, Object> properties) {

		String format = (String)properties.get("format");

		if (Validator.isNull(format)) {
			throw new IllegalArgumentException(
				"The property \"format\" is null");
		}

		_logMessageFormatters.remove(format);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LoggingAuditMessageProcessor.class);

	private volatile LoggingAuditMessageProcessorConfiguration
		_loggingAuditMessageProcessorConfiguration;
	private final Map<String, LogMessageFormatter> _logMessageFormatters =
		new ConcurrentHashMap<>();

}