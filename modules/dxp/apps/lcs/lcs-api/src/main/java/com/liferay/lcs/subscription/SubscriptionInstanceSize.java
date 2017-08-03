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

package com.liferay.lcs.subscription;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author  Igor Beslic
 * @version 2.1.1
 * @since   LCS 1.5
 */
public enum SubscriptionInstanceSize {

	SIZE_1(1, "small", 8), SIZE_2(2, "medium", 12), SIZE_3(3, "large", 16),
	SIZE_4(4, "extra-large", Integer.MAX_VALUE),
	SIZE_UNDEFINED(0, "undefined", 0);

	public static SubscriptionInstanceSize getSubscriptionInstanceSize(
		int processorCoresCount) {

		if (processorCoresCount <= 0) {
			throw new IllegalArgumentException(
				"Invalid number of processor cores: " + processorCoresCount);
		}

		SubscriptionInstanceSize[] values = values();

		Arrays.sort(
			values, new SubscriptionInstanceSizeProcesorCoresCountComparator());

		for (SubscriptionInstanceSize subscriptionInstanceSize : values) {
			if (processorCoresCount <=
					subscriptionInstanceSize.getProcessorCoresAllowed()) {

				return subscriptionInstanceSize;
			}
		}

		return SIZE_UNDEFINED;
	}

	public static SubscriptionInstanceSize valueOf(int instanceSize) {
		for (SubscriptionInstanceSize subscriptionInstanceSize : values()) {
			if (instanceSize == subscriptionInstanceSize.getInstanceSize()) {
				return subscriptionInstanceSize;
			}
		}

		return SIZE_UNDEFINED;
	}

	public int getInstanceSize() {
		return _instanceSize;
	}

	public String getLabel() {
		return _label;
	}

	public int getProcessorCoresAllowed() {
		return _processorCoresAllowed;
	}

	public static class SubscriptionInstanceSizeProcesorCoresCountComparator
		implements Comparator<SubscriptionInstanceSize> {

		@Override
		public int compare(
			SubscriptionInstanceSize subscriptionInstanceSize1,
			SubscriptionInstanceSize subscriptionInstanceSize2) {

			return subscriptionInstanceSize1._processorCoresAllowed -
				subscriptionInstanceSize2._processorCoresAllowed;
		}

	}

	private SubscriptionInstanceSize(
		int instanceSize, String label, int processorCoresAllowed) {

		_instanceSize = instanceSize;
		_label = label;
		_processorCoresAllowed = processorCoresAllowed;
	}

	private final int _instanceSize;
	private final String _label;
	private final int _processorCoresAllowed;

}