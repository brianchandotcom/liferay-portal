/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.object.model.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.TextFormatter;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectDefinitionImpl extends ObjectDefinitionBaseImpl {

	public static String getShortName(String name) {
		String shortName = name;

		if (shortName.startsWith("C_")) {
			shortName = shortName.substring(2);
		}

		return shortName;
	}

	@Override
	public String getClassName() {
		if (isSystem()) {
			throw new UnsupportedOperationException();
		}

		return getDBTableName();
	}

	@Override
	public String getPortletId() {
		if (isSystem()) {
			throw new UnsupportedOperationException();
		}

		return getDBTableName();
	}

	@Override
	public String getRESTContextPath() {
		if (isSystem()) {
			throw new UnsupportedOperationException();
		}

		return StringBundler.concat(
			"O_", getCompanyId(), "/",
			TextFormatter.formatPlural(StringUtil.toLowerCase(getShortName())));
	}

	@Override
	public String getShortName() {
		return getShortName(getName());
	}

}