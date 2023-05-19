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
 * Provides a wrapper for {@link CompareRunsService}.
 *
 * @author José Abelenda
 * @see CompareRunsService
 * @generated
 */
public class CompareRunsServiceWrapper
	implements CompareRunsService, ServiceWrapper<CompareRunsService> {

	public CompareRunsServiceWrapper() {
		this(null);
	}

	public CompareRunsServiceWrapper(CompareRunsService compareRunsService) {
		_compareRunsService = compareRunsService;
	}

	@Override
	public int getComparison(
		long companyId, long testrayRunId1, long testrayRunId2,
		String testrayDueStatus1, String testrayDueStatus2) {

		return _compareRunsService.getComparison(
			companyId, testrayRunId1, testrayRunId2, testrayDueStatus1,
			testrayDueStatus2);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _compareRunsService.getOSGiServiceIdentifier();
	}

	@Override
	public CompareRunsService getWrappedService() {
		return _compareRunsService;
	}

	@Override
	public void setWrappedService(CompareRunsService compareRunsService) {
		_compareRunsService = compareRunsService;
	}

	private CompareRunsService _compareRunsService;

}