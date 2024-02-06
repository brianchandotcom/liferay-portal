/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.builder.internal.upgrade.v0_1_0;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectRelationship;
import com.liferay.object.service.ObjectDefinitionLocalService;
import com.liferay.object.service.ObjectRelationshipLocalService;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

/**
 * @author Alejandro Tardín
 */
public class DeleteAPIPropertiesToAPIPropertiesUpgradeProcess
	extends UpgradeProcess {

	public DeleteAPIPropertiesToAPIPropertiesUpgradeProcess(
		CompanyLocalService companyLocalService,
		ObjectDefinitionLocalService objectDefinitionLocalService,
		ObjectRelationshipLocalService objectRelationshipLocalService) {

		_companyLocalService = companyLocalService;
		_objectDefinitionLocalService = objectDefinitionLocalService;
		_objectRelationshipLocalService = objectRelationshipLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompanyId(
			companyId -> {
				ObjectDefinition objectDefinition =
					_objectDefinitionLocalService.
						fetchObjectDefinitionByExternalReferenceCode(
							"L_API_PROPERTY", companyId);

				if (objectDefinition == null) {
					return;
				}

				ObjectRelationship objectRelationship =
					_objectRelationshipLocalService.
						fetchObjectRelationshipByExternalReferenceCode(
							"L_API_PROPERTIES_TO_API_PROPERTIES", companyId,
							objectDefinition.getObjectDefinitionId());

				if (objectRelationship == null) {
					return;
				}

				_objectRelationshipLocalService.deleteObjectRelationship(
					objectRelationship);
			});
	}

	private final CompanyLocalService _companyLocalService;
	private final ObjectDefinitionLocalService _objectDefinitionLocalService;
	private final ObjectRelationshipLocalService
		_objectRelationshipLocalService;

}