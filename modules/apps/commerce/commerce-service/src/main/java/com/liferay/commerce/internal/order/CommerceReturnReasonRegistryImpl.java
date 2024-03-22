/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.internal.order;

import com.liferay.commerce.internal.order.comparator.CommerceReturnReasonOrderComparator;
import com.liferay.commerce.order.CommerceReturnReason;
import com.liferay.commerce.order.CommerceReturnReasonRegistry;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Crescenzo Rega
 */
@Component(service = CommerceReturnReasonRegistry.class)
public class CommerceReturnReasonRegistryImpl
	implements CommerceReturnReasonRegistry {

	@Override
	public CommerceReturnReason getCommerceReturnReason(String key) {
		if (Validator.isNull(key) ||
			!FeatureFlagManagerUtil.isEnabled("LPD-10562")) {

			return null;
		}

		CommerceReturnReason commerceReturnReason =
			_serviceTrackerMap.getService(key);

		if (commerceReturnReason == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No commerce return reason registered with key " + key);
			}
		}

		return commerceReturnReason;
	}

	@Override
	public List<CommerceReturnReason> getCommerceReturnReasons() {
		if (!FeatureFlagManagerUtil.isEnabled("LPD-10562")) {
			return Collections.emptyList();
		}

		List<CommerceReturnReason> commerceReturnReasons = new ArrayList<>();

		try {
			commerceReturnReasons = ListUtil.fromCollection(
				_serviceTrackerMap.values());

			commerceReturnReasons.sort(_commerceReturnReasonOrderComparator);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception);
			}
		}

		return Collections.unmodifiableList(commerceReturnReasons);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommerceReturnReason.class, null,
			(serviceReference, emitter) -> {
				CommerceReturnReason commerceReturnReason =
					bundleContext.getService(serviceReference);

				try {
					if (commerceReturnReason.getKey() != null) {
						emitter.emit(commerceReturnReason.getKey());
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
		CommerceReturnReasonRegistryImpl.class);

	private final Comparator<CommerceReturnReason>
		_commerceReturnReasonOrderComparator =
			new CommerceReturnReasonOrderComparator();
	private ServiceTrackerMap<String, CommerceReturnReason> _serviceTrackerMap;

}