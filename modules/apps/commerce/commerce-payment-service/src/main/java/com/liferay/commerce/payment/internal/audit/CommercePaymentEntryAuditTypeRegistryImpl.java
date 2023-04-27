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

package com.liferay.commerce.payment.internal.audit;

import com.liferay.commerce.payment.audit.CommercePaymentEntryAuditType;
import com.liferay.commerce.payment.audit.CommercePaymentEntryAuditTypeRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerCustomizerFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Luca Pellizzon
 */
@Component(service = CommercePaymentEntryAuditTypeRegistry.class)
public class CommercePaymentEntryAuditTypeRegistryImpl
	implements CommercePaymentEntryAuditTypeRegistry {

	@Override
	public CommercePaymentEntryAuditType getCommercePaymentEntryAuditType(
		String key) {

		ServiceTrackerCustomizerFactory.ServiceWrapper
			<CommercePaymentEntryAuditType>
				commercePaymentEntryAuditTypeServiceWrapper =
					_serviceTrackerMap.getService(key);

		if (commercePaymentEntryAuditTypeServiceWrapper == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No CommercePaymentEntryAuditType registered with key " +
						key);
			}

			return null;
		}

		return commercePaymentEntryAuditTypeServiceWrapper.getService();
	}

	@Override
	public List<CommercePaymentEntryAuditType>
		getCommercePaymentEntryAuditTypes() {

		List<CommercePaymentEntryAuditType> commercePaymentEntryAuditTypes =
			new ArrayList<>();

		List
			<ServiceTrackerCustomizerFactory.ServiceWrapper
				<CommercePaymentEntryAuditType>>
					commercePaymentEntryAuditTypeServiceWrappers =
						ListUtil.fromCollection(_serviceTrackerMap.values());

		for (ServiceTrackerCustomizerFactory.ServiceWrapper
				<CommercePaymentEntryAuditType>
					commerceDiscountRuleTypeServiceWrapper :
						commercePaymentEntryAuditTypeServiceWrappers) {

			commercePaymentEntryAuditTypes.add(
				commerceDiscountRuleTypeServiceWrapper.getService());
		}

		return Collections.unmodifiableList(commercePaymentEntryAuditTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommercePaymentEntryAuditType.class,
			"commerce.payment.entry.audit.type.key",
			ServiceTrackerCustomizerFactory.
				<CommercePaymentEntryAuditType>serviceWrapper(bundleContext));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePaymentEntryAuditTypeRegistryImpl.class);

	private ServiceTrackerMap
		<String,
		 ServiceTrackerCustomizerFactory.ServiceWrapper
			 <CommercePaymentEntryAuditType>> _serviceTrackerMap;

}