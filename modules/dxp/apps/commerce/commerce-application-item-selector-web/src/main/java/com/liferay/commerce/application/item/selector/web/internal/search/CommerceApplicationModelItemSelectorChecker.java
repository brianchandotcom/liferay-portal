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

package com.liferay.commerce.application.item.selector.web.internal.search;

import com.liferay.commerce.application.model.CommerceApplicationModel;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Set;

import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceApplicationModelItemSelectorChecker
	extends EmptyOnClickRowChecker {

	public CommerceApplicationModelItemSelectorChecker(
		RenderResponse renderResponse,
		long[] checkedCommerceApplicationModelIds) {

		super(renderResponse);

		_checkedCommerceApplicationModelIds = SetUtil.fromArray(
			checkedCommerceApplicationModelIds);
	}

	@Override
	public boolean isChecked(Object object) {
		CommerceApplicationModel commerceApplicationModel =
			(CommerceApplicationModel)object;

		return _checkedCommerceApplicationModelIds.contains(
			commerceApplicationModel.getCommerceApplicationModelId());
	}

	@Override
	public boolean isDisabled(Object object) {
		return isChecked(object);
	}

	private final Set<Long> _checkedCommerceApplicationModelIds;

}