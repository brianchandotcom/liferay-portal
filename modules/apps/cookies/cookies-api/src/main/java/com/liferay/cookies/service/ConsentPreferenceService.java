/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.cookies.service;

import com.liferay.cookies.model.ConsentPreference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

import java.util.Date;
import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for ConsentPreference. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see ConsentPreferenceServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface ConsentPreferenceService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.cookies.service.impl.ConsentPreferenceServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the consent preference remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link ConsentPreferenceServiceUtil} if injection and service tracking are not available.
	 */
	public ConsentPreference addConsentPreference(
			long userId, String domain, Date expirationDate, String name,
			String value)
		throws PortalException;

	public void deleteConsentPreference(long userId, String domain, String name)
		throws PortalException;

	public void deleteConsentPreferences(long userId);

	public void deleteConsentPreferences(long userId, String domain);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public ConsentPreference getConsentPreference(
		long userId, String domain, String name);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<ConsentPreference> getConsentPreferences(
		long userId, String domain);

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public ConsentPreference updateConsentPreference(
		ConsentPreference consentPreference);

}
// LIFERAY-SERVICE-BUILDER-HASH:2140313568