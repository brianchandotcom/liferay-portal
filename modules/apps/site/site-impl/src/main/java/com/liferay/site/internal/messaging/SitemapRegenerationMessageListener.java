/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.internal.messaging;

import com.liferay.portal.kernel.messaging.BaseMessageListener;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.DestinationStatistics;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelper;
import com.liferay.portal.kernel.scheduler.StorageType;
import com.liferay.portal.kernel.scheduler.messaging.SchedulerResponse;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.site.internal.constants.SitemapDestinationNames;
import com.liferay.site.manager.SitemapManager;
import com.liferay.site.storage.helper.SitemapStorageHelper;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cheryl Tang
 */
@Component(
	property = "destination.name=" + SitemapDestinationNames.SITEMAP_REGENERATION,
	service = MessageListener.class
)
public class SitemapRegenerationMessageListener extends BaseMessageListener {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_destination = _destinationFactory.createDestination(
			DestinationConfiguration.createSerialDestinationConfiguration(
				SitemapDestinationNames.SITEMAP_REGENERATION));

		_serviceRegistration = bundleContext.registerService(
			Destination.class, _destination,
			MapUtil.singletonDictionary(
				"destination.name", _destination.getName()));
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	@Override
	protected void doReceive(Message message) throws Exception {
		_sitemapManager.regenerateSitemap(
			message.getString("childSitemapKey"), message.getLong("companyId"),
			message.getLong("groupId"));

		if (!_isRegenerateSitemapPending()) {
			_sitemapStorageHelper.moveSitemapFilesToPortalCache();
		}
	}

	private boolean _isRegenerateSitemapPending() throws Exception {
		DestinationStatistics destinationStatistics =
			_destination.getDestinationStatistics();

		if (destinationStatistics.getPendingMessageCount() > 0) {
			return true;
		}

		for (SchedulerResponse schedulerResponse :
				_schedulerEngineHelper.getScheduledJobs(
					SitemapDestinationNames.SITEMAP_REGENERATION,
					StorageType.PERSISTED)) {

			if (_schedulerEngineHelper.getNextFireDate(schedulerResponse) !=
					null) {

				return true;
			}
		}

		return false;
	}

	private Destination _destination;

	@Reference
	private DestinationFactory _destinationFactory;

	@Reference
	private SchedulerEngineHelper _schedulerEngineHelper;

	private ServiceRegistration<Destination> _serviceRegistration;

	@Reference
	private SitemapManager _sitemapManager;

	@Reference
	private SitemapStorageHelper _sitemapStorageHelper;

}