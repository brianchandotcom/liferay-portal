/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.item.provider;

import com.liferay.info.item.provider.InfoItemStatusProvider;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;

/**
 * @author Víctor Galán
 */
public class ObjectEntryInfoItemStatusProvider
	implements InfoItemStatusProvider<ObjectEntry> {

	public ObjectEntryInfoItemStatusProvider(
		ObjectDefinition objectDefinition) {

		_objectDefinition = objectDefinition;
	}

	@Override
	public boolean supportsStatus() {
		return _objectDefinition.isEnableObjectEntryDraft();
	}

	private final ObjectDefinition _objectDefinition;

}