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
import com.liferay.commerce.product.service.CPDefinitionLocalServiceUtil;

/**
 * @author Alessio Antonio Rendina
 */
@ProviderType
public class CPDefinitionLinkImpl extends CPDefinitionLinkBaseImpl {

	public CPDefinitionLinkImpl() {
	}

	@Override
	public CPDefinition getCPDefinition1() {
		return CPDefinitionLocalServiceUtil.fetchCPDefinition(
			getCPDefinitionId1());
	}

	@Override
	public CPDefinition getCPDefinition2() {
		return CPDefinitionLocalServiceUtil.fetchCPDefinition(
			getCPDefinitionId2());
	}

}