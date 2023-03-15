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

package com.liferay.osb.faro.service.persistence;

import aQute.bnd.annotation.ProviderType;

/**
 * @author Matthew Kong
 * @generated
 */
@ProviderType
public interface FaroChannelFinder {

	public int countByKeywords(
		long groupId, int permissionType, String query, long userId);

	public java.util.List<com.liferay.osb.faro.model.FaroChannel>
		findByKeywords(
			long groupId, int permissionType, String query, long userId,
			int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.osb.faro.model.FaroChannel> orderByComparator);

}