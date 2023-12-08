/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.security.auth;

import com.liferay.portal.kernel.module.service.Snapshot;

/**
 * @author Michael C. Han
 * @author Shuyang Zhou
 */
public class FullNameGeneratorFactory {

	public static FullNameGenerator getInstance() {
		FullNameGenerator fullNameGenerator = _fullNameGeneratorSnapshot.get();

		if (fullNameGenerator != null) {
			return fullNameGenerator;
		}

		return DefaultFullNameGenerator.INSTANCE;
	}

	private FullNameGeneratorFactory() {
	}

	private static final Snapshot<FullNameGenerator>
		_fullNameGeneratorSnapshot = new Snapshot<>(
			FullNameGeneratorFactory.class, FullNameGenerator.class, null,
			true);

}