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

package com.liferay.portal.workflow.web.internal.display.context;

import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItem;
import com.liferay.frontend.taglib.clay.servlet.taglib.util.NavigationItemList;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.ResourceBundleLoader;
import com.liferay.portal.workflow.portlet.tab.WorkflowPortletTab;
import com.liferay.portal.workflow.web.internal.constants.WorkflowPortletKeys;
import com.liferay.portal.workflow.web.internal.display.context.util.WorkflowNavigationRequestHelper;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;

/**
 * @author Jeyvison Nascimento
 */
public class WorkflowNavigationDisplayContext {

	public WorkflowNavigationDisplayContext(
		RenderRequest renderRequest,
		ResourceBundleLoader resourceBundleLoader) {

		_workflowNavigationRequestHelper = new WorkflowNavigationRequestHelper(
			renderRequest);

		_resourceBundleLoader = resourceBundleLoader;
	}

	public List<NavigationItem> getNavigationItems(
		WorkflowPortletTab selectedWorkflowPortletTab,
		List<WorkflowPortletTab> workflowPortletTabs) {

		if (workflowPortletTabs.size() == 1) {
			return Collections.emptyList();
		}

		ResourceBundle resourceBundle =
			_resourceBundleLoader.loadResourceBundle(
				_workflowNavigationRequestHelper.getLocale());

		LiferayPortletResponse liferayPortletResponse =
			_workflowNavigationRequestHelper.getLiferayPortletResponse();

		PortletURL renderURL = liferayPortletResponse.createRenderURL(
			WorkflowPortletKeys.CONTROL_PANEL_WORKFLOW);

		return new NavigationItemList() {
			{
				for (WorkflowPortletTab workflowPortletTab :
						workflowPortletTabs) {

					add(
						navigationItem -> {
							navigationItem.setActive(
								Objects.equals(
									workflowPortletTab.getName(),
									selectedWorkflowPortletTab.getName()));
							navigationItem.setHref(
								renderURL, "tab", workflowPortletTab.getName());
							navigationItem.setLabel(
								LanguageUtil.get(
									resourceBundle,
									workflowPortletTab.getName()));
						});
				}
			}
		};
	}

	private final ResourceBundleLoader _resourceBundleLoader;
	private final WorkflowNavigationRequestHelper
		_workflowNavigationRequestHelper;

}