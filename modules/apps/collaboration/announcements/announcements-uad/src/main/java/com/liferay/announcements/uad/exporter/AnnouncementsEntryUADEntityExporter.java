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

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.service.AnnouncementsEntryLocalService;
import com.liferay.announcements.uad.constants.AnnouncementsUADConstants;
import com.liferay.announcements.uad.entity.AnnouncementsEntryUADEntity;
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
	property = {"model.class.name=" + AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_ENTRY},
	service = UADEntityExporter.class
)
public class AnnouncementsEntryUADEntityExporter extends BaseUADEntityExporter {

	@Override
	public byte[] export(UADEntity uadEntity) throws PortalException {
		AnnouncementsEntry announcementsEntry = _getAnnouncementsEntry(
			uadEntity);

		String xml = announcementsEntry.toXmlString();

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
				PerformActionMethod<AnnouncementsEntry>() {

				@Override
				public void performAction(AnnouncementsEntry announcementsEntry)
					throws PortalException {

					AnnouncementsEntryUADEntity announcementsEntryUADEntity =
						_getAnnouncementsEntryUADEntity(
							userId, announcementsEntry);

					byte[] data = export(announcementsEntryUADEntity);

					try {
						zipWriter.addEntry(
							announcementsEntry.getEntryId() + ".xml", data);
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
		return AnnouncementsEntry.class.getName();
	}

	@Override
	protected UADEntityAggregator getUADEntityAggregator() {
		return _uadEntityAggregator;
	}

	private ActionableDynamicQuery _getActionableDynamicQuery(long userId) {
		return _uadDynamicQueryHelper.addActionableDynamicQueryCriteria(
			_announcementsEntryLocalService.getActionableDynamicQuery(),
			AnnouncementsUADConstants.USER_ID_FIELD_NAMES_ANNOUNCEMENTS_ENTRY,
			userId);
	}

	private AnnouncementsEntry _getAnnouncementsEntry(UADEntity uadEntity)
		throws PortalException {

		_validate(uadEntity);

		AnnouncementsEntryUADEntity announcementsEntryUADEntity =
			(AnnouncementsEntryUADEntity)uadEntity;

		return announcementsEntryUADEntity.getAnnouncementsEntry();
	}

	private AnnouncementsEntryUADEntity _getAnnouncementsEntryUADEntity(
		long userId, AnnouncementsEntry announcementsEntry) {

		return new AnnouncementsEntryUADEntity(
			userId, String.valueOf(announcementsEntry.getEntryId()),
			announcementsEntry);
	}

	private void _validate(UADEntity uadEntity) throws PortalException {
		if (!(uadEntity instanceof AnnouncementsEntryUADEntity)) {
			throw new UADEntityException();
		}
	}

	@Reference
	private AnnouncementsEntryLocalService _announcementsEntryLocalService;

	@Reference
	private UADDynamicQueryHelper _uadDynamicQueryHelper;

	@Reference(
		target = "(model.class.name=" + AnnouncementsUADConstants.CLASS_NAME_ANNOUNCEMENTS_ENTRY + ")"
	)
	private UADEntityAggregator _uadEntityAggregator;

}