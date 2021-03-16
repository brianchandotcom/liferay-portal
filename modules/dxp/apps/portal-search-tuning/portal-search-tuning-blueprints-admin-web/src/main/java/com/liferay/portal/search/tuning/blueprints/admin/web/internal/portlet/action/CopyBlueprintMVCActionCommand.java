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

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.portlet.action;

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
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminMVCCommandNames;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminWebKeys;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.util.BlueprintsAdminRequestHelper;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintsPortletKeys;
import com.liferay.portal.search.tuning.blueprints.exception.BlueprintValidationException;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintService;

import java.io.IOException;

import java.util.HashMap;
import java.util.List;
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
		"javax.portlet.name=" + BlueprintsPortletKeys.BLUEPRINTS_ADMIN,
		"mvc.command.name=" + BlueprintsAdminMVCCommandNames.COPY_BLUEPRINT
	},
	service = MVCActionCommand.class
)
public class CopyBlueprintMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Optional<Blueprint> blueprintOptional =
			_blueprintsAdminRequestHelper.getBlueprintFromRequest(
				actionRequest, actionResponse);

		if (!blueprintOptional.isPresent()) {
			return;
		}

		_createCopy(actionRequest, actionResponse, blueprintOptional.get());
	}

	private void _createCopy(
		ActionRequest actionRequest, ActionResponse actionResponse,
		Blueprint sourceBlueprint) {

		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				Blueprint.class.getName(), actionRequest);

			_blueprintService.addCompanyBlueprint(
				_getTargetTitleMap(sourceBlueprint),
				sourceBlueprint.getDescriptionMap(),
				sourceBlueprint.getConfiguration(),
				sourceBlueprint.getSelectedElements(),
				sourceBlueprint.getType(), serviceContext);

			sendRedirect(actionRequest, actionResponse);
		}
		catch (BlueprintValidationException blueprintValidationException) {
			_log.error(
				blueprintValidationException.getMessage(),
				blueprintValidationException);

			List<String> errors = blueprintValidationException.getErrors();

			errors.forEach(key -> SessionErrors.add(actionRequest, key));
		}
		catch (PortalException portalException) {
			_log.error(portalException.getMessage(), portalException);

			SessionErrors.add(
				actionRequest, BlueprintsAdminWebKeys.ERROR,
				portalException.getMessage());
		}
		catch (IOException ioException) {
			_log.error(ioException.getMessage(), ioException);

			SessionErrors.add(
				actionRequest, BlueprintsAdminWebKeys.ERROR,
				ioException.getMessage());
		}
	}

	private Map<Locale, String> _getTargetTitleMap(Blueprint blueprint) {
		Map<Locale, String> sourceTitleMap = blueprint.getTitleMap();
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
		CopyBlueprintMVCActionCommand.class);

	@Reference
	private BlueprintsAdminRequestHelper _blueprintsAdminRequestHelper;

	@Reference
	private BlueprintService _blueprintService;

	@Reference
	private Language _language;

}