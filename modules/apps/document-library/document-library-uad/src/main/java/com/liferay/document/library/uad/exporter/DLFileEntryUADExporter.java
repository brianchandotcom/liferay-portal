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

package com.liferay.document.library.uad.exporter;

import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.user.associated.data.exporter.UADExporter;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 * @author Drew Brokke
 */
@Component(immediate = true, service = UADExporter.class)
public class DLFileEntryUADExporter extends BaseDLFileEntryUADExporter {

	@Override
	protected void writeToZip(DLFileEntry dlFileEntry, ZipWriter zipWriter)
		throws Exception {

		String dlFileEntryFileName = StringBundler.concat(
			String.valueOf(dlFileEntry.getPrimaryKeyObj()), ".",
			dlFileEntry.getExtension());

		zipWriter.addEntry(dlFileEntryFileName, dlFileEntry.getContentStream());

		zipWriter.addEntry(
			dlFileEntry.getPrimaryKeyObj() + "-meta.xml", export(dlFileEntry));
	}

}