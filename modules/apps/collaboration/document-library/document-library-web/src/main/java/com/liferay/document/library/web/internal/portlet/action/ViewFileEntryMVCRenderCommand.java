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
import com.liferay.document.library.kernel.exception.NoSuchFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFileVersionException;
import com.liferay.document.library.repository.authorization.capability.AuthorizationCapability;
import com.liferay.portal.kernel.exception.NoSuchRepositoryEntryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderConstants;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;

import java.io.IOException;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

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
	extends GetFileEntryMVCRenderCommand {

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
		}
		catch (NoSuchFileEntryException | NoSuchFileVersionException |
			   NoSuchRepositoryEntryException | PrincipalException e) {

			SessionErrors.add(renderRequest, e.getClass());

			return "/document_library/error.jsp";
		}
		catch (IOException | PortalException e) {
			throw new PortletException(e);
		}

		return super.render(renderRequest, renderResponse);
	}

	@Override
	protected String getPath() {
		return "/document_library/view_file_entry.jsp";
	}

}