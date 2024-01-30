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
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.sql.dsl.DSLFunctionFactoryUtil;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.DSLQuery;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.asset.model.impl.AssetTagImpl;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public class AssetTagFinderImpl
	extends AssetTagFinderBaseImpl implements AssetTagFinder {

	@Override
	public int countByG_C_N(long groupId, long classNameId, String name) {
		Session session = null;

		try {
			session = openSession();

			Long[] assetTagIds = _getAssetTagIds(groupId, classNameId, name);

			if (ArrayUtil.isEmpty(assetTagIds)) {
				return 0;
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				DSLQueryFactoryUtil.countDistinct(
					AssetEntries_AssetTagsTable.INSTANCE.entryId
				).from(
					AssetEntries_AssetTagsTable.INSTANCE
				).where(
					AssetEntries_AssetTagsTable.INSTANCE.tagId.in(assetTagIds)
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
						DSLFunctionFactoryUtil.lower(
							AssetTagTable.INSTANCE.name
						).like(
							StringUtil.toLowerCase(name)
						));
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

	private Long[] _getAssetTagIds(
		long groupId, long classNameId, String name) {

		Session session = null;

		try {
			session = openSession();

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				DSLQueryFactoryUtil.selectDistinct(
					AssetTagTable.INSTANCE
				).from(
					AssetTagTable.INSTANCE
				).innerJoinON(
					AssetEntries_AssetTagsTable.INSTANCE,
					AssetEntries_AssetTagsTable.INSTANCE.tagId.eq(
						AssetTagTable.INSTANCE.tagId)
				).innerJoinON(
					AssetEntryTable.INSTANCE,
					AssetEntryTable.INSTANCE.entryId.eq(
						AssetEntries_AssetTagsTable.INSTANCE.entryId)
				).where(
					AssetEntryTable.INSTANCE.groupId.eq(
						groupId
					).and(
						() -> {
							if (classNameId <= 0) {
								return null;
							}

							return AssetEntryTable.INSTANCE.classNameId.eq(
								classNameId);
						}
					).and(
						AssetEntryTable.INSTANCE.visible.eq(true)
					).and(
						() -> {
							if (name == null) {
								return null;
							}

							return AssetTagTable.INSTANCE.name.like(name);
						}
					)
				));

			sqlQuery.addEntity("AssetTag", AssetTagImpl.class);

			List<AssetTag> assetTags = (List<AssetTag>)QueryUtil.list(
				sqlQuery, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			List<Long> assetTagIds = TransformUtil.unsafeTransform(
				assetTags,
				assetTag -> {
					if (!StringUtil.equals(assetTag.getName(), name)) {
						return null;
					}

					return Long.valueOf(assetTag.getTagId());
				});

			return assetTagIds.toArray(new Long[0]);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

}