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

package com.liferay.commerce.inventory.web.internal.portlet.action;

import com.liferay.commerce.inventory.web.internal.constants.CPDefinitionInventoryWebKeys;
import com.liferay.commerce.model.CPDefinitionInventory;
import com.liferay.commerce.service.CPDefinitionInventoryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ParamUtil;

import javax.portlet.RenderRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CPDefinitionInventoryActionHelper.class)
public class CPDefinitionInventoryActionHelper {

	public CPDefinitionInventory getCPDefinitionInventory(
			RenderRequest renderRequest)
		throws PortalException {

		CPDefinitionInventory cpDefinitionInventory =
			(CPDefinitionInventory)renderRequest.getAttribute(
				CPDefinitionInventoryWebKeys.CP_DEFINITION_INVENTORY);

		if (cpDefinitionInventory != null) {
			return cpDefinitionInventory;
		}

		long cpDefinitionId = ParamUtil.getLong(
			renderRequest, "cpDefinitionId");

		if (cpDefinitionId > 0) {
			cpDefinitionInventory =
				_cpDefinitionInventoryService.
					fetchCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);
		}

		if (cpDefinitionInventory != null) {
			renderRequest.setAttribute(
				CPDefinitionInventoryWebKeys.CP_DEFINITION_INVENTORY,
				cpDefinitionInventory);
		}

		return cpDefinitionInventory;
	}

	@Reference
	private CPDefinitionInventoryService _cpDefinitionInventoryService;

}