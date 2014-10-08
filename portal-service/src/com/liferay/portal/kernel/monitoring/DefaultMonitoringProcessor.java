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

package com.liferay.portal.kernel.monitoring;

import com.liferay.portal.kernel.monitoring.statistics.DataSample;
import com.liferay.portal.kernel.monitoring.statistics.DataSampleProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class DefaultMonitoringProcessor
	implements DataSampleProcessor<DataSample>, MonitoringProcessor {

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

	@Override
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

	public void registerDataSampleProcessor(
		String namespace, DataSampleProcessor<DataSample> dataSampleProcessor) {

		List<DataSampleProcessor<DataSample>> dataSampleProcessors =
			_dataSampleProcessors.get(namespace);

		if (dataSampleProcessors == null) {
			dataSampleProcessors =
				new ArrayList<DataSampleProcessor<DataSample>>();

			_dataSampleProcessors.put(namespace, dataSampleProcessors);
		}

		dataSampleProcessors.add(dataSampleProcessor);
	}

	public void setDataSampleProcessors(
		Map<String, List<DataSampleProcessor<DataSample>>>
			dataSampleProcessors) {

		_dataSampleProcessors.putAll(dataSampleProcessors);
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

	public void unregisterDataSampleProcessor(
		String namespace, DataSampleProcessor<DataSample> dataSampleProcessor) {

		List<DataSampleProcessor<DataSample>> dataSampleProcessors =
			_dataSampleProcessors.get(namespace);

		if (dataSampleProcessors != null) {
			dataSampleProcessors.remove(dataSampleProcessor);
		}
	}

	private final Map<String, List<DataSampleProcessor<DataSample>>>
		_dataSampleProcessors = new ConcurrentHashMap
			<String, List<DataSampleProcessor<DataSample>>>();
	private final Map<String, Level> _levels =
		new ConcurrentHashMap<String, Level>();

}