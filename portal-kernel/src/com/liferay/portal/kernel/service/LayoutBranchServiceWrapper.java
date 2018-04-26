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

package com.liferay.portal.kernel.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link LayoutBranchService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutBranchService
 * @generated
 */
@ProviderType
public class LayoutBranchServiceWrapper implements LayoutBranchService,
	ServiceWrapper<LayoutBranchService> {
	public LayoutBranchServiceWrapper(LayoutBranchService layoutBranchService) {
		_layoutBranchService = layoutBranchService;
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutBranch addLayoutBranch(
		long layoutRevisionId, String name, String description, boolean master,
		ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutBranchService.addLayoutBranch(layoutRevisionId, name,
			description, master, serviceContext);
	}

	@Override
	public void deleteLayoutBranch(long layoutBranchId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutBranchService.deleteLayoutBranch(layoutBranchId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _layoutBranchService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutBranch updateLayoutBranch(
		long layoutBranchId, String name, String description,
		ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutBranchService.updateLayoutBranch(layoutBranchId, name,
			description, serviceContext);
	}

	@Override
	public LayoutBranchService getWrappedService() {
		return _layoutBranchService;
	}

	@Override
	public void setWrappedService(LayoutBranchService layoutBranchService) {
		_layoutBranchService = layoutBranchService;
	}

	private LayoutBranchService _layoutBranchService;
}