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

package com.liferay.search.experiences.web.internal.power.tools.portlet.action;

import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.search.experiences.constants.SXPPortletKeys;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 * @author André de Oliveira
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SXPPortletKeys.SXP_POWER_TOOLS,
		"mvc.command.name=/sxp_power_tools/import_wikipedia"
	},
	service = MVCActionCommand.class
)
public class ImportWikipediaMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
		ActionRequest actionRequest, ActionResponse actionResponse) {
	}

	@Reference
	private JournalArticleLocalService _journalArticleLocalService;

}