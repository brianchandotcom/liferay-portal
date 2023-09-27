/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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