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

package com.liferay.layout.internal.importer;

import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.layout.internal.service.DefaultLayoutLayoutSetPrototypeLocalServiceWrapper;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = DefaultLayoutDefinitionImporter.class)
public class DefaultLayoutDefinitionImporter {

	public void importDefaultLayoutDefinition(
			Layout layout, ServiceContext serviceContext)
		throws PortalException {

		try {
			LayoutPageTemplateStructure layoutPageTemplateStructure =
				_layoutPageTemplateStructureLocalService.
					fetchLayoutPageTemplateStructure(
						layout.getGroupId(), layout.getPlid(), true);

			LayoutStructure layoutStructure = LayoutStructure.of(
				layoutPageTemplateStructure.getData(
					SegmentsExperienceConstants.ID_DEFAULT));

			String releaseInfo = StringPool.BLANK;

			if (_HTTP_HEADER_VERSION_VERBOSITY_PARTIAL) {
				releaseInfo = ReleaseInfo.getName();
			}
			else if (!_HTTP_HEADER_VERSION_VERBOSITY_DEFAULT) {
				releaseInfo = ReleaseInfo.getReleaseInfo();
			}

			Map<String, String> values = HashMapBuilder.put(
				"RELEASE_INFO",
				"Welcome to ".concat(
					StringUtil.replace(
						releaseInfo, CharPool.OPEN_PARENTHESIS,
						"<br>" + StringPool.OPEN_PARENTHESIS)
				).concat(
					"."
				)
			).put(
				"WELCOME_IMAGE_URL",
				_getWelcomeImageURL(
					layout.getGroupId(), layout.getUserId(), layout.getPlid(),
					serviceContext)
			).build();

			String layoutDefinitionJSON = StringUtil.replace(
				_DEFAULT_LAYOUT_DEFINITION, "${", "}", values);

			_layoutPageTemplatesImporter.importPageElement(
				layout, layoutStructure, layoutStructure.getMainItemId(),
				layoutDefinitionJSON, 0);
		}
		catch (Exception exception) {
			throw new PortalException(exception);
		}
	}

	private String _getWelcomeImageURL(
			long groupId, long userId, long plid, ServiceContext serviceContext)
		throws Exception {

		Repository repository = _portletFileRepository.fetchPortletRepository(
			groupId, Layout.class.getName());

		if (repository == null) {
			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			repository = _portletFileRepository.addPortletRepository(
				groupId, Layout.class.getName(), serviceContext);
		}

		FileEntry fileEntry = _portletFileRepository.fetchPortletFileEntry(
			groupId, repository.getDlFolderId(), _FILE_NAME_WELCOME_IMAGE);

		if (fileEntry == null) {
			byte[] bytes = _file.getBytes(
				DefaultLayoutLayoutSetPrototypeLocalServiceWrapper.class,
				_FILE_NAME_WELCOME_IMAGE);

			fileEntry = _portletFileRepository.addPortletFileEntry(
				groupId, userId, Layout.class.getName(), plid,
				Layout.class.getName(), repository.getDlFolderId(), bytes,
				_FILE_NAME_WELCOME_IMAGE,
				MimeTypesUtil.getContentType(_FILE_NAME_WELCOME_IMAGE), false);
		}

		return DLUtil.getDownloadURL(
			fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK);
	}

	private static final String _DEFAULT_LAYOUT_DEFINITION = StringUtil.read(
		DefaultLayoutLayoutSetPrototypeLocalServiceWrapper.class,
		"default-layout-definition.json");

	private static final String _FILE_NAME_WELCOME_IMAGE = "welcome_bg.jpg";

	private static final boolean _HTTP_HEADER_VERSION_VERBOSITY_DEFAULT =
		StringUtil.equalsIgnoreCase(
			PropsValues.HTTP_HEADER_VERSION_VERBOSITY, "off");

	private static final boolean _HTTP_HEADER_VERSION_VERBOSITY_PARTIAL =
		StringUtil.equalsIgnoreCase(
			PropsValues.HTTP_HEADER_VERSION_VERBOSITY, "partial");

	@Reference
	private File _file;

	@Reference
	private LayoutPageTemplatesImporter _layoutPageTemplatesImporter;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private PortletFileRepository _portletFileRepository;

}