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

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.segments.context.Context;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.provider.SegmentsEntryProvider;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;
import com.liferay.segments.service.SegmentsEntryLocalService;

import java.util.HashSet;
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
			_segmentsEntryLocalService.getSegmentsEntry(segmentsEntryId);

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
			_segmentsEntryLocalService.getSegmentsEntry(segmentsEntryId);

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

		Set<Long> segmentsEntryIds = new HashSet<>();

		for (SegmentsEntryProvider segmentsEntryProvider :
				_serviceTrackerMap.values()) {

			long[] segmentsEntryProviderSegmentsEntryIds =
				segmentsEntryProvider.getSegmentsEntryIds(
					groupId, className, classPK, context);

			for (long segmentsEntryId : segmentsEntryProviderSegmentsEntryIds) {
				segmentsEntryIds.add(segmentsEntryId);
			}
		}

		return ArrayUtil.toLongArray(segmentsEntryIds);
	}

	@Override
	public SegmentsEntryProvider getSegmentsEntryProvider(String source) {
		return _serviceTrackerMap.getService(source);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, SegmentsEntryProvider.class,
			"segments.entry.provider.source");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryProviderRegistryImpl.class);

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	private ServiceTrackerMap<String, SegmentsEntryProvider> _serviceTrackerMap;

}