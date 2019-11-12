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
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.base.SegmentsExperimentRelServiceBaseImpl;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the segments experiment rel remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * <code>com.liferay.segments.service.SegmentsExperimentRelService</code>
 * interface. <p> This is a remote service. Methods of this service are expected
 * to have security checks based on the propagated JAAS credentials because this
 * service can be accessed remotely.
 * </p>
 *
 * @author Eduardo García
 * @see    SegmentsExperimentRelServiceBaseImpl
 */
@Component(
	property = {
		"json.web.service.context.name=segments",
		"json.web.service.context.path=SegmentsExperimentRel"
	},
	service = AopService.class
)
public class SegmentsExperimentRelServiceImpl
	extends SegmentsExperimentRelServiceBaseImpl {

	@Override
	public SegmentsExperimentRel addSegmentsExperimentRel(
			long segmentsExperimentId, long segmentsExperienceId,
			ServiceContext serviceContext)
		throws PortalException {

		_segmentsExperimentResourcePermission.check(
			getPermissionChecker(), segmentsExperimentId, ActionKeys.UPDATE);

		return segmentsExperimentRelLocalService.addSegmentsExperimentRel(
			segmentsExperimentId, segmentsExperienceId, serviceContext);
	}

	@Override
	public SegmentsExperimentRel deleteSegmentsExperimentRel(
			long segmentsExperimentRelId)
		throws PortalException {

		SegmentsExperimentRel segmentsExperimentRel =
			segmentsExperimentRelPersistence.findByPrimaryKey(
				segmentsExperimentRelId);

		_segmentsExperimentResourcePermission.check(
			getPermissionChecker(),
			segmentsExperimentRel.getSegmentsExperimentId(), ActionKeys.UPDATE);

		return segmentsExperimentRelLocalService.deleteSegmentsExperimentRel(
			segmentsExperimentRelId);
	}

	@Override
	public SegmentsExperimentRel getSegmentsExperimentRel(
			long segmentsExperimentId, long segmentsExperienceId)
		throws PortalException {

		SegmentsExperimentRel segmentsExperimentRel =
			segmentsExperimentRelLocalService.getSegmentsExperimentRel(
				segmentsExperimentId, segmentsExperienceId);

		_segmentsExperimentResourcePermission.check(
			getPermissionChecker(), segmentsExperimentId, ActionKeys.VIEW);

		return segmentsExperimentRel;
	}

	@Override
	public List<SegmentsExperimentRel> getSegmentsExperimentRels(
			long segmentsExperimentId)
		throws PortalException {

		_segmentsExperimentResourcePermission.check(
			getPermissionChecker(), segmentsExperimentId, ActionKeys.VIEW);

		return segmentsExperimentRelLocalService.getSegmentsExperimentRels(
			segmentsExperimentId);
	}

	@Override
	public SegmentsExperimentRel updateSegmentsExperimentRel(
			long segmentsExperimentRelId, double split)
		throws PortalException {

		SegmentsExperimentRel segmentsExperimentRel =
			segmentsExperimentRelLocalService.getSegmentsExperimentRel(
				segmentsExperimentRelId);

		_segmentsExperimentResourcePermission.check(
			getPermissionChecker(),
			segmentsExperimentRel.getSegmentsExperimentId(), ActionKeys.UPDATE);

		return segmentsExperimentRelLocalService.updateSegmentsExperimentRel(
			segmentsExperimentRelId, split);
	}

	@Override
	public SegmentsExperimentRel updateSegmentsExperimentRel(
			long segmentsExperimentRelId, String name,
			ServiceContext serviceContext)
		throws PortalException {

		SegmentsExperimentRel segmentsExperimentRel =
			segmentsExperimentRelLocalService.getSegmentsExperimentRel(
				segmentsExperimentRelId);

		_segmentsExperimentResourcePermission.check(
			getPermissionChecker(),
			segmentsExperimentRel.getSegmentsExperimentId(), ActionKeys.UPDATE);

		return segmentsExperimentRelLocalService.updateSegmentsExperimentRel(
			segmentsExperimentRelId, name, serviceContext);
	}

	@Reference(
		target = "(model.class.name=com.liferay.segments.model.SegmentsExperiment)"
	)
	private ModelResourcePermission<SegmentsExperiment>
		_segmentsExperimentResourcePermission;

}