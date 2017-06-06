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

package com.liferay.commerce.product.type.grouped.web.internal;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.type.CPType;
import com.liferay.commerce.product.type.grouped.constants.GroupedCPTypeConstants;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryLocalService;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marco Leo
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"commerce.product.type.display.order:Integer=10",
		"commerce.product.type.name=" + GroupedCPTypeConstants.NAME
	},
	service = CPType.class
)
public class GroupedCPType implements CPType {

	@Override
	public void deleteCPDefinition(long cpDefinitionId) throws PortalException {
		_cpDefinitionGroupedEntryLocalService.deleteCPDefinitionGroupedEntries(
			cpDefinitionId);
	}

	@Override
	public void exportCPDefinition(
			CPDefinition cpDefinition, PortletDataContext portletDataContext)
		throws Exception {
	}

	@Override
	public String getCPDefinitionEditUrl(
			long cpDefinitionId, HttpServletRequest httpServletRequest)
		throws PortalException {

		return null;
	}

	@Override
	public String getLabel(Locale locale) {
		return LanguageUtil.get(locale, GroupedCPTypeConstants.NAME);
	}

	@Override
	public String getName() {
		return GroupedCPTypeConstants.NAME;
	}

	@Reference
	private CPDefinitionGroupedEntryLocalService
		_cpDefinitionGroupedEntryLocalService;

}