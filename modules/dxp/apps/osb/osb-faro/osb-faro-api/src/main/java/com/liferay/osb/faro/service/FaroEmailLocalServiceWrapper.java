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

package com.liferay.osb.faro.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link FaroEmailLocalService}.
 *
 * @author Matthew Kong
 * @see FaroEmailLocalService
 * @generated
 */
public class FaroEmailLocalServiceWrapper
	implements FaroEmailLocalService, ServiceWrapper<FaroEmailLocalService> {

	public FaroEmailLocalServiceWrapper(
		FaroEmailLocalService faroEmailLocalService) {

		_faroEmailLocalService = faroEmailLocalService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _faroEmailLocalService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.ResourceBundle getResourceBundle(java.util.Locale locale) {
		return _faroEmailLocalService.getResourceBundle(locale);
	}

	@Override
	public String getTemplate(String name) throws Exception {
		return _faroEmailLocalService.getTemplate(name);
	}

	@Override
	public FaroEmailLocalService getWrappedService() {
		return _faroEmailLocalService;
	}

	@Override
	public void setWrappedService(FaroEmailLocalService faroEmailLocalService) {
		_faroEmailLocalService = faroEmailLocalService;
	}

	private FaroEmailLocalService _faroEmailLocalService;

}