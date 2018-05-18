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

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.util.SetUtil;

import java.util.Set;

import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionItemSelectorChecker extends EmptyOnClickRowChecker {

	public CPDefinitionItemSelectorChecker(
		RenderResponse renderResponse, long[] checkedCPDefinitionIds,
		long[] disabledCPDefinitionIds) {

		super(renderResponse);

		_checkedCPDefinitionIds = SetUtil.fromArray(checkedCPDefinitionIds);
		_disabledCPDefinitionIds = SetUtil.fromArray(disabledCPDefinitionIds);
	}

	@Override
	public boolean isChecked(Object obj) {
		CPDefinition cpDefinition = (CPDefinition)obj;

		return _checkedCPDefinitionIds.contains(
			cpDefinition.getCPDefinitionId());
	}

	@Override
	public boolean isDisabled(Object obj) {
		CPDefinition cpDefinition = (CPDefinition)obj;

		return _disabledCPDefinitionIds.contains(
			cpDefinition.getCPDefinitionId());
	}

	private final Set<Long> _checkedCPDefinitionIds;
	private final Set<Long> _disabledCPDefinitionIds;

}