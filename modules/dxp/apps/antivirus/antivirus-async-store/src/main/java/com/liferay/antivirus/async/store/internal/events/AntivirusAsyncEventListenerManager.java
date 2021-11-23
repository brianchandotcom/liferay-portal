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

package com.liferay.antivirus.async.store.internal.events;

import com.liferay.antivirus.async.store.events.AntivirusAsyncEvent;
import com.liferay.antivirus.async.store.events.AntivirusAsyncEventListener;
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
			_SERVICE_REFERENCE_MAPPER);
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
				listener -> listener.receive(message));
		}

		antivirusAsyncEventListeners = _serviceTrackerMap.getService(
			_ANY_CLASS);

		if (antivirusAsyncEventListeners != null) {
			antivirusAsyncEventListeners.forEach(
				listener -> listener.receive(message));
		}
	}

	private static final String _ANY_CLASS = "<ANY_CLASS>";

	private static final ServiceReferenceMapper
		<String, AntivirusAsyncEventListener> _SERVICE_REFERENCE_MAPPER =
			(serviceReference, emitter) -> {
				List<String> classNames = StringUtil.asList(
					serviceReference.getProperty("class.name"));

				for (String className : classNames) {
					emitter.emit(className);
				}

				if (classNames.isEmpty()) {
					emitter.emit(_ANY_CLASS);
				}
			};

	private ServiceTrackerMap<String, List<AntivirusAsyncEventListener>>
		_serviceTrackerMap;

}