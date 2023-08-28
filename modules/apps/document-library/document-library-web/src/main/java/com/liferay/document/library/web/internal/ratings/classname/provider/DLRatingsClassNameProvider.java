/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.web.internal.ratings.classname.provider;

import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.ratings.page.ratings.classname.provider.RatingsClassNameProvider;

import org.osgi.service.component.annotations.Component;

/**
 * @author Roberto Díaz
 */
@Component(
	property = {
		"model.class.name=com.liferay.document.library.kernel.model.DLFileEntry",
		"model.class.name=com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry"
	},
	service = RatingsClassNameProvider.class
)
public class DLRatingsClassNameProvider implements RatingsClassNameProvider {

	@Override
	public String getClassName() {
		return DLFileEntryConstants.getClassName();
	}

}