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

package com.liferay.segments.internal.processor;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.segments.processor.SegmentsExperienceRequestProcessor;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = "segments.experience.request.processor.priority:Integer=0",
	service = SegmentsExperienceRequestProcessor.class
)
public class DefaultSegmentsExperienceRequestProcessor
	implements SegmentsExperienceRequestProcessor {

	@Override
	public long[] getSegmentsExperienceIds(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long groupId, long plid,
			long[] segmentsExperienceIds)
		throws PortalException {

		return TransformUtil.transformToLongArray(
			_segmentsExperienceLocalService.getSegmentsExperiences(
				groupId, plid, true),
			segmentsExperience -> {
				if (segmentsExperience.getPriority() < 0) {
					return null;
				}

				return segmentsExperience.getSegmentsExperienceId();
			});
	}

	@Override
	public long[] getSegmentsExperienceIds(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, long groupId, long plid,
			long[] segmentsEntryIds, long[] segmentsExperienceIds)
		throws PortalException {

		return TransformUtil.transformToLongArray(
			_segmentsExperienceLocalService.getSegmentsExperiences(
				groupId, segmentsEntryIds, plid, true),
			segmentsExperience -> {
				if (segmentsExperience.getPriority() < 0) {
					return null;
				}

				return segmentsExperience.getSegmentsExperienceId();
			});
	}

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}