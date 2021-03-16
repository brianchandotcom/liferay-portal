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

package com.liferay.portal.search.tuning.blueprints.tracking.util;

import java.util.Date;
import java.util.List;

/**
 * @author Petteri Karttunen
 */
public interface TrackingHelper {

	public List<String> getKeywordsByCountry(
		String languageId, int start, int end, String orderByCol, boolean desc);

	public List<String> getKeywordsByGroups(
		long[] groupIds, int start, int end, String orderByCol, boolean desc);

	public List<String> getKeywordsByLanguage(
		String languageId, int start, int end, String orderByCol, boolean desc);

	public List<String> getKeywordsByRoles(
		long[] roleIds, int start, int end, String orderByCol, boolean desc);

	public List<String> getKeywordsByTimeRange(Date startDate, Date endDate);

}