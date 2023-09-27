/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.simple.internal;

import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;

import java.util.Locale;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	property = {
		"commerce.product.type.display.order:Integer=5",
		"commerce.product.type.name=" + SimpleCPTypeConstants.NAME
	},
	service = CPType.class
)
public class SimpleCPType implements CPType {

	@Override
	public void deleteCPDefinition(long cpDefinitionId) throws PortalException {
	}

	@Override
	public String getLabel(Locale locale) {
		return _language.get(locale, SimpleCPTypeConstants.NAME);
	}

	@Override
	public String getName() {
		return SimpleCPTypeConstants.NAME;
	}

	@Reference
	private Language _language;

}