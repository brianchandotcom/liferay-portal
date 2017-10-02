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

package com.liferay.portlet.documentlibrary.service.impl;

import com.liferay.document.library.kernel.model.DLSyncEvent;
import com.liferay.portlet.documentlibrary.service.base.DLSyncEventLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Dennis Ju
 * @deprecated As of 7.0.0, replaced by {@link
 *             com.liferay.document.library.sync.service.impl.DLSyncEventLocalServiceImpl}
 */
@Deprecated
public class DLSyncEventLocalServiceImpl
	extends DLSyncEventLocalServiceBaseImpl {

	@Override
	public DLSyncEvent addDLSyncEvent(String event, String type, long typePK) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.sync.service.impl." +
					"DLSyncEventLocalServiceImpl");
	}

	@Override
	public void deleteDLSyncEvents() {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.sync.service.impl." +
					"DLSyncEventLocalServiceImpl");
	}

	@Override
	public List<DLSyncEvent> getDLSyncEvents(long modifiedTime) {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.sync.service.impl." +
					"DLSyncEventLocalServiceImpl");
	}

	@Override
	public List<DLSyncEvent> getLatestDLSyncEvents() {
		throw new UnsupportedOperationException(
			"This class is deprecated and replaced by " +
				"com.liferay.document.library.sync.service.impl." +
					"DLSyncEventLocalServiceImpl");
	}

}