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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.audit.AuditException;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.audit.AuditRouter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.proxy.ProxyMessageListener;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.audit.AuditMessageProcessor;
import com.liferay.portal.security.audit.configuration.AuditConfiguration;
import com.liferay.portal.security.audit.router.internal.constants.AuditConstants;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 * @author Brian Greenwald
 * @author Prathima Shreenath
 */
@Component(
	configurationPid = "com.liferay.portal.security.audit.configuration.AuditConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	service = DefaultAuditRouter.class
)
public class DefaultAuditRouter implements AuditRouter {

	@Override
	public boolean isDeployed() {
		int auditMessageProcessorsCount = _auditMessageProcessors.size();

		if ((auditMessageProcessorsCount > 0) ||
			!_globalAuditMessageProcessors.isEmpty()) {

			return true;
		}

		return false;
	}

	@Override
	public void route(AuditMessage auditMessage) throws AuditException {
		if (!_auditEnabled) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Audit disabled, not processing message: " + auditMessage);
			}

			return;
		}

		for (AuditMessageProcessor globalAuditMessageProcessor :
				_globalAuditMessageProcessors) {

			globalAuditMessageProcessor.process(auditMessage);
		}

		String eventType = auditMessage.getEventType();

		Set<AuditMessageProcessor> auditMessageProcessors =
			_auditMessageProcessors.get(eventType);

		if (auditMessageProcessors != null) {
			for (AuditMessageProcessor auditMessageProcessor :
					auditMessageProcessors) {

				auditMessageProcessor.process(auditMessage);
			}
		}
	}

	@Activate
	protected void activate(
		BundleContext bundleContext, Map<String, Object> properties) {

		modified(properties);

		ProxyMessageListener proxyMessageListener = new ProxyMessageListener();

		proxyMessageListener.setManager(this);
		proxyMessageListener.setMessageBus(_messageBus);

		Dictionary<String, Object> proxyMessageListenerProperties =
			new HashMapDictionary<>();

		proxyMessageListenerProperties.put(
			"destination.name", DestinationNames.AUDIT);

		_serviceRegistration = bundleContext.registerService(
			ProxyMessageListener.class, proxyMessageListener,
			proxyMessageListenerProperties);
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceRegistration != null) {
			_serviceRegistration.unregister();
		}
	}

	protected String[] getEventTypes(
		AuditMessageProcessor auditMessageProcessor,
		Map<String, Object> properties) {

		String eventTypes = (String)properties.get(AuditConstants.EVENT_TYPES);

		if (Validator.isNull(eventTypes)) {
			throw new IllegalArgumentException(
				"The property \"" + AuditConstants.EVENT_TYPES + "\" is null");
		}

		return StringUtil.split(eventTypes);
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		AuditConfiguration auditConfiguration =
			ConfigurableUtil.createConfigurable(
				AuditConfiguration.class, properties);

		_auditEnabled = auditConfiguration.enabled();
	}

	@Reference (
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setAuditMessageProcessor(
		AuditMessageProcessor auditMessageProcessor,
		Map<String, Object> properties) {

		String[] eventTypes = getEventTypes(auditMessageProcessor, properties);

		if ((eventTypes.length == 1) && eventTypes[0].equals(StringPool.STAR)) {
			_globalAuditMessageProcessors.add(auditMessageProcessor);

			return;
		}

		for (String eventType : eventTypes) {
			Set<AuditMessageProcessor> auditMessageProcessorsSet =
				_auditMessageProcessors.get(eventType);

			if (auditMessageProcessorsSet == null) {
				auditMessageProcessorsSet = new HashSet<>();

				_auditMessageProcessors.put(
					eventType, auditMessageProcessorsSet);
			}

			auditMessageProcessorsSet.add(auditMessageProcessor);
		}
	}

	protected void unsetAuditMessageProcessor(
		AuditMessageProcessor auditMessageProcessor,
		Map<String, Object> properties) {

		String[] eventTypes = getEventTypes(auditMessageProcessor, properties);

		if ((eventTypes.length == 1) && eventTypes[0].equals(StringPool.STAR)) {
			_globalAuditMessageProcessors.remove(auditMessageProcessor);

			return;
		}

		for (String eventType : eventTypes) {
			Set<AuditMessageProcessor> auditMessageProcessorsSet =
				_auditMessageProcessors.get(eventType);

			if (auditMessageProcessorsSet == null) {
				continue;
			}

			auditMessageProcessorsSet.remove(auditMessageProcessor);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		DefaultAuditRouter.class);

	private volatile boolean _auditEnabled;
	private final Map<String, Set<AuditMessageProcessor>>
		_auditMessageProcessors = new ConcurrentHashMap<>();
	private final List<AuditMessageProcessor> _globalAuditMessageProcessors =
		new CopyOnWriteArrayList<>();

	@Reference
	private MessageBus _messageBus;

	private ServiceRegistration<ProxyMessageListener> _serviceRegistration;

}