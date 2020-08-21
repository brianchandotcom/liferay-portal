/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.organization.web.internal.frontend;

import com.liferay.frontend.taglib.clay.data.Filter;

/**
 * @author Alessio Antonio Rendina
 */
public class OrganizationFilterImpl implements Filter {

	@Override
	public String getKeywords() {
		return _keywords;
	}

	public long getOrganizationId() {
		return _accountId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setKeywords(String keywords) {
		_keywords = keywords;
	}

	public void setOrganizationId(long accountId) {
		_accountId = accountId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	private long _accountId;
	private String _keywords;
	private long _userId;

}