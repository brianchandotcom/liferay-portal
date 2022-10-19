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

import com.liferay.change.tracking.conflict.ConflictInfo;
import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.constants.CTPortletKeys;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.model.CTPreferences;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.change.tracking.service.CTEntryLocalService;
import com.liferay.change.tracking.service.CTPreferencesLocalService;
import com.liferay.change.tracking.web.internal.constants.CTWebKeys;
import com.liferay.change.tracking.web.internal.display.CTDisplayRendererRegistry;
import com.liferay.change.tracking.web.internal.display.context.ViewConflictsDisplayContext;
import com.liferay.portal.kernel.dao.orm.ORMException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import java.sql.SQLException;

import java.util.List;
import java.util.Map;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Samuel Trong Tran
 */
@Component(
	property = {
		"javax.portlet.name=" + CTPortletKeys.PUBLICATIONS,
		"mvc.command.name=/change_tracking/view_conflicts"
	},
	service = MVCRenderCommand.class
)
public class ViewConflictsMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws PortletException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		CTPreferences ctPreferences =
			_ctPreferencesLocalService.fetchCTPreferences(
				themeDisplay.getCompanyId(), themeDisplay.getUserId());

		long activeCtCollectionId = CTConstants.CT_COLLECTION_ID_PRODUCTION;

		if (ctPreferences != null) {
			activeCtCollectionId = ctPreferences.getCtCollectionId();
		}

		long ctCollectionId = ParamUtil.getLong(
			renderRequest, "ctCollectionId");

		try {
			CTCollection ctCollection =
				_ctCollectionLocalService.getCTCollection(ctCollectionId);

			Map<Long, List<ConflictInfo>> conflictInfoMap = null;

			boolean hasUnapprovedChanges =
				_ctCollectionLocalService.hasUnapprovedChanges(ctCollectionId);

			if (!hasUnapprovedChanges) {
				conflictInfoMap = _ctCollectionLocalService.checkConflicts(
					ctCollection);
			}

			renderRequest.setAttribute(
				CTWebKeys.VIEW_CONFLICTS_DISPLAY_CONTEXT,
				new ViewConflictsDisplayContext(
					activeCtCollectionId, conflictInfoMap, ctCollection,
					_ctDisplayRendererRegistry, _ctEntryLocalService,
					hasUnapprovedChanges, _language, _portal, renderRequest,
					renderResponse));

			return "/publications/view_conflicts.jsp";
		}
		catch (PortalException portalException) {
			throw new PortletException(portalException);
		}
		catch (SQLException sqlException) {
			throw new ORMException(sqlException);
		}
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference
	private CTDisplayRendererRegistry _ctDisplayRendererRegistry;

	@Reference
	private CTEntryLocalService _ctEntryLocalService;

	@Reference
	private CTPreferencesLocalService _ctPreferencesLocalService;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}