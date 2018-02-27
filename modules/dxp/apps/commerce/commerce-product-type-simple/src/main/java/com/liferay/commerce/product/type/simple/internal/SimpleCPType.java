/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.commerce.product.type.simple.internal;

import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.simple.constants.SimpleCPTypeConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
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
	public String getCPDefinitionEditUrl(
			long cpDefinitionId, HttpServletRequest httpServletRequest)
		throws PortalException {

		return null;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, SimpleCPTypeConstants.NAME);
	}

	@Override
	public String getName() {
		return SimpleCPTypeConstants.NAME;
	}

}