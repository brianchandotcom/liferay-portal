/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CalendarUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.impl.AssetEntryImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.sql.Timestamp;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class AssetEntryFinderImpl
	extends BasePersistenceImpl<AssetEntry> implements AssetEntryFinder {

	public static String FIND_BY_AND_CATEGORY_IDS =
		AssetEntryFinder.class.getName() + ".findByAndCategoryIds";

	public static String FIND_BY_AND_CATEGORY_IDS_TREE =
		AssetEntryFinder.class.getName() + ".findByAndCategoryIdsTree";

	public static String FIND_BY_AND_TAG_IDS =
		AssetEntryFinder.class.getName() + ".findByAndTagIds";

	public int countEntries(AssetEntryQuery entryQuery) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = buildAssetQuerySQL(entryQuery, true, session);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetEntry> findEntries(AssetEntryQuery entryQuery)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			SQLQuery q = buildAssetQuerySQL(entryQuery, false, session);

			return (List<AssetEntry>)QueryUtil.list(
				q, getDialect(), entryQuery.getStart(), entryQuery.getEnd());
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void buildAllCategoriesSQL(
		String sqlId, long[] categoryIds, StringBundler sb) {

		sb.append(" AND AssetEntry.entryId IN (");

		for (int i = 0; i < categoryIds.length; i++) {
			sb.append(CustomSQLUtil.get(sqlId));

			if ((i + 1) < categoryIds.length) {
				sb.append(" AND AssetEntry.entryId IN (");
			}
		}

		for (int i = 0; i < categoryIds.length; i++) {
			if ((i + 1) < categoryIds.length) {
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);
	}

	protected void buildAllTagsSQL(long[] tagIds, StringBundler sb) {
		sb.append(" AND AssetEntry.entryId IN (");

		for (int i = 0; i < tagIds.length; i++) {
			sb.append(CustomSQLUtil.get(FIND_BY_AND_TAG_IDS));

			if ((i + 1) < tagIds.length) {
				sb.append(" AND AssetEntry.entryId IN (");
			}
		}

		for (int i = 0; i < tagIds.length; i++) {
			if ((i + 1) < tagIds.length) {
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);
	}

	protected SQLQuery buildAssetQuerySQL(
		AssetEntryQuery entryQuery, boolean count, Session session) {

		StringBundler sb = new StringBundler();

		if (count) {
			sb.append(
				"SELECT COUNT(DISTINCT AssetEntry.entryId) AS COUNT_VALUE ");
		}
		else {
			sb.append("SELECT DISTINCT {AssetEntry.*} ");
		}

		sb.append("FROM AssetEntry ");

		if (entryQuery.getAnyTagIds().length > 0) {
			sb.append("INNER JOIN ");
			sb.append("AssetEntries_AssetTags ON ");
			sb.append("(AssetEntries_AssetTags.entryId = ");
			sb.append("AssetEntry.entryId) ");
			sb.append("INNER JOIN ");
			sb.append("AssetTag ON ");
			sb.append("(AssetTag.tagId = AssetEntries_AssetTags.tagId) ");
		}

		if (entryQuery.getAnyCategoryIds().length > 0) {
			sb.append("INNER JOIN ");
			sb.append("AssetEntries_AssetCategories ON ");
			sb.append("(AssetEntries_AssetCategories.entryId = ");
			sb.append("AssetEntry.entryId) ");
			sb.append("INNER JOIN ");
			sb.append("AssetCategory ON ");
			sb.append("(AssetCategory.categoryId = ");
			sb.append("AssetEntries_AssetCategories.categoryId) ");
		}

		if (entryQuery.getLinkedAssetEntryId() > 0) {
			sb.append("INNER JOIN ");
			sb.append("AssetLink ON ");
			sb.append("(AssetEntry.entryId = AssetLink.entryId1) ");
			sb.append("OR (AssetEntry.entryId = AssetLink.entryId2)");
		}

		if (entryQuery.getOrderByCol1().equals("ratings") ||
			entryQuery.getOrderByCol2().equals("ratings")) {

			sb.append(" LEFT JOIN ");
			sb.append("RatingsEntry ON ");
			sb.append("(RatingsEntry.classNameId = ");
			sb.append("AssetEntry.classNameId) AND ");
			sb.append("(RatingsEntry.classPK = AssetEntry.classPK)");
		}

		sb.append("WHERE ");

		int whereIndex = sb.index();

		if (entryQuery.getLinkedAssetEntryId() > 0) {
			sb.append(" AND ((AssetLink.entryId1 = ?) OR ");
			sb.append("(AssetLink.entryId2 = ?))");
			sb.append(" AND (AssetEntry.entryId != ?)");
		}

		if (entryQuery.isVisible() != null) {
			sb.append(" AND (visible = ?)");
		}

		if (entryQuery.isExcludeZeroViewCount()) {
			sb.append(" AND (AssetEntry.viewCount > 0)");
		}

		// Layout

		Layout layout = entryQuery.getLayout();

		if (layout != null) {
			sb.append(" AND (AssetEntry.layoutUuid = ?)");
		}

		// Category conditions

		if (entryQuery.getAllCategoryIds().length > 0) {
			if (PropsValues.ASSET_CATEGORIES_SEARCH_HIERARCHICAL) {
				buildAllCategoriesSQL(
					FIND_BY_AND_CATEGORY_IDS_TREE,
					entryQuery.getAllCategoryIds(), sb);
			}
			else {
				buildAllCategoriesSQL(
					FIND_BY_AND_CATEGORY_IDS, entryQuery.getAllCategoryIds(),
					sb);
			}
		}

		if (entryQuery.getAnyCategoryIds().length > 0) {
			if (PropsValues.ASSET_CATEGORIES_SEARCH_HIERARCHICAL) {
				sb.append(
					getCategoryIds(
						FIND_BY_AND_CATEGORY_IDS_TREE,
						entryQuery.getAnyCategoryIds()));
			}
			else {
				sb.append(
					getCategoryIds(
						FIND_BY_AND_CATEGORY_IDS,
						entryQuery.getAnyCategoryIds()));
			}
		}

		if (entryQuery.getNotAllCategoryIds().length > 0) {
			if (PropsValues.ASSET_CATEGORIES_SEARCH_HIERARCHICAL) {
				buildNotAnyCategoriesSQL(
					FIND_BY_AND_CATEGORY_IDS_TREE,
					entryQuery.getNotAllCategoryIds(), sb);
			}
			else {
				buildNotAnyCategoriesSQL(
					FIND_BY_AND_CATEGORY_IDS, entryQuery.getNotAllCategoryIds(),
					sb);
			}
		}

		if (entryQuery.getNotAnyCategoryIds().length > 0) {
			sb.append(" AND (");

			if (PropsValues.ASSET_CATEGORIES_SEARCH_HIERARCHICAL) {
				sb.append(
					getNotCategoryIds(
						FIND_BY_AND_CATEGORY_IDS_TREE,
						entryQuery.getNotAnyCategoryIds()));
			}
			else {
				sb.append(
					getNotCategoryIds(
						FIND_BY_AND_CATEGORY_IDS,
						entryQuery.getNotAnyCategoryIds()));
			}

			sb.append(") ");
		}

		// Asset entry subtypes

		if (entryQuery.getClassTypeIds().length > 0) {
			buildClassTypeIdsSQL(entryQuery.getClassTypeIds(), sb);
		}

		// Tag conditions

		if (entryQuery.getAllTagIds().length > 0) {
			buildAllTagsSQL(entryQuery.getAllTagIds(), sb);
		}

		if (entryQuery.getAnyTagIds().length > 0) {
			sb.append(" AND (");
			sb.append(getTagIds(entryQuery.getAnyTagIds(), StringPool.EQUAL));
			sb.append(") ");
		}

		if (entryQuery.getNotAllTagIds().length > 0) {
			buildNotAnyTagsSQL(entryQuery.getNotAllTagIds(), sb);
		}

		if (entryQuery.getNotAnyTagIds().length > 0) {
			sb.append(" AND (");
			sb.append(getNotTagIds(entryQuery.getNotAnyTagIds()));
			sb.append(") ");
		}

		// Other conditions

		sb.append(
			getDates(
				entryQuery.getPublishDate(), entryQuery.getExpirationDate()));
		sb.append(getGroupIds(entryQuery.getGroupIds()));
		sb.append(getClassNameIds(entryQuery.getClassNameIds()));

		if (!count) {
			sb.append(" ORDER BY ");

			if (entryQuery.getOrderByCol1().equals("ratings")) {
				sb.append("RatingsEntry.score");
			}
			else {
				sb.append("AssetEntry.");
				sb.append(entryQuery.getOrderByCol1());
			}

			sb.append(StringPool.SPACE);
			sb.append(entryQuery.getOrderByType1());

			if (Validator.isNotNull(entryQuery.getOrderByCol2()) &&
				!entryQuery.getOrderByCol1().equals(
					entryQuery.getOrderByCol2())) {

				if (entryQuery.getOrderByCol2().equals("ratings")) {
					sb.append(", RatingsEntry.score");
				}
				else {
					sb.append(", AssetEntry.");
					sb.append(entryQuery.getOrderByCol2());
				}

				sb.append(StringPool.SPACE);
				sb.append(entryQuery.getOrderByType2());
			}
		}

		if (sb.index() > whereIndex) {
			String where = sb.stringAt(whereIndex);

			if (where.startsWith(" AND")) {
				sb.setStringAt(where.substring(4), whereIndex);
			}
		}

		String sql = sb.toString();

		SQLQuery q = session.createSQLQuery(sql);

		if (count) {
			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);
		}
		else {
			q.addEntity("AssetEntry", AssetEntryImpl.class);
		}

		QueryPos qPos = QueryPos.getInstance(q);

		if (entryQuery.getLinkedAssetEntryId() > 0) {
			qPos.add(entryQuery.getLinkedAssetEntryId());
			qPos.add(entryQuery.getLinkedAssetEntryId());
			qPos.add(entryQuery.getLinkedAssetEntryId());
		}

		if (entryQuery.isVisible() != null) {
			qPos.add(entryQuery.isVisible());
		}

		if (layout != null) {
			qPos.add(layout.getUuid());
		}

		if (PropsValues.ASSET_CATEGORIES_SEARCH_HIERARCHICAL) {
			qPos.add(entryQuery.getAllLeftAndRightCategoryIds());
			qPos.add(entryQuery.getAnyLeftAndRightCategoryIds());
			qPos.add(entryQuery.getNotAllLeftAndRightCategoryIds());
			qPos.add(entryQuery.getNotAnyLeftAndRightCategoryIds());
		}
		else {
			qPos.add(entryQuery.getAllCategoryIds());
			qPos.add(entryQuery.getAnyCategoryIds());
			qPos.add(entryQuery.getNotAllCategoryIds());
			qPos.add(entryQuery.getNotAnyCategoryIds());
		}

		qPos.add(entryQuery.getAllTagIds());
		qPos.add(entryQuery.getAnyTagIds());
		qPos.add(entryQuery.getNotAllTagIds());
		qPos.add(entryQuery.getNotAnyTagIds());

		setDates(
			qPos, entryQuery.getPublishDate(),
			entryQuery.getExpirationDate());

		qPos.add(entryQuery.getGroupIds());
		qPos.add(entryQuery.getClassNameIds());

		return q;
	}

	protected void buildClassTypeIdsSQL(long[] classTypeIds, StringBundler sb) {
		sb.append(" AND (");

		for (int i = 0; i < classTypeIds.length; i++) {
			sb.append(" AssetEntry.classTypeId = ");
			sb.append(classTypeIds[i]);

			if ((i + 1) < classTypeIds.length) {
				sb.append(" OR ");
			}
			else {
				sb.append(StringPool.CLOSE_PARENTHESIS);
			}
		}
	}

	protected void buildNotAnyCategoriesSQL(
		String sqlId, long[] categoryIds, StringBundler sb) {

		sb.append(" AND (");

		for (int i = 0; i < categoryIds.length; i++) {
			sb.append("AssetEntry.entryId NOT IN (");
			sb.append(CustomSQLUtil.get(sqlId));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			if ((i + 1) < categoryIds.length) {
				sb.append(" OR ");
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);
	}

	protected void buildNotAnyTagsSQL(long[] tagIds, StringBundler sb) {
		sb.append(" AND (");

		for (int i = 0; i < tagIds.length; i++) {
			sb.append("AssetEntry.entryId NOT IN (");
			sb.append(CustomSQLUtil.get(FIND_BY_AND_TAG_IDS));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			if ((i + 1) < tagIds.length) {
				sb.append(" OR ");
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);
	}

	protected String getCategoryIds(String sqlId, long[] categoryIds) {
		StringBundler sb = new StringBundler();

		sb.append(" AND (");

		for (int i = 0; i < categoryIds.length; i++) {
			sb.append("AssetEntry.entryId IN (");
			sb.append(CustomSQLUtil.get(sqlId));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			if ((i + 1) < categoryIds.length) {
				sb.append(" OR ");
			}
		}

		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	protected String getClassNameIds(long[] classNameIds) {
		if (classNameIds.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(classNameIds.length + 2);

		sb.append(" AND (AssetEntry.classNameId = ?");

		for (int i = 1; i < classNameIds.length; i++) {
			sb.append(" OR AssetEntry.classNameId = ? ");
		}

		sb.append(") ");

		return sb.toString();
	}

	protected String getDates(Date publishDate, Date expirationDate) {
		StringBundler sb = new StringBundler(4);

		if (publishDate != null) {
			sb.append(" AND (AssetEntry.publishDate IS NULL OR ");
			sb.append("AssetEntry.publishDate < ?)");
		}

		if (expirationDate != null) {
			sb.append(" AND (AssetEntry.expirationDate IS NULL OR ");
			sb.append("AssetEntry.expirationDate > ?)");
		}

		return sb.toString();
	}

	protected String getGroupIds(long[] groupIds) {
		if (groupIds.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(groupIds.length + 2);

		sb.append(" AND (AssetEntry.groupId = ? ");

		for (int i = 1; i < groupIds.length; i++) {
			sb.append(" OR AssetEntry.groupId = ? ");
		}

		sb.append(")");

		return sb.toString();
	}

	protected String getNotCategoryIds(String sqlId, long[] notCategoryIds) {
		if (notCategoryIds.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(notCategoryIds.length * 4 - 1);

		for (int i = 0; i < notCategoryIds.length; i++) {
			sb.append("AssetEntry.entryId NOT IN (");
			sb.append(CustomSQLUtil.get(sqlId));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			if ((i + 1) < notCategoryIds.length) {
				sb.append(" AND ");
			}
		}

		return sb.toString();
	}

	protected String getNotTagIds(long[] notTagIds) {
		if (notTagIds.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(notTagIds.length * 4 - 1);

		for (int i = 0; i < notTagIds.length; i++) {
			sb.append("AssetEntry.entryId NOT IN (");
			sb.append(CustomSQLUtil.get(FIND_BY_AND_TAG_IDS));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			if ((i + 1) < notTagIds.length) {
				sb.append(" AND ");
			}
		}

		return sb.toString();
	}

	protected String getTagIds(long[] tagIds, String operator) {
		StringBundler sb = new StringBundler(tagIds.length * 4 - 1);

		for (int i = 0; i < tagIds.length; i++) {
			sb.append("AssetTag.tagId ");
			sb.append(operator);
			sb.append(" ? ");

			if ((i + 1) != tagIds.length) {
				sb.append("OR ");
			}
		}

		return sb.toString();
	}

	protected void setDates(
		QueryPos qPos, Date publishDate, Date expirationDate) {

		if (publishDate != null) {
			Timestamp publishDate_TS = CalendarUtil.getTimestamp(publishDate);

			qPos.add(publishDate_TS);
		}

		if (expirationDate != null) {
			Timestamp expirationDate_TS =
				CalendarUtil.getTimestamp(expirationDate);

			qPos.add(expirationDate_TS);
		}
	}

}