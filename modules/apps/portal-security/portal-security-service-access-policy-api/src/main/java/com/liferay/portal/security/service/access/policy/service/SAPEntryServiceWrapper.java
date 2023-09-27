/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link SAPEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see SAPEntryService
 * @generated
 */
public class SAPEntryServiceWrapper
	implements SAPEntryService, ServiceWrapper<SAPEntryService> {

	public SAPEntryServiceWrapper() {
		this(null);
	}

	public SAPEntryServiceWrapper(SAPEntryService sapEntryService) {
		_sapEntryService = sapEntryService;
	}

	@Override
	public com.liferay.portal.security.service.access.policy.model.SAPEntry
			addSAPEntry(
				String allowedServiceSignatures, boolean defaultSAPEntry,
				boolean enabled, String name,
				java.util.Map<java.util.Locale, String> titleMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sapEntryService.addSAPEntry(
			allowedServiceSignatures, defaultSAPEntry, enabled, name, titleMap,
			serviceContext);
	}

	@Override
	public com.liferay.portal.security.service.access.policy.model.SAPEntry
			deleteSAPEntry(long sapEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sapEntryService.deleteSAPEntry(sapEntryId);
	}

	@Override
	public com.liferay.portal.security.service.access.policy.model.SAPEntry
			deleteSAPEntry(
				com.liferay.portal.security.service.access.policy.model.SAPEntry
					sapEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sapEntryService.deleteSAPEntry(sapEntry);
	}

	@Override
	public com.liferay.portal.security.service.access.policy.model.SAPEntry
			fetchSAPEntry(long companyId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sapEntryService.fetchSAPEntry(companyId, name);
	}

	@Override
	public java.util.List
		<com.liferay.portal.security.service.access.policy.model.SAPEntry>
			getCompanySAPEntries(long companyId, int start, int end) {

		return _sapEntryService.getCompanySAPEntries(companyId, start, end);
	}

	@Override
	public java.util.List
		<com.liferay.portal.security.service.access.policy.model.SAPEntry>
			getCompanySAPEntries(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.security.service.access.policy.model.
						SAPEntry> orderByComparator) {

		return _sapEntryService.getCompanySAPEntries(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCompanySAPEntriesCount(long companyId) {
		return _sapEntryService.getCompanySAPEntriesCount(companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _sapEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.security.service.access.policy.model.SAPEntry
			getSAPEntry(long sapEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sapEntryService.getSAPEntry(sapEntryId);
	}

	@Override
	public com.liferay.portal.security.service.access.policy.model.SAPEntry
			getSAPEntry(long companyId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sapEntryService.getSAPEntry(companyId, name);
	}

	@Override
	public com.liferay.portal.security.service.access.policy.model.SAPEntry
			updateSAPEntry(
				long sapEntryId, String allowedServiceSignatures,
				boolean defaultSAPEntry, boolean enabled, String name,
				java.util.Map<java.util.Locale, String> titleMap,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _sapEntryService.updateSAPEntry(
			sapEntryId, allowedServiceSignatures, defaultSAPEntry, enabled,
			name, titleMap, serviceContext);
	}

	@Override
	public SAPEntryService getWrappedService() {
		return _sapEntryService;
	}

	@Override
	public void setWrappedService(SAPEntryService sapEntryService) {
		_sapEntryService = sapEntryService;
	}

	private SAPEntryService _sapEntryService;

}