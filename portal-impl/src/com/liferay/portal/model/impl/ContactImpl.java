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

package com.liferay.portal.model.impl;

import com.liferay.portal.kernel.json.JSON;
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

	/**
	 * Always returns true, since this information is no longer stored in the database
	 *
	 * @deprecated As of Judson (7.1.x)
	 */
	public boolean getMale() {
		return true;
	}

	/**
	 * Always returns true, since this information is no longer stored in the database
	 *
	 * @deprecated As of Judson (7.1.x)
	 */
	public boolean isMale() {
		return true;
	}

	/**
	 * Does nothing, since this information is no longer stored in the database
	 *
	 * @deprecated As of Judson (7.1.x)
	 */
	public void setMale(boolean male) {
	}

	private static class ClassNameIds {

		private ClassNameIds() {
		}

		private static final long _USER_CLASS_NAME_ID =
			PortalUtil.getClassNameId(User.class);

	}

}