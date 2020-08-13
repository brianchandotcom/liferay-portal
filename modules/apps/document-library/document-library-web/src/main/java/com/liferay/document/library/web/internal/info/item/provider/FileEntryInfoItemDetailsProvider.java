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

package com.liferay.document.library.web.internal.info.item.provider;

import com.liferay.info.item.InfoItemClassDetails;
import com.liferay.info.item.InfoItemDetails;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.provider.InfoItemDetailsProvider;
import com.liferay.portal.kernel.repository.model.FileEntry;

import org.osgi.service.component.annotations.Component;

/**
 * @author Jorge Ferrer
 */
@Component(service = InfoItemDetailsProvider.class)
public class FileEntryInfoItemDetailsProvider
	implements InfoItemDetailsProvider<FileEntry> {

	@Override
	public InfoItemClassDetails getInfoItemClassDetails() {
		return new InfoItemClassDetails(FileEntry.class.getName());
	}

	@Override
	public InfoItemDetails getInfoItemDetails(FileEntry fileEntry) {
		return new InfoItemDetails(
			getInfoItemClassDetails(),
			new InfoItemReference(
				FileEntry.class.getName(), fileEntry.getFileEntryId()));
	}

}