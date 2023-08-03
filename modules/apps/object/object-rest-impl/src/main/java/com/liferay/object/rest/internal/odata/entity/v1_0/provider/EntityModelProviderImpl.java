/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.internal.odata.entity.v1_0.provider;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.rest.internal.odata.entity.v1_0.ObjectEntryEntityModel;
import com.liferay.object.rest.odata.entity.v1_0.provider.EntityModelProvider;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.odata.entity.EntityModel;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Correa
 */
@Component(service = EntityModelProvider.class)
public class EntityModelProviderImpl implements EntityModelProvider {

	@Override
	public EntityModel getEntityModel(ObjectDefinition objectDefinition) {
		try {
			return new ObjectEntryEntityModel(
				objectDefinition,
				_objectFieldLocalService.getObjectFields(
					objectDefinition.getObjectDefinitionId()));
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@Reference
	private ObjectFieldLocalService _objectFieldLocalService;

}