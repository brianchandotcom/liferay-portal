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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.base.SegmentsExperimentRelServiceBaseImpl;

import java.util.List;

/**
 * The implementation of the segments experiment rel remote service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.segments.service.SegmentsExperimentRelService</code> interface.
 *
 * <p>
 * This is a remote service. Methods of this service are expected to have security checks based on the propagated JAAS credentials because this service can be accessed remotely.
 * </p>
 *
 * @author Eduardo García
 * @see SegmentsExperimentRelServiceBaseImpl
 */
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
	public List<SegmentsExperimentRel> getSegmentsExperimentRels(
			long segmentsExperimentId)
		throws PortalException {

		_segmentsExperimentResourcePermission.check(
			getPermissionChecker(), segmentsExperimentId, ActionKeys.VIEW);

		return segmentsExperimentRelLocalService.getSegmentsExperimentRels(
			segmentsExperimentId);
	}

	private static volatile ModelResourcePermission<SegmentsExperiment>
		_segmentsExperimentResourcePermission =
			ModelResourcePermissionFactory.getInstance(
				SegmentsExperimentServiceImpl.class,
				"_segmentsExperimentResourcePermission",
				SegmentsExperiment.class);

}