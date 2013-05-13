/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.pwd;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.PasswordPolicy;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BasicToolkit implements Toolkit {

	@Override
	public abstract String generate(PasswordPolicy passwordPolicy);

	@Override
	public abstract void validate(
			long userId, String password1, String password2,
			PasswordPolicy passwordPolicy)
		throws PortalException, SystemException;

	@Override
	public void validate(
			String password1, String password2, PasswordPolicy passwordPolicy)
		throws PortalException, SystemException {

		validate(0, password1, password2, passwordPolicy);
	}

}