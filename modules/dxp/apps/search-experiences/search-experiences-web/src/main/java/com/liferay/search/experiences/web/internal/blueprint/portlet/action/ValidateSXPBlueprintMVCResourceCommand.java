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

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.portlet.JSONPortletResponseUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCResourceCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCResourceCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.search.experiences.constants.SXPPortletKeys;
import com.liferay.search.experiences.exception.SXPBlueprintConfigurationsJSONException;
import com.liferay.search.experiences.model.SXPBlueprint;
import com.liferay.search.experiences.service.SXPBlueprintLocalService;
import com.liferay.search.experiences.validator.SXPBlueprintValidator;
import com.liferay.search.experiences.web.internal.blueprint.constants.SXPBlueprintMVCCommandNames;
import com.liferay.search.experiences.web.internal.blueprint.portlet.action.util.ProblemToJSONTranslator;
import com.liferay.search.experiences.web.internal.blueprint.util.SXPBlueprintRequestUtil;

import java.util.ResourceBundle;

import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + SXPPortletKeys.SXP_BLUEPRINT,
		"mvc.command.name=" + SXPBlueprintMVCCommandNames.VALIDATE_SXP_BLUEPRINT
	},
	service = MVCResourceCommand.class
)
public class ValidateSXPBlueprintMVCResourceCommand
	extends BaseMVCResourceCommand {

	@Override
	protected void doServeResource(
			ResourceRequest resourceRequest, ResourceResponse resourceResponse)
		throws Exception {

		JSONPortletResponseUtil.writeJSON(
			resourceRequest, resourceResponse,
			getResponseJSONString(resourceRequest));
	}

	protected String getResponseJSONString(ResourceRequest resourceRequest) {
		try {
			SXPBlueprint sxpBlueprint = _getSXPBlueprint(resourceRequest);

			_sxpBlueprintValidator.validate(
				sxpBlueprint.getConfigurationsJSON());

			return String.valueOf(_jsonFactory.createJSONObject());
		}
		catch (SXPBlueprintConfigurationsJSONException
					sxpBlueprintConfigurationsJSONException) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					sxpBlueprintConfigurationsJSONException.getMessage(),
					sxpBlueprintConfigurationsJSONException);
			}

			return _problemToJSONTranslator.translate(
				sxpBlueprintConfigurationsJSONException.getProblems(),
				_getResourceBundle(resourceRequest));
		}
	}

	private ResourceBundle _getResourceBundle(ResourceRequest resourceRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());
	}

	private SXPBlueprint _getSXPBlueprint(ResourceRequest resourceRequest) {
		SXPBlueprint sxpBlueprint =
			_sxpBlueprintLocalService.createSXPBlueprint(0L);

		sxpBlueprint.setConfigurationsJSON(
			SXPBlueprintRequestUtil.getConfigurationsJSON(resourceRequest));

		return sxpBlueprint;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ValidateSXPBlueprintMVCResourceCommand.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ProblemToJSONTranslator _problemToJSONTranslator;

	@Reference
	private SXPBlueprintLocalService _sxpBlueprintLocalService;

	@Reference
	private SXPBlueprintValidator _sxpBlueprintValidator;

}