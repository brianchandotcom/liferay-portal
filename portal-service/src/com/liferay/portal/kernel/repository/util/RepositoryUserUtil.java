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

package com.liferay.portal.kernel.repository.util;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.BaseServiceImpl;

/**
 * @author Adolfo Pérez
 *
 * @deprecated As of 7.0.0, with no direct replacement
 */
@Deprecated
public class RepositoryUserUtil {

	/**
	 * See {@link com.liferay.portal.service.BaseServiceImpl#getUserId()}
	 *
	 * @deprecated As of 7.0.0, with no direct replacement
	 */
	@Deprecated
	public static long getUserId() throws PrincipalException {
		String name = PrincipalThreadLocal.getName();

		if (name == null) {
			throw new PrincipalException();
		}

		if (Validator.isNull(name)) {
			throw new PrincipalException("Principal is null");
		}
		else {
			for (int i = 0; i < BaseServiceImpl.ANONYMOUS_NAMES.length; i++) {
				if (StringUtil.equalsIgnoreCase(
						name, BaseServiceImpl.ANONYMOUS_NAMES[i])) {

					throw new PrincipalException(
						"Principal cannot be " +
							BaseServiceImpl.ANONYMOUS_NAMES[i]);
				}
			}
		}

		return GetterUtil.getLong(name);
	}

}