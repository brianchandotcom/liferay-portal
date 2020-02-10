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

package com.liferay.document.library.web.internal.portlet.action;

import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryTypeException;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.web.internal.configuration.FFDocumentLibraryDDMEditorConfiguration;
import com.liferay.document.library.web.internal.constants.DLWebKeys;
import com.liferay.document.library.web.internal.display.context.DLEditFileEntryTypeDisplayContext;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStorageLinkLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 * @author Sergio González
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.FFDocumentLibraryDDMEditorConfiguration",
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/edit_file_entry_type"
	},
	service = MVCRenderCommand.class
)
public class EditFileEntryTypeMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			renderRequest.setAttribute(
				DLWebKeys.
					DOCUMENT_LIBRARY_EDIT_EDIT_FILE_ENTRY_TYPE_DISPLAY_CONTEXT,
				new DLEditFileEntryTypeDisplayContext(
					_ddm, _ddmStorageLinkLocalService,
					_ddmStructureLocalService,
					_ffDocumentLibraryDDMEditorConfiguration, _language,
					_portal.getLiferayPortletRequest(renderRequest)));

			long fileEntryTypeId = ParamUtil.getLong(
				renderRequest, "fileEntryTypeId");

			if (fileEntryTypeId <= 0) {
				return "/document_library/edit_file_entry_type.jsp";
			}

			ThemeDisplay themeDisplay =
				(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

			DLFileEntryType dlFileEntryType =
				_dlFileEntryTypeService.getFileEntryType(fileEntryTypeId);

			_dlFileEntryTypeModelResourcePermission.check(
				themeDisplay.getPermissionChecker(), dlFileEntryType,
				ActionKeys.UPDATE);

			renderRequest.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY_TYPE, dlFileEntryType);

			renderRequest.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE,
				_getDDMStructure(dlFileEntryType));

			return "/document_library/edit_file_entry_type.jsp";
		}
		catch (NoSuchFileEntryTypeException | PrincipalException exception) {
			SessionErrors.add(renderRequest, exception.getClass());

			return "/document_library/error.jsp";
		}
		catch (PortalException portalException) {
			throw new PortletException(portalException);
		}
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffDocumentLibraryDDMEditorConfiguration =
			ConfigurableUtil.createConfigurable(
				FFDocumentLibraryDDMEditorConfiguration.class, properties);
	}

	private DDMStructure _getDDMStructure(DLFileEntryType dlFileEntryType) {
		DDMStructure ddmStructure = _ddmStructureLocalService.fetchStructure(
			dlFileEntryType.getGroupId(),
			_portal.getClassNameId(DLFileEntryMetadata.class),
			DLUtil.getDDMStructureKey(dlFileEntryType));

		if (ddmStructure != null) {
			return ddmStructure;
		}

		ddmStructure = _ddmStructureLocalService.fetchStructure(
			dlFileEntryType.getGroupId(),
			_portal.getClassNameId(DLFileEntryMetadata.class),
			DLUtil.getDeprecatedDDMStructureKey(dlFileEntryType));

		if (ddmStructure != null) {
			return ddmStructure;
		}

		return _ddmStructureLocalService.fetchStructure(
			dlFileEntryType.getGroupId(),
			_portal.getClassNameId(DLFileEntryMetadata.class),
			dlFileEntryType.getFileEntryTypeKey());
	}

	@Reference
	private DDM _ddm;

	@Reference
	private DDMStorageLinkLocalService _ddmStorageLinkLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.document.library.kernel.model.DLFileEntryType)"
	)
	private volatile ModelResourcePermission<DLFileEntryType>
		_dlFileEntryTypeModelResourcePermission;

	@Reference
	private DLFileEntryTypeService _dlFileEntryTypeService;

	private volatile FFDocumentLibraryDDMEditorConfiguration
		_ffDocumentLibraryDDMEditorConfiguration;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}