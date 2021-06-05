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

package com.liferay.change.tracking.web.internal.portlet.action;

import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.web.internal.constants.CTWebKeys;
import com.liferay.change.tracking.web.internal.display.context.ReschedulePublicationDisplayContext;
import com.liferay.change.tracking.web.internal.scheduler.PublishScheduler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/reschedule_publication"
	},
	service = MVCRenderCommand.class
)
public class ReschedulePublicationMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		try {
			long ctCollectionId = ParamUtil.getLong(
				renderRequest, "ctCollectionId");

			CTCollection ctCollection =
				_ctCollectionLocalService.getCTCollection(ctCollectionId);

			_ctCollectionModelResourcePermission.check(
				themeDisplay.getPermissionChecker(), ctCollection,
				ActionKeys.VIEW);

			ReschedulePublicationDisplayContext
				reschedulePublicationDisplayContext =
					new ReschedulePublicationDisplayContext(
						ctCollection, _language, _portal, renderRequest,
						renderResponse,
						_publishScheduler.getScheduledPublishInfo(
							ctCollection));

			renderRequest.setAttribute(
				CTWebKeys.RESCHEDULE_PUBLICATION_DISPLAY_CONTEXT,
				reschedulePublicationDisplayContext);

			return "/publications/reschedule_publication.jsp";
		}
		catch (PortalException portalException) {
			throw new PortletException(portalException);
		}
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.change.tracking.model.CTCollection)"
	)
	private ModelResourcePermission<CTCollection>
		_ctCollectionModelResourcePermission;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private PublishScheduler _publishScheduler;

}