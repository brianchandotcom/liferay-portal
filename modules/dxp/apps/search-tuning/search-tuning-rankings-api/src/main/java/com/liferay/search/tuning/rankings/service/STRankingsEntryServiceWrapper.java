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

package com.liferay.search.tuning.rankings.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link STRankingsEntryService}.
 *
 * @author Bryan Engler
 * @see STRankingsEntryService
 * @generated
 */
public class STRankingsEntryServiceWrapper
	implements ServiceWrapper<STRankingsEntryService>, STRankingsEntryService {

	public STRankingsEntryServiceWrapper(
		STRankingsEntryService stRankingsEntryService) {

		_stRankingsEntryService = stRankingsEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _stRankingsEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public STRankingsEntryService getWrappedService() {
		return _stRankingsEntryService;
	}

	@Override
	public void setWrappedService(
		STRankingsEntryService stRankingsEntryService) {

		_stRankingsEntryService = stRankingsEntryService;
	}

	private STRankingsEntryService _stRankingsEntryService;

}