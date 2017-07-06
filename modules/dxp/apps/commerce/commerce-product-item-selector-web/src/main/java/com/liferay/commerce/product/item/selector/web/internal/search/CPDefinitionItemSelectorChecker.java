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
import com.liferay.portal.kernel.dao.search.EmptyOnClickRowChecker;

import java.util.List;

import javax.portlet.RenderResponse;

/**
 * @author Alessio Antonio Rendina
 */
public class CPDefinitionItemSelectorChecker extends EmptyOnClickRowChecker {

	public CPDefinitionItemSelectorChecker(
		RenderResponse renderResponse,
		List<CPDefinitionLink> cpDefinitionLinks) {

		super(renderResponse);

		_cpDefinitionLinks = cpDefinitionLinks;
	}

	@Override
	public boolean isChecked(Object obj) {
		CPDefinition cpDefinition = (CPDefinition)obj;

		for (CPDefinitionLink cpDefinitionLink : _cpDefinitionLinks) {
			if (cpDefinitionLink.getCPDefinitionId2() ==
					cpDefinition.getCPDefinitionId()) {

				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isDisabled(Object obj) {
		CPDefinition cpDefinition = (CPDefinition)obj;

		for (CPDefinitionLink cpDefinitionLink : _cpDefinitionLinks) {
			if (cpDefinitionLink.getCPDefinitionId1() ==
					cpDefinition.getCPDefinitionId()) {

				return true;
			}
		}

		return super.isDisabled(obj);
	}

	private final List<CPDefinitionLink> _cpDefinitionLinks;

}