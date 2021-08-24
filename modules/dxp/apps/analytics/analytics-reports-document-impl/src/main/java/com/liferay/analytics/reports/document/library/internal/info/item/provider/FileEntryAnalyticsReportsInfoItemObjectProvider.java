/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.analytics.reports.document.library.internal.info.item.provider;

import com.liferay.analytics.reports.info.item.provider.AnalyticsReportsInfoItemObjectProvider;
import com.liferay.info.exception.NoSuchInfoItemException;
import com.liferay.info.item.ClassPKInfoItemIdentifier;
import com.liferay.info.item.InfoItemReference;
import com.liferay.info.item.InfoItemServiceTracker;
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
			_infoItemServiceTracker.getFirstInfoItemService(
				InfoItemObjectProvider.class, getClassName());

		try {
			return (FileEntry)infoItemObjectProvider.getInfoItem(
				classPKInfoItemIdentifier);
		}
		catch (NoSuchInfoItemException noSuchInfoItemException) {
			_log.error(noSuchInfoItemException, noSuchInfoItemException);
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
	private InfoItemServiceTracker _infoItemServiceTracker;

}