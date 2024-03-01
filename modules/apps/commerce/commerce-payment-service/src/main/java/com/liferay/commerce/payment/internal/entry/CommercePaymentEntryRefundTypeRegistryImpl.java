/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.payment.internal.entry;

import com.liferay.commerce.payment.entry.CommercePaymentEntryRefundType;
import com.liferay.commerce.payment.entry.CommercePaymentEntryRefundTypeRegistry;
import com.liferay.commerce.payment.internal.entry.comparator.CommercePaymentEntryRefundTypeOrderComparator;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Alessio Antonio Rendina
 */
@Component(service = CommercePaymentEntryRefundTypeRegistry.class)
public class CommercePaymentEntryRefundTypeRegistryImpl
	implements CommercePaymentEntryRefundTypeRegistry {

	@Override
	public CommercePaymentEntryRefundType getCommercePaymentEntryRefundType(
		String key) {

		if (Validator.isNull(key) ||
			!FeatureFlagManagerUtil.isEnabled("COMMERCE-12754")) {

			return null;
		}

		CommercePaymentEntryRefundType commercePaymentEntryRefundType =
			_serviceTrackerMap.getService(key);

		if (commercePaymentEntryRefundType == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce payment entry refund type registered with " +
						"key " + key);
			}
		}

		return commercePaymentEntryRefundType;
	}

	@Override
	public List<CommercePaymentEntryRefundType>
		getCommercePaymentEntryRefundTypes() {

		if (!FeatureFlagManagerUtil.isEnabled("COMMERCE-12754")) {
			return Collections.emptyList();
		}

		List<CommercePaymentEntryRefundType> commercePaymentEntryRefundTypes =
			new ArrayList<>();

		try {
			commercePaymentEntryRefundTypes = TransformUtil.transform(
				_serviceTrackerMap.values(),
				commercePaymentEntryRefundType -> {
					if (commercePaymentEntryRefundType.getEnabled()) {
						return commercePaymentEntryRefundType;
					}

					return null;
				});

			commercePaymentEntryRefundTypes.sort(
				_commercePaymentEntryRefundTypeOrderComparator);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return Collections.unmodifiableList(commercePaymentEntryRefundTypes);
	}

	@Activate
	@Modified
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommercePaymentEntryRefundType.class, null,
			(serviceReference, emitter) -> {
				CommercePaymentEntryRefundType commercePaymentEntryRefundType =
					bundleContext.getService(serviceReference);

				try {
					if (commercePaymentEntryRefundType.getKey() != null) {
						emitter.emit(commercePaymentEntryRefundType.getKey());
					}
				}
				finally {
					bundleContext.ungetService(serviceReference);
				}
			});
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePaymentEntryRefundTypeRegistryImpl.class);

	private final Comparator<CommercePaymentEntryRefundType>
		_commercePaymentEntryRefundTypeOrderComparator =
			new CommercePaymentEntryRefundTypeOrderComparator();
	private volatile ServiceTrackerMap<String, CommercePaymentEntryRefundType>
		_serviceTrackerMap;

}