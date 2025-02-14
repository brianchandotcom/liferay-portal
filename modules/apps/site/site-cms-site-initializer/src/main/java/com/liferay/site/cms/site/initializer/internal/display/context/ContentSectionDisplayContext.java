/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.DropdownItem;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.site.cms.site.initializer.internal.configuration.CMSSiteInitializerConfiguration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sam Ziemer
 */
public class ContentSectionDisplayContext {

	public ContentSectionDisplayContext(
		CMSSiteInitializerConfiguration cmsSiteInitializerConfiguration) {

		_cmsSiteInitializerConfiguration = cmsSiteInitializerConfiguration;
	}

	public String getAPIURL() {
		StringBundler sb = new StringBundler(3);

		sb.append("/o/search/v1.0/search?emptySearch=true");
		sb.append("&nestedFields=embedded&entryClassNames=");

		sb.append(
			ArrayUtil.toString(
				_cmsSiteInitializerConfiguration.contentClassNames(),
				StringPool.BLANK));

		return sb.toString();
	}

	public List<DropdownItem> getBulkActionDropdownItems() {
		return new ArrayList<>();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return new ArrayList<>();
	}

	private final CMSSiteInitializerConfiguration
		_cmsSiteInitializerConfiguration;

}