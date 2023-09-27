/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.blogs.reading.time.internal.service;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.blogs.service.BlogsEntryLocalServiceWrapper;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.servlet.taglib.ui.ImageSelector;
import com.liferay.reading.time.service.ReadingTimeEntryLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = ServiceWrapper.class)
public class BlogsReadingTimeEntryLocalServiceWrapper
	extends BlogsEntryLocalServiceWrapper {

	@Override
	public BlogsEntry addEntry(
			String externalReferenceCode, long userId, String title,
			String subtitle, String urlTitle, String description,
			String content, Date displayDate, boolean allowPingbacks,
			boolean allowTrackbacks, String[] trackbacks,
			String coverImageCaption, ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws PortalException {

		BlogsEntry blogsEntry = super.addEntry(
			externalReferenceCode, userId, title, subtitle, urlTitle,
			description, content, displayDate, allowPingbacks, allowTrackbacks,
			trackbacks, coverImageCaption, coverImageImageSelector,
			smallImageImageSelector, serviceContext);

		_readingTimeEntryLocalService.updateReadingTimeEntry(blogsEntry);

		return blogsEntry;
	}

	@Override
	public BlogsEntry deleteEntry(BlogsEntry blogsEntry)
		throws PortalException {

		blogsEntry = super.deleteEntry(blogsEntry);

		_readingTimeEntryLocalService.deleteReadingTimeEntry(blogsEntry);

		return blogsEntry;
	}

	@Override
	public BlogsEntry updateEntry(
			long userId, long entryId, String title, String subtitle,
			String urlTitle, String description, String content,
			Date displayDate, boolean allowPingbacks, boolean allowTrackbacks,
			String[] trackbacks, String coverImageCaption,
			ImageSelector coverImageImageSelector,
			ImageSelector smallImageImageSelector,
			ServiceContext serviceContext)
		throws PortalException {

		BlogsEntry blogsEntry = super.updateEntry(
			userId, entryId, title, subtitle, urlTitle, description, content,
			displayDate, allowPingbacks, allowTrackbacks, trackbacks,
			coverImageCaption, coverImageImageSelector, smallImageImageSelector,
			serviceContext);

		_readingTimeEntryLocalService.updateReadingTimeEntry(blogsEntry);

		return blogsEntry;
	}

	@Reference
	private BlogsEntryLocalService _blogsEntryLocalService;

	@Reference
	private ReadingTimeEntryLocalService _readingTimeEntryLocalService;

}