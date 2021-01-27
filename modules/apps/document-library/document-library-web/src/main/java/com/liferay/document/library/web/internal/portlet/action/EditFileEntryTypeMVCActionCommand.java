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

import com.liferay.data.engine.rest.dto.v2_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v2_0.DataLayout;
import com.liferay.data.engine.rest.resource.exception.DataDefinitionValidationException;
import com.liferay.data.engine.rest.resource.v2_0.DataDefinitionResource;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryTypeException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryTypeException;
import com.liferay.document.library.kernel.exception.NoSuchMetadataSetException;
import com.liferay.document.library.kernel.exception.RequiredFileEntryTypeException;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeService;
import com.liferay.dynamic.data.mapping.exception.RequiredStructureException;
import com.liferay.dynamic.data.mapping.kernel.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.kernel.StructureDefinitionException;
import com.liferay.dynamic.data.mapping.kernel.StructureDuplicateElementException;
import com.liferay.dynamic.data.mapping.kernel.StructureNameException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseTransactionalMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.Collections;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alicia García
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/edit_file_entry_type"
	},
	service = MVCActionCommand.class
)
public class EditFileEntryTypeMVCActionCommand
	extends BaseTransactionalMVCActionCommand {

	@Override
	protected void doTransactionalCommand(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD)) {
				_addFileEntryType(actionRequest);
			}
			else if (cmd.equals(Constants.UPDATE)) {
				_updateFileEntryType(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				_deleteFileEntryType(actionRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				_subscribeFileEntryType(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				_unsubscribeFileEntryType(actionRequest);
			}

			if (SessionErrors.isEmpty(actionRequest)) {
				SessionMessages.add(
					actionRequest,
					_portal.getPortletId(actionRequest) +
						SessionMessages.KEY_SUFFIX_REFRESH_PORTLET,
					DLPortletKeys.DOCUMENT_LIBRARY);

				String redirect = _portal.escapeRedirect(
					ParamUtil.getString(actionRequest, "redirect"));

				if (Validator.isNotNull(redirect)) {
					actionResponse.sendRedirect(redirect);
				}
			}
		}
		catch (DataDefinitionValidationException |
			   DuplicateFileEntryTypeException | NoSuchMetadataSetException |
			   RequiredStructureException | StructureDefinitionException |
			   StructureDuplicateElementException | StructureNameException
				   exception) {

			SessionErrors.add(actionRequest, exception.getClass());
		}
		catch (RequiredFileEntryTypeException requiredFileEntryTypeException) {
			SessionErrors.add(
				actionRequest, requiredFileEntryTypeException.getClass());

			actionResponse.setRenderParameter("navigation", "file_entry_types");
		}
		catch (NoSuchFileEntryTypeException | NoSuchStructureException |
			   PrincipalException exception) {

			SessionErrors.add(actionRequest, exception.getClass());

			actionResponse.setRenderParameter(
				"mvcPath", "/document_library/error.jsp");
		}
	}

	private void _addFileEntryType(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).user(
				themeDisplay.getUser()
			).build();

		DataDefinition dataDefinition = DataDefinition.toDTO(
			ParamUtil.getString(actionRequest, "dataDefinition"));

		long[] ddmStructureIds = _getLongArray(
			actionRequest, "ddmStructuresSearchContainerPrimaryKeys");

		if (ArrayUtil.isEmpty(dataDefinition.getDataDefinitionFields()) &&
			ArrayUtil.isEmpty(ddmStructureIds)) {

			throw new DataDefinitionValidationException.MustSetFields();
		}

		dataDefinition.setDefaultDataLayout(
			DataLayout.toDTO(ParamUtil.getString(actionRequest, "dataLayout")));

		dataDefinition =
			dataDefinitionResource.postSiteDataDefinitionByContentType(
				themeDisplay.getScopeGroupId(), "document-library",
				dataDefinition);

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");

		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntryType.class.getName(), actionRequest);

		DLFileEntryType fileEntryType =
			_dlFileEntryTypeService.addFileEntryType(
				themeDisplay.getScopeGroupId(), dataDefinition.getId(), null,
				nameMap, descriptionMap, serviceContext);

		_dlFileEntryTypeLocalService.addDDMStructureLinks(
			fileEntryType.getFileEntryTypeId(),
			SetUtil.fromArray(ddmStructureIds));
	}

	private void _deleteFileEntryType(ActionRequest actionRequest)
		throws Exception {

		try {
			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long fileEntryTypeId = ParamUtil.getLong(
				actionRequest, "fileEntryTypeId");

			DLFileEntryType fileEntryType =
				_dlFileEntryTypeService.getFileEntryType(fileEntryTypeId);

			DataDefinitionResource dataDefinitionResource =
				DataDefinitionResource.builder(
				).user(
					themeDisplay.getUser()
				).build();

			dataDefinitionResource.deleteDataDefinition(
				fileEntryType.getDataDefinitionId());

			_dlFileEntryTypeService.deleteFileEntryType(fileEntryTypeId);

			_dlFileEntryTypeLocalService.updateDDMStructureLinks(
				fileEntryTypeId, Collections.emptySet());
		}
		catch (RequiredStructureException requiredStructureException) {
			throw new RequiredFileEntryTypeException(
				requiredStructureException);
		}
	}

	private long[] _getLongArray(PortletRequest portletRequest, String name) {
		String value = portletRequest.getParameter(name);

		if (value == null) {
			return null;
		}

		return StringUtil.split(GetterUtil.getString(value), 0L);
	}

	private void _subscribeFileEntryType(ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fileEntryTypeId = ParamUtil.getLong(
			actionRequest, "fileEntryTypeId");

		_dlAppService.subscribeFileEntryType(
			themeDisplay.getScopeGroupId(), fileEntryTypeId);
	}

	private void _unsubscribeFileEntryType(ActionRequest actionRequest)
		throws PortalException {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fileEntryTypeId = ParamUtil.getLong(
			actionRequest, "fileEntryTypeId");

		_dlAppService.unsubscribeFileEntryType(
			themeDisplay.getScopeGroupId(), fileEntryTypeId);
	}

	private void _updateFileEntryType(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fileEntryTypeId = ParamUtil.getLong(
			actionRequest, "fileEntryTypeId");

		DataDefinitionResource dataDefinitionResource =
			DataDefinitionResource.builder(
			).user(
				themeDisplay.getUser()
			).build();

		DataDefinition dataDefinition = DataDefinition.toDTO(
			ParamUtil.getString(actionRequest, "dataDefinition"));

		long[] ddmStructureIds = _getLongArray(
			actionRequest, "ddmStructuresSearchContainerPrimaryKeys");

		if (ArrayUtil.isEmpty(dataDefinition.getDataDefinitionFields()) &&
			ArrayUtil.isEmpty(ddmStructureIds)) {

			throw new DataDefinitionValidationException.MustSetFields();
		}

		dataDefinition.setDefaultDataLayout(
			DataLayout.toDTO(ParamUtil.getString(actionRequest, "dataLayout")));

		dataDefinitionResource.putDataDefinition(
			ParamUtil.getLong(actionRequest, "dataDefinitionId"),
			dataDefinition);

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");

		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		_dlFileEntryTypeService.updateFileEntryType(
			fileEntryTypeId, nameMap, descriptionMap);

		if (ddmStructureIds != null) {
			_dlFileEntryTypeLocalService.updateDDMStructureLinks(
				fileEntryTypeId, SetUtil.fromArray(ddmStructureIds));
		}
	}

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@Reference
	private DLFileEntryTypeService _dlFileEntryTypeService;

	@Reference
	private Portal _portal;

}