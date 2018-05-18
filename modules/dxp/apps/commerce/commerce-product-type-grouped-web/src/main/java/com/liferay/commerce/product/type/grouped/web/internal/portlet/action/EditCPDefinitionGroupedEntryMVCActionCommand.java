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
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Andrea Di Giorgi
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CPPortletKeys.CP_DEFINITIONS,
		"mvc.command.name=editCPDefinitionGroupedEntry"
	},
	service = MVCActionCommand.class
)
public class EditCPDefinitionGroupedEntryMVCActionCommand
	extends BaseMVCActionCommand {

	protected void addCPDefinitionGroupedEntries(ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionId = ParamUtil.getLong(
			actionRequest, "cpDefinitionId");
		long[] entryCPDefinitionIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "entryCPDefinitionIds"), 0L);

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			CPDefinitionGroupedEntry.class.getName(), actionRequest);

		_cpDefinitionGroupedEntryService.addCPDefinitionGroupedEntries(
			cpDefinitionId, entryCPDefinitionIds, serviceContext);
	}

	protected void deleteCPDefinitionGroupedEntries(ActionRequest actionRequest)
		throws Exception {

		long[] deleteCPDefinitionGroupedEntryIds = null;

		long cpDefinitionGroupedEntryId = ParamUtil.getLong(
			actionRequest, "cpDefinitionGroupedEntryId");

		if (cpDefinitionGroupedEntryId > 0) {
			deleteCPDefinitionGroupedEntryIds =
				new long[] {cpDefinitionGroupedEntryId};
		}
		else {
			deleteCPDefinitionGroupedEntryIds = StringUtil.split(
				ParamUtil.getString(
					actionRequest, "deleteCPDefinitionGroupedEntryIds"),
				0L);
		}

		for (long deleteCPDefinitionGroupedEntryId :
				deleteCPDefinitionGroupedEntryIds) {

			_cpDefinitionGroupedEntryService.deleteCPDefinitionGroupedEntry(
				deleteCPDefinitionGroupedEntryId);
		}
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.ADD)) {
			addCPDefinitionGroupedEntries(actionRequest);
		}
		else if (cmd.equals(Constants.DELETE)) {
			deleteCPDefinitionGroupedEntries(actionRequest);
		}
		else if (cmd.equals(Constants.UPDATE)) {
			updateCPDefinitionGroupedEntry(actionRequest);
		}
	}

	protected CPDefinitionGroupedEntry updateCPDefinitionGroupedEntry(
			ActionRequest actionRequest)
		throws Exception {

		long cpDefinitionGroupedEntryId = ParamUtil.getLong(
			actionRequest, "cpDefinitionGroupedEntryId");

		double priority = ParamUtil.getDouble(actionRequest, "priority");
		int quantity = ParamUtil.getInteger(actionRequest, "quantity");

		return _cpDefinitionGroupedEntryService.updateCPDefinitionGroupedEntry(
			cpDefinitionGroupedEntryId, priority, quantity);
	}

	@Reference
	private CPDefinitionGroupedEntryService _cpDefinitionGroupedEntryService;

}