/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.audience.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AudienceEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see AudienceEntryService
 * @generated
 */
public class AudienceEntryServiceWrapper
	implements AudienceEntryService, ServiceWrapper<AudienceEntryService> {

	public AudienceEntryServiceWrapper() {
		this(null);
	}

	public AudienceEntryServiceWrapper(
		AudienceEntryService audienceEntryService) {

		_audienceEntryService = audienceEntryService;
	}

	@Override
	public com.liferay.audience.model.AudienceEntry addAudienceEntry(
			String externalReferenceCode, String json,
			java.util.Map<java.util.Locale, String> nameMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _audienceEntryService.addAudienceEntry(
			externalReferenceCode, json, nameMap, serviceContext);
	}

	@Override
	public com.liferay.audience.model.AudienceEntry deleteAudienceEntry(
			long audienceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _audienceEntryService.deleteAudienceEntry(audienceEntryId);
	}

	@Override
	public java.util.List<com.liferay.audience.model.AudienceEntry>
		getAudienceEntries(
			long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.audience.model.AudienceEntry> orderByComparator) {

		return _audienceEntryService.getAudienceEntries(
			companyId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.audience.model.AudienceEntry>
		getAudienceEntries(
			long companyId, String name, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.audience.model.AudienceEntry> orderByComparator) {

		return _audienceEntryService.getAudienceEntries(
			companyId, name, start, end, orderByComparator);
	}

	@Override
	public int getAudienceEntriesCount(long companyId) {
		return _audienceEntryService.getAudienceEntriesCount(companyId);
	}

	@Override
	public int getAudienceEntriesCount(long companyId, String name) {
		return _audienceEntryService.getAudienceEntriesCount(companyId, name);
	}

	@Override
	public com.liferay.audience.model.AudienceEntry getAudienceEntry(
			long audienceEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _audienceEntryService.getAudienceEntry(audienceEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _audienceEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.audience.model.AudienceEntry updateAudienceEntry(
			long audienceEntryId, String json,
			java.util.Map<java.util.Locale, String> nameMap)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _audienceEntryService.updateAudienceEntry(
			audienceEntryId, json, nameMap);
	}

	@Override
	public AudienceEntryService getWrappedService() {
		return _audienceEntryService;
	}

	@Override
	public void setWrappedService(AudienceEntryService audienceEntryService) {
		_audienceEntryService = audienceEntryService;
	}

	private AudienceEntryService _audienceEntryService;

}
// LIFERAY-SERVICE-BUILDER-HASH:-1752987791