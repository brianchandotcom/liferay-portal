/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.internal.conflict;

import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.ResourceBundle;

/**
 * @author Preston Crary
 */
public class DefaultConstraintConflictInfo extends BaseConflictInfo {

	public DefaultConstraintConflictInfo(
		long sourcePrimaryKey, long targetPrimaryKey,
		String uniqueColumnNames) {

		_sourcePrimaryKey = sourcePrimaryKey;
		_targetPrimaryKey = targetPrimaryKey;
		_uniqueColumnNames = uniqueColumnNames;
	}

	@Override
	public String getConflictDescription(ResourceBundle resourceBundle) {
		return LanguageUtil.format(
			resourceBundle, "values-for-x-are-not-unique", _uniqueColumnNames,
			false);
	}

	@Override
	public String getResolutionDescription(ResourceBundle resourceBundle) {
		return LanguageUtil.format(
			resourceBundle, "update-values-to-be-unique", _uniqueColumnNames,
			false);
	}

	@Override
	public long getSourcePrimaryKey() {
		return _sourcePrimaryKey;
	}

	@Override
	public long getTargetPrimaryKey() {
		return _targetPrimaryKey;
	}

	private final long _sourcePrimaryKey;
	private final long _targetPrimaryKey;
	private final String _uniqueColumnNames;

}