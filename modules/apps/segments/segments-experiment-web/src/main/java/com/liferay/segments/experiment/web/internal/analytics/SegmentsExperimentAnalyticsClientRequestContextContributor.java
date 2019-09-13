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

package com.liferay.segments.experiment.web.internal.analytics;

import com.liferay.analytics.client.AnalyticsClientRequestContextContributor;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.segments.experiment.web.internal.constants.SegmentsExperimentWebKeys;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcellus Tavares
 * @author Eduardo García
 */
@Component(
	immediate = true, service = AnalyticsClientRequestContextContributor.class
)
public class SegmentsExperimentAnalyticsClientRequestContextContributor
	implements AnalyticsClientRequestContextContributor {

	@Override
	public void contribute(
		Map<String, String> context, HttpServletRequest httpServletRequest) {

		String segmentsExperienceKey = _getSegmentsExperienceKey(
			GetterUtil.getLongValues(
				httpServletRequest.getAttribute(
					SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS)));

		SegmentsExperiment segmentsExperiment =
			(SegmentsExperiment)httpServletRequest.getAttribute(
				SegmentsExperimentWebKeys.SEGMENTS_EXPERIMENT);

		if (segmentsExperiment != null) {
			context.put(
				"experienceId", segmentsExperiment.getSegmentsExperienceKey());
			context.put(
				"experimentId", segmentsExperiment.getSegmentsExperimentKey());
			context.put("variantId", segmentsExperienceKey);

			return;
		}

		context.put("experienceId", segmentsExperienceKey);
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

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}