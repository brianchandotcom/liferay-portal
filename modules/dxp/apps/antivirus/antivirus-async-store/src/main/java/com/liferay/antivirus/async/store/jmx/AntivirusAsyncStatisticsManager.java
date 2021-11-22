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

package com.liferay.antivirus.async.store.jmx;

import com.liferay.antivirus.async.store.constants.AntivirusAsyncConstants;
import com.liferay.antivirus.async.store.events.AntivirusAsyncEvent;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationStatistics;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.SchedulerException;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;

import javax.management.DynamicMBean;
import javax.management.NotCompliantMBeanException;
import javax.management.StandardMBean;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.antivirus.async.store.configuration.AntivirusAsyncConfiguration",
	configurationPolicy = ConfigurationPolicy.REQUIRE, immediate = true,
	property = {
		"antivirus.async.event=PROCESSING_ERROR",
		"antivirus.async.event=SIZE_EXCEEDED", "antivirus.async.event=SUCCESS",
		"antivirus.async.event=VIRUS_FOUND",
		"destination.target=(destination.name=" + AntivirusAsyncConstants.ANTIVIRUS_DESTINATION + ")",
		"jmx.objectname=com.liferay.antivirus:classification=antivirus_async,name=AntivirusAsyncStatistics",
		"jmx.objectname.cache.key=AntivirusAsyncStatistics"
	},
	service = {
		AntivirusAsyncStatisticsManagerMBean.class, BiConsumer.class,
		DynamicMBean.class
	}
)
public class AntivirusAsyncStatisticsManager
	extends StandardMBean
	implements AntivirusAsyncStatisticsManagerMBean,
			   BiConsumer<String, Map.Entry<Message, Object[]>> {

	@Activate
	public AntivirusAsyncStatisticsManager(
			@Reference(name = "destination") Destination destination)
		throws NotCompliantMBeanException {

		super(AntivirusAsyncStatisticsManagerMBean.class);

		_destination = destination;
	}

	@Override
	public void accept(
		String eventName, Map.Entry<Message, Object[]> eventData) {

		AntivirusAsyncEvent antivirusAsyncEvent = AntivirusAsyncEvent.withName(
			eventName);

		if (antivirusAsyncEvent == AntivirusAsyncEvent.PROCESSING_ERROR) {
			_processingErrorCounter.incrementAndGet();
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.SUCCESS) {
			_totalScannedCounter.incrementAndGet();
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.SIZE_EXCEEDED) {
			_sizeExceededCounter.incrementAndGet();
		}
		else if (antivirusAsyncEvent == AntivirusAsyncEvent.VIRUS_FOUND) {
			_virusFoundCounter.incrementAndGet();
			_totalScannedCounter.incrementAndGet();
		}
	}

	@Override
	public int getActiveScanCount() {
		if (_autoRefresh || (_destinationStatistics == null)) {
			refresh();
		}

		return _destinationStatistics.getActiveThreadCount();
	}

	@Override
	public String getLastRefresh() {
		return String.valueOf(_lastRefresh);
	}

	@Override
	public long getPendingScanCount() {
		if (_autoRefresh || (_destinationStatistics == null)) {
			refresh();
		}

		long pendingScanCount = _destinationStatistics.getPendingMessageCount();

		try {
			List<SchedulerResponse> scheduledJobs =
				_schedulerEngineHelper.getScheduledJobs(
					AntivirusAsyncConstants.ANTIVIRUS_GROUP_NAME,
					StorageType.PERSISTED);

			pendingScanCount += scheduledJobs.size();
		}
		catch (SchedulerException schedulerException) {
			_log.error(
				"An error occured attempting to retrieve the number of " +
					"scheduled async antivirus scans",
				schedulerException);
		}

		return pendingScanCount;
	}

	@Override
	public long getProcessingErrorCount() {
		return _processingErrorCounter.get();
	}

	@Override
	public long getSizeExceededCount() {
		return _sizeExceededCounter.get();
	}

	@Override
	public long getTotalScannedCount() {
		return _totalScannedCounter.get();
	}

	@Override
	public long getVirusFoundCount() {
		return _virusFoundCounter.get();
	}

	@Override
	public boolean isAutoRefresh() {
		return _autoRefresh;
	}

	@Override
	public void refresh() {
		if (System.currentTimeMillis() > _lastRefresh) {
			_lastRefresh = System.currentTimeMillis();
			_destinationStatistics = _destination.getDestinationStatistics();
			_processingErrorCounter.set(0);
			_sizeExceededCounter.set(0);
			_totalScannedCounter.set(0);
			_virusFoundCounter.set(0);
		}
	}

	@Override
	public void setAutoRefresh(boolean autoRefresh) {
		_autoRefresh = autoRefresh;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AntivirusAsyncStatisticsManager.class);

	private boolean _autoRefresh;
	private final Destination _destination;
	private DestinationStatistics _destinationStatistics;
	private long _lastRefresh;
	private final AtomicLong _processingErrorCounter = new AtomicLong();

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	private final AtomicLong _sizeExceededCounter = new AtomicLong();
	private final AtomicLong _totalScannedCounter = new AtomicLong();
	private final AtomicLong _virusFoundCounter = new AtomicLong();

}