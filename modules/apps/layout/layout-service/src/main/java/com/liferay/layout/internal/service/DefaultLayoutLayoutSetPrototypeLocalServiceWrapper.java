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

package com.liferay.layout.internal.service;

import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.layout.page.template.importer.LayoutPageTemplatesImporter;
import com.liferay.layout.page.template.model.LayoutPageTemplateStructure;
import com.liferay.layout.page.template.service.LayoutPageTemplateStructureLocalService;
import com.liferay.layout.util.structure.LayoutStructure;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutConstants;
import com.liferay.portal.kernel.model.LayoutSetPrototype;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalService;
import com.liferay.portal.kernel.service.LayoutSetPrototypeLocalServiceWrapper;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceWrapper;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MimeTypesUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.segments.constants.SegmentsExperienceConstants;

import java.util.Locale;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Pavel Savinov
 */
@Component(immediate = true, service = ServiceWrapper.class)
public class DefaultLayoutLayoutSetPrototypeLocalServiceWrapper
	extends LayoutSetPrototypeLocalServiceWrapper {

	public DefaultLayoutLayoutSetPrototypeLocalServiceWrapper() {
		super(null);
	}

	public DefaultLayoutLayoutSetPrototypeLocalServiceWrapper(
		LayoutSetPrototypeLocalService layoutSetPrototypeLocalService) {

		super(layoutSetPrototypeLocalService);
	}

	@Override
	public LayoutSetPrototype addLayoutSetPrototype(
			long userId, long companyId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, boolean active,
			boolean layoutsUpdateable, ServiceContext serviceContext)
		throws PortalException {

		LayoutSetPrototype layoutSetPrototype = super.addLayoutSetPrototype(
			userId, companyId, nameMap, descriptionMap, active,
			layoutsUpdateable, serviceContext);

		if (GetterUtil.getBoolean(
				serviceContext.getAttribute("addDefaultLayout"), true)) {

			Layout defaultLayout = _layoutLocalService.addLayout(
				userId, layoutSetPrototype.getGroupId(), true,
				LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, "Home", null, null,
				LayoutConstants.TYPE_CONTENT, false, "/home", serviceContext);

			LayoutPageTemplateStructure layoutPageTemplateStructure =
				_layoutPageTemplateStructureLocalService.
					fetchLayoutPageTemplateStructure(
						defaultLayout.getGroupId(),
						_portal.getClassNameId(Layout.class.getName()),
						defaultLayout.getPlid(), true);

			LayoutStructure layoutStructure = LayoutStructure.of(
				layoutPageTemplateStructure.getData(
					SegmentsExperienceConstants.ID_DEFAULT));

			try {
				String layoutDefinitionJSON = StringUtil.replace(
					_DEFAULT_LAYOUT_DEFINITION, "${WELCOME_IMAGE_URL}",
					_getWelcomeImageURL(
						defaultLayout.getGroupId(), userId,
						defaultLayout.getPlid(), serviceContext));

				_layoutPageTemplatesImporter.importPageElement(
					defaultLayout, layoutStructure,
					layoutStructure.getMainItemId(), layoutDefinitionJSON, 0);
			}
			catch (Exception exception) {
				throw new PortalException(exception);
			}
		}

		return layoutSetPrototype;
	}

	private String _getWelcomeImageURL(
			long groupId, long userId, long plid, ServiceContext serviceContext)
		throws Exception {

		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				groupId, Layout.class.getName());

		if (repository == null) {
			serviceContext.setAddGroupPermissions(true);
			serviceContext.setAddGuestPermissions(true);

			repository = PortletFileRepositoryUtil.addPortletRepository(
				groupId, Layout.class.getName(), serviceContext);
		}

		FileEntry fileEntry = _portletFileRepository.fetchPortletFileEntry(
			groupId, repository.getDlFolderId(), _WELCOME_IMAGE_FILENAME);

		if (fileEntry == null) {
			byte[] bytes = _file.getBytes(
				DefaultLayoutLayoutSetPrototypeLocalServiceWrapper.class,
				_WELCOME_IMAGE_FILENAME);

			fileEntry = _portletFileRepository.addPortletFileEntry(
				groupId, userId, Layout.class.getName(), plid,
				Layout.class.getName(), repository.getDlFolderId(), bytes,
				_WELCOME_IMAGE_FILENAME,
				MimeTypesUtil.getContentType(_WELCOME_IMAGE_FILENAME), false);
		}

		return DLUtil.getDownloadURL(
			fileEntry, fileEntry.getFileVersion(), null, StringPool.BLANK);
	}

	private static final String _DEFAULT_LAYOUT_DEFINITION = StringUtil.read(
		DefaultLayoutLayoutSetPrototypeLocalServiceWrapper.class,
		"default-layout-definition.json");

	private static final String _WELCOME_IMAGE_FILENAME = "welcome_bg.jpg";

	@Reference
	private File _file;

	@Reference
	private LayoutLocalService _layoutLocalService;

	@Reference
	private LayoutPageTemplatesImporter _layoutPageTemplatesImporter;

	@Reference
	private LayoutPageTemplateStructureLocalService
		_layoutPageTemplateStructureLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortletFileRepository _portletFileRepository;

}