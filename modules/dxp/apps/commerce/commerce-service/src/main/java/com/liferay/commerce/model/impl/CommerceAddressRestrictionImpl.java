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

package com.liferay.commerce.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.model.CommerceCountry;
import com.liferay.commerce.service.CommerceCountryLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public class CommerceAddressRestrictionImpl
	extends CommerceAddressRestrictionBaseImpl {

	public CommerceAddressRestrictionImpl() {
	}

	@Override
	public CommerceCountry getCommerceCountry() throws PortalException {
		return CommerceCountryLocalServiceUtil.getCommerceCountry(
			getCommerceCountryId());
	}

}