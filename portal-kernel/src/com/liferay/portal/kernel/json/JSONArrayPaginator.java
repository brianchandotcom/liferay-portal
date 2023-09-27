/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.json;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class JSONArrayPaginator {

	public JSONArrayPaginator() throws Exception {
		this(_DELTA);
	}

	public JSONArrayPaginator(int delta) throws Exception {
		if (delta <= 0) {
			delta = _DELTA;
		}

		int start = 0;
		int end = delta;

		while (true) {
			JSONArray jsonArray = paginate(start, end);

			if (jsonArray.length() == 0) {
				break;
			}

			start = end;

			end += delta;
		}
	}

	protected abstract JSONArray paginate(int start, int end) throws Exception;

	private static final int _DELTA = 500;

}