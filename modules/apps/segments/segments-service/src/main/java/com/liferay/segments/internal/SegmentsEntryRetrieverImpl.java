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

package com.liferay.segments.internal;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.context.Context;
import com.liferay.segments.provider.SegmentsEntryProviderRegistry;
import com.liferay.segments.simulator.SegmentsEntrySimulator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Cristina González
 */
@Component(service = SegmentsEntryRetriever.class)
public class SegmentsEntryRetrieverImpl implements SegmentsEntryRetriever {

	@Override
	public long[] getSegmentsEntryIds(
		long groupId, long userId, Context context, long[] segmentEntryIds) {

		try {
			if (!_segmentsConfigurationProvider.isSegmentationEnabled(
					_getCompanyId(groupId))) {

				return new long[] {SegmentsEntryConstants.ID_DEFAULT};
			}
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}

		return ArrayUtil.toLongArray(
			SetUtil.fromArray(
				ArrayUtil.append(
					_getSegmentEntryIds(
						groupId, userId, context, segmentEntryIds),
					SegmentsEntryConstants.ID_DEFAULT)));
	}

	private long _getCompanyId(long groupId) throws PortalException {
		if (groupId == 0) {
			return _portal.getDefaultCompanyId();
		}

		Group group = _groupLocalService.getGroup(groupId);

		return group.getCompanyId();
	}

	private long[] _getSegmentEntryIds(
		long groupId, long userId, Context context, long[] segmentEntryIds) {

		if ((_segmentsEntrySimulator != null) &&
			_segmentsEntrySimulator.isSimulationActive(userId)) {

			return _segmentsEntrySimulator.getSimulatedSegmentsEntryIds(userId);
		}

		try {
			return _segmentsEntryProviderRegistry.getSegmentsEntryIds(
				groupId, User.class.getName(), userId, context,
				segmentEntryIds);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}

			return new long[0];
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsEntryRetrieverImpl.class);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsConfigurationProvider _segmentsConfigurationProvider;

	@Reference
	private SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(model.class.name=com.liferay.portal.kernel.model.User)"
	)
	private volatile SegmentsEntrySimulator _segmentsEntrySimulator;

}