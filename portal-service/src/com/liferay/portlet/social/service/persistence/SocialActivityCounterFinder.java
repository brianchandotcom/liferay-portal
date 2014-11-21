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

package com.liferay.portlet.social.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface SocialActivityCounterFinder {
	public int countU_ByG_N(long groupId, java.lang.String[] names);

	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> findAC_ByG_N_S_E_1(
		long groupId, java.lang.String name, int startPeriod, int endPeriod,
		int periodLength);

	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> findAC_ByG_N_S_E_2(
		long groupId, java.lang.String counterName, int startPeriod,
		int endPeriod, int periodLength);

	public java.util.List<com.liferay.portlet.social.model.SocialActivityCounter> findAC_By_G_C_C_N_S_E(
		long groupId, java.util.List<java.lang.Long> userIds,
		java.lang.String[] names, int start, int end);

	public java.util.List<java.lang.Long> findU_ByG_N(long groupId,
		java.lang.String[] names, int start, int end);
}