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

package com.liferay.commerce.product.type.virtual.web.internal;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.virtual.constants.VirtualCPTypeConstants;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 */
@Component(
	immediate = true,
	property = {
		"commerce.product.type.display.order:Integer=15",
		"commerce.product.type.name=" + VirtualCPTypeConstants.NAME
	},
	service = CPType.class
)
public class VirtualCPType implements CPType {

	@Override
	public void deleteCPDefinition(long cpDefinitionId) throws PortalException {
		_cpDefinitionVirtualSettingLocalService.
			deleteCPDefinitionVirtualSetting(
				CPDefinition.class.getName(), cpDefinitionId);
	}

	@Override
	public String getLabel(Locale locale) {
		ResourceBundle resourceBundle = ResourceBundleUtil.getBundle(
			"content.Language", locale, getClass());

		return LanguageUtil.get(resourceBundle, VirtualCPTypeConstants.NAME);
	}

	@Override
	public String getName() {
		return VirtualCPTypeConstants.NAME;
	}

	@Reference
	private CPDefinitionVirtualSettingLocalService
		_cpDefinitionVirtualSettingLocalService;

}