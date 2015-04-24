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

package com.liferay.cobertura.coveragedata;

import java.io.Serializable;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import net.sourceforge.cobertura.coveragedata.CoverageData;

/**
 * @author Shuyang Zhou
 */
public abstract class CoverageDataContainer
	implements CoverageData, Serializable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CoverageDataContainer)) {
			return false;
		}

		CoverageDataContainer coverageDataContainer =
			(CoverageDataContainer)obj;

		return children.equals(coverageDataContainer.children);
	}

	@Override
	public double getBranchCoverageRate() {
		int numberOfValidBranches = 0;
		int numberOfCoveredBranches = 0;

		for (CoverageData coverageData : children.values()) {
			numberOfValidBranches += coverageData.getNumberOfValidBranches();
			numberOfCoveredBranches +=
				coverageData.getNumberOfCoveredBranches();
		}

		if (numberOfValidBranches == 0) {
			return 1D;
		}

		return (double)numberOfCoveredBranches / numberOfValidBranches;
	}

	@Override
	public double getLineCoverageRate() {
		int numberOfValidLines = 0;
		int numberOfCoveredLines = 0;

		for (CoverageData coverageData : children.values()) {
			numberOfValidLines += coverageData.getNumberOfValidLines();
			numberOfCoveredLines += coverageData.getNumberOfCoveredLines();
		}

		if (numberOfValidLines == 0) {
			return 1D;
		}

		return (double)numberOfCoveredLines / numberOfValidLines;
	}

	@Override
	public int getNumberOfCoveredBranches() {
		int numberOfCoveredBranches = 0;

		for (CoverageData coverageData : children.values()) {
			numberOfCoveredBranches +=
				coverageData.getNumberOfCoveredBranches();
		}

		return numberOfCoveredBranches;
	}

	@Override
	public int getNumberOfCoveredLines() {
		int numberOfCoveredLines = 0;

		for (CoverageData coverageData : children.values()) {
			numberOfCoveredLines += coverageData.getNumberOfCoveredLines();
		}

		return numberOfCoveredLines;
	}

	@Override
	public int getNumberOfValidBranches() {
		int numberOfValidBranches = 0;

		for (CoverageData coverageData : children.values()) {
			numberOfValidBranches += coverageData.getNumberOfValidBranches();
		}

		return numberOfValidBranches;
	}

	@Override
	public int getNumberOfValidLines() {
		int numberOfValidLines = 0;

		for (CoverageData coverageData : children.values()) {
			numberOfValidLines += coverageData.getNumberOfValidLines();
		}

		return numberOfValidLines;
	}

	@Override
	public int hashCode() {
		return children.hashCode();
	}

	@Override
	public void merge(CoverageData otherCoverageData) {
		CoverageDataContainer otherCoverageDataContainer =
			(CoverageDataContainer)otherCoverageData;

		Map<Object, CoverageData> otherChildren =
			otherCoverageDataContainer.children;

		for (Entry<Object, CoverageData> entry : otherChildren.entrySet()) {
			CoverageData otherChild = entry.getValue();

			CoverageData myChild = children.putIfAbsent(
				entry.getKey(), otherChild);

			if (myChild != null) {
				myChild.merge(otherChild);
			}
		}
	}

	protected final ConcurrentMap<Object, CoverageData> children =
		new ConcurrentHashMap<>();

	private static final long serialVersionUID = 1;

}