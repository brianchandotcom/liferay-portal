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

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionLink;
import com.liferay.commerce.product.service.CPDefinitionLinkLocalService;
import com.liferay.commerce.product.service.CPDefinitionLinkService;
import com.liferay.commerce.product.service.CPDefinitionService;
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import org.osgi.service.component.annotations.Reference;

import java.util.Collections;
import java.util.List;

import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionItemSelectorChecker extends EmptyOnClickRowChecker {

	public CPDefinitionItemSelectorChecker(
		RenderResponse renderResponse, long[] checkedCPDefinitionIds,
		long[] disabledCPDefinitionIds) {

		super(renderResponse);

		_checkedCPDefinitionIds = checkedCPDefinitionIds;
		_disabledCPDefinitionIds = disabledCPDefinitionIds;
	}

	@Override
	public boolean isChecked(Object obj) {
		CPDefinition cpDefinition = (CPDefinition)obj;

		return ArrayUtil.contains(
			_checkedCPDefinitionIds, cpDefinition.getCPDefinitionId());
	}

	@Override
	public boolean isDisabled(Object obj) {
		CPDefinition cpDefinition = (CPDefinition)obj;

		return ArrayUtil.contains(
			_disabledCPDefinitionIds, cpDefinition.getCPDefinitionId());
	}

	private final long[] _checkedCPDefinitionIds;
	private final long[] _disabledCPDefinitionIds;

}