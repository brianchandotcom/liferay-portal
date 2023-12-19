/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.web.internal.util;

import com.liferay.object.model.ObjectEntry;
import com.liferay.object.model.ObjectEntryWrapper;

/**
 * @author Eudaldo Alonso
 */
public class ProxyObjectEntry extends ObjectEntryWrapper {

	public ProxyObjectEntry(
		ObjectEntry serviceBuilderObjectEntry,
		com.liferay.object.rest.dto.v1_0.ObjectEntry dtoObjectEntry) {

		super(serviceBuilderObjectEntry);

		_dtoObjectEntry = dtoObjectEntry;
	}

	public com.liferay.object.rest.dto.v1_0.ObjectEntry getDTOObjectEntry() {
		return _dtoObjectEntry;
	}

	private final com.liferay.object.rest.dto.v1_0.ObjectEntry _dtoObjectEntry;

}