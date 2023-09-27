/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.document.library.web.internal.counter;

import com.liferay.adaptive.media.image.counter.AMImageCounter;
import com.liferay.adaptive.media.image.mime.type.AMImageMimeTypeProvider;
import com.liferay.adaptive.media.image.validator.AMImageValidator;
import com.liferay.document.library.configuration.DLFileEntryConfiguration;
import com.liferay.document.library.kernel.service.DLFileEntryLocalService;
import com.liferay.document.library.kernel.service.DLFileVersionLocalService;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

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
	property = "adaptive.media.key=document-library",
	service = AMImageCounter.class
)
public class DLAMImageCounter implements AMImageCounter {

	@Override
	public int countExpectedAMImageEntries(long companyId) {
		return _getFileEntriesCount(companyId) -
			_getTrashedFileEntriesCount(companyId);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_dlFileEntryConfiguration = ConfigurableUtil.createConfigurable(
			DLFileEntryConfiguration.class, properties);
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
				ArrayUtil.filter(
					_amImageMimeTypeProvider.getSupportedMimeTypes(),
					_amImageValidator::isProcessingSupported)));

		Property sizeProperty = PropertyFactoryUtil.forName("size");

		dlFileEntryEntryDynamicQuery.add(
			sizeProperty.le(
				_dlFileEntryConfiguration.previewableProcessorMaxSize()));

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
	private AMImageValidator _amImageValidator;

	private volatile DLFileEntryConfiguration _dlFileEntryConfiguration;

	@Reference
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@Reference
	private DLFileVersionLocalService _dlFileVersionLocalService;

}