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

package com.liferay.portlet.announcements.service.persistence.impl;

import com.liferay.announcements.kernel.model.AnnouncementsEntry;
import com.liferay.announcements.kernel.service.persistence.AnnouncementsEntryPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
public class AnnouncementsEntryFinderBaseImpl
	extends BasePersistenceImpl<AnnouncementsEntry> {

	public AnnouncementsEntryFinderBaseImpl() {
		setModelClass(AnnouncementsEntry.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);
	}

	@Override
	public Set<String> getBadColumnNames() {
		return getAnnouncementsEntryPersistence().getBadColumnNames();
	}

	/**
	 * Returns the announcements entry persistence.
	 *
	 * @return the announcements entry persistence
	 */
	public AnnouncementsEntryPersistence getAnnouncementsEntryPersistence() {
		return announcementsEntryPersistence;
	}

	/**
	 * Sets the announcements entry persistence.
	 *
	 * @param announcementsEntryPersistence the announcements entry persistence
	 */
	public void setAnnouncementsEntryPersistence(
		AnnouncementsEntryPersistence announcementsEntryPersistence) {

		this.announcementsEntryPersistence = announcementsEntryPersistence;
	}

	@BeanReference(type = AnnouncementsEntryPersistence.class)
	protected AnnouncementsEntryPersistence announcementsEntryPersistence;

	private static final Log _log = LogFactoryUtil.getLog(
		AnnouncementsEntryFinderBaseImpl.class);

}