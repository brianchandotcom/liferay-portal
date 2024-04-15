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
import com.liferay.petra.lang.HashUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * @author Alessio Antonio Rendina
 * @author Crescenzo Rega
 */
@Component(service = CommercePaymentEntryRefundTypeRegistry.class)
public class CommercePaymentEntryRefundTypeRegistryImpl
	implements CommercePaymentEntryRefundTypeRegistry {

	@Override
	public CommercePaymentEntryRefundType getCommercePaymentEntryRefundType(
		long companyId, String key) {

		if (Validator.isNull(key)) {
			return null;
		}

		CommercePaymentEntryRefundType commercePaymentEntryRefundType =
			_serviceTrackerMap.getService(new ScopedKey(companyId, key));

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
		getCommercePaymentEntryRefundTypes(long companyId) {

		List<CommercePaymentEntryRefundType> commercePaymentEntryRefundTypes =
			new ArrayList<>();

		for (ScopedKey scopedKey : _serviceTrackerMap.keySet()) {
			if (companyId == scopedKey._companyId) {
				commercePaymentEntryRefundTypes.add(
					_serviceTrackerMap.getService(scopedKey));
			}
		}

		commercePaymentEntryRefundTypes.sort(
			_commercePaymentEntryRefundTypeOrderComparator);

		return Collections.unmodifiableList(commercePaymentEntryRefundTypes);
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, CommercePaymentEntryRefundType.class,
			"(enabled=true)",
			(serviceReference, emitter) -> emitter.emit(
				new ScopedKey(
					GetterUtil.getLong(
						serviceReference.getProperty("companyId")),
					String.valueOf(serviceReference.getProperty("key")))));
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
	private ServiceTrackerMap<ScopedKey, CommercePaymentEntryRefundType>
		_serviceTrackerMap;

	private static class ScopedKey {

		@Override
		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}

			if (!(object instanceof ScopedKey)) {
				return false;
			}

			ScopedKey scopedKey = (ScopedKey)object;

			if ((_companyId == scopedKey._companyId) &&
				Objects.equals(_key, scopedKey._key)) {

				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			int hash = HashUtil.hash(0, _companyId);

			return HashUtil.hash(hash, _key);
		}

		private ScopedKey(long companyId, String key) {
			_companyId = companyId;
			_key = key;
		}

		private final long _companyId;
		private final String _key;

	}

}