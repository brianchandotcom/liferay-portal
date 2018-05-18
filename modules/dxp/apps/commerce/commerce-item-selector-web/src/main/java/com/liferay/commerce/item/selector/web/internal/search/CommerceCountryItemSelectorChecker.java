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

package com.liferay.commerce.item.selector.web.internal.search;

import com.liferay.commerce.model.CommerceCountry;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Set;

import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceCountryItemSelectorChecker extends EmptyOnClickRowChecker {

	public CommerceCountryItemSelectorChecker(
		RenderResponse renderResponse, long[] checkedCommerceCountryIds) {

		super(renderResponse);

		_checkedCommerceCountryIds = SetUtil.fromArray(
			checkedCommerceCountryIds);
	}

	@Override
	public boolean isChecked(Object obj) {
		CommerceCountry commerceCountry = (CommerceCountry)obj;

		return _checkedCommerceCountryIds.contains(
			commerceCountry.getCommerceCountryId());
	}

	@Override
	public boolean isDisabled(Object obj) {
		return isChecked(obj);
	}

	private final Set<Long> _checkedCommerceCountryIds;

}