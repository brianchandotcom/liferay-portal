/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.json;

import com.liferay.portal.kernel.json.JSON;

/**
 * @author Igor Spasic
 */
@JSON(strict = true)
public class Two {

	public String getFone() {
		return _fone;
	}

	public int getFtwo() {
		return _ftwo;
	}

	public long getNot() {
		return _not;
	}

	private final String _fone = "string";

	@JSON
	private final int _ftwo = 173;

	@JSON(include = false)
	private final long _not = -1;

}