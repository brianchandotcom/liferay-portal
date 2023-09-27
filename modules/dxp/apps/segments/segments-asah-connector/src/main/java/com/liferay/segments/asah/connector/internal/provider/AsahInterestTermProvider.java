/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.provider;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.segments.asah.connector.internal.cache.AsahInterestTermCache;
import com.liferay.segments.asah.connector.internal.constants.SegmentsAsahDestinationNames;
import com.liferay.segments.constants.SegmentsEntryConstants;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sarai Díaz
 */
@Component(
	property = "segments.entry.provider.source=" + SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND,
	service = AsahInterestTermProvider.class
)
public class AsahInterestTermProvider {

	public String[] getInterestTerms(long companyId, String userId) {
		String[] cachedInterestTerms = _asahInterestTermCache.getInterestTerms(
			userId);

		if (cachedInterestTerms == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Asah interest terms cache not found for user ID " +
						userId);
			}

			_sendMessage(companyId, userId);

			return new String[0];
		}

		return cachedInterestTerms;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				SegmentsAsahDestinationNames.INTEREST_TERMS);

		Destination destination = _destinationFactory.createDestination(
			destinationConfiguration);

		_destinationServiceRegistration = bundleContext.registerService(
			Destination.class, destination,
			MapUtil.singletonDictionary(
				"destination.name", destination.getName()));
	}

	@Deactivate
	protected void deactivate() {
		_destinationServiceRegistration.unregister();
	}

	private void _sendMessage(long companyId, String userId) {
		Message message = new Message();

		message.put("companyId", companyId);
		message.put("userId", userId);

		_messageBus.sendMessage(
			SegmentsAsahDestinationNames.INTEREST_TERMS, message);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AsahInterestTermProvider.class);

	@Reference
	private AsahInterestTermCache _asahInterestTermCache;

	@Reference
	private DestinationFactory _destinationFactory;

	private ServiceRegistration<Destination> _destinationServiceRegistration;

	@Reference
	private MessageBus _messageBus;

}