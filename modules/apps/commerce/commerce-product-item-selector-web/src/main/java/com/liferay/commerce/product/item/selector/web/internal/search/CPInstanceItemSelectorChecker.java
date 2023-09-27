/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean isChecked(Object object) {
		CPInstance cpInstance = (CPInstance)object;

		return _checkedCPInstanceIds.contains(cpInstance.getCPInstanceId());
	}

	@Override
	public boolean isDisabled(Object object) {
		return isChecked(object);
	}

	private final Set<Long> _checkedCPInstanceIds;

}