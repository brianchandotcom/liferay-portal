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
import com.liferay.blogs.model.BlogsStatsUserDAO;
import com.liferay.blogs.model.impl.BlogsStatsUserDAOImpl;
import com.liferay.blogs.service.base.BlogsStatsUserLocalServiceBaseImpl;
import com.liferay.blogs.service.persistence.BlogsEntryPersistence;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Expression;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.ratings.kernel.model.RatingsEntryTable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.LongStream;

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
	public List<BlogsStatsUserDAO> getGroupsStatsUsers(
		long companyId, long groupId, int start, int end) {

		List<Object[]> results = _blogsEntryPersistence.dslQuery(
			DSLQueryFactoryUtil.select(
				BlogsEntryTable.INSTANCE.userId, _lastPostDateExpression,
				_entryCountExpression, _ratingsTotalEntriesExpression,
				_ratingsAverageScoreExpression, _ratingsTotalScoreExpression
			).from(
				BlogsEntryTable.INSTANCE
			).leftJoinOn(
				RatingsEntryTable.INSTANCE,
				BlogsEntryTable.INSTANCE.entryId.eq(
					RatingsEntryTable.INSTANCE.classPK
				).and(
					RatingsEntryTable.INSTANCE.classNameId.eq(
						_classNameLocalService.getClassNameId(
							BlogsEntry.class.getName()))
				)
			).where(
				BlogsEntryTable.INSTANCE.groupId.eq(
					groupId
				).and(
					BlogsEntryTable.INSTANCE.companyId.eq(companyId)
				)
			).groupBy(
				BlogsEntryTable.INSTANCE.userId
			).orderBy(
				_entryCountExpression.descending()
			).limit(
				start, end
			));

		List<BlogsStatsUserDAO> blogsStatsUsers = new ArrayList<>(
			results.size());

		for (Object[] columns : results) {
			Long userId = (Long)columns[0];
			Long entryCount = (Long)columns[1];
			Date lastPostDate = (Date)columns[2];
			Integer ratingsTotalEntries = (Integer)columns[3];
			Double ratingsAverageScore = (Double)columns[4];
			Double ratingsTotalScore = (Double)columns[5];

			blogsStatsUsers.add(
				new BlogsStatsUserDAOImpl(
					groupId, userId, lastPostDate, entryCount,
					ratingsTotalEntries, ratingsAverageScore,
					ratingsTotalScore));
		}

		return blogsStatsUsers;
	}

	@Override
	public List<BlogsStatsUserDAO> getGroupStatsUsers(
		long groupId, int start, int end) {

		List<Object[]> results = _blogsEntryPersistence.dslQuery(
			DSLQueryFactoryUtil.select(
				BlogsEntryTable.INSTANCE.userId, _lastPostDateExpression,
				_entryCountExpression, _ratingsTotalEntriesExpression,
				_ratingsAverageScoreExpression, _ratingsTotalScoreExpression
			).from(
				BlogsEntryTable.INSTANCE
			).leftJoinOn(
				RatingsEntryTable.INSTANCE,
				BlogsEntryTable.INSTANCE.entryId.eq(
					RatingsEntryTable.INSTANCE.classPK
				).and(
					RatingsEntryTable.INSTANCE.classNameId.eq(
						_classNameLocalService.getClassNameId(
							BlogsEntry.class.getName()))
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

		List<BlogsStatsUserDAO> blogsStatsUsers = new ArrayList<>(
			results.size());

		for (Object[] columns : results) {
			Long userId = (Long)columns[0];
			Long entryCount = (Long)columns[1];
			Date lastPostDate = (Date)columns[2];
			Integer ratingsTotalEntries = (Integer)columns[3];
			Double ratingsAverageScore = (Double)columns[4];
			Double ratingsTotalScore = (Double)columns[5];

			blogsStatsUsers.add(
				new BlogsStatsUserDAOImpl(
					groupId, userId, lastPostDate, entryCount,
					ratingsTotalEntries, ratingsAverageScore,
					ratingsTotalScore));
		}

		return blogsStatsUsers;
	}

	@Override
	public List<BlogsStatsUser> getOrganizationStatsUsers(
		long organizationId, int start, int end,
		OrderByComparator<BlogsStatsUser> orderByComparator) {

		LongStream longStream = Arrays.stream(
			_userLocalService.getOrganizationUserIds(organizationId));

		Long[] organizationUserIds = longStream.boxed(
		).toArray(
			Long[]::new
		);

		List<Object[]> results = _blogsEntryPersistence.dslQuery(
			DSLQueryFactoryUtil.select(
				BlogsEntryTable.INSTANCE.userId, _lastPostDateExpression,
				_entryCountExpression, _ratingsTotalEntriesExpression,
				_ratingsAverageScoreExpression, _ratingsTotalScoreExpression
			).from(
				BlogsEntryTable.INSTANCE
			).leftJoinOn(
				RatingsEntryTable.INSTANCE,
				BlogsEntryTable.INSTANCE.entryId.eq(
					RatingsEntryTable.INSTANCE.classPK
				).and(
					RatingsEntryTable.INSTANCE.classNameId.eq(
						_classNameLocalService.getClassNameId(
							BlogsEntry.class.getName()))
				)
			).where(
				BlogsEntryTable.INSTANCE.userId.in(organizationUserIds)
			).groupBy(
				BlogsEntryTable.INSTANCE.userId
			).orderBy(
				_lastPostDateExpression.descending()
			).limit(
				start, end
			));

		List<BlogsStatsUserDAO> blogsStatsUsers = new ArrayList<>(
			results.size());

		for (Object[] columns : results) {
			Long userId = (Long)columns[0];
			Long entryCount = (Long)columns[1];
			Date lastPostDate = (Date)columns[2];
			Integer ratingsTotalEntries = (Integer)columns[3];
			Double ratingsAverageScore = (Double)columns[4];
			Double ratingsTotalScore = (Double)columns[5];

			blogsStatsUsers.add(
				new BlogsStatsUserDAOImpl(
					groupId, userId, lastPostDate, entryCount,
					ratingsTotalEntries, ratingsAverageScore,
					ratingsTotalScore));
		}

		return blogsStatsUsers;
	}

	@Override
	public BlogsStatsUserDAO getStatsUser(long groupId, long userId)
		throws PortalException {

		List<Object[]> results = _blogsEntryPersistence.dslQuery(
			DSLQueryFactoryUtil.select(
				BlogsEntryTable.INSTANCE.userId, _lastPostDateExpression,
				_entryCountExpression, _ratingsTotalEntriesExpression,
				_ratingsAverageScoreExpression, _ratingsTotalScoreExpression
			).from(
				BlogsEntryTable.INSTANCE
			).leftJoinOn(
				RatingsEntryTable.INSTANCE,
				BlogsEntryTable.INSTANCE.entryId.eq(
					RatingsEntryTable.INSTANCE.classPK
				).and(
					RatingsEntryTable.INSTANCE.classNameId.eq(
						_classNameLocalService.getClassNameId(
							BlogsEntry.class.getName()))
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

		Object[] blogsStatsUser = results.get(0);

		Date lastPostDate = (Date)blogsStatsUser[1];
		Long entryCount = (Long)blogsStatsUser[2];
		Long ratingsTotalEntries = (Long)blogsStatsUser[3];
		Double ratingsAverageScore = (Double)blogsStatsUser[4];
		Double ratingsTotalScore = (Double)blogsStatsUser[5];

		return new BlogsStatsUserDAOImpl(
			groupId, userId, lastPostDate, entryCount,
			GetterUtil.get(ratingsTotalEntries, 0L),
			GetterUtil.get(ratingsAverageScore, 0D),
			GetterUtil.get(ratingsTotalScore, 0D));
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

	@Reference
	private UserLocalService _userLocalService;

}