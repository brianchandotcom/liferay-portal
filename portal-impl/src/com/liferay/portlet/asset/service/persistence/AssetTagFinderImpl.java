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
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.asset.NoSuchTagException;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.impl.AssetTagImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public class AssetTagFinderImpl
	extends BasePersistenceImpl<AssetTag> implements AssetTagFinder {

	public static String COUNT_BY_G_N =
		AssetTagFinder.class.getName() + ".countByG_N";

	public static String COUNT_BY_G_C_N =
		AssetTagFinder.class.getName() + ".countByG_C_N";

	public static String COUNT_BY_G_N_P =
		AssetTagFinder.class.getName() + ".countByG_N_P";

	public static String FIND_BY_ENTRY_ID =
		AssetTagFinder.class.getName() + ".findByEntryId";

	public static String FIND_BY_G_N =
		AssetTagFinder.class.getName() + ".findByG_N";

	public static String FIND_BY_C_C =
		AssetTagFinder.class.getName() + ".findByC_C";

	public static String FIND_BY_G_C_N =
		AssetTagFinder.class.getName() + ".findByG_C_N";

	public static String FIND_BY_G_N_P =
		AssetTagFinder.class.getName() + ".findByG_N_P";

	public static String FIND_BY_G_N_S_E =
			AssetTagFinder.class.getName() + ".findByG_N_S_E";

	public int countByG_C_N(long groupId, long classNameId, String name)
		throws SystemException {

		return doCountByG_C_N(groupId, classNameId, name, false);
	}

	public int countByG_N_P(long groupId, String name, String[] tagProperties)
		throws SystemException {

		return doCountByG_N_P(groupId, name, tagProperties, false);
	}

	public int filterCountByG_N(long groupId, String name)
		throws SystemException {

		return doCountByG_N(groupId, name, true);
	}

	public int filterCountByG_C_N(long groupId, long classNameId, String name)
		throws SystemException {

		return doCountByG_C_N(groupId, classNameId, name, true);
	}

	public int filterCountByG_N_P(
			long groupId, String name, String[] tagProperties)
		throws SystemException {

		return doCountByG_N_P(groupId, name, tagProperties, true);
	}

	public AssetTag filterFindByG_N(long groupId, String name)
		throws NoSuchTagException, SystemException {

		return doFindByG_N(groupId, name, true);
	}

	public List<AssetTag> filterFindByG_C_N(
			long groupId, long classNameId, String name, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return doFindByG_C_N(
			groupId, classNameId, name, start, end, obc, true);
	}

	public List<AssetTag> filterFindByG_N_P(
			long groupId, String name, String[] tagProperties, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		return doFindByG_N_P(
			groupId, name, tagProperties, start, end, obc, true);
	}

	public List<AssetTag> findByEntryId(long entryId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_ENTRY_ID);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("AssetTag", AssetTagImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(entryId);

			return (List<AssetTag>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public AssetTag findByG_N(long groupId, String name)
		throws NoSuchTagException, SystemException {

		return doFindByG_N(groupId, name, false);
	}

	public List<AssetTag> findByC_C(long classNameId, long classPK)
		throws SystemException {

		Session session = null;

		try {
			AssetEntry entry = AssetEntryUtil.fetchByC_C(
				classNameId, classPK);

			if (entry == null) {
				return Collections.emptyList();
			}

			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_C);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("AssetTag", AssetTagImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(entry.getEntryId());

			return (List<AssetTag>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<AssetTag> findByG_C_N(
			long groupId, long classNameId, String name, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return doFindByG_C_N(
			groupId, classNameId, name, start, end, obc, false);
	}

	public List<AssetTag> findByG_N_P(
			long groupId, String name, String[] tagProperties, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		return doFindByG_N_P(
			groupId, name, tagProperties, start, end, obc, false);
	}

	public List<AssetTag> findByG_N_S_E(
			long groupId, String name, int startPeriod, int endPeriod)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_N_S_E);
			SQLQuery q = session.createSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(name);
			qPos.add(startPeriod);
			qPos.add(endPeriod);

			List<AssetTag> assetTags = new ArrayList<AssetTag>();

			Iterator<Object[]> itr = q.iterate();

			while (itr.hasNext()) {
				Object[] array = itr.next();

				AssetTag assetTag = new AssetTagImpl();

				assetTag.setTagId(
					GetterUtil.getLong((Serializable)array[0]));
				assetTag.setName(GetterUtil.getString((Serializable)array[1]));
				assetTag.setAssetCount(
					GetterUtil.getInteger((Serializable)array[2]));

				assetTags.add(assetTag);
			}

			return assetTags;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getJoin(String[] tagProperties) {
		if (tagProperties.length == 0) {
			return StringPool.BLANK;
		}
		else {
			StringBundler sb = new StringBundler(tagProperties.length * 3 + 1);

			sb.append(" INNER JOIN AssetTagProperty ON ");
			sb.append(" (AssetTagProperty.tagId = AssetTag.tagId) AND ");

			for (int i = 0; i < tagProperties.length; i++) {
				sb.append("(AssetTagProperty.key_ = ? AND ");
				sb.append("AssetTagProperty.value = ?) ");

				if ((i + 1) < tagProperties.length) {
					sb.append(" AND ");
				}
			}

			return sb.toString();
		}
	}

	protected int doCountByG_N(
			long groupId, String name, boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_N);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, AssetTag.class.getName(), "AssetTag.tagId", groupId);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
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

	protected int doCountByG_C_N(
			long groupId, long classNameId, String name,
			boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_C_N);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, AssetTag.class.getName(), "AssetTag.tagId", groupId);
			}

			SQLQuery q = session.createSQLQuery(sql);

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

	protected int doCountByG_N_P(
			long groupId, String name, String[] tagProperties,
			boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_N_P);

			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(tagProperties));

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, AssetTag.class.getName(), "AssetTag.tagId", groupId);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, tagProperties);

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

	protected AssetTag doFindByG_N(
			long groupId, String name, boolean inlineSQLHelper)
		throws NoSuchTagException, SystemException {

		name = name.trim().toLowerCase();

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_N);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, AssetTag.class.getName(), "AssetTag.tagId", groupId);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("AssetTag", AssetTagImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(name);

			List<AssetTag> list = q.list();

			if (list.size() == 0) {
				StringBundler sb = new StringBundler(6);

				sb.append("No AssetTag exists with the key ");
				sb.append("{groupId=");
				sb.append(groupId);
				sb.append(", name=");
				sb.append(name);
				sb.append("}");

				throw new NoSuchTagException(sb.toString());
			}
			else {
				return list.get(0);
			}
		}
		catch (NoSuchTagException nste) {
			throw nste;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<AssetTag> doFindByG_C_N(
			long groupId, long classNameId, String name, int start, int end,
			OrderByComparator obc, boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_C_N);

			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, AssetTag.class.getName(), "AssetTag.tagId", groupId);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("AssetTag", AssetTagImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(classNameId);
			qPos.add(name);
			qPos.add(name);

			return (List<AssetTag>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<AssetTag> doFindByG_N_P(
			long groupId, String name, String[] tagProperties, int start,
			int end, OrderByComparator obc, boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_N_P);

			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(tagProperties));
			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, AssetTag.class.getName(), "AssetTag.tagId", groupId);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("AssetTag", AssetTagImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, tagProperties);

			qPos.add(groupId);
			qPos.add(name);
			qPos.add(name);

			return (List<AssetTag>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected void setJoin(QueryPos qPos, String[] tagProperties) {
		for (String tagProperty : tagProperties) {
			String[] tagPropertyParts = StringUtil.split(
				tagProperty, CharPool.COLON);

			String key = StringPool.BLANK;

			if (tagPropertyParts.length > 0) {
				key = GetterUtil.getString(tagPropertyParts[0]);
			}

			String value = StringPool.BLANK;

			if (tagPropertyParts.length > 1) {
				value = GetterUtil.getString(tagPropertyParts[1]);
			}

			qPos.add(key);
			qPos.add(value);
		}
	}

}