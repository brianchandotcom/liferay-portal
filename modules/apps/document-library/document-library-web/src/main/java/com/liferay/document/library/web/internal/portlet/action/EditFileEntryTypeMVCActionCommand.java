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
import com.liferay.document.library.kernel.exception.DuplicateFileEntryTypeException;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryTypeException;
import com.liferay.document.library.kernel.exception.NoSuchMetadataSetException;
import com.liferay.document.library.kernel.exception.RequiredFileEntryTypeException;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLAppService;
import com.liferay.document.library.kernel.service.DLFileEntryTypeService;
import com.liferay.document.library.web.internal.configuration.FFDocumentLibraryDDMEditorConfiguration;
import com.liferay.dynamic.data.mapping.kernel.DDMForm;
import com.liferay.dynamic.data.mapping.kernel.NoSuchStructureException;
import com.liferay.dynamic.data.mapping.kernel.RequiredStructureException;
import com.liferay.dynamic.data.mapping.kernel.StructureDefinitionException;
import com.liferay.dynamic.data.mapping.kernel.StructureDuplicateElementException;
import com.liferay.dynamic.data.mapping.kernel.StructureNameException;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslator;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

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
	service = MVCActionCommand.class
)
public class EditFileEntryTypeMVCActionCommand extends BaseMVCActionCommand {

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_ffDocumentLibraryDDMEditorConfiguration =
			ConfigurableUtil.createConfigurable(
				FFDocumentLibraryDDMEditorConfiguration.class, properties);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws IOException, PortalException {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
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
					sendRedirect(actionRequest, actionResponse, redirect);
				}
			}
		}
		catch (DuplicateFileEntryTypeException | NoSuchMetadataSetException |
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

	private void _deleteFileEntryType(ActionRequest actionRequest)
		throws PortalException {

		long fileEntryTypeId = ParamUtil.getLong(
			actionRequest, "fileEntryTypeId");

		_dlFileEntryTypeService.deleteFileEntryType(fileEntryTypeId);
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
		throws PortalException {

		long fileEntryTypeId = ParamUtil.getLong(
			actionRequest, "fileEntryTypeId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		long[] ddmStructureIds = _getLongArray(
			actionRequest, "ddmStructuresSearchContainerPrimaryKeys");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntryType.class.getName(), actionRequest);

		DDMForm ddmForm = _ddmBeanTranslator.translate(
			_ddm.getDDMForm(actionRequest));

		serviceContext.setAttribute("ddmForm", ddmForm);

		serviceContext.setAttribute(
			"useDataEngineEditor",
			_ffDocumentLibraryDDMEditorConfiguration.useDataEngineEditor());

		if (fileEntryTypeId <= 0) {

			// Add file entry type

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			long groupId = themeDisplay.getScopeGroupId();

			Group scopeGroup = _groupLocalService.getGroup(groupId);

			if (scopeGroup.isLayout()) {
				groupId = scopeGroup.getParentGroupId();
			}

			_dlFileEntryTypeService.addFileEntryType(
				groupId, null, nameMap, descriptionMap, ddmStructureIds,
				serviceContext);
		}
		else {

			// Update file entry type

			_dlFileEntryTypeService.updateFileEntryType(
				fileEntryTypeId, nameMap, descriptionMap, ddmStructureIds,
				serviceContext);
		}
	}

	@Reference
	private DDM _ddm;

	@Reference
	private DDMBeanTranslator _ddmBeanTranslator;

	@Reference
	private DLAppService _dlAppService;

	@Reference
	private DLFileEntryTypeService _dlFileEntryTypeService;

	private volatile FFDocumentLibraryDDMEditorConfiguration
		_ffDocumentLibraryDDMEditorConfiguration;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

}