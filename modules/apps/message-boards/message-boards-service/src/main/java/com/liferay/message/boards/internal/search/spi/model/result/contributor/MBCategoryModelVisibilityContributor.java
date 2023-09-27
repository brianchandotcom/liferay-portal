/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.search.spi.model.result.contributor;

import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalService;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.search.spi.model.result.contributor.ModelVisibilityContributor;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier Gamarra
 */
@Component(
	property = "indexer.class.name=com.liferay.message.boards.model.MBCategory",
	service = ModelVisibilityContributor.class
)
public class MBCategoryModelVisibilityContributor
	implements ModelVisibilityContributor {

	@Override
	public boolean isVisible(long classPK, int status) {
		MBCategory mbCategory;

		try {
			mbCategory = mbCategoryLocalService.getCategory(classPK);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}

		return isVisible(mbCategory.getStatus(), status);
	}

	@Reference
	protected MBCategoryLocalService mbCategoryLocalService;

}