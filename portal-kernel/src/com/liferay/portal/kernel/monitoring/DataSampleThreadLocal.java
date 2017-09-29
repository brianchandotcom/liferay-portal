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

import com.liferay.petra.lang.CentralizedThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.function.Function;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class DataSampleThreadLocal {

	public static void addDataSample(DataSample dataSample) {
		DataSampleThreadLocal dataSampleThreadLocal =
			_dataSampleThreadLocal.get();

		dataSampleThreadLocal._addDataSample(dataSample);
	}

	public static void clearDataSamples() {
		_dataSampleThreadLocal.remove();
	}

	public static List<DataSample> getDataSamples() {
		DataSampleThreadLocal dataSampleThreadLocal =
			_dataSampleThreadLocal.get();

		return ListUtil.fromCollection(dataSampleThreadLocal._getDataSamples());
	}

	public static void initialize() {
		_dataSampleThreadLocal.get();
	}

	public long getMonitorTime() {
		return _monitorTime;
	}

	private DataSampleThreadLocal() {
		_monitorTime = System.currentTimeMillis();
	}

	private void _addDataSample(DataSample dataSample) {
		_dataSamples.add(dataSample);
	}

	private Queue<DataSample> _getDataSamples() {
		return _dataSamples;
	}

	private static final ThreadLocal<DataSampleThreadLocal>
		_dataSampleThreadLocal = new CentralizedThreadLocal<>(
			DataSampleThreadLocal.class + "._dataSampleThreadLocal",
			DataSampleThreadLocal::new, Function.identity(), true);

	private final Queue<DataSample> _dataSamples =
		new ConcurrentLinkedQueue<>();
	private final long _monitorTime;

}