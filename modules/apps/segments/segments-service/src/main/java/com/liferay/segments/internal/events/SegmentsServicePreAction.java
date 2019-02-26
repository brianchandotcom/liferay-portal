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

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.events.Action;
import com.liferay.portal.kernel.events.ActionException;
import com.liferay.portal.kernel.events.LifecycleAction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.segments.constants.SegmentsConstants;
import com.liferay.segments.constants.SegmentsWebKeys;
import com.liferay.segments.context.Context;
import com.liferay.segments.internal.configuration.SegmentsServiceConfiguration;
import com.liferay.segments.internal.context.RequestContextMapper;
import com.liferay.segments.model.SegmentsEntry;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperienceModel;
import com.liferay.segments.provider.SegmentsEntryProvider;
import com.liferay.segments.service.SegmentsEntryLocalService;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	configurationPid = "com.liferay.segments.internal.configuration.SegmentsServiceConfiguration",
	property = "key=servlet.service.events.pre", service = LifecycleAction.class
)
public class SegmentsServicePreAction extends Action {

	@Override
	public void run(HttpServletRequest request, HttpServletResponse response)
		throws ActionException {

		try {
			doRun(request);
		}
		catch (Exception e) {
			throw new ActionException(e);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_segmentsServiceConfiguration = ConfigurableUtil.createConfigurable(
			SegmentsServiceConfiguration.class, properties);
	}

	protected void doRun(HttpServletRequest request) throws PortalException {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isLifecycleRender()) {
			return;
		}

		long[] segmentsEntryIds = null;
		long[] segmentsExperienceIds = null;

		Layout layout = themeDisplay.getLayout();

		long classNameId = _classNameLocalService.getClassNameId(
			Layout.class.getName());

		if (_segmentsServiceConfiguration.segmentationEnabled() &&
			!layout.isTypeControlPanel()) {

			try {
				Context context = _requestContextMapper.map(request);

				segmentsEntryIds = _segmentsEntryProvider.getSegmentsEntryIds(
					User.class.getName(), themeDisplay.getUserId(), context);

				segmentsExperienceIds = _getSegmentsExperienceIds(
					layout.getGroupId(), segmentsEntryIds, classNameId,
					layout.getPrimaryKey());

			}
			catch (PortalException pe) {
				if (_log.isWarnEnabled()) {
					_log.warn(pe.getMessage());
				}
			}
		}

		if (segmentsEntryIds == null) {
			SegmentsEntry segmentsEntry =
				_segmentsEntryLocalService.fetchSegmentsEntry(
					themeDisplay.getCompanyGroupId(),
					SegmentsConstants.KEY_DEFAULT, true);

			segmentsEntryIds = new long[] {segmentsEntry.getSegmentsEntryId()};
		}

		if (segmentsExperienceIds == null) {
			SegmentsExperience segmentsExperience =
				_segmentsExperienceLocalService.fetchDefaultSegmentsExperience(
					layout.getGroupId(), classNameId, layout.getPrimaryKey(),
					true);

			segmentsExperienceIds = new long[] {
				segmentsExperience.getSegmentsExperienceId()
			};
		}

		request.setAttribute(
			SegmentsWebKeys.SEGMENTS_ENTRY_IDS, segmentsEntryIds);
		request.setAttribute(
			SegmentsWebKeys.SEGMENTS_EXPERIENCE_IDS, segmentsExperienceIds);
	}

	private long[] _getSegmentsExperienceIds(
			long groupId, long[] segmentsEntryIds, long classNameId,
			long classPK)
		throws PortalException {

		List<SegmentsExperience> segmentsExperiences =
			_segmentsExperienceLocalService.getSegmentsExperiences(
				groupId, segmentsEntryIds, classNameId, classPK, true, true,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Stream<SegmentsExperience> stream = segmentsExperiences.stream();

		return stream.mapToLong(
			SegmentsExperienceModel::getSegmentsExperienceId
		).toArray();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SegmentsServicePreAction.class);

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private RequestContextMapper _requestContextMapper;

	@Reference
	private SegmentsEntryLocalService _segmentsEntryLocalService;

	@Reference
	private SegmentsEntryProvider _segmentsEntryProvider;

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	private SegmentsServiceConfiguration _segmentsServiceConfiguration;

}