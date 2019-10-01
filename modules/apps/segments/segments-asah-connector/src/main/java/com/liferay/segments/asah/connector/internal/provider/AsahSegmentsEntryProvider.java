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

package com.liferay.segments.asah.connector.internal.provider;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationConfiguration;
import com.liferay.portal.kernel.messaging.DestinationFactory;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.asah.connector.internal.cache.AsahSegmentsEntryCache;
import com.liferay.segments.asah.connector.internal.constants.SegmentsAsahDestinationNames;
import com.liferay.segments.asah.connector.internal.context.contributor.SegmentsAsahRequestContextContributor;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.context.Context;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.provider.SegmentsEntryProvider;
import com.liferay.segments.service.SegmentsEntryRelLocalService;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Arques
 * @author Eduardo García
 */
@Component(
	immediate = true,
	property = "segments.entry.provider.source=" + SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND,
	service = SegmentsEntryProvider.class
)
public class AsahSegmentsEntryProvider implements SegmentsEntryProvider {

	@Override
	public long[] getSegmentsEntryClassPKs(
			long segmentsEntryId, int start, int end)
		throws PortalException {

		List<SegmentsEntryRel> segmentsEntryRels =
			_segmentsEntryRelLocalService.getSegmentsEntryRels(
				segmentsEntryId, start, end, null);

		Stream<SegmentsEntryRel> stream = segmentsEntryRels.stream();

		return stream.mapToLong(
			SegmentsEntryRel::getClassPK
		).toArray();
	}

	@Override
	public int getSegmentsEntryClassPKsCount(long segmentsEntryId)
		throws PortalException {

		return _segmentsEntryRelLocalService.getSegmentsEntryRelsCount(
			segmentsEntryId);
	}

	@Override
	public long[] getSegmentsEntryIds(
		long groupId, String className, long classPK) {

		return getSegmentsEntryIds(groupId, className, classPK, null);
	}

	@Override
	public long[] getSegmentsEntryIds(
		long groupId, String className, long classPK, Context context) {

		if (context == null) {
			return new long[0];
		}

		String userId = GetterUtil.getString(
			context.get(
				SegmentsAsahRequestContextContributor.
					KEY_SEGMENTS_ANONYMOUS_USER_ID));

		if (Validator.isNull(userId)) {
			return new long[0];
		}

		long[] cachedSegmentsEntryIds =
			_asahSegmentsEntryCache.getSegmentsEntryIds(userId);

		if (cachedSegmentsEntryIds == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Asah segments cache not found for user ID " + userId);
			}

			_sendMessage(userId);

			return new long[0];
		}

		return cachedSegmentsEntryIds;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		DestinationConfiguration destinationConfiguration =
			new DestinationConfiguration(
				DestinationConfiguration.DESTINATION_TYPE_PARALLEL,
				SegmentsAsahDestinationNames.INDIVIDUAL_SEGMENTS);

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

	private void _sendMessage(String userId) {
		Message message = new Message();

		message.setPayload(userId);

		_messageBus.sendMessage(
			SegmentsAsahDestinationNames.INDIVIDUAL_SEGMENTS, message);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AsahSegmentsEntryProvider.class);

	@Reference
	private AsahSegmentsEntryCache _asahSegmentsEntryCache;

	@Reference
	private DestinationFactory _destinationFactory;

	private ServiceRegistration<Destination> _destinationServiceRegistration;

	@Reference
	private MessageBus _messageBus;

	@Reference
	private SegmentsEntryRelLocalService _segmentsEntryRelLocalService;

}