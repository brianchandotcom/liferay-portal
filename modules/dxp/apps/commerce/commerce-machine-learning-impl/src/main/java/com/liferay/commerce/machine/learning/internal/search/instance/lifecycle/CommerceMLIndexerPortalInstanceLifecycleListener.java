/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.machine.learning.internal.search.instance.lifecycle;

import com.liferay.commerce.machine.learning.internal.search.api.CommerceMLIndexer;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.CompanyLocalService;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Marco Leo
 */
@Component(service = PortalInstanceLifecycleListener.class)
public class CommerceMLIndexerPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
		try {
			for (CommerceMLIndexer commerceMLIndexer : _serviceTrackerList) {
				commerceMLIndexer.createIndex(company.getCompanyId());
			}
		}
		catch (Exception exception) {
			_log.error(
				"Unable to add commerce recommend index for company " + company,
				exception);
		}
	}

	@Override
	public void portalInstanceUnregistered(Company company) throws Exception {
		try {
			for (CommerceMLIndexer commerceMLIndexer : _serviceTrackerList) {
				commerceMLIndexer.dropIndex(company.getCompanyId());
			}
		}
		catch (Exception exception) {
			_log.error(
				"Unable to remove commerce recommend index for company " +
					company,
				exception);
		}
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerList = ServiceTrackerListFactory.open(
			bundleContext, CommerceMLIndexer.class, null,
			new ServiceTrackerCustomizer
				<CommerceMLIndexer, CommerceMLIndexer>() {

				@Override
				public CommerceMLIndexer addingService(
					ServiceReference<CommerceMLIndexer> serviceReference) {

					CommerceMLIndexer commerceMLIndexer =
						bundleContext.getService(serviceReference);

					_companyLocalService.forEachCompanyId(
						companyId -> commerceMLIndexer.createIndex(companyId));

					return commerceMLIndexer;
				}

				@Override
				public void modifiedService(
					ServiceReference<CommerceMLIndexer> serviceReference,
					CommerceMLIndexer commerceMLIndexer) {
				}

				@Override
				public void removedService(
					ServiceReference<CommerceMLIndexer> serviceReference,
					CommerceMLIndexer commerceMLIndexer) {

					bundleContext.ungetService(serviceReference);
				}

			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerList.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceMLIndexerPortalInstanceLifecycleListener.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	private ServiceTrackerList<CommerceMLIndexer> _serviceTrackerList;

}