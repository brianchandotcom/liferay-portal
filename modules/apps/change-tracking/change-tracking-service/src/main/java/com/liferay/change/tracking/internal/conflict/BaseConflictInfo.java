/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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