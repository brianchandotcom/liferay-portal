/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.social.kernel.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface SocialActivityCounterFinder {

	public int countU_ByG_N(long groupId, String[] names);

	public java.util.List<com.liferay.social.kernel.model.SocialActivityCounter>
		findAC_ByG_N_S_E_1(
			long groupId, String name, int startPeriod, int endPeriod,
			int periodLength);

	public java.util.List<com.liferay.social.kernel.model.SocialActivityCounter>
		findAC_ByG_N_S_E_2(
			long groupId, String counterName, int startPeriod, int endPeriod,
			int periodLength);

	public java.util.List<com.liferay.social.kernel.model.SocialActivityCounter>
		findAC_By_G_C_C_N_S_E(
			long groupId, java.util.List<Long> userIds, String[] names,
			int start, int end);

	public java.util.List<Long> findU_ByG_N(
		long groupId, String[] names, int start, int end);

}