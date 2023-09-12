/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.monitoring.internal.messaging;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.monitoring.DataSample;
import com.liferay.portal.kernel.monitoring.DataSampleProcessor;
import com.liferay.portal.kernel.monitoring.Level;
import com.liferay.portal.kernel.monitoring.MonitoringControl;
import com.liferay.portal.kernel.monitoring.MonitoringException;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 */
@Component(
	enabled = false,
	property = "destination.name=" + DestinationNames.MONITORING,
	service = MessageListener.class
)
public class MonitoringMessageListener extends BaseMessageListener {

	public void processDataSample(DataSample dataSample)
		throws MonitoringException {

		String namespace = dataSample.getNamespace();

		Level level = _monitoringControl.getLevel(namespace);

		if ((level != null) && level.equals(Level.OFF)) {
			return;
		}

		List<DataSampleProcessor<DataSample>> dataSampleProcessors =
			_serviceTrackerMap.getService(namespace);

		if (ListUtil.isEmpty(dataSampleProcessors)) {
			return;
		}

		for (DataSampleProcessor<DataSample> dataSampleProcessor :
				dataSampleProcessors) {

			dataSampleProcessor.processDataSample(dataSample);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openMultiValueMap(
			bundleContext,
			(Class<DataSampleProcessor<DataSample>>)
				(Class<?>)DataSampleProcessor.class,
			"namespace");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
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

	@Reference
	private MonitoringControl _monitoringControl;

	private ServiceTrackerMap<String, List<DataSampleProcessor<DataSample>>>
		_serviceTrackerMap;

}