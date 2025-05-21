/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.context;

import com.liferay.portal.kernel.model.User;

/**
 * @author Vendel Toreki
 */
public class ImportTaskContext {

	public User getOriginalUser() {
		return _originalUser;
	}

	public void setOriginalUser(User originalUser) {
		_originalUser = originalUser;
	}

	private User _originalUser;

}