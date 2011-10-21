/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.DuplicateFileEntryTypeException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;
import com.liferay.portlet.documentlibrary.NoSuchMetadataSetException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeServiceUtil;
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.dynamicdatamapping.StructureDuplicateElementException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Alexander Chow
 * @author Sergio González
 */
public class EditFileEntryTypeAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
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

			if (SessionErrors.isEmpty(actionRequest)) {
				SessionMessages.add(
					actionRequest,
					portletConfig.getPortletName() + ".doRefresh",
					PortletKeys.DOCUMENT_LIBRARY);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof DuplicateFileEntryTypeException ||
				e instanceof NoSuchMetadataSetException ||
				e instanceof StructureDuplicateElementException) {

				SessionErrors.add(actionRequest, e.getClass().getName());
			}
			else if (e instanceof NoSuchFileEntryTypeException ||
					 e instanceof NoSuchStructureException ||
					 e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.document_library.error");
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		DLFileEntryType fileEntryType = null;

		try {
			long fileEntryTypeId = ParamUtil.getLong(
				renderRequest, "fileEntryTypeId");

			if (fileEntryTypeId > 0) {
				fileEntryType = DLFileEntryTypeServiceUtil.getFileEntryType(
					fileEntryTypeId);

				renderRequest.setAttribute(
					WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY_TYPE, fileEntryType);

				DDMStructure ddmStructure =
					DDMStructureLocalServiceUtil.fetchStructure(
						fileEntryType.getGroupId(), "auto_" + fileEntryTypeId);

				renderRequest.setAttribute(
					WebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE, ddmStructure);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchFileEntryTypeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.document_library.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(
				renderRequest,
				"portlet.document_library.edit_file_entry_type"));
	}

	protected void deleteFileEntryType(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fileEntryTypeId = ParamUtil.getLong(
			actionRequest, "fileEntryTypeId");

		DLFileEntryTypeServiceUtil.deleteFileEntryType(fileEntryTypeId);
	}

	protected long[] getLongArray(PortletRequest portletRequest, String name) {
		String value = portletRequest.getParameter(name);

		if (value == null) {
			return null;
		}

		return StringUtil.split(GetterUtil.getString(value), 0L);
	}

	protected void updateFileEntryType(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fileEntryTypeId = ParamUtil.getLong(
			actionRequest, "fileEntryTypeId");

		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");
		long[] ddmStructureIds = getLongArray(
			actionRequest, "ddmStructuresSearchContainerPrimaryKeys");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			DLFileEntryType.class.getName(), actionRequest);

		if (fileEntryTypeId <= 0) {

			// Add file entry type

			long groupId = themeDisplay.getScopeGroupId();

			Group scopeGroup = GroupLocalServiceUtil.getGroup(groupId);

			if (scopeGroup.isLayout()) {
				groupId = scopeGroup.getParentGroupId();
			}

			DLFileEntryTypeServiceUtil.addFileEntryType(
				groupId, name, description, ddmStructureIds, serviceContext);
		}
		else {

			// Update file entry type

			DLFileEntryTypeServiceUtil.updateFileEntryType(
				fileEntryTypeId, name, description, ddmStructureIds,
				serviceContext);
		}
	}

}