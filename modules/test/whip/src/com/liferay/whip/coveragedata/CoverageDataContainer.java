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

package com.liferay.whip.coveragedata;

import java.io.Serializable;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Shuyang Zhou
 */
public abstract class CoverageDataContainer
		<K, V extends CoverageData<V>, T extends CoverageDataContainer<K, V, T>>
	implements CoverageData<T>, Serializable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CoverageDataContainer)) {
			return false;
		}

		CoverageDataContainer<K, V, T> coverageDataContainer =
			(CoverageDataContainer<K, V, T>)obj;

		return children.equals(coverageDataContainer.children);
	}

	@Override
	public double getBranchCoverageRate() {
		int numberOfCoveredBranches = 0;
		int numberOfValidBranches = 0;

		for (CoverageData<V> coverageData : children.values()) {
			numberOfCoveredBranches +=
				coverageData.getNumberOfCoveredBranches();
			numberOfValidBranches += coverageData.getNumberOfValidBranches();
		}

		if (numberOfValidBranches == 0) {
			return 1D;
		}

		return (double)numberOfCoveredBranches / numberOfValidBranches;
	}

	@Override
	public double getLineCoverageRate() {
		int numberOfCoveredLines = 0;
		int numberOfValidLines = 0;

		for (CoverageData<V> coverageData : children.values()) {
			numberOfCoveredLines += coverageData.getNumberOfCoveredLines();
			numberOfValidLines += coverageData.getNumberOfValidLines();
		}

		if (numberOfValidLines == 0) {
			return 1D;
		}

		return (double)numberOfCoveredLines / numberOfValidLines;
	}

	@Override
	public int getNumberOfCoveredBranches() {
		int numberOfCoveredBranches = 0;

		for (CoverageData<V> coverageData : children.values()) {
			numberOfCoveredBranches +=
				coverageData.getNumberOfCoveredBranches();
		}

		return numberOfCoveredBranches;
	}

	@Override
	public int getNumberOfCoveredLines() {
		int numberOfCoveredLines = 0;

		for (CoverageData<V> coverageData : children.values()) {
			numberOfCoveredLines += coverageData.getNumberOfCoveredLines();
		}

		return numberOfCoveredLines;
	}

	@Override
	public int getNumberOfValidBranches() {
		int numberOfValidBranches = 0;

		for (CoverageData<V> coverageData : children.values()) {
			numberOfValidBranches += coverageData.getNumberOfValidBranches();
		}

		return numberOfValidBranches;
	}

	@Override
	public int getNumberOfValidLines() {
		int numberOfValidLines = 0;

		for (CoverageData<V> coverageData : children.values()) {
			numberOfValidLines += coverageData.getNumberOfValidLines();
		}

		return numberOfValidLines;
	}

	@Override
	public int hashCode() {
		return children.hashCode();
	}

	@Override
	public void merge(T otherCoverageDataContainer) {
		Map<K, V> otherChildren = otherCoverageDataContainer.children;

		for (Entry<K, V> entry : otherChildren.entrySet()) {
			V otherChildCoverageData = entry.getValue();

			V myChildCoverageData = children.putIfAbsent(
				entry.getKey(), otherChildCoverageData);

			if (myChildCoverageData != null) {
				myChildCoverageData.merge(otherChildCoverageData);
			}
		}
	}

	protected final ConcurrentMap<K, V> children = new ConcurrentHashMap<>();

	private static final long serialVersionUID = 1;

}