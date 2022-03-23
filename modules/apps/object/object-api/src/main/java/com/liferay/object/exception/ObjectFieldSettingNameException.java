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

package com.liferay.object.exception;

import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Set;

/**
 * @author Marco Leo
 */
public class ObjectFieldSettingNameException extends PortalException {

	public ObjectFieldSettingNameException() {
	}

	public ObjectFieldSettingNameException(String msg) {
		super(msg);
	}

	public ObjectFieldSettingNameException(String msg, Throwable throwable) {
		super(msg, throwable);
	}

	public ObjectFieldSettingNameException(Throwable throwable) {
		super(throwable);
	}

	public static class NotAllowedNames
		extends ObjectFieldSettingNameException {

		public NotAllowedNames(
			String objectFieldName, Set<String> objectFieldSettingsNames) {

			super(
				String.format(
					"The settings %s are not allowed for object field %s",
					StringUtil.merge(
						objectFieldSettingsNames, StringPool.COMMA_AND_SPACE),
					objectFieldName));
		}

	}

}