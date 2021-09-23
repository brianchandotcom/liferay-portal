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
import com.liferay.search.experiences.exception.SXPElementElementDefinitionJSONException;
import com.liferay.search.experiences.model.SXPElement;
import com.liferay.search.experiences.service.SXPElementLocalService;
import com.liferay.search.experiences.validator.SXPElementValidator;
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
		"mvc.command.name=" + SXPBlueprintMVCCommandNames.VALIDATE_SXP_ELEMENT
	},
	service = MVCResourceCommand.class
)
public class ValidateSXPElementMVCResourceCommand
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
			SXPElement sxpElement = _getSXPElement(resourceRequest);

			_sxpElementValidator.validate(
				sxpElement.getElementDefinitionJSON(),
				SXPBlueprintRequestUtil.getSXPElementType(resourceRequest));

			return String.valueOf(_jsonFactory.createJSONObject());
		}
		catch (SXPElementElementDefinitionJSONException
					sxpElementDefinitionJSONException) {

			if (_log.isDebugEnabled()) {
				_log.debug(
					sxpElementDefinitionJSONException.getMessage(),
					sxpElementDefinitionJSONException);
			}

			return _problemToJSONTranslator.translate(
				sxpElementDefinitionJSONException.getProblems(),
				_getResourceBundle(resourceRequest));
		}
	}

	private ResourceBundle _getResourceBundle(ResourceRequest resourceRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)resourceRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return ResourceBundleUtil.getBundle(
			"content.Language", themeDisplay.getLocale(), getClass());
	}

	private SXPElement _getSXPElement(ResourceRequest resourceRequest) {
		SXPElement sxpElement = _sxpElementLocalService.createSXPElement(0L);

		sxpElement.setElementDefinitionJSON(
			SXPBlueprintRequestUtil.getElementDefinitionJSON(resourceRequest));

		return sxpElement;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ValidateSXPElementMVCResourceCommand.class);

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private ProblemToJSONTranslator _problemToJSONTranslator;

	@Reference
	private SXPElementLocalService _sxpElementLocalService;

	@Reference
	private SXPElementValidator _sxpElementValidator;

}