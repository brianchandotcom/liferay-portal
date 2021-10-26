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

package com.liferay.segments.internal.provider;

import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.osgi.service.tracker.collections.map.PropertyServiceReferenceComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.segments.context.Context;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.provider.SegmentsEntryProvider;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;
import com.liferay.segments.service.SegmentsEntryLocalService;

import java.util.Collections;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = SegmentsEntryProviderRegistry.class)
public class SegmentsEntryProviderRegistryImpl
	implements SegmentsEntryProviderRegistry {

	@Override
	public long[] getSegmentsEntryClassPKs(
			long segmentsEntryId, int start, int end)
		throws PortalException {

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(segmentsEntryId);

		if (segmentsEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No segments entry found with segments entry ID " +
						segmentsEntryId);
			}

			return new long[0];
		}

		SegmentsEntryProvider segmentsEntryProvider = getSegmentsEntryProvider(
			segmentsEntry.getSource());

		if (segmentsEntryProvider == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No segments entry provider found for source " +
						segmentsEntry.getSource());
			}

			return new long[0];
		}

		return segmentsEntryProvider.getSegmentsEntryClassPKs(
			segmentsEntryId, start, end);
	}

	@Override
	public int getSegmentsEntryClassPKsCount(long segmentsEntryId)
		throws PortalException {

		SegmentsEntry segmentsEntry =
			_segmentsEntryLocalService.fetchSegmentsEntry(segmentsEntryId);

		if (segmentsEntry == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No segments entry found with segments entry ID " +
						segmentsEntryId);
			}

			return 0;
		}

		SegmentsEntryProvider segmentsEntryProvider = getSegmentsEntryProvider(
			segmentsEntry.getSource());

		if (segmentsEntryProvider == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"No segments entry provider found for source " +
						segmentsEntry.getSource());
			}

			return 0;
		}

		return segmentsEntryProvider.getSegmentsEntryClassPKsCount(
			segmentsEntryId);
	}

	@Override
	public long[] getSegmentsEntryIds(
			long groupId, String className, long classPK, Context context)
		throws PortalException {

		long[] segmentsEntryIds = new long[0];

		for (SegmentsEntryProvider segmentsEntryProvider :
				_serviceTrackerList) {

			segmentsEntryIds = ArrayUtil.append(
				segmentsEntryIds,
				segmentsEntryProvider.getSegmentsEntryIds(
					groupId, className, classPK, context, segmentsEntryIds));
		}

		Set<Long> segmentsEntryIdsSet = SetUtil.fromArray(segmentsEntryIds);

		return ArrayUtil.toLongArray(segmentsEntryIdsSet);
	}

	@Override
	public SegmentsEntryProvider getSegmentsEntryProvider(String source) {
		return _serviceTrackerMap.getService(source);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, SegmentsEntryProvider.class,
			Collections.reverseOrder(
				new PropertyServiceReferenceComparator<>(
					"segments.entry.provider.order")));
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, SegmentsEntryProvider.class,
			"segments.entry.provider.source");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryProviderRegistryImpl.class);

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	private ServiceTrackerList<SegmentsEntryProvider> _serviceTrackerList;
	private ServiceTrackerMap<String, SegmentsEntryProvider> _serviceTrackerMap;

}