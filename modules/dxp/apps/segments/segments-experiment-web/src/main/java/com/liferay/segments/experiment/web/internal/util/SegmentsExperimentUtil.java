/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.experiment.web.internal.util;

import com.liferay.analytics.settings.configuration.AnalyticsConfiguration;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;

import java.util.Locale;

/**
 * @author David Arques
 */
public class SegmentsExperimentUtil {

	public static final String ANALYTICS_CLOUD_TRIAL_URL =
		"https://www.liferay.com/products/analytics-cloud/get-started";

	public static JSONObject toGoalJSONObject(
		Locale locale, UnicodeProperties typeSettingsUnicodeProperties) {

		String goal = typeSettingsUnicodeProperties.getProperty("goal");

		return JSONUtil.put(
			"label",
			LanguageUtil.get(
				ResourceBundleUtil.getBundle(
					"content.Language", locale, SegmentsExperimentUtil.class),
				goal)
		).put(
			"target", typeSettingsUnicodeProperties.getProperty("goalTarget")
		).put(
			"value", goal
		);
	}

	public static JSONObject toSegmentsExperimentJSONObject(
			AnalyticsConfiguration analyticsConfiguration, Group group,
			Locale locale, SegmentsExperiment segmentsExperiment)
		throws PortalException {

		if (segmentsExperiment == null) {
			return null;
		}

		return JSONUtil.put(
			"confidenceLevel", segmentsExperiment.getConfidenceLevel()
		).put(
			"description", segmentsExperiment.getDescription()
		).put(
			"detailsURL",
			_getViewSegmentsExperimentDetailsURL(
				analyticsConfiguration, group, segmentsExperiment)
		).put(
			"editable", _isEditable(segmentsExperiment)
		).put(
			"goal",
			toGoalJSONObject(
				locale, segmentsExperiment.getTypeSettingsProperties())
		).put(
			"name", segmentsExperiment.getName()
		).put(
			"segmentsEntryName", segmentsExperiment.getSegmentsEntryName(locale)
		).put(
			"segmentsExperienceId",
			String.valueOf(segmentsExperiment.getSegmentsExperienceId())
		).put(
			"segmentsExperimentId",
			String.valueOf(segmentsExperiment.getSegmentsExperimentId())
		).put(
			"status", toStatusJSONObject(locale, segmentsExperiment.getStatus())
		).put(
			"type", toTypeJSONObject(locale, segmentsExperiment.getType())
		);
	}

	public static JSONObject toSegmentsExperimentRelJSONObject(
			Locale locale, SegmentsExperimentRel segmentsExperimentRel)
		throws PortalException {

		if (segmentsExperimentRel == null) {
			return null;
		}

		return JSONUtil.put(
			"control", segmentsExperimentRel.isControl()
		).put(
			"name", segmentsExperimentRel.getName(locale)
		).put(
			"segmentsExperienceId",
			String.valueOf(segmentsExperimentRel.getSegmentsExperienceId())
		).put(
			"segmentsExperimentId",
			String.valueOf(segmentsExperimentRel.getSegmentsExperimentId())
		).put(
			"segmentsExperimentRelId",
			String.valueOf(segmentsExperimentRel.getSegmentsExperimentRelId())
		).put(
			"split", segmentsExperimentRel.getSplit()
		);
	}

	public static JSONObject toStatusJSONObject(Locale locale, int status) {
		SegmentsExperimentConstants.Status segmentsExperimentConstantsStatus =
			SegmentsExperimentConstants.Status.parse(status);

		if (segmentsExperimentConstantsStatus == null) {
			return null;
		}

		return JSONUtil.put(
			"label",
			LanguageUtil.get(
				ResourceBundleUtil.getBundle(
					"content.Language", locale, SegmentsExperimentUtil.class),
				segmentsExperimentConstantsStatus.getLabel())
		).put(
			"value", segmentsExperimentConstantsStatus.getValue()
		);
	}

	public static JSONObject toTypeJSONObject(Locale locale, String type) {
		SegmentsExperimentConstants.Type segmentsExperimentConstantsType =
			SegmentsExperimentConstants.Type.parse(type);

		if (segmentsExperimentConstantsType == null) {
			return null;
		}

		return JSONUtil.put(
			"label",
			LanguageUtil.get(
				ResourceBundleUtil.getBundle(
					"content.Language", locale, SegmentsExperimentUtil.class),
				segmentsExperimentConstantsType.getLabel())
		).put(
			"value", segmentsExperimentConstantsType.name()
		);
	}

	private static String _getViewSegmentsExperimentDetailsURL(
		AnalyticsConfiguration analyticsConfiguration, Group group,
		SegmentsExperiment segmentsExperiment) {

		if (segmentsExperiment == null) {
			return StringPool.BLANK;
		}

		String liferayAnalyticsURL =
			analyticsConfiguration.liferayAnalyticsURL();

		if (Validator.isNull(liferayAnalyticsURL)) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(5);

		sb.append(liferayAnalyticsURL);

		String analyticsChannelId = GetterUtil.getString(
			group.getTypeSettingsProperty("analyticsChannelId"));

		if (Validator.isNotNull(analyticsChannelId)) {
			sb.append(StringPool.SLASH);
			sb.append(analyticsChannelId);
		}

		sb.append("/tests/overview/");
		sb.append(segmentsExperiment.getSegmentsExperimentKey());

		return sb.toString();
	}

	private static boolean _isEditable(SegmentsExperiment segmentsExperiment) {
		SegmentsExperimentConstants.Status status =
			SegmentsExperimentConstants.Status.valueOf(
				segmentsExperiment.getStatus());

		return status.isEditable();
	}

	private SegmentsExperimentUtil() {
	}

}