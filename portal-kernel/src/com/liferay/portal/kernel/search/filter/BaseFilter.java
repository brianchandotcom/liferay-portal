/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.filter;

import com.liferay.petra.string.StringBundler;

/**
 * @author Michael C. Han
 */
public abstract class BaseFilter implements Filter {

	@Override
	public String getExecutionOption() {
		return _executionOption;
	}

	@Override
	public Boolean isCached() {
		return true;
	}

	@Override
	public void setCached(Boolean cached) {
		_cached = cached;
	}

	@Override
	public void setExecutionOption(String executionOption) {
		_executionOption = executionOption;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"(cached=", _cached, ", executionOption=", _executionOption, ")");
	}

	private Boolean _cached;
	private String _executionOption;

}