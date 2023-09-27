/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.impl;

import com.liferay.portal.tools.service.builder.test.model.NullConvertibleEntry;
import com.liferay.portal.tools.service.builder.test.service.base.NullConvertibleEntryLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class NullConvertibleEntryLocalServiceImpl
	extends NullConvertibleEntryLocalServiceBaseImpl {

	@Override
	public NullConvertibleEntry addNullConvertibleEntry(String name) {
		long nullConvertibleEntryId = counterLocalService.increment();

		NullConvertibleEntry nullConvertibleEntry =
			nullConvertibleEntryPersistence.create(nullConvertibleEntryId);

		nullConvertibleEntry.setName(name);

		return nullConvertibleEntryPersistence.update(nullConvertibleEntry);
	}

	@Override
	public NullConvertibleEntry fetchNullConvertibleEntry(String name) {
		return nullConvertibleEntryPersistence.fetchByName(name);
	}

	@Override
	public int getNullConvertibleEntries(String name) {
		return nullConvertibleEntryPersistence.countByName(name);
	}

}