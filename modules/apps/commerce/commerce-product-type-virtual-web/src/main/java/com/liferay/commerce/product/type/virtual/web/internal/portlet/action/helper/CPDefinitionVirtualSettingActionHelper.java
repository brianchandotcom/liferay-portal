/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.virtual.web.internal.portlet.action.helper;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPInstance;
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
		long cpInstanceId = ParamUtil.getLong(renderRequest, "cpInstanceId");

		if (cpInstanceId > 0) {
			cpDefinitionVirtualSetting =
				_cpDefinitionVirtualSettingService.
					fetchCPDefinitionVirtualSetting(
						CPInstance.class.getName(), cpInstanceId);
		}
		else if (cpDefinitionId > 0) {
			cpDefinitionVirtualSetting =
				_cpDefinitionVirtualSettingService.
					fetchCPDefinitionVirtualSetting(
						CPDefinition.class.getName(), cpDefinitionId);
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