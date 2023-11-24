/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.service;

import com.liferay.commerce.product.type.virtual.model.CPDVirtualSettingFileEntry;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * Provides the remote service utility for CPDVirtualSettingFileEntry. This utility wraps
 * <code>com.liferay.commerce.product.type.virtual.service.impl.CPDVirtualSettingFileEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPDVirtualSettingFileEntryService
 * @generated
 */
public class CPDVirtualSettingFileEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.product.type.virtual.service.impl.CPDVirtualSettingFileEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static CPDVirtualSettingFileEntry addCPDefinitionVirtualSetting(
			long groupId, String className, long classPK,
			long cpDefinitionVirtualSettingId, long fileEntryId, String url,
			String version)
		throws PortalException {

		return getService().addCPDefinitionVirtualSetting(
			groupId, className, classPK, cpDefinitionVirtualSettingId,
			fileEntryId, url, version);
	}

	public static CPDVirtualSettingFileEntry deleteCPDVirtualSettingFileEntry(
			String className, long classPK, long cpdVirtualSettingFileEntryId)
		throws PortalException {

		return getService().deleteCPDVirtualSettingFileEntry(
			className, classPK, cpdVirtualSettingFileEntryId);
	}

	public static CPDVirtualSettingFileEntry fetchCPDVirtualSettingFileEntry(
			long cpdVirtualSettingFileEntryId)
		throws PortalException {

		return getService().fetchCPDVirtualSettingFileEntry(
			cpdVirtualSettingFileEntryId);
	}

	public static List<CPDVirtualSettingFileEntry>
			getCPDVirtualSettingFileEntries(
				String className, long classPK,
				long cpDefinitionVirtualSettingId, int start, int end)
		throws PortalException {

		return getService().getCPDVirtualSettingFileEntries(
			className, classPK, cpDefinitionVirtualSettingId, start, end);
	}

	public static CPDVirtualSettingFileEntry getCPDVirtualSettingFileEntry(
			long cpdVirtualSettingFileEntryId)
		throws PortalException {

		return getService().getCPDVirtualSettingFileEntry(
			cpdVirtualSettingFileEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static CPDVirtualSettingFileEntry updateCPDefinitionVirtualSetting(
			long cpdVirtualSettingFileEntryId, long fileEntryId, String url,
			String version)
		throws PortalException {

		return getService().updateCPDefinitionVirtualSetting(
			cpdVirtualSettingFileEntryId, fileEntryId, url, version);
	}

	public static CPDVirtualSettingFileEntryService getService() {
		return _service;
	}

	public static void setService(CPDVirtualSettingFileEntryService service) {
		_service = service;
	}

	private static volatile CPDVirtualSettingFileEntryService _service;

}