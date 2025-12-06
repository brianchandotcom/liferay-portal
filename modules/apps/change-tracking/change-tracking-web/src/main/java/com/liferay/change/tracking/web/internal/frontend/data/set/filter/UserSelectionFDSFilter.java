/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.frontend.data.set.filter;

import com.liferay.frontend.data.set.filter.BaseSelectionFDSFilter;
import com.liferay.frontend.data.set.filter.FDSFilter;
import com.liferay.frontend.data.set.filter.SelectionFDSFilterItem;
import com.liferay.petra.function.transform.TransformUtil;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Noor Najjar
 */
@Component(service = FDSFilter.class)
public class UserSelectionFDSFilter extends BaseSelectionFDSFilter {

	public UserSelectionFDSFilter(Map<String, Object> usersMap) {
		_usersMap = usersMap;
	}

	@Override
	public String getId() {
		return "ownerId";
	}

	@Override
	public String getLabel() {
		return "users";
	}

	@Override
	public List<SelectionFDSFilterItem> getSelectionFDSFilterItems(
		Locale locale) {

		return TransformUtil.transform(
			_usersMap.entrySet(),
			entry -> {
				Map<String, String> user =
					(Map<String, String>)entry.getValue();

				return new SelectionFDSFilterItem(
					user.get("userName"), String.valueOf(entry.getKey()));
			});
	}

	private final Map<String, Object> _usersMap;

}