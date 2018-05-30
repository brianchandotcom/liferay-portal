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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CPDisplayLayoutService}.
 *
 * @author Marco Leo
 * @see CPDisplayLayoutService
 * @generated
 */
@ProviderType
public class CPDisplayLayoutServiceWrapper implements CPDisplayLayoutService,
	ServiceWrapper<CPDisplayLayoutService> {
	public CPDisplayLayoutServiceWrapper(
		CPDisplayLayoutService cpDisplayLayoutService) {
		_cpDisplayLayoutService = cpDisplayLayoutService;
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _cpDisplayLayoutService.getOSGiServiceIdentifier();
	}

	@Override
	public CPDisplayLayoutService getWrappedService() {
		return _cpDisplayLayoutService;
	}

	@Override
	public void setWrappedService(CPDisplayLayoutService cpDisplayLayoutService) {
		_cpDisplayLayoutService = cpDisplayLayoutService;
	}

	private CPDisplayLayoutService _cpDisplayLayoutService;
}