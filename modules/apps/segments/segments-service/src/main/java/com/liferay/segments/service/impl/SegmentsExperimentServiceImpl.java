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
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.LayoutPermissionUtil;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.service.SegmentsExperienceLocalService;
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
	public SegmentsExperiment deleteSegmentsExperiment(
			long segmentsExperimentId)
		throws PortalException {

		_segmentsExperimentResourcePermission.check(
			getPermissionChecker(),
			segmentsExperimentLocalService.getSegmentsExperiment(
				segmentsExperimentId),
			ActionKeys.DELETE);

		return segmentsExperimentLocalService.deleteSegmentsExperiment(
			segmentsExperimentId);
	}

	@Override
	public SegmentsExperiment deleteSegmentsExperiment(
			String segmentsExperimentKey)
		throws PortalException {

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentLocalService.getSegmentsExperiment(
				segmentsExperimentKey);

		_segmentsExperimentResourcePermission.check(
			getPermissionChecker(), segmentsExperiment, ActionKeys.DELETE);

		return segmentsExperimentLocalService.deleteSegmentsExperiment(
			segmentsExperiment);
	}

	@Override
	public SegmentsExperiment fetchSegmentsExperiment(
			long segmentsExperienceId, long classNameId, long classPK,
			int[] statuses)
		throws PortalException {

		LayoutPermissionUtil.check(
			getPermissionChecker(), classPK, ActionKeys.UPDATE);

		return segmentsExperimentLocalService.fetchSegmentsExperiment(
			segmentsExperienceId, classNameId, classPK, statuses);
	}

	@Override
	public SegmentsExperiment fetchSegmentsExperiment(
			long groupId, String segmentsExperimentKey)
		throws PortalException {

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentLocalService.fetchSegmentsExperiment(
				groupId, segmentsExperimentKey);

		if ((segmentsExperiment != null) &&
			_segmentsExperimentResourcePermission.contains(
				getPermissionChecker(), segmentsExperiment, ActionKeys.VIEW)) {

			return segmentsExperiment;
		}

		return null;
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
			groupId, classNameId, classPK);
	}

	@Override
	public SegmentsExperiment updateSegmentsExperiment(
			long segmentsExperimentId, double confidenceLevel, int status)
		throws PortalException {

		_segmentsExperimentResourcePermission.check(
			getPermissionChecker(),
			segmentsExperimentLocalService.getSegmentsExperiment(
				segmentsExperimentId),
			ActionKeys.UPDATE);

		return segmentsExperimentLocalService.updateSegmentsExperiment(
			segmentsExperimentId, confidenceLevel, status);
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
	public SegmentsExperiment updateSegmentsExperimentStatus(
			long segmentsExperimentId, int status)
		throws PortalException {

		_segmentsExperimentResourcePermission.check(
			getPermissionChecker(),
			segmentsExperimentLocalService.getSegmentsExperiment(
				segmentsExperimentId),
			ActionKeys.UPDATE);

		return segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperimentId, status);
	}

	@Override
	public SegmentsExperiment updateSegmentsExperimentStatus(
			long segmentsExperimentId, long winnerSegmentsExperienceId,
			int status)
		throws PortalException {

		_segmentsExperimentResourcePermission.check(
			getPermissionChecker(),
			segmentsExperimentLocalService.getSegmentsExperiment(
				segmentsExperimentId),
			ActionKeys.UPDATE);

		return segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperimentId, winnerSegmentsExperienceId, status);
	}

	@Override
	public SegmentsExperiment updateSegmentsExperimentStatus(
			String segmentsExperimentKey, int status)
		throws PortalException {

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentLocalService.getSegmentsExperiment(
				segmentsExperimentKey);

		_segmentsExperimentResourcePermission.check(
			getPermissionChecker(), segmentsExperiment, ActionKeys.UPDATE);

		return segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(), status);
	}

	@Override
	public SegmentsExperiment updateSegmentsExperimentStatus(
			String segmentsExperimentKey, String winnerSegmentsExperienceKey,
			int status)
		throws PortalException {

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentLocalService.getSegmentsExperiment(
				segmentsExperimentKey);

		_segmentsExperimentResourcePermission.check(
			getPermissionChecker(), segmentsExperiment, ActionKeys.UPDATE);

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.getSegmentsExperience(
				segmentsExperiment.getGroupId(), winnerSegmentsExperienceKey);

		return segmentsExperimentLocalService.updateSegmentsExperimentStatus(
			segmentsExperiment.getSegmentsExperimentId(),
			segmentsExperience.getSegmentsExperienceId(), status
		);
	}

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.segments.model.SegmentsExperiment)"
	)
	private ModelResourcePermission<SegmentsExperiment>
		_segmentsExperimentResourcePermission;

}