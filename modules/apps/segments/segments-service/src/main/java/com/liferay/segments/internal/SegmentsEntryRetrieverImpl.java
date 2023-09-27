/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.internal;

import com.liferay.osgi.util.service.Snapshot;
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

		Group group = _groupLocalService.fetchGroup(groupId);

		if (group == null) {
			return _portal.getDefaultCompanyId();
		}

		return group.getCompanyId();
	}

	private long[] _getSegmentEntryIds(
		long groupId, long userId, Context context, long[] segmentEntryIds) {

		SegmentsEntrySimulator segmentsEntrySimulator =
			_segmentsEntrySimulatorSnapshot.get();

		if ((segmentsEntrySimulator != null) &&
			segmentsEntrySimulator.isSimulationActive(userId)) {

			return segmentsEntrySimulator.getSimulatedSegmentsEntryIds(userId);
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

	private static final Snapshot<SegmentsEntrySimulator>
		_segmentsEntrySimulatorSnapshot = new Snapshot<>(
			SegmentsEntryRetrieverImpl.class, SegmentsEntrySimulator.class,
			"(model.class.name=com.liferay.portal.kernel.model.User)", true);

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsConfigurationProvider _segmentsConfigurationProvider;

	@Reference
	private SegmentsEntryProviderRegistry _segmentsEntryProviderRegistry;

}