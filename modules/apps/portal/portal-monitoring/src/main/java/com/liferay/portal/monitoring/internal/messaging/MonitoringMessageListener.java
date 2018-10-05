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

package com.liferay.portal.monitoring.internal.messaging;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.monitoring.DataSample;
import com.liferay.portal.kernel.monitoring.DataSampleProcessor;
import com.liferay.portal.kernel.monitoring.Level;
import com.liferay.portal.kernel.monitoring.MonitoringControl;
import com.liferay.portal.kernel.monitoring.MonitoringException;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false, immediate = true,
	property = "destination.name=" + DestinationNames.MONITORING,
	service = {MessageListener.class, MonitoringControl.class}
)
public class MonitoringMessageListener
	extends BaseMessageListener implements MonitoringControl {

	@Override
	public Level getLevel(String namespace) {
		Level level = _levels.get(namespace);

		if (level == null) {
			return Level.OFF;
		}

		return level;
	}

	@Override
	public Set<String> getNamespaces() {
		return _levels.keySet();
	}

	public void processDataSample(DataSample dataSample)
		throws MonitoringException {

		String namespace = dataSample.getNamespace();

		Level level = _levels.get(namespace);

		if ((level != null) && level.equals(Level.OFF)) {
			return;
		}

		List<DataSampleProcessor<DataSample>> dataSampleProcessors =
			_dataSampleProcessors.get(namespace);

		if ((dataSampleProcessors == null) || dataSampleProcessors.isEmpty()) {
			return;
		}

		for (DataSampleProcessor<DataSample> dataSampleProcessor :
				dataSampleProcessors) {

			dataSampleProcessor.processDataSample(dataSample);
		}
	}

	@Override
	public void setLevel(String namespace, Level level) {
		_levels.put(namespace, level);
	}

	public void setLevels(Map<String, String> levels) {
		for (Map.Entry<String, String> entry : levels.entrySet()) {
			String namespace = entry.getKey();

			String levelName = entry.getValue();

			Level level = Level.valueOf(levelName);

			_levels.put(namespace, level);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void doReceive(Message message) throws Exception {
		List<DataSample> dataSamples = (List<DataSample>)message.getPayload();

		if (ListUtil.isNotEmpty(dataSamples)) {
			for (DataSample dataSample : dataSamples) {
				processDataSample(dataSample);
			}
		}
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected synchronized void registerDataSampleProcessor(
		DataSampleProcessor<DataSample> dataSampleProcessor,
		Map<String, Object> properties) {

		String namespace = (String)properties.get("namespace");

		if (Validator.isNull(namespace)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No namespace defined for service " +
						dataSampleProcessor.getClass());
			}

			return;
		}

		List<DataSampleProcessor<DataSample>> dataSampleProcessors =
			_dataSampleProcessors.get(namespace);

		if (dataSampleProcessors == null) {
			dataSampleProcessors = new ArrayList<>();

			_dataSampleProcessors.put(namespace, dataSampleProcessors);
		}

		dataSampleProcessors.add(dataSampleProcessor);
	}

	@Reference(
		target = "(destination.name=" + DestinationNames.MONITORING + ")",
		unbind = "-"
	)
	protected void setDestination(Destination destination) {
	}

	protected synchronized void unregisterDataSampleProcessor(
		DataSampleProcessor<DataSample> dataSampleProcessor,
		Map<String, Object> properties) {

		String namespace = (String)properties.get("namespace");

		if (Validator.isNull(namespace)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No namespace defined for service " +
						dataSampleProcessor.getClass());
			}

			return;
		}

		List<DataSampleProcessor<DataSample>> dataSampleProcessors =
			_dataSampleProcessors.get(namespace);

		if (dataSampleProcessors != null) {
			dataSampleProcessors.remove(dataSampleProcessor);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MonitoringMessageListener.class);

	private final Map<String, List<DataSampleProcessor<DataSample>>>
		_dataSampleProcessors = new ConcurrentHashMap<>();
	private final Map<String, Level> _levels = new ConcurrentHashMap<>();

}