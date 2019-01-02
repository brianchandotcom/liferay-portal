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

import com.liferay.bulk.selection.BulkSelection;
import com.liferay.bulk.selection.BulkSelectionFactory;
import com.liferay.bulk.selection.BulkSelectionRunner;
import com.liferay.document.library.bulk.selection.EditTagsBulkSelectionAction;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.Serializable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/edit_tags"
	},
	service = MVCActionCommand.class
)
public class EditTagsMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws PortalException {

		try {
			Set<String> commonTagNamesSet = SetUtil.fromArray(
				ParamUtil.getStringValues(actionRequest, "commonTagNames"));

			ServiceContext serviceContext = ServiceContextFactory.getInstance(
				actionRequest);

			String[] toAddTagNames = serviceContext.getAssetTagNames();

			commonTagNamesSet.removeAll(Arrays.asList(toAddTagNames));

			String[] toRemoveTagNames = commonTagNamesSet.toArray(
				new String[commonTagNamesSet.size()]);

			ThemeDisplay themeDisplay =
				(ThemeDisplay)actionRequest.getAttribute(WebKeys.THEME_DISPLAY);

			BulkSelection<FileEntry> bulkSelection =
				_bulkSelectionFactory.create(actionRequest.getParameterMap());

			boolean append = ParamUtil.getBoolean(actionRequest, "append");

			_bulkSelectionRunner.run(
				bulkSelection, _editTagsBulkSelectionAction,
				new HashMap<String, Serializable>() {
					{
						put("append", append);
						put("toAddTagNames", toAddTagNames);
						put("toRemoveTagNames", toRemoveTagNames);
						put("userId", themeDisplay.getUserId());
					}
				});

			String successMessage = LanguageUtil.get(
				_portal.getHttpServletRequest(actionRequest), "changes-saved");

			SessionMessages.add(
				actionRequest, "requestProcessed", successMessage);
		}
		catch (PortalException pe) {
			throw pe;
		}
		catch (Throwable throwable) {
			throw new PortalException(throwable);
		}
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.kernel.repository.model.FileEntry)"
	)
	private BulkSelectionFactory<FileEntry> _bulkSelectionFactory;

	@Reference
	private BulkSelectionRunner _bulkSelectionRunner;

	@Reference
	private EditTagsBulkSelectionAction _editTagsBulkSelectionAction;

	@Reference
	private Portal _portal;

}