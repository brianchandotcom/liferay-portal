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

package com.liferay.search.experiences.web.internal.blueprint.display.context;

import com.liferay.petra.portlet.url.builder.PortletURLBuilder;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.search.experiences.service.SXPElementService;
import com.liferay.search.experiences.web.internal.blueprint.util.SXPBlueprintRequestUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Kevin Tan
 * @author Petteri Karttunen
 */
public abstract class EditEntryDisplayBuilder {

	public EditEntryDisplayBuilder(
		RenderRequest renderRequest, RenderResponse renderResponse,
		SXPElementService sxpElementService, JSONFactory jsonFactory,
		Language language) {

		this.renderRequest = renderRequest;
		this.renderResponse = renderResponse;
		this.sxpElementService = sxpElementService;
		this.jsonFactory = jsonFactory;
		this.language = language;

		httpServletRequest = SXPBlueprintRequestUtil.getHttpServletRequest(
			renderRequest);

		themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
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

	protected Map<String, Object> getContext() {
		return HashMapBuilder.<String, Object>put(
			"availableLanguages", getAvailableLanguagesJSONObject()
		).put(
			"contextPath", renderRequest.getContextPath()
		).put(
			"defaultLocale", LocaleUtil.toLanguageId(LocaleUtil.getDefault())
		).put(
			"locale", themeDisplay.getLanguageId()
		).put(
			"namespace", renderResponse.getNamespace()
		).build();
	}

	protected JSONObject getDescriptionJSONObject(
		Map<Locale, String> descriptionMap) {

		JSONObject descriptionJSONObject = jsonFactory.createJSONObject();

		descriptionMap.forEach(
			(key, value) -> descriptionJSONObject.put(
				StringUtil.replace(key.toString(), '_', "-"), value));

		return descriptionJSONObject;
	}

	protected String getRedirect() {
		String redirect = ParamUtil.getString(renderRequest, "redirect");

		if (Validator.isNull(redirect)) {
			redirect = String.valueOf(renderResponse.createRenderURL());
		}

		return redirect;
	}

	protected String getSubmitFormURL(String actionName, boolean edit) {
		return PortletURLBuilder.createActionURL(
			renderResponse
		).setActionName(
			actionName
		).setCMD(
			edit ? Constants.EDIT : Constants.ADD
		).setRedirect(
			getRedirect()
		).buildString();
	}

	protected JSONObject getTitleJSONObject(Map<Locale, String> titleMap) {
		JSONObject titleJSONObject = jsonFactory.createJSONObject();

		titleMap.forEach(
			(key, value) -> titleJSONObject.put(
				StringUtil.replace(key.toString(), '_', "-"), value));

		return titleJSONObject;
	}

	protected void setData(
		EntryDisplayContext entryDisplayContext, Map<String, Object> props) {

		entryDisplayContext.setData(
			HashMapBuilder.<String, Object>put(
				"context", getContext()
			).put(
				"props", props
			).build());
	}

	protected void setRedirect(EntryDisplayContext entryDisplayContext) {
		entryDisplayContext.setRedirect(getRedirect());
	}

	protected final HttpServletRequest httpServletRequest;
	protected final JSONFactory jsonFactory;
	protected final Language language;
	protected final RenderRequest renderRequest;
	protected final RenderResponse renderResponse;
	protected final SXPElementService sxpElementService;
	protected final ThemeDisplay themeDisplay;

}