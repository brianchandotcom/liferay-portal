/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.machine.learning.internal.search.instance.lifecycle;

import com.liferay.analytics.machine.learning.internal.search.api.RecommendationIndexer;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManager;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class RecommendationIndexerPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		if (_serviceTrackerList == null) {
			return;
		}

		try {
			for (RecommendationIndexer recommendationIndexer :
					_serviceTrackerList) {

				recommendationIndexer.createIndex(company.getCompanyId());
			}
		}
		catch (Exception exception) {
			_log.error(
				"Unable to add analytics recommendation index for company " +
					company,
				exception);
		}
	}

	@Override
	public void portalInstanceUnregistered(Company company) throws Exception {
		if (_serviceTrackerList == null) {
			return;
		}

		try {
			for (RecommendationIndexer recommendationIndexer :
					_serviceTrackerList) {

				recommendationIndexer.dropIndex(company.getCompanyId());
			}
		}
		catch (Exception exception) {
			_log.error(
				"Unable to remove analytics recommendation index for company " +
					company,
				exception);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		if (!_featureFlagManager.isEnabled("LRAC-14771")) {
			_serviceTrackerList = null;

			return;
		}

		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, RecommendationIndexer.class);
	}

	@Deactivate
	protected void deactivate() {
		if (_serviceTrackerList == null) {
			return;
		}

		_serviceTrackerList.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RecommendationIndexerPortalInstanceLifecycleListener.class);

	@Reference
	private FeatureFlagManager _featureFlagManager;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	private ServiceTrackerList<RecommendationIndexer> _serviceTrackerList;

}