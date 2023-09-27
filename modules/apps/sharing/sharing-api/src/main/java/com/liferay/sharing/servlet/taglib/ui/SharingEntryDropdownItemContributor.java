/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharing.servlet.taglib.ui;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.sharing.model.SharingEntry;

import java.util.List;

/**
 * @author Adolfo Pérez
 */
public interface SharingEntryDropdownItemContributor {

	public List<DropdownItem> getSharingEntryDropdownItems(
		SharingEntry sharingEntry, ThemeDisplay themeDisplay);

}