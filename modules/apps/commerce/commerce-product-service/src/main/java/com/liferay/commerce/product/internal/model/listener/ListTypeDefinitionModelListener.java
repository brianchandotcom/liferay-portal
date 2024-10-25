/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.model.listener;

import com.liferay.commerce.product.service.CPSpecificationOptionListTypeDefinitionRelLocalService;
import com.liferay.list.type.exception.RequiredListTypeDefinitionException;
import com.liferay.list.type.model.ListTypeDefinition;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Sbarra
 */
@Component(service = ModelListener.class)
public class ListTypeDefinitionModelListener
	extends BaseModelListener<ListTypeDefinition> {

	@Override
	public void onBeforeRemove(ListTypeDefinition listTypeDefinition)
		throws ModelListenerException {

		int count =
			_cpSpecificationOptionListTypeDefinitionRelLocalService.
				getCPSpecificationOptionListTypeDefinitionRelsCount(
					listTypeDefinition.getListTypeDefinitionId());

		if (count > 0) {
			throw new ModelListenerException(
				new RequiredListTypeDefinitionException());
		}
	}

	@Reference
	private CPSpecificationOptionListTypeDefinitionRelLocalService
		_cpSpecificationOptionListTypeDefinitionRelLocalService;

}