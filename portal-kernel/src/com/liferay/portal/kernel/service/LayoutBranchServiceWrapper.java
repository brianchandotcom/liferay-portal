/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link LayoutBranchService}.
 *
 * @author Brian Wing Shun Chan
 * @see LayoutBranchService
 * @generated
 */
public class LayoutBranchServiceWrapper
	implements LayoutBranchService, ServiceWrapper<LayoutBranchService> {

	public LayoutBranchServiceWrapper() {
		this(null);
	}

	public LayoutBranchServiceWrapper(LayoutBranchService layoutBranchService) {
		_layoutBranchService = layoutBranchService;
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutBranch addLayoutBranch(
			long layoutRevisionId, java.lang.String name,
			java.lang.String description, boolean master,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutBranchService.addLayoutBranch(
			layoutRevisionId, name, description, master, serviceContext);
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
	public java.lang.String getOSGiServiceIdentifier() {
		return _layoutBranchService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.model.LayoutBranch updateLayoutBranch(
			long layoutBranchId, java.lang.String name,
			java.lang.String description, ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _layoutBranchService.updateLayoutBranch(
			layoutBranchId, name, description, serviceContext);
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