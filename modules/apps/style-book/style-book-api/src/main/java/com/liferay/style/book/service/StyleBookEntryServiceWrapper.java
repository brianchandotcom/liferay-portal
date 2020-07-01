/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.style.book.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link StyleBookEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see StyleBookEntryService
 * @generated
 */
public class StyleBookEntryServiceWrapper
	implements ServiceWrapper<StyleBookEntryService>, StyleBookEntryService {

	public StyleBookEntryServiceWrapper(
		StyleBookEntryService styleBookEntryService) {

		_styleBookEntryService = styleBookEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _styleBookEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public StyleBookEntryService getWrappedService() {
		return _styleBookEntryService;
	}

	@Override
	public void setWrappedService(StyleBookEntryService styleBookEntryService) {
		_styleBookEntryService = styleBookEntryService;
	}

	private StyleBookEntryService _styleBookEntryService;

}