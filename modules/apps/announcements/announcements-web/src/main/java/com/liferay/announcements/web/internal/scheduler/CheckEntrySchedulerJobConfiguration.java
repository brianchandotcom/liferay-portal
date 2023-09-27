/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.announcements.web.internal.scheduler;

import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalService;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.kernel.cluster.ClusterMasterTokenTransitionListener;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.scheduler.TimeUnit;
import com.liferay.portal.kernel.scheduler.TriggerConfiguration;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.util.PropsValues;

import java.util.Date;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Raymond Augé
 * @author Tina Tian
 */
@Component(service = SchedulerJobConfiguration.class)
public class CheckEntrySchedulerJobConfiguration
	implements SchedulerJobConfiguration {

	@Override
	public UnsafeRunnable<Exception> getJobExecutorUnsafeRunnable() {
		return () -> {
			Date startDate = _previousEndDate;
			Date endDate = new Date();

			if (startDate == null) {
				startDate = new Date(
					endDate.getTime() - _ANNOUNCEMENTS_ENTRY_CHECK_INTERVAL);
			}

			_previousEndDate = endDate;

			_announcementsEntryLocalService.checkEntries(startDate, endDate);
		};
	}

	@Override
	public TriggerConfiguration getTriggerConfiguration() {
		return TriggerConfiguration.createTriggerConfiguration(
			PropsValues.ANNOUNCEMENTS_ENTRY_CHECK_INTERVAL, TimeUnit.MINUTE);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistration = bundleContext.registerService(
			ClusterMasterTokenTransitionListener.class,
			new CheckEntryClusterMasterTokenTransitionListener(), null);
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	private static final long _ANNOUNCEMENTS_ENTRY_CHECK_INTERVAL =
		PropsValues.ANNOUNCEMENTS_ENTRY_CHECK_INTERVAL * Time.MINUTE;

	@Reference
	private AnnouncementsEntryLocalService _announcementsEntryLocalService;

	private Date _previousEndDate;
	private ServiceRegistration<ClusterMasterTokenTransitionListener>
		_serviceRegistration;

	private class CheckEntryClusterMasterTokenTransitionListener
		implements ClusterMasterTokenTransitionListener {

		@Override
		public void masterTokenAcquired() {
		}

		@Override
		public void masterTokenReleased() {
			_previousEndDate = null;
		}

	}

}