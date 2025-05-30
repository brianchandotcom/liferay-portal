/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.rest.odata.entity.v1_0.provider;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectField;
import com.liferay.portal.odata.entity.EntityModel;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Carlos Correa
 */
@ProviderType
public interface EntityModelProvider {

	public EntityModel getEntityModel(ObjectDefinition objectDefinition);

	public EntityModel getEntityModel(
		ObjectDefinition objectDefinition, List<ObjectField> objectFields);

	public default EntityModel getLegacyEntityModel(
		ObjectDefinition objectDefinition) {

		return null;
	}

}