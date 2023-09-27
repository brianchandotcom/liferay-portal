/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.layoutconfiguration.util.velocity;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.portlet.PortletProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 */
public class InitColumnProcessor implements ColumnProcessor {

	public List<String> getColumns() {
		return _columns;
	}

	@Override
	public String processColumn(String columnId) {
		_columns.add(columnId);

		return StringPool.BLANK;
	}

	@Override
	public String processColumn(String columnId, String classNames) {
		_columns.add(columnId);

		return StringPool.BLANK;
	}

	@Override
	public String processDynamicColumn(String columnId, String classNames)
		throws Exception {

		_columns.add(columnId);

		return StringPool.BLANK;
	}

	@Override
	public String processMax() {
		return StringPool.BLANK;
	}

	@Override
	public String processPortlet(String portletId) {
		return StringPool.BLANK;
	}

	@Override
	public String processPortlet(
		String portletId, Map<String, ?> defaultSettingsMap) {

		return StringPool.BLANK;
	}

	@Override
	public String processPortlet(
		String portletProviderClassName,
		PortletProvider.Action portletProviderAction) {

		return StringPool.BLANK;
	}

	private final List<String> _columns = new ArrayList<>();

}