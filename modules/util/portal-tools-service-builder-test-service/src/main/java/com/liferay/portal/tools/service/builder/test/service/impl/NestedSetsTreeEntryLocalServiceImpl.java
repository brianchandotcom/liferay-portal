/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.service.builder.test.service.impl;

import com.liferay.portal.tools.service.builder.test.model.NestedSetsTreeEntry;
import com.liferay.portal.tools.service.builder.test.service.base.NestedSetsTreeEntryLocalServiceBaseImpl;

/**
 * @author Brian Wing Shun Chan
 */
public class NestedSetsTreeEntryLocalServiceImpl
	extends NestedSetsTreeEntryLocalServiceBaseImpl {

	@Override
	public NestedSetsTreeEntry addNestedSetsTreeEntry(long groupId) {
		long nestedSetsTreeEntryId = counterLocalService.increment();

		NestedSetsTreeEntry nestedSetsTreeEntry = createNestedSetsTreeEntry(
			nestedSetsTreeEntryId);

		nestedSetsTreeEntry.setGroupId(groupId);
		nestedSetsTreeEntry.setParentNestedSetsTreeEntryId(0);

		addNestedSetsTreeEntry(nestedSetsTreeEntry);

		return nestedSetsTreeEntry;
	}

}