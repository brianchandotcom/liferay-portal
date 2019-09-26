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
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.segments.constants.SegmentsExperienceConstants;
import com.liferay.segments.constants.SegmentsExperimentConstants;
import com.liferay.segments.exception.LockedSegmentsExperimentException;
import com.liferay.segments.exception.SegmentsExperimentRelNameException;
import com.liferay.segments.exception.SegmentsExperimentRelSplitException;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperiment;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsExperienceLocalService;
import com.liferay.segments.service.base.SegmentsExperimentRelLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the segments experiment rel local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are
 * added, rerun ServiceBuilder to copy their definitions into the
 * <code>com.liferay.segments.service.SegmentsExperimentRelLocalService</code>
 * interface.  <p> This is a local service. Methods of this service will not
 * have security checks based on the propagated JAAS credentials because this
 * service can only be accessed from within the same VM.
 * </p>
 *
 * @author Eduardo García
 * @see    SegmentsExperimentRelLocalServiceBaseImpl
 */
@Component(
	property = "model.class.name=com.liferay.segments.model.SegmentsExperimentRel",
	service = AopService.class
)
public class SegmentsExperimentRelLocalServiceImpl
	extends SegmentsExperimentRelLocalServiceBaseImpl {

	@Override
	public SegmentsExperimentRel addSegmentsExperimentRel(
			long segmentsExperimentId, long segmentsExperienceId,
			ServiceContext serviceContext)
		throws PortalException {

		_validateSegmentsExperimentStatus(segmentsExperimentId);

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
	public SegmentsExperimentRel deleteSegmentsExperimentRel(
			long segmentsExperimentRelId)
		throws PortalException {

		return segmentsExperimentRelLocalService.deleteSegmentsExperimentRel(
			segmentsExperimentRelPersistence.findByPrimaryKey(
				segmentsExperimentRelId));
	}

	@Override
	public SegmentsExperimentRel deleteSegmentsExperimentRel(
			SegmentsExperimentRel segmentsExperimentRel)
		throws PortalException {

		return segmentsExperimentRelLocalService.deleteSegmentsExperimentRel(
			segmentsExperimentRel, false);
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public SegmentsExperimentRel deleteSegmentsExperimentRel(
			SegmentsExperimentRel segmentsExperimentRel, boolean force)
		throws PortalException {

		if (!force) {
			_validateSegmentsExperimentStatus(
				segmentsExperimentRel.getSegmentsExperimentId());
		}

		// Segments experiment rel

		segmentsExperimentRelPersistence.remove(segmentsExperimentRel);

		// Segments experience

		if (!segmentsExperimentRel.isActive() &&
			(segmentsExperimentRel.getSegmentsExperienceId() !=
				SegmentsExperienceConstants.ID_DEFAULT)) {

			_segmentsExperienceLocalService.deleteSegmentsExperience(
				segmentsExperimentRel.getSegmentsExperienceId());
		}

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
				segmentsExperimentRel, true);
		}
	}

	@Override
	public SegmentsExperimentRel fetchSegmentsExperimentRel(
			long segmentsExperimentId, long segmentsExperienceId)
		throws PortalException {

		return segmentsExperimentRelPersistence.fetchByS_S(
			segmentsExperimentId, segmentsExperienceId);
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

	@Override
	public SegmentsExperimentRel updateSegmentsExperimentRel(
			long segmentsExperimentRelId, double split)
		throws PortalException {

		SegmentsExperimentRel segmentsExperimentRel =
			segmentsExperimentRelPersistence.findByPrimaryKey(
				segmentsExperimentRelId);

		return _updateSegmentsExperimentRelSplit(segmentsExperimentRel, split);
	}

	@Override
	public SegmentsExperimentRel updateSegmentsExperimentRel(
			long segmentsExperimentId, long segmentsExperienceId, double split)
		throws PortalException {

		SegmentsExperimentRel segmentsExperimentRel =
			segmentsExperimentRelPersistence.findByS_S(
				segmentsExperimentId, segmentsExperienceId);

		return _updateSegmentsExperimentRelSplit(segmentsExperimentRel, split);
	}

	@Override
	public SegmentsExperimentRel updateSegmentsExperimentRel(
			long segmentsExperimentRelId, String name,
			ServiceContext serviceContext)
		throws PortalException {

		SegmentsExperimentRel segmentsExperimentRel =
			segmentsExperimentRelPersistence.findByPrimaryKey(
				segmentsExperimentRelId);

		_validateSegmentsExperimentStatus(
			segmentsExperimentRel.getSegmentsExperimentId());

		if (segmentsExperimentRel.isControl()) {
			throw new SegmentsExperimentRelNameException(
				"The experiment control experience cannot be updated");
		}

		SegmentsExperience segmentsExperience =
			_segmentsExperienceLocalService.getSegmentsExperience(
				segmentsExperimentRel.getSegmentsExperienceId());

		segmentsExperience.setName(name, serviceContext.getLocale());

		_segmentsExperienceLocalService.updateSegmentsExperience(
			segmentsExperience);

		return segmentsExperimentRelPersistence.update(segmentsExperimentRel);
	}

	private SegmentsExperimentRel _updateSegmentsExperimentRelSplit(
			SegmentsExperimentRel segmentsExperimentRel, double split)
		throws PortalException {

		_validateSegmentsExperimentRelSplit(split);

		segmentsExperimentRel.setSplit(split);

		return segmentsExperimentRelPersistence.update(segmentsExperimentRel);
	}

	private void _validateSegmentsExperimentRelSplit(double split)
		throws PortalException {

		if ((split > 1) || (split < 0)) {
			throw new SegmentsExperimentRelSplitException(
				"Split " + split + " is not a value between 0 and 1");
		}
	}

	private void _validateSegmentsExperimentStatus(long segmentsExperimentId)
		throws PortalException {

		SegmentsExperiment segmentsExperiment =
			segmentsExperimentPersistence.findByPrimaryKey(
				segmentsExperimentId);

		SegmentsExperimentConstants.Status status =
			SegmentsExperimentConstants.Status.valueOf(
				segmentsExperiment.getStatus());

		if (!status.isEditable()) {
			throw new LockedSegmentsExperimentException(segmentsExperimentId);
		}
	}

	@Reference
	private SegmentsExperienceLocalService _segmentsExperienceLocalService;

}