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
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
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
	public long getExportDataCount(long userId) throws PortalException {
		ActionableDynamicQuery nonemptyFileActionableDynamicQuery =
			getActionableDynamicQuery(userId);

		ActionableDynamicQuery.AddCriteriaMethod nonemptyFileAddCriteriaMethod =
			nonemptyFileActionableDynamicQuery.getAddCriteriaMethod();

		nonemptyFileActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				nonemptyFileAddCriteriaMethod.addCriteria(dynamicQuery);

				dynamicQuery.add(RestrictionsFactoryUtil.gt("size", 0L));
			});

		long count = nonemptyFileActionableDynamicQuery.performCount() * 2L;

		ActionableDynamicQuery emptyFileActionableDynamicQuery =
			getActionableDynamicQuery(userId);

		ActionableDynamicQuery.AddCriteriaMethod emptyFileAddCriteriaMethod =
			emptyFileActionableDynamicQuery.getAddCriteriaMethod();

		nonemptyFileActionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				emptyFileAddCriteriaMethod.addCriteria(dynamicQuery);

				dynamicQuery.add(RestrictionsFactoryUtil.eq("size", 0L));
			});

		return count + emptyFileActionableDynamicQuery.performCount();
	}

	@Override
	protected void writeToZip(DLFileEntry dlFileEntry, ZipWriter zipWriter)
		throws Exception {

		if (dlFileEntry.getSize() > 0) {
			String dlFileEntryFileName = StringBundler.concat(
				dlFileEntry.getPrimaryKeyObj(), ".",
				dlFileEntry.getExtension());

			zipWriter.addEntry(
				dlFileEntryFileName, dlFileEntry.getContentStream());
		}

		zipWriter.addEntry(
			dlFileEntry.getPrimaryKeyObj() + "-meta.xml", export(dlFileEntry));
	}

}