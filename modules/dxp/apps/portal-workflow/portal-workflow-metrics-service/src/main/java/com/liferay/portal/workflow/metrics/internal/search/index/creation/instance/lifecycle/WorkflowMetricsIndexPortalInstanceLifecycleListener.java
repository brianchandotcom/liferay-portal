/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.search.index.creation.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.workflow.metrics.internal.search.index.creation.helper.WorkflowMetricsIndexCreator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class WorkflowMetricsIndexPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstancePreregistered(Company company) throws Exception {
		_workflowMetricsIndexCreator.createIndex(company);
	}

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		_workflowMetricsIndexCreator.reindex(company);
	}

	@Override
	public void portalInstanceUnregistered(Company company) throws Exception {
		_workflowMetricsIndexCreator.removeIndex(company);
	}

	@Reference
	private WorkflowMetricsIndexCreator _workflowMetricsIndexCreator;

}