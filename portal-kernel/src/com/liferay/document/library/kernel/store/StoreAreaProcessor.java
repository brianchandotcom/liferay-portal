/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.document.library.kernel.store;

import java.time.temporal.TemporalAmount;

import java.util.function.Predicate;

/**
 * @author Adolfo Pérez
 */
public interface StoreAreaProcessor {

	public String cleanUpDeletedStoreArea(
		long companyId, int deletionQuota, Predicate<String> predicate,
		String startOffset, TemporalAmount temporalAmount);

	public String cleanUpNewStoreArea(
		long companyId, int evictionQuota, Predicate<String> predicate,
		String startOffset, TemporalAmount temporalAmount);

	public boolean copy(String sourceFileName, String destinationFileName);

	public boolean copyDirectory(
		long companyId, long repositoryId, String dirName,
		StoreArea[] sourceStoreAreas, StoreArea destinationStoreArea);

}