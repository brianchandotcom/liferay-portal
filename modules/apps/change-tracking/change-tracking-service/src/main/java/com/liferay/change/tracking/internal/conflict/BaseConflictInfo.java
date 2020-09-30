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

package com.liferay.change.tracking.internal.conflict;

import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Preston Crary
 */
public abstract class BaseConflictInfo implements ConflictInfo {

	@Override
	public long getCTAutoResolutionInfoId() {
		return 0;
	}

	@Override
	public ResourceBundle getResourceBundle(Locale locale) {
		return ResourceBundleUtil.getBundle(locale, BaseConflictInfo.class);
	}

	@Override
	public boolean isResolved() {
		return false;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{conflict description=",
			getConflictDescription(getResourceBundle(LocaleUtil.getDefault())),
			", resolved=", isResolved(), ", sourcePrimaryKey=",
			getSourcePrimaryKey(), ", targetPrimaryKey=", getTargetPrimaryKey(),
			"}");
	}

}