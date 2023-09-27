/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.constants;

import com.liferay.petra.string.StringPool;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShipmentConstants {

	public static final int[] ALLOWED_ORDER_STATUSES = {
		CommerceOrderConstants.ORDER_STATUS_DISPUTED,
		CommerceOrderConstants.ORDER_STATUS_PENDING,
		CommerceOrderConstants.ORDER_STATUS_PARTIALLY_REFUNDED,
		CommerceOrderConstants.ORDER_STATUS_PARTIALLY_SHIPPED
	};

	public static final int SHIPMENT_STATUS_DELIVERED = 3;

	public static final int SHIPMENT_STATUS_PROCESSING = 0;

	public static final int SHIPMENT_STATUS_READY_TO_BE_SHIPPED = 1;

	public static final int SHIPMENT_STATUS_SHIPPED = 2;

	public static final int[] SHIPMENT_STATUSES = {
		SHIPMENT_STATUS_PROCESSING, SHIPMENT_STATUS_READY_TO_BE_SHIPPED,
		SHIPMENT_STATUS_SHIPPED, SHIPMENT_STATUS_DELIVERED
	};

	public static String getShipmentLabelStyle(int shipmentStatus) {
		if (shipmentStatus == SHIPMENT_STATUS_DELIVERED) {
			return "info";
		}
		else if ((shipmentStatus == SHIPMENT_STATUS_READY_TO_BE_SHIPPED) ||
				 (shipmentStatus == SHIPMENT_STATUS_PROCESSING)) {

			return "warning";
		}
		else if (shipmentStatus == SHIPMENT_STATUS_SHIPPED) {
			return "success";
		}

		return StringPool.BLANK;
	}

	public static Integer getShipmentStatus(String label) {
		if (label.equals("delivered")) {
			return SHIPMENT_STATUS_DELIVERED;
		}
		else if (label.equals("processing")) {
			return SHIPMENT_STATUS_PROCESSING;
		}
		else if (label.equals("ready-to-ship")) {
			return SHIPMENT_STATUS_READY_TO_BE_SHIPPED;
		}
		else if (label.equals("shipped")) {
			return SHIPMENT_STATUS_SHIPPED;
		}

		return null;
	}

	public static String getShipmentStatusLabel(int shipmentStatus) {
		if (shipmentStatus == SHIPMENT_STATUS_DELIVERED) {
			return "delivered";
		}
		else if (shipmentStatus == SHIPMENT_STATUS_PROCESSING) {
			return "processing";
		}
		else if (shipmentStatus == SHIPMENT_STATUS_READY_TO_BE_SHIPPED) {
			return "ready-to-ship";
		}
		else if (shipmentStatus == SHIPMENT_STATUS_SHIPPED) {
			return "shipped";
		}

		return null;
	}

	public static String getShipmentTransitionLabel(int shipmentStatus) {
		if (shipmentStatus == SHIPMENT_STATUS_DELIVERED) {
			return "deliver";
		}
		else if (shipmentStatus == SHIPMENT_STATUS_PROCESSING) {
			return "reprocess";
		}
		else if (shipmentStatus == SHIPMENT_STATUS_READY_TO_BE_SHIPPED) {
			return "finish-processing";
		}
		else if (shipmentStatus == SHIPMENT_STATUS_SHIPPED) {
			return "ship";
		}

		return null;
	}

}