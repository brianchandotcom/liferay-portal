/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

/**
 * Provides a wrapper for {@link LayoutSetBranchService}.
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSetBranchService
 * @generated
 */
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
	public java.lang.String getBeanIdentifier() {
		return _layoutSetBranchService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_layoutSetBranchService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.LayoutSetBranch addLayoutSetBranch(
		long groupId, boolean privateLayout, java.lang.String name,
		java.lang.String description, boolean master,
		long copyLayoutSetBranchId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetBranchService.addLayoutSetBranch(groupId,
			privateLayout, name, description, master, copyLayoutSetBranchId,
			serviceContext);
	}

	public void deleteLayoutSetBranch(long layoutSetBranchId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutSetBranchService.deleteLayoutSetBranch(layoutSetBranchId);
	}

	public java.util.List<com.liferay.portal.model.LayoutSetBranch> getLayoutSetBranches(
		long groupId, boolean privateLayout)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetBranchService.getLayoutSetBranches(groupId,
			privateLayout);
	}

	public com.liferay.portal.model.LayoutSetBranch mergeLayoutSetBranch(
		long layoutSetBranchId, long mergeLayoutSetBranchId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetBranchService.mergeLayoutSetBranch(layoutSetBranchId,
			mergeLayoutSetBranchId, serviceContext);
	}

	public com.liferay.portal.model.LayoutSetBranch updateLayoutSetBranch(
		long groupId, long layoutSetBranchId, java.lang.String name,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetBranchService.updateLayoutSetBranch(groupId,
			layoutSetBranchId, name, description, serviceContext);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	public LayoutSetBranchService getWrappedLayoutSetBranchService() {
		return _layoutSetBranchService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	public void setWrappedLayoutSetBranchService(
		LayoutSetBranchService layoutSetBranchService) {
		_layoutSetBranchService = layoutSetBranchService;
	}

	public LayoutSetBranchService getWrappedService() {
		return _layoutSetBranchService;
	}

	public void setWrappedService(LayoutSetBranchService layoutSetBranchService) {
		_layoutSetBranchService = layoutSetBranchService;
	}

	private LayoutSetBranchService _layoutSetBranchService;
}