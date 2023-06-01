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
import com.liferay.adaptive.media.image.validator.AMImageValidator;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.document.library.configuration.DLFileEntryConfiguration;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.DLFileEntryConfiguration",
	property = "adaptive.media.key=blogs", service = AMImageCounter.class
)
public class BlogsAMImageCounter implements AMImageCounter {

	@Override
	public int countExpectedAMImageEntries(long companyId) {
		DynamicQuery dynamicQuery = _dlFileEntryLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(companyId));

		Property classNameIdProperty = PropertyFactoryUtil.forName(
			"classNameId");

		dynamicQuery.add(
			classNameIdProperty.eq(
				_classNameLocalService.getClassNameId(
					BlogsEntry.class.getName())));

		Property mimeTypeProperty = PropertyFactoryUtil.forName("mimeType");

		dynamicQuery.add(
			mimeTypeProperty.in(
				ArrayUtil.filter(
					_amImageMimeTypeProvider.getSupportedMimeTypes(),
					_amImageValidator::isProcessingSupported)));

		Property sizeProperty = PropertyFactoryUtil.forName("size");

		dynamicQuery.add(
			sizeProperty.le(
				_dlFileEntryConfiguration.previewableProcessorMaxSize()));

		return (int)_dlFileEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_dlFileEntryConfiguration = ConfigurableUtil.createConfigurable(
			DLFileEntryConfiguration.class, properties);
	}

	@Reference
	private AMImageMimeTypeProvider _amImageMimeTypeProvider;

	@Reference
	private AMImageValidator _amImageValidator;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private volatile DLFileEntryConfiguration _dlFileEntryConfiguration;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

}