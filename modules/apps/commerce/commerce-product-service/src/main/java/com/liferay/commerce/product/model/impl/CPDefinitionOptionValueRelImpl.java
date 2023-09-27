/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.model.impl;

import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.service.CPDefinitionOptionRelLocalServiceUtil;
import com.liferay.commerce.product.service.CPInstanceLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 */
public class CPDefinitionOptionValueRelImpl
	extends CPDefinitionOptionValueRelBaseImpl {

	@Override
	public CPInstance fetchCPInstance() {
		return CPInstanceLocalServiceUtil.fetchCProductInstance(
			getCProductId(), getCPInstanceUuid());
	}

	@Override
	public CPDefinitionOptionRel getCPDefinitionOptionRel()
		throws PortalException {

		return CPDefinitionOptionRelLocalServiceUtil.getCPDefinitionOptionRel(
			getCPDefinitionOptionRelId());
	}

}