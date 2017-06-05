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

package com.liferay.commerce.product.type.virtual.web.internal.portlet.action;

import com.liferay.commerce.product.type.virtual.model.CPDefinitionVirtualSetting;
import com.liferay.commerce.product.type.virtual.service.CPDefinitionVirtualSettingService;
import com.liferay.commerce.product.type.virtual.web.internal.constants.CPDefinitionVirtualSettingWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.RenderRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPDefinitionVirtualSettingActionHelper.class)
public class CPDefinitionVirtualSettingActionHelper {

	public CPDefinitionVirtualSetting getCPDefinitionVirtualSetting(
			RenderRequest renderRequest)
		throws PortalException {

		CPDefinitionVirtualSetting cpDefinitionVirtualSetting =
			(CPDefinitionVirtualSetting)renderRequest.getAttribute(
				CPDefinitionVirtualSettingWebKeys.
					CP_DEFINITION_VIRTUAL_SETTING);

		if (cpDefinitionVirtualSetting != null) {
			return cpDefinitionVirtualSetting;
		}

		long cpDefinitionId = ParamUtil.getLong(
			renderRequest, "cpDefinitionId");

		if (cpDefinitionId > 0) {
			cpDefinitionVirtualSetting =
				_cpDefinitionVirtualSettingService.
					fetchCPDefinitionVirtualSettingByCPDefinitionId(
						cpDefinitionId);
		}

		if (cpDefinitionVirtualSetting != null) {
			renderRequest.setAttribute(
				CPDefinitionVirtualSettingWebKeys.CP_DEFINITION_VIRTUAL_SETTING,
				cpDefinitionVirtualSetting);
		}

		return cpDefinitionVirtualSetting;
	}

	@Reference
	private CPDefinitionVirtualSettingService
		_cpDefinitionVirtualSettingService;

}