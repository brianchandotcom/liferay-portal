/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.changeset;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Máté Thurzó
 */
public class ChangesetManagerUtil {

	public static ChangesetManager getChangesetManager() {
		ChangesetManager changesetManager = _changesetManagerSnapshot.get();

		if (changesetManager == null) {
			throw new NullPointerException("Changeset manager is null");
		}

		return changesetManager;
	}

	private static final Snapshot<ChangesetManager> _changesetManagerSnapshot =
		new Snapshot<>(ChangesetManagerUtil.class, ChangesetManager.class);

}