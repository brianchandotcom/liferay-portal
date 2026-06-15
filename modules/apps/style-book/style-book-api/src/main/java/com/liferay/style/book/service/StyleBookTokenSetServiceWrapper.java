/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.style.book.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link StyleBookTokenSetService}.
 *
 * @author Brian Wing Shun Chan
 * @see StyleBookTokenSetService
 * @generated
 */
public class StyleBookTokenSetServiceWrapper
	implements ServiceWrapper<StyleBookTokenSetService>,
			   StyleBookTokenSetService {

	public StyleBookTokenSetServiceWrapper() {
		this(null);
	}

	public StyleBookTokenSetServiceWrapper(
		StyleBookTokenSetService styleBookTokenSetService) {

		_styleBookTokenSetService = styleBookTokenSetService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _styleBookTokenSetService.getOSGiServiceIdentifier();
	}

	@Override
	public StyleBookTokenSetService getWrappedService() {
		return _styleBookTokenSetService;
	}

	@Override
	public void setWrappedService(
		StyleBookTokenSetService styleBookTokenSetService) {

		_styleBookTokenSetService = styleBookTokenSetService;
	}

	private StyleBookTokenSetService _styleBookTokenSetService;

}
// LIFERAY-SERVICE-BUILDER-HASH:2101222606