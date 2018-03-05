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

package com.liferay.announcements.uad.anonymizer;

import com.liferay.announcements.kernel.model.AnnouncementsFlag;
import com.liferay.announcements.kernel.service.AnnouncementsFlagLocalService;
import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.announcements.uad.entity.AnnouncementsFlagUADEntity;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.anonymizer.BaseUADEntityAnonymizer;
import com.liferay.user.associated.data.anonymizer.UADEntityAnonymizer;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exception.UADEntityException;
import com.liferay.user.associated.data.util.UADAnonymizerHelper;
import com.liferay.user.associated.data.util.UADDynamicQueryHelper;

import java.util.Collections;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noah Sherrill
 */
@Component(
	immediate = true,
	property = {"model.class.name=" + AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_FLAG},
	service = UADEntityAnonymizer.class
)
public class AnnouncementsFlagUADEntityAnonymizer
	extends BaseUADEntityAnonymizer {

	@Override
	public void autoAnonymize(UADEntity uadEntity) throws PortalException {
		AnnouncementsFlag announcementsFlag = _getAnnouncementsFlag(uadEntity);

		_autoAnonymize(announcementsFlag);
	}

	@Override
	public void autoAnonymizeAll(long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_getActionableDynamicQuery(userId);

		actionableDynamicQuery.setPerformActionMethod(
			(AnnouncementsFlag announcementsFlag) -> _autoAnonymize(
				announcementsFlag));

		actionableDynamicQuery.performActions();
	}

	@Override
	public void delete(UADEntity uadEntity) throws PortalException {
		AnnouncementsFlag announcementsFlag = _getAnnouncementsFlag(uadEntity);

		_announcementsFlagLocalService.deleteFlag(announcementsFlag);
	}

	@Override
	public void deleteAll(long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_getActionableDynamicQuery(userId);

		actionableDynamicQuery.setPerformActionMethod(
			(AnnouncementsFlag announcementsFlag) ->
				_announcementsFlagLocalService.deleteFlag(announcementsFlag));

		actionableDynamicQuery.performActions();
	}

	@Override
	public List<String> getUADEntityNonanonymizableFieldNames() {
		return Collections.emptyList();
	}

	@Override
	protected UADEntityAggregator getUADEntityAggregator() {
		return _uadEntityAggregator;
	}

	private void _autoAnonymize(AnnouncementsFlag announcementsFlag)
		throws PortalException {

		announcementsFlag.setUserId(_uadAnonymizerHelper.getAnonymousUserId());

		_announcementsFlagLocalService.updateAnnouncementsFlag(
			announcementsFlag);
	}

	private ActionableDynamicQuery _getActionableDynamicQuery(long userId) {
		return _uadDynamicQueryHelper.addActionableDynamicQueryCriteria(
			_announcementsFlagLocalService.getActionableDynamicQuery(),
			AnnouncementsUADConstants.USER_ID_FIELD_NAMES_ANNOUNCEMENTS_FLAG,
			userId);
	}

	private AnnouncementsFlag _getAnnouncementsFlag(UADEntity uadEntity)
		throws PortalException {

		_validate(uadEntity);

		AnnouncementsFlagUADEntity announcementsFlagUADEntity =
			(AnnouncementsFlagUADEntity)uadEntity;

		return announcementsFlagUADEntity.getAnnouncementsFlag();
	}

	private void _validate(UADEntity uadEntity) throws PortalException {
		if (!(uadEntity instanceof AnnouncementsFlagUADEntity)) {
			throw new UADEntityException();
		}
	}

	@Reference
	private AnnouncementsFlagLocalService _announcementsFlagLocalService;

	@Reference
	private UADAnonymizerHelper _uadAnonymizerHelper;

	@Reference
	private UADDynamicQueryHelper _uadDynamicQueryHelper;

	@Reference(
		target = "(model.class.name=" + AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_FLAG + ")"
	)
	private UADEntityAggregator _uadEntityAggregator;

}