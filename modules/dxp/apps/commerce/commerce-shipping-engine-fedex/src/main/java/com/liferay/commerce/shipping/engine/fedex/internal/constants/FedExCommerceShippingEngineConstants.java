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

package com.liferay.commerce.shipping.engine.fedex.internal.constants;

import com.fedex.ws.rate.v22.DropoffType;
import com.fedex.ws.rate.v22.ServiceType;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Andrea Di Giorgi
 */
public class FedExCommerceShippingEngineConstants {

	public static final String[] DROPOFF_TYPES = {
		DropoffType._BUSINESS_SERVICE_CENTER, DropoffType._DROP_BOX,
		DropoffType._REGULAR_PICKUP, DropoffType._REQUEST_COURIER,
		DropoffType._STATION
	};

	public static final String PACKING_TYPE_BY_DIMENSIONS =
		"pack-by-dimensions";

	public static final String PACKING_TYPE_ONE_ITEM_PER_PACKAGE =
		"pack-one-item-per-package";

	public static final String[] PACKING_TYPES =
		{PACKING_TYPE_BY_DIMENSIONS, PACKING_TYPE_ONE_ITEM_PER_PACKAGE};

	public static final String SERVICE_NAME =
		"com.liferay.commerce.shipping.engine.fedex";

	public static final String[] SERVICE_TYPES = {
		ServiceType._EUROPE_FIRST_INTERNATIONAL_PRIORITY,
		ServiceType._FEDEX_1_DAY_FREIGHT, ServiceType._FEDEX_2_DAY,
		ServiceType._FEDEX_2_DAY_AM, ServiceType._FEDEX_2_DAY_FREIGHT,
		ServiceType._FEDEX_3_DAY_FREIGHT, ServiceType._FEDEX_DISTANCE_DEFERRED,
		ServiceType._FEDEX_EXPRESS_SAVER, ServiceType._FEDEX_FIRST_FREIGHT,
		ServiceType._FEDEX_FREIGHT_ECONOMY, ServiceType._FEDEX_FREIGHT_PRIORITY,
		ServiceType._FEDEX_GROUND, ServiceType._FEDEX_NEXT_DAY_AFTERNOON,
		ServiceType._FEDEX_NEXT_DAY_EARLY_MORNING,
		ServiceType._FEDEX_NEXT_DAY_END_OF_DAY,
		ServiceType._FEDEX_NEXT_DAY_FREIGHT,
		ServiceType._FEDEX_NEXT_DAY_MID_MORNING, ServiceType._FIRST_OVERNIGHT,
		ServiceType._GROUND_HOME_DELIVERY, ServiceType._INTERNATIONAL_ECONOMY,
		ServiceType._INTERNATIONAL_ECONOMY_FREIGHT,
		ServiceType._INTERNATIONAL_FIRST, ServiceType._INTERNATIONAL_PRIORITY,
		ServiceType._INTERNATIONAL_PRIORITY_EXPRESS,
		ServiceType._INTERNATIONAL_PRIORITY_FREIGHT,
		ServiceType._PRIORITY_OVERNIGHT, ServiceType._SAME_DAY,
		ServiceType._SAME_DAY_CITY, ServiceType._SMART_POST,
		ServiceType._STANDARD_OVERNIGHT
	};

	public static String getDropoffTypeLabel(String dropoffType) {
		return StringUtil.replace(
			StringUtil.toLowerCase(dropoffType), CharPool.UNDERLINE,
			CharPool.DASH);
	}

	public static String getServiceTypeLabel(String serviceType) {
		String label = StringUtil.replace(
			StringUtil.toLowerCase(serviceType), CharPool.UNDERLINE,
			CharPool.DASH);

		if (!label.startsWith("fedex-")) {
			label = "fedex-" + label;
		}

		return label;
	}

}