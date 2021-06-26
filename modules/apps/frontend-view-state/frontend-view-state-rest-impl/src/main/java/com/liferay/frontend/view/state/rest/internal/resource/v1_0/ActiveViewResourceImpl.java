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

package com.liferay.frontend.view.state.rest.internal.resource.v1_0;

import com.liferay.frontend.view.state.model.FVSActiveEntry;
import com.liferay.frontend.view.state.model.FVSEntry;
import com.liferay.frontend.view.state.rest.resource.v1_0.ActiveViewResource;
import com.liferay.frontend.view.state.service.FVSActiveEntryLocalService;
import com.liferay.frontend.view.state.service.FVSEntryLocalService;
import com.liferay.portal.kernel.exception.NoSuchModelException;

import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Javier Gamarra
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/active-view.properties",
	scope = ServiceScope.PROTOTYPE, service = ActiveViewResource.class
)
public class ActiveViewResourceImpl extends BaseActiveViewResourceImpl {

	@Override
	public Object getActiveViewPageLayoutPortlet(
			String activeViewId, Long pageLayoutId, String portletId)
		throws Exception {

		FVSActiveEntry fvsActiveEntry =
			_fvsActiveEntryLocalService.fetchFVSActiveEntry(
				contextUser.getUserId(), activeViewId, pageLayoutId, portletId);

		if (fvsActiveEntry == null) {
			throw new NoSuchModelException();
		}

		FVSEntry fvsEntry = _fvsEntryLocalService.getFVSEntry(
			fvsActiveEntry.getFvsEntryId());

		return fvsEntry.getViewState();
	}

	@Override
	public Response putActiveViewPageLayoutPortlet(
			String activeViewId, Long pageLayoutId, String portletId,
			String string)
		throws Exception {

		FVSActiveEntry fvsActiveEntry =
			_fvsActiveEntryLocalService.fetchFVSActiveEntry(
				contextUser.getUserId(), activeViewId, pageLayoutId, portletId);

		FVSEntry fvsEntry = null;

		if (fvsActiveEntry == null) {
			fvsEntry = _fvsEntryLocalService.addFVSEntry(
				contextUser.getUserId(), string);

			fvsActiveEntry = _fvsActiveEntryLocalService.addFVSActiveEntry(
				contextUser.getUserId(), fvsEntry.getFvsEntryId(), activeViewId,
				pageLayoutId, portletId);
		}
		else {
			fvsEntry = _fvsEntryLocalService.getFVSEntry(
				fvsActiveEntry.getFvsEntryId());

			fvsEntry.setViewState(string);

			fvsEntry = _fvsEntryLocalService.updateFVSEntry(fvsEntry);
		}

		return Response.ok(
			fvsEntry.getViewState()
		).build();
	}

	@Reference
	private FVSActiveEntryLocalService _fvsActiveEntryLocalService;

	@Reference
	private FVSEntryLocalService _fvsEntryLocalService;

}