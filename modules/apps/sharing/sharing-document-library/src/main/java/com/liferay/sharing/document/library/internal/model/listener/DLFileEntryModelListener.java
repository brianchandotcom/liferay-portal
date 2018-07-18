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

package com.liferay.sharing.document.library.internal.model.listener;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.portal.kernel.exception.ModelListenerException;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.sharing.service.SharingEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = ModelListener.class)
public class DLFileEntryModelListener extends BaseModelListener<DLFileEntry> {

	@Override
	public void onBeforeRemove(DLFileEntry dlFileEntry)
		throws ModelListenerException {

		long classNameId = _classNameLocalService.getClassNameId(
			DLFileEntry.class.getName());

		_sharingEntryLocalService.deleteSharingEntries(
			classNameId, dlFileEntry.getFileEntryId());
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private SharingEntryLocalService _sharingEntryLocalService;

}