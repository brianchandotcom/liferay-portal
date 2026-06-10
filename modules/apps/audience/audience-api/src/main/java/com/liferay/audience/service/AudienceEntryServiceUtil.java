/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.service;

import com.liferay.audience.model.AudienceEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for AudienceEntry. This utility wraps
 * <code>com.liferay.audience.service.impl.AudienceEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AudienceEntryService
 * @generated
 */
public class AudienceEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.audience.service.impl.AudienceEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static AudienceEntry addAudienceEntry(
			String externalReferenceCode, String json, String name,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addAudienceEntry(
			externalReferenceCode, json, name, serviceContext);
	}

	public static AudienceEntry deleteAudienceEntry(long audienceEntryId)
		throws PortalException {

		return getService().deleteAudienceEntry(audienceEntryId);
	}

	public static List<AudienceEntry> getAudienceEntries(
			long companyId, int start, int end,
			OrderByComparator<AudienceEntry> orderByComparator)
		throws PortalException {

		return getService().getAudienceEntries(
			companyId, start, end, orderByComparator);
	}

	public static List<AudienceEntry> getAudienceEntries(
			long companyId, String name, int start, int end,
			OrderByComparator<AudienceEntry> orderByComparator)
		throws PortalException {

		return getService().getAudienceEntries(
			companyId, name, start, end, orderByComparator);
	}

	public static int getAudienceEntriesCount(long companyId)
		throws PortalException {

		return getService().getAudienceEntriesCount(companyId);
	}

	public static int getAudienceEntriesCount(long companyId, String name)
		throws PortalException {

		return getService().getAudienceEntriesCount(companyId, name);
	}

	public static AudienceEntry getAudienceEntry(long audienceEntryId)
		throws PortalException {

		return getService().getAudienceEntry(audienceEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static AudienceEntry updateAudienceEntry(
			long audienceEntryId, String json, String name)
		throws PortalException {

		return getService().updateAudienceEntry(audienceEntryId, json, name);
	}

	public static AudienceEntryService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<AudienceEntryService> _serviceSnapshot =
		new Snapshot<>(
			AudienceEntryServiceUtil.class, AudienceEntryService.class);

}
// LIFERAY-SERVICE-BUILDER-HASH:-941878168