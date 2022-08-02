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

package com.liferay.layout.utility.page.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link LayoutUtilityPageEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutUtilityPageEntryService
 * @generated
 */
public class LayoutUtilityPageEntryServiceWrapper
	implements LayoutUtilityPageEntryService,
			   ServiceWrapper<LayoutUtilityPageEntryService> {

	public LayoutUtilityPageEntryServiceWrapper() {
		this(null);
	}

	public LayoutUtilityPageEntryServiceWrapper(
		LayoutUtilityPageEntryService layoutUtilityPageEntryService) {

		_layoutUtilityPageEntryService = layoutUtilityPageEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutUtilityPageEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public LayoutUtilityPageEntryService getWrappedService() {
		return _layoutUtilityPageEntryService;
	}

	@Override
	public void setWrappedService(
		LayoutUtilityPageEntryService layoutUtilityPageEntryService) {

		_layoutUtilityPageEntryService = layoutUtilityPageEntryService;
	}

	private LayoutUtilityPageEntryService _layoutUtilityPageEntryService;

}