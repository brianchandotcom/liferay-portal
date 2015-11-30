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

package com.liferay.document.library.web.portlet.action;

import com.liferay.document.library.web.constants.DLPortletKeys;
import com.liferay.dynamic.data.mapping.util.DDM;
import com.liferay.dynamic.data.mapping.util.DDMBeanTranslator;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalService;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileEntryTypeException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;
import com.liferay.portlet.documentlibrary.NoSuchMetadataSetException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLAppService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeService;
import com.liferay.portlet.dynamicdatamapping.DDMForm;
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.dynamicdatamapping.RequiredStructureException;
import com.liferay.portlet.dynamicdatamapping.StructureDefinitionException;
import com.liferay.portlet.dynamicdatamapping.StructureDuplicateElementException;
import com.liferay.portlet.dynamicdatamapping.StructureNameException;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;

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
	service = MVCActionCommand.class
)
public class EditFileEntryTypeMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteFileEntryType(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fileEntryTypeId = ParamUtil.getLong(
			actionRequest, "fileEntryTypeId");

		_dlFileEntryTypeService.deleteFileEntryType(fileEntryTypeId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateFileEntryType(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteFileEntryType(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribeFileEntryType(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribeFileEntryType(actionRequest);
			}

			if (SessionErrors.isEmpty(actionRequest)) {
				SessionMessages.add(
					actionRequest,
					PortalUtil.getPortletId(actionRequest) +
						SessionMessages.KEY_SUFFIX_REFRESH_PORTLET,
					DLPortletKeys.DOCUMENT_LIBRARY);

				String redirect = PortalUtil.escapeRedirect(
					ParamUtil.getString(actionRequest, "redirect"));

				if (Validator.isNotNull(redirect)) {
					sendRedirect(actionRequest, actionResponse, redirect);
				}
			}
		}
		catch (Exception e) {
			if (e instanceof DuplicateFileEntryTypeException ||
				e instanceof NoSuchMetadataSetException ||
				e instanceof StructureDefinitionException ||
				e instanceof StructureDuplicateElementException ||
				e instanceof StructureNameException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof NoSuchFileEntryTypeException ||
					 e instanceof NoSuchStructureException ||
					 e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter(
					"mvcPath", "/document_library/error.jsp");
			}
			else if (e instanceof RequiredStructureException) {
				SessionErrors.add(actionRequest, e.getClass());
			}
			else {
				throw e;
			}
		}
	}

	protected long[] getLongArray(PortletRequest portletRequest, String name) {
		String value = portletRequest.getParameter(name);

		if (value == null) {
			return null;
		}

		return StringUtil.split(GetterUtil.getString(value), 0L);
	}

	@Reference(unbind = "-")
	protected void setDDM(DDM ddm) {
		_ddm = ddm;
	}

	@Reference(unbind = "-")
	protected void setDDMBeanTranslator(DDMBeanTranslator ddmBeanTranslator) {
		_ddmBeanTranslator = ddmBeanTranslator;
	}

	@Reference(unbind = "-")
	protected void setDLAppService(DLAppService dlAppService) {
		_dlAppService = dlAppService;
	}

	@Reference(unbind = "-")
	protected void setDLFileEntryTypeService(
		DLFileEntryTypeService dlFileEntryTypeService) {

		_dlFileEntryTypeService = dlFileEntryTypeService;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	protected void subscribeFileEntryType(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fileEntryTypeId = ParamUtil.getLong(
			actionRequest, "fileEntryTypeId");

		_dlAppService.subscribeFileEntryType(
			themeDisplay.getScopeGroupId(), fileEntryTypeId);
	}

	protected void unsubscribeFileEntryType(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fileEntryTypeId = ParamUtil.getLong(
			actionRequest, "fileEntryTypeId");

		_dlAppService.unsubscribeFileEntryType(
			themeDisplay.getScopeGroupId(), fileEntryTypeId);
	}

	protected void updateFileEntryType(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fileEntryTypeId = ParamUtil.getLong(
			actionRequest, "fileEntryTypeId");

		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");

		long[] ddmStructureIds = getLongArray(
			actionRequest, "ddmStructuresSearchContainerPrimaryKeys");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntryType.class.getName(), actionRequest);

		DDMForm ddmForm = _ddmBeanTranslator.translate(
			_ddm.getDDMForm(actionRequest));

		serviceContext.setAttribute("ddmForm", ddmForm);

		if (fileEntryTypeId <= 0) {

			// Add file entry type

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

	private volatile DDM _ddm;
	private volatile DDMBeanTranslator _ddmBeanTranslator;
	private volatile DLAppService _dlAppService;
	private volatile DLFileEntryTypeService _dlFileEntryTypeService;
	private volatile GroupLocalService _groupLocalService;

}