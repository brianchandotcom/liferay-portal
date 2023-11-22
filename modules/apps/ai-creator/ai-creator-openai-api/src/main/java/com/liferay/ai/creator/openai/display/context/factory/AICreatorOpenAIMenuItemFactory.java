/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.ai.creator.openai.display.context.factory;

import com.liferay.portal.kernel.servlet.taglib.ui.MenuItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;

/**
 * @author Roberto Díaz
 */
public interface AICreatorOpenAIMenuItemFactory {

	public MenuItem createAICreatorCreateImageMenuItem(
		long repositoryId, long folderId, long fileEntryTypeId,
		ThemeDisplay themeDisplay);

}