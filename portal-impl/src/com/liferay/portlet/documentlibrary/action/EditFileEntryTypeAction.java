/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.portlet.LiferayPortletConfig;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.documentlibrary.DuplicateFileEntryTypeException;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryTypeException;
import com.liferay.portlet.documentlibrary.NoSuchMetadataSetException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryMetadata;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeServiceUtil;
import com.liferay.portlet.documentlibrary.util.DLUtil;
import com.liferay.portlet.dynamicdatamapping.NoSuchStructureException;
import com.liferay.portlet.dynamicdatamapping.RequiredStructureException;
import com.liferay.portlet.dynamicdatamapping.StructureDuplicateElementException;
import com.liferay.portlet.dynamicdatamapping.StructureNameException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.dynamicdatamapping.service.DDMStructureLocalServiceUtil;

import java.util.Locale;
import java.util.Map;

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
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
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
				LiferayPortletConfig liferayPortletConfig =
					(LiferayPortletConfig)portletConfig;

				SessionMessages.add(
					actionRequest,
					liferayPortletConfig.getPortletId() +
						SessionMessages.KEY_SUFFIX_REFRESH_PORTLET,
					PortletKeys.DOCUMENT_LIBRARY);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof DuplicateFileEntryTypeException ||
				e instanceof NoSuchMetadataSetException ||
				e instanceof StructureDuplicateElementException ||
				e instanceof StructureNameException) {

				SessionErrors.add(actionRequest, e.getClass());
			}
			else if (e instanceof NoSuchFileEntryTypeException ||
					 e instanceof NoSuchStructureException ||
					 e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass());

				setForward(actionRequest, "portlet.document_library.error");
			}
			else if (e instanceof RequiredStructureException) {
				SessionErrors.add(actionRequest, e.getClass());

				sendRedirect(actionRequest, actionResponse);
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		DLFileEntryType dlFileEntryType = null;

		try {
			long fileEntryTypeId = ParamUtil.getLong(
				renderRequest, "fileEntryTypeId");

			if (fileEntryTypeId > 0) {
				dlFileEntryType = DLFileEntryTypeServiceUtil.getFileEntryType(
					fileEntryTypeId);

				renderRequest.setAttribute(
					WebKeys.DOCUMENT_LIBRARY_FILE_ENTRY_TYPE, dlFileEntryType);

				DDMStructure ddmStructure =
					DDMStructureLocalServiceUtil.fetchStructure(
						dlFileEntryType.getGroupId(),
						PortalUtil.getClassNameId(DLFileEntryMetadata.class),
						DLUtil.getDDMStructureKey(dlFileEntryType));

				if (ddmStructure == null) {
					ddmStructure = DDMStructureLocalServiceUtil.fetchStructure(
						dlFileEntryType.getGroupId(),
						PortalUtil.getClassNameId(DLFileEntryMetadata.class),
						DLUtil.getDeprecatedDDMStructureKey(dlFileEntryType));
				}

				renderRequest.setAttribute(
					WebKeys.DYNAMIC_DATA_MAPPING_STRUCTURE, ddmStructure);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchFileEntryTypeException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward(
					"portlet.document_library.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
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

	protected void subscribeFileEntryType(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fileEntryTypeId = ParamUtil.getLong(
			actionRequest, "fileEntryTypeId");

		DLAppServiceUtil.subscribeFileEntryType(
			themeDisplay.getScopeGroupId(), fileEntryTypeId);
	}

	protected void unsubscribeFileEntryType(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long fileEntryTypeId = ParamUtil.getLong(
			actionRequest, "fileEntryTypeId");

		DLAppServiceUtil.unsubscribeFileEntryType(
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

		if (fileEntryTypeId <= 0) {

			// Add file entry type

			long groupId = themeDisplay.getScopeGroupId();

			Group scopeGroup = GroupLocalServiceUtil.getGroup(groupId);

			if (scopeGroup.isLayout()) {
				groupId = scopeGroup.getParentGroupId();
			}

			DLFileEntryTypeServiceUtil.addFileEntryType(
				groupId, null, nameMap, descriptionMap, ddmStructureIds,
				serviceContext);
		}
		else {

			// Update file entry type

			DLFileEntryTypeServiceUtil.updateFileEntryType(
				fileEntryTypeId, nameMap, descriptionMap, ddmStructureIds,
				serviceContext);
		}
	}

}