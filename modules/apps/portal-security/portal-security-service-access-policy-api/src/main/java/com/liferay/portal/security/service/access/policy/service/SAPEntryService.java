/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.service.access.policy.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for SAPEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SAPEntryServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface SAPEntryService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.portal.security.service.access.policy.service.impl.SAPEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the sap entry remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link SAPEntryServiceUtil} if injection and service tracking are not available.
	 */
	public SAPEntry addSAPEntry(
			String allowedServiceSignatures, boolean defaultSAPEntry,
			boolean enabled, String name, Map<Locale, String> titleMap,
			ServiceContext serviceContext)
		throws PortalException;

	public SAPEntry deleteSAPEntry(long sapEntryId) throws PortalException;

	public SAPEntry deleteSAPEntry(SAPEntry sapEntry) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SAPEntry fetchSAPEntry(long companyId, String name)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SAPEntry> getCompanySAPEntries(
		long companyId, int start, int end);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<SAPEntry> getCompanySAPEntries(
		long companyId, int start, int end,
		OrderByComparator<SAPEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanySAPEntriesCount(long companyId);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SAPEntry getSAPEntry(long sapEntryId) throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public SAPEntry getSAPEntry(long companyId, String name)
		throws PortalException;

	public SAPEntry updateSAPEntry(
			long sapEntryId, String allowedServiceSignatures,
			boolean defaultSAPEntry, boolean enabled, String name,
			Map<Locale, String> titleMap, ServiceContext serviceContext)
		throws PortalException;

}