/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Michael C. Han
 */
public class DefaultFullNameGenerator implements FullNameGenerator {

	public String getFullName(
		String firstName, String middleName, String lastName) {

		StringBundler sb = new StringBundler();

		sb.append(firstName);

		if (Validator.isNotNull(middleName)) {
			sb.append(StringPool.SPACE);
			sb.append(middleName);
		}

		if (Validator.isNotNull(lastName)) {
			sb.append(StringPool.SPACE);
			sb.append(lastName);
		}

		return sb.toString();
	}

	public String[] splitFullName(String fullName) {
		String firstName = StringPool.BLANK;
		String middleName = StringPool.BLANK;
		String lastName = StringPool.BLANK;

		if (Validator.isNotNull(fullName)) {
			String[] name = StringUtil.split(fullName, CharPool.SPACE);

			firstName = name[0];
			middleName = StringPool.BLANK;
			lastName = name[name.length - 1];

			if (name.length > 2) {
				for (int i = 1; i < name.length - 1; i++) {
					if (Validator.isNull(name[i].trim())) {
						continue;
					}

					if (i != 1) {
						middleName += StringPool.SPACE;
					}

					middleName += name[i].trim();
				}
			}
		}
		else {
			firstName = GetterUtil.getString(firstName, lastName);
			lastName = firstName;
		}

		return new String[] {firstName, middleName, lastName};
	}

}