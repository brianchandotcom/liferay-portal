/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.monitoring.statistics;

import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
public class DataSampleThreadLocal implements Cloneable {

	public static void addDataSample(DataSample dataSample) {
		_dataSampleThreadLocal.get()._addDataSample(dataSample);
	}

	public static void clearDataSamples() {
		_dataSampleThreadLocal.remove();
	}

	public static List<DataSample> getDataSamples() {
		return ListUtil.copy(_dataSampleThreadLocal.get()._getDataSamples());
	}

	@Override
	public Object clone() {
		return new DataSampleThreadLocal();
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

	private List<DataSample> _getDataSamples() {
		return _dataSamples;
	}

	private static ThreadLocal<DataSampleThreadLocal> _dataSampleThreadLocal =
		new AutoResetThreadLocal<DataSampleThreadLocal>(
			DataSampleThreadLocal.class + "._dataSampleThreadLocal",
			new DataSampleThreadLocal());

	private List<DataSample> _dataSamples = new ArrayList<DataSample>();
	private long _monitorTime;

}