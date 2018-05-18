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

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;
import com.liferay.commerce.product.service.CPDefinitionOptionValueRelLocalServiceUtil;
import com.liferay.commerce.product.service.CPOptionLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.List;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 */
@ProviderType
public class CPDefinitionOptionRelImpl extends CPDefinitionOptionRelBaseImpl {

	public CPDefinitionOptionRelImpl() {
	}

	@Override
	public CPDefinition getCPDefinition() throws PortalException {
		return CPDefinitionLocalServiceUtil.getCPDefinition(
			getCPDefinitionId());
	}

	@Override
	public List<CPDefinitionOptionValueRel> getCPDefinitionOptionValueRels() {
		return CPDefinitionOptionValueRelLocalServiceUtil.
			getCPDefinitionOptionValueRels(
				getCPDefinitionOptionRelId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);
	}

	@Override
	public CPOption getCPOption() throws PortalException {
		return CPOptionLocalServiceUtil.getCPOption(getCPOptionId());
	}

}