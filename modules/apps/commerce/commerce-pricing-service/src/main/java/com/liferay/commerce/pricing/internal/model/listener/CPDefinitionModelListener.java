/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.pricing.internal.model.listener;

import com.liferay.commerce.pricing.model.CommercePricingClassCPDefinitionRel;
import com.liferay.commerce.pricing.service.CommercePricingClassCPDefinitionRelLocalService;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lianne Louie
 */
@Component(service = ModelListener.class)
public class CPDefinitionModelListener extends BaseModelListener<CPDefinition> {

	@Override
	public void onBeforeRemove(CPDefinition cpDefinition) {
		List<CommercePricingClassCPDefinitionRel>
			commercePricingClassCPDefinitionRels =
				_commercePricingClassCPDefinitionRelLocalService.
					getCommercePricingClassByCPDefinitionId(
						cpDefinition.getCPDefinitionId());

		try {
			for (CommercePricingClassCPDefinitionRel
					commercePricingClassCPDefinitionRel :
						commercePricingClassCPDefinitionRels) {

				_commercePricingClassCPDefinitionRelLocalService.
					deleteCommercePricingClassCPDefinitionRel(
						commercePricingClassCPDefinitionRel);
			}
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Reference
	private CommercePricingClassCPDefinitionRelLocalService
		_commercePricingClassCPDefinitionRelLocalService;

}