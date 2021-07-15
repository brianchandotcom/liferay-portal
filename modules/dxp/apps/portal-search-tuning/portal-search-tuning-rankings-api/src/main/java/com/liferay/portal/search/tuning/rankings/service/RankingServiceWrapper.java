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

package com.liferay.portal.search.tuning.rankings.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link RankingService}.
 *
 * @author Bryan Engler
 * @see RankingService
 * @generated
 */
public class RankingServiceWrapper
	implements RankingService, ServiceWrapper<RankingService> {

	public RankingServiceWrapper(RankingService rankingService) {
		_rankingService = rankingService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _rankingService.getOSGiServiceIdentifier();
	}

	@Override
	public RankingService getWrappedService() {
		return _rankingService;
	}

	@Override
	public void setWrappedService(RankingService rankingService) {
		_rankingService = rankingService;
	}

	private RankingService _rankingService;

}