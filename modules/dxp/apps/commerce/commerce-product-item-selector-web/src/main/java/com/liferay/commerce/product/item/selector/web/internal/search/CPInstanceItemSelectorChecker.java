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

package com.liferay.commerce.product.item.selector.web.internal.search;

import com.liferay.commerce.product.model.CPInstance;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Set;

import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CPInstanceItemSelectorChecker extends EmptyOnClickRowChecker {

	public CPInstanceItemSelectorChecker(
		RenderResponse renderResponse, long[] checkedCPInstanceIds) {

		super(renderResponse);

		_checkedCPInstanceIds = SetUtil.fromArray(checkedCPInstanceIds);
	}

	@Override
	public boolean isChecked(Object obj) {
		CPInstance cpInstance = (CPInstance)obj;

		return _checkedCPInstanceIds.contains(cpInstance.getCPInstanceId());
	}

	@Override
	public boolean isDisabled(Object obj) {
		return isChecked(obj);
	}

	private final Set<Long> _checkedCPInstanceIds;

}