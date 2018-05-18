/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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