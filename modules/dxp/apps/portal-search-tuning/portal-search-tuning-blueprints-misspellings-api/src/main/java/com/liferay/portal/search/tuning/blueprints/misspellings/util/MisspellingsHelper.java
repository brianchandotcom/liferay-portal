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

package com.liferay.portal.search.tuning.blueprints.misspellings.util;

import com.liferay.portal.search.tuning.blueprints.misspellings.index.MisspellingSet;

import java.util.List;

/**
 * @author Petteri Karttunen
 */
public interface MisspellingsHelper {

	public void addMisspellingSet(
		long companyId, long groupId, String languageId, String phrase,
		List<String> misspellings);

	public void deleteCompanyMisspellingSets(long companyId);

	public int getCompanyMisspellingSetCount(long companyId);

	public List<MisspellingSet> getCompanyMisspellingSets(long companyId);

	public void updateMisspellingSet(
		long companyId, long groupId, String misspellingSetId,
		String languageId, String phrase, List<String> misspellings);

}