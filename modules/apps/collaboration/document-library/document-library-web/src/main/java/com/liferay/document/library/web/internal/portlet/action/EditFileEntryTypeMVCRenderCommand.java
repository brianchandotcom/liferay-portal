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

import com.liferay.document.library.kernel.exception.NoSuchFileEntryTypeException;
import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeService;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.dynamic.data.mapping.kernel.DDMStructure;
import com.liferay.dynamic.data.mapping.kernel.DDMStructureManagerUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 * @author Sergio González
 */
@Component(
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

		DLFileEntryType dlFileEntryType = null;

		try {
			long fileEntryTypeId = ParamUtil.getLong(
				renderRequest, "fileEntryTypeId");

			if (fileEntryTypeId > 0) {
				dlFileEntryType = _dlFileEntryTypeService.getFileEntryType(
					fileEntryTypeId);

				renderRequest.setAttribute(
					WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY_TYPE, dlFileEntryType);

				DDMStructure ddmStructure =
					DDMStructureManagerUtil.fetchStructure(
						dlFileEntryType.getGroupId(),
						PortalUtil.getClassNameId(DLFileEntryMetadata.class),
						DLUtil.getDDMStructureKey(dlFileEntryType));

				if (ddmStructure == null) {
					ddmStructure = DDMStructureManagerUtil.fetchStructure(
						dlFileEntryType.getGroupId(),
						PortalUtil.getClassNameId(DLFileEntryMetadata.class),
						DLUtil.getDeprecatedDDMStructureKey(dlFileEntryType));
				}

				renderRequest.setAttribute(
					WebKeys.DOCUMENT_LIBRARY_DYNAMIC_DATA_MAPPING_STRUCTURE,
					ddmStructure);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchFileEntryTypeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return "/document_library/error.jsp";
			}
			else {
				throw new PortletException(e);
			}
		}

		return "/document_library/edit_file_entry_type.jsp";
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryTypeService(
		DLFileEntryTypeService dlFileEntryTypeService) {

		_dlFileEntryTypeService = dlFileEntryTypeService;
	}

	private DLFileEntryTypeService _dlFileEntryTypeService;

}