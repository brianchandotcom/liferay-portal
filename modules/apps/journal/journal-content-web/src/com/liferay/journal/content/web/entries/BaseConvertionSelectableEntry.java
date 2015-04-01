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

package com.liferay.journal.content.web.entries;

import com.liferay.journal.content.web.util.UserToolEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.model.BaseSelectableEntry;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.util.DocumentConversionUtil;
public abstract class BaseConvertionSelectableEntry
	extends BaseSelectableEntry implements UserToolEntry {

	public abstract String getExtension();

	@Override
	public boolean isEnabled() {
		if (!PrefsPropsUtil.getBoolean(
				PropsKeys.OPENOFFICE_SERVER_ENABLED,
				PropsValues.OPENOFFICE_SERVER_ENABLED)) {

			return false;
		}

		if (!ArrayUtil.contains(
				DocumentConversionUtil.getConversions("html"),
				getExtension())) {

			return false;
		}

		return super.isEnabled();
	}

}