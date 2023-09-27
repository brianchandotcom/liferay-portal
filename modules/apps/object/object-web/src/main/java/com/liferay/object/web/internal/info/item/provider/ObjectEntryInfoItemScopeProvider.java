/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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