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

package com.liferay.portal.kernel.backgroundtask.display;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatusRegistryUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManager;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Writer;

import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andrew Betts
 */
public abstract class BaseBackgroundTaskDisplay
	implements BackgroundTaskDisplay {

	public BaseBackgroundTaskDisplay(BackgroundTask backgroundTask) {
		this.backgroundTask = backgroundTask;

		backgroundTaskStatus =
			BackgroundTaskStatusRegistryUtil.getBackgroundTaskStatus(
				backgroundTask.getBackgroundTaskId());
	}

	@Override
	public String getDisplayName(HttpServletRequest request) {
		if (Validator.isNull(backgroundTask.getName())) {
			return LanguageUtil.get(request, "untitled");
		}

		return backgroundTask.getName();
	}

	@Override
	public abstract int getPercentage();

	@Override
	public int getStatus() {
		return backgroundTask.getStatus();
	}

	@Override
	public String getStatusLabel() {
		return getStatusLabel(Locale.getDefault());
	}

	@Override
	public String getStatusLabel(Locale locale) {
		return LanguageUtil.get(locale, backgroundTask.getStatusLabel());
	}

	@Override
	public boolean hasPercentage() {
		if (getPercentage() >= PERCENTAGE_MIN) {
			return true;
		}

		return false;
	}

	@Override
	public String renderDisplayTemplate() {
		return renderDisplayTemplate(Locale.getDefault());
	}

	@Override
	public String renderDisplayTemplate(Locale locale) {
		TemplateResource templateResource = getTemplateResource();

		if (templateResource == null) {
			return StringPool.BLANK;
		}

		TemplateManager templateManager =
			TemplateManagerUtil.getTemplateManager(
				TemplateConstants.LANG_TYPE_FTL);

		Template template = templateManager.getTemplate(templateResource, true);

		template.put("backgroundTask", backgroundTask);
		template.put("backgroundTaskDisplay", this);
		template.put("backgroundTaskStatus", backgroundTaskStatus);
		template.put(
			"statusMessageJSONObject", getStatusMessageJSONObject(locale));

		Map<String, Object> templateVars = getTemplateVars();

		if (templateVars != null) {
			template.putAll(getTemplateVars());
		}

		Writer writer = new UnsyncStringWriter();

		try {
			template.processTemplate(writer);
		}
		catch (TemplateException te) {
			if (_log.isDebugEnabled()) {
				_log.debug(te, te);
			}

			return StringPool.BLANK;
		}

		return writer.toString();
	}

	protected long getBackgroundTaskStatusAttributeLong(String attributeKey) {
		return GetterUtil.getLong(
			backgroundTaskStatus.getAttribute(attributeKey));
	}

	protected String getBackgroundTaskStatusAttributeString(
		String attributeKey) {

		return GetterUtil.getString(
			backgroundTaskStatus.getAttribute(attributeKey));
	}

	protected JSONObject getStatusMessageJSONObject(Locale locale) {
		JSONObject jsonObject = null;

		try {
			jsonObject = JSONFactoryUtil.createJSONObject(
				backgroundTask.getStatusMessage());
		}
		catch (JSONException jsone) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsone, jsone);
			}
		}

		return translateJSON(jsonObject, locale);
	}

	protected abstract TemplateResource getTemplateResource();

	protected abstract Map<String, Object> getTemplateVars();

	protected boolean hasBackgroundTaskStatus() {
		if (backgroundTaskStatus != null) {
			return true;
		}

		return false;
	}

	protected JSONArray translateJSON(JSONArray jsonArray, Locale locale) {
		JSONArray translatedJSON = JSONFactoryUtil.createJSONArray();

		for (Object object : jsonArray) {
			if (object instanceof JSONObject) {
				translatedJSON.put(translateJSON((JSONObject)object, locale));
			}
			else if (object instanceof JSONArray) {
				translatedJSON.put(translateJSON((JSONArray)object, locale));
			}
			else if (object instanceof String) {
				translatedJSON.put(LanguageUtil.get(locale, (String)object));
			}
			else {
				translatedJSON.put(object);
			}
		}

		return translatedJSON;
	}

	protected JSONObject translateJSON(JSONObject jsonObject, Locale locale) {
		if (locale == null) {
			return jsonObject;
		}

		JSONObject translatedJSON = JSONFactoryUtil.createJSONObject();

		Iterator<String> iterator = jsonObject.keys();

		while (iterator.hasNext()) {
			String key = iterator.next();

			Object object = jsonObject.get(key);

			if (object instanceof JSONObject) {
				translatedJSON.put(
					key, translateJSON((JSONObject)object, locale));
			}
			else if (object instanceof JSONArray) {
				translatedJSON.put(
					key, translateJSON((JSONArray)object, locale));
			}
			else if (object instanceof String) {
				translatedJSON.put(
					key, LanguageUtil.get(locale, (String)object));
			}
			else {
				translatedJSON.put(key, object);
			}
		}

		return translatedJSON;
	}

	protected static final int PERCENTAGE_MAX = 100;

	protected static final int PERCENTAGE_MIN = 0;

	protected static final int PERCENTAGE_NONE = -1;

	protected final BackgroundTask backgroundTask;
	protected final BackgroundTaskStatus backgroundTaskStatus;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseBackgroundTaskDisplay.class);

}