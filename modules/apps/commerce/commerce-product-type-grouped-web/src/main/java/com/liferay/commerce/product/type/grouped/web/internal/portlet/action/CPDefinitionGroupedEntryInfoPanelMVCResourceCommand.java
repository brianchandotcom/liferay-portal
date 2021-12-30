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

package com.liferay.commerce.product.type.grouped.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryService;
import com.liferay.commerce.product.type.grouped.web.internal.constants.GroupedCPTypeWebKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	enabled = false, immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=/cp_definitions/cp_definition_grouped_entry_info_panel"
	},
	service = MVCResourceCommand.class
)
public class CPDefinitionGroupedEntryInfoPanelMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		resourceRequest.setAttribute(
			GroupedCPTypeWebKeys.CP_DEFINITION_GROUPED_ENTRIES,
			_getCPDefinitionGroupedEntries(resourceRequest));

		include(
			resourceRequest, resourceResponse,
			"/cp_definition_grouped_entry_info_panel.jsp");
	}

	private List<CPDefinitionGroupedEntry> _getCPDefinitionGroupedEntries(
			ResourceRequest resourceRequest)
		throws Exception {

		List<CPDefinitionGroupedEntry> cpDefinitionGroupedEntries =
			new ArrayList<>();

		long[] cpDefinitionGroupedEntryIds = ParamUtil.getLongValues(
			resourceRequest, "rowIds");

		for (long cpDefinitionGroupedEntryId : cpDefinitionGroupedEntryIds) {
			cpDefinitionGroupedEntries.add(
				_cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntry(
					cpDefinitionGroupedEntryId));
		}

		return cpDefinitionGroupedEntries;
	}

	@Reference
	private CPDefinitionGroupedEntryService _cpDefinitionGroupedEntryService;

}