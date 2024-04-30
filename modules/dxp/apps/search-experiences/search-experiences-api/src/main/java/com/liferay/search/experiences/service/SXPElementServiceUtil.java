/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.search.experiences.model.SXPElement;

import java.util.Map;

/**
 * Provides the remote service utility for SXPElement. This utility wraps
 * <code>com.liferay.search.experiences.service.impl.SXPElementServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see SXPElementService
 * @generated
 */
public class SXPElementServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.search.experiences.service.impl.SXPElementServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static SXPElement addSXPElement(
			String externalReferenceCode,
			Map<java.util.Locale, String> descriptionMap,
			String elementDefinitionJSON, String fallbackDescription,
			String fallbackTitle, boolean readOnly, String schemaVersion,
			Map<java.util.Locale, String> titleMap, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addSXPElement(
			externalReferenceCode, descriptionMap, elementDefinitionJSON,
			fallbackDescription, fallbackTitle, readOnly, schemaVersion,
			titleMap, type, serviceContext);
	}

	public static SXPElement deleteSXPElement(long sxpElementId)
		throws PortalException {

		return getService().deleteSXPElement(sxpElementId);
	}

	public static SXPElement fetchSXPElement(long sxpElementId)
		throws PortalException {

		return getService().fetchSXPElement(sxpElementId);
	}

	public static SXPElement fetchSXPElementByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().fetchSXPElementByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static SXPElement getSXPElement(long sxpElementId)
		throws PortalException {

		return getService().getSXPElement(sxpElementId);
	}

	public static SXPElement getSXPElementByExternalReferenceCode(
			String externalReferenceCode, long companyId)
		throws PortalException {

		return getService().getSXPElementByExternalReferenceCode(
			externalReferenceCode, companyId);
	}

	public static SXPElement updateSXPElement(
			String externalReferenceCode, long sxpElementId,
			Map<java.util.Locale, String> descriptionMap,
			String elementDefinitionJSON, String schemaVersion, boolean hidden,
			Map<java.util.Locale, String> titleMap,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().updateSXPElement(
			externalReferenceCode, sxpElementId, descriptionMap,
			elementDefinitionJSON, schemaVersion, hidden, titleMap,
			serviceContext);
	}

	public static SXPElementService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<SXPElementService> _serviceSnapshot =
		new Snapshot<>(SXPElementServiceUtil.class, SXPElementService.class);

}