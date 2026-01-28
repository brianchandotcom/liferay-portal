/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.util;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.segments.asah.connector.internal.cache.AsahSegmentsEntryCache;
import com.liferay.segments.asah.connector.util.IndividualSegmentsHelper;
import com.liferay.segments.constants.SegmentsEntryConstants;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.service.SegmentsEntryLocalService;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rachael Koestartyo
 */
@Component(service = IndividualSegmentsHelper.class)
public class IndividualSegmentsHelperImpl implements IndividualSegmentsHelper {

	public SegmentsEntry addSegmentsEntry(
		long companyId, String id, String name) {

		try {
			ServiceContext serviceContext = _getServiceContext(companyId);

			Map<Locale, String> nameMap = Collections.singletonMap(
				_portal.getSiteDefaultLocale(serviceContext.getScopeGroupId()),
				name);

			SegmentsEntry segmentsEntry =
				_segmentsEntryLocalService.fetchSegmentsEntry(
					serviceContext.getScopeGroupId(), id);

			if (segmentsEntry == null) {
				return _segmentsEntryLocalService.addSegmentsEntry(
					id, nameMap, Collections.emptyMap(), true, null,
					SegmentsEntryConstants.SOURCE_ASAH_FARO_BACKEND,
					serviceContext);
			}

			return _segmentsEntryLocalService.updateSegmentsEntry(
				segmentsEntry.getSegmentsEntryId(), id, nameMap, null, true,
				null, serviceContext);
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to process individual segment " + id, portalException);
		}

		return null;
	}

	public void updateIndividualSegmentMemberships(
			String individualPK, List<String> individualSegmentIds,
			Boolean removed, SegmentsEntry segmentsEntry, Long userId)
		throws PortalException {

		if (userId != null) {
			if (removed) {
				_removeSegmentsEntryRels(
					segmentsEntry, Collections.singleton(userId));
			}
			else {
				_addSegmentsEntryRels(
					segmentsEntry, Collections.singleton(userId));
			}
		}

		_putSegmentsEntryIdsCache(
			segmentsEntry.getCompanyId(), individualPK, individualSegmentIds);
	}

	private void _addSegmentsEntryRels(
		SegmentsEntry segmentsEntry, Set<Long> userIds) {

		try {
			_segmentsEntryLocalService.addSegmentsEntryClassPKs(
				segmentsEntry.getSegmentsEntryId(),
				ArrayUtil.toLongArray(userIds),
				_getServiceContext(segmentsEntry.getCompanyId()));
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to process individuals " + userIds, portalException);
		}
	}

	private ServiceContext _getServiceContext(long companyId)
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		Company company = _companyLocalService.getCompany(companyId);

		serviceContext.setScopeGroupId(company.getGroupId());

		User user = company.getGuestUser();

		serviceContext.setUserId(user.getUserId());

		return serviceContext;
	}

	private void _putSegmentsEntryIdsCache(
			long companyId, String userId, List<String> individualSegmentIds)
		throws PortalException {

		ServiceContext serviceContext = _getServiceContext(companyId);

		List<Long> segmentsEntryIds = TransformUtil.transform(
			individualSegmentIds,
			individualSegmentId -> {
				SegmentsEntry curSegmentsEntry =
					_segmentsEntryLocalService.fetchSegmentsEntry(
						serviceContext.getScopeGroupId(), individualSegmentId);

				if (curSegmentsEntry != null) {
					return curSegmentsEntry.getSegmentsEntryId();
				}

				return null;
			});

		_asahSegmentsEntryCache.putSegmentsEntryIds(
			userId, ArrayUtil.toLongArray(segmentsEntryIds));
	}

	private void _removeSegmentsEntryRels(
		SegmentsEntry segmentsEntry, Set<Long> userIds) {

		try {
			_segmentsEntryLocalService.deleteSegmentsEntryClassPKs(
				segmentsEntry.getSegmentsEntryId(),
				ArrayUtil.toLongArray(userIds));
		}
		catch (PortalException portalException) {
			_log.error(
				"Unable to remove individuals " + userIds, portalException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		IndividualSegmentsHelperImpl.class);

	@Reference
	private AsahSegmentsEntryCache _asahSegmentsEntryCache;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

}