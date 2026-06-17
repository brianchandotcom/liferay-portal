/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.service.Snapshot;
import com.liferay.style.book.model.StyleBookTokenSet;

/**
 * Provides the remote service utility for StyleBookTokenSet. This utility wraps
 * <code>com.liferay.style.book.service.impl.StyleBookTokenSetServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see StyleBookTokenSetService
 * @generated
 */
public class StyleBookTokenSetServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.style.book.service.impl.StyleBookTokenSetServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static StyleBookTokenSet addStyleBookTokenSet(
			String externalReferenceCode, String description,
			String frontendTokenCategoryName, String name,
			long styleBookEntryId, String themeId)
		throws PortalException {

		return getService().addStyleBookTokenSet(
			externalReferenceCode, description, frontendTokenCategoryName, name,
			styleBookEntryId, themeId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static StyleBookTokenSetService getService() {
		return _serviceSnapshot.get();
	}

	private static final Snapshot<StyleBookTokenSetService> _serviceSnapshot =
		new Snapshot<>(
			StyleBookTokenSetServiceUtil.class, StyleBookTokenSetService.class);

}
// LIFERAY-SERVICE-BUILDER-HASH:143866258