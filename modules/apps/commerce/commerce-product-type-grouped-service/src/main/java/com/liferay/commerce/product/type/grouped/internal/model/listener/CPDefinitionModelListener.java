/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.type.grouped.internal.model.listener;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryLocalService;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 */
@Component(service = ModelListener.class)
public class CPDefinitionModelListener extends BaseModelListener<CPDefinition> {

	@Override
	public void onBeforeRemove(CPDefinition cpDefinition) {
		_cpDefinitionGroupedEntryLocalService.
			deleteEntryCProductCPDefinitionGroupedEntries(
				cpDefinition.getCProductId());
	}

	@Reference
	private CPDefinitionGroupedEntryLocalService
		_cpDefinitionGroupedEntryLocalService;

}