/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.shop.by.diagram.internal.search.spi.model.result.contributor;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.shop.by.diagram.model.CSDiagramEntry;
import com.liferay.commerce.shop.by.diagram.service.CSDiagramEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

/**
 * @author Alessio Antonio Rendina
 */
public class CSDiagramEntryModelVisibilityContributor
	implements ModelVisibilityContributor {

	public CSDiagramEntryModelVisibilityContributor(
		CSDiagramEntryLocalService csDiagramEntryLocalService) {

		_csDiagramEntryLocalService = csDiagramEntryLocalService;
	}

	@Override
	public boolean isVisible(long classPK, int status) {
		try {
			CSDiagramEntry csDiagramEntry =
				_csDiagramEntryLocalService.getCSDiagramEntry(classPK);

			CPDefinition cpDefinition = csDiagramEntry.getCPDefinition();

			return isVisible(cpDefinition.getStatus(), status);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to check visibility for commerce product " +
						"definition diagram entry",
					portalException);
			}

			return false;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CSDiagramEntryModelVisibilityContributor.class);

	private final CSDiagramEntryLocalService _csDiagramEntryLocalService;

}