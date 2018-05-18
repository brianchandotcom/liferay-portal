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