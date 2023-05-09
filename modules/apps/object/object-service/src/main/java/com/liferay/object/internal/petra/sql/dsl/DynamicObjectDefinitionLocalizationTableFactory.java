/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.internal.petra.sql.dsl;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionLocalizationTable;
import com.liferay.object.service.persistence.ObjectFieldPersistence;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(service = DynamicObjectDefinitionLocalizationTableFactory.class)
public class DynamicObjectDefinitionLocalizationTableFactory {

	public DynamicObjectDefinitionLocalizationTable create(
		ObjectDefinition objectDefinition) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-146755") ||
			!objectDefinition.isEnableLocalization()) {

			return null;
		}

		return new DynamicObjectDefinitionLocalizationTable(
			objectDefinition,
			_objectFieldPersistence.findByODI_L(
				objectDefinition.getObjectDefinitionId(), true));
	}

	@Reference
	private ObjectFieldPersistence _objectFieldPersistence;

}