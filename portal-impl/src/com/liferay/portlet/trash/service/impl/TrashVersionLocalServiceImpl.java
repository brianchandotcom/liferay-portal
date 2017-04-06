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

package com.liferay.portlet.trash.service.impl;

import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.trash.service.base.TrashVersionLocalServiceBaseImpl;
import com.liferay.trash.kernel.model.TrashVersion;

import java.util.List;

/**
 * @author Zsolt Berentey
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.trash.service.impl.TrashVersionLocalServiceImpl}
 */
@Deprecated
public class TrashVersionLocalServiceImpl
	extends TrashVersionLocalServiceBaseImpl {

	@Override
	public TrashVersion addTrashVersion(
		long trashEntryId, String className, long classPK, int status,
		UnicodeProperties typeSettingsProperties) {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashVersionLocalServiceImpl");
	}

	@Override
	public TrashVersion deleteTrashVersion(String className, long classPK) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashVersionLocalServiceImpl");
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #fetchVersion(String, long)}
	 */
	@Deprecated
	@Override
	public TrashVersion fetchVersion(
		long entryId, String className, long classPK) {

		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashVersionLocalServiceImpl");
	}

	@Override
	public TrashVersion fetchVersion(String className, long classPK) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashVersionLocalServiceImpl");
	}

	@Override
	public List<TrashVersion> getVersions(long entryId) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashVersionLocalServiceImpl");
	}

	@Override
	public List<TrashVersion> getVersions(long entryId, String className) {
		throw new UnsupportedOperationException(
			"This class is deprecate and replaced by " +
				"com.liferay.trash.service.impl.TrashVersionLocalServiceImpl");
	}

}