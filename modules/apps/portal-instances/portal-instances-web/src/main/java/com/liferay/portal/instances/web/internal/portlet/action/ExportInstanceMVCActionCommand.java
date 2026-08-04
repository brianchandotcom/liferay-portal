/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.instances.web.internal.portlet.action;

import com.liferay.portal.db.partition.util.DBPartitionUtil;
import com.liferay.portal.instances.configuration.PortalInstanceConfigurationExporter;
import com.liferay.portal.instances.web.internal.constants.PortalInstancesPortletKeys;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.CompanyService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.WebKeys;

import jakarta.portlet.ActionRequest;
import jakarta.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Avalos
 */
@Component(
	property = {
		"jakarta.portlet.name=" + PortalInstancesPortletKeys.PORTAL_INSTANCES,
		"mvc.command.name=/portal_instances/export_instance"
	},
	service = MVCActionCommand.class
)
public class ExportInstanceMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!FeatureFlagManagerUtil.isEnabled(
				themeDisplay.getCompanyId(), "LPD-11342")) {

			throw new UnsupportedOperationException();
		}

		long companyId = ParamUtil.getLong(actionRequest, "companyId");

		JSONObject jsonObject = _jsonFactory.createJSONObject();

		try {
			_companyService.exportCompany(companyId);

			_portalInstanceConfigurationExporter.exportConfigurations(
				companyId);

			jsonObject.put(
				"successMessage",
				_language.format(
					actionRequest.getLocale(),
					"the-instance-was-exported-to-the-schema-x",
					DBPartitionUtil.
						DATABASE_EXPORTED_PARTITION_SCHEMA_NAME_PREFIX +
							companyId));
		}
		catch (Exception exception) {
			_log.error(
				"Unable to export portal instance " + companyId, exception);

			jsonObject.put(
				"error",
				_language.format(
					actionRequest.getLocale(), "export-failed-with-message-x",
					GetterUtil.getString(exception.getMessage())));
		}

		JSONPortletResponseUtil.writeJSON(
			actionRequest, actionResponse, jsonObject);

		hideDefaultSuccessMessage(actionRequest);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ExportInstanceMVCActionCommand.class);

	@Reference
	private CompanyService _companyService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private PortalInstanceConfigurationExporter
		_portalInstanceConfigurationExporter;

}