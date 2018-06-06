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

package com.liferay.fragment.web.internal.portlet.action;

import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.fragment.constants.FragmentPortletKeys;
import com.liferay.fragment.exception.FragmentEntryContentException;
import com.liferay.fragment.model.FragmentEntry;
import com.liferay.fragment.service.FragmentEntryLocalService;
import com.liferay.fragment.service.FragmentEntryService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portlet.PortletURLFactoryUtil;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Base64;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletURL;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + FragmentPortletKeys.FRAGMENT,
		"mvc.command.name=/fragment/edit_fragment_entry"
	},
	service = MVCActionCommand.class
)
public class EditFragmentEntryMVCActionCommand extends BaseMVCActionCommand {

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		long fragmentEntryId = ParamUtil.getLong(
			actionRequest, "fragmentEntryId");

		String name = ParamUtil.getString(actionRequest, "name");
		String css = ParamUtil.getString(actionRequest, "cssContent");
		String js = ParamUtil.getString(actionRequest, "jsContent");
		String html = ParamUtil.getString(actionRequest, "htmlContent");
		long previewFileEntryId = ParamUtil.getLong(
			actionRequest, "previewFileEntryId");
		String previewBase64 = ParamUtil.getString(
			actionRequest, "previewBase64");
		int status = ParamUtil.getInteger(actionRequest, "status");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		try {
			if ((previewFileEntryId <= 0) &&
				Validator.isNotNull(previewBase64)) {

				FileEntry fileEntry = _updatePreviewFileEntry(
					serviceContext.getUserId(),
					serviceContext.getScopeGroupId(), fragmentEntryId,
					previewBase64);

				previewFileEntryId = fileEntry.getFileEntryId();
			}

			FragmentEntry fragmentEntry =
				_fragmentEntryService.updateFragmentEntry(
					fragmentEntryId, name, css, html, js, previewFileEntryId,
					status);

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (status == WorkflowConstants.ACTION_SAVE_DRAFT) {
				redirect = _getSaveAndContinueRedirect(
					actionRequest, fragmentEntry);
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (FragmentEntryContentException fece) {
			hideDefaultErrorMessage(actionRequest);

			actionResponse.setRenderParameter(
				"mvcRenderCommandName", "/fragment/edit_fragment_entry");
			actionResponse.setRenderParameter(
				"fragmentEntryId", String.valueOf(fragmentEntryId));
			actionResponse.setRenderParameter("cssContent", css);
			actionResponse.setRenderParameter("jsContent", js);
			actionResponse.setRenderParameter("htmlContent", html);

			SessionErrors.add(actionRequest, fece.getClass(), fece);
		}
	}

	private String _getSaveAndContinueRedirect(
		ActionRequest actionRequest, FragmentEntry fragmentEntry) {

		PortletURL portletURL = PortletURLFactoryUtil.create(
			actionRequest, FragmentPortletKeys.FRAGMENT,
			PortletRequest.RENDER_PHASE);

		portletURL.setParameter(
			"mvcRenderCommandName", "/fragment/edit_fragment_entry");
		portletURL.setParameter(
			"fragmentCollectionId",
			String.valueOf(fragmentEntry.getFragmentCollectionId()));
		portletURL.setParameter(
			"fragmentEntryId",
			String.valueOf(fragmentEntry.getFragmentEntryId()));

		return portletURL.toString();
	}

	private FileEntry _updatePreviewFileEntry(
			long userId, long groupId, long fragmentEntryId,
			String previewBase64)
		throws PortalException {

		String fileName = fragmentEntryId + "_thumbnail.png";

		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				groupId, FragmentPortletKeys.FRAGMENT);

		if (repository != null) {
			FileEntry fileEntry =
				PortletFileRepositoryUtil.fetchPortletFileEntry(
					groupId, repository.getDlFolderId(), fileName);

			if (fileEntry != null) {
				PortletFileRepositoryUtil.deletePortletFileEntry(
					fileEntry.getFileEntryId());
			}
		}

		return PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, userId, FragmentEntry.class.getName(), fragmentEntryId,
			FragmentPortletKeys.FRAGMENT,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			Base64.decode(previewBase64), fileName, ContentTypes.IMAGE_PNG,
			false);
	}

	@Reference
	private FragmentEntryLocalService _fragmentEntryLocalService;

	@Reference
	private FragmentEntryService _fragmentEntryService;

}