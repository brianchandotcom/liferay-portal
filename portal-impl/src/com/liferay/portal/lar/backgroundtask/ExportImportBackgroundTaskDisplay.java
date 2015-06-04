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

package com.liferay.portal.lar.backgroundtask;

import com.liferay.portal.kernel.backgroundtask.BackgroundTaskDisplayJSONTransformer;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskStatus;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskDisplay;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.BackgroundTask;
import com.liferay.portal.security.permission.ResourceActionsUtil;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

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

		BackgroundTaskStatus backgroundTaskStatus = getBackgroundTaskStatus();

		_percentage = _NO_PERCENTAGE;

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
				StagingBackgroundTaskConstants.
					ALL_MODEL_ADDITION_COUNTERS_TOTAL);
		long allPortletAdditionCounter = getBackgroundTaskStatusAttributeLong(
			StagingBackgroundTaskConstants.ALL_PORTLET_ADDITION_COUNTER);

		_allProgressBarCountersTotal =
			allModelAdditionCountersTotal + allPortletAdditionCounter;

		long currentModelAdditionCountersTotal =
			getBackgroundTaskStatusAttributeLong(
				StagingBackgroundTaskConstants.
					CURRENT_MODEL_ADDITION_COUNTERS_TOTAL);
		long currentPortletAdditionCounter =
			getBackgroundTaskStatusAttributeLong(
				StagingBackgroundTaskConstants.
					CURRENT_PORTLET_ADDITION_COUNTER);

		_currentProgressBarCountersTotal =
			currentModelAdditionCountersTotal + currentPortletAdditionCounter;

		_phase = getBackgroundTaskStatusAttributeString(
			StagingBackgroundTaskConstants.PHASE);
		_stagedModelName = getBackgroundTaskStatusAttributeString(
			StagingBackgroundTaskConstants.STAGED_MODEL_NAME);
		_stagedModelType = getBackgroundTaskStatusAttributeString(
			StagingBackgroundTaskConstants.STAGED_MODEL_TYPE);
	}

	@Override
	public String getMessage(Locale locale) {
		if (hasStagedModelMessage()) {
			return translateStagedModelMessage(locale);
		}

		return LanguageUtil.get(locale, createMessageKey());
	}

	@Override
	public int getPercentage() {
		if (_percentage > _NO_PERCENTAGE) {
			return _percentage;
		}

		_percentage = _MAX_PERCENTAGE;

		if (_allProgressBarCountersTotal > 0) {
			int base = _MAX_PERCENTAGE;

			if (_phase.equals(Constants.EXPORT) &&
				!Validator.equals(_cmd, Constants.PUBLISH_TO_REMOTE)) {

				base = _EXPORT_PHASE_MAX_PERCENTAGE;
			}

			_percentage = Math.round(
				(float)_currentProgressBarCountersTotal /
					_allProgressBarCountersTotal * base);
		}

		return _percentage;
	}

	@Override
	public boolean hasMessage() {
		if (hasRemoteMessage() || hasStagedModelMessage()) {
			return true;
		}

		return false;
	}

	@Override
	public boolean hasPercentage() {
		if ((_allProgressBarCountersTotal > 0) &&
			(!Validator.equals(_cmd, Constants.PUBLISH_TO_REMOTE) ||
			 (getPercentage() < _MAX_PERCENTAGE))) {

			return true;
		}

		return false;
	}

	protected JSONObject createMessageDetails(BackgroundTask backgroundTask) {
		if (_details != null) {
			return _details;
		}

		JSONObject backgroundTaskJSON = null;

		try {
			backgroundTaskJSON = JSONFactoryUtil.createJSONObject(
				backgroundTask.getStatusMessage());
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}

			return null;
		}

		boolean exported = MapUtil.getBoolean(
			backgroundTask.getTaskContextMap(), "exported");
		boolean validated = MapUtil.getBoolean(
			backgroundTask.getTaskContextMap(), "validated");

		String detailHeader =
			"an-unexpected-error-occurred-with-the-publication-process." +
				"-please-check-your-portal-and-publishing-configuration";

		if (exported && !validated) {
			detailHeader =
				"an-unexpected-error-occurred-with-the-publication-" +
					"process.-please-check-your-portal-and-publishing-" +
						"configuration";
		}

		JSONArray detailItems = JSONFactoryUtil.createJSONArray();

		JSONArray errorMessagesJSONArray = backgroundTaskJSON.getJSONArray(
			"messageListItems");

		if (errorMessagesJSONArray != null) {
			String message = backgroundTaskJSON.getString("message");

			BackgroundTaskDisplayJSONTransformer.addDetailItem(
				detailItems, message, errorMessagesJSONArray);
		}

		JSONArray warningMessagesJSONArray = backgroundTaskJSON.getJSONArray(
			"warningMessages");

		if (warningMessagesJSONArray != null) {
			String message = "the-following-data-has-not-been-published";

			if ((errorMessagesJSONArray != null) &&
				(errorMessagesJSONArray.length() > 0)) {

				message =
					"consider-that-the-following-data-would-not-have-been-" +
						"published-either";
			}

			BackgroundTaskDisplayJSONTransformer.addDetailItem(
				detailItems, message, warningMessagesJSONArray);
		}

		int status = backgroundTaskJSON.getInt("status");

		_details = BackgroundTaskDisplayJSONTransformer.createDetailsJSONObject(
			detailHeader, detailItems, status);

		return _details;
	}

	protected String createMessageKey() {
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

			if (Validator.equals(_cmd, Constants.IMPORT)) {
				_messageKey = "importing";
			}
			else if (Validator.equals(_cmd, Constants.PUBLISH_TO_LIVE) ||
					 Validator.equals(_cmd, Constants.PUBLISH_TO_REMOTE)) {

				_messageKey = "publishing";
			}
		}

		return _messageKey;
	}

	protected boolean hasRemoteMessage() {
		if (Validator.equals(_cmd, Constants.PUBLISH_TO_REMOTE) &&
			(getPercentage() == _MAX_PERCENTAGE)) {

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

	protected String translateStagedModelMessage(Locale locale) {
		StringBundler sb = new StringBundler(8);

		sb.append("<strong>");
		sb.append(LanguageUtil.get(locale, createMessageKey()));
		sb.append(StringPool.TRIPLE_PERIOD);
		sb.append("</strong>");
		sb.append(
			ResourceActionsUtil.getModelResource(locale, _stagedModelType));
		sb.append("<em>");
		sb.append(HtmlUtil.escape(_stagedModelName));
		sb.append("</em>");

		return sb.toString();
	}

	private static final int _EXPORT_PHASE_MAX_PERCENTAGE = 50;

	private static final Log _log = LogFactoryUtil.getLog(
		ExportImportBackgroundTaskDisplay.class);

	private final long _allProgressBarCountersTotal;
	private final String _cmd;
	private final long _currentProgressBarCountersTotal;
	private JSONObject _details;
	private String _messageKey;
	private int _percentage;
	private final String _phase;
	private final String _stagedModelName;
	private final String _stagedModelType;

}