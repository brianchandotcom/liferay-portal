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

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.security.audit.AuditEventManager;
import com.liferay.portal.security.audit.AuditMessageProcessor;
import com.liferay.portal.security.audit.router.configuration.PersistentAuditMessageProcessorConfiguration;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
@Component(
	configurationPid = "com.liferay.portal.security.audit.router.configuration.PersistentAuditMessageProcessorConfiguration",
	immediate = true, property = "eventTypes=*",
	service = AuditMessageProcessor.class
)
public class PersistentAuditMessageProcessor implements AuditMessageProcessor {

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
		PersistentAuditMessageProcessorConfiguration
			messageProcessorConfiguration = ConfigurableUtil.createConfigurable(
				PersistentAuditMessageProcessorConfiguration.class, properties);

		_enabled = false;

		if ((messageProcessorConfiguration != null) &&
			messageProcessorConfiguration.enabled()) {

			_enabled = true;
		}
	}

	protected void doProcess(AuditMessage auditMessage) throws Exception {
		if (!_enabled) {
			return;
		}

		_auditEventManager.addAuditEvent(auditMessage);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PersistentAuditMessageProcessor.class);

	@Reference
	private AuditEventManager _auditEventManager;

	private volatile boolean _enabled;

}