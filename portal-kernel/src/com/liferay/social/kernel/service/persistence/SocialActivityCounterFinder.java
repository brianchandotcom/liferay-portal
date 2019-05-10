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