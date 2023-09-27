/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.asset.service.persistence.impl;

import com.liferay.asset.kernel.model.AssetEntries_AssetTagsTable;
import com.liferay.asset.kernel.model.AssetEntryTable;
import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.model.AssetTagTable;
import com.liferay.asset.kernel.service.persistence.AssetTagFinder;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.asset.model.impl.AssetTagImpl;
import com.liferay.social.kernel.model.SocialActivityCounterTable;

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

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				DSLQueryFactoryUtil.countDistinct(
					AssetEntries_AssetTagsTable.INSTANCE.entryId
				).from(
					AssetTagTable.INSTANCE
				).innerJoinON(
					AssetEntries_AssetTagsTable.INSTANCE,
					AssetEntries_AssetTagsTable.INSTANCE.tagId.eq(
						AssetTagTable.INSTANCE.tagId)
				).where(
					AssetEntries_AssetTagsTable.INSTANCE.entryId.in(
						DSLQueryFactoryUtil.select(
							AssetEntryTable.INSTANCE.entryId
						).from(
							AssetEntryTable.INSTANCE
						).where(
							AssetEntryTable.INSTANCE.groupId.eq(
								groupId
							).and(
								AssetEntryTable.INSTANCE.visible.eq(true)
							)
						)
					).and(
						AssetTagTable.INSTANCE.name.like(
							StringUtil.toLowerCase(name))
					)
				));

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			Iterator<Long> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Long count = iterator.next();

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
		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				DSLQueryFactoryUtil.countDistinct(
					AssetEntries_AssetTagsTable.INSTANCE.entryId
				).from(
					AssetTagTable.INSTANCE
				).innerJoinON(
					AssetEntries_AssetTagsTable.INSTANCE,
					AssetEntries_AssetTagsTable.INSTANCE.tagId.eq(
						AssetTagTable.INSTANCE.tagId)
				).where(
					() -> {
						Predicate predicate =
							AssetEntries_AssetTagsTable.INSTANCE.entryId.in(
								DSLQueryFactoryUtil.select(
									AssetEntryTable.INSTANCE.entryId
								).from(
									AssetEntryTable.INSTANCE
								).where(
									AssetEntryTable.INSTANCE.groupId.eq(
										groupId
									).and(
										AssetEntryTable.INSTANCE.classNameId.eq(
											classNameId)
									).and(
										AssetEntryTable.INSTANCE.visible.eq(
											true)
									)
								));

						if (name == null) {
							return predicate;
						}

						return predicate.and(
							AssetTagTable.INSTANCE.name.like(
								StringUtil.toLowerCase(name)));
					}
				));

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			Iterator<Long> iterator = sqlQuery.iterate();

			if (iterator.hasNext()) {
				Long count = iterator.next();

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
		OrderByComparator<AssetTag> orderByComparator) {

		Session session = null;

		try {
			session = openSession();

			DSLQuery dslQuery = DSLQueryFactoryUtil.selectDistinct(
				AssetTagTable.INSTANCE
			).from(
				AssetTagTable.INSTANCE
			).innerJoinON(
				AssetEntries_AssetTagsTable.INSTANCE,
				AssetEntries_AssetTagsTable.INSTANCE.tagId.eq(
					AssetTagTable.INSTANCE.tagId)
			).where(
				() -> {
					Predicate predicate =
						AssetEntries_AssetTagsTable.INSTANCE.entryId.in(
							DSLQueryFactoryUtil.select(
								AssetEntryTable.INSTANCE.entryId
							).from(
								AssetEntryTable.INSTANCE
							).where(
								AssetEntryTable.INSTANCE.groupId.eq(
									groupId
								).and(
									AssetEntryTable.INSTANCE.classNameId.eq(
										classNameId)
								).and(
									AssetEntryTable.INSTANCE.visible.eq(true)
								)
							));

					if (name == null) {
						return predicate;
					}

					return predicate.and(
						AssetTagTable.INSTANCE.name.like(
							StringUtil.toLowerCase(name)));
				}
			).orderBy(
				orderByStep -> {
					if (orderByComparator == null) {
						return orderByStep.orderBy(
							AssetTagTable.INSTANCE.name.ascending());
					}

					return orderByStep.orderBy(
						AssetTagTable.INSTANCE, orderByComparator);
				}
			);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(dslQuery);

			sqlQuery.addEntity("AssetTag", AssetTagImpl.class);

			return (List<AssetTag>)QueryUtil.list(
				sqlQuery, getDialect(), start, end);
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

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				DSLQueryFactoryUtil.select(
					AssetTagTable.INSTANCE.tagId, AssetTagTable.INSTANCE.name,
					DSLFunctionFactoryUtil.sum(
						SocialActivityCounterTable.INSTANCE.currentValue)
				).from(
					AssetTagTable.INSTANCE
				).innerJoinON(
					AssetEntries_AssetTagsTable.INSTANCE,
					AssetEntries_AssetTagsTable.INSTANCE.tagId.eq(
						AssetTagTable.INSTANCE.tagId)
				).innerJoinON(
					SocialActivityCounterTable.INSTANCE,
					SocialActivityCounterTable.INSTANCE.classNameId.eq(
						AssetEntryTable.INSTANCE.classNameId
					).and(
						SocialActivityCounterTable.INSTANCE.classPK.eq(
							AssetEntryTable.INSTANCE.classPK)
					)
				).where(
					SocialActivityCounterTable.INSTANCE.groupId.eq(
						groupId
					).and(
						SocialActivityCounterTable.INSTANCE.name.eq(name)
					).and(
						SocialActivityCounterTable.INSTANCE.startPeriod.gte(
							startPeriod)
					).and(
						SocialActivityCounterTable.INSTANCE.startPeriod.lte(
							endPeriod)
					).and(
						DSLFunctionFactoryUtil.add(
							SocialActivityCounterTable.INSTANCE.startPeriod,
							periodLength
						).lte(
							endPeriod
						)
					)
				).groupBy(
					AssetTagTable.INSTANCE.tagId, AssetTagTable.INSTANCE.name
				));

			List<AssetTag> assetTags = new ArrayList<>();

			Iterator<Object[]> iterator = sqlQuery.iterate();

			while (iterator.hasNext()) {
				Object[] array = iterator.next();

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