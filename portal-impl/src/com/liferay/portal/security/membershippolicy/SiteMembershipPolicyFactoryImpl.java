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

package com.liferay.portal.security.membershippolicy;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceReference;
import com.liferay.registry.ServiceTracker;
import com.liferay.registry.ServiceTrackerCustomizer;

/**
 * @author Sergio González
 * @author Shuyang Zhou
 * @author Roberto Díaz
 * @author Peter Fellwock
 */
public class SiteMembershipPolicyFactoryImpl
	implements SiteMembershipPolicyFactory {

	@Override
	public SiteMembershipPolicy getSiteMembershipPolicy() {
		return _instance._serviceTracker.getService();
	}

	private SiteMembershipPolicyFactoryImpl() {
		Registry registry = RegistryUtil.getRegistry();

		_serviceTracker = registry.trackServices(
			SiteMembershipPolicy.class, 
			new SiteMembershipPolicyTrackerCustomizer());

		_serviceTracker.open();
	}

	private static SiteMembershipPolicyFactoryImpl
	_instance = new SiteMembershipPolicyFactoryImpl();

	private ServiceTracker<?, SiteMembershipPolicy> _serviceTracker;

	private static Log _log = LogFactoryUtil.getLog(
		SiteMembershipPolicyFactoryImpl.class);
	
	private class SiteMembershipPolicyTrackerCustomizer
	implements ServiceTrackerCustomizer<SiteMembershipPolicy, 
		SiteMembershipPolicy> {

		@Override
		public SiteMembershipPolicy addingService(
			ServiceReference<SiteMembershipPolicy> serviceReference) {
			
			Boolean autoVerify  = (Boolean) serviceReference.getProperty(
				SiteMembershipPolicy.MEMBERSHIP_POLICY_AUTO_VERIFY);
			
			Registry registry = RegistryUtil.getRegistry();
	
			Object service = registry.getService(serviceReference);
	
			SiteMembershipPolicy siteMembershipPolicy =
				(SiteMembershipPolicy) service;
	
			if((autoVerify != null) && (autoVerify.booleanValue())){
				try {
					siteMembershipPolicy.verifyPolicy();
				}
				catch (PortalException e) {
					_log.error(
						"Customizer catches failure trying to verifyPolicy:",
						e);
					return null;
				}
			}
			
			return siteMembershipPolicy;
		}
	
		@Override
		public void modifiedService(
			ServiceReference<SiteMembershipPolicy> serviceReference, 
			SiteMembershipPolicy service) {
		}
	
		@Override
		public void removedService(
			ServiceReference<SiteMembershipPolicy> serviceReference, 
			SiteMembershipPolicy service) {		
		}

	}

}