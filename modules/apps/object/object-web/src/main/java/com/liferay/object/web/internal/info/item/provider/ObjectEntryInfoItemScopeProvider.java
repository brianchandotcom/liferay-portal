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

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.info.constants.InfoItemScopeConstants;
import com.liferay.info.item.provider.InfoItemScopeProvider;
import com.liferay.object.constants.ObjectDefinitionConstants;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.scope.ObjectScopeProvider;
import com.liferay.object.scope.ObjectScopeProviderRegistry;

import java.util.Objects;

/**
 * @author Rubén Pulido
 */
public class ObjectEntryInfoItemScopeProvider
	implements InfoItemScopeProvider<ObjectEntry> {

	public ObjectEntryInfoItemScopeProvider(
		ObjectDefinition objectDefinition,
		ObjectScopeProviderRegistry objectScopeProviderRegistry) {

		_objectDefinition = objectDefinition;
		_objectScopeProviderRegistry = objectScopeProviderRegistry;
	}

	@Override
	public int getScope() {
		ObjectScopeProvider objectScopeProvider =
			_objectScopeProviderRegistry.getObjectScopeProvider(
				_objectDefinition.getScope());

		if (Objects.equals(
				objectScopeProvider.getKey(),
				ObjectDefinitionConstants.SCOPE_COMPANY)) {

			return InfoItemScopeConstants.SCOPE_COMPANY;
		}

		return InfoItemScopeConstants.SCOPE_SITE;
	}

	private final ObjectDefinition _objectDefinition;
	private final ObjectScopeProviderRegistry _objectScopeProviderRegistry;

}