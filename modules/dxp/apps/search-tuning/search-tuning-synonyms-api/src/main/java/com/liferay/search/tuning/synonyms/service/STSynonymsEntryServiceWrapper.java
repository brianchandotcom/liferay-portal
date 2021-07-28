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

package com.liferay.search.tuning.synonyms.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link STSynonymsEntryService}.
 *
 * @author Bryan Engler
 * @see STSynonymsEntryService
 * @generated
 */
public class STSynonymsEntryServiceWrapper
	implements ServiceWrapper<STSynonymsEntryService>, STSynonymsEntryService {

	public STSynonymsEntryServiceWrapper(
		STSynonymsEntryService stSynonymsEntryService) {

		_stSynonymsEntryService = stSynonymsEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _stSynonymsEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public STSynonymsEntryService getWrappedService() {
		return _stSynonymsEntryService;
	}

	@Override
	public void setWrappedService(
		STSynonymsEntryService stSynonymsEntryService) {

		_stSynonymsEntryService = stSynonymsEntryService;
	}

	private STSynonymsEntryService _stSynonymsEntryService;

}