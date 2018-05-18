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

package com.liferay.commerce.product.type.grouped.web.internal.portlet.action;

import com.liferay.commerce.product.constants.CPPortletKeys;
import com.liferay.commerce.product.type.grouped.model.CPDefinitionGroupedEntry;
import com.liferay.commerce.product.type.grouped.service.CPDefinitionGroupedEntryService;
import com.liferay.commerce.product.type.grouped.web.internal.constants.GroupedCPTypeWebKeys;
import com.liferay.portal.kernel.exception.PortalException;
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
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=cpDefinitionGroupedEntryInfoPanel"
	},
	service = MVCResourceCommand.class
)
public class CPDefinitionGroupedEntryInfoPanelMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		List<CPDefinitionGroupedEntry> cpDefinitionGroupedEntries =
			getCPDefinitionGroupedEntries(resourceRequest);

		resourceRequest.setAttribute(
			GroupedCPTypeWebKeys.CP_DEFINITION_GROUPED_ENTRIES,
			cpDefinitionGroupedEntries);

		include(
			resourceRequest, resourceResponse,
			"/definition_grouped_entry_info_panel.jsp");
	}

	protected List<CPDefinitionGroupedEntry> getCPDefinitionGroupedEntries(
			ResourceRequest resourceRequest)
		throws PortalException {

		List<CPDefinitionGroupedEntry> cpDefinitionGroupedEntries =
			new ArrayList<>();

		long[] cpDefinitionGroupedEntryIds = ParamUtil.getLongValues(
			resourceRequest, "rowIds");

		for (long cpDefinitionGroupedEntryId : cpDefinitionGroupedEntryIds) {
			CPDefinitionGroupedEntry cpDefinitionGroupedEntry =
				_cpDefinitionGroupedEntryService.getCPDefinitionGroupedEntry(
					cpDefinitionGroupedEntryId);

			cpDefinitionGroupedEntries.add(cpDefinitionGroupedEntry);
		}

		return cpDefinitionGroupedEntries;
	}

	@Reference
	private CPDefinitionGroupedEntryService _cpDefinitionGroupedEntryService;

}