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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.asset.NoSuchTagException;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetTagConstants;
import com.liferay.portlet.asset.model.impl.AssetTagImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 */
public class AssetTagFinderImpl
	extends BasePersistenceImpl<AssetTag> implements AssetTagFinder {

	public static final String COUNT_BY_G_N =
		AssetTagFinder.class.getName() + ".countByG_N";

	public static final String COUNT_BY_G_C_N =
		AssetTagFinder.class.getName() + ".countByG_C_N";

	public static final String COUNT_BY_G_N_P =
		AssetTagFinder.class.getName() + ".countByG_N_P";

	public static final String FIND_BY_G_N =
		AssetTagFinder.class.getName() + ".findByG_N";

	public static final String FIND_BY_G_C_N =
		AssetTagFinder.class.getName() + ".findByG_C_N";

	public static final String FIND_BY_G_N_P =
		AssetTagFinder.class.getName() + ".findByG_N_P";

	public static final String FIND_BY_G_N_S_E =
			AssetTagFinder.class.getName() + ".findByG_N_S_E";

	@Override
	public int countByG_C_N(long groupId, long classNameId, String name)
		throws SystemException {

		return doCountByG_C_N(groupId, classNameId, name, false);
	}

	@Override
	public int countByG_N_P(long groupId, String name, String[] tagProperties)
		throws SystemException {

		return doCountByG_N_P(groupId, name, tagProperties, false);
	}

	@Override
	public int filterCountByG_N(long groupId, String name)
		throws SystemException {

		return doCountByG_N(groupId, name, true);
	}

	@Override
	public int filterCountByG_C_N(long groupId, long classNameId, String name)
		throws SystemException {

		return doCountByG_C_N(groupId, classNameId, name, true);
	}

	@Override
	public int filterCountByG_N_P(
			long groupId, String name, String[] tagProperties)
		throws SystemException {

		return doCountByG_N_P(groupId, name, tagProperties, true);
	}

	@Override
	public AssetTag filterFindByG_N(long groupId, String name)
		throws NoSuchTagException, SystemException {

		return doFindByG_N(groupId, name, true);
	}

	@Override
	public List<AssetTag> filterFindByG_C_N(
			long groupId, long classNameId, String name, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return doFindByG_C_N(groupId, classNameId, name, start, end, obc, true);
	}

	@Override
	public List<AssetTag> filterFindByG_N_P(
			long[] groupIds, String name, String[] tagProperties, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		return doFindByG_N_P(
			groupIds, name, tagProperties, start, end, obc, true);
	}

	@Override
	public AssetTag findByG_N(long groupId, String name)
		throws NoSuchTagException, SystemException {

		return doFindByG_N(groupId, name, false);
	}

	@Override
	public List<AssetTag> findByG_C_N(
			long groupId, long classNameId, String name, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return doFindByG_C_N(
			groupId, classNameId, name, start, end, obc, false);
	}

	@Override
	public List<AssetTag> findByG_N_P(
			long[] groupIds, String name, String[] tagProperties, int start,
			int end, OrderByComparator obc)
		throws SystemException {

		return doFindByG_N_P(
			groupIds, name, tagProperties, start, end, obc, false);
	}

	@Override
	public List<AssetTag> findByG_N_S_E(
			long groupId, String name, int startPeriod, int endPeriod,
			int periodLength)
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
			qPos.add(periodLength);
			qPos.add(endPeriod);

			List<AssetTag> assetTags = new ArrayList<AssetTag>();

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
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
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
					sql, AssetTag.class.getName(), "AssetTag.tagId",
					PortalUtil.getSiteGroupId(groupId));
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

			String lowerCaseName = StringUtil.toLowerCase(name);

			qPos.add(lowerCaseName);

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
					sql, AssetTag.class.getName(), "AssetTag.tagId",
					PortalUtil.getSiteGroupId(groupId));
			}

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(classNameId);

			String lowerCaseName = StringUtil.toLowerCase(name);

			qPos.add(lowerCaseName);
			qPos.add(lowerCaseName);

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

			String lowerCaseName = StringUtil.toLowerCase(name);

			qPos.add(lowerCaseName);
			qPos.add(lowerCaseName);

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

		name = StringUtil.toLowerCase(name.trim());

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

			String lowerCaseName = StringUtil.toLowerCase(name);

			qPos.add(lowerCaseName);

			List<AssetTag> tags = q.list();

			if (!tags.isEmpty()) {
				return tags.get(0);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}

		StringBundler sb = new StringBundler(6);

		sb.append("No AssetTag exists with the key ");
		sb.append("{groupId=");
		sb.append(groupId);
		sb.append(", name=");
		sb.append(name);
		sb.append("}");

		throw new NoSuchTagException(sb.toString());
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
					sql, AssetTag.class.getName(), "AssetTag.tagId",
					PortalUtil.getSiteGroupId(groupId));
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("AssetTag", AssetTagImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(classNameId);

			String lowerCaseName = name;

			if (Validator.isNotNull(name)) {
				lowerCaseName = StringUtil.toLowerCase(name);
			}

			qPos.add(lowerCaseName);
			qPos.add(lowerCaseName);

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
			long[] groupIds, String name, String[] tagProperties, int start,
			int end, OrderByComparator obc, boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_N_P);

			sql = StringUtil.replace(sql, "[$JOIN$]", getJoin(tagProperties));
			sql = StringUtil.replace(
				sql, "[$GROUP_ID$]", getGroupIds(groupIds));
			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, AssetTag.class.getName(), "AssetTag.tagId", groupIds);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("AssetTag", AssetTagImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			setJoin(qPos, tagProperties);

			qPos.add(groupIds);

			String lowerCaseName = StringUtil.toLowerCase(name);

			qPos.add(lowerCaseName);
			qPos.add(lowerCaseName);

			return (List<AssetTag>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String getGroupIds(long[] groupIds) {
		if (groupIds.length == 0) {
			return StringPool.BLANK;
		}

		StringBundler sb = new StringBundler(groupIds.length * 2);

		sb.append(StringPool.OPEN_PARENTHESIS);

		for (int i = 0; i < groupIds.length; i++) {
			sb.append("groupId = ?");

			if ((i + 1) < groupIds.length) {
				sb.append(" OR ");
			}
		}

		sb.append(") AND");

		return sb.toString();
	}

	protected String getJoin(String[] tagProperties) {
		if (tagProperties.length == 0) {
			return StringPool.BLANK;
		}

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

	protected void setJoin(QueryPos qPos, String[] tagProperties) {
		for (String tagProperty : tagProperties) {
			String[] tagPropertyParts = StringUtil.split(
				tagProperty, AssetTagConstants.PROPERTY_KEY_VALUE_SEPARATOR);

			if (tagPropertyParts.length <= 1) {
				tagPropertyParts = StringUtil.split(
					tagProperty, CharPool.COLON);
			}

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