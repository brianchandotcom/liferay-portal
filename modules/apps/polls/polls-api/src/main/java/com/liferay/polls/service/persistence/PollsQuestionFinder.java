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

package com.liferay.polls.service.persistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public interface PollsQuestionFinder {

	public int countByKeywords(
		long companyId, long[] groupIds, String keywords);

	public int countByC_G_T_D(
		long companyId, long[] groupIds, String title, String description,
		boolean andOperator);

	public java.util.List<com.liferay.polls.model.PollsQuestion> findByKeywords(
		long companyId, long[] groupIds, String keywords, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.polls.model.PollsQuestion> orderByComparator);

	public java.util.List<com.liferay.polls.model.PollsQuestion> findByC_G_T_D(
		long companyId, long[] groupIds, String title, String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator
			<com.liferay.polls.model.PollsQuestion> orderByComparator);

}