/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
@Component(service = UADExporter.class)
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

		zipWriter.addEntry(
			dlFileEntry.getPrimaryKeyObj() + "-meta.xml", export(dlFileEntry));

		if (dlFileEntry.getSize() > 0) {
			String dlFileEntryFileName = StringBundler.concat(
				dlFileEntry.getPrimaryKeyObj(), ".",
				dlFileEntry.getExtension());

			zipWriter.addEntry(
				dlFileEntryFileName, dlFileEntry.getContentStream());
		}
	}

}