/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.search.spi.model.result.contributor;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

/**
 * @author Luan Maoski
 */
public class MBMessageModelVisibilityContributor
	implements ModelVisibilityContributor {

	public MBMessageModelVisibilityContributor(
		MBMessageLocalService mbMessageLocalService) {

		_mbMessageLocalService = mbMessageLocalService;
	}

	@Override
	public boolean isVisible(long classPK, int status) {
		MBMessage message;

		try {
			message = _mbMessageLocalService.getMessage(classPK);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}

		return isVisible(message.getStatus(), status);
	}

	private final MBMessageLocalService _mbMessageLocalService;

}