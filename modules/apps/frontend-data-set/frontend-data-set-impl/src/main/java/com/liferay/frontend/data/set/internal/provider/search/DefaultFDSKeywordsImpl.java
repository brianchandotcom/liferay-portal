/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.internal.provider.search;

import com.liferay.frontend.data.set.provider.search.FDSKeywords;

/**
 * @author Marco Leo
 */
public class DefaultFDSKeywordsImpl implements FDSKeywords {

	@Override
	public String getKeywords() {
		return _keywords;
	}

	public void setKeywords(String keywords) {
		_keywords = keywords;
	}

	private String _keywords;

}