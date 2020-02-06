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
import com.liferay.portal.kernel.servlet.SessionErrors;
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
 * @author Cristina González
 */
@Component(
	configurationPid = "com.liferay.document.library.configuration.FFDocumentLibraryDDMEditorConfiguration",
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/ddm/edit_ddm_structure"
	},
	service = MVCRenderCommand.class
)
public class EditDDMStructureMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			renderRequest.setAttribute(
				FFDocumentLibraryDDMEditorConfiguration.class.getName(),
				_ffDocumentLibraryDDMEditorConfiguration);

			renderRequest.setAttribute(
				DLWebKeys.
					DOCUMENT_LIBRARY_EDIT_EDIT_FILE_ENTRY_TYPE_DISPLAY_CONTEXT,
				new DLEditFileEntryTypeDisplayContext(
					_ddm, _ddmStorageLinkLocalService,
					_ddmStructureLocalService, _language,
					_portal.getLiferayPortletRequest(renderRequest)));

			renderRequest.setAttribute(
				WebKeys.DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE,
				_getDDMStructure(renderRequest));
		}
		catch (PortalException portalException) {
			SessionErrors.add(renderRequest, portalException.getClass());

			return "/document_library/error.jsp";
		}

		return "/document_library/ddm/edit_ddm_structure.jsp";
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffDocumentLibraryDDMEditorConfiguration =
			ConfigurableUtil.createConfigurable(
				FFDocumentLibraryDDMEditorConfiguration.class, properties);
	}

	private DDMStructure _getDDMStructure(RenderRequest renderRequest)
		throws PortalException {

		return _ddmStructureLocalService.fetchDDMStructure(
			ParamUtil.getLong(renderRequest, "ddmStructureId"));
	}

	@Reference
	private DDM _ddm;

	@Reference
	private DDMStorageLinkLocalService _ddmStorageLinkLocalService;

	@Reference
	private DDMStructureLocalService _ddmStructureLocalService;

	private volatile FFDocumentLibraryDDMEditorConfiguration
		_ffDocumentLibraryDDMEditorConfiguration;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}