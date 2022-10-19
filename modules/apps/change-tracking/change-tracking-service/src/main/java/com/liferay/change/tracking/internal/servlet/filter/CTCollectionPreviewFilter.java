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

package com.liferay.change.tracking.internal.servlet.filter;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.exception.NoSuchCollectionException;
import com.liferay.change.tracking.internal.CTCollectionPreviewThreadLocal;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.service.CTCollectionLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.HttpComponentsUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.servlet.filters.BasePortalFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Truong
 */
@Component(
	property = {
		"dispatcher=FORWARD", "dispatcher=REQUEST", "servlet-context-name=",
		"servlet-filter-name=CTCollection Preview Filter", "url-pattern=/*"
	},
	service = Filter.class
)
public class CTCollectionPreviewFilter extends BasePortalFilter {

	@Override
	protected void processFilter(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, FilterChain filterChain)
		throws Exception {

		long previewCTCollectionId = ParamUtil.getLong(
			httpServletRequest, "previewCTCollectionId", -1);

		if (previewCTCollectionId == -1) {
			processFilter(
				CTCollectionPreviewFilter.class.getName(), httpServletRequest,
				httpServletResponse, filterChain);

			return;
		}

		String mode = ParamUtil.getString(httpServletRequest, "p_l_mode");

		if (!mode.equals("preview")) {
			httpServletResponse.sendRedirect(
				HttpComponentsUtil.addParameter(
					_portal.getCurrentURL(httpServletRequest), "p_l_mode",
					Constants.PREVIEW));

			return;
		}

		if (previewCTCollectionId != CTConstants.CT_COLLECTION_ID_PRODUCTION) {
			CTCollection ctCollection =
				_ctCollectionLocalService.fetchCTCollection(
					previewCTCollectionId);

			if (ctCollection == null) {
				_portal.sendError(
					new NoSuchCollectionException(), httpServletRequest,
					httpServletResponse);

				return;
			}

			if (ctCollection.getStatus() != WorkflowConstants.STATUS_DRAFT) {
				_portal.sendError(
					new PortalException("Collection is not available"),
					httpServletRequest, httpServletResponse);

				return;
			}

			PermissionChecker permissionChecker =
				PermissionThreadLocal.getPermissionChecker();

			if (!_modelResourcePermission.contains(
					permissionChecker, ctCollection, ActionKeys.VIEW)) {

				_portal.sendError(
					new PrincipalException.MustHavePermission(
						permissionChecker, CTCollection.class.getName(),
						previewCTCollectionId, ActionKeys.VIEW),
					httpServletRequest, httpServletResponse);

				return;
			}
		}

		CTCollectionPreviewThreadLocal.setCTCollectionId(previewCTCollectionId);

		processFilter(
			CTCollectionPreviewFilter.class.getName(), httpServletRequest,
			httpServletResponse, filterChain);
	}

	@Reference
	private CTCollectionLocalService _ctCollectionLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.change.tracking.model.CTCollection)"
	)
	private ModelResourcePermission<CTCollection> _modelResourcePermission;

	@Reference
	private Portal _portal;

}