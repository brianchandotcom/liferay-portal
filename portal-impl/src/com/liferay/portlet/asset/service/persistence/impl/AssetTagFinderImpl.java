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

package com.liferay.portlet.asset.service.persistence.impl;

import com.liferay.asset.kernel.model.AssetEntries_AssetTagsTable;
import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.persistence.AssetTagFinder;
import com.liferay.petra.sql.dsl.DSLFunctionUtil;
import com.liferay.petra.sql.dsl.DSLQueryUtil;
import com.liferay.petra.sql.dsl.expressions.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.petra.sql.dsl.query.OrderByStep;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.asset.model.impl.AssetTagImpl;
import com.liferay.social.kernel.model.SocialActivityCounter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public class AssetTagFinderImpl
	extends AssetTagFinderBaseImpl implements AssetTagFinder {

	@Override
	public int countByG_N(long groupId, String name) {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(
				DSLQueryUtil.countDistinct(
					AssetEntries_AssetTagsTable.INSTANCE.entryId
				).from(
					AssetTag.TABLE
				).innerJoinON(
					AssetEntries_AssetTagsTable.INSTANCE,
					AssetEntries_AssetTagsTable.INSTANCE.tagId.eq(
						AssetTag.TABLE.tagId)
				).where(
					AssetEntries_AssetTagsTable.INSTANCE.entryId.in(
						DSLQueryUtil.select(
							AssetEntry.TABLE.entryId
						).from(
							AssetEntry.TABLE
						).where(
							AssetEntry.TABLE.groupId.eq(
								groupId
							).and(
								AssetEntry.TABLE.visible.eq(true)
							)
						)
					).and(
						AssetTag.TABLE.name.like(StringUtil.toLowerCase(name))
					)
				));

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public int countByG_C_N(long groupId, long classNameId, String name) {
		Predicate predicate = AssetEntries_AssetTagsTable.INSTANCE.entryId.in(
			DSLQueryUtil.select(
				AssetEntry.TABLE.entryId
			).from(
				AssetEntry.TABLE
			).where(
				AssetEntry.TABLE.groupId.eq(
					groupId
				).and(
					AssetEntry.TABLE.classNameId.eq(classNameId)
				).and(
					AssetEntry.TABLE.visible.eq(true)
				)
			));

		if (name != null) {
			predicate = predicate.and(
				AssetTag.TABLE.name.like(StringUtil.toLowerCase(name)));
		}

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(
				DSLQueryUtil.countDistinct(
					AssetEntries_AssetTagsTable.INSTANCE.entryId
				).from(
					AssetTag.TABLE
				).innerJoinON(
					AssetEntries_AssetTagsTable.INSTANCE,
					AssetEntries_AssetTagsTable.INSTANCE.tagId.eq(
						AssetTag.TABLE.tagId)
				).where(
					predicate
				));

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<AssetTag> findByG_C_N(
		long groupId, long classNameId, String name, int start, int end,
		OrderByComparator<AssetTag> obc) {

		Predicate predicate = AssetEntries_AssetTagsTable.INSTANCE.entryId.in(
			DSLQueryUtil.select(
				AssetEntry.TABLE.entryId
			).from(
				AssetEntry.TABLE
			).where(
				AssetEntry.TABLE.groupId.eq(
					groupId
				).and(
					AssetEntry.TABLE.classNameId.eq(classNameId)
				).and(
					AssetEntry.TABLE.visible.eq(true)
				)
			));

		if (name != null) {
			predicate = predicate.and(
				AssetTag.TABLE.name.like(StringUtil.toLowerCase(name)));
		}

		Session session = null;

		try {
			session = openSession();

			OrderByStep orderByStep = DSLQueryUtil.selectDistinct(
				AssetTag.TABLE
			).from(
				AssetTag.TABLE
			).innerJoinON(
				AssetEntries_AssetTagsTable.INSTANCE,
				AssetEntries_AssetTagsTable.INSTANCE.tagId.eq(
					AssetTag.TABLE.tagId)
			).where(
				predicate
			);

			DSLQuery dslQuery = null;

			if (obc == null) {
				dslQuery = orderByStep.orderBy(AssetTag.TABLE.name.ascending());
			}
			else {
				dslQuery = orderByStep.orderBy(AssetTag.TABLE, obc);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(dslQuery);

			q.addEntity("AssetTag", AssetTagImpl.class);

			return (List<AssetTag>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<AssetTag> findByG_N_S_E(
		long groupId, String name, int startPeriod, int endPeriod,
		int periodLength) {

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = session.createSynchronizedSQLQuery(
				DSLQueryUtil.select(
					AssetTag.TABLE.tagId, AssetTag.TABLE.name,
					DSLFunctionUtil.sum(
						SocialActivityCounter.TABLE.currentValue)
				).from(
					AssetTag.TABLE
				).innerJoinON(
					AssetEntries_AssetTagsTable.INSTANCE,
					AssetEntries_AssetTagsTable.INSTANCE.tagId.eq(
						AssetTag.TABLE.tagId)
				).innerJoinON(
					SocialActivityCounter.TABLE,
					SocialActivityCounter.TABLE.classNameId.eq(
						AssetEntry.TABLE.classNameId
					).and(
						SocialActivityCounter.TABLE.classPK.eq(
							AssetEntry.TABLE.classPK)
					)
				).where(
					SocialActivityCounter.TABLE.groupId.eq(
						groupId
					).and(
						SocialActivityCounter.TABLE.name.eq(name)
					).and(
						SocialActivityCounter.TABLE.startPeriod.gte(startPeriod)
					).and(
						SocialActivityCounter.TABLE.startPeriod.lte(endPeriod)
					).and(
						DSLFunctionUtil.add(
							SocialActivityCounter.TABLE.startPeriod,
							periodLength
						).lte(
							endPeriod
						)
					)
				).groupBy(
					AssetTag.TABLE.tagId, AssetTag.TABLE.name
				));

			List<AssetTag> assetTags = new ArrayList<>();

			Iterator<Object[]> itr = q.iterate();

			while (itr.hasNext()) {
				Object[] array = itr.next();

				AssetTag assetTag = new AssetTagImpl();

				assetTag.setTagId(GetterUtil.getLong(array[0]));
				assetTag.setName(GetterUtil.getString(array[1]));
				assetTag.setAssetCount(GetterUtil.getInteger(array[2]));

				assetTags.add(assetTag);
			}

			return assetTags;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

}