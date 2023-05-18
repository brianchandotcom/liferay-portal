/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.osb.testray.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CompareRunsLocalService}.
 *
 * @author José Abelenda
 * @see CompareRunsLocalService
 * @generated
 */
public class CompareRunsLocalServiceWrapper
	implements CompareRunsLocalService,
			   ServiceWrapper<CompareRunsLocalService> {

	public CompareRunsLocalServiceWrapper() {
		this(null);
	}

	public CompareRunsLocalServiceWrapper(
		CompareRunsLocalService compareRunsLocalService) {

		_compareRunsLocalService = compareRunsLocalService;
	}

	@Override
	public int getComparison(
		long companyId, long runIdA, long runIdB, String statusA,
		String statusB) {

		return _compareRunsLocalService.getComparison(
			companyId, runIdA, runIdB, statusA, statusB);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _compareRunsLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public CompareRunsLocalService getWrappedService() {
		return _compareRunsLocalService;
	}

	@Override
	public void setWrappedService(
		CompareRunsLocalService compareRunsLocalService) {

		_compareRunsLocalService = compareRunsLocalService;
	}

	private CompareRunsLocalService _compareRunsLocalService;

}