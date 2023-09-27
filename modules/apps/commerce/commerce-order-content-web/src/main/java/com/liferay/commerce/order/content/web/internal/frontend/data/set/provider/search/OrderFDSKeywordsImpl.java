/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.order.content.web.internal.frontend.data.set.provider.search;

import com.liferay.frontend.data.set.provider.search.FDSKeywords;

/**
 * @author Alessio Antonio Rendina
 */
public class OrderFDSKeywordsImpl implements FDSKeywords {

	public long getAccountId() {
		return _accountId;
	}

	public long getCommerceOrderId() {
		return _commerceOrderId;
	}

	@Override
	public String getKeywords() {
		return _keywords;
	}

	public void setAccountId(long accountId) {
		_accountId = accountId;
	}

	public void setCommerceOrderId(long commerceOrderId) {
		_commerceOrderId = commerceOrderId;
	}

	public void setKeywords(String keywords) {
		_keywords = keywords;
	}

	private long _accountId;
	private long _commerceOrderId;
	private String _keywords;

}