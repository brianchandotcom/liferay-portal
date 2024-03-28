/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.web.internal.portlet.tab;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.workflow.portlet.tab.WorkflowPortletTab;
import com.liferay.portal.workflow.portlet.tab.WorkflowPortletTabRegistry;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Feliphe Marinho
 */
@Component(service = WorkflowPortletTabRegistry.class)
public class WorkflowPortletTabRegistryImpl
	implements WorkflowPortletTabRegistry {

	@Override
	public boolean contains(String portalWorkflowTabsName) {
		return _serviceTrackerMap.containsKey(portalWorkflowTabsName);
	}

	@Override
	public WorkflowPortletTab getWorkflowPortletTab(
		String portalWorkflowTabsName) {

		return _serviceTrackerMap.getService(portalWorkflowTabsName);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, WorkflowPortletTab.class,
			"portal.workflow.tabs.name");
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, WorkflowPortletTab> _serviceTrackerMap;

}