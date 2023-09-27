/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.style.book.model.StyleBookEntry;

/**
 * Provides the remote service utility for StyleBookEntry. This utility wraps
 * <code>com.liferay.style.book.service.impl.StyleBookEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see StyleBookEntryService
 * @generated
 */
public class StyleBookEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.style.book.service.impl.StyleBookEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static StyleBookEntry addStyleBookEntry(
			long groupId, String name, String styleBookEntryKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addStyleBookEntry(
			groupId, name, styleBookEntryKey, serviceContext);
	}

	public static StyleBookEntry addStyleBookEntry(
			long groupId, String frontendTokensValues, String name,
			String styleBookEntryKey,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addStyleBookEntry(
			groupId, frontendTokensValues, name, styleBookEntryKey,
			serviceContext);
	}

	public static StyleBookEntry copyStyleBookEntry(
			long groupId, long sourceStyleBookEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().copyStyleBookEntry(
			groupId, sourceStyleBookEntryId, serviceContext);
	}

	public static StyleBookEntry deleteStyleBookEntry(long styleBookEntryId)
		throws PortalException {

		return getService().deleteStyleBookEntry(styleBookEntryId);
	}

	public static StyleBookEntry deleteStyleBookEntry(
			StyleBookEntry styleBookEntry)
		throws PortalException {

		return getService().deleteStyleBookEntry(styleBookEntry);
	}

	public static StyleBookEntry discardDraftStyleBookEntry(
			long styleBookEntryId)
		throws PortalException {

		return getService().discardDraftStyleBookEntry(styleBookEntryId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static StyleBookEntry publishDraft(long styleBookEntryId)
		throws PortalException {

		return getService().publishDraft(styleBookEntryId);
	}

	public static StyleBookEntry updateDefaultStyleBookEntry(
			long styleBookEntryId, boolean defaultStyleBookEntry)
		throws PortalException {

		return getService().updateDefaultStyleBookEntry(
			styleBookEntryId, defaultStyleBookEntry);
	}

	public static StyleBookEntry updateFrontendTokensValues(
			long styleBookEntryId, String frontendTokensValues)
		throws PortalException {

		return getService().updateFrontendTokensValues(
			styleBookEntryId, frontendTokensValues);
	}

	public static StyleBookEntry updateName(long styleBookEntryId, String name)
		throws PortalException {

		return getService().updateName(styleBookEntryId, name);
	}

	public static StyleBookEntry updatePreviewFileEntryId(
			long styleBookEntryId, long previewFileEntryId)
		throws PortalException {

		return getService().updatePreviewFileEntryId(
			styleBookEntryId, previewFileEntryId);
	}

	public static StyleBookEntry updateStyleBookEntry(
			long styleBookEntryId, String frontendTokensValues, String name)
		throws PortalException {

		return getService().updateStyleBookEntry(
			styleBookEntryId, frontendTokensValues, name);
	}

	public static StyleBookEntryService getService() {
		return _service;
	}

	public static void setService(StyleBookEntryService service) {
		_service = service;
	}

	private static volatile StyleBookEntryService _service;

}