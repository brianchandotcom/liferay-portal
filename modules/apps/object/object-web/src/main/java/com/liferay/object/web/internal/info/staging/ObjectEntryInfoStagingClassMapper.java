/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.info.staging;

import com.liferay.info.staging.InfoStagingClassMapper;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;

/**
 * @author Eudaldo Alonso
 */
public class ObjectEntryInfoStagingClassMapper
	implements InfoStagingClassMapper<ObjectEntry> {

	public ObjectEntryInfoStagingClassMapper(
		ObjectDefinition objectDefinition) {

		_objectDefinition = objectDefinition;
	}

	@Override
	public String getClassName() {
		return _objectDefinition.getClassName();
	}

	@Override
	public String getStagingClassName() {
		return _objectDefinition.getExternalReferenceCode();
	}

	private final ObjectDefinition _objectDefinition;

}