/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.blogs.web.internal.counter;

import com.liferay.adaptive.media.image.counter.AMImageCounter;
import com.liferay.adaptive.media.image.counter.BaseAMImageCounter;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.document.library.configuration.DLFileEntryConfigurationProvider;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	property = "adaptive.media.key=blogs", service = AMImageCounter.class
)
public class BlogsAMImageCounter extends BaseAMImageCounter {

	@Override
	protected void forEachFileEntry(
			long companyId, Consumer<DLFileEntry> consumer)
		throws PortalException {

		_dlFileEntryLocalService.forEachFileEntry(
			companyId,
			_classNameLocalService.getClassNameId(BlogsEntry.class.getName()),
			consumer,
			_dlFileEntryConfigurationProvider.
				getCompanyPreviewableProcessorMaxSize(companyId),
			getMimeTypes());
	}

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DLFileEntryConfigurationProvider _dlFileEntryConfigurationProvider;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

}