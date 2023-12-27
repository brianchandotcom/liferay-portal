/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.internal.search.spi.model.result.contributor;

import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.service.CommerceTermEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceTermEntryModelVisibilityContributor
	implements ModelVisibilityContributor {

	public CommerceTermEntryModelVisibilityContributor(
		CommerceTermEntryLocalService commerceTermEntryLocalService) {

		_commerceTermEntryLocalService = commerceTermEntryLocalService;
	}

	@Override
	public boolean isVisible(long classPK, int status) {
		try {
			CommerceTermEntry commerceTermEntry =
				_commerceTermEntryLocalService.getCommerceTermEntry(classPK);

			return isVisible(commerceTermEntry.getStatus(), status);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to check visibility for commerce term entry",
					portalException);
			}

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceTermEntryModelVisibilityContributor.class);

	private final CommerceTermEntryLocalService _commerceTermEntryLocalService;

}