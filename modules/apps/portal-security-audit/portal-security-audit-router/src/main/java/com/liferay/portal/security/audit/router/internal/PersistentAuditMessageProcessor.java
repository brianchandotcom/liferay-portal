/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.audit.router.internal;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.audit.AuditMessage;
import com.liferay.portal.kernel.change.tracking.CTTransactionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.NamedThreadFactory;
import com.liferay.portal.security.audit.AuditEventManager;
import com.liferay.portal.security.audit.AuditMessageProcessor;
import com.liferay.portal.security.audit.router.configuration.PersistentAuditMessageProcessorConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 * @author Brian Wing Shun Chan
 */
@Component(
	configurationPid = "com.liferay.portal.security.audit.router.configuration.PersistentAuditMessageProcessorConfiguration",
	property = "eventTypes=*", service = AuditMessageProcessor.class
)
public class PersistentAuditMessageProcessor implements AuditMessageProcessor {

	@Override
	public void process(AuditMessage auditMessage) {
		try {
			doProcess(auditMessage);
		}
		catch (CTTransactionException ctTransactionException) {
			throw ctTransactionException;
		}
		catch (Exception exception) {
			_log.fatal(
				"Unable to process audit message " + auditMessage, exception);
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		_scheduledExecutorService = new ScheduledThreadPoolExecutor(
			1,
			new NamedThreadFactory(
				PersistentAuditMessageProcessor.class.getName(),
				Thread.NORM_PRIORITY, null));

		modified(properties);
	}

	@Deactivate
	protected void deactivate() {
		_scheduledExecutorService.shutdown();

		_flush();
	}

	protected void doProcess(AuditMessage auditMessage) throws Exception {
		PersistentAuditMessageProcessorConfiguration
			persistentAuditMessageProcessorConfiguration =
				_persistentAuditMessageProcessorConfiguration;

		if (!persistentAuditMessageProcessorConfiguration.enabled()) {
			return;
		}

		_queue.add(auditMessage);

		int size = _queueSize.incrementAndGet();

		if (size >= persistentAuditMessageProcessorConfiguration.bufferSize()) {
			_flush();
		}
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		_persistentAuditMessageProcessorConfiguration =
			ConfigurableUtil.createConfigurable(
				PersistentAuditMessageProcessorConfiguration.class, properties);

		synchronized (this) {
			if (_scheduledFuture != null) {
				_scheduledFuture.cancel(false);
			}

			long flushInterval =
				_persistentAuditMessageProcessorConfiguration.flushInterval();

			if (flushInterval > 0) {
				_scheduledFuture =
					_scheduledExecutorService.scheduleWithFixedDelay(
						this::_flush, flushInterval, flushInterval,
						TimeUnit.MILLISECONDS);
			}
			else {
				_scheduledFuture = null;
			}
		}
	}

	private void _flush() {
		List<AuditMessage> auditMessages = new ArrayList<>();

		AuditMessage auditMessage = null;

		while ((auditMessage = _queue.poll()) != null) {
			auditMessages.add(auditMessage);
		}

		int flushedSize = auditMessages.size();

		if (flushedSize > 0) {
			int size = _queueSize.get();

			while (!_queueSize.compareAndSet(size, size - flushedSize)) {
				size = _queueSize.get();
			}

			_auditEventManager.addAuditEvents(auditMessages);

			if (_log.isDebugEnabled()) {
				_log.debug("Bulk flushed AuditMessage count " + flushedSize);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PersistentAuditMessageProcessor.class);

	@Reference
	private AuditEventManager _auditEventManager;

	private volatile PersistentAuditMessageProcessorConfiguration
		_persistentAuditMessageProcessorConfiguration;
	private final Queue<AuditMessage> _queue = new ConcurrentLinkedQueue<>();
	private final AtomicInteger _queueSize = new AtomicInteger();
	private ScheduledExecutorService _scheduledExecutorService;
	private volatile ScheduledFuture<?> _scheduledFuture;

}