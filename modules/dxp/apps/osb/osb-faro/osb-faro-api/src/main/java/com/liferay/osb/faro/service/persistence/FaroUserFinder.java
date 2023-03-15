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

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Matthew Kong
 * @generated
 */
@ProviderType
public interface FaroUserFinder {

	public int countByChannelKeywords(
		long channelGroupId, boolean available, String query,
		java.util.List<Integer> statuses, long workspaceGroupId);

	public int countByKeywords(
		long groupId, String query, java.util.List<Integer> statuses);

	public java.util.List<com.liferay.osb.faro.model.FaroUser>
		findByChannelKeywords(
			long channelGroupId, boolean available, String query,
			java.util.List<Integer> statuses, long workspaceGroupId, int start,
			int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.osb.faro.model.FaroUser> orderByComparator);

	public java.util.List<com.liferay.osb.faro.model.FaroUser> findByKeywords(
		long groupId, String query, java.util.List<Integer> statuses, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.osb.faro.model.FaroUser> orderByComparator);

}