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

package com.liferay.blogs.service.impl;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.model.BlogsEntryTable;
import com.liferay.blogs.model.BlogsStatsUser;
import com.liferay.blogs.model.impl.BlogsStatsUserImpl;
import com.liferay.blogs.service.base.BlogsStatsUserLocalServiceBaseImpl;
import com.liferay.blogs.service.persistence.BlogsEntryPersistence;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Users_OrgsTable;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.ratings.kernel.model.RatingsEntryTable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Máté Thurzó
 */
@Component(
	property = "model.class.name=com.liferay.blogs.model.BlogsStatsUser",
	service = AopService.class
)
public class BlogsStatsUserLocalServiceImpl
	extends BlogsStatsUserLocalServiceBaseImpl {

	@Override
	public List<BlogsStatsUser> getGroupsStatsUsers(
		long companyId, long groupId, int start, int end) {

		List<Object[]> results = _blogsEntryPersistence.dslQuery(
			DSLQueryFactoryUtil.select(
				BlogsEntryTable.INSTANCE.groupId,
				BlogsEntryTable.INSTANCE.userId, _lastPostDateExpression,
				_entryCountExpression, _ratingsTotalEntriesExpression,
				_ratingsAverageScoreExpression, _ratingsTotalScoreExpression
			).from(
				BlogsEntryTable.INSTANCE
			).leftJoinOn(
				RatingsEntryTable.INSTANCE,
				RatingsEntryTable.INSTANCE.classNameId.eq(
					_classNameLocalService.getClassNameId(
						BlogsEntry.class.getName())
				).and(
					RatingsEntryTable.INSTANCE.classPK.eq(
						BlogsEntryTable.INSTANCE.entryId)
				)
			).where(
				BlogsEntryTable.INSTANCE.companyId.eq(
					companyId
				).and(
					BlogsEntryTable.INSTANCE.groupId.eq(groupId)
				)
			).groupBy(
				BlogsEntryTable.INSTANCE.userId
			).orderBy(
				_entryCountExpression.descending()
			).limit(
				start, end
			));

		return _getBlogsStatsUsersList(results);
	}

	@Override
	public List<BlogsStatsUser> getGroupStatsUsers(
		long groupId, int start, int end) {

		List<Object[]> results = _blogsEntryPersistence.dslQuery(
			DSLQueryFactoryUtil.select(
				BlogsEntryTable.INSTANCE.groupId,
				BlogsEntryTable.INSTANCE.userId, _lastPostDateExpression,
				_entryCountExpression, _ratingsTotalEntriesExpression,
				_ratingsAverageScoreExpression, _ratingsTotalScoreExpression
			).from(
				BlogsEntryTable.INSTANCE
			).leftJoinOn(
				RatingsEntryTable.INSTANCE,
				RatingsEntryTable.INSTANCE.classNameId.eq(
					_classNameLocalService.getClassNameId(
						BlogsEntry.class.getName())
				).and(
					RatingsEntryTable.INSTANCE.classPK.eq(
						BlogsEntryTable.INSTANCE.entryId)
				)
			).where(
				BlogsEntryTable.INSTANCE.groupId.eq(
					groupId
				).and(
					_entryCountExpression.neq(0L)
				)
			).groupBy(
				BlogsEntryTable.INSTANCE.userId
			).orderBy(
				_lastPostDateExpression.descending()
			).limit(
				start, end
			));

		return _getBlogsStatsUsersList(results);
	}

	@Override
	public List<BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end) {

		List<Object[]> results = _blogsEntryPersistence.dslQuery(
			DSLQueryFactoryUtil.select(
				BlogsEntryTable.INSTANCE.groupId,
				BlogsEntryTable.INSTANCE.userId, _lastPostDateExpression,
				_entryCountExpression, _ratingsTotalEntriesExpression,
				_ratingsAverageScoreExpression, _ratingsTotalScoreExpression
			).from(
				BlogsEntryTable.INSTANCE
			).innerJoinON(
				Users_OrgsTable.INSTANCE,
				Users_OrgsTable.INSTANCE.userId.eq(
					BlogsEntryTable.INSTANCE.userId)
			).leftJoinOn(
				RatingsEntryTable.INSTANCE,
				RatingsEntryTable.INSTANCE.classNameId.eq(
					_classNameLocalService.getClassNameId(
						BlogsEntry.class.getName())
				).and(
					RatingsEntryTable.INSTANCE.classPK.eq(
						BlogsEntryTable.INSTANCE.entryId)
				)
			).where(
				Users_OrgsTable.INSTANCE.organizationId.eq(organizationId)
			).groupBy(
				BlogsEntryTable.INSTANCE.userId
			).orderBy(
				_lastPostDateExpression.descending()
			).limit(
				start, end
			));

		return _getBlogsStatsUsersList(results);
	}

	@Override
	public BlogsStatsUser getStatsUser(long groupId, long userId)
		throws PortalException {

		List<Object[]> results = _blogsEntryPersistence.dslQuery(
			DSLQueryFactoryUtil.select(
				BlogsEntryTable.INSTANCE.groupId,
				BlogsEntryTable.INSTANCE.userId, _lastPostDateExpression,
				_entryCountExpression, _ratingsTotalEntriesExpression,
				_ratingsAverageScoreExpression, _ratingsTotalScoreExpression
			).from(
				BlogsEntryTable.INSTANCE
			).leftJoinOn(
				RatingsEntryTable.INSTANCE,
				RatingsEntryTable.INSTANCE.classNameId.eq(
					_classNameLocalService.getClassNameId(
						BlogsEntry.class.getName())
				).and(
					RatingsEntryTable.INSTANCE.classPK.eq(
						BlogsEntryTable.INSTANCE.entryId)
				)
			).where(
				BlogsEntryTable.INSTANCE.groupId.eq(
					groupId
				).and(
					BlogsEntryTable.INSTANCE.userId.eq(userId)
				)
			).groupBy(
				BlogsEntryTable.INSTANCE.userId
			));

		List<BlogsStatsUser> blogsStatsUsers = _getBlogsStatsUsersList(results);

		if (blogsStatsUsers.isEmpty()) {
			return new BlogsStatsUserImpl(groupId, userId, null, 0, 0, 0, 0);
		}

		return blogsStatsUsers.get(0);
	}

	private List<BlogsStatsUser> _getBlogsStatsUsersList(
		List<Object[]> results) {

		List<BlogsStatsUser> blogsStatsUsers = new ArrayList<>(results.size());

		for (Object[] columns : results) {
			Long groupId = (Long)columns[0];
			Long userId = (Long)columns[1];
			Date lastPostDate = (Date)columns[2];
			Long entryCount = (Long)columns[3];
			Long ratingsTotalEntries = (Long)columns[4];
			Double ratingsAverageScore = (Double)columns[5];
			Double ratingsTotalScore = (Double)columns[6];

			blogsStatsUsers.add(
				new BlogsStatsUserImpl(
					groupId, userId, lastPostDate, entryCount,
					GetterUtil.get(ratingsTotalEntries, 0L),
					GetterUtil.get(ratingsAverageScore, 0D),
					GetterUtil.get(ratingsTotalScore, 0D)));
		}

		return blogsStatsUsers;
	}

	@Reference
	private BlogsEntryPersistence _blogsEntryPersistence;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private final Expression<Long> _entryCountExpression =
		DSLFunctionFactoryUtil.countDistinct(
			BlogsEntryTable.INSTANCE.entryId
		).as(
			"entryCount"
		);
	private final Expression<Date> _lastPostDateExpression =
		DSLFunctionFactoryUtil.max(
			BlogsEntryTable.INSTANCE.modifiedDate
		).as(
			"lastPostDate"
		);
	private final Expression<Number> _ratingsAverageScoreExpression =
		DSLFunctionFactoryUtil.avg(
			RatingsEntryTable.INSTANCE.score
		).as(
			"averageScore"
		);
	private final Expression<Long> _ratingsTotalEntriesExpression =
		DSLFunctionFactoryUtil.countDistinct(
			RatingsEntryTable.INSTANCE.entryId
		).as(
			"totalEntries"
		);
	private final Expression<Number> _ratingsTotalScoreExpression =
		DSLFunctionFactoryUtil.sum(
			RatingsEntryTable.INSTANCE.score
		).as(
			"totalScore"
		);

}