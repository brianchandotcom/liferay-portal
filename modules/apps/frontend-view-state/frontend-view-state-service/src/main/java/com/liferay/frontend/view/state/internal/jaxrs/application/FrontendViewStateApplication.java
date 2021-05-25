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

package com.liferay.frontend.view.state.internal.jaxrs.application;

import com.liferay.frontend.view.state.model.FVSActiveEntry;
import com.liferay.frontend.view.state.model.FVSEntry;
import com.liferay.frontend.view.state.service.FVSActiveEntryLocalService;
import com.liferay.frontend.view.state.service.FVSEntryLocalService;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.jaxrs.whiteboard.JaxrsWhiteboardConstants;

/**
 * @author Iván Zaera Avellón
 */
@Component(
	property = {
		JaxrsWhiteboardConstants.JAX_RS_APPLICATION_BASE + "=/frontend-view-state/app",
		JaxrsWhiteboardConstants.JAX_RS_NAME + "=Liferay.Frontend.View.State",
		"auth.verifier.auth.verifier.PortalSessionAuthVerifier.urls.includes=/*",
		"auth.verifier.guest.allowed=true", "liferay.oauth2=false"
	},
	service = Application.class
)
public class FrontendViewStateApplication {

	@GET
	@Path("/active-view/{clayDataSetDisplayId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getActiveView(
		@PathParam("clayDataSetDisplayId") String clayDataSetDisplayId,
		@QueryParam("plid") long plid,
		@QueryParam("portletId") String portletId, @Context User user) {

		try {
			FVSActiveEntry fvsActiveEntry =
				_fvsActiveEntryLocalService.fetchFVSActiveEntry(
					user.getUserId(), clayDataSetDisplayId, plid, portletId);

			if (fvsActiveEntry == null) {
				return Response.status(
					Response.Status.NOT_FOUND
				).build();
			}

			FVSEntry fvsEntry = _fvsEntryLocalService.getFVSEntry(
				fvsActiveEntry.getFvsEntryId());

			return Response.ok(
				_jsonFactory.createJSONObject(fvsEntry.getViewState()),
				MediaType.APPLICATION_JSON
			).build();
		}
		catch (Exception exception) {
			_log.error("Unable to get active view", exception);
		}

		return Response.status(
			Response.Status.NOT_FOUND
		).build();
	}

	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/active-view/{clayDataSetDisplayId}/save")
	@POST
	public Response saveActiveView(
		@PathParam("clayDataSetDisplayId") String clayDataSetDisplayId,
		@QueryParam("plid") long plid,
		@QueryParam("portletId") String portletId, @Context User user,
		String viewState) {

		try {
			FVSActiveEntry fvsActiveEntry =
				_fvsActiveEntryLocalService.fetchFVSActiveEntry(
					user.getUserId(), clayDataSetDisplayId, plid, portletId);

			if (fvsActiveEntry == null) {
				FVSEntry fvsEntry = _fvsEntryLocalService.addFVSEntry(
					user.getUserId(), viewState);

				_fvsActiveEntryLocalService.addFVSActiveEntry(
					user.getUserId(), fvsEntry.getFvsEntryId(),
					clayDataSetDisplayId, plid, portletId);
			}
			else {
				FVSEntry fvsEntry = _fvsEntryLocalService.getFVSEntry(
					fvsActiveEntry.getFvsEntryId());

				fvsEntry.setViewState(viewState);

				_fvsEntryLocalService.updateFVSEntry(fvsEntry);
			}

			return Response.ok(
			).build();
		}
		catch (Exception exception) {
			_log.error("Unable to save active view", exception);
		}

		return Response.status(
			Response.Status.NOT_FOUND
		).build();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		FrontendViewStateApplication.class);

	@Reference
	private FVSActiveEntryLocalService _fvsActiveEntryLocalService;

	@Reference
	private FVSEntryLocalService _fvsEntryLocalService;

	@Reference
	private JSONFactory _jsonFactory;

}