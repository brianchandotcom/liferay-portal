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

package com.liferay.segments.experiment.web.internal.servlet.taglib;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.segments.experiment.web.internal.constants.SegmentsExperimentWebKeys;
import com.liferay.segments.experiment.web.internal.util.SegmentsExperimentUtil;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(immediate = true, service = DynamicInclude.class)
public class SegmentsExperimentAnalyticsTopHeadDynamicInclude
	implements DynamicInclude {

	@Override
	public void include(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, String key)
		throws IOException {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!SegmentsExperimentUtil.isAnalyticsEnabled(
				themeDisplay.getCompanyId())) {

			return;
		}

		SegmentsExperiment segmentsExperiment =
			(SegmentsExperiment)httpServletRequest.getAttribute(
				SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT);
		long[] segmentsExperienceIds = GetterUtil.getLongValues(
			httpServletRequest.getAttribute(
				SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS));

		StringBundler sb = StringUtil.replaceToStringBundler(
			_TMPL_CONTENT, "${", "}",
			_getValues(
				segmentsExperiment,
				_getSegmentsExperienceKey(segmentsExperienceIds)));

		sb.writeTo(httpServletResponse.getWriter());
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/dynamic_include/top_head.jsp#analytics");
	}

	private String _getSegmentsExperienceKey(long[] segmentsExperienceIds) {
		if (segmentsExperienceIds.length > 0) {
			SegmentsExperience segmentsExperience =
				_segmentsExperienceLocalService.fetchSegmentsExperience(
					segmentsExperienceIds[0]);

			if (segmentsExperience != null) {
				return segmentsExperience.getSegmentsExperienceKey();
			}
		}

		return SegmentsExperienceConstants.KEY_DEFAULT;
	}

	private Map<String, String> _getValues(
		SegmentsExperiment segmentsExperiment,
		String segmentsExperimentSegmentsExperienceKey) {

		Map<String, String> analyticsClientContextMap = new HashMap<>();

		if (segmentsExperiment == null) {
			analyticsClientContextMap.put(
				"experienceId", segmentsExperimentSegmentsExperienceKey);
			analyticsClientContextMap.put("experimentId", StringPool.BLANK);
			analyticsClientContextMap.put("variantId", StringPool.BLANK);

			return analyticsClientContextMap;
		}

		analyticsClientContextMap.put(
			"experienceId", segmentsExperiment.getSegmentsExperienceKey());
		analyticsClientContextMap.put(
			"experimentId", segmentsExperiment.getSegmentsExperimentKey());
		analyticsClientContextMap.put(
			"variantId", segmentsExperimentSegmentsExperienceKey);

		return analyticsClientContextMap;
	}

	private static final String _TMPL_CONTENT = StringUtil.read(
		SegmentsExperimentAnalyticsTopHeadJSPDynamicInclude.class,
		"analytics.tmpl");

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}