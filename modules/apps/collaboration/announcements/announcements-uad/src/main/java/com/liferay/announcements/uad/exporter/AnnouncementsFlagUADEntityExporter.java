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

package com.liferay.announcements.uad.exporter;

import com.liferay.announcements.kernel.model.AnnouncementsFlag;
import com.liferay.announcements.kernel.service.AnnouncementsFlagLocalService;
import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.announcements.uad.entity.AnnouncementsFlagUADEntity;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.user.associated.data.aggregator.UADEntityAggregator;
import com.liferay.user.associated.data.entity.UADEntity;
import com.liferay.user.associated.data.exception.UADEntityException;
import com.liferay.user.associated.data.exception.UADEntityExporterException;
import com.liferay.user.associated.data.exporter.BaseUADEntityExporter;
import com.liferay.user.associated.data.exporter.UADEntityExporter;
import com.liferay.user.associated.data.util.UADDynamicQueryHelper;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Noah Sherrill
 */
@Component(
	immediate = true,
	property = {"model.class.name=" + AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_FLAG},
	service = UADEntityExporter.class
)
public class AnnouncementsFlagUADEntityExporter extends BaseUADEntityExporter {

	@Override
	public byte[] export(UADEntity uadEntity) throws PortalException {
		AnnouncementsFlag announcementsFlag = _getAnnouncementsFlag(uadEntity);

		String xml = announcementsFlag.toXmlString();

		xml = formatXML(xml);

		try {
			return xml.getBytes(StringPool.UTF8);
		}
		catch (UnsupportedEncodingException uee) {
			throw new UADEntityExporterException(uee);
		}
	}

	@Override
	public File exportAll(long userId) throws PortalException {
		ActionableDynamicQuery actionableDynamicQuery =
			_getActionableDynamicQuery(userId);

		ZipWriter zipWriter = getZipWriter(userId);

		actionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.
				PerformActionMethod<AnnouncementsFlag>() {

				@Override
				public void performAction(AnnouncementsFlag announcementsFlag)
					throws PortalException {

					AnnouncementsFlagUADEntity announcementsFlagUADEntity =
						_getAnnouncementsFlagUADEntity(
							userId, announcementsFlag);

					byte[] data = export(announcementsFlagUADEntity);

					try {
						zipWriter.addEntry(
							announcementsFlag.getFlagId() + ".xml", data);
					}
					catch (IOException ioe) {
						throw new PortalException(ioe);
					}
				}

			});

		actionableDynamicQuery.performActions();

		return zipWriter.getFile();
	}

	@Override
	protected String getEntityName() {
		return AnnouncementsFlag.class.getName();
	}

	@Override
	protected UADEntityAggregator getUADEntityAggregator() {
		return _uadEntityAggregator;
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

	private AnnouncementsFlagUADEntity _getAnnouncementsFlagUADEntity(
		long userId, AnnouncementsFlag announcementsFlag) {

		return new AnnouncementsFlagUADEntity(
			userId, String.valueOf(announcementsFlag.getFlagId()),
			announcementsFlag);
	}

	private void _validate(UADEntity uadEntity) throws PortalException {
		if (!(uadEntity instanceof AnnouncementsFlagUADEntity)) {
			throw new UADEntityException();
		}
	}

	@Reference
	private AnnouncementsFlagLocalService _announcementsFlagLocalService;

	@Reference
	private UADDynamicQueryHelper _uadDynamicQueryHelper;

	@Reference(
		target = "(model.class.name=" + AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_FLAG + ")"
	)
	private UADEntityAggregator _uadEntityAggregator;

}