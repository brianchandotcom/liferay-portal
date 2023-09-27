/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	private ObjectFieldSettingNameException(String msg) {
		super(msg);
	}

}