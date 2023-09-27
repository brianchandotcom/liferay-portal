/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.whip.coveragedata;

/**
 * @author Shuyang Zhou
 */
public interface BranchCoverageData<T extends BranchCoverageData<T>> {

	public double getBranchCoverageRate();

	public int getNumberOfCoveredBranches();

	public int getNumberOfValidBranches();

	public void merge(T t);

}