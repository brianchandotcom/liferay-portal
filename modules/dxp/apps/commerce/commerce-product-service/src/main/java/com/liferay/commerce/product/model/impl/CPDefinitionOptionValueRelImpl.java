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

package com.liferay.commerce.product.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 */
@ProviderType
public class CPDefinitionOptionValueRelImpl
	extends CPDefinitionOptionValueRelBaseImpl {

	public CPDefinitionOptionValueRelImpl() {
	}

	@Override
	public CPDefinitionOptionRel getCPDefinitionOptionRel()
		throws PortalException {

		return CPDefinitionOptionRelLocalServiceUtil.getCPDefinitionOptionRel(
			getCPDefinitionOptionRelId());
	}

}