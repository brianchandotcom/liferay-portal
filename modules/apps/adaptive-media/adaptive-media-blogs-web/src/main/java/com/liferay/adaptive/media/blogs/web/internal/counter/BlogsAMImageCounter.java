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

package com.liferay.adaptive.media.blogs.web.internal.counter;

import com.liferay.adaptive.media.image.counter.AMImageCounter;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true, property = "adaptive.media.key=blogs",
	service = AMImageCounter.class
)
public class BlogsAMImageCounter implements AMImageCounter {

	@Override
	public int countExpectedAMImageEntries(long companyId) {
		DynamicQuery dynamicQuery = _dlFileEntryLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		Property classNameIdProperty = PropertyFactoryUtil.forName(
			"classNameId");

		long classNameId = _classNameLocalService.getClassNameId(
			BlogsEntry.class.getName());

		dynamicQuery.add(classNameIdProperty.eq(classNameId));

		Property mimeTypeProperty = PropertyFactoryUtil.forName("mimeType");

		dynamicQuery.add(
			mimeTypeProperty.in(
				_amImageMimeTypeProvider.getSupportedMimeTypes()));

		return (int)_dlFileEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Reference
	private AMImageMimeTypeProvider _amImageMimeTypeProvider;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

}