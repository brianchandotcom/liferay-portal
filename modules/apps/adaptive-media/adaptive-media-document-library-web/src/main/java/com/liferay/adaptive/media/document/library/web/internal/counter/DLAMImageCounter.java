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

package com.liferay.adaptive.media.document.library.web.internal.counter;

import com.liferay.adaptive.media.image.counter.AMImageCounter;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.adaptive.media.image.size.AMImageSizeProvider;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(
	immediate = true, property = "adaptive.media.key=document-library",
	service = AMImageCounter.class
)
public class DLAMImageCounter implements AMImageCounter {

	@Override
	public int countExpectedAMImageEntries(long companyId) {
		return _getFileEntriesCount(companyId) -
			_getTrashedFileEntriesCount(companyId);
	}

	private int _getFileEntriesCount(long companyId) {
		DynamicQuery dlFileEntryEntryDynamicQuery =
			_dlFileEntryLocalService.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dlFileEntryEntryDynamicQuery.add(companyIdProperty.eq(companyId));

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");
		Property repositoryIdProperty = PropertyFactoryUtil.forName(
			"repositoryId");

		dlFileEntryEntryDynamicQuery.add(
			groupIdProperty.eqProperty(repositoryIdProperty));

		Property mimeTypeProperty = PropertyFactoryUtil.forName("mimeType");

		dlFileEntryEntryDynamicQuery.add(
			mimeTypeProperty.in(
				_amImageMimeTypeProvider.getSupportedMimeTypes()));

		Property sizeProperty = PropertyFactoryUtil.forName("size");

		dlFileEntryEntryDynamicQuery.add(
			sizeProperty.le(_amImageSizeProvider.getImageMaxSize()));

		return (int)_dlFileEntryLocalService.dynamicQueryCount(
			dlFileEntryEntryDynamicQuery);
	}

	private int _getTrashedFileEntriesCount(long companyId) {
		DynamicQuery dlFileVersionDynamicQuery =
			_dlFileVersionLocalService.dynamicQuery();

		dlFileVersionDynamicQuery.setProjection(
			ProjectionFactoryUtil.countDistinct("fileEntryId"));

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dlFileVersionDynamicQuery.add(companyIdProperty.eq(companyId));

		Property groupIdProperty = PropertyFactoryUtil.forName("groupId");
		Property repositoryIdProperty = PropertyFactoryUtil.forName(
			"repositoryId");

		dlFileVersionDynamicQuery.add(
			groupIdProperty.eqProperty(repositoryIdProperty));

		Property mimeTypeProperty = PropertyFactoryUtil.forName("mimeType");

		dlFileVersionDynamicQuery.add(
			mimeTypeProperty.in(
				_amImageMimeTypeProvider.getSupportedMimeTypes()));

		Property statusProperty = PropertyFactoryUtil.forName("status");

		dlFileVersionDynamicQuery.add(
			statusProperty.eq(WorkflowConstants.STATUS_IN_TRASH));

		return (int)_dlFileEntryLocalService.dynamicQueryCount(
			dlFileVersionDynamicQuery);
	}

	@Reference
	private AMImageMimeTypeProvider _amImageMimeTypeProvider;

	@Reference
	private AMImageSizeProvider _amImageSizeProvider;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

}