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

package com.liferay.saml.opensaml.integration.internal.resolver;

import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.saml.opensaml.integration.resolver.NameIdResolver;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Mika Koivisto
 */
@Component(immediate = true, service = NameIdResolverRegistry.class)
public class NameIdResolverRegistry {

	public NameIdResolver getNameIdResolver(String entityId) {
		long companyId = CompanyThreadLocal.getCompanyId();

		NameIdResolver nameIdResolver = _nameIdResolvers.getService(
			companyId + "," + entityId);

		if (nameIdResolver == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No attribute resolver for company ID " + companyId +
						" and entity ID " + entityId);
			}

			nameIdResolver = _defaultNameIdResolver;
		}

		return nameIdResolver;
	}

	@Reference(
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(!(companyId=*))", unbind = "-"
	)
	public void setDefaultNameIdResolver(NameIdResolver defaultNameIdResolver) {
		_defaultNameIdResolver = defaultNameIdResolver;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_nameIdResolvers = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, NameIdResolver.class, "(companyId=*)",
			new DefaultServiceReferenceMapper(_log));
	}

	@Deactivate
	protected void deactivate() {
		_nameIdResolvers.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		NameIdResolverRegistry.class);

	private NameIdResolver _defaultNameIdResolver;
	private ServiceTrackerMap<String, NameIdResolver> _nameIdResolvers;

}