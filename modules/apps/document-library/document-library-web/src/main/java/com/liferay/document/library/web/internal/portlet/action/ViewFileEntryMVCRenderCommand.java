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

import com.liferay.asset.display.page.portlet.AssetDisplayPageFriendlyURLProvider;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.constants.DLPortletKeys;
import com.liferay.document.library.display.context.DLDisplayContextProvider;
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFileVersionException;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.repository.authorization.capability.AuthorizationCapability;
import com.liferay.document.library.util.DLAssetHelper;
import com.liferay.document.library.web.internal.display.context.DLAdminDisplayContext;
import com.liferay.document.library.web.internal.display.context.DLAdminDisplayContextProvider;
import com.liferay.document.library.web.internal.display.context.DLViewFileEntryDisplayContext;
import com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.constants.MVCRenderConstants;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Html;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	property = {
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY,
		"javax.portlet.name=" + DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
		"javax.portlet.name=" + DLPortletKeys.MEDIA_GALLERY_DISPLAY,
		"mvc.command.name=/document_library/view_file_entry"
	},
	service = MVCRenderCommand.class
)
public class ViewFileEntryMVCRenderCommand
	extends BaseFileEntryMVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		try {
			long fileEntryId = ParamUtil.getLong(renderRequest, "fileEntryId");

			Repository fileEntryRepository =
				RepositoryProviderUtil.getFileEntryRepository(fileEntryId);

			if (fileEntryRepository.isCapabilityProvided(
					AuthorizationCapability.class)) {

				AuthorizationCapability authorizationCapability =
					fileEntryRepository.getCapability(
						AuthorizationCapability.class);

				authorizationCapability.authorize(
					renderRequest, renderResponse);

				if (authorizationCapability.hasCustomRedirectFlow(
						renderRequest, renderResponse)) {

					return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
				}
			}

			if (!StringUtil.equals(
					DLPortletKeys.DOCUMENT_LIBRARY_ADMIN,
					_portal.getPortletId(renderRequest))) {

				ThemeDisplay themeDisplay =
					(ThemeDisplay)renderRequest.getAttribute(
						WebKeys.THEME_DISPLAY);

				String assetDisplayPageFriendlyURL =
					_assetDisplayPageFriendlyURLProvider.getFriendlyURL(
						FileEntry.class.getName(), fileEntryId, themeDisplay);

				if (assetDisplayPageFriendlyURL != null) {
					HttpServletResponse httpServletResponse =
						_portal.getHttpServletResponse(renderResponse);

					httpServletResponse.sendRedirect(
						assetDisplayPageFriendlyURL);

					return MVCRenderConstants.MVC_PATH_VALUE_SKIP_DISPATCH;
				}
			}

			return super.render(renderRequest, renderResponse);
		}
		catch (NoSuchFileEntryException | NoSuchFileVersionException |
			   NoSuchRepositoryEntryException | PrincipalException exception) {

			SessionErrors.add(renderRequest, exception.getClass());

			return "/document_library/error.jsp";
		}
		catch (IOException | PortalException exception) {
			throw new PortletException(exception);
		}
	}

	@Override
	protected String getPath() {
		return "/document_library/view_file_entry.jsp";
	}

	@Override
	protected void setAttributes(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortalException {

		super.setAttributes(renderRequest, renderResponse);

		DLAdminDisplayContext dlAdminDisplayContext =
			_dlAdminDisplayContextProvider.getDLAdminDisplayContext(
				_portal.getHttpServletRequest(renderRequest),
				_portal.getHttpServletResponse(renderResponse));

		DLViewFileEntryDisplayContext dlViewFileEntryDisplayContext =
			new DLViewFileEntryDisplayContext(
				dlAdminDisplayContext, _dlDisplayContextProvider, _html,
				_portal.getHttpServletRequest(renderRequest), _language,
				_portal, renderRequest, renderResponse);

		renderRequest.setAttribute(
			DLViewFileEntryDisplayContext.class.getName(),
			dlViewFileEntryDisplayContext);

		AssetEntry layoutAssetEntry = _assetEntryLocalService.fetchEntry(
			DLFileEntryConstants.getClassName(),
			_dlAssetHelper.getAssetClassPK(
				dlViewFileEntryDisplayContext.getFileEntry(),
				dlViewFileEntryDisplayContext.getFileVersion()));

		renderRequest.setAttribute(
			WebKeys.LAYOUT_ASSET_ENTRY, layoutAssetEntry);
	}

	@Reference
	private AssetDisplayPageFriendlyURLProvider
		_assetDisplayPageFriendlyURLProvider;

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private DLAdminDisplayContextProvider _dlAdminDisplayContextProvider;

	@Reference
	private DLAssetHelper _dlAssetHelper;

	@Reference
	private DLDisplayContextProvider _dlDisplayContextProvider;

	@Reference
	private Html _html;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}