/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.site.cms.site.initializer.internal.display.context;

import com.liferay.frontend.data.set.model.FDSActionDropdownItem;
import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.model.ObjectEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Collections;
import java.util.List;

/**
 * @author Mikel Lorza
 */
public class ViewVersionHistoryDisplayContext {

	public ViewVersionHistoryDisplayContext(
		ObjectDefinition objectDefinition, ObjectEntry objectEntry) {

		_objectDefinition = objectDefinition;
		_objectEntry = objectEntry;
	}

	public String getAPIURL() throws PortalException {
		StringBundler sb = new StringBundler(5);

		sb.append("/o");
		sb.append(_objectDefinition.getRESTContextPath());
		sb.append(StringPool.SLASH);
		sb.append(_objectEntry.getObjectEntryId());
		sb.append("/versions");

		return sb.toString();
	}

	public List<FDSActionDropdownItem> getFDSActionDropdownItems() {
		return Collections.emptyList();
	}

	private final ObjectDefinition _objectDefinition;
	private final ObjectEntry _objectEntry;

}