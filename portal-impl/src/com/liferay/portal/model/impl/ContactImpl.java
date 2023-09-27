/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.FullNameGenerator;
import com.liferay.portal.kernel.security.auth.FullNameGeneratorFactory;
import com.liferay.portal.kernel.util.PortalUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class ContactImpl extends ContactBaseImpl {

	@Override
	public String getFullName() {
		FullNameGenerator fullNameGenerator =
			FullNameGeneratorFactory.getInstance();

		return fullNameGenerator.getFullName(
			getFirstName(), getMiddleName(), getLastName());
	}

	@Override
	public boolean isUser() {
		if (getClassNameId() == ClassNameIds._USER_CLASS_NAME_ID) {
			return true;
		}

		return false;
	}

	private static class ClassNameIds {

		private ClassNameIds() {
		}

		private static final long _USER_CLASS_NAME_ID =
			PortalUtil.getClassNameId(User.class);

	}

}