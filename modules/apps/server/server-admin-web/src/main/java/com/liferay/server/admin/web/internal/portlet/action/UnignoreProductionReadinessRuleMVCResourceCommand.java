/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.server.admin.web.internal.portlet.action;

import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.server.admin.web.internal.production.readiness.ProductionReadinessIgnoredRuleUtil;

import jakarta.portlet.ResourceRequest;
import jakarta.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Lily Chi
 */
@Component(
	property = {
		"jakarta.portlet.name=" + PortletKeys.SERVER_ADMIN,
		"mvc.command.name=/server_admin/unignore_production_readiness_rule"
	},
	service = MVCResourceCommand.class
)
public class UnignoreProductionReadinessRuleMVCResourceCommand
	extends BaseProductionReadinessMVCResourceCommand {

	@Override
	protected void serveProductionReadinessResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		ProductionReadinessIgnoredRuleUtil.removeIgnoredRule(
			ParamUtil.getString(resourceRequest, "ruleKey"));
	}

}