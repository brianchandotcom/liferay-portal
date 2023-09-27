/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.antivirus.async.store.internal.event;

import com.liferay.antivirus.async.store.event.AntivirusAsyncEvent;
import com.liferay.antivirus.async.store.event.AntivirusAsyncEventListener;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Raymond Augé
 */
@Component(service = AntivirusAsyncEventListenerManager.class)
public class AntivirusAsyncEventListenerManager {

	public void onMissing(Message message) {
		_onEvent(AntivirusAsyncEvent.MISSING, message);
	}

	public void onPrepare(Message message) {
		_onEvent(AntivirusAsyncEvent.PREPARE, message);
	}

	public void onProcessingError(Message message, Exception exception) {
		message.put("exception", exception);

		_onEvent(AntivirusAsyncEvent.PROCESSING_ERROR, message);
	}

	public void onSizeExceeded(Message message, Exception exception) {
		message.put("exception", exception);

		_onEvent(AntivirusAsyncEvent.SIZE_EXCEEDED, message);
	}

	public void onSuccess(Message message) {
		_onEvent(AntivirusAsyncEvent.SUCCESS, message);
	}

	public void onVirusFound(
		Message message, Exception exception, String virusName) {

		message.put("exception", exception);
		message.put("virusName", virusName);

		_onEvent(AntivirusAsyncEvent.VIRUS_FOUND, message);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext, AntivirusAsyncEventListener.class, null,
			_serviceReferenceMapper);
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private void _onEvent(
		AntivirusAsyncEvent antivirusAsyncEvent, Message message) {

		message.put("antivirusAsyncEvent", antivirusAsyncEvent);

		String className = message.getString("className");

		List<AntivirusAsyncEventListener> antivirusAsyncEventListeners =
			_serviceTrackerMap.getService(className);

		if (antivirusAsyncEventListeners != null) {
			antivirusAsyncEventListeners.forEach(
				antivirusAsyncEventListener ->
					antivirusAsyncEventListener.receive(message));
		}

		antivirusAsyncEventListeners = _serviceTrackerMap.getService(
			_CLASS_NAME_ANY);

		if (antivirusAsyncEventListeners != null) {
			antivirusAsyncEventListeners.forEach(
				listener -> listener.receive(message));
		}
	}

	private static final String _CLASS_NAME_ANY = "<ANY>";

	private static final ServiceReferenceMapper
		<String, AntivirusAsyncEventListener> _serviceReferenceMapper =
			(serviceReference, emitter) -> {
				List<String> classNames = StringUtil.asList(
					serviceReference.getProperty("class.name"));

				for (String className : classNames) {
					emitter.emit(className);
				}

				if (classNames.isEmpty()) {
					emitter.emit(_CLASS_NAME_ANY);
				}
			};

	private ServiceTrackerMap<String, List<AntivirusAsyncEventListener>>
		_serviceTrackerMap;

}