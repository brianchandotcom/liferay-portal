/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jürgen Kappler
 */
public class AllSectionDisplayContext {

	public String getAPIURL() {
		return "/o/search/v1.0/search?emptySearch=true";
	}

	public List<DropdownItem> getBulkActionDropdownItems() {
		return new ArrayList<>();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return new ArrayList<>();
	}

}