/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cmp.site.initializer.internal.display.context;

import com.liferay.list.type.service.ListTypeEntryLocalService;
import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Pedro Leite
 */
public class ViewTaskInfoSummarySectionDisplayContext
	extends BaseInfoSummarySectionDisplayContext {

	public ViewTaskInfoSummarySectionDisplayContext(
		ListTypeEntryLocalService listTypeEntryLocalService,
		ObjectEntry objectEntry,
		ObjectFieldLocalService objectFieldLocalService,
		ThemeDisplay themeDisplay) {

		super(
			listTypeEntryLocalService, objectEntry, objectFieldLocalService,
			themeDisplay);
	}

	@Override
	public Map<String, Object> getProperties() throws Exception {
		return HashMapBuilder.<String, Object>put(
			"taskId", objectEntry.getObjectEntryId()
		).putAll(
			super.getProperties()
		).build();
	}

}