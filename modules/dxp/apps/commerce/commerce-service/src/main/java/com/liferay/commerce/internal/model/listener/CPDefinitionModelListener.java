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

package com.liferay.commerce.internal.model.listener;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.service.CPDefinitionAvailabilityRangeLocalService;
import com.liferay.commerce.service.CPDefinitionInventoryLocalService;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 * @author Andrea Di Giorgi
 * @author Marco Leo
 */
@Component(immediate = true, service = ModelListener.class)
public class CPDefinitionModelListener extends BaseModelListener<CPDefinition> {

	@Override
	public void onBeforeRemove(CPDefinition cpDefinition) {
		long cpDefinitionId = cpDefinition.getCPDefinitionId();

		_cpDefinitionAvailabilityRangeLocalService.
			deleteCPDefinitionAvailabilityRangeByCPDefinitionId(cpDefinitionId);
		_cpDefinitionInventoryLocalService.
			deleteCPDefinitionInventoryByCPDefinitionId(cpDefinitionId);
	}

	@Reference
	private CPDefinitionAvailabilityRangeLocalService
		_cpDefinitionAvailabilityRangeLocalService;

	@Reference
	private CPDefinitionInventoryLocalService
		_cpDefinitionInventoryLocalService;

}