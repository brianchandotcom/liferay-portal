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

package com.liferay.segments.experiment.web.internal.servlet.taglib;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.taglib.DynamicInclude;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.experiment.web.internal.constants.SegmentsExperimentWebKeys;
import com.liferay.segments.experiment.web.internal.util.SegmentsExperimentUtil;
import com.liferay.segments.manager.SegmentsExperienceManager;
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
@Component(service = DynamicInclude.class)
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

		if (!SegmentsExperimentUtil.isAnalyticsSynced(
				themeDisplay.getCompanyId(), themeDisplay.getScopeGroupId())) {

			return;
		}

		SegmentsExperiment segmentsExperiment =
			(SegmentsExperiment)httpServletRequest.getAttribute(
				SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT);

		SegmentsExperienceManager segmentsExperienceManager =
			new SegmentsExperienceManager(_segmentsExperienceLocalService);

		StringBundler sb = StringUtil.replaceToStringBundler(
			_TMPL_CONTENT, "${", "}",
			_getValues(
				segmentsExperiment,
				_getSegmentsExperienceKey(
					segmentsExperienceManager.getSegmentsExperienceId(
						httpServletRequest))));

		sb.writeTo(httpServletResponse.getWriter());
	}

	@Override
	public void register(DynamicIncludeRegistry dynamicIncludeRegistry) {
		dynamicIncludeRegistry.register(
			"/dynamic_include/top_head.jsp#analytics");
	}

	private String _getSegmentsExperienceKey(long segmentsExperienceId) {
		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.fetchSegmentsExperience(
				segmentsExperienceId);

		if (segmentsExperience != null) {
			return segmentsExperience.getSegmentsExperienceKey();
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