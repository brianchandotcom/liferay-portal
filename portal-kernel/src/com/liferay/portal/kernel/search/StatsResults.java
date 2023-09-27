/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import java.io.Serializable;

/**
 * @author Michael C. Han
 */
public class StatsResults implements Serializable {

	public StatsResults(String field) {
		_field = field;
	}

	public long getCount() {
		return _count;
	}

	public String getField() {
		return _field;
	}

	public double getMax() {
		return _max;
	}

	public double getMean() {
		return _mean;
	}

	public double getMin() {
		return _min;
	}

	public int getMissing() {
		return _missing;
	}

	public double getStandardDeviation() {
		return _standardDeviation;
	}

	public double getSum() {
		return _sum;
	}

	public double getSumOfSquares() {
		return _sumOfSquares;
	}

	public void setCount(long count) {
		_count = count;
	}

	public void setMax(double max) {
		_max = max;
	}

	public void setMean(double mean) {
		_mean = mean;
	}

	public void setMin(double min) {
		_min = min;
	}

	public void setMissing(int missing) {
		_missing = missing;
	}

	public void setStandardDeviation(double standardDeviation) {
		_standardDeviation = standardDeviation;
	}

	public void setSum(double sum) {
		_sum = sum;
	}

	public void setSumOfSquares(double sumOfSquares) {
		_sumOfSquares = sumOfSquares;
	}

	private long _count;
	private final String _field;
	private double _max;
	private double _mean;
	private double _min;
	private int _missing;
	private double _standardDeviation;
	private double _sum;
	private double _sumOfSquares;

}