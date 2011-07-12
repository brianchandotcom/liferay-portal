/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.util;

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.repository.liferayrepository.model.LiferayFileEntry;

/**
 * @author Alexander Chow
 * @author Mika Koivisto
 */
public class RawMetadataProcessor implements DLProcessor {

	public void trigger(FileEntry fileEntry) {
		if (fileEntry instanceof LiferayFileEntry) {
			MessageBusUtil.sendMessage(
				DestinationNames.DOCUMENT_LIBRARY_RAW_METADATA_PROCESSOR,
				fileEntry.getModel());
		}
	}

}