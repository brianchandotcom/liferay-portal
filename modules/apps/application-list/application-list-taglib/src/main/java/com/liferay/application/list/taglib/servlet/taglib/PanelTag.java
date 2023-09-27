/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.application.list.taglib.servlet.taglib;

import com.liferay.application.list.PanelCategory;
import com.liferay.application.list.PanelCategoryRegistry;
import com.liferay.application.list.RootPanelCategory;
import com.liferay.application.list.constants.ApplicationListWebKeys;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * @author Adolfo Pérez
 */
public class PanelTag extends BasePanelTag {

	@Override
	public int doEndTag() throws JspException {
		if (ListUtil.isEmpty(_getChildPanelCategories(getRequest()))) {
			doClearTag();

			return EVAL_PAGE;
		}

		return super.doEndTag();
	}

	@Override
	public int doStartTag() throws JspException {
		return EVAL_BODY_INCLUDE;
	}

	public PanelCategory getPanelCategory() {
		return _panelCategory;
	}

	public void setPanelCategory(PanelCategory panelCategory) {
		_panelCategory = panelCategory;
	}

	@Override
	protected void cleanUp() {
		super.cleanUp();

		_panelCategory = null;
	}

	@Override
	protected String getPage() {
		return "/panel/page.jsp";
	}

	@Override
	protected void setAttributes(HttpServletRequest httpServletRequest) {
		httpServletRequest.setAttribute(
			"liferay-application-list:panel:childPanelCategories",
			_getChildPanelCategories(httpServletRequest));
		httpServletRequest.setAttribute(
			"liferay-application-list:panel:panelCategory",
			_getPanelCategory());
	}

	private List<PanelCategory> _getChildPanelCategories(
		HttpServletRequest httpServletRequest) {

		PanelCategoryRegistry panelCategoryRegistry =
			(PanelCategoryRegistry)httpServletRequest.getAttribute(
				ApplicationListWebKeys.PANEL_CATEGORY_REGISTRY);

		ThemeDisplay themeDisplay =
			(ThemeDisplay)httpServletRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		return panelCategoryRegistry.getChildPanelCategories(
			_getPanelCategory(), themeDisplay.getPermissionChecker(),
			getGroup());
	}

	private PanelCategory _getPanelCategory() {
		if (_panelCategory == null) {
			_panelCategory = RootPanelCategory.getInstance();
		}

		return _panelCategory;
	}

	private PanelCategory _panelCategory;

}