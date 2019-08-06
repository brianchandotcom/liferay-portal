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

package com.liferay.segments.service.impl;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.base.SegmentsExperimentServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Eduardo García
 */
@Component(
	property = {
		"json.web.service.context.name=segments",
		"json.web.service.context.path=SegmentsExperiment"
	},
	service = AopService.class
)
public class SegmentsExperimentServiceImpl
	extends SegmentsExperimentServiceBaseImpl {

	@Override
	public SegmentsExperiment addSegmentsExperiment(
			long segmentsExperienceId, long classNameId, long classPK,
			String name, String description, String goal, String goalTarget,
			ServiceContext serviceContext)
		throws PortalException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), classPK, ActionKeys.UPDATE);

		return segmentsExperimentLocalService.addSegmentsExperiment(
			segmentsExperienceId, classNameId, classPK, name, description, goal,
			goalTarget, serviceContext);
	}

	@Override
	public SegmentsExperiment fetchSegmentsExperiment(
			long segmentsExperienceId, long classNameId, long classPK,
			int status)
		throws PortalException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), classPK, ActionKeys.UPDATE);

		return segmentsExperimentLocalService.fetchSegmentsExperiment(
			segmentsExperienceId, classNameId, classPK, status);
	}

	@Override
	public List<SegmentsExperiment> getSegmentsExperienceSegmentsExperiments(
			long[] segmentsExperienceIds, long classNameId, long classPK,
			int[] statuses, int start, int end)
		throws PortalException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), classPK, ActionKeys.UPDATE);

		return segmentsExperimentLocalService.
			getSegmentsExperienceSegmentsExperiments(
				segmentsExperienceIds, classNameId, classPK, statuses, start,
				end);
	}

	@Override
	public SegmentsExperiment getSegmentsExperiment(long segmentsExperimentId)
		throws PortalException {

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentLocalService.getSegmentsExperiment(
				segmentsExperimentId);

		_segmentsExperimentResourcePermission.check(
			getPermissionChecker(), segmentsExperiment, ActionKeys.VIEW);

		return segmentsExperiment;
	}

	@Override
	public List<SegmentsExperiment> getSegmentsExperiments(
		long groupId, long classNameId, long classPK) {

		return segmentsExperimentPersistence.filterFindByG_C_C(
			groupId, classNameId, _getPublishedLayoutClassPK(classPK));
	}

	@Override
	public SegmentsExperiment updateSegmentsExperiment(
			long segmentsExperimentId, String name, String description,
			String goal, String goalTarget)
		throws PortalException {

		_segmentsExperimentResourcePermission.check(
			getPermissionChecker(),
			segmentsExperimentLocalService.getSegmentsExperiment(
				segmentsExperimentId),
			ActionKeys.UPDATE);

		return segmentsExperimentLocalService.updateSegmentsExperiment(
			segmentsExperimentId, name, description, goal, goalTarget);
	}

	@Override
	public SegmentsExperiment updateSegmentsExperiment(
			String segmentsExperimentKey, int status)
		throws PortalException {

		_segmentsExperimentResourcePermission.check(
			getPermissionChecker(),
			segmentsExperimentLocalService.getSegmentsExperiment(
				segmentsExperimentKey),
			ActionKeys.UPDATE);

		return segmentsExperimentLocalService.updateSegmentsExperiment(
			segmentsExperimentKey, status);
	}

	private long _getPublishedLayoutClassPK(long classPK) {
		Layout layout = layoutLocalService.fetchLayout(classPK);

		if ((layout != null) &&
			(layout.getClassNameId() == classNameLocalService.getClassNameId(
				Layout.class)) &&
			(layout.getClassPK() != 0)) {

			return layout.getClassPK();
		}

		return classPK;
	}

	@Reference(
		target = "(model.class.name=com.liferay.segments.model.SegmentsExperiment)"
	)
	private ModelResourcePermission<SegmentsExperiment>
		_segmentsExperimentResourcePermission;

}