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

package com.liferay.commerce.forecast.model;

/**
 * @author Andrea Di Giorgi
 */
public class CommerceForecastEntryConstants {

	public static final String PERIOD_LABEL_MONTHLY = "monthly";

	public static final String PERIOD_LABEL_WEEKLY = "weekly";

	public static final int PERIOD_MONTHLY = 1;

	public static final int PERIOD_WEEKLY = 2;

	public static final int[] PERIODS = {PERIOD_MONTHLY, PERIOD_WEEKLY};

	public static final String TARGET_LABEL_QUANTITY = "quantity";

	public static final String TARGET_LABEL_REVENUE = "revenue";

	public static final int TARGET_QUANTITY = 1;

	public static final int TARGET_REVENUE = 2;

	public static final int[] TARGETS = {TARGET_QUANTITY, TARGET_REVENUE};

	public static int getLabelPeriod(String label) {
		if (label.equals(PERIOD_LABEL_MONTHLY)) {
			return PERIOD_MONTHLY;
		}
		else if (label.equals(PERIOD_LABEL_WEEKLY)) {
			return PERIOD_WEEKLY;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public static int getLabelTarget(String label) {
		if (label.equals(TARGET_LABEL_QUANTITY)) {
			return TARGET_QUANTITY;
		}
		else if (label.equals(TARGET_LABEL_REVENUE)) {
			return TARGET_REVENUE;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public static String getPeriodLabel(int period) {
		if (period == PERIOD_MONTHLY) {
			return PERIOD_LABEL_MONTHLY;
		}
		else if (period == PERIOD_WEEKLY) {
			return PERIOD_LABEL_WEEKLY;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

	public static String getTargetLabel(int target) {
		if (target == TARGET_QUANTITY) {
			return TARGET_LABEL_QUANTITY;
		}
		else if (target == TARGET_REVENUE) {
			return TARGET_LABEL_REVENUE;
		}
		else {
			throw new IllegalArgumentException();
		}
	}

}