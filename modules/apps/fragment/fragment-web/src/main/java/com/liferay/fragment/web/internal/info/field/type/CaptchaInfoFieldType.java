/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.fragment.web.internal.info.field.type;

import com.liferay.info.field.type.InfoFieldType;

/**
 * @author Eudaldo Alonso
 */
public class CaptchaInfoFieldType implements InfoFieldType {

	public static final CaptchaInfoFieldType INSTANCE =
		new CaptchaInfoFieldType();

	public String getName() {
		return "captcha";
	}

	private CaptchaInfoFieldType() {
	}

}