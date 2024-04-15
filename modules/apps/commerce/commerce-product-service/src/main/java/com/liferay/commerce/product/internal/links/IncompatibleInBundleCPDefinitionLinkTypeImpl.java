/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.links;

import com.liferay.commerce.product.constants.CPDefinitionLinkTypeConstants;
import com.liferay.commerce.product.links.CPDefinitionLinkType;

import org.osgi.service.component.annotations.Component;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPDefinitionLinkType.class)
public class IncompatibleInBundleCPDefinitionLinkTypeImpl
	implements CPDefinitionLinkType {

	@Override
	public String getType() {
		return CPDefinitionLinkTypeConstants.INCOMPATIBLE_IN_BUNDLE;
	}

	@Override
	public boolean isActive() {
		return true;
	}

	@Override
	public boolean isBiDirectional() {
		return true;
	}

}