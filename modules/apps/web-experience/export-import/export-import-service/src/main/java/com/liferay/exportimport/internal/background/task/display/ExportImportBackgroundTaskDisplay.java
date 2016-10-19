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

package com.liferay.exportimport.internal.background.task.display;

import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.display.BaseBackgroundTaskDisplay;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.URLTemplateResource;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Andrew Betts
 */
public class ExportImportBackgroundTaskDisplay
	extends BaseBackgroundTaskDisplay {

	public ExportImportBackgroundTaskDisplay(BackgroundTask backgroundTask) {
		super(backgroundTask);

		Map<String, Serializable> taskContextMap =
			backgroundTask.getTaskContextMap();

		_cmd = MapUtil.getString(taskContextMap, Constants.CMD);

		_percentage = PERCENTAGE_NONE;

		if (backgroundTaskStatus == null) {
			_allProgressBarCountersTotal = 0;
			_currentProgressBarCountersTotal = 0;
			_phase = null;
			_stagedModelName = null;
			_stagedModelType = null;

			return;
		}

		long allModelAdditionCountersTotal =
			getBackgroundTaskStatusAttributeLong(
				"allModelAdditionCountersTotal");
		long allPortletAdditionCounter = getBackgroundTaskStatusAttributeLong(
			"allPortletAdditionCounter");

		_allProgressBarCountersTotal =
			allModelAdditionCountersTotal + allPortletAdditionCounter;

		long currentModelAdditionCountersTotal =
			getBackgroundTaskStatusAttributeLong(
				"currentModelAdditionCountersTotal");
		long currentPortletAdditionCounter =
			getBackgroundTaskStatusAttributeLong(
				"currentPortletAdditionCounter");

		_currentProgressBarCountersTotal =
			currentModelAdditionCountersTotal + currentPortletAdditionCounter;

		_phase = getBackgroundTaskStatusAttributeString("phase");
		_stagedModelName = getBackgroundTaskStatusAttributeString(
			"stagedModelName");
		_stagedModelType = getBackgroundTaskStatusAttributeString(
			"stagedModelType");
	}

	@Override
	public int getPercentage() {
		if (_percentage > PERCENTAGE_NONE) {
			return _percentage;
		}

		_percentage = PERCENTAGE_MAX;

		if (_allProgressBarCountersTotal > PERCENTAGE_MIN) {
			int base = PERCENTAGE_MAX;

			if (_phase.equals(Constants.EXPORT) &&
				!Objects.equals(_cmd, Constants.PUBLISH_TO_REMOTE)) {

				base = _EXPORT_PHASE_MAX_PERCENTAGE;
			}

			_percentage = Math.round(
				(float)_currentProgressBarCountersTotal /
					_allProgressBarCountersTotal * base);
		}

		return _percentage;
	}

	@Override
	public boolean hasPercentage() {
		if (!hasBackgroundTaskStatus()) {
			return false;
		}

		if ((_allProgressBarCountersTotal > PERCENTAGE_MIN) &&
			(!Objects.equals(_cmd, Constants.PUBLISH_TO_REMOTE) ||
			 (getPercentage() < PERCENTAGE_MAX))) {

			return true;
		}

		return false;
	}

	@Override
	public String renderDisplayTemplate(Locale locale) {
		if (!backgroundTask.isInProgress()) {
			return super.renderDisplayTemplate(locale);
		}

		if (hasStagedModelMessage()) {
			return getStagedModelMessage(locale);
		}

		return LanguageUtil.get(locale, getStatusMessageKey());
	}

	protected String getStagedModelMessage(Locale locale) {
		StringBundler sb = new StringBundler(8);

		sb.append("<strong>");
		sb.append(LanguageUtil.get(locale, getStatusMessageKey()));
		sb.append(StringPool.TRIPLE_PERIOD);
		sb.append("</strong>");
		sb.append(
			ResourceActionsUtil.getModelResource(locale, _stagedModelType));
		sb.append("<em>");
		sb.append(HtmlUtil.escape(_stagedModelName));
		sb.append("</em>");

		return sb.toString();
	}

	protected String getStatusMessageKey() {
		if (Validator.isNotNull(_messageKey)) {
			return _messageKey;
		}

		_messageKey = StringPool.BLANK;

		if (hasRemoteMessage()) {
			_messageKey =
				"please-wait-as-the-publication-processes-on-the-remote-site";
		}
		else if (hasStagedModelMessage()) {
			_messageKey = "exporting";

			if (Objects.equals(_cmd, Constants.IMPORT)) {
				_messageKey = "importing";
			}
			else if (Objects.equals(_cmd, Constants.PUBLISH_TO_LIVE) ||
					 Objects.equals(_cmd, Constants.PUBLISH_TO_REMOTE)) {

				_messageKey = "publishing";
			}
		}

		return _messageKey;
	}

	@Override
	protected TemplateResource getTemplateResource() {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return new URLTemplateResource(
			_DETIALS_TEMPLATE, classLoader.getResource(_DETIALS_TEMPLATE));
	}

	@Override
	protected Map<String, Object> getTemplateVars() {
		Map<String, Object> templateVars = new HashMap<>();

		templateVars.put(
			"exported",
			MapUtil.getBoolean(backgroundTask.getTaskContextMap(), "exported"));
		templateVars.put(
			"validated",
			MapUtil.getBoolean(
				backgroundTask.getTaskContextMap(), "validated"));

		templateVars.put("htmlUtil", HtmlUtil.getHtml());

		return templateVars;
	}

	protected boolean hasRemoteMessage() {
		if (Objects.equals(_cmd, Constants.PUBLISH_TO_REMOTE) &&
			(getPercentage() == PERCENTAGE_MAX)) {

			return true;
		}

		return false;
	}

	protected boolean hasStagedModelMessage() {
		if (Validator.isNotNull(_stagedModelName) &&
			Validator.isNotNull(_stagedModelType)) {

			return true;
		}

		return false;
	}

	private static final String _DETIALS_TEMPLATE =
		"com/liferay/exportimport/internal/background/task/display" +
			"/dependencies/export_import_background_task_details.ftl";

	private static final int _EXPORT_PHASE_MAX_PERCENTAGE = 50;

	private final long _allProgressBarCountersTotal;
	private final String _cmd;
	private final long _currentProgressBarCountersTotal;
	private String _messageKey;
	private int _percentage;
	private final String _phase;
	private final String _stagedModelName;
	private final String _stagedModelType;

}