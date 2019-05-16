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

package com.liferay.batch.engine.core.internal.item.io;

import com.liferay.batch.engine.core.constants.BatchConstants;
import com.liferay.batch.engine.core.item.io.Resource;
import com.liferay.document.library.kernel.service.DLAppLocalService;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;

import java.io.InputStream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Ivica Cardic
 */
@Component(name = "DLResource", service = Resource.class)
public class DLResourceImpl implements Resource {

	@Override
	public InputStream getInputStream(UnicodeProperties jobSettingsProperties) {
		try {
			FileEntry fileEntry = _dlAppLocalService.getFileEntry(
				GetterUtil.getLong(
					jobSettingsProperties.get(BatchConstants.FILE_ENTRY_ID)));

			return fileEntry.getContentStream();
		}
		catch (Exception e) {
			throw new IllegalStateException(e);
		}
	}

	@Reference
	private DLAppLocalService _dlAppLocalService;

}