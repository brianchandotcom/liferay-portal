/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Galluzzi
 */
public class StructureUsagesDisplayContext {

	public StructureUsagesDisplayContext(
		HttpServletRequest httpServletRequest) {

		_httpServletRequest = httpServletRequest;
	}

	public String getAPIURL() {
		StringBundler sb = new StringBundler(4);

		sb.append("/o/search/v1.0/search?emptySearch=true&");
		sb.append("filter=objectDefinitionId eq ");
		sb.append(ParamUtil.getLong(_httpServletRequest, "objectDefinitionId"));
		sb.append("&nestedFields=embedded");

		return sb.toString();
	}

	public List<DropdownItem> getBulkActionDropdownItems() {
		return Collections.emptyList();
	}

	private final HttpServletRequest _httpServletRequest;

}