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

package com.liferay.portal.search.tuning.blueprints.admin.web.internal.display.context;

import com.liferay.exportimport.kernel.exception.NoSuchConfigurationException;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.tuning.blueprints.admin.web.internal.constants.BlueprintsAdminWebKeys;
import com.liferay.portal.search.tuning.blueprints.constants.BlueprintTypes;
import com.liferay.portal.search.tuning.blueprints.model.Blueprint;
import com.liferay.portal.search.tuning.blueprints.service.BlueprintService;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionURL;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Kevin Tan
 */
public abstract class EditEntryDisplayBuilder {

	public EditEntryDisplayBuilder(
		BlueprintService blueprintService,
		HttpServletRequest httpServletRequest, Language language,
		JSONFactory jsonFactory, RenderRequest renderRequest,
		RenderResponse renderResponse) {

		this.blueprintService = blueprintService;
		this.httpServletRequest = httpServletRequest;
		this.language = language;
		this.jsonFactory = jsonFactory;
		this.renderRequest = renderRequest;
		this.renderResponse = renderResponse;

		blueprintId = ParamUtil.getLong(
			renderRequest, BlueprintsAdminWebKeys.BLUEPRINT_ID);
		blueprintType = ParamUtil.getInteger(
			renderRequest, BlueprintsAdminWebKeys.BLUEPRINT_TYPE,
			BlueprintTypes.BLUEPRINT);

		blueprint = getBlueprint();

		portletRequest = (PortletRequest)httpServletRequest.getAttribute(
			JavaConstants.JAVAX_PORTLET_REQUEST);
		themeDisplay = (ThemeDisplay)httpServletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);
	}

	protected JSONObject getAvailableLanguagesJSONObject() {
		JSONObject jsonObject = jsonFactory.createJSONObject();

		for (Locale locale : language.getAvailableLocales()) {
			jsonObject.put(
				LocaleUtil.toLanguageId(locale),
				locale.getDisplayName(themeDisplay.getLocale()));
		}

		return jsonObject;
	}

	protected Blueprint getBlueprint() {
		Blueprint blueprint = null;

		if (blueprintId > 0) {
			try {
				blueprint = blueprintService.getBlueprint(blueprintId);

				blueprintType = blueprint.getType();
			}
			catch (NoSuchConfigurationException noSuchConfigurationException) {
				_log.error(
					"Blueprint " + blueprintId + " not found.",
					noSuchConfigurationException);

				SessionErrors.add(
					renderRequest, BlueprintsAdminWebKeys.ERROR,
					noSuchConfigurationException.getMessage());
			}
			catch (PortalException portalException) {
				_log.error(portalException.getMessage(), portalException);

				SessionErrors.add(
					renderRequest, BlueprintsAdminWebKeys.ERROR,
					portalException.getMessage());
			}
		}

		return blueprint;
	}

	protected Map<String, Object> getContext() {
		return HashMapBuilder.<String, Object>put(
			"availableLanguages", getAvailableLanguagesJSONObject()
		).put(
			"contextPath", portletRequest.getContextPath()
		).put(
			"defaultLocale", LocaleUtil.toLanguageId(LocaleUtil.getDefault())
		).put(
			"locale", themeDisplay.getLanguageId()
		).put(
			"namespace", renderResponse.getNamespace()
		).build();
	}

	protected JSONObject getDescriptionJSONObject() {
		Map<Locale, String> descriptionMap = blueprint.getDescriptionMap();

		JSONObject descriptionJSONObject = jsonFactory.createJSONObject();

		descriptionMap.forEach(
			(key, value) -> descriptionJSONObject.put(
				StringUtil.replace(key.toString(), '_', "-"), value));

		return descriptionJSONObject;
	}

	protected String getRedirect() {
		String redirect = ParamUtil.getString(httpServletRequest, "redirect");

		if (Validator.isNull(redirect)) {
			redirect = String.valueOf(renderResponse.createRenderURL());
		}

		return redirect;
	}

	protected String getSubmitFormURL(String actionName) {
		ActionURL actionURL = renderResponse.createActionURL();

		actionURL.setParameter(ActionRequest.ACTION_NAME, actionName);
		actionURL.setParameter(
			Constants.CMD,
			(blueprint != null) ? Constants.EDIT : Constants.ADD);
		actionURL.setParameter("redirect", getRedirect());

		return actionURL.toString();
	}

	protected JSONObject getTitleJSONObject() {
		Map<Locale, String> titleMap = blueprint.getTitleMap();

		JSONObject titleJSONObject = jsonFactory.createJSONObject();

		titleMap.forEach(
			(key, value) -> titleJSONObject.put(
				StringUtil.replace(key.toString(), '_', "-"), value));

		return titleJSONObject;
	}

	protected void setConfigurationId(
		BlueprintDisplayContext editBlueprintDisplayContext) {

		editBlueprintDisplayContext.setBlueprintId(blueprintId);
	}

	protected void setConfigurationType(
		BlueprintDisplayContext editBlueprintDisplayContext) {

		editBlueprintDisplayContext.setBlueprintType(blueprintType);
	}

	protected void setData(
		BlueprintDisplayContext editBlueprintDisplayContext,
		Map<String, Object> props) {

		editBlueprintDisplayContext.setData(
			HashMapBuilder.<String, Object>put(
				"context", getContext()
			).put(
				"props", props
			).build());
	}

	protected void setPageTitle(
		BlueprintDisplayContext editBlueprintDisplayContext) {

		StringBundler sb = new StringBundler(2);

		sb.append((blueprint != null) ? "edit-" : "add-");

		if (blueprintType == BlueprintTypes.BLUEPRINT) {
			sb.append("blueprint");
		}
		else {
			sb.append("element");
		}

		editBlueprintDisplayContext.setPageTitle(
			language.get(httpServletRequest, sb.toString()));
	}

	protected void setRedirect(
		BlueprintDisplayContext editBlueprintDisplayContext) {

		editBlueprintDisplayContext.setRedirect(getRedirect());
	}

	protected final Blueprint blueprint;
	protected final long blueprintId;
	protected final BlueprintService blueprintService;
	protected int blueprintType;
	protected final HttpServletRequest httpServletRequest;
	protected final JSONFactory jsonFactory;
	protected final Language language;
	protected final PortletRequest portletRequest;
	protected final RenderRequest renderRequest;
	protected final RenderResponse renderResponse;
	protected final ThemeDisplay themeDisplay;

	private static final Log _log = LogFactoryUtil.getLog(
		EditEntryDisplayBuilder.class);

}