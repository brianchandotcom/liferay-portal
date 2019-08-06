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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.segments.model.SegmentsEntryRel;
import com.liferay.segments.service.base.SegmentsEntryRelLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;

/**
 * @author Eduardo García
 */
@Component(
	property = "model.class.name=com.liferay.segments.model.SegmentsEntryRel",
	service = AopService.class
)
public class SegmentsEntryRelLocalServiceImpl
	extends SegmentsEntryRelLocalServiceBaseImpl {

	@Override
	public SegmentsEntryRel addSegmentsEntryRel(
			long segmentsEntryId, long classNameId, long classPK,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getUserId());

		long segmentsEntryRelId = counterLocalService.increment();

		SegmentsEntryRel segmentsEntryRel = segmentsEntryRelPersistence.create(
			segmentsEntryRelId);

		segmentsEntryRel.setGroupId(serviceContext.getScopeGroupId());
		segmentsEntryRel.setCompanyId(user.getCompanyId());
		segmentsEntryRel.setUserId(user.getUserId());
		segmentsEntryRel.setUserName(user.getFullName());
		segmentsEntryRel.setCreateDate(
			serviceContext.getCreateDate(new Date()));
		segmentsEntryRel.setModifiedDate(
			serviceContext.getModifiedDate(new Date()));
		segmentsEntryRel.setSegmentsEntryId(segmentsEntryId);
		segmentsEntryRel.setClassNameId(classNameId);
		segmentsEntryRel.setClassPK(classPK);

		segmentsEntryRelPersistence.update(segmentsEntryRel);

		return segmentsEntryRel;
	}

	@Override
	public void deleteSegmentsEntryRel(
			long segmentsEntryId, long classNameId, long classPK)
		throws PortalException {

		segmentsEntryRelPersistence.removeByS_CN_CPK(
			segmentsEntryId, classNameId, classPK);
	}

	@Override
	public void deleteSegmentsEntryRels(long segmentsEntryId) {
		segmentsEntryRelPersistence.removeBySegmentsEntryId(segmentsEntryId);
	}

	@Override
	public void deleteSegmentsEntryRels(long classNameId, long classPK) {
		segmentsEntryRelPersistence.removeByCN_CPK(classNameId, classPK);
	}

	@Override
	public List<SegmentsEntryRel> getSegmentsEntryRels(long segmentsEntryId) {
		return segmentsEntryRelPersistence.findBySegmentsEntryId(
			segmentsEntryId);
	}

	@Override
	public List<SegmentsEntryRel> getSegmentsEntryRels(
		long segmentsEntryId, int start, int end,
		OrderByComparator<SegmentsEntryRel> orderByComparator) {

		return segmentsEntryRelPersistence.findBySegmentsEntryId(
			segmentsEntryId, start, end, orderByComparator);
	}

	@Override
	public List<SegmentsEntryRel> getSegmentsEntryRels(
		long classNameId, long classPK) {

		return segmentsEntryRelPersistence.findByCN_CPK(classNameId, classPK);
	}

	@Override
	public List<SegmentsEntryRel> getSegmentsEntryRels(
		long groupId, long classNameId, long classPK) {

		return segmentsEntryRelPersistence.findByG_CN_CPK(
			groupId, classNameId, classPK);
	}

	@Override
	public int getSegmentsEntryRelsCount(long segmentsEntryId) {
		return segmentsEntryRelPersistence.countBySegmentsEntryId(
			segmentsEntryId);
	}

	@Override
	public int getSegmentsEntryRelsCount(long classNameId, long classPK) {
		return segmentsEntryRelPersistence.countByCN_CPK(classNameId, classPK);
	}

	@Override
	public int getSegmentsEntryRelsCount(
		long groupId, long classNameId, long classPK) {

		return segmentsEntryRelPersistence.countByG_CN_CPK(
			groupId, classNameId, classPK);
	}

	@Override
	public boolean hasSegmentsEntryRel(
		long segmentsEntryId, long classNameId, long classPK) {

		SegmentsEntryRel segmentsEntryRel =
			segmentsEntryRelPersistence.fetchByS_CN_CPK(
				segmentsEntryId, classNameId, classPK);

		if (segmentsEntryRel != null) {
			return true;
		}

		return false;
	}

}