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

package com.liferay.search.experiences.web.internal.blueprint.portlet.action;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.search.experiences.constants.SXPPortletKeys;
import com.liferay.search.experiences.exception.SXPBlueprintConfigurationsJSONException;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintService;
import com.liferay.search.experiences.web.internal.blueprint.constants.SXPBlueprintMVCCommandNames;
import com.liferay.search.experiences.web.internal.blueprint.constants.SXPBlueprintWebKeys;
import com.liferay.search.experiences.web.internal.blueprint.util.SXPBlueprintRequestUtil;

import java.io.IOException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SXPPortletKeys.SXP_BLUEPRINT,
		"mvc.command.name=" + SXPBlueprintMVCCommandNames.COPY_SXP_BLUEPRINT
	},
	service = MVCActionCommand.class
)
public class CopySXPBlueprintMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Optional<SXPBlueprint> sxpBlueprintOptional =
			SXPBlueprintRequestUtil.getSXPBlueprint(
				actionRequest, actionResponse);

		if (!sxpBlueprintOptional.isPresent()) {
			return;
		}

		_createCopy(actionRequest, actionResponse, sxpBlueprintOptional.get());
	}

	private void _createCopy(
		ActionRequest actionRequest, ActionResponse actionResponse,
		SXPBlueprint sourceBlueprint) {

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				SXPBlueprint.class.getName(), actionRequest);

			_sxpSXPBlueprintService.addSXPBlueprint(
				sourceBlueprint.getConfigurationsJSON(),
				sourceBlueprint.getDescriptionMap(),
				sourceBlueprint.getElementInstancesJSON(),
				_getTitleMap(sourceBlueprint), serviceContext);

			sendRedirect(actionRequest, actionResponse);
		}
		catch (SXPBlueprintConfigurationsJSONException
					sxpBlueprintConfigurationsJSONException) {

			_log.error(
				sxpBlueprintConfigurationsJSONException.getMessage(),
				sxpBlueprintConfigurationsJSONException);

			SessionErrors.add(
				actionRequest, SXPBlueprintWebKeys.ERROR,
				sxpBlueprintConfigurationsJSONException.getMessage());
		}
		catch (PortalException portalException) {
			_log.error(portalException.getMessage(), portalException);

			SessionErrors.add(
				actionRequest, SXPBlueprintWebKeys.ERROR,
				portalException.getMessage());
		}
		catch (IOException ioException) {
			_log.error(ioException.getMessage(), ioException);

			SessionErrors.add(
				actionRequest, SXPBlueprintWebKeys.ERROR,
				ioException.getMessage());
		}
	}

	private Map<Locale, String> _getTitleMap(SXPBlueprint sxpBlueprint) {
		Map<Locale, String> sourceTitleMap = sxpBlueprint.getTitleMap();
		Map<Locale, String> targetTitleMap = new HashMap<>();

		for (Map.Entry<Locale, String> entry : sourceTitleMap.entrySet()) {
			StringBundler sb = new StringBundler(4);

			sb.append(entry.getValue());
			sb.append(" (");
			sb.append(_language.get(entry.getKey(), "copy"));
			sb.append(")");

			targetTitleMap.put(entry.getKey(), sb.toString());
		}

		return targetTitleMap;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CopySXPBlueprintMVCActionCommand.class);

	@Reference
	private Language _language;

	@Reference
	private SXPBlueprintService _sxpSXPBlueprintService;

}