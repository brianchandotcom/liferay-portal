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

package com.liferay.segments.internal.events;

import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.SegmentsEntryRetriever;
import com.liferay.segments.configuration.provider.SegmentsConfigurationProvider;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.segments.context.RequestContextMapper;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.processor.SegmentsExperienceRequestProcessorRegistry;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	configurationPid = "com.liferay.segments.configuration.SegmentsConfiguration",
	property = "key=servlet.service.events.pre", service = LifecycleAction.class
)
public class SegmentsServicePreAction extends Action {

	@Override
	public void run(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws ActionException {

		try {
			if (!_segmentsConfigurationProvider.isSegmentationEnabled(
					_portal.getCompanyId(httpServletRequest))) {

				return;
			}

			_run(httpServletRequest, httpServletResponse);
		}
		catch (Exception exception) {
			throw new ActionException(exception);
		}
	}

	private long[] _getSegmentsExperienceIds(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse, long groupId, long userId,
		long classNameId, long classPK) {

		try {
			long[] segmentsExperienceIds =
				_segmentsExperienceRequestProcessorRegistry.
					getSegmentsExperienceIds(
						httpServletRequest, httpServletResponse, groupId,
						classNameId, classPK);

			Set<Long> segmentsExperienceIdsSegmentsEntryIds = new HashSet<>();

			for (long segmentsExperienceId : segmentsExperienceIds) {
				SegmentsExperience segmentsExperience =
					_segmentsExperienceLocalService.fetchSegmentsExperience(
						segmentsExperienceId);

				segmentsExperienceIdsSegmentsEntryIds.add(
					segmentsExperience.getSegmentsEntryId());
			}

			long[] cachedSegmentsEntryIds =
				(long[])httpServletRequest.getAttribute(
					SegmentsWebKeys.SEGMENTS_ENTRY_IDS);

			long[] segmentsEntryIds = null;

			if (cachedSegmentsEntryIds != null) {
				segmentsEntryIds = cachedSegmentsEntryIds;
			}
			else {
				segmentsEntryIds = _segmentsEntryRetriever.getSegmentsEntryIds(
					groupId, userId,
					_requestContextMapper.map(httpServletRequest),
					ArrayUtil.toArray(
						segmentsExperienceIdsSegmentsEntryIds.toArray(
							new Long[0])));
			}

			httpServletRequest.setAttribute(
				SegmentsWebKeys.SEGMENTS_ENTRY_IDS, segmentsEntryIds);

			return _segmentsExperienceRequestProcessorRegistry.
				getSegmentsExperienceIds(
					httpServletRequest, httpServletResponse, groupId,
					classNameId, classPK, segmentsEntryIds);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}

		return new long[] {
			_segmentsExperienceLocalService.fetchDefaultSegmentsExperienceId(
				classPK)
		};
	}

	private void _run(
		HttpServletRequest httpServletRequest,
		HttpServletResponse httpServletResponse) {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isLifecycleRender()) {
			return;
		}

		Layout layout = themeDisplay.getLayout();

		if ((layout == null) || layout.isTypeControlPanel() ||
			(!layout.isTypeAssetDisplay() && !layout.isTypeContent())) {

			return;
		}

		httpServletRequest.setAttribute(
			SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS,
			_getSegmentsExperienceIds(
				httpServletRequest, httpServletResponse, layout.getGroupId(),
				themeDisplay.getUserId(),
				_portal.getClassNameId(Layout.class.getName()),
				layout.getPlid()));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsServicePreAction.class);

	@Reference
	private Portal _portal;

	@Reference
	private RequestContextMapper _requestContextMapper;

	@Reference
	private SegmentsConfigurationProvider _segmentsConfigurationProvider;

	@Reference
	private volatile SegmentsEntryRetriever _segmentsEntryRetriever;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference
	private SegmentsExperienceRequestProcessorRegistry
		_segmentsExperienceRequestProcessorRegistry;

}