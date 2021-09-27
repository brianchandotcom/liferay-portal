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
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapper;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

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
		_onEvent(AntivirusAsyncEvent.PROCESSING_ERROR, message, exception);
	}

	public void onSizeExceeded(Message message, Exception exception) {
		_onEvent(AntivirusAsyncEvent.SIZE_EXCEEDED, message, exception);
	}

	public void onSuccess(Message message) {
		_onEvent(AntivirusAsyncEvent.SUCCESS, message);
	}

	public void onVirusFound(
		Message message, Exception exception, String virusName) {

		_onEvent(
			AntivirusAsyncEvent.VIRUS_FOUND, message, exception, virusName);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_missingConsumerServiceTrackerMap = _getServiceTrackerMap(
			bundleContext, AntivirusAsyncEvent.MISSING);
		_prepareConsumerServiceTrackerMap = _getServiceTrackerMap(
			bundleContext, AntivirusAsyncEvent.PREPARE);
		_processingErrorConsumerServiceTrackerMap = _getServiceTrackerMap(
			bundleContext, AntivirusAsyncEvent.PROCESSING_ERROR);
		_sizeExceededConsumerServiceTrackerMap = _getServiceTrackerMap(
			bundleContext, AntivirusAsyncEvent.SIZE_EXCEEDED);
		_successConsumerServiceTrackerMap = _getServiceTrackerMap(
			bundleContext, AntivirusAsyncEvent.SUCCESS);
		_virusFoundConsumerServiceTrackerMap = _getServiceTrackerMap(
			bundleContext, AntivirusAsyncEvent.VIRUS_FOUND);
	}

	@Deactivate
	protected void deactivate() {
		_missingConsumerServiceTrackerMap.close();
		_prepareConsumerServiceTrackerMap.close();
		_processingErrorConsumerServiceTrackerMap.close();
		_sizeExceededConsumerServiceTrackerMap.close();
		_successConsumerServiceTrackerMap.close();
		_virusFoundConsumerServiceTrackerMap.close();
	}

	private List<BiConsumer<String, Map.Entry<Message, Object[]>>>
		_getEventListeners(
			AntivirusAsyncEvent antivirusAsyncEvent, String className) {

		List<BiConsumer<String, Map.Entry<Message, Object[]>>> list =
			Collections.emptyList();

		if (antivirusAsyncEvent == AntivirusAsyncEvent.MISSING) {
			list = _missingConsumerServiceTrackerMap.getService(className);
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.PREPARE) {
			list = _prepareConsumerServiceTrackerMap.getService(className);
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.PROCESSING_ERROR) {
			list = _processingErrorConsumerServiceTrackerMap.getService(
				className);
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.SIZE_EXCEEDED) {
			list = _sizeExceededConsumerServiceTrackerMap.getService(className);
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.SUCCESS) {
			list = _successConsumerServiceTrackerMap.getService(className);
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.VIRUS_FOUND) {
			list = _virusFoundConsumerServiceTrackerMap.getService(className);
		}

		if (list == null) {
			list = Collections.emptyList();
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	private ServiceTrackerMap
		<String, List<BiConsumer<String, Map.Entry<Message, Object[]>>>>
			_getServiceTrackerMap(
				BundleContext bundleContext,
				AntivirusAsyncEvent antivirusAsyncEvent) {

		String eventName = antivirusAsyncEvent.name();

		return ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext,
			(Class<BiConsumer<String, Map.Entry<Message, Object[]>>>)
				(Class<?>)BiConsumer.class,
			StringBundler.concat("(antivirus.async.event=", eventName, ")"),
			_SERVICE_REFERENCE_MAPPER);
	}

	private void _onEvent(
		AntivirusAsyncEvent antivirusAsyncEvent, Message message,
		Object... arguments) {

		String className = message.getString("className");

		List<BiConsumer<String, Map.Entry<Message, Object[]>>> eventListeners =
			new ArrayList<>(_getEventListeners(antivirusAsyncEvent, className));

		eventListeners.addAll(
			_getEventListeners(antivirusAsyncEvent, _ANY_CLASS));

		if (_log.isDebugEnabled()) {
			_log.debug(
				StringBundler.concat(
					"Triggering Antivirus Async event listeners ",
					eventListeners, " for ", antivirusAsyncEvent.name(), " on ",
					message.getValues(), " with arguments ",
					Arrays.toString(arguments)));
		}

		for (BiConsumer<String, Map.Entry<Message, Object[]>> eventListener :
				eventListeners) {

			try {
				eventListener.accept(
					antivirusAsyncEvent.name(),
					new AbstractMap.SimpleEntry<>(message, arguments));
			}
			catch (Throwable throwable) {
				_log.error(
					StringBundler.concat(
						"Error processing Antivirus Async event listener ",
						eventListener, ": "),
					throwable);
			}
		}
	}

	private static final String _ANY_CLASS = "<ANY_CLASS>";

	private static final ServiceReferenceMapper<String, BiConsumer<String, ?>>
		_SERVICE_REFERENCE_MAPPER = (serviceReference, emitter) -> {
			List<String> classNames = StringUtil.asList(
				serviceReference.getProperty("class.name"));

			for (String className : classNames) {
				emitter.emit(className);
			}

			if (classNames.isEmpty()) {
				emitter.emit(_ANY_CLASS);
			}
		};

	private static final Log _log = LogFactoryUtil.getLog(
		AntivirusAsyncEventListenerManager.class);

	private ServiceTrackerMap
		<String, List<BiConsumer<String, Map.Entry<Message, Object[]>>>>
			_missingConsumerServiceTrackerMap;
	private ServiceTrackerMap
		<String, List<BiConsumer<String, Map.Entry<Message, Object[]>>>>
			_prepareConsumerServiceTrackerMap;
	private ServiceTrackerMap
		<String, List<BiConsumer<String, Map.Entry<Message, Object[]>>>>
			_processingErrorConsumerServiceTrackerMap;
	private ServiceTrackerMap
		<String, List<BiConsumer<String, Map.Entry<Message, Object[]>>>>
			_sizeExceededConsumerServiceTrackerMap;
	private ServiceTrackerMap
		<String, List<BiConsumer<String, Map.Entry<Message, Object[]>>>>
			_successConsumerServiceTrackerMap;
	private ServiceTrackerMap
		<String, List<BiConsumer<String, Map.Entry<Message, Object[]>>>>
			_virusFoundConsumerServiceTrackerMap;

}