/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.media.query;

import java.util.List;

/**
 * @author Alejandro Tardín
 */
public class MediaQuery {

	public MediaQuery(List<Condition> conditions, String src) {
		_conditions = conditions;
		_src = src;
	}

	public List<Condition> getConditions() {
		return _conditions;
	}

	public String getSrc() {
		return _src;
	}

	private final List<Condition> _conditions;
	private final String _src;

}