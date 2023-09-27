/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.changeset;

/**
 * @author Máté Thurzó
 */
public class ChangesetManagerUtil {

	public static ChangesetManager getChangesetManager() {
		if (_changesetManager != null) {
			return _changesetManager;
		}

		throw new NullPointerException("Changeset manager is null");
	}

	public static void setChangesetManager(ChangesetManager changesetManager) {
		if (_changesetManager != null) {
			changesetManager = _changesetManager;

			return;
		}

		_changesetManager = changesetManager;
	}

	private static ChangesetManager _changesetManager;

}