/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.document.library.internal.info.item.provider;

import com.liferay.analytics.reports.info.item.provider.AnalyticsReportsInfoItemObjectProvider;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceRegistry;
import com.liferay.info.item.provider.InfoItemObjectProvider;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Yurena Cabrera
 */
@Component(service = AnalyticsReportsInfoItemObjectProvider.class)
public class FileEntryAnalyticsReportsInfoItemObjectProvider
	implements AnalyticsReportsInfoItemObjectProvider<FileEntry> {

	@Override
	public FileEntry getAnalyticsReportsInfoItemObject(
		InfoItemReference infoItemReference) {

		ClassPKInfoItemIdentifier classPKInfoItemIdentifier =
			(ClassPKInfoItemIdentifier)
				infoItemReference.getInfoItemIdentifier();

		InfoItemObjectProvider<Object> infoItemObjectProvider =
			_infoItemServiceRegistry.getFirstInfoItemService(
				InfoItemObjectProvider.class, getClassName(),
				classPKInfoItemIdentifier.getInfoItemServiceFilter());

		try {
			return (FileEntry)infoItemObjectProvider.getInfoItem(
				classPKInfoItemIdentifier);
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			_log.error(noSuchInfoItemException);
		}

		return null;
	}

	@Override
	public String getClassName() {
		return FileEntry.class.getName();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FileEntryAnalyticsReportsInfoItemObjectProvider.class);

	@Reference
	private InfoItemServiceRegistry _infoItemServiceRegistry;

}