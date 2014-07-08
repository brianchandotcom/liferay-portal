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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link LayoutSetBranchService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutSetBranchService
 * @generated
 */
@ProviderType
public class LayoutSetBranchServiceWrapper implements LayoutSetBranchService,
	ServiceWrapper<LayoutSetBranchService> {
	public LayoutSetBranchServiceWrapper(
		LayoutSetBranchService layoutSetBranchService) {
		_layoutSetBranchService = layoutSetBranchService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _layoutSetBranchService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_layoutSetBranchService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portal.model.LayoutSetBranch addLayoutSetBranch(
		long groupId, boolean privateLayout, java.lang.String name,
		java.lang.String description, boolean master,
		long copyLayoutSetBranchId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetBranchService.addLayoutSetBranch(groupId,
			privateLayout, name, description, master, copyLayoutSetBranchId,
			serviceContext);
	}

	@Override
	public void deleteLayoutSetBranch(long layoutSetBranchId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_layoutSetBranchService.deleteLayoutSetBranch(layoutSetBranchId);
	}

	@Override
	public java.util.List<com.liferay.portal.model.LayoutSetBranch> getLayoutSetBranches(
		long groupId, boolean privateLayout) {
		return _layoutSetBranchService.getLayoutSetBranches(groupId,
			privateLayout);
	}

	@Override
	public com.liferay.portal.model.LayoutSetBranch mergeLayoutSetBranch(
		long layoutSetBranchId, long mergeLayoutSetBranchId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetBranchService.mergeLayoutSetBranch(layoutSetBranchId,
			mergeLayoutSetBranchId, serviceContext);
	}

	@Override
	public com.liferay.portal.model.LayoutSetBranch updateLayoutSetBranch(
		long groupId, long layoutSetBranchId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _layoutSetBranchService.updateLayoutSetBranch(groupId,
			layoutSetBranchId, name, description, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public LayoutSetBranchService getWrappedLayoutSetBranchService() {
		return _layoutSetBranchService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedLayoutSetBranchService(
		LayoutSetBranchService layoutSetBranchService) {
		_layoutSetBranchService = layoutSetBranchService;
	}

	@Override
	public LayoutSetBranchService getWrappedService() {
		return _layoutSetBranchService;
	}

	@Override
	public void setWrappedService(LayoutSetBranchService layoutSetBranchService) {
		_layoutSetBranchService = layoutSetBranchService;
	}

	private LayoutSetBranchService _layoutSetBranchService;
}