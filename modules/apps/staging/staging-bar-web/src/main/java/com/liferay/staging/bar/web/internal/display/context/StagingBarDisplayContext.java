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

package com.liferay.staging.bar.web.internal.display.context;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.service.LayoutRevisionLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Jürgen Kappler
 */
public class StagingBarDisplayContext {

	public StagingBarDisplayContext(
		LiferayPortletRequest liferayPortletRequest, Layout layout) {

		_themeDisplay = (ThemeDisplay)liferayPortletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		_layout = layout;
	}

	public boolean isDraftLayout() {
		if (_draftLayout != null) {
			return _draftLayout;
		}

		if (!_layout.isTypeContent()) {
			_draftLayout = false;

			return _draftLayout;
		}

		boolean draftLayout = false;

		if ((_layout.getClassNameId() == PortalUtil.getClassNameId(
				Layout.class)) &&
			(_layout.getClassPK() > 0)) {

			draftLayout = true;
		}

		_draftLayout = draftLayout;

		return _draftLayout;
	}

	public LayoutRevision updateLayoutRevision(LayoutRevision layoutRevision) {
		if (!_layout.isTypeContent() || (layoutRevision == null) ||
			layoutRevision.isApproved() || isDraftLayout()) {

			return layoutRevision;
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setWorkflowAction(WorkflowConstants.ACTION_PUBLISH);

		try {
			layoutRevision =
				LayoutRevisionLocalServiceUtil.updateLayoutRevision(
					_themeDisplay.getUserId(),
					layoutRevision.getLayoutRevisionId(),
					layoutRevision.getLayoutBranchId(),
					layoutRevision.getName(), layoutRevision.getTitle(),
					layoutRevision.getDescription(),
					layoutRevision.getKeywords(), layoutRevision.getRobots(),
					layoutRevision.getTypeSettings(),
					layoutRevision.getIconImage(),
					layoutRevision.getIconImageId(),
					layoutRevision.getThemeId(),
					layoutRevision.getColorSchemeId(), layoutRevision.getCss(),
					serviceContext);
		}
		catch (PortalException portalException) {
			if (_log.isWarnEnabled()) {
				_log.warn(portalException);
			}
		}

		return layoutRevision;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingBarDisplayContext.class);

	private Boolean _draftLayout;
	private final Layout _layout;
	private final ThemeDisplay _themeDisplay;

}