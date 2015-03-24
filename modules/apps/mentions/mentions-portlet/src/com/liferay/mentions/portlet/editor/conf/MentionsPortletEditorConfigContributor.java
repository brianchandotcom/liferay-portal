/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.mentions.portlet.editor.conf;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.editor.config.PortletEditorConfigContributor;

import java.util.Map;

import org.osgi.service.component.annotations.Component;

/**
 * @author Sergio González
 */
@Component(
	property = {
		"editor.config.key=commentsEditor",
		"service.ranking:Integer=10"
	}
)
public class MentionsPortletEditorConfigContributor
	implements PortletEditorConfigContributor {

	public void populateConfigJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {

		JSONObject trigger = JSONFactoryUtil.createJSONObject();
		trigger.put(
			"resultFilters", "function(query, results) {return results;}");
		trigger.put("resultTextLocator", "screenName");

		LiferayPortletURL autoCompleteUserURL =
			liferayPortletResponse.createResourceURL("1_WAR_mentionsportlet");

		String source =
			autoCompleteUserURL.toString() + "&" +
				PortalUtil.getPortletNamespace("1_WAR_mentionsportlet");

		trigger.put("source", source);
		trigger.put("term", "@");
		trigger.put("tplReplace", "{mention}");

		StringBundler sb = new StringBundler(8);

		sb.append("<div class=\"display-style-3 taglib-user-display\">");
		sb.append("<span>");
		sb.append("<span class=\"user-profile-image\" ");
		sb.append("style=\"background-image: url('{portraitURL}');");
		sb.append("background-size: 32px 32px; height: 32px; width: 32px;\"></span>");
		sb.append("<span class=\"user-name\">{fullName}</span>");
		sb.append("<span class=\"user-details\">@{screenName}</span></span>");
		sb.append("</div>");

		trigger.put("tplResults", sb.toString());

		JSONArray triggersArr = JSONFactoryUtil.createJSONArray();

		triggersArr.put(trigger);

		JSONObject autoCompleteConfig = JSONFactoryUtil.createJSONObject();

		String extraPlugins = jsonObject.getString("extraPlugins");

		if (Validator.isNotNull(extraPlugins)) {
			extraPlugins += ",autocomplete";
		}
		else {
			extraPlugins = "autocomplete";
		}

		autoCompleteConfig.put("requestTemplate", "query={query}");
		autoCompleteConfig.put("trigger", triggersArr);

		jsonObject.put("extraPlugins", extraPlugins);
		jsonObject.put("autocomplete", autoCompleteConfig);
	}

	public void populateOptionsJSONObject(
		JSONObject jsonObject, Map<String, Object> inputEditorTaglibAttributes,
		ThemeDisplay themeDisplay,
		LiferayPortletResponse liferayPortletResponse) {
	}

}