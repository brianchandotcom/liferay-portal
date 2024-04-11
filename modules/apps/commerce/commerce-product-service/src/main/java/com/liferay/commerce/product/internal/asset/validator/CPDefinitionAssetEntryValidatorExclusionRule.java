/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.asset.validator;

import com.liferay.asset.kernel.validator.AssetEntryValidatorExclusionRule;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.service.CPDefinitionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian I. Kim
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CPDefinition",
	service = AssetEntryValidatorExclusionRule.class
)
public class CPDefinitionAssetEntryValidatorExclusionRule
	implements AssetEntryValidatorExclusionRule {

	@Override
	public boolean isValidationExcluded(
		long groupId, String className, long classPK, long classTypePK,
		long[] assetCategoryIds, String[] assetTagNames) {

		CPDefinition cpDefinition = _cpDefinitionLocalService.fetchCPDefinition(
			classPK);

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		if ((cpDefinition != null) &&
			(serviceContext.getWorkflowAction() !=
				WorkflowConstants.ACTION_PUBLISH)) {

			return true;
		}

		return false;
	}

	@Reference
	private CPDefinitionLocalService _cpDefinitionLocalService;

}