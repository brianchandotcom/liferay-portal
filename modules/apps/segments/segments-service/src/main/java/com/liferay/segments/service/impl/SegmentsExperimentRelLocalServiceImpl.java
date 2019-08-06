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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.base.SegmentsExperimentRelLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

/**
 * The implementation of the segments experiment rel local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.liferay.segments.service.SegmentsExperimentRelLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Eduardo García
 * @see SegmentsExperimentRelLocalServiceBaseImpl
 */
public class SegmentsExperimentRelLocalServiceImpl
	extends SegmentsExperimentRelLocalServiceBaseImpl {

	@Override
	public SegmentsExperimentRel addSegmentsExperimentRel(
			long segmentsExperimentId, long segmentsExperienceId,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		long segmentsExperimentRelId = counterLocalService.increment();

		SegmentsExperimentRel segmentsExperimentRel =
			segmentsExperimentRelPersistence.create(segmentsExperimentRelId);

		segmentsExperimentRel.setGroupId(serviceContext.getScopeGroupId());
		segmentsExperimentRel.setCompanyId(user.getCompanyId());
		segmentsExperimentRel.setUserId(user.getUserId());
		segmentsExperimentRel.setUserName(user.getFullName());
		segmentsExperimentRel.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		segmentsExperimentRel.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		segmentsExperimentRel.setSegmentsExperimentId(segmentsExperimentId);
		segmentsExperimentRel.setSegmentsExperienceId(segmentsExperienceId);

		segmentsExperimentRelPersistence.update(segmentsExperimentRel);

		return segmentsExperimentRel;
	}

	@Override
	public void deleteSegmentsExperimentRels(long segmentsExperimentId)
		throws PortalException {

		List<SegmentsExperimentRel> segmentsExperimentRels =
			segmentsExperimentRelPersistence.findBySegmentsExperimentId(
				segmentsExperimentId);

		for (SegmentsExperimentRel segmentsExperimentRel :
				segmentsExperimentRels) {

			segmentsExperimentRelLocalService.deleteSegmentsExperimentRel(
				segmentsExperimentRel.getSegmentsExperimentRelId());
		}
	}

	@Override
	public SegmentsExperimentRel getSegmentsExperimentRel(
			long segmentsExperimentId, long segmentsExperienceId)
		throws PortalException {

		return segmentsExperimentRelPersistence.findByS_S(
			segmentsExperimentId, segmentsExperienceId);
	}

	@Override
	public List<SegmentsExperimentRel> getSegmentsExperimentRels(
		long segmentsExperimentId) {

		return segmentsExperimentRelPersistence.findBySegmentsExperimentId(
			segmentsExperimentId);
	}

}