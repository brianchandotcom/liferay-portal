/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service;

import com.liferay.portal.kernel.change.tracking.CTAware;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.style.book.model.StyleBookEntry;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for StyleBookEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see StyleBookEntryServiceUtil
 * @generated
 */
@AccessControlled
@CTAware
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface StyleBookEntryService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this interface directly. Add custom service methods to <code>com.liferay.style.book.service.impl.StyleBookEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface. Consume the style book entry remote service via injection or a <code>org.osgi.util.tracker.ServiceTracker</code>. Use {@link StyleBookEntryServiceUtil} if injection and service tracking are not available.
	 */
	public StyleBookEntry addStyleBookEntry(
			long groupId, String name, String styleBookEntryKey,
			ServiceContext serviceContext)
		throws PortalException;

	public StyleBookEntry addStyleBookEntry(
			long groupId, String frontendTokensValues, String name,
			String styleBookEntryKey, ServiceContext serviceContext)
		throws PortalException;

	public StyleBookEntry copyStyleBookEntry(
			long groupId, long sourceStyleBookEntryId,
			ServiceContext serviceContext)
		throws PortalException;

	public StyleBookEntry deleteStyleBookEntry(long styleBookEntryId)
		throws PortalException;

	public StyleBookEntry deleteStyleBookEntry(StyleBookEntry styleBookEntry)
		throws PortalException;

	public StyleBookEntry discardDraftStyleBookEntry(long styleBookEntryId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public StyleBookEntry publishDraft(long styleBookEntryId)
		throws PortalException;

	public StyleBookEntry updateDefaultStyleBookEntry(
			long styleBookEntryId, boolean defaultStyleBookEntry)
		throws PortalException;

	public StyleBookEntry updateFrontendTokensValues(
			long styleBookEntryId, String frontendTokensValues)
		throws PortalException;

	public StyleBookEntry updateName(long styleBookEntryId, String name)
		throws PortalException;

	public StyleBookEntry updatePreviewFileEntryId(
			long styleBookEntryId, long previewFileEntryId)
		throws PortalException;

	public StyleBookEntry updateStyleBookEntry(
			long styleBookEntryId, String frontendTokensValues, String name)
		throws PortalException;

}