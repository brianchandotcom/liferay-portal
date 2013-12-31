/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.asset.NoSuchCategoryException;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetCategoryConstants;
import com.liferay.portlet.asset.model.impl.AssetCategoryImpl;
import com.liferay.portlet.asset.model.impl.AssetCategoryModelImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Jorge Ferrer
 * @author Shuyang Zhou
 */
public class AssetCategoryFinderImpl
	extends BasePersistenceImpl<AssetCategory> implements AssetCategoryFinder {

	public static final String COUNT_BY_G_C_N =
		AssetCategoryFinder.class.getName() + ".countByG_C_N";

	public static final String COUNT_BY_G_N_P =
		AssetCategoryFinder.class.getName() + ".countByG_N_P";

	public static final String FIND_BY_G_L =
		AssetCategoryFinder.class.getName() + ".findByG_L";

	public static final String FIND_BY_G_N =
		AssetCategoryFinder.class.getName() + ".findByG_N";

	public static final String FIND_BY_G_N_P =
		AssetCategoryFinder.class.getName() + ".findByG_N_P";

	public static final FinderPath FINDER_PATH_FIND_BY_G_L = new FinderPath(
		AssetCategoryModelImpl.ENTITY_CACHE_ENABLED,
		AssetCategoryModelImpl.FINDER_CACHE_ENABLED, List.class,
		AssetCategoryPersistenceImpl.FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
		"findByG_L", new String[] {Long.class.getName()});

	@Override
	public int countByG_C_N(long groupId, long classNameId, String name)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_C_N);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(classNameId);
			qPos.add(name);
			qPos.add(name);

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

	@Override
	public int countByG_N_P(
			long groupId, String name, String[] categoryProperties)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_N_P);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, categoryProperties);

			qPos.add(groupId);
			qPos.add(name);
			qPos.add(name);

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

	@Override
	public List<Long> findByG_L(Long parentCategoryId) throws SystemException {
		Object[] finderArgs = new Object[] {parentCategoryId};

		List<Long> list = (List<Long>)FinderCacheUtil.getResult(
			FINDER_PATH_FIND_BY_G_L, finderArgs, this);

		if (list != null) {
			return list;
		}

		AssetCategory parentAssetCategory = AssetCategoryUtil.fetchByPrimaryKey(
			parentCategoryId);

		if (parentAssetCategory == null) {
			return Collections.emptyList();
		}

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_L);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("categoryId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(parentAssetCategory.getGroupId());
			qPos.add(parentAssetCategory.getLeftCategoryId());
			qPos.add(parentAssetCategory.getRightCategoryId());

			list = q.list();
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			if (list == null) {
				FinderCacheUtil.removeResult(
					FINDER_PATH_FIND_BY_G_L, finderArgs);
			}
			else {
				FinderCacheUtil.putResult(
					FINDER_PATH_FIND_BY_G_L, finderArgs, list);
			}

			closeSession(session);
		}

		return list;
	}

	@Override
	public AssetCategory findByG_N(long groupId, String name)
		throws NoSuchCategoryException, SystemException {

		name = StringUtil.toLowerCase(name.trim());

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_N);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("AssetCategory", AssetCategoryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(name);

			List<AssetCategory> categories = q.list();

			if (!categories.isEmpty()) {
				return categories.get(0);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}

		StringBundler sb = new StringBundler(6);

		sb.append("No AssetCategory exists with the key ");
		sb.append("{groupId=");
		sb.append(groupId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		throw new NoSuchCategoryException(sb.toString());
	}

	@Override
	public List<AssetCategory> findByG_N_P(
			long groupId, String name, String[] categoryProperties)
		throws SystemException {

		return findByG_N_P(
			groupId, name, categoryProperties, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS);
	}

	@Override
	public List<AssetCategory> findByG_N_P(
			long groupId, String name, String[] categoryProperties, int start,
			int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_N_P);

			sql = StringUtil.replace(
				sql, "[$JOIN$]", getJoin(categoryProperties));

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("AssetCategory", AssetCategoryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, categoryProperties);

			qPos.add(groupId);
			qPos.add(name);
			qPos.add(name);

			return (List<AssetCategory>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getJoin(String[] categoryProperties) {
		if (categoryProperties.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(categoryProperties.length * 3 + 2);

		sb.append(" INNER JOIN AssetCategoryProperty ON ");
		sb.append(" (AssetCategoryProperty.categoryId = ");
		sb.append(" AssetCategory.categoryId) AND ");

		for (int i = 0; i < categoryProperties.length; i++) {
			sb.append("(AssetCategoryProperty.key_ = ? AND ");
			sb.append("AssetCategoryProperty.value = ?) ");

			if ((i + 1) < categoryProperties.length) {
				sb.append(" AND ");
			}
		}

		return sb.toString();
	}

	protected void setJoin(QueryPos qPos, String[] categoryProperties) {
		for (int i = 0; i < categoryProperties.length; i++) {
			String[] categoryProperty = StringUtil.split(
				categoryProperties[i],
				AssetCategoryConstants.PROPERTY_KEY_VALUE_SEPARATOR);

			if (categoryProperty.length <= 1) {
				categoryProperty = StringUtil.split(
					categoryProperties[i], CharPool.COLON);
			}

			String key = StringPool.BLANK;

			if (categoryProperty.length > 0) {
				key = GetterUtil.getString(categoryProperty[0]);
			}

			String value = StringPool.BLANK;

			if (categoryProperty.length > 1) {
				value = GetterUtil.getString(categoryProperty[1]);
			}

			qPos.add(key);
			qPos.add(value);
		}
	}

}