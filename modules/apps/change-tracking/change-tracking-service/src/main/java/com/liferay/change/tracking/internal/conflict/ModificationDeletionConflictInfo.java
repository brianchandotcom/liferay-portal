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
public class ModificationDeletionConflictInfo extends BaseConflictInfo {

	public ModificationDeletionConflictInfo(
		long modelClassPK, boolean publicationConflict) {

		_modelClassPK = modelClassPK;
		_publicationConflict = publicationConflict;
	}

	@Override
	public String getConflictDescription(ResourceBundle resourceBundle) {
		return LanguageUtil.get(
			resourceBundle, "modification-deletion-conflict");
	}

	@Override
	public String getResolutionDescription(ResourceBundle resourceBundle) {
		String message = "deletion-conflicts-with-a-modification";

		if (_publicationConflict) {
			message =
				"deletion-conflicts-with-modifications-in-another-publication";
		}

		return LanguageUtil.get(resourceBundle, message);
	}

	@Override
	public long getSourcePrimaryKey() {
		return _modelClassPK;
	}

	@Override
	public long getTargetPrimaryKey() {
		return _modelClassPK;
	}

	private final long _modelClassPK;
	private final boolean _publicationConflict;

}