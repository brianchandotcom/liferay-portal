/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.model.listener;

import com.liferay.commerce.product.model.CPConfigurationList;
import com.liferay.commerce.product.service.CPConfigurationListRelLocalService;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Danny Situ
 */
@Component(service = ModelListener.class)
public class CPConfigurationListModelListener
	extends BaseModelListener<CPConfigurationList> {

	public void onAfterRemove(CPConfigurationList cpConfigurationList)
		throws ModelListenerException {

		if (cpConfigurationList == null) {
			return;
		}

		try {
			_cpConfigurationListLocalService.deleteCPConfigurationListRels(
				cpConfigurationList.getCPConfigurationListId());
		}
		catch (PortalException portalException) {
			throw new ModelListenerException(portalException);
		}
	}

	@Reference
	private CPConfigurationListRelLocalService _cpConfigurationListLocalService;

}